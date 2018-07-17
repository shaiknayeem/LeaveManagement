--
-- Database: `leavemanagement_db`
--

CREATE DATABASE IF NOT EXISTS `leavemanagement_db`;
USE `leavemanagement_db`;


-- ENTITIES

--
-- Struttura della tabella `leave`
--

CREATE TABLE IF NOT EXISTS `leave` (
	`cancel` bool ,
	`fromDate` date ,
	`reason` varchar(40) ,
	`status` varchar(40) ,
	`toDate` date ,
	`type` varchar(40) ,
	
	-- RELAZIONI

	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);





--
-- Struttura della tabella `user`
--

CREATE TABLE IF NOT EXISTS `user` (
	`mail` varchar(40) ,
	`name` varchar(40) ,
	`password` varchar(40)  NOT NULL,
	`roles` varchar(40) ,
	`surname` varchar(40) ,
	`username` varchar(40)  NOT NULL,
	
	-- RELAZIONI
	`leave` int(11)  REFERENCES leave(_id),

	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);


-- Security

INSERT INTO `leavemanagement_db`.`user` (`username`, `password`, `_id`) VALUES ('admin', '1a1dc91c907325c69271ddf0c944bc72', 1);

CREATE TABLE IF NOT EXISTS `roles` (
	`role` varchar(30) ,
	
	-- RELAZIONI

	`_user` int(11)  NOT NULL REFERENCES user(_id),
	`_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT 

);
INSERT INTO `leavemanagement_db`.`roles` (`role`, `_user`, `_id`) VALUES ('ADMIN', '1', 1);






