UPDATE jimu_report_db_param
SET 
	   <#if jimuReportDbParam.id ?exists>
		   ID = :jimuReportDbParam.id,
		</#if>
	   <#if jimuReportDbParam.jimuReportHeadId ?exists>
		   JIMU_REPORT_HEAD_ID = :jimuReportDbParam.jimuReportHeadId,
		</#if>
	   <#if jimuReportDbParam.paramName ?exists>
		   PARAM_NAME = :jimuReportDbParam.paramName,
		</#if>
	   <#if jimuReportDbParam.paramTxt ?exists>
		   PARAM_TXT = :jimuReportDbParam.paramTxt,
		</#if>
	   <#if jimuReportDbParam.paramValue ?exists>
		   PARAM_VALUE = :jimuReportDbParam.paramValue,
		</#if>
	   <#if jimuReportDbParam.orderNum ?exists>
		   ORDER_NUM = :jimuReportDbParam.orderNum,
		</#if>
	   <#if jimuReportDbParam.createBy ?exists>
		   CREATE_BY = :jimuReportDbParam.createBy,
		</#if>
	    <#if jimuReportDbParam.createTime ?exists>
		   CREATE_TIME = :jimuReportDbParam.createTime,
		</#if>
	   <#if jimuReportDbParam.updateBy ?exists>
		   UPDATE_BY = :jimuReportDbParam.updateBy,
		</#if>
	    <#if jimuReportDbParam.updateTime ?exists>
		   UPDATE_TIME = :jimuReportDbParam.updateTime,
		</#if>
		<#if jimuReportDbParam.searchFlag ?exists>
		   SEARCH_FLAG = :jimuReportDbParam.searchFlag,
		</#if>

		<#if jimuReportDbParam.searchMode ?exists>
		   SEARCH_MODE = :jimuReportDbParam.searchMode,
		</#if>
	    <#if jimuReportDbParam.widgetType ?exists>
		   WIDGET_TYPE = :jimuReportDbParam.widgetType,
		</#if>
		<#if jimuReportDbParam.dictCode ?exists>
		   DICT_CODE = :jimuReportDbParam.dictCode,
		</#if>
        <#if jimuReportDbParam.searchFormat ?exists>
		   SEARCH_FORMAT = :jimuReportDbParam.searchFormat,
		</#if>   
        <#if jimuReportDbParam.extJson ?exists>
           EXT_JSON = :jimuReportDbParam.extJson,
		</#if>
WHERE id = :jimuReportDbParam.id