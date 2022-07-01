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