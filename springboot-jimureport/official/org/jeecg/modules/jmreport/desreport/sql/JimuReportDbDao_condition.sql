		<#if ( jimuReportDb.id )?? && jimuReportDb.id ?length gt 0>
		    /* id */
			and jrd.ID = :jimuReportDb.id
		</#if>
		<#if ( jimuReportDb.jimuReportId )?? && jimuReportDb.jimuReportId ?length gt 0>
		    /* 主键字段 */
			and jrd.JIMU_REPORT_ID = :jimuReportDb.jimuReportId
		</#if>
		<#if ( jimuReportDb.createBy )?? && jimuReportDb.createBy ?length gt 0>
		    /* 创建人登录名称 */
			and jrd.CREATE_BY = :jimuReportDb.createBy
		</#if>
		<#if ( jimuReportDb.updateBy )?? && jimuReportDb.updateBy ?length gt 0>
		    /* 更新人登录名称 */
			and jrd.UPDATE_BY = :jimuReportDb.updateBy
		</#if>
	    <#if ( jimuReportDb.createTime )??>
		    /* 创建日期 */
			and jrd.CREATE_TIME = :jimuReportDb.createTime
		</#if>
	    <#if ( jimuReportDb.updateTime )??>
		    /* 更新日期 */
			and jrd.UPDATE_TIME = :jimuReportDb.updateTime
		</#if>
		<#if ( jimuReportDb.dbCode )?? && jimuReportDb.dbCode ?length gt 0>
		    /* 数据集编码 */
			and jrd.DB_CODE = :jimuReportDb.dbCode
		</#if>
		<#if ( jimuReportDb.dbChName )?? && jimuReportDb.dbChName ?length gt 0>
		    /* 数据集名字 */
			and jrd.DB_CH_NAME = :jimuReportDb.dbChName
		</#if>
		<#if ( jimuReportDb.dbType )?? && jimuReportDb.dbType ?length gt 0>
		    /* 数据源类型 */
			and jrd.DB_TYPE = :jimuReportDb.dbType
		</#if>
		<#if ( jimuReportDb.dbTableName )?? && jimuReportDb.dbTableName ?length gt 0>
		    /* 数据库表名 */
			and jrd.DB_TABLE_NAME = :jimuReportDb.dbTableName
		</#if>
		<#if ( jimuReportDb.dbDynSql )?? && jimuReportDb.dbDynSql ?length gt 0>
		    /* 动态查询SQL */
			and jrd.DB_DYN_SQL = :jimuReportDb.dbDynSql
		</#if>
		<#if ( jimuReportDb.dbKey )?? && jimuReportDb.dbKey ?length gt 0>
		    /* 数据源KEY */
			and jrd.DB_KEY = :jimuReportDb.dbKey
		</#if>
		<#if ( jimuReportDb.tbDbKey )?? && jimuReportDb.tbDbKey ?length gt 0>
		    /* 填报数据源 */
			and jrd.TB_DB_KEY = :jimuReportDb.tbDbKey
		</#if>
		<#if ( jimuReportDb.tbDbTableName )?? && jimuReportDb.tbDbTableName ?length gt 0>
		    /* 填报数据表 */
			and jrd.TB_DB_TABLE_NAME = :jimuReportDb.tbDbTableName
		</#if>
		<#if ( jimuReportDb.javaType )?? && jimuReportDb.javaType ?length gt 0>
		    /* java类数据集  类型（spring:springkey,class:java类名） */
			and jrd.JAVA_TYPE = :jimuReportDb.javaType
		</#if>
		<#if ( jimuReportDb.javaValue )?? && jimuReportDb.javaValue ?length gt 0>
		    /* java类数据源  数值（bean key/java类名） */
			and jrd.JAVA_VALUE = :jimuReportDb.javaValue
		</#if>
		<#if ( jimuReportDb.apiUrl )?? && jimuReportDb.apiUrl ?length gt 0>
		    /* 请求地址 */
			and jrd.API_URL = :jimuReportDb.apiUrl
		</#if>
		<#if ( jimuReportDb.apiMethod )?? && jimuReportDb.apiMethod ?length gt 0>
		    /* 请求方法0-get,1-post */
			and jrd.API_METHOD = :jimuReportDb.apiMethod
		</#if>
		<#if ( jimuReportDb.isPage )?? && jimuReportDb.isPage ?length gt 0>
		    /* 是否作为分页,0:不分页，1:分页 */
			and jrd.IS_PAGE = :jimuReportDb.isPage
		</#if>
		<#if ( jimuReportDb.dbSource )?? && jimuReportDb.dbSource ?length gt 0>
		    /* 数据源 */
			and jrd.DB_SOURCE = :jimuReportDb.dbSource
		</#if>
		<#if ( jimuReportDb.dbSourceType )?? && jimuReportDb.dbSourceType ?length gt 0>
		    /* 数据库类型MYSQL ORACLE SQLSERVE */
			and jrd.DB_SOURCE_TYPE = :jimuReportDb.dbSourceType
		</#if>
		<#if ( jimuReportDb.isList )?? && jimuReportDb.isList ?length gt 0>
		    /* isList */
			and jrd.IS_LIST = :jimuReportDb.isList
		</#if>
