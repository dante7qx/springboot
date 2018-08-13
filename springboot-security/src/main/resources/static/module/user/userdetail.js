var UserDetailPage = {
		SUBMIT_FLAG: false,
		paramUserId: '',
		initPage: function(paramUserId) {
			this.paramUserId = paramUserId;
			this.loadData();
		},
		initRoleCombotree: function(roles) {
			$('#roles','#userDetailForm').combotree({
			    url: 'role/query_role_tree',
			    required: false,
			    editable: false,
			    multiple: true
			});
			if(roles) {
				$('#roles','#userDetailForm').combotree('setValues', roles);
			}
		},
		loadData: function() {
			if(this.paramUserId) {
				hnasys.util.controlFormBtn(false, 'userFormBtnContainer');
				$('#account','#userDetailForm').validatebox({
					required: false,
					validType: ''
				});
				$('#passwordTr','#userDetailForm').hide();
				$('#password','#userDetailForm').validatebox({required: false});
				this.loadDataById(false);
			} else {
				hnasys.util.controlFormBtn(true, 'userFormBtnContainer');
				this.initRoleCombotree();
				$('#userDetailForm').form('clear');
			}
		},
		loadDataById: function(editable) {
			$.ajax({
				url: 'user/query_by_id',
				type: 'post',
				data: {
					id: UserDetailPage.paramUserId
				},
				success: function(result) {
					if(!result['flag']) {
						$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
						return;
					}
					$('#userDetailForm').form('clear').form('load', result['data']);
					UserDetailPage.initRoleCombotree(result['data']['roleIds']);
					hnasys.util.isEditForm('userDetailForm', editable);
					hnasys.util.isEditFormCommon('userDetailForm', false, 'account');
				}
			});
		},
		submit: function() {
			$('#userDetailForm').form('submit', {
				iframe: false,
			    url: 'user/update',
			    success:function(result){
			    	var result = eval('(' + result + ')');
			        if(result['flag']) {
			        	$('#userGridlist').datagrid('reload');
			        	$('#userWindow').window('close');
			        } else {
			        	$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
			        }
			    }
			});
		},
		reset: function() {
			if(this.paramUserId) {
				this.loadDataById(true);
			} else {
				$('#userDetailForm').form('clear');
			}
		},
		del: function() {
			$.messager.confirm('提示', '您确定要删除吗?', function(r){
				if (r){
					$.ajax({
						url: 'user/delete_by_id',
						type: 'post',
						data: {
							id: $('#id','#userDetailForm').val(),
							_method: 'delete'
						},
						success: function(result) {
							if(!result['flag']) {
								$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
								return;
							}
							$('#userWindow').window('close');
							$('#userGridlist').datagrid('reload');
						}
					});
				}
			});
		},
		edit: function() {
			hnasys.util.controlFormBtn(true, 'userFormBtnContainer');
			hnasys.util.isEditForm('userDetailForm', true);
			hnasys.util.isEditFormCommon('userDetailForm', false, 'account');
		}
};

$.extend($.fn.validatebox.defaults.rules, {
	uniqueUserAccount: {
		validator: function(value, param){
			if(!value) return true;
			var result = JSON.parse($.ajax({
				url: 'user/query_by_account',
				type: 'post',
				async: false,
				cache: false,
				data: {account: value}
			}).responseText);
			if(!result['flag']) {
				return false;
				$.fn.validatebox.defaults.rules.message = '系统错误，请稍后重试';
			}
			if(result['data']) {
				return false;
			}
			return true;
        },
        message: '该账号已存在，请重新输入'
	}
});