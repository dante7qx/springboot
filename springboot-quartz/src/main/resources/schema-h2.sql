drop table t_scheduler_job if exists;
create table t_scheduler_job (
	id bigint not null auto_increment,
	job_id varchar(64) not null,
	job_name varchar(256) not null,
	job_class varchar(256) not null,
	job_desc varchar(2048) not null,
	previous_fire_time timestamp,
	fire_time timestamp not null,
	next_fire_time timestamp,
	start_time timestamp not null,
	primary key(id)
);