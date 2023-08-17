-- 账户表
CREATE TABLE IF NOT EXISTS `tb_account`
(
    `id`        INTEGER PRIMARY KEY auto_increment,
    `user_name` VARCHAR(100),
    `age`       INTEGER,
    `birthday`  DATETIME
);

INSERT INTO tb_account(id, user_name, age, birthday)
VALUES (1, '张三', 18, '2020-01-11'),
       (2, '李四', 19, '2021-03-21'),
       (3, '王五', 23, '2000-03-21')
       (4, '马六', 32, '1993-03-21');
       

-- 图书表
CREATE TABLE IF NOT EXISTS `tb_book`
(
    `id`        INTEGER PRIMARY KEY auto_increment,
    `account_id` Integer,
    `title`      VARCHAR(100),
    `content`    text
);

-- 角色表
CREATE TABLE IF NOT EXISTS `tb_role`
(
    `id`        INTEGER PRIMARY KEY auto_increment,
    `name`      VARCHAR(100)
);

-- 账户角色关系表
CREATE TABLE IF NOT EXISTS `tb_role_mapping`
(
    `account_id`   INTEGER ,
    `role_id`      INTEGER
);