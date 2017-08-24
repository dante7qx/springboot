DROP database IF EXISTS `springbatch`;

CREATE DATABASE `springbatch` DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

USE `springbatch`;

DROP TABLE IF EXISTS PEOPLE;

CREATE TABLE PEOPLE  (
    person_id bigint auto_increment primary key,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);