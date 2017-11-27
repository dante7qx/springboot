var SchedulerPage = {
	init: function() {
		$(window).resize(function(){
			$('#schedulerGridlist').datagrid({fitColumns:true});
		});
		this.loadSchedulerList();
	},
	loadSchedulerList: function() {
		$('#schedulerGridlist').datagrid({
		    url: '/scheduler/query_job',
		    title: '系统定时任务列表',
		    rownumbers: true,
		    rownumberWidth: 20,
		    singleSelect: true,
		    fitColumns: true,
		    pagination: true,
		    pageSize: 20,
		    pageList: [20, 40, 60],
		    toolbar: '#schedulerGridToolbar',
		    sortName: 'jobId',
		    sortOrder: 'asc',
		    remoteSort: false,
		    height: $(window).height() - 20,
		    frozenColumns: [[
                 {field:'ck',checkbox:true}
            ]],
		    columns:[[
		        {field:'jobId',title:'任务编号',width:100,halign:'center',align:'left',sortable:true,formatter:function(value,row,index) {
		        	return '<a class="bdhref" href="javascript:;" onclick="SchedulerPage.editScheduler('+row['id']+')">'+value+'</a>';
		        }},
		        {field:'jobName',title:'任务名称',width:120,halign:'center'},
		        {field:'jobDesc',title:'任务描述',width:200,halign:'center'},
		        {field:'startJob',title:'启动状态',width:60,halign:'center',align:'center',formatter:function(val) {
		        	return val ? "启动" : "停止";
		        }},
		        {field:'fireTime',title:'触发时间',width:110,halign:'center',align:'left'},
		        {field:'previousFireTime',title:'上次执行时间',width:110,halign:'center',align:'left'},
		        {field:'nextFireTime',title:'下次执行时间',width:110,halign:'center',align:'left'}
		        
		    ]]
		});
	},
	search: function() {
		$('#schedulerGridlist').datagrid('load', {
			'q[jobId]': $('#queryJobId','#schedulerGridToolbar').textbox("getValue")
		});
	},
	reset: function() {
		$('#schedulerQueryForm').form('clear');
	},
	editScheduler: function(id) {
		$('#schedulerWindow').show().window({
			title: (id ? '编辑定时任务' : '新增定时任务'),
			closed: false,
			cache: false,
			href: (id ? 'editscheduler?id='+id : 'editscheduler'),
			extractor: function(data) {
				return data;
			}	
		});
	},
	del: function() {
		var checkRow = $('#schedulerGridlist').datagrid('getChecked');
		if(checkRow.length == 0) {
			$.messager.alert('提示','请至少选择一条记录！');
			return;
		}
		$.messager.confirm('提示', '您确定要删除吗?', function(r){
			if (r){
				$.ajax({
					url: ctx+'/sysmgr/scheduler/delete_by_id',
					type: 'post',
					data: {
						id: checkRow[0]['id'],
					},
					success: function(result) {
						if(result['resultCode'] != COMMON_CONFIG['SUCCESS']) {
							$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
							return;
						}
						$('#schedulerGridlist').datagrid('reload');
					}
				});
			}
		});
	}
};