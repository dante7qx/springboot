CREATE DATABASE IF NOT EXISTS `springboot` DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;

USE `springboot`;

DROP TABLE IF EXISTS T_Person;
CREATE TABLE T_Person (
	Id bigint(20) NOT NULL, 
	Name varchar(32) NOT NULL COMMENT '姓名',
	Age int NOT NULL COMMENT '年龄',
	Address varchar(256) NOT NULL COMMENT '地址',
	UpdateBy varchar(32) NOT NULL COMMENT '更新人',
	UpdateDate DATETIME NOT NULL COMMENT '更新时间'
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci
COMMENT='人员表' ;

DROP TABLE IF EXISTS T_Hobby;
CREATE TABLE T_Hobby (
	Id bigint(20) NOT NULL, 
	Hobby varchar(32) NOT NULL COMMENT '爱好',
	IsDelete tinyint NOT NULL COMMENT '是否删除',
	PersonId bigint(20) NOT NULL COMMENT '人员Id',
	UpdateBy varchar(32) NOT NULL COMMENT '更新人',
	UpdateDate DATETIME NOT NULL COMMENT '更新时间'
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_general_ci
COMMENT='人员爱好表' ;
