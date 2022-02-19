INSERT INTO T_Person(Id, Name, Age, Address, UpdateBy, UpdateDate) VALUES 
(1, '张三', 34, '北京朝阳区', '管理员1', now()),
(2, '李四', 24, '上海黄埔区', '管理员2', now()),
(3, '王五', 43, '武汉光谷区', '管理员3', now()),
(4, '赵六', 37, '天水秦州区', '管理员4', now());

INSERT INTO T_Hobby(Id, Hobby, IsDelete, PersonId, UpdateBy, UpdateDate) VALUES 
(1, '足球', 0, 1, '管理员1', now()),
(2, '篮球', 0, 1, '管理员1', now()),
(3, '排球', 0, 2, '管理员1', now()),
(4, '羽毛球', 0, 2, '管理员1', now()),
(5, '击剑', 0, 2, '管理员1', now()),
(6, '冰球', 0, 3, '管理员1', now()),
(7, '网球', 0, 3, '管理员1', now()),
(8, '花样滑冰', 0, 3, '管理员1', now()),
(9, '冰壶', 0, 4, '管理员1', now()),
(10, '橄榄球', 0, 4, '管理员1', now()),
(11, '漫画', 0, 4, '管理员1', now()),
(12, '台球', 0, 1, '管理员1', now()),
(13, '音乐', 0, 2, '管理员1', now());


alter table T_Person add constraint pk_t_person primary key (id) ;
alter table T_Person modify column id bigint(20) not null auto_increment;

alter table T_Hobby add constraint pk_t_hobby primary key (id) ;
alter table T_Hobby modify column id bigint(20) not null auto_increment;