package org.dante.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dante.springboot.enums.CandidateGroup;
import org.dante.springboot.util.UserUtil;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/flowbasic")
public class FlowableBasicController {
	
	private static final String FLOW_DEFINITION = "bpmn20/test1_parallel.bpmn20.xml";
	private static final String PROCESSDEFINITION_KEY = "pro1_parallel_key";
	
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
		log.info("流程部署成功：{} - {}", deploy.getId(), deploy.getName());
		return deploy;
	}
	
	/**
	 * 查询流程定义
	 * 
	 * @param deployId
	 * @return
	 */
	@GetMapping("/query_process_definition/{deployId}")
	public String queryProcessDefinition(@PathVariable String deployId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
//		repositoryService.suspendProcessDefinitionById(processDefinition.getId()); // 挂起
//		repositoryService.activateProcessDefinitionById(processDefinition.getId()); // 激活
		return processDefinition.getId();
	}

	/**
	 * 开始一个流程
	 * 
	 * @return
	 */
	@GetMapping("/start_process")
	public String startProcessInstance() {
		// 选取请假类型
		Map<String, Object> varMap = new HashMap<String, Object>();
		varMap.put("leaveType", "sick");
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESSDEFINITION_KEY);
//		runtimeService.startProcessInstanceById("流程定义Id");
		log.info("启动成功: {}", processInstance.getId());
		return processInstance.getProcessInstanceId();
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
	@GetMapping("/handle_task/{idenType}/{command}")
	public Map<String, Object> handleTaskList(@PathVariable String idenType, @PathVariable String command) {
		Map<String, Object> returnMap = new HashMap<>();
		List<Task> taskList = taskService.createTaskQuery().taskAssignee(idenType).list();
		log.info("{}待办任务{}", idenType, taskList.size());
		
		for (Task task : taskList) {
			if("test001".equals(idenType)  && "1".equals(command)) {
				log.info("test001 收文登记保存，待提交办公室主任处理...");
			} else {
				returnMap.put("command", command);
				taskService.complete(task.getId(), returnMap);
			}
		}
		
		// 历史任务
		List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery()
				.processDefinitionKey(PROCESSDEFINITION_KEY).list();
		historicTaskList.forEach(ht -> {
			log.info("任务: {} - {} - {}", ht.getAssignee(), ht.getName(), ht.getEndTime());
		});
		
		
		return returnMap;
	}
	
	
	/**
	 * 查询待处理任务，并完成任务
	 * 
	 * @param idenType 当前登陆人
	 * @return
	 */
	@GetMapping("/qyery_task_list/{idenType}")
	public Map<String, Object> queryTaskList(@PathVariable String idenType) {
		// 学生、老师、校长查询任务
		String candidateGroup = "";
		String candidateGroupDesc = "";
		String claim = "";
		Map<String, Object> variables = new HashMap<>();
		switch (idenType) {
		case "stu":
			candidateGroup = CandidateGroup.STU.getValue();
			candidateGroupDesc = CandidateGroup.STU.getDesc();
			claim = "学生但丁";
			break;
		case "tech":
			candidateGroup = CandidateGroup.TECH.getValue();
			candidateGroupDesc = CandidateGroup.TECH.getDesc();
			claim = "老师斯巴达";
			variables.put("command", "agree");	// 流程变量，用于网关流程条件判断
			break;
		case "mtech":
			candidateGroup = CandidateGroup.MTECH.getValue();
			candidateGroupDesc = CandidateGroup.MTECH.getDesc();
			claim = "校长路西法";
			variables.put("command", "agree");	// 流程变量，用于网关流程条件判断
			break;
		default:
			break;
		}
		List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(candidateGroup).list();
		log.info("{}待办任务{}", candidateGroupDesc, taskList.size());
		for (Task task : taskList) {
			// 申领任务（页面上，登陆选择）
			taskService.claim(task.getId(), claim);
			
			// 完成
			taskService.complete(task.getId(), variables);
		}
		Map<String, Object> result = new HashMap<>();
		result.put(idenType, candidateGroupDesc);
		result.put("variables", variables);
		result.put("taskSize", taskList.size());
		return result;
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

		
		result.put("历史流程实例", historicProcessList.size());
		result.put("历史任务", historicTaskList.size());
		return result;
	}
	
	@GetMapping("/get_user")
	public String getUser() {
		return UserUtil.currentUser();
	}
	
}
