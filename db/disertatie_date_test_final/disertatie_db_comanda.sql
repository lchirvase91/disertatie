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
-- Table structure for table `comanda`
--

DROP TABLE IF EXISTS `comanda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comanda` (
  `comanda_id` int(11) NOT NULL AUTO_INCREMENT,
  `comanda_nr_colete` int(11) NOT NULL,
  `comanda_greutate` int(11) NOT NULL,
  `comanda_data_comanda` date NOT NULL,
  `comanda_data_preluare` datetime DEFAULT NULL,
  `comanda_data_expediere` datetime DEFAULT NULL,
  `comanda_data_livrare` datetime DEFAULT NULL,
  `comanda_observatii` varchar(100) DEFAULT NULL,
  `comanda_asignare` int(11) DEFAULT NULL,
  `comanda_exp_id` int(11) NOT NULL,
  `comanda_dest_id` int(11) NOT NULL,
  PRIMARY KEY (`comanda_id`),
  KEY `fk_comanda_exp_idx` (`comanda_exp_id`),
  KEY `fk_comanda_dest_idx` (`comanda_dest_id`),
  CONSTRAINT `fk_comanda_dest` FOREIGN KEY (`comanda_dest_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comanda_exp` FOREIGN KEY (`comanda_exp_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comanda`
--

LOCK TABLES `comanda` WRITE;
/*!40000 ALTER TABLE `comanda` DISABLE KEYS */;
INSERT INTO `comanda` VALUES (1,1,2,'2016-05-20',NULL,NULL,NULL,NULL,NULL,1,3),(2,3,7,'2016-05-20',NULL,NULL,NULL,NULL,NULL,3,1),(3,1,5,'2016-05-20',NULL,NULL,NULL,NULL,NULL,1,3),(4,1,1,'2016-05-20',NULL,NULL,NULL,NULL,NULL,1,2),(5,2,4,'2016-05-20',NULL,NULL,NULL,NULL,NULL,2,1),(6,1,2,'2016-06-12',NULL,NULL,NULL,NULL,NULL,4,3);
/*!40000 ALTER TABLE `comanda` ENABLE KEYS */;
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
