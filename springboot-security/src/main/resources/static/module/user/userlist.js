var UserPage = {
		getUserList: function() {
			$.ajax({
				url: 'user/query',
				type: 'post',
				dateType: 'json',
				contentType: 'application/json',
				data: JSON.stringify({name:'dante', age: 20}),
				success: function(result) {
					if(result && result.length > 0) {
						$.each(result, function(i, user) {
							$('#userInfo').append('<span>')
							.append(user.name + ' -> ' + user.email)
							.append('<br/>')
							.append('</span>');
						});
					} else {
						alert('出错了!');
					}
				}
			});
		},
		loadUserList: function() {
			$('#userGridlist').datagrid({
			    url:'user/query_user_list',
			    title: '用户列表',
			    rownumbers: true,
			    rownumberWidth: 20,
			    singleSelect: true,
			    fitColumns: true,
			    pagination: true,
			    pageSize: COMMON_CONFIG['PAGESIZE'],
			    pageList: COMMON_CONFIG['PAGELIST'],
			    toolbar: '#userGridToolbar',
			    sortName: 'name',
			    sortOrder: 'desc',
			    remoteSort: false,
			    height: $(window).height() - 20,
			    columns:[[
			        {field:'account',title:'帐号',width:50,halign:'center',align:'center',formatter:function(value,row,index) {
			        	return '<a class="bdhref" href="javascript:;" onclick="UserPage.editUser('+row['id']+')">'+value+'</a>';
			        }},
			        {field:'name',title:'姓名',width:100,halign:'center',sortable:true},
			        {field:'email',title:'邮箱',width:100,halign:'center',align:'left'}
			    ]]
			});
		},
		search: function() {
			$('#userGridlist').datagrid('load', {
				'q[name]': $('#queryName','#userGridToolbar').val(),
				'q[email]': $('#queryEmail','#userGridToolbar').val(),
				'q[queryDate]': $('#queryDate','#userGridToolbar').combobox('getValue')
			});
		},
		reset: function() {
			$('#userQueryForm').form('clear');
		},
		editUser: function(id) {
			$('#userWindow').show().window({
				title: (id ? '编辑用户' : '新增用户'),
				closed: false,
				cache: false,
				href: (id ? 'edituser?id='+id : 'edituser'),
				extractor: function(data) {
					return data;
				}	
			});
		},
		modifyPassword: function() {
			
		}
};