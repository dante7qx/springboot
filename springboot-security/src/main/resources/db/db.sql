-- 权限表
CREATE TABLE t_authority (  
  id bigint(20) NOT NULL AUTO_INCREMENT,  
  code varchar(64) NOT NULL, 
  name varchar(64) NOT NULL, 
  authority_desc varchar(100) NOT NULL DEFAULT '',  
  pid bigint(20) NOT NULL DEFAULT -1,
  show_order int NOT NULL DEFAULT 1,
  CONSTRAINT t_authority_pk PRIMARY KEY (id),
  CONSTRAINT t_authority_un_code UNIQUE KEY (code) 
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 
COLLATE=utf8_general_ci; 

-- 资源表
CREATE TABLE t_resource (  
  id bigint(20) NOT NULL AUTO_INCREMENT,  
  name varchar(64) NOT NULL, 
  url varchar(128) NOT NULL, 
  type varchar(32) NOT NULL DEFAULT 'ui', -- 'ui', 'rest'
  pid bigint(20) NOT NULL DEFAULT -1, 
  full_id varchar(128) NOT NULL DEFAULT '',
  show_order int NOT NULL DEFAULT 1,
  authority_id bigint(20) NOT NULL,
  CONSTRAINT t_resource_pk PRIMARY KEY (id),
  CONSTRAINT t_resource_authority_id_fk FOREIGN KEY (authority_id) REFERENCES t_authority (id)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 
COLLATE=utf8_general_ci; 


-- 角色表
CREATE TABLE t_role (  
  id bigint(20) NOT NULL AUTO_INCREMENT,   
  name varchar(64) NOT NULL, 
  role_desc varchar(128) NOT NULL DEFAULT '',  
  CONSTRAINT t_role_pk PRIMARY KEY (id) 
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 
COLLATE=utf8_general_ci;

-- 角色权限表
CREATE TABLE t_role_authority (
  id bigint(20) NOT NULL AUTO_INCREMENT, 
  role_id bigint(20) NOT NULL,
  authority_id bigint(20) NOT NULL,
  CONSTRAINT t_role_authority_pk PRIMARY KEY (id),
  CONSTRAINT t_role_authority_authority_id_fk FOREIGN KEY (authority_id) REFERENCES t_authority (id),
  CONSTRAINT t_role_authority_role_id_fk FOREIGN KEY (role_id) REFERENCES t_role (id)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;

-- 用户表
CREATE TABLE t_user (  
  id bigint(20) NOT NULL AUTO_INCREMENT,
  account varchar(64) NOT NULL, 
  name varchar(64) NOT NULL, 
  password varchar(256) NOT NULL,
  salt varchar(128) NOT NULL,
  email varchar(128) NOT NULL DEFAULT '', 
  CONSTRAINT t_user_pk PRIMARY KEY (id)，
  CONSTRAINT t_user_un_account UNIQUE KEY (account) 
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8 
COLLATE=utf8_general_ci;

-- 用户角色表
CREATE TABLE t_user_role (
  id bigint(20) NOT NULL AUTO_INCREMENT, 
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  CONSTRAINT t_user_role_pk PRIMARY KEY (id),
  CONSTRAINT t_user_role_user_id_fk FOREIGN KEY (user_id) REFERENCES t_user (id),
  CONSTRAINT t_user_role_role_id_fk FOREIGN KEY (role_id) REFERENCES t_role (id)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci;
