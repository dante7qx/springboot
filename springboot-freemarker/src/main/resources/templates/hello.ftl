<!DOCTYPE html>
<html lang="en">
<head >
<title>Spring Boot Demo - FreeMarker</title>
</head>
<body>
    <h2>首页</h2>
    <#assign id = "${x}">
    <ul>
        <#list userList as item> 
            <li>${item!}</li>
        </#list>
    </ul>
    <#include "part1.ftl">
</body>
</html>