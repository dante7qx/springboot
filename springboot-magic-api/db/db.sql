
use springboot_magic_api;

DROP TABLE IF EXISTS magic_api_file;
CREATE TABLE `magic_api_file` (
  `file_path` varchar(512) NOT NULL,
  `file_content` mediumtext,
  PRIMARY KEY (`file_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS magic_api_backup;
CREATE TABLE `magic_api_backup` (
  `id` varchar(32) NOT NULL COMMENT '原对象ID',
  `create_date` bigint NOT NULL COMMENT '备份时间',
  `tag` varchar(32) DEFAULT NULL COMMENT '标签',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `name` varchar(64) DEFAULT NULL COMMENT '原名称',
  `content` blob COMMENT '备份内容',
  `create_by` varchar(64) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`,`create_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS test_data;
create table test_data (
    id   bigint not NULL primary key,
    name varchar(100) null
);
INSERT INTO test_data (id, name) VALUES (1, 'magicApi');
INSERT INTO test_data (id, name) VALUES (2, 'xiaoDong');