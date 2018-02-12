-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 12, 2018 at 02:02 PM
-- Server version: 5.7.21-0ubuntu0.16.04.1
-- PHP Version: 7.0.25-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `workshop`
--

-- --------------------------------------------------------

--
-- Table structure for table `exercise`
--

DROP TABLE IF EXISTS `exercise`;
CREATE TABLE IF NOT EXISTS `exercise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `exercise`
--

INSERT INTO `exercise` (`id`, `title`, `description`) VALUES
(1, 'zadanie 1', 'rozwiazanie 1+1 =2 '),
(2, 'zadanie 2', 'rozwiazanie 1*1 =1 '),
(3, 'zadanie 3', 'rozwiazanie 1-1 =0 '),
(4, 'zadanie 4', 'rozwiazanie 1/1 =1 ');

-- --------------------------------------------------------

--
-- Table structure for table `solution`
--

DROP TABLE IF EXISTS `solution`;
CREATE TABLE IF NOT EXISTS `solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `description` text,
  `exercise_id` int(11) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `users_id` (`users_id`),
  KEY `excercise_id` (`exercise_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `solution`
--

INSERT INTO `solution` (`id`, `created`, `updated`, `description`, `exercise_id`, `users_id`) VALUES
(1, '2018-02-12 06:17:21', '2018-02-12 13:57:02', 'testowy opis do zmiany', 1, 1),
(2, '2018-02-12 06:17:21', '2018-02-12 13:57:02', 'TEST TEST TEST', 1, 1),
(3, '2018-02-11 05:17:21', '2018-02-11 15:29:33', 'opissss', 1, 5),
(4, '2018-02-12 13:17:40', NULL, 'opiss', 2, 2),
(5, '2018-02-12 13:32:19', NULL, 'opiss', 4, 4),
(6, '2018-02-12 13:39:55', NULL, 'opiss', 2, 3),
(7, '2018-02-12 13:39:55', NULL, 'opiss', 4, 5),
(8, '2018-02-12 13:39:55', NULL, 'opissssssssssdsadasdasdas', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
CREATE TABLE IF NOT EXISTS `Users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(60) NOT NULL,
  `person_group_id` int(11) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `toeknExpire` varchar(255) DEFAULT NULL,
  `loginTries` int(1) DEFAULT NULL,
  `lasLoginDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `person_group_id` (`person_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `email`, `username`, `password`, `person_group_id`, `token`, `toeknExpire`, `loginTries`, `lasLoginDate`) VALUES
(1, 'email@wp.pl', 'imieLogin', '$2a$10$jrhZ6X05mXZt4H7UvWRRPusjYjGCFy0BTtEKiUlhWvHW0ceBaYN3S', NULL, NULL, NULL, NULL, NULL),
(2, 'blabla@blabla.pl', 'imie2Login', '$2a$10$35u06skt6furEayEpNAZye8iutdL40Ail96eXAbOQ95lsA6UA2o42', NULL, NULL, NULL, NULL, NULL),
(3, '2ema2il@wp.pl', 'imi1e2Login', '$2a$10$CisXdLSFiXBrIzWNazbifO7suakO9ztnFLkSs3oECJ1k4TbuPaK/u', NULL, NULL, NULL, NULL, NULL),
(4, 'email@w.p.pl', 'imi1e2Login', '$2a$10$k31IXDcSBAXDdISlMIKtG.fvGMAwJYKXEhoWT4FqDksYKVgQISt9O', NULL, NULL, NULL, NULL, NULL),
(5, 'email@w2p.pl', 'imie i login', '$2a$10$7kwjl9.bGu3I42MvW/YC8OnFtjdSqy25b9vL0vwwTpAh/n.htwUN.', NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE IF NOT EXISTS `user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_group`
--

INSERT INTO `user_group` (`id`, `name`) VALUES
(1, 'TESTERZY'),
(2, 'OOP OOP'),
(3, 'MANULA AND AUTOMATIC TESTERS'),
(4, 'grupa3 python'),
(5, 'grupa4 bash perl etc'),
(6, 'grupa0 mysql and other DB'),
(7, 'grupa1 php'),
(8, 'grupa2 java'),
(9, 'grupa3 python');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `solution`
--
ALTER TABLE `solution`
  ADD CONSTRAINT `solution_ibfk_1` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`),
  ADD CONSTRAINT `solution_ibfk_2` FOREIGN KEY (`users_id`) REFERENCES `Users` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
