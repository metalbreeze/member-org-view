# MySQL-Front 5.1  (Build 4.2)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


# Host: 127.0.0.1    Database: springmvchibernate
# ------------------------------------------------------
# Server version 5.1.72-community

DROP DATABASE IF EXISTS `springmvchibernate`;
CREATE DATABASE `springmvchibernate` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `springmvchibernate`;

#
# Source for table group1
#

DROP TABLE IF EXISTS `group1`;
CREATE TABLE `group1` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `createDate` date DEFAULT NULL,
  `node_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

#
# Dumping data for table group1
#

LOCK TABLES `group1` WRITE;
/*!40000 ALTER TABLE `group1` DISABLE KEYS */;
INSERT INTO `group1` VALUES (1,'组1212312','2017-01-31',1);
INSERT INTO `group1` VALUES (2,'组aad','2017-01-31',7);
INSERT INTO `group1` VALUES (3,'组12312','2017-01-31',NULL);
INSERT INTO `group1` VALUES (4,'组12','2017-01-31',NULL);
INSERT INTO `group1` VALUES (5,'123','2017-02-01',NULL);
INSERT INTO `group1` VALUES (6,'2017-02-03','2017-02-03',NULL);
/*!40000 ALTER TABLE `group1` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table group1_user
#

DROP TABLE IF EXISTS `group1_user`;
CREATE TABLE `group1_user` (
  `user_id` int(11) unsigned NOT NULL,
  `group_id` int(11) unsigned NOT NULL,
  `level` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Dumping data for table group1_user
#

LOCK TABLES `group1_user` WRITE;
/*!40000 ALTER TABLE `group1_user` DISABLE KEYS */;
INSERT INTO `group1_user` VALUES (1,1,1);
INSERT INTO `group1_user` VALUES (2,1,2);
/*!40000 ALTER TABLE `group1_user` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table node
#

DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) unsigned DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

#
# Dumping data for table node
#

LOCK TABLES `node` WRITE;
/*!40000 ALTER TABLE `node` DISABLE KEYS */;
INSERT INTO `node` VALUES (1,NULL,1);
INSERT INTO `node` VALUES (2,1,2);
INSERT INTO `node` VALUES (3,1,3);
INSERT INTO `node` VALUES (4,2,4);
INSERT INTO `node` VALUES (5,2,5);
INSERT INTO `node` VALUES (6,3,6);
INSERT INTO `node` VALUES (7,NULL,7);
INSERT INTO `node` VALUES (8,7,8);
/*!40000 ALTER TABLE `node` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table person
#

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `country` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Dumping data for table person
#

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'123123','123123');
INSERT INTO `person` VALUES (2,'张三','张三');
INSERT INTO `person` VALUES (3,'æ??ç??','ä½ å¥½');
INSERT INTO `person` VALUES (4,'æ??ç??','æ??ç??');
INSERT INTO `person` VALUES (5,'æ??ç??','æ??ç??');
INSERT INTO `person` VALUES (6,'æ??','æ??');
INSERT INTO `person` VALUES (7,'?','?');
INSERT INTO `person` VALUES (8,'?','');
INSERT INTO `person` VALUES (9,'我','我');
INSERT INTO `person` VALUES (10,'æ','æ');
INSERT INTO `person` VALUES (11,'å¤ª','å¤ªå¤ªå¤ª');
INSERT INTO `person` VALUES (12,'我','我');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

#
# Source for table user
#

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '',
  `mobile` varchar(30) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

#
# Dumping data for table user
#

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'张3123','123123',NULL,1);
INSERT INTO `user` VALUES (2,'人名','123123',NULL,1);
INSERT INTO `user` VALUES (3,'张三','1231',NULL,1);
INSERT INTO `user` VALUES (4,'王',NULL,NULL,1);
INSERT INTO `user` VALUES (5,'李',NULL,NULL,1);
INSERT INTO `user` VALUES (6,'张4',NULL,NULL,1);
INSERT INTO `user` VALUES (7,'张5',NULL,NULL,1);
INSERT INTO `user` VALUES (8,'张7',NULL,NULL,1);
INSERT INTO `user` VALUES (9,'张9',NULL,NULL,1);
INSERT INTO `user` VALUES (10,'张10',NULL,NULL,1);
INSERT INTO `user` VALUES (11,'张3123','1865',NULL,1);
INSERT INTO `user` VALUES (12,'张3123','张3123',NULL,1);
INSERT INTO `user` VALUES (13,'张3123','张3123',NULL,1);
INSERT INTO `user` VALUES (14,'张3123','张3123',NULL,1);
INSERT INTO `user` VALUES (15,'张3123','张3123',NULL,1);
INSERT INTO `user` VALUES (16,'张3123','张3123',NULL,1);
INSERT INTO `user` VALUES (17,'我','我',NULL,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
