UPDATE jimu_report_data_source
SET 
	   <#if jimuReportDataSource.id ?exists>
		   ID = :jimuReportDataSource.id,
		</#if>
	   <#if jimuReportDataSource.name ?exists>
		   NAME = :jimuReportDataSource.name,
		</#if>
	   <#if jimuReportDataSource.reportId ?exists>
		   REPORT_ID = :jimuReportDataSource.reportId,
		</#if>
	   <#if jimuReportDataSource.code ?exists>
		   CODE = :jimuReportDataSource.code,
		</#if>
	   <#if jimuReportDataSource.remark ?exists>
		   REMARK = :jimuReportDataSource.remark,
		</#if>
	   <#if jimuReportDataSource.dbType ?exists>
		   DB_TYPE = :jimuReportDataSource.dbType,
		</#if>
	   <#if jimuReportDataSource.dbDriver ?exists>
		   DB_DRIVER = :jimuReportDataSource.dbDriver,
		</#if>
	   <#if jimuReportDataSource.dbUrl ?exists>
		   DB_URL = :jimuReportDataSource.dbUrl,
		</#if>
	   <#if jimuReportDataSource.dbUsername ?exists>
		   DB_USERNAME = :jimuReportDataSource.dbUsername,
		</#if>
	   <#if jimuReportDataSource.dbPassword ?exists>
		   DB_PASSWORD = :jimuReportDataSource.dbPassword,
		</#if>
	   <#if jimuReportDataSource.createBy ?exists>
		   CREATE_BY = :jimuReportDataSource.createBy,
		</#if>
	    <#if jimuReportDataSource.createTime ?exists>
		   CREATE_TIME = :jimuReportDataSource.createTime,
		</#if>
	   <#if jimuReportDataSource.updateBy ?exists>
		   UPDATE_BY = :jimuReportDataSource.updateBy,
		</#if>
	    <#if jimuReportDataSource.updateTime ?exists>
		   UPDATE_TIME = :jimuReportDataSource.updateTime,
		</#if>
		<#if jimuReportDataSource.connectTimes ?exists>
		   CONNECT_TIMES = :jimuReportDataSource.connectTimes,
		</#if>
		<#if jimuReportDataSource.type ?exists>
		   TYPE = :jimuReportDataSource.type,
		</#if>	
    <#if jimuReportDataSource.tenantId ?exists>
       TENANT_ID = :jimuReportDataSource.tenantId,
		</#if>
WHERE id = :jimuReportDataSource.id