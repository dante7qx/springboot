		<#if ( jimuReportLink.id )?? && jimuReportLink.id ?length gt 0>
		    /* 主键id */
			and jrl.ID = :jimuReportLink.id
		</#if>
		<#if ( jimuReportLink.reportId )?? && jimuReportLink.reportId ?length gt 0>
		    /* 积木设计器id */
			and jrl.REPORT_ID = :jimuReportLink.reportId
		</#if>
		<#if ( jimuReportLink.parameter )?? && jimuReportLink.parameter ?length gt 0>
		    /* 参数 */
			and jrl.PARAMETER = :jimuReportLink.parameter
		</#if>
		<#if ( jimuReportLink.ejectType )?? && jimuReportLink.ejectType ?length gt 0>
		    /* 弹出方式（0 当前页面 1 新窗口） */
			and jrl.EJECT_TYPE = :jimuReportLink.ejectType
		</#if>
		<#if ( jimuReportLink.linkName )?? && jimuReportLink.linkName ?length gt 0>
		    /* 链接名称 */
			and jrl.LINK_NAME = :jimuReportLink.linkName
		</#if>
		<#if ( jimuReportLink.apiMethod )?? && jimuReportLink.apiMethod ?length gt 0>
		    /* 请求方法0-get,1-post */
			and jrl.API_METHOD = :jimuReportLink.apiMethod
		</#if>
		<#if ( jimuReportLink.linkType )?? && jimuReportLink.linkType ?length gt 0>
		    /* 链接方式(0 网络报表 1 网络连接 2 图表联动) */
			and jrl.LINK_TYPE = :jimuReportLink.linkType
		</#if>
		<#if ( jimuReportLink.apiUrl )?? && jimuReportLink.apiUrl ?length gt 0>
		    /* 外网api */
			and jrl.API_URL = :jimuReportLink.apiUrl
		</#if>	
        <#if ( jimuReportLink.requirement )?? && jimuReportLink.requirement ?length gt 0>
		    /* 表达式 */
			and jrl.requirement = :jimuReportLink.requirement
		</#if>
