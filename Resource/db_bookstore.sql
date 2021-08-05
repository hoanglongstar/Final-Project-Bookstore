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
INSERT INTO `bookstore`.`user_roles` (`user_id`, `role_id`) VALUES ('3', '2');
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

CREATE TABLE `bookstore`.`persistent_logins` (
  `series` VARCHAR(64) NOT NULL,
  `username` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL,
  PRIMARY KEY (`series`));

ALTER TABLE `bookstore`.`products` 
ADD INDEX `fk_category_id_idx` (`category_id` ASC) VISIBLE;
;
ALTER TABLE `bookstore`.`products` 
ADD CONSTRAINT `fk_category_id`
  FOREIGN KEY (`category_id`)
  REFERENCES `bookstore`.`categories` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
CREATE TABLE `bookstore`.`invoice` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NOT NULL,
  `order_date` DATETIME NULL,
  `total_payable` DOUBLE NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
    
CREATE TABLE `bookstore`.`invoice_details` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `item_total` DOUBLE NOT NULL,
  PRIMARY KEY (`id`));
    
ALTER TABLE `bookstore`.`customers` 
DROP COLUMN `address`;

CREATE TABLE `bookstore`.`address` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NULL,
  `district` VARCHAR(45) NULL,
  `ward` VARCHAR(45) NULL,
  `street` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
INSERT INTO `bookstore`.`customers` (`email`, `password`, `first_name`, `last_name`, `phone_number`) VALUES ('user1@gmail.com', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG', 'new', 'user', '0123456789');
INSERT INTO `bookstore`.`customers` (`email`, `password`, `first_name`, `last_name`, `phone_number`) VALUES ('user2', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG', 'super', 'user', '0987654321');
INSERT INTO `bookstore`.`customers` (`email`, `password`, `first_name`, `last_name`, `phone_number`) VALUES ('user3', '$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG', 'test', 'account', '0546231879');

ALTER TABLE `bookstore`.`address` 
ADD COLUMN `customer_id` INT NOT NULL AFTER `street`,
ADD INDEX `fk_address_id_idx` (`customer_id` ASC) VISIBLE;

ALTER TABLE `bookstore`.`address` 
	ADD CONSTRAINT `fk_address_id`
  FOREIGN KEY (`customer_id`)
  REFERENCES `bookstore`.`customers` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

INSERT INTO `bookstore`.`address` (`city`, `district`, `ward`, `street`, `customer_id`) VALUES ('HCM', '1', 'da kao', 'nguyen trai', '2');
INSERT INTO `bookstore`.`address` (`city`, `district`, `ward`, `street`, `customer_id`) VALUES ('HCM', '3', '13', 'le van sy', '1');
INSERT INTO `bookstore`.`address` (`city`, `district`, `ward`, `street`, `customer_id`) VALUES ('HCM', '5', '6', 'nguyen van cu', '3');
INSERT INTO `bookstore`.`address` (`city`, `district`, `ward`, `street`, `customer_id`) VALUES ('HCM', 'Tan Binh', '12', 'Truong Chinh', '2');

ALTER TABLE `bookstore`.`roles` 
ADD COLUMN `description` VARCHAR(256) NULL AFTER `role_name`;

ALTER TABLE `bookstore`.`users` 
ADD COLUMN `address` VARCHAR(256) NULL AFTER `enabled`,
ADD COLUMN `phone_number` VARCHAR(45) NULL AFTER `address`,
ADD COLUMN `date_of_birth` DATE NULL AFTER `phone_number`,
ADD COLUMN `identity_number` VARCHAR(45) NULL AFTER `date_of_birth`;

INSERT INTO `bookstore`.`invoice` (`code`, `order_date`, `total_payable`, `status`, `customer_invoice_id`) VALUES ('DH0011', '2021-08-04 15:22:30', '220', 'PROCESSING', '1');
INSERT INTO `bookstore`.`invoice` (`code`, `order_date`, `total_payable`, `status`, `customer_invoice_id`) VALUES ('DH002', '2021-08-04 16:00:01', '229', 'DELIVERING', '2');

INSERT INTO `bookstore`.`invoice_details` (`quantity`, `item_total`, `invoice_detail_id`, `product_id`) VALUES ('1', '100', '1', '2');
INSERT INTO `bookstore`.`invoice_details` (`quantity`, `item_total`, `invoice_detail_id`, `product_id`) VALUES ('1', '120', '1', '3');
INSERT INTO `bookstore`.`invoice_details` (`quantity`, `item_total`, `invoice_detail_id`, `product_id`) VALUES ('1', '109', '2', '1');
INSERT INTO `bookstore`.`invoice_details` (`quantity`, `item_total`, `invoice_detail_id`, `product_id`) VALUES ('1', '120', '1', '3');

ALTER TABLE `bookstore`.`customers` 
ADD COLUMN `date_of_birth` DATE NULL DEFAULT NULL AFTER `last_name`;