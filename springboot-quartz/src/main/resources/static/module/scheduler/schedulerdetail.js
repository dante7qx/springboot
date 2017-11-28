var SchedulerDetailPage = {
		SUBMIT_FLAG: false,
		paramSchedulerId: '',
		initPage: function(paramSchedulerId) {
			this.paramSchedulerId = paramSchedulerId;
			this.initJobIdCombo();
			this.loadData();
		},
		initJobIdCombo: function() {
			$('#jobId','#schedulerDetailForm').combobox({
				url:'/scheduler/query_combo',
			    valueField: 'jobId',
			    textField: 'jobName',
			    onSelect: function(record) {
			    	$('#jobName','#schedulerDetailForm').textbox('setValue', record['jobName']);
			    	$('#jobClass','#schedulerDetailForm').val(record['jobClass']);
			    	$('#tmpJobId','#schedulerDetailForm').val(record['jobId']);
			    }
			});
		},
		loadData: function() {
			if(this.paramSchedulerId) {
				this.loadDataById(false);
			} 
		},
		loadDataById: function(editable) {
			$.ajax({
				url: '/scheduler/query_by_id',
				type: 'post',
				data: {
					id: SchedulerDetailPage.paramSchedulerId
				},
				success: function(result) {
					$('#schedulerDetailForm').form('clear').form('load', result);
					SchedulerDetailPage.loadUpdateInfo(result);
				}
			});
		},
		loadUpdateInfo: function(data) {
			var updateDate = data['updateDate'] ? data['updateDate'] : '';
			$('#schedulerUpdateInfo').text('更新时间：' + updateDate);
		},
		submit: function() {
			if(SchedulerDetailPage.SUBMIT_FLAG) {
				return;
			}
			if($('#schedulerDetailForm').form('validate')) {
				SchedulerDetailPage.SUBMIT_FLAG = true;
			}
			$('#schedulerDetailForm').form('submit', {
				iframe: false,
			    url: '/scheduler/update_job',
			    success:function(result){
			    	SchedulerDetailPage.SUBMIT_FLAG = false;
		        	$('#schedulerGridlist').datagrid('reload');
		        	$('#schedulerWindow').window('close');
			    }
			});
		},
		reset: function() {
			if(this.paramSchedulerId) {
				this.initPageWidget();
				this.loadDataById(true);
			} else {
				$('#schedulerDetailForm').form('clear');
			}
			SchedulerDetailPage.SUBMIT_FLAG = false;
		},
		del: function() {
			$.messager.confirm('提示', '您确定要删除吗?', function(r){
				if (r){
					$.ajax({
						url: ctx+'/sysmgr/scheduler/delete_by_id',
						type: 'post',
						data: {
							id: $('#id','#schedulerDetailForm').val()
						},
						success: function(result) {
							$('#schedulerWindow').window('close');
							$('#schedulerGridlist').datagrid('reload');
						}
					});
				}
			});
		}
};

$.extend($.fn.validatebox.defaults.rules, {
	uniqueJobId: {
		validator: function(value, param){
			if(!value) return true;
			var id = $('#id','#schedulerDetailForm').val();
			var jobId = $('#tmpJobId','#schedulerDetailForm').val();
			var result = JSON.parse($.ajax({
				url: '/scheduler/check_job_exist',
				type: 'post',
				async: false,
				cache: false,
				data: {
					id: id,
					jobId: jobId
				}
			}).responseText);
			if(result) {
				return false;
			}
			return true;
        },
        message: '该任务已存在，请重新输入！'
	}
});