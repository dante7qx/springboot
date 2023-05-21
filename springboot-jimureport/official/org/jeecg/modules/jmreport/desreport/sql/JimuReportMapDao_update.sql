UPDATE jimu_report_map
SET 
	   <#if jimuReportMap.id ?exists>
		   ID = :jimuReportMap.id,
		</#if>
	   <#if jimuReportMap.createBy ?exists>
		   CREATE_BY = :jimuReportMap.createBy,
		</#if>
	    <#if jimuReportMap.createTime ?exists>
		   CREATE_TIME = :jimuReportMap.createTime,
		</#if>
	   <#if jimuReportMap.updateBy ?exists>
		   UPDATE_BY = :jimuReportMap.updateBy,
		</#if>
	    <#if jimuReportMap.updateTime ?exists>
		   UPDATE_TIME = :jimuReportMap.updateTime,
		</#if>
	   <#if jimuReportMap.sysOrgCode ?exists>
		   SYS_ORG_CODE = :jimuReportMap.sysOrgCode,
		</#if>
	   <#if jimuReportMap.name ?exists>
		   NAME = :jimuReportMap.name,
		</#if>
	   <#if jimuReportMap.data ?exists>
		   DATA = :jimuReportMap.data,
		</#if>
	   <#if jimuReportMap.delFlag ?exists>
		   DEL_FLAG = :jimuReportMap.delFlag,
		</#if>
	   <#if jimuReportMap.label ?exists>
		   LABEL = :jimuReportMap.label,
		</#if>
WHERE id = :jimuReportMap.id