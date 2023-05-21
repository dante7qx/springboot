		<#if ( jimuReport.id )?? && jimuReport.id ?length gt 0>
		    /* 主键 */
			and jr.ID = :jimuReport.id
		</#if>
		<#if ( jimuReport.code )?? && jimuReport.code ?length gt 0>
		    /* 编码 */
			and jr.CODE = :jimuReport.code
		</#if>
		<#if ( jimuReport.name )?? && jimuReport.name ?length gt 0>
		    /* 名称 */
			and jr.NAME = :jimuReport.name
		</#if>
		<#if ( jimuReport.note )?? && jimuReport.note ?length gt 0>
		    /* 说明 */
			and jr.NOTE = :jimuReport.note
		</#if>
		<#if ( jimuReport.status )?? && jimuReport.status ?length gt 0>
		    /* 状态 */
			and jr.STATUS = :jimuReport.status
		</#if>
		<#if ( jimuReport.type )?? && jimuReport.type ?length gt 0>
		    /* 类型 */
			and jr.TYPE = :jimuReport.type
		</#if>
		<#if ( jimuReport.jsonStr )?? && jimuReport.jsonStr ?length gt 0>
		    /* json字符串 */
			and jr.JSON_STR = :jimuReport.jsonStr
		</#if>
		<#if ( jimuReport.apiUrl )?? && jimuReport.apiUrl ?length gt 0>
		    /* 请求地址 */
			and jr.API_URL = :jimuReport.apiUrl
		</#if>
		<#if ( jimuReport.thumb )?? && jimuReport.thumb ?length gt 0>
		    /* 缩略图 */
			and jr.THUMB = :jimuReport.thumb
		</#if>
		<#if ( jimuReport.createBy )?? && jimuReport.createBy ?length gt 0>
		    /* 创建人 */
			and jr.CREATE_BY = :jimuReport.createBy
		</#if>
	    <#if ( jimuReport.createTime )??>
		    /* 创建时间 */
			and jr.CREATE_TIME = :jimuReport.createTime
		</#if>
		<#if ( jimuReport.updateBy )?? && jimuReport.updateBy ?length gt 0>
		    /* 修改人 */
			and jr.UPDATE_BY = :jimuReport.updateBy
		</#if>
	    <#if ( jimuReport.updateTime )??>
		    /* 修改时间 */
			and jr.UPDATE_TIME = :jimuReport.updateTime
		</#if>
		<#if ( jimuReport.delFlag )?? && jimuReport.delFlag ?length gt 0>
		    /* 删除标识0-正常,1-已删除 */
			and jr.DEL_FLAG = :jimuReport.delFlag
		</#if>
		<#if ( jimuReport.apiMethod )?? && jimuReport.apiMethod ?length gt 0>
		    /* 请求方法0-get,1-post */
			and jr.API_METHOD = :jimuReport.apiMethod
		</#if>
		<#if ( jimuReport.apiCode )?? && jimuReport.apiCode ?length gt 0>
		    /* 请求编码 */
			and jr.API_CODE = :jimuReport.apiCode
		</#if>
		<#if ( jimuReport.template )?? && jimuReport.template ?length gt 0>
		    /* 是否是模板 0-是,1-不是 */
			and jr.TEMPLATE = :jimuReport.template
		</#if>
		<#if ( jimuReport.viewCount )?? && jimuReport.viewCount ?length gt 0>
		    /* 浏览次数 */
			and jr.VIEW_COUNT = :jimuReport.viewCount
		</#if>
		<#if ( jimuReport.cssStr )?? && jimuReport.cssStr ?length gt 0>
			and jr.CSS_STR = :jimuReport.cssStr
		</#if>
		<#if ( jimuReport.jsStr )?? && jimuReport.jsStr ?length gt 0>
			and jr.JS_STR = :jimuReport.jsStr
		</#if>		
		<#if ( jimuReport.tenantId )?? && jimuReport.tenantId ?length gt 0>
			/* 租户标识 */
			and jr.TENANT_ID = :jimuReport.tenantId
		</#if>
