UPDATE jimu_dict_item
SET 
	   <#if dictItem.id ?exists>
		   ID = :dictItem.id,
		</#if>
	   <#if dictItem.dictId ?exists>
		   DICT_ID = :dictItem.dictId,
		</#if>
	   <#if dictItem.itemText ?exists>
		   ITEM_TEXT = :dictItem.itemText,
		</#if>
	   <#if dictItem.itemValue ?exists>
		   ITEM_VALUE = :dictItem.itemValue,
		</#if>
	   <#if dictItem.description ?exists>
		   DESCRIPTION = :dictItem.description,
		</#if>
	   <#if dictItem.sortOrder ?exists>
		   SORT_ORDER = :dictItem.sortOrder,
		</#if>
	   <#if dictItem.status ?exists>
		   STATUS = :dictItem.status,
		</#if>
	   <#if dictItem.createBy ?exists>
		   CREATE_BY = :dictItem.createBy,
		</#if>
	    <#if dictItem.createTime ?exists>
		   CREATE_TIME = :dictItem.createTime,
		</#if>
	   <#if dictItem.updateBy ?exists>
		   UPDATE_BY = :dictItem.updateBy,
		</#if>
	    <#if dictItem.updateTime ?exists>
		   UPDATE_TIME = :dictItem.updateTime,
		</#if>
WHERE id = :dictItem.id