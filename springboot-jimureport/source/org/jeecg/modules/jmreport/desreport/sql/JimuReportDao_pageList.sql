SELECT jr.ID,jr.NAME,jr.CODE,jr.TYPE,jr.template,jr.thumb FROM jimu_report jr
WHERE
1=1
<#if ( jimuReport.name )?? && jimuReport.name ?length gt 0>
    /* 名称 */
and jr.NAME  like  :jimuReport.name
</#if>
<#if ( jimuReport.createBy )?? && jimuReport.createBy ?length gt 0>
/* 创建人 */
and jr.CREATE_BY = :jimuReport.createBy
</#if>
<#if ( jimuReport.type )?? && jimuReport.type ?length gt 0>
    /* 类型 */
    and jr.TYPE = :jimuReport.type
</#if>
<#if ( jimuReport.delFlag )?? && jimuReport.delFlag ?length gt 0>
    /* 删除标识0-正常,1-已删除 */
    and jr.DEL_FLAG = :jimuReport.delFlag
</#if>
<#if ( jimuReport.template )?? && jimuReport.template ?length gt 0>
    /* 是否是模板 0-是,1-不是 */
    and jr.TEMPLATE = :jimuReport.template
</#if>
<#if ( jimuReport.tenantId )?? && jimuReport.tenantId ?length gt 0>
    /* 租户标识 */
    and jr.tenant_id = :jimuReport.tenantId
</#if>
 ORDER BY jr.create_time DESC 
