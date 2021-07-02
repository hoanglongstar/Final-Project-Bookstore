CREATE SCHEMA `bookstore` DEFAULT CHARACTER SET utf8 ;

use bookstore;

CREATE TABLE `bookstore`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(64) NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(128) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));

INSERT INTO `bookstore`.`users` (`full_name`, `username`, `password`) VALUES ('ADMIN', 'admin', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG');

CREATE TABLE `bookstore`.`roles` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`));

INSERT INTO `bookstore`.`roles` (`role_name`) VALUES ('ADMIN');
INSERT INTO `bookstore`.`roles` (`role_name`) VALUES ('PRODUCT');
INSERT INTO `bookstore`.`roles` (`role_name`) VALUES ('SALE');
INSERT INTO `bookstore`.`roles` (`role_name`) VALUES ('SHIPPER');

CREATE TABLE `bookstore`.`user_roles` (
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`));

ALTER TABLE `bookstore`.`user_roles` 
ADD CONSTRAINT `fk_role_id`
  FOREIGN KEY (`role_id`)
  REFERENCES `bookstore`.`roles` (`role_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '1');

CREATE TABLE `bookstore`.`customers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(128) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `phone_number` VARCHAR(45) NULL,
  `address` VARCHAR(45) NULL,
  `photo_url` VARCHAR(45) NULL,
  `rank` VARCHAR(45) NULL,
  `create_date` DATETIME NULL,
  `last_login` VARCHAR(45) NULL,
  `email_verified` TINYINT NULL DEFAULT 0,
  `verification_code` VARCHAR(256) NULL,
  `auth_provider` VARCHAR(45) NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`));

CREATE TABLE `bookstore`.`categories` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `description` VARCHAR(256) NULL,
  `photo` VARCHAR(45) NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `parent_id` INT NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `bookstore`.`products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `code` VARCHAR(45) NOT NULL,
  `description` VARCHAR(256) NULL,
  `category_id` INT NOT NULL,
  `photo` VARCHAR(45) NULL,
  `quantity` INT NULL,
  `price` FLOAT NULL,
  `sale_price` FLOAT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`));
  
  ALTER TABLE `bookstore`.`user_roles` 
ADD CONSTRAINT `fk_user_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `bookstore`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
INSERT INTO `bookstore`.`users` (`full_name`, `username`, `password`) VALUES ('GreenA', 'greena', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG');
INSERT INTO `bookstore`.`users` (`full_name`, `username`, `password`) VALUES ('GreenB', 'greenb', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG');
INSERT INTO `bookstore`.`users` (`full_name`, `username`, `password`) VALUES ('GreenC', 'greenc', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG');

INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('2', '2');
INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('2', '3');
INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('3', '3');
INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('4', '4');

INSERT INTO `bookstore`.`products` (`name`, `code`, `description`, `category_id`, `quantity`, `price`, `sale_price`, `enabled`) VALUES ('Rong Do', 'BS001', 'Detective, Novel', '1', '50', '109.000', '85.000', '1');
INSERT INTO `bookstore`.`products` (`name`, `code`, `description`, `category_id`, `quantity`, `price`, `sale_price`, `enabled`) VALUES ('Su im lang cua bay cuu', 'BS002', 'Novel', '1', '30', '100.000', '100.000', '1');
INSERT INTO `bookstore`.`products` (`name`, `code`, `description`, `category_id`, `quantity`, `price`, `sale_price`, `enabled`) VALUES ('Hannibal', 'BS003', 'Novel', '1', '60', '120.000', '90.000', '1');
INSERT INTO `bookstore`.`products` (`name`, `code`, `description`, `category_id`, `quantity`, `price`, `sale_price`, `enabled`) VALUES ('Hannibal troi day', 'BS004', 'Novel', '1', '35', '138.000', '100.000', '1');

ALTER TABLE `bookstore`.`categories` 
ADD INDEX `fk_parent_id_idx` (`parent_id` ASC) VISIBLE;
;
ALTER TABLE `bookstore`.`categories` 
ADD CONSTRAINT `fk_parent_id`
  FOREIGN KEY (`parent_id`)
  REFERENCES `bookstore`.`categories` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
INSERT INTO `bookstore`.`categories` (`name`, `code`, `description`, `enabled`) VALUES ('Van hoc the gioi', 'VHTG', 'Sach nuoc ngoai', '1');
INSERT INTO `bookstore`.`categories` (`name`, `code`, `description`, `enabled`, `parent_id`) VALUES ('Tieu thuyet', 'TTNN', 'Tieu thuyet nuoc ngoai', '1', '1');
INSERT INTO `bookstore`.`categories` (`name`, `code`, `description`, `enabled`, `parent_id`) VALUES ('Sach thieu nhi ', 'TNNN', 'Sach thieu nhi nuoc ngoai', '1', '1');

ALTER TABLE `bookstore`.`users` 
ADD COLUMN `avatar` VARCHAR(45) NULL DEFAULT NULL AFTER `password`;

ALTER TABLE `bookstore`.`customers` 
CHANGE COLUMN `last_login` `last_login` DATETIME NULL DEFAULT NULL ;

ALTER TABLE `bookstore`.`customers` 
CHANGE COLUMN `rank` `customer_rank` VARCHAR(45) NULL DEFAULT NULL ;
