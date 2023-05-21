UPDATE jimu_report
SET 
	   <#if jimuReport.id ?exists>
		   ID = :jimuReport.id,
		</#if>
	   <#if jimuReport.code ?exists>
		   CODE = :jimuReport.code,
		</#if>
	   <#if jimuReport.name ?exists>
		   NAME = :jimuReport.name,
		</#if>
	   <#if jimuReport.note ?exists>
		   NOTE = :jimuReport.note,
		</#if>
	   <#if jimuReport.status ?exists>
		   STATUS = :jimuReport.status,
		</#if>
	   <#if jimuReport.type ?exists>
		   TYPE = :jimuReport.type,
		</#if>
	   <#if jimuReport.jsonStr ?exists>
		   JSON_STR = :jimuReport.jsonStr,
		</#if>
	   <#if jimuReport.apiUrl ?exists>
		   API_URL = :jimuReport.apiUrl,
		</#if>
	   <#if jimuReport.thumb ?exists>
		   THUMB = :jimuReport.thumb,
		</#if>
	   <#if jimuReport.createBy ?exists>
		   CREATE_BY = :jimuReport.createBy,
		</#if>
	    <#if jimuReport.createTime ?exists>
		   CREATE_TIME = :jimuReport.createTime,
		</#if>
	   <#if jimuReport.updateBy ?exists>
		   UPDATE_BY = :jimuReport.updateBy,
		</#if>
	    <#if jimuReport.updateTime ?exists>
		   UPDATE_TIME = :jimuReport.updateTime,
		</#if>
	   <#if jimuReport.delFlag ?exists>
		   DEL_FLAG = :jimuReport.delFlag,
		</#if>
	   <#if jimuReport.apiMethod ?exists>
		   API_METHOD = :jimuReport.apiMethod,
		</#if>
	   <#if jimuReport.apiCode ?exists>
		   API_CODE = :jimuReport.apiCode,
		</#if>
	   <#if jimuReport.template ?exists>
		   TEMPLATE = :jimuReport.template,
		</#if>
	   <#if jimuReport.viewCount ?exists>
		   VIEW_COUNT = :jimuReport.viewCount,
		</#if>
		<#if jimuReport.cssStr ?exists>
		   CSS_STR = :jimuReport.cssStr,
		</#if>
	   <#if jimuReport.jsStr ?exists>
		   JS_STR = :jimuReport.jsStr,
		</#if>	  
    <#if jimuReport.tenantId ?exists>
       TENANT_ID = :jimuReport.tenantId,
		</#if>
WHERE id = :jimuReport.id