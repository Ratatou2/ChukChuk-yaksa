-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: chukchuk_db
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_pill_effect`
--

DROP TABLE IF EXISTS `user_pill_effect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_pill_effect` (
  `user_pill_effect_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `pill_id` bigint DEFAULT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_date` datetime DEFAULT NULL,
  `is_delete` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_pill_effect_id`),
  KEY `FK_user_pill_effect_pill_id` (`pill_id`),
  KEY `FK_user_pill_effect_user_id` (`user_id`),
  KEY `FK_user_pill_effect_category_id` (`category_id`),
  CONSTRAINT `FK_user_pill_effect_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `FK_user_pill_effect_pill_id` FOREIGN KEY (`pill_id`) REFERENCES `pill` (`pill_id`),
  CONSTRAINT `FK_user_pill_effect_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_pill_effect`
--

LOCK TABLES `user_pill_effect` WRITE;
/*!40000 ALTER TABLE `user_pill_effect` DISABLE KEYS */;
INSERT INTO `user_pill_effect` VALUES (1,1,1,1,'Sample memo 1','2024-03-19 15:40:41',NULL,0),(2,2,1,2,'Sample memo 2','2024-03-19 15:40:41',NULL,0),(3,3,2,3,'Sample memo 3','2024-03-19 15:40:41',NULL,0),(4,1,2,4,'Sample memo 4','2024-03-19 15:40:41',NULL,0),(5,2,3,5,'Sample memo 5','2024-03-19 15:40:41',NULL,0),(6,3,3,6,'Sample memo 6','2024-03-19 15:40:41',NULL,0),(7,1,4,7,'Sample memo 7','2024-03-19 15:40:41',NULL,0),(8,2,4,8,'Sample memo 8','2024-03-19 15:40:41',NULL,0),(9,3,5,9,'Sample memo 9','2024-03-19 15:40:41',NULL,0),(10,1,5,10,'Sample memo 10','2024-03-19 15:40:41',NULL,0);
/*!40000 ALTER TABLE `user_pill_effect` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-19 15:46:46
