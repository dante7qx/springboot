var RolePage = {
		loadRoleList: function() {
			$('#roleGridlist').datagrid({
			    url:'role/query_role_list',
			    title: '角色列表',
			    rownumbers: true,
			    rownumberWidth: 20,
			    singleSelect: true,
			    fitColumns: true,
			    pagination: true,
			    pageSize: COMMON_CONFIG['PAGESIZE'],
			    pageList: COMMON_CONFIG['PAGELIST'],
			    toolbar: '#roleGridToolbar',
			    sortName: 'name',
			    sortOrder: 'desc',
			    remoteSort: false,
			    height: $(window).height() - 20,
			    frozenColumns: [[
                     {field:'ck',checkbox:true}
                ]],
			    columns:[[
			        {field:'name',title:'角色名称',width:100,halign:'center',align:'left',sortable:true,formatter:function(value,row,index) {
			        	return '<a class="bdhref" href="javascript:;" onclick="RolePage.editRole('+row['id']+')">'+value+'</a>';
			        }},
			        {field:'roleDesc',title:'角色描述',width:280,halign:'center'},
			        {field:'id',title:'关联用户',width:60,halign:'center',align:'center',formatter:function(value) {
			        	return '<a class="bdhref" href="javascript:;" onclick="RolePage.loadRelUser('+value+')">查看</a>';
			        }}
			        
			    ]]
			});
		},
		search: function() {
			$('#roleGridlist').datagrid('load', {
				'q[name]': $('#queryName','#roleGridToolbar').val()
			});
		},
		reset: function() {
			$('#roleQueryForm').form('clear');
		},
		editRole: function(id) {
			$('#roleWindow').show().window({
				title: (id ? '编辑角色' : '新增角色'),
				closed: false,
				cache: false,
				href: (id ? 'editrole?id='+id : 'editrole'),
				extractor: function(data) {
					return data;
				}	
			});
		},
		del: function() {
			var checkRows = $('#roleGridlist').datagrid('getChecked');
			if(checkRows.length == 0) {
				$.messager.alert('提示','请至少选择一条记录！');
				return;
			}
			var delIdArr = [];
			$.each(checkRows, function(i, row) {
				delIdArr.push(row['id']);
			});
			console.log(delIdArr);
			$.messager.confirm('提示', '您确定要删除吗?', function(r){
				if (r){
					$.ajax({
						url: 'role/delete_by_ids',
						type: 'post',
						data: {
							ids: delIdArr,
							_method: 'delete'
						},
						success: function(result) {
							if(!result['flag']) {
								$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
								return;
							}
							$('#roleGridlist').datagrid('reload');
						}
					});
				}
			});
		},
		loadRelUser: function(roleId) {
			$('#roleUserWindow').show().window('open');
			$('#roleUserGridlist').datagrid({
			    url:'role/query_role_user',
			    title: '用户列表',
			    rownumbers: true,
			    rownumberWidth: 20,
			    singleSelect: true,
			    fitColumns: true,
			    sortName: 'name',
			    sortOrder: 'desc',
			    remoteSort: false,
			    height: 493,
			    queryParams:{roleId: roleId},
			    columns:[[
			        {field:'account',title:'帐号',width:100,halign:'center',align:'left',sortable:true},
			        {field:'name',title:'姓名',width:100,halign:'center',align:'left'},
			        {field:'email',title:'邮箱',width:180,halign:'center',align:'left'}
			        
			    ]]
			});
		}
};