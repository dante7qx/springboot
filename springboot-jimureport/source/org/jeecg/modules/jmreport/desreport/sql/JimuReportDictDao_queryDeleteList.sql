select * from jimu_dict where del_flag = 1
<#if ( username )?? && username ?length gt 0>
and create_by= :username
</#if>