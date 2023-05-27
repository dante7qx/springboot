-- 创建表空间（由DBA执行）
create tablespace spirit datafile 'spirit.dbf' size 128;
create user spirit identified by spirit#123456 default tablespace spirit; 
grant dba to spirit;


-- Risun RSP
create tablespace RISUNRSP datafile 'RISUNRSP.DBF' size 128;
create user risun identified by "risun@123!" DEFAULT tablespace RISUNRSP ; 
grant RESOURCE,VTI,SOI to risun;

-- 代码生成所需权限
grant select on dba_objects to risun;
grant select on dba_constraints to risun;


-- grant select on all_cons_columns to risun;
-- grant select on dba_tables to risun;
-- revoke select on dba_tables from risun;

-- 删除
DROP USER risun CASCADE;
DROP tablespace RISUNRSP; 


-- 用户权限 						https://www.cndba.cn/dave/article/108605
-- 查询表，字段，索引，约束等常用语句 https://www.cnblogs.com/aaacarrot/p/17084012.html

-- 自增ID，手动设置Id
SET IDENTITY_INSERT t_demo ON;
INSERT ...
SET IDENTITY_INSERT t_demo OFF;