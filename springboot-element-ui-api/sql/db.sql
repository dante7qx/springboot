-- 中国气象频道官 - 城市代码表
drop table if exists dp_city_code;
create table dp_city_code (
  code varchar(16) not null comment '城市代码',
  ch varchar(32) not null   comment '城市名称',
  en varchar(64) not null   comment '城市拼音',
  extra varchar(128) not null default '' comment '额外信息',
  primary key (code, ch, en)
) engine=innodb comment = '城市代码表';