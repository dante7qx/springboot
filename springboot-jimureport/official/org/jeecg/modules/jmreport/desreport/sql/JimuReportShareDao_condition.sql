		<#if ( jimuReportShare.id )?? && jimuReportShare.id ?length gt 0>
		    /* 主键 */
			and jrs.ID = :jimuReportShare.id
		</#if>
		<#if ( jimuReportShare.reportId )?? && jimuReportShare.reportId ?length gt 0>
		    /* 在线excel设计器id */
			and jrs.REPORT_ID = :jimuReportShare.reportId
		</#if>
		<#if ( jimuReportShare.previewUrl )?? && jimuReportShare.previewUrl ?length gt 0>
		    /* 预览地址 */
			and jrs.PREVIEW_URL = :jimuReportShare.previewUrl
		</#if>
		<#if ( jimuReportShare.previewLock )?? && jimuReportShare.previewLock ?length gt 0>
		    /* 密码锁 */
			and jrs.PREVIEW_LOCK = :jimuReportShare.previewLock
		</#if>
	    <#if ( jimuReportShare.lastUpdateTime )??>
		    /* 最后更新时间 */
			and jrs.LAST_UPDATE_TIME = :jimuReportShare.lastUpdateTime
		</#if>
		<#if ( jimuReportShare.termOfValidity )?? && jimuReportShare.termOfValidity ?length gt 0>
		    /* 有效期(0:永久有效，1:1天，2:7天) */
			and jrs.TERM_OF_VALIDITY = :jimuReportShare.termOfValidity
		</#if>
		<#if ( jimuReportShare.status )?? && jimuReportShare.status ?length gt 0>
		    /* 是否过期(0未过期，1已过期) */
			and jrs.STATUS = :jimuReportShare.status
		</#if>
    <#if ( jimuReportShare.previewLockStatus )?? && jimuReportShare.previewLockStatus ?length gt 0>
        /* 密码锁状态(0不存在密码锁，1存在密码锁) */
			and jrs.PREVIEW_LOCK_STATUS = :jimuReportShare.previewLockStatus
		</#if>
