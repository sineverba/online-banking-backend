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
(12,	11.11,	'Insurance KKW',	'2022-02-24 19:22:51.889850'),
(13,	7.77,	'Global IJDJHF',	'2022-02-25 07:23:51.240445'),
(14,	2.22,	'National UUIIOO',	'2022-02-25 14:16:37.200504'),
(15,	-202.00,	'Medical Insurance',	'2022-02-25 14:16:54.457176'),
(16,	8.88,	'Daily bank interest',	'2022-02-26 07:43:23.856498'),
(17,	7.77,	'Stake 5HHIG',	'2022-02-27 06:38:28.489241'),
(18,	7.77,	'Stake 5HHIG',	'2022-02-27 09:10:18.689972'),
(19,	5.55,	'Stake 5HHIG',	'2022-02-27 09:10:37.382327'),
(20,	7.77,	NULL,	'2022-02-27 09:11:08.641589'),
(21,	7.77,	'Stake 5HHIG',	'2022-02-27 09:11:14.881241'),
(22,	7.77,	'Stake 5HHIG',	'2022-02-27 09:24:34.068538'),
(23,	7.77,	'Stake 5HHIG',	'2022-02-27 09:38:19.242984'),
(28,	4.44,	'Stake 5HHIG',	'2022-03-03 18:24:12.090851'),
(29,	-20.00,	'Pet Shop',	'2022-03-03 18:24:25.996135'),
(30,	7.77,	'Stake 5HHIG',	'2022-03-05 08:24:55.557341'),
(31,	-12.34,	'Supermarket',	'2022-03-05 08:25:18.134922'),
(36,	-100.00,	'Amazon Gift Card',	'2022-03-06 13:32:59.185699'),
(37,	11.11,	'Daily interest AGGF',	'2022-03-18 18:44:01.291086'),
(38,	-22.00,	'Cake and biscuits',	'2022-03-18 18:44:23.839985'),
(39,	100.00,	'Gift from grandPa and Grand ma!',	'2022-03-20 09:48:39.506947'),
(40,	7.77,	'Compound interest A7A7A8',	'2022-03-20 09:59:11.950031'),
(41,	-80.77,	'Xmas Party',	'2022-03-20 09:59:43.535285'),
(42,	5.55,	'Daily Interest AAJGHG',	'2022-03-22 20:58:46.573314'),
(43,	-10.00,	'Phone Mobile recharge',	'2022-03-22 20:59:02.905769'),
(44,	2500.00,	'April Salary',	'2022-03-25 20:45:14.839527'),
(45,	-4.44,	'Bar Happy',	'2022-03-25 20:45:17.555240'),
(46,	7.77,	'Daily interest 5.55',	'2022-03-26 07:56:42.588727'),
(47,	-50.00,	'Gasoline Station 144th Street',	'2022-03-26 07:57:33.629479');

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `roles` (`id`, `role`) VALUES
(1,	'ROLE_ADMIN'),
(2,	'ROLE_CUSTOMER');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `password` varchar(256) DEFAULT NULL,
  `username` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users` (`id`, `password`, `username`) VALUES
(1,	'$2a$10$SLZnEcosM46bVHrEmMS9euPJNKB/e3K/ITA0tOV8actbZsA4yEAkW',	'112233');

DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `users_id` int unsigned NOT NULL,
  `roles_id` int unsigned NOT NULL,
  PRIMARY KEY (`users_id`,`roles_id`),
  KEY `FKa62j07k5mhgifpp955h37ponj` (`roles_id`),
  CONSTRAINT `FKa62j07k5mhgifpp955h37ponj` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKml90kef4w2jy7oxyqv742tsfc` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `users_roles` (`users_id`, `roles_id`) VALUES
(1,	2);

-- 2022-03-26 09:48:06
