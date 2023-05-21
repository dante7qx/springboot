		<#if ( jimuReportDataSource.id )?? && jimuReportDataSource.id ?length gt 0>
		    /* id */
			and jrds.ID = :jimuReportDataSource.id
		</#if>
		<#if ( jimuReportDataSource.name )?? && jimuReportDataSource.name ?length gt 0>
		    /* 数据源名称 */
			and jrds.NAME = :jimuReportDataSource.name
		</#if>
		<#if ( jimuReportDataSource.reportId )?? && jimuReportDataSource.reportId ?length gt 0>
		    /* 报表_id */
			and jrds.REPORT_ID = :jimuReportDataSource.reportId
		</#if>
		<#if ( jimuReportDataSource.code )?? && jimuReportDataSource.code ?length gt 0>
		    /* 编码 */
			and jrds.CODE = :jimuReportDataSource.code
		</#if>
		<#if ( jimuReportDataSource.remark )?? && jimuReportDataSource.remark ?length gt 0>
		    /* 备注 */
			and jrds.REMARK = :jimuReportDataSource.remark
		</#if>
		<#if ( jimuReportDataSource.dbType )?? && jimuReportDataSource.dbType ?length gt 0>
		    /* 数据库类型 */
			and jrds.DB_TYPE = :jimuReportDataSource.dbType
		</#if>
		<#if ( jimuReportDataSource.dbDriver )?? && jimuReportDataSource.dbDriver ?length gt 0>
		    /* 驱动类 */
			and jrds.DB_DRIVER = :jimuReportDataSource.dbDriver
		</#if>
		<#if ( jimuReportDataSource.dbUrl )?? && jimuReportDataSource.dbUrl ?length gt 0>
		    /* 数据源地址 */
			and jrds.DB_URL = :jimuReportDataSource.dbUrl
		</#if>
		<#if ( jimuReportDataSource.dbUsername )?? && jimuReportDataSource.dbUsername ?length gt 0>
		    /* 用户名 */
			and jrds.DB_USERNAME = :jimuReportDataSource.dbUsername
		</#if>
		<#if ( jimuReportDataSource.dbPassword )?? && jimuReportDataSource.dbPassword ?length gt 0>
		    /* 密码 */
			and jrds.DB_PASSWORD = :jimuReportDataSource.dbPassword
		</#if>
		<#if ( jimuReportDataSource.createBy )?? && jimuReportDataSource.createBy ?length gt 0>
		    /* 创建人 */
			and jrds.CREATE_BY = :jimuReportDataSource.createBy
		</#if>
	    <#if ( jimuReportDataSource.createTime )??>
		    /* 创建日期 */
			and jrds.CREATE_TIME = :jimuReportDataSource.createTime
		</#if>
		<#if ( jimuReportDataSource.updateBy )?? && jimuReportDataSource.updateBy ?length gt 0>
		    /* 更新人 */
			and jrds.UPDATE_BY = :jimuReportDataSource.updateBy
		</#if>
	    <#if ( jimuReportDataSource.updateTime )??>
		    /* 更新日期 */
			and jrds.UPDATE_TIME = :jimuReportDataSource.updateTime
		</#if>
		<#if ( jimuReportDataSource.type )??>
		    /* 更新日期 */
			and jrds.TYPE = :jimuReportDataSource.type
		</#if>	   
    <#if ( jimuReportDataSource.tenantId )??>
      /* 租户标识 */
			and jrds.TENANT_ID = :jimuReportDataSource.tenantId
		</#if>
