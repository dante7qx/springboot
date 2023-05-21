SELECT COUNT(*) FROM jimu_dict_item
WHERE
item_value = :dictItem.itemValue
and dict_id = :dictItem.dictId    
<#if ( dictItem.id )?? && dictItem.id ?length gt 0>
and id != :dictItem.id
</#if>