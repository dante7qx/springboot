UPDATE jimu_report_share
SET 
	   <#if jimuReportShare.id ?exists>
		   ID = :jimuReportShare.id,
		</#if>
	   <#if jimuReportShare.reportId ?exists>
		   REPORT_ID = :jimuReportShare.reportId,
		</#if>
	   <#if jimuReportShare.previewUrl ?exists>
		   PREVIEW_URL = :jimuReportShare.previewUrl,
		</#if>
	   <#if jimuReportShare.previewLock ?exists>
		   PREVIEW_LOCK = :jimuReportShare.previewLock,
		</#if>
	    <#if jimuReportShare.lastUpdateTime ?exists>
		   LAST_UPDATE_TIME = :jimuReportShare.lastUpdateTime,
		</#if>
	   <#if jimuReportShare.termOfValidity ?exists>
		   TERM_OF_VALIDITY = :jimuReportShare.termOfValidity,
		</#if>
	   <#if jimuReportShare.status ?exists>
		   STATUS = :jimuReportShare.status,
		</#if>
    <#if jimuReportShare.previewLockStatus ?exists>
       PREVIEW_LOCK_STATUS = :jimuReportShare.previewLockStatus,
    </#if>
WHERE id = :jimuReportShare.id