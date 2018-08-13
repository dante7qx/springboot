var hnasys = {};

hnasys.util = {
		isEditForm: function (id, flag, spCompsIds) {
        	if(spCompsIds!=null && spCompsIds.length>0) {
        		$.each(spCompsIds, function(key, val) {
        			hnasys.util.isEditFormCommon(id,flag,val);
        		});
        	} else {
        		hnasys.util.isEditFormCommon(id,flag,null);
        	}
		},
		isEditFormCommon : function(id, flag, spId){
	    	var val = '';
	    	var partEditFlag = false;
	    	if(spId!=null){
	    		val = '#'+spId;
	    		partEditFlag = true;
	    	}
	        if (flag) {   
	        	$('input[type="text"]'+val+',textarea'+val+',input[type="file"]'+val+'', '#' + id).each(function(){
	        		$(this).removeClass('formborder')
	            	if($(this).attr("rdonly")!="true"){
	            		$(this).removeAttr('readonly');
	            	}
	            });
	            $('input[type="radio"]'+val+',input[type="checkbox"]'+val+'', '#' + id).each(function(){
	            	if($(this).attr("dsable")!="true"){
	            		$(this).removeAttr('disabled');
	            	}
	            });
	            if(partEditFlag){
	            	$(val, '#' + id).next('.combo').removeClass('formborder').find('.combo-arrow').show();
	            	$(val, '#' + id).next('.numberbox').removeClass('formborder');
	            }else{
	            	$('.combo', '#' + id).removeClass('formborder').find('.combo-arrow').show();
	            	$('.numberbox', '#' + id).removeClass('formborder');
	            }
	        } else {
	            $('input[type="text"]'+val+',textarea'+val+',input[type="file"]'+val+'', '#' + id).each(function(){
	            	$(this).addClass('formborder');
	            	if($(this).attr("rdonly")){
	            		$(this).attr('readonly', 'readonly');
	            	}else{
	            		if($(this).attr("readonly")){
		            		$(this).attr("rdonly","true");
		            	}else{
		            		$(this).attr('readonly', 'readonly').attr("rdonly","false");
		            	}
	            	}
	            });
	            $('input[type="radio"]'+val+',input[type="checkbox"]'+val+'', '#' + id).each(function(){
	            	if($(this).attr("dsable")){
	            		$(this).attr('disabled', true);
	            	}else{
		            	if($(this).attr("disabled")){
		            		$(this).attr('dsable', "true");
		            	}else{
		            		$(this).attr('disabled', true).attr('dsable', "false");
		            	}
	            	}
	            });
	            if(partEditFlag){//部分不可编辑
	            	$(val, '#' + id).next('.combo').addClass('formborder').find('.combo-arrow').hide();
	            	$(val, '#' + id).next('.numberbox').addClass('formborder');
	            }else{
	            	$('.combo', '#' + id).addClass('formborder').find('.combo-arrow').hide();
	            	$('.numberbox', '#' + id).addClass('formborder');
	            }
	        }
	    },
	    controlFormBtn: function(editable, containerId, callback) {
	    	var containerId = '#'+containerId;
	    	if(editable) {
	    		$('.formEdit,.formDelete',containerId).hide();
	    		$('.formPersist',containerId).show();
	    	} else {
	    		$('.formEdit,.formDelete',containerId).show();
	    		$('.formPersist',containerId).hide();
	    	}
	    	if(callback && typeof callback == "function") {
	    		callback();
	    	}
	    },
	    tableMerges : function (id, fieldArr, befField) {
			for ( var i = 0; i < fieldArr.length; i++) {
				if(befField) {
					hnasys.util.tableMergeBef(id, fieldArr[i], befField);
				} else {
					hnasys.util.tableMerge(id, fieldArr[i]);
				}
			}
		},
		tableMerge : function (id, field) {
			var tabId = '#' + id;
			var rows = $(tabId).datagrid('getRows');
			var rowStr = "";
			var merges = [];
			var j = 0;
			var x = 0;
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if (i == 0) {
					rowStr = row[field];
					var merObj = {};
					merObj.index = 0;
					merges[j] = merObj;
				}
				if (rowStr != row[field]) {
					rowStr = row[field];
					merges[j].rowspan = x;
					x = 1;
					j++;
					var merObj = {};
					merObj.index = i;
					merges[j] = merObj;
				} else {
					x++;
				}
				if (i == (rows.length - 1)) {
					merges[j].rowspan = x;
				}
			}
			for ( var i = 0; i < merges.length; i++) {
				$(tabId).datagrid('mergeCells', {
					index : merges[i].index,
					field : field,
					rowspan : merges[i].rowspan
				});
			}
		},
		tableMergeBef : function(id, field, befField) {
			var tabId = '#' + id;
			var rows = $(tabId).datagrid('getRows');
			var rowStr = "";
			var merges = [];
			var j = 0;
			var x = 0;
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				var realVal = row[field] + '_' + (row[befField]? row[befField] : hnasys.util.guidGenerator());
				if (i == 0) {
					rowStr = realVal;
					var merObj = {};
					merObj.index = 0;
					merges[j] = merObj;
				}
				if (rowStr != realVal) {
					rowStr = realVal;
					merges[j].rowspan = x;
					x = 1;
					j++;
					var merObj = {};
					merObj.index = i;
					merges[j] = merObj;
				} else{
					x++;
				}
				if (i == (rows.length - 1)) {
					merges[j].rowspan = x;
				}
			}
			for ( var i = 0; i < merges.length; i++) {
				$(tabId).datagrid('mergeCells', {
					index : merges[i].index,
					field : field,
					rowspan : merges[i].rowspan
				});
			}
		},
		deleteGridRow : function(index, gridId, fieldArr){
			var nRows = new Array();
			var rows = $('#'+gridId).datagrid('getRows');
			if(rows.length == 1) {
				$('#'+gridId).datagrid('deleteRow', 0);
				return;
			}
			var pos = 0;
			for(var i=0; i<rows.length; i++) {
				if(i != index) {
					var row = {};
					for(var j=0; j<fieldArr.length; j++) {
						row[fieldArr[j]] = rows[i][fieldArr[j]];
					}
					nRows[pos] = row;
					pos++;
				}
			}
			for(var i=(rows.length-1); i>=0; i--) {
				$('#'+gridId).datagrid('deleteRow', i);
			}
			for(var i=0; i<nRows.length; i++) {
				$('#'+gridId).datagrid('appendRow',nRows[i]);
			}
		},
		deleteGridRow : function(index, gridId, fieldArr, editorFieldArr){
			var nRows = new Array();
			var nEditors = new Array();
			var rows = $('#'+gridId).datagrid('getRows');
			if(rows.length == 1) {
				$('#'+gridId).datagrid('deleteRow', 0);
				return;
			}
			var pos = 0;
			for(var i=0; i<rows.length; i++) {
				if(i != index) {
					var row = {};
					var editor = {};
					for(var j=0; j<fieldArr.length; j++) {
						row[fieldArr[j]] = rows[i][fieldArr[j]];
					}
					if(editorFieldArr.length > 0) {
						for(var j=0; j<editorFieldArr.length; j++) {
							var ed = $('#'+gridId).datagrid('getEditor', {index:i,field:editorFieldArr[j]});
							editor[editorFieldArr[j]] = $(ed.target).val();
						}
						
					}
					nRows[pos] = row;
					nEditors[pos] = editor; 
					pos++;
				}
			}
			for(var i=(rows.length-1); i>=0; i--) {
				$('#'+gridId).datagrid('deleteRow', i);
			}
			for(var i=0; i<nRows.length; i++) {
				$('#'+gridId).datagrid('appendRow',nRows[i]);
			}
			return nEditors;
		}
};