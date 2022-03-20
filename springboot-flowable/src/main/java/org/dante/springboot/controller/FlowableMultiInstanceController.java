package org.dante.springboot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dante.springboot.controller.DateUtils.TimeFormat;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/flowmulti")
public class FlowableMultiInstanceController {
	
//	private static final String FLOW_DEFINITION = "bpmn20/multi_instance.bpmn20.xml";
	private static final String FLOW_DEFINITION = "bpmn20/multi2.bpmn20.xml";
	private static final String PROCESSDEFINITION_KEY = "mutil_instance_2";
	private static final String COMMAND = "command";
	private static final String ROLE = "role";
	private static final String BGS = "BGS";	// 办公室领导
	private static final String BGSYG = "BGSYG";	// 办公室员工
	
	private static final String RSC = "RSC";	// 人事处领导
	private static final String RSCYG = "RSCYG";	// 人事处员工
	private static final String HB_COLLECTION = "roleList"; // 会办部门集合
	private static final String BM_COLLECTION = "userList"; // 部门处理集合
	
	private static String PROCESS_INSTANCE_ID = "dbdddd97-9ef1-11ec-b358-ba08179e7e59";	// 流程实例Id
	
	private static Map<String, List<String>> multiMap = new HashMap<>();
	
	static {
		multiMap.put(ROLE, Arrays.asList(BGS, RSC)); // 办公室、人事处
		multiMap.put(BGS, Arrays.asList("b01", "b02")); // 办公室领导
		multiMap.put(BGSYG, Arrays.asList("by01", "by02")); // 办公室员工
		multiMap.put(RSC, Arrays.asList("r01", "r02")); // 人事处领导
		multiMap.put(RSCYG, Arrays.asList("ry01", "ry02")); // 人事处员工
	}
	
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	
	/**
	 * 部署流程定义
	 * 
	 * @return
	 */
	@GetMapping("/create")
	public Deployment createDeploy() {
		Deployment deploy = repositoryService.createDeployment().addClasspathResource(FLOW_DEFINITION).deploy();
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).latestVersion().singleResult();
		log.info("流程部署成功：{} - {}，流程定义Id：{}", deploy.getId(), deploy.getName(), processDefinition.getId());
		return deploy;
	}
	
	

	/**
	 * 开始一个流程
	 * 
	 * @return
	 */
	@GetMapping("/start_process")
	public String startProcessInstance() {
		Map<String, Object> varMap = new HashMap<String, Object>();
		varMap.put(HB_COLLECTION, multiMap.get(ROLE)); 	// 办公室、人事处会办
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESSDEFINITION_KEY, varMap);
		PROCESS_INSTANCE_ID = processInstance.getId();
		log.info("启动成功: {}", processInstance.getId());
		return processInstance.getProcessInstanceId();
	}
	
	/**
	 * 查看当前用户的待办任务
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/task/{userId}")
	public int getTaskList(@PathVariable String userId) {
		String role = getRoleByUserId(userId); // 办公室、人事处领导角色
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateGroup(role).list();
		taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateUser(userId).list();
		if(CollectionUtils.isEmpty(taskList)) {
			taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateUser(userId).list();
			if(CollectionUtils.isEmpty(taskList)) {
				taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskAssignee(userId).list();
			}
		}
		
		
		return taskList.size();
	}
	
	/**
	 * 转办任务 和 委派任务
	 * 
	 * https://blog.51cto.com/nanjke/2845653
	 * @return
	 */
	@GetMapping("/task_turn/{owenerId}/{acceptUserId}")
	public String turnTodo(@PathVariable String ownerId, @PathVariable String acceptUserId) {
		String taskId = "";
		
		// 转办任务 (不保留当前人owner的待办)
		taskService.setOwner(taskId, ownerId);
		taskService.setAssignee(taskId, acceptUserId);
		
		// 委派任务，是将任务节点分给其他人处理，等其他人处理好之后，委派任务会自动回到委派人的任务中
		// 即，保留当前人的待办
		taskService.setOwner(taskId, ownerId);
		taskService.delegateTask(taskId, acceptUserId);
		
		// 被委派任务的办理:   办理完成后，委派任务会自动回到委派人的任务中
		Map<String, Object> variables = taskService.getVariables(taskId);
		taskService.resolveTask(taskId, variables);
		return "";
	}
	
	/**
	 * 会办
	 * 
	 * /hb_process/bm/b01
	 * /hb_process/bm/r01
	 * 
	 * /hb_process/jy/b01
	 * /hb_process/jy/r01
	 * 
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/hb_process/{command}/{userId}")
	public String hbProcess(@PathVariable String command, @PathVariable String userId) throws Exception {
		String role = getRoleByUserId(userId); // 办公室、人事处领导角色
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateGroup(role).list();
		
		log.info("{} - {}, 任务 {}", userId, role, taskList.size());
		if(taskList.size() > 1) {
			throw new Exception("错误");
		} else if(taskList.size() == 0) {
			return "无任务";
		}
		Task task = taskList.get(0);
		Map<String, Object> variables = taskService.getVariables(task.getId());
		
		if("bm".equalsIgnoreCase(command)) {
			variables.put(COMMAND, "sendBM"); // 部门办理
			List<String> userList = (List<String>) variables.get(BM_COLLECTION);
			if(CollectionUtils.isEmpty(userList)) {
				userList = new ArrayList<>();
			}
			if(BGS.equals(role)) {
				if(!userList.contains("by01")) {
					userList.add("by01");
				}
			} else if(RSC.equals(role)) {
				if(!userList.contains("ry01")) {
					userList.add("ry01");
				}
			}
			log.info("部门处理人 ==> {}", userList);
			variables.put(BM_COLLECTION, userList);
		} else if("jy".equals(command)) {
			variables.put(COMMAND, "sendJY"); // 机要办理
		}
		
		variables.put("FINISHED", Boolean.TRUE);
		variables.put("FINISHED_STR", Boolean.TRUE.toString());
		taskService.setAssignee(task.getId(), userId);	// 设置任务的受理人
		taskService.complete(task.getId(), variables);
		return role.concat(" - ").concat(userId).concat(" - ").concat(command);
	}
	
	/**
	 * 部门办理
	 * 
	 * bm_process/hb/by01
	 * bm_process/hb/ry01
	 * 
	 * bm_process/doc/by01
	 * bm_process/doc/ry01
	 * 
	 * @param userId
	 * @param command   hb -> 办理完成  doc-> 转发文
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/bm_process/{command}/{userId}")
	public String bmProcess(@PathVariable String command, @PathVariable String userId) throws Exception {
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskCandidateUser(userId).list();
		if(CollectionUtils.isEmpty(taskList)) {
			taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskAssignee(userId).list();
		}
		log.info("{}, 任务 {}", userId, taskList.size());
		if(taskList.size() > 1) {
			throw new Exception("错误");
		} else if(taskList.size() == 0) {
			return "无任务";
		}
		Task task = taskList.get(0);
		Map<String, Object> variables = taskService.getVariables(task.getId());
		if("hb".equals(command)) {
			variables.put(COMMAND, "sendHB"); // 办理完成
		} else if("doc".equals(command)) {
			variables.put(COMMAND, "sendDoc"); // 转发文
		}
		
		variables.put("FINISHED_2", Boolean.FALSE);
		
		Boolean FINISHED = (Boolean) variables.get("FINISHED");
		if(Boolean.TRUE.equals(FINISHED)) {
			System.out.println("==================================");
		} else {
			System.out.println("***********************************");
		}
		
		taskService.setAssignee(task.getId(), userId);	// 设置任务的受理人
		taskService.complete(task.getId(), variables);
		return userId.concat(" - ").concat(command);
	}
	
	/**
	 * 机要办理
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/jy_process")
	public String jyProcess() throws Exception {
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(PROCESS_INSTANCE_ID).taskAssignee("test001").list();
		log.info("机要任务 {}", taskList.size());
		if(taskList.size() > 1) {
			throw new Exception("错误");
		} else if(taskList.size() == 0) {
			return "无任务";
		}
		Task task = taskList.get(0);
		Map<String, Object> variables = taskService.getVariables(task.getId());
		log.info("会办人员：{}", variables.get(HB_COLLECTION));
		log.info("部门办理人员：{}", variables.get(BM_COLLECTION));
		taskService.complete(task.getId(), variables);
		return "";
	}
	
	
	
	/**
	 * 处理任务
	 * 
	 * test001 (no command) 收文登记，Task 不要 complete /handle_task/test001/1
	 * 
	 * test001 send -> 办公室主任 /handle_task/test001/send
	 * 
	 * test001 close -> 结束	/handle_task/test001/close
	 * 
	 * test002 refused -> 退回收文登记 /handle_task/test002/refused
	 * 
	 * 
	 * @param idenType
	 * @param command 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/handle_task/{userId}/{command}")
	public Map<String, Object> handleTaskList(@PathVariable String userId, @PathVariable String command) {
		String comment = "";
		Map<String, Object> varMap = new HashMap<>();
		
		String role = getRoleByUserId(userId); // 用户角色
		List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(role).list();
		if(CollectionUtils.isEmpty(taskList)) {
			taskList = taskService.createTaskQuery().taskCandidateUser(userId).list();
			if(CollectionUtils.isEmpty(taskList)) {
				taskList = taskService.createTaskQuery().taskAssignee(userId).list();
			}
		}
		log.info("{}待办任务{}", userId, taskList.size());
		
		for (Task task : taskList) {
			if("hb".equals(command)) {
				List<String> userList = taskService.getVariable(task.getId(), BM_COLLECTION, List.class);
				if(CollectionUtils.isEmpty(userList)) {
					userList = new ArrayList<>();
				}
				// 会办部门
				if(BGS.equals(role)) {
					if(!userList.contains("by01")) {
						userList.add("by01");
					}
					comment = "交由办公室员工by01进行处理";
				} else if(RSC.equals(role)) {
					if(!userList.contains("ry01")) {
						userList.add("ry01");
					}
					comment = "交由人事处员工ry01进行处理";
				}
				varMap.put(BM_COLLECTION, userList);
				varMap.put(COMMAND, "sendBM"); // 部门办理
			} else if("bm".equals(command)) {
				// 部门办理
				comment = userId + "办理完成";
				varMap.put(COMMAND, "sendHB"); // 部门办理
			} else if("jy".equals(command)) {
				comment = userId + "交由机要办理";
				varMap.put(COMMAND, "sendJY"); // 机要办理
			} else if("end".equals(command)) {
				comment = userId + "办结";
			}
			
			taskService.setAssignee(task.getId(), userId);	// 设置任务的受理人
			taskService.addComment(task.getId(), task.getProcessInstanceId(), comment);
			taskService.complete(task.getId(), varMap);
		}
		
		// 历史任务
		/*
		List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery()
				.processDefinitionKey(PROCESSDEFINITION_KEY)
				.processInstanceId(PROCESS_INSTANCE_ID)
				.orderByHistoricTaskInstanceEndTime()
				.asc()
				.list();
		log.info("===============================================================");
		historicTaskList.forEach(ht -> {
			log.info("{}【{}】，{} ～ {}", ht.getName(), ht.getAssignee(),
					ht.getStartTime() != null ? DateUtils.formatDate(ht.getStartTime(), TimeFormat.LONG_DATE_PATTERN_LINE) : ht.getStartTime(),
					ht.getEndTime() != null ? DateUtils.formatDate(ht.getEndTime(), TimeFormat.LONG_DATE_PATTERN_LINE) : ht.getEndTime());
			Comment c = taskService.getTaskComments(ht.getId(), "comment").get(0);
			log.info("  审批意见: {}", c.getFullMessage());
		});
		log.info("===============================================================");
		*/
		return varMap;
	}
	
	/**
	 * 退回上一个节点
	 * 
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/handle_task/{userId}/back")
	public String goBackPrevTask(@PathVariable String userId) throws Exception {
		int taskIndex = 0;
		if("r01".equals(userId)) {
			taskIndex = 1;
		}
		
		Task task = taskService.createTaskQuery().taskCandidateUser(userId).list().get(0);
		String targetKey = "sid-B8BEBEF0-EE82-4358-BAB5-C1C0445A73EE";
		log.info("CURENT_TASK: {} == {}", task.getTaskDefinitionId(), task.getTaskDefinitionKey());
        // 目标节点信息
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
        		.activityId(targetKey)
        		.processInstanceId(task.getProcessInstanceId())
                .activityType("userTask")
        		.orderByHistoricActivityInstanceStartTime().asc().list();
        HistoricActivityInstance historicActivityInstance = list.get(0);
    	log.info(historicActivityInstance.getId() + " - " + historicActivityInstance.getAssignee());
        //目标节点的处理人ID
        
//        Map<String, Object> params = new HashMap<>();
//        runtimeService.setVariables(task.getProcessInstanceId(), params);
        
        // 退回会办
       List<String> currentActivityIds = new ArrayList<>();
       currentActivityIds.add(task.getTaskDefinitionKey());
       runtimeService
        	.createChangeActivityStateBuilder()
        	.processInstanceId(task.getProcessInstanceId())
        	.moveActivityIdsToSingleActivityId(currentActivityIds, targetKey)
        	.changeState();
		return "";
	}
	
	
	/**
	 * 历史查询
	 * 
	 * 一旦流程执行完毕，活动的数据都会被清空，上面查询的接口都查不到数据
	 * 
	 * @return
	 */
	@GetMapping("/query_hist")
	public Map<String, Object> queryHistory() {
		Map<String, Object> result = new HashMap<>();
		// 历史流程实例
		List<HistoricProcessInstance> historicProcessList = historyService.createHistoricProcessInstanceQuery()
				.processDefinitionKey(PROCESSDEFINITION_KEY).list();
		// 历史任务
		List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery()
				.processDefinitionKey(PROCESSDEFINITION_KEY).list();
		// 历史活动实例查询
		List<HistoricActivityInstance> haiList = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(PROCESS_INSTANCE_ID)
                .activityType("userTask")
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
		
		result.put("历史流程实例", historicProcessList.size());
		result.put("历史任务", historicTaskList.size());
		result.put("历史活动实例", haiList.size());
		
		log.info("===============================================================");
		haiList.forEach(h -> {
			log.info("{}【{}】，{} ～ {}", h.getActivityName(), h.getAssignee(),
					h.getStartTime() != null ? DateUtils.formatDate(h.getStartTime(), TimeFormat.LONG_DATE_PATTERN_LINE) : h.getStartTime(),
							h.getEndTime() != null ? DateUtils.formatDate(h.getEndTime(), TimeFormat.LONG_DATE_PATTERN_LINE) : h.getEndTime());
			Comment comment = taskService.getTaskComments(h.getTaskId(), "comment").get(0);
			log.info("  审批意见: {}", comment.getFullMessage());
		});
		
		log.info("===============================================================");
		return result;
	}
	
	/**
	 * 获取指定用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	private String getRoleByUserId(String userId) {
		String role = multiMap.entrySet().stream().filter(m -> m.getValue().contains(userId))
				.map(m -> m.getKey()).collect(Collectors.joining());
		return role;
	}
	
	
}
