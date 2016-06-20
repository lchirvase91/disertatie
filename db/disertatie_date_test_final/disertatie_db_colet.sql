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
-- Table structure for table `colet`
--

DROP TABLE IF EXISTS `colet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colet` (
  `colet_id` int(11) NOT NULL AUTO_INCREMENT,
  `colet_awb` bigint(20) DEFAULT NULL,
  `colet_status` varchar(30) NOT NULL,
  `colet_comanda_id` int(11) NOT NULL,
  PRIMARY KEY (`colet_id`),
  UNIQUE KEY `colet_awb_UNIQUE` (`colet_awb`),
  KEY `fk_colet_comanda_idx` (`colet_comanda_id`),
  CONSTRAINT `fk_colet_comanda` FOREIGN KEY (`colet_comanda_id`) REFERENCES `comanda` (`comanda_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colet`
--

LOCK TABLES `colet` WRITE;
/*!40000 ALTER TABLE `colet` DISABLE KEYS */;
INSERT INTO `colet` VALUES (1,NULL,'nepreluat',1),(2,NULL,'nepreluat',2),(3,NULL,'nepreluat',2),(4,NULL,'nepreluat',2),(5,NULL,'nepreluat',3),(6,NULL,'nepreluat',4),(7,NULL,'nepreluat',5),(8,NULL,'nepreluat',6);
/*!40000 ALTER TABLE `colet` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-20 12:45:25
