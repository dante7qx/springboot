drop table pub_service_doc if exists;
create table pub_service_doc (
	id varchar(64),
	service_title varchar(128),
	doc_content text,
	primary key(id)
);