		<#if ( reportDict.id )?? && reportDict.id ?length gt 0>
		    /* id */
			and sd.ID = :reportDict.id
		</#if>
		<#if ( reportDict.dictName )?? && reportDict.dictName ?length gt 0>
		    /* 字典名称 */
			and sd.DICT_NAME like :reportDict.dictName
		</#if>
		<#if ( reportDict.dictCode )?? && reportDict.dictCode ?length gt 0>
		    /* 字典编码 */
			and sd.DICT_CODE like :reportDict.dictCode
		</#if>
		<#if ( reportDict.description )?? && reportDict.description ?length gt 0>
		    /* 描述 */
			and sd.DESCRIPTION = :reportDict.description
		</#if>
		<#if ( reportDict.delFlag )?? && reportDict.delFlag ?length gt 0>
		    /* 删除状态 */
			and sd.DEL_FLAG = :reportDict.delFlag
		</#if>
		<#if ( reportDict.createBy )?? && reportDict.createBy ?length gt 0>
		    /* 创建人 */
			and sd.CREATE_BY = :reportDict.createBy
		</#if>
	    <#if ( reportDict.createTime )??>
		    /* 创建时间 */
			and sd.CREATE_TIME = :reportDict.createTime
		</#if>
		<#if ( reportDict.updateBy )?? && reportDict.updateBy ?length gt 0>
		    /* 更新人 */
			and sd.UPDATE_BY = :reportDict.updateBy
		</#if>
	    <#if ( reportDict.updateTime )??>
		    /* 更新时间 */
			and sd.UPDATE_TIME = :reportDict.updateTime
		</#if>
		<#if ( reportDict.type )?? && reportDict.type ?length gt 0>
		    /* 字典类型0为string,1为number */
			and sd.TYPE = :reportDict.type
		</#if>		
    <#if ( reportDict.tenantId )?? && reportDict.tenantId ?length gt 0>
	    /* 租户标识 */
			and sd.TENANT_ID = :reportDict.tenantId
		</#if>
