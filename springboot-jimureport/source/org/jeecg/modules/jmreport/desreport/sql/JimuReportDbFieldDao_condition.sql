		<#if ( jimuReportDbField.id )?? && jimuReportDbField.id ?length gt 0>
		    /* id */
			and jrdf.ID = :jimuReportDbField.id
		</#if>
		<#if ( jimuReportDbField.createBy )?? && jimuReportDbField.createBy ?length gt 0>
		    /* 创建人登录名称 */
			and jrdf.CREATE_BY = :jimuReportDbField.createBy
		</#if>
	    <#if ( jimuReportDbField.createTime )??>
		    /* 创建日期 */
			and jrdf.CREATE_TIME = :jimuReportDbField.createTime
		</#if>
		<#if ( jimuReportDbField.updateBy )?? && jimuReportDbField.updateBy ?length gt 0>
		    /* 更新人登录名称 */
			and jrdf.UPDATE_BY = :jimuReportDbField.updateBy
		</#if>
	    <#if ( jimuReportDbField.updateTime )??>
		    /* 更新日期 */
			and jrdf.UPDATE_TIME = :jimuReportDbField.updateTime
		</#if>
		<#if ( jimuReportDbField.jimuReportDbId )?? && jimuReportDbField.jimuReportDbId ?length gt 0>
		    /* 数据源ID */
			and jrdf.JIMU_REPORT_DB_ID = :jimuReportDbField.jimuReportDbId
		</#if>
		<#if ( jimuReportDbField.fieldName )?? && jimuReportDbField.fieldName ?length gt 0>
		    /* 字段名 */
			and jrdf.FIELD_NAME = :jimuReportDbField.fieldName
		</#if>
		<#if ( jimuReportDbField.fieldText )?? && jimuReportDbField.fieldText ?length gt 0>
		    /* 字段文本 */
			and jrdf.FIELD_TEXT = :jimuReportDbField.fieldText
		</#if>
		<#if ( jimuReportDbField.widgetType )?? && jimuReportDbField.widgetType ?length gt 0>
		    /* 控件类型 */
			and jrdf.WIDGET_TYPE = :jimuReportDbField.widgetType
		</#if>
		<#if ( jimuReportDbField.widgetWidth )?? && jimuReportDbField.widgetWidth ?length gt 0>
		    /* 控件宽度 */
			and jrdf.WIDGET_WIDTH = :jimuReportDbField.widgetWidth
		</#if>
		<#if ( jimuReportDbField.orderNum )?? && jimuReportDbField.orderNum ?length gt 0>
		    /* 排序 */
			and jrdf.ORDER_NUM = :jimuReportDbField.orderNum
		</#if>
		<#if ( jimuReportDbField.searchFlag )?? && jimuReportDbField.searchFlag ?length gt 0>
		    /* 查询标识0否1是 默认0 */
			and jrdf.SEARCH_FLAG = :jimuReportDbField.searchFlag
		</#if>
		<#if ( jimuReportDbField.searchMode )?? && jimuReportDbField.searchMode ?length gt 0>
		    /* 查询模式1简单2范围 */
			and jrdf.SEARCH_MODE = :jimuReportDbField.searchMode
		</#if>
		<#if ( jimuReportDbField.dictCode )?? && jimuReportDbField.dictCode ?length gt 0>
		    /* 字典编码支持从表中取数据 */
			and jrdf.DICT_CODE = :jimuReportDbField.dictCode
		</#if>
		<#if ( jimuReportDbField.searchFormat )?? && jimuReportDbField.searchFormat ?length gt 0>
		    /* 字典编码支持从表中取数据 */
			and jrdf.SEARCH_FORMAT = :jimuReportDbField.searchFormat
		</#if>	
        <#if ( jimuReportDbField.extJson )?? && jimuReportDbField.extJson ?length gt 0>
		    /* 字典编码支持从表中取数据 */
			and jrdf.EXT_JSON = :jimuReportDbField.extJson
		</#if>

