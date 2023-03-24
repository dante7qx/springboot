-- 分库分表
drop database if exists proxy_ds_0;
drop database if exists proxy_ds_1;

create database proxy_ds_0;
create database proxy_ds_1;