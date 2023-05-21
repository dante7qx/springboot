		<#if ( jimuReportDbParam.id )?? && jimuReportDbParam.id ?length gt 0>
		    /* id */
			and jrdp.ID = :jimuReportDbParam.id
		</#if>
		<#if ( jimuReportDbParam.jimuReportHeadId )?? && jimuReportDbParam.jimuReportHeadId ?length gt 0>
		    /* 动态报表ID */
			and jrdp.JIMU_REPORT_HEAD_ID = :jimuReportDbParam.jimuReportHeadId
		</#if>
		<#if ( jimuReportDbParam.paramName )?? && jimuReportDbParam.paramName ?length gt 0>
		    /* 参数字段 */
			and jrdp.PARAM_NAME = :jimuReportDbParam.paramName
		</#if>
		<#if ( jimuReportDbParam.paramTxt )?? && jimuReportDbParam.paramTxt ?length gt 0>
		    /* 参数文本 */
			and jrdp.PARAM_TXT = :jimuReportDbParam.paramTxt
		</#if>
		<#if ( jimuReportDbParam.paramValue )?? && jimuReportDbParam.paramValue ?length gt 0>
		    /* 参数默认值 */
			and jrdp.PARAM_VALUE = :jimuReportDbParam.paramValue
		</#if>
		<#if ( jimuReportDbParam.orderNum )?? && jimuReportDbParam.orderNum ?length gt 0>
		    /* 排序 */
			and jrdp.ORDER_NUM = :jimuReportDbParam.orderNum
		</#if>
		<#if ( jimuReportDbParam.createBy )?? && jimuReportDbParam.createBy ?length gt 0>
		    /* 创建人登录名称 */
			and jrdp.CREATE_BY = :jimuReportDbParam.createBy
		</#if>
	    <#if ( jimuReportDbParam.createTime )??>
		    /* 创建日期 */
			and jrdp.CREATE_TIME = :jimuReportDbParam.createTime
		</#if>
		<#if ( jimuReportDbParam.updateBy )?? && jimuReportDbParam.updateBy ?length gt 0>
		    /* 更新人登录名称 */
			and jrdp.UPDATE_BY = :jimuReportDbParam.updateBy
		</#if>
	    <#if ( jimuReportDbParam.updateTime )??>
		    /* 更新日期 */
			and jrdp.UPDATE_TIME = :jimuReportDbParam.updateTime
		</#if>
		 <#if ( jimuReportDbParam.searchFlag )??>
			and jrdp.SEARCH_FLAG = :jimuReportDbParam.searchFlag
		</#if>

		<#if ( jimuReportDbParam.searchMode )??>
			and jrdp.SEARCH_MODE = :jimuReportDbParam.searchMode
		</#if>

		<#if ( jimuReportDbParam.widgetType )?? && jimuReportDbParam.widgetType ?length gt 0>
			and jrdp.WIDGET_TYPE = :jimuReportDbParam.widgetType
		</#if>

		<#if ( jimuReportDbParam.dictCode )?? && jimuReportDbParam.dictCode ?length gt 0>
			and jrdp.DICT_CODE = :jimuReportDbParam.dictCode
		</#if>

		<#if ( jimuReportDbParam.searchFormat )?? && jimuReportDbParam.searchFormat ?length gt 0>
			and jrdp.SEARCH_FORMAT = :jimuReportDbParam.searchFormat
		</#if>

        <#if ( jimuReportDbParam.extJson )?? && jimuReportDbParam.extJson ?length gt 0>
			and jrdp.EXT_JSON = :jimuReportDbParam.extJson
		</#if>