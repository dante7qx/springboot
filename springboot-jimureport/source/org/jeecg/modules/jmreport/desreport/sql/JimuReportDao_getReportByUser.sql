select id, name from jimu_report
where 
<#if username?? && username ?length gt 0 >
    create_by=:username and
</#if>
    id!=:reportId and del_flag=0