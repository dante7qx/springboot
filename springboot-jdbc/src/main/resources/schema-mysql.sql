use test-mysql8;

create table if not exists t_user (
	id bigint not null auto_increment,
	name varchar(64) not null,
	age int not null,
	primary key(id)
);

create table if not exists t_contact (
	id bigint not null auto_increment,
	user_id bigint,
	mobile varchar(20) not null,
	address varchar(512),
	constraint fk_user_contact foreign key(user_id) references t_user(id) on delete cascade,
);