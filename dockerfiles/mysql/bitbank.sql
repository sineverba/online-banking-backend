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
(2,	-450.00,	'Arthur High School February 2022',	'2022-02-07 07:15:29.000000'),
(3,	100.00,	'Postman test',	'2022-02-08 11:49:59.245823'),
(4,	74.50,	'Postman test 2',	'2022-02-08 14:02:49.854420'),
(5,	7.40,	'Daily Interest - CED 002',	'2022-02-10 07:49:32.120245'),
(6,	4.40,	'Daily Interest - CED 003',	'2022-02-10 07:53:57.450064'),
(7,	2.20,	'Daily Interest - CED 004',	'2022-02-11 11:22:33.430064');

-- 2022-02-10 10:39:25
