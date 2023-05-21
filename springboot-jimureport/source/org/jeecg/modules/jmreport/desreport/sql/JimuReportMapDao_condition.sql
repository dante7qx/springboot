		<#if ( jimuReportMap.id )?? && jimuReportMap.id ?length gt 0>
		    /* id */
			and jrm.ID = :jimuReportMap.id
		</#if>
		<#if ( jimuReportMap.createBy )?? && jimuReportMap.createBy ?length gt 0>
		    /* 创建人 */
			and jrm.CREATE_BY = :jimuReportMap.createBy
		</#if>
	    <#if ( jimuReportMap.createTime )??>
		    /* 创建日期 */
			and jrm.CREATE_TIME = :jimuReportMap.createTime
		</#if>
		<#if ( jimuReportMap.updateBy )?? && jimuReportMap.updateBy ?length gt 0>
		    /* 更新人 */
			and jrm.UPDATE_BY = :jimuReportMap.updateBy
		</#if>
	    <#if ( jimuReportMap.updateTime )??>
		    /* 更新日期 */
			and jrm.UPDATE_TIME = :jimuReportMap.updateTime
		</#if>
		<#if ( jimuReportMap.sysOrgCode )?? && jimuReportMap.sysOrgCode ?length gt 0>
		    /* 所属部门 */
			and jrm.SYS_ORG_CODE = :jimuReportMap.sysOrgCode
		</#if>
		<#if ( jimuReportMap.name )?? && jimuReportMap.name ?length gt 0>
		    /* 地图名称 */
			and jrm.NAME = :jimuReportMap.name
		</#if>
		<#if ( jimuReportMap.data )?? && jimuReportMap.data ?length gt 0>
		    /* 地图数据 */
			and jrm.DATA = :jimuReportMap.data
		</#if>
		<#if ( jimuReportMap.delFlag )?? && jimuReportMap.delFlag ?length gt 0>
		    /* 0表示未删除,1表示删除 */
			and jrm.DEL_FLAG = :jimuReportMap.delFlag
		</#if>
		<#if ( jimuReportMap.label )?? && jimuReportMap.label ?length gt 0>
		    /* 地图名称 */
			and jrm.LABEL = :jimuReportMap.label
		</#if>
