DROP DATABASE IF EXISTS `ds_0`;
DROP DATABASE IF EXISTS `ds_1`;

CREATE DATABASE IF NOT EXISTS `ds_0`;	
CREATE DATABASE IF NOT EXISTS `ds_1`;
USE `ds_0`; 

CREATE TABLE IF NOT EXISTS `t_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

CREATE TABLE IF NOT EXISTS `t_order_item` (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
