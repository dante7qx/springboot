UPDATE jimu_dict
SET 
	   <#if reportDict.id ?exists>
		   ID = :reportDict.id,
		</#if>
	   <#if reportDict.dictName ?exists>
		   DICT_NAME = :reportDict.dictName,
		</#if>
	   <#if reportDict.dictCode ?exists>
		   DICT_CODE = :reportDict.dictCode,
		</#if>
	   <#if reportDict.description ?exists>
		   DESCRIPTION = :reportDict.description,
		</#if>
	   <#if reportDict.delFlag ?exists>
		   DEL_FLAG = :reportDict.delFlag,
		</#if>
	   <#if reportDict.createBy ?exists>
		   CREATE_BY = :reportDict.createBy,
		</#if>
	    <#if reportDict.createTime ?exists>
		   CREATE_TIME = :reportDict.createTime,
		</#if>
	   <#if reportDict.updateBy ?exists>
		   UPDATE_BY = :reportDict.updateBy,
		</#if>
	    <#if reportDict.updateTime ?exists>
		   UPDATE_TIME = :reportDict.updateTime,
		</#if>
	   <#if reportDict.type ?exists>
		   TYPE = :reportDict.type,
		</#if>
    <#if reportDict.tenantId ?exists>
       TENANT_ID = :reportDict.tenantId,
    </#if>
WHERE id = :reportDict.id