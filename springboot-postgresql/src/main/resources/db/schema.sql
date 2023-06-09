-- 借阅者表
drop table if exists t_userinfo;
create table t_userinfo (
  id serial primary key,
  name varchar(20) not null,
  age int not null
);
comment on column t_userinfo.id is '主键';
comment on column t_userinfo.name is '姓名';
comment on column t_userinfo.age is '年龄';
comment on table t_userinfo is '借阅者表';

-- 书籍表
drop table if exists t_bookinfo;
create table t_bookinfo (
  id int primary key,
  book_no varchar(20) not null,
  book_name varchar(20) not null
);
comment on column t_bookinfo.id is '主键';
comment on column t_bookinfo.book_no is '书籍编号';
comment on column t_bookinfo.book_name is '书籍名称';
comment on table t_bookinfo is '书籍表';

-- 借阅记录表
drop table if exists t_borrow_record;
create table t_borrow_record (
  id serial primary key,
  user_id int not null,
  book_id int not null,
  borrow_time timestamp not null
);
comment on column t_borrow_record.id is '主键';
comment on column t_borrow_record.user_id is '用户id';
comment on column t_borrow_record.book_id is '书籍id';
comment on column t_borrow_record.borrow_time is '借阅时间';
comment on table t_borrow_record is '借阅记录表';


-- 测试示例表
drop table if exists t_demo;
create table t_demo (
  demo_id bigserial not null primary key,
  demo_name varchar(30) default '',
  demo_time date,
  demo_image varchar(2048),
  attachment varchar(2048),
  demo_content text,
  del_flag smallint default 0,
  create_by varchar(64) default '',
  create_time timestamp,
  update_by varchar(64) default '',
  update_time timestamp
);

COMMENT ON COLUMN t_demo.demo_id is '业务主键ID';
COMMENT ON COLUMN t_demo.demo_name is '业务名称';
COMMENT ON COLUMN t_demo.demo_time is '业务时间';
COMMENT ON COLUMN t_demo.demo_image is '业务图片';
COMMENT ON COLUMN t_demo.attachment is '业务附件';
COMMENT ON COLUMN t_demo.demo_content is '业务名称';
COMMENT ON COLUMN t_demo.del_flag is '删除标识 0 未删除 1 已删除';
COMMENT ON COLUMN t_demo.create_by is '创建者';
COMMENT ON COLUMN t_demo.create_time is '创建时间';
COMMENT ON COLUMN t_demo.update_by is '更新者';
COMMENT ON COLUMN t_demo.update_time is '更新时间';
COMMENT ON TABLE t_demo is '业务表';