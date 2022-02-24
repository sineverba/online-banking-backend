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
(4,	7.31,	'Daily Interest - CED 009',	'2022-02-12 20:59:06.709249'),
(5,	7.77,	'Daily FF ZHGFYT',	'2022-02-19 15:00:50.424322'),
(6,	-11.00,	'Pizza Domino',	'2022-02-19 15:00:56.463711'),
(7,	4.44,	'Daily Learn - GPKI8J',	'2022-02-22 19:40:07.895071'),
(8,	-12.00,	'Bar Tower',	'2022-02-22 19:40:18.831178'),
(9,	-30.00,	'Italian Tavern - Spaghetti \'n\' Mandolino',	'2022-02-23 19:25:24.386038'),
(10,	250.00,	'Monthly Interest',	'2022-02-23 19:25:39.993603'),
(11,	4.44,	'Daily Loan HGGYRT5',	'2022-02-24 19:02:39.883321'),
(12,	11.11,	'Insurance KKW',	'2022-02-24 19:22:51.889850');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(256) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `password`, `username`) VALUES
(1,	'$2a$10$SLZnEcosM46bVHrEmMS9euPJNKB/e3K/ITA0tOV8actbZsA4yEAkW',	'112233');

-- 2022-02-24 19:27:55
