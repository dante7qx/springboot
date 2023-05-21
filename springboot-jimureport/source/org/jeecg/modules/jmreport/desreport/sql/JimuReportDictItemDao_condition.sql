		<#if ( dictItem.id )?? && dictItem.id ?length gt 0>
		    /* id */
			and sdi.ID = :dictItem.id
		</#if>
		<#if ( dictItem.dictId )?? && dictItem.dictId ?length gt 0>
		    /* 字典id */
			and sdi.DICT_ID = :dictItem.dictId
		</#if>
		<#if ( dictItem.itemText )?? && dictItem.itemText ?length gt 0>
		    /* 字典项文本 */
			and sdi.ITEM_TEXT = :dictItem.itemText
		</#if>
		<#if ( dictItem.itemValue )?? && dictItem.itemValue ?length gt 0>
		    /* 字典项值 */
			and sdi.ITEM_VALUE = :dictItem.itemValue
		</#if>
		<#if ( dictItem.description )?? && dictItem.description ?length gt 0>
		    /* 描述 */
			and sdi.DESCRIPTION = :dictItem.description
		</#if>
		<#if ( dictItem.sortOrder )?? && dictItem.sortOrder ?length gt 0>
		    /* 排序 */
			and sdi.SORT_ORDER = :dictItem.sortOrder
		</#if>
		<#if ( dictItem.status )?? && dictItem.status ?length gt 0>
		    /* 状态（1启用 0不启用） */
			and sdi.STATUS = :dictItem.status
		</#if>
		<#if ( dictItem.createBy )?? && dictItem.createBy ?length gt 0>
		    /* createBy */
			and sdi.CREATE_BY = :dictItem.createBy
		</#if>
	    <#if ( dictItem.createTime )??>
		    /* createTime */
			and sdi.CREATE_TIME = :dictItem.createTime
		</#if>
		<#if ( dictItem.updateBy )?? && dictItem.updateBy ?length gt 0>
		    /* updateBy */
			and sdi.UPDATE_BY = :dictItem.updateBy
		</#if>
	    <#if ( dictItem.updateTime )??>
		    /* updateTime */
			and sdi.UPDATE_TIME = :dictItem.updateTime
		</#if>
