UPDATE jimu_report_db
SET 
	   <#if jimuReportDb.id ?exists>
		   ID = :jimuReportDb.id,
		</#if>
	   <#if jimuReportDb.jimuReportId ?exists>
		   JIMU_REPORT_ID = :jimuReportDb.jimuReportId,
		</#if>
	   <#if jimuReportDb.createBy ?exists>
		   CREATE_BY = :jimuReportDb.createBy,
		</#if>
	   <#if jimuReportDb.updateBy ?exists>
		   UPDATE_BY = :jimuReportDb.updateBy,
		</#if>
	    <#if jimuReportDb.createTime ?exists>
		   CREATE_TIME = :jimuReportDb.createTime,
		</#if>
	    <#if jimuReportDb.updateTime ?exists>
		   UPDATE_TIME = :jimuReportDb.updateTime,
		</#if>
	   <#if jimuReportDb.dbCode ?exists>
		   DB_CODE = :jimuReportDb.dbCode,
		</#if>
	   <#if jimuReportDb.dbChName ?exists>
		   DB_CH_NAME = :jimuReportDb.dbChName,
		</#if>
	   <#if jimuReportDb.dbType ?exists>
		   DB_TYPE = :jimuReportDb.dbType,
		</#if>
	   <#if jimuReportDb.dbTableName ?exists>
		   DB_TABLE_NAME = :jimuReportDb.dbTableName,
		</#if>
	   <#if jimuReportDb.dbDynSql ?exists>
		   DB_DYN_SQL = :jimuReportDb.dbDynSql,
		</#if>
	   <#if jimuReportDb.dbKey ?exists>
		   DB_KEY = :jimuReportDb.dbKey,
		</#if>
	   <#if jimuReportDb.tbDbKey ?exists>
		   TB_DB_KEY = :jimuReportDb.tbDbKey,
		</#if>
	   <#if jimuReportDb.tbDbTableName ?exists>
		   TB_DB_TABLE_NAME = :jimuReportDb.tbDbTableName,
		</#if>
	   <#if jimuReportDb.javaType ?exists>
		   JAVA_TYPE = :jimuReportDb.javaType,
		</#if>
	   <#if jimuReportDb.javaValue ?exists>
		   JAVA_VALUE = :jimuReportDb.javaValue,
		</#if>
	   <#if jimuReportDb.apiUrl ?exists>
		   API_URL = :jimuReportDb.apiUrl,
		</#if>
	   <#if jimuReportDb.apiMethod ?exists>
		   API_METHOD = :jimuReportDb.apiMethod,
		</#if>
	   <#if jimuReportDb.isPage ?exists>
		   IS_PAGE = :jimuReportDb.isPage,
		</#if>
	   <#if jimuReportDb.dbSource ?exists>
		   DB_SOURCE = :jimuReportDb.dbSource,
		</#if>
	   <#if jimuReportDb.dbSourceType ?exists>
		   DB_SOURCE_TYPE = :jimuReportDb.dbSourceType,
		</#if>
	   <#if jimuReportDb.isList ?exists>
		   IS_LIST = :jimuReportDb.isList,
		</#if>
        <#if jimuReportDb.jsonData ?exists>
           JSON_DATA = :jimuReportDb.jsonData,
        </#if>
        <#if jimuReportDb.apiConvert ?exists>
             API_CONVERT = :jimuReportDb.apiConvert,
        </#if>
WHERE id = :jimuReportDb.id