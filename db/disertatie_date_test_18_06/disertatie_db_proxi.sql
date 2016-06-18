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
-- Table structure for table `proxi`
--

DROP TABLE IF EXISTS `proxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proxi` (
  `proxi_id` int(11) NOT NULL,
  `proxi_hub_id_plecare` int(11) NOT NULL,
  `proxi_hub_id_sosire` int(11) NOT NULL,
  `proxi_next` int(11) NOT NULL,
  PRIMARY KEY (`proxi_id`),
  UNIQUE KEY `UNIQUE` (`proxi_hub_id_plecare`,`proxi_hub_id_sosire`),
  KEY `fk_hub_has_hub_hub2_idx` (`proxi_hub_id_sosire`),
  KEY `fk_hub_has_hub_hub1_idx` (`proxi_hub_id_plecare`),
  CONSTRAINT `fk_hub_has_hub_hub1` FOREIGN KEY (`proxi_hub_id_plecare`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_hub_has_hub_hub2` FOREIGN KEY (`proxi_hub_id_sosire`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proxi`
--

LOCK TABLES `proxi` WRITE;
/*!40000 ALTER TABLE `proxi` DISABLE KEYS */;
INSERT INTO `proxi` VALUES (1,1,2,2),(2,1,3,2),(3,1,4,4),(4,2,1,1),(5,2,3,3),(6,2,4,1),(7,3,1,2),(8,3,2,2),(9,3,4,2),(10,4,1,1),(11,4,2,1),(12,4,3,1);
/*!40000 ALTER TABLE `proxi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-18 12:15:27
