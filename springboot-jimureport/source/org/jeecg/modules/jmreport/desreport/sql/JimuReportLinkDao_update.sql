UPDATE jimu_report_link
SET 
	   <#if jimuReportLink.id ?exists>
		   ID = :jimuReportLink.id,
		</#if>
	   <#if jimuReportLink.reportId ?exists>
		   REPORT_ID = :jimuReportLink.reportId,
		</#if>
	   <#if jimuReportLink.parameter ?exists>
		   PARAMETER = :jimuReportLink.parameter,
		</#if>
	   <#if jimuReportLink.ejectType ?exists>
		   EJECT_TYPE = :jimuReportLink.ejectType,
		</#if>
	   <#if jimuReportLink.linkName ?exists>
		   LINK_NAME = :jimuReportLink.linkName,
		</#if>
	   <#if jimuReportLink.apiMethod ?exists>
		   API_METHOD = :jimuReportLink.apiMethod,
		</#if>
	   <#if jimuReportLink.linkType ?exists>
		   LINK_TYPE = :jimuReportLink.linkType,
		</#if>
	   <#if jimuReportLink.apiUrl ?exists>
		   API_URL = :jimuReportLink.apiUrl,
		</#if>
		<#if jimuReportLink.linkChartId ?exists>
		   LINK_CHART_ID = :jimuReportLink.linkChartId,
		</#if>	
        <#if jimuReportLink.requirement ?exists>
        requirement = :jimuReportLink.requirement
		</#if>
WHERE id = :jimuReportLink.id