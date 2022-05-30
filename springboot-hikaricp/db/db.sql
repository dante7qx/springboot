drop table if exists t_multi_thread_insert;
create table t_multi_thread_insert (
  id bigint(20) not null auto_increment comment 'id',
  uid varchar(32) not null comment 'uid',
  name varchar(128) comment '名称',
  create_time datetime comment '创建时间',
  update_time datetime comment '更新时间',

  primary key (id)
) engine = innodb auto_increment = 1 comment = '多线程插入';