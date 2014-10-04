-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 16, 2014 at 12:44 PM
-- Server version: 5.1.41
-- PHP Version: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `nlp_db`
--
CREATE DATABASE `nlp_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `nlp_db`;

-- --------------------------------------------------------

--
-- Table structure for table `managers`
--

CREATE TABLE IF NOT EXISTS `managers` (
  `id` int(31) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `managers`
--


-- --------------------------------------------------------

--
-- Table structure for table `match_events`
--

CREATE TABLE IF NOT EXISTS `match_events` (
  `id` int(31) NOT NULL AUTO_INCREMENT,
  `match_id` int(31) NOT NULL,
  `team_id` int(31) NOT NULL,
  `player_id` int(31) NOT NULL,
  `event_time` decimal(10,2) NOT NULL,
  `event_type` varchar(128) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `match_events`
--

INSERT INTO `match_events` (`id`, `match_id`, `team_id`, `player_id`, `event_time`, `event_type`, `created`, `modified`) VALUES
(1, 1, 1, 1, '2.00', 'freekick', '2014-04-08 04:42:03', '2014-04-08 04:42:03'),
(2, 1, 1, 1, '10.52', 'freekick', '2014-04-08 04:42:17', '2014-04-08 04:42:17'),
(3, 1, 1, 1, '15.00', 'freekick', '2014-04-08 04:42:19', '2014-04-08 04:42:19'),
(4, 1, 1, 1, '45.00', 'freekick', '2014-04-08 04:42:20', '2014-04-08 04:42:20'),
(5, 1, 1, 1, '63.40', 'freekick', '2014-04-09 14:35:57', '2014-04-09 14:35:57'),
(6, 1, 1, 1, '50.50', 'goal', '2014-04-11 16:55:36', '2014-04-11 16:55:36'),
(7, 1, 1, 1, '79.00', 'goal', '2014-04-11 16:55:57', '2014-04-11 16:55:57'),
(8, 1, 1, 1, '70.00', 'freekick', '2014-04-11 16:56:19', '2014-04-11 16:56:19'),
(9, 1, 2, 1, '71.00', 'freekick', '2014-04-11 16:57:09', '2014-04-11 16:57:09'),
(10, 1, 2, 1, '89.99', 'freekick', '2014-04-13 14:36:48', '2014-04-13 14:36:48');

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE IF NOT EXISTS `matches` (
  `id` int(31) NOT NULL AUTO_INCREMENT,
  `match_time` datetime DEFAULT NULL,
  `venue` varchar(512) DEFAULT NULL,
  `home_team_id` int(31) NOT NULL,
  `visiting_team_id` int(31) NOT NULL,
  `home_team_goals` int(8) NOT NULL DEFAULT '0',
  `home_team_free_kicks` int(8) NOT NULL DEFAULT '0',
  `home_team_penalties` int(8) NOT NULL DEFAULT '0',
  `home_team_total_shots` int(8) NOT NULL DEFAULT '0',
  `home_team_shots_on_target` int(8) NOT NULL DEFAULT '0',
  `home_team_shots_off_target` int(8) NOT NULL DEFAULT '0',
  `home_team_corners` int(8) NOT NULL DEFAULT '0',
  `home_team_offsides` int(8) NOT NULL DEFAULT '0',
  `home_team_fouls` int(8) NOT NULL DEFAULT '0',
  `home_team_yellow_cards` int(8) NOT NULL DEFAULT '0',
  `home_team_red_cards` int(8) NOT NULL DEFAULT '0',
  `home_team_crosses` int(8) NOT NULL DEFAULT '0',
  `visiting_team_goals` int(8) NOT NULL DEFAULT '0',
  `visiting_team_red_cards` int(8) NOT NULL DEFAULT '0',
  `referee_name` varchar(512) DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `visiting_team_free_kicks` int(8) NOT NULL DEFAULT '0',
  `visiting_team_penalties` int(8) NOT NULL DEFAULT '0',
  `visiting_team_total_shots` int(8) NOT NULL DEFAULT '0',
  `visiting_team_shots_on_target` int(8) NOT NULL DEFAULT '0',
  `visiting_team_shots_off_target` int(8) NOT NULL DEFAULT '0',
  `visiting_team_corners` int(8) NOT NULL DEFAULT '0',
  `visiting_team_crosses` int(8) NOT NULL DEFAULT '0',
  `visiting_team_offsides` int(8) NOT NULL DEFAULT '0',
  `visiting_team_fouls` int(8) NOT NULL DEFAULT '0',
  `visiting_team_yellow_cards` int(8) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`id`, `match_time`, `venue`, `home_team_id`, `visiting_team_id`, `home_team_goals`, `home_team_free_kicks`, `home_team_penalties`, `home_team_total_shots`, `home_team_shots_on_target`, `home_team_shots_off_target`, `home_team_corners`, `home_team_offsides`, `home_team_fouls`, `home_team_yellow_cards`, `home_team_red_cards`, `home_team_crosses`, `visiting_team_goals`, `visiting_team_red_cards`, `referee_name`, `created`, `modified`, `visiting_team_free_kicks`, `visiting_team_penalties`, `visiting_team_total_shots`, `visiting_team_shots_on_target`, `visiting_team_shots_off_target`, `visiting_team_corners`, `visiting_team_crosses`, `visiting_team_offsides`, `visiting_team_fouls`, `visiting_team_yellow_cards`) VALUES
(1, '2014-04-07 20:00:00', 'Emirates stadium', 1, 2, 14, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, NULL, '2014-04-09 14:41:05', '2014-04-09 14:41:12', 2, 0, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

CREATE TABLE IF NOT EXISTS `players` (
  `id` int(31) NOT NULL AUTO_INCREMENT,
  `name` varchar(512) NOT NULL,
  `height` decimal(10,0) DEFAULT NULL,
  `weight` decimal(10,0) DEFAULT NULL,
  `country_of_birth` varchar(256) DEFAULT NULL,
  `national_team` varchar(256) DEFAULT NULL,
  `appearances` int(10) DEFAULT NULL,
  `titles_won` int(10) DEFAULT NULL,
  `goals` int(10) DEFAULT NULL,
  `yellow_cards` int(10) DEFAULT NULL,
  `red_cards` int(10) DEFAULT NULL,
  `team_id` int(31) NOT NULL,
  `dob` date DEFAULT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  `match_id` int(31) NOT NULL,
  `position` varchar(256) NOT NULL,
  `is_substitute` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `players`
--


-- --------------------------------------------------------

--
-- Table structure for table `teams`
--

CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(31) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `position` int(8) NOT NULL,
  `wins` int(8) NOT NULL,
  `draws` int(8) NOT NULL,
  `lost` int(8) NOT NULL,
  `goals_for` int(8) NOT NULL,
  `goals_against` int(8) NOT NULL,
  `goals_difference` int(8) NOT NULL,
  `points` int(8) NOT NULL,
  `manager_id` int(8) NOT NULL,
  `created` datetime NOT NULL,
  `modified` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `teams`
--

INSERT INTO `teams` (`id`, `name`, `position`, `wins`, `draws`, `lost`, `goals_for`, `goals_against`, `goals_difference`, `points`, `manager_id`, `created`, `modified`) VALUES
(1, 'Bayern Munich', 5, 12, 2, 4, 20, 16, 12, 24, 12, '2014-04-09 15:08:35', '2014-04-09 15:08:41'),
(2, 'Barcelona', 2, 20, 10, 2, 30, 19, 15, 32, 2, '2014-04-09 15:09:15', '2014-04-09 15:09:22');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
