/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50619
Source Host           : localhost:3306
Source Database       : ssh

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2015-08-26 20:13:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appdownloadinfo
-- ----------------------------
DROP TABLE IF EXISTS `appdownloadinfo`;
CREATE TABLE `appdownloadinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(11) DEFAULT NULL,
  `oldappvid` int(11) DEFAULT NULL,
  `appvid` int(11) DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `imei` varchar(64) DEFAULT NULL,
  `ipaddr` varchar(64) DEFAULT NULL,
  `macaddr` varchar(64) DEFAULT NULL,
  `clientinfo` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for appinfo
-- ----------------------------
DROP TABLE IF EXISTS `appinfo`;
CREATE TABLE `appinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appname` varchar(128) DEFAULT NULL,
  `appdesc` longtext,
  `createdate` datetime DEFAULT NULL,
  `newestappvid` int(11) DEFAULT NULL,
  `applogo` varchar(128) DEFAULT NULL,
  `applogomd5` varchar(255) DEFAULT NULL,
  `barcode` varchar(128) DEFAULT NULL,
  `barcodemd5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for appversioninfo
-- ----------------------------
DROP TABLE IF EXISTS `appversioninfo`;
CREATE TABLE `appversioninfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appid` int(11) DEFAULT NULL,
  `versioncode` int(11) DEFAULT NULL,
  `versionname` varchar(128) DEFAULT NULL,
  `updatelog` longtext,
  `updatedate` datetime DEFAULT NULL,
  `respath` varchar(255) DEFAULT NULL,
  `resmd5` varchar(255) DEFAULT NULL,
  `updatetype` int(11) DEFAULT NULL,
  `downloadpath` varchar(255) DEFAULT NULL,
  `autoinstall` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
