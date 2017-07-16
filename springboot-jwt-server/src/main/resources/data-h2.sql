-- t_user
insert into t_user(id, user_name, password, email, last_password_reset_date) values (1, 'dante', '$2a$10$0mZumykXOWGcaosL7K6wqOv3v3McNyRbvn0.nOJW9ezmeIrXwtaJK', 'dantefreedom@gmail.com', '2017-07-11 12:00:00');
insert into t_user(id, user_name, password, email, last_password_reset_date) values (2, 'snake', '$2a$10$HOWyFK4QrL/RRH01vagCVOCHGCBmBhLNSYVfsYcgvTIaQAQlgOsyC', 'snake@163.com', CURRENT_TIMESTAMP);
insert into t_user(id, user_name, password, email, last_password_reset_date) values (3, 'admin', '$2a$10$QcJ2JfIU5NgXJ/DpObelM.0b3pB0Sdt9/ZC/CqqI5AfkiuESLEdFG', 'admin@126.com', CURRENT_TIMESTAMP);

-- t_role
insert into t_role(id, code, name) values (1, 'USER', '普通用户');
insert into t_role(id, code, name) values (2, 'ADMIN', '管理员');
insert into t_role(id, code, name) values (3, 'SUPERADMIN', '超级管理员');

-- t_user_role
insert into t_user_role(user_id, role_id) values (1, 2);
insert into t_user_role(user_id, role_id) values (2, 1);
insert into t_user_role(user_id, role_id) values (3, 3);
