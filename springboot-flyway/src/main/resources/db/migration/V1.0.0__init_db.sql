drop table if exists t_user ;
create table `t_user` (
  `id` bigint(20) NOT NULL auto_increment comment '主键',
  `name` varchar(20) NOT NULL comment '姓名',
  `age` int(5) DEFAULT NULL comment '年龄',
  primary key (`id`)
) engine=innodb default charset=utf8mb4;

insert into t_user (name, age) values ('dante', 30);