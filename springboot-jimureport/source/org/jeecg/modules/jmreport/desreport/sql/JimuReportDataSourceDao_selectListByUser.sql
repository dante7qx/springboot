SELECT * FROM jimu_report_data_source where 
 type = 'report' 
<#if createBy?? && createBy ?length gt 0>
 and create_by = :createBy
</#if>
<#if tenantId?? && tenantId ?length gt 0>
 and tenant_id = :tenantId
</#if>