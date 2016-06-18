-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: disertatie_db
-- ------------------------------------------------------
-- Server version	5.6.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_nume` varchar(30) NOT NULL,
  `user_prenume` varchar(30) NOT NULL,
  `user_telefon` varchar(10) NOT NULL,
  `user_statut` varchar(20) NOT NULL,
  `user_userlog_id` int(11) NOT NULL,
  `user_hub_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_cnp_UNIQUE` (`user_telefon`),
  KEY `fk_user_userlog_idx` (`user_userlog_id`),
  KEY `fk_user_hub1_idx` (`user_hub_id`),
  CONSTRAINT `fk_user_hub1` FOREIGN KEY (`user_hub_id`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_userlog` FOREIGN KEY (`user_userlog_id`) REFERENCES `userlog` (`userlog_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Chirvase','Liviu','1111','operator',1,1),(2,'Chirvase','George','2222','curier',2,1),(3,'Chirvase','Alex','3333','curier',3,1),(4,'Popa','Horia','4444','operator',4,2),(5,'Popa','Gigel','5555','curier',5,2),(6,'Popa','Claudiu','6666','curier',6,2),(7,'Popescu','Razvan','7777','operator',7,3),(8,'Popescu','Alin','8888','curier',8,3),(9,'Popescu','Bogdan','9999','curier',9,3),(10,'Ionescu','Sorin','1234','operator',10,4),(11,'Ionescu','Catalin','4365','curier',11,4),(12,'Ionescu','Mihai','9876','curier',12,4);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-18 12:15:28
