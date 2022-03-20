
drop table if exists r_receive_doc;
create table r_receive_doc (
  receive_doc_id bigint(20) not null auto_increment comment 'id',
  process_instance_id varchar(128) default '' comment '流程实例id',
  file_type varchar(20) default '' NOT NULL comment '文件类型',
  receive_unit varchar(128) NOT NULL comment '来文单位',
  reveice_time date comment '来文时间',
  reveice_no date NOT NULL comment '来文编号',
  receive_title varchar(256) NOT NULL comment '来文标题',
  feedback_deadline varchar(64) default '' comment '反馈期限',
  attachment varchar(256) NOT NULL comment '附件',
  create_by varchar(64)  comment '创建人',
  create_time datetime comment '创建时间',
  update_by varchar(64)  comment '更新人',
  update_time datetime comment '更新时间'
  
  primary key (receive_doc_id)
) engine = innodb auto_increment = 1 comment = '收文登记';












