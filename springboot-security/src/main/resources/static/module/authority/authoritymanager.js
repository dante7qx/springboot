var AuthorityPage = {
	PUBLIC: 'public',
	loadTree: function() {
		$('#authorityTree').tree({
		    url: 'authority/query_tree',
		    dnd: true,
		    onSelect: function(node){
		    	AuthorityPage.loadData(node, false);
			},
			onDrop: function(target,source,point) {
				AuthorityPage.dragNode($('#authorityTree').tree('getNode',target), source, point);
			},
			onContextMenu: function(e,node){
                e.preventDefault();
                $(this).tree('select',node.target);
                $('#authorityTreeMenu').menu('show',{
                    left: e.pageX,
                    top: e.pageY
                });
            }
		});
	},
	loadData: function(node, editable) {
		if(node['id'] < 0) {
			$('#authorityContainer').css('display', 'none');
		} else {
			$('#authorityContainer').show();
			hnasys.util.controlFormBtn(editable, 'authorityFormBtnContainer');
			$('#authorityForm').form('clear').form('load', node['attributes']);
			if(node['attributes']['parentAuthority']) {
				$('#pid','#authorityForm').val(node['attributes']['parentAuthority']['id']);
			}
			hnasys.util.isEditForm('authorityForm', editable);
		}
	},
	addNode: function() {
		var curNode = $('#authorityTree').tree('getSelected');
		if(!curNode) {
			$.messager.alert('提示','请先选择一个父节点！');
			return;
		}
		this.edit();
		$('#authorityContainer').show();
		$('#authorityForm').form('clear');
		$('#code','#authorityForm').val(AuthorityPage.PUBLIC);
		$('#pid','#authorityForm').val(curNode['id'] > 0 ? curNode['id'] : '');
		
	},
	delNode: function() {
		var curNode = $('#authorityTree').tree('getSelected');
		if(!curNode) {
			$.messager.alert('提示','请先选择一个父节点！');
			return;
		} else if(curNode['id'] < 0) {
			$.messager.alert('提示','不允许删除所有权限！');
			return;
		}
		$.messager.confirm('提示', '本操作会删除当前节点及其子节点，您确定要删除吗?', function(r){
			if (r){
				$.ajax({
					url: 'authority/delete_by_id',
					type: 'post',
					data: {
						id: curNode['id'],
						_method: 'delete'
					},
					success: function(result) {
						if(!result['flag']) {
							$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
							return;
						}
						$('#authorityTree').tree('pop', curNode.target);
						$('#authorityTree').tree('select', $('#authorityTree').tree('getRoot').target);
					}
				});
			}
		});
	},
	expandAll: function() {
		$('#authorityTree').tree('expandAll');
	},
	collapseAll: function() {
		var node = $('#authorityTree').tree('find', -1);
		$('#authorityTree').tree('collapse', node.target).tree('select', node.target);
	},
	appendNode: function(data) {
		var id = $('#id','#authorityForm').val();
		var node = [];
		var newNode = {};
		newNode['id'] = data['id'];
		newNode['text'] = data['name'];
		newNode['state'] = 'open';
		var newNodeAttr = {};
		newNodeAttr['id'] = data['id'];
		newNodeAttr['name'] = data['name'];
		newNodeAttr['code'] = data['code'];
		newNodeAttr['authorityDesc'] = data['authorityDesc'];
		newNodeAttr['pid'] = data['pid'];
		newNode['attributes'] = newNodeAttr;
		node.push(newNode);
		var selected = $('#authorityTree').tree('getSelected');
		if(id) {
			$('#authorityTree').tree('update', {
				target: selected.target,
				text: newNode['text'],
				attributes: newNode['attributes']
			});
		} else {
			$('#authorityTree').tree('append', {
				parent: selected.target,
				data: node
			});
			$('#authorityTree').tree('select', $('#authorityTree').tree('find', data['id']).target);
		}
	},
	submit: function() {
		$('#authorityForm').form('submit', {
			iframe: false,
		    url: 'authority/update',
		    success:function(result){
		    	var result = eval('(' + result + ')');
		        if(result['flag']) {
		        	AuthorityPage.appendNode(result['data']);
		        	$('#authorityForm').form('load', result['data']);
		        	hnasys.util.controlFormBtn(false, 'authorityFormBtnContainer');
		        	hnasys.util.isEditForm('authorityForm', false);
		        } else {
		        	$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
		        }
		    }
		});
	},
	reset: function() {
		var curNode = $('#authorityTree').tree('getSelected');
		var id = $('#id','#authorityForm').val();
		if(id) {
			AuthorityPage.loadData(curNode, true);
		} else {
			$('#authorityForm').form('clear');
			$('#pid','#authorityForm').val(curNode['id']);
		}
	},
	edit: function() {
		hnasys.util.controlFormBtn(true, 'authorityFormBtnContainer');
		hnasys.util.isEditForm('authorityForm', true);
	},
	dragNode: function(targetNode, sourceNode, point) {
		var targetId = targetNode['id'];
		var targetPid = '';
		var sourceId = sourceNode['id'];
		var targetParentNode = $('#authorityTree').tree('getParent',targetNode.target);
		if(targetParentNode && targetParentNode['id'] > 0) {
			targetPid = targetParentNode['id'];
		}
		var targetShowOrder = targetId > 0 ? targetNode['attributes']['showOrder'] : 1;
		$.ajax({
			url: 'authority/update_when_drag',
			type: 'post',
			data: {
				point: point,
				targetId: targetId,
				targetPid: targetPid,
				targetShowOrder: targetShowOrder,
				sourceId: sourceId
			},
			success: function(result) {
				if(!result['flag']) {
					$.messager.alert('错误','系统错误，请联系系统管理员', 'error');
					return;
				}
			}
		});
	}
};