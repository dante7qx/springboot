-- t_student
insert into t_student(id, name, age, update_date) values (1, '张三', 21, CURRENT_TIMESTAMP);
insert into t_student(id, name, age, update_date) values (2, '李四', 22, CURRENT_TIMESTAMP);
insert into t_student(id, name, age, update_date) values (3, '王五', 23, CURRENT_TIMESTAMP);
insert into t_student(id, name, age, update_date) values (4, '赵六', 24, CURRENT_TIMESTAMP);
insert into t_student(id, name, age, update_date) values (5, '马七', 22, CURRENT_TIMESTAMP);

-- t_teacher
insert into t_teacher(id, name, post, update_date) values (1, '墨冲老师', '讲师', CURRENT_TIMESTAMP);
insert into t_teacher(id, name, post, update_date) values (2, '秦风老师', '教授', CURRENT_TIMESTAMP);
insert into t_teacher(id, name, post, update_date) values (3, '天义老师', '博导', CURRENT_TIMESTAMP);

-- t_course
insert into t_course(id, name, week, start_time, end_time) values(1, 'Java', '周一', '0900', '1100');
insert into t_course(id, name, week, start_time, end_time) values(2, 'Go', '周一', '1400', '1600');
insert into t_course(id, name, week, start_time, end_time) values(3, 'Docker', '周二', '1500', '1700');
insert into t_course(id, name, week, start_time, end_time) values(4, 'React', '周三', '0900', '1100');
insert into t_course(id, name, week, start_time, end_time) values(5, 'Linux', '周四', '1300', '1500');
insert into t_course(id, name, week, start_time, end_time) values(6, 'Redis', '周五', '1400', '1600');


-- t_student_course
insert into t_student_course(student_id, course_id) values(1, 1);
insert into t_student_course(student_id, course_id) values(1, 3);
insert into t_student_course(student_id, course_id) values(1, 5);
insert into t_student_course(student_id, course_id) values(2, 5);
insert into t_student_course(student_id, course_id) values(2, 1);
insert into t_student_course(student_id, course_id) values(3, 4);
insert into t_student_course(student_id, course_id) values(4, 1);
insert into t_student_course(student_id, course_id) values(4, 2);
insert into t_student_course(student_id, course_id) values(4, 6);
insert into t_student_course(student_id, course_id) values(5, 3);
insert into t_student_course(student_id, course_id) values(5, 5);

-- t_teacher_course
insert into t_teacher_course(teacher_id, course_id) values(1, 1);
insert into t_teacher_course(teacher_id, course_id) values(1, 2);
insert into t_teacher_course(teacher_id, course_id) values(1, 6);
insert into t_teacher_course(teacher_id, course_id) values(2, 4);
insert into t_teacher_course(teacher_id, course_id) values(3, 3);
insert into t_teacher_course(teacher_id, course_id) values(3, 5);
