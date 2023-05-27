drop table if exists spirit.t_demo;
create table spirit.t_demo (
  demo_id bigint not null auto_increment comment '业务主键ID',
  demo_name varchar(30) default '' comment '业务名称',
  demo_time datetime comment '业务时间',
  demo_image varchar(2048) comment '业务图片',
  attachment varchar(2048) comment '业务附件',
  demo_content CLOB comment '业务名称',
  del_flag tinyint default 0 comment '删除标识 0 未删除 1 已删除',
  create_by varchar(64) default '' comment '创建者',
  create_time datetime comment '创建时间',
  update_by varchar(64) default '' comment '更新者',
  update_time datetime comment '更新时间',
  NOT CLUSTER primary key (demo_id)
) STORAGE(ON spirit, CLUSTERBTR);
COMMENT ON TABLE spirit.t_demo IS '测试用表';