drop table user if exists;
create table user (
	id bigint generated by default as identity,
	account varchar(32),
	name varchar(64),
	age int,
	balance decimal(20,2),
	update_date datetime, 
	primary key(id)
);