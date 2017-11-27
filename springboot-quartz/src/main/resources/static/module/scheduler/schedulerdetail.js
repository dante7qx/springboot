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
			    }
			});
		},
		loadData: function() {
			if(this.paramSchedulerId) {
				this.loadDataById(false);
			} else {
				$('#schedulerDetailForm').form('clear');
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
			    	var result = eval('(' + result + ')');
			    	if(result['id'] > 0) {
			        	$('#schedulerGridlist').datagrid('reload');
			        	$('#schedulerWindow').window('close');
			        } else {
			        	$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
			        }
			    }
			});
		},
		reset: function() {
			if(this.paramSchedulerId) {
				this.initPageWidget();
				this.loadDataById(true);
			} else {
				$('#schedulerDetailForm').form('clear');
				var nodes = $('#authorityTree').tree('getChecked');
				if(nodes) {
					$.each(nodes, function(i, node) {
						$('#authorityTree').tree('uncheck', node.target);
					}); 
				}
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
							if(result['resultCode'] != COMMON_CONFIG['SUCCESS']) {
								$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
								return;
							}
							$('#schedulerWindow').window('close');
							$('#schedulerGridlist').datagrid('reload');
						}
					});
				}
			});
		},
		edit: function() {
			spirit.util.controlFormBtn(true, 'schedulerFormBtnContainer');
			spirit.util.isEditForm('schedulerDetailForm', true);
		},
		getSelectedAuthority: function() {
			var checkNodeIds = [];
			var nodes = $('#authorityTree').tree('getChecked');
			if(nodes) {
				$.each(nodes, function(i, node) {
					checkNodeIds.push(node['id']);
				}); 
			}
			return checkNodeIds;
		}
};