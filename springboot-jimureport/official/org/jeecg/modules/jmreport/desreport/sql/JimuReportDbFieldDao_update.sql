UPDATE jimu_report_db_field
SET 
	   <#if jimuReportDbField.id ?exists>
		   ID = :jimuReportDbField.id,
		</#if>
	   <#if jimuReportDbField.createBy ?exists>
		   CREATE_BY = :jimuReportDbField.createBy,
		</#if>
	    <#if jimuReportDbField.createTime ?exists>
		   CREATE_TIME = :jimuReportDbField.createTime,
		</#if>
	   <#if jimuReportDbField.updateBy ?exists>
		   UPDATE_BY = :jimuReportDbField.updateBy,
		</#if>
	    <#if jimuReportDbField.updateTime ?exists>
		   UPDATE_TIME = :jimuReportDbField.updateTime,
		</#if>
	   <#if jimuReportDbField.jimuReportDbId ?exists>
		   JIMU_REPORT_DB_ID = :jimuReportDbField.jimuReportDbId,
		</#if>
	   <#if jimuReportDbField.fieldName ?exists>
		   FIELD_NAME = :jimuReportDbField.fieldName,
		</#if>
	   <#if jimuReportDbField.fieldText ?exists>
		   FIELD_TEXT = :jimuReportDbField.fieldText,
		</#if>
	   <#if jimuReportDbField.widgetType ?exists>
		   WIDGET_TYPE = :jimuReportDbField.widgetType,
		</#if>
	   <#if jimuReportDbField.widgetWidth ?exists>
		   WIDGET_WIDTH = :jimuReportDbField.widgetWidth,
		</#if>
	   <#if jimuReportDbField.orderNum ?exists>
		   ORDER_NUM = :jimuReportDbField.orderNum,
		</#if>
	   <#if jimuReportDbField.searchFlag ?exists>
		   SEARCH_FLAG = :jimuReportDbField.searchFlag,
		</#if>
	   <#if jimuReportDbField.searchMode ?exists>
		   SEARCH_MODE = :jimuReportDbField.searchMode,
		</#if>
		<#if jimuReportDbField.searchValue ?exists>
		   SEARCH_VALUE = :jimuReportDbField.searchValue,
		</#if>
	   <#if jimuReportDbField.dictCode ?exists>
		   DICT_CODE = :jimuReportDbField.dictCode,
		</#if>
		<#if jimuReportDbField.searchFormat ?exists>
		   SEARCH_FORMAT = :jimuReportDbField.searchFormat,
		</#if>
        <#if jimuReportDbField.extJson ?exists>
           EXT_JSON = :jimuReportDbField.extJson,
		</#if>
WHERE id = :jimuReportDbField.id