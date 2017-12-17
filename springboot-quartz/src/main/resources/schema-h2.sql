drop table t_scheduler_job if exists;
create table t_scheduler_job (
	id bigint not null auto_increment,
	job_id varchar(64) not null,
	job_name varchar(256) not null,
	job_class varchar(256) not null,
	job_desc varchar(2048) not null,
	cron varchar(64) null,
	previous_fire_time timestamp null,
	fire_time timestamp null,
	next_fire_time timestamp null,
	start_time timestamp null,
	start_job int not null default 1,
	fail_reason varchar(200000),
	update_date timestamp DEFAULT CURRENT_TIMESTAMP,
	primary key(id)
);

drop table t_test if exists;
create table t_test (
	id bigint not null auto_increment,
	name varchar(256) not null,
	update_date timestamp not null,
	primary key(id)
);