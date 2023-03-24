-- 分库分表
DROP DATABASE IF EXISTS jdbc_ds_0;
DROP DATABASE IF EXISTS jdbc_ds_1;
DROP DATABASE IF EXISTS jdbc_ds_2;

CREATE DATABASE jdbc_ds_0;
CREATE DATABASE jdbc_ds_1;
CREATE DATABASE jdbc_ds_2;

-- 写不分库读分库
DROP DATABASE IF EXISTS jdbc_write_ds;
DROP DATABASE IF EXISTS jdbc_read_ds_0;
DROP DATABASE IF EXISTS jdbc_read_ds_1;

CREATE DATABASE jdbc_write_ds;
CREATE DATABASE jdbc_read_ds_0;
CREATE DATABASE jdbc_read_ds_1;

