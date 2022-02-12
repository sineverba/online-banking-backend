-- Adminer 4.8.1 MySQL 8.0.28 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

DROP TABLE IF EXISTS `bank_account_transactions`;
CREATE TABLE `bank_account_transactions` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `amount` decimal(11,2) DEFAULT NULL,
  `purpose` varchar(256) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `bank_account_transactions` (`id`, `amount`, `purpose`, `transaction_date`) VALUES
(1,	2500.00,	'January 2022 salary',	'2022-02-06 16:49:03.000000'),
(2,	14.74,	'Daily Interest - CED 006',	'2022-02-12 09:12:32.717240'),
(3,	11.74,	'Daily Interest - CED 007',	'2022-02-12 17:26:14.744998'),
(4,	7.31,	'Daily Interest - CED 009',	'2022-02-12 20:59:06.709249');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(256) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 2022-02-12 20:59:36
