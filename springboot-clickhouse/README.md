## ClickHouse 列式数据库

### 一. 概述

ClickHouse是一个用于联机分析(OLAP)的列式数据库管理系统(DBMS)。

### 二. 安装



### 三. 用户权限

```sql
-- 创建用户
create user root identified  by 'iamdante';
-- 授权
GRANT SHOW, SELECT, INSERT, ALTER, CREATE, DROP, UNDROP TABLE, TRUNCATE, OPTIMIZE, BACKUP, KILL QUERY, KILL TRANSACTION, MOVE PARTITION BETWEEN SHARDS, ACCESS MANAGEMENT, SYSTEM, dictGet, INTROSPECTION, SOURCES, CLUSTER ON *.* TO root WITH GRANT OPTION;
```



### 八. 参考资料

- https://clickhouse.com/docs/zh
- https://juejin.cn/post/6939023230833262622
- https://blog.csdn.net/hanxiaotongtong/category_11854147.html