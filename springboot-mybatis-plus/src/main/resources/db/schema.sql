DROP TABLE IF EXISTS t_emp;
CREATE TABLE t_emp
(
    id    BIGINT(20) NOT NULL COMMENT '主键ID',
    name  VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age   INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS t_address;
CREATE TABLE t_address
(
    id      BIGINT(20) NOT NULL COMMENT '主键ID',
    emp_id BIGINT(20) NULL DEFAULT NULL COMMENT '用户id',
    city    VARCHAR(50) NULL DEFAULT NULL COMMENT '城市',
    address VARCHAR(50) NULL DEFAULT NULL COMMENT '地址',
    PRIMARY KEY (id)
);


DELETE FROM t_emp;

INSERT INTO t_emp (id, name, age, email)
VALUES (1, 'Jone', 18, 'test1@baomidou.com'),
       (2, 'Jack', 20, 'test2@baomidou.com'),
       (3, 'Tom', 28, 'test3@baomidou.com'),
       (4, 'Sandy', 21, 'test4@baomidou.com'),
       (5, 'Billie', 24, 'test5@baomidou.com');

DELETE FROM t_address;

INSERT INTO t_address (id, emp_id, city, address)
VALUES (1, 1, '北京', '人民广场'),
       (2, 2, '上海', '人民广场'),
       (3, 3, '广州', '人民广场'),
       (4, 4, '上海', '人民广场'),
       (5, 5, '北京', '人民广场');