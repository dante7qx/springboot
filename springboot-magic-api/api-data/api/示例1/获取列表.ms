{
  "properties" : { },
  "id" : "14c187799ba641c3a90ecbd859511637",
  "script" : null,
  "groupId" : "8590bf2f29a14fbebea3591139bf89f6",
  "name" : "获取列表",
  "createTime" : 1691638181443,
  "updateTime" : null,
  "lock" : null,
  "createBy" : null,
  "updateBy" : null,
  "path" : "list",
  "method" : "GET",
  "parameters" : [ ],
  "options" : [ ],
  "requestBody" : null,
  "headers" : [ ],
  "paths" : [ ],
  "responseBody" : null,
  "description" : null,
  "requestBodyDefinition" : null,
  "responseBodyDefinition" : null
}
================================
var sql = """
select * from test_data
"""

return db.select(sql)