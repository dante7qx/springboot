drop table pub_service_doc if exists;
create table pub_service_doc (
	id varchar(64),
	service_title varchar(128),
	doc_content text,
	primary key(id)
);

create table git_doc (
	id varchar(64),
	title varchar(128),
	primary key(id)
);

create table git_doc_chapter(
	id varchar(64),
	git_doc_id varchar(64),
	title varchar(128),
	level INT,
	seq INT,
	pid varchar(64),
	source_doc_addr varchar(1024),
	doc_page_addr varchar(1024),
	primary key(id)
);

