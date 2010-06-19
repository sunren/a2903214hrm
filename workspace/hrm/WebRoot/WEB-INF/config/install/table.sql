-- MySQL dump 10.11
--
-- Host: localhost    Database: hr150
-- ------------------------------------------------------
-- Server version	5.0.67-community-nt

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
-- Table structure for table `attdmachine`
--
use hr;

DROP TABLE IF EXISTS `attdmachine`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attdmachine` (
  `mac_id` varchar(36) NOT NULL default '' COMMENT '考勤机UUID',
  `mac_no` varchar(36) NOT NULL default '',
  `mac_ip` varchar(64) default '',
  `mac_port` varchar(8) default '',
  `mac_password` int(10) default NULL,
  `mac_type` int(10) default NULL,
  `mac_status` int(10) default NULL,
  `mac_desc` varchar(128) default NULL,
  `mac_sort_id` int(10) default NULL,
  PRIMARY KEY  (`mac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attdmachine`
--

LOCK TABLES `attdmachine` WRITE;
/*!40000 ALTER TABLE `attdmachine` DISABLE KEYS */;
/*!40000 ALTER TABLE `attdmachine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attdoriginaldata`
--

DROP TABLE IF EXISTS `attdoriginaldata`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attdoriginaldata` (
  `aod_id` varchar(50) NOT NULL default '',
  `aod_emp_no` varchar(36) NOT NULL default '',
  `aod_attd_date` date NOT NULL default '0000-00-00',
  `aod_card_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `aod_ttd_machine_no` varchar(32) default NULL,
  `aod_status` int(10) unsigned NOT NULL default '0',
  `aod_last_modify_user` varchar(64) default NULL,
  `aod_last_modify_time` datetime default NULL,
  `aod_memo` varchar(255) default NULL,
  PRIMARY KEY  (`aod_id`),
  KEY `FK_aod_emp_no` (`aod_emp_no`),
  CONSTRAINT `FK_aod_emp_no` FOREIGN KEY (`aod_emp_no`) REFERENCES `employee` (`emp_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attdoriginaldata`
--

LOCK TABLES `attdoriginaldata` WRITE;
/*!40000 ALTER TABLE `attdoriginaldata` DISABLE KEYS */;
/*!40000 ALTER TABLE `attdoriginaldata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attdsyncrecord`
--

DROP TABLE IF EXISTS `attdsyncrecord`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attdsyncrecord` (
  `asr_id` varchar(36) NOT NULL default '',
  `asr_emp_no` varchar(36) NOT NULL default '',
  `asr_attd_machine_no` varchar(36) default '',
  `asr_emp_machine_no` int(10) default '0',
  `asr_emp_card_no` varchar(32) NOT NULL default '',
  `asr_sync` int(10) default NULL,
  PRIMARY KEY  (`asr_id`),
  KEY `FK_asr_emp_no` (`asr_emp_no`),
  KEY `FK_asr_machine_no` (`asr_attd_machine_no`),
  CONSTRAINT `FK_asr_emp_no` FOREIGN KEY (`asr_emp_no`) REFERENCES `employee` (`emp_no`),
  CONSTRAINT `FK_asr_machine_no` FOREIGN KEY (`asr_attd_machine_no`) REFERENCES `attdmachine` (`mac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attdsyncrecord`
--

LOCK TABLES `attdsyncrecord` WRITE;
/*!40000 ALTER TABLE `attdsyncrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `attdsyncrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendmonthly`
--

DROP TABLE IF EXISTS `attendmonthly`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attendmonthly` (
  `attm_id` varchar(36) NOT NULL default '',
  `attm_emp_id` varchar(36) NOT NULL default '',
  `attm_yearmonth` varchar(6) NOT NULL default '',
  `attm_dept` varchar(36) NOT NULL default '',
  `attm_location` varchar(36) NOT NULL default '',
  `attm_pb_no` varchar(36) default NULL,
  `attm_emptype` varchar(36) NOT NULL default '',
  `attm_year` varchar(4) NOT NULL default '',
  `attm_month` varchar(2) NOT NULL default '',
  `attm_duty_days` decimal(4,1) NOT NULL default '0.0',
  `attm_duty_hours` decimal(4,1) NOT NULL default '0.0',
  `attm_on_duty_days` decimal(4,1) NOT NULL default '0.0',
  `attm_on_duty_hours` decimal(4,1) NOT NULL default '0.0',
  `attm_off_duty_days` decimal(4,1) NOT NULL default '0.0',
  `attm_off_duty_hours` decimal(4,1) NOT NULL default '0.0',
  `attm_late_times` decimal(4,1) default NULL,
  `attm_early_leave` decimal(4,1) default NULL,
  `attm_absent_days` decimal(4,1) default NULL,
  `attm_absent_hours` decimal(4,1) default NULL,
  `attm_overtime_hours` decimal(4,1) default NULL,
  `attm_ot_normal_hours` decimal(4,1) default NULL,
  `attm_ot_weekend_hours` decimal(4,1) default NULL,
  `attm_ot_holiday_hours` decimal(4,1) default NULL,
  `attm_leave_days` decimal(4,1) default NULL,
  `attm_leave_hours` decimal(4,1) default NULL,
  `attm_leave_annual_days` decimal(4,1) default NULL,
  `attm_leave_annual_hours` decimal(4,1) default NULL,
  `attm_leave_tiaoxiu_days` decimal(4,1) default NULL,
  `attm_leave_tiaoxiu_hours` decimal(4,1) default NULL,
  `attm_leave_tiaoxiu01_days` decimal(4,1) default NULL,
  `attm_leave_tiaoxiu01_hours` decimal(4,1) default NULL,
  `attm_leave_casual_days` decimal(4,1) default NULL,
  `attm_leave_casual_hours` decimal(4,1) default NULL,
  `attm_leave_sick_days` decimal(4,1) default NULL,
  `attm_leave_sick_hours` decimal(4,1) default NULL,
  `attm_leave_sick01_days` decimal(4,1) default NULL,
  `attm_leave_sick01_hours` decimal(4,1) default NULL,
  `attm_leave_sick02_days` decimal(4,1) default NULL,
  `attm_leave_sick02_hours` decimal(4,1) default NULL,
  `attm_leave_wedding_days` decimal(4,1) default NULL,
  `attm_leave_wedding_hours` decimal(4,1) default NULL,
  `attm_leave_maternity_days` decimal(4,1) default NULL,
  `attm_leave_maternity_hours` decimal(4,1) default NULL,
  `attm_leave_travel_days` decimal(4,1) default NULL,
  `attm_leave_travel_hours` decimal(4,1) default NULL,
  `attm_leave_outer_days` decimal(4,1) default NULL,
  `attm_leave_outer_hours` decimal(4,1) default NULL,
  `attm_leave_other_days` decimal(4,1) default NULL,
  `attm_leave_other_hours` decimal(4,1) default NULL,
  `attm_field01` varchar(32) default NULL,
  `attm_field02` varchar(32) default NULL,
  `attm_field03` varchar(32) default NULL,
  `attm_field04` varchar(32) default NULL,
  `attm_field05` varchar(32) default NULL,
  `attm_field06` varchar(32) default NULL,
  `attm_field07` varchar(32) default NULL,
  `attm_field08` varchar(32) default NULL,
  `attm_field09` varchar(32) default NULL,
  `attm_field10` varchar(32) default NULL,
  `attm_field11` varchar(32) default NULL,
  `attm_field12` varchar(32) default NULL,
  `attm_field13` varchar(32) default NULL,
  `attm_field14` varchar(32) default NULL,
  `attm_field15` varchar(32) default NULL,
  `attm_field16` varchar(32) default NULL,
  `attm_field17` varchar(32) default NULL,
  `attm_field18` varchar(32) default NULL,
  `attm_field19` varchar(32) default NULL,
  `attm_field20` varchar(32) default NULL,
  `attm_field21` varchar(32) default NULL,
  `attm_field22` varchar(32) default NULL,
  `attm_field23` varchar(32) default NULL,
  `attm_field24` varchar(32) default NULL,
  `attm_comments` varchar(128) default NULL,
  PRIMARY KEY  (`attm_id`),
  KEY `FK_attm_emp_id` (`attm_emp_id`),
  KEY `FK_attm_dept` (`attm_dept`),
  KEY `FK_attm_location` (`attm_location`),
  KEY `FK_attm_emptype` (`attm_emptype`),
  KEY `FK_attm_pb_no` (`attm_pb_no`),
  CONSTRAINT `FK_attm_pb_no` FOREIGN KEY (`attm_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_attm_dept` FOREIGN KEY (`attm_dept`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_attm_emptype` FOREIGN KEY (`attm_emptype`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_attm_emp_id` FOREIGN KEY (`attm_emp_id`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_attm_location` FOREIGN KEY (`attm_location`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attendmonthly`
--

LOCK TABLES `attendmonthly` WRITE;
/*!40000 ALTER TABLE `attendmonthly` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendmonthly` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendperiod`
--

DROP TABLE IF EXISTS `attendperiod`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attendperiod` (
  `attp_id` varchar(36) NOT NULL default '',
  `attp_yearmonth` varchar(6) NOT NULL default '',
  `attp_year` varchar(4) NOT NULL default '',
  `attp_month` varchar(2) NOT NULL default '',
  `attp_status` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`attp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attendperiod`
--

LOCK TABLES `attendperiod` WRITE;
/*!40000 ALTER TABLE `attendperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendshift`
--

DROP TABLE IF EXISTS `attendshift`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attendshift` (
  `atts_id` varchar(36) NOT NULL default '',
  `atts_name` varchar(32) default NULL,
  `atts_desc` varchar(126) default NULL,
  `atts_night_shift` int(10) unsigned NOT NULL default '0',
  `atts_color` varchar(6) NOT NULL default '',
  `atts_short_name` varchar(2) default NULL,
  `atts_is_default` int(10) unsigned default NULL,
  `atts_sort_id` int(10) unsigned NOT NULL default '0',
  `atts_session` varchar(64) NOT NULL default '',
  `atts_working_hour` int(10) unsigned default NULL,
  `atts_status` int(10) unsigned NOT NULL default '1',
  `atts_stricked` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`atts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attendshift`
--

LOCK TABLES `attendshift` WRITE;
/*!40000 ALTER TABLE `attendshift` DISABLE KEYS */;
INSERT INTO `attendshift` VALUES ('05f03392-870a-42b5-bf89-2c9c95faa654','夜班','车间工人',1,'FF0000','晚',NULL,3,'18:00-32:00',14,1,0),('1af7fda1-a265-49bf-b036-98e25031eac4','常日班','办公室管理人员',0,'0066FF','常',1,4,'08:00-12:00,13:00-17:00',8,1,0),('23863f79-eb3f-44f9-80a9-d7192c2eeda5','中班','车间工人',0,'FF9900','中',NULL,2,'12:00-23:00',11,1,0),('835541c0-9263-40d8-bdb5-216858696ba0','早班','车间工人',0,'009900','早',NULL,1,'06:00-9:30,10:00-12:00,13:00-17:00',9,1,0);
/*!40000 ALTER TABLE `attendshift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendshiftareadept`
--

DROP TABLE IF EXISTS `attendshiftareadept`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attendshiftareadept` (
  `asad_id` varchar(36) NOT NULL,
  `asad_shift_id` varchar(36) NOT NULL,
  `asad_area_id` varchar(36) default NULL,
  `asad_dept_id` varchar(36) default NULL,
  PRIMARY KEY  (`asad_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attendshiftareadept`
--

LOCK TABLES `attendshiftareadept` WRITE;
/*!40000 ALTER TABLE `attendshiftareadept` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendshiftareadept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `authority` (
  `authority_id` int(10) unsigned NOT NULL default '0',
  `authority_module_no` varchar(6) NOT NULL default '',
  `authority_condition_no` varchar(16) NOT NULL default '',
  `authority_action_no` varchar(32) NOT NULL default '',
  `authority_desc` varchar(128) default NULL,
  PRIMARY KEY  (`authority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'101','2','0,1,2,3,6,7,8','员工管理专员(所有)'),(2,'101','3','0','员工管理HR经理'),(3,'111','1','0,2','本人员工资料可读'),(4,'111','2','0,2','下属员工资料可读'),(5,'111','3','0,2','所有员工资料可读'),(6,'101','1','','员工管理专员(分管部门或地区)'),(11,'201','2','','薪资专员(所有)'),(12,'201','3','','薪资HR经理'),(13,'211','1','','查看本人薪资'),(14,'211','2','','查看下属员工薪水'),(15,'211','3','','查看所有员工薪水'),(16,'201','1',' ','薪资专员(分管某部门或地区)'),(17,'231','','','薪资邮件通知'),(21,'301','1','','培训管理员'),(22,'301','2','','HR经理（培训管理员+HR培训计划备案）'),(23,'311','1','','提交本人培训计划'),(24,'311','2','','提交本人培训计划，并审核本部门计划'),(25,'311','3','','提交本人培训计划，并审核所有部门计划'),(31,'401','2','','考勤专员(所有)'),(32,'401','3','','考勤HR经理'),(33,'411','1','','提交本人考勤申请'),(34,'411','2','','提交下属员工考勤申请，并进行经理审核'),(35,'411','3','','提交所有人考勤申请，并进行总经理审核'),(36,'401','1','','考勤专员(分管某部门或地区)'),(41,'501','1','','绩效管理员(暂不使用)'),(42,'501','2','','HR经理（绩效管理员+HR审核权）(暂不使用)'),(43,'511','1','','提交本人绩效考评表(暂不使用)'),(44,'511','2','','提交本人绩效考评表，并审核本部门考评结果(暂不使用)'),(45,'511','3','','提交本人绩效考评表，并审核所有部门考评结果(暂不使用)'),(51,'601','1','','招聘管理员'),(52,'601','2','','HR经理（招聘管理员+HR审核）'),(53,'611','1','','提交本部门招聘计划'),(54,'611','2','','提交审核本部门招聘计划'),(55,'611','3','','提交审核所有部门招聘计划'),(61,'701','1','','运行报表'),(62,'701','2','','运行报表+维护自定义报表'),(63,'701','3','','运行报表+维护自定义报表+维护预定义报表'),(71,'801','','','新闻发布管理员'),(72,'802','2','0','查询新闻限制部门（本部门+所负责部门）'),(73,'802','3','0','查询所有新闻'),(81,'911','','','用户管理'),(82,'921','','','角色权限管理'),(83,'931','','','公司注册信息'),(84,'941','','','日志/Log管理'),(85,'951','','','系统邮件管理'),(86,'952','','','基础表格设置'),(87,'961','','','系统参数设置'),(88,'962','','','数据清理'),(89,'963','','','数据备份与恢复'),(91,'702','1','','运行自定义接口'),(92,'702','2','','运行+维护自定义接口');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authoritypos`
--

DROP TABLE IF EXISTS `authoritypos`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `authoritypos` (
  `ap_id` varchar(36) NOT NULL default '',
  `ap_pos_id` varchar(36) NOT NULL default '',
  `ap_dept_id` varchar(36) default NULL,
  `ap_loc_id` varchar(36) default NULL,
  `ap_module` varchar(6) NOT NULL default '',
  `ap_auth_validate` varchar(42) NOT NULL default '',
  PRIMARY KEY  (`ap_id`),
  KEY `FK_ap_pos_id` (`ap_pos_id`),
  KEY `FK_ap_dept_id` (`ap_dept_id`),
  KEY `FK_ap_loc_id` (`ap_loc_id`),
  CONSTRAINT `FK_ap_dept_id` FOREIGN KEY (`ap_dept_id`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ap_loc_id` FOREIGN KEY (`ap_loc_id`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ap_pos_id` FOREIGN KEY (`ap_pos_id`) REFERENCES `position` (`position_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `authoritypos`
--

LOCK TABLES `authoritypos` WRITE;
/*!40000 ALTER TABLE `authoritypos` DISABLE KEYS */;
/*!40000 ALTER TABLE `authoritypos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `benefittype`
--

DROP TABLE IF EXISTS `benefittype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `benefittype` (
  `benefittype_id` varchar(36) NOT NULL default '',
  `benefittype_name` varchar(64) NOT NULL default '',
  `benefittype_desc` varchar(128) default NULL,
  `benefittype_loc_no` varchar(36) default NULL,
  `benefittype_pay_type` int(10) unsigned NOT NULL default '0',
  `benefittype_sort_id` int(10) unsigned NOT NULL default '0',
  `benefittype_pension_uplimit` decimal(9,2) default NULL,
  `benefittype_pension_lowlimit` decimal(9,2) default NULL,
  `benefittype_unemployment_uplimit` decimal(9,2) default NULL,
  `benefittype_unemployment_lowlimit` decimal(9,2) default NULL,
  `benefittype_medicare_uplimit` decimal(9,2) default NULL,
  `benefittype_medicare_lowlimit` decimal(9,2) default NULL,
  `benefittype_injury_uplimit` decimal(9,2) default NULL,
  `benefittype_injury_lowlimit` decimal(9,2) default NULL,
  `benefittype_childbirth_uplimit` decimal(9,2) default NULL,
  `benefittype_childbirth_lowlimit` decimal(9,2) default NULL,
  `benefittype_housing_uplimit` decimal(9,2) default NULL,
  `benefittype_housing_lowlimit` decimal(9,2) default NULL,
  `benefittype_other_uplimit` decimal(9,2) default NULL,
  `benefittype_other_lowlimit` decimal(9,2) default NULL,
  PRIMARY KEY  (`benefittype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `benefittype`
--

LOCK TABLES `benefittype` WRITE;
/*!40000 ALTER TABLE `benefittype` DISABLE KEYS */;
INSERT INTO `benefittype` VALUES ('0d70a48f-8ce8-40ff-b938-d6c50bfcb3b1','城保','城市四险一金','bc262b84-d1ff-4664-8b62-210386b16ef0',0,1,'8676.00','1735.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'8676.00','1735.00',NULL,NULL),('10f0eb5a-8ccb-4d3b-adaa-d3c9a9ce104e','镇保','城镇三险一金','bc262b84-d1ff-4664-8b62-210386b16ef0',0,2,'8676.00','960.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'8676.00','960.00',NULL,NULL),('a53f5978-9b51-4072-b740-ef75ca15a7f7','综保','上海市综合保险','bc262b84-d1ff-4664-8b62-210386b16ef0',0,3,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'216.90','216.90');
/*!40000 ALTER TABLE `benefittype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `city` (
  `city_no` varchar(16) NOT NULL default '',
  `city_name` varchar(64) NOT NULL default '',
  `city_desc` varchar(128) default NULL,
  `city_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`city_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `client` (
  `client_no` varchar(8) NOT NULL default '',
  `client_id` varchar(36) NOT NULL default '',
  `client_name` varchar(128) NOT NULL default '',
  `client_short_name` varchar(32) default NULL,
  `client_address` varchar(128) default NULL,
  `client_zip` varchar(8) default NULL,
  `client_phone` varchar(32) NOT NULL default '',
  `client_fax` varchar(32) default NULL,
  `client_email` varchar(64) NOT NULL default '',
  `client_contact_name` varchar(32) default NULL,
  `client_activate_time` datetime default NULL,
  `client_service_months` int(11) default NULL,
  `client_service_times` int(10) unsigned default NULL,
  `client_limit` varchar(255) NOT NULL default '',
  `client_status` int(10) unsigned NOT NULL default '0',
  `client_remarks` varchar(255) default NULL,
  PRIMARY KEY  (`client_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('DEMO','80041ee0-17f1-4cd4-8e9d-52d276f1f5fa','','','','','','','','',NULL,NULL,NULL,'QVH@:nPWTOQr:)=\nU/Aj>c15B_0<;`/m:b_A5071v_.9=+p924jdE+6qIX7A7d=7E_/s?Zi>',0,'');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `department` (
  `department_no` varchar(36) NOT NULL default '',
  `department_name` varchar(64) NOT NULL default '',
  `department_desc` varchar(128) default NULL,
  `department_status` int(10) unsigned NOT NULL default '1',
  `department_sort_id` int(10) unsigned NOT NULL default '0',
  `department_parent_id` varchar(36) default NULL,
  `department_cate` int(10) unsigned default NULL,
  `department_business_desc` varchar(255) default NULL,
  `department_business_attach` varchar(128) default NULL,
  `department_create_date` date NOT NULL default '0000-00-00',
  `department_end_date` date default NULL,
  `department_trade` varchar(64) default NULL,
  `department_person_inlaw` varchar(64) default NULL,
  `department_memo` varchar(128) default NULL,
  PRIMARY KEY  (`department_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `depthist`
--

DROP TABLE IF EXISTS `depthist`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `depthist` (
  `dhi_id` varchar(36) NOT NULL default '',
  `dhi_dept_no` varchar(36) NOT NULL default '',
  `dhi_dept_name` varchar(64) NOT NULL default '',
  `dhi_dept_status` int(10) unsigned NOT NULL default '0',
  `dhi_dept_sup_id` varchar(36) default NULL,
  `dhi_valid_from` date NOT NULL default '0000-00-00',
  `dhi_valid_to` date default NULL,
  PRIMARY KEY  (`dhi_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `depthist`
--

LOCK TABLES `depthist` WRITE;
/*!40000 ALTER TABLE `depthist` DISABLE KEYS */;
/*!40000 ALTER TABLE `depthist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ecptype`
--

DROP TABLE IF EXISTS `ecptype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ecptype` (
  `ecptype_id` varchar(36) NOT NULL default '',
  `ecptype_name` varchar(32) NOT NULL default '',
  `ecptype_desc` varchar(128) default NULL,
  `ecptype_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`ecptype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ecptype`
--

LOCK TABLES `ecptype` WRITE;
/*!40000 ALTER TABLE `ecptype` DISABLE KEYS */;
INSERT INTO `ecptype` VALUES ('043b03bd-cc56-44f5-b344-5fd001daf0c9','转岗',NULL,2),('043b03bd-cc56-44f5-b644-5fd001daf012','加薪','',1),('043b03bd-cc56-44f5-b644-5fd333daf0c9','其它',NULL,5),('043b03bd-cc56-44f5-b6we-5fd001daf0c9','转正',NULL,3),('043b03bd-cdd6-44f5-b644-5fd001daf0c9','升职','',4);
/*!40000 ALTER TABLE `ecptype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ecttype`
--

DROP TABLE IF EXISTS `ecttype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ecttype` (
  `ecttype_no` varchar(36) NOT NULL default '',
  `ecttype_name` varchar(64) NOT NULL default '',
  `ecttype_desc` varchar(128) default NULL,
  `ecttype_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`ecttype_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ecttype`
--

LOCK TABLES `ecttype` WRITE;
/*!40000 ALTER TABLE `ecttype` DISABLE KEYS */;
INSERT INTO `ecttype` VALUES ('4028800f177190a40117719221740001','正式合同','正式员工合同',1),('4028800f1771a5d0011771a821790001','其他合同','其他合同',0),('4028800f1771dbcf011771dc92b40001','临时合同','临时员工合同',2);
/*!40000 ALTER TABLE `ecttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailsend`
--

DROP TABLE IF EXISTS `emailsend`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emailsend` (
  `es_id` varchar(36) NOT NULL default '',
  `es_from` varchar(64) default NULL,
  `es_to` varchar(255) default NULL,
  `es_cc` varchar(255) default NULL,
  `es_title` varchar(255) NOT NULL default '',
  `es_content` mediumtext NOT NULL,
  `es_status` int(10) unsigned NOT NULL default '0',
  `es_createtime` datetime NOT NULL default '0000-00-00 00:00:00',
  `es_sendtime` datetime default NULL,
  PRIMARY KEY  (`es_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emailsend`
--

LOCK TABLES `emailsend` WRITE;
/*!40000 ALTER TABLE `emailsend` DISABLE KEYS */;
/*!40000 ALTER TABLE `emailsend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailtemplate`
--

DROP TABLE IF EXISTS `emailtemplate`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emailtemplate` (
  `et_id` varchar(36) NOT NULL default '',
  `et_title_no` varchar(32) NOT NULL default '',
  `et_notes` varchar(255) default NULL,
  `et_status` int(10) unsigned default NULL,
  `et_send_mode` int(10) unsigned default NULL,
  PRIMARY KEY  (`et_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emailtemplate`
--

LOCK TABLES `emailtemplate` WRITE;
/*!40000 ALTER TABLE `emailtemplate` DISABLE KEYS */;
INSERT INTO `emailtemplate` VALUES ('0570b078-3dbf-4810-9086-85816cb78328','OtSubmitNotify','经理或HR给替员工提交加班申请后，Email给员工本人',1,2),('08bc33e0-fc32-4f65-b69a-ba6f756d64b2','OTBackup','加班申请被HR备案后，Email给申请者本人',1,2),('08dcc2b5-4582-4a3b-9823-06050af6ab4c','TREPCancel','培训计划被HR废除，Email给申请者本人',1,2),('126d073d-b156-46a2-ba7e-19ed729a3696','OTConfirm','加班申请被HR确认后，Email给申请者本人',1,2),('183244ae-9235-4fd8-a4d0-adadd06b8aea','RecPlanSubmit','员工提交招聘计划后，Email部门经理/其他部门负责人；特定职位会在部门经理提交后，Email给总经理',1,2),('1b6bc573-1193-461a-b491-d179394d9835','OTCancel','加班申请被HR废除，Email给申请者本人',1,2),('1f180d9b-d81f-4b16-b7c8-85a76ad28bf1','CompPlanApprove','调薪计划被HR批准时，Email给计划创建者',1,2),('2216a070-50d0-4501-b76a-d5b154417d98','CompPlanFinish','调薪计划已执行完毕，Email给计划创建者',1,2),('2c191583-9eb5-4ae3-bd57-fd0c076ccacc','RecPlanReject','招聘计划被部门经理/总经理/HR经理拒绝，Email给申请者本人(即申请的创建者)',1,2),('402880e6197df6b901197dfb84b90001','NewUserPassword','系统管理员修改用户密码后，点击\"提交(发邮件)\"可以选择发送email给该员工',1,0),('4c768a66-db56-4012-be74-6d4315496497','CompPlanSubmit','调薪计划每次提交(逐条或批量)后，Email给组织结构树中上一级调薪经理',1,2),('51503772-8299-4b5a-9d89-cdbdb4f33a96','RecPlanApprove','招聘计划被部门经理/总经理/HR经理批准后，Email给申请者本人(即申请的创建者)',1,2),('5f6d682f-be2d-48bb-b34c-e2716425c314','TREPApprove','培训计划被部门经理/总经理/HR经理批准后，Email给申请者本人',1,2),('6066b023-bdf0-42ac-85a7-43dcb346c83d','LeaveCancel','请假申请被HR废除，Email给申请者本人',1,2),('629c0481-8f97-4329-b08d-f53b2e1ef509','LeaveApprove','请假申请被部门经理/总经理/HR经理批准后，Email给申请者本人',1,1),('70d7d7ea-f70e-44e2-bb6c-ddb5710d729f','OTReject','加班申请被部门经理/总经理/HR经理拒绝，email给申请者本人',1,2),('70e831f3-2706-4517-a086-97a46d53e198','OTApproveNotify','加班申请被审批通过后通知加班申请者，cc给记录创建者',1,2),('71989b3d-a2fe-4d56-bc3f-6f0dea26797b','LeaveReject','请假申请被部门经理/总经理/HR经理拒绝，Email给申请者本人',1,2),('77ad4998-3331-4ab9-a779-060051a70248','TREPSubmit','员工提交培训计划后，Email部门经理/其他部门负责人；部门经理提交后，Email给总经理',1,2),('7b84c839-5f28-4868-99ac-a7ef5f65ab8f','LeaveSubmitNotify','经理或HR给替员工提交请假申请后，Email给员工本人',1,2),('7ed299e0-cd3e-4091-8006-de4beaec8760','LeaveResumption','请假申请被HR销假后，Email给申请者本人',1,2),('85e06f75-2005-40aa-86d3-bb1456c25f3d','RecPlanFinish','招聘计划已完成(状态为已终止或已关闭)，Email给申请者本人(即申请的创建者)',1,2),('94a3b74e-b31b-4724-b94e-630ee2a1eac0','LeaveBackup','请假申请被HR备案后，Email给申请者本人',1,2),('9e8ed82a-6cd5-4699-8ec9-74cebfdfeb16','CompPlanReject','调薪计划被HR拒绝时，Email给计划创建者',1,2),('a22c5e3c-27d7-404b-a9f5-e9eb1758d841','LeaveApproveNotify','请假申请被审批通过后通知请假申请者，cc给记录创建者',1,2),('a3011a9b-5d69-4614-99e5-09da38c22d5b','CompPlanCancel','调薪计划被HR废除时，Email给计划创建者',1,2),('b9fd2178-8c17-40b4-b721-ccd8646bc036','NewUser','新用户创建成功后，email给该员工，同时cc给员工信息创建者',1,0),('be743fe4-b329-4432-8fc4-a0bb3be2e5d6','OTApprove','加班申请被部门经理/总经理/HR经理批准后，Email给申请者本人',1,1),('cac532ec-899b-4cce-b08c-9e39ca5e7169','TREPFinish','培训计划已完成(状态为已反馈)，Email给申请者本人',1,2),('db35a76a-7852-47f0-824a-7bf094c342d0','LeaveSubmit','员工提交请假申请后，Email部门经理/部门负责人；部门经理提交后，Email给总经理',1,1),('f268c180-b106-431e-afa8-87cfe4900f2c','OTSubmit','员工提交加班申请后，Email部门经理/其他部门负责人；部门经理提交后，Email给总经理',1,1),('f8fa1cad-788e-4e36-a4f6-6c256d6f44c1','RecPlanCancel','招聘计划被HR废除，Email给申请者本人(即申请的创建者)',1,2),('fc547747-8aaf-418d-ba25-9a61919b5229','TREPReject','培训计划被部门经理/总经理/HR经理拒绝，Email给申请者本人',1,2);
/*!40000 ALTER TABLE `emailtemplate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empaddconf`
--

DROP TABLE IF EXISTS `empaddconf`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empaddconf` (
  `eadc_id` varchar(36) NOT NULL default '',
  `eadc_table_name` varchar(128) NOT NULL default '',
  `eadc_seq` int(10) unsigned NOT NULL default '0',
  `eadc_field_type` varchar(36) default NULL,
  `eadc_field_value` varchar(255) default NULL,
  `eadc_comments` varchar(255) default NULL,
  `eadc_field_name` varchar(128) NOT NULL default '',
  PRIMARY KEY  (`eadc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empaddconf`
--

LOCK TABLES `empaddconf` WRITE;
/*!40000 ALTER TABLE `empaddconf` DISABLE KEYS */;
INSERT INTO `empaddconf` VALUES ('402880e6176bad3801176bbc6e3f0001','empadditional',7,'select','美国,加拿大,新加坡,日本,韩国,英国,法国,意大利,德国,其他','','签证国家'),('402880e6176bad3801176bc663e30005','empadditional',2,'textarea','爱好：','','兴趣爱好'),('402880e6176bad3801176bcbc4200009','empadditional',13,'input','','','居住证号码'),('402880e6176bad3801176bcdefc4000b','empadditional',1,'textarea','','','职业概述'),('402880e6176bad3801176c04d20c0012','empadditional',8,'input','','','成本中心'),('402880f81b73bc81011b73c28c300001','empadditional',9,'input','自主用工,非自主用工','','签证号码'),('402880f81b73bc81011b73c2ced80002','empadditional',10,'input','','','劳动手册编号'),('402880f81b73bc81011b73c339170003','empadditional',14,'input','','','暂住证号码'),('402880f81b73bc81011b73c3a1d00004','empadditional',12,'date','','','暂住证转入日期'),('ff808081176ca2f301176cac31480001','empadditional',11,'date','','','居住证获得日期'),('ff808081176ca2f301176cac31480002','IEmpsalaryPay',1,'input',NULL,NULL,'年'),('ff808081176ca2f301176cac31480003','IEmpsalaryPay',2,'input',NULL,NULL,'月');
/*!40000 ALTER TABLE `empaddconf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empbenefit`
--

DROP TABLE IF EXISTS `empbenefit`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empbenefit` (
  `ebf_id` varchar(36) NOT NULL default '',
  `ebf_emp_no` varchar(36) NOT NULL default '',
  `ebf_start_month` varchar(6) NOT NULL default '',
  `ebf_end_month` varchar(6) default NULL,
  `ebf_pension_no` varchar(32) default NULL,
  `ebf_medical_no` varchar(32) default NULL,
  `ebf_housing_no` varchar(32) default NULL,
  `ebf_pension_amount` decimal(9,2) default NULL,
  `ebf_housing_amount` decimal(9,2) default NULL,
  `ebf_insurance_amount` decimal(9,2) default NULL,
  `ebf_comments` varchar(255) default NULL,
  PRIMARY KEY  (`ebf_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empbenefit`
--

LOCK TABLES `empbenefit` WRITE;
/*!40000 ALTER TABLE `empbenefit` DISABLE KEYS */;
/*!40000 ALTER TABLE `empbenefit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empbenefitplan`
--

DROP TABLE IF EXISTS `empbenefitplan`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empbenefitplan` (
  `ebp_id` varchar(36) NOT NULL,
  `ebp_emp_no` varchar(36) NOT NULL,
  `ebp_yearmonth` varchar(6) NOT NULL,
  `ebp_belong_yearmonth` varchar(6) NOT NULL,
  `ebp_pension_amount_b` decimal(9,2) default NULL,
  `ebp_housing_amount_b` decimal(9,2) default NULL,
  `ebp_insurance_amount_b` decimal(9,2) default NULL,
  `ebp_esav_id` varchar(36) default NULL,
  `ebp_column1` decimal(12,2) default NULL,
  `ebp_column2` decimal(12,2) default NULL,
  `ebp_column3` decimal(12,2) default NULL,
  `ebp_column4` decimal(12,2) default NULL,
  `ebp_column5` decimal(12,2) default NULL,
  `ebp_column6` decimal(12,2) default NULL,
  `ebp_column7` decimal(12,2) default NULL,
  `ebp_column8` decimal(12,2) default NULL,
  `ebp_column9` decimal(12,2) default NULL,
  `ebp_column10` decimal(12,2) default NULL,
  `ebp_column11` decimal(12,2) default NULL,
  `ebp_column12` decimal(12,2) default NULL,
  `ebp_column13` decimal(12,2) default NULL,
  `ebp_column14` decimal(12,2) default NULL,
  `ebp_column15` decimal(12,2) default NULL,
  `ebp_column16` decimal(12,2) default NULL,
  `ebp_column17` decimal(12,2) default NULL,
  `ebp_column18` decimal(12,2) default NULL,
  `ebp_column19` decimal(12,2) default NULL,
  `ebp_column20` decimal(12,2) default NULL,
  `ebp_column21` decimal(12,2) default NULL,
  `ebp_column22` decimal(12,2) default NULL,
  `ebp_column23` decimal(12,2) default NULL,
  `ebp_column24` decimal(12,2) default NULL,
  `ebp_column25` decimal(12,2) default NULL,
  `ebp_column26` decimal(12,2) default NULL,
  `ebp_column27` decimal(12,2) default NULL,
  `ebp_column28` decimal(12,2) default NULL,
  `ebp_column29` decimal(12,2) default NULL,
  `ebp_column30` decimal(12,2) default NULL,
  `ebp_column31` decimal(12,2) default NULL,
  `ebp_column32` decimal(12,2) default NULL,
  `ebp_column33` decimal(12,2) default NULL,
  `ebp_column34` decimal(12,2) default NULL,
  `ebp_column35` decimal(12,2) default NULL,
  `ebp_column36` decimal(12,2) default NULL,
  `ebp_column37` decimal(12,2) default NULL,
  `ebp_column38` decimal(12,2) default NULL,
  `ebp_column39` decimal(12,2) default NULL,
  `ebp_column40` decimal(12,2) default NULL,
  `ebp_column41` decimal(12,2) default NULL,
  `ebp_column42` decimal(12,2) default NULL,
  `ebp_column43` decimal(12,2) default NULL,
  `ebp_column44` decimal(12,2) default NULL,
  `ebp_column45` decimal(12,2) default NULL,
  `ebp_column46` decimal(12,2) default NULL,
  `ebp_column47` decimal(12,2) default NULL,
  `ebp_column48` decimal(12,2) default NULL,
  `ebp_status` int(10) unsigned NOT NULL default '0',
  `ebp_comments` varchar(128) default NULL,
  `ebp_create_by` varchar(36) default NULL,
  `ebp_create_time` datetime default NULL,
  `ebp_last_change_by` varchar(36) default NULL,
  `ebp_last_change_time` datetime default NULL,
  PRIMARY KEY  (`ebp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empbenefitplan`
--

LOCK TABLES `empbenefitplan` WRITE;
/*!40000 ALTER TABLE `empbenefitplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `empbenefitplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empcontract`
--

DROP TABLE IF EXISTS `empcontract`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empcontract` (
  `ect_id` varchar(36) NOT NULL default '',
  `ect_no` varchar(36) default NULL,
  `ect_emp_no` varchar(36) NOT NULL default '',
  `ect_start_date` date NOT NULL default '0000-00-00',
  `ect_end_date` date default NULL,
  `ect_type_no` varchar(36) NOT NULL default '',
  `etc_expire` int(10) unsigned NOT NULL default '0',
  `ect_attatchment` varchar(128) default NULL,
  `ect_comments` varchar(255) default NULL,
  `ect_status` varchar(16) NOT NULL default '',
  `ect_create_by` varchar(36) NOT NULL default '',
  `ect_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `ect_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ect_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`ect_id`),
  KEY `FK_ect_type_no` (`ect_type_no`),
  KEY `FK_ect_emp_no` (`ect_emp_no`),
  CONSTRAINT `FK_ect_emp_no` FOREIGN KEY (`ect_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ect_type_no` FOREIGN KEY (`ect_type_no`) REFERENCES `ecttype` (`ecttype_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empcontract`
--

LOCK TABLES `empcontract` WRITE;
/*!40000 ALTER TABLE `empcontract` DISABLE KEYS */;
/*!40000 ALTER TABLE `empcontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empeval`
--

DROP TABLE IF EXISTS `empeval`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empeval` (
  `ee_id` varchar(36) NOT NULL default '',
  `ee_emp_no` varchar(36) NOT NULL default '',
  `ee_dept_no` varchar(36) NOT NULL default '',
  `ee_pb_no` varchar(36) NOT NULL default '',
  `ee_manager` varchar(36) NOT NULL default '',
  `ee_type` varchar(36) NOT NULL default '',
  `ee_start_date` date default NULL,
  `ee_end_date` date default NULL,
  `ee_rate` varchar(36) default NULL,
  `ee_comments` varchar(255) default NULL,
  `ee_attachment` varchar(128) default NULL,
  `ee_create_by` varchar(36) NOT NULL default '',
  `ee_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `ee_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ee_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`ee_id`),
  KEY `FK_ee_emp_no` (`ee_emp_no`),
  KEY `FK_ee_dept_no` (`ee_dept_no`),
  KEY `FK_ee_manager` (`ee_manager`),
  KEY `FK_ee_pb_no` (`ee_pb_no`),
  CONSTRAINT `FK_ee_pb_no` FOREIGN KEY (`ee_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ee_dept_no` FOREIGN KEY (`ee_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ee_emp_no` FOREIGN KEY (`ee_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_ee_manager` FOREIGN KEY (`ee_manager`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empeval`
--

LOCK TABLES `empeval` WRITE;
/*!40000 ALTER TABLE `empeval` DISABLE KEYS */;
/*!40000 ALTER TABLE `empeval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emphistorg`
--

DROP TABLE IF EXISTS `emphistorg`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emphistorg` (
  `eho_id` varchar(36) NOT NULL,
  `eho_emp_no` varchar(36) NOT NULL default '',
  `eho_dept_no` varchar(36) NOT NULL default '',
  `eho_pb_no` varchar(36) NOT NULL default '',
  `eho_valid_from` date NOT NULL default '0000-00-00',
  `eho_valid_to` date default NULL,
  `eho_is_latest` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`eho_id`),
  KEY `IND_eho_emp_no_1` (`eho_emp_no`,`eho_is_latest`),
  KEY `FK_eho_emp_no` (`eho_emp_no`),
  KEY `FK_eho_dept_no` (`eho_dept_no`),
  CONSTRAINT `FK_eho_dept_no` FOREIGN KEY (`eho_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eho_emp_no` FOREIGN KEY (`eho_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emphistorg`
--

LOCK TABLES `emphistorg` WRITE;
/*!40000 ALTER TABLE `emphistorg` DISABLE KEYS */;
/*!40000 ALTER TABLE `emphistorg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emphistoryedu`
--

DROP TABLE IF EXISTS `emphistoryedu`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emphistoryedu` (
  `ehe_id` varchar(36) NOT NULL default '',
  `ehe_emp_no` varchar(36) NOT NULL default '',
  `ehe_date_start` date default NULL,
  `ehe_date_end` date default NULL,
  `ehe_school` varchar(64) default NULL,
  `ehe_major` varchar(64) default NULL,
  `ehe_degree` varchar(64) default NULL,
  `ehe_comments` varchar(255) default NULL,
  `ehe_attatchment` varchar(128) default NULL,
  `ehe_create_by` varchar(36) NOT NULL default '',
  `ehe_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `ehe_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ehe_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`ehe_id`),
  KEY `FK_ehe_emp_no` (`ehe_emp_no`),
  CONSTRAINT `FK_ehe_emp_no` FOREIGN KEY (`ehe_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emphistoryedu`
--

LOCK TABLES `emphistoryedu` WRITE;
/*!40000 ALTER TABLE `emphistoryedu` DISABLE KEYS */;
/*!40000 ALTER TABLE `emphistoryedu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emphistoryjob`
--

DROP TABLE IF EXISTS `emphistoryjob`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emphistoryjob` (
  `ehj_id` varchar(36) NOT NULL default '',
  `ehj_emp_no` varchar(36) NOT NULL default '',
  `ehj_company` varchar(64) default NULL,
  `ehj_date_start` date default NULL,
  `ehj_date_end` date default NULL,
  `ehj_position` varchar(64) default NULL,
  `ehj_description` varchar(255) default NULL,
  `ehj_create_by` varchar(36) NOT NULL default '',
  `ehj_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `ehj_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ehj_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`ehj_id`),
  KEY `FK_ehj_emp_no` (`ehj_emp_no`),
  CONSTRAINT `FK_ehj_emp_no` FOREIGN KEY (`ehj_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emphistoryjob`
--

LOCK TABLES `emphistoryjob` WRITE;
/*!40000 ALTER TABLE `emphistoryjob` DISABLE KEYS */;
/*!40000 ALTER TABLE `emphistoryjob` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emphistorytrain`
--

DROP TABLE IF EXISTS `emphistorytrain`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emphistorytrain` (
  `eht_id` varchar(36) NOT NULL default '',
  `eht_emp_no` varchar(36) NOT NULL default '',
  `eht_department` varchar(64) default NULL,
  `eht_location` varchar(64) default NULL,
  `eht_start_date` date default NULL,
  `eht_end_date` date default NULL,
  `eht_course` varchar(128) default NULL,
  `eht_certificate` varchar(128) default NULL,
  `eht_comments` varchar(255) default NULL,
  `eht_attatchment` varchar(128) default NULL,
  `eht_create_by` varchar(36) NOT NULL default '',
  `eht_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `eht_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `eht_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`eht_id`),
  KEY `FK_eht_emp_no` (`eht_emp_no`),
  CONSTRAINT `FK_eht_emp_no` FOREIGN KEY (`eht_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emphistorytrain`
--

LOCK TABLES `emphistorytrain` WRITE;
/*!40000 ALTER TABLE `emphistorytrain` DISABLE KEYS */;
/*!40000 ALTER TABLE `emphistorytrain` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `employee` (
  `emp_no` varchar(36) NOT NULL default '',
  `emp_esc_join` varchar(36) default NULL,
  `emp_distinct_no` varchar(32) default NULL,
  `emp_machine_no` int(10) unsigned NOT NULL default '0',
  `emp_name` varchar(64) NOT NULL default '',
  `emp_fname` varchar(32) default NULL,
  `emp_mname` varchar(16) default NULL,
  `emp_lname` varchar(32) default NULL,
  `emp_birth_date` date NOT NULL default '0000-00-00',
  `emp_gender` int(10) unsigned NOT NULL default '0',
  `emp_marital` int(10) unsigned default NULL,
  `emp_blood` varchar(2) default NULL,
  `emp_politics` varchar(16) default NULL,
  `emp_diploma` varchar(16) default NULL,
  `emp_school` varchar(64) default NULL,
  `emp_speciality` varchar(64) default NULL,
  `emp_city_no` varchar(16) default NULL,
  `emp_nation` varchar(16) default NULL,
  `emp_residence_loc` varchar(128) default NULL,
  `emp_profile_loc` varchar(128) default NULL,
  `emp_identification_type` int(10) unsigned NOT NULL default '0',
  `emp_identification_no` varchar(32) default NULL,
  `emp_benefit_type` varchar(36) default NULL,
  `emp_benefit_id` varchar(36) default NULL,
  `emp_home_phone` varchar(32) default NULL,
  `emp_mobile` varchar(32) default NULL,
  `emp_home_addr` varchar(128) default NULL,
  `emp_home_zip` varchar(6) default NULL,
  `emp_curr_addr` varchar(128) default NULL,
  `emp_curr_zip` varchar(6) default NULL,
  `emp_email` varchar(64) default NULL,
  `emp_msn` varchar(64) default NULL,
  `emp_work_phone` varchar(32) default NULL,
  `emp_image` varchar(64) default NULL,
  `emp_work_date` date default NULL,
  `emp_join_date` date NOT NULL default '0000-00-00',
  `emp_confirm_date` date default NULL,
  `emp_dept_no` varchar(36) NOT NULL default '',
  `emp_pb_no` varchar(36) NOT NULL default '',
  `emp_type` varchar(36) NOT NULL default '',
  `emp_location_no` varchar(36) NOT NULL default '',
  `emp_perf_rate` varchar(16) default NULL,
  `emp_perf_date` date default NULL,
  `emp_current_contract` varchar(36) default NULL,
  `emp_status` int(10) unsigned NOT NULL default '1',
  `emp_terminate_date` date default NULL,
  `emp_quit_id` varchar(36) default NULL,
  `emp_urgent_contact` varchar(64) default NULL,
  `emp_urgent_con_method` varchar(128) default NULL,
  `emp_comments` varchar(255) default NULL,
  `emp_import_by_interface` int(10) unsigned default NULL,
  `emp_shift_type` int(10) unsigned default NULL,
  `emp_shift_no` varchar(32) default NULL,
  `emp_additional1` varchar(255) default NULL,
  `emp_additional2` varchar(255) default NULL,
  `emp_additional3` varchar(255) default NULL,
  `emp_additional4` varchar(255) default NULL,
  `emp_additional5` varchar(255) default NULL,
  `emp_additional6` varchar(255) default NULL,
  `emp_additional7` varchar(255) default NULL,
  `emp_additional8` varchar(255) default NULL,
  `emp_additional9` varchar(255) default NULL,
  `emp_additional10` varchar(255) default NULL,
  `emp_additional11` varchar(255) default NULL,
  `emp_additional12` varchar(255) default NULL,
  `emp_additional13` varchar(255) default NULL,
  `emp_additional14` varchar(255) default NULL,
  `emp_additional15` varchar(255) default NULL,
  `emp_additional16` varchar(255) default NULL,
  `emp_resume1` varchar(128) default NULL,
  `emp_resume2` varchar(128) default NULL,
  `emp_create_by` varchar(36) default NULL,
  `emp_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `emp_last_change_by` varchar(36) default NULL,
  `emp_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`emp_no`),
  KEY `FK_emp_dept_no` (`emp_dept_no`),
  KEY `FK_emp_type` (`emp_type`),
  KEY `FK_emp_location_no` (`emp_location_no`),
  KEY `FK_emp_distinct_no` (`emp_distinct_no`),
  KEY `FK_emp_pb_no` (`emp_pb_no`),
  CONSTRAINT `FK_emp_pb_no` FOREIGN KEY (`emp_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_emp_dept_no` FOREIGN KEY (`emp_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_emp_location_no` FOREIGN KEY (`emp_location_no`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_emp_type` FOREIGN KEY (`emp_type`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empquit`
--

DROP TABLE IF EXISTS `empquit`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empquit` (
  `eq_id` varchar(36) NOT NULL default '',
  `eq_emp_no` varchar(36) NOT NULL default '',
  `eq_dept_no` varchar(36) NOT NULL default '',
  `eq_pb_no` varchar(36) NOT NULL default '',
  `eq_type` varchar(1) default NULL,
  `eq_date` date default NULL,
  `eq_permission` varchar(36) NOT NULL default '',
  `eq_reason` varchar(128) default NULL,
  `er_comments` varchar(255) default NULL,
  `eq_create_by` varchar(36) NOT NULL default '',
  `eq_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `eq_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `eq_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`eq_id`),
  KEY `FK_eq_emp_no` (`eq_emp_no`),
  KEY `FK_eq_permission` (`eq_permission`),
  KEY `FK_eq_dept_no` (`eq_dept_no`),
  KEY `FK_eq_pb_no` (`eq_pb_no`),
  CONSTRAINT `FK_eq_pb_no` FOREIGN KEY (`eq_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eq_dept_no` FOREIGN KEY (`eq_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eq_emp_no` FOREIGN KEY (`eq_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eq_permission` FOREIGN KEY (`eq_permission`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empquit`
--

LOCK TABLES `empquit` WRITE;
/*!40000 ALTER TABLE `empquit` DISABLE KEYS */;
/*!40000 ALTER TABLE `empquit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emprelations`
--

DROP TABLE IF EXISTS `emprelations`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emprelations` (
  `erel_id` varchar(36) NOT NULL default '',
  `erel_emp_no` varchar(36) NOT NULL default '',
  `erel_relationship` varchar(16) NOT NULL default '',
  `erel_name` varchar(64) NOT NULL default '',
  `erel_birthday` date default NULL,
  `erel_birthday_remind` int(10) unsigned default NULL,
  `erel_company` varchar(64) default NULL,
  `erel_position` varchar(64) default NULL,
  `erel_contact_info` varchar(255) default NULL,
  `erel_create_by` varchar(36) NOT NULL default '',
  `erel_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `erel_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `erel_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`erel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emprelations`
--

LOCK TABLES `emprelations` WRITE;
/*!40000 ALTER TABLE `emprelations` DISABLE KEYS */;
/*!40000 ALTER TABLE `emprelations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empreward`
--

DROP TABLE IF EXISTS `empreward`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empreward` (
  `er_id` varchar(36) NOT NULL default '',
  `er_emp_no` varchar(36) NOT NULL default '',
  `er_dept_no` varchar(36) default NULL,
  `er_pb_no` varchar(36) NOT NULL default '',
  `er_type` varchar(32) default NULL,
  `er_content` varchar(255) default NULL,
  `er_create_by` varchar(36) NOT NULL default '',
  `er_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `er_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `er_last_change_by` varchar(36) NOT NULL default '',
  `er_exe_date` date NOT NULL default '0000-00-00',
  `er_form` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`er_id`),
  KEY `FK_er_emp_no` (`er_emp_no`),
  KEY `FK_er_dept_no` (`er_dept_no`),
  KEY `FK_er_pb_no` (`er_pb_no`),
  CONSTRAINT `FK_er_pb_no` FOREIGN KEY (`er_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_er_dept_no` FOREIGN KEY (`er_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_er_emp_no` FOREIGN KEY (`er_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empreward`
--

LOCK TABLES `empreward` WRITE;
/*!40000 ALTER TABLE `empreward` DISABLE KEYS */;
/*!40000 ALTER TABLE `empreward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryacct`
--

DROP TABLE IF EXISTS `empsalaryacct`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryacct` (
  `esac_id` varchar(36) NOT NULL default '',
  `esac_name` varchar(64) NOT NULL default '',
  `esac_desc` varchar(128) default NULL,
  PRIMARY KEY  (`esac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryacct`
--

LOCK TABLES `empsalaryacct` WRITE;
/*!40000 ALTER TABLE `empsalaryacct` DISABLE KEYS */;
INSERT INTO `empsalaryacct` VALUES ('402880e6185fba8201185fc559270004','正式员工帐套','公司正式员工帐套模板（上海三险一金2008版）'),('402880e618976591011897a9a53d0036','临时员工A帐套','临时员工帐套模板（每月固定收入）'),('402880e61897cb9e011897ccae6d0001','临时员工B帐套','临时员工帐套模板（按天计算费用）'),('402880e61897cb9e011897da488b0056','实习生A帐套','实习生帐套模板（每月固定收入）'),('402880e6189ba66501189bca43a30001','实习生B帐套','实习生帐套模板（按天计算费用）');
/*!40000 ALTER TABLE `empsalaryacct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryacctitems`
--

DROP TABLE IF EXISTS `empsalaryacctitems`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryacctitems` (
  `esai_id` varchar(36) NOT NULL default '',
  `esai_esav_id` varchar(36) NOT NULL default '',
  `esai_data_seq` int(10) unsigned NOT NULL default '0',
  `esai_data_id` varchar(36) NOT NULL default '',
  `esai_data_is_calc` int(10) unsigned NOT NULL default '0',
  `esai_data_calc` varchar(512) default NULL,
  `esai_data_rounding` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`esai_id`),
  KEY `FK_esai_data_id` (`esai_data_id`),
  CONSTRAINT `FK_esai_data_id` FOREIGN KEY (`esai_data_id`) REFERENCES `empsalarydatadef` (`esdd_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryacctitems`
--

LOCK TABLES `empsalaryacctitems` WRITE;
/*!40000 ALTER TABLE `empsalaryacctitems` DISABLE KEYS */;
INSERT INTO `empsalaryacctitems` VALUES ('402880331d085940011d08c5dc0f0047','402880e6185fba8201185fc5596d0005',1,'402880e618c1299f0118c12bf7d9001b',0,'',0),('402880331d085940011d08c5dc1f0048','402880e6185fba8201185fc5596d0005',2,'402880e618c1299f0118c12bf7d9014b',0,'',0),('402880331d085940011d08c5dc2e0049','402880e6185fba8201185fc5596d0005',3,'402880e618c1299f0118c12bf7d9015b',0,'',0),('402880331d085940011d08c5dc3e004a','402880e6185fba8201185fc5596d0005',4,'402880e618c1299f0118c12bf7d9016b',2,'A2+A3',0),('402880331d085940011d08c5dc4e004b','402880e6185fba8201185fc5596d0005',5,'402880e618c1299f0118c12bf7d9005b',1,'',0),('402880331d085940011d08c5dc5d004c','402880e6185fba8201185fc5596d0005',6,'402880e618c1299f0118c12bf7d9006b',2,'?(A5>0,A1/C1/8*A5,0)',0),('402880331d085940011d08c5dc6d004d','402880e6185fba8201185fc5596d0005',7,'402880e618c1299f0118c12bf7d9018b',1,'',0),('402880331d085940011d08c5dc7d004e','402880e6185fba8201185fc5596d0005',8,'402880e618c1299f0118c12bf7d9019b',2,'?(A7>=C1,200,0)',0),('402880331d085940011d08c5dc7d004f','402880e6185fba8201185fc5596d0005',9,'402880e618c1299f0118c12bf7d9020b',1,'',0),('402880331d085940011d08c5dc8c0050','402880e6185fba8201185fc5596d0005',10,'402880e618c1299f0118c12bf7d9021b',2,'?(A9>0,100*A9,0)',0),('402880331d085940011d08c5dc9c0051','402880e6185fba8201185fc5596d0005',11,'402880e618c1299f0118c12bf7d9022b',1,'',0),('402880331d085940011d08c5dcab0052','402880e6185fba8201185fc5596d0005',12,'402880e618c1299f0118c12bf7d9023b',1,'',0),('402880331d085940011d08c5dcbb0053','402880e6185fba8201185fc5596d0005',13,'402880e618c1299f0118c12bf7d9024b',1,'',0),('402880331d085940011d08c5dccb0054','402880e6185fba8201185fc5596d0005',14,'402880e618c1299f0118c12bf7d9008b',2,'?(A12+A13>0,(A1+A2+A3)/C1*(A12+A13*0.3),0)',0),('402880331d085940011d08c5dcda0055','402880e6185fba8201185fc5596d0005',15,'402880e618c1299f0118c12bf7d9010b',2,'A6+A8+A10+A11-A14',0),('402880331d085940011d08c5dcea0056','402880e6185fba8201185fc5596d0005',16,'402880e618c1299f0118c12bf7d9011b',2,'A1+A4+A15',0),('402880331d085940011d08c5dcea0057','402880e6185fba8201185fc5596d0005',17,'402880e618c1299f0118c12bf7d9025b',0,'',0),('402880331d085940011d08c5dd090058','402880e6185fba8201185fc5596d0005',18,'402880e618c1299f0118c12bf7d9026b',0,'',0),('402880331d085940011d08c5dd090059','402880e6185fba8201185fc5596d0005',19,'402880e618c1299f0118c12bf7d9027b',2,'A17*0.08',0),('402880331d085940011d08c5dd19005a','402880e6185fba8201185fc5596d0005',20,'402880e618c1299f0118c12bf7d9028b',2,'A17*0.02',0),('402880331d085940011d08c5dd28005b','402880e6185fba8201185fc5596d0005',21,'402880e618c1299f0118c12bf7d9029b',2,'A17*0.01',0),('402880331d085940011d08c5dd38005c','402880e6185fba8201185fc5596d0005',22,'402880e618c1299f0118c12bf7d9030b',2,'A18*0.07',0),('402880331d085940011d08c5dd48005d','402880e6185fba8201185fc5596d0005',23,'402880e618c1299f0118c12bf7d9031b',2,'A19+A20+A21+A22',0),('402880331d085940011d08c5dd67005e','402880e6185fba8201185fc5596d0005',24,'402880e618c1299f0118c12bf7d9032b',0,'',0),('402880331d085940011d08c5dd86005f','402880e6185fba8201185fc5596d0005',25,'402880e618c1299f0118c12bf7d9034b',2,'A23+A24',0),('402880331d085940011d08c5ddb50060','402880e6185fba8201185fc5596d0005',26,'402880e618c1299f0118c12bf7d9012b',2,'$(A16-A23)',0),('402880331d085940011d08c5ddd40061','402880e6185fba8201185fc5596d0005',27,'402880e618c1299f0118c12bf7d9033b',1,'',0),('402880331d085940011d08c5ddf40062','402880e6185fba8201185fc5596d0005',28,'402880e618c1299f0118c12bf7d9013b',2,'A16-A25-A26+A27',0),('402880331d085940011d08c5de030063','402880e6185fba8201185fc5596d0005',29,'ff8080811c74a505011c74b013a40001',2,'A17*0.37',0),('402880331d085940011d08c5de130064','402880e6185fba8201185fc5596d0005',30,'ff8080811c74a505011c74b15a0e0002',2,'A18*0.07',0),('402880331d085940011d08c5de130065','402880e6185fba8201185fc5596d0005',31,'ff8080811c74a505011c74b1f41c0003',2,'A29+A30',0),('402880ea1cdf262b011ce07099f70023','402880e6189ba66501189bca441b0002',1,'402880e618c1299f0118c12bf7d9039b',0,'',0),('402880ea1cdf262b011ce0709a3e0024','402880e6189ba66501189bca441b0002',2,'402880e618c1299f0118c12bf7d9035b',0,'',0),('402880ea1cdf262b011ce0709a5c0025','402880e6189ba66501189bca441b0002',3,'402880e618c1299f0118c12bf7d9001b',2,'A1*A2',0),('402880ea1cdf262b011ce0709a840026','402880e6189ba66501189bca441b0002',4,'402880e618c1299f0118c12bf7d9005b',1,'',6),('402880ea1cdf262b011ce0709aac0027','402880e6189ba66501189bca441b0002',5,'402880e618c1299f0118c12bf7d9006b',2,'?(A4>0,A1/8*A4,0)',0),('402880ea1cdf262b011ce0709ade0028','402880e6189ba66501189bca441b0002',6,'402880e618c1299f0118c12bf7d9037b',1,'',0),('402880ea1cdf262b011ce0709b060029','402880e6189ba66501189bca441b0002',7,'402880e618c1299f0118c12bf7d9010b',2,'A5+A6',0),('402880ea1cdf262b011ce0709b2e002a','402880e6189ba66501189bca441b0002',8,'402880e618c1299f0118c12bf7d9011b',2,'A3+A7',0),('402880ea1cdf262b011ce0709b56002b','402880e6189ba66501189bca441b0002',9,'4028804a1a9a34bf011a9aa2e81d000e',2,'$(A8-A9)',0),('402880ea1cdf262b011ce0709b88002c','402880e6189ba66501189bca441b0002',10,'402880e618c1299f0118c12bf7d9013b',0,'A8-A9-A9',0),('402880ea1cdf262b011ce070d899002d','402880e61897cb9e011897ccaedb0002',1,'402880e618c1299f0118c12bf7d9039b',0,'',0),('402880ea1cdf262b011ce070d8c1002e','402880e61897cb9e011897ccaedb0002',2,'402880e618c1299f0118c12bf7d9035b',0,'',0),('402880ea1cdf262b011ce070d8f3002f','402880e61897cb9e011897ccaedb0002',3,'402880e618c1299f0118c12bf7d9001b',2,'A1*A2',0),('402880ea1cdf262b011ce070d91b0030','402880e61897cb9e011897ccaedb0002',4,'402880e618c1299f0118c12bf7d9005b',1,'',0),('402880ea1cdf262b011ce070d9430031','402880e61897cb9e011897ccaedb0002',5,'402880e618c1299f0118c12bf7d9006b',2,'?(A4>0,A1/8*A4,0)',0),('402880ea1cdf262b011ce070d9750032','402880e61897cb9e011897ccaedb0002',6,'402880e618c1299f0118c12bf7d9037b',1,'',0),('402880ea1cdf262b011ce070d99d0033','402880e61897cb9e011897ccaedb0002',7,'402880e618c1299f0118c12bf7d9010b',2,'A5+A6',0),('402880ea1cdf262b011ce070d9c50034','402880e61897cb9e011897ccaedb0002',8,'402880e618c1299f0118c12bf7d9011b',2,'A3+A7',0),('402880ea1cdf262b011ce070d9ed0035','402880e61897cb9e011897ccaedb0002',9,'402880e618c1299f0118c12bf7d9032b',0,'',0),('402880ea1cdf262b011ce070da510036','402880e61897cb9e011897ccaedb0002',10,'402880e618c1299f0118c12bf7d9034b',2,'A9',0),('402880ea1cdf262b011ce070da830037','402880e61897cb9e011897ccaedb0002',11,'402880e618c1299f0118c12bf7d9012b',2,'$(A8-A10)',0),('402880ea1cdf262b011ce070daab0038','402880e61897cb9e011897ccaedb0002',12,'402880e618c1299f0118c12bf7d9013b',2,'A8-A10-A11',0),('402880ea1cdf262b011ce07119c50039','402880e61897cb9e011897da48db0057',1,'402880e618c1299f0118c12bf7d9001b',0,'',0),('402880ea1cdf262b011ce0711a15003a','402880e61897cb9e011897da48db0057',2,'402880e618c1299f0118c12bf7d9002b',0,'',0),('402880ea1cdf262b011ce0711a5b003b','402880e61897cb9e011897da48db0057',3,'402880e618c1299f0118c12bf7d9003b',0,'',0),('402880ea1cdf262b011ce0711ac9003c','402880e61897cb9e011897da48db0057',4,'402880e618c1299f0118c12bf7d9016b',2,'A2+A3',0),('402880ea1cdf262b011ce0711afb003d','402880e61897cb9e011897da48db0057',5,'402880e618c1299f0118c12bf7d9005b',1,'',0),('402880ea1cdf262b011ce0711b23003e','402880e61897cb9e011897da48db0057',6,'402880e618c1299f0118c12bf7d9006b',2,'?(A5>0,A1/21.92/8*A5,0)',0),('402880ea1cdf262b011ce0711b55003f','402880e61897cb9e011897da48db0057',7,'402880e618c1299f0118c12bf7d9007b',1,'',0),('402880ea1cdf262b011ce0711b7d0040','402880e61897cb9e011897da48db0057',8,'402880e618c1299f0118c12bf7d9008b',2,'?(A7>0,A1/21.92*A7,0)',0),('402880ea1cdf262b011ce0711baf0041','402880e61897cb9e011897da48db0057',9,'402880e618c1299f0118c12bf7d9037b',1,'',0),('402880ea1cdf262b011ce0711bd70042','402880e61897cb9e011897da48db0057',10,'402880e618c1299f0118c12bf7d9010b',2,'A6-A8+A9',0),('402880ea1cdf262b011ce0711bff0043','402880e61897cb9e011897da48db0057',11,'402880e618c1299f0118c12bf7d9011b',2,'A1+A4+A10',0),('402880ea1cdf262b011ce0711c270044','402880e61897cb9e011897da48db0057',12,'402880e618c1299f0118c12bf7d9012b',2,'$(A11)',0),('402880ea1cdf262b011ce0711c4f0045','402880e61897cb9e011897da48db0057',13,'402880e618c1299f0118c12bf7d9013b',2,'A11-A12',0),('402880ea1cdf262b011ce0714eb20046','402880e618976591011897a9a5830037',1,'402880e618c1299f0118c12bf7d9001b',0,'',0),('402880ea1cdf262b011ce0714eda0047','402880e618976591011897a9a5830037',2,'402880e618c1299f0118c12bf7d9038b',0,'',0),('402880ea1cdf262b011ce0714f0c0048','402880e618976591011897a9a5830037',3,'402880e618c1299f0118c12bf7d9005b',1,'',0),('402880ea1cdf262b011ce0714f340049','402880e618976591011897a9a5830037',4,'402880e618c1299f0118c12bf7d9006b',2,'?(A3>0,A1/21.92/8*A3,0)',0),('402880ea1cdf262b011ce0714f5c004a','402880e618976591011897a9a5830037',5,'402880e618c1299f0118c12bf7d9007b',1,'',0),('402880ea1cdf262b011ce0714f84004b','402880e618976591011897a9a5830037',6,'402880e618c1299f0118c12bf7d9008b',2,'?(A5>0,A1/21.92*A5,0)',0),('402880ea1cdf262b011ce0714fa2004c','402880e618976591011897a9a5830037',7,'402880e618c1299f0118c12bf7d9037b',1,'',0),('402880ea1cdf262b011ce0714fd4004d','402880e618976591011897a9a5830037',8,'402880e618c1299f0118c12bf7d9010b',2,'A4-A6+A7',0),('402880ea1cdf262b011ce0714ffc004e','402880e618976591011897a9a5830037',9,'402880e618c1299f0118c12bf7d9011b',2,'A1+A2+A8',0),('402880ea1cdf262b011ce0715043004f','402880e618976591011897a9a5830037',10,'402880e618c1299f0118c12bf7d9012b',2,'$(A9)',0),('402880ea1cdf262b011ce07150750050','402880e618976591011897a9a5830037',11,'402880e618c1299f0118c12bf7d9013b',2,'A9-A10',0);
/*!40000 ALTER TABLE `empsalaryacctitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryacctversion`
--

DROP TABLE IF EXISTS `empsalaryacctversion`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryacctversion` (
  `esav_id` varchar(36) NOT NULL default '',
  `esav_esac_id` varchar(36) NOT NULL default '',
  `esav_valid_from` date NOT NULL default '0000-00-00',
  `esav_valid_to` date default NULL,
  `esav_create_by` varchar(36) default NULL,
  `esav_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `esav_last_change_by` varchar(36) default NULL,
  `esav_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`esav_id`),
  KEY `FK_esav_esac_id` (`esav_esac_id`),
  CONSTRAINT `FK_esav_esac_id` FOREIGN KEY (`esav_esac_id`) REFERENCES `empsalaryacct` (`esac_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryacctversion`
--

LOCK TABLES `empsalaryacctversion` WRITE;
/*!40000 ALTER TABLE `empsalaryacctversion` DISABLE KEYS */;
INSERT INTO `empsalaryacctversion` VALUES ('402880e6185fba8201185fc5596d0005','402880e6185fba8201185fc559270004','2008-01-28',NULL,'855e2ab3-1a63-417a-a296-a0add3085500','2008-02-28 00:00:00','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-12 11:26:39'),('402880e618976591011897a9a5830037','402880e618976591011897a9a53d0036','2008-03-10',NULL,'855e2ab3-1a63-417a-a296-a0add3085500','2008-03-10 00:00:00','855e2ab3-1a63-417a-a296-a0add3085500','2008-10-09 15:10:34'),('402880e61897cb9e011897ccaedb0002','402880e61897cb9e011897ccae6d0001','2008-03-10',NULL,'855e2ab3-1a63-417a-a296-a0add3085500','2008-03-10 00:00:00','855e2ab3-1a63-417a-a296-a0add3085500','2008-10-09 15:10:03'),('402880e61897cb9e011897da48db0057','402880e61897cb9e011897da488b0056','2008-03-10',NULL,'855e2ab3-1a63-417a-a296-a0add3085500','2008-03-10 00:00:00','855e2ab3-1a63-417a-a296-a0add3085500','2008-10-09 15:10:20'),('402880e6189ba66501189bca441b0002','402880e6189ba66501189bca43a30001','2008-01-11',NULL,'855e2ab3-1a63-417a-a296-a0add3085500','2008-03-11 00:00:00','855e2ab3-1a63-417a-a296-a0add3085500','2008-10-09 15:09:47');
/*!40000 ALTER TABLE `empsalaryacctversion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryadj`
--

DROP TABLE IF EXISTS `empsalaryadj`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryadj` (
  `esa_id` varchar(36) NOT NULL default '',
  `esa_empno` varchar(36) NOT NULL default '',
  `esa_ecptype_id` varchar(36) NOT NULL default '',
  `esa_cur_eff_date` date default NULL,
  `esa_cur_incr_rate` decimal(7,2) default NULL,
  `esa_cur_incr_rate1` decimal(7,2) default NULL,
  `esa_comments` varchar(255) default NULL,
  `esa_changed` int(10) unsigned NOT NULL default '0',
  `esa_status` int(10) unsigned NOT NULL default '1',
  `esa_need_gm_approve` int(10) unsigned default NULL,
  `esa_next_approver` varchar(36) default NULL,
  `esa_create_by` varchar(36) NOT NULL default '',
  `esa_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `esa_last_change_by` varchar(36) NOT NULL default '',
  `esa_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `esa_esav_id_cur` varchar(36) NOT NULL default '',
  `esa_esav_id_pro` varchar(36) NOT NULL default '',
  `esa_jobgrade_cur` varchar(36) NOT NULL default '',
  `esa_jobgrade_pro` varchar(36) NOT NULL default '',
  `esa_column1_cur` decimal(12,2) default NULL,
  `esa_column2_cur` decimal(12,2) default NULL,
  `esa_column3_cur` decimal(12,2) default NULL,
  `esa_column4_cur` decimal(12,2) default NULL,
  `esa_column5_cur` decimal(12,2) default NULL,
  `esa_column6_cur` decimal(12,2) default NULL,
  `esa_column7_cur` decimal(12,2) default NULL,
  `esa_column8_cur` decimal(12,2) default NULL,
  `esa_column9_cur` decimal(12,2) default NULL,
  `esa_column10_cur` decimal(12,2) default NULL,
  `esa_column11_cur` decimal(12,2) default NULL,
  `esa_column12_cur` decimal(12,2) default NULL,
  `esa_column13_cur` decimal(12,2) default NULL,
  `esa_column14_cur` decimal(12,2) default NULL,
  `esa_column15_cur` decimal(12,2) default NULL,
  `esa_column16_cur` decimal(12,2) default NULL,
  `esa_column17_cur` decimal(12,2) default NULL,
  `esa_column18_cur` decimal(12,2) default NULL,
  `esa_column19_cur` decimal(12,2) default NULL,
  `esa_column20_cur` decimal(12,2) default NULL,
  `esa_column21_cur` decimal(12,2) default NULL,
  `esa_column22_cur` decimal(12,2) default NULL,
  `esa_column23_cur` decimal(12,2) default NULL,
  `esa_column24_cur` decimal(12,2) default NULL,
  `esa_column25_cur` decimal(12,2) default NULL,
  `esa_column26_cur` decimal(12,2) default NULL,
  `esa_column27_cur` decimal(12,2) default NULL,
  `esa_column28_cur` decimal(12,2) default NULL,
  `esa_column29_cur` decimal(12,2) default NULL,
  `esa_column30_cur` decimal(12,2) default NULL,
  `esa_column31_cur` decimal(12,2) default NULL,
  `esa_column32_cur` decimal(12,2) default NULL,
  `esa_column33_cur` decimal(12,2) default NULL,
  `esa_column34_cur` decimal(12,2) default NULL,
  `esa_column35_cur` decimal(12,2) default NULL,
  `esa_column36_cur` decimal(12,2) default NULL,
  `esa_column37_cur` decimal(12,2) default NULL,
  `esa_column38_cur` decimal(12,2) default NULL,
  `esa_column39_cur` decimal(12,2) default NULL,
  `esa_column40_cur` decimal(12,2) default NULL,
  `esa_column41_cur` decimal(12,2) default NULL,
  `esa_column42_cur` decimal(12,2) default NULL,
  `esa_column43_cur` decimal(12,2) default NULL,
  `esa_column44_cur` decimal(12,2) default NULL,
  `esa_column45_cur` decimal(12,2) default NULL,
  `esa_column46_cur` decimal(12,2) default NULL,
  `esa_column47_cur` decimal(12,2) default NULL,
  `esa_column48_cur` decimal(12,2) default NULL,
  `esa_column1_pro` decimal(12,2) default NULL,
  `esa_column2_pro` decimal(12,2) default NULL,
  `esa_column3_pro` decimal(12,2) default NULL,
  `esa_column4_pro` decimal(12,2) default NULL,
  `esa_column5_pro` decimal(12,2) default NULL,
  `esa_column6_pro` decimal(12,2) default NULL,
  `esa_column7_pro` decimal(12,2) default NULL,
  `esa_column8_pro` decimal(12,2) default NULL,
  `esa_column9_pro` decimal(12,2) default NULL,
  `esa_column10_pro` decimal(12,2) default NULL,
  `esa_column11_pro` decimal(12,2) default NULL,
  `esa_column12_pro` decimal(12,2) default NULL,
  `esa_column13_pro` decimal(12,2) default NULL,
  `esa_column14_pro` decimal(12,2) default NULL,
  `esa_column15_pro` decimal(12,2) default NULL,
  `esa_column16_pro` decimal(12,2) default NULL,
  `esa_column17_pro` decimal(12,2) default NULL,
  `esa_column18_pro` decimal(12,2) default NULL,
  `esa_column19_pro` decimal(12,2) default NULL,
  `esa_column20_pro` decimal(12,2) default NULL,
  `esa_column21_pro` decimal(12,2) default NULL,
  `esa_column22_pro` decimal(12,2) default NULL,
  `esa_column23_pro` decimal(12,2) default NULL,
  `esa_column24_pro` decimal(12,2) default NULL,
  `esa_column25_pro` decimal(12,2) default NULL,
  `esa_column26_pro` decimal(12,2) default NULL,
  `esa_column27_pro` decimal(12,2) default NULL,
  `esa_column28_pro` decimal(12,2) default NULL,
  `esa_column29_pro` decimal(12,2) default NULL,
  `esa_column30_pro` decimal(12,2) default NULL,
  `esa_column31_pro` decimal(12,2) default NULL,
  `esa_column32_pro` decimal(12,2) default NULL,
  `esa_column33_pro` decimal(12,2) default NULL,
  `esa_column34_pro` decimal(12,2) default NULL,
  `esa_column35_pro` decimal(12,2) default NULL,
  `esa_column36_pro` decimal(12,2) default NULL,
  `esa_column37_pro` decimal(12,2) default NULL,
  `esa_column38_pro` decimal(12,2) default NULL,
  `esa_column39_pro` decimal(12,2) default NULL,
  `esa_column40_pro` decimal(12,2) default NULL,
  `esa_column41_pro` decimal(12,2) default NULL,
  `esa_column42_pro` decimal(12,2) default NULL,
  `esa_column43_pro` decimal(12,2) default NULL,
  `esa_column44_pro` decimal(12,2) default NULL,
  `esa_column45_pro` decimal(12,2) default NULL,
  `esa_column46_pro` decimal(12,2) default NULL,
  `esa_column47_pro` decimal(12,2) default NULL,
  `esa_column48_pro` decimal(12,2) default NULL,
  PRIMARY KEY  (`esa_id`),
  KEY `FK_esa_ecptype_id` (`esa_ecptype_id`),
  KEY `FK_esa_jobgrade_cur` (`esa_jobgrade_cur`),
  KEY `FK_esa_jobgrade_pro` (`esa_jobgrade_pro`),
  KEY `FK_esa_next_approver` (`esa_next_approver`),
  CONSTRAINT `FK_esa_ecptype_id` FOREIGN KEY (`esa_ecptype_id`) REFERENCES `ecptype` (`ecptype_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esa_jobgrade_cur` FOREIGN KEY (`esa_jobgrade_cur`) REFERENCES `jobgrade` (`jobgrade_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esa_jobgrade_pro` FOREIGN KEY (`esa_jobgrade_pro`) REFERENCES `jobgrade` (`jobgrade_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esa_next_approver` FOREIGN KEY (`esa_next_approver`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryadj`
--

LOCK TABLES `empsalaryadj` WRITE;
/*!40000 ALTER TABLE `empsalaryadj` DISABLE KEYS */;
/*!40000 ALTER TABLE `empsalaryadj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryconfig`
--

DROP TABLE IF EXISTS `empsalaryconfig`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryconfig` (
  `esc_empno` varchar(36) NOT NULL default '',
  `esc_jobgrade` varchar(36) NOT NULL default '',
  `esc_last_eff_date` datetime default NULL,
  `esc_last_incr_rate` decimal(7,2) default NULL,
  `esc_last_incr_rate1` decimal(7,2) default NULL,
  `esc_esav_id` varchar(36) NOT NULL default '',
  `esc_bank_account_no` varchar(32) default NULL,
  `esc_bank_name` varchar(64) default NULL,
  `esc_cost_center` varchar(32) default NULL,
  `esc_create_by` varchar(36) NOT NULL default '',
  `esc_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `esc_last_change_by` varchar(36) NOT NULL default '',
  `esc_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `esc_column1` decimal(12,2) default NULL,
  `esc_column2` decimal(12,2) default NULL,
  `esc_column3` decimal(12,2) default NULL,
  `esc_column4` decimal(12,2) default NULL,
  `esc_column5` decimal(12,2) default NULL,
  `esc_column6` decimal(12,2) default NULL,
  `esc_column7` decimal(12,2) default NULL,
  `esc_column8` decimal(12,2) default NULL,
  `esc_column9` decimal(12,2) default NULL,
  `esc_column10` decimal(12,2) default NULL,
  `esc_column11` decimal(12,2) default NULL,
  `esc_column12` decimal(12,2) default NULL,
  `esc_column13` decimal(12,2) default NULL,
  `esc_column14` decimal(12,2) default NULL,
  `esc_column15` decimal(12,2) default NULL,
  `esc_column16` decimal(12,2) default NULL,
  `esc_column17` decimal(12,2) default NULL,
  `esc_column18` decimal(12,2) default NULL,
  `esc_column19` decimal(12,2) default NULL,
  `esc_column20` decimal(12,2) default NULL,
  `esc_column21` decimal(12,2) default NULL,
  `esc_column22` decimal(12,2) default NULL,
  `esc_column23` decimal(12,2) default NULL,
  `esc_column24` decimal(12,2) default NULL,
  `esc_column25` decimal(12,2) default NULL,
  `esc_column26` decimal(12,2) default NULL,
  `esc_column27` decimal(12,2) default NULL,
  `esc_column28` decimal(12,2) default NULL,
  `esc_column29` decimal(12,2) default NULL,
  `esc_column30` decimal(12,2) default NULL,
  `esc_column31` decimal(12,2) default NULL,
  `esc_column32` decimal(12,2) default NULL,
  `esc_column33` decimal(12,2) default NULL,
  `esc_column34` decimal(12,2) default NULL,
  `esc_column35` decimal(12,2) default NULL,
  `esc_column36` decimal(12,2) default NULL,
  `esc_column37` decimal(12,2) default NULL,
  `esc_column38` decimal(12,2) default NULL,
  `esc_column39` decimal(12,2) default NULL,
  `esc_column40` decimal(12,2) default NULL,
  `esc_column41` decimal(12,2) default NULL,
  `esc_column42` decimal(12,2) default NULL,
  `esc_column43` decimal(12,2) default NULL,
  `esc_column44` decimal(12,2) default NULL,
  `esc_column45` decimal(12,2) default NULL,
  `esc_column46` decimal(12,2) default NULL,
  `esc_column47` decimal(12,2) default NULL,
  `esc_column48` decimal(12,2) default NULL,
  PRIMARY KEY  (`esc_empno`),
  KEY `FK_esc_last_change_by` (`esc_last_change_by`),
  KEY `FK_esc_create_by` (`esc_create_by`),
  KEY `FK_esc_jobgrade1` (`esc_jobgrade`),
  CONSTRAINT `FK_esc_create_by` FOREIGN KEY (`esc_create_by`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esc_jobgrade1` FOREIGN KEY (`esc_jobgrade`) REFERENCES `jobgrade` (`jobgrade_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esc_last_change_by` FOREIGN KEY (`esc_last_change_by`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryconfig`
--

LOCK TABLES `empsalaryconfig` WRITE;
/*!40000 ALTER TABLE `empsalaryconfig` DISABLE KEYS */;
/*!40000 ALTER TABLE `empsalaryconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalarydatadef`
--

DROP TABLE IF EXISTS `empsalarydatadef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalarydatadef` (
  `esdd_id` varchar(36) NOT NULL default '',
  `esdd_name` varchar(64) NOT NULL default '',
  `esdd_desc` varchar(128) default NULL,
  `esdd_data_type` int(10) unsigned NOT NULL default '0',
  `esdd_data_is_calc` int(10) unsigned default NULL,
  `esdd_data_rounding` int(10) unsigned default NULL,
  `esdd_data_length` int(10) unsigned default NULL,
  `esdd_sort_id` int(10) unsigned NOT NULL default '0',
  `esdd_data_output` int(10) unsigned default NULL COMMENT '项目导出格式',
  PRIMARY KEY  (`esdd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalarydatadef`
--

LOCK TABLES `empsalarydatadef` WRITE;
/*!40000 ALTER TABLE `empsalarydatadef` DISABLE KEYS */;
INSERT INTO `empsalarydatadef` VALUES ('4028804a1a9a34bf011a9aa2e81d000e','年终奖所得税','年终奖所得税',18,2,0,NULL,45,1),('4028804a1a9a34bf011a9aa3f861000f','年终奖','年终奖',6,1,0,NULL,22,1),('402880e618c1299f0118c12bf7d9001b','基本工资','基本工资',1,0,0,NULL,4,1),('402880e618c1299f0118c12bf7d9002b','租房补贴','租房补贴',3,0,0,NULL,8,1),('402880e618c1299f0118c12bf7d9003b','其他补贴','其他补贴',3,0,0,NULL,9,1),('402880e618c1299f0118c12bf7d9005b','本月加班(小时)','本月加班(小时)',5,1,0,NULL,20,1),('402880e618c1299f0118c12bf7d9006b','加班费','加班费',6,2,0,NULL,21,1),('402880e618c1299f0118c12bf7d9007b','本月缺勤(天)','本月缺勤(天)',5,1,0,NULL,13,1),('402880e618c1299f0118c12bf7d9008b','请假扣款','请假扣款',6,2,0,NULL,17,1),('402880e618c1299f0118c12bf7d9010b','浮动项目总额','浮动项目总额',7,2,0,NULL,24,1),('402880e618c1299f0118c12bf7d9011b','税前收入','税前收入',8,2,0,NULL,25,1),('402880e618c1299f0118c12bf7d9012b','所得税','所得税',18,2,0,NULL,44,1),('402880e618c1299f0118c12bf7d9013b','税后收入','税后收入',19,2,0,NULL,47,1),('402880e618c1299f0118c12bf7d9014b','职务津贴','职务津贴',3,0,0,NULL,7,1),('402880e618c1299f0118c12bf7d9015b','技能津贴','技能津贴',3,0,0,NULL,6,1),('402880e618c1299f0118c12bf7d9016b','固定项目总额','固定项目总额',4,2,0,NULL,10,1),('402880e618c1299f0118c12bf7d9018b','本月出勤(天)','本月出勤(天)',5,1,0,NULL,12,1),('402880e618c1299f0118c12bf7d9019b','全勤奖','全勤奖',6,2,0,NULL,14,1),('402880e618c1299f0118c12bf7d9020b','本月出差(天)','本月出差(天)',5,1,0,NULL,18,1),('402880e618c1299f0118c12bf7d9021b','出差津贴','出差津贴',6,2,0,NULL,19,1),('402880e618c1299f0118c12bf7d9022b','业绩奖金','业绩奖金',6,1,0,NULL,11,1),('402880e618c1299f0118c12bf7d9023b','本月事假(天)','本月事假(天)',5,1,0,NULL,15,1),('402880e618c1299f0118c12bf7d9024b','本月病假(天)','本月病假(天)',5,1,0,NULL,16,1),('402880e618c1299f0118c12bf7d9025b','社保基数','社保基数',9,0,0,NULL,26,1),('402880e618c1299f0118c12bf7d9026b','公积金基数','公积金基数',9,0,0,NULL,27,1),('402880e618c1299f0118c12bf7d9027b','养老保险','养老保险（个人缴）',10,2,0,NULL,28,1),('402880e618c1299f0118c12bf7d9028b','医疗保险','医疗保险（个人缴）',10,2,0,NULL,30,1),('402880e618c1299f0118c12bf7d9029b','失业保险','失业保险（个人缴）',10,2,0,NULL,29,1),('402880e618c1299f0118c12bf7d9030b','公积金','公积金（个人缴）',12,2,0,NULL,33,1),('402880e618c1299f0118c12bf7d9031b','社保总额','社保总额（个人缴）',15,2,0,NULL,34,1),('402880e618c1299f0118c12bf7d9032b','其他福利扣款','其他福利扣款',14,0,0,NULL,43,1),('402880e618c1299f0118c12bf7d9033b','税收调整','其他税收调整项目',18,1,0,NULL,46,1),('402880e618c1299f0118c12bf7d9034b','代缴社保总额','社保总额（公司代缴）',16,2,0,NULL,41,1),('402880e618c1299f0118c12bf7d9035b','日薪','日薪',3,0,0,NULL,2,1),('402880e618c1299f0118c12bf7d9036b','本月计薪天数','本月计薪天数',3,0,0,NULL,3,1),('402880e618c1299f0118c12bf7d9037b','其他奖励','其他奖励',6,1,0,NULL,23,1),('402880e618c1299f0118c12bf7d9038b','固定补贴','固定补贴',3,0,0,NULL,5,1),('402880e618c1299f0118c12bf7d9039b','日薪基数','日薪基数',2,0,0,NULL,1,1),('402880e81d035424011d043ae2710003','代缴工伤保险','工伤保险（公司缴）',11,2,0,NULL,38,1),('402880e81d035424011d043ba5630004','代缴生育保险','生育保险（公司缴）',11,2,0,NULL,39,1),('402880ea1cdf262b011ce07a59460051','代缴失业保险','失业保险（公司代缴）',11,2,0,NULL,36,1),('402880ea1cdf262b011ce07c25e60052','免税额度','免税额度（公司承担）',14,0,0,NULL,42,1),('81891c37-eb71-4ad3-b55a-536095519c2d','应纳税所得额','应纳税所得额（税前-个人缴）',17,2,0,NULL,39,1),('ff8080811c74a505011c74b013a40001','代缴养老保险','养老保险（公司代缴）',11,2,0,NULL,35,1),('ff8080811c74a505011c74b15a0e0002','代缴公积金','公积金（公司代缴）',13,2,0,NULL,40,1),('ff8080811c74a505011c74b1f41c0003','代缴医疗保险','医疗保险（公司代缴）',11,2,0,NULL,37,1);
/*!40000 ALTER TABLE `empsalarydatadef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalarypay`
--

DROP TABLE IF EXISTS `empsalarypay`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalarypay` (
  `esp_id` varchar(36) NOT NULL default '',
  `esp_empno` varchar(36) NOT NULL default '',
  `esp_emptype` varchar(36) NOT NULL default '',
  `esp_dept` varchar(36) NOT NULL default '',
  `esp_location` varchar(36) NOT NULL default '',
  `esp_pb_no` varchar(36) default NULL,
  `esp_jobgrade` varchar(36) default NULL,
  `esp_bank_name` varchar(64) default NULL,
  `esp_bank_account_no` varchar(32) default NULL,
  `esp_cost_center` varchar(32) default NULL,
  `esp_yearmonth` varchar(6) NOT NULL default '',
  `esp_year` varchar(4) NOT NULL default '',
  `esp_month` varchar(2) NOT NULL default '',
  `esp_esav_id` varchar(36) NOT NULL default '',
  `esp_benefit_plans` int(10) unsigned NOT NULL default '0',
  `esp_changed` int(10) unsigned NOT NULL default '0',
  `esp_comment` varchar(255) default NULL,
  `esp_column1` decimal(12,2) default NULL,
  `esp_column2` decimal(12,2) default NULL,
  `esp_column3` decimal(12,2) default NULL,
  `esp_column4` decimal(12,2) default NULL,
  `esp_column5` decimal(12,2) default NULL,
  `esp_column6` decimal(12,2) default NULL,
  `esp_column7` decimal(12,2) default NULL,
  `esp_column8` decimal(12,2) default NULL,
  `esp_column9` decimal(12,2) default NULL,
  `esp_column10` decimal(12,2) default NULL,
  `esp_column11` decimal(12,2) default NULL,
  `esp_column12` decimal(12,2) default NULL,
  `esp_column13` decimal(12,2) default NULL,
  `esp_column14` decimal(12,2) default NULL,
  `esp_column15` decimal(12,2) default NULL,
  `esp_column16` decimal(12,2) default NULL,
  `esp_column17` decimal(12,2) default NULL,
  `esp_column18` decimal(12,2) default NULL,
  `esp_column19` decimal(12,2) default NULL,
  `esp_column20` decimal(12,2) default NULL,
  `esp_column21` decimal(12,2) default NULL,
  `esp_column22` decimal(12,2) default NULL,
  `esp_column23` decimal(12,2) default NULL,
  `esp_column24` decimal(12,2) default NULL,
  `esp_column25` decimal(12,2) default NULL,
  `esp_column26` decimal(12,2) default NULL,
  `esp_column27` decimal(12,2) default NULL,
  `esp_column28` decimal(12,2) default NULL,
  `esp_column29` decimal(12,2) default NULL,
  `esp_column30` decimal(12,2) default NULL,
  `esp_column31` decimal(12,2) default NULL,
  `esp_column32` decimal(12,2) default NULL,
  `esp_column33` decimal(12,2) default NULL,
  `esp_column34` decimal(12,2) default NULL,
  `esp_column35` decimal(12,2) default NULL,
  `esp_column36` decimal(12,2) default NULL,
  `esp_column37` decimal(12,2) default NULL,
  `esp_column38` decimal(12,2) default NULL,
  `esp_column39` decimal(12,2) default NULL,
  `esp_column40` decimal(12,2) default NULL,
  `esp_column41` decimal(12,2) default NULL,
  `esp_column42` decimal(12,2) default NULL,
  `esp_column43` decimal(12,2) default NULL,
  `esp_column44` decimal(12,2) default NULL,
  `esp_column45` decimal(12,2) default NULL,
  `esp_column46` decimal(12,2) default NULL,
  `esp_column47` decimal(12,2) default NULL,
  `esp_column48` decimal(12,2) default NULL,
  PRIMARY KEY  (`esp_id`),
  KEY `FK_esp_empno` (`esp_empno`),
  KEY `FK_esp_emptype` (`esp_emptype`),
  KEY `FK_esp_dept` (`esp_dept`),
  KEY `FK_esp_location` (`esp_location`),
  KEY `FK_esp_jobgrade` (`esp_jobgrade`),
  KEY `FK_esp_pb_no` (`esp_pb_no`),
  CONSTRAINT `FK_esp_pb_no` FOREIGN KEY (`esp_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esp_dept` FOREIGN KEY (`esp_dept`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esp_empno` FOREIGN KEY (`esp_empno`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esp_emptype` FOREIGN KEY (`esp_emptype`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esp_jobgrade` FOREIGN KEY (`esp_jobgrade`) REFERENCES `jobgrade` (`jobgrade_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_esp_location` FOREIGN KEY (`esp_location`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalarypay`
--

LOCK TABLES `empsalarypay` WRITE;
/*!40000 ALTER TABLE `empsalarypay` DISABLE KEYS */;
/*!40000 ALTER TABLE `empsalarypay` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empsalaryperiod`
--

DROP TABLE IF EXISTS `empsalaryperiod`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empsalaryperiod` (
  `espd_id` varchar(36) NOT NULL default '',
  `espd_yearmonth` varchar(6) NOT NULL default '',
  `espd_status` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`espd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empsalaryperiod`
--

LOCK TABLES `empsalaryperiod` WRITE;
/*!40000 ALTER TABLE `empsalaryperiod` DISABLE KEYS */;
/*!40000 ALTER TABLE `empsalaryperiod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empshift`
--

DROP TABLE IF EXISTS `empshift`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `empshift` (
  `empshift_id` varchar(36) NOT NULL default '',
  `empshift_emp_no` varchar(36) NOT NULL default '',
  `empshift_shift_no` varchar(36) NOT NULL default '',
  `empshift_date` date NOT NULL default '0000-00-00',
  `empshift_create_by` varchar(36) default NULL,
  `empshift_create_time` datetime default NULL,
  `empshift_last_change_by` varchar(36) default NULL,
  `empshift_last_change_time` datetime default NULL,
  PRIMARY KEY  (`empshift_id`),
  KEY `FK_empshift_emp_no` (`empshift_emp_no`),
  KEY `FK_empshift_shift_no` (`empshift_shift_no`),
  CONSTRAINT `FK_empshift_emp_no` FOREIGN KEY (`empshift_emp_no`) REFERENCES `employee` (`emp_no`),
  CONSTRAINT `FK_empshift_shift_no` FOREIGN KEY (`empshift_shift_no`) REFERENCES `attendshift` (`atts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `empshift`
--

LOCK TABLES `empshift` WRITE;
/*!40000 ALTER TABLE `empshift` DISABLE KEYS */;
/*!40000 ALTER TABLE `empshift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emptransfer`
--

DROP TABLE IF EXISTS `emptransfer`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emptransfer` (
  `eft_id` varchar(36) NOT NULL default '',
  `eft_emp_no` varchar(36) NOT NULL default '',
  `eft_transfer_date` date default NULL,
  `eft_transfer_type` varchar(16) NOT NULL default '',
  `eft_old_dept_no` varchar(36) default NULL,
  `eft_new_dept_no` varchar(36) default NULL,
  `eft_old_emp_type` varchar(36) default NULL,
  `eft_new_emp_type` varchar(36) default NULL,
  `eft_old_pb_no` varchar(36) default NULL,
  `eft_new_pb_no` varchar(36) default NULL,
  `eft_reason` varchar(128) default NULL,
  `eft_comments` varchar(255) NOT NULL default '',
  `eft_create_by` varchar(36) NOT NULL default '',
  `eft_create_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `eft_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `eft_last_change_by` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`eft_id`),
  KEY `FK_eft_emp_no` (`eft_emp_no`),
  KEY `FK_eft_old_dept_no` (`eft_old_dept_no`),
  KEY `FK_eft_new_dept_no` (`eft_new_dept_no`),
  KEY `FK_eft_old_emp_type` (`eft_old_emp_type`),
  KEY `FK_eft_new_emp_type` (`eft_new_emp_type`),
  KEY `FK_eft_old_pb_no` (`eft_old_pb_no`),
  KEY `FK_eft_new_pb_no` (`eft_new_pb_no`),
  CONSTRAINT `FK_eft_emp_no` FOREIGN KEY (`eft_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_new_dept_no` FOREIGN KEY (`eft_new_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_new_emp_type` FOREIGN KEY (`eft_new_emp_type`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_new_pb_no` FOREIGN KEY (`eft_new_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_old_dept_no` FOREIGN KEY (`eft_old_dept_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_old_emp_type` FOREIGN KEY (`eft_old_emp_type`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_eft_old_pb_no` FOREIGN KEY (`eft_old_pb_no`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emptransfer`
--

LOCK TABLES `emptransfer` WRITE;
/*!40000 ALTER TABLE `emptransfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `emptransfer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emptype`
--

DROP TABLE IF EXISTS `emptype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `emptype` (
  `emptype_no` varchar(36) NOT NULL default '',
  `emptype_name` varchar(64) NOT NULL default '',
  `emptype_sortid` int(10) unsigned NOT NULL default '0',
  `emptype_desc` varchar(128) default NULL,
  `emptype_status` int(10) unsigned NOT NULL default '1',
  PRIMARY KEY  (`emptype_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `emptype`
--

LOCK TABLES `emptype` WRITE;
/*!40000 ALTER TABLE `emptype` DISABLE KEYS */;
INSERT INTO `emptype` VALUES ('186c3582-1a42-4628-b2a0-c58c2faaa571','外包员工',2,'Contractor',1),('5038536e-3ab9-47e2-b8e8-6a37adbcbcd1','实习生',3,'Internship',1),('a78ca955-858d-4c3c-a0f9-3405436ebe61','正式员工',1,'Regular Employees',1),('f5171a35-4936-4b82-8fae-7e65402f5101','兼职',4,'Temporary Employees',1);
/*!40000 ALTER TABLE `emptype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `help`
--

DROP TABLE IF EXISTS `help`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `help` (
  `help_id` varchar(36) NOT NULL default '',
  `help_title` varchar(64) NOT NULL default '',
  `help_desc` mediumtext NOT NULL,
  `help_class_id` varchar(36) NOT NULL default '0',
  PRIMARY KEY  (`help_id`),
  KEY `FK_help_class_id` (`help_class_id`),
  CONSTRAINT `FK_help_class_id` FOREIGN KEY (`help_class_id`) REFERENCES `helpclass` (`hc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `help`
--

LOCK TABLES `help` WRITE;
/*!40000 ALTER TABLE `help` DISABLE KEYS */;
INSERT INTO `help` VALUES ('1','查找用户','<p>这里有4个条件选项，可以设置“员工”，“员工角色”，“部门”，“经理名称”作为查询条件进行查询。“员工”选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有用户相关信息。<br />\r\n<img src=\"helpimages/system_searchT.gif\" alt=\"\" /></p>','7'),('10','我的加班列表-删除加班','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;后确认就可以删除对应记录。可删除前提：可以删除状态为已拒绝状态的加班申请。普通员工还可以删除已提交装态的加班申请，部门经理可以删除部门已审状态的加班申请，总经理可以删除总经理已审状态的加班申请。</p>','14'),('100','快速修改','<p>点击<img alt=\"\" src=\"helpimages/system_fastMf.gif\" />按钮 ，弹出快速修改层<br />\r\n<img alt=\"\" src=\"helpimages/system_fastMfInfo.gif\" /><br />\r\n填写&ldquo;标题&rdquo;，&ldquo;简述&rdquo;，&ldquo;正文&rdquo;点击<img alt=\"\" src=\"helpimages/system_modify.gif\" />按钮，完成修改操作。点击<img alt=\"\" src=\"helpimages/common_clear.gif\" />按钮 清空所填写的信息。点击<img alt=\"\" src=\"helpimages/common_close.gif\" />按钮 关闭层，显示信息查看页面。</p>','13'),('101','完全修改','<p>点击<img alt=\"\" src=\"helpimages/system_completeMf.gif\" />按钮进入完全修改页面。<br />\r\n隶属分类：标记信息属于那个类型。所属部门信息：标记该新闻是反映那个部门的信息。点击右侧的<img alt=\"\" src=\"helpimages/system_addInfoLk.gif\" />链接，进入新增信息界面。 选择&ldquo;隶属分类&rdquo;，&ldquo;状态信息&rdquo;，&ldquo;所属部门信息&rdquo; <img alt=\"\" src=\"helpimages/system_selectDept.gif\" />，填写&ldquo;标题&rdquo;，&ldquo;简述&rdquo;，&ldquo;正文&rdquo;，点击 <img alt=\"\" src=\"helpimages/common_view.gif\" />按钮，选择上传图片和上传附件，填写图片说明和附件说明，点击<img alt=\"\" src=\"helpimages/system_addInfo.gif\" />按钮 ，完成新增信息操作，进入信息查看页面。点击<img alt=\"\" src=\"helpimages/system_back.gif\" />按钮 放弃当前操作，进入信息查看页面。</p>','13'),('102','删除信息','<p>点击 <u>删除</u> 按钮 ，点击确认对话框的<img alt=\"\" src=\"helpimages/common_confirmB.gif\" />按钮，删除相应信息。</p>','13'),('103','上传文件','<p><img alt=\"\" src=\"helpimages/profile_upLoad.gif\" /> <br />\r\n文件名称：选择要上传的文件名称。导入信息含标题：选中表示导入的excel文件还有标题，导入的时候系统会自动匹配。否则用户手动取匹配。点击<img alt=\"\" src=\"helpimages/common_view.gif\" />按钮 选择要导入的文件（必须是excel文件），点击<img alt=\"\" src=\"helpimages/profile_upLoadB.gif\" />按钮进入&ldquo;信息匹配&rdquo;页面。</p>','35'),('104','信息匹配','<p>左侧选择列表存放数据的标题<img alt=\"\" src=\"helpimages/profile_selectTl.gif\" />，后面为三列数据表示excel表中的三组数据。包含功能 匹配检查，导入数据，检查数据。匹配检查：用户在左侧列表中选择标题 ，每选择一次就会对选择的数据进行匹配检查，只有检查通过用户才可以继续操作。</p>','35'),('105','导入数据','<p>用户点击<img alt=\"\" src=\"helpimages/profile_post.gif\" />按钮 ，系统将上传文件导入数据库，如果出错会进入&ldquo;错误提示&rdquo;页面，详见&ldquo;错误提示&rdquo;</p>','35'),('106','检查数据','<p>用户点击<img alt=\"\" src=\"helpimages/profile_check.gif\" />按钮 ，系统将检查数据的正确性。如果出错会进入&ldquo;错误提示&rdquo;页面，详见&ldquo;错误提示&rdquo; 如果正确的话，点击<img alt=\"\" src=\"helpimages/profile_postB.gif\" />按钮 ，将数据导入数据库。</p>','35'),('107','错误提示','<p><img alt=\"\" src=\"helpimages/profile_errorList.gif\" /><br />\r\n逻辑错误：数据中的逻辑错误。包括 &ldquo;出错行数&rdquo; ，&ldquo;员工编号&rdquo;，&ldquo;出错类型&rdquo;，&ldquo;错误描述&rdquo;。出错行数：提示出错在excel表的第几行。出错类型：提示错误类型。错误描述：出错的详细描述，以及改正建议。匹配错误：数据中的匹配错误。包括 &ldquo;出错行数&rdquo; ，&ldquo;匹配字段名称&rdquo;， &ldquo;错误描述&rdquo;。出错行数：提示出错在excel表的第几行。匹配字段名称：提示出错的匹配字段。错误描述：出错的详细描述，以及改正建议。</p>','35'),('11','我的请假列表-查看请假','<p>点击对应的请假单编号显示对应的请假单详细信息。点击关闭按钮，关闭对应的请假详情显示。&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_leave_content.bmp\" />&rdquo;</p>','14'),('113','查询薪资发放','<p>这里有4个条件选项，可以设置“员工”， “部门”， “业务单元”，“年月”作为查询条件进行查询。“员工”选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。默认的查询条件是所有符合条件的员工，所有部门，所有业务单元，当前年月。点击“重置”，即可显示默认查询条件下的薪资发放信息。</p>','26'),('114','初始化薪资发放项目','<p>第一次查询某个年月的薪资发放时，系统会提出警告，该月的薪资发放不存在，是否要初始化，选择“是”的情况下系统开始初始化该月的薪资发放。初始化结束后会显示所有符合该月薪资发放条件的员工的薪资信息。此时该月的薪资发放为草稿状态。</p>','26'),('115','查看/修改薪资发放项目','<p>初始化后，可以在列表中查看基本工资，固定项总额，浮动项总额，税前总收入，福利项总额，所得税，税后收入；也可点击基本工资后的“<img src=\"helpimages/Search.gif\" alt=\"\" />”图标填写详细薪资资料以及薪资发放备注，红色图标“<img src=\"helpimages/Search_change.gif\" alt=\"\" />”说明该员工的薪资发放数据和薪资配置数据不同。点击“提交”按钮后会自动计算并保存薪资数据。点击“重新计算薪资”按钮后系统会计算薪资数据但不做保存，点击“重置”按钮后系统将恢复到该员工的配置薪资但不做保存，点击“关闭”将关闭弹出页面。点击员工姓名，可查看该员工的薪资配置。如果是已经提交的薪资发放数据或者已经批准的薪资发放数据，则只能查看相关薪资信息而不能修改。</p>','26'),('116','重新计算薪资发放信息','<p>点击页面上的“重新计算薪资”按钮，系统将重新计算并保存所有员工的薪资，之前所做的一切薪资改动将被覆盖。只有未提交的薪资发放状态下才能重新计算。</p>','26'),('117','删除薪资发放信息','<p>点击操作栏的“<img alt=\"\" src=\"helpimages/common_del.gif\" />”图标，选择确定删除后，系统会删除该员工当月的薪资信息，之后将不列入统计。</p>','26'),('118','提交薪资发放','<p>点击页面上的“封帐申请”按钮，所有员工的薪资信息以及汇总信息将显示在薪资发放提交列表中，确认无误后点击“确认提交”按钮，该月的薪资发放信息将被提交至HRM审批，此时该月的薪资发放为提交状态。若发现有误，可点击“返回”按钮回到薪资发放列表中做修改。</p>','26'),('119','审核薪资发放','<p>当薪资发放提交后，HRM点击页面上的“审核”按钮，所有员工的薪资信息以及汇总信息将显示在薪资发放审核列表中，确认无误后点击“审批通过”将确认该月的薪资发放信息封帐；若发现有误，可以点击“解封”按钮列将薪资发退回为草稿状态重新修正。</p>','26'),('12','我的加班列表-查看加班','<p>点击对应的加班单编号显示对应的加班单详细信息。点击关闭按钮，关闭对应的加班详情显示。&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_overtime_content.bmp\" />&rdquo;</p>','14'),('120','上传文件','<p><img alt=\"\" src=\"helpimages/profile_upLoad.gif\" /> <br />\r\n文件名称：选择要上传的文件名称。导入信息含标题：选中表示导入的excel文件还有标题，导入的时候系统会自动匹配。否则用户手动取匹配。点击<img alt=\"\" src=\"helpimages/common_view.gif\" />按钮 选择要导入的文件（必须是excel文件），点击<img alt=\"\" src=\"helpimages/profile_upLoadB.gif\" />按钮进入&ldquo;信息匹配&rdquo;页面。</p>','28'),('121','信息匹配','<p>左侧选择列表存放数据的标题<img alt=\"\" src=\"helpimages/profile_selectTl.gif\" />，后面为三列数据表示excel表中的三组数据。包含功能 匹配检查，导入数据，检查数据。匹配检查：用户在左侧列表中选择标题 ，每选择一次就会对选择的数据进行匹配检查，只有检查通过用户才可以继续操作。</p>','28'),('122','导入数据','<p>用户点击<img alt=\"\" src=\"helpimages/profile_post.gif\" />按钮 ，系统将上传文件导入数据库，如果出错会进入&ldquo;错误提示&rdquo;页面，详见&ldquo;错误提示&rdquo;</p>','28'),('123','检查数据','<p>用户点击<img alt=\"\" src=\"helpimages/profile_check.gif\" />按钮 ，系统将检查数据的正确性。如果出错会进入&ldquo;错误提示&rdquo;页面，详见&ldquo;错误提示&rdquo; 如果正确的话，点击<img alt=\"\" src=\"helpimages/profile_postB.gif\" />按钮 ，将数据导入数据库。</p>','28'),('124','错误提示','<p><img alt=\"\" src=\"helpimages/profile_errorList.gif\" /><br />\r\n逻辑错误：数据中的逻辑错误。包括 &ldquo;出错行数&rdquo; ，&ldquo;员工编号&rdquo;，&ldquo;出错类型&rdquo;，&ldquo;错误描述&rdquo;。出错行数：提示出错在excel表的第几行。出错类型：提示错误类型。错误描述：出错的详细描述，以及改正建议。匹配错误：数据中的匹配错误。包括 &ldquo;出错行数&rdquo; ，&ldquo;匹配字段名称&rdquo;， &ldquo;错误描述&rdquo;。出错行数：提示出错在excel表的第几行。匹配字段名称：提示出错的匹配字段。错误描述：出错的详细描述，以及改正建议。</p>','28'),('125','查看信息','<p>用户可以在本页面查看薪资等级的信息。</p>','29'),('126','添加信息','<p>用户可以从每个列表的左下方找到新增按钮&ldquo;<img alt=\"\" src=\"helpimages/common_add.gif\" /> &rdquo;，点击以后将会弹出添加信息的界面层，在界面层中用户输入信息以后，可以点保存按钮&ldquo;<img alt=\"\" src=\"helpimages/common_save.gif\" /> &rdquo;来保存信息，点清空按钮&ldquo; <img alt=\"\" p=\"\" src=\"helpimages/common_clear.gif\" />&rdquo;可以清空已经输入的数据，点关闭 按钮&ldquo; <img alt=\"\" src=\"helpimages/common_close.gif\" />&rdquo;可以关闭添加界面层。</p>','29'),('127','删除信息','<p>用户可以单击列表行，单击某行后，此行会整行变黄， 然后点列表左下方的&ldquo;<img alt=\"\" src=\"helpimages/common_delete.gif\" /> &rdquo;来删除这行的数据。</p>','29'),('128','修改信息','<p>用户可以双击某行或点列表左下放的修改按钮&ldquo;<img alt=\"\" src=\"helpimages/common_update.gif\" />&rdquo;，点击以后将会弹出修改信息的界面层，在界面层中输入用户信息以后，可以点保存按钮&ldquo;<img alt=\"\" src=\"helpimages/common_save.gif\" />&rdquo;来保存修改后的数据，可以点清空按钮&ldquo;<img alt=\"\" src=\"helpimages/common_clear.gif\" />&rdquo;清空已经输入的数据，点关闭 按钮&ldquo;<img alt=\"\" src=\"helpimages/common_close.gif\" />&rdquo; 可以关闭添加界面层。</p>','29'),('129','查询调薪计划','<p><strong>基本查询:</strong>有5个条件选项，可以设置&ldquo;员工&rdquo;， &ldquo;部门&rdquo;， &ldquo;经理姓名&rdquo;，&ldquo;状态&rdquo;，&ldquo;显示历史调薪记录&rdquo;作为查询条件进行查询。&ldquo;员工&rdquo;选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有用户相关薪资配置信息。如果选择了&ldquo;显示历史调薪记录&rdquo;列表中将会列出状态为已调整和已作废的调薪计划。</p>\r\n<p><strong>高级查询:</strong>有9个条件选项, 可以设置&ldquo;员工姓名&rdquo;， &ldquo;员工编号&rdquo;，&ldquo;用工形式&rdquo;，&ldquo;部门&rdquo;，&rdquo;业务单元&ldquo;，&rdquo;员工状态&ldquo;，&ldquo;计划状态&rdquo;，&rdquo;工作地点&ldquo;，&ldquo;经理姓名&rdquo;作为查询条件进行查询。点击<img alt=\"\" src=\"helpimages/common_basic_search.gif\" />，可以进行基本查询和高级查询的切换。</p>\r\n<p><strong>查看方式:</strong>用户可以选择查看全部和分页查看，两种方式，查看全部将会在当前页显示所有调薪计划，分页查看每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图<img alt=\"\" src=\"helpimages/common_page.gif\" />，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p><strong>排序:</strong>点击列表上的粗体字段名，如&ldquo;<img alt=\"\" src=\"helpimages/common_sort.gif\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），点击以后，将会出现<img alt=\"\" src=\"helpimages/common_sort_asc.gif\" />表示升序，再次点击，将会出现<img alt=\"\" src=\"helpimages/common_sort_desc.gif\" />表示降序。</p>','25'),('13','我的请假列表-删除请假','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;后确认就可以删除对应记录。可删除前提：可以删除状态为已拒绝状态的请假申请。普通员工还可以删除已提交装态的请假申请，部门经理可以删除部门已审状态的请假申请，总经理可以删除总经理已审状态的请假申请。</p>','14'),('130','作废调薪计划','<p>用户可以对状态为HR已审的调薪计划进行作废操作，用户可以点操作中的作废按钮&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;，这时将会弹出作废调薪计划的界面层，当输入必填的备注信息以后，点&ldquo;作废调薪计划&rdquo;的按钮就可以作废此条调薪计划，点&ldquo;关闭&rdquo;按钮或界面层右上角的叉形按钮可以放弃作废此条调薪计划，同时关闭界面层。</p>','25'),('131','手工调整调薪计划','<p>用户可以对状态为HR已审的调薪计划进操作，用户可以点操作中的手工调整按钮&ldquo;<img alt=\"\" src=\"helpimages/comp_add_salaryconf.gif\" />&rdquo;，这时将会弹出确认对话框，点&ldquo;确定&rdquo;按钮就可以调整此调薪计划所涉及的员工的薪资同时调薪计划的状态变为已调整，点&ldquo;取消&rdquo;按钮可以放弃手工调整此条调薪计划。</p>','25'),('132','查看员工薪资配置的详细信息','<p>点击列表中位于员工姓名上的链接，如&ldquo;<u>杨国强</u>&rdquo;，会进入显示员工薪资配置详细信息的页面。</p>','25'),('133','查看薪资汇总信息','<p>当用户选择了经理姓名或部门的查询条件进行查询以后，在列表的最后一行将会出现，薪资的统计信息。</p>','25'),('134','查看员工的基本信息','<p>当用户把鼠标移动到员工姓名上时，将会显示该员工的员工基本信息，如下图：<br><img alt=\"\" src=\"helpimages/comp_employeeinfo.gif\" /></p>','25'),('135','查看调薪计划的日志记录','<p>点击某条调薪计划的状态如&ldquo;部门已审&rdquo;可以弹出此条调薪计划的日志层。如下图：<img alt=\"\" src=\"helpimages/common_log.gif\" /></p>','25'),('136','调薪计划数据导出','<p>点击位于列表左上角的&ldquo;<img alt=\"\" src=\"helpimages/common_export.gif\" />&rdquo;按钮，将会弹出一个对话框，点击&ldquo;打开&rdquo;按钮将会打开含有调薪计划信息的EXCEL文件，点击&ldquo;保存&rdquo;按钮将会先保存含有调薪计划信息的EXCEL文件到用户的计算机中，然后打开此文件，点击&ldquo;取消&rdquo;按钮放弃数据导出的操作。</p>','25'),('137','我的资料->基本资料','<p><span style=\"font-size: 9pt; color: black; font-family: 宋体\">包含所有的员工基本信息,管理员可以修改除考评结果、考评日期、状态和最后修改时间以外的其他所有字段，只能查看个人信息，点击</span><span style=\"font-size: 10.5pt; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/update.gif\" /></span><span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><v:shapetype id=\"_x0000_t75\" coordsize=\"21600,21600\" o:spt=\"75\" o:preferrelative=\"t\" path=\"m@4@5l@4@11@9@11@9@5xe\" filled=\"f\" stroked=\"f\"> </v:shapetype></span><span style=\"font-size: 10.5pt; font-family: 宋体\">＂</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">提交所做的修改，点击</span><span style=\"font-size: 10.5pt; font-family: 宋体\">＂</span><span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><v:shape id=\"_x0000_i1026\" style=\"width: 34.5pt; height: 15.75pt\" type=\"#_x0000_t75\"><img alt=\"\" src=\"helpimages/cancel.gif\" />&nbsp;<v:imagedata src=\"file:///C:\\DOCUME~1\\ADMINI~1\\LOCALS~1\\Temp\\msohtml1\\05\\clip_image003.png\" o:title=\"\"></v:imagedata></v:shape></span><span style=\"font-size: 10.5pt; font-family: 宋体\">＂</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">取消当前所做的修改。</span></p>','30'),('138','我的资料->附加资料','<p>包含员工的工作经历，教育背景，培训经历，简历和自定义字段。只有管理员对上述各模块有增删改查的权限，普通员工只有查看自己信息的权限，部门经理有查看部门员工信息的权限。单击模块名称右侧的&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;，就可以弹出新增对话框。单击每条信息右侧的<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/edit.gif\" />＂就可以对此信息进行修改，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">单击＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂进行删除操作，教育背景与培训经历同工作经历的功能基本相同。简历可以上传两份简历，单击</span>&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;可以上传，如果不不选择文件，则不对原有文件操作，上传成功后自动删除原有文件。点击<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂对简历进行删除操作。修改自定义信息时，改变任一输入框的值，按钮</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/save.gif\" />＂会自动生效，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">点击后就可以保存了。</span></p>','30'),('139','我的资料->人事档案','<p class=\"MsoNormal\" style=\"text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">页面包含员工的所有档案：人事合同，调动管理，考评管理，奖惩管理，离职管理。只有管理员才能修改，删除上述各模块信息，并查看所有员工信息，部门经理可以查看本部门所有员工信息，普通员工只能查看自己的信息。</span>单击模块名称右侧的&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;，就可以弹出新增对话框。单击每条信息右侧的<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/edit.gif\" />＂就可以对此信息进行修改，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">单击＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂进行删除操作。值得注意的是：调动信息只能删除最后添加的一条，离职管理只能删除和修改最后添加的一条。新增考评，调动，奖惩信息时，部门，和业务单元是员工当前的数据，无需用户填写。调动，奖惩，考评，离职，修改后，员工所对应的当前状态也随之改变。其他功能各个模块基本相同。</span></p>','30'),('14','我的加班列表-修改加班','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;可进入修改页面。可修改前提：加班为已拒绝状态。添加条件有：加班种类、日期、加班开始时间、结束时间、加班理由，上面所有选项全部为必填选项，开始时间必须小于结束时间。操作有：提交加班申请&ldquo;<img alt=\"\" src=\"helpimages/examin_submitovertime.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交则是将加班单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后部门经理已审状态。</p>','14'),('140','新增员工','<p class=\"MsoNormal\" style=\"text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">第一步：新增员工信息<span lang=\"EN-US\">:<o:p></o:p></span></span></p>\r\n<p>页面主要包括员工的基本资料和与工作相关的资料信息，如员工部门等，带&ldquo;＊&rdquo;的内容为用户必填信息，其中员工编号是对一个员工的惟一标识，系统根据用户输入的当前字符自动计算编号，用户可以根据提示选择或者自选输入编号；点击经理右侧的<img alt=\"\" src=\"helpimages/search_icon.gif\" />图标就可以按员工的编号，姓名，或部门名称查询系统中的用户，从列表中选择一个用户信息即为当前员工的经理，如果员工经理为空，则经理默认为本人。<br />\r\n填写完必填字段单击＂确定＂按钮提交员工信息，转向第操作成功或失败提示页面，如果操作成功，用户可以对页面上可编辑字段进行修改，完成之后可点击附加资料按钮对员工的附加教育背景，工作经历，培训经历以及用户的自定义信息进行配置。</p>\r\n<p class=\"MsoNormal\" style=\"text-indent: 13.5pt; text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\"><span lang=\"EN-US\"><o:p></o:p></span></span></p>\r\n<p class=\"MsoNormal\" style=\"text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">第二步：新增员工附加资料<span lang=\"EN-US\">:<o:p></o:p></span></span></p>\r\n<p class=\"MsoNormal\" style=\"text-indent: 18pt; text-align: left\" align=\"left\">包含员工的工作经历，教育背景，培训经历，简历和自定义字段。单击模块名称右侧的&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;，就可以弹出新增对话框。单击每条信息右侧的<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"../../help/helpimages/edit.gif\" />＂就可以对此信息进行修改，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">单击＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂进行删除操作，教育背景与培训经历同工作经历的功能基本相同。简历可以上传两份简历，单击</span>&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;可以上传，如果不不选择文件，则不对原有文件操作，上传成功后自动删除原有文件。点击<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂对简历进行删除操作。修改自定义信息时，改变任一输入框的值，按钮</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"../../help/helpimages/save.gif\" />＂会自动生效，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">点击后就可以保存了。</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\"><span lang=\"EN-US\"><o:p></o:p></span></span></p>\r\n<p class=\"MsoNormal\" style=\"text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">第三步：新增员工人事档案<span lang=\"EN-US\">:<o:p></o:p></span></span></p>\r\n<p class=\"MsoNormal\" style=\"text-indent: 18pt; text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">页面包含员工的所有档案：人事合同，调动管理，考评管理，奖惩管理，离职管理。</span>单击模块名称右侧的&ldquo;<span lang=\"EN-US\" style=\"font-size: 10.5pt; font-family: &quot;Times New Roman&quot;\"><img alt=\"\" src=\"helpimages/CreateProject.gif\" /></span>&rdquo;，就可以弹出新增对话框。单击每条信息右侧的<span style=\"font-size: 9pt; color: black; font-family: 宋体\">＂<img alt=\"\" src=\"helpimages/edit.gif\" />＂就可以对此信息进行修改，</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">单击＂<img alt=\"\" src=\"helpimages/common_del.gif\" />＂进行删除操作。</span><span style=\"font-size: 9pt; color: black; font-family: 宋体\">新增考评，调动，奖惩信息时，部门，和业务单元是员工当前的数据，无需用户填写。</span></p>\r\n<h3><span style=\"font-weight: normal; font-size: 9pt; color: black\">第四步：创建员工薪资<span lang=\"EN-US\"><o:p></o:p></span></span></h3>\r\n<p class=\"MsoNormal\" style=\"text-indent: 18pt; text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">必填字段包括员工姓名、薪资级别、薪资货币币种、员工的基本工资、员工银行帐号、员工的基本工资，单击＂提交＂保存薪资信息。</span></p>\r\n<h3><span style=\"font-weight: normal; font-size: 9pt; color: black\">第五步：配置员工考勤信息</span></h3>\r\n<p class=\"MsoNormal\" style=\"text-indent: 18pt; text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\">主要配置员工当年的年假额度,年假余额到期时间表示当前配置的天数最晚可以用到什么时候，年假余额表示今年可以用的看似天数。</span></p>\r\n<h3>\r\n<p class=\"MsoNormal\" style=\"text-indent: 18pt; text-align: left\" align=\"left\"><span style=\"font-size: 9pt; color: black; font-family: 宋体\"><span lang=\"EN-US\"><o:p></o:p></span></span></p>\r\n</h3>','31'),('141','查找员工薪资配置信息','<p><strong>基本查询:</strong>有4个条件选项，可以设置&ldquo;员工&rdquo;， &ldquo;部门&rdquo;，&ldquo;员工类型&rdquo;，&ldquo;经理&rdquo;作为查询条件进行查询。&ldquo;员工&rdquo;选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有用户相关薪资配置信息。</p>\r\n<p><strong>高级查询:</strong>有9个条件选项, 可以设置&ldquo;员工姓名&rdquo;， &ldquo;部门&rdquo;，&ldquo;员工类型&rdquo;，&ldquo;员工编号&rdquo;，&rdquo;业务单元&ldquo;，&rdquo;经理&ldquo;，&ldquo;职称&rdquo;，&rdquo;办公地点&ldquo;，&ldquo;员工状态&rdquo;作为查询条件进行查询。<br />\r\n点击<img alt=\"\" src=\"helpimages/common_basic_search.gif\" />，可以进行基本查询和高级查询的切换。<br />\r\n<strong>分页按钮:</strong>每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图<img alt=\"\" src=\"helpimages/common_page.gif\" /></p>\r\n<p><strong>排序:</strong>点击列表上的粗体字段名，如&ldquo;<img alt=\"\" src=\"helpimages/common_sort.gif\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），<br />\r\n点击以后，将会出现<img alt=\"\" src=\"helpimages/common_sort_asc.gif\" />表示升序，再次点击，将会出现<img alt=\"\" src=\"helpimages/common_sort_desc.gif\" />表示降序。</p>','22'),('142','添加员工薪资配置信息','<p>如果某员工没有配置薪资，可以点击操作中的&ldquo;<img alt=\"\" src=\"helpimages/comp_add_salaryconf.gif\" />&ldquo;按钮进入薪资创建页面。进入页面后，若该职务的员工有默认帐套，则初始时帐套显示为默认帐套：&ldquo;<img alt=\"\" src=\"helpimages/defaultAcct.gif\" />&ldquo;点击选择帐套，可以改变员工帐套，选择帐套后，页面会出现一系列帐套相，固定相是需要填写的，浮动相不需要此时填写，但每月薪资发放时会需要填写，由计算公式得出的也不需要填写。填写完成后点击&ldquo;<img alt=\"\" src=\"helpimages/caculate.gif\" />&ldquo;可以查看公式生成的数据如税后收入等，点击&ldquo;<img alt=\"\" src=\"helpimages/common_ok.gif\" />&ldquo;新增员工薪资配置。此页面的字段：</p>\r\n<p>员工姓名：列出所有尚未创建相应薪资的员工姓名。<br />\r\n薪资等级：可以从下拉框中选择员工的薪资等级，必选项。<br />\r\n薪资帐套：可以从下拉框中选择本公司<a href=\"searchHelp.action?classId=64\">自定义的薪资帐套。</a><br />\r\n员工银行帐号:可以输入员工的银行帐号，必填项。</p>','22'),('143','删除或回退员工薪资配置信息','<p>如果某员工有薪资配置信息,点击操作中的&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，会弹出确认框，点击&ldquo;确定&rdquo;，完成删除员工薪资配置信息操作，点击&ldquo;取消&rdquo;，放弃删除员工薪资配置信息操作。若有薪资调整处理流程，如果存在调薪历史，点击&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;会使薪资配置回退到上次调薪前的状态，否则删除当前薪资配置。</p>','22'),('144','查看员工薪资配置的详细信息','<p>点击列表中位于员工号上的链接，如&ldquo;<u>TYHR0021</u>&rdquo;，会进入显示员工薪资配置详细信息的页面。</p>','22'),('145','修改员工薪资配置','<p>点击列表中的&ldquo;<img alt=\"\" src=\"helpimages/comp_fast_adjust.gif\" />&rdquo;按钮，进入员工薪资配置修改页面，本页面与员工薪资增加页面类似，只是初始化时加载了原先本员工的薪资配置。</p>','22'),('146','员工薪资配置信息数据导出','<p>点击位于列表左上角的&ldquo;<img alt=\"\" src=\"helpimages/common_export.gif\" />&rdquo;按钮，将会弹出一个对话框，点击&ldquo;打开&rdquo;按钮将会打开含有员工薪资配置信息的EXCEL文件，点击&ldquo;保存&rdquo;按钮将会先保存含有员工薪资配置信息的EXCEL文件到用户的计算机中，然后打开此文件，点击&ldquo;取消&rdquo;按钮放弃数据导出的操作。</p>','22'),('148','查看员工薪资详细信息','<p>&nbsp;</p>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 点击列表中位于员工编号上的链接，如&rdquo;<u>TYHR0032</u> &rdquo;，会进入显示员工薪资配置详细信息的页面。用户可以在此页面查看员工所有的薪资信息，员工的薪资发放信息，员工的调薪历史信息。并且HR用户可以修改银行帐号和快速调薪。</p>\r\n<p><strong>修改银行帐号：</strong>点&ldquo;<img alt=\"\" src=\"helpimages/comp_update_bankaccount.gif\" />&rdquo;弹出修改银行帐号的界面层，用户可以输入新的银行帐号，点&ldquo;确定&rdquo;按钮将执行修改银行帐号的操作，点&ldquo;取消&rdquo;将放弃修改银行帐号的操作，点击位于界面右上角的叉形按钮也可以关闭界面，也可以放弃修改银行帐号的操作。<br />\r\n<strong>查看员工薪资发放历史：</strong>点击&ldquo; <u>3个月</u> <u>6个月</u> <u>1年</u> &rdquo;调薪历史：点击&rdquo;调薪历史&rdquo;链接可以弹出显示员工调薪历史的界面层，用户可以点&ldquo;关闭&rdquo;按钮来关闭界面层，点击位于界面右上角的叉形按钮也可以关闭界面层。 <br />\r\n返回到员工薪资配置列表：点击&ldquo;<u>返回到员工薪资配置列表</u> &rdquo;可以返回到员工薪资配置列表。</p>\r\n<p><strong>查看员工调薪历史：</strong>若存在系统调薪流程，点击<u>调薪历史</u>会出现调薪历史的层，否则不会出现<u>调薪历史</u>链接。</p>','22'),('149','员工列表','<p align=\"left\" class=\"MsoNormal\" style=\"margin-left: 18pt; text-indent: -18pt; text-align: left;\"><span lang=\"EN-US\" style=\"font-size: 9pt; color: black; font-family: 宋体;\" /><strong>基本查询:</strong></p>\r\n<p align=\"left\" class=\"MsoNormal\" style=\"margin-left: 18pt; text-indent: -18pt; text-align: left;\">　　　　有4个条件选项，可以设置“员工”，  “部门”，“员工类型”，“员工状态”作为查询条件进行查询。“员工”选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有用户相关信息。</p>\r\n<p><strong>高级查询:</strong></p>\r\n<p>　　　　有16个条件选项, 可以设置“员工姓名”，“员工编号”，  “部门”，”业务单元“，“员工　　种类”，“学历”，”经理“，“职称”，”办公地点“，“家庭电话”，“工作电话”，“邮箱地址”，　　“手机号码”，“员工状态”，“毕业学校”，“专业”作为查询条件进行查询。<br />\r\n点击<img alt=\"\" src=\"helpimages/common_basic_search.gif\" />，可以进行基本查询和高级查询的切换。<br />\r\n<strong>分页按钮:</strong></p>\r\n<p>　　　　每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图　　　　　　　<img alt=\"\" src=\"helpimages/common_page.gif\" /></p>\r\n<p><strong>排序:</strong></p>\r\n<p>　　　　点击列表上的粗体字段名，如“<img alt=\"\" src=\"helpimages/common_sort.gif\" />”可以对数据按照员工姓名进行排序　　（升序，降序），点击以后，将会出现<img alt=\"\" src=\"helpimages/common_sort_asc.gif\" />表示升序，再次点击，将会出现　　<img alt=\"\" src=\"helpimages/common_sort_desc.gif\" />表示降序。</p>\r\n<p><strong>数据导出：</strong></p>\r\n<p><span lang=\"EN-US\" style=\"font-size: 9pt; color: black; font-family: 宋体;\"><span>　　　　点击＂<img alt=\"\" src=\"helpimages/common_export.gif\" />＂把当前的查询结果导出到EXCEL中</span></span></p>\r\n<p><strong>批量修改：</strong></p>\r\n<p>　　　　用户可以同时修改多个员工的部门，业务单元，工作地点，用工形式，经理，职　　称，<span lang=\"EN-US\" style=\"font-size: 9pt; color: black; font-family: 宋体;\"><span> 选中列表左侧的复选框，改变列表下方的下拉框的值,然后再点击＂<img alt=\"\" src=\"helpimages/common_update.gif\" />＂按　　钮，可实现批量修改功能．</span></span></p>\r\n<p align=\"left\" class=\"MsoNormal\" style=\"margin-left: 18pt; text-indent: -18pt; text-align: left;\"><span style=\"font-size: 9pt; color: black; font-family: 宋体;\"><span lang=\"EN-US\"><span style=\"font-size: 9pt; color: black; font-family: 宋体;\"><span lang=\"EN-US\"><o:p></o:p></span></span></span></span></p>','32'),('15','我的请假列表—log状态查看','<p>点击对应的请假单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前请假单的log信息。</p>','14'),('156','档案管理','<p>&#160;&#160;&#160; 1. 人事合同管理。管理员可以查询员工的人事合同信息，查询条件包括员工（可以输入员工编号或员工姓名进行查询）、合同分类，合同编号，合同状态和合同的起止时间，点＂查询＂进行查询操作，点＂重置＂所有查询条件恢复到初始状态．点击某一行的<span style=\"font-size: 9pt; color: black; font-family: 宋体;\">＂<img src=\"helpimages/edit.gif\" alt=\"\" />＂</span>， 可以进行修改操作，点击＂<span style=\"font-size: 9pt; color: black; font-family: 宋体;\"><img alt=\"\" src=\"helpimages/common_del.gif\" /></span>＂，删除员工合同。<br />\r\n2. 调动记录管理。管理员可以员工的调动记录，可以按照员工的编号或姓名，员工的调动前的部门和岗位，调动后的部门和岗位，调动的种类，调动的原因和调动的日期进行查询。<br />\r\n3. 奖惩记录管理。管理员可以进行员工考评记录的管理，可以按照员工的姓名，编号，考评的部门，业务单元，考评的起止日期，考评的结果，考评的类型进行查询，对于查询结果中的每一条记录，可以选中该行的<span style=\"font-size: 9pt; color: black; font-family: 宋体;\">＂<img src=\"helpimages/edit.gif\" alt=\"\" />＂</span>按钮进行修改操作，点击＂<span style=\"font-size: 9pt; color: black; font-family: 宋体;\"><img alt=\"\" src=\"helpimages/common_del.gif\" /></span>＂删除此条记录。<br />\r\n4. 离职记录管理。包括离职记录的查询，修改和删除操作，可以按照员工编号，员工姓名，部门，业务单元，岗位，奖惩类型以及奖惩形式进行查询，对于查询结果中的记录，可以选中该行的<span style=\"font-size: 9pt; color: black; font-family: 宋体;\">＂<img src=\"helpimages/edit.gif\" alt=\"\" />＂</span>按钮进行修改操作，点击＂<span style=\"font-size: 9pt; color: black; font-family: 宋体;\"><img alt=\"\" src=\"helpimages/common_del.gif\" /></span>＂删除此条记录。<br />\r\n5. 离职记录。可能通过员工的编号，姓名，离职的类型，离职的时间进行查询。</p>','33'),('157','查询员工调薪计划','<p>有3个条件选项，可以设置&ldquo;员工&rdquo;， &ldquo;部门&rdquo;，&ldquo;经理姓名&rdquo;作为查询条件进行查询。&ldquo;员工&rdquo;选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有用户相关调薪计划信息。<br />\r\n<strong>查看方式:</strong>用户可以选择查看全部和分页查看，两种方式，查看全部将会在当前页显示所有调薪计划，分页查看每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图，<img alt=\"\" src=\"helpimages/common_page.gif\" />点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。<br />\r\n<strong>排序:</strong>点击列表上的粗体字段名，如&ldquo;<img alt=\"\" src=\"helpimages/common_sort.gif\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），<br />\r\n点击以后，将会出现<img alt=\"\" src=\"helpimages/common_sort_asc.gif\" />表示升序，再次点击，将会出现<img alt=\"\" src=\"helpimages/common_sort_desc.gif\" />表示降序。</p>','23'),('158','创建调薪计划','<p><span style=\"font-size: 9pt; color: black\">如果员工没有调薪计划，用户可创建该员工的调薪计划，用户可以点选项&ldquo;</span><img alt=\"\" src=\"helpimages/comp_ecptype.gif\" /><span style=\"font-size: 9pt; color: black\">&ldquo;从中选择加薪种类，从&rdquo;<img alt=\"\" src=\"helpimages/comp_jobgrade.gif\" /> &ldquo;中选择新的薪资级别，在&rdquo;</span><img alt=\"\" src=\"helpimages/common_effectdate.gif\" /><span style=\"font-size: 9pt; color: black\">&nbsp;</span><span style=\"font-size: 9pt; color: black\">&ldquo;中输入调薪计划生效日期或者点&rdquo;<img alt=\"\" src=\"helpimages/common_inputdate.gif\" /> &nbsp;&ldquo;在弹出的日历中选择日期，在&rdquo;&ldquo;</span><span style=\"font-size: 9pt; color: black\">&nbsp;</span><img alt=\"\" src=\"helpimages/comp_inputbasesalary.gif\" />&rdquo;<span style=\"font-size: 9pt; color: black\">中输入基本工资或者点&ldquo;</span><img alt=\"\" src=\"helpimages/comp_inputdetailbasesalary.gif\" /><span style=\"font-size: 9pt; color: black\">&nbsp;&rdquo;</span><span style=\"font-size: 9pt; color: black\">按钮在弹出的界面层中输入更详细的薪资信息，输入好基本工资以后，会自动计算出薪资增长率并填入到&ldquo;</span><img alt=\"\" src=\"helpimages/comp_increment.gif\" /><span style=\"font-size: 9pt; color: black\">&rdquo;中，用户也可以先在&ldquo;</span><img alt=\"\" src=\"helpimages/comp_increment.gif\" /><span style=\"font-size: 9pt; color: black\">&rdquo;中输入增长比率，系统会自动计算出新的基本工资并填入到&ldquo;</span><img alt=\"\" src=\"helpimages/comp_inputbasesalary.gif\" />&rdquo;中，当所有的数据填写完毕以后，在操作中将会出现&ldquo; <img alt=\"\" src=\"helpimages/common_running.gif\" />&rdquo;表示正在创建调薪计划，此时用户可以等待，也可以继续创建另一条调薪计划，当创建调薪计划完毕后，在操作中将会出现&ldquo;<img alt=\"\" src=\"helpimages/common_submit.gif\" /><span style=\"font-size: 9pt; color: black\">&rdquo;提交按钮和&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" /> &rdquo;删除按钮，同时状态将会显示&ldquo;<u>草稿</u> &rdquo;。</span></p>','23'),('159','修改调薪计划','<p><span style=\"font-size: 9pt; color: black; font-family: 宋体; mso-bidi-font-family: 宋体; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA\">如果用户要修改调薪计划，只需要在可输入框中填入新的数据即可。此时在在操作中将会出现&ldquo;<img alt=\"\" src=\"helpimages/common_running.gif\" />&rdquo;表示正在更新调薪计划中，当更新完毕后，将重新出现&ldquo;<img alt=\"\" src=\"helpimages/common_submit.gif\" />&rdquo;提交按钮和&ldquo; <img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;删除按钮。</span><font face=\"宋体\"></font></p>','23'),('16','我的加班列表—log状态查看','<p>点击对应的加班单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，，显示当前加班单的log信息。</p>','14'),('160','删除调薪计划','<p><span style=\"font-size: 9pt; color: black; font-family: 宋体; mso-bidi-font-family: 宋体; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA\">如果在操作中有&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;删除按钮，用户就可以点此按钮来删除调薪计划，点击将会出现确定对话框，点&ldquo;确定&rdquo;按钮将会删除此条调薪计划，点&ldquo;取消&rdquo;按钮将会取消删除操作。</span></p>','23'),('161','提交调薪计划','<p><strong>全部提交：</strong>用户可以在创建或修改了多条调薪计划以后，点列表左上方的&ldquo;<img alt=\"\" src=\"helpimages/comp_submitall.gif\" /> &rdquo;按钮，就可以批量提交所有的调薪计划。<br />\r\n<strong>单条提交：</strong>用户可以在创建或修改了一条调薪计划以后，点操作中的&ldquo;<img alt=\"\" src=\"helpimages/common_submit.gif\" />&rdquo;提交按钮将会弹出提交调薪计划的界面层，用户必须输入备注（将会保存到此条调薪计划的日志），输入备注以后，点&ldquo;提交调薪计划&rdquo;按钮将会执行提交调薪计划的操作，在操作中将会出现&ldquo;<img alt=\"\" src=\"helpimages/common_running.gif\" /> &rdquo;表示正在提交调薪计划，此时用户可以等待，也可以继续提交另一条调薪计划，当提交完毕后，在操作中的&ldquo;<img alt=\"\" src=\"helpimages/common_submit.gif\" />&rdquo;提交按钮和&ldquo; <img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;删除按钮将会消失，同时状态将会显示&ldquo; <u>部门已审</u>&rdquo;或&ldquo;<u>已提交</u>&rdquo;。点&ldquo;取消&rdquo;按钮将会放弃提交。</p>','23'),('162','查看调薪计划的日志记录','<p>点击某条调薪计划的状态如&ldquo;<u>部门已审</u>&rdquo;可以弹出此条调薪计划的日志层。如下图：<img alt=\"\" src=\"helpimages/common_log.gif\" /></p>','23'),('163','查看员工薪资配置的详细信息','<p>当用户选择了部门或经理姓名的查询条件进行查询以后，在列表的最后一行将会出现，薪资的统计信息。</p>','23'),('164','查看员工的基本信息','<p>当用户把鼠标移动到员工姓名上时，将会显示该员工的员工基本信息，如下图：<br />\r\n<img alt=\"\" src=\"helpimages/comp_employeedetail.gif\" /></p>','23'),('165','组织结构','<p><span style=\"font-size: 9pt; font-family: 宋体; color: black;\">以树的方式查看员工的组织结构，快速了解员工的组织结构信息和员工的联系方式信息。</span></p>','34'),('166','单条审批调薪计划','<div align=\"left\"><span style=\"font-size: 9pt; color: black\">用户可以点操作中的同意&ldquo;<img alt=\"\" src=\"helpimages/common_hrapprove.gif\" /> &rdquo;或拒绝&ldquo; <img alt=\"\" src=\"helpimages/common_hrdisapprove.gif\" />&rdquo;按钮，来审批调薪计划，点击后将会出现界面层，用户输入必填的备注信息以后点&ldquo;同意调薪计划&rdquo;或&ldquo;拒绝调薪计划&rdquo;就可以完成审批，点&ldquo;关闭&rdquo;或右上角的茶形按钮可以放弃审批同时关闭界面层。</span></div>','24'),('167','批量审批调薪计划','<p>用户可以选中要审批的调薪计划的多选框&ldquo;<img alt=\"\" src=\"helpimages/common_checkbox.gif\" />&rdquo;，选中以后点列表左上角或左下角出处的&ldquo;<img alt=\"\" src=\"helpimages/common_hrapprovebutton.gif\" />&rdquo;，就可以批量审批调薪计划。</p>','24'),('168','查询调薪计划','<p>这里有4个条件选项，可以设置&ldquo;员工&rdquo;，&ldquo;经理姓名&rdquo;，&ldquo;月历&rdquo;，&ldquo;部门&rdquo;作为查询条件进行查询。&ldquo;员工&rdquo;选项可以进行模糊查询，既可以对员工姓名，也可以对员工编号进行查询。用户点击重置，可以初始化列表即显示所有待审批的调薪计划相关信息。</p>\r\n<p><strong>查看方式:</strong>用户可以选择查看全部和分页查看，两种方式，查看全部将会在当前页显示所有调薪计划，分页查看每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图，<img alt=\"\" src=\"helpimages/common_page.gif\" />点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p><strong>排序:</strong>点击列表上的粗体字段名，如&ldquo;<img alt=\"\" src=\"helpimages/common_sort.gif\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），点击以后，将会出现<img alt=\"\" src=\"helpimages/common_sort_asc.gif\" />表示升序，再次点击，将会出现<img alt=\"\" src=\"helpimages/common_sort_desc.gif\" />表示降序。</p>','24'),('169','查看员工薪资配置的详细信息','<p>点击列表中位于员工姓名上的链接，如&ldquo;<u>杨国强</u>&rdquo;，会进入显示员工薪资配置详细信息的页面。</p>','24'),('17','我的请假列表—排序操作','<p>点击对应的标题栏&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_myleave_title.bmp\" />&rdquo;，按照指定标题进行排序。</p>','14'),('170','查看薪资汇总信息','<p>当用户选择了经理姓名的查询条件进行查询以后，在列表的最后一行将会出现，薪资的统计信息。</p>','24'),('171','查看调薪计划的日志记录','<p>点击某条调薪计划的状态如&ldquo;部门已审&rdquo;可以弹出此条调薪计划的日志层。如下图：<img alt=\"\" src=\"helpimages/common_log.gif\" /></p>','24'),('172','查看员工的基本信息','<p>当用户把鼠标移动到员工姓名上时，将会显示该员工的员工基本信息，如下图：<br />\r\n<img alt=\"\" src=\"helpimages/comp_employeeinfo.gif\" /></p>','24'),('173','查找招聘计划','<p>这里有3个条件选项，可以设置&ldquo;职位名称&rdquo;，&ldquo; 工作地点&rdquo;，&ldquo; 计划状态&rdquo;，作为查询条件进行查询。提示：<img alt=\"\" src=\"helpimages/recruitment_img7.jpg\" />显示历史纪录复选框的作用是 显示关闭日起在当前日期之前的纪录（默认为历史纪录不显示）。<br />\r\n提示：重置按钮的作用是将当前页面置为初始进入状态。</p>','40'),('174','新增招聘计划','<p>点击列表上方的&ldquo;<img alt=\"\" src=\"helpimages/recruitment_img1.jpg\" />&rdquo;按钮，到新增招聘计划页面进行增加操作。</p>','40'),('175','删除招聘计划','<p>点击列表右侧的叉形&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，会弹出删除确认框，点击&ldquo;确定&rdquo;，完成删除招聘计划操作。</p>','40'),('176','修改招聘计划','<p>点击列表右侧的笔形&ldquo;<img alt=\"\" src=\"helpimages/edit.gif\" />&rdquo;按钮，到编辑招聘计划页面进行编辑操作。</p>','40'),('177','单个提交招聘计划','<p>点击列表右侧的向上箭头形&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，出现提交层<img alt=\"\" src=\"helpimages/recruitment_img9.jpg\" />。在提交层中输入提交备注后 单击&ldquo;提交&rdquo;按钮将此招聘计划提交给部门经理或HR经理审批。如果您本人是部门经理，则提交后的状态是部门已审。如果您本人是Hr经理，则提交后的状态为HR已审。并将你的操作记入到log中。</p>','40'),('178','批量提交招聘计划','<p>点击列表下侧的&ldquo;<img alt=\"\" src=\"helpimages/recruitment_img10.jpg\" />&rdquo;按钮，则将您选中的招聘计划提交给部门经理或HR经理审批。如果您本人是部门经理，则提交后的状态是部门已审。如果您本人是Hr经理，则提交后的状态为HR已审。<br />\r\n提示：列表标题的复选框单击后，可以选中所有状态为草稿的招聘计划</p>','40'),('179','修改密码','<p><span style=\"font-size: 9pt; font-family: 宋体; color: black;\">修改当前用户的密码．必填字段包括原密码，新密码，重复密码三个字段，填写原密码，然后两次输入新密码，点<span lang=\"EN-US\">”</span>提交<span lang=\"EN-US\">”</span>按钮，保存修改过的密码．</span></p>','36'),('18','我的加班列表—排序操作','<p>点击对应的标题栏&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_mysearch_title.bmp\" />&rdquo;，按照指定标题进行排序。</p>','14'),('180','员工报表','<p>报表功能包括报表查看和PDF下载．<br />\r\n报表包括：<br />\r\n１．在职员工人数按部门分析<br />\r\n２．在职员工人数其他分析<br />\r\n３．新进员工分析月报<br />\r\n４．新进员工分析季报<br />\r\n５．新进员工分析半年报<br />\r\n６．新进员工分析年报<br />\r\n７．员工离职分析月报<br />\r\n８．员工离职分析季报<br />\r\n９．员工离职分析半年报<br />\r\n１０．员工离职分析年报</p>','37'),('181','存为草稿','<p>单击菜单后进入新增页面</p>\r\n<img alt=\"\" src=\"helpimages/recruitment_img2.jpg\" />\r\n<br>红色提示的字段为必填字段。输入相应信息后，单击：<img alt=\"\" src=\"helpimages/recruitment_img3.jpg\" /><p><em>创建一个仅仅您个人可以修改，删除，查看的招聘计划\r\n<p>&nbsp;</p>\r\n</em></p>','41'),('182','提交招聘计划','单击：<img alt=\"\" src=\"helpimages/recruitment_img4.jpg\" />按钮\r\n<p>将此招聘计划提交给部门经理或HR经理审批。如果您本人是部门经理，则提交后的状态是部门已审。如果您本人是Hr经理，则提交后的状态为HR已审。并将你的操作记入到log中。</p>','41'),('183','重置','单击：<img alt=\"\" src=\"helpimages/recruitment_img5.jpg\" />按钮<p>将页面信息清空。</p>','41'),('184','组织结构管理','<p><strong>查看信息：</strong></p>\r\n<p>用户通过点击页面上的Tab查看各个对应选项的详细信息</p>\r\n<p><strong>添加信息：</strong></p>\r\n<p>用户可以从每个列表的左下方找到新增按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_add.gif\" alt=\"\" /> ”，点击以后将会弹出添加信息的界面层，在界面层中用户输入信息以后，可以点保存按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_save.gif\" alt=\"\" /> ”来保存信息，点清空按钮“ <img src=\"http://localhost:8080/hr/help/helpimages/common_clear.gif\" p=\"\" alt=\"\" />”可以清空已经输入的数据，点关闭 按钮“ <img src=\"http://localhost:8080/hr/help/helpimages/common_close.gif\" alt=\"\" />”可以关闭添加界面层。其中带＊的为必填内容，不可以为空。</p>\r\n<p><strong>删除内容：</strong></p>\r\n<p>用户可以单击列表行，单击某行后，此行会整行变黄， 然后点列表左下方的“<img src=\"http://localhost:8080/hr/help/helpimages/common_delete.gif\" alt=\"\" /> ”来删除这行的数据。</p>\r\n<h4>修改信息:</h4>\r\n<p>用户可以双击某行或点列表左下放的修改按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_update.gif\" alt=\"\" />”，点击以后将会弹出修改信息的界面层，在界面层中输入用户信息以后，可以点保存按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_save.gif\" alt=\"\" />”来保存修改后的数据，可以点清空按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_clear.gif\" alt=\"\" />”清空已经输入的数据，点关闭 按钮“<img src=\"http://localhost:8080/hr/help/helpimages/common_close.gif\" alt=\"\" />” 可以关闭添加界面层。</p>\r\n<p><img alt=\"\" src=\"file:///C:/DOCUME~1/ADMINI~1/LOCALS~1/Temp/moz-screenshot-8.jpg\" /><img alt=\"\" src=\"file:///C:/DOCUME~1/ADMINI~1/LOCALS~1/Temp/moz-screenshot-7.jpg\" /></p>\r\n<p align=\"left\" class=\"MsoNormal\" style=\"margin-left: 18pt; text-indent: -18pt; text-align: left;\"><span lang=\"EN-US\" style=\"font-size: 9pt; color: black; font-family: 宋体;\" /></p>','38'),('185','查找招聘计划','<p>这里有3个条件选项，可以设置&ldquo;职位名称&rdquo;，&ldquo; 工作地点&rdquo;，&ldquo; 计划状态&rdquo;，作为查询条件进行查询。<br />\r\n提示：重置按钮的作用是将当前页面置为初始进入状态。</p>','42'),('186','审批同意招聘计划','<p>点击列表右侧的大拇指朝上形的<img alt=\"\" src=\"helpimages/common_hrapprove.gif\" />按钮，出现同意备注层。在同意备注层中输入同意备注后 单击&ldquo;确定同意&rdquo;按钮将此招聘计划审批通过，并将您的同意操作记入到log中。</p>','42'),('187','审批拒绝招聘计划','<p>点击列表右侧的大拇指朝下形的&ldquo;<img alt=\"\" src=\"helpimages/common_hrdisapprove.gif\" />&rdquo;按钮，出现拒绝备注层。在拒绝备注层中输入拒绝备注后 单击&ldquo;确定拒绝&rdquo;按钮将此招聘计划审批拒绝，并将您的拒绝操作记入到log中。</p>','42'),('188','批量审批同意招聘计划','<p>点击列表下侧的&ldquo;部门审批&rdquo;按钮，则将您选中的招聘计划全部审批同意。提交后的状态是部门已审。等待HR 审批。并将您的同意操作记入到log中。<br />\r\n提示：列表标题的复选框单击后，可以选中所有状态为草稿的招聘计划</p>','42'),('189','查找招聘计划','<p>这里有4个条件选项，可以设置&ldquo;职位名称&rdquo;，&ldquo; 工作地点&rdquo;，&ldquo; 计划状态&rdquo;，作为查询条件进行查询。</p>\r\n<p>提示：重置按钮的作用是将当前页面置为初始进入状态。</p>','43'),('19','我的请假申请-新增请假','<p>添加条件有：员工、请假种类、请假开始时间、请假结束时间、请假理由，上面的所有选项都为必填，开始与结束时间按半天记。操作有：提交请假单&ldquo;<img alt=\"\" src=\"helpimages/examin_submitleave.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交请假单是将当前请假单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后为部门已审状态。</p>','15'),('190','审批同意招聘计划','<p>点击列表右侧的大拇指朝上形的&ldquo;<img alt=\"\" src=\"helpimages/common_hrapprove.gif\" />&rdquo;按钮，出现同意备注层。在同意备注层中输入同意备注后 单击&ldquo;确定同意&rdquo;按钮将此招聘计划审批通过，并将您的同意操作记入到log中。</p>','43'),('191','审批拒绝招聘计划','<p>点击列表右侧的大拇指朝下形的&ldquo;<img alt=\"\" src=\"helpimages/common_hrdisapprove.gif\" />&rdquo;按钮，出现拒绝备注层。在拒绝备注层中输入拒绝备注后 单击&ldquo;确定拒绝&rdquo;按钮将此招聘计划审批拒绝，并将您的拒绝操作记入到log中。</p>','43'),('192','批量审批同意招聘计划','<p>点击列表下侧的&ldquo;<img alt=\"\" src=\"helpimages/recruitment_img13.jpg\" />&rdquo;按钮，则将您选中的招聘计划全部审批同意。提交后的状态是HR已审。并将您的同意操作记入到log中。<br />\r\n提示：列表标题的复选框单击后，可以选中所有状态为草稿的招聘计划</p>','43'),('193','查找招聘计划','<p>这里有4个条件选项，可以设置&ldquo;职位名称&rdquo;，&ldquo;职位所属部门&rdquo;，&ldquo; 工作地点&rdquo;，&ldquo; 计划状态&rdquo;，作为查询条件进行查询。<br />\r\n提示：<img alt=\"\" src=\"helpimages/recruitment_img7.jpg\" />显示历史纪录复选框的作用是 显示关闭日起在当前日期之前的纪录（默认为历史纪录不显示）。<br />\r\n提示：<img alt=\"\" src=\"helpimages/recruitment_img5.jpg\" />重置按钮的作用是将当前页面置为初始进入状态。</p>','44'),('194','编辑招聘计划','<p>点击列表右侧的笔形的&ldquo;<img alt=\"\" src=\"helpimages/edit.gif\" />&rdquo;按钮，对状态为HR 已审的招聘计划进行修改操作。</p>','44'),('195','废除/关闭/中止 招聘计划','<p>点击列表右侧的叉形的&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，将所选招聘计划废除/关闭/中止。如果实际招聘人数为零，则状态更改为废除；如果实际招聘人数大于零但小于计划招聘人数，则状态更改为中止；如果实际招聘人数大于或等于计划招聘人数，则状态更改为关闭；并将您的操作记入到log中。</p>','44'),('196','删除招聘计划','<p>点击列表右侧的红色叉形的&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，将对应的招聘计划删除。</p>','44'),('197','查看招聘计划详细信息','<p>点击列表中的招聘计划编号，则显示此条招聘计划的详细信息。并将此招聘计划下的所有应聘者列表显示在下放，你可以对此招聘计划下的应聘者进行增加，删除，修改操作。</p>','44'),('2','修改密码','<p>点击列表中的“修改密码” 链接，弹出修改密码的界面层 输入新密码，重复密码，点击“提交” ，修改密码完成。点击“取消” 放弃本次操作， 修改密码的界面层将关闭。</p>','7'),('20','我的加班申请-新增加班','<p>添加条件有：员工、加班种类、日期、加班开始时间、结束时间、加班理由，上面所有选项全部为必填选项，开始时间必须小于结束时间。操作有：&ldquo;<img alt=\"\" src=\"helpimages/examin_submitovertime.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交请假单提交是将加班单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后为部门已审状态。</p>','15'),('203','查找应聘者','<p>这里有4个条件选项，可以设置&ldquo;应聘部门&rdquo;，&ldquo; 应聘职位&rdquo;，&ldquo; 应聘者姓名&rdquo; ，&ldquo; 应聘者状态&rdquo;，作为查询条件进行查询。<br />\r\n提示：重置按钮的作用是将当前页面置为初始进入状态。</p>','45'),('204','新增应聘者','<p>点击列表上方的&ldquo;<img alt=\"\" src=\"helpimages/recruitment_img17.jpg\" />&rdquo;按钮，到新增应聘者页面进行增加操作。</p>','45'),('205','删除应聘者','<p>点击列表右侧的叉形&ldquo;：<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;按钮，会弹出删除确认框，点击&ldquo;确定&rdquo;，完成删除应聘者操作。</p>','45'),('206','修改应聘者','<p>点击列表右侧的笔形&ldquo;<img alt=\"\" src=\"helpimages/edit.gif\" />&rdquo;按钮，到编辑应聘者页面进行编辑操作。</p>','45'),('207','应聘者附件下载','<p>点击列表右侧的别针：<img alt=\"\" src=\"helpimages/recruitment_img22.jpg\" />按钮，下载应聘者的附件。</p>','45'),('21','部门请假列表-查询','<p>查询条件有：请假种类、请假开始时间、请假结束时间、部门、员工、请假理由，请假种类默认为全部种类，请假开始和结束时间必须同时填写，部门搜索必须具有相应的权限才可显示，员工查询为编号名字的模糊查询、理由查询为模糊查询。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','16'),('213','招聘计划分析','<p>选择报表名称为：招聘计划分析，单击查看报表，列出报表信息</p>','46'),('214','应聘者分析','<p>选择报表名称为：应聘者分析，单击查看报表，列出报表信息<br />\r\nPDF下载<br />\r\n单击PDF下载，下载pdf各式的报表</p>','46'),('215','新增招聘渠道','<p>点击列表下方&ldquo;<img alt=\"\" src=\"helpimages/recruitment_img25.jpg\" />&rdquo;按钮，弹出新增招聘渠道层;<img alt=\"\" src=\"helpimages/recruitment_img28.jpg\" />进行增加操作。</p>','47'),('216','删除招聘渠道','<p>点击列表下方&ldquo;;<img alt=\"\" src=\"helpimages/recruitment_img26.jpg\" />&rdquo;按钮，会弹出删除确认框，点击&ldquo;确定&rdquo;，完成删除招聘渠道操作。</p>','47'),('217','修改招聘渠道','<p>点击列表下方&ldquo;;<img alt=\"\" src=\"helpimages/recruitment_img27.jpg\" />&rdquo;按钮或双击对应的行，弹出编辑招聘渠道层进行编辑操作。</p>','47'),('218','查看角色','<p>点击左侧列表中的 角色名 进入&ldquo;查看角色&rdquo;页面，角色名：显示当前的角色名。角色描述：显示对角色的描述信息。 下面的列表显示了改角色拥有的对应权限。</p>','8'),('219','修改角色','<p>点击页面的 <u>修改角色信息</u> 按钮 进入&ldquo;修改用户角色&rdquo;页面,角色名：角色的名称。 <br />\r\n角色描述：对角色的描述信息。 <br />\r\n下面的列表显示了所有的权限。选择你对次角色赋予的权限。<br />\r\n点击<img src=\"helpimages/common_submitB.gif\" alt=\"\" />按钮 ，完成角色的修改。</p>','8'),('22','部门加班列表-查询','<p>查询条件有：加班种类、加班开始时间、加班结束时间、部门、员工、加班理由，加班种类默认为全部种类，加班开始和加班时间必须同时填写，部门搜索必须具有相应的权限才可显示，员工查询为编号名字的模糊查询、理由查询为模糊查询。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','16'),('220','删除角色','<p>点击页面的 <u>删除</u> 按钮，如果当前角色下没有用户的话就会完成删除操作，否则提示出错信息。</p>','8'),('221','增加角色','<p>点击<img alt=\"\" src=\"helpimages/system_addRole.gif\" />按钮，进入&quot;新增角色&quot;界面。<br />\r\n角色名：角色的名称。 <br />\r\n角色描述：对角色的描述信息。 <br />\r\n下面的列表显示了所有的权限。选择你对次角色赋予的权限。<br />\r\n点击<img alt=\"\" src=\"helpimages/common_submitB.gif\" />按钮 ，完成角色的增加。</p>','8'),('222','薪资综合报','<p>薪资综合报表分为薪资发放历史和薪资发放预测两部分,薪资发放历史统计过去六个月或者十二个月的薪资发放情况,薪资发放预测预测员工将来六个月或者十二个月的薪资发放情况.</p>\r\n<p>1.报表输出条件：&nbsp;&nbsp;&nbsp;</p>\r\n<p>&nbsp;&nbsp;&nbsp; 年月范围: 六个月、十二个月</p>\r\n<p>&nbsp;&nbsp;&nbsp; 金额： 总计、人均</p>\r\n<p>&nbsp;&nbsp; &nbsp;部门： 包含公司的所有部门</p>\r\n<p>&nbsp;&nbsp;&nbsp; 调薪状态： 包含部门已审、不包含部门已审</p>\r\n<p>2. 报表输出格式：</p>\r\n<p>&nbsp;&nbsp;&nbsp;&nbsp;分为HTML格式输出(查看报表)和PDF格式下载</p>\r\n<p>3. 输出报表分类：</p>\r\n<p>&nbsp;&nbsp;&nbsp; 前两个图表为历史发放统计，后两个为薪资发放预测。</p>','27'),('223','查询','<p>有4个条件选项，可以设置&ldquo;课程名称&rdquo;， &ldquo;培训老师&rdquo;，&ldquo;培训地点&rdquo;，&ldquo;计划状态&rdquo;作为查询条件进行查询。在各选项框中输入查询条件后，点击查询后，符合条件的计划将在列表中为您显示出来。当您点击重置按钮，可以初始化列表即显示所有培训计划信息。</p>','49'),('224','分页','<p>每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图<span lang=\"EN-US\">&ldquo;<img alt=\"\" src=\"helpimages/image001.png\" />&rdquo;</span>点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>','49'),('225','排序','<p>点击列表上的粗体字段名，如&ldquo; &rdquo;可以对数据按照课程名称进行排序（升序，降序），<br />\r\n点击以后，将会出现 表示升序，再此点击，将会出现 表示降序。</p>','49'),('226','查看课程详细信息','<p>单击课程名称下每门具体课程的名称链接，就会弹出关于这门课程的详细信息。</p>','49'),('23','部门请假列表-审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_approve.bmp\" />&rdquo;可进行审批。审批前提：已提交状态或部门已审状态。审批条件：备注信息，非必填选项,。操作有：批准申请、取消，批准申请将指定的加班单设置为部门已审状态或总经理已审状态，取消为不进行任何改变。</p>','16'),('232','申请','<p>在每条记录的最右方有一个 ;<img alt=\"\" src=\"helpimages/image005.jpg\" />按钮，单击后出现培训申请备注框。如果你想在这个地方记下您申请的原因，请把原因天写到备注框中。如果你不想填写，直接单击提交按钮即可。单击取消，关闭申请层，申请动作被终止。</p>','50'),('233','查询','<p>有4个条件选项，可以设置&ldquo;课程名称&rdquo;， &ldquo;培训老师&rdquo;，&ldquo;培训地点&rdquo;，&ldquo;计划状态&rdquo;作为查询条件进行查询。在各选项框中输入查询条件后，点击查询后，符合条件的计划将在列表中为您显示出来。当您点击重置按钮，可以初始化列表即显示所有培训计划信息。</p>','50'),('234','分页','<p>每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图<img alt=\"\" src=\"helpimages/image001.png\" /> ，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>','50'),('235','排序','<p>点击列表上的粗体字段名，如&ldquo; <img alt=\"\" src=\"helpimages/image003.jpg\" /> &rdquo;可以对数据按照课程名称进行排序（升序，降序），</p>\r\n<p>点击以后，将会出现<img alt=\"\" src=\"helpimages/image004.jpg\" /> 表示升序，再此点击，将会出现 表示降序。</p>\r\n<p>查看课程详细信息：</p>\r\n<p>单击课程名称下每门具体课程的名称链接，就会弹出关于这门课程的详细信息。</p>','50'),('236','查看课程详细信息','<p><span style=\"font-size: 9pt; color: #333333; font-family: 宋体; mso-bidi-font-family: \'Courier New\'; mso-ansi-language: EN-US; mso-fareast-language: ZH-CN; mso-bidi-language: AR-SA; mso-ascii-font-family: \'Courier New\'; mso-hansi-font-family: \'Courier New\'\">单击课程名称下每门具体课程的名称链接，就会谈出关于这门课程的详细信息。</span></p>','50'),('237','单条审批培训计划','<p>用户可以点操作中的同意&ldquo;<img alt=\"\" src=\"helpimages/image007.jpg\" /> &rdquo;或拒绝&ldquo;<img alt=\"\" src=\"helpimages/image009.jpg\" /> &rdquo;按钮，来审批调薪计划，点击后将会出现界面层，用户输入必填的备注信息以后点&ldquo;同意培训计划&rdquo;或&ldquo;拒绝培训计划&rdquo;就可以完成审批，点&ldquo;关闭&rdquo;或右上角的叉形按钮可以放弃审批同时关闭界面层。</p>','51'),('238','批量审批培训计划','<p>用户可以选中要审批的培训计划的多选框&ldquo; <img alt=\"\" src=\"helpimages/image011.jpg\" />&rdquo;，选中以后点列表左上角或左下角出处的&ldquo;同意&rdquo;按钮，就可以批量审批同意培训计划。</p>','51'),('239','查询培训计划','<p>这里有3个条件选项，可以设置&ldquo;员工&rdquo;，&ldquo;课程名称&rdquo;，&ldquo;培训地点&rdquo;作为查询条件进行查询。用户点击重置，可以初始化列表即显示所有待审批的培训计划相关信息。</p>\r\n<p>查看方式:用户可以分页查看培训计划，分页查看每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图 <img alt=\"\" src=\"helpimages/image001.png\" />，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p>排序:点击列表上的粗体字段名，如&ldquo; <img alt=\"\" src=\"helpimages/image007.jpg\" />&rdquo;可以对数据按照课程进行排序（升序，降序），</p>\r\n<p>点击以后，将会出现<img alt=\"\" src=\"helpimages/image007.jpg\" /> 表示升序，再此点击，将会出现<img alt=\"\" src=\"helpimages/image009.jpg\" /> 表示降序。</p>','51'),('24','部门加班列表-审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_approve.bmp\" />&rdquo;可进行审批。审批前提：已提交状态或部门已审状态。审批条件：备注信息，非必填选项。操作有：批准申请、取消，批准申请将指定的请假单设置为部门已审状态或总经理已审状态，取消为不进行任何改变。</p>','16'),('240','查看课程的详细信息','<p>点击列表中位于课程名称上的链接，会进入显示课程详细信息的页面。</p>','51'),('241','查看培训计划的日志记录','<p>点击某条培训计划的状态如&ldquo; <img alt=\"\" src=\"helpimages/image013.png\" />&rdquo;可以弹出此条培训计划的日志层。如下图：<img alt=\"\" src=\"helpimages/image056.jpg\" /></p>','51'),('242','单条审批培训计划','<p>用户可以点操作中的同意&ldquo;<img alt=\"\" src=\"helpimages/image007.jpg\" /> &rdquo;或拒绝&ldquo;<img alt=\"\" src=\"helpimages/image009.jpg\" /> &rdquo;按钮，来审批调薪计划，点击后将会出现界面层，用户输入必填的备注信息以后点&ldquo;同意培训计划&rdquo;或&ldquo;拒绝培训计划&rdquo;就可以完成审批，点&ldquo;关闭&rdquo;或右上角的叉形按钮可以放弃审批同时关闭界面层。</p>','52'),('243','批量审批培训计划','<p>用户可以选中要审批的培训计划的多选框&ldquo;<img src=\"helpimages/image011.jpg\" alt=\"\" /> &rdquo;，选中以后点列表左上角或左下角出处的&ldquo;同意&rdquo;按钮，就可以批量审批同意培训计划。</p>','52'),('244','查询培训计划','<p>这里有3个条件选项，可以设置&ldquo;员工&rdquo;，&ldquo;课程名称&rdquo;，&ldquo;培训地点&rdquo;作为查询条件进行查询。用户点击重置，可以初始化列表即显示所有待审批的培训计划相关信息。</p>\r\n<p>查看方式:用户可以分页查看培训计划，分页查看每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图<img alt=\"\" src=\"helpimages/image001.png\" /> ，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p>排序:点击列表上的粗体字段名，如&ldquo; <img alt=\"\" src=\"helpimages/image007.jpg\" />&rdquo;可以对数据按照课程进行排序（升序，降序），</p>\r\n<p>点击以后，将会出现<img alt=\"\" src=\"helpimages/image007.jpg\" /> 表示升序，再此点击，将会出现 <img alt=\"\" src=\"helpimages/image009.jpg\" />表示降序。</p>','52'),('245','查看课程的详细信息','<p>点击列表中位于课程名称上的链接，会进入显示课程详细信息的页面。</p>','52'),('246','查看培训计划的日志记录','<p>点击某条培训计划的状态如&ldquo;HR已审&rdquo;可以弹出此条培训计划的日志层。如下图：<img alt=\"\" src=\"helpimages/image056.jpg\" /></p>','52'),('247','课程计划列表','<p>基本查询:有4个基本查询条件，分别为&ldquo;员工&rdquo;（包括申请人的员工编号和姓名）， &ldquo;课程名称&rdquo;，&ldquo;培训地点&rdquo;，&ldquo;状态&rdquo;（培训计划状态，包括&ldquo;已提交&rdquo;、&ldquo;&rdquo;&ldquo;部门已审&rdquo;、&ldquo;HR已审&rdquo;、&ldquo;已拒绝&rdquo;、&ldquo;已作废&rdquo;、&ldquo;已反馈&rdquo;等）。选择一个或者多个查询条件进行模糊和精确查询，点击&ldquo;重置&rdquo;按钮后，可以初始化列表，使列表恢复到刚刚点击菜单&ldquo;员工培训计划&rdquo;后的状态。</p>\r\n<p>分页按钮:每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图 <img alt=\"\" src=\"helpimages/image001.png\" />，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p>排序:点击列表上的粗体字段名，如&ldquo; <img alt=\"\" src=\"helpimages/image046.jpg\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），</p>\r\n<p>点击以后，将会出现 <img alt=\"\" src=\"helpimages/image047.png\" />表示升序，再次点击，将会出现 <img alt=\"\" src=\"helpimages/image049.png\" />表示降序。</p>','53'),('248','查看课程培训计划详细信息','<p>点击列表中的URL链接&ldquo;课程名称&rdquo;，如：<img alt=\"\" src=\"helpimages/image052.jpg\" /> 后，弹出课程计划详细信息层，在给层中也可以进行课程附件下载。</p>','53'),('249','查看Logs','<p>在列表中，单击&ldquo;状态&rdquo;，如<img alt=\"\" src=\"helpimages/image054.jpg\" /> ，弹出一个层，该层显示了该培训计划的操作历史，该层如下所示<img alt=\"\" src=\"helpimages/image056.jpg\" /></p>','53'),('25','部门请假列表-审批拒绝','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_reject.bmp\" />&rdquo;可进行拒绝。审批前提：已提交状态或部门已审状态。审批条件：备注信息，必填选项。操作有：拒绝申请、取消，拒绝申请将指定的加班单设置为部门拒绝状态，取消为不进行任何改变。</p>','16'),('250','作废操作','<p>对于状态为&ldquo;HR已审&rdquo;的培训计划，旁边有个小按钮 <img alt=\"\" src=\"helpimages/image057.png\" />，点击后，弹出一个对话框，点击&ldquo;确定&rdquo;，完成废除操作，点击&ldquo;取消&rdquo;，放弃废除操作。</p>','53'),('251','反馈操作','<p>对于状态为&ldquo;HR已审&rdquo;的培训计划，旁边有个小按钮<img alt=\"\" src=\"helpimages/image059.png\" /> ，点击后，弹出一个对话框，点击&ldquo;确定&rdquo;，完成反馈操作，点击&ldquo;取消&rdquo;，放弃反馈操作。</p>','53'),('252','员工报表','<p>报表功能包括报表查看和PDF下载．<br />\r\n报表包括：</p>','54'),('253','课程列表','<p>基本查询:有4个基本查询条件，分别为&ldquo;课程编号&rdquo;， &ldquo;课程名称&rdquo;，&ldquo;课程培训种类&rdquo;，&ldquo;课程状态&rdquo;。选择一个或者多个查询条件进行模糊和精确查询，点击&ldquo;重置&rdquo;按钮后，可以初始化列表，使列表恢复到刚刚点击菜单&ldquo;课程设置&rdquo;后的状态。</p>\r\n<p>分页按钮:每页显示20条数据，如果数据总量&gt;20条将会出现分页按钮，如下图 <img alt=\"\" src=\"helpimages/image001.png\" />，点&ldquo;开始&rdquo;按钮将回到第一页，点&ldquo;上页&rdquo;按钮将会到前一页，点&ldquo;下页&rdquo;按钮将回到下一页，点&ldquo;最后&rdquo;按钮将回到最后一页。</p>\r\n<p>排序:点击列表上的粗体字段名，如&ldquo; <img alt=\"\" src=\"helpimages/image019.png\" />&rdquo;可以对数据按照员工姓名进行排序（升序，降序），</p>\r\n<p>点击以后，将会出现 <img alt=\"\" src=\"helpimages/image021.png\" />表示升序，再次点击，将会出现<img alt=\"\" src=\"helpimages/image023.png\" /> 表示降序。</p>','55'),('254','增加新课程','<p>如果想要新增一门培训课程，可以点击列表左上角的按钮&ldquo; <img alt=\"\" src=\"helpimages/image026.jpg\" />&rdquo;，进入新增课程页面，该页面的必填字段的旁边都会有符号<img alt=\"\" src=\"helpimages/image028.jpg\" /> 进行标记，填写完必填和选填字段后，其中的按钮&ldquo;取消&rdquo;的作用是重置，使页面恢复到输入前的状态，点击按钮&ldquo;确定&rdquo;后，就完成添加操作。</p>','55'),('255','修改课程信息','<p>在列表中，点击某课程旁边的按钮 <img alt=\"\" src=\"helpimages/image030.jpg\" />，进入更新课程信息页面，修改课程信息，如果想放弃所做的修改，则点击按钮&ldquo;取消&rdquo;，其作用是重置，最后点击&ldquo;更新&rdquo;后完成修改操作。</p>','55'),('256','删除课程','<p>列表中，点击某课程旁边的按钮 <img alt=\"\" src=\"helpimages/image057.png\" />后，弹出一个对话框，点击&ldquo;确定&rdquo;后，完成删除操作，点击&ldquo;取消&rdquo;，则放弃删除该课程。</p>','55'),('257','查看课程的详细信息','<p>点击列表中课程编号，如&ldquo; <img alt=\"\" src=\"helpimages/image033.png\" />&rdquo;，进入课程详细信息的查看页面。</p>','55'),('258','关闭/允许开放课程','<p>在课程列表中，点击&ldquo;状态&rdquo;这一列中的按钮 <img alt=\"\" src=\"helpimages/image035.png\" />，实现课程的关闭和开放操作，如某课程开放时，状态为&ldquo;<img alt=\"\" src=\"helpimages/image037.png\" /> &rdquo;</p>\r\n<p>课程关闭时，状态为&ldquo; <img alt=\"\" src=\"helpimages/image040.jpg\" />&rdquo;，该状态下的课程不能对其增加课程培训计划。</p>','55'),('259','添加某个课程的培训计划','<p>在查看课程的详细信息页面，点击位于列表左上角的&ldquo;<img alt=\"\" src=\"helpimages/image042.jpg\" /> &rdquo;按钮，进入新增课程计划页面，该页面的必填字段的旁边都会有符号<img alt=\"\" src=\"helpimages/image028.jpg\" /> 进行标记，填写完必填和选填字段后，其中的按钮&ldquo;取消&rdquo;的作用是重置，使页面恢复到输入前的状态，点击按钮&ldquo;确定&rdquo;后，就完成添加操作。</p>','55'),('26','部门加班列表-审批拒绝','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_reject.bmp\" />&rdquo;可进行拒绝。审批前提：已提交状态或部门已审状态。审批条件：备注信息，必填选项。操作有：拒绝申请、取消，拒绝申请将指定的请假单设置为部门拒绝状态，取消为不进行任何改变。</p>','16'),('260','修改课程的培训计划','<p>在列表中，点击课程培训计划旁边的按钮 <img alt=\"\" src=\"helpimages/image030.jpg\" />，进入更新课程培训计划页面，修改课程培训计划信息，如果想放弃所做的修改，则点击按钮&ldquo;取消&rdquo;，其作用是重置，最后点击&ldquo;更新&rdquo;后完成修改操作。</p>','55'),('261','删除课程培训计划','<p>列表中，点击某课程培训计划旁边的按钮<img alt=\"\" src=\"helpimages/image031.png\" /> 后，弹出一个对话框，点击&ldquo;确定&rdquo;后，完成删除操作，点击&ldquo;取消&rdquo;，则放弃删除该课程。</p>','55'),('262','查看课程培训计划的详细信息','<p>点击列表中课程培训计划的编号，如&ldquo;<img alt=\"\" src=\"helpimages/image044.jpg\" /> &rdquo;，弹出一个课程计划详细信息的层。在该层中也可以进行课程附件下载。</p>','55'),('263','关闭/允许申请课程培训计划','<p>在查看课程详细信息页面，在状态列中，点击按钮<img alt=\"\" src=\"helpimages/image035.png\" /> ，实现课程培训计划的关闭和允许申请操作，状态为关闭状态的课程培训计划不会在&ldquo;培训申请&rdquo;模块中出现。</p>','55'),('264','增加培训种类','<p>用户可以从每个列表的左下方看到新增按钮&ldquo; <img src=\"helpimages/image061.png\" alt=\"\" />&rdquo;，点击以后将会弹出添加培训种类的界面层，在界面层中输入信息后，可以点保存按钮&ldquo; <img src=\"helpimages/image063.png\" alt=\"\" />&rdquo;来保存信息，若想重新输入，则点清空按钮&ldquo;<img src=\"helpimages/image065.png\" alt=\"\" /> &rdquo;来清空已经输入的数据，点关闭 按钮&ldquo; <img src=\"helpimages/image068.jpg\" alt=\"\" />&rdquo;可以关闭添加界面层。</p>','56'),('265','删除培训种类','<p>用户可以单击列表某一行，此行将整行变黄色，如图</p><img src=\"helpimages/image069.png\" alt=\"\" />\r\n<p><br />\r\n然后点列表左下方的&ldquo;<img src=\"helpimages/image071.png\" alt=\"\" /> &rdquo;来删除这行的数据。</p>','56'),('266','修改培训种类','<p>用户可以双击某行或先选重某行后点击列表左下方的修改按钮&ldquo; <img src=\"helpimages/image073.png\" alt=\"\" />&rdquo;，点击以后将会弹出修改信息的界面层，在界面层中修改完信息后，可以点保存按钮&ldquo;<img src=\"helpimages/image063.png\" alt=\"\" /> &rdquo;来保存修改过的信息若想重新输入，则点清空按钮&ldquo;<img src=\"helpimages/image065.png\" alt=\"\" /> &rdquo;来清空已经输入的数据，点关闭 按钮&ldquo; <img src=\"helpimages/image068.jpg\" alt=\"\" />&rdquo;后，关闭添加界面层。</p>','56'),('27','部门请假列表-查看请假','<p>点击对应的请假单编号显示对应的请假单详细信息。点击关闭按钮，关闭对应的请假详情显示。</p>','16'),('28','部门加班列表-查看加班','<p>点击对应的加班单编号显示对应的加班单详细信息。点击关闭按钮，关闭对应的加班详情显示。</p>','16'),('29','部门请假列表—批量审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_nsubmit.bmp\" />&rdquo;可进行批量审批。审批前提：已提交状态或部门已审状态。</p>','16'),('3','我的请假列表-查找','<p>&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_mysearch_leave.bmp\" />&rdquo;查找条件有：请假种类、请假开始时间、请假结束时间、请假状态，其中请假种类可以默认表示全部种类，开始时间和结束时间必须同时填写，请假状态默认为全部状态。操作有：查询、重置，其中查询操作，是按照用户选择的查询条件进行查询显示（开始结束时间搜索，返回的是与这个时间段相重叠的信息），重置操作返回的是用户在默认情况下的所有信息（默认情况下，是所有时间的所有种类的所有状态的信息）。</p>','14'),('30','部门加班列表—批量审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_nsubmit.bmp\" />&rdquo;可进行批量审批。审批前提：已提交状态或部门已审状态。</p>','16'),('31','部门请假列表—log状态查看','<p>点击对应的请假单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前请假单的log信息。</p>','16'),('32','部门加班列表—log状态查看','<p>点击对应的加班单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前加班单的log信息。</p>','16'),('33','部门请假列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','16'),('34','部门加班列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','16'),('35','HR请假列表-查询','<p>查询条件有：请假种类、请假开始时间、请假结束时间、部门、员工、请假理由，请假种类默认为全部种类，请假开始和结束时间必须同时填写，部门默认为全部部门，员工查询为编号名字的模糊查询、理由查询为模糊查询。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','17'),('36','HR加班列表-查询','<p>查询条件有：加班种类、加班开始时间、加班结束时间、部门、员工、加班理由，加班种类默认为全部种类，加班开始和加班时间必须同时填写，部门默认为全部部门，员工查询为编号名字的模糊查询、理由查询为模糊查询。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','17'),('37','HR请假列表-审批通过','<p> 点击&ldquo;<img alt=\"\" src=\"helpimages/examin_approve.bmp\" />&rdquo;可以进行审批操作。审批前提：部门已审状态或总经理已审状态。审批条件：备注信息，非必填选项,。操作有：批准申请、取消，批准申请将指定的请假单设置为HR已审状态，取消为不进行任何改变。</p>','17'),('38','HR加班列表-审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_approve.bmp\" />&rdquo;可以进行审批操作。审批前提：部门已审状态或总经理已审。审批条件：备注信息，非必填选项。操作有：批准申请、取消，批准申请将指定的加班单设置为HR已审状态，取消为不进行任何改变。</p>','17'),('39','HR请假列表-审批拒绝','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_reject.bmp\" />&rdquo;可以进行拒绝操作。审批前提：部门已审状态或总经理已审状态。审批条件：备注信息，必填选项。操作有：拒绝申请、取消，拒绝申请将指定的请假单设置为HR拒绝状态，取消为不进行任何改变。</p>','17'),('4','我的加班列表-查找','<p>&ldquo;<img width=\"200\" alt=\"\" src=\"helpimages/examin_mysearch.bmp\" />&rdquo;查找条件有：加班种类、加班搜索开始日期、加班搜索结束日期、加班状态，其中加班种类可以默认表示全部种类，开始日期和结束日期必须同时填写，加班状态默认为全部状态。操作有：查询、重置，其中查询操作，是按照用户选择的查询调节进行查询显示，重置操作返回的是用户在默认情况下的所有信息（默认情况下，是所有时间的所有种类的所有状态的信息）。</p>','14'),('40','HR加班列表-审批拒绝','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_reject.bmp\" />&rdquo;可以进行拒绝操作。审批前提：部门已审状态或总经理已审状态。审批条件：备注信息，必填选项。操作有：拒绝申请、取消，拒绝申请将指定的加班单设置为HR拒绝状态，取消为不进行任何改变。</p>','17'),('4028803118de62e30118deaa959a0001','帐套列表','<p>帐套列表是帐套管理的首页，页面包括帐套的查询和列表显示，帐套增删改的按钮。</p>\r\n<p>查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;包括按帐套名字的查询和是否显示所有版本，若显示所有版本，则所有历史版本和当前版本都会显示，否则只显示当前版本。点击&ldquo;<img alt=\"\" src=\"helpimages/add_acct.gif\" />&rdquo;进入新增页面；点击&ldquo;<img alt=\"\" src=\"helpimages/edit.gif\" />&rdquo;进入修改页面，点击&ldquo;<img alt=\"\" src=\"helpimages/common_copy.gif\" />&rdquo;进入复制页面；点击&ldquo;<img alt=\"\" src=\"helpimages/common_del.gif\" />&rdquo;确认后，删除当前版本帐套，若此帐套还有历史版本，则帐套使用上一版本，对应员工薪资配置自动修改，否则删除本帐套，使用本帐套的员工的薪资配置会被删除。若查看所有版本，历史版本是不可以修改的，点击&ldquo;<img alt=\"\" src=\"helpimages/view.gif\" />&rdquo;进入查看历史版本页面。</p>','64'),('4028803118de62e30118dec80abd0002','新增和复制帐套','<p>进入新增帐套页面后，填写帐套名称和帐套描述，注意帐套名称不能和现有帐套名称重复。</p>\r\n<p>然后依次填写帐套相名称，选择项目种类，字段属性。项目种类用于计算薪资汇总数据，分为18类，&ldquo;<img alt=\"\" src=\"helpimages/acctItemType.gif\" />&rdquo;图中红色字体为必选项，其他为选填相。 字段属性分为三类：&ldquo;<img alt=\"\" src=\"helpimages/acctCacType.gif\" />&rdquo;固定值是在配置薪资时填写的，浮动值是在薪资发放时填写的，而计算公式不需要填写薪资，但在此处配置帐套时要填写公式。公式计算是用按一定语法定义的计算器，输入的公式必须符合语法。公式输入完成后，若不符合语法则会出现&ldquo;<img alt=\"\" src=\"helpimages/common_warn.gif\" />&rdquo;；最后选择尾数舍入方式，默认为保留两位小数。</p>\r\n<p>符：公式语法</p>\r\n<p><strong>1.公式</strong>其实就是一些自定义的语法，包括变量和表达式，其中包含的关键字有：<font color=\"#ff0000\">＋－＊／（）? ，# A1 A2 ...A32　B1 C1 D1 B2 &lt; &gt; &gt;= &lt;= == </font><br />\r\n<strong>2.自定义变量1：</strong><font color=\"#556b2f\">A1 &mdash;&mdash;A32</font>，是指一个帐套中的帐套项目，一个帐套最多有32个项目，所以依次为A1到A32，An为常量或公式，一般来说应遵循由大到小的引用次序，例如第3个帐套项目即A3为公式：A1+A2*A1，这是正常的，而A3如果为公式：A4*A12，这种次序是不合理的，因为公式计算是按次序进行的。在计算A3时，A4或A12往往还没有赋值（如果他们也是公式的话），这种公式会导致意外的结果。<br />\r\n<strong>3.自定义变量2：</strong>B1，B2，C1，D1。这四个变量是根据具体员工而来的，B1是此员工的工龄、B2是单位工龄、C1是当月考勤天数、D1是薪资级别等级，任意公式中可以出现这四个变量。<br />\r\n<strong>4.自定义表达式1：</strong>算术表达式，如（A1+A2）*A3，B1/A3，A3*0.04，200，这就类似于平常的加减乘除算术表达式，其中的项可以是常数，也可以是1或2中自定义的变量。<br />\r\n<strong>5.自定义表达式2：</strong>?(X1, X2, X3) , 其中X1为布尔表达式，如A1&gt;A2，A3&lt;=0.9等，布尔表达式中只可以出现一次布尔操作符(包括： &lt; &gt; &gt;= &lt;= == )，布尔操作符两边是4中所说的自定义表达式1。X2和X3都是自定义表达式1。如果上述布尔表达式结果为true，则自定义表达式2结果取X2，否则取X3的结果。<br />\r\n<strong>6.自定义表达式3：</strong>#(max,min,value), 总共可以包含三个自定义表达式1。如#(A1,A2,A3)，max,min,value分别是 A1，A2，A3，调用evaluator获得数值后若value&gt;max则表达式取A1的值即max，若value&lt;min则表达式取A2的值即min，否则表达式去A3即value的值。<br />\r\n当前只定义了上述几种，随着版本的更新和公式算法的完善，可能还会有自定的变量和公式出现。</p>','64'),('4028803118de62e30118df3897d90003','修改帐套或另存为帐套新版本','<p>修改帐套页面和新增帐套页面类似。</p>\r\n<p>点击&ldquo;<img alt=\"\" src=\"helpimages/profile_check.gif\" />&rdquo;可以预览帐套修改后，使用本帐套的员工的薪资配置变化，点击&ldquo;<img alt=\"\" src=\"helpimages/update.gif\" />&rdquo;更新帐套，若帐套被更新，则使用本帐套的员工的薪资配置随之变化；点击&ldquo;<img alt=\"\" src=\"helpimages/saveAs.gif\" />&rdquo;按钮另存为新版本，旧版本封存，当前帐套使用新版本，同样，使用本帐套的员工的薪资配置随之变化。</p>','64'),('402880ed179f2d6701179f47a22c0002','我的资料-> 薪资信息','<p><span style=\"font-size: 9pt; color: black; font-family: 宋体\">页面可查看员工的薪资信息，单击第五行第二列的连接可以查看员工的福利标准，如单击＂＂跳出一个层显示员工的福利标准。单击列表表头链接＂３个月＂、＂６个月＂、＂１年＂分别可 以看到三个月、六个月和十二个月的薪资发放历史。单击链接＂调薪历史＂可以看到员工的所有调薪历史。</span></p>','30'),('402880f317b5e60e0117bf29f6490002','我的资料->考勤记录','<p>设置员工当前年份的年假余额，其中余额到期日的时间格式必须为xxxx-xx-xx格式，如2007-12-31，本年额度表示员工当年年假额度，必须为合法的数字</p>','30'),('402880f317b5e60e0117c37636e10006','删除资料','<p>首先选择任意一行，点击列表下面的<img src=\"helpimages/common_delete.gif\" alt=\"\" />按钮 ，弹出确认框，单击<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />,完成删除资料的操作。</p>','10'),('402880f317b5e60e0117c378cea80007','删除资料','<p>首先选择任意一行，点击列表下面的<img src=\"helpimages/common_delete.gif\" alt=\"\" />按钮 ，弹出确认框，单击<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />,完成删除资料的操作。</p>','10'),('402880f317b5e60e0117c38172e30008','备份文件.','<p>首先请认真阅读备份还原协议,并按要求仔细操作.备份数据:</p>\r\n<p>首先点击备份数据库按钮,如果你的数据库不是mysql或者你的mysql路径没有正确</p>\r\n<p>配置的话会弹出如下对话框. <img src=\"helpimages/mysqlbin.gif\" alt=\"\" />,在这个对话框中填入你的mysql的bin的绝对路径.</p>\r\n<p>然后点击确定按钮 <img alt=\"\" src=\"helpimages/confirm.gif\" />,mysql的路径会重新配置.然后退出系统,等待服务器重启.</p>\r\n<p>再次进入数据备份与还原页面,点击备份数据库按钮,如果这次配置正确的话会弹出</p>\r\n<p>提示框,提示你确不确定保存,点击确定<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />按钮,备份后的文件在下面列表中显示出来,</p>\r\n<p>文件初始化大小为0 ,当数据备份完后刷新页面,文件大小变为数据库的实际大小.</p>\r\n<p>&#160;</p>','59'),('402880f317b5e60e0117c3a0ef63000c','数据还原.','<p>选择列表中的一个文件,然后点击还原按钮<a href=\"#\">还原</a></p>\r\n<p>会弹出提示框,提示你是否确定还原,点击确定<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />按钮系统执行还原操作.</p>','59'),('402880f317b5e60e0117c3a0ef85000d','数据删除.','<p>选择列表中的一个文件,然后点击删除按钮<a href=\"#\">删除</a></p>\r\n<p>会弹出提示框,提示你是否确定删除,点击确定<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />按钮系统执行删除操作.</p>','59'),('402880f317c3a5cc0117c3a9ba520002','数据查询.','<p>设置了对模块,状态,日期的分段查询.</p>\r\n<p>对模块查询:点击如图<img src=\"helpimages/modelselect.gif\" alt=\"\" />下拉列表选择要清理的模块.也可以选择全部,表示对所有模块</p>\r\n<p>进行查询.</p>\r\n<p>对状态查询:点击如图<img src=\"helpimages/statusselect.gif\" alt=\"\" />下拉列表选择要清理的状态.也可以选择全部,表示对所有要清理的状态进行查询.</p>\r\n<p>对时间段的查询:可以对创建时间和修改时间进行分段查询.</p>\r\n<p>设置完查询条件,点击查找<img alt=\"\" src=\"helpimages/system_search.gif\" />按钮,下面的列表中将会显示查询到的数据.</p>\r\n<p>点击重置按钮<img src=\"helpimages/system_reset.gif\" alt=\"\" />,初始化查询条件 .</p>','60'),('402880f317c3a5cc0117c3b4579b0004','数据清理.','<p>选中列表中的数据然后点击删除按钮<img alt=\"\" src=\"helpimages/system_btDelete.gif\" />删除选中的数据.或者是点击列表表头的</p>\r\n<p>单选框选中所有数据删除.</p>','60'),('41','HR请假列表-查看请假','<p>点击对应的请假单编号显示对应的请假单详细信息。点击关闭按钮，关闭对应的请假详情显示。</p>','17'),('42','HR加班列表-查看加班','<p>点击对应的加班单编号显示对应的加班单详细信息。点击关闭按钮，关闭对应的加班详情显示。</p>','17'),('43','HR请假列表—批量审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_nsubmit.bmp\" />&rdquo;可以进行批量审批。审批前提：部门已审状态或总经理已审状态。</p>','17'),('44','HR加班列表—批量审批通过','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_nsubmit.bmp\" />&rdquo;可以进行批量审批。审批前提：部门已审状态或总经理已审状态。</p>','17'),('45','HR请假列表—log状态查看','<p>点击对应的请假单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前请假单的log信息。</p>','17'),('46','HR加班列表—log状态查看','<p>点击对应的加班单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前加班单的log信息。</p>','17'),('47','HR请假列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','17'),('48','HR加班列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','17'),('49','员工请假列表-查询','<p>查询条件有：请假种类、请假开始时间、请假结束时间、部门、员工、请假理由、请假状态，请假种类默认为全部种类，请假开始和结束时间必须同时填写，部门搜索必须具有相应的权限才可显示，员工查询为编号名字的模糊查询、理由查询为模糊查询,请假状态为非草稿状态的搜索。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','18'),('5','我的请假列表-新增请假','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_addleave.bmp\" />&rdquo;进入添加页面。添加条件有：请假种类、请假开始时间、请假结束时间、请假理由，上面的所有选项都为必填，开始与结束时间按半天记。操作有：提交请假单&ldquo;<img alt=\"\" src=\"helpimages/examin_submitleave.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交请假单是将当前请假单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后部门经理已审状态。</p>','14'),('50','员工加班列表-查询','<p>查询条件有：加班种类、加班开始时间、加班结束时间、部门、员工、加班理由、加班状态，加班种类默认为全部种类，加班开始和加班时间必须同时填写，部门搜索必须具有相应的权限才可显示，员工查询为编号名字的模糊查询、理由查询为模糊查询、加班状态为非草稿状态的搜索。操作有：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询则是按照用户选择的条件进行查询，重置为将所有搜索条件设置为默认值，并给出搜索结果。</p>','18'),('51','员工请假列表-废弃','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_cancel.bmp\" />&rdquo;可以进行废弃操作。废弃前提：HR已审状态。废弃条件：备注信息，非必填选项,。操作有：废弃申请、取消，废弃申请将指定的请假单设置为废弃状态，取消为不进行任何改变。</p>','18'),('52','员工加班列表-废弃','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_cancel.bmp\" />&rdquo;可以进行废弃操作。废弃前提：HR已审状态。废弃条件：备注信息，非必填选项。操作有：废弃申请、取消，废弃申请将指定的加班单设置为废弃状态，取消为不进行任何改变。</p>','18'),('53','员工请假列表-查看请假','<p>点击对应的请假单编号显示对应的请假单详细信息。点击关闭按钮，关闭对应的请假详情显示。</p>','18'),('54','员工加班列表-查看加班','<p>点击对应的加班单编号显示对应的加班单详细信息。点击关闭按钮，关闭对应的加班详情显示。</p>','18'),('55','员工请假列表—log状态查看','<p>点击对应的请假单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前请假单的log信息。</p>','18'),('56','员工加班列表—log状态查看','<p>点击对应的加班单的状态说明&ldquo;<img alt=\"\" src=\"helpimages/examin_log.bmp\" />&rdquo;，显示当前加班单的log信息。</p>','18'),('57','员工请假列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','18'),('58','员工加班列表—排序操作','<p>点击对应的标题栏，按照指定标题进行排序。</p>','18'),('59','考勤报表-查看','<p>报表统计条件：考勤报表类型、统计周期、统计种类、统计是否包括部门已审状态，按照考勤报表的种类，考勤报表分为请假报表和加班报表、统计周期以月计算、种类对应请假或加班的种类、默认情况下，统计周期为当年的1月至12月、种类为全部种类、不包括部门审批状态.填好条件后点击&ldquo;<img alt=\"\" src=\"helpimages/examin_report.bmp\" />&rdquo;就可以显示报表</p>','19'),('6','我的加班列表-新增加班','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_addovertime.bmp\" />&rdquo;进入添加页面。添加条件有：加班种类、日期、加班开始时间、结束时间、加班理由，上面所有选项全部为必填选项，开始时间必须小于结束时间。操作有：提交加班申请&ldquo;<img alt=\"\" src=\"helpimages/examin_submitovertime.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交是将加班单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后部门经理已审状态。</p>','14'),('60','考勤报表-pdf下载','<p>对应查看的条件，将报表输出为pdf格式。点击&ldquo;<img alt=\"\" src=\"helpimages/examin_pdf.bmp\" />&rdquo;下载</p>','19'),('61','当前年份-查询','<p>查询条件：部门、员工，部门默认为全部部门，员工是对应员工编号和员工姓名的模糊查询。操作：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询是根据条件讲结果显示、重置为将查询条件重置并显示默认结果。</p>','20'),('62','下一年份-查询','<p>查询条件：部门、员工，部门默认为全部部门，员工是对应员工编号和员工姓名的模糊查询。操作：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询是根据条件讲结果显示、重置为将查询条件重置并显示默认结果。</p>','20'),('63','历史年份-查询','<p>年份选择：默认为历史年份查询条件：部门、员工，部门默认为全部部门，员工是对应员工编号和员工姓名的模糊查询。操作：查询&ldquo;<img alt=\"\" src=\"helpimages/examin_search.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，查询是根据条件将结果显示、重置为将查询条件重置并显示默认结果。备注：历史年份只会显示存在的年假信息，而不会显示所有在职员工的年假信息，即没有年假的用户是不会被显示的。</p>','20'),('64','当前年份-年假信息更改','<p>更改条件：上年年假到期日、本年年度、备注，到期日如果不填写，表示上年余额不再可用，如果只填写备注，等于无效操作。</p>','20'),('65','下一年份-年假信息更改','<p>更改条件：上年年假到期日、本年年度、备注，到期日如果不填写，表示上年余额不再可用，如果只填写备注，等于无效操作。</p>','20'),('66','当前年份-批量年假信息更改','<p>对选定的员工进行批量年假信息修改，可操作前提为有选中。更改条件：上年年假到期日、本年年度、备注，到期日如果不填写，表示上年余额不再可用，如果只填写备注，等于无效操作。</p>','20'),('67','下一年份-批量年假信息更改','<p>对选定的员工进行批量年假信息修改，可操作前提为有选中。更改条件：上年年假到期日、本年年度、备注，到期日如果不填写，表示上年余额不再可用，如果只填写备注，等于无效操作。</p>','20'),('68','当前年份-删除年假','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;将对应行的年假信息删除。</p>','20'),('69','下一年份-删除年假','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;将对应行的年假信息删除。</p>','20'),('7','我的请假列表-修改请假','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;可进入修改页面。可修改前提：请假为已拒绝状态。修改条件有：请假种类、请假开始时间、请假结束时间、请假理由，上面的所有选项都为必填，开始与结束时间按半天记。操作有：提交请假单&ldquo;<img alt=\"\" src=\"helpimages/examin_submitleave.bmp\" />&rdquo;、重置&ldquo;<img alt=\"\" src=\"helpimages/examin_reset.bmp\" />&rdquo;，提交则是将加班单直接存储为提交状态进入审批流程，重置所有添加条件全部为初始值。普通员工提交后为已提交状态，部门经理提交后为部门已审状态，总经理提交后为总经理已审状态，HR经理提交后部门经理已审状态。</p>','14'),('70','当前年份-提交修改','<p>可操作前提是：存在修改项，将修改数据提交并保存。点击&ldquo;<img alt=\"\" src=\"helpimages/examin_submitupdate.bmp\" />&rdquo;</p>','20'),('71','下一年份-提交修改','<p>可操作前提是：存在修改项，将修改数据提交并保存。点击&ldquo;<img alt=\"\" src=\"helpimages/examin_submitupdate.bmp\" />&rdquo;</p>','20'),('72','当前年份-取消修改','<p>可操作前提是：存在修改项，将修改数据重新加载并显示而不进行保存。点击&ldquo;<img alt=\"\" src=\"helpimages/examin_cancelupdate.bmp\" />&rdquo;</p>','20'),('73','下一年份-取消修改','<p>可操作前提是：存在修改项，将修改数据重新加载并显示而不进行保存。点击&ldquo;<img alt=\"\" src=\"helpimages/examin_cancelupdate.bmp\" />&rdquo;</p>','20'),('74','当前年份-年假详细信息展示','<p>加载对应的用户对应年份的年假详细信息，同时加载有记录的请假单。</p>','20'),('75','下一年份-年假详细信息展示','<p>加载对应的用户对应年份的年假详细信息，同时加载有记录的请假单。</p>','20'),('76','历史年份-年假详细信息展示','<p>加载对应的用户对应年份的年假详细信息，同时加载有记录的请假单。</p>','20'),('77','假期日历-查看','<p>按照年份进行查看。</p>','21'),('78','假期日历-添加','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_addcalendar.bmp\" />&rdquo;进行添加。添加条件：对应的日期、工作地点、种类、描述，日期必填、工作地点默认全部地区、类型包括假日与工作日。</p>','21'),('79','假期日历-修改','<p>双击或者选择后点击&ldquo;<img alt=\"\" src=\"helpimages/examin_updatecalendar.bmp\" />&rdquo;进行修改。修改条件：对应的日期、工作地点、种类、描述，日期必填、工作地点默认全部地区、类型包括假日与工作日。操作：提交、取消，提交情况将数据保存,取消不进行任何修改。</p>','21'),('80','假期日历-删除','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_deletecalendar.bmp\" />&rdquo;删除选定的日历。</p>','21'),('81','新增用户','<p>点击<img src=\"helpimages/system_addUser.gif\" alt=\"\" />进入新增用户界面.<br />\r\n员工姓名：列出所有尚未创建相应用户的员工。 用户名：缺省为员工编号。 在下一行的“用户所属角色”里面选择该用户的角色（可以多选）。点击<img src=\"helpimages/common_submitB.gif\" alt=\"\" />按钮 ，添加用户完成，点击<img src=\"helpimages/common_back.gif\" alt=\"\" />按钮 ，返回用户列表界面。</p>','7'),('82','删除用户','<p>点击列表中的 <u>删除</u> 链接 ，会弹出确认框，点击<img src=\"helpimages/common_confirmB.gif\" alt=\"\" /> ，完成删除用户操作。</p>','7'),('83','修改用户状态','<p>点击列表中的停用图标<img src=\"helpimages/image040.jpg\" alt=\"\" />，弹出确认框，点击启用图标<img alt=\"\" src=\"helpimages/common_confirmB.jpg\" />,完成停用用户操作。点击 <img src=\"helpimages/image040.jpg\" alt=\"\" />，实现启用用户操作。修改用户角色：点击列表中的 <u>用户角色</u> 链接 ，弹出修改角色界面层， 选择该用户的角色（可以多选），点击<img alt=\"\" src=\"helpimages/common_submitB.gif\" />按钮 ，修改用户角色操作完成，点击<img alt=\"\" src=\"helpimages/common_concal.gif\" /> 按钮，返回用户列表界面。<br />\r\n<img alt=\"\" src=\"helpimages/system_changeRole.gif\" /></p>','7'),('84','查看权限','所在模块：列出权限的模块信息。 \r\n权限描述：对权限的相关说明。 \r\n相关提示如下\r\nAction：0=查询 | 1=新增 | 2=修改 | 3=删除 | 6=打印 | 7=数据导出 | 8=数据导入   Condition：0=无 | 1=本人 | 2=部门 | 3=所有','9'),('85','查看资料','<p>用户可以点列表上方的Tab“<img src=\"helpimages/sys_config_tab.jpg\" alt=\"\" />”,可以切换查看各个选项卡的的信息。</p>','10'),('86','增加资料','<p>点击列表下面的<img src=\"helpimages/common_add.gif\" alt=\"\" />按钮 ，根据选择的信息类型，会打开不同的层如下<br />\r\n<img src=\"helpimages/command_add.jpg\" alt=\"\" /> <br />\r\n对应每一个层，按提示操作填写内容，单击<img src=\"helpimages/common_save.gif\" alt=\"\" /> 完成增加相应资料操作。单击“清空” ，层上对应的信息将被清空。点击<img src=\"helpimages/common_close.gif\" alt=\"\" />，关闭层，取消本次操作。</p>','10'),('87','修改资料','<p>首先选择任意一行，点击列表下面的<img src=\"helpimages/common_update.gif\" alt=\"\" /> 按钮 或者双击某一行，根据选择的信息类型，会打开不同的层，对应每一个层，按提示操作填写内容，单击<img src=\"helpimages/common_save.gif\" alt=\"\" /> 完成修改相应资料操作。单击<img src=\"helpimages/common_clear.gif\" alt=\"\" />，层上对应的信息将被清空。点击<img src=\"helpimages/common_close.gif\" alt=\"\" />，关闭层，取消本次操作</p>','10'),('88','新增模版','<p>点击<img src=\"helpimages/system_addModel.gif\" alt=\"\" />按钮 ，打开新增模版层 <br />\r\n<img src=\"helpimages/system_emailModel.gif\" alt=\"\" /> <br />\r\n填写“编号”，“标题”，“内容”，“备注”。点击<img src=\"helpimages/system_insert.gif\" alt=\"\" />按钮，插入固定的标签字段。点击<img src=\"helpimages/system_modify.gif\" alt=\"\" />，确认，完成新增模版操作，关闭层。点击<img src=\"helpimages/common_concal.gif\" alt=\"\" />，按钮 ，放弃本次操作，关闭层。</p>','11'),('89','修改模版','<p>点击<img alt=\"\" src=\"helpimages/edit.gif\" />按钮 ，打开修改模版层 <br />\r\n<img alt=\"\" src=\"helpimages/system_emailModel.gif\" /> <br />\r\n填写“编号”，“标题”，“内容”，“备注”。点击<img alt=\"\" src=\"helpimages/system_insert.gif\" />按钮，插入固定的标签字段。点击<img alt=\"\" src=\"helpimages/system_modify.gif\" />，确认，完成新增模版操作，关闭层。点击<img alt=\"\" src=\"helpimages/common_concal.gif\" />，按钮 ，放弃本次操作，关闭层。</p>','11'),('90','预览模版','<p>点击列表中的 <img src=\"helpimages/mailscan.gif\" alt=\"\" /> 链接 ，打开预览模版层 <br />\r\n<img src=\"helpimages/system_view.gif\" alt=\"\" /> <br />\r\n这里显示了模版的页面效果。 点击<img src=\"helpimages/common_concal.gif\" alt=\"\" />按钮 ，关闭层。</p>','11'),('91','删除模版','<p>点击列表中的 <img src=\"helpimages/common_del.gif\" alt=\"\" /> 链接 ，点击确认对话框的<img src=\"helpimages/common_confirmB.gif\" alt=\"\" />按钮，执行删除模版的操作</p>','11'),('92','查看邮件','<p>可按“收件人”，“标题”，“状态”，“创建时间”，“发送时间”来设置查询条件。 <br />\r\n<img alt=\"\" src=\"helpimages/system_emailSh.gif\" /> <br />\r\n点击<img alt=\"\" src=\"helpimages/system_search.gif\" /> 按钮，显示查询到的邮件列表。点击<img alt=\"\" src=\"helpimages/system_reset.gif\" /> 按钮，初始化邮件列表即显示所有的邮件列表。</p>','12'),('93','预览邮件','<p>点击列表中的 <img src=\"helpimages/mailscan.gif\" alt=\"\" /> 链接，打开预览模版层，这里显示了模版的页面效果。 <br />\r\n<img src=\"helpimages/system_view.gif\" alt=\"\" /> <br />\r\n点击<img src=\"helpimages/common_concal.gif\" alt=\"\" /> 按钮 ，关闭层。</p>','12'),('94','删除邮件','<p>点击列表中的<img src=\"helpimages/common_del.gif\" alt=\"\" />图标 ，点击确认框中的<img src=\"helpimages/common_confirmB.gif\" alt=\"\" /> 按钮，完成删除邮件操作。</p>','12'),('95','重发邮件','<p>点击列表中的 <img src=\"helpimages/mailresend.gif\" alt=\"\" /> 链接 ，点击确认框中的<img alt=\"\" src=\"helpimages/common_confirmB.gif\" /> 按钮，完成重发邮件操作。</p>','12'),('96','批量删除','<p>选择要删除的邮件，点击<img alt=\"\" src=\"helpimages/system_btDelete.gif\" /> 按钮 ，点击确认框中的<img alt=\"\" src=\"helpimages/common_confirmB.gif\" /> 按钮，完成批量删除邮件操作。</p>','12'),('97','批量重发','<p>选择要删除的邮件，点击<img src=\"helpimages/system_resend.gif\" alt=\"\" /> 按钮 ，点击确认框中的<img src=\"helpimages/system_btDelete.gif\" alt=\"\" /> 按钮，完成批量重发邮件操作。</p>','12'),('98','查看信息','<p>包含的信息有&ldquo;公司新闻&rdquo;，&ldquo;文档下载&rdquo;，&ldquo;最新公告&rdquo;，&ldquo;先进人物&rdquo;，&ldquo;职位空缺&rdquo;和&ldquo;新增公司信息&rdquo;。<br />\r\n<img alt=\"\" src=\"helpimages/system_infoRight.gif\" /> <br />\r\n点击右侧的链接，可以查看相应的信息。</p>','13'),('99','新增信息','<p>隶属分类：标记信息属于那个类型。所属部门信息：标记该新闻是反映那个部门的信息。点击右侧的<img alt=\"\" src=\"helpimages/system_addInfoLk.gif\" />链接，进入新增信息界面。 选择&ldquo;隶属分类&rdquo;，&ldquo;状态信息&rdquo;，&ldquo;所属部门信息&rdquo; <img alt=\"\" src=\"helpimages/system_selectDept.gif\" />，填写&ldquo;标题&rdquo;，&ldquo;简述&rdquo;，&ldquo;正文&rdquo;，点击 <img alt=\"\" src=\"helpimages/common_view.gif\" />按钮，选择上传图片和上传附件，填写图片说明和附件说明，点击<img alt=\"\" src=\"helpimages/system_addInfo.gif\" />按钮 ，完成新增信息操作，进入信息查看页面。点击<img alt=\"\" src=\"helpimages/system_back.gif\" />按钮 放弃当前操作，进入信息查看页面。</p>','13'),('a081808017bf50e50117bf67d65d0001','员工加班列表—删除操作','<p>对于有权限的用户提供删除操作，点击删除按钮&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;并确认后会删除该加班申请</p>','18'),('a081808017bf50e50117bf67d6880002','员工请假列表—删除操作','<p>对于有权限的用户提供删除操作，点击删除按钮&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;并确认后会删除该请假申请</p>','18'),('a081808017bf50e50117bf6bfeb60003','HR请假列表-修改操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;HR经理可以对需要审批的请假申请进行修改，点击修改按钮后进入修改页面，除用户外其他信息都可修改，修改完毕后点击审批通过&ldquo;<img alt=\"\" src=\"helpimages/examin_approve1.bmp\" />&rdquo;，该条请假申请的内容就会被修改，并且状态变为HR已审状态。</p>','17'),('a081808017bf50e50117bf6bfed00004','HR加班列表-修改操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;HR经理可以对需要审批的加班申请进行修改，点击修改按钮后进入修改页面，除用户外其他信息都可修改，修改完毕后点击审批通过&ldquo;<img alt=\"\" src=\"helpimages/examin_approve1.bmp\" />&rdquo;，该条加班申请的内容就会被修改，并且状态变为HR已审状态。</p>','17'),('a081808017bf50e50117bf6bfede0005','HR请假列表-删除操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;HR经理可以对需要审批的请假申请进行删除操作，点击删除按钮并确认后，该条请假申请就会被删除。</p>','17'),('a081808017bf50e50117bf6bfeea0006','HR加班列表-删除操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;HR经理可以对需要审批的加班申请进行删除操作，点击删除按钮并确认后，该条加班申请就会被删除。</p>','17'),('a081808017bf50e50117bf7151840007','部门请假列表—修改操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;部门经理或总经理对需要审批的请假申请可以进行修改操作，点击修改按钮后会进入修改页面，除员工外其他信息都可以修改，修改完毕后点击审批通过&ldquo;<img alt=\"\" src=\"helpimages/examin_approve1.bmp\" />&rdquo;按钮，该条请假申请的内容就会被修改，并且状态会根据权限变为部门已审或总经理已审状态。</p>','16'),('a081808017bf50e50117bf7151a30008','部门加班列表—修改操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_update.bmp\" />&rdquo;部门经理或总经理对需要审批的加班申请可以进行修改操作，点击修改按钮后会进入修改页面，除员工外其他信息都可以修改，修改完毕后点击审批通过&ldquo;<img alt=\"\" src=\"helpimages/examin_approve1.bmp\" />&rdquo;按钮，该条加班申请的内容就会被修改，并且状态会根据权限变为部门已审或总经理已审状态。</p>','16'),('a081808017bf50e50117bf7151c40009','部门请假列表—删除操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;部门经理或总经理可以对需要审批的请假申请进行删除操作，点击删除按钮并且确认后，该条请假记录就会被删除。</p>','16'),('a081808017bf50e50117bf7151cf000a','部门加班列表—删除操作','<p>点击&ldquo;<img alt=\"\" src=\"helpimages/examin_delete.bmp\" />&rdquo;部门经理或总经理可以对需要审批的加班申请进行删除操作，点击删除按钮并且确认后，该条加班记录就会被删除。</p>','16'),('a081808017c358c40117c3a76fe00005','修改公司资料','<p>根据页面内容用户可以修改公司的资料，其中公司编号和限制用户是不能修改的，填写完毕后，点击<img alt=\"\" src=\"helpimages/sys_clientsubmit.bmp\" />按钮即设置成功！</p>','62'),('a081808017c358c40117c3abec690006','设置考勤限制','<p>可以对考勤流程中是否需要总经理审批进行设置，如果需要总经审批，可以甚至按照提示设置限制条件，设置完毕后点击<img alt=\"\" src=\"helpimages/sys_sysupdate.bmp\" />按钮即可。</p>','63');
/*!40000 ALTER TABLE `help` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `helpclass`
--

DROP TABLE IF EXISTS `helpclass`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `helpclass` (
  `hc_id` varchar(36) NOT NULL default '',
  `hc_class_name` varchar(128) NOT NULL default '',
  `hc_father_id` varchar(36) default NULL,
  `hc_sort_id` int(10) unsigned NOT NULL default '0',
  `hc_brief` varchar(255) default NULL,
  PRIMARY KEY  (`hc_id`),
  KEY `FK_hc_father_id` (`hc_father_id`),
  CONSTRAINT `FK_hc_father_id` FOREIGN KEY (`hc_father_id`) REFERENCES `helpclass` (`hc_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `helpclass`
--

LOCK TABLES `helpclass` WRITE;
/*!40000 ALTER TABLE `helpclass` DISABLE KEYS */;
INSERT INTO `helpclass` VALUES ('1','首页',NULL,1,''),('10','基本资料设置','58',4,'用户可以对系统的全局配置信息，包含员工状态，新闻种类，休假种类，薪资计算周期，合同种类，货币，加薪类型，加班类型自定义字段进行查看，增加，删除，修改操作。'),('11','邮件模板设置','58',5,'设置邮件模版，包含功能有：新增，修改，删除，预览。'),('12','邮件管理','58',6,'邮件模版管理，包含功能有：预览，删除，重发，查询，批量重发，批量删除。'),('13','信息管理','1',1,'显示公司的相关信息，包含相关功能有查看信息，快速修改，完全修改，删除信息，新增信息。'),('14','我的考勤列表','4',1,'用于管理个人考勤记录，包括两个模块，请假模块、加班模块。主要提供个人考勤信息的查找、查看、添加、修改、删除功能。'),('15','考勤申请','4',2,'用于提出个人考勤申请，包括两个模块，请假模块、加班模块。主要提供考勤信息的添加功能。'),('16','部门经理审批','4',3,'用于部门经理或总经理对其管辖的人员进行考勤管理，包括两个模块，请假模块、加班模块。主要提供部门经理或总经理对加班和请假信息的审批操作。'),('17','HR经理审批','4',4,'用于HR经理对其管辖的人员进行考勤管理，包括两个模块，请假模块、加班模块。主要提供HR经理对加班和请假信息的审批操作。'),('18','员工考勤列表','4',5,'用于为部门经理、hr经理、考勤管理人员提供考勤信息的查看和删除同时为考勤管理用户提供废弃功能，包括两个模块，请假模块、加班模块。'),('19','考勤报表','4',6,'用于为考勤管理人员提供员工年假信息的配置管理和查看，包括当前年份模块，下一年份、历史查看。'),('2','员工',NULL,2,''),('20','员工年假余额表','4',7,'用于为考勤管理人员提供员工年假信息的配置管理和查看，包括当前年份模块，下一年份、历史查看。'),('21','假期日历设置','4',8,'用于为考勤管理人员提供假期日历的设置，日历记录的除周末以外的假期和调价工作日。'),('22','员工薪资列表','3',1,'显示员工薪资列表，并进行简单的查询可以执行的操作有查找员工薪资配置信息，添加员工薪资配置信息，删除员工薪资配置信息，查看员工薪资配置的详细信息，修改员工薪资配置，员工薪资配置信息数据导出。'),('23','我的调薪计划','3',4,'用户可以在此页面上查询调薪计划，创建调薪计划，修改调薪计划，删除调薪计划，提交调薪计划，查看调薪计划的日志记录，查看员工薪资配置的详细信息，查看薪资汇总信息，查看员工的基本信息。'),('24','HR经理审批','3',5,'显示待审批的调薪计划列表（状态为部门已审），用户可以单条或批量地同意或拒绝调薪计划，并可以进行简单的查询，查看员工薪资配置的详细信息，查看薪资汇总信息，查看员工的基本信息，查看调薪计划的日志记录。'),('25','调薪计划列表','3',6,'用户可以在此页面中查询所有的调薪计划，对状态为HR已审的进行作废和手工调整，查看员工薪资配置的详细信息，查看薪资汇总信息，查看员工的基本信息，查看调薪计划的日志记录，调薪计划数据导出。'),('26','薪资发放','3',3,'按年月显示薪资发放列表，并进行简单的查询及数据导出。可以执行的操作有查询薪资发放，初始化薪资发放项目，查看/修改薪资发放项目，保存薪资发放信息，删除员工的薪资发放，提交薪资发放封帐，审核薪资发放封帐，薪资发放数据导出。'),('27','薪资综合报表','3',7,'薪资综合报表分为薪资发放历史和薪资发放预测两部分.'),('28','员工薪资导入','3',2,'上传一个excel文件,并将excel文件的内容导入数据库.'),('29','薪资等级','3',9,'用户可以在此页面薪资等级信息进行添加，删除，修改，查看的操作。'),('3','薪资',NULL,3,''),('30','我的资料','2',1,'查看修改当前员工的基本信息、档案和薪资信息，管理员可修改所有的字段，普通员工只能查看个人信息不能对个人或他人资料进行编辑修改。'),('31','新增员工','2',2,'系统管理员可以增加新员工。增加过程分为五步，第一步是添加员工信息，第二步是添加员工附加信息，第三步是添加员工人事档案，第四步是配置员工薪资，第五步是配置员工考勤信息．'),('32','员工列表','2',3,'员工查询页面，包括基本查询和高级查询．可对查询结果进行批量删除和修改，可以导出列表到EXCEL中。'),('33','档案管理','2',4,'系统管理员可以查询员工的档案，包括员工人事合同，调动记录，奖惩记录，离职记录的搜索功能'),('34','员工通讯录','2',5,'查看公司的组织结构'),('35','员工信息导入','2',6,'上传一个excel文件,并将excel文件的内容导入数据库.'),('36','修改密码','2',7,'修改当前员工的密码'),('37','员工报表','2',8,'输出员工报表'),('38','组织结构管理','2',9,'管理员可以对办公地点、部门和业务单元，用工形式进行查看，新增，修改，删除配置'),('4','考勤',NULL,4,''),('40','我的招聘计划','57',1,'显示当前用户的招聘计划列表，可进行本人创建招聘计划的查询、增加、修改、提交操作'),('41','新增招聘计划','57',2,'创建一个新的招聘计划'),('42','部门经理审批','57',3,'概述：显示当前用户所在部门的所有状态为已提交的招聘计划列表，部门经理或总经理可进行列表中招聘计划的查询、审批操作。'),('43','HR经理审批','57',4,'显示所有状态为部门已审的招聘计划列表，HR经理可进行列表中招聘计划的查询、审批操作。'),('44','招聘计划列表','57',5,'显示除状态为草稿以外的所有招聘计划列表，招聘管理员可进行列表中招聘计划的查询。对状态为HR已审的招聘计划进行编辑、废除、中止、关闭、删除等操作。'),('45','应聘者管理','57',6,'所有应聘者列表，招聘管理员可进行列表中应聘者的查询，增加，修改，删除等操作。'),('46','招聘报表','57',7,'察看招聘计划和应聘者报表'),('47','招聘渠道列表','57',8,'所有招聘渠道列表，招聘管理员可进行列表中招聘渠道增加，修改，删除等操作。'),('48','培训',NULL,48,NULL),('49','我的培训计划','48',1,'显示我的培训计划列表，登陆者可以方便的查询自己申请成功的培训计划，察看课程的详细信息和课程计划状态'),('50','培训申请','48',2,'显示培训计划列表，登陆者可以方便的查询所有的培训计划，并根据自己的情况来申请相应的课程'),('51','部门经理审批','48',3,'概述：显示待审批的培训计划列表（状态为已提交），用户可以单条或批量地同意或拒绝培训计划，并可以进行简单的查询，查看培训计划的详细信息。'),('52','HR经理审批','48',4,'概述：显示待审批的培训计划列表（状态为部门已审），用户可以单条或批量地同意或拒绝培训计划，并可以进行简单的查询，查看培训计划的详细信息。'),('53','员工培训计划','48',5,'该列表显示了所有部门的员工申请过的各种状态的培训计划，在该列表中，可以根据基本条件查询某个课程的培训计划，并可以查看课程计划的详细信息，对于状态为“HR已审”的培训计划，还可以进行 “作废”和“反馈”操作，单击列表中的状态，可以查看该状态的操作记录（即logs）。'),('54','培训报表','48',6,'报表查看和PDF下载'),('55','课程设置','48',7,'这是一张显示培训课程的列表，在该列表中，可以根据基本条件查询某个课程，该列表可进行的操作为添加新课程、并对课程进行查看、修改、删除以及关闭和允许开课等操作，在查看课程的详细信息页面，可以添加该课程的培训计划，一个课程可以添加多个计划，并可以删除、修改以及关闭/允许申请培训计划。'),('56','培训种类','48',8,'该列表用于对培训种类进行统一的管理，可以“增加”、“修改”、“删除”培训种类。'),('57','招聘',NULL,57,''),('58','系统',NULL,58,''),('59','备份与还原','58',4,'备份数据库中的数据,生成.sql文档,用来防止数据意外遭到破坏而无法恢复.'),('60','数据清理','58',5,'清除系统中薪资,考勤,培训,招聘状态为草稿,已作废,已拒绝的信息.'),('61','客户管理','58',6,'暂时不对用户开放!'),('62','公司资料设置','58',4,'对用户所在公司的基本信息进行设置，用户可以查看和修改。'),('63','系统资料设置','58',4,'对影响整个系统运行的一些参数进行设置'),('64','帐套管理','3',10,'提供对员工帐套的增删改查和帐套版本的管理等操作，还包括帐套查询，新增帐套，更新帐套，另存为新版本，复制帐套，删除帐套等。'),('7','用户管理','58',1,'显示用户列表，并进行简单的查询可以执行的操作有查找用户，添加用户，修改用户密码，修改用户角色，修改用户状态，删除用户。'),('8','角色管理','58',2,'显示角色列表，可以查看角色信息，增加角色，修改角色，删除角色。'),('9','权限管理','58',3,'显示权限列表，可以查看权限的相关信息。');
/*!40000 ALTER TABLE `helpclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infoclass`
--

DROP TABLE IF EXISTS `infoclass`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `infoclass` (
  `infoclass_id` varchar(36) NOT NULL default '',
  `infoclass_name` varchar(16) NOT NULL default '',
  `infoclass_sort_id` int(10) unsigned NOT NULL default '0',
  `infoclass_status` int(10) unsigned NOT NULL default '1',
  PRIMARY KEY  (`infoclass_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `infoclass`
--

LOCK TABLES `infoclass` WRITE;
/*!40000 ALTER TABLE `infoclass` DISABLE KEYS */;
INSERT INTO `infoclass` VALUES ('116f35da-2c9b-4gc6-b9ac-acd9538b5787','文档下载',5,1),('126f35da-2c9b-4gc6-b9ac-acd9548b5787','最新公告',1,1),('136f35da-2c9b-4gc6-b9ac-acd9558b5787','先进人物',3,1),('146f35da-2c9b-4gc6-b9ac-acd9568b5787','职位空缺',4,1),('196f35da-2c9b-4gc6-b9ac-acd9528b5787','公司新闻',2,1);
/*!40000 ALTER TABLE `infoclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `information`
--

DROP TABLE IF EXISTS `information`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `information` (
  `info_id` varchar(36) NOT NULL default '',
  `info_class_id` varchar(36) NOT NULL default '',
  `info_dept_limit` varchar(255) default NULL,
  `info_title` varchar(64) NOT NULL default '',
  `info_breif` varchar(255) NOT NULL default '',
  `info_content` mediumtext NOT NULL,
  `info_file_name` varchar(128) default NULL,
  `info_file_desc` varchar(255) default NULL,
  `info_pic_name` varchar(128) default NULL,
  `info_pic_desc` varchar(255) default NULL,
  `info_viewed_num` int(10) unsigned NOT NULL default '0',
  `info_status` int(10) unsigned NOT NULL default '1',
  `info_create_by` varchar(36) NOT NULL default '',
  `info_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `info_last_change_by` varchar(36) NOT NULL default '',
  `info_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`info_id`),
  KEY `FK_info_class_id` (`info_class_id`),
  CONSTRAINT `FK_info_class_id` FOREIGN KEY (`info_class_id`) REFERENCES `infoclass` (`infoclass_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `information`
--

LOCK TABLES `information` WRITE;
/*!40000 ALTER TABLE `information` DISABLE KEYS */;
/*!40000 ALTER TABLE `information` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inmatch`
--

DROP TABLE IF EXISTS `inmatch`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `inmatch` (
  `im_id` varchar(36) NOT NULL,
  `im_imm_id` varchar(36) NOT NULL,
  `im_imb_id` varchar(36) NOT NULL,
  `im_field_desc` varchar(64) NOT NULL,
  `im_format` varchar(32) default NULL,
  `im_detect_error` int(10) unsigned NOT NULL default '0',
  `im_required` int(10) unsigned NOT NULL default '0',
  `im_is_input` int(10) unsigned NOT NULL default '0',
  `im_sort_id` int(10) unsigned NOT NULL default '0',
  `im_sample` varchar(255) NOT NULL default '',
  `im_sample_width` int(10) unsigned default NULL,
  PRIMARY KEY  (`im_id`),
  UNIQUE KEY `imm_imb` (`im_imm_id`,`im_imb_id`),
  KEY `FK_im_imb_id` (`im_imb_id`),
  CONSTRAINT `FK_im_imb_id` FOREIGN KEY (`im_imb_id`) REFERENCES `inmatchbasic` (`imb_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_im_imm_id` FOREIGN KEY (`im_imm_id`) REFERENCES `inmatchmodel` (`imm_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `inmatch`
--

LOCK TABLES `inmatch` WRITE;
/*!40000 ALTER TABLE `inmatch` DISABLE KEYS */;
INSERT INTO `inmatch` VALUES ('4028801b2450a1c2012451c962f80022','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2d50001','工号','36',0,1,1,0,'tyhrm0001',15),('4028801b2450a1c2012451c962f80023','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50002','1',NULL,0,0,1,3,'常',NULL),('4028801b2450a1c2012451c962f80024','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50003','2',NULL,0,0,1,4,'常',NULL),('4028801b2450a1c2012451c962f80025','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50004','3',NULL,0,0,1,5,'常',NULL),('4028801b2450a1c2012451c962f80026','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50005','4',NULL,0,0,1,6,'常',NULL),('4028801b2450a1c2012451c962f80027','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50006','5',NULL,0,0,1,7,'常',NULL),('4028801b2450a1c2012451c962f80028','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50007','6',NULL,0,0,1,8,'常',NULL),('4028801b2450a1c2012451c962f80029','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50008','7',NULL,0,0,1,9,'常',NULL),('4028801b2450a1c2012451c962f8002a','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e50009','8',NULL,0,0,1,10,'常',NULL),('4028801b2450a1c2012451c962f8002b','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c4b2e5000a','9',NULL,0,0,1,11,'常',NULL),('4028801b2450a1c2012451c962f8002c','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf6000b','10',NULL,0,0,1,12,'常',NULL),('4028801b2450a1c2012451c962f8002d','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf6000c','11',NULL,0,0,1,13,'常',NULL),('4028801b2450a1c2012451c962f8002e','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf6000d','12',NULL,0,0,1,14,'常',NULL),('4028801b2450a1c2012451c962f8002f','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf6000e','13',NULL,0,0,1,15,'常',NULL),('4028801b2450a1c2012451c963070030','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf6000f','14',NULL,0,0,1,16,'常',NULL),('4028801b2450a1c2012451c963070031','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf60010','15',NULL,0,0,1,17,'常',NULL),('4028801b2450a1c2012451c963070032','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf60011','16',NULL,0,0,1,18,'常',NULL),('4028801b2450a1c2012451c963070033','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf60012','17',NULL,0,0,1,19,'常',NULL),('4028801b2450a1c2012451c963070034','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf60013','18',NULL,0,0,1,20,'常',NULL),('4028801b2450a1c2012451c963070035','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c6ecf60014','19',NULL,0,0,1,21,'常',NULL),('4028801b2450a1c2012451c963070036','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c0015','20',NULL,0,0,1,22,'常',NULL),('4028801b2450a1c2012451c963070037','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c0016','21',NULL,0,0,1,23,'常',NULL),('4028801b2450a1c2012451c963070038','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c0017','22',NULL,0,0,1,24,'常',NULL),('4028801b2450a1c2012451c963070039','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c0018','23',NULL,0,0,1,25,'常',NULL),('4028801b2450a1c2012451c96307003a','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c0019','24',NULL,0,0,1,26,'常',NULL),('4028801b2450a1c2012451c96307003b','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c001a','25',NULL,0,0,1,27,'常',NULL),('4028801b2450a1c2012451c96307003c','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c001b','26',NULL,0,0,1,28,'常',NULL),('4028801b2450a1c2012451c96307003d','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c001c','27',NULL,0,0,1,29,'常',NULL),('4028801b2450a1c2012451c96307003e','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c001d','28',NULL,0,0,1,30,'常',NULL),('4028801b2450a1c2012451c96307003f','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c7f31c001e','29',NULL,0,0,1,31,'常',NULL),('4028801b2450a1c2012451c963070040','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c84daf001f','30',NULL,0,0,1,32,'常',NULL),('4028801b2450a1c2012451c963070041','4028801b2450a1c2012451c962f80021','4028801b2450a1c2012451c84daf0020','31',NULL,0,0,1,33,'常',NULL),('4028801b2450a1c2012451dbf1b30050','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0042','工号','36',0,1,1,0,'tyhrm0001',15),('4028801b2450a1c2012451dbf1b30051','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0043','考勤卡号',NULL,0,0,1,3,'card0001',NULL),('4028801b2450a1c2012451dbf1b30052','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0044','考勤机号',NULL,0,0,1,4,'machine01',NULL),('4028801b2450a1c2012451dbf1b30053','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0045','考勤日期','yyyy-MM-dd',0,1,1,5,'2009-10-14',12),('4028801b2450a1c2012451dbf1b30054','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0046','刷卡时间1',NULL,0,1,1,6,'08:54',NULL),('4028801b2450a1c2012451dbf1b30055','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0047','刷卡时间2',NULL,0,0,1,7,'08:54',NULL),('4028801b2450a1c2012451dbf1b30056','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0048','刷卡时间3',NULL,0,0,1,8,'08:54',NULL),('4028801b2450a1c2012451dbf1b30057','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c0049','刷卡时间4',NULL,0,0,1,9,'08:54',NULL),('4028801b2450a1c2012451dbf1b30058','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c004a','刷卡时间5',NULL,0,0,1,10,'08:54',NULL),('4028801b2450a1c2012451dbf1b30059','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d03d6c004b','刷卡时间6',NULL,0,0,1,11,'08:54',NULL),('4028801b2450a1c2012451dbf1b3005a','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d0cf3c004c','刷卡时间7',NULL,0,0,1,12,'08:54',NULL),('4028801b2450a1c2012451dbf1b3005b','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d0cf3c004d','刷卡时间8',NULL,0,0,1,13,'08:54',NULL),('4028801b2450a1c2012451dbf1b3005c','4028801b2450a1c2012451dbf1b3004f','4028801b2450a1c2012451d2b801004e','备注',NULL,0,0,1,14,'备注',25),('402880e526f9694f0126f9dee0e70014','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5837c0001','一级部门','64',0,0,1,6,'人力资源部',NULL),('402880e526f9694f0126f9dee0e70015','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5838c0002','二级部门','64',0,0,1,7,'人力资源部',NULL),('402880e526f9694f0126f9dee0e70016','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5838c0003','三级部门','64',0,0,1,8,'人力资源部',NULL),('402880e526f9694f0126f9dee0e70017','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5838c0004','四级部门','64',0,0,1,9,'人力资源部',NULL),('402880e526f9694f0126f9dee0e70018','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5838c0005','五级部门','64',0,0,1,10,'人力资源部',NULL),('402880e526f9694f0126f9dee0e7001a','402880f0248a706001248a8b93e90001','402880e526f9694f0126f9b5839b0006','职位','64',0,1,1,12,'工程师',NULL),('402880e52717e27e012718460110000b','402880f02479d73701247a10a78a0063','402880e52717e27e0127184600c20001','变动前一级部门','64',0,0,1,4,'咨询部',20),('402880e52717e27e012718460110000c','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010002','变动前二级部门','64',0,0,1,5,'技术部',20),('402880e52717e27e012718460110000d','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010003','变动前三级部门','64',0,0,1,6,'技术部',20),('402880e52717e27e012718460110000e','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010004','变动前四级部门','64',0,0,1,7,'技术部',20),('402880e52717e27e012718460110000f','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010005','变动前五级部门','64',0,0,1,8,'技术部',20),('402880e52717e27e0127184601100010','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010006','变动前部门','64',0,0,1,9,'技术部',20),('402880e52717e27e0127184601100011','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010007','变动前职位','64',0,0,1,10,'技术总监',20),('402880e52717e27e0127184601100012','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010008','变动后一级部门','64',0,0,1,11,'技术部',20),('402880e52717e27e0127184601100013','402880f02479d73701247a10a78a0063','402880e52717e27e0127184601010009','变动后二级部门','64',0,0,1,12,'技术部',20),('402880e52717e27e0127184601100014','402880f02479d73701247a10a78a0063','402880e52717e27e012718460110000a','变动后三级部门','64',0,0,1,13,'技术部',20),('402880e52717e27e012718506a3a001a','402880f02479d73701247a10a78a0063','402880e52717e27e012718506a3a0015','变动后四级部门','64',0,0,1,14,'技术部',20),('402880e52717e27e012718506a3a001b','402880f02479d73701247a10a78a0063','402880e52717e27e012718506a3a0016','变动后五级部门','64',0,0,1,15,'技术部',20),('402880e52717e27e012718506a3a001c','402880f02479d73701247a10a78a0063','402880e52717e27e012718506a3a0017','变动后部门','64',0,1,1,16,'技术部',20),('402880e52717e27e012718506a3a001d','402880f02479d73701247a10a78a0063','402880e52717e27e012718506a3a0018','变动后职位','64',0,1,1,17,'项目经理',20),('402880e52717e27e012718506a3a001e','402880f02479d73701247a10a78a0063','402880e52717e27e012718506a3a0019','变动后经理','32',0,0,1,18,'tyhrm0026',15),('402880f0245c846f01245cb5017f008a','402880f0245c846f01245cb5017f0089','402880e42451a7fc01245248e7030001','工号','32',1,1,1,0,'ty0002',15),('402880f0245c846f01245cb5017f008c','402880f0245c846f01245cb5017f0089','402880e42451a7fc01245248e7030003','职位','64',1,1,1,4,'检测员',20),('402880f0245c846f01245cb5017f008d','402880f0245c846f01245cb5017f0089','402880e42451a7fc01245248e7030004','部门','64',1,1,1,2,'技术部',20),('402880f0245c846f01245cb5017f008e','402880f0245c846f01245cb5017f0089','402880e42451a7fc01245248e7030005','考评经理','32',1,1,1,6,'ty0001',12),('402880f0245c846f01245cb5017f008f','402880f0245c846f01245cb5017f0089','402880e42451a7fc0124524bec730006','起始日期','yyyy-MM-dd',1,1,1,7,'2008-10-23',12),('402880f0245c846f01245cb5017f0090','402880f0245c846f01245cb5017f0089','402880e42451a7fc0124524bec730007','终止日期','yyyy-MM-dd',1,1,1,8,'2009-10-22',12),('402880f0245c846f01245cb5017f0091','402880f0245c846f01245cb5017f0089','402880e42451a7fc0124524bec730008','备注','255',1,1,1,10,'请录入备注',25),('402880f0245c846f01245cb5017f0092','402880f0245c846f01245cb5017f0089','402880e42451a7fc0124524bec730009','考评结果','36',1,1,1,9,'A',NULL),('402880f0245c846f01245cb5017f0093','402880f0245c846f01245cb5017f0089','402880e42451a7fc0124524bec73000a','考评种类',NULL,1,1,1,5,'全年',NULL),('402880f0245c846f01245cc0a10500e5','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c870001','工号','32',0,1,1,0,'ty0001',12),('402880f0245c846f01245cc0a10500e6','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970002','A1','2',0,0,1,3,'0.00',12),('402880f0245c846f01245cc0a10500e7','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970003','A2','2',0,0,1,4,'0.00',12),('402880f0245c846f01245cc0a10500e8','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970004','A3','2',0,0,1,5,'0.00',12),('402880f0245c846f01245cc0a10500e9','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970005','A4','2',0,0,1,6,'0.00',12),('402880f0245c846f01245cc0a10500ea','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970006','A5','2',0,0,1,7,'0.00',12),('402880f0245c846f01245cc0a10500eb','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970007','A6','2',0,0,1,8,'0.00',12),('402880f0245c846f01245cc0a10500ec','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970008','A7','2',0,0,1,9,'0.00',12),('402880f0245c846f01245cc0a10500ed','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c970009','A8','2',0,0,1,10,'0.00',12),('402880f0245c846f01245cc0a10500ee','402880f0245c846f01245cc0a10500e4','402881e72458c350012458ca1c97000a','A9','2',0,0,1,11,'0.00',12),('402880f0245c846f01245cc0a10500ef','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc584000b','A10','2',0,0,1,12,'0.00',12),('402880f0245c846f01245cc0a10500f0','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc584000c','A11','2',0,0,1,13,'0.00',12),('402880f0245c846f01245cc0a10500f1','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc584000d','A12','2',0,0,1,14,'0.00',12),('402880f0245c846f01245cc0a10500f2','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc584000e','A13','2',0,0,1,15,'0.00',12),('402880f0245c846f01245cc0a10500f3','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc584000f','A14','2',0,0,1,16,'0.00',12),('402880f0245c846f01245cc0a10500f4','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc5840010','A15','2',0,0,1,17,'0.00',12),('402880f0245c846f01245cc0a10500f5','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc5840011','A16','2',0,0,1,18,'0.00',12),('402880f0245c846f01245cc0a10500f6','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc5840012','A17','2',0,0,1,19,'0.00',12),('402880f0245c846f01245cc0a10500f7','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc5840013','A18','2',0,0,1,20,'0.00',12),('402880f0245c846f01245cc0a10500f8','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cbc5840014','A19','2',0,0,1,21,'0.00',12),('402880f0245c846f01245cc0a10500f9','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee0015','A20','2',0,0,1,22,'0.00',12),('402880f0245c846f01245cc0a10500fa','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee0016','A21','2',0,0,1,23,'0.00',12),('402880f0245c846f01245cc0a10500fb','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee0017','A22','2',0,0,1,24,'0.00',12),('402880f0245c846f01245cc0a10500fc','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee0018','A23','2',0,0,1,25,'0.00',12),('402880f0245c846f01245cc0a10500fd','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee0019','A24','2',0,0,1,26,'0.00',12),('402880f0245c846f01245cc0a10500fe','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee001a','A25','2',0,0,1,27,'0.00',12),('402880f0245c846f01245cc0a10500ff','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee001b','A26','2',0,0,1,28,'0.00',12),('402880f0245c846f01245cc0a1050100','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee001c','A27','2',0,0,1,29,'0.00',12),('402880f0245c846f01245cc0a1050101','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee001d','A28','2',0,0,1,30,'0.00',12),('402880f0245c846f01245cc0a1050102','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cd6eee001e','A29','2',0,0,1,31,'0.00',12),('402880f0245c846f01245cc0a1050103','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee581001f','A30','2',0,0,1,32,'0.00',12),('402880f0245c846f01245cc0a1050104','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810020','A31','2',0,0,1,33,'0.00',12),('402880f0245c846f01245cc0a1050105','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810021','A32','2',0,0,1,34,'0.00',12),('402880f0245c846f01245cc0a1050106','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810022','A33','2',0,0,1,35,'0.00',12),('402880f0245c846f01245cc0a1050107','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810023','A34','2',0,0,1,36,'0.00',12),('402880f0245c846f01245cc0a1050108','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810024','A35','2',0,0,1,37,'0.00',12),('402880f0245c846f01245cc0a1050109','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810025','A36','2',0,0,1,38,'0.00',12),('402880f0245c846f01245cc0a105010a','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810026','A37','2',0,0,1,39,'0.00',12),('402880f0245c846f01245cc0a105010b','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810027','A38','2',0,0,1,40,'0.00',12),('402880f0245c846f01245cc0a105010c','402880f0245c846f01245cc0a10500e4','402881e72458c350012458cee5810028','A39','2',0,0,1,41,'0.00',12),('402880f0245c846f01245cc0a105010d','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a70029','A40','2',0,0,1,42,'0.00',12),('402880f0245c846f01245cc0a105010e','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002a','A41','2',0,0,1,43,'0.00',12),('402880f0245c846f01245cc0a105010f','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002b','A42','2',0,0,1,44,'0.00',12),('402880f0245c846f01245cc0a1050110','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002c','A43','2',0,0,1,45,'0.00',12),('402880f0245c846f01245cc0a1050111','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002d','A44','2',0,0,1,46,'0.00',12),('402880f0245c846f01245cc0a1050112','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002e','A45','2',0,0,1,47,'0.00',12),('402880f0245c846f01245cc0a1050113','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3a7002f','A46','2',0,0,1,48,'0.00',12),('402880f0245c846f01245cc0a1050114','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3b70030','A47','2',0,0,1,49,'0.00',12),('402880f0245c846f01245cc0a1050115','402880f0245c846f01245cc0a10500e4','402881e72458c350012458d0c3b70031','A48','2',0,0,1,50,'0.00',12),('402880f0245c846f01245cc6d9bb0151','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b40002','工号','32',1,1,1,0,'ty0002',12),('402880f0245c846f01245cc6d9bb0152','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b40003','银行帐号','32',1,1,1,3,'123-123123',20),('402880f0245c846f01245cc6d9bb0153','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b40004','薪资级别','64',0,1,1,4,'销售经理',12),('402880f0245c846f01245cc6d9bb0154','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b40005','薪资帐套','64',0,1,1,5,'正式员工帐套',16),('402880f0245c846f01245cc6d9bb0155','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b40009','银行开户行','64',0,0,1,6,'中国建设银行',16),('402880f0245c846f01245cc6d9bb0156','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2478b4000a','成本中心','32',0,0,1,7,'成本中心',12),('402880f0245c846f01245cc6d9bb0157','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf94000b','A1','2',0,0,1,8,'0.00',12),('402880f0245c846f01245cc6d9bb0158','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf94000c','A2','2',0,0,1,9,'0.00',12),('402880f0245c846f01245cc6d9bb0159','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf94000d','A3','2',0,0,1,10,'0.00',12),('402880f0245c846f01245cc6d9bb015a','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf94000e','A4','2',0,0,1,11,'0.00',12),('402880f0245c846f01245cc6d9bb015b','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf94000f','A5','2',0,0,1,12,'0.00',12),('402880f0245c846f01245cc6d9bb015c','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf940010','A6','2',0,0,1,13,'0.00',12),('402880f0245c846f01245cc6d9bb015d','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf940011','A7','2',0,0,1,14,'0.00',12),('402880f0245c846f01245cc6d9bb015e','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf940012','A8','2',0,0,1,15,'0.00',12),('402880f0245c846f01245cc6d9bb015f','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf940013','A9','2',0,0,1,16,'0.00',12),('402880f0245c846f01245cc6d9bb0160','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c26cf940014','A10','2',0,0,1,17,'0.00',12),('402880f0245c846f01245cc6d9bb0161','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a030015','A11','2',0,0,1,18,'0.00',12),('402880f0245c846f01245cc6d9bb0162','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a030016','A12','2',0,0,1,19,'0.00',12),('402880f0245c846f01245cc6d9bb0163','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a030017','A13','2',0,0,1,20,'0.00',12),('402880f0245c846f01245cc6d9bb0164','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a030018','A14','2',0,0,1,21,'0.00',12),('402880f0245c846f01245cc6d9bb0165','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a030019','A15','2',0,0,1,22,'0.00',12),('402880f0245c846f01245cc6d9bb0166','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a03001a','A16','2',0,0,1,23,'0.00',12),('402880f0245c846f01245cc6d9bb0167','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a03001b','A17','2',0,0,1,24,'0.00',12),('402880f0245c846f01245cc6d9bb0168','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a03001c','A18','2',0,0,1,25,'0.00',12),('402880f0245c846f01245cc6d9bb0169','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c290a03001d','A19','2',0,0,1,26,'0.00',12),('402880f0245c846f01245cc6d9bb016a','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a4199001e','A20','2',0,0,1,27,'0.00',12),('402880f0245c846f01245cc6d9bb016b','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a4199001f','A21','2',0,0,1,28,'0.00',12),('402880f0245c846f01245cc6d9bb016c','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90020','A22','2',0,0,1,29,'0.00',12),('402880f0245c846f01245cc6d9bb016d','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90021','A23','2',0,0,1,30,'0.00',12),('402880f0245c846f01245cc6d9bb016e','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90022','A24','2',0,0,1,31,'0.00',12),('402880f0245c846f01245cc6d9bb016f','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90023','A25','2',0,0,1,32,'0.00',12),('402880f0245c846f01245cc6d9bb0170','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90024','A26','2',0,0,1,33,'0.00',12),('402880f0245c846f01245cc6d9bb0171','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90025','A27','2',0,0,1,34,'0.00',12),('402880f0245c846f01245cc6d9bb0172','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90026','A28','2',0,0,1,35,'0.00',12),('402880f0245c846f01245cc6d9bb0173','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2a41a90027','A29','2',0,0,1,36,'0.00',12),('402880f0245c846f01245cc6d9bb0174','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b97070028','A30','2',0,0,1,37,'0.00',12),('402880f0245c846f01245cc6d9bb0175','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b97070029','A31','2',0,0,1,38,'0.00',12),('402880f0245c846f01245cc6d9bb0176','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002a','A32','2',0,0,1,39,'0.00',12),('402880f0245c846f01245cc6d9bb0177','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002b','A33','2',0,0,1,40,'0.00',12),('402880f0245c846f01245cc6d9bb0178','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002c','A34','2',0,0,1,41,'0.00',12),('402880f0245c846f01245cc6d9bb0179','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002d','A35','2',0,0,1,42,'0.00',12),('402880f0245c846f01245cc6d9bb017a','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002e','A36','2',0,0,1,43,'0.00',12),('402880f0245c846f01245cc6d9bb017b','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b9707002f','A37','2',0,0,1,44,'0.00',12),('402880f0245c846f01245cc6d9bb017c','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b97070030','A38','2',0,0,1,45,'0.00',12),('402880f0245c846f01245cc6d9bb017d','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2b97070031','A39','2',0,0,1,46,'0.00',12),('402880f0245c846f01245cc6d9bb017e','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870032','A40','2',0,0,1,47,'0.00',12),('402880f0245c846f01245cc6d9bb017f','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870033','A41','2',0,0,1,48,'0.00',12),('402880f0245c846f01245cc6d9bb0180','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870034','A42','2',0,0,1,49,'0.00',12),('402880f0245c846f01245cc6d9bb0181','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870035','A43','2',0,0,1,50,'0.00',12),('402880f0245c846f01245cc6d9bb0182','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870036','A44','2',0,0,1,51,'0.00',12),('402880f0245c846f01245cc6d9bb0183','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870037','A45','2',0,0,1,52,'0.00',12),('402880f0245c846f01245cc6d9bb0184','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870038','A46','2',0,0,1,53,'0.00',12),('402880f0245c846f01245cc6d9bb0185','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb5870039','A47','2',0,0,1,54,'0.00',12),('402880f0245c846f01245cc6d9bb0186','402880f0245c846f01245cc6d9bb0150','402880e5245c177201245c2cb587003a','A48','2',0,0,1,55,'0.00',12),('402880f0245c846f01245cc859e30188','402880f0245c846f01245cc859e30187','4028801b244675ef01244770564a0001','工号','32',0,1,1,0,'ty0001',12),('402880f0245c846f01245cc859e30189','402880f0245c846f01245cc859e30187','4028801b244675ef01244770564a0002','社保种类','32',0,1,1,3,'城保',12),('402880f0245c846f01245cc859e3018a','402880f0245c846f01245cc859e30187','4028801b244675ef0124477b61120003','生效年月','6',0,1,1,4,'200906',10),('402880f0245c846f01245cc859e3018b','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af7100008','养老保险号','32',0,0,1,5,'a123',16),('402880f0245c846f01245cc859e3018c','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af7100009','医疗保险号','32',0,0,1,6,'b456',16),('402880f0245c846f01245cc859e3018d','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af710000a','公积金号','32',0,0,1,7,'3101019770707****',16),('402880f0245c846f01245cc859e3018e','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af710000b','社保基数','2',0,0,1,8,'0.00',12),('402880f0245c846f01245cc859f3018f','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af710000c','公积金基数','2',0,0,1,9,'0.00',12),('402880f0245c846f01245cc859f30190','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af710000d','综合保险基数','2',0,0,1,10,'0.00',12),('402880f0245c846f01245cc859f30191','402880f0245c846f01245cc859e30187','4028801b244675ef0124478af710000e','备注','255',0,0,1,11,'请填写备注',25),('402880f0245c846f01245cca99f00193','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0001','工号','32',0,0,1,0,'ty0001',15),('402880f0245c846f01245cca99f00194','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0002','身份证号码','32',0,0,1,3,'37072519841013****',20),('402880f0245c846f01245cca99f00195','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0003','养老保险号','32',0,0,1,4,'a123',15),('402880f0245c846f01245cca99f00196','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0004','医疗保险号','32',0,0,1,5,'b456',15),('402880f0245c846f01245cca99f00197','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0005','公积金号','32',0,0,1,6,'c789',15),('402880f0245c846f01245cca99f00198','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0006','生效年月','6',0,1,1,7,'200906',12),('402880f0245c846f01245cca99f00199','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0007','社保基数','2',0,0,1,8,'0.00',12),('402880f0245c846f01245cca99f0019a','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0008','公积金基数','2',0,0,1,9,'0.00',12),('402880f0245c846f01245cca99f0019b','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f0009','综合保险基数','2',0,0,1,10,'0.00',12),('402880f0245c846f01245cca99f0019c','402880f0245c846f01245cca99f00192','4028801b244b6fa701244b7f317f000a','备注','255',0,0,1,11,'请输入备注',25),('402880f02479d737012479e26b7a0003','4028801b2450a1c2012451c962f80021','402880f02479d737012479e26b5b0001','姓名','30',0,0,1,1,'陈初发',12),('402880f02479d737012479e26b7a0004','4028801b2450a1c2012451c962f80021','402880f02479d737012479e26b7a0002','部门','30',0,0,1,2,'总裁办公室',20),('402880f02479d737012479f03dbf000a','402880f02479d737012479f03dbf0009','4028801a24527a310124528f8263000b','工号','32',1,1,1,0,'tyhrm0001',10),('402880f02479d737012479f03dbf000b','402880f02479d737012479f03dbf0009','402880f02476130a012479d0c589004a','姓名','32',0,0,1,1,'张三',10),('402880f02479d737012479f03dbf000c','402880f02479d737012479f03dbf0009','402880f02476130a012479d0c5a8004c','部门','32',0,0,1,2,'技术咨询部',16),('402880f02479d737012479f03dbf000f','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e000e','全勤(小时)','2',0,0,0,4,'160',10),('402880f02479d737012479f03dbf0010','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e000f','全勤(天)','2',0,1,1,5,'20',10),('402880f02479d737012479f03dbf0011','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0010','出勤(小时)','2',0,0,0,6,'160',10),('402880f02479d737012479f03dbf0012','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0011','出勤(天)','2',0,1,1,7,'20',10),('402880f02479d737012479f03dbf0013','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0012','缺勤(小时)','2',0,0,0,8,'8',10),('402880f02479d737012479f03dbf0014','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0013','缺勤(天)','2',0,1,1,9,'1',10),('402880f02479d737012479f03dbf0015','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0014','迟到(次)','2',0,0,1,10,'2',10),('402880f02479d737012479f03dbf0016','402880f02479d737012479f03dbf0009','4028801a24527a3101245297299e0015','早退(次)','2',0,0,1,11,'2',10),('402880f02479d737012479f03dbf0017','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a30016','旷工(小时)','2',0,0,0,12,'8',10),('402880f02479d737012479f03dbf0018','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a30017','旷工(天)','2',0,0,1,13,'1',10),('402880f02479d737012479f03dbf0019','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a30018','普通加班(小时)','2',0,0,1,38,'8',10),('402880f02479d737012479f03dbf001a','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a30019','周末加班(小时)','2',0,0,1,39,'3',10),('402880f02479d737012479f03dbf001b','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a3001a','节假日加班(小时)','2',0,0,1,40,'4',10),('402880f02479d737012479f03dbf001c','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a3001b','年假(小时)','2',0,0,0,14,'5',10),('402880f02479d737012479f03dbf001d','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a3001c','年假(天)','2',0,0,1,15,'40',10),('402880f02479d737012479f03dbf001e','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a3001d','调休(小时)','2',0,0,0,32,'30',10),('402880f02479d737012479f03dbf001f','402880f02479d737012479f03dbf0009','4028801a24527a310124529c75a3001e','调休(天)','2',0,0,1,33,'5',10),('402880f02479d737012479f03dbf0020','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e19001f','事假(小时)','2',0,0,0,16,'50',10),('402880f02479d737012479f03dbf0021','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190020','事假(天)','2',0,0,1,17,'6',10),('402880f02479d737012479f03dbf0022','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190021','病假(小时)','2',0,0,0,18,'4',10),('402880f02479d737012479f03dbf0023','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190022','病假(天)','2',0,0,1,19,'5',10),('402880f02479d737012479f03dbf0024','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190023','病假住院(小时)','2',0,0,0,20,'8',10),('402880f02479d737012479f03dbf0025','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190024','病假住院(天)','2',0,0,1,21,'1',10),('402880f02479d737012479f03dbf0026','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190025','带薪病假(小时)','2',0,0,0,22,'8',10),('402880f02479d737012479f03dbf0027','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190026','带薪病假(天)','2',0,0,1,23,'1',10),('402880f02479d737012479f03dbf0028','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190027','婚假(小时)','2',0,0,0,24,'8',10),('402880f02479d737012479f03dbf0029','402880f02479d737012479f03dbf0009','4028801a24527a31012452a08e190028','婚假(天)','2',0,0,1,25,'1',10),('402880f02479d737012479f03dbf002a','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e2450029','产假(小时)','2',0,0,0,26,'8',10),('402880f02479d737012479f03dbf002b','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002a','产假(天)','2',0,0,1,27,'1',10),('402880f02479d737012479f03dbf002c','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002b','出差(小时)','2',0,0,0,28,'16',10),('402880f02479d737012479f03dbf002d','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002c','出差(天)','2',0,0,1,29,'2',10),('402880f02479d737012479f03dbf002e','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002d','外出(小时)','2',0,0,0,30,'16',10),('402880f02479d737012479f03dbf002f','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002e','外出(天)','2',0,0,1,31,'2',10),('402880f02479d737012479f03dbf0030','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e245002f','其他请假(小时)','2',0,0,0,36,'4',10),('402880f02479d737012479f03dbf0031','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e2450030','其他请假(天)','2',0,0,1,37,'0.5',10),('402880f02479d737012479f03dbf0032','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e2450031','调休过期(小时)','2',0,0,0,34,'16',10),('402880f02479d737012479f03dbf0033','402880f02479d737012479f03dbf0009','4028801a24527a31012452a3e2450032','调休过期(天)','2',0,0,1,35,'2',10),('402880f02479d737012479f03dbf0034','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0033','自定义字段01','32',0,0,0,41,'自定义字段01',NULL),('402880f02479d737012479f03dbf0035','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0034','自定义字段02','32',0,0,0,42,'自定义字段02',NULL),('402880f02479d737012479f03dbf0036','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0035','自定义字段03','32',0,0,0,43,'自定义字段03',NULL),('402880f02479d737012479f03dbf0037','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0036','自定义字段04','32',0,0,0,44,'自定义字段04',NULL),('402880f02479d737012479f03dbf0038','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0037','自定义字段05','32',0,0,0,45,'自定义字段05',NULL),('402880f02479d737012479f03dbf0039','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0038','自定义字段06','32',0,0,0,46,'自定义字段06',NULL),('402880f02479d737012479f03dbf003a','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b0039','自定义字段07','32',0,0,0,47,'自定义字段07',NULL),('402880f02479d737012479f03dbf003b','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b003a','自定义字段08','32',0,0,0,48,'自定义字段08',NULL),('402880f02479d737012479f03dbf003c','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b003b','自定义字段09','32',0,0,0,49,'自定义字段09',NULL),('402880f02479d737012479f03dbf003d','402880f02479d737012479f03dbf0009','4028801a24527a31012452a7182b003c','自定义字段10','32',0,0,0,50,'自定义字段10',NULL),('402880f02479d737012479f03dbf003e','402880f02479d737012479f03dbf0009','4028801a24527a31012452a92551003d','自定义字段11','32',0,0,0,51,'自定义字段11',NULL),('402880f02479d737012479f03dbf003f','402880f02479d737012479f03dbf0009','4028801a24527a31012452a92551003e','自定义字段12','32',0,0,0,52,'自定义字段12',NULL),('402880f02479d737012479f03dbf0040','402880f02479d737012479f03dbf0009','4028801a24527a31012452a92551003f','自定义字段13','32',0,0,0,53,'自定义字段13',NULL),('402880f02479d737012479f03dbf0041','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600040','自定义字段14','32',0,0,0,54,'自定义字段14',NULL),('402880f02479d737012479f03dbf0042','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600041','自定义字段15','32',0,0,0,55,'自定义字段15',NULL),('402880f02479d737012479f03dbf0043','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600042','自定义字段16','32',0,0,0,56,'自定义字段16',NULL),('402880f02479d737012479f03dbf0044','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600043','自定义字段17','32',0,0,0,57,'自定义字段17',NULL),('402880f02479d737012479f03dbf0045','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600044','自定义字段18','32',0,0,0,58,'自定义字段18',NULL),('402880f02479d737012479f03dbf0046','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600045','自定义字段19','32',0,0,0,59,'自定义字段19',NULL),('402880f02479d737012479f03dbf0047','402880f02479d737012479f03dbf0009','4028801a24527a31012452a925600046','自定义字段20','32',0,0,0,60,'自定义字段20',NULL),('402880f02479d737012479f03dbf0048','402880f02479d737012479f03dbf0009','4028801a24527a31012452aa571a0047','自定义字段21','32',0,0,0,61,'自定义字段21',NULL),('402880f02479d737012479f03dbf0049','402880f02479d737012479f03dbf0009','4028801a24527a31012452aa571a0048','自定义字段22','32',0,0,0,62,'自定义字段22',NULL),('402880f02479d737012479f03dbf004a','402880f02479d737012479f03dbf0009','4028801a24527a31012452aa571a0049','自定义字段23','32',0,0,0,63,'自定义字段23',NULL),('402880f02479d737012479f03dbf004b','402880f02479d737012479f03dbf0009','4028801a24527a31012452aa571a004a','自定义字段24','32',0,0,0,64,'自定义字段24',NULL),('402880f02479d737012479f03dbf004c','402880f02479d737012479f03dbf0009','4028801a2455ffb9012457f4074b0017','备注','128',0,0,1,65,'备注',26),('402880f02479d737012479fce54f0056','402880f02479d737012479fce54f0055','402880e4245175d001245182bc4c0001','工号','32',1,1,1,0,'ty0001',15),('402880f02479d737012479fce54f0057','402880f02479d737012479fce54f0055','402880f02479d737012479f7cc03004d','姓名','32',0,0,1,1,'张三',12),('402880f02479d737012479fce54f0058','402880f02479d737012479fce54f0055','402880e4245175d001245182bc4c0004','部门','64',1,0,1,2,'人力资源部',20),('402880f02479d737012479fce54f005a','402880f02479d737012479fce54f0055','402880e4245175d001245182bc4c0003','职位','64',1,0,1,4,'经理',NULL),('402880f02479d737012479fce54f005b','402880f02479d737012479fce54f0055','402880e4245175d0012451869f2c0007','奖惩日期','yyyy-MM-dd',0,1,1,5,'2009-10-20',12),('402880f02479d737012479fce54f005c','402880f02479d737012479fce54f0055','402880e4245175d001245188831e0008','奖惩形式','36',0,1,1,7,'现金',NULL),('402880f02479d737012479fce54f005d','402880f02479d737012479fce54f0055','402880e4245175d0012451869f2c0005','奖惩种类',NULL,0,1,1,6,'奖励',NULL),('402880f02479d737012479fce54f005e','402880f02479d737012479fce54f0055','402880e4245175d0012451869f2c0006','备注','255',0,1,1,8,'在此录入备注',25),('402880f02479d73701247a10a78a0064','402880f02479d73701247a10a78a0063','402880e4245b8e8d01245b90b4360001','工号','32',1,1,1,0,'ty0002',15),('402880f02479d73701247a10a78a0065','402880f02479d73701247a10a78a0063','402880f02479d73701247a0ee548005f','姓名','32',0,0,1,1,'张三',12),('402880f02479d73701247a10a78a0066','402880f02479d73701247a10a78a0063','402880e4245b8e8d01245b90b4360006','变动日期','yyyy-MM-dd',0,0,1,2,'2995-10-22',12),('402880f02479d73701247a10a78a0067','402880f02479d73701247a10a78a0063','402880e4245b8e8d01245b90b4360007','变动种类',NULL,0,0,1,3,'平调',NULL),('402880f02479d73701247a10a78a006c','402880f02479d73701247a10a78a0063','402880e4245b8e8d01245b90b4360009','变动原因','128',0,0,1,19,'临时调动',20),('402880f02479d73701247a10a78a006d','402880f02479d73701247a10a78a0063','402880e4245b8e8d01245b90b4360008','备注','255',0,0,1,20,'请录入备注',25),('402880f02479d73701247a4008df0070','4028801b2450a1c2012451dbf1b3004f','402880f02479d73701247a4008df006e','姓名',NULL,0,0,1,1,'陈初发',12),('402880f02479d73701247a4008df0071','4028801b2450a1c2012451dbf1b3004f','402880f02479d73701247a4008df006f','部门',NULL,0,0,1,2,'总裁办公室',20),('402880f02479d73701247a4912f50078','402880f0245c846f01245cc0a10500e4','402880f02479d73701247a4912f50076','姓名','64',0,0,1,1,'陈初发',12),('402880f02479d73701247a4912f50079','402880f0245c846f01245cc0a10500e4','402880f02479d73701247a4912f50077','部门','64',0,0,1,2,'总裁办公室',20),('402880f02479d73701247a4b9f5d007f','402880f0245c846f01245cb5017f0089','402880f02479d73701247a4b9f5d007e','姓名','32',0,0,1,1,'张三',20),('402880f02479d73701247a4f9eec0084','402880f0245c846f01245cc6d9bb0150','402880f02479d73701247a4f9eec0082','姓名','64',0,0,1,1,'陈初发',12),('402880f02479d73701247a4f9eec0085','402880f0245c846f01245cc6d9bb0150','402880f02479d73701247a4f9eec0083','部门','64',0,0,1,2,'总裁办公室',16),('402880f02479d73701247a627fcf0094','402880f0245c846f01245cc859e30187','402880f02479d73701247a627fbf0092','姓名','64',0,0,1,1,'陈初发',12),('402880f02479d73701247a627fcf0095','402880f0245c846f01245cc859e30187','402880f02479d73701247a627fbf0093','部门','64',0,0,1,2,'总裁办公室',20),('402880f02479d73701247b988161009d','402880f0245c846f01245cca99f00192','402880f02479d73701247b988161009b','姓名','64',0,0,1,1,'陈初发',12),('402880f02479d73701247b988161009e','402880f0245c846f01245cca99f00192','402880f02479d73701247b988161009c','部门','64',0,0,1,2,'总裁办公室',20),('402880f0248a706001248a8b93e90002','402880f0248a706001248a8b93e90001','35374455-2b93-4693-9d0f-99c8ac92c1e3','工号','32',0,1,1,0,'ty0001',10),('402880f0248a706001248a8b93e90003','402880f0248a706001248a8b93e90001','abe42933-ffe4-47d6-82be-4552fba8bb4d','姓名','64',0,1,1,1,'人力资源管理系统',10),('402880f0248a706001248a8b93e90004','402880f0248a706001248a8b93e90001','0745fe68-07ae-4ab3-b64e-ab2a650a77ee','英文名','80',0,0,0,2,'TengSource',20),('402880f0248a706001248a8b93e90005','402880f0248a706001248a8b93e90001','180542d6-dcec-4da6-a01d-1ef2920481f8','生日','yyyy-MM-dd',0,1,1,3,'1985-8-11',12),('402880f0248a706001248a8b93e90006','402880f0248a706001248a8b93e90001','db0daccc-e1fc-4b08-9c1f-ce72f79444b3','性别',NULL,0,1,1,4,'男',6),('402880f0248a706001248a8b93e90007','402880f0248a706001248a8b93e90001','28c36150-9aad-49f5-8f8d-e45f356fc6ac','婚否',NULL,0,0,1,5,'未婚',6),('402880f0248a706001248a8b93e90009','402880f0248a706001248a8b93e90001','11e7794e-a1cb-4570-8b05-d96fe3c1321f','部门','64',0,1,1,11,'人力资源部',20),('402880f0248a706001248a8b93f9000d','402880f0248a706001248a8b93e90001','8581d13c-103b-47d0-bc6f-9f5a7b805c5b','工作地区','64',0,1,1,15,'上海',20),('402880f0248a706001248a8b93f9000e','402880f0248a706001248a8b93e90001','9732b770-3885-49a3-a6ac-589b8b0b2682','经理工号','32',0,0,1,16,'ty0001',10),('402880f0248a706001248a8b93f9000f','402880f0248a706001248a8b93e90001','c97e76ff-8573-404f-8d4a-a2881146642c','用工形式','32',0,1,1,17,'正式员工',20),('402880f0248a706001248a8b93f90010','402880f0248a706001248a8b93e90001','75f09d00-c9e8-43ac-8644-cd891792ee8c','参加工作日期','yyyy-MM-dd',0,0,1,18,'2008-06-17',12),('402880f0248a706001248a8b93f90011','402880f0248a706001248a8b93e90001','c4e20c8a-7f08-4578-9bb8-a9cd15a3b87a','入职日期','yyyy-MM-dd',0,1,1,19,'2008-06-17',12),('402880f0248a706001248a8b93f90012','402880f0248a706001248a8b93e90001','37a8fb6a-a514-4982-8f65-2bfaf60cd53f','转正日期','yyyy-MM-dd',0,0,1,20,'2008-11-24',12),('402880f0248a706001248a8b93f90013','402880f0248a706001248a8b93e90001','43dd9b82-bb8f-4377-b211-83d6304f44fd','证件种类',NULL,0,1,1,21,'身份证',10),('402880f0248a706001248a8b93f90014','402880f0248a706001248a8b93e90001','8c56e714-0dd5-4ca4-ac4a-18b04baf14fb','证件号码','32',0,1,1,22,'36213119811016****',20),('402880f0248a706001248a8b93f90015','402880f0248a706001248a8b93e90001','040edf7d-e359-4824-a58e-59f57ddb7146','血型',NULL,0,0,1,23,'A',6),('402880f0248a706001248a8b93f90016','402880f0248a706001248a8b93e90001','e4c74e9e-0019-4461-932e-d1fab58dd36b','政治面貌','16',0,0,1,24,'群众',10),('402880f0248a706001248a8b93f90017','402880f0248a706001248a8b93e90001','61590553-a079-43c1-94bd-27a179023d19','学历','16',0,0,1,25,'本科',10),('402880f0248a706001248a8b93f90018','402880f0248a706001248a8b93e90001','b5a92876-069f-4444-8536-3bacd37f908f','毕业院校','64',0,0,1,26,'南京财经大学',20),('402880f0248a706001248a8b93f90019','402880f0248a706001248a8b93e90001','35cdae81-d19a-41f7-82ba-e415b2626ad1','专业','64',0,0,1,27,'国际经济与贸易',20),('402880f0248a706001248a8b93f9001a','402880f0248a706001248a8b93e90001','04725c37-90df-46fd-86b2-79f389faba62','籍贯','16',0,0,1,28,'湖北仙桃',10),('402880f0248a706001248a8b93f9001b','402880f0248a706001248a8b93e90001','10791bef-68ca-4c6a-87e0-a4734344dd9b','民族','16',0,0,1,29,'汉',6),('402880f0248a706001248a8b93f9001c','402880f0248a706001248a8b93e90001','5d5d45d5-bf6e-4ce8-870d-8d66eb1bf41e','户口所在地','128',0,0,1,30,'上海市浦东新区东方路1361号海富大厦18C',26),('402880f0248a706001248a8b93f9001d','402880f0248a706001248a8b93e90001','1d7ef3db-182a-4410-b838-3568f4e4b21b','档案所在地','128',0,0,1,31,'上海市浦东新区东方路1361号海富大厦18C',26),('402880f0248a706001248a8b93f9001e','402880f0248a706001248a8b93e90001','828a6b97-6fef-4eb1-a38c-3ab64e46987c','社保种类','64',0,0,1,32,'上海四金',12),('402880f0248a706001248a8b93f9001f','402880f0248a706001248a8b93e90001','c47ff799-5341-4a65-8e5f-4d0253f8a632','邮箱','64',0,0,1,33,'sales@hr.com',30),('402880f0248a706001248a8b93f90020','402880f0248a706001248a8b93e90001','4a0e3c17-c153-4889-9bd5-f9081cc8e1a3','MSN/QQ','64',0,0,1,34,'tengsource@hotmail.com',30),('402880f0248a706001248a8b93f90021','402880f0248a706001248a8b93e90001','58a74c1b-0864-44d9-8777-740db5c1f3c5','工作电话','32',0,0,1,35,'021-50705175',16),('402880f0248a706001248a8b93f90022','402880f0248a706001248a8b93e90001','04b262b1-77d9-4904-b1ed-8863fad691ba','手机','32',0,0,1,36,'1510920****',16),('402880f0248a706001248a8b93f90023','402880f0248a706001248a8b93e90001','3281470c-dc7c-495a-b0d0-606571e7bc4e','家庭电话','32',0,0,1,37,'021-50705175',16),('402880f0248a706001248a8b93f90024','402880f0248a706001248a8b93e90001','e2469ce5-8854-4bfc-8377-4d60eba1fabc','当前住址','128',0,0,1,38,'上海市浦东新区东方路1361号海富大厦',26),('402880f0248a706001248a8b93f90025','402880f0248a706001248a8b93e90001','d856c9d4-f347-422a-8dd1-d45d7464e5c3','当前住址邮编','16',0,0,1,39,'200127',10),('402880f0248a706001248a8b93f90026','402880f0248a706001248a8b93e90001','6665c77e-be4f-47fb-abb0-bd457bb97da5','家庭地址','128',0,0,1,40,'上海市浦东新区东方路1361号海富大厦',26),('402880f0248a706001248a8b93f90027','402880f0248a706001248a8b93e90001','50d753ab-d0bb-4053-9bf0-f74a28bc9e41','家庭地址邮编','16',0,0,1,41,'200127',10),('402880f0248a706001248a8b93f90028','402880f0248a706001248a8b93e90001','f184db88-06b0-437e-87af-75d4d423be1a','紧急联系人','64',0,0,1,42,'人力资源管理系统',10),('402880f0248a706001248a8b93f90029','402880f0248a706001248a8b93e90001','af75f95c-aec1-40d4-a9f2-25347ae38b1e','紧急联系方式','128',0,0,1,43,'50205175',20),('402880f0248a706001248a8b93f9002a','402880f0248a706001248a8b93e90001','8e009d17-d517-4034-95d4-8e30a913dd3d','离职日期','yyyy-MM-dd',0,0,1,64,'2009-12-31',12),('402880f0248a706001248a8b93f9002b','402880f0248a706001248a8b93e90001','4a9cfb95-227f-4227-a9f9-f87c086edc0a','考勤方式',NULL,0,0,1,44,'默认班次刷卡',12),('402880f0248a706001248a8b93f9002c','402880f0248a706001248a8b93e90001','402880f0248a422001248a60d9400001','考勤卡号','32',0,0,1,45,'10000122',12),('402880f0248a706001248a8b93f9002d','402880f0248a706001248a8b93e90001','7e3dbcbf-cf7d-4d0a-bd2e-f78168f330b9','状态',NULL,0,1,1,46,'在职',10),('402880f0248a706001248a8b93f9002e','402880f0248a706001248a8b93e90001','c92cf36a-175f-439c-a7bd-810d147889dc','备注','255',0,0,1,47,'请录入备注',20),('402880f0248a706001248a8b93f9002f','402880f0248a706001248a8b93e90001','25d9a313-cbd3-4edb-8c7c-9c6cfced139f','自定义字段01','255',0,0,1,48,'自定义字段01',15),('402880f0248a706001248a8b93f90030','402880f0248a706001248a8b93e90001','0a8f2773-a9e9-402a-ba9d-a28570135fa1','自定义字段02','255',0,0,1,49,'自定义字段02',15),('402880f0248a706001248a8b93f90031','402880f0248a706001248a8b93e90001','1a633a56-e554-48c3-9ebe-523cce87124f','自定义字段03','255',0,0,1,50,'自定义字段03',15),('402880f0248a706001248a8b93f90032','402880f0248a706001248a8b93e90001','6f2bc50c-c013-409d-adee-fbea55dbf608','自定义字段04','255',0,0,1,51,'自定义字段04',15),('402880f0248a706001248a8b93f90033','402880f0248a706001248a8b93e90001','2cb4a408-ac15-41a0-b5d5-a66cf03aaa7f','自定义字段05','255',0,0,1,52,'自定义字段05',15),('402880f0248a706001248a8b93f90034','402880f0248a706001248a8b93e90001','d05cb9cf-d91e-4610-b13b-ae9796bfcb8d','自定义字段06','255',0,0,1,53,'自定义字段06',15),('402880f0248a706001248a8b93f90035','402880f0248a706001248a8b93e90001','e3c89853-6be6-4140-acbe-a6f50c0c72be','自定义字段07','255',0,0,1,54,'自定义字段07',15),('402880f0248a706001248a8b93f90036','402880f0248a706001248a8b93e90001','6f0a9a25-badc-4dba-a650-00933612e9a6','自定义字段08','255',0,0,1,55,'自定义字段08',15),('402880f0248a706001248a8b93f90037','402880f0248a706001248a8b93e90001','0eb12aa5-116f-4e0a-a655-2594a5fa96aa','自定义字段09','255',0,0,1,56,'自定义字段09',15),('402880f0248a706001248a8b93f90038','402880f0248a706001248a8b93e90001','bf48cc8c-9fd3-46e6-8bcc-9c4ecfa19ce2','自定义字段10','255',0,0,1,57,'自定义字段10',15),('402880f0248a706001248a8b93f90039','402880f0248a706001248a8b93e90001','239dcd92-7fd0-438a-8d0c-a364ba48ef81','自定义字段11','255',0,0,1,58,'自定义字段11',15),('402880f0248a706001248a8b93f9003a','402880f0248a706001248a8b93e90001','57d4910e-1271-4fa0-b366-f0445f692acd','自定义字段12','255',0,0,1,59,'自定义字段12',15),('402880f0248a706001248a8b9408003b','402880f0248a706001248a8b93e90001','4f266706-c205-41ca-8c18-9ec479d79a7b','自定义字段13','255',0,0,1,60,'自定义字段13',15),('402880f0248a706001248a8b9408003c','402880f0248a706001248a8b93e90001','c77c4b95-48b3-4bf0-845a-d8f62a118bc7','自定义字段14','255',0,0,1,61,'自定义字段14',15),('402880f0248a706001248a8b9408003d','402880f0248a706001248a8b93e90001','267297ef-83ae-47fc-9aa0-6c6187ae8484','自定义字段15','255',0,0,1,62,'自定义字段15',15),('402880f0248a706001248a8b9408003e','402880f0248a706001248a8b93e90001','e449e659-5893-4065-b235-b55fb2d42d2a','自定义字段16','255',0,0,1,63,'自定义字段16',15),('402880f0248a706001248adf46da0043','402880f0248a706001248a8b93e90001','402880f0248a706001248adf46da003f','离职种类',NULL,0,0,1,65,'辞职',12),('402880f0248a706001248adf46da0044','402880f0248a706001248a8b93e90001','402880f0248a706001248adf46da0040','离职审批人工号',NULL,0,0,1,66,'ty0001',10),('402880f0248a706001248adf46da0045','402880f0248a706001248a8b93e90001','402880f0248a706001248adf46da0041','离职原因',NULL,0,0,1,67,'个人原因',15),('402880f0248a706001248adf46da0046','402880f0248a706001248a8b93e90001','402880f0248a706001248adf46da0042','离职备注',NULL,0,0,1,68,'如果状态为离职，才需填写离职信息',30),('402880f0248a706001248ae587ae004e','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae0047','合同起始日期','yyyy-MM-dd',0,0,1,69,'2008-06-17',12),('402880f0248a706001248ae587ae004f','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae0048','合同结束日期','yyyy-MM-dd',0,0,1,70,'2009-06-16',12),('402880f0248a706001248ae587ae0050','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae0049','合同编号',NULL,0,0,1,71,'TYC01001',12),('402880f0248a706001248ae587ae0051','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae004a','合同种类',NULL,0,0,1,72,'正式合同',12),('402880f0248a706001248ae587ae0052','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae004b','合同状态',NULL,0,0,1,73,'有效',12),('402880f0248a706001248ae587ae0053','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae004c','合同期限',NULL,0,0,1,74,'有限期',12),('402880f0248a706001248ae587ae0054','402880f0248a706001248a8b93e90001','402880f0248a706001248ae587ae004d','合同备注',NULL,0,0,1,75,'如果有合同数据，才需填写相关信息',30),('402880f0248afef801248b0d3db20003','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060002','工号','32',0,1,1,0,'ty0002',10),('402880f0248afef801248b0d3db20004','402880f0248afef801248b0d3db20002','402880f02479d73701247a4fa41c008e','姓名','32',0,0,1,1,'张三',12),('402880f0248afef801248b0d3db20005','402880f0248afef801248b0d3db20002','402880f02479d73701247a4fa41c008f','部门','32',0,0,1,2,'技术咨询部',15),('402880f0248afef801248b0d3db20006','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060007','合同起始日期','yyyy-MM-dd',0,1,1,3,'2009-11-23',12),('402880f0248afef801248b0d3db20007','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060008','合同结束日期','yyyy-MM-dd',0,0,1,4,'2010-11-22',12),('402880f0248afef801248b0d3db20008','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060003','合同编号','36',0,0,1,5,'TY2009001',12),('402880f0248afef801248b0d3db20009','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060006','合同种类','64',0,1,1,6,'正式合同',12),('402880f0248afef801248b0d3db2000a','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060005','合同状态',NULL,0,1,1,7,'有效',12),('402880f0248afef801248b0d3db2000b','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060004','合同期限',NULL,0,1,1,8,'有期限',12),('402880f0248afef801248b0d3db2000c','402880f0248afef801248b0d3db20002','402880e4245bc90301245bd48e060009','备注','255',0,0,1,9,'请录入备注',26),('402881e524aae9260124ab0d0d6a0008','402880f02479d737012479f03dbf0009','402881e524aae9260124ab0d0d5a0007','年月','6',0,0,0,3,'200910',10),('402881e524aae9260124ab478dda0012','402881e524aae9260124ab478dda0011','4028801a24527a310124528f8263000b','工号','32',1,1,1,0,'tyhrm0001',10),('402881e524aae9260124ab478dda0013','402881e524aae9260124ab478dda0011','402880f02476130a012479d0c589004a','姓名','32',0,0,1,1,'张三',10),('402881e524aae9260124ab478dda0014','402881e524aae9260124ab478dda0011','402880f02476130a012479d0c5a8004c','部门','32',0,0,1,2,'技术咨询部',16),('402881e524aae9260124ab478dda0015','402881e524aae9260124ab478dda0011','402881e524aae9260124ab0d0d5a0007','年月','6',0,0,0,3,'200910',10),('402881e524aae9260124ab478dda0016','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e000e','全勤(小时)','2',0,0,1,4,'160',10),('402881e524aae9260124ab478dda0017','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e000f','全勤(天)','2',0,0,0,5,'20',10),('402881e524aae9260124ab478dda0018','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0010','出勤(小时)','2',0,0,1,6,'160',10),('402881e524aae9260124ab478dda0019','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0011','出勤(天)','2',0,0,0,7,'20',10),('402881e524aae9260124ab478dda001a','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0012','缺勤(小时)','2',0,0,1,8,'8',10),('402881e524aae9260124ab478dda001b','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0013','缺勤(天)','2',0,0,0,9,'1',10),('402881e524aae9260124ab478dda001c','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0014','迟到(次)','2',0,0,1,10,'2',10),('402881e524aae9260124ab478dda001d','402881e524aae9260124ab478dda0011','4028801a24527a3101245297299e0015','早退(次)','2',0,0,1,11,'2',10),('402881e524aae9260124ab478dda001e','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a30016','旷工(小时)','2',0,0,1,12,'8',10),('402881e524aae9260124ab478dda001f','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a30017','旷工(天)','2',0,0,0,13,'1',10),('402881e524aae9260124ab478dda0020','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a3001b','年假(小时)','2',0,0,1,14,'5',10),('402881e524aae9260124ab478dda0021','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a3001c','年假(天)','2',0,0,0,15,'40',10),('402881e524aae9260124ab478dda0022','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e19001f','事假(小时)','2',0,0,1,16,'50',10),('402881e524aae9260124ab478dda0023','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190020','事假(天)','2',0,0,0,17,'6',10),('402881e524aae9260124ab478dda0024','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190021','病假(小时)','2',0,0,1,18,'4',10),('402881e524aae9260124ab478dda0025','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190022','病假(天)','2',0,0,0,19,'5',10),('402881e524aae9260124ab478dda0026','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190023','病假住院(小时)','2',0,0,1,20,'8',10),('402881e524aae9260124ab478dda0027','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190024','病假住院(天)','2',0,0,0,21,'1',10),('402881e524aae9260124ab478dda0028','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190025','带薪病假(小时)','2',0,0,1,22,'8',10),('402881e524aae9260124ab478dda0029','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190026','带薪病假(天)','2',0,0,0,23,'1',10),('402881e524aae9260124ab478dda002a','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190027','婚假(小时)','2',0,0,1,24,'8',10),('402881e524aae9260124ab478dda002b','402881e524aae9260124ab478dda0011','4028801a24527a31012452a08e190028','婚假(天)','2',0,0,0,25,'1',10),('402881e524aae9260124ab478dda002c','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e2450029','产假(小时)','2',0,0,1,26,'8',10),('402881e524aae9260124ab478dda002d','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002a','产假(天)','2',0,0,0,27,'1',10),('402881e524aae9260124ab478dda002e','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002b','出差(小时)','2',0,0,1,28,'16',10),('402881e524aae9260124ab478dda002f','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002c','出差(天)','2',0,0,0,29,'2',10),('402881e524aae9260124ab478dda0030','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002d','外出(小时)','2',0,0,1,30,'16',10),('402881e524aae9260124ab478dda0031','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002e','外出(天)','2',0,0,0,31,'2',10),('402881e524aae9260124ab478dda0032','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a3001d','调休(小时)','2',0,0,1,32,'30',10),('402881e524aae9260124ab478dda0033','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a3001e','调休(天)','2',0,0,0,33,'5',10),('402881e524aae9260124ab478dda0034','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e2450031','调休过期(小时)','2',0,0,1,34,'16',10),('402881e524aae9260124ab478dda0035','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e2450032','调休过期(天)','2',0,0,0,35,'2',10),('402881e524aae9260124ab478dda0036','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e245002f','其他请假(小时)','2',0,0,1,36,'4',10),('402881e524aae9260124ab478dda0037','402881e524aae9260124ab478dda0011','4028801a24527a31012452a3e2450030','其他请假(天)','2',0,0,0,37,'0.5',10),('402881e524aae9260124ab478dda0038','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a30018','普通加班(小时)','2',0,0,1,38,'8',10),('402881e524aae9260124ab478dda0039','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a30019','周末加班(小时)','2',0,0,1,39,'3',10),('402881e524aae9260124ab478dda003a','402881e524aae9260124ab478dda0011','4028801a24527a310124529c75a3001a','节假日加班(小时)','2',0,0,1,40,'4',10),('402881e524aae9260124ab478dda003b','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0033','自定义字段01','32',0,0,0,41,'自定义字段01',NULL),('402881e524aae9260124ab478dda003c','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0034','自定义字段02','32',0,0,0,42,'自定义字段02',NULL),('402881e524aae9260124ab478dda003d','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0035','自定义字段03','32',0,0,0,43,'自定义字段03',NULL),('402881e524aae9260124ab478e19003e','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0036','自定义字段04','32',0,0,0,44,'自定义字段04',NULL),('402881e524aae9260124ab478e19003f','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0037','自定义字段05','32',0,0,0,45,'自定义字段05',NULL),('402881e524aae9260124ab478e190040','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0038','自定义字段06','32',0,0,0,46,'自定义字段06',NULL),('402881e524aae9260124ab478e190041','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b0039','自定义字段07','32',0,0,0,47,'自定义字段07',NULL),('402881e524aae9260124ab478e190042','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b003a','自定义字段08','32',0,0,0,48,'自定义字段08',NULL),('402881e524aae9260124ab478e190043','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b003b','自定义字段09','32',0,0,0,49,'自定义字段09',NULL),('402881e524aae9260124ab478e190044','402881e524aae9260124ab478dda0011','4028801a24527a31012452a7182b003c','自定义字段10','32',0,0,0,50,'自定义字段10',NULL),('402881e524aae9260124ab478e190045','402881e524aae9260124ab478dda0011','4028801a24527a31012452a92551003d','自定义字段11','32',0,0,0,51,'自定义字段11',NULL),('402881e524aae9260124ab478e190046','402881e524aae9260124ab478dda0011','4028801a24527a31012452a92551003e','自定义字段12','32',0,0,0,52,'自定义字段12',NULL),('402881e524aae9260124ab478e190047','402881e524aae9260124ab478dda0011','4028801a24527a31012452a92551003f','自定义字段13','32',0,0,0,53,'自定义字段13',NULL),('402881e524aae9260124ab478e190048','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600040','自定义字段14','32',0,0,0,54,'自定义字段14',NULL),('402881e524aae9260124ab478e190049','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600041','自定义字段15','32',0,0,0,55,'自定义字段15',NULL),('402881e524aae9260124ab478e19004a','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600042','自定义字段16','32',0,0,0,56,'自定义字段16',NULL),('402881e524aae9260124ab478e19004b','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600043','自定义字段17','32',0,0,0,57,'自定义字段17',NULL),('402881e524aae9260124ab478e19004c','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600044','自定义字段18','32',0,0,0,58,'自定义字段18',NULL),('402881e524aae9260124ab478e19004d','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600045','自定义字段19','32',0,0,0,59,'自定义字段19',NULL),('402881e524aae9260124ab478e19004e','402881e524aae9260124ab478dda0011','4028801a24527a31012452a925600046','自定义字段20','32',0,0,0,60,'自定义字段20',NULL),('402881e524aae9260124ab478e19004f','402881e524aae9260124ab478dda0011','4028801a24527a31012452aa571a0047','自定义字段21','32',0,0,0,61,'自定义字段21',NULL),('402881e524aae9260124ab478e190050','402881e524aae9260124ab478dda0011','4028801a24527a31012452aa571a0048','自定义字段22','32',0,0,0,62,'自定义字段22',NULL),('402881e524aae9260124ab478e190051','402881e524aae9260124ab478dda0011','4028801a24527a31012452aa571a0049','自定义字段23','32',0,0,0,63,'自定义字段23',NULL),('402881e524aae9260124ab478e190052','402881e524aae9260124ab478dda0011','4028801a24527a31012452aa571a004a','自定义字段24','32',0,0,0,64,'自定义字段24',NULL),('402881e524aae9260124ab478e190053','402881e524aae9260124ab478dda0011','4028801a2455ffb9012457f4074b0017','备注','128',0,0,1,65,'备注',26);
/*!40000 ALTER TABLE `inmatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inmatchbasic`
--

DROP TABLE IF EXISTS `inmatchbasic`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `inmatchbasic` (
  `imb_id` varchar(36) NOT NULL,
  `imb_io_id` varchar(36) NOT NULL,
  `imb_field_name` varchar(64) NOT NULL,
  `imb_field_desc` varchar(64) NOT NULL,
  `imb_data_type` varchar(32) NOT NULL,
  `imb_format` varchar(32) default NULL,
  `imb_detect_error` int(10) unsigned NOT NULL default '15',
  `imb_required` int(10) unsigned NOT NULL default '0',
  `imb_sort_id` int(10) unsigned NOT NULL default '0',
  `imb_sample` varchar(255) NOT NULL,
  PRIMARY KEY  (`imb_id`),
  KEY `FK_imb_io_id` (`imb_io_id`),
  CONSTRAINT `FK_imb_io_id` FOREIGN KEY (`imb_io_id`) REFERENCES `iodef` (`io_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `inmatchbasic`
--

LOCK TABLES `inmatchbasic` WRITE;
/*!40000 ALTER TABLE `inmatchbasic` DISABLE KEYS */;
INSERT INTO `inmatchbasic` VALUES ('040edf7d-e359-4824-a58e-59f57ddb7146','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBlood','血型','empBlood',NULL,0,0,23,'A'),('04725c37-90df-46fd-86b2-79f389faba62','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCityNo','籍贯','string','16',0,0,28,'湖北仙桃'),('04b262b1-77d9-4904-b1ed-8863fad691ba','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMobile','手机','_mobile','32',0,0,36,'1510920****'),('0745fe68-07ae-4ab3-b64e-ab2a650a77ee','ec934ce9-37bd-4c5d-b26c-91894ff427e9','englishName','英文名','string','80',0,0,2,'TengSource'),('0a8f2773-a9e9-402a-ba9d-a28570135fa1','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional2','自定义字段02','string','255',0,0,49,'自定义字段02'),('0eb12aa5-116f-4e0a-a655-2594a5fa96aa','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional9','自定义字段09','string','255',0,0,56,'自定义字段09'),('10791bef-68ca-4c6a-87e0-a4734344dd9b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empNation','民族','string','16',0,0,29,'汉'),('11e7794e-a1cb-4570-8b05-d96fe3c1321f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo.departmentName','部门','string','64',0,1,11,'人力资源部'),('180542d6-dcec-4da6-a01d-1ef2920481f8','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBirthDate','生日','date','yyyy-MM-dd',0,1,3,'1985-8-11'),('1a633a56-e554-48c3-9ebe-523cce87124f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional3','自定义字段03','string','255',0,0,50,'自定义字段03'),('1d7ef3db-182a-4410-b838-3568f4e4b21b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empProfileLoc','档案所在地','string','128',0,0,31,'上海市浦东新区东方路1361号海富大厦18C'),('239dcd92-7fd0-438a-8d0c-a364ba48ef81','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional11','自定义字段11','string','255',0,0,58,'自定义字段11'),('25d9a313-cbd3-4edb-8c7c-9c6cfced139f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional1','自定义字段01','string','255',0,0,48,'自定义字段01'),('267297ef-83ae-47fc-9aa0-6c6187ae8484','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional15','自定义字段15','string','255',0,0,62,'自定义字段15'),('28c36150-9aad-49f5-8f8d-e45f356fc6ac','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMarital','婚否','empMarital',NULL,0,0,5,'未婚'),('2cb4a408-ac15-41a0-b5d5-a66cf03aaa7f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional5','自定义字段05','string','255',0,0,52,'自定义字段05'),('3281470c-dc7c-495a-b0d0-606571e7bc4e','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomePhone','家庭电话','_phone','32',0,0,37,'021-50705175'),('35374455-2b93-4693-9d0f-99c8ac92c1e3','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDistinctNo','工号','string','32',0,1,0,'ty0001'),('35cdae81-d19a-41f7-82ba-e415b2626ad1','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSpeciality','专业','string','64',0,0,27,'国际经济与贸易'),('37a8fb6a-a514-4982-8f65-2bfaf60cd53f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empConfirmDate','转正日期','date','yyyy-MM-dd',0,0,20,'2008-11-24'),('4028801a24527a310124528f8263000b','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEmpId.empDistinctNo','工号','string','32',1,1,0,'tyhrm0001'),('4028801a24527a3101245297299e000e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmDutyHours','全勤(小时)','decimal','2',0,0,4,'160'),('4028801a24527a3101245297299e000f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmDutyDays','全勤(天)','decimal','2',0,0,5,'20'),('4028801a24527a3101245297299e0010','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOnDutyHours','出勤(小时)','decimal','2',0,0,6,'160'),('4028801a24527a3101245297299e0011','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOnDutyDays','出勤(天)','decimal','2',0,0,7,'20'),('4028801a24527a3101245297299e0012','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOffDutyHours','缺勤(小时)','decimal','2',0,0,8,'8'),('4028801a24527a3101245297299e0013','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOffDutyDays','缺勤(天)','decimal','2',0,0,9,'1'),('4028801a24527a3101245297299e0014','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLateTimes','迟到(次)','decimal','2',0,0,10,'2'),('4028801a24527a3101245297299e0015','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEarlyLeave','早退(次)','decimal','2',0,0,11,'2'),('4028801a24527a310124529c75a30016','0972da74-99e1-43fd-86f8-8a25228b74fe','attmAbsentHours','旷工(小时)','decimal','2',0,0,12,'8'),('4028801a24527a310124529c75a30017','0972da74-99e1-43fd-86f8-8a25228b74fe','attmAbsentDays','旷工(天)','decimal','2',0,0,13,'1'),('4028801a24527a310124529c75a30018','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtNormalHours','普通加班(小时)','decimal','2',0,0,38,'8'),('4028801a24527a310124529c75a30019','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtWeekendHours','周末加班(小时)','decimal','2',0,0,39,'3'),('4028801a24527a310124529c75a3001a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtHolidayHours','节假日加班(小时)','decimal','2',0,0,40,'4'),('4028801a24527a310124529c75a3001b','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveAnnualHours','年假(小时)','decimal','2',0,0,14,'5'),('4028801a24527a310124529c75a3001c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveAnnualDays','年假(天)','decimal','2',0,0,15,'40'),('4028801a24527a310124529c75a3001d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTiaoxiuHours','调休(小时)','decimal','2',0,0,32,'30'),('4028801a24527a310124529c75a3001e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTiaoxiuDays','调休(天)','decimal','2',0,0,33,'5'),('4028801a24527a31012452a08e19001f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveCasualHours','事假(小时)','decimal','2',0,0,16,'50'),('4028801a24527a31012452a08e190020','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveCasualDays','事假(天)','decimal','2',0,0,17,'6'),('4028801a24527a31012452a08e190021','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSickHours','病假(小时)','decimal','2',0,0,18,'4'),('4028801a24527a31012452a08e190022','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSickDays','病假(天)','decimal','2',0,0,19,'5'),('4028801a24527a31012452a08e190023','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSick01Hours','病假住院(小时)','decimal','2',0,0,20,'8'),('4028801a24527a31012452a08e190024','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSick01Days','病假住院(天)','decimal','2',0,0,21,'1'),('4028801a24527a31012452a08e190025','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSick02Hours','带薪病假(小时)','decimal','2',0,0,22,'8'),('4028801a24527a31012452a08e190026','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSick02Days','带薪病假(天)','decimal','2',0,0,23,'1'),('4028801a24527a31012452a08e190027','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveWeddingHours','婚假(小时)','decimal','2',0,0,24,'8'),('4028801a24527a31012452a08e190028','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveWeddingDays','婚假(天)','decimal','2',0,0,25,'1'),('4028801a24527a31012452a3e2450029','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveMaternityHours','产假(小时)','decimal','2',0,0,26,'8'),('4028801a24527a31012452a3e245002a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveMaternityDays','产假(天)','decimal','2',0,0,27,'1'),('4028801a24527a31012452a3e245002b','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTravelHours','出差(小时)','decimal','2',0,0,28,'16'),('4028801a24527a31012452a3e245002c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTravelDays','出差(天)','decimal','2',0,0,29,'2'),('4028801a24527a31012452a3e245002d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOuterHours','外出(小时)','decimal','2',0,0,30,'16'),('4028801a24527a31012452a3e245002e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOuterDays','外出(天)','decimal','2',0,0,31,'2'),('4028801a24527a31012452a3e245002f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOtherHours','其他请假(小时)','decimal','2',0,0,36,'4'),('4028801a24527a31012452a3e2450030','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOtherDays','其他请假(天)','decimal','2',0,0,37,'0.5'),('4028801a24527a31012452a3e2450031','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTiaoxiu01Hours','调休过期(小时)','decimal','2',0,0,34,'16'),('4028801a24527a31012452a3e2450032','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTiaoxiu01Days','调休过期(天)','decimal','2',0,0,35,'2'),('4028801a24527a31012452a7182b0033','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField01','自定义字段01','string','32',0,0,41,'自定义字段01'),('4028801a24527a31012452a7182b0034','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField02','自定义字段02','string','32',0,0,42,'自定义字段02'),('4028801a24527a31012452a7182b0035','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField03','自定义字段03','string','32',0,0,43,'自定义字段03'),('4028801a24527a31012452a7182b0036','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField04','自定义字段04','string','32',0,0,44,'自定义字段04'),('4028801a24527a31012452a7182b0037','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField05','自定义字段05','string','32',0,0,45,'自定义字段05'),('4028801a24527a31012452a7182b0038','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField06','自定义字段06','string','32',0,0,46,'自定义字段06'),('4028801a24527a31012452a7182b0039','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField07','自定义字段07','string','32',0,0,47,'自定义字段07'),('4028801a24527a31012452a7182b003a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField08','自定义字段08','string','32',0,0,48,'自定义字段08'),('4028801a24527a31012452a7182b003b','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField09','自定义字段09','string','32',0,0,49,'自定义字段09'),('4028801a24527a31012452a7182b003c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField10','自定义字段10','string','32',0,0,50,'自定义字段10'),('4028801a24527a31012452a92551003d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField11','自定义字段11','string','32',0,0,51,'自定义字段11'),('4028801a24527a31012452a92551003e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField12','自定义字段12','string','32',0,0,52,'自定义字段12'),('4028801a24527a31012452a92551003f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField13','自定义字段13','string','32',0,0,53,'自定义字段13'),('4028801a24527a31012452a925600040','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField14','自定义字段14','string','32',0,0,54,'自定义字段14'),('4028801a24527a31012452a925600041','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField15','自定义字段15','string','32',0,0,55,'自定义字段15'),('4028801a24527a31012452a925600042','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField16','自定义字段16','string','32',0,0,56,'自定义字段16'),('4028801a24527a31012452a925600043','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField17','自定义字段17','string','32',0,0,57,'自定义字段17'),('4028801a24527a31012452a925600044','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField18','自定义字段18','string','32',0,0,58,'自定义字段18'),('4028801a24527a31012452a925600045','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField19','自定义字段19','string','32',0,0,59,'自定义字段19'),('4028801a24527a31012452a925600046','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField20','自定义字段20','string','32',0,0,60,'自定义字段20'),('4028801a24527a31012452aa571a0047','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField21','自定义字段21','string','32',0,0,61,'自定义字段21'),('4028801a24527a31012452aa571a0048','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField22','自定义字段22','string','32',0,0,62,'自定义字段22'),('4028801a24527a31012452aa571a0049','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField23','自定义字段23','string','32',0,0,63,'自定义字段23'),('4028801a24527a31012452aa571a004a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField24','自定义字段24','string','32',0,0,64,'自定义字段24'),('4028801a2455ffb9012457f4074b0017','0972da74-99e1-43fd-86f8-8a25228b74fe','attmComments','备注','string','128',0,0,65,'备注'),('4028801b244675ef01244770564a0001','eee58bd1-176f-4853-9d50-c4581b1aff2f','employee.empDistinctNo','工号','string','32',0,1,0,'ty0001'),('4028801b244675ef01244770564a0002','eee58bd1-176f-4853-9d50-c4581b1aff2f','employee.empBenefitType.benefitTypeName','社保种类','string','64',0,1,3,'城保'),('4028801b244675ef0124477b61120003','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfStartMonth','生效年月','string','6',0,1,4,'200906'),('4028801b244675ef0124478af7100008','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfPensionNo','养老保险号','string','32',0,0,5,'a123'),('4028801b244675ef0124478af7100009','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfMedicalNo','医疗保险号','string','32',0,0,6,'b456'),('4028801b244675ef0124478af710000a','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfHousingNo','公积金号','string','32',0,0,7,'3101019770707****'),('4028801b244675ef0124478af710000b','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfPensionAmount','社保基数','decimal','2',0,0,8,'0.00'),('4028801b244675ef0124478af710000c','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfHousingAmount','公积金基数','decimal','2',0,0,9,'0.00'),('4028801b244675ef0124478af710000d','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfInsuranceAmount','综合保险基数','decimal','2',0,0,10,'0.00'),('4028801b244675ef0124478af710000e','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfComments','备注','string','255',0,0,11,'请填写备注'),('4028801b244b6fa701244b7f317f0001','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','employee.empDistinctNo','工号','string','32',0,0,0,'ty0001'),('4028801b244b6fa701244b7f317f0002','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','employee.empIdentificationNo','身份证号码','string','32',0,0,3,'37072519841013****'),('4028801b244b6fa701244b7f317f0003','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfPensionNo','养老保险号','string','32',0,0,4,'a123'),('4028801b244b6fa701244b7f317f0004','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfMedicalNo','医疗保险号','string','32',0,0,5,'b456'),('4028801b244b6fa701244b7f317f0005','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfHousingNo','公积金号','string','32',0,0,6,'c789'),('4028801b244b6fa701244b7f317f0006','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfStartMonth','生效年月','string','6',0,1,7,'200906'),('4028801b244b6fa701244b7f317f0007','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfPensionAmount','社保基数','decimal','2',0,0,8,'0.00'),('4028801b244b6fa701244b7f317f0008','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfHousingAmount','公积金基数','decimal','2',0,0,9,'0.00'),('4028801b244b6fa701244b7f317f0009','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfInsuranceAmount','综合保险基数','decimal','2',0,0,10,'0.00'),('4028801b244b6fa701244b7f317f000a','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfComments','备注','string','255',0,0,11,'请输入备注'),('4028801b2450a1c2012451c4b2d50001','3afc8569-463b-4fcd-8f52-9356af2306a2','emp.empDistinctNo','工号','string','32',0,1,0,'tyhrm0001'),('4028801b2450a1c2012451c4b2e50002','3afc8569-463b-4fcd-8f52-9356af2306a2','_1','1','string','2',0,0,3,'常'),('4028801b2450a1c2012451c4b2e50003','3afc8569-463b-4fcd-8f52-9356af2306a2','_2','2','string','2',0,0,4,'常'),('4028801b2450a1c2012451c4b2e50004','3afc8569-463b-4fcd-8f52-9356af2306a2','_3','3','string','2',0,0,5,'常'),('4028801b2450a1c2012451c4b2e50005','3afc8569-463b-4fcd-8f52-9356af2306a2','_4','4','string','2',0,0,6,'常'),('4028801b2450a1c2012451c4b2e50006','3afc8569-463b-4fcd-8f52-9356af2306a2','_5','5','string','2',0,0,7,'常'),('4028801b2450a1c2012451c4b2e50007','3afc8569-463b-4fcd-8f52-9356af2306a2','_6','6','string','2',0,0,8,'常'),('4028801b2450a1c2012451c4b2e50008','3afc8569-463b-4fcd-8f52-9356af2306a2','_7','7','string','2',0,0,9,'常'),('4028801b2450a1c2012451c4b2e50009','3afc8569-463b-4fcd-8f52-9356af2306a2','_8','8','string','2',0,0,10,'常'),('4028801b2450a1c2012451c4b2e5000a','3afc8569-463b-4fcd-8f52-9356af2306a2','_9','9','string','2',0,0,11,'常'),('4028801b2450a1c2012451c6ecf6000b','3afc8569-463b-4fcd-8f52-9356af2306a2','_10','10','string','2',0,0,12,'常'),('4028801b2450a1c2012451c6ecf6000c','3afc8569-463b-4fcd-8f52-9356af2306a2','_11','11','string','2',0,0,13,'常'),('4028801b2450a1c2012451c6ecf6000d','3afc8569-463b-4fcd-8f52-9356af2306a2','_12','12','string','2',0,0,14,'常'),('4028801b2450a1c2012451c6ecf6000e','3afc8569-463b-4fcd-8f52-9356af2306a2','_13','13','string','2',0,0,15,'常'),('4028801b2450a1c2012451c6ecf6000f','3afc8569-463b-4fcd-8f52-9356af2306a2','_14','14','string','2',0,0,16,'常'),('4028801b2450a1c2012451c6ecf60010','3afc8569-463b-4fcd-8f52-9356af2306a2','_15','15','string','2',0,0,17,'常'),('4028801b2450a1c2012451c6ecf60011','3afc8569-463b-4fcd-8f52-9356af2306a2','_16','16','string','2',0,0,18,'常'),('4028801b2450a1c2012451c6ecf60012','3afc8569-463b-4fcd-8f52-9356af2306a2','_17','17','string','2',0,0,19,'常'),('4028801b2450a1c2012451c6ecf60013','3afc8569-463b-4fcd-8f52-9356af2306a2','_18','18','string','2',0,0,20,'常'),('4028801b2450a1c2012451c6ecf60014','3afc8569-463b-4fcd-8f52-9356af2306a2','_19','19','string','2',0,0,21,'常'),('4028801b2450a1c2012451c7f31c0015','3afc8569-463b-4fcd-8f52-9356af2306a2','_20','20','string','2',0,0,22,'常'),('4028801b2450a1c2012451c7f31c0016','3afc8569-463b-4fcd-8f52-9356af2306a2','_21','21','string','2',0,0,23,'常'),('4028801b2450a1c2012451c7f31c0017','3afc8569-463b-4fcd-8f52-9356af2306a2','_22','22','string','2',0,0,24,'常'),('4028801b2450a1c2012451c7f31c0018','3afc8569-463b-4fcd-8f52-9356af2306a2','_23','23','string','2',0,0,25,'常'),('4028801b2450a1c2012451c7f31c0019','3afc8569-463b-4fcd-8f52-9356af2306a2','_24','24','string','2',0,0,26,'常'),('4028801b2450a1c2012451c7f31c001a','3afc8569-463b-4fcd-8f52-9356af2306a2','_25','25','string','2',0,0,27,'常'),('4028801b2450a1c2012451c7f31c001b','3afc8569-463b-4fcd-8f52-9356af2306a2','_26','26','string','2',0,0,28,'常'),('4028801b2450a1c2012451c7f31c001c','3afc8569-463b-4fcd-8f52-9356af2306a2','_27','27','string','2',0,0,29,'常'),('4028801b2450a1c2012451c7f31c001d','3afc8569-463b-4fcd-8f52-9356af2306a2','_28','28','string','2',0,0,30,'常'),('4028801b2450a1c2012451c7f31c001e','3afc8569-463b-4fcd-8f52-9356af2306a2','_29','29','string','2',0,0,31,'常'),('4028801b2450a1c2012451c84daf001f','3afc8569-463b-4fcd-8f52-9356af2306a2','_30','30','string','2',0,0,32,'常'),('4028801b2450a1c2012451c84daf0020','3afc8569-463b-4fcd-8f52-9356af2306a2','_31','31','string','2',0,0,33,'常'),('4028801b2450a1c2012451d03d6c0042','a24c4b3a-321e-440a-b739-8018f873440d','emp.empDistinctNo','员工编号','string','36',0,0,0,'tyhrm0001'),('4028801b2450a1c2012451d03d6c0043','a24c4b3a-321e-440a-b739-8018f873440d','emp.empShiftNo','考勤卡号','string',NULL,0,0,1,'card0001'),('4028801b2450a1c2012451d03d6c0044','a24c4b3a-321e-440a-b739-8018f873440d','aodMachineNo','考勤机号','string',NULL,0,0,2,'machine01'),('4028801b2450a1c2012451d03d6c0045','a24c4b3a-321e-440a-b739-8018f873440d','aodAttdDate','考勤日期','date','yyyy-MM-dd',0,0,3,'2009-10-14'),('4028801b2450a1c2012451d03d6c0046','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime1','刷卡时间1','string',NULL,0,0,4,'08:54'),('4028801b2450a1c2012451d03d6c0047','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime2','刷卡时间2','string',NULL,0,0,5,'08:54'),('4028801b2450a1c2012451d03d6c0048','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime3','刷卡时间3','string',NULL,0,0,6,'08:54'),('4028801b2450a1c2012451d03d6c0049','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime4','刷卡时间4','string',NULL,0,0,7,'08:54'),('4028801b2450a1c2012451d03d6c004a','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime5','刷卡时间5','string',NULL,0,0,8,'08:54'),('4028801b2450a1c2012451d03d6c004b','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime6','刷卡时间6','string',NULL,0,0,9,'08:54'),('4028801b2450a1c2012451d0cf3c004c','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime7','刷卡时间7','string',NULL,0,0,10,'08:54'),('4028801b2450a1c2012451d0cf3c004d','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime8','刷卡时间8','string',NULL,0,0,11,'08:54'),('4028801b2450a1c2012451d2b801004e','a24c4b3a-321e-440a-b739-8018f873440d','aodMemo','备注','string',NULL,0,0,12,'备注'),('402880e4245175d001245182bc4c0001','16f058cb-1038-4fb9-b13e-c89e4b887fba','employee.empDistinctNo','工号','string','32',1,1,0,'ty0001'),('402880e4245175d001245182bc4c0003','16f058cb-1038-4fb9-b13e-c89e4b887fba','erPbNo.pbName','职位','string','64',1,0,4,'经理'),('402880e4245175d001245182bc4c0004','16f058cb-1038-4fb9-b13e-c89e4b887fba','department.departmentName','部门','string','64',1,0,2,'人力资源部'),('402880e4245175d0012451869f2c0005','16f058cb-1038-4fb9-b13e-c89e4b887fba','erType','奖惩种类','erType',NULL,0,1,5,'奖励'),('402880e4245175d0012451869f2c0006','16f058cb-1038-4fb9-b13e-c89e4b887fba','erContent','奖惩备注','string','255',0,1,8,'在此录入备注'),('402880e4245175d0012451869f2c0007','16f058cb-1038-4fb9-b13e-c89e4b887fba','erExeDate','奖惩日期','date','yyyy-MM-dd',0,1,6,'2009-10-20'),('402880e4245175d001245188831e0008','16f058cb-1038-4fb9-b13e-c89e4b887fba','erForm','奖惩形式','string','36',0,1,7,'现金'),('402880e42451a7fc01245248e7030001','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','employeeByEeEmpNo.empDistinctNo','工号','string','32',1,1,0,'ty0002'),('402880e42451a7fc01245248e7030003','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eePbNo.pbName','职位','string','64',1,1,4,'检测员'),('402880e42451a7fc01245248e7030004','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','department.departmentName','部门','string','64',1,1,2,'技术部'),('402880e42451a7fc01245248e7030005','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','employeeByEeManager.empDistinctNo','考评经理','string','32',1,1,5,'ty0001'),('402880e42451a7fc0124524bec730006','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eeStartDate','起始日期','date','yyyy-MM-dd',1,1,7,'2009-01-01'),('402880e42451a7fc0124524bec730007','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eeEndDate','终止日期','date','yyyy-MM-dd',1,1,8,'2009-12-31'),('402880e42451a7fc0124524bec730008','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eeComments','备注','string','255',1,1,10,'请录入备注'),('402880e42451a7fc0124524bec730009','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eeRate','考评结果','string','36',1,1,9,'A'),('402880e42451a7fc0124524bec73000a','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','eeType','考评种类','eeType',NULL,1,1,6,'全年'),('402880e4245b8e8d01245b90b4360001','74008fd7-ec77-4a68-92b7-b5fe17d834fc','employee.empDistinctNo','工号','string','32',1,1,0,'ty0002'),('402880e4245b8e8d01245b90b4360006','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftTransferDate','变动日期','date','yyyy-MM-dd',0,0,2,'2009-10-22'),('402880e4245b8e8d01245b90b4360007','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftTransferType','变动种类','eftTransferType',NULL,0,0,3,'平调'),('402880e4245b8e8d01245b90b4360008','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftComments','备注','string','255',0,0,20,'请录入备注'),('402880e4245b8e8d01245b90b4360009','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftReason','变动原因','string','128',0,0,19,'临时调动'),('402880e4245bc90301245bd48e060002','bd2f89b7-8f46-4634-931f-90171cfe7731','employee.empDistinctNo','工号','string','32',0,1,0,'ty0002'),('402880e4245bc90301245bd48e060003','bd2f89b7-8f46-4634-931f-90171cfe7731','ectNo','合同编号','string','36',0,0,5,'TY2009001'),('402880e4245bc90301245bd48e060004','bd2f89b7-8f46-4634-931f-90171cfe7731','etcExpire','合同期限','etcExpire',NULL,0,1,8,'有期限'),('402880e4245bc90301245bd48e060005','bd2f89b7-8f46-4634-931f-90171cfe7731','ectStatus','合同状态','ectStatus',NULL,0,1,7,'有效'),('402880e4245bc90301245bd48e060006','bd2f89b7-8f46-4634-931f-90171cfe7731','contractType.ectName','合同种类','string','64',0,1,6,'正式合同'),('402880e4245bc90301245bd48e060007','bd2f89b7-8f46-4634-931f-90171cfe7731','ectStartDate','合同起始日期','date','yyyy-MM-dd',0,1,3,'2009-11-23'),('402880e4245bc90301245bd48e060008','bd2f89b7-8f46-4634-931f-90171cfe7731','ectEndDate','合同结束日期','date','yyyy-MM-dd',0,0,4,'2010-11-22'),('402880e4245bc90301245bd48e060009','bd2f89b7-8f46-4634-931f-90171cfe7731','ectComments','备注','string','255',0,0,9,'请录入备注'),('402880e5245c177201245c2478b40002','c968e365-86f7-48bf-9a5a-5924ce18514e','employee.empDistinctNo','工号','string','32',1,1,0,'ty0002'),('402880e5245c177201245c2478b40003','c968e365-86f7-48bf-9a5a-5924ce18514e','escBankAccountNo','银行帐号','string','32',1,1,3,'123-123123'),('402880e5245c177201245c2478b40004','c968e365-86f7-48bf-9a5a-5924ce18514e','escJobgrade.jobGradeName','薪资级别','string','64',0,1,4,'销售经理'),('402880e5245c177201245c2478b40005','c968e365-86f7-48bf-9a5a-5924ce18514e','escEsavId.esavEsac.esacName','薪资帐套','string','64',0,1,5,'正式员工帐套'),('402880e5245c177201245c2478b40009','c968e365-86f7-48bf-9a5a-5924ce18514e','escBankName','银行开户行','string','64',0,0,6,'中国建设银行'),('402880e5245c177201245c2478b4000a','c968e365-86f7-48bf-9a5a-5924ce18514e','escCostCenter','成本中心','string','32',0,0,7,'软件销售管理费'),('402880e5245c177201245c26cf94000b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn1','A1','decimal','2',0,0,8,'0.00'),('402880e5245c177201245c26cf94000c','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn2','A2','decimal','2',0,0,9,'0.00'),('402880e5245c177201245c26cf94000d','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn3','A3','decimal','2',0,0,10,'0.00'),('402880e5245c177201245c26cf94000e','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn4','A4','decimal','2',0,0,11,'0.00'),('402880e5245c177201245c26cf94000f','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn5','A5','decimal','2',0,0,12,'0.00'),('402880e5245c177201245c26cf940010','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn6','A6','decimal','2',0,0,13,'0.00'),('402880e5245c177201245c26cf940011','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn7','A7','decimal','2',0,0,14,'0.00'),('402880e5245c177201245c26cf940012','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn8','A8','decimal','2',0,0,15,'0.00'),('402880e5245c177201245c26cf940013','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn9','A9','decimal','2',0,0,16,'0.00'),('402880e5245c177201245c26cf940014','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn10','A10','decimal','2',0,0,17,'0.00'),('402880e5245c177201245c290a030015','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn11','A11','decimal','2',0,0,18,'0.00'),('402880e5245c177201245c290a030016','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn12','A12','decimal','2',0,0,19,'0.00'),('402880e5245c177201245c290a030017','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn13','A13','decimal','2',0,0,20,'0.00'),('402880e5245c177201245c290a030018','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn14','A14','decimal','2',0,0,21,'0.00'),('402880e5245c177201245c290a030019','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn15','A15','decimal','2',0,0,22,'0.00'),('402880e5245c177201245c290a03001a','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn16','A16','decimal','2',0,0,23,'0.00'),('402880e5245c177201245c290a03001b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn17','A17','decimal','2',0,0,24,'0.00'),('402880e5245c177201245c290a03001c','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn18','A18','decimal','2',0,0,25,'0.00'),('402880e5245c177201245c290a03001d','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn19','A19','decimal','2',0,0,26,'0.00'),('402880e5245c177201245c2a4199001e','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn20','A20','decimal','2',0,0,27,'0.00'),('402880e5245c177201245c2a4199001f','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn21','A21','decimal','2',0,0,28,'0.00'),('402880e5245c177201245c2a41a90020','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn22','A22','decimal','2',0,0,29,'0.00'),('402880e5245c177201245c2a41a90021','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn23','A23','decimal','2',0,0,30,'0.00'),('402880e5245c177201245c2a41a90022','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn24','A24','decimal','2',0,0,31,'0.00'),('402880e5245c177201245c2a41a90023','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn25','A25','decimal','2',0,0,32,'0.00'),('402880e5245c177201245c2a41a90024','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn26','A26','decimal','2',0,0,33,'0.00'),('402880e5245c177201245c2a41a90025','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn27','A27','decimal','2',0,0,34,'0.00'),('402880e5245c177201245c2a41a90026','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn28','A28','decimal','2',0,0,35,'0.00'),('402880e5245c177201245c2a41a90027','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn29','A29','decimal','2',0,0,36,'0.00'),('402880e5245c177201245c2b97070028','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn30','A30','decimal','2',0,0,37,'0.00'),('402880e5245c177201245c2b97070029','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn31','A31','decimal','2',0,0,38,'0.00'),('402880e5245c177201245c2b9707002a','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn32','A32','decimal','2',0,0,39,'0.00'),('402880e5245c177201245c2b9707002b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn33','A33','decimal','2',0,0,40,'0.00'),('402880e5245c177201245c2b9707002c','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn34','A34','decimal','2',0,0,41,'0.00'),('402880e5245c177201245c2b9707002d','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn35','A35','decimal','2',0,0,42,'0.00'),('402880e5245c177201245c2b9707002e','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn36','A36','decimal','2',0,0,43,'0.00'),('402880e5245c177201245c2b9707002f','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn37','A37','decimal','2',0,0,44,'0.00'),('402880e5245c177201245c2b97070030','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn38','A38','decimal','2',0,0,45,'0.00'),('402880e5245c177201245c2b97070031','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn39','A39','decimal','2',0,0,46,'0.00'),('402880e5245c177201245c2cb5870032','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn40','A40','decimal','2',0,0,47,'0.00'),('402880e5245c177201245c2cb5870033','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn41','A41','decimal','2',0,0,48,'0.00'),('402880e5245c177201245c2cb5870034','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn42','A42','decimal','2',0,0,49,'0.00'),('402880e5245c177201245c2cb5870035','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn43','A43','decimal','2',0,0,50,'0.00'),('402880e5245c177201245c2cb5870036','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn44','A44','decimal','2',0,0,51,'0.00'),('402880e5245c177201245c2cb5870037','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn45','A45','decimal','2',0,0,52,'0.00'),('402880e5245c177201245c2cb5870038','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn46','A46','decimal','2',0,0,53,'0.00'),('402880e5245c177201245c2cb5870039','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn47','A47','decimal','2',0,0,54,'0.00'),('402880e5245c177201245c2cb587003a','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn48','A48','decimal','2',0,0,55,'0.00'),('402880e526f9694f0126f9b5837c0001','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo1.departmentName','一级部门','string','64',0,0,6,'人力资源部'),('402880e526f9694f0126f9b5838c0002','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo2.departmentName','二级部门','string','64',0,0,7,'人力资源部'),('402880e526f9694f0126f9b5838c0003','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo3.departmentName','三级部门','string','64',0,0,8,'人力资源部'),('402880e526f9694f0126f9b5838c0004','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo4.departmentName','四级部门','string','64',0,0,9,'人力资源部'),('402880e526f9694f0126f9b5838c0005','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo5.departmentName','五级部门','string','64',0,0,10,'人力资源部'),('402880e526f9694f0126f9b5839b0006','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empPbNo.pbName','职位','string','64',0,1,12,'工程师'),('402880e52717e27e0127184600c20001','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo1.departmentName','变动前一级部门','string','64',0,0,4,'咨询部'),('402880e52717e27e0127184601010002','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo2.departmentName','变动前二级部门','string','64',0,0,5,'技术部'),('402880e52717e27e0127184601010003','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo3.departmentName','变动前三级部门','string','64',0,0,6,'技术部'),('402880e52717e27e0127184601010004','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo4.departmentName','变动前四级部门','string','64',0,0,7,'技术部'),('402880e52717e27e0127184601010005','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo5.departmentName','变动前五级部门','string','64',0,0,8,'技术部'),('402880e52717e27e0127184601010006','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldDeptNo.departmentName','变动前部门','string','64',0,0,9,'技术部'),('402880e52717e27e0127184601010007','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftOldPbId.pbName','变动前职位','string','64',0,0,10,'技术总监'),('402880e52717e27e0127184601010008','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo1.departmentName','变动后一级部门','string','64',0,0,11,'技术部'),('402880e52717e27e0127184601010009','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo2.departmentName','变动后二级部门','string','64',0,0,12,'技术部'),('402880e52717e27e012718460110000a','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo3.departmentName','变动后三级部门','string','64',0,0,13,'技术部'),('402880e52717e27e012718506a3a0015','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo4.departmentName','变动后四级部门','string','64',0,0,14,'技术部'),('402880e52717e27e012718506a3a0016','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo5.departmentName','变动后五级部门','string','64',0,0,15,'技术部'),('402880e52717e27e012718506a3a0017','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewDeptNo.departmentName','变动后部门','string','64',0,1,16,'技术部'),('402880e52717e27e012718506a3a0018','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewPbId.pbName','变动后职位','string','64',0,1,17,'项目经理'),('402880e52717e27e012718506a3a0019','74008fd7-ec77-4a68-92b7-b5fe17d834fc','eftNewSupNo.empDistinctNo','变动后经理','string','32',0,0,18,'tyhrm0026'),('402880f02476130a012479d0c589004a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEmpId.empName','姓名','string','32',0,0,1,'张三'),('402880f02476130a012479d0c5a8004c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEmpId.empDeptNo.departmentName','部门','string','32',0,0,2,'技术咨询部'),('402880f02479d737012479e26b5b0001','3afc8569-463b-4fcd-8f52-9356af2306a2','emp.empName','姓名','string','64',0,0,1,'陈初发'),('402880f02479d737012479e26b7a0002','3afc8569-463b-4fcd-8f52-9356af2306a2','emp.empDeptNo.departmentName','部门','string','64',0,0,2,'总裁办公室'),('402880f02479d737012479f7cc03004d','16f058cb-1038-4fb9-b13e-c89e4b887fba','employee.empName','姓名','string','32',0,0,1,'张三'),('402880f02479d73701247a0ee548005f','74008fd7-ec77-4a68-92b7-b5fe17d834fc','employee.empName','姓名','string','32',0,0,1,'张三'),('402880f02479d73701247a4008df006e','a24c4b3a-321e-440a-b739-8018f873440d','emp.empName','员工姓名','string',NULL,0,0,13,'陈初发'),('402880f02479d73701247a4008df006f','a24c4b3a-321e-440a-b739-8018f873440d','emp.empDeptNo.departmentName','部门名称','string',NULL,0,0,14,'总裁办公室'),('402880f02479d73701247a4912f50076','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espEmpno.empName','姓名','string','64',0,0,1,'陈初发'),('402880f02479d73701247a4912f50077','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espEmpno.empDeptNo.departmentName','部门','string','64',0,0,2,'总裁办公室'),('402880f02479d73701247a4b9f5d007e','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','employeeByEeEmpNo.empName','姓名','string','32',0,0,1,'张三'),('402880f02479d73701247a4f9eec0082','c968e365-86f7-48bf-9a5a-5924ce18514e','employee.empName','姓名','string','64',0,0,1,'陈初发'),('402880f02479d73701247a4f9eec0083','c968e365-86f7-48bf-9a5a-5924ce18514e','employee.empDeptNo.departmentName','部门','string','64',0,0,2,'总裁办公室'),('402880f02479d73701247a4fa41c008e','bd2f89b7-8f46-4634-931f-90171cfe7731','employee.empName','姓名','string','32',0,0,1,'张三'),('402880f02479d73701247a4fa41c008f','bd2f89b7-8f46-4634-931f-90171cfe7731','employee.empDeptNo.departmentName','部门','string','32',0,0,2,'技术咨询部'),('402880f02479d73701247a627fbf0092','eee58bd1-176f-4853-9d50-c4581b1aff2f','employee.empName','姓名','string','64',0,0,1,'陈初发'),('402880f02479d73701247a627fbf0093','eee58bd1-176f-4853-9d50-c4581b1aff2f','employee.empDeptNo.departmentName','部门','string','64',0,0,2,'总裁办公室'),('402880f02479d73701247b988161009b','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','employee.empName','姓名','string','64',0,0,1,'陈初发'),('402880f02479d73701247b988161009c','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','employee.empDeptNo.departmentName','部门','string','64',0,0,2,'总裁办公室'),('402880f0248a422001248a60d9400001','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empShiftNo','考勤卡号','string','32',0,0,45,'10000122'),('402880f0248a706001248adf46da003f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','quit.eqType','离职种类','eqType',NULL,0,0,65,'辞职'),('402880f0248a706001248adf46da0040','ec934ce9-37bd-4c5d-b26c-91894ff427e9','quit.eqPermission.empDistinctNo','离职审批人工号','string','32',0,0,66,'ty0001'),('402880f0248a706001248adf46da0041','ec934ce9-37bd-4c5d-b26c-91894ff427e9','quit.eqReason','离职原因','string','128',0,0,67,'个人原因'),('402880f0248a706001248adf46da0042','ec934ce9-37bd-4c5d-b26c-91894ff427e9','quit.erComments','离职备注','string','255',0,0,68,'如果状态为离职，才需填写离职信息'),('402880f0248a706001248ae587ae0047','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.ectStartDate','合同起始日期','date','yyyy-MM-dd',0,0,69,'2008-06-17'),('402880f0248a706001248ae587ae0048','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.ectEndDate','合同结束日期','date','yyyy-MM-dd',0,0,70,'2009-06-16'),('402880f0248a706001248ae587ae0049','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.ectNo','合同编号','string','32',0,0,71,'TYC01001'),('402880f0248a706001248ae587ae004a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.contractType.ectName','合同种类','string','32',0,0,72,'正式合同'),('402880f0248a706001248ae587ae004b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.ectStatus','合同状态','ectStatus',NULL,0,0,73,'有效'),('402880f0248a706001248ae587ae004c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.etcExpire','合同期限','etcExpire',NULL,0,0,74,'有限期'),('402880f0248a706001248ae587ae004d','ec934ce9-37bd-4c5d-b26c-91894ff427e9','contract.ectComments','合同备注','string','255',0,0,75,'如果有合同数据，才需填写相关信息'),('402881e524aae9260124ab0d0d5a0007','0972da74-99e1-43fd-86f8-8a25228b74fe','attmYearmonth','年月','string','6',0,0,3,'200910'),('402881e72458c350012458ca1c870001','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espEmpno.empDistinctNo','工号','string','32',0,1,0,'ty0001'),('402881e72458c350012458ca1c970002','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn1','A1','decimal','2',0,0,3,'0.00'),('402881e72458c350012458ca1c970003','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn2','A2','decimal','2',0,0,4,'0.00'),('402881e72458c350012458ca1c970004','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn3','A3','decimal','2',0,0,5,'0.00'),('402881e72458c350012458ca1c970005','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn4','A4','decimal','2',0,0,6,'0.00'),('402881e72458c350012458ca1c970006','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn5','A5','decimal','2',0,0,7,'0.00'),('402881e72458c350012458ca1c970007','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn6','A6','decimal','2',0,0,8,'0.00'),('402881e72458c350012458ca1c970008','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn7','A7','decimal','2',0,0,9,'0.00'),('402881e72458c350012458ca1c970009','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn8','A8','decimal','2',0,0,10,'0.00'),('402881e72458c350012458ca1c97000a','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn9','A9','decimal','2',0,0,11,'0.00'),('402881e72458c350012458cbc584000b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn10','A10','decimal','2',0,0,12,'0.00'),('402881e72458c350012458cbc584000c','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn11','A11','decimal','2',0,0,13,'0.00'),('402881e72458c350012458cbc584000d','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn12','A12','decimal','2',0,0,14,'0.00'),('402881e72458c350012458cbc584000e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn13','A13','decimal','2',0,0,15,'0.00'),('402881e72458c350012458cbc584000f','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn14','A14','decimal','2',0,0,16,'0.00'),('402881e72458c350012458cbc5840010','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn15','A15','decimal','2',0,0,17,'0.00'),('402881e72458c350012458cbc5840011','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn16','A16','decimal','2',0,0,18,'0.00'),('402881e72458c350012458cbc5840012','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn17','A17','decimal','2',0,0,19,'0.00'),('402881e72458c350012458cbc5840013','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn18','A18','decimal','2',0,0,20,'0.00'),('402881e72458c350012458cbc5840014','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn19','A19','decimal','2',0,0,21,'0.00'),('402881e72458c350012458cd6eee0015','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn20','A20','decimal','2',0,0,22,'0.00'),('402881e72458c350012458cd6eee0016','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn21','A21','decimal','2',0,0,23,'0.00'),('402881e72458c350012458cd6eee0017','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn22','A22','decimal','2',0,0,24,'0.00'),('402881e72458c350012458cd6eee0018','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn23','A23','decimal','2',0,0,25,'0.00'),('402881e72458c350012458cd6eee0019','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn24','A24','decimal','2',0,0,26,'0.00'),('402881e72458c350012458cd6eee001a','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn25','A25','decimal','2',0,0,27,'0.00'),('402881e72458c350012458cd6eee001b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn26','A26','decimal','2',0,0,28,'0.00'),('402881e72458c350012458cd6eee001c','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn27','A27','decimal','2',0,0,29,'0.00'),('402881e72458c350012458cd6eee001d','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn28','A28','decimal','2',0,0,30,'0.00'),('402881e72458c350012458cd6eee001e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn29','A29','decimal','2',0,0,31,'0.00'),('402881e72458c350012458cee581001f','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn30','A30','decimal','2',0,0,32,'0.00'),('402881e72458c350012458cee5810020','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn31','A31','decimal','2',0,0,33,'0.00'),('402881e72458c350012458cee5810021','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn32','A32','decimal','2',0,0,34,'0.00'),('402881e72458c350012458cee5810022','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn33','A33','decimal','2',0,0,35,'0.00'),('402881e72458c350012458cee5810023','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn34','A34','decimal','2',0,0,36,'0.00'),('402881e72458c350012458cee5810024','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn35','A35','decimal','2',0,0,37,'0.00'),('402881e72458c350012458cee5810025','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn36','A36','decimal','2',0,0,38,'0.00'),('402881e72458c350012458cee5810026','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn37','A37','decimal','2',0,0,39,'0.00'),('402881e72458c350012458cee5810027','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn38','A38','decimal','2',0,0,40,'0.00'),('402881e72458c350012458cee5810028','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn39','A39','decimal','2',0,0,41,'0.00'),('402881e72458c350012458d0c3a70029','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn40','A40','decimal','2',0,0,42,'0.00'),('402881e72458c350012458d0c3a7002a','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn41','A41','decimal','2',0,0,43,'0.00'),('402881e72458c350012458d0c3a7002b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn42','A42','decimal','2',0,0,44,'0.00'),('402881e72458c350012458d0c3a7002c','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn43','A43','decimal','2',0,0,45,'0.00'),('402881e72458c350012458d0c3a7002d','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn44','A44','decimal','2',0,0,46,'0.00'),('402881e72458c350012458d0c3a7002e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn45','A45','decimal','2',0,0,47,'0.00'),('402881e72458c350012458d0c3a7002f','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn46','A46','decimal','2',0,0,48,'0.00'),('402881e72458c350012458d0c3b70030','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn47','A47','decimal','2',0,0,49,'0.00'),('402881e72458c350012458d0c3b70031','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','espColumn48','A48','decimal','2',0,0,50,'0.00'),('43dd9b82-bb8f-4377-b211-83d6304f44fd','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empIdentificationType','证件种类','empIdentificationType',NULL,0,1,21,'身份证'),('4a0e3c17-c153-4889-9bd5-f9081cc8e1a3','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMsn','MSN/QQ','_msn','64',0,0,34,'tengsource@hotmail.com'),('4a9cfb95-227f-4227-a9f9-f87c086edc0a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empShiftType','考勤方式','empShiftType',NULL,0,0,44,'默认班次刷卡'),('4f266706-c205-41ca-8c18-9ec479d79a7b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional13','自定义字段13','string','255',0,0,60,'自定义字段13'),('50d753ab-d0bb-4053-9bf0-f74a28bc9e41','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomeZip','家庭地址邮编','_zip','16',0,0,41,'200127'),('57d4910e-1271-4fa0-b366-f0445f692acd','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional12','自定义字段12','string','255',0,0,59,'自定义字段12'),('58a74c1b-0864-44d9-8777-740db5c1f3c5','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empWorkPhone','工作电话','_phone','32',0,0,35,'021-50705175'),('5d5d45d5-bf6e-4ce8-870d-8d66eb1bf41e','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empResidenceLoc','户口所在地','string','128',0,0,30,'上海市浦东新区东方路1361号海富大厦18C'),('61590553-a079-43c1-94bd-27a179023d19','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDiploma','学历','string','16',0,0,25,'本科'),('6665c77e-be4f-47fb-abb0-bd457bb97da5','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomeAddr','家庭地址','string','128',0,0,40,'上海市浦东新区东方路1361号海富大厦'),('6f0a9a25-badc-4dba-a650-00933612e9a6','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional8','自定义字段08','string','255',0,0,55,'自定义字段08'),('6f2bc50c-c013-409d-adee-fbea55dbf608','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional4','自定义字段04','string','255',0,0,51,'自定义字段04'),('75f09d00-c9e8-43ac-8644-cd891792ee8c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empWorkDate','参加工作日期','date','yyyy-MM-dd',0,0,18,'2008-06-17'),('7e3dbcbf-cf7d-4d0a-bd2e-f78168f330b9','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empStatus','状态','empStatus',NULL,0,1,46,'在职'),('828a6b97-6fef-4eb1-a38c-3ab64e46987c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBenefitType.benefitTypeName','社保种类','string','64',0,0,32,'上海四金'),('8581d13c-103b-47d0-bc6f-9f5a7b805c5b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empLocationNo.locationName','工作地区','string','64',0,1,15,'上海'),('8c56e714-0dd5-4ca4-ac4a-18b04baf14fb','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empIdentificationNo','证件号码','string','32',0,1,22,'36213119811016****'),('8e009d17-d517-4034-95d4-8e30a913dd3d','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empTerminateDate','离职日期','date','yyyy-MM-dd',0,0,64,'2010-12-31'),('9732b770-3885-49a3-a6ac-589b8b0b2682','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSupNo.empDistinctNo','经理工号','string','32',0,0,16,'ty0001'),('abe42933-ffe4-47d6-82be-4552fba8bb4d','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empName','姓名','string','64',0,1,1,'人力资源管理系统'),('af75f95c-aec1-40d4-a9f2-25347ae38b1e','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empUrgentConMethod','紧急联系方式','string','128',0,0,43,'50205175'),('b5a92876-069f-4444-8536-3bacd37f908f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSchool','毕业院校','string','64',0,0,26,'南京财经大学'),('bf48cc8c-9fd3-46e6-8bcc-9c4ecfa19ce2','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional10','自定义字段10','string','255',0,0,57,'自定义字段10'),('c47ff799-5341-4a65-8e5f-4d0253f8a632','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empEmail','邮箱','_email','64',0,0,33,'sales@hr.com'),('c4e20c8a-7f08-4578-9bb8-a9cd15a3b87a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empJoinDate','入职日期','date','yyyy-MM-dd',0,1,19,'2008-06-17'),('c77c4b95-48b3-4bf0-845a-d8f62a118bc7','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional14','自定义字段14','string','255',0,0,61,'自定义字段14'),('c92cf36a-175f-439c-a7bd-810d147889dc','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empComments','备注','string','255',0,0,47,'请录入备注'),('c97e76ff-8573-404f-8d4a-a2881146642c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empType.emptypeName','用工形式','string','32',0,1,17,'正式员工'),('d05cb9cf-d91e-4610-b13b-ae9796bfcb8d','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional6','自定义字段06','string','255',0,0,53,'自定义字段06'),('d856c9d4-f347-422a-8dd1-d45d7464e5c3','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCurrZip','当前住址邮编','_zip','16',0,0,39,'200127'),('db0daccc-e1fc-4b08-9c1f-ce72f79444b3','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empGender','性别','empGender',NULL,0,1,4,'男'),('e2469ce5-8854-4bfc-8377-4d60eba1fabc','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCurrAddr','当前住址','string','128',0,0,38,'上海市浦东新区东方路1361号海富大厦'),('e3c89853-6be6-4140-acbe-a6f50c0c72be','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional7','自定义字段07','string','255',0,0,54,'自定义字段07'),('e449e659-5893-4065-b235-b55fb2d42d2a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empAdditional16','自定义字段16','string','255',0,0,63,'自定义字段16'),('e4c74e9e-0019-4461-932e-d1fab58dd36b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empPolitics','政治面貌','string','16',0,0,24,'群众'),('f184db88-06b0-437e-87af-75d4d423be1a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empUrgentContact','紧急联系人','string','64',0,0,42,'人力资源管理系统');
/*!40000 ALTER TABLE `inmatchbasic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inmatchmodel`
--

DROP TABLE IF EXISTS `inmatchmodel`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `inmatchmodel` (
  `imm_id` varchar(36) NOT NULL,
  `imm_io_id` varchar(36) NOT NULL,
  `imm_name` varchar(64) NOT NULL,
  `imm_desc` varchar(64) default NULL,
  `imm_input_type` varchar(8) NOT NULL,
  `imm_no_title` int(10) unsigned NOT NULL default '0',
  `imm_import_mode` int(10) unsigned NOT NULL default '0',
  `imm_default` int(10) unsigned NOT NULL default '0',
  `imm_create_by` varchar(36) NOT NULL,
  `imm_create_time` datetime NOT NULL,
  `imm_last_change_by` varchar(36) NOT NULL,
  `imm_last_change_time` datetime NOT NULL,
  PRIMARY KEY  (`imm_id`),
  KEY `FK_imm_io_id` (`imm_io_id`),
  CONSTRAINT `FK_imm_io_id` FOREIGN KEY (`imm_io_id`) REFERENCES `iodef` (`io_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `inmatchmodel`
--

LOCK TABLES `inmatchmodel` WRITE;
/*!40000 ALTER TABLE `inmatchmodel` DISABLE KEYS */;
INSERT INTO `inmatchmodel` VALUES ('4028801b2450a1c2012451c962f80021','3afc8569-463b-4fcd-8f52-9356af2306a2','排班信息导入','排班信息导入','excel',0,0,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-14 14:43:22','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 14:01:37'),('4028801b2450a1c2012451dbf1b3004f','a24c4b3a-321e-440a-b739-8018f873440d','考勤机数据导入','考勤机数据导入','excel',0,0,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-14 15:03:39','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 14:19:19'),('402880f0245c846f01245cb5017f0089','afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','考评记录导入','考评记录导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-16 17:36:56','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 15:40:12'),('402880f0245c846f01245cc0a10500e4','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','薪资发放导入','薪资发放导入','excel',0,1,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-16 17:49:38','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-29 18:27:13'),('402880f0245c846f01245cc6d9bb0150','c968e365-86f7-48bf-9a5a-5924ce18514e','薪资配置导入','薪资配置导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-16 17:56:26','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-29 17:49:01'),('402880f0245c846f01245cc859e30187','eee58bd1-176f-4853-9d50-c4581b1aff2f','社保初始化导入','社保初始化导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-16 17:58:04','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-29 17:50:23'),('402880f0245c846f01245cca99f00192','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','社保基数调整导入','社保基数调整导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-16 18:00:31','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-29 17:44:14'),('402880f02479d737012479f03dbf0009','0972da74-99e1-43fd-86f8-8a25228b74fe','每月考勤数据导入-按天','每月考勤数据导入-按天','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-22 09:50:37','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 00:02:29'),('402880f02479d737012479fce54f0055','16f058cb-1038-4fb9-b13e-c89e4b887fba','奖惩记录导入','奖惩记录导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-22 10:04:27','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 15:38:16'),('402880f02479d73701247a10a78a0063','74008fd7-ec77-4a68-92b7-b5fe17d834fc','员工变动记录导入','员工变动记录导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-22 10:26:02','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 14:51:26'),('402880f0248a706001248a8b93e90001','ec934ce9-37bd-4c5d-b26c-91894ff427e9','员工基础资料导入','员工基础资料导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-25 15:14:13','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-26 17:12:18'),('402880f0248afef801248b0d3db20002','bd2f89b7-8f46-4634-931f-90171cfe7731','人事合同导入','人事合同导入','excel',0,0,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-25 17:35:51','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-26 18:33:03'),('402881e524aae9260124ab478dda0011','0972da74-99e1-43fd-86f8-8a25228b74fe','每月考勤数据导入-按小时','每月考勤数据导入-按小时','excel',0,0,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-31 23:47:23','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 00:04:14');
/*!40000 ALTER TABLE `inmatchmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iodef`
--

DROP TABLE IF EXISTS `iodef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `iodef` (
  `io_id` varchar(36) NOT NULL,
  `io_name` varchar(64) NOT NULL,
  `io_desc` varchar(128) default NULL,
  `io_type` int(10) unsigned NOT NULL default '0',
  `io_file_path` int(10) unsigned NOT NULL default '0',
  `io_file_has_title` int(10) unsigned NOT NULL default '0',
  `io_match_type` int(10) unsigned NOT NULL default '0',
  `io_module` int(10) unsigned NOT NULL default '0',
  `io_authority` varchar(64) NOT NULL,
  `io_is_extend` int(10) unsigned default NULL,
  `io_import_mode` int(10) unsigned default NULL,
  `io_class_name` varchar(128) NOT NULL,
  `io_fetch_names` varchar(255) NOT NULL,
  `io_create_by` varchar(36) default NULL,
  `io_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `io_last_change_by` varchar(36) default NULL,
  `io_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`io_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `iodef`
--

LOCK TABLES `iodef` WRITE;
/*!40000 ALTER TABLE `iodef` DISABLE KEYS */;
INSERT INTO `iodef` VALUES ('032ee263-9f5b-46b8-bede-6491a8fa7730','OAttendDaily','每日考勤 - 每日考勤数据导出',1,0,1,0,4,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-31 19:18:55','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-31 19:18:55'),('0972da74-99e1-43fd-86f8-8a25228b74fe','IExamMonthly','每月考勤 - 每月考勤数据导入',0,0,1,0,4,'#401,1#401,2',0,NULL,'com.hr.examin.domain.Attendmonthly','attmEmpId','855e2ab3-1a63-417a-a296-a0add3085500','2008-09-25 16:37:39','855e2ab3-1a63-417a-a296-a0add3085500','2009-05-21 14:51:43'),('09e0e6bb-d846-474a-aeeb-8655d2541686','OSalaryAcctAll','薪资帐套 - 所有薪资帐套导出',1,0,1,0,2,'#201,1#201,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-21 14:32:37','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-21 14:32:37'),('16f058cb-1038-4fb9-b13e-c89e4b887fba','IEmpreward','档案管理 - 奖惩记录导入',0,0,0,0,1,'#201,1#201,2#221,0',0,NULL,'com.hr.profile.domain.Empreward','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16'),('3afc8569-463b-4fcd-8f52-9356af2306a2','IExaminShift','排班管理 - 排班信息导入',0,0,1,0,4,'#401,1#421,2#421,3',0,NULL,'com.hr.io.domain.ExaminShiftBean','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-07 14:50:38','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-07 15:24:48'),('444bd513-c17a-4dc3-ac7a-e73444b00dfb','OEmpbenefitHistory','社保管理 - 社保缴费历史导出',1,0,0,0,2,'#201,1#201,2',0,NULL,'Empbenefitplan','ebpEmpNo','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:49','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:49'),('4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','IEmpSalaryBenefitUpd','社保管理 - 社保基数调整导入',0,0,1,0,2,'#201,1#201,2',0,NULL,'com.hr.compensation.domain.Empbenefit','','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-25 14:47:49','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-26 16:31:08'),('53979184-f70f-4360-b548-635dde0fc7ec','OEmpSalaryBenefit','社保管理 - 社保配置导出',1,0,1,0,2,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-24 16:48:03','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-25 14:33:16'),('5d46f106-6099-478c-8911-9c684222cf4c','OEmpContract','档案管理 - 人事合同导出',1,0,0,0,1,'#101,1#101,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:14','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:14'),('653d4849-2f36-4261-bce9-a99eb6c15073','OEmpQuit','档案管理 - 离职记录导出',1,0,0,0,1,'#101,1#101,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:36:35','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:36:35'),('65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','IEmpSalaryPay','薪资发放 - 每月薪资发放导入',0,0,1,0,2,'#201,1#201,2',0,NULL,'com.hr.compensation.domain.Empsalarypay','','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-14 18:21:39','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-14 18:21:39'),('68ee8cfa-4788-4290-b265-f6f4c14e4889','OEmployeeBasic','员工管理 - 在职员工基础资料导出',1,0,1,0,1,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-24 16:48:03','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-25 14:33:16'),('74008fd7-ec77-4a68-92b7-b5fe17d834fc','IEmptransfer','档案管理 - 变动记录导入',0,0,0,0,1,'#201,1#201,2#221,0',0,NULL,'com.hr.profile.domain.Emptransfer','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16'),('801f19ca-c8a7-4550-be9c-8ab0950631a3','OEmployeeQuit','离职员工 - 离职员工基础资料导出',1,0,1,0,1,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-28 11:23:00','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-28 11:23:00'),('96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','OEmpEval','档案管理 - 考评记录导出',1,0,0,0,1,'#101,1#101,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:31','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:31'),('a24c4b3a-321e-440a-b739-8018f873440d','IExaminCardData','刷卡管理 - 考勤机数据导入',0,0,1,0,4,'#401,1#401,2',0,NULL,'com.hr.io.domain.ExaminCardBean','','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-14 18:21:39','bb7076a9-ec41-492b-b20a-1efb4e5e0c0b','2009-06-02 13:31:55'),('ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','OExamMonthly','每月考勤 - 每月考勤数据导出',1,0,1,0,4,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-31 19:18:55','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-31 19:18:55'),('afc1cf90-4e9e-4e2d-9798-4fb7cb7d4288','IEmpeval','档案管理 - 考评记录导入',0,0,0,0,1,'#201,1#201,2#221,0',0,NULL,'com.hr.profile.domain.Empeval','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16'),('b809eebf-b8a0-4a00-919f-57e330ad4da1','OEmpTransfer','档案管理 - 变动记录导出',1,0,0,0,1,'#101,1#101,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:36:19','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:36:19'),('bd2f89b7-8f46-4634-931f-90171cfe7731','IEmpContract','档案管理 - 人事合同导入',0,0,0,0,1,'#201,1#201,2#221,0',0,NULL,'com.hr.profile.domain.Empcontract','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16'),('c968e365-86f7-48bf-9a5a-5924ce18514e','IEmpSalaryConfig','薪资配置 - 薪资配置导入',0,0,1,0,2,'#201,1#201,2',0,NULL,'com.hr.compensation.domain.Empsalaryconfig','','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-14 18:21:39','855e2ab3-1a63-417a-a296-a0add3085500','2009-05-21 15:06:32'),('cca1a5ac-6140-421c-9eea-848c615b8306','OSalaryAcctitems','薪资帐套 - 薪资帐套导出',1,0,1,0,2,'#201,1#201,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-23 14:51:09','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-23 14:51:09'),('dab34b21-15cd-42b0-beb9-8c66d1f49a22','OEmployeeContract','合同管理 - 在职员工人事合同导出',1,0,1,0,1,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-27 15:01:51','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-27 15:01:51'),('ec934ce9-37bd-4c5d-b26c-91894ff427e9','IEmployeeBasic','员工管理 - 员工基础资料导入',0,0,1,0,1,'#101,1#101,2',0,NULL,'com.hr.profile.domain.Employee','','855e2ab3-1a63-417a-a296-a0add3085500','2008-03-14 18:21:39','855e2ab3-1a63-417a-a296-a0add3085500','2008-08-20 15:31:19'),('eee58bd1-176f-4853-9d50-c4581b1aff2e','OExaminShift','排班管理 - 排班信息导出',1,0,1,0,4,'#201,1#201,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-24 16:48:03','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-25 14:33:16'),('eee58bd1-176f-4853-9d50-c4581b1aff2f','IEmpSalaryBenefit','社保管理 - 社保配置初始化导入',0,0,1,0,2,'#201,1#201,2#221,0',0,NULL,'com.hr.compensation.domain.Empbenefit','','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-24 16:48:03','855e2ab3-1a63-417a-a296-a0add3085500','2009-02-25 14:33:16'),('efa905bc-c55a-4678-b21b-2ea94067f178','OEmpReward','档案管理 - 奖惩记录导出',1,0,0,0,1,'#101,1#101,2',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:54','855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:54'),('f0f570ec-4057-4ba8-ad13-a1f542828cbe','OEmpSalaryPay','薪资发放 - 每月薪资发放导出',1,0,1,0,2,'#201,1#201,2#221,0',0,NULL,'','','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16','855e2ab3-1a63-417a-a296-a0add3085500','2009-07-29 13:44:16');
/*!40000 ALTER TABLE `iodef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iomatch`
--

DROP TABLE IF EXISTS `iomatch`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `iomatch` (
  `iomatch_id` varchar(36) NOT NULL default '',
  `iomatch_io_id` varchar(36) NOT NULL default '',
  `iomatch_field_name` varchar(32) NOT NULL default '',
  `iomatch_field_desc` varchar(64) default NULL,
  `iomatch_required` int(10) unsigned NOT NULL default '0',
  `iomatch_length` int(10) unsigned NOT NULL default '0',
  `iomatch_valid_type` varchar(64) default NULL,
  `iomatch_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`iomatch_id`),
  KEY `FK_iomatch_io_id` (`iomatch_io_id`),
  CONSTRAINT `FK_iomatch_io_id` FOREIGN KEY (`iomatch_io_id`) REFERENCES `iodef` (`io_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `iomatch`
--

LOCK TABLES `iomatch` WRITE;
/*!40000 ALTER TABLE `iomatch` DISABLE KEYS */;
INSERT INTO `iomatch` VALUES ('02b3ce33-325a-4d88-94e9-ad644f3d8008','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empGender','性别',1,1,'Gender',3),('02b6cc58-1531-4f18-b440-8c2023207c18','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField02','E2',0,255,'String',44),('02be55bd-ce2c-4559-9297-f22b9f366066','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField07','E7',0,255,'String',49),('02ed4845-6090-420a-992a-7d7fe45b9c0a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField10','E10',0,255,'String',52),('03ad006e-1c84-4609-992b-007160b99a83','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empEmail','邮箱地址',0,64,'Email',4),('068f9e82-277b-4f48-8408-c4fedb7d79ee','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn3','A3',0,12,'BigDecimal',10),('072106d8-ae03-40ed-83cb-7d6cd22ee5f5','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn19','A19',0,12,'BigDecimal',20),('07585760-5d08-4ec7-ab93-43020dc2635d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField14','E14',0,32,'String',34),('07a9ed23-d987-4c84-86e3-0fc041901385','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBlood','血型',0,2,'Blood',17),('08f3d26b-f1f3-4af2-8ab2-8a14770ccd24','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn21','A21',0,12,'BigDecimal',22),('09612e39-bef8-4f01-91c7-4d3b841e7f9a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField06','E6',0,32,'String',26),('0a2c4e5f-2924-4f46-86c5-87a136397295','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField21','E21',0,32,'String',41),('0b172ee9-bc3a-47b8-a6a3-3b53ed0098a1','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMsn','MSN',0,64,'Msn',34),('0d86c147-91bb-4f63-9a59-efce5a8ccb3b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn31','A31',0,12,'BigDecimal',32),('10e874c8-8a66-4da8-adbc-539320d8d276','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn15','A15',0,12,'BigDecimal',16),('157ec9bd-2a23-4cde-9364-52dfd87f9f54','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn17','A17',0,12,'BigDecimal',24),('16251030-779d-4e09-b563-5719bb435b47','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn26','A26',0,12,'BigDecimal',33),('17a65005-d6f1-4560-9c49-7a07d41284dc','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn12','A12',0,12,'BigDecimal',13),('181a1546-9135-43f1-bd00-9f6bc6623a98','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn31','A31',0,12,'BigDecimal',38),('1b994606-57ca-4bef-a53f-1aee3e706144','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField20','E20',0,32,'String',40),('1c0848ab-525b-49f0-9186-db324ea855fd','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empPolitics','政治面貌',0,16,'String',18),('1cc84fe5-54fd-4c86-b7f8-5f037cd567c2','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empWorkPhone','工作电话',0,32,'Phone',5),('1dc1f385-85d7-469c-8956-97f18fc8ca29','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn5','A5',0,12,'BigDecimal',12),('213b733b-5335-4eda-902b-bb9f3586be9a','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn25','A25',0,12,'BigDecimal',32),('22fcb173-1640-4f56-96bd-3937f5100c91','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomePhone','家庭电话',0,32,'Phone',27),('23210ae5-8fa6-4823-84ad-b897a7beac88','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDistinctNo','员工编号',1,32,'String',0),('24cf9bac-5f96-4c3e-b935-afd8784e1211','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField07','E7',0,32,'String',27),('250451ee-bf44-4050-b356-495dc97b872a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMobile','手机',0,32,'Mobile',28),('29660108-b301-4aa3-9a8f-7ca88f1698c5','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSick01Hours','病假住院',0,3,'BigDecimal',10),('2acf538e-d614-4a7d-a8df-baca251c8993','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn6','A6',0,12,'BigDecimal',7),('2cf349fa-20cf-4184-9a7a-d3203972a261','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empType','用工形式',1,32,'String',9),('2d5d967b-c98d-4583-8657-d32185887414','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn23','A23',0,12,'BigDecimal',24),('2d7139fd-1587-4c65-9d3f-5418dca65830','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn23','A23',0,12,'BigDecimal',30),('2f08fad0-1af2-4e4d-bb4b-018dc75f4dbb','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn30','A30',0,12,'BigDecimal',37),('32711353-c85a-4000-9b8c-f60d90bf0b8b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn18','A18',0,12,'BigDecimal',25),('3335237e-8f66-4844-a612-a7e03edcdcca','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn22','A22',0,12,'BigDecimal',29),('3374d93d-3089-49f1-8dc4-72a27e208e2f','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn32','A32',0,12,'BigDecimal',33),('357bb2c2-059b-420c-be5d-74b9160c2e58','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn5','A5',0,12,'BigDecimal',6),('36505395-975e-41ec-a382-ed827a1dfeb2','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField11','E11',0,255,'String',53),('36904556-70a2-42de-8bfa-f30cda828794','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn2','A2',0,12,'BigDecimal',9),('38686ddb-319d-4736-b13e-588cd76b7a82','c968e365-86f7-48bf-9a5a-5924ce18514e','escEsacId','薪资帐套',1,64,'String',7),('3b008b44-4ac3-4484-990b-ec1cd4a698be','c968e365-86f7-48bf-9a5a-5924ce18514e','escBankAccountNo','银行帐户',1,32,'String',2),('3c85c44c-1751-4912-8ee0-84864029d734','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSpeciality','专业',0,64,'String',21),('3d04e1f8-a38f-4424-955f-a556adba27ed','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn7','A7',0,12,'BigDecimal',8),('3dd6f079-aa7d-4137-ae68-5bd72a302810','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn28','A28',0,12,'BigDecimal',35),('3fae7c72-82ea-4470-a53f-fff317564198','a24c4b3a-321e-440a-b739-8018f873440d','empDistinctNo','用户工号',0,36,'String',0),('402880e51e86a227011e86b5b04e0001','a24c4b3a-321e-440a-b739-8018f873440d','aodMemo','备注',0,255,'String',5),('402880e51f3eeb40011f4079baec002a','c968e365-86f7-48bf-9a5a-5924ce18514e','escBankName','银行开户行',0,64,'String',3),('402880e51f632c2e011f633ffd8f0001','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn39','A39',0,12,'BigDecimal',46),('402880e51f632c2e011f633ffd9f0002','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn48','A48',0,12,'BigDecimal',55),('402880e51f632c2e011f633ffd9f0004','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn37','A37',0,12,'BigDecimal',44),('402880e51f632c2e011f633ffd9f0005','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn33','A33',0,12,'BigDecimal',40),('402880e51f632c2e011f633ffd9f0006','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn40','A40',0,12,'BigDecimal',47),('402880e51f632c2e011f633ffd9f0007','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn43','A43',0,12,'BigDecimal',50),('402880e51f632c2e011f633ffd9f0008','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn45','A45',0,12,'BigDecimal',52),('402880e51f632c2e011f633ffd9f0009','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn36','A36',0,12,'BigDecimal',43),('402880e51f632c2e011f633ffd9f000a','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn47','A47',0,12,'BigDecimal',54),('402880e51f632c2e011f633ffd9f000b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn34','A34',0,12,'BigDecimal',41),('402880e51f632c2e011f633ffd9f000c','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn35','A35',0,12,'BigDecimal',42),('402880e51f632c2e011f633ffd9f000d','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn38','A38',0,12,'BigDecimal',45),('402880e51f632c2e011f633ffd9f000e','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn42','A42',0,12,'BigDecimal',49),('402880e51f632c2e011f633ffd9f000f','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn46','A46',0,12,'BigDecimal',53),('402880e51f632c2e011f633ffd9f0010','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn44','A44',0,12,'BigDecimal',51),('402880e51f632c2e011f633ffd9f0011','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn41','A41',0,12,'BigDecimal',48),('402880e51f632c2e011f6366e8640034','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn40','A40',0,12,'BigDecimal',41),('402880e51f632c2e011f6366e8730035','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn34','A34',0,12,'BigDecimal',35),('402880e51f632c2e011f6366e8730036','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn38','A38',0,12,'BigDecimal',39),('402880e51f632c2e011f6366e8730037','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn35','A35',0,12,'BigDecimal',36),('402880e51f632c2e011f6366e8730038','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn41','A41',0,12,'BigDecimal',42),('402880e51f632c2e011f6366e8730039','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn48','A48',0,12,'BigDecimal',49),('402880e51f632c2e011f6366e883003a','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn47','A47',0,12,'BigDecimal',48),('402880e51f632c2e011f6366e883003b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn43','A43',0,12,'BigDecimal',44),('402880e51f632c2e011f6366e883003c','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn42','A42',0,12,'BigDecimal',43),('402880e51f632c2e011f6366e883003d','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn37','A37',0,12,'BigDecimal',38),('402880e51f632c2e011f6366e883003e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn36','A36',0,12,'BigDecimal',37),('402880e51f632c2e011f6366e883003f','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn33','A33',0,12,'BigDecimal',34),('402880e51f632c2e011f6366e8830040','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn39','A39',0,12,'BigDecimal',40),('402880e51f632c2e011f6366e8830041','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn44','A44',0,12,'BigDecimal',45),('402880e51f632c2e011f6366e8830042','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn45','A45',0,12,'BigDecimal',46),('402880e51f632c2e011f6366e8830043','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn46','A46',0,12,'BigDecimal',47),('402880e51fa5e6ed011fa77828f40003','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfEmpUId','员工编号',1,36,'String',0),('402880e51fa5e6ed011fa77bf7700004','eee58bd1-176f-4853-9d50-c4581b1aff2f','memo','备注',0,255,'String',9),('402880e51fa5e6ed011fa77bf7700005','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfPensionNo','养老保险号',0,32,'String',3),('402880e51fa5e6ed011fa77bf7700006','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfPensionAmount','社保基数',0,12,'BigDecimal',6),('402880e51fa5e6ed011fa77bf7700007','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfHousingNo','公积金号',0,32,'String',4),('402880e51fa5e6ed011fa77bf7700008','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfEmpBeneType','社保种类',1,36,'String',1),('402880e51fa5e6ed011fa77bf7700009','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfInsuranceAmount','综合保险基数',0,12,'BigDecimal',8),('402880e51fa5e6ed011fa77bf770000a','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfHousingAmount','公积金基数',0,12,'BigDecimal',7),('402880e51fa5e6ed011fa77bf770000b','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfStartMonth','生效年月',1,10,'String',2),('402880e51fa5e6ed011fa77bf770000c','eee58bd1-176f-4853-9d50-c4581b1aff2f','ebfMedicalNo','医疗保险号',0,32,'String',5),('402880e51fabeb76011fac3071e00005','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfEmpUId','员工编号',0,36,'String',0),('402880e51fb19e87011fb1ac81730001','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','empCardId','身份证号码',0,30,'String',1),('402880e51fb19e87011fb1b2cff40002','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','memo','备注',0,255,'String',9),('402880e51fb19e87011fb1b2cff40003','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfInsuranceAmount','综合保险基数',0,12,'BigDecimal',8),('402880e51fb19e87011fb1b2cff40004','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfPensionNo','养老保险号',0,32,'String',2),('402880e51fb19e87011fb1b2cff40005','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfStartMonth','生效年月',1,10,'String',5),('402880e51fb19e87011fb1b2cff40006','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfHousingNo','公积金号',0,32,'String',3),('402880e51fb19e87011fb1b2d0030007','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfPensionAmount','社保基数',0,12,'BigDecimal',6),('402880e51fb19e87011fb1b2d0130008','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfMedicalNo','医疗保险号',0,32,'String',4),('402880e51fb19e87011fb1b2d0130009','4ff5dd9d-466d-4a70-b5f8-b531a27bdd81','ebfHousingAmount','公积金基数',0,12,'BigDecimal',7),('402880e52160e2ff012161fe1e750011','c968e365-86f7-48bf-9a5a-5924ce18514e','escCostCenter','成本中心',0,32,'String',4),('402880e5219e80f401219f6688f90001','a24c4b3a-321e-440a-b739-8018f873440d','empShiftNo','考勤卡号',0,32,'String',1),('402880e5219e80f401219f6bac380002','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime8','打卡时间8',0,20,'String',12),('402880e5219e80f401219f6bac380003','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime5','打卡时间5',0,20,'String',9),('402880e5219e80f401219f6bac380004','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime7','打卡时间7',0,20,'String',11),('402880e5219e80f401219f6bac380005','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime6','打卡时间6',0,20,'String',10),('402880e5219e80f401219f6bac380006','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime3','打卡时间3',0,20,'String',7),('402880e5219e80f401219f6bac380007','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime2','打卡时间2',0,20,'String',6),('402880e5219e80f401219f6bac380008','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime4','打卡时间4',0,20,'String',8),('402880e6225300e3012253fa732a0006','3afc8569-463b-4fcd-8f52-9356af2306a2','empDistinctNo','员工编号',1,36,'String',0),('402880e6225300e3012254149ea0000f','3afc8569-463b-4fcd-8f52-9356af2306a2','28','28',0,36,'String',36),('402880e6225300e3012254149ea00010','3afc8569-463b-4fcd-8f52-9356af2306a2','27','27',0,36,'String',35),('402880e6225300e3012254149ea00011','3afc8569-463b-4fcd-8f52-9356af2306a2','21','21',0,36,'String',29),('402880e6225300e3012254149ea00012','3afc8569-463b-4fcd-8f52-9356af2306a2','20','20',0,36,'String',28),('402880e6225300e3012254149ea00013','3afc8569-463b-4fcd-8f52-9356af2306a2','8','8',0,36,'String',16),('402880e6225300e3012254149ea00014','3afc8569-463b-4fcd-8f52-9356af2306a2','11','11',0,36,'String',19),('402880e6225300e3012254149ea00015','3afc8569-463b-4fcd-8f52-9356af2306a2','24','24',0,36,'String',32),('402880e6225300e3012254149ea00016','3afc8569-463b-4fcd-8f52-9356af2306a2','19','19',0,36,'String',27),('402880e6225300e3012254149ea00017','3afc8569-463b-4fcd-8f52-9356af2306a2','25','25',0,36,'String',33),('402880e6225300e3012254149eb00018','3afc8569-463b-4fcd-8f52-9356af2306a2','22','22',0,36,'String',30),('402880e6225300e3012254149eb00019','3afc8569-463b-4fcd-8f52-9356af2306a2','18','18',0,36,'String',26),('402880e6225300e3012254149eb0001a','3afc8569-463b-4fcd-8f52-9356af2306a2','29','29',0,36,'String',37),('402880e6225300e3012254149eb0001b','3afc8569-463b-4fcd-8f52-9356af2306a2','26','26',0,36,'String',34),('402880e6225300e3012254149eb0001c','3afc8569-463b-4fcd-8f52-9356af2306a2','9','9',0,36,'String',17),('402880e6225300e3012254149eb0001d','3afc8569-463b-4fcd-8f52-9356af2306a2','7','7',0,36,'String',15),('402880e6225300e3012254149eb0001e','3afc8569-463b-4fcd-8f52-9356af2306a2','17','17',0,36,'String',25),('402880e6225300e3012254149eb0001f','3afc8569-463b-4fcd-8f52-9356af2306a2','4','4',0,36,'String',12),('402880e6225300e3012254149eb00020','3afc8569-463b-4fcd-8f52-9356af2306a2','6','6',0,36,'String',14),('402880e6225300e3012254149eb00021','3afc8569-463b-4fcd-8f52-9356af2306a2','2','2',0,36,'String',10),('402880e6225300e3012254149eb00022','3afc8569-463b-4fcd-8f52-9356af2306a2','16','16',0,36,'String',24),('402880e6225300e3012254149eb00023','3afc8569-463b-4fcd-8f52-9356af2306a2','3','3',0,36,'String',11),('402880e6225300e3012254149eb00024','3afc8569-463b-4fcd-8f52-9356af2306a2','12','12',0,36,'String',20),('402880e6225300e3012254149eb00025','3afc8569-463b-4fcd-8f52-9356af2306a2','31','31',0,36,'String',39),('402880e6225300e3012254149eb00026','3afc8569-463b-4fcd-8f52-9356af2306a2','23','23',0,36,'String',31),('402880e6225300e3012254149eb00027','3afc8569-463b-4fcd-8f52-9356af2306a2','14','14',0,36,'String',22),('402880e6225300e3012254149eb00028','3afc8569-463b-4fcd-8f52-9356af2306a2','30','30',0,36,'String',38),('402880e6225300e3012254149eb00029','3afc8569-463b-4fcd-8f52-9356af2306a2','5','5',0,36,'String',13),('402880e6225300e3012254149eb0002a','3afc8569-463b-4fcd-8f52-9356af2306a2','15','15',0,36,'String',23),('402880e6225300e3012254149eb0002b','3afc8569-463b-4fcd-8f52-9356af2306a2','13','13',0,36,'String',21),('402880e6225300e3012254149eb0002c','3afc8569-463b-4fcd-8f52-9356af2306a2','10','10',0,36,'String',18),('402880e6225300e3012254149eb0002d','3afc8569-463b-4fcd-8f52-9356af2306a2','1','1',0,36,'String',9),('402880e81c984e73011c98a8019f007a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEmpId','用户工号',1,36,'String',0),('402880e81c984e73011c98e13ee1007c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOffDuty','缺勤',1,3,'BigDecimal',3),('402880e81c984e73011c98e13ee1007d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmDutyHours','全勤',1,3,'BigDecimal',1),('402880e81c984e73011c98e13ee1007e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOnDuty','出勤',1,3,'BigDecimal',2),('402880e81c984e73011c98e4934c0081','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveCasualHours','事假',0,3,'BigDecimal',8),('402880e81c984e73011c98e4934c0082','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveWeddingHours','婚假',0,3,'BigDecimal',11),('402880e81c984e73011c98e4934c0084','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTravelHours','出差',0,3,'BigDecimal',13),('402880e81c984e73011c98e4934c0085','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveSickHours','病假',0,3,'BigDecimal',9),('402880e81c984e73011c98e4934c0086','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOtherHours','其他请假',0,3,'BigDecimal',16),('402880e81c984e73011c98e4934c0087','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveAnnualHours','年假',0,3,'BigDecimal',7),('402880e81c984e73011c98e4934c0088','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveMaternityHours','产假',0,3,'BigDecimal',12),('402880e81c984e73011c98e66d3c008a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmAbsentTimes','旷工',0,3,'BigDecimal',6),('402880e81c984e73011c98eb4808008c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtNormalHours','日常加班',0,3,'BigDecimal',17),('402880e81c984e73011c98eb4808008d','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtWeekendHours','周末加班',0,3,'BigDecimal',18),('402880e81c984e73011c98eb4808008f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmComments','备注',0,128,'String',20),('402880e81c984e73011c98eb48080090','0972da74-99e1-43fd-86f8-8a25228b74fe','attmOtHolidayHours','节假日加班',0,3,'BigDecimal',19),('402880e81e390f5b011e395d48dc000b','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLateTimes','迟到',0,3,'BigDecimal',4),('402880e81e390f5b011e395d48dc000c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmEarlyLeave','早退',0,3,'BigDecimal',5),('402880eb1ddb7de1011de0c4e8c60001','a24c4b3a-321e-440a-b739-8018f873440d','aodTtdMachineNo','考勤机号',0,20,'String',4),('412501f8-4970-434f-81df-cd9322182494','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn9','A9',0,12,'BigDecimal',16),('417c3b33-843d-41d6-be76-89484d5da03c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField12','E12',0,32,'String',32),('418a8d38-036f-41e1-a94c-cbbc782a322e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField23','E23',0,32,'String',43),('41e69da2-5d38-4f29-97ca-1e146c39c7e4','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empComments','备注',0,255,'String',42),('45d0147c-2909-4142-8c86-8eff119c0d90','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCurrZip','当前地址邮编',0,16,'Zip',30),('466b8355-8081-4b8e-974c-3a7d69624ae7','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empIdentificationNo','证件编号',1,32,'String',14),('4a46f85a-b06d-4341-a9bc-628581f61239','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn7','A7',0,12,'BigDecimal',14),('4be362ce-517f-4175-8af0-4317f7a058cf','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField05','E5',0,32,'String',25),('4c0a4630-82aa-49ee-80b7-1dddb0ddb91e','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empGroup','工作小组',0,64,'String',15),('4d36fba2-6bb8-4966-8232-e5407b14f294','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField19','E19',0,32,'String',39),('4e901bb9-21c7-49cd-8839-6157b24c2c52','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn9','A9',0,12,'BigDecimal',10),('51bbc0bb-a809-48de-a7ac-381cf60a1f13','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empShiftType','考勤方式',0,64,'String',42),('51e7cb50-084e-4c3f-8f68-f316a2866929','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empIdentificationType','证件种类',1,32,'String',13),('5444b789-41ac-402c-afce-a1d629c577b8','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBuNo','业务单元',0,64,'String',39),('55991ccd-976b-42ef-833e-f0ac2cc63dd8','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBenefitType','社保种类',0,64,'String',26),('565e3652-5a13-4baf-9af9-e9928c1e3ca9','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField13','E13',0,255,'String',55),('570c6690-daf4-4c0b-b7c0-2a3c028fe5df','c968e365-86f7-48bf-9a5a-5924ce18514e','escJobgrade','薪资级别',1,16,'String',1),('593fb4bb-8301-4e8e-bbc2-cd2da547db52','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn13','A13',0,12,'BigDecimal',14),('5a768389-3a39-4218-9b2c-b7c743387c5b','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSchool','毕业院校',0,64,'String',20),('5a9bad9d-0e53-4434-abb3-c1225287fa7c','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn1','A1',0,12,'BigDecimal',2),('5e684cd4-30c8-4723-8a9c-1add531b3ead','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn12','A12',0,12,'BigDecimal',19),('62aed3ac-d139-46bb-ac2f-7c27c2770146','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn20','A20',0,12,'BigDecimal',21),('638db1fb-b8ad-4c77-8950-401f9036003c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empSupNo','经理',0,32,'String',33),('64569ef3-6a59-48e2-b3db-0894d06995fb','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn18','A18',0,12,'BigDecimal',19),('64ca7c87-3710-425b-b8b6-7e519ce4ab81','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empResidenceLoc','户口所在地',0,128,'String',24),('65fc71a8-d29c-4c10-8b46-942f7036b602','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn25','A25',0,12,'BigDecimal',26),('66c4a26b-035c-4bd9-9ba5-fae3f7231582','a24c4b3a-321e-440a-b739-8018f873440d','aodCardTime','打卡时间',0,20,'String',3),('67729323-b04f-4a91-af6f-70e3792405a6','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empUrgentContact','紧急联系人',0,64,'String',37),('69d73999-74de-4b0f-8fd7-d17f7e13b98e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn16','A16',0,12,'BigDecimal',17),('6b736b9f-ccf4-4fb2-8763-16937feeb616','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField09','E9',0,255,'String',51),('72992e9a-f651-4f6a-b9b3-f4036615c387','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empProfileLoc','档案所在地',0,128,'String',25),('72d45316-6466-4ebc-b530-915ca48ea32c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empUrgentConMethod','紧急联系方式',0,128,'String',38),('755f2dbc-5604-47e8-b701-2377926b38aa','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBranchNo','分公司',0,36,'String',11),('756354a3-61e4-447a-93c7-a3c8b291668c','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empLocationNo','工作地点',1,64,'String',12),('757847a1-a70b-47b2-8986-90e232cd0cee','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn24','A24',0,12,'BigDecimal',25),('78bf87e5-ce04-4d38-b167-e7848812bb3e','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn30','A30',0,12,'BigDecimal',31),('7b47dfed-8897-4d2d-9ea2-4878ff4ee727','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn11','A11',0,12,'BigDecimal',12),('7be3e9bf-06fc-4dfe-a470-d5930da46774','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn24','A24',0,12,'BigDecimal',31),('82d120fb-cd8d-4054-8031-55cc08db0f63','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn14','A14',0,12,'BigDecimal',15),('845faf52-2621-4225-8e71-edce307bf894','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn4','A4',0,12,'BigDecimal',5),('85d43cba-bc97-427b-b63a-4d8588d0e2cd','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn3','A3',0,12,'BigDecimal',4),('88e4297b-749c-4ed1-9a0a-ed85b76ba2c1','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn6','A6',0,12,'BigDecimal',13),('8a560964-a7f5-45b5-acd9-e241256fb0e6','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomeZip','家庭地址邮编',0,16,'Zip',32),('8b37f16d-3cb4-4ed4-88b3-ae1cc10a9f48','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDiploma','学历',0,16,'String',19),('8c63a7bd-d816-4b0a-a4ba-796a7f4d2120','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn22','A22',0,12,'BigDecimal',23),('8cc517e3-4796-4853-a433-8fd08ea6f3fe','c968e365-86f7-48bf-9a5a-5924ce18514e','id','员工编号',1,32,'String',0),('8fca8846-e60c-4874-a4d7-d38d60170538','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField06','E6',0,255,'String',48),('91233bed-ffde-4a03-9c57-c073511978b8','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField16','E16',0,255,'String',58),('934adf2b-c235-43a1-9a08-7c38fadd9012','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empJobTitle','工作岗位',1,64,'String',6),('936201d9-db16-4fd7-8f1e-633ab6258cb1','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn4','A4',0,12,'BigDecimal',11),('938c500d-1374-43fa-afd2-eace967c5436','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField02','E2',0,32,'String',22),('9418e166-c7cf-4369-aab2-c0550fa6238e','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField17','E17',0,32,'String',37),('98001506-d53f-4651-8a6e-10fcb235894d','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn28','A28',0,12,'BigDecimal',29),('99bb07e1-27e7-4474-9b59-8e7fe1415e04','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn1','A1',0,12,'BigDecimal',8),('9a769524-4a27-4cbe-add0-cf2e26002638','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField04','E4',0,32,'String',24),('9ecf076b-6c24-43db-a9da-58f6c7d4550b','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn21','A21',0,12,'BigDecimal',28),('a0b035b1-d2e6-4b44-9690-16127f356d40','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empEnglishName','英文姓名',0,80,'String',2),('a152d880-f058-4ea3-8c53-515422422d97','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveTiaoXiuHours','调休',0,3,'BigDecimal',15),('a1a46466-6de1-4685-b238-55a9b6973094','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empMarital','婚否',0,2,'Marry',16),('a37f808e-9f53-4c9b-b076-2aa165f73203','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField10','E10',0,32,'String',30),('a6278a73-403b-4cad-a8c8-220e1d03fda0','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empWorkDate','参加工作日期',1,32,'Date',7),('a822eac9-60d2-41d3-b1ae-3b509d843252','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField03','E3',0,255,'String',45),('a9261dcb-a36a-4b44-b8d8-d40ca5e7861c','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField24','E24',0,32,'String',44),('aa668880-1597-484a-978b-dee14c607985','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField01','E1',0,255,'String',43),('ab14e335-cb3e-49de-b73b-7f624a387b79','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn29','A29',0,12,'BigDecimal',36),('ab7e09a8-7826-4fa9-b9b8-85f7d782ec9b','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn26','A26',0,12,'BigDecimal',27),('ad543859-00b3-4bfe-b06b-391a9934905e','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn10','A10',0,12,'BigDecimal',17),('af057ba8-5af9-438a-b8f0-67c95e185c70','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn15','A15',0,12,'BigDecimal',22),('af43934b-3542-443b-975c-6a6dfb2a59ac','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCurrAddr','当前住址',0,128,'String',29),('b372dc86-9b95-4966-90a9-275878a10365','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empJoinDate','入职日期',1,32,'Date',8),('ba9040c2-bcc2-49f9-8dba-0c19c5173085','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField09','E9',0,32,'String',29),('bbc916f8-ba8c-4249-b734-96ae252fb739','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField22','E22',0,32,'String',42),('bf0573ac-4092-4d1d-b6e9-be999ac18d77','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empHomeAddr','家庭住址',0,128,'String',31),('c0ffe76c-8034-485e-995f-17cde25d8610','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn16','A16',0,12,'BigDecimal',23),('c1923eb2-eae5-43da-b5dd-598013b44a10','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn27','A27',0,12,'BigDecimal',34),('c1cede75-2579-447d-a42b-2c0f6ab6a994','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empTerminateDate','离职日期',0,10,'Date',40),('c5e42b72-bcd4-4b35-b359-c612b2a54b4a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField16','E16',0,32,'String',36),('c6f374cf-9301-42a4-a28f-c4ea38f663ec','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField05','E5',0,255,'String',47),('c7f94ed2-f9c8-4b2f-9589-d080ae5b38a3','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn19','A19',0,12,'BigDecimal',26),('c832f69b-559d-443a-90e8-097eb2bcfdf2','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn14','A14',0,12,'BigDecimal',21),('c9015ff4-5151-4fac-8030-c83491f796ee','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField14','E14',0,255,'String',56),('cb71bf4c-c922-4213-a5d9-856de6b6332a','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empName','员工姓名',1,64,'String',1),('cc1438a0-1dc7-4d45-b006-fcefc795b8a3','0972da74-99e1-43fd-86f8-8a25228b74fe','attmLeaveOuterHours','因公外出',0,3,'BigDecimal',14),('ccc18665-9ed1-428c-af76-9af38978ee74','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn20','A20',0,12,'BigDecimal',27),('cde3280c-9f7e-412c-8195-90e4452f4718','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField04','E4',0,255,'String',46),('d09f1aa0-568c-41ea-8abc-52066547cb9a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField01','E1',0,32,'String',21),('d0e106bd-0c6b-460c-96a3-fdd0fe9775d8','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn8','A8',0,12,'BigDecimal',9),('d6b46295-0bee-4861-bf94-14a64c27bfce','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empCityNo','籍贯',0,16,'String',22),('d739ff2e-7acb-439d-be39-b0d573675b9f','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField13','E13',0,32,'String',33),('d8add10f-8e11-475b-b7ad-9fcc471d92cc','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empDeptNo','部门',1,64,'String',10),('da978d98-6d3f-45d4-8eb3-0df5a5acdb36','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn2','A2',0,12,'BigDecimal',3),('dcf84186-24ca-4d44-8077-89a43401f787','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn17','A17',0,12,'BigDecimal',18),('dd355d14-6955-4f47-ae02-fd2f9084294d','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField15','E15',0,255,'String',57),('de4dc113-50aa-4db2-93a8-78c5cf87fdf0','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn32','A32',0,12,'BigDecimal',39),('e13a8ac6-d36a-4554-b8fa-5df6883b68a2','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField03','E3',0,32,'String',23),('e25c2427-328d-42f6-b9c7-d6ca83d11fa8','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField12','E12',0,255,'String',54),('e528136b-1884-4abb-92b2-fb582ef15db9','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField08','E8',0,32,'String',28),('e63e680a-f455-4da3-b519-b418810f3e32','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField18','E18',0,32,'String',38),('e83558c4-12b8-4f7a-bc10-8543bf9a964a','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField11','E11',0,32,'String',31),('e8607fe3-68cd-4542-ae43-1daef53ec56f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empBirthDate','出生日期',1,10,'Date',2),('ec540ab1-0430-4c1e-b387-34b15535f3f6','a24c4b3a-321e-440a-b739-8018f873440d','aodAttdDate','考勤日期',1,19,'Date',2),('ed21f56b-b9cc-4e93-9944-cb227c7d0854','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn8','A8',0,12,'BigDecimal',15),('eef90782-298a-47c5-a370-16fe34cd6e7f','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empConfirmDate','转正日期',0,10,'Date',35),('f1e8ff80-1317-4d1f-91d9-247b8127e804','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn11','A11',0,12,'BigDecimal',18),('f570a609-33b9-4284-9d20-b97ce9a50cd2','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','empDistinctNo','员工编号',1,32,'String',1),('f6785947-4b8f-4a00-a3a7-94681150da99','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn29','A29',0,12,'BigDecimal',30),('f68d3178-2a64-4aae-b997-3d25f3b698ff','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn27','A27',0,12,'BigDecimal',28),('f698e726-f6ca-4eab-af8f-7526ed6147c7','65f4f432-5f5c-4a3d-9ecf-c76efa7fcbe3','escColumn10','A10',0,12,'BigDecimal',11),('f7087015-c131-45e5-9cd6-eb3026cc54ac','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empStatus','状态',1,32,'String',11),('f755c96d-432e-403e-aebc-3950af4c2a71','ec934ce9-37bd-4c5d-b26c-91894ff427e9','empNation','民族',0,16,'String',23),('faf20a0e-aa42-4790-80b6-868b4c60b524','ec934ce9-37bd-4c5d-b26c-91894ff427e9','eadlField08','E8',0,255,'String',50),('feb7474f-b9b7-46c1-b305-760676b82f0d','c968e365-86f7-48bf-9a5a-5924ce18514e','escColumn13','A13',0,12,'BigDecimal',20),('ff85b896-5896-4824-84f1-43915d2101ce','0972da74-99e1-43fd-86f8-8a25228b74fe','attmField15','E15',0,32,'String',35);
/*!40000 ALTER TABLE `iomatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobgrade`
--

DROP TABLE IF EXISTS `jobgrade`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jobgrade` (
  `jobgrade_id` varchar(36) NOT NULL default '',
  `jobgrade_no` varchar(16) NOT NULL,
  `jobgrade_level` int(10) unsigned NOT NULL default '0',
  `jobgrade_mrp` decimal(11,2) NOT NULL default '0.00',
  `jobgrade_name` varchar(64) NOT NULL default '',
  `jobgrade_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`jobgrade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jobgrade`
--

LOCK TABLES `jobgrade` WRITE;
/*!40000 ALTER TABLE `jobgrade` DISABLE KEYS */;
INSERT INTO `jobgrade` VALUES ('23904035-9fcb-49d1-8ce8-91d56c85c0fc','T4',4,'111010.01','技术总监',8),('5ad3eb21-58bd-4469-811a-ed26659bcf1c','S3',3,'15601.10','销售经理',10),('5cf62979-cc35-4490-ab84-e9844074884a','A1',1,'12909.90','普通员工',1),('66fe7c7c-884b-480d-8217-fc2d2a0799b0','T3',3,'12050.05','项目经理',7),('97987e16-e8af-4615-ad31-0dea92a11fc9','S4',4,'170696.69','销售总监',11),('9da478a5-b887-40b6-82c6-2886ec44a7d8','A3',3,'12868.86','经理',3),('bc56b557-05a9-4d6b-9e2a-0eb4512b844e','A5',5,'134422.22','总裁',5),('bc7922c2-bbbc-4511-8a79-2856adf23b8f','A2',2,'16752.52','主管',2),('c2b1bf18-2b82-45dd-812c-d8e67da5619f','A4',4,'10752.52','总监',4),('d2cf8346-c724-4050-b9ed-06c1d4974949','S2',2,'10969.96','销售主管',9),('ed181227-c3e5-483c-9c42-5b91c8534559','T2',2,'11969.96','主任工程师',6);
/*!40000 ALTER TABLE `jobgrade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jobtitle`
--

DROP TABLE IF EXISTS `jobtitle`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `jobtitle` (
  `jobtitle_no` varchar(36) NOT NULL default '',
  `jobtitle_name` varchar(64) NOT NULL default '',
  `jobtitle_desc` varchar(128) default NULL,
  `jobtitle_status` int(10) unsigned NOT NULL default '1',
  `jobtitle_default_acct` varchar(36) default NULL,
  `jobtitle_default_jg` varchar(36) default NULL,
  `jobtitle_need_gm_approve` int(10) unsigned NOT NULL default '0',
  `jobtitle_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`jobtitle_no`),
  KEY `FK_jobtitle_default_acct` (`jobtitle_default_acct`),
  KEY `FK_jobtitle_default_jg` (`jobtitle_default_jg`),
  CONSTRAINT `FK_jobtitle_default_acct` FOREIGN KEY (`jobtitle_default_acct`) REFERENCES `empsalaryacct` (`esac_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_jobtitle_default_jg` FOREIGN KEY (`jobtitle_default_jg`) REFERENCES `jobgrade` (`jobgrade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `jobtitle`
--

LOCK TABLES `jobtitle` WRITE;
/*!40000 ALTER TABLE `jobtitle` DISABLE KEYS */;
/*!40000 ALTER TABLE `jobtitle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leavebalance`
--

DROP TABLE IF EXISTS `leavebalance`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `leavebalance` (
  `lb_id` varchar(36) NOT NULL default '',
  `lb_emp_no` varchar(36) NOT NULL default '',
  `lb_year` varchar(4) NOT NULL default '',
  `lb_leave_type` int(10) unsigned NOT NULL default '0',
  `lb_bal_forward_day` decimal(6,2) default NULL,
  `lb_bal_forward_hour` decimal(7,1) default NULL,
  `lb_bal_end_date` date default NULL,
  `lb_days_of_year` decimal(6,2) default NULL,
  `lb_hours_of_year` decimal(7,1) default NULL,
  `lb_days_for_release` decimal(6,2) default NULL,
  `lb_hours_for_release` decimal(7,1) default NULL,
  `lb_release_start_date` date default NULL,
  `lb_other_days` decimal(6,2) default NULL,
  `lb_other_hours` decimal(7,1) default NULL,
  `lb_status` int(10) unsigned NOT NULL default '0',
  `lb_comments` varchar(128) default NULL,
  PRIMARY KEY  (`lb_id`),
  KEY `FK_lb_emp_no` (`lb_emp_no`),
  CONSTRAINT `FK_lb_emp_no` FOREIGN KEY (`lb_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `leavebalance`
--

LOCK TABLES `leavebalance` WRITE;
/*!40000 ALTER TABLE `leavebalance` DISABLE KEYS */;
/*!40000 ALTER TABLE `leavebalance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leavecalendar`
--

DROP TABLE IF EXISTS `leavecalendar`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `leavecalendar` (
  `lc_id` varchar(36) NOT NULL default '',
  `lc_date` date NOT NULL default '0000-00-00',
  `lc_sign` int(10) unsigned NOT NULL default '0',
  `lc_location_no` varchar(36) default NULL,
  `lc_date_desc` varchar(64) default NULL,
  PRIMARY KEY  (`lc_id`),
  UNIQUE KEY `date_no` (`lc_date`,`lc_location_no`),
  KEY `FK_lc_location_no` (`lc_location_no`),
  CONSTRAINT `FK_lc_location_no` FOREIGN KEY (`lc_location_no`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `leavecalendar`
--

LOCK TABLES `leavecalendar` WRITE;
/*!40000 ALTER TABLE `leavecalendar` DISABLE KEYS */;
INSERT INTO `leavecalendar` VALUES ('402880e71e9aedf5011e9b16cd650001','2009-01-02',0,NULL,'元旦'),('402880e71e9aedf5011e9b16eee10002','2009-01-01',0,NULL,'元旦'),('402880e71e9aedf5011e9b1742340003','2009-01-04',1,NULL,'元旦周日调为正常工作日'),('402880e71e9aedf5011e9b50d3e10004','2009-01-26',0,NULL,'春节初一'),('402880e71e9aedf5011e9b50fea30005','2009-01-27',0,NULL,'春节初二'),('402880e71e9aedf5011e9b5120f20006','2009-01-28',0,NULL,'春节初三'),('402880e71e9aedf5011e9b5140330007','2009-01-29',0,NULL,'春节初四'),('402880e71e9aedf5011e9b516d760008','2009-01-30',0,NULL,'春节初五'),('402880e71e9aedf5011e9b52b1210009','2009-01-24',1,NULL,'春节前的周日调整为正常工作日'),('402880e71e9aedf5011e9b530899000a','2009-02-01',1,NULL,'春节后的周日调整为正常工作日'),('402880e71e9aedf5011e9b58bd87000b','2009-04-06',0,NULL,'清明节'),('402880e71e9aedf5011e9b59c897000c','2009-05-01',0,NULL,'劳动节'),('402880e71e9aedf5011e9b5a4dd4000d','2009-05-28',0,NULL,'端午节'),('402880e71e9aedf5011e9b5a7864000e','2009-05-29',0,NULL,'端午节'),('402880e71e9aedf5011e9b5affd2000f','2009-05-31',1,NULL,'端午节后的周日改完正常工作日'),('402880e71e9aedf5011e9b5ca2d20010','2009-10-01',0,NULL,'国庆节'),('402880e71e9aedf5011e9b5cc23b0011','2009-10-02',0,NULL,'国庆节'),('402880e71e9aedf5011e9b5d7cf20012','2009-09-27',1,NULL,'国庆节前的周日改正常工作日'),('402880e71e9aedf5011e9b5dc28a0013','2009-10-10',1,NULL,'国庆节后的周六改正常工作日'),('402880e71e9aedf5011e9b5f18840014','2009-10-05',0,NULL,'国庆节'),('402880e71e9aedf5011e9b5f3f660015','2009-10-06',0,NULL,'国庆节'),('402880e71e9aedf5011e9b5f594c0016','2009-10-07',0,NULL,'国庆节'),('402880e71e9aedf5011e9b5f9ca80017','2009-10-08',0,NULL,'国庆节'),('402880ea25e24a8c0125e28674ba003c','2010-01-01',2,NULL,'元旦'),('402880ea25e24a8c0125e29848b1003d','2010-02-13',2,NULL,'除夕'),('402880ea25e24a8c0125e2988387003e','2010-02-14',2,NULL,'初一'),('402880ea25e24a8c0125e298ad56003f','2010-02-15',2,NULL,'初二'),('402880ea25e24a8c0125e298fb180040','2010-02-16',0,NULL,'初三'),('402880ea25e24a8c0125e2992e5d0041','2010-02-17',0,NULL,'初四'),('402880ea25e24a8c0125e29961740042','2010-02-18',0,NULL,'初五'),('402880ea25e24a8c0125e2998a680043','2010-02-19',0,NULL,'初六'),('402880ea25e24a8c0125e29a7bdc0044','2010-02-20',1,NULL,'春节2月20日（星期六）、21日（星期日）上班'),('402880ea25e24a8c0125e29ac6530045','2010-02-21',1,NULL,'春节2月20日（星期六）、21日（星期日）上班'),('402880ea25e24a8c0125e29dfc2a0046','2010-04-05',2,NULL,'清明节'),('402880ea25e24a8c0125e2a7c5e60047','2010-05-01',2,NULL,'劳动节'),('402880ea25e24a8c0125e2a87d6f0048','2010-05-03',0,NULL,'劳动节调假'),('402880ea25e24a8c0125e2a9c6a80049','2010-06-12',1,NULL,'端午节6月12日（星期六）、13日（星期日）上班'),('402880ea25e24a8c0125e2aa3bb9004a','2010-06-13',1,NULL,'端午节6月12日（星期六）、13日（星期日）上班'),('402880ea25e24a8c0125e2aacc21004b','2010-06-14',0,NULL,'端午节调假'),('402880ea25e24a8c0125e2ab4132004c','2010-06-15',0,NULL,'端午节调假'),('402880ea25e24a8c0125e2aba78d004d','2010-06-16',2,NULL,'端午节'),('402880ea25e24a8c0125e2ac7cb0004e','2010-09-19',1,NULL,'中秋节9月19日（星期日）、25日（星期六）上班'),('402880ea25e24a8c0125e2acdd3f004f','2010-09-25',1,NULL,'中秋节9月19日（星期日）、25日（星期六）上班'),('402880ea25e24a8c0125e2adeac70050','2010-09-22',2,NULL,'中秋节'),('402880ea25e24a8c0125e2ae3c320051','2010-09-23',0,NULL,'中秋节调假'),('402880ea25e24a8c0125e2ae9e860052','2010-09-24',0,NULL,'中秋节调假'),('402880ea25e24a8c0125e2af35480053','2010-09-26',1,NULL,'国庆节9月26日（星期日）、10月9日（星期六）上班'),('402880ea25e24a8c0125e2af67450054','2010-10-09',1,NULL,'国庆节9月26日（星期日）、10月9日（星期六）上班'),('402880ea25e24a8c0125e2b00f8b0055','2010-10-01',2,NULL,'国庆节'),('402880ea25e24a8c0125e2b044660056','2010-10-02',2,NULL,'国庆节'),('402880ea25e24a8c0125e2b066a40057','2010-10-03',2,NULL,'国庆节'),('402880ea25e24a8c0125e2b0a0320058','2010-10-04',0,NULL,'国庆节调假'),('402880ea25e24a8c0125e2b0c86b0059','2010-10-05',0,NULL,'国庆节调假'),('402880ea25e24a8c0125e2b10d83005a','2010-10-06',0,NULL,'国庆节调假'),('402880ea25e24a8c0125e2b14452005b','2010-10-07',0,NULL,'国庆节调假');
/*!40000 ALTER TABLE `leavecalendar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leaverequest`
--

DROP TABLE IF EXISTS `leaverequest`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `leaverequest` (
  `lr_id` varchar(36) NOT NULL default '',
  `lr_no` int(10) unsigned NOT NULL default '0',
  `lr_emp_no` varchar(36) NOT NULL default '',
  `lr_emp_dept` varchar(36) NOT NULL default '',
  `lr_emp_location` varchar(36) NOT NULL default '',
  `lr_lt_no` varchar(36) NOT NULL default '',
  `lr_reason` varchar(255) default NULL,
  `lr_start_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `lr_start_apm` int(10) unsigned default NULL,
  `lr_end_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `lr_end_apm` int(10) unsigned default NULL,
  `lr_total_hours` decimal(7,1) NOT NULL default '0.0',
  `lr_total_days` decimal(6,2) NOT NULL default '0.00',
  `lr_next_approver` varchar(36) default NULL,
  `lr_status` int(10) unsigned NOT NULL default '0',
  `lr_create_by` varchar(36) NOT NULL default '',
  `lr_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `lr_last_change_by` varchar(36) NOT NULL default '',
  `lr_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `lr_security_no` varchar(36) default NULL,
  PRIMARY KEY  (`lr_id`),
  KEY `FK_lr_emp_no` (`lr_emp_no`),
  KEY `FK_lr_emp_dept` (`lr_emp_dept`),
  KEY `FK_lr_emp_location` (`lr_emp_location`),
  KEY `FK_lr_lt_no` (`lr_lt_no`),
  KEY `FK_lr_create_by` (`lr_create_by`),
  KEY `FK_lr_last_change_by` (`lr_last_change_by`),
  CONSTRAINT `FK_lr_emp_dept` FOREIGN KEY (`lr_emp_dept`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_lr_emp_location` FOREIGN KEY (`lr_emp_location`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_lr_emp_no` FOREIGN KEY (`lr_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_lr_lt_no` FOREIGN KEY (`lr_lt_no`) REFERENCES `leavetype` (`lt_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `leaverequest`
--

LOCK TABLES `leaverequest` WRITE;
/*!40000 ALTER TABLE `leaverequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `leaverequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leavetype`
--

DROP TABLE IF EXISTS `leavetype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `leavetype` (
  `lt_no` varchar(36) NOT NULL default '',
  `lt_name` varchar(64) NOT NULL default '',
  `lt_desc` varchar(128) default NULL,
  `lt_special_cat` int(10) unsigned NOT NULL default '0',
  `lt_max_apply_days` decimal(6,1) default NULL,
  `lt_min_apply_days` decimal(6,1) default NULL,
  `lt_max_apply_hours` decimal(7,1) default NULL,
  `lt_min_apply_hours` decimal(7,1) default NULL,
  `lt_incl_holiday` int(10) unsigned NOT NULL default '0',
  `lt_bal_forward` date default NULL,
  `lt_bal_forward_day_limit` decimal(6,1) default NULL,
  `lt_bal_forward_hour_limit` decimal(7,1) default NULL,
  `lt_bal_forward_per_limit` decimal(6,2) default NULL,
  `lt_bal_forward_rounding` int(10) unsigned default NULL,
  `lt_days_of_year` varchar(128) default NULL,
  `lt_hours_of_year` varchar(128) default NULL,
  `lt_release_method` varchar(8) default NULL,
  `lt_days_for_release` varchar(128) default NULL,
  `lt_hours_for_release` varchar(128) default NULL,
  `lt_release_rounding` int(10) unsigned default NULL,
  `lt_other_parameter` varchar(128) default NULL,
  `lt_need_comments` int(10) unsigned NOT NULL default '0',
  `lt_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`lt_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `leavetype`
--

LOCK TABLES `leavetype` WRITE;
/*!40000 ALTER TABLE `leavetype` DISABLE KEYS */;
INSERT INTO `leavetype` VALUES ('059cbc77-1726-476c-9589-0417489a9891','婚假','Wedding',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,4),('4dd9feef-7c50-4fe0-8770-44542e5d2d67','其它','Others',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,7),('50692abb-98da-4bf6-872c-0a44c9149b66','产假','Maternity',0,'120.0',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,5),('7464ba7b-4573-43e5-8a44-be4f14548a55','事假','Casual Leave',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,2),('800de0fc-9666-4ca5-8a99-cc71ed5c3f30','因公外出','外出办事',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,9),('8dff3cb0-3073-451d-8e98-fab600676c09','出差','Travel',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,6),('af606c86-22bb-4609-bf4b-13d79f059c7d','年假','Annual Leave',1,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,1),('d5c04a53-9401-4fff-abf6-cc639fda3b7e','病假','Sick Leave',0,'90.0',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,3),('da526930-1d94-40cf-978b-6d9e4527ea44','调休','加班调休',0,NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,8),('ef7474fb-d598-48a4-b222-93512e1a66f9','病假-住院','Sick Leave',0,'90.0',NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,3);
/*!40000 ALTER TABLE `leavetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `location` (
  `location_no` varchar(36) NOT NULL default '',
  `location_name` varchar(64) NOT NULL default '',
  `location_desc` varchar(128) default NULL,
  `location_status` int(10) unsigned NOT NULL default '1',
  `location_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`location_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES ('91b14ff4-3c14-40b0-98a8-d534a3f77ef7','杭州','',1,2),('bc262b84-d1ff-4664-8b62-210386b16ef0','上海','',1,1);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orgheads`
--

DROP TABLE IF EXISTS `orgheads`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `orgheads` (
  `orgheads_id` varchar(36) NOT NULL default '',
  `orgheads_org_no` varchar(36) NOT NULL default '',
  `orgheads_responsibility` varchar(16) NOT NULL default '',
  `orgheads_emp_no` varchar(36) NOT NULL default '',
  PRIMARY KEY  (`orgheads_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `orgheads`
--

LOCK TABLES `orgheads` WRITE;
/*!40000 ALTER TABLE `orgheads` DISABLE KEYS */;
/*!40000 ALTER TABLE `orgheads` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ouperfcriteria`
--

DROP TABLE IF EXISTS `ouperfcriteria`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ouperfcriteria` (
  `oup_id` varchar(36) NOT NULL default '',
  `oup_name` varchar(64) NOT NULL default '',
  `oup_rate` int(10) unsigned default NULL,
  `oup_dept_id` varchar(36) default NULL,
  `oup_pb_id` varchar(36) default NULL,
  `oup_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`oup_id`),
  KEY `FK_oup_dept_id` (`oup_dept_id`),
  KEY `FK_oup_pb_id` (`oup_pb_id`),
  CONSTRAINT `FK_oup_dept_id` FOREIGN KEY (`oup_dept_id`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_oup_pb_id` FOREIGN KEY (`oup_pb_id`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ouperfcriteria`
--

LOCK TABLES `ouperfcriteria` WRITE;
/*!40000 ALTER TABLE `ouperfcriteria` DISABLE KEYS */;
/*!40000 ALTER TABLE `ouperfcriteria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ouqualify`
--

DROP TABLE IF EXISTS `ouqualify`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ouqualify` (
  `ouq_id` varchar(36) NOT NULL default '',
  `ouq_name` varchar(32) NOT NULL default '',
  `ouq_desc` varchar(64) default NULL,
  `ouq_pb_id` varchar(36) default NULL,
  `ouq_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`ouq_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ouqualify`
--

LOCK TABLES `ouqualify` WRITE;
/*!40000 ALTER TABLE `ouqualify` DISABLE KEYS */;
/*!40000 ALTER TABLE `ouqualify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ouresponse`
--

DROP TABLE IF EXISTS `ouresponse`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ouresponse` (
  `our_id` varchar(36) NOT NULL default '',
  `our_name` varchar(32) NOT NULL default '',
  `our_desc` varchar(256) default NULL,
  `our_rate` int(10) unsigned default NULL,
  `our_dept_id` varchar(36) default NULL,
  `our_pb_id` varchar(36) default NULL,
  `our_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`our_id`),
  KEY `FK_our_dept_id` (`our_dept_id`),
  KEY `FK_our_pb_id` (`our_pb_id`),
  CONSTRAINT `FK_our_dept_id` FOREIGN KEY (`our_dept_id`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_our_pb_id` FOREIGN KEY (`our_pb_id`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ouresponse`
--

LOCK TABLES `ouresponse` WRITE;
/*!40000 ALTER TABLE `ouresponse` DISABLE KEYS */;
/*!40000 ALTER TABLE `ouresponse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outmatch`
--

DROP TABLE IF EXISTS `outmatch`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `outmatch` (
  `om_id` varchar(36) NOT NULL,
  `om_omm_id` varchar(36) NOT NULL,
  `om_omb_id` varchar(36) NOT NULL,
  `om_field_desc` varchar(64) NOT NULL,
  `om_format` varchar(32) default NULL,
  `om_column_width` int(10) unsigned NOT NULL default '15',
  `om_statistic` varchar(8) default NULL,
  `om_is_group` int(10) unsigned NOT NULL,
  `om_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`om_id`),
  UNIQUE KEY `omm_omb` (`om_omm_id`,`om_omb_id`),
  KEY `FK_om_omb_id` (`om_omb_id`),
  CONSTRAINT `FK_om_omb_id` FOREIGN KEY (`om_omb_id`) REFERENCES `outmatchbasic` (`omb_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_om_omm_id` FOREIGN KEY (`om_omm_id`) REFERENCES `outmatchmodel` (`omm_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `outmatch`
--

LOCK TABLES `outmatch` WRITE;
/*!40000 ALTER TABLE `outmatch` DISABLE KEYS */;
INSERT INTO `outmatch` VALUES ('257fe465-be48-4439-80a1-524ab55bb57a','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','e9975c17-a03d-4066-a707-697d1131b0b8','三级部门',NULL,15,NULL,0,6),('38119c27-9be6-4bcc-af03-880e07920cf3','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','047d54ce-296a-44c1-b64c-e301864c5bd6','用工形式',NULL,15,NULL,0,9),('3dcbf258-46fb-4ad9-8cf4-a61511e36cd8','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','596e3832-a858-4b4b-ba83-a772c1424667','一级部门',NULL,12,NULL,0,4),('3f1def8c-5910-4c43-a4cc-705cad9079b1','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','d07c7dff-8ec8-4b1e-9449-5ddd7c09fbab','姓名',NULL,15,NULL,0,1),('402880e4232bd2d301232c51f94b0003','402880e4232bd2d301232c51f3bd0001','1fa208b1-18e5-43f7-b8f9-fb1fbd6788d8','部门',NULL,16,NULL,1,0),('402880e4232bd2d301232c51f94b0004','402880e4232bd2d301232c51f3bd0001','21359e01-99fd-4b10-83fc-abc99360157a','用工形式',NULL,12,NULL,0,9),('402880e4232bd2d301232c51f94b0009','402880e4232bd2d301232c51f3bd0001','402880f02304355e012307fee18b00b0','二级部门',NULL,12,NULL,0,5),('402880e4232bd2d301232c51f955000b','402880e4232bd2d301232c51f3bd0001','402880f02304355e012307fee18b00b2','三级部门',NULL,16,NULL,0,6),('402880e4232bd2d301232c51f955000f','402880e4232bd2d301232c51f3bd0001','402880f02304355e012308017a3700bd','职位描述',NULL,10,NULL,0,8),('402880e4232bd2d301232c51f955003e','402880e4232bd2d301232c51f3bd0001','402880f0230992e7012309a9b6e40035','薪资级别',NULL,12,NULL,0,12),('402880e4232bd2d301232c51f955003f','402880e4232bd2d301232c51f3bd0001','402880f0230992e7012309a9b6e40036','薪资等级',NULL,12,NULL,0,13),('402880e4232bd2d301232c51f9550040','402880e4232bd2d301232c51f3bd0001','402880f0230992e7012309a9b6e40037','银行帐号',NULL,20,NULL,0,15),('402880e4232bd2d301232c51f9550041','402880e4232bd2d301232c51f3bd0001','402880f0230992e7012309a9b6e40038','银行开户行',NULL,20,NULL,0,14),('402880e4232bd2d301232c51f95f0045','402880e4232bd2d301232c51f3bd0001','62a6b61e-ab87-403d-bd91-812f07c66749','帐套名称',NULL,20,NULL,0,16),('402880e4232bd2d301232c51f95f0046','402880e4232bd2d301232c51f3bd0001','69f3cf82-a924-42e6-868f-7a3e12b995ce','备注',NULL,20,NULL,0,17),('402880e4232bd2d301232c51f95f0048','402880e4232bd2d301232c51f3bd0001','72b8f8f2-48f7-4eb9-aae3-e46dab721fa3','姓名',NULL,10,NULL,0,2),('402880e4232bd2d301232c51f95f0049','402880e4232bd2d301232c51f3bd0001','751634b9-ac30-4bc9-92eb-3fb660ee5cb0','一级部门',NULL,12,NULL,0,4),('402880e4232bd2d301232c51f95f004b','402880e4232bd2d301232c51f3bd0001','9c684133-459a-4778-bcd2-8772302e61c3','工作地区',NULL,12,NULL,0,7),('402880e4232bd2d301232c51f95f004c','402880e4232bd2d301232c51f3bd0001','eaeb77b0-d0bd-4b8a-b37c-c39580dbf5e1','工号',NULL,10,NULL,3,1),('402880e423785e400123789c86e30009','402880e423785e400123789c86660007','01590c79-0b64-4a05-bdda-8f3d48eb9557','变动日期','yyyy-MM-dd',12,NULL,0,10),('402880e423785e400123789c86e3000c','402880e423785e400123789c86660007','0fe502cf-231c-4b15-9193-2f90cbc5d191','姓名',NULL,10,NULL,0,1),('402880e423785e400123789c86e30019','402880e423785e400123789c86660007','402880e423785e4001237882bc880001','现职位描述',NULL,10,NULL,0,8),('402880e423785e400123789c86e3001b','402880e423785e400123789c86660007','402880e423785e4001237898641c0005','变动备注',NULL,20,NULL,0,17),('402880e423785e400123789c86e30024','402880e423785e400123789c86660007','5cec577c-7481-4fa2-a1ae-852aedfe4b35','现部门',NULL,16,NULL,0,3),('402880e423785e400123789c86e30029','402880e423785e400123789c86660007','6763a274-e43c-4257-a104-1161bb65bd7b','工作地区',NULL,12,NULL,0,7),('402880e423785e400123789c86e3002f','402880e423785e400123789c86660007','7163da50-8cb4-4175-b220-30681b45ce66','现三级部门',NULL,16,NULL,0,6),('402880e423785e400123789c86e30031','402880e423785e400123789c86660007','7a99b12a-afc4-4154-a4c7-29016bf9a53f','变动原因',NULL,20,NULL,0,16),('402880e423785e400123789c86e30032','402880e423785e400123789c86660007','7d3cadaf-a843-48ce-8f03-4aa62724df75','现分公司',NULL,16,NULL,0,2),('402880e423785e400123789c86e30036','402880e423785e400123789c86660007','81312f3e-e399-4f5f-b9dc-f3f8778ea02e','现二级部门',NULL,12,NULL,0,5),('402880e423785e400123789c86e30040','402880e423785e400123789c86660007','a4458ade-910e-4ee0-9f97-089db2120db4','工号',NULL,10,NULL,0,0),('402880e423785e400123789c86e3004b','402880e423785e400123789c86660007','bd78bcd6-f5b6-43ab-94d4-9fdf2478c546','变动种类',NULL,15,NULL,0,11),('402880e423785e400123789c86e3004d','402880e423785e400123789c86660007','c4602c23-40a1-4f99-8a61-b1ed950dd731','现一级部门',NULL,12,NULL,0,4),('402880e423785e40012378c1cf710059','402880e423785e40012378c1cea60058','0710b4b3-9c17-4a53-bc60-e019de179a2a','分公司',NULL,16,NULL,0,2),('402880e423785e40012378c1cf71005f','402880e423785e40012378c1cea60058','1760909c-1ce0-4bad-a519-36a3982575a2','职位描述',NULL,10,NULL,0,8),('402880e423785e40012378c1cf710060','402880e423785e40012378c1cea60058','17d2c017-fa26-4b88-bdd8-b4582ca407cd','合同期限',NULL,12,NULL,0,17),('402880e423785e40012378c1cf710061','402880e423785e40012378c1cea60058','1c73f90e-1276-4f62-a648-6e94235fbfa7','一级部门',NULL,12,NULL,0,4),('402880e423785e40012378c1cf710067','402880e423785e40012378c1cea60058','393e200b-d55c-4672-9c61-5300fa9b718f','转正日期','yyyy-MM-dd',12,NULL,0,11),('402880e423785e40012378c1cf710075','402880e423785e40012378c1cea60058','56d66565-377d-4a35-9c31-a46037a9f6e7','三级部门',NULL,16,NULL,0,6),('402880e423785e40012378c1cf710078','402880e423785e40012378c1cea60058','66352651-fbda-4b3c-a602-4b7439cdc7ce','合同备注',NULL,26,NULL,0,18),('402880e423785e40012378c1cf71007b','402880e423785e40012378c1cea60058','6ac3fddb-ba07-40a6-9634-80b7c631094f','姓名',NULL,10,NULL,0,1),('402880e423785e40012378c1cf710081','402880e423785e40012378c1cea60058','75e53854-a831-4168-956d-12fff393de06','合同状态',NULL,12,NULL,0,15),('402880e423785e40012378c1cf710085','402880e423785e40012378c1cea60058','8e6982b6-8d97-4695-8463-7ac19f522f4b','合同种类',NULL,20,NULL,0,14),('402880e423785e40012378c1cf81008b','402880e423785e40012378c1cea60058','9b5a4679-61ce-416b-8843-090fcd30bbc4','合同起始日期','yyyy-MM-dd',12,NULL,0,12),('402880e423785e40012378c1cf81008c','402880e423785e40012378c1cea60058','9da167e3-386a-4c93-984d-c2140003308d','用工形式',NULL,12,NULL,0,9),('402880e423785e40012378c1cf810094','402880e423785e40012378c1cea60058','be4e4a29-4058-4172-a556-a578d5b2a8fb','部门',NULL,16,NULL,0,3),('402880e423785e40012378c1cf810096','402880e423785e40012378c1cea60058','c20bbc32-d5a5-4321-88d7-adc84bd496bd','二级部门',NULL,12,NULL,0,5),('402880e423785e40012378c1cf810097','402880e423785e40012378c1cea60058','c21651d0-4951-434c-941b-123edc3624ca','合同结束日期','yyyy-MM-dd',12,NULL,0,13),('402880e423785e40012378c1cf810098','402880e423785e40012378c1cea60058','c3defad5-dd1c-49cc-aca5-f281cb661209','入职日期','yyyy-MM-dd',12,NULL,0,10),('402880e423785e40012378c1cf810099','402880e423785e40012378c1cea60058','c858dc97-a566-423d-bd0d-693d1e952afa','工作地区',NULL,12,NULL,0,7),('402880e423785e40012378c1cf81009a','402880e423785e40012378c1cea60058','c9c23c68-2369-40fe-992c-66c63ba61344','合同编号',NULL,15,NULL,0,16),('402880e423785e40012378c1cf8100a5','402880e423785e40012378c1cea60058','ff731861-d28d-4e19-9bf3-1c2069208566','工号',NULL,10,NULL,0,0),('402880e423792e7f012379358a360009','402880e423792e7f01237935899a0001','2d789820-f07c-42f7-9a25-48e0607ad5a0','考评备注',NULL,20,NULL,0,18),('402880e423792e7f012379358a36000a','402880e423792e7f01237935899a0001','2dd1e92e-02f5-4a1b-91e1-220b635da92e','考评结果',NULL,12,NULL,0,17),('402880e423792e7f012379358a46000f','402880e423792e7f01237935899a0001','402880e423785e40012378d8d7fd00a6','分公司',NULL,16,NULL,0,2),('402880e423792e7f012379358a460011','402880e423792e7f01237935899a0001','402880e423785e40012378d8d7fd00a8','二级部门',NULL,12,NULL,0,5),('402880e423792e7f012379358a460013','402880e423792e7f01237935899a0001','402880e423785e40012378dce3b100ae','工作地区',NULL,12,NULL,0,7),('402880e423792e7f012379358a460016','402880e423792e7f01237935899a0001','402880e423785e40012378dce3b100b1','职位描述',NULL,10,NULL,0,8),('402880e423792e7f012379358a46001a','402880e423792e7f01237935899a0001','402880e423785e40012378ee29d000bc','部门',NULL,16,NULL,0,3),('402880e423792e7f012379358a46001c','402880e423792e7f01237935899a0001','402880e423785e40012378ee29d000be','一级部门',NULL,12,NULL,0,4),('402880e423792e7f012379358a46001e','402880e423792e7f01237935899a0001','402880e423785e40012378ee29d000c0','三级部门',NULL,16,NULL,0,6),('402880e423792e7f012379358a460021','402880e423792e7f01237935899a0001','497febba-dcf7-4f78-af11-986589e7b8a2','结束日期','yyyy-MM-dd',12,NULL,0,11),('402880e423792e7f012379358a46002d','402880e423792e7f01237935899a0001','788eda69-a027-4fba-b9d2-771e260e7b77','起始日期','yyyy-MM-dd',12,NULL,0,10),('402880e423792e7f012379358a460031','402880e423792e7f01237935899a0001','81bf5be9-e955-4ae4-acd8-63ec76adefbc','姓名',NULL,10,NULL,0,1),('402880e423792e7f012379358a46003a','402880e423792e7f01237935899a0001','9de87469-852f-4f66-b4e0-8e5944c50a79','工号',NULL,10,NULL,0,0),('402880e423792e7f012379358a46003d','402880e423792e7f01237935899a0001','a6e7ca7b-4edf-44ad-a2c2-2c4371a730e1','考评职位描述',NULL,10,NULL,0,16),('402880e423792e7f012379358a460047','402880e423792e7f01237935899a0001','c22f28a7-bf4f-40c2-afa1-03d332bf0588','考评种类',NULL,12,NULL,0,12),('402880e423792e7f01237977b135005c','402880e423792e7f01237977b07a005a','025e2b3f-012e-4e83-a9ee-d31facf0173c','姓名',NULL,10,NULL,0,1),('402880e423792e7f01237977b135006b','402880e423792e7f01237977b07a005a','402880e423792e7f012379653a860055','职位描述',NULL,10,NULL,0,8),('402880e423792e7f01237977b135006e','402880e423792e7f01237977b07a005a','4709bcd9-bb4e-46a3-8c61-4f3c6a6b58f9','奖惩日期','yyyy-MM-dd',12,NULL,0,9),('402880e423792e7f01237977b1350077','402880e423792e7f01237977b07a005a','6b4132b2-55a4-4736-8272-8ebe254f6053','工号',NULL,10,NULL,0,0),('402880e423792e7f01237977b135007f','402880e423792e7f01237977b07a005a','7fe71b14-6738-447f-89cf-e11fe8b1d61f','部门',NULL,16,NULL,0,3),('402880e423792e7f01237977b1350080','402880e423792e7f01237977b07a005a','819de58c-d079-4eae-b663-4feb949bc0de','工作地区',NULL,12,NULL,0,7),('402880e423792e7f01237977b1350083','402880e423792e7f01237977b07a005a','8a73334f-6e89-4d2c-b6db-c3b96ef9adcb','奖惩种类',NULL,12,NULL,0,10),('402880e423792e7f01237977b1350085','402880e423792e7f01237977b07a005a','8be1487e-f791-486f-8cbd-849077d37d76','一级部门',NULL,12,NULL,0,4),('402880e423792e7f01237977b1350095','402880e423792e7f01237977b07a005a','b43680ea-1fa1-42c6-aae0-c891e5139f22','三级部门',NULL,16,NULL,0,6),('402880e423792e7f01237977b1350096','402880e423792e7f01237977b07a005a','b53ca36f-9d64-44fc-8cfd-a5d5a9ec94c4','二级部门',NULL,12,NULL,0,5),('402880e423792e7f01237977b1350099','402880e423792e7f01237977b07a005a','c13530e8-63d9-4a68-9427-4f9eb977b207','奖惩形式',NULL,12,NULL,0,11),('402880e423792e7f01237977b135009e','402880e423792e7f01237977b07a005a','e3d1620e-fa74-4148-9002-cac362e5f01e','奖惩备注','15',20,NULL,0,12),('402880e423792e7f01237977b13500a6','402880e423792e7f01237977b07a005a','f7bd8e15-cd05-4298-858c-ee36bb5a06d4','分公司',NULL,16,NULL,0,2),('402880e42379dc1e01237a4d36570004','402880e42379dc1e01237a4d35bb0003','03fec031-9862-47ca-b768-83c255def9dc','职位描述',NULL,10,NULL,0,8),('402880e42379dc1e01237a4d3667000f','402880e42379dc1e01237a4d35bb0003','4776d73c-9196-4eda-ad53-4365233bf552','姓名',NULL,10,NULL,0,1),('402880e42379dc1e01237a4d36670017','402880e42379dc1e01237a4d35bb0003','5f13b67f-9a2b-4d63-861b-303b478adbee','部门',NULL,16,NULL,0,3),('402880e42379dc1e01237a4d3667001a','402880e42379dc1e01237a4d35bb0003','691880a6-bf48-43db-b0d1-b67a151db983','分公司',NULL,16,NULL,0,2),('402880e42379dc1e01237a4d3667002a','402880e42379dc1e01237a4d35bb0003','92aec4d7-1214-47d0-a103-81e1f3086f74','入职日期','yyyy-MM-dd',12,NULL,0,15),('402880e42379dc1e01237a4d3667002b','402880e42379dc1e01237a4d35bb0003','969ed78d-e8b1-47f3-b72f-d76d15e8623b','离职审批人',NULL,10,NULL,0,12),('402880e42379dc1e01237a4d3667002c','402880e42379dc1e01237a4d35bb0003','9bd8f863-e944-494c-b734-2bb308b6f48f','三级部门',NULL,16,NULL,0,6),('402880e42379dc1e01237a4d3667002d','402880e42379dc1e01237a4d35bb0003','9c73c0e7-413c-4f4f-acb0-c3a5bec1f24c','工号',NULL,10,NULL,0,0),('402880e42379dc1e01237a4d36670030','402880e42379dc1e01237a4d35bb0003','b004419c-8c77-4e0e-a845-89978b20ddb5','离职日期','yyyy-MM-dd',12,NULL,0,10),('402880e42379dc1e01237a4d36670033','402880e42379dc1e01237a4d35bb0003','b6a8e7b1-4777-4b34-8d85-d077f4c51b72','用工形式',NULL,12,NULL,0,9),('402880e42379dc1e01237a4d3667003c','402880e42379dc1e01237a4d35bb0003','d8c802ec-cae1-47ac-bd55-24400c7a89ee','工作地区',NULL,12,NULL,0,7),('402880e42379dc1e01237a4d36670042','402880e42379dc1e01237a4d35bb0003','e6537ba1-48e9-4e31-bd69-8f58345655ce','离职备注',NULL,20,NULL,0,14),('402880e42379dc1e01237a4d36670043','402880e42379dc1e01237a4d35bb0003','e8dc3be0-cc2f-4a8c-9a96-d4244c259677','一级部门',NULL,12,NULL,0,4),('402880e42379dc1e01237a4d36670048','402880e42379dc1e01237a4d35bb0003','f666ce48-8f4a-4367-be3c-aa8fb98e55c0','离职种类',NULL,12,NULL,0,11),('402880e42379dc1e01237a4d3667004c','402880e42379dc1e01237a4d35bb0003','fc226222-da25-4fb1-9ca1-c62cc987d0b8','离职原因',NULL,20,NULL,0,13),('402880e42379dc1e01237a4d3667004d','402880e42379dc1e01237a4d35bb0003','fcca7328-4fc4-458f-8149-c923acb58317','二级部门',NULL,12,NULL,0,5),('402880e623dfd4180123dfe1900d0005','ff808081232096ef0123237a954e01e0','402880e623dfd4180123dfe18e190001','全勤(小时)',NULL,15,NULL,0,17),('402880e924a5388e0124a53a28bf0003','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53a28b50001','工号',NULL,10,NULL,0,0),('402880e924a5388e0124a53a28bf0004','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53a28bf0002','姓名',NULL,10,NULL,0,1),('402880e924a5388e0124a53cabdb000f','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53cabc70005','分公司',NULL,16,NULL,0,2),('402880e924a5388e0124a53cabdb0011','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53cabc70007','部门',NULL,16,NULL,0,3),('402880e924a5388e0124a53cabdb0013','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53cabc70009','一级部门',NULL,12,NULL,0,4),('402880e924a5388e0124a53cabdb0015','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53cabd1000b','二级部门',NULL,12,NULL,0,5),('402880e924a5388e0124a53cabdb0017','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53cabd1000d','三级部门',NULL,16,NULL,0,6),('402880e924a5388e0124a53e7dcd001f','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53e7dcd0019','工作地区',NULL,12,NULL,0,7),('402880e924a5388e0124a53e7dcd0021','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53e7dcd001b','职位',NULL,10,NULL,0,8),('402880e924a5388e0124a53e7dcd0023','f26f823e-30b0-460a-b357-ea4527364ab4','402880e924a5388e0124a53e7dcd001d','用工形式',NULL,12,NULL,0,9),('402880e924af34030124af45be160001','402880f0230992e7012309ba32dc0084','26f4ea56-a380-4dd8-a8c0-c7db70d59e97','分公司',NULL,16,NULL,0,6),('402880e924af34030124af45be200002','402880f0230992e7012309ba32dc0084','9c45b911-09c1-4823-b5e2-ee11c1955968','一级部门',NULL,12,NULL,0,7),('402880e924af34030124af45be200003','402880f0230992e7012309ba32dc0084','bd2b474c-b7bf-4292-a386-42feb49ea2c7','二级部门',NULL,12,NULL,0,8),('402880e924af34030124af5e83be0004','402880e423792e7f01237935899a0001','402880e423785e40012378dce3b100b2','用工形式',NULL,12,NULL,0,9),('402880e924af34030124af603cf60005','402880f0246a87a301246bd423df004e','402880e423785e40012378dce3b100b2','用工形式',NULL,12,NULL,0,9),('402880e924af34030124af6186080006','402880e423785e400123789c86660007','9b091a55-d462-441b-8289-5b5cf8a09b34','现用工形式',NULL,12,NULL,0,9),('402880e924af34030124af61ab4f0007','402880f0246a87a301246be0df8400a0','9b091a55-d462-441b-8289-5b5cf8a09b34','现用工形式',NULL,12,NULL,0,9),('402880e924af34030124af69c1470022','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0008','日薪基数','2',15,NULL,0,18),('402880e924af34030124af69c1470023','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0008','日薪基数','2',15,NULL,0,18),('402880e924af34030124af69c1470024','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0009','日薪','2',15,NULL,0,19),('402880e924af34030124af69c1470025','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0009','日薪','2',15,NULL,0,19),('402880e924af34030124af69c1470026','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000a','基本工资','2',15,NULL,0,20),('402880e924af34030124af69c1470027','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000a','基本工资','2',15,NULL,0,20),('402880e924af34030124af69c1470028','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000b','本月出勤(天)','2',15,NULL,0,21),('402880e924af34030124af69c1470029','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000b','本月出勤(天)','2',15,NULL,0,21),('402880e924af34030124af69c147002a','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000c','本月缺勤(天)','2',15,NULL,0,22),('402880e924af34030124af69c147002b','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000c','本月缺勤(天)','2',15,NULL,0,22),('402880e924af34030124af69c151002c','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000d','年终奖','2',15,NULL,0,23),('402880e924af34030124af69c151002d','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000d','年终奖','2',15,NULL,0,23),('402880e924af34030124af69c151002e','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000e','浮动项目总额','2',15,NULL,0,24),('402880e924af34030124af69c151002f','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000e','浮动项目总额','2',15,NULL,0,24),('402880e924af34030124af69c1510030','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d000f','税前收入','2',15,NULL,0,25),('402880e924af34030124af69c1510031','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d000f','税前收入','2',15,NULL,0,25),('402880e924af34030124af69c1510032','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0010','社保基数','2',15,NULL,0,26),('402880e924af34030124af69c1510033','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0010','社保基数','2',15,NULL,0,26),('402880e924af34030124af69c1510034','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0011','公积金基数','2',15,NULL,0,27),('402880e924af34030124af69c1510035','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0011','公积金基数','2',15,NULL,0,27),('402880e924af34030124af69c1510036','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0012','养老保险','2',15,NULL,0,28),('402880e924af34030124af69c1510037','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0012','养老保险','2',15,NULL,0,28),('402880e924af34030124af69c1510038','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0013','失业保险','2',15,NULL,0,29),('402880e924af34030124af69c1510039','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0013','失业保险','2',15,NULL,0,29),('402880e924af34030124af69c151003a','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0014','医疗保险','2',15,NULL,0,30),('402880e924af34030124af69c151003b','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0014','医疗保险','2',15,NULL,0,30),('402880e924af34030124af69c151003c','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0015','公积金','2',15,NULL,0,31),('402880e924af34030124af69c151003d','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0015','公积金','2',15,NULL,0,31),('402880e924af34030124af69c151003e','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0016','社保总额','2',15,NULL,0,32),('402880e924af34030124af69c151003f','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0016','社保总额','2',15,NULL,0,32),('402880e924af34030124af69c1510040','ff808081232096ef0123221a6b420127','402880e924af34030124af69c13d0017','代缴养老保险','2',15,NULL,0,33),('402880e924af34030124af69c1510041','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c13d0017','代缴养老保险','2',15,NULL,0,33),('402880e924af34030124af69c1510042','ff808081232096ef0123221a6b420127','402880e924af34030124af69c1470018','代缴失业保险','2',15,NULL,0,34),('402880e924af34030124af69c1510043','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c1470018','代缴失业保险','2',15,NULL,0,34),('402880e924af34030124af69c1510044','ff808081232096ef0123221a6b420127','402880e924af34030124af69c1470019','代缴医疗保险','2',15,NULL,0,35),('402880e924af34030124af69c1510045','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c1470019','代缴医疗保险','2',15,NULL,0,35),('402880e924af34030124af69c1510046','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001a','代缴工伤保险','2',15,NULL,0,36),('402880e924af34030124af69c1510047','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001a','代缴工伤保险','2',15,NULL,0,36),('402880e924af34030124af69c1510048','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001b','代缴生育保险','2',15,NULL,0,37),('402880e924af34030124af69c1510049','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001b','代缴生育保险','2',15,NULL,0,37),('402880e924af34030124af69c151004a','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001c','代缴公积金','2',15,NULL,0,38),('402880e924af34030124af69c151004b','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001c','代缴公积金','2',15,NULL,0,38),('402880e924af34030124af69c151004c','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001d','代缴社保总额','2',15,NULL,0,39),('402880e924af34030124af69c151004d','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001d','代缴社保总额','2',15,NULL,0,39),('402880e924af34030124af69c151004e','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001e','其他福利扣款','2',15,NULL,0,40),('402880e924af34030124af69c151004f','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001e','其他福利扣款','2',15,NULL,0,40),('402880e924af34030124af69c1510050','ff808081232096ef0123221a6b420127','402880e924af34030124af69c147001f','应纳税所得额','2',15,NULL,0,41),('402880e924af34030124af69c1510051','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c147001f','应纳税所得额','2',15,NULL,0,41),('402880e924af34030124af69c1510052','ff808081232096ef0123221a6b420127','402880e924af34030124af69c1470020','所得税','2',15,NULL,0,42),('402880e924af34030124af69c1510053','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c1470020','所得税','2',15,NULL,0,42),('402880e924af34030124af69c1510054','ff808081232096ef0123221a6b420127','402880e924af34030124af69c1470021','税后收入','2',15,NULL,0,43),('402880e924af34030124af69c1510055','402880e4232bd2d301232c51f3bd0001','402880e924af34030124af69c1470021','税后收入','2',15,NULL,0,43),('402880e924af34030124af69c1e70063','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70056','养老保险','2',15,NULL,0,14),('402880e924af34030124af69c1f10064','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70057','失业保险','2',15,NULL,0,15),('402880e924af34030124af69c1f10065','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70058','医疗保险','2',15,NULL,0,16),('402880e924af34030124af69c1f10066','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70059','公积金','2',15,NULL,0,17),('402880e924af34030124af69c1f10067','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005a','社保总额','2',15,NULL,0,18),('402880e924af34030124af69c1f10068','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005b','代缴养老保险','2',15,NULL,0,19),('402880e924af34030124af69c1f10069','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005c','代缴失业保险','2',15,NULL,0,20),('402880e924af34030124af69c1f1006a','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005d','代缴医疗保险','2',15,NULL,0,21),('402880e924af34030124af69c1f1006b','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005e','代缴工伤保险','2',15,NULL,0,22),('402880e924af34030124af69c1f1006c','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e7005f','代缴生育保险','2',15,NULL,0,23),('402880e924af34030124af69c1f1006d','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70060','代缴公积金','2',15,NULL,0,24),('402880e924af34030124af69c1f1006e','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70061','代缴社保总额','2',15,NULL,0,25),('402880e924af34030124af69c1f1006f','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880e924af34030124af69c1e70062','其他福利扣款','2',15,NULL,0,26),('402880e924af34030124af6cab500070','402880e4232bd2d301232c51f3bd0001','402880f02304355e01230805d5d100c1','入职日期','yyyy-MM-dd',12,NULL,0,10),('402880e924af34030124af6cab500071','402880e4232bd2d301232c51f3bd0001','402880f02304355e0123081ac1d500d8','社保种类',NULL,12,NULL,0,11),('402880f0230992e7012309b3894b0040','402880f0230992e7012309b3892c003f','02f27d3d-0226-4f4f-bce0-b009845a0aa2','职位描述',NULL,10,NULL,0,11),('402880f0230992e7012309b3894b0041','402880f0230992e7012309b3892c003f','0bf7ec22-6f9c-4106-8327-9a593f0ce071','当前住址邮编',NULL,10,NULL,0,34),('402880f0230992e7012309b3894b0042','402880f0230992e7012309b3892c003f','1720e1f1-028a-4b96-91b0-28d9ad6c6833','自定义字段12',NULL,15,NULL,0,54),('402880f0230992e7012309b3894b0043','402880f0230992e7012309b3892c003f','18d92d44-03d6-4b0e-9e6c-66cdcc31185c','档案所在地',NULL,26,NULL,0,26),('402880f0230992e7012309b3894b0044','402880f0230992e7012309b3892c003f','1aa37c05-c985-4a2c-93da-f00728689c8d','工作地区',NULL,12,NULL,0,9),('402880f0230992e7012309b3894b0046','402880f0230992e7012309b3892c003f','1e3b814d-6dbf-4d2a-bcad-c5878f20f658','考勤卡号',NULL,12,NULL,0,40),('402880f0230992e7012309b3894b0047','402880f0230992e7012309b3892c003f','1e4056ee-e76d-41c3-beb1-d265266cb74e','自定义字段16',NULL,15,NULL,0,58),('402880f0230992e7012309b3894b0049','402880f0230992e7012309b3892c003f','284ec370-a94f-48be-a99c-2ddba0cf95a2','自定义字段13',NULL,15,NULL,0,55),('402880f0230992e7012309b3894b004a','402880f0230992e7012309b3892c003f','29fa9dde-8ac0-4d38-894a-ba91360642c7','社保种类',NULL,12,NULL,0,27),('402880f0230992e7012309b3894b004b','402880f0230992e7012309b3892c003f','2b2e7d2e-3bb6-40d6-8250-ff14ac05053e','姓名',NULL,10,NULL,0,1),('402880f0230992e7012309b3894b004c','402880f0230992e7012309b3892c003f','36e1f3ed-2491-4a5e-8fc6-29f2643d0504','紧急联系方式',NULL,20,NULL,0,38),('402880f0230992e7012309b3894b004d','402880f0230992e7012309b3892c003f','37080208-d2ec-41cb-81e4-c9c7dbe5e769','专业',NULL,20,NULL,0,22),('402880f0230992e7012309b3894b004e','402880f0230992e7012309b3892c003f','3c094b2d-ffb6-463f-a39f-54fe6a5b9c96','学历',NULL,10,NULL,0,20),('402880f0230992e7012309b3894b004f','402880f0230992e7012309b3892c003f','3e4329b4-2ff0-4a60-9b3d-87e334b0afc3','转正日期','yyyy-MM-dd',12,NULL,0,15),('402880f0230992e7012309b3894b0050','402880f0230992e7012309b3892c003f','3e8ac800-5299-4aa8-8729-4711f35eae99','部门',NULL,16,NULL,0,5),('402880f0230992e7012309b3894b0052','402880f0230992e7012309b3892c003f','40d805e5-bfc5-4586-9e4b-24e214086d84','毕业院校',NULL,20,NULL,0,21),('402880f0230992e7012309b3894b0053','402880f0230992e7012309b3892c003f','4bdf0e2d-33fa-48e7-b19a-987c99698927','自定义字段11',NULL,15,NULL,0,53),('402880f0230992e7012309b3894b0054','402880f0230992e7012309b3892c003f','4be1e261-bb39-4e41-a582-8d9754870be5','职位',NULL,10,NULL,0,10),('402880f0230992e7012309b3894b0057','402880f0230992e7012309b3892c003f','60fe5d52-dde3-45af-a1f5-bb86c0cb9e51','考勤方式',NULL,12,NULL,0,39),('402880f0230992e7012309b3894b0058','402880f0230992e7012309b3892c003f','62c23858-d1e7-4cd6-a863-f9ea56a36636','自定义字段07',NULL,15,NULL,0,49),('402880f0230992e7012309b3894b0059','402880f0230992e7012309b3892c003f','6320f072-34b3-4b8e-97c9-d10bcd5ff063','紧急联系人',NULL,10,NULL,0,37),('402880f0230992e7012309b3894b005a','402880f0230992e7012309b3892c003f','63ced644-c668-4a0f-96cb-a7cb44710408','用工形式',NULL,12,NULL,0,12),('402880f0230992e7012309b3894b005b','402880f0230992e7012309b3892c003f','661b3c23-c64f-49c9-82b1-756af27e506f','民族',NULL,6,NULL,0,24),('402880f0230992e7012309b3894b005d','402880f0230992e7012309b3892c003f','6acc88dd-b708-405d-bf47-673d0b987195','自定义字段04',NULL,15,NULL,0,46),('402880f0230992e7012309b3894b005e','402880f0230992e7012309b3892c003f','6bb6d076-0b24-4424-bd83-1b420ccbc090','自定义字段14',NULL,15,NULL,0,56),('402880f0230992e7012309b3894b005f','402880f0230992e7012309b3892c003f','738239c7-00df-4944-921a-d31f06e933e6','工作电话',NULL,16,NULL,0,30),('402880f0230992e7012309b3894b0061','402880f0230992e7012309b3892c003f','7b82488b-0a80-40cf-92f9-425b68cfb4b8','家庭地址',NULL,30,NULL,0,35),('402880f0230992e7012309b3894b0062','402880f0230992e7012309b3892c003f','7dd9ecc0-32b6-40cc-8658-400f1fc159a2','自定义字段09',NULL,15,NULL,0,51),('402880f0230992e7012309b3894b0063','402880f0230992e7012309b3892c003f','839fbf2c-3db0-446b-bbf7-bc5c1f0bf751','手机',NULL,16,NULL,0,31),('402880f0230992e7012309b3894b0064','402880f0230992e7012309b3892c003f','83f80480-3f21-4eec-ba27-16d0407929a2','备注',NULL,20,NULL,0,42),('402880f0230992e7012309b3894b0065','402880f0230992e7012309b3892c003f','848dde83-4b7d-41a8-8ca6-685d8784f6d2','血型',NULL,6,NULL,0,18),('402880f0230992e7012309b3894b0066','402880f0230992e7012309b3892c003f','89195781-45a8-4365-a084-0b448c35b17b','籍贯',NULL,10,NULL,0,23),('402880f0230992e7012309b3894b0067','402880f0230992e7012309b3892c003f','8f9adecb-279b-42f3-a78a-5d94ca676372','自定义字段01',NULL,15,NULL,0,43),('402880f0230992e7012309b3894b0068','402880f0230992e7012309b3892c003f','9090c357-1e01-489a-98f2-82f6722c72b3','自定义字段06',NULL,15,NULL,0,48),('402880f0230992e7012309b3894b0069','402880f0230992e7012309b3892c003f','920a8c56-4b13-4d59-bc26-1475112e2ae4','邮箱',NULL,30,NULL,0,28),('402880f0230992e7012309b3894b006b','402880f0230992e7012309b3892c003f','9ab7ecb6-f2d6-4477-8872-3064283e0831','入职日期','yyyy-MM-dd',12,NULL,0,14),('402880f0230992e7012309b3894b006c','402880f0230992e7012309b3892c003f','9c45b911-09c1-4823-b5e2-ee11c1955968','一级部门',NULL,12,NULL,0,6),('402880f0230992e7012309b3894b006d','402880f0230992e7012309b3892c003f','9c884ac3-a2c7-4fbb-89c3-3f6a49ce8d50','自定义字段03',NULL,15,NULL,0,45),('402880f0230992e7012309b3894b006e','402880f0230992e7012309b3892c003f','9f6f08d0-db01-4959-b697-a7ac11747874','自定义字段15',NULL,15,NULL,0,57),('402880f0230992e7012309b3894b006f','402880f0230992e7012309b3892c003f','a03da18b-57f9-4295-88a3-fb16bafa3eec','状态',NULL,10,NULL,0,41),('402880f0230992e7012309b3894b0070','402880f0230992e7012309b3892c003f','a4dc5c19-b59c-4f28-9195-e1f74418583f','性别',NULL,6,NULL,0,3),('402880f0230992e7012309b3894b0071','402880f0230992e7012309b3892c003f','a5e15eb7-b4fb-42e3-a351-f57102c3f169','自定义字段05',NULL,15,NULL,0,47),('402880f0230992e7012309b3894b0072','402880f0230992e7012309b3892c003f','acf4af89-a206-481d-9332-159f61fb3247','家庭电话',NULL,16,NULL,0,32),('402880f0230992e7012309b3894b0073','402880f0230992e7012309b3892c003f','af1b2804-72de-4c80-8198-34b77e384c61','生日','yyyy-MM-dd',12,NULL,0,2),('402880f0230992e7012309b3894b0074','402880f0230992e7012309b3892c003f','b42dc96d-75b4-4dcc-a4c2-2532d6e3de85','家庭地址邮编',NULL,10,NULL,0,36),('402880f0230992e7012309b3894b0075','402880f0230992e7012309b3892c003f','b8957c7e-af44-4757-817b-c60c030ed018','自定义字段08',NULL,15,NULL,0,50),('402880f0230992e7012309b3894b0076','402880f0230992e7012309b3892c003f','bc07cfcb-d9c6-49ae-825e-f0e5d726d764','户口所在地',NULL,26,NULL,0,25),('402880f0230992e7012309b3894b0077','402880f0230992e7012309b3892c003f','bd2b474c-b7bf-4292-a386-42feb49ea2c7','二级部门',NULL,12,NULL,0,7),('402880f0230992e7012309b3894b0078','402880f0230992e7012309b3892c003f','c7a8ebe7-e39a-40b1-87de-c97c649b73c6','婚否',NULL,6,NULL,0,4),('402880f0230992e7012309b3894b007a','402880f0230992e7012309b3892c003f','d3ae8b8f-f0f6-47bb-8b83-6efb051f6c3d','证件种类',NULL,10,NULL,0,16),('402880f0230992e7012309b3894b007b','402880f0230992e7012309b3892c003f','d70ac137-348b-4fbe-8f9e-d28fe9ed067e','当前住址',NULL,30,NULL,0,33),('402880f0230992e7012309b3894b007c','402880f0230992e7012309b3892c003f','e0f2623f-c827-4eeb-b624-e15f2a15ac77','参加工作日期','yyyy-MM-dd',12,NULL,0,13),('402880f0230992e7012309b3894b007d','402880f0230992e7012309b3892c003f','e25b05cb-3bf1-4c5f-8538-5712f91d4ccf','工号',NULL,10,NULL,0,0),('402880f0230992e7012309b3894b007e','402880f0230992e7012309b3892c003f','e9295f7c-b45e-4a1c-afb9-9dec3d2a6676','政治面貌',NULL,10,NULL,0,19),('402880f0230992e7012309b3894b007f','402880f0230992e7012309b3892c003f','ebe62cef-3420-49ce-9d2c-563ae3907306','MSN/QQ',NULL,30,NULL,0,29),('402880f0230992e7012309b3895b0080','402880f0230992e7012309b3892c003f','f38afe04-1331-4bcd-82a1-9b971932434b','自定义字段10',NULL,15,NULL,0,52),('402880f0230992e7012309b3895b0081','402880f0230992e7012309b3892c003f','fc01696a-faf6-40b2-b4bc-872e9e792100','证件号码',NULL,20,NULL,0,17),('402880f0230992e7012309b3895b0082','402880f0230992e7012309b3892c003f','fe2f9efc-e32b-45fb-83c0-8260f17f5db5','三级部门',NULL,16,NULL,0,8),('402880f0230992e7012309b3895b0083','402880f0230992e7012309b3892c003f','ff042d42-b873-4c36-abe0-114836e95a27','自定义字段02',NULL,15,NULL,0,44),('402880f0230992e7012309ba330b0085','402880f0230992e7012309ba32dc0084','02f27d3d-0226-4f4f-bce0-b009845a0aa2','职位描述',NULL,10,NULL,0,12),('402880f0230992e7012309ba330b0086','402880f0230992e7012309ba32dc0084','0bf7ec22-6f9c-4106-8327-9a593f0ce071','当前住址邮编',NULL,10,NULL,0,35),('402880f0230992e7012309ba330b0087','402880f0230992e7012309ba32dc0084','1720e1f1-028a-4b96-91b0-28d9ad6c6833','自定义字段12',NULL,15,NULL,0,55),('402880f0230992e7012309ba330b0088','402880f0230992e7012309ba32dc0084','18d92d44-03d6-4b0e-9e6c-66cdcc31185c','档案所在地',NULL,26,NULL,0,27),('402880f0230992e7012309ba330b0089','402880f0230992e7012309ba32dc0084','1aa37c05-c985-4a2c-93da-f00728689c8d','工作地区',NULL,12,NULL,0,10),('402880f0230992e7012309ba330b008b','402880f0230992e7012309ba32dc0084','1e3b814d-6dbf-4d2a-bcad-c5878f20f658','考勤卡号',NULL,12,NULL,0,41),('402880f0230992e7012309ba330b008c','402880f0230992e7012309ba32dc0084','1e4056ee-e76d-41c3-beb1-d265266cb74e','自定义字段16',NULL,15,NULL,0,59),('402880f0230992e7012309ba330b008e','402880f0230992e7012309ba32dc0084','284ec370-a94f-48be-a99c-2ddba0cf95a2','自定义字段13',NULL,15,NULL,0,56),('402880f0230992e7012309ba330b008f','402880f0230992e7012309ba32dc0084','29fa9dde-8ac0-4d38-894a-ba91360642c7','社保种类',NULL,12,NULL,0,28),('402880f0230992e7012309ba330b0090','402880f0230992e7012309ba32dc0084','2b2e7d2e-3bb6-40d6-8250-ff14ac05053e','姓名',NULL,10,NULL,0,3),('402880f0230992e7012309ba330b0091','402880f0230992e7012309ba32dc0084','36e1f3ed-2491-4a5e-8fc6-29f2643d0504','紧急联系方式',NULL,20,NULL,0,39),('402880f0230992e7012309ba330b0092','402880f0230992e7012309ba32dc0084','37080208-d2ec-41cb-81e4-c9c7dbe5e769','专业',NULL,20,NULL,0,23),('402880f0230992e7012309ba330b0093','402880f0230992e7012309ba32dc0084','3c094b2d-ffb6-463f-a39f-54fe6a5b9c96','学历',NULL,10,NULL,0,21),('402880f0230992e7012309ba330b0094','402880f0230992e7012309ba32dc0084','3e4329b4-2ff0-4a60-9b3d-87e334b0afc3','转正日期','yyyy-MM-dd',12,NULL,0,16),('402880f0230992e7012309ba330b0095','402880f0230992e7012309ba32dc0084','3e8ac800-5299-4aa8-8729-4711f35eae99','部门',NULL,16,NULL,1,0),('402880f0230992e7012309ba330b0097','402880f0230992e7012309ba32dc0084','40d805e5-bfc5-4586-9e4b-24e214086d84','毕业院校',NULL,20,NULL,0,22),('402880f0230992e7012309ba330b0098','402880f0230992e7012309ba32dc0084','4bdf0e2d-33fa-48e7-b19a-987c99698927','自定义字段11',NULL,15,NULL,0,54),('402880f0230992e7012309ba330b0099','402880f0230992e7012309ba32dc0084','4be1e261-bb39-4e41-a582-8d9754870be5','职位',NULL,10,'count',0,11),('402880f0230992e7012309ba330b009c','402880f0230992e7012309ba32dc0084','60fe5d52-dde3-45af-a1f5-bb86c0cb9e51','考勤方式',NULL,12,NULL,0,40),('402880f0230992e7012309ba330b009d','402880f0230992e7012309ba32dc0084','62c23858-d1e7-4cd6-a863-f9ea56a36636','自定义字段07',NULL,15,NULL,0,50),('402880f0230992e7012309ba330b009e','402880f0230992e7012309ba32dc0084','6320f072-34b3-4b8e-97c9-d10bcd5ff063','紧急联系人',NULL,10,NULL,0,38),('402880f0230992e7012309ba330b009f','402880f0230992e7012309ba32dc0084','63ced644-c668-4a0f-96cb-a7cb44710408','用工形式',NULL,12,NULL,0,13),('402880f0230992e7012309ba330b00a0','402880f0230992e7012309ba32dc0084','661b3c23-c64f-49c9-82b1-756af27e506f','民族',NULL,6,NULL,0,25),('402880f0230992e7012309ba330b00a2','402880f0230992e7012309ba32dc0084','6acc88dd-b708-405d-bf47-673d0b987195','自定义字段04',NULL,15,NULL,0,47),('402880f0230992e7012309ba330b00a3','402880f0230992e7012309ba32dc0084','6bb6d076-0b24-4424-bd83-1b420ccbc090','自定义字段14',NULL,15,NULL,0,57),('402880f0230992e7012309ba330b00a4','402880f0230992e7012309ba32dc0084','738239c7-00df-4944-921a-d31f06e933e6','工作电话',NULL,16,NULL,0,31),('402880f0230992e7012309ba330b00a6','402880f0230992e7012309ba32dc0084','7b82488b-0a80-40cf-92f9-425b68cfb4b8','家庭地址',NULL,30,NULL,0,36),('402880f0230992e7012309ba330b00a7','402880f0230992e7012309ba32dc0084','7dd9ecc0-32b6-40cc-8658-400f1fc159a2','自定义字段09',NULL,15,NULL,0,52),('402880f0230992e7012309ba330b00a8','402880f0230992e7012309ba32dc0084','839fbf2c-3db0-446b-bbf7-bc5c1f0bf751','手机',NULL,16,NULL,0,32),('402880f0230992e7012309ba330b00a9','402880f0230992e7012309ba32dc0084','83f80480-3f21-4eec-ba27-16d0407929a2','备注',NULL,20,NULL,0,43),('402880f0230992e7012309ba330b00aa','402880f0230992e7012309ba32dc0084','848dde83-4b7d-41a8-8ca6-685d8784f6d2','血型',NULL,6,NULL,0,19),('402880f0230992e7012309ba331b00ab','402880f0230992e7012309ba32dc0084','89195781-45a8-4365-a084-0b448c35b17b','籍贯',NULL,10,NULL,0,24),('402880f0230992e7012309ba331b00ac','402880f0230992e7012309ba32dc0084','8f9adecb-279b-42f3-a78a-5d94ca676372','自定义字段01',NULL,15,NULL,0,44),('402880f0230992e7012309ba331b00ad','402880f0230992e7012309ba32dc0084','9090c357-1e01-489a-98f2-82f6722c72b3','自定义字段06',NULL,15,NULL,0,49),('402880f0230992e7012309ba331b00ae','402880f0230992e7012309ba32dc0084','920a8c56-4b13-4d59-bc26-1475112e2ae4','邮箱',NULL,30,NULL,0,29),('402880f0230992e7012309ba331b00b0','402880f0230992e7012309ba32dc0084','9ab7ecb6-f2d6-4477-8872-3064283e0831','入职日期','yyyy-MM-dd',12,NULL,0,15),('402880f0230992e7012309ba331b00b2','402880f0230992e7012309ba32dc0084','9c884ac3-a2c7-4fbb-89c3-3f6a49ce8d50','自定义字段03',NULL,15,NULL,0,46),('402880f0230992e7012309ba331b00b3','402880f0230992e7012309ba32dc0084','9f6f08d0-db01-4959-b697-a7ac11747874','自定义字段15',NULL,15,NULL,0,58),('402880f0230992e7012309ba331b00b4','402880f0230992e7012309ba32dc0084','a03da18b-57f9-4295-88a3-fb16bafa3eec','状态',NULL,10,NULL,0,42),('402880f0230992e7012309ba331b00b5','402880f0230992e7012309ba32dc0084','a4dc5c19-b59c-4f28-9195-e1f74418583f','性别',NULL,6,NULL,1,1),('402880f0230992e7012309ba331b00b6','402880f0230992e7012309ba32dc0084','a5e15eb7-b4fb-42e3-a351-f57102c3f169','自定义字段05',NULL,15,NULL,0,48),('402880f0230992e7012309ba331b00b7','402880f0230992e7012309ba32dc0084','acf4af89-a206-481d-9332-159f61fb3247','家庭电话',NULL,16,NULL,0,33),('402880f0230992e7012309ba331b00b8','402880f0230992e7012309ba32dc0084','af1b2804-72de-4c80-8198-34b77e384c61','生日','yyyy-MM-dd',12,NULL,0,4),('402880f0230992e7012309ba331b00b9','402880f0230992e7012309ba32dc0084','b42dc96d-75b4-4dcc-a4c2-2532d6e3de85','家庭地址邮编',NULL,10,NULL,0,37),('402880f0230992e7012309ba331b00ba','402880f0230992e7012309ba32dc0084','b8957c7e-af44-4757-817b-c60c030ed018','自定义字段08',NULL,15,NULL,0,51),('402880f0230992e7012309ba331b00bb','402880f0230992e7012309ba32dc0084','bc07cfcb-d9c6-49ae-825e-f0e5d726d764','户口所在地',NULL,26,NULL,0,26),('402880f0230992e7012309ba331b00bd','402880f0230992e7012309ba32dc0084','c7a8ebe7-e39a-40b1-87de-c97c649b73c6','婚否',NULL,6,NULL,0,5),('402880f0230992e7012309ba331b00bf','402880f0230992e7012309ba32dc0084','d3ae8b8f-f0f6-47bb-8b83-6efb051f6c3d','证件种类',NULL,10,NULL,0,17),('402880f0230992e7012309ba331b00c0','402880f0230992e7012309ba32dc0084','d70ac137-348b-4fbe-8f9e-d28fe9ed067e','当前住址',NULL,30,NULL,0,34),('402880f0230992e7012309ba331b00c1','402880f0230992e7012309ba32dc0084','e0f2623f-c827-4eeb-b624-e15f2a15ac77','参加工作日期','yyyy-MM-dd',12,NULL,0,14),('402880f0230992e7012309ba331b00c2','402880f0230992e7012309ba32dc0084','e25b05cb-3bf1-4c5f-8538-5712f91d4ccf','工号-员工人数',NULL,10,'count',0,2),('402880f0230992e7012309ba331b00c3','402880f0230992e7012309ba32dc0084','e9295f7c-b45e-4a1c-afb9-9dec3d2a6676','政治面貌',NULL,10,NULL,0,20),('402880f0230992e7012309ba331b00c4','402880f0230992e7012309ba32dc0084','ebe62cef-3420-49ce-9d2c-563ae3907306','MSN/QQ',NULL,30,NULL,0,30),('402880f0230992e7012309ba331b00c5','402880f0230992e7012309ba32dc0084','f38afe04-1331-4bcd-82a1-9b971932434b','自定义字段10',NULL,15,NULL,0,53),('402880f0230992e7012309ba331b00c6','402880f0230992e7012309ba32dc0084','fc01696a-faf6-40b2-b4bc-872e9e792100','证件号码',NULL,20,NULL,0,18),('402880f0230992e7012309ba331b00c7','402880f0230992e7012309ba32dc0084','fe2f9efc-e32b-45fb-83c0-8260f17f5db5','三级部门',NULL,16,NULL,0,9),('402880f0230992e7012309ba331b00c8','402880f0230992e7012309ba32dc0084','ff042d42-b873-4c36-abe0-114836e95a27','自定义字段02',NULL,15,NULL,0,45),('402880f0230992e7012309ca914a00ca','402880f0230992e7012309ca912b00c9','08b10aa9-500d-4068-a1e4-1d0283b6fb73','自定义字段14',NULL,15,NULL,0,61),('402880f0230992e7012309ca914a00cb','402880f0230992e7012309ca912b00c9','08ce8fbc-4211-412b-b5f3-3fe916799a97','工作地区',NULL,12,NULL,0,15),('402880f0230992e7012309ca914a00cc','402880f0230992e7012309ca912b00c9','09437a74-7d5d-4456-acba-869cf84edbef','自定义字段06',NULL,15,NULL,0,53),('402880f0230992e7012309ca914a00cd','402880f0230992e7012309ca912b00c9','0997954c-e1eb-4051-b019-ecf7fb2d1a97','政治面貌',NULL,10,NULL,0,24),('402880f0230992e7012309ca914a00ce','402880f0230992e7012309ca912b00c9','0a3a151a-8d84-4fef-9ef7-ba3528a043da','学历',NULL,10,NULL,0,25),('402880f0230992e7012309ca914a00cf','402880f0230992e7012309ca912b00c9','0bd555a4-8350-4599-a5a3-8ae395e7a783','紧急联系人',NULL,10,NULL,0,42),('402880f0230992e7012309ca914a00d0','402880f0230992e7012309ca912b00c9','1946eea1-bbbf-420a-a711-65e832c808d6','部门',NULL,16,NULL,0,11),('402880f0230992e7012309ca914a00d1','402880f0230992e7012309ca912b00c9','1dc105a5-c169-49b1-867a-137a863e3bf3','入职日期','yyyy-MM-dd',12,NULL,0,19),('402880f0230992e7012309ca914a00d2','402880f0230992e7012309ca912b00c9','22648ff9-f2a0-4ee1-bcdf-302811041cc5','自定义字段13',NULL,15,NULL,0,60),('402880f0230992e7012309ca914a00d4','402880f0230992e7012309ca912b00c9','28adc410-29cf-4cce-b670-0053b89e3431','证件号码',NULL,20,NULL,0,22),('402880f0230992e7012309ca914a00d5','402880f0230992e7012309ca912b00c9','28fd489d-2882-4b4e-be16-518f9094feab','离职原因',NULL,16,NULL,0,5),('402880f0230992e7012309ca914a00d6','402880f0230992e7012309ca912b00c9','2c0b0089-3be2-406b-a3dc-c2c196cd1945','毕业院校',NULL,20,NULL,0,26),('402880f0230992e7012309ca914a00d7','402880f0230992e7012309ca912b00c9','303777df-9ffc-44b5-bd59-af53ec7e059f','离职种类',NULL,12,NULL,0,3),('402880f0230992e7012309ca914a00d8','402880f0230992e7012309ca912b00c9','37121620-7dcc-4ea3-88f8-398546fb6eba','户口所在地',NULL,26,NULL,0,30),('402880f0230992e7012309ca914a00d9','402880f0230992e7012309ca912b00c9','3ac9ffa7-dba6-4254-84f3-a363eed11b3f','手机',NULL,16,NULL,0,36),('402880f0230992e7012309ca914a00da','402880f0230992e7012309ca912b00c9','3f3606a1-bf8d-478a-8378-4d3e1950046a','自定义字段09',NULL,15,NULL,0,56),('402880f0230992e7012309ca914a00db','402880f0230992e7012309ca912b00c9','402880f02304355e0123072dee55000a','职位描述',NULL,10,NULL,0,16),('402880f0230992e7012309ca914a00dd','402880f0230992e7012309ca912b00c9','426e9415-a8e7-4c0a-b64a-2baa2c91459a','工作电话',NULL,16,NULL,0,35),('402880f0230992e7012309ca914a00de','402880f0230992e7012309ca912b00c9','483698ee-7479-4f6b-bdf3-aa2cf4944d6d','自定义字段08',NULL,15,NULL,0,55),('402880f0230992e7012309ca914a00df','402880f0230992e7012309ca912b00c9','4bc5077b-5607-4a9c-8d62-aea235cb9e30','离职日期','yyyy-MM-dd',12,NULL,0,2),('402880f0230992e7012309ca914a00e0','402880f0230992e7012309ca912b00c9','52caf51e-3089-4a0b-8834-4d392cf2b33a','自定义字段10',NULL,15,NULL,0,57),('402880f0230992e7012309ca914a00e1','402880f0230992e7012309ca912b00c9','53204224-67eb-4eb5-a9b4-102cfe504f38','三级部门',NULL,16,NULL,0,14),('402880f0230992e7012309ca914a00e2','402880f0230992e7012309ca912b00c9','5762ec72-3fa8-4211-9e8e-783d994b9911','婚否',NULL,6,NULL,0,9),('402880f0230992e7012309ca914a00e3','402880f0230992e7012309ca912b00c9','5a9361bd-264e-4943-a431-53bc4913f819','二级部门',NULL,12,NULL,0,13),('402880f0230992e7012309ca914a00e4','402880f0230992e7012309ca912b00c9','5ce94398-aaba-4b0d-b8a9-9c75bee0fd0f','自定义字段03',NULL,15,NULL,0,50),('402880f0230992e7012309ca914a00e5','402880f0230992e7012309ca912b00c9','620fee4e-ec45-4cde-9203-b2222b93646e','证件种类',NULL,10,NULL,0,21),('402880f0230992e7012309ca914a00e6','402880f0230992e7012309ca912b00c9','6c49aa8c-d371-4e34-8d26-c499e51985b7','参加工作日期','yyyy-MM-dd',12,NULL,0,18),('402880f0230992e7012309ca914a00e7','402880f0230992e7012309ca912b00c9','6dee72b0-df7a-47ab-b35a-05743d72b45b','籍贯',NULL,10,NULL,0,28),('402880f0230992e7012309ca914a00e8','402880f0230992e7012309ca912b00c9','6ef0c8d3-ef3f-4390-901c-35b41b3b24db','工号',NULL,10,NULL,0,0),('402880f0230992e7012309ca914a00ea','402880f0230992e7012309ca912b00c9','71852d09-a6ca-4838-9d69-8ce9fba04a4a','离职审批人',NULL,10,NULL,0,4),('402880f0230992e7012309ca914a00eb','402880f0230992e7012309ca912b00c9','7201920f-dd98-4273-92e4-599beb1a08ea','档案所在地',NULL,26,NULL,0,31),('402880f0230992e7012309ca914a00ec','402880f0230992e7012309ca912b00c9','72ea1da4-5332-49ab-bd0b-ea2142c6b666','分公司',NULL,16,NULL,0,10),('402880f0230992e7012309ca915a00ed','402880f0230992e7012309ca912b00c9','758e84a6-60b4-48db-b71d-b0dd37613540','考勤卡号',NULL,12,NULL,0,45),('402880f0230992e7012309ca915a00ee','402880f0230992e7012309ca912b00c9','786c82f4-ec15-40a8-923a-c19fc32082f1','紧急联系方式',NULL,20,NULL,0,43),('402880f0230992e7012309ca915a00ef','402880f0230992e7012309ca912b00c9','8001dc94-796d-4736-b080-6c20ea675620','家庭地址',NULL,30,NULL,0,40),('402880f0230992e7012309ca915a00f0','402880f0230992e7012309ca912b00c9','8289aa85-4ae9-4fca-b0b5-181f3e2d859b','一级部门',NULL,12,NULL,0,12),('402880f0230992e7012309ca915a00f1','402880f0230992e7012309ca912b00c9','8429b061-ac1f-41bd-b61e-649c2620f72e','自定义字段15',NULL,15,NULL,0,62),('402880f0230992e7012309ca915a00f2','402880f0230992e7012309ca912b00c9','8a49d1f7-3862-4f04-875b-53789998564f','血型',NULL,6,NULL,0,23),('402880f0230992e7012309ca915a00f3','402880f0230992e7012309ca912b00c9','8e7a11d1-8a39-4da1-b335-674cd41086fe','考勤方式',NULL,12,NULL,0,44),('402880f0230992e7012309ca915a00f4','402880f0230992e7012309ca912b00c9','8f280fdb-2eb2-4a8a-890e-415c8c3ecafa','社保种类',NULL,12,NULL,0,32),('402880f0230992e7012309ca915a00f5','402880f0230992e7012309ca912b00c9','9167dd9d-194a-4aa6-9d5f-fd06d4ded7b1','当前住址',NULL,30,NULL,0,38),('402880f0230992e7012309ca915a00f7','402880f0230992e7012309ca912b00c9','a0489f0e-0534-435d-bb80-77a7b611c6a0','姓名',NULL,10,NULL,0,1),('402880f0230992e7012309ca915a00f8','402880f0230992e7012309ca912b00c9','a71cc0cf-8217-4a46-a5f6-b823bf1c4fa4','自定义字段07',NULL,15,NULL,0,54),('402880f0230992e7012309ca915a00f9','402880f0230992e7012309ca912b00c9','a924677c-29fe-4727-8ebe-a1bb77596141','生日','yyyy-MM-dd',12,NULL,0,7),('402880f0230992e7012309ca915a00fa','402880f0230992e7012309ca912b00c9','aa23fef7-8b6a-4943-bd83-50e03a06e35a','自定义字段16',NULL,15,NULL,0,63),('402880f0230992e7012309ca915a00fb','402880f0230992e7012309ca912b00c9','aa67d3ca-995e-441c-b245-157fa74ce34a','状态',NULL,10,NULL,0,46),('402880f0230992e7012309ca915a00fc','402880f0230992e7012309ca912b00c9','ab0b8a6a-ec3e-4e7a-96ad-5068f764d054','MSN/QQ',NULL,30,NULL,0,34),('402880f0230992e7012309ca915a00fd','402880f0230992e7012309ca912b00c9','b0602d22-a005-49dc-9ba8-9ef75f3ee478','家庭电话',NULL,16,NULL,0,37),('402880f0230992e7012309ca915a00ff','402880f0230992e7012309ca912b00c9','be926824-829e-4083-82a7-545d938ef7e6','自定义字段02',NULL,15,NULL,0,49),('402880f0230992e7012309ca915a0100','402880f0230992e7012309ca912b00c9','c9107a25-0144-4698-bca3-56b8604cad5f','自定义字段12',NULL,15,NULL,0,59),('402880f0230992e7012309ca915a0101','402880f0230992e7012309ca912b00c9','cb47ac01-3de9-49bd-8c5e-d41e726abcc4','性别',NULL,6,NULL,0,8),('402880f0230992e7012309ca915a0102','402880f0230992e7012309ca912b00c9','cc929da2-0f5d-4a1f-ac29-e57883c0b8a0','自定义字段11',NULL,15,NULL,0,58),('402880f0230992e7012309ca915a0103','402880f0230992e7012309ca912b00c9','ccd34d2d-55ca-42b1-9cae-0d6c29ca17a1','民族',NULL,6,NULL,0,29),('402880f0230992e7012309ca915a0104','402880f0230992e7012309ca912b00c9','d0cde5c5-88b9-4944-8f51-8cb6ee957a58','自定义字段04',NULL,15,NULL,0,51),('402880f0230992e7012309ca915a0106','402880f0230992e7012309ca912b00c9','d9322f29-3e75-49c5-b7c6-09205866441f','转正日期','yyyy-MM-dd',12,NULL,0,20),('402880f0230992e7012309ca915a0108','402880f0230992e7012309ca912b00c9','db18b49f-b0aa-4820-9abb-fd375d6f4d07','专业',NULL,20,NULL,0,27),('402880f0230992e7012309ca915a010a','402880f0230992e7012309ca912b00c9','e2c41ac8-289f-4300-940b-d86abb8bdabc','备注',NULL,20,NULL,0,47),('402880f0230992e7012309ca915a010b','402880f0230992e7012309ca912b00c9','e7561987-a5a1-4965-90ea-a8aab87b5018','邮箱',NULL,30,NULL,0,33),('402880f0230992e7012309ca915a010c','402880f0230992e7012309ca912b00c9','e932027b-fbb9-49f3-a477-5a5763351cb4','用工形式',NULL,12,NULL,0,17),('402880f0230992e7012309ca915a010d','402880f0230992e7012309ca912b00c9','eda22534-4023-4f04-b070-ebb80a4db7cb','离职备注',NULL,20,NULL,0,6),('402880f0230992e7012309ca915a010e','402880f0230992e7012309ca912b00c9','f8668d0c-1fa7-4365-986e-a24057d3ec7f','自定义字段01',NULL,15,NULL,0,48),('402880f0230992e7012309ca915a010f','402880f0230992e7012309ca912b00c9','fb0c2f6a-7611-4177-a5d8-c8e7bae4e7b7','家庭地址邮编',NULL,10,NULL,0,41),('402880f0230992e7012309ca915a0110','402880f0230992e7012309ca912b00c9','fba9b9f1-d6dd-4c24-b20d-6b051bc6bd8a','自定义字段05',NULL,15,NULL,0,52),('402880f0230992e7012309ca915a0111','402880f0230992e7012309ca912b00c9','fd878218-2c57-453c-a4e6-be606bae7388','当前住址邮编',NULL,10,NULL,0,39),('402880f0230992e7012309cdc7cd0114','402880f0230992e7012309cdc7ae0113','08b10aa9-500d-4068-a1e4-1d0283b6fb73','自定义字段14',NULL,15,NULL,0,61),('402880f0230992e7012309cdc7cd0115','402880f0230992e7012309cdc7ae0113','08ce8fbc-4211-412b-b5f3-3fe916799a97','工作地区',NULL,12,NULL,0,15),('402880f0230992e7012309cdc7cd0116','402880f0230992e7012309cdc7ae0113','09437a74-7d5d-4456-acba-869cf84edbef','自定义字段06',NULL,15,NULL,0,53),('402880f0230992e7012309cdc7cd0117','402880f0230992e7012309cdc7ae0113','0997954c-e1eb-4051-b019-ecf7fb2d1a97','政治面貌',NULL,10,NULL,0,24),('402880f0230992e7012309cdc7cd0118','402880f0230992e7012309cdc7ae0113','0a3a151a-8d84-4fef-9ef7-ba3528a043da','学历',NULL,10,NULL,0,25),('402880f0230992e7012309cdc7cd0119','402880f0230992e7012309cdc7ae0113','0bd555a4-8350-4599-a5a3-8ae395e7a783','紧急联系人',NULL,10,NULL,0,42),('402880f0230992e7012309cdc7cd011a','402880f0230992e7012309cdc7ae0113','1946eea1-bbbf-420a-a711-65e832c808d6','部门',NULL,16,NULL,0,11),('402880f0230992e7012309cdc7cd011b','402880f0230992e7012309cdc7ae0113','1dc105a5-c169-49b1-867a-137a863e3bf3','入职日期','yyyy-MM-dd',12,NULL,0,19),('402880f0230992e7012309cdc7cd011c','402880f0230992e7012309cdc7ae0113','22648ff9-f2a0-4ee1-bcdf-302811041cc5','自定义字段13',NULL,15,NULL,0,60),('402880f0230992e7012309cdc7cd011e','402880f0230992e7012309cdc7ae0113','28adc410-29cf-4cce-b670-0053b89e3431','证件号码',NULL,20,NULL,0,22),('402880f0230992e7012309cdc7cd011f','402880f0230992e7012309cdc7ae0113','28fd489d-2882-4b4e-be16-518f9094feab','离职原因',NULL,16,NULL,1,0),('402880f0230992e7012309cdc7cd0120','402880f0230992e7012309cdc7ae0113','2c0b0089-3be2-406b-a3dc-c2c196cd1945','毕业院校',NULL,20,NULL,0,26),('402880f0230992e7012309cdc7cd0121','402880f0230992e7012309cdc7ae0113','303777df-9ffc-44b5-bd59-af53ec7e059f','离职种类',NULL,12,NULL,3,1),('402880f0230992e7012309cdc7cd0122','402880f0230992e7012309cdc7ae0113','37121620-7dcc-4ea3-88f8-398546fb6eba','户口所在地',NULL,26,NULL,0,30),('402880f0230992e7012309cdc7cd0123','402880f0230992e7012309cdc7ae0113','3ac9ffa7-dba6-4254-84f3-a363eed11b3f','手机',NULL,16,NULL,0,36),('402880f0230992e7012309cdc7cd0124','402880f0230992e7012309cdc7ae0113','3f3606a1-bf8d-478a-8378-4d3e1950046a','自定义字段09',NULL,15,NULL,0,56),('402880f0230992e7012309cdc7cd0125','402880f0230992e7012309cdc7ae0113','402880f02304355e0123072dee55000a','职位描述',NULL,10,NULL,0,16),('402880f0230992e7012309cdc7cd0127','402880f0230992e7012309cdc7ae0113','426e9415-a8e7-4c0a-b64a-2baa2c91459a','工作电话',NULL,16,NULL,0,35),('402880f0230992e7012309cdc7cd0128','402880f0230992e7012309cdc7ae0113','483698ee-7479-4f6b-bdf3-aa2cf4944d6d','自定义字段08',NULL,15,NULL,0,55),('402880f0230992e7012309cdc7cd0129','402880f0230992e7012309cdc7ae0113','4bc5077b-5607-4a9c-8d62-aea235cb9e30','离职日期','yyyy-MM-dd',12,NULL,0,4),('402880f0230992e7012309cdc7cd012a','402880f0230992e7012309cdc7ae0113','52caf51e-3089-4a0b-8834-4d392cf2b33a','自定义字段10',NULL,15,NULL,0,57),('402880f0230992e7012309cdc7cd012b','402880f0230992e7012309cdc7ae0113','53204224-67eb-4eb5-a9b4-102cfe504f38','三级部门',NULL,16,NULL,0,14),('402880f0230992e7012309cdc7cd012c','402880f0230992e7012309cdc7ae0113','5762ec72-3fa8-4211-9e8e-783d994b9911','婚否',NULL,6,NULL,0,9),('402880f0230992e7012309cdc7cd012d','402880f0230992e7012309cdc7ae0113','5a9361bd-264e-4943-a431-53bc4913f819','二级部门',NULL,12,NULL,0,13),('402880f0230992e7012309cdc7cd012e','402880f0230992e7012309cdc7ae0113','5ce94398-aaba-4b0d-b8a9-9c75bee0fd0f','自定义字段03',NULL,15,NULL,0,50),('402880f0230992e7012309cdc7cd012f','402880f0230992e7012309cdc7ae0113','620fee4e-ec45-4cde-9203-b2222b93646e','证件种类',NULL,10,NULL,0,21),('402880f0230992e7012309cdc7cd0130','402880f0230992e7012309cdc7ae0113','6c49aa8c-d371-4e34-8d26-c499e51985b7','参加工作日期','yyyy-MM-dd',12,NULL,0,18),('402880f0230992e7012309cdc7cd0131','402880f0230992e7012309cdc7ae0113','6dee72b0-df7a-47ab-b35a-05743d72b45b','籍贯',NULL,10,NULL,0,28),('402880f0230992e7012309cdc7cd0132','402880f0230992e7012309cdc7ae0113','6ef0c8d3-ef3f-4390-901c-35b41b3b24db','工号-离职人数',NULL,10,'count',3,2),('402880f0230992e7012309cdc7cd0134','402880f0230992e7012309cdc7ae0113','71852d09-a6ca-4838-9d69-8ce9fba04a4a','离职审批人',NULL,10,NULL,0,5),('402880f0230992e7012309cdc7cd0135','402880f0230992e7012309cdc7ae0113','7201920f-dd98-4273-92e4-599beb1a08ea','档案所在地',NULL,26,NULL,0,31),('402880f0230992e7012309cdc7cd0136','402880f0230992e7012309cdc7ae0113','72ea1da4-5332-49ab-bd0b-ea2142c6b666','分公司',NULL,16,NULL,0,10),('402880f0230992e7012309cdc7cd0137','402880f0230992e7012309cdc7ae0113','758e84a6-60b4-48db-b71d-b0dd37613540','考勤卡号',NULL,12,NULL,0,45),('402880f0230992e7012309cdc7cd0138','402880f0230992e7012309cdc7ae0113','786c82f4-ec15-40a8-923a-c19fc32082f1','紧急联系方式',NULL,20,NULL,0,43),('402880f0230992e7012309cdc7cd0139','402880f0230992e7012309cdc7ae0113','8001dc94-796d-4736-b080-6c20ea675620','家庭地址',NULL,30,NULL,0,40),('402880f0230992e7012309cdc7cd013a','402880f0230992e7012309cdc7ae0113','8289aa85-4ae9-4fca-b0b5-181f3e2d859b','一级部门',NULL,12,NULL,0,12),('402880f0230992e7012309cdc7cd013b','402880f0230992e7012309cdc7ae0113','8429b061-ac1f-41bd-b61e-649c2620f72e','自定义字段15',NULL,15,NULL,0,62),('402880f0230992e7012309cdc7cd013c','402880f0230992e7012309cdc7ae0113','8a49d1f7-3862-4f04-875b-53789998564f','血型',NULL,6,NULL,0,23),('402880f0230992e7012309cdc7cd013d','402880f0230992e7012309cdc7ae0113','8e7a11d1-8a39-4da1-b335-674cd41086fe','考勤方式',NULL,12,NULL,0,44),('402880f0230992e7012309cdc7cd013e','402880f0230992e7012309cdc7ae0113','8f280fdb-2eb2-4a8a-890e-415c8c3ecafa','社保种类',NULL,12,NULL,0,32),('402880f0230992e7012309cdc7cd013f','402880f0230992e7012309cdc7ae0113','9167dd9d-194a-4aa6-9d5f-fd06d4ded7b1','当前住址',NULL,30,NULL,0,38),('402880f0230992e7012309cdc7cd0141','402880f0230992e7012309cdc7ae0113','a0489f0e-0534-435d-bb80-77a7b611c6a0','姓名',NULL,10,NULL,0,3),('402880f0230992e7012309cdc7cd0142','402880f0230992e7012309cdc7ae0113','a71cc0cf-8217-4a46-a5f6-b823bf1c4fa4','自定义字段07',NULL,15,NULL,0,54),('402880f0230992e7012309cdc7cd0143','402880f0230992e7012309cdc7ae0113','a924677c-29fe-4727-8ebe-a1bb77596141','生日','yyyy-MM-dd',12,NULL,0,7),('402880f0230992e7012309cdc7cd0144','402880f0230992e7012309cdc7ae0113','aa23fef7-8b6a-4943-bd83-50e03a06e35a','自定义字段16',NULL,15,NULL,0,63),('402880f0230992e7012309cdc7cd0145','402880f0230992e7012309cdc7ae0113','aa67d3ca-995e-441c-b245-157fa74ce34a','状态',NULL,10,NULL,0,46),('402880f0230992e7012309cdc7cd0146','402880f0230992e7012309cdc7ae0113','ab0b8a6a-ec3e-4e7a-96ad-5068f764d054','MSN/QQ',NULL,30,NULL,0,34),('402880f0230992e7012309cdc7cd0147','402880f0230992e7012309cdc7ae0113','b0602d22-a005-49dc-9ba8-9ef75f3ee478','家庭电话',NULL,16,NULL,0,37),('402880f0230992e7012309cdc7cd0149','402880f0230992e7012309cdc7ae0113','be926824-829e-4083-82a7-545d938ef7e6','自定义字段02',NULL,15,NULL,0,49),('402880f0230992e7012309cdc7cd014a','402880f0230992e7012309cdc7ae0113','c9107a25-0144-4698-bca3-56b8604cad5f','自定义字段12',NULL,15,NULL,0,59),('402880f0230992e7012309cdc7cd014b','402880f0230992e7012309cdc7ae0113','cb47ac01-3de9-49bd-8c5e-d41e726abcc4','性别',NULL,6,NULL,0,8),('402880f0230992e7012309cdc7cd014c','402880f0230992e7012309cdc7ae0113','cc929da2-0f5d-4a1f-ac29-e57883c0b8a0','自定义字段11',NULL,15,NULL,0,58),('402880f0230992e7012309cdc7cd014d','402880f0230992e7012309cdc7ae0113','ccd34d2d-55ca-42b1-9cae-0d6c29ca17a1','民族',NULL,6,NULL,0,29),('402880f0230992e7012309cdc7cd014e','402880f0230992e7012309cdc7ae0113','d0cde5c5-88b9-4944-8f51-8cb6ee957a58','自定义字段04',NULL,15,NULL,0,51),('402880f0230992e7012309cdc7cd0150','402880f0230992e7012309cdc7ae0113','d9322f29-3e75-49c5-b7c6-09205866441f','转正日期','yyyy-MM-dd',12,NULL,0,20),('402880f0230992e7012309cdc7cd0152','402880f0230992e7012309cdc7ae0113','db18b49f-b0aa-4820-9abb-fd375d6f4d07','专业',NULL,20,NULL,0,27),('402880f0230992e7012309cdc7cd0154','402880f0230992e7012309cdc7ae0113','e2c41ac8-289f-4300-940b-d86abb8bdabc','备注',NULL,20,NULL,0,47),('402880f0230992e7012309cdc7cd0155','402880f0230992e7012309cdc7ae0113','e7561987-a5a1-4965-90ea-a8aab87b5018','邮箱',NULL,30,NULL,0,33),('402880f0230992e7012309cdc7cd0156','402880f0230992e7012309cdc7ae0113','e932027b-fbb9-49f3-a477-5a5763351cb4','用工形式',NULL,12,NULL,0,17),('402880f0230992e7012309cdc7dd0157','402880f0230992e7012309cdc7ae0113','eda22534-4023-4f04-b070-ebb80a4db7cb','离职备注',NULL,20,NULL,0,6),('402880f0230992e7012309cdc7dd0158','402880f0230992e7012309cdc7ae0113','f8668d0c-1fa7-4365-986e-a24057d3ec7f','自定义字段01',NULL,15,NULL,0,48),('402880f0230992e7012309cdc7dd0159','402880f0230992e7012309cdc7ae0113','fb0c2f6a-7611-4177-a5d8-c8e7bae4e7b7','家庭地址邮编',NULL,10,NULL,0,41),('402880f0230992e7012309cdc7dd015a','402880f0230992e7012309cdc7ae0113','fba9b9f1-d6dd-4c24-b20d-6b051bc6bd8a','自定义字段05',NULL,15,NULL,0,52),('402880f0230992e7012309cdc7dd015b','402880f0230992e7012309cdc7ae0113','fd878218-2c57-453c-a4e6-be606bae7388','当前住址邮编',NULL,10,NULL,0,39),('402880f0230992e7012309d6c6b80162','402880f0230992e7012309d6c689015d','1703bdeb-43d6-434c-8079-adc5c89d5daa','入职日期','yyyy-MM-dd',12,NULL,0,11),('402880f0230992e7012309d6c6b80165','402880f0230992e7012309d6c689015d','1d2d72e1-9ad5-4e95-8c38-5dbdbd1640c1','三级部门',NULL,16,NULL,0,6),('402880f0230992e7012309d6c6b80167','402880f0230992e7012309d6c689015d','29ed807d-2d4e-459d-8905-5725bdf6165e','姓名',NULL,10,NULL,0,1),('402880f0230992e7012309d6c6b8016e','402880f0230992e7012309d6c689015d','402880f02304355e012307827aec000f','职位描述',NULL,10,NULL,0,8),('402880f0230992e7012309d6c6b80180','402880f0230992e7012309d6c689015d','403b3549-b28f-458a-a956-d1a65e73b9ef','用工形式',NULL,12,NULL,0,9),('402880f0230992e7012309d6c6b80181','402880f0230992e7012309d6c689015d','428f4a57-19d9-44a0-93e6-70505acc0336','合同期限',NULL,10,NULL,0,16),('402880f0230992e7012309d6c6b80182','402880f0230992e7012309d6c689015d','48b83abc-5780-4224-86f2-0be97d1e577d','合同种类',NULL,12,NULL,0,14),('402880f0230992e7012309d6c6b80183','402880f0230992e7012309d6c689015d','4b81740a-fb5f-41e3-9938-ba47c34c234a','合同编号',NULL,12,NULL,0,15),('402880f0230992e7012309d6c6b80186','402880f0230992e7012309d6c689015d','5f7b2fd2-67a8-4eca-8e95-88dd3ae137a9','参加工作日期','yyyy-MM-dd',12,NULL,0,10),('402880f0230992e7012309d6c6b8018e','402880f0230992e7012309d6c689015d','84079380-e5af-426e-86ce-1bdfaf1ed6ff','转正日期','yyyy-MM-dd',12,NULL,0,12),('402880f0230992e7012309d6c6b80191','402880f0230992e7012309d6c689015d','88577c5a-a705-4d6f-b3f3-906b640bfea2','合同结束日期','yyyy-MM-dd',12,NULL,0,18),('402880f0230992e7012309d6c6b80196','402880f0230992e7012309d6c689015d','9e526398-25fa-4170-aaec-cc63fbe338de','工号',NULL,10,NULL,0,0),('402880f0230992e7012309d6c6b80197','402880f0230992e7012309d6c689015d','a23d0118-5130-4283-89ff-09f0b33a5cd8','公司司龄','1',10,NULL,0,13),('402880f0230992e7012309d6c6b8019b','402880f0230992e7012309d6c689015d','b0cb27ec-b4da-4251-b82e-b3d461e9f4d3','工作地区',NULL,12,NULL,0,7),('402880f0230992e7012309d6c6b8019c','402880f0230992e7012309d6c689015d','b87791f4-ab9b-4aa1-a5f4-b161575ce26a','合同备注',NULL,20,NULL,0,20),('402880f0230992e7012309d6c6b8019e','402880f0230992e7012309d6c689015d','bca3c812-fc94-4655-bfa7-7693b97b9ddf','合同状态',NULL,10,NULL,0,19),('402880f0230992e7012309d6c6b801a0','402880f0230992e7012309d6c689015d','bfd320c2-01d5-432b-9a3f-fe2f48231fac','二级部门',NULL,12,NULL,0,5),('402880f0230992e7012309d6c6b801a1','402880f0230992e7012309d6c689015d','c16731d5-c67a-4ed4-a9c8-bbb01ec2d554','部门',NULL,16,NULL,0,3),('402880f0230992e7012309d6c6b801a2','402880f0230992e7012309d6c689015d','cce6a3e1-1956-4e31-86fe-38870437e60c','分公司',NULL,16,NULL,0,2),('402880f0230992e7012309d6c6b801a3','402880f0230992e7012309d6c689015d','cd794a47-c1b2-4caf-91e7-84a9b929ce66','一级部门',NULL,12,NULL,0,4),('402880f0230992e7012309d6c6b801a8','402880f0230992e7012309d6c689015d','fbacd67e-c45f-4429-ace2-4403f98be992','合同起始日期','yyyy-MM-dd',12,NULL,0,17),('402880f0230992e7012309d97b7801af','402880f0230992e7012309d979f101aa','1703bdeb-43d6-434c-8079-adc5c89d5daa','入职日期','yyyy-MM-dd',12,NULL,0,11),('402880f0230992e7012309d97b7801b2','402880f0230992e7012309d979f101aa','1d2d72e1-9ad5-4e95-8c38-5dbdbd1640c1','三级部门',NULL,16,NULL,0,7),('402880f0230992e7012309d97b7801b4','402880f0230992e7012309d979f101aa','29ed807d-2d4e-459d-8905-5725bdf6165e','姓名',NULL,10,NULL,0,2),('402880f0230992e7012309d97b7801bb','402880f0230992e7012309d979f101aa','402880f02304355e012307827aec000f','职位描述',NULL,10,NULL,0,8),('402880f0230992e7012309d97b8701cd','402880f0230992e7012309d979f101aa','403b3549-b28f-458a-a956-d1a65e73b9ef','用工形式',NULL,12,NULL,0,9),('402880f0230992e7012309d97b8701ce','402880f0230992e7012309d979f101aa','428f4a57-19d9-44a0-93e6-70505acc0336','合同期限',NULL,10,NULL,0,16),('402880f0230992e7012309d97b8701cf','402880f0230992e7012309d979f101aa','48b83abc-5780-4224-86f2-0be97d1e577d','合同种类',NULL,12,NULL,0,14),('402880f0230992e7012309d97b8701d0','402880f0230992e7012309d979f101aa','4b81740a-fb5f-41e3-9938-ba47c34c234a','合同编号',NULL,12,NULL,0,15),('402880f0230992e7012309d97b8701d3','402880f0230992e7012309d979f101aa','5f7b2fd2-67a8-4eca-8e95-88dd3ae137a9','参加工作日期','yyyy-MM-dd',12,NULL,0,10),('402880f0230992e7012309d97b8701db','402880f0230992e7012309d979f101aa','84079380-e5af-426e-86ce-1bdfaf1ed6ff','转正日期','yyyy-MM-dd',12,NULL,0,12),('402880f0230992e7012309d97b8701de','402880f0230992e7012309d979f101aa','88577c5a-a705-4d6f-b3f3-906b640bfea2','合同结束日期','yyyy-MM-dd',12,NULL,0,18),('402880f0230992e7012309d97b8701e3','402880f0230992e7012309d979f101aa','9e526398-25fa-4170-aaec-cc63fbe338de','工号',NULL,10,NULL,3,1),('402880f0230992e7012309d97b8701e4','402880f0230992e7012309d979f101aa','a23d0118-5130-4283-89ff-09f0b33a5cd8','公司司龄','1',10,NULL,0,13),('402880f0230992e7012309d97b8701e8','402880f0230992e7012309d979f101aa','b0cb27ec-b4da-4251-b82e-b3d461e9f4d3','工作地区',NULL,12,NULL,0,6),('402880f0230992e7012309d97b8701e9','402880f0230992e7012309d979f101aa','b87791f4-ab9b-4aa1-a5f4-b161575ce26a','合同备注',NULL,20,NULL,0,20),('402880f0230992e7012309d97b8701eb','402880f0230992e7012309d979f101aa','bca3c812-fc94-4655-bfa7-7693b97b9ddf','合同状态',NULL,10,NULL,0,19),('402880f0230992e7012309d97b8701ed','402880f0230992e7012309d979f101aa','bfd320c2-01d5-432b-9a3f-fe2f48231fac','二级部门',NULL,12,NULL,0,5),('402880f0230992e7012309d97b8701ee','402880f0230992e7012309d979f101aa','c16731d5-c67a-4ed4-a9c8-bbb01ec2d554','部门',NULL,16,NULL,1,0),('402880f0230992e7012309d97b8701ef','402880f0230992e7012309d979f101aa','cce6a3e1-1956-4e31-86fe-38870437e60c','分公司',NULL,16,NULL,0,3),('402880f0230992e7012309d97b8701f0','402880f0230992e7012309d979f101aa','cd794a47-c1b2-4caf-91e7-84a9b929ce66','一级部门',NULL,12,NULL,0,4),('402880f0230992e7012309d97b8701f5','402880f0230992e7012309d979f101aa','fbacd67e-c45f-4429-ace2-4403f98be992','合同起始日期','yyyy-MM-dd',12,NULL,0,17),('402880f0239cabc30123a70f52580010','402880f0239cabc30123a70f5238000f','0710b4b3-9c17-4a53-bc60-e019de179a2a','分公司',NULL,16,NULL,0,3),('402880f0239cabc30123a70f52580016','402880f0239cabc30123a70f5238000f','1760909c-1ce0-4bad-a519-36a3982575a2','职位描述',NULL,10,NULL,0,8),('402880f0239cabc30123a70f52580017','402880f0239cabc30123a70f5238000f','17d2c017-fa26-4b88-bdd8-b4582ca407cd','合同期限',NULL,12,NULL,0,17),('402880f0239cabc30123a70f52580018','402880f0239cabc30123a70f5238000f','1c73f90e-1276-4f62-a648-6e94235fbfa7','一级部门',NULL,12,NULL,0,4),('402880f0239cabc30123a70f5258001e','402880f0239cabc30123a70f5238000f','393e200b-d55c-4672-9c61-5300fa9b718f','转正日期','yyyy-MM-dd',12,NULL,0,11),('402880f0239cabc30123a70f5258002d','402880f0239cabc30123a70f5238000f','56d66565-377d-4a35-9c31-a46037a9f6e7','三级部门',NULL,16,NULL,0,6),('402880f0239cabc30123a70f52580030','402880f0239cabc30123a70f5238000f','66352651-fbda-4b3c-a602-4b7439cdc7ce','合同备注',NULL,26,NULL,0,18),('402880f0239cabc30123a70f52580033','402880f0239cabc30123a70f5238000f','6ac3fddb-ba07-40a6-9634-80b7c631094f','姓名',NULL,10,NULL,0,2),('402880f0239cabc30123a70f52580038','402880f0239cabc30123a70f5238000f','75e53854-a831-4168-956d-12fff393de06','合同状态',NULL,12,NULL,0,15),('402880f0239cabc30123a70f5258003c','402880f0239cabc30123a70f5238000f','8e6982b6-8d97-4695-8463-7ac19f522f4b','合同种类',NULL,20,NULL,0,14),('402880f0239cabc30123a70f52580042','402880f0239cabc30123a70f5238000f','9b5a4679-61ce-416b-8843-090fcd30bbc4','合同起始日期','yyyy-MM-dd',12,NULL,3,12),('402880f0239cabc30123a70f52580043','402880f0239cabc30123a70f5238000f','9da167e3-386a-4c93-984d-c2140003308d','用工形式',NULL,12,NULL,0,9),('402880f0239cabc30123a70f5267004a','402880f0239cabc30123a70f5238000f','be4e4a29-4058-4172-a556-a578d5b2a8fb','部门',NULL,16,NULL,1,0),('402880f0239cabc30123a70f5267004c','402880f0239cabc30123a70f5238000f','c20bbc32-d5a5-4321-88d7-adc84bd496bd','二级部门',NULL,12,NULL,0,5),('402880f0239cabc30123a70f5267004d','402880f0239cabc30123a70f5238000f','c21651d0-4951-434c-941b-123edc3624ca','合同结束日期','yyyy-MM-dd',12,NULL,0,13),('402880f0239cabc30123a70f5267004e','402880f0239cabc30123a70f5238000f','c3defad5-dd1c-49cc-aca5-f281cb661209','入职日期','yyyy-MM-dd',12,NULL,0,10),('402880f0239cabc30123a70f5267004f','402880f0239cabc30123a70f5238000f','c858dc97-a566-423d-bd0d-693d1e952afa','工作地区',NULL,12,NULL,0,7),('402880f0239cabc30123a70f52670050','402880f0239cabc30123a70f5238000f','c9c23c68-2369-40fe-992c-66c63ba61344','合同编号',NULL,15,NULL,0,16),('402880f0239cabc30123a70f5267005b','402880f0239cabc30123a70f5238000f','ff731861-d28d-4e19-9bf3-1c2069208566','工号',NULL,10,NULL,3,1),('402880f0246a87a301246bcb85350004','402880f0246a87a301246bcb85350003','03fec031-9862-47ca-b768-83c255def9dc','职位描述',NULL,10,NULL,0,8),('402880f0246a87a301246bcb8535000f','402880f0246a87a301246bcb85350003','4776d73c-9196-4eda-ad53-4365233bf552','姓名',NULL,10,NULL,0,2),('402880f0246a87a301246bcb85350017','402880f0246a87a301246bcb85350003','5f13b67f-9a2b-4d63-861b-303b478adbee','部门',NULL,16,NULL,1,0),('402880f0246a87a301246bcb8535001a','402880f0246a87a301246bcb85350003','691880a6-bf48-43db-b0d1-b67a151db983','分公司',NULL,16,NULL,0,3),('402880f0246a87a301246bcb8535002a','402880f0246a87a301246bcb85350003','92aec4d7-1214-47d0-a103-81e1f3086f74','入职日期','yyyy-MM-dd',12,NULL,0,15),('402880f0246a87a301246bcb8535002b','402880f0246a87a301246bcb85350003','969ed78d-e8b1-47f3-b72f-d76d15e8623b','离职审批人',NULL,10,NULL,0,12),('402880f0246a87a301246bcb8535002c','402880f0246a87a301246bcb85350003','9bd8f863-e944-494c-b734-2bb308b6f48f','三级部门',NULL,16,NULL,0,6),('402880f0246a87a301246bcb8535002d','402880f0246a87a301246bcb85350003','9c73c0e7-413c-4f4f-acb0-c3a5bec1f24c','工号',NULL,10,NULL,0,1),('402880f0246a87a301246bcb85350030','402880f0246a87a301246bcb85350003','b004419c-8c77-4e0e-a845-89978b20ddb5','离职日期','yyyy-MM-dd',12,NULL,0,10),('402880f0246a87a301246bcb85350033','402880f0246a87a301246bcb85350003','b6a8e7b1-4777-4b34-8d85-d077f4c51b72','用工形式',NULL,12,NULL,0,9),('402880f0246a87a301246bcb8535003c','402880f0246a87a301246bcb85350003','d8c802ec-cae1-47ac-bd55-24400c7a89ee','工作地区',NULL,12,NULL,0,7),('402880f0246a87a301246bcb85350042','402880f0246a87a301246bcb85350003','e6537ba1-48e9-4e31-bd69-8f58345655ce','离职备注',NULL,20,NULL,0,14),('402880f0246a87a301246bcb85350043','402880f0246a87a301246bcb85350003','e8dc3be0-cc2f-4a8c-9a96-d4244c259677','一级部门',NULL,12,NULL,0,4),('402880f0246a87a301246bcb85350048','402880f0246a87a301246bcb85350003','f666ce48-8f4a-4367-be3c-aa8fb98e55c0','离职种类',NULL,12,NULL,0,11),('402880f0246a87a301246bcb8535004c','402880f0246a87a301246bcb85350003','fc226222-da25-4fb1-9ca1-c62cc987d0b8','离职原因',NULL,20,NULL,0,13),('402880f0246a87a301246bcb8535004d','402880f0246a87a301246bcb85350003','fcca7328-4fc4-458f-8149-c923acb58317','二级部门',NULL,12,NULL,0,5),('402880f0246a87a301246bd423df0056','402880f0246a87a301246bd423df004e','2d789820-f07c-42f7-9a25-48e0607ad5a0','考评备注',NULL,20,NULL,0,18),('402880f0246a87a301246bd423df0057','402880f0246a87a301246bd423df004e','2dd1e92e-02f5-4a1b-91e1-220b635da92e','考评结果',NULL,12,NULL,0,17),('402880f0246a87a301246bd423df005c','402880f0246a87a301246bd423df004e','402880e423785e40012378d8d7fd00a6','分公司',NULL,16,NULL,0,3),('402880f0246a87a301246bd423df005e','402880f0246a87a301246bd423df004e','402880e423785e40012378d8d7fd00a8','二级部门',NULL,12,NULL,0,5),('402880f0246a87a301246bd423df0060','402880f0246a87a301246bd423df004e','402880e423785e40012378dce3b100ae','工作地区',NULL,12,NULL,0,7),('402880f0246a87a301246bd423df0063','402880f0246a87a301246bd423df004e','402880e423785e40012378dce3b100b1','职位描述',NULL,10,NULL,0,8),('402880f0246a87a301246bd423df0067','402880f0246a87a301246bd423df004e','402880e423785e40012378ee29d000bc','部门',NULL,16,NULL,1,0),('402880f0246a87a301246bd423df0069','402880f0246a87a301246bd423df004e','402880e423785e40012378ee29d000be','一级部门',NULL,12,NULL,0,4),('402880f0246a87a301246bd423df006b','402880f0246a87a301246bd423df004e','402880e423785e40012378ee29d000c0','三级部门',NULL,16,NULL,0,6),('402880f0246a87a301246bd423df006e','402880f0246a87a301246bd423df004e','497febba-dcf7-4f78-af11-986589e7b8a2','结束日期','yyyy-MM-dd',12,NULL,0,11),('402880f0246a87a301246bd423df007a','402880f0246a87a301246bd423df004e','788eda69-a027-4fba-b9d2-771e260e7b77','起始日期','yyyy-MM-dd',12,NULL,0,10),('402880f0246a87a301246bd423df007e','402880f0246a87a301246bd423df004e','81bf5be9-e955-4ae4-acd8-63ec76adefbc','姓名',NULL,10,NULL,0,2),('402880f0246a87a301246bd423df0087','402880f0246a87a301246bd423df004e','9de87469-852f-4f66-b4e0-8e5944c50a79','工号',NULL,10,NULL,3,1),('402880f0246a87a301246bd423df008a','402880f0246a87a301246bd423df004e','a6e7ca7b-4edf-44ad-a2c2-2c4371a730e1','考评职位描述',NULL,10,NULL,0,16),('402880f0246a87a301246bd423df0094','402880f0246a87a301246bd423df004e','c22f28a7-bf4f-40c2-afa1-03d332bf0588','考评种类',NULL,12,NULL,0,12),('402880f0246a87a301246be0df8400a2','402880f0246a87a301246be0df8400a0','01590c79-0b64-4a05-bdda-8f3d48eb9557','变动日期','yyyy-MM-dd',12,NULL,0,10),('402880f0246a87a301246be0df8400a5','402880f0246a87a301246be0df8400a0','0fe502cf-231c-4b15-9193-2f90cbc5d191','姓名',NULL,10,NULL,0,2),('402880f0246a87a301246be0df8400b2','402880f0246a87a301246be0df8400a0','402880e423785e4001237882bc880001','现职位描述',NULL,10,NULL,0,8),('402880f0246a87a301246be0df8400b4','402880f0246a87a301246be0df8400a0','402880e423785e4001237898641c0005','变动备注',NULL,20,NULL,0,17),('402880f0246a87a301246be0df8400bd','402880f0246a87a301246be0df8400a0','5cec577c-7481-4fa2-a1ae-852aedfe4b35','现部门',NULL,16,NULL,1,0),('402880f0246a87a301246be0df8400c2','402880f0246a87a301246be0df8400a0','6763a274-e43c-4257-a104-1161bb65bd7b','工作地区',NULL,12,NULL,0,7),('402880f0246a87a301246be0df8400c8','402880f0246a87a301246be0df8400a0','7163da50-8cb4-4175-b220-30681b45ce66','现三级部门',NULL,16,NULL,0,6),('402880f0246a87a301246be0df8400ca','402880f0246a87a301246be0df8400a0','7a99b12a-afc4-4154-a4c7-29016bf9a53f','变动原因',NULL,20,NULL,0,16),('402880f0246a87a301246be0df8400cb','402880f0246a87a301246be0df8400a0','7d3cadaf-a843-48ce-8f03-4aa62724df75','现分公司',NULL,16,NULL,0,3),('402880f0246a87a301246be0df9300cf','402880f0246a87a301246be0df8400a0','81312f3e-e399-4f5f-b9dc-f3f8778ea02e','现二级部门',NULL,12,NULL,0,5),('402880f0246a87a301246be0df9300d9','402880f0246a87a301246be0df8400a0','a4458ade-910e-4ee0-9f97-089db2120db4','工号',NULL,10,NULL,3,1),('402880f0246a87a301246be0df9300e4','402880f0246a87a301246be0df8400a0','bd78bcd6-f5b6-43ab-94d4-9fdf2478c546','变动种类',NULL,15,NULL,0,11),('402880f0246a87a301246be0df9300e6','402880f0246a87a301246be0df8400a0','c4602c23-40a1-4f99-8a61-b1ed950dd731','现一级部门',NULL,12,NULL,0,4),('402880f0246a87a301246be3c70b00f3','402880f0246a87a301246be3c70b00f1','025e2b3f-012e-4e83-a9ee-d31facf0173c','姓名',NULL,10,NULL,0,2),('402880f0246a87a301246be3c70b0102','402880f0246a87a301246be3c70b00f1','402880e423792e7f012379653a860055','职位描述',NULL,10,NULL,0,8),('402880f0246a87a301246be3c70b0105','402880f0246a87a301246be3c70b00f1','4709bcd9-bb4e-46a3-8c61-4f3c6a6b58f9','奖惩日期','yyyy-MM-dd',12,NULL,0,9),('402880f0246a87a301246be3c70b010e','402880f0246a87a301246be3c70b00f1','6b4132b2-55a4-4736-8272-8ebe254f6053','工号',NULL,10,NULL,3,1),('402880f0246a87a301246be3c70b0116','402880f0246a87a301246be3c70b00f1','7fe71b14-6738-447f-89cf-e11fe8b1d61f','部门',NULL,16,NULL,1,0),('402880f0246a87a301246be3c70b0117','402880f0246a87a301246be3c70b00f1','819de58c-d079-4eae-b663-4feb949bc0de','工作地区',NULL,12,NULL,0,7),('402880f0246a87a301246be3c70b011a','402880f0246a87a301246be3c70b00f1','8a73334f-6e89-4d2c-b6db-c3b96ef9adcb','奖惩种类',NULL,12,NULL,0,10),('402880f0246a87a301246be3c70b011c','402880f0246a87a301246be3c70b00f1','8be1487e-f791-486f-8cbd-849077d37d76','一级部门',NULL,12,NULL,0,4),('402880f0246a87a301246be3c70b012c','402880f0246a87a301246be3c70b00f1','b43680ea-1fa1-42c6-aae0-c891e5139f22','三级部门',NULL,16,NULL,0,6),('402880f0246a87a301246be3c70b012d','402880f0246a87a301246be3c70b00f1','b53ca36f-9d64-44fc-8cfd-a5d5a9ec94c4','二级部门',NULL,12,NULL,0,5),('402880f0246a87a301246be3c70b0130','402880f0246a87a301246be3c70b00f1','c13530e8-63d9-4a68-9427-4f9eb977b207','奖惩形式',NULL,12,NULL,0,11),('402880f0246a87a301246be3c70b0135','402880f0246a87a301246be3c70b00f1','e3d1620e-fa74-4148-9002-cac362e5f01e','奖惩备注','15',20,NULL,0,12),('402880f0246a87a301246be3c70b013d','402880f0246a87a301246be3c70b00f1','f7bd8e15-cd05-4298-858c-ee36bb5a06d4','分公司',NULL,16,NULL,0,3),('402880f024945fb301249478ae200006','402880f024945fb301249478ae110001','1aa37c05-c985-4a2c-93da-f00728689c8d','工作地区',NULL,12,NULL,0,7),('402880f024945fb301249478ae20000a','402880f024945fb301249478ae110001','26f4ea56-a380-4dd8-a8c0-c7db70d59e97','分公司',NULL,16,NULL,0,2),('402880f024945fb301249478ae20000d','402880f024945fb301249478ae110001','2b2e7d2e-3bb6-40d6-8250-ff14ac05053e','姓名',NULL,10,NULL,0,1),('402880f024945fb301249478ae300012','402880f024945fb301249478ae110001','3e8ac800-5299-4aa8-8729-4711f35eae99','部门',NULL,16,NULL,0,3),('402880f024945fb301249478ae300021','402880f024945fb301249478ae110001','738239c7-00df-4944-921a-d31f06e933e6','工作电话',NULL,16,NULL,0,10),('402880f024945fb301249478ae300025','402880f024945fb301249478ae110001','839fbf2c-3db0-446b-bbf7-bc5c1f0bf751','手机',NULL,16,NULL,0,11),('402880f024945fb301249478ae30002b','402880f024945fb301249478ae110001','920a8c56-4b13-4d59-bc26-1475112e2ae4','邮箱',NULL,30,NULL,0,8),('402880f024945fb301249478ae300039','402880f024945fb301249478ae110001','bd2b474c-b7bf-4292-a386-42feb49ea2c7','二级部门',NULL,12,NULL,0,5),('402880f024945fb301249478ae30003f','402880f024945fb301249478ae110001','e25b05cb-3bf1-4c5f-8538-5712f91d4ccf','工号',NULL,10,NULL,0,0),('402880f024945fb301249478ae300041','402880f024945fb301249478ae110001','ebe62cef-3420-49ce-9d2c-563ae3907306','MSN/QQ',NULL,30,NULL,0,9),('402880f02495539a01249557fa5f0004','402880f02495539a01249557fa5f0003','18c11002-c04a-4900-a930-1eab9a5e8b05','帐套名称',NULL,15,NULL,1,0),('402880f02495539a01249557fa5f0005','402880f02495539a01249557fa5f0003','402880f02495539a01249556a8f80001','帐套描述',NULL,30,NULL,0,1),('402880f02495539a01249557fa5f0006','402880f02495539a01249557fa5f0003','54d436f2-3f9e-4e0c-aca1-26c7f2bdd3b0','项目种类',NULL,12,NULL,0,3),('402880f02495539a01249557fa5f0007','402880f02495539a01249557fa5f0003','7a9fee2b-f923-4ba2-bd85-dc0ce87a4cee','项目编号',NULL,10,NULL,0,2),('402880f02495539a01249557fa5f0008','402880f02495539a01249557fa5f0003','a4073287-7301-4f14-bc96-05297f38e887','计算公式',NULL,30,NULL,0,6),('402880f02495539a01249557fa5f0009','402880f02495539a01249557fa5f0003','b4055f3c-088c-401b-bf92-7a83e0d95b09','尾数舍入',NULL,10,NULL,0,7),('402880f02495539a01249557fa6f000a','402880f02495539a01249557fa5f0003','c2f25fcd-28f4-4b4b-9e81-d157c2ddd48c','字段属性',NULL,12,NULL,0,5),('402880f02495539a01249557fa6f000b','402880f02495539a01249557fa5f0003','e5e39892-6a53-4818-830d-eff3f12c5112','项目名称',NULL,15,NULL,0,4),('402880f02495539a01249566a7a3000d','402880f02495539a01249566a793000c','1aa79122-a462-4874-ba7e-3c3715609537','项目种类',NULL,12,NULL,0,1),('402880f02495539a01249566a7a3000e','402880f02495539a01249566a793000c','34b0e3a1-464b-47b9-8ccf-b03575417445','计算公式',NULL,30,NULL,0,4),('402880f02495539a01249566a7a3000f','402880f02495539a01249566a793000c','4376b386-1d04-44fe-9f03-8f1932def859','项目编号',NULL,10,NULL,0,0),('402880f02495539a01249566a7a30010','402880f02495539a01249566a793000c','44c26f79-ddce-4d3e-94d3-2b8c128642ed','尾数舍入',NULL,10,NULL,0,5),('402880f02495539a01249566a7a30011','402880f02495539a01249566a793000c','861c91a5-ecd3-42d2-bc5a-affbcd40308d','项目名称',NULL,15,NULL,0,2),('402880f02495539a01249566a7a30012','402880f02495539a01249566a793000c','903ab48b-7935-47d8-a4bd-8e8f49247817','字段属性',NULL,12,NULL,0,3),('402880f0249586da0124958c7cf00005','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','402880f0249586da0124958c7ce10003','分公司',NULL,16,NULL,0,2),('402880f024959e30012495a9e1790002','ff808081232096ef0123221a6b420127','402880f024959e30012495a9e15a0001','分公司',NULL,16,NULL,0,2),('402880f024959e30012495a9e1790003','402880e4232bd2d301232c51f3bd0001','402880f024959e30012495a9e15a0001','分公司',NULL,16,NULL,0,3),('402880f024a13a830124a15435790064','402880f024a13a830124a15435790063','0abf630f-f2ec-4a7a-bdb9-955222f68588','工号','32',12,NULL,0,1),('402880f024a13a830124a15435790065','402880f024a13a830124a15435790063','231d2444-424d-486b-b405-74e1ab14ae88','缺勤(小时)','2',12,NULL,0,13),('402880f024a13a830124a15435790066','402880f024a13a830124a15435790063','26406cf2-c418-4277-8cb6-4898bc92c1c8','月考勤备注','128',26,NULL,0,33),('402880f024a13a830124a15435790067','402880f024a13a830124a15435790063','36d6f89a-69e9-4edd-8f9b-eed6331b07b3','病假(小时)','2',12,NULL,0,20),('402880f024a13a830124a15435790068','402880f024a13a830124a15435790063','386c4c9d-cb36-4650-a513-4983f17040ad','其他请假(小时)','2',12,NULL,0,28),('402880f024a13a830124a1543579006a','402880f024a13a830124a15435790063','3adeb5d9-5f37-459c-9600-95fb36a9351d','全勤(小时)','2',12,NULL,0,11),('402880f024a13a830124a1543579006b','402880f024a13a830124a15435790063','402880f024a13a830124a14c7c4d0049','年月','6',10,NULL,0,0),('402880f024a13a830124a15435790078','402880f024a13a830124a15435790063','5423b641-f380-479b-aba2-f14a2afcdae5','旷工(小时)','2',12,NULL,0,16),('402880f024a13a830124a15435790079','402880f024a13a830124a15435790063','6770f92d-4367-47f5-9c27-e680f27e3dce','年假(小时)','2',12,NULL,0,18),('402880f024a13a830124a1543579007a','402880f024a13a830124a15435790063','67949407-c342-49e7-bb02-26360cddff63','出差(小时)','2',12,NULL,0,24),('402880f024a13a830124a1543579007b','402880f024a13a830124a15435790063','6e8dbb76-0a53-4d18-8830-e374d7653200','节假日加班(小时)','2',12,NULL,0,32),('402880f024a13a830124a1543579007c','402880f024a13a830124a15435790063','8b461277-8176-40d0-850c-fa0e81ac3266','产假(小时)','2',12,NULL,0,23),('402880f024a13a830124a1543579007d','402880f024a13a830124a15435790063','8b515ccd-ea5d-402d-80a7-1134d8a51675','请假(小时)','2',12,NULL,0,17),('402880f024a13a830124a1543579007e','402880f024a13a830124a15435790063','8b5d62f1-53c9-4171-8bb2-2dbef09984a7','日常加班(小时)','2',12,NULL,0,30),('402880f024a13a830124a1543579007f','402880f024a13a830124a15435790063','9b7a9856-83c9-4d3e-a071-84cb9ed57283','事假(小时)','2',12,NULL,0,19),('402880f024a13a830124a15435790080','402880f024a13a830124a15435790063','9d142c9c-8229-4817-aade-faca06680b4e','周末加班(小时)','2',12,NULL,0,31),('402880f024a13a830124a15435790082','402880f024a13a830124a15435790063','a8647e13-781c-4c25-827c-fe401215cefd','迟到(次)','2',12,NULL,0,14),('402880f024a13a830124a15435790083','402880f024a13a830124a15435790063','b2365077-ff82-47ac-9787-2b2212950806','姓名','64',12,NULL,0,2),('402880f024a13a830124a15435790084','402880f024a13a830124a15435790063','b5e1c7fa-c570-4c76-a521-bafc734f515c','加班(小时)','2',12,NULL,0,29),('402880f024a13a830124a15435790085','402880f024a13a830124a15435790063','c218fac2-84df-4018-a15a-ed3a53448d9e','病假住院(小时)','2',12,NULL,0,21),('402880f024a13a830124a15435790086','402880f024a13a830124a15435790063','c7fca848-1219-4598-a362-80f442fcaae8','早退(次)','2',12,NULL,0,15),('402880f024a13a830124a15435790088','402880f024a13a830124a15435790063','dc4fdac0-2773-4209-9b53-ff3586a9740e','婚假(小时)','2',12,NULL,0,22),('402880f024a13a830124a15435790089','402880f024a13a830124a15435790063','dcd0bdb0-a439-45d2-ae66-50dba8b749f1','调休(小时)','2',12,NULL,0,26),('402880f024a13a830124a1543579008a','402880f024a13a830124a15435790063','e35c7f11-7269-4834-bbd3-adb087460036','出勤(小时)','2',12,NULL,0,12),('402880f024a13a830124a1543579008b','402880f024a13a830124a15435790063','f84e1d77-a1ec-4dc3-950f-4bc6031bc892','因公外出(小时)','2',12,NULL,0,25),('402880f024a13a830124a1554023008d','402880f024a13a830124a1554023008c','0abf630f-f2ec-4a7a-bdb9-955222f68588','工号','32',12,NULL,0,1),('402880f024a13a830124a1554023008f','402880f024a13a830124a1554023008c','26406cf2-c418-4277-8cb6-4898bc92c1c8','月考勤备注','128',26,NULL,0,33),('402880f024a13a830124a15540230092','402880f024a13a830124a1554023008c','39bc683a-249e-468e-aaa8-c820e873a036','缺勤(天)','2',12,NULL,0,13),('402880f024a13a830124a15540230094','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d0049','年月','6',10,NULL,0,0),('402880f024a13a830124a15540230095','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004a','全勤(天)','2',12,NULL,0,11),('402880f024a13a830124a15540230096','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004b','请假(天)','2',12,NULL,0,17),('402880f024a13a830124a15540230097','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004c','年假(天)','2',12,NULL,0,18),('402880f024a13a830124a15540230098','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004d','事假(天)','2',12,NULL,0,19),('402880f024a13a830124a15540230099','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004e','病假(天)','2',12,NULL,0,20),('402880f024a13a830124a1554023009a','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d004f','病假住院(天)','2',12,NULL,0,21),('402880f024a13a830124a1554023009b','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d0050','婚假(天)','2',12,NULL,0,22),('402880f024a13a830124a1554023009c','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d0051','产假(天)','2',12,NULL,0,23),('402880f024a13a830124a1554023009d','402880f024a13a830124a1554023008c','402880f024a13a830124a14c7c4d0052','出差(天)','2',12,NULL,0,24),('402880f024a13a830124a1554023009e','402880f024a13a830124a1554023008c','402880f024a13a830124a14f53d7005d','因公外出(天)','2',12,NULL,0,25),('402880f024a13a830124a1554023009f','402880f024a13a830124a1554023008c','402880f024a13a830124a14f53d7005e','调休(天)','2',12,NULL,0,26),('402880f024a13a830124a155402300a0','402880f024a13a830124a1554023008c','402880f024a13a830124a14f53d7005f','其他请假(天)','2',12,NULL,0,28),('402880f024a13a830124a155402300a4','402880f024a13a830124a1554023008c','6e8dbb76-0a53-4d18-8830-e374d7653200','节假日加班(小时)','2',12,NULL,0,32),('402880f024a13a830124a155402300a7','402880f024a13a830124a1554023008c','8b5d62f1-53c9-4171-8bb2-2dbef09984a7','日常加班(小时)','2',12,NULL,0,30),('402880f024a13a830124a155403300a9','402880f024a13a830124a1554023008c','9d142c9c-8229-4817-aade-faca06680b4e','周末加班(小时)','2',12,NULL,0,31),('402880f024a13a830124a155403300aa','402880f024a13a830124a1554023008c','a650181a-7611-4c0d-9fad-0ef09690f64a','旷工(天)','2',12,NULL,0,16),('402880f024a13a830124a155403300ab','402880f024a13a830124a1554023008c','a8647e13-781c-4c25-827c-fe401215cefd','迟到(次)','2',12,NULL,0,14),('402880f024a13a830124a155403300ac','402880f024a13a830124a1554023008c','b2365077-ff82-47ac-9787-2b2212950806','姓名','64',12,NULL,0,2),('402880f024a13a830124a155403300ad','402880f024a13a830124a1554023008c','b5e1c7fa-c570-4c76-a521-bafc734f515c','加班(小时)','2',12,NULL,0,29),('402880f024a13a830124a155403300af','402880f024a13a830124a1554023008c','c7fca848-1219-4598-a362-80f442fcaae8','早退(次)','2',12,NULL,0,15),('402880f024a13a830124a155403300b0','402880f024a13a830124a1554023008c','da68a20e-552b-40e1-95ea-923c3e2a2c3a','出勤(天)','2',12,NULL,0,12),('402880f024a13a830124a15c87aa00bf','402880f024a13a830124a1554023008c','402880f024a13a830124a15c87aa00b5','分公司',NULL,16,NULL,0,3),('402880f024a13a830124a15c87aa00c0','402880f024a13a830124a15435790063','402880f024a13a830124a15c87aa00b5','分公司',NULL,16,NULL,0,3),('402880f024a13a830124a15c87aa00c3','402880f024a13a830124a1554023008c','402880f024a13a830124a15c87aa00b7','部门',NULL,16,NULL,0,4),('402880f024a13a830124a15c87aa00c4','402880f024a13a830124a15435790063','402880f024a13a830124a15c87aa00b7','部门',NULL,16,NULL,0,4),('402880f024a13a830124a15c87aa00c7','402880f024a13a830124a1554023008c','402880f024a13a830124a15c87aa00b9','一级部门',NULL,12,NULL,0,5),('402880f024a13a830124a15c87aa00c8','402880f024a13a830124a15435790063','402880f024a13a830124a15c87aa00b9','一级部门',NULL,12,NULL,0,5),('402880f024a13a830124a15c87aa00cb','402880f024a13a830124a1554023008c','402880f024a13a830124a15c87aa00bb','二级部门',NULL,12,NULL,0,6),('402880f024a13a830124a15c87aa00cc','402880f024a13a830124a15435790063','402880f024a13a830124a15c87aa00bb','二级部门',NULL,12,NULL,0,6),('402880f024a13a830124a15c87aa00cf','402880f024a13a830124a1554023008c','402880f024a13a830124a15c87aa00bd','三级部门',NULL,16,NULL,0,7),('402880f024a13a830124a15c87aa00d0','402880f024a13a830124a15435790063','402880f024a13a830124a15c87aa00bd','三级部门',NULL,16,NULL,0,7),('402880f024a13a830124a15e233900d9','402880f024a13a830124a1554023008c','402880f024a13a830124a15e233900d4','职位描述',NULL,10,NULL,0,9),('402880f024a13a830124a15e233900da','402880f024a13a830124a15435790063','402880f024a13a830124a15e233900d4','职位描述',NULL,10,NULL,0,9),('402880f024a13a830124a15e233900db','402880f024a13a830124a1554023008c','402880f024a13a830124a15e233900d5','用工形式',NULL,12,NULL,0,10),('402880f024a13a830124a15e233900dc','402880f024a13a830124a15435790063','402880f024a13a830124a15e233900d5','用工形式',NULL,12,NULL,0,10),('402880f024a1d34b0124a1d62a460003','402880f024a13a830124a1554023008c','402880f024a1d34b0124a1d62a460001','工作地区',NULL,12,NULL,0,8),('402880f024a1d34b0124a1d62a460004','402880f024a13a830124a15435790063','402880f024a1d34b0124a1d62a460001','工作地区',NULL,12,NULL,0,8),('402881e524aae9260124aaf0d6a20004','402880f024a13a830124a15435790063','402881e524aae9260124aaf0d6830001','调休过期(小时)','2',12,NULL,0,27),('402881e524aae9260124aaf0d6a20005','402880f024a13a830124a1554023008c','402881e524aae9260124aaf0d6a20002','调休过期(天)','2',12,NULL,0,27),('402881e524ae08300124ae0fc0490003','402881e524ae08300124ae0fc0490002','ff808081232096ef0123236fe20e01b8','部门',NULL,16,NULL,1,0),('402881e524ae08300124ae0fc0490004','402881e524ae08300124ae0fc0490002','f9e15593-ed45-4023-b3e1-fb4b1ea076dc','工号',NULL,20,NULL,3,1),('402881e524ae08300124ae0fc0490005','402881e524ae08300124ae0fc0490002','2dd22aa5-5067-4466-aaf1-47e8c7416a19','姓名',NULL,20,NULL,0,2),('402881e524ae08300124ae0fc0490006','402881e524ae08300124ae0fc0490002','ff808081232096ef0123236fe20e01b6','分公司',NULL,16,NULL,0,3),('402881e524ae08300124ae0fc0490007','402881e524ae08300124ae0fc0490002','ff808081232096ef0123236fe20e01ba','一级部门',NULL,12,NULL,0,4),('402881e524ae08300124ae0fc0490008','402881e524ae08300124ae0fc0490002','ff808081232096ef01232373933e01c6','二级部门',NULL,12,NULL,0,5),('402881e524ae08300124ae0fc0490009','402881e524ae08300124ae0fc0490002','ff808081232096ef01232373933e01c8','三级部门',NULL,16,NULL,0,6),('402881e524ae08300124ae0fc049000a','402881e524ae08300124ae0fc0490002','ff808081232096ef01232373933e01ca','工作地区',NULL,12,NULL,0,7),('402881e524ae08300124ae0fc049000b','402881e524ae08300124ae0fc0490002','ff808081232096ef01232373933e01cd','职位描述',NULL,10,NULL,0,8),('402881e524ae08300124ae0fc049000c','402881e524ae08300124ae0fc0490002','ff808081232096ef01232373933e01ce','用工形式',NULL,12,NULL,0,9),('402881e524ae08300124ae0fc049000d','402881e524ae08300124ae0fc0490002','ff808081232096ef01232375ec5701da','考勤方式',NULL,12,NULL,0,10),('402881e524ae08300124ae0fc049000e','402881e524ae08300124ae0fc0490002','ff808081232096ef01232375ec5701db','考勤卡号',NULL,12,NULL,0,11),('402881e524ae08300124ae0fc049000f','402881e524ae08300124ae0fc0490002','ff808081232096ef01232375ec6101dc','状态',NULL,10,NULL,0,12),('402881e524ae08300124ae0fc0490010','402881e524ae08300124ae0fc0490002','019ca49f-8be6-4cf4-9ec7-7942c1be9db9','考勤日期','yyyy-MM-dd',12,NULL,3,13),('402881e524ae08300124ae0fc0490011','402881e524ae08300124ae0fc0490002','7463df78-7a41-4a8c-b96b-6a8dc15491f1','考勤班次',NULL,10,NULL,0,14),('402881e524ae08300124ae0fc0490012','402881e524ae08300124ae0fc0490002','95713fee-4eb1-4624-9c37-1e35e6d1d901','上班时间','HH:mm',10,NULL,0,15),('402881e524ae08300124ae0fc0490013','402881e524ae08300124ae0fc0490002','c5e22b6c-4602-40db-8e6a-ac22ce09aa0f','下班时间','HH:mm',10,NULL,0,16),('402881e524ae08300124ae0fc0490015','402881e524ae08300124ae0fc0490002','402880e623dfd4180123dfe18e190001','全勤(小时)',NULL,15,NULL,0,17),('402881e524ae08300124ae0fc0490017','402881e524ae08300124ae0fc0490002','06cf0d59-9b10-4f3e-9429-a3e108615b70','迟到(分钟)',NULL,10,NULL,0,18),('402881e524ae08300124ae0fc0490018','402881e524ae08300124ae0fc0490002','8eaa3bdd-fef4-43db-bf22-e810fd92f523','早退(分钟)',NULL,10,NULL,0,19),('402881e524ae08300124ae0fc0490019','402881e524ae08300124ae0fc0490002','b4decc7f-81cd-48e7-937c-15b191c1af1e','请假(小时)',NULL,10,NULL,0,20),('402881e524ae08300124ae0fc049001b','402881e524ae08300124ae0fc0490002','0bedc9bd-b27d-4da6-89d7-bc092500eed8','加班(小时)',NULL,10,NULL,0,21),('402881e524ae08300124ae0fc049001c','402881e524ae08300124ae0fc0490002','de678597-584c-45ef-919c-f0d21d394385','缺勤(小时)',NULL,10,NULL,0,22),('402881e524ae08300124ae0fc049001e','402881e524ae08300124ae0fc0490002','9210faff-820c-4bfe-9de8-01176ed7e61c','异常描述',NULL,20,NULL,0,23),('402881e524ae08300124ae117367001f','ff808081232096ef0123237a954e01e0','ff808081232096ef0123236fe20e01b6','分公司',NULL,16,NULL,0,2),('402881e524ae08300124ae1173670020','ff808081232096ef0123237a954e01e0','ff808081232096ef0123236fe20e01ba','一级部门',NULL,12,NULL,0,4),('402881e524ae08300124ae1173670021','ff808081232096ef0123237a954e01e0','ff808081232096ef01232373933e01c6','二级部门',NULL,12,NULL,0,5),('402881e524ae08300124ae1173670022','ff808081232096ef0123237a954e01e0','ff808081232096ef01232373933e01c8','三级部门',NULL,16,NULL,0,6),('402881e524ae08300124ae1173670023','ff808081232096ef0123237a954e01e0','ff808081232096ef01232373933e01cd','职位描述',NULL,10,NULL,0,8),('402881e524ae08300124ae1173670024','ff808081232096ef0123237a954e01e0','ff808081232096ef01232373933e01ce','用工形式',NULL,12,NULL,0,9),('531c2c64-e342-4b73-97ab-85e8ac535de2','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','034fd99c-996d-4be1-884d-acad99520cfa','缴费年月',NULL,15,NULL,0,11),('54f99d58-4ea6-43d4-9cef-8fa7e98d1d83','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','9ef9d287-86df-4048-8962-9cade3442092','缴费种类',NULL,15,NULL,0,13),('715ef84a-c656-498b-aacb-ff66cbc276e5','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','fdb09fe4-c4a4-4959-8336-13fe05c26dc1','职位描述',NULL,10,NULL,0,8),('82df276f-4f94-4fda-92f8-9cab758ed18e','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','dc711751-cdc1-4af8-9f40-ba01a9b0da8b','所属年月',NULL,15,NULL,0,12),('8573c01f-01ba-42f6-82dd-e6d80df00835','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','dcbdc388-0cb7-467d-9012-846f898e8f78','工号',NULL,15,'count',0,0),('87a05c1a-1dcf-43c2-b10b-6d20548fd752','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','482798c9-0e0c-4e77-9644-eaa1b8f049eb','二级部门',NULL,12,NULL,0,5),('ae8988b2-d43b-45b2-ad92-05f482741175','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','5cb498d9-6c1e-441d-b151-770875f0c9bb','工作地区',NULL,12,NULL,0,7),('f62b7e25-9706-4168-995a-dc6233609a7b','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','cb2bbc9b-c392-462e-8467-b85604e588f9','部门',NULL,16,NULL,0,3),('fa120e6a-0746-49b5-918d-b7d38a75a86e','0524a661-f0f1-4f67-8bb3-04ddd8d983ef','19615170-1ae2-4abd-b33f-2cf1ed1650ae','社保种类',NULL,15,NULL,0,10),('ff808081232096ef012320b833e20003','ff808081232096ef012320b833ba0002','0010f8c8-8a16-459d-80ad-6e378e7b54f6','生效年月',NULL,10,NULL,0,23),('ff808081232096ef012320b833e20004','ff808081232096ef012320b833ba0002','25d1ae0a-da72-4b91-9cd8-8be18bbff2fa','公积金号',NULL,20,NULL,0,25),('ff808081232096ef012320b833e20005','ff808081232096ef012320b833ba0002','37362198-7e48-4fe0-94b6-62e792d3c0d0','公积金基数','2',12,NULL,0,14),('ff808081232096ef012320b833e20006','ff808081232096ef012320b833ba0002','3a9e089f-c6f1-471d-83bb-bf77369484c7','用工形式',NULL,12,NULL,0,9),('ff808081232096ef012320b833e20007','ff808081232096ef012320b833ba0002','3b17a2d4-ffd7-44f7-afab-8442f5e7f430','个人缴公积金',NULL,12,NULL,0,18),('ff808081232096ef012320b833e20008','ff808081232096ef012320b833ba0002','402880e423166cb1012316813d560001','社保备注',NULL,20,NULL,0,27),('ff808081232096ef012320b833e2000d','ff808081232096ef012320b833ba0002','402880f02304355e012307aed6e10038','分公司',NULL,16,NULL,0,2),('ff808081232096ef012320b833e20017','ff808081232096ef012320b833ba0002','402880f02304355e012307b5266c004e','入职日期','yyyy-MM-dd',12,NULL,0,10),('ff808081232096ef012320b833e20030','ff808081232096ef012320b833ba0002','402880f02304355e012307c0c6ec0082','状态',NULL,10,NULL,0,11),('ff808081232096ef012320b833f60042','ff808081232096ef012320b833ba0002','4affd66f-2315-4b5c-b32e-2b46d9859cdc','公司代缴社保总额',NULL,12,NULL,0,22),('ff808081232096ef012320b833f60043','ff808081232096ef012320b833ba0002','533fa122-eff9-4ca6-9b33-d2f9544a00fa','医疗保险号',NULL,20,NULL,0,26),('ff808081232096ef012320b833f60044','ff808081232096ef012320b833ba0002','54f3ade0-d706-4e4c-a551-fe59700c07ae','工号',NULL,10,NULL,0,0),('ff808081232096ef012320b833f60045','ff808081232096ef012320b833ba0002','663edc97-d0a7-4d49-8bbc-ac1e8bae1fb2','姓名',NULL,10,NULL,0,1),('ff808081232096ef012320b833f60046','ff808081232096ef012320b833ba0002','665e0d4a-6d38-495b-bd3d-13f7236f731d','其他福利项',NULL,12,NULL,0,20),('ff808081232096ef012320b833f60047','ff808081232096ef012320b833ba0002','673f10bd-79d5-45a8-9984-4aa0ff0d6804','公司代缴社保',NULL,12,NULL,0,17),('ff808081232096ef012320b833f60048','ff808081232096ef012320b833ba0002','7d0050a8-6052-43c1-8441-2fa64dfc91a3','职位描述',NULL,10,NULL,0,8),('ff808081232096ef012320b833f60049','ff808081232096ef012320b833ba0002','978f1c52-3dc8-4295-9ca9-0dca2f098bba','社保基数',NULL,12,NULL,0,13),('ff808081232096ef012320b833f6004a','ff808081232096ef012320b833ba0002','980ba3f4-0d3d-4c1b-b079-8f272f433c4e','一级部门',NULL,12,NULL,0,4),('ff808081232096ef012320b833f6004b','ff808081232096ef012320b833ba0002','990c160e-c0ce-48d7-9a07-52563b8cbe20','二级部门',NULL,12,NULL,0,5),('ff808081232096ef012320b833f6004c','ff808081232096ef012320b833ba0002','a530cdb3-6e6a-457f-b375-6f96ecc9d417','个人缴社保',NULL,12,NULL,0,16),('ff808081232096ef012320b833f6004d','ff808081232096ef012320b833ba0002','ace22b89-85f6-4061-a605-6ec205e3e725','社保种类',NULL,12,NULL,0,12),('ff808081232096ef012320b833f6004e','ff808081232096ef012320b833ba0002','b7f49a7a-a778-4ee6-9531-bc8c2bca08fc','公司代缴公积金',NULL,12,NULL,0,19),('ff808081232096ef012320b833f6004f','ff808081232096ef012320b833ba0002','d14bfc2e-dcc5-48de-bca1-ebfb32fd8b22','个人缴社保总额',NULL,12,NULL,0,21),('ff808081232096ef012320b833f60050','ff808081232096ef012320b833ba0002','d621c88b-7561-44ee-8628-1429ca3974c2','综合保险基数',NULL,12,NULL,0,15),('ff808081232096ef012320b833f60051','ff808081232096ef012320b833ba0002','e7f72fb0-9bbb-4e68-8dbb-553757c97390','三级部门',NULL,16,NULL,0,6),('ff808081232096ef012320b833f60052','ff808081232096ef012320b833ba0002','ed7f032d-0290-4b2d-9bb6-62bf60d03a2d','养老保险号',NULL,20,NULL,0,24),('ff808081232096ef012320b833f60054','ff808081232096ef012320b833ba0002','f2f27d98-1b0c-4444-a300-e8ea564a8e1d','工作地区',NULL,12,NULL,0,7),('ff808081232096ef012320b833f60055','ff808081232096ef012320b833ba0002','fcf600bc-43cb-4508-b884-6b875b79fc0b','部门',NULL,16,NULL,0,3),('ff808081232096ef012321e796850088','ff808081232096ef012321e796490087','0010f8c8-8a16-459d-80ad-6e378e7b54f6','生效年月',NULL,10,NULL,0,23),('ff808081232096ef012321e796850089','ff808081232096ef012321e796490087','25d1ae0a-da72-4b91-9cd8-8be18bbff2fa','公积金号',NULL,20,NULL,0,25),('ff808081232096ef012321e79685008a','ff808081232096ef012321e796490087','37362198-7e48-4fe0-94b6-62e792d3c0d0','公积金基数','2',12,'avg',0,14),('ff808081232096ef012321e79685008b','ff808081232096ef012321e796490087','3a9e089f-c6f1-471d-83bb-bf77369484c7','用工形式',NULL,12,NULL,0,9),('ff808081232096ef012321e79685008c','ff808081232096ef012321e796490087','3b17a2d4-ffd7-44f7-afab-8442f5e7f430','个人缴公积金',NULL,12,'sum',0,18),('ff808081232096ef012321e79685008d','ff808081232096ef012321e796490087','402880e423166cb1012316813d560001','社保备注',NULL,20,NULL,0,27),('ff808081232096ef012321e796850092','ff808081232096ef012321e796490087','402880f02304355e012307aed6e10038','分公司',NULL,16,NULL,0,3),('ff808081232096ef012321e79685009c','ff808081232096ef012321e796490087','402880f02304355e012307b5266c004e','入职日期','yyyy-MM-dd',12,NULL,0,10),('ff808081232096ef012321e7968f00b5','ff808081232096ef012321e796490087','402880f02304355e012307c0c6ec0082','状态',NULL,10,NULL,0,11),('ff808081232096ef012321e7968f00c7','ff808081232096ef012321e796490087','4affd66f-2315-4b5c-b32e-2b46d9859cdc','公司代缴社保总额',NULL,12,'sum',0,22),('ff808081232096ef012321e7968f00c8','ff808081232096ef012321e796490087','533fa122-eff9-4ca6-9b33-d2f9544a00fa','医疗保险号',NULL,20,NULL,0,26),('ff808081232096ef012321e7968f00c9','ff808081232096ef012321e796490087','54f3ade0-d706-4e4c-a551-fe59700c07ae','工号',NULL,10,NULL,3,1),('ff808081232096ef012321e7968f00ca','ff808081232096ef012321e796490087','663edc97-d0a7-4d49-8bbc-ac1e8bae1fb2','姓名',NULL,10,NULL,0,2),('ff808081232096ef012321e7968f00cb','ff808081232096ef012321e796490087','665e0d4a-6d38-495b-bd3d-13f7236f731d','其他福利项',NULL,12,'sum',0,20),('ff808081232096ef012321e7968f00cc','ff808081232096ef012321e796490087','673f10bd-79d5-45a8-9984-4aa0ff0d6804','公司代缴社保',NULL,12,'sum',0,17),('ff808081232096ef012321e7968f00cd','ff808081232096ef012321e796490087','7d0050a8-6052-43c1-8441-2fa64dfc91a3','职位描述',NULL,10,NULL,0,8),('ff808081232096ef012321e7968f00ce','ff808081232096ef012321e796490087','978f1c52-3dc8-4295-9ca9-0dca2f098bba','社保基数',NULL,12,'avg',0,13),('ff808081232096ef012321e7968f00cf','ff808081232096ef012321e796490087','980ba3f4-0d3d-4c1b-b079-8f272f433c4e','一级部门',NULL,12,NULL,0,4),('ff808081232096ef012321e7968f00d0','ff808081232096ef012321e796490087','990c160e-c0ce-48d7-9a07-52563b8cbe20','二级部门',NULL,12,NULL,0,5),('ff808081232096ef012321e7968f00d1','ff808081232096ef012321e796490087','a530cdb3-6e6a-457f-b375-6f96ecc9d417','个人缴社保',NULL,12,'sum',0,16),('ff808081232096ef012321e7968f00d2','ff808081232096ef012321e796490087','ace22b89-85f6-4061-a605-6ec205e3e725','社保种类',NULL,12,NULL,0,12),('ff808081232096ef012321e7968f00d3','ff808081232096ef012321e796490087','b7f49a7a-a778-4ee6-9531-bc8c2bca08fc','公司代缴公积金',NULL,12,'sum',0,19),('ff808081232096ef012321e7968f00d4','ff808081232096ef012321e796490087','d14bfc2e-dcc5-48de-bca1-ebfb32fd8b22','个人缴社保总额',NULL,12,'sum',0,21),('ff808081232096ef012321e7968f00d5','ff808081232096ef012321e796490087','d621c88b-7561-44ee-8628-1429ca3974c2','综合保险基数',NULL,12,'avg',0,15),('ff808081232096ef012321e7968f00d6','ff808081232096ef012321e796490087','e7f72fb0-9bbb-4e68-8dbb-553757c97390','三级部门',NULL,16,NULL,0,6),('ff808081232096ef012321e7968f00d7','ff808081232096ef012321e796490087','ed7f032d-0290-4b2d-9bb6-62bf60d03a2d','养老保险号',NULL,20,NULL,0,24),('ff808081232096ef012321e7968f00d9','ff808081232096ef012321e796490087','f2f27d98-1b0c-4444-a300-e8ea564a8e1d','工作地区',NULL,12,NULL,0,7),('ff808081232096ef012321e7968f00da','ff808081232096ef012321e796490087','fcf600bc-43cb-4508-b884-6b875b79fc0b','部门',NULL,16,NULL,1,0),('ff808081232096ef0123221a6b7e0129','ff808081232096ef0123221a6b420127','1fa208b1-18e5-43f7-b8f9-fb1fbd6788d8','部门',NULL,16,NULL,0,3),('ff808081232096ef0123221a6b7e012a','ff808081232096ef0123221a6b420127','21359e01-99fd-4b10-83fc-abc99360157a','用工形式',NULL,12,NULL,0,9),('ff808081232096ef0123221a6b88012f','ff808081232096ef0123221a6b420127','402880f02304355e012307fee18b00b0','二级部门',NULL,12,NULL,0,5),('ff808081232096ef0123221a6b880131','ff808081232096ef0123221a6b420127','402880f02304355e012307fee18b00b2','三级部门',NULL,16,NULL,0,6),('ff808081232096ef0123221a6b880135','ff808081232096ef0123221a6b420127','402880f02304355e012308017a3700bd','职位描述',NULL,10,NULL,0,8),('ff808081232096ef0123221a6b880137','ff808081232096ef0123221a6b420127','402880f02304355e01230805d5d100c1','入职日期','yyyy-MM-dd',12,NULL,0,10),('ff808081232096ef0123221a6b880144','ff808081232096ef0123221a6b420127','402880f02304355e0123081ac1d500d8','社保种类',NULL,12,NULL,0,11),('ff808081232096ef0123221a6b880164','ff808081232096ef0123221a6b420127','402880f0230992e7012309a9b6e40035','薪资级别',NULL,12,NULL,0,12),('ff808081232096ef0123221a6b880165','ff808081232096ef0123221a6b420127','402880f0230992e7012309a9b6e40036','薪资等级',NULL,12,NULL,0,13),('ff808081232096ef0123221a6b880166','ff808081232096ef0123221a6b420127','402880f0230992e7012309a9b6e40037','银行帐号',NULL,20,NULL,0,15),('ff808081232096ef0123221a6b880167','ff808081232096ef0123221a6b420127','402880f0230992e7012309a9b6e40038','银行开户行',NULL,20,NULL,0,14),('ff808081232096ef0123221a6b88016b','ff808081232096ef0123221a6b420127','62a6b61e-ab87-403d-bd91-812f07c66749','帐套名称',NULL,20,NULL,0,16),('ff808081232096ef0123221a6b88016c','ff808081232096ef0123221a6b420127','69f3cf82-a924-42e6-868f-7a3e12b995ce','备注',NULL,20,NULL,0,17),('ff808081232096ef0123221a6b88016e','ff808081232096ef0123221a6b420127','72b8f8f2-48f7-4eb9-aae3-e46dab721fa3','姓名',NULL,10,NULL,0,1),('ff808081232096ef0123221a6b88016f','ff808081232096ef0123221a6b420127','751634b9-ac30-4bc9-92eb-3fb660ee5cb0','一级部门',NULL,12,NULL,0,4),('ff808081232096ef0123221a6b880171','ff808081232096ef0123221a6b420127','9c684133-459a-4778-bcd2-8772302e61c3','工作地区',NULL,12,NULL,0,7),('ff808081232096ef0123221a6b880172','ff808081232096ef0123221a6b420127','eaeb77b0-d0bd-4b8a-b37c-c39580dbf5e1','工号',NULL,10,NULL,0,0),('ff808081232096ef0123237a95bc01e1','ff808081232096ef0123237a954e01e0','019ca49f-8be6-4cf4-9ec7-7942c1be9db9','考勤日期','yyyy-MM-dd',12,NULL,3,13),('ff808081232096ef0123237a95bc01e2','ff808081232096ef0123237a954e01e0','06cf0d59-9b10-4f3e-9429-a3e108615b70','迟到(分钟)',NULL,10,NULL,0,18),('ff808081232096ef0123237a95bc01e3','ff808081232096ef0123237a954e01e0','0bedc9bd-b27d-4da6-89d7-bc092500eed8','加班(小时)',NULL,10,NULL,0,21),('ff808081232096ef0123237a95bc01e5','ff808081232096ef0123237a954e01e0','2dd22aa5-5067-4466-aaf1-47e8c7416a19','姓名',NULL,20,NULL,0,1),('ff808081232096ef0123237a95bc01e6','ff808081232096ef0123237a954e01e0','7463df78-7a41-4a8c-b96b-6a8dc15491f1','考勤班次',NULL,10,NULL,0,14),('ff808081232096ef0123237a95bc01e7','ff808081232096ef0123237a954e01e0','8eaa3bdd-fef4-43db-bf22-e810fd92f523','早退(分钟)',NULL,10,NULL,0,19),('ff808081232096ef0123237a95bc01e8','ff808081232096ef0123237a954e01e0','9210faff-820c-4bfe-9de8-01176ed7e61c','异常描述',NULL,20,NULL,0,23),('ff808081232096ef0123237a95bc01e9','ff808081232096ef0123237a954e01e0','95713fee-4eb1-4624-9c37-1e35e6d1d901','上班时间','HH:mm',10,NULL,0,15),('ff808081232096ef0123237a95bc01ea','ff808081232096ef0123237a954e01e0','b4decc7f-81cd-48e7-937c-15b191c1af1e','请假(小时)',NULL,10,NULL,0,20),('ff808081232096ef0123237a95bc01eb','ff808081232096ef0123237a954e01e0','c5e22b6c-4602-40db-8e6a-ac22ce09aa0f','下班时间','HH:mm',10,NULL,0,16),('ff808081232096ef0123237a95bc01ec','ff808081232096ef0123237a954e01e0','de678597-584c-45ef-919c-f0d21d394385','缺勤(小时)',NULL,10,NULL,0,22),('ff808081232096ef0123237a95bc01ed','ff808081232096ef0123237a954e01e0','f9e15593-ed45-4023-b3e1-fb4b1ea076dc','工号',NULL,20,NULL,3,0),('ff808081232096ef0123237a95bc01f4','ff808081232096ef0123237a954e01e0','ff808081232096ef0123236fe20e01b8','部门',NULL,16,NULL,0,3),('ff808081232096ef0123237a95bc01fc','ff808081232096ef0123237a954e01e0','ff808081232096ef01232373933e01ca','工作地区',NULL,12,NULL,0,7),('ff808081232096ef0123237a95bc0202','ff808081232096ef0123237a954e01e0','ff808081232096ef01232375ec5701da','考勤方式',NULL,12,NULL,0,10),('ff808081232096ef0123237a95bc0203','ff808081232096ef0123237a954e01e0','ff808081232096ef01232375ec5701db','考勤卡号',NULL,12,NULL,0,11),('ff808081232096ef0123237a95bc0204','ff808081232096ef0123237a954e01e0','ff808081232096ef01232375ec6101dc','状态',NULL,10,NULL,0,12);
/*!40000 ALTER TABLE `outmatch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outmatchbasic`
--

DROP TABLE IF EXISTS `outmatchbasic`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `outmatchbasic` (
  `omb_id` varchar(36) NOT NULL,
  `omb_io_id` varchar(36) NOT NULL,
  `omb_field_name` varchar(64) NOT NULL,
  `omb_field_desc` varchar(64) NOT NULL,
  `omb_data_type` varchar(32) NOT NULL,
  `omb_format` varchar(32) default NULL,
  `omb_column_width` int(10) unsigned NOT NULL default '15',
  `omb_can_group` int(10) unsigned NOT NULL default '0',
  `omb_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`omb_id`),
  KEY `FK_omb_out_id` USING BTREE (`omb_io_id`),
  CONSTRAINT `FK_omb_io_id` FOREIGN KEY (`omb_io_id`) REFERENCES `iodef` (`io_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `outmatchbasic`
--

LOCK TABLES `outmatchbasic` WRITE;
/*!40000 ALTER TABLE `outmatchbasic` DISABLE KEYS */;
INSERT INTO `outmatchbasic` VALUES ('0010f8c8-8a16-459d-80ad-6e378e7b54f6','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfStartMonth','生效年月','string',NULL,10,1,78),('0119c887-ae6e-4ec8-b5d8-27c651b3fc98','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empCurrZip','当前住址邮编','string',NULL,10,0,43),('01303bf8-52ff-4395-a038-aa7646fb40d9','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empSpeciality','专业','string',NULL,20,1,31),('01590c79-0b64-4a05-bdda-8f3d48eb9557','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftTransferDate','变动日期','date','yyyy-MM-dd',12,0,68),('019ca49f-8be6-4cf4-9ec7-7942c1be9db9','032ee263-9f5b-46b8-bede-6491a8fa7730','examinDate','考勤日期','date','yyyy-MM-dd',12,0,25),('025e2b3f-012e-4e83-a9ee-d31facf0173c','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empName','姓名','string',NULL,10,0,1),('02f27d3d-0226-4f4f-bce0-b009845a0aa2','68ee8cfa-4788-4290-b265-f6f4c14e4889','empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('034fd99c-996d-4be1-884d-acad99520cfa','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpYearMonth','缴费年月','string',NULL,10,1,22),('03fec031-9862-47ca-b768-83c255def9dc','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('041be66e-332d-486e-857b-96b1193ffc8a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo1.DepartmentDesc','一级部门描述','string','',20,1,11),('047d54ce-296a-44c1-b64c-e301864c5bd6','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empType.emptypeName','用工形式','string',NULL,12,1,17),('0564dcee-3658-48cb-bc62-28f0ad06e5ae','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empIdentificationNo','证件号码','string',NULL,20,0,26),('05be09a5-ed85-404b-b5e3-64ca87a3bfca','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empWorkPhone','工作电话','string',NULL,16,0,39),('06546844-6d7e-4b7c-acab-3518c07b3f0b','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espLocation.locationDesc','工作地区描述','string',NULL,20,1,17),('06cf0d59-9b10-4f3e-9429-a3e108615b70','032ee263-9f5b-46b8-bede-6491a8fa7730','lateMinutes','迟到(分钟)','decimal',NULL,10,0,32),('0710b4b3-9c17-4a53-bc60-e019de179a2a','5d46f106-6099-478c-8911-9c684222cf4c','employee.empBranch.departmentName','分公司','string',NULL,16,1,6),('0744392c-39d2-40eb-b2dc-5e95ea67f654','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional16','自定义字段16','string',NULL,15,1,67),('07827c48-15e5-48df-ba64-46ecb7d86d40','5d46f106-6099-478c-8911-9c684222cf4c','employee.empStatus','状态','empStatus',NULL,10,0,50),('08a271ac-fceb-4712-a5ac-ddde9d914bfa','dab34b21-15cd-42b0-beb9-8c66d1f49a22','englishName','英文名','string','',20,0,2),('08b10aa9-500d-4068-a1e4-1d0283b6fb73','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional14','自定义字段14','string','',15,1,65),('08ce8fbc-4211-412b-b5f3-3fe916799a97','801f19ca-c8a7-4550-be9c-8ab0950631a3','empLocationNo.locationName','工作地区','string','',12,1,16),('08d4c1da-c41d-4c27-8573-15e83b91bb74','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional5','自定义字段05','string',NULL,15,1,56),('09437a74-7d5d-4456-acba-869cf84edbef','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional6','自定义字段06','string','',15,1,57),('0964e0be-e8f2-4a7a-8352-5a5825a62f92','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional14','自定义字段14','string',NULL,15,1,65),('096e2e59-57d5-449d-8927-dd33c58eb2e9','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empPolitics','政治面貌','string',NULL,10,1,28),('0997954c-e1eb-4051-b019-ecf7fb2d1a97','801f19ca-c8a7-4550-be9c-8ab0950631a3','empPolitics','政治面貌','string','',10,1,28),('09e02158-67a6-42d6-88da-21724b2224aa','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empStatus','状态','integer',NULL,10,1,50),('0a3a151a-8d84-4fef-9ef7-ba3528a043da','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDiploma','学历','string','',10,1,29),('0a63cc55-aab6-4463-9d7f-06e11054b346','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.englishName','英文名','string',NULL,20,0,2),('0a8ab6b9-9c1b-4af4-a8e6-5e68c5806798','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('0abf630f-f2ec-4a7a-bdb9-955222f68588','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDistinctNo','工号','string',NULL,10,0,1),('0ac6f52d-d902-4acc-9658-73aaede44bab','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empPbNo.pbName','职位','string',NULL,10,1,15),('0b069281-4bd5-4a64-ba2e-74a7160f04bc','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional1','自定义字段01','string',NULL,15,1,52),('0bd1fd4e-ab57-4ec8-b2ef-db9ef94c8865','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empSpeciality','专业','string',NULL,20,1,31),('0bd555a4-8350-4599-a5a3-8ae395e7a783','801f19ca-c8a7-4550-be9c-8ab0950631a3','empUrgentContact','紧急联系人','string','',10,0,46),('0bedc9bd-b27d-4da6-89d7-bc092500eed8','032ee263-9f5b-46b8-bede-6491a8fa7730','overtimeHours','加班(小时)','decimal',NULL,10,0,36),('0bf7ec22-6f9c-4106-8327-9a593f0ce071','68ee8cfa-4788-4290-b265-f6f4c14e4889','empCurrZip','当前住址邮编','string',NULL,10,0,43),('0e10d2e2-d1e0-4764-93f0-a8ae7692f1ea','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEsavId.esavEsac.esacDesc','帐套描述','string',NULL,20,1,20),('0fe502cf-231c-4b15-9193-2f90cbc5d191','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empName','姓名','string',NULL,10,0,1),('1030b83f-b61e-4c65-b6f1-ebe84fa2225b','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empShiftNo','考勤卡号','string','',12,0,49),('11099558-e271-4fc7-99ce-fd6dc2b831ea','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional9','自定义字段09','string',NULL,15,1,60),('118197c6-3cda-47b9-b201-45b9c2e66c7c','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('119bca26-dd0b-4e8c-9ba1-264286c3f4dd','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional11','自定义字段11','string',NULL,15,1,62),('141e1775-f309-4e0e-9de9-dff37b873e6e','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empCityNo','籍贯','string','',10,1,32),('15851225-c69e-47ed-90f1-991d533e99dd','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empEmail','邮箱','string',NULL,30,0,37),('15b789c1-8631-45c0-ba0f-0c2835b545c3','5d46f106-6099-478c-8911-9c684222cf4c','employee.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('1637a2e6-b2d1-447e-b40d-80ebe644a738','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empMarital','婚否','empMarital',NULL,6,1,5),('1644c1a6-e82c-4422-9c0a-3d796b540b62','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional10','自定义字段10','string',NULL,15,1,61),('16ec6ca2-3eaa-4a9d-a350-23092bf87955','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo2.departmentDesc','原二级部门描述','string',NULL,20,1,74),('1703bdeb-43d6-434c-8079-adc5c89d5daa','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('1720e1f1-028a-4b96-91b0-28d9ad6c6833','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional12','自定义字段12','string',NULL,15,1,63),('1760909c-1ce0-4bad-a519-36a3982575a2','5d46f106-6099-478c-8911-9c684222cf4c','employee.empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('17d2c017-fa26-4b88-bdd8-b4582ca407cd','5d46f106-6099-478c-8911-9c684222cf4c','etcExpire','合同期限','etcExpire',NULL,12,0,74),('17dfa640-0b08-4e1c-add5-43b8cab1c46f','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empType.emptypeDesc','用工形式描述','string',NULL,20,1,18),('187a0b49-f906-41d3-8d91-effc00627228','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empBlood','血型','string','',6,1,27),('18c11002-c04a-4900-a930-1eab9a5e8b05','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiEsav.esavEsac.esacName','帐套名称','string',NULL,15,1,0),('18d92d44-03d6-4b0e-9e6c-66cdcc31185c','68ee8cfa-4788-4290-b265-f6f4c14e4889','empProfileLoc','档案所在地','string',NULL,26,0,35),('1946eea1-bbbf-420a-a711-65e832c808d6','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo.departmentName','部门','string','',16,1,8),('19604254-9aec-42f7-8bad-e7d4b868c819','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empNation','民族','string',NULL,6,1,33),('19615170-1ae2-4abd-b33f-2cf1ed1650ae','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,21),('19759ba4-c97d-4d00-a5c3-298f79121977','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empCurrZip','当前住址邮编','string','',10,0,43),('1a3cff93-4e6f-4ce3-bf6c-1d5deeb8cd7a','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('1aa37c05-c985-4a2c-93da-f00728689c8d','68ee8cfa-4788-4290-b265-f6f4c14e4889','empLocationNo.locationName','工作地区','string',NULL,12,1,16),('1aa79122-a462-4874-ba7e-3c3715609537','cca1a5ac-6140-421c-9eea-848c615b8306','esaiEsdd.esddDataType','项目种类','esddDataType',NULL,12,0,1),('1c1a7a0b-ff68-4f03-b837-956d6f3962f5','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('1c73f90e-1276-4f62-a648-6e94235fbfa7','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('1cb1ca01-4c54-4e71-9ad5-c405973533e0','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('1cfae76b-1cc5-4d7f-a7d7-96276aa29c97','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional4','自定义字段04','string',NULL,15,1,55),('1d2d72e1-9ad5-4e95-8c38-5dbdbd1640c1','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo3.DepartmentName','三级部门','string','',16,1,14),('1d770be7-f80c-4948-a2cf-241dd4b559b5','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional7','自定义字段07','string',NULL,15,1,58),('1dc105a5-c169-49b1-867a-137a863e3bf3','801f19ca-c8a7-4550-be9c-8ab0950631a3','empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('1e3b49a7-04d6-4273-a42f-5b62f439809d','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empResidenceLoc','户口所在地','string',NULL,26,0,34),('1e3b814d-6dbf-4d2a-bcad-c5878f20f658','68ee8cfa-4788-4290-b265-f6f4c14e4889','empShiftNo','考勤卡号','string',NULL,12,0,49),('1e4056ee-e76d-41c3-beb1-d265266cb74e','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional16','自定义字段16','string',NULL,15,1,67),('1ef4d85c-6620-4bf2-9ff4-63f484e4b188','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewEmpType.emptypeDesc','现用工形式描述','string',NULL,20,1,21),('1fa208b1-18e5-43f7-b8f9-fb1fbd6788d8','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDept.departmentName','部门','string',NULL,16,1,8),('21359e01-99fd-4b10-83fc-abc99360157a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmptype.emptypeName','用工形式','string',NULL,12,1,20),('217fd9b3-f2dd-4f90-bd5c-071c7e4966a8','032ee263-9f5b-46b8-bede-6491a8fa7730','dutyHours','工时','decimal',NULL,10,0,29),('22648ff9-f2a0-4ee1-bcdf-302811041cc5','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional13','自定义字段13','string','',15,1,64),('231d2444-424d-486b-b405-74e1ab14ae88','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOffDutyHours','缺勤(小时)','decimal','2',12,0,23),('23753465-fd18-4b2b-a046-aaa2409cc4fd','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo2.DepartmentDesc','二级部门描述','string','',20,1,13),('239440ff-7216-4652-ad6d-8675c3d81ecd','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional2','自定义字段02','string',NULL,15,1,53),('23b71fbe-641d-4b46-9a41-1f99169780b1','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('24c36dbf-2147-4cd1-b146-cda1a178d74f','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('24f813a2-3f02-47fc-83a4-8a47453a4fff','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empUrgentContact','紧急联系人','string',NULL,10,0,46),('250e48b6-8800-4094-a4f4-28d1cb87e77d','5d46f106-6099-478c-8911-9c684222cf4c','employee.empResidenceLoc','户口所在地','string',NULL,26,1,34),('25d1ae0a-da72-4b91-9cd8-8be18bbff2fa','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfHousingNo','公积金号','string',NULL,20,0,80),('26406cf2-c418-4277-8cb6-4898bc92c1c8','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmComments','月考勤备注','string',NULL,26,0,57),('265319ff-8364-4979-b645-3aa1550d2678','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional14','自定义字段14','string',NULL,15,1,65),('26a54c3b-f565-47f9-ad56-b7ff1a51682e','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empMsn','MSN/QQ','string',NULL,30,0,38),('26f4ea56-a380-4dd8-a8c0-c7db70d59e97','68ee8cfa-4788-4290-b265-f6f4c14e4889','empBranch.departmentName','分公司','string',NULL,16,1,6),('284ec370-a94f-48be-a99c-2ddba0cf95a2','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional13','自定义字段13','string',NULL,15,1,64),('28adc410-29cf-4cce-b670-0053b89e3431','801f19ca-c8a7-4550-be9c-8ab0950631a3','empIdentificationNo','证件号码','string','',20,0,26),('28fd489d-2882-4b4e-be16-518f9094feab','801f19ca-c8a7-4550-be9c-8ab0950631a3','quit.eqReason','离职原因','string','',16,1,71),('29648070-40f2-4b7e-a10d-6c8d777f268f','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empBranch.departmentDesc','分公司描述','string','',20,1,7),('29cbe904-8de4-47ba-ae64-9ec7755e7cdd','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('29ed807d-2d4e-459d-8905-5725bdf6165e','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empName','姓名','string','',10,0,1),('29fa9dde-8ac0-4d38-894a-ba91360642c7','68ee8cfa-4788-4290-b265-f6f4c14e4889','empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('2a0676ae-ce07-4536-a64d-ba5ad0cb125c','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empMarital','婚否','empMarital','',6,1,5),('2b2e7d2e-3bb6-40d6-8250-ff14ac05053e','68ee8cfa-4788-4290-b265-f6f4c14e4889','empName','姓名','string',NULL,10,0,1),('2c0b0089-3be2-406b-a3dc-c2c196cd1945','801f19ca-c8a7-4550-be9c-8ab0950631a3','empSchool','毕业院校','string','',20,0,30),('2c6cd148-38f1-4604-b009-fe800af4ba85','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo3.Departmentdesc','三级部门描述','string','',20,1,15),('2d789820-f07c-42f7-9a25-48e0607ad5a0','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','eeComments','考评备注','string',NULL,20,0,80),('2dd1e92e-02f5-4a1b-91e1-220b635da92e','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','eeRate','考评结果','string',NULL,12,0,79),('2dd22aa5-5067-4466-aaf1-47e8c7416a19','032ee263-9f5b-46b8-bede-6491a8fa7730','empName','姓名','string',NULL,20,0,1),('2f08d5a2-2d8d-4c8c-aadb-41a87bdda794','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldPbId.pbName','原职位','string',NULL,16,1,75),('303777df-9ffc-44b5-bd59-af53ec7e059f','801f19ca-c8a7-4550-be9c-8ab0950631a3','quit.eqType','离职种类','eqType','',12,1,69),('32a9236b-8ee4-44cd-87ae-9bbc69749369','5d46f106-6099-478c-8911-9c684222cf4c','employee.empCityNo','籍贯','string',NULL,10,1,32),('332ad135-3e1b-442c-acc1-f82d495a6a2c','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewBranch.departmentDesc','现分公司描述','string',NULL,20,1,7),('3372b18a-d155-4cd9-aec6-4d3c7b370770','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empPolitics','政治面貌','string',NULL,10,1,28),('345b5657-86bc-4197-bfa5-a4022b5de14e','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('34ae71ca-7284-4e20-8b48-802f136d2311','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empResidenceLoc','户口所在地','string',NULL,26,0,34),('34b0e3a1-464b-47b9-8ccf-b03575417445','cca1a5ac-6140-421c-9eea-848c615b8306','esaiDataCalc','计算公式','string',NULL,30,0,4),('34f55b3d-306b-47be-94d8-d5d4d20bec2f','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo2.DepartmentDesc','二级部门描述','string','',20,1,13),('35bba8f2-5e83-4f72-a88f-31c43cb3c15e','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldPbId.pbDesc','原职位描述','string',NULL,20,1,76),('36d6f89a-69e9-4edd-8f9b-eed6331b07b3','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveSickHours','病假(小时)','decimal','2',12,0,35),('36d8afeb-7e70-4066-acf6-fd1b89d64afc','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('36e1f3ed-2491-4a5e-8fc6-29f2643d0504','68ee8cfa-4788-4290-b265-f6f4c14e4889','empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('37080208-d2ec-41cb-81e4-c9c7dbe5e769','68ee8cfa-4788-4290-b265-f6f4c14e4889','empSpeciality','专业','string',NULL,20,1,31),('37121620-7dcc-4ea3-88f8-398546fb6eba','801f19ca-c8a7-4550-be9c-8ab0950631a3','empResidenceLoc','户口所在地','string','',26,0,34),('37362198-7e48-4fe0-94b6-62e792d3c0d0','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfHousingAmount','公积金基数','decimal',NULL,12,0,69),('37f9d407-1800-423c-83a0-16098c8e9022','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empPbNo.pbName','职位','string','',10,1,18),('38133908-414d-491d-b26d-486fafc1a657','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empHomeAddr','家庭地址','string',NULL,30,0,44),('382085e8-55f7-4959-840d-516f30652862','5d46f106-6099-478c-8911-9c684222cf4c','employee.englishName','英文名','string',NULL,20,0,2),('386c4c9d-cb36-4650-a513-4983f17040ad','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveOtherHours','其他请假(小时)','decimal','2',12,0,51),('393e200b-d55c-4672-9c61-5300fa9b718f','5d46f106-6099-478c-8911-9c684222cf4c','employee.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('39bc1829-d802-4253-8641-3ddb22dd255e','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDept.departmentDesc','部门描述','string',NULL,20,1,9),('39bc683a-249e-468e-aaa8-c820e873a036','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOffDutyDays','缺勤(天)','decimal','2',12,0,24),('3a641836-1abc-42b2-8f38-2ad68dd2657e','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empCurrAddr','当前住址','string',NULL,30,0,42),('3a9e089f-c6f1-471d-83bb-bf77369484c7','53979184-f70f-4360-b548-635dde0fc7ec','empType.emptypeName','用工形式','string',NULL,12,1,20),('3ac9ffa7-dba6-4254-84f3-a363eed11b3f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empMobile','手机','string','',16,0,40),('3adeb5d9-5f37-459c-9600-95fb36a9351d','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmDutyHours','全勤(小时)','decimal','2',12,0,19),('3b17a2d4-ffd7-44f7-afab-8442f5e7f430','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn12','个人缴公积金','decimal',NULL,12,0,73),('3b305b10-900d-4b08-81b8-6ebb6ee6e1ab','5d46f106-6099-478c-8911-9c684222cf4c','employee.empNation','民族','string',NULL,6,0,33),('3ba4b17e-c243-4c03-882b-e5adbb7c8f1a','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional3','自定义字段03','string',NULL,15,1,54),('3bf03330-2040-4ec6-ad97-a4001ded2654','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empType.emptypeName','用工形式','string',NULL,12,1,20),('3c094b2d-ffb6-463f-a39f-54fe6a5b9c96','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDiploma','学历','string',NULL,10,1,29),('3c3f9bf0-4ea5-49ee-8083-6f53f8fde5e9','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional16','自定义字段16','string',NULL,15,1,67),('3c8993bd-af33-491e-bef7-e6315998a7bb','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional3','自定义字段03','string',NULL,15,1,54),('3ca62228-c99f-4f0a-83bf-ebe2edd63bdc','5d46f106-6099-478c-8911-9c684222cf4c','employee.empMobile','手机','string',NULL,16,0,40),('3d888872-e780-4cf1-8418-c15fb2ca482a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empUrgentConMethod','紧急联系方式','string','',20,0,47),('3d8e9348-332f-4b73-8952-aecba6bd5fd6','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empPolitics','政治面貌','string',NULL,10,1,28),('3da7fa96-7651-45ba-b5d7-51019a172e55','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empShiftNo','考勤卡号','string',NULL,12,0,49),('3e4329b4-2ff0-4a60-9b3d-87e334b0afc3','68ee8cfa-4788-4290-b265-f6f4c14e4889','empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('3e8ac800-5299-4aa8-8729-4711f35eae99','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo.departmentName','部门','string',NULL,16,1,8),('3ef31fb0-f4a1-4fce-a312-e95d50195070','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional10','自定义字段10','string',NULL,15,1,61),('3f3606a1-bf8d-478a-8378-4d3e1950046a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional9','自定义字段09','string','',15,1,60),('3f4d0a34-b0ee-4773-aee4-fcac24bb0b3b','5d46f106-6099-478c-8911-9c684222cf4c','employee.empShiftNo','考勤卡号','string',NULL,12,1,49),('402880e423166cb1012316813d560001','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfComments','社保备注','string',NULL,20,0,82),('402880e423785e4001237882bc880001','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewPbId.pbDesc','现职位描述','string',NULL,10,0,19),('402880e423785e400123788dad110003','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empBenefitType.benefitTypeName','社保种类','string',NULL,12,0,36),('402880e423785e4001237898641c0005','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftComments','变动备注','string',NULL,20,0,79),('402880e423785e40012378d8d7fd00a6','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empBranch.departmentName','分公司','string',NULL,16,1,6),('402880e423785e40012378d8d7fd00a7','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('402880e423785e40012378d8d7fd00a8','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('402880e423785e40012378d8d7fd00a9','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('402880e423785e40012378dce3b100ae','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('402880e423785e40012378dce3b100af','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('402880e423785e40012378dce3b100b0','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empPbNo.pbName','职位','string',NULL,10,1,18),('402880e423785e40012378dce3b100b1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('402880e423785e40012378dce3b100b2','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empType.emptypeName','用工形式','string',NULL,12,1,20),('402880e423785e40012378dce3b100b3','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('402880e423785e40012378e28e1200ba','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('402880e423785e40012378ee29d000bc','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo.departmentName','部门','string',NULL,16,1,8),('402880e423785e40012378ee29d000bd','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('402880e423785e40012378ee29d000be','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('402880e423785e40012378ee29d000bf','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('402880e423785e40012378ee29d000c0','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('402880e423785e40012378ee29d000c1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('402880e423792e7f012379653a860054','efa905bc-c55a-4678-b21b-2ea94067f178','employee.englishName','英文名','string',NULL,20,0,2),('402880e423792e7f012379653a860055','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('402880e423792e7f0123796d40e80058','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('402880e42379dc1e01237a456e950001','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('402880e623dfd4180123dfe18e190001','032ee263-9f5b-46b8-bede-6491a8fa7730','oughtDutyHours','全勤(小时)','decimal',NULL,15,0,30),('402880e623dfd4180123dfe18e190002','032ee263-9f5b-46b8-bede-6491a8fa7730','oughtDutyDays','全勤(天)','decimal',NULL,15,0,31),('402880e623dfd4180123dfe18e190003','032ee263-9f5b-46b8-bede-6491a8fa7730','absentDays','缺勤(天)','decimal',NULL,15,0,38),('402880e623dfd4180123dfe18e190004','032ee263-9f5b-46b8-bede-6491a8fa7730','leaveDays','请假(天)','decimal',NULL,15,0,35),('402880e82392e3c90123932c002b0073','5d46f106-6099-478c-8911-9c684222cf4c','employee.empComments','备注','string',NULL,20,0,51),('402880e924a5388e0124a53a28b50001','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDistinctNo','工号','string',NULL,10,0,0),('402880e924a5388e0124a53a28bf0002','eee58bd1-176f-4853-9d50-c4581b1aff2e','empName','姓名','string',NULL,10,0,1),('402880e924a5388e0124a53cabc70005','eee58bd1-176f-4853-9d50-c4581b1aff2e','empBranch.departmentName','分公司','string',NULL,16,1,2),('402880e924a5388e0124a53cabc70006','eee58bd1-176f-4853-9d50-c4581b1aff2e','empBranch.departmentDesc','分公司描述','string',NULL,20,1,3),('402880e924a5388e0124a53cabc70007','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo.departmentName','部门','string',NULL,16,1,4),('402880e924a5388e0124a53cabc70008','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo.departmentDesc','部门描述','string',NULL,20,1,5),('402880e924a5388e0124a53cabc70009','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,6),('402880e924a5388e0124a53cabc7000a','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,7),('402880e924a5388e0124a53cabd1000b','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,8),('402880e924a5388e0124a53cabd1000c','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,9),('402880e924a5388e0124a53cabd1000d','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,10),('402880e924a5388e0124a53cabd1000e','eee58bd1-176f-4853-9d50-c4581b1aff2e','empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,11),('402880e924a5388e0124a53e7dcd0019','eee58bd1-176f-4853-9d50-c4581b1aff2e','empLocationNo.locationName','工作地区','string',NULL,12,1,12),('402880e924a5388e0124a53e7dcd001a','eee58bd1-176f-4853-9d50-c4581b1aff2e','empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,13),('402880e924a5388e0124a53e7dcd001b','eee58bd1-176f-4853-9d50-c4581b1aff2e','empPbNo.pbName','职位','string',NULL,10,1,14),('402880e924a5388e0124a53e7dcd001c','eee58bd1-176f-4853-9d50-c4581b1aff2e','empPbNo.pbDesc','职位描述','string',NULL,10,0,15),('402880e924a5388e0124a53e7dcd001d','eee58bd1-176f-4853-9d50-c4581b1aff2e','empType.emptypeName','用工形式','string',NULL,12,1,16),('402880e924a5388e0124a53e7dcd001e','eee58bd1-176f-4853-9d50-c4581b1aff2e','empType.emptypeDesc','用工形式描述','string',NULL,20,1,17),('402880e924af34030124af69c13d0008','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9039b','日薪基数','decimal','2',15,0,77),('402880e924af34030124af69c13d0009','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9035b','日薪','decimal','2',15,0,78),('402880e924af34030124af69c13d000a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9001b','基本工资','decimal','2',15,0,79),('402880e924af34030124af69c13d000b','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9018b','本月出勤(天)','decimal','2',15,0,80),('402880e924af34030124af69c13d000c','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9007b','本月缺勤(天)','decimal','2',15,0,81),('402880e924af34030124af69c13d000d','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.4028804a1a9a34bf011a9aa3f861000f','年终奖','decimal','2',15,0,82),('402880e924af34030124af69c13d000e','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9010b','浮动项目总额','decimal','2',15,0,83),('402880e924af34030124af69c13d000f','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9011b','税前收入','decimal','2',15,0,84),('402880e924af34030124af69c13d0010','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9025b','社保基数','decimal','2',15,0,85),('402880e924af34030124af69c13d0011','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9026b','公积金基数','decimal','2',15,0,86),('402880e924af34030124af69c13d0012','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9027b','养老保险','decimal','2',15,0,87),('402880e924af34030124af69c13d0013','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9029b','失业保险','decimal','2',15,0,88),('402880e924af34030124af69c13d0014','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9028b','医疗保险','decimal','2',15,0,89),('402880e924af34030124af69c13d0015','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9030b','公积金','decimal','2',15,0,90),('402880e924af34030124af69c13d0016','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9031b','社保总额','decimal','2',15,0,91),('402880e924af34030124af69c13d0017','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880ea1c72f62b011c739788960017','代缴养老保险','decimal','2',15,0,92),('402880e924af34030124af69c1470018','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880ea1c72f62b011c739686d50015','代缴失业保险','decimal','2',15,0,93),('402880e924af34030124af69c1470019','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880ea1cdf262b011ce00d6b830001','代缴医疗保险','decimal','2',15,0,94),('402880e924af34030124af69c147001a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e81d035424011d043ae2710003','代缴工伤保险','decimal','2',15,0,95),('402880e924af34030124af69c147001b','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e81d035424011d043ba5630004','代缴生育保险','decimal','2',15,0,96),('402880e924af34030124af69c147001c','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880ea1c72f62b011c7396dba40016','代缴公积金','decimal','2',15,0,97),('402880e924af34030124af69c147001d','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9034b','代缴社保总额','decimal','2',15,0,98),('402880e924af34030124af69c147001e','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9032b','其他福利扣款','decimal','2',15,0,99),('402880e924af34030124af69c147001f','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.4028804a1a9a34bf011a9aa2e81d000e','应纳税所得额','decimal','2',15,0,100),('402880e924af34030124af69c1470020','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9012b','所得税','decimal','2',15,0,101),('402880e924af34030124af69c1470021','f0f570ec-4057-4ba8-ad13-a1f542828cbe','outPutList.402880e618c1299f0118c12bf7d9013b','税后收入','decimal','2',15,0,102),('402880e924af34030124af69c1e70056','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9027b','养老保险','decimal','2',15,0,35),('402880e924af34030124af69c1e70057','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9029b','失业保险','decimal','2',15,0,36),('402880e924af34030124af69c1e70058','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9028b','医疗保险','decimal','2',15,0,37),('402880e924af34030124af69c1e70059','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9030b','公积金','decimal','2',15,0,38),('402880e924af34030124af69c1e7005a','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9031b','社保总额','decimal','2',15,0,39),('402880e924af34030124af69c1e7005b','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880ea1c72f62b011c739788960017','代缴养老保险','decimal','2',15,0,40),('402880e924af34030124af69c1e7005c','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880ea1c72f62b011c739686d50015','代缴失业保险','decimal','2',15,0,41),('402880e924af34030124af69c1e7005d','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880ea1cdf262b011ce00d6b830001','代缴医疗保险','decimal','2',15,0,42),('402880e924af34030124af69c1e7005e','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e81d035424011d043ae2710003','代缴工伤保险','decimal','2',15,0,43),('402880e924af34030124af69c1e7005f','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e81d035424011d043ba5630004','代缴生育保险','decimal','2',15,0,44),('402880e924af34030124af69c1e70060','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880ea1c72f62b011c7396dba40016','代缴公积金','decimal','2',15,0,45),('402880e924af34030124af69c1e70061','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9034b','代缴社保总额','decimal','2',15,0,46),('402880e924af34030124af69c1e70062','444bd513-c17a-4dc3-ac7a-e73444b00dfb','outPutList.402880e618c1299f0118c12bf7d9032b','其他福利扣款','decimal','2',15,0,47),('402880f02304355e0123072332680007','68ee8cfa-4788-4290-b265-f6f4c14e4889','empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('402880f02304355e0123072dee55000a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empPbNo.pbDesc','职位描述','string','',10,0,19),('402880f02304355e012307304218000c','801f19ca-c8a7-4550-be9c-8ab0950631a3','empType.emptypeDesc','用工形式描述','string','',20,1,21),('402880f02304355e012307827aec000e','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empGender','性别','empGender','',6,1,4),('402880f02304355e012307827aec000f','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empPbNo.pbDesc','职位描述','string','',10,0,19),('402880f02304355e012307827aec0010','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empType.emptypeDesc','用工形式描述','string','',20,1,21),('402880f02304355e0123078adf3d0014','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional1','自定义字段01','string','',15,1,52),('402880f02304355e0123078adf3d0015','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional2','自定义字段02','string','',15,1,53),('402880f02304355e0123078adf3d0016','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional3','自定义字段03','string','',15,1,54),('402880f02304355e0123078adf3d0017','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional4','自定义字段04','string','',15,1,55),('402880f02304355e0123078adf3d0018','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional5','自定义字段05','string','',15,1,56),('402880f02304355e0123078adf3d0019','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional6','自定义字段06','string','',15,1,57),('402880f02304355e0123078adf3d001a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional7','自定义字段07','string','',15,1,58),('402880f02304355e0123078adf3d001b','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional8','自定义字段08','string','',15,1,59),('402880f02304355e0123078adf3d001c','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional9','自定义字段09','string','',15,1,60),('402880f02304355e0123078c42380026','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional10','自定义字段10','string','',15,1,61),('402880f02304355e0123078c42380027','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional11','自定义字段11','string','',15,1,62),('402880f02304355e0123078c42380028','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional12','自定义字段12','string','',15,1,63),('402880f02304355e0123078c42380029','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional13','自定义字段13','string','',15,1,64),('402880f02304355e0123078c4238002a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional14','自定义字段14','string','',15,1,65),('402880f02304355e0123078c4238002b','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional15','自定义字段15','string','',15,1,66),('402880f02304355e0123078c4238002c','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empAdditional16','自定义字段16','string','',15,1,67),('402880f02304355e012307aed6e10034','53979184-f70f-4360-b548-635dde0fc7ec','englishName','英文名','string',NULL,20,0,2),('402880f02304355e012307aed6e10035','53979184-f70f-4360-b548-635dde0fc7ec','empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('402880f02304355e012307aed6e10036','53979184-f70f-4360-b548-635dde0fc7ec','empGender','性别','empGender',NULL,6,1,4),('402880f02304355e012307aed6e10037','53979184-f70f-4360-b548-635dde0fc7ec','empMarital','婚否','empMarital',NULL,6,1,5),('402880f02304355e012307aed6e10038','53979184-f70f-4360-b548-635dde0fc7ec','empBranch.departmentName','分公司','string',NULL,16,1,6),('402880f02304355e012307aed6e10039','53979184-f70f-4360-b548-635dde0fc7ec','empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('402880f02304355e012307aed6e1003a','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('402880f02304355e012307b1db380042','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('402880f02304355e012307b1db380043','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('402880f02304355e012307b1db380044','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('402880f02304355e012307b1db380045','53979184-f70f-4360-b548-635dde0fc7ec','empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('402880f02304355e012307b1db380046','53979184-f70f-4360-b548-635dde0fc7ec','empPbNo.pbName','职位','string',NULL,10,1,18),('402880f02304355e012307b5266c004c','53979184-f70f-4360-b548-635dde0fc7ec','empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('402880f02304355e012307b5266c004d','53979184-f70f-4360-b548-635dde0fc7ec','empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('402880f02304355e012307b5266c004e','53979184-f70f-4360-b548-635dde0fc7ec','empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('402880f02304355e012307b5266c004f','53979184-f70f-4360-b548-635dde0fc7ec','empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('402880f02304355e012307b5266c0050','53979184-f70f-4360-b548-635dde0fc7ec','empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('402880f02304355e012307baecff0056','53979184-f70f-4360-b548-635dde0fc7ec','empBlood','血型','string',NULL,6,1,27),('402880f02304355e012307baecff0057','53979184-f70f-4360-b548-635dde0fc7ec','empPolitics','政治面貌','string',NULL,10,1,28),('402880f02304355e012307baecff0058','53979184-f70f-4360-b548-635dde0fc7ec','empDiploma','学历','string',NULL,10,1,29),('402880f02304355e012307baecff0059','53979184-f70f-4360-b548-635dde0fc7ec','empSchool','毕业院校','string',NULL,20,0,30),('402880f02304355e012307baecff005a','53979184-f70f-4360-b548-635dde0fc7ec','empSpeciality','专业','string',NULL,20,1,31),('402880f02304355e012307baecff005b','53979184-f70f-4360-b548-635dde0fc7ec','empCityNo','籍贯','string',NULL,10,1,32),('402880f02304355e012307baecff005c','53979184-f70f-4360-b548-635dde0fc7ec','empNation','民族','string',NULL,6,1,33),('402880f02304355e012307baecff005d','53979184-f70f-4360-b548-635dde0fc7ec','empResidenceLoc','户口所在地','string',NULL,26,0,34),('402880f02304355e012307baecff005e','53979184-f70f-4360-b548-635dde0fc7ec','empProfileLoc','档案所在地','string',NULL,26,0,35),('402880f02304355e012307baecff005f','53979184-f70f-4360-b548-635dde0fc7ec','empEmail','邮箱','string',NULL,30,0,36),('402880f02304355e012307be0ef0006c','53979184-f70f-4360-b548-635dde0fc7ec','empMsn','MSN/QQ','string',NULL,30,0,37),('402880f02304355e012307be0ef0006d','53979184-f70f-4360-b548-635dde0fc7ec','empWorkPhone','工作电话','string',NULL,16,0,38),('402880f02304355e012307be0ef0006e','53979184-f70f-4360-b548-635dde0fc7ec','empMobile','手机','string',NULL,16,0,39),('402880f02304355e012307be0ef0006f','53979184-f70f-4360-b548-635dde0fc7ec','empHomePhone','家庭电话','string',NULL,16,0,40),('402880f02304355e012307be0ef00070','53979184-f70f-4360-b548-635dde0fc7ec','empCurrAddr','当前住址','string',NULL,30,0,41),('402880f02304355e012307be0ef00071','53979184-f70f-4360-b548-635dde0fc7ec','empCurrZip','当前住址邮编','string',NULL,10,0,42),('402880f02304355e012307be0ef00072','53979184-f70f-4360-b548-635dde0fc7ec','empHomeAddr','家庭地址','string',NULL,30,0,43),('402880f02304355e012307be0ef00073','53979184-f70f-4360-b548-635dde0fc7ec','empHomeZip','家庭地址邮编','string',NULL,10,0,44),('402880f02304355e012307be0ef00074','53979184-f70f-4360-b548-635dde0fc7ec','empUrgentContact','紧急联系人','string',NULL,10,0,45),('402880f02304355e012307be0ef00075','53979184-f70f-4360-b548-635dde0fc7ec','empUrgentConMethod','紧急联系方式','string',NULL,20,0,46),('402880f02304355e012307c0c6ec0080','53979184-f70f-4360-b548-635dde0fc7ec','empShiftType','考勤方式','empShiftType',NULL,12,1,47),('402880f02304355e012307c0c6ec0081','53979184-f70f-4360-b548-635dde0fc7ec','empShiftNo','考勤卡号','string',NULL,12,0,48),('402880f02304355e012307c0c6ec0082','53979184-f70f-4360-b548-635dde0fc7ec','empStatus','状态','empStatus',NULL,10,1,49),('402880f02304355e012307c0c6ec0083','53979184-f70f-4360-b548-635dde0fc7ec','empComments','备注','string',NULL,20,0,50),('402880f02304355e012307c0c6ec0084','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional1','自定义字段01','string',NULL,15,1,51),('402880f02304355e012307c0c6ec0085','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional2','自定义字段02','string',NULL,15,1,52),('402880f02304355e012307c0c6ec0086','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional3','自定义字段03','string',NULL,15,1,53),('402880f02304355e012307c0c6ec0087','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional4','自定义字段04','string',NULL,15,1,54),('402880f02304355e012307c0c6ec0088','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional5','自定义字段05','string',NULL,15,1,55),('402880f02304355e012307c0c6ec0089','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional6','自定义字段06','string',NULL,15,1,56),('402880f02304355e012307c30c960094','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional7','自定义字段07','string',NULL,15,1,57),('402880f02304355e012307c30c960095','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional8','自定义字段08','string',NULL,15,1,58),('402880f02304355e012307c30c960096','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional9','自定义字段09','string',NULL,15,1,59),('402880f02304355e012307c30c960097','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional10','自定义字段10','string',NULL,15,1,60),('402880f02304355e012307c30c960098','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional11','自定义字段11','string',NULL,15,1,61),('402880f02304355e012307c30c960099','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional12','自定义字段12','string',NULL,15,1,62),('402880f02304355e012307c30c96009a','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional13','自定义字段13','string',NULL,15,1,63),('402880f02304355e012307c30c96009b','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional14','自定义字段14','string',NULL,15,1,64),('402880f02304355e012307c30c96009c','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional15','自定义字段15','string',NULL,15,1,65),('402880f02304355e012307c30c96009d','53979184-f70f-4360-b548-635dde0fc7ec','empAdditional16','自定义字段16','string',NULL,15,1,66),('402880f02304355e012307fab67700a8','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('402880f02304355e012307fab67700a9','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empGender','性别','empGender',NULL,6,1,4),('402880f02304355e012307fab67700aa','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empMarital','婚否','empMarital',NULL,6,1,5),('402880f02304355e012307fee18b00b0','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('402880f02304355e012307fee18b00b1','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('402880f02304355e012307fee18b00b2','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('402880f02304355e012307fee18b00b3','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('402880f02304355e012307fee18b00b4','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmptype.emptypeDesc','用工形式描述','string',NULL,20,1,21),('402880f02304355e012308017a2700bc','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espPbNo.pbName','职位','string',NULL,10,1,18),('402880f02304355e012308017a3700bd','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espPbNo.pbDesc','职位描述','string',NULL,10,0,19),('402880f02304355e01230805d5d100c0','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('402880f02304355e01230805d5d100c1','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('402880f02304355e01230805d5d100c2','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('402880f02304355e01230805d5d100c3','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('402880f02304355e01230805d5d100c4','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empIdentificationNo','证件号码','string',NULL,20,0,26),('402880f02304355e01230805d5d100c5','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empBlood','血型','string',NULL,6,1,27),('402880f02304355e01230805d5d100c6','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empPolitics','政治面貌','string',NULL,10,1,28),('402880f02304355e01230805d5d100c7','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empDiploma','学历','string',NULL,10,1,29),('402880f02304355e01230805d5d100c8','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empSchool','毕业院校','string',NULL,20,0,30),('402880f02304355e01230805d5d100c9','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empSpeciality','专业','string',NULL,20,1,31),('402880f02304355e0123081ac1d500d4','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empCityNo','籍贯','string',NULL,10,1,32),('402880f02304355e0123081ac1d500d5','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empNation','民族','string',NULL,6,1,33),('402880f02304355e0123081ac1d500d6','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empResidenceLoc','户口所在地','string',NULL,26,0,34),('402880f02304355e0123081ac1d500d7','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empProfileLoc','档案所在地','string',NULL,26,0,35),('402880f02304355e0123081ac1d500d8','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('402880f02304355e0123081ac1d500d9','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empEmail','邮箱','string',NULL,30,0,37),('402880f02304355e0123081ac1d500da','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empMsn','MSN/QQ','string',NULL,30,0,38),('402880f02304355e0123081ac1d500db','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empWorkPhone','工作电话','string',NULL,16,0,39),('402880f02304355e0123081ac1d500dc','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empMobile','手机','string',NULL,16,0,40),('402880f02304355e0123081ac1d500dd','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empHomePhone','家庭电话','string',NULL,16,0,41),('402880f0230992e70123099b318b0001','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empCurrAddr','当前住址','string',NULL,30,0,42),('402880f0230992e70123099b318b0002','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empCurrZip','当前住址邮编','string',NULL,10,0,43),('402880f0230992e70123099b318b0003','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empHomeAddr','家庭地址','string',NULL,30,0,44),('402880f0230992e70123099b318b0004','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('402880f0230992e70123099b318b0005','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empUrgentContact','紧急联系人','string',NULL,10,0,46),('402880f0230992e70123099b318b0006','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('402880f0230992e70123099b319b0007','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empShiftType','考勤方式','empShiftType',NULL,12,1,48),('402880f0230992e70123099b319b0008','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empShiftNo','考勤卡号','string',NULL,12,0,49),('402880f0230992e70123099b319b0009','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empStatus','状态','empStatus',NULL,10,1,50),('402880f0230992e70123099b319b000a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empComments','备注','string',NULL,20,0,51),('402880f0230992e70123099d0d410015','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional1','自定义字段01','string',NULL,15,1,52),('402880f0230992e70123099d0d410016','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional2','自定义字段02','string',NULL,15,1,53),('402880f0230992e70123099d0d410017','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional3','自定义字段03','string',NULL,15,1,54),('402880f0230992e70123099d0d410018','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional4','自定义字段04','string',NULL,15,1,55),('402880f0230992e70123099d0d410019','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional5','自定义字段05','string',NULL,15,1,56),('402880f0230992e70123099d0d41001a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional6','自定义字段06','string',NULL,15,1,57),('402880f0230992e70123099d0d41001b','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional7','自定义字段07','string',NULL,15,1,58),('402880f0230992e70123099d0d41001c','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional8','自定义字段08','string',NULL,15,1,59),('402880f0230992e70123099d0d41001d','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional9','自定义字段09','string',NULL,15,1,60),('402880f0230992e70123099d0d41001e','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional10','自定义字段10','string',NULL,15,1,61),('402880f0230992e70123099dda740029','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional11','自定义字段11','string',NULL,15,1,62),('402880f0230992e70123099dda74002a','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional12','自定义字段12','string',NULL,15,1,63),('402880f0230992e70123099dda74002b','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional13','自定义字段13','string',NULL,15,1,64),('402880f0230992e70123099dda74002c','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional14','自定义字段14','string',NULL,15,1,65),('402880f0230992e70123099dda74002d','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional15','自定义字段15','string',NULL,15,1,66),('402880f0230992e70123099dda74002e','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.EmpAdditional16','自定义字段16','string',NULL,15,1,67),('402880f0230992e7012309a9b6e40035','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espJobgrade.jobGradeNo','薪资级别','string',NULL,12,1,68),('402880f0230992e7012309a9b6e40036','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espJobgrade.jobGradeLevel','薪资等级','string',NULL,12,1,69),('402880f0230992e7012309a9b6e40037','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espBankAccountNo','银行帐号','string',NULL,20,0,71),('402880f0230992e7012309a9b6e40038','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espBankName','银行开户行','string',NULL,20,1,72),('402880f0230992e7012309a9b6e40039','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEsavId.esavEsac.esacDesc','帐套描述','string',NULL,20,1,75),('402880f02495539a01249556a8f80001','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiEsav.esavEsac.esacDesc','帐套描述','string',NULL,30,0,1),('402880f0249586da01249587cf7d0001','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.englishName','英文名','string',NULL,20,0,2),('402880f0249586da0124958c7ce10003','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empBranch.departmentName','分公司','string',NULL,16,1,3),('402880f0249586da0124958c7ce10004','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empBranch.departmentDesc','分公司描述','string',NULL,20,1,4),('402880f024959e30012495a9e15a0001','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espBranch.DepartmentName','分公司','string',NULL,16,1,6),('402880f024959e30012495abef990004','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espBranch.DepartmentDesc','分公司描述','string',NULL,20,1,7),('402880f024a13a830124a14c7c4d0049','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmYearmonth','年月','string',NULL,10,0,0),('402880f024a13a830124a14c7c4d004a','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmDutyDays','全勤(天)','decimal','2',12,0,20),('402880f024a13a830124a14c7c4d004b','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveDays','请假(天)','decimal','2',12,0,30),('402880f024a13a830124a14c7c4d004c','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveAnnualDays','年假(天)','decimal','2',12,0,32),('402880f024a13a830124a14c7c4d004d','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveCasualDays','事假(天)','decimal','2',12,0,34),('402880f024a13a830124a14c7c4d004e','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveSickDays','病假(天)','decimal','2',12,0,36),('402880f024a13a830124a14c7c4d004f','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveSick01Days','病假住院(天)','decimal','2',12,0,38),('402880f024a13a830124a14c7c4d0050','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveWeddingDays','婚假(天)','decimal','2',12,0,40),('402880f024a13a830124a14c7c4d0051','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveMaternityDays','产假(天)','decimal','2',12,0,42),('402880f024a13a830124a14c7c4d0052','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTravelDays','出差(天)','decimal','2',12,0,44),('402880f024a13a830124a14f53d7005d','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveOuterDays','因公外出(天)','decimal','2',12,0,46),('402880f024a13a830124a14f53d7005e','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTiaoxiuDays','调休(天)','decimal','2',12,0,48),('402880f024a13a830124a14f53d7005f','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveOtherDays','其他请假(天)','decimal','2',12,0,52),('402880f024a13a830124a15c87aa00b5','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmBranch.DepartmentName','分公司','string',NULL,16,1,3),('402880f024a13a830124a15c87aa00b6','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmBranch.DepartmentDesc','分公司描述','string',NULL,20,1,4),('402880f024a13a830124a15c87aa00b7','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmDept.departmentName','部门','string',NULL,16,1,5),('402880f024a13a830124a15c87aa00b8','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmDept.departmentDesc','部门描述','string',NULL,20,1,6),('402880f024a13a830124a15c87aa00b9','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,7),('402880f024a13a830124a15c87aa00ba','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,8),('402880f024a13a830124a15c87aa00bb','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,9),('402880f024a13a830124a15c87aa00bc','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,10),('402880f024a13a830124a15c87aa00bd','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,11),('402880f024a13a830124a15c87aa00be','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,12),('402880f024a13a830124a15e233900d3','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmPbNo.pbName','职位','string',NULL,10,1,15),('402880f024a13a830124a15e233900d4','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmPbNo.pbDesc','职位描述','string',NULL,10,0,16),('402880f024a13a830124a15e233900d5','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmptype.emptypeName','用工形式','string',NULL,12,1,17),('402880f024a13a830124a15e233900d6','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmptype.emptypeDesc','用工形式描述','string',NULL,20,1,18),('402880f024a1d34b0124a1d62a460001','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLocation.locationName','工作地区','string',NULL,12,1,13),('402880f024a1d34b0124a1d62a460002','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLocation.locationDesc','工作地区描述','string',NULL,20,1,14),('402881e524aae9260124aaf0d6830001','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTiaoxiu01Hours','调休过期(小时)','decimal','2',12,0,49),('402881e524aae9260124aaf0d6a20002','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTiaoxiu01Days','调休过期(天)','decimal','2',12,0,50),('403b3549-b28f-458a-a956-d1a65e73b9ef','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empType.emptypeName','用工形式','string','',12,1,20),('40d805e5-bfc5-4586-9e4b-24e214086d84','68ee8cfa-4788-4290-b265-f6f4c14e4889','empSchool','毕业院校','string',NULL,20,0,30),('41de0552-6cd2-4b1c-b773-b1690353e946','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espJobgrade.jobGradeName','薪资级别描述','string',NULL,20,1,70),('425e22e6-a0d4-4783-9a2e-b7c1b2c261e1','5d46f106-6099-478c-8911-9c684222cf4c','employee.empUrgentConMethod','紧急联系方式','string',NULL,20,1,47),('426e9415-a8e7-4c0a-b64a-2baa2c91459a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empWorkPhone','工作电话','string','',16,0,39),('428f4a57-19d9-44a0-93e6-70505acc0336','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.etcExpire','合同期限','etcExpire','',10,1,71),('42a4c543-52f6-4974-b709-3f664e411ef2','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empSchool','毕业院校','string',NULL,20,0,30),('43489da7-1931-4964-a0c0-6f0ac7fa8e18','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional1','自定义字段01','string',NULL,15,1,52),('4376b386-1d04-44fe-9f03-8f1932def859','cca1a5ac-6140-421c-9eea-848c615b8306','esaiDataSeqStr','项目编号','string',NULL,10,0,0),('4394128b-0016-4384-9d48-9f6895cefd6e','5d46f106-6099-478c-8911-9c684222cf4c','employee.empWorkPhone','工作电话','string',NULL,16,0,39),('4434321d-4845-45d8-9d3b-f1c2cb880fda','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional1','自定义字段01','string',NULL,15,1,52),('4453efca-2f11-41b9-8b20-8388368c7b57','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewPbId.pbName','现职位','string',NULL,10,1,18),('44bf80af-9296-40fe-a575-6763fa2debc5','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldEmpType.emptypeName','原用工形式','string',NULL,20,1,76),('44c26f79-ddce-4d3e-94d3-2b8c128642ed','cca1a5ac-6140-421c-9eea-848c615b8306','esaiDataRounding','尾数舍入','esaiDataRounding',NULL,10,0,5),('45665f8c-3e32-4695-ae2d-1ba8456dca28','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empMobile','手机','string',NULL,16,0,40),('456a5823-484a-4af3-b468-336c4d02c014','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional5','自定义字段05','string',NULL,15,1,56),('46247f64-e993-49e3-9b6f-3418e4e7157f','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empIdentificationNo','证件号码','string',NULL,20,0,26),('46bfde06-126e-4552-a305-81edafdc5068','5d46f106-6099-478c-8911-9c684222cf4c','employee.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('4709bcd9-bb4e-46a3-8c61-4f3c6a6b58f9','efa905bc-c55a-4678-b21b-2ea94067f178','erExeDate','奖惩日期','date','yyyy-MM-dd',12,0,68),('4776d73c-9196-4eda-ad53-4365233bf552','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empName','姓名','string',NULL,10,0,1),('47ac068c-121c-47f7-946d-be8a81cb813b','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional15','自定义字段15','string',NULL,15,1,66),('47be25a3-fa39-44ad-8549-f041a1b169de','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional15','自定义字段15','string',NULL,15,1,66),('482798c9-0e0c-4e77-9644-eaa1b8f049eb','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,9),('483698ee-7479-4f6b-bdf3-aa2cf4944d6d','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional8','自定义字段08','string','',15,1,59),('48503b18-1c11-415c-889d-477c64a1063e','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empMobile','手机','string',NULL,16,0,40),('48b83abc-5780-4224-86f2-0be97d1e577d','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.contractType.ectName','合同种类','string','',12,1,69),('49441249-ea51-424e-ab2a-ec08bb76247f','5d46f106-6099-478c-8911-9c684222cf4c','employee.empHomeAddr','家庭地址','string',NULL,30,0,44),('497febba-dcf7-4f78-af11-986589e7b8a2','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','eeEndDate','结束日期','date','yyyy-MM-dd',12,0,69),('49e70f0d-581e-42bf-9f8c-813908146162','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.englishName','英文名','string',NULL,20,0,2),('4a7929d9-3951-4168-9f16-eb3d9e19c8a1','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('4a9c4239-c37b-42fc-8d87-96a69aa0ea5c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('4ae20362-b733-4c62-a4ba-48a3a2337cc6','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empMsn','MSN/QQ','string',NULL,30,0,38),('4affd66f-2315-4b5c-b32e-2b46d9859cdc','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn16','公司代缴社保总额','decimal',NULL,12,0,77),('4b2588c9-f145-4c45-9075-b643b4757170','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empShiftType','考勤方式','empShiftType',NULL,12,1,48),('4b534d4d-5386-45fe-b64b-8001f83c5d6c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('4b81740a-fb5f-41e3-9938-ba47c34c234a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.ectNo','合同编号','string','',12,0,70),('4bc5077b-5607-4a9c-8d62-aea235cb9e30','801f19ca-c8a7-4550-be9c-8ab0950631a3','empTerminateDate','离职日期','date','yyyy-MM-dd',12,0,68),('4bdf0e2d-33fa-48e7-b19a-987c99698927','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional11','自定义字段11','string',NULL,15,1,62),('4be1e261-bb39-4e41-a582-8d9754870be5','68ee8cfa-4788-4290-b265-f6f4c14e4889','empPbNo.pbName','职位','string',NULL,10,1,18),('4becf4af-dbde-4210-98c8-b7fe3cc09968','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empUrgentContact','紧急联系人','string','',10,0,46),('4da11738-3ea8-405c-8906-7f12c08c6802','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empBlood','血型','string',NULL,6,1,27),('4f63b7ca-d2e1-4c71-9237-485463a017ef','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional1','自定义字段01','string',NULL,15,1,52),('4fe58b5e-e3c5-4c3c-955e-4bf9d548efc1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empBlood','血型','string',NULL,6,1,27),('512aea5b-3a00-4610-9f02-213397b16ac6','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional4','自定义字段04','string',NULL,15,1,55),('51d2bcf6-7e82-4889-982e-0ed2b88c9185','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empIdentificationNo','证件号码','string',NULL,20,0,26),('52caf51e-3089-4a0b-8834-4d392cf2b33a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional10','自定义字段10','string','',15,1,61),('53204224-67eb-4eb5-a9b4-102cfe504f38','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo3.DepartmentName','三级部门','string','',16,1,14),('5339e6ab-929a-4ee3-99f5-219494f08961','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('533fa122-eff9-4ca6-9b33-d2f9544a00fa','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfMedicalNo','医疗保险号','string',NULL,20,0,81),('53ee1b57-c026-433f-b97d-13dc0053ebb7','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empShiftType','考勤方式','empShiftType','',12,1,48),('53ef0b49-2878-4978-bf32-c4e0a9701562','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional2','自定义字段02','string',NULL,15,1,53),('5423b641-f380-479b-aba2-f14a2afcdae5','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmAbsentHours','旷工(小时)','decimal','2',12,0,27),('542c5235-a99c-4b1e-8f69-ce1c939582c4','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('54437d43-3a2f-41ad-91d0-e43201eed38c','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('548fd557-277b-47f4-a8b7-13f39d4ccb3c','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empCityNo','籍贯','string',NULL,10,1,32),('54d436f2-3f9e-4e0c-aca1-26c7f2bdd3b0','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiEsdd.esddDataType','项目种类','esddDataType',NULL,12,0,3),('54f3ade0-d706-4e4c-a551-fe59700c07ae','53979184-f70f-4360-b548-635dde0fc7ec','empDistinctNo','工号','string',NULL,10,0,0),('55174562-d377-4796-828f-05fcac1ccf62','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo2.departmentName','原二级部门','string',NULL,12,1,73),('55625be2-6e4a-466d-a172-08aefd085c27','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDiploma','学历','string',NULL,10,1,29),('55a53480-6a7b-46c7-8b78-098cebc750eb','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('5657b310-8fc4-4fe8-ae87-29042c6d5458','5d46f106-6099-478c-8911-9c684222cf4c','employee.empCurrAddr','当前住址','string',NULL,30,0,42),('56d66565-377d-4a35-9c31-a46037a9f6e7','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('56e57b50-9c9c-4f46-a532-add5e83b7b3d','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional14','自定义字段14','string',NULL,15,1,65),('5762ec72-3fa8-4211-9e8e-783d994b9911','801f19ca-c8a7-4550-be9c-8ab0950631a3','empMarital','婚否','empMarital','',6,1,5),('58ff879e-f945-4610-940f-3c58a75ab809','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional12','自定义字段12','string',NULL,15,1,63),('596e3832-a858-4b4b-ba83-a772c1424667','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,7),('5a043de5-1151-411c-9284-dbc00b717781','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional13','自定义字段13','string',NULL,15,1,64),('5a8e0444-2a29-4e04-b41a-f6d81ae3f984','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empResidenceLoc','户口所在地','string',NULL,26,0,34),('5a9361bd-264e-4943-a431-53bc4913f819','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo2.DepartmentName','二级部门','string','',12,1,12),('5ac8f730-888c-4699-b711-f2c36601b43c','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo3.departmentNameDesc','原三级部门描述','string',NULL,20,1,75),('5ae59cd7-3ee5-4afe-91d0-594a029e85d7','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empMobile','手机','string',NULL,16,0,40),('5b23b237-5fd8-47fe-b761-1b996de50d1e','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empCurrZip','当前住址邮编','string',NULL,10,0,43),('5cb498d9-6c1e-441d-b151-770875f0c9bb','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empLocationNo.locationName','工作地区','string',NULL,12,1,13),('5ce94398-aaba-4b0d-b8a9-9c75bee0fd0f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional3','自定义字段03','string','',15,1,54),('5cec577c-7481-4fa2-a1ae-852aedfe4b35','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo.departmentName','现部门','string',NULL,16,1,8),('5cf6c82d-dbb8-4cbb-9092-9600ff56ece4','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empHomeAddr','家庭地址','string',NULL,30,0,44),('5d7c991d-9237-47b3-861c-879bb3ba712e','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('5e77da97-6ca9-45a3-9b53-ed039c11c64d','68ee8cfa-4788-4290-b265-f6f4c14e4889','empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('5f13b67f-9a2b-4d63-861b-303b478adbee','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo.departmentName','部门','string',NULL,16,1,8),('5f7b2fd2-67a8-4eca-8e95-88dd3ae137a9','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('60fe5d52-dde3-45af-a1f5-bb86c0cb9e51','68ee8cfa-4788-4290-b265-f6f4c14e4889','empShiftType','考勤方式','empShiftType',NULL,12,1,48),('61107dbe-7baf-46f5-9aac-7e8fe83b0c03','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empUrgentContact','紧急联系人','string',NULL,10,0,46),('620fee4e-ec45-4cde-9203-b2222b93646e','801f19ca-c8a7-4550-be9c-8ab0950631a3','empIdentificationType','证件种类','empIdentificationType','',10,0,25),('626fb3c5-fec8-4d76-94f5-0ff3dcf01e2f','efa905bc-c55a-4678-b21b-2ea94067f178','erJobTitle.jobtitleNameDesc','执行岗位描述','string',NULL,20,1,75),('62a6b61e-ab87-403d-bd91-812f07c66749','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEsavId.esavEsac.esacName','帐套名称','string',NULL,20,1,74),('62c23858-d1e7-4cd6-a863-f9ea56a36636','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional7','自定义字段07','string',NULL,15,1,58),('6320f072-34b3-4b8e-97c9-d10bcd5ff063','68ee8cfa-4788-4290-b265-f6f4c14e4889','empUrgentContact','紧急联系人','string',NULL,10,0,46),('63285764-5432-4f2b-b1d4-bd768ce1ffd5','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empGender','性别','empGender',NULL,6,1,4),('63acc690-ff93-4325-9f64-b6aea03ea039','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional16','自定义字段16','string',NULL,15,1,67),('63ced644-c668-4a0f-96cb-a7cb44710408','68ee8cfa-4788-4290-b265-f6f4c14e4889','empType.emptypeName','用工形式','string',NULL,12,1,20),('64cee071-ab60-4fb1-ad48-463c6c67926a','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional16','自定义字段16','string',NULL,15,1,67),('653baa29-5ad3-4db0-9319-81323d204f76','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empDiploma','学历','string',NULL,10,1,29),('661b3c23-c64f-49c9-82b1-756af27e506f','68ee8cfa-4788-4290-b265-f6f4c14e4889','empNation','民族','string',NULL,6,1,33),('66352651-fbda-4b3c-a602-4b7439cdc7ce','5d46f106-6099-478c-8911-9c684222cf4c','ectComments','合同备注','string',NULL,26,0,75),('663edc97-d0a7-4d49-8bbc-ac1e8bae1fb2','53979184-f70f-4360-b548-635dde0fc7ec','empName','姓名','string',NULL,10,0,1),('66418a43-59d5-4470-ba72-c5bcdde00920','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldEmpType.emptypeNameDesc','原用工形式描述','string',NULL,20,1,77),('665e0d4a-6d38-495b-bd3d-13f7236f731d','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn14','其他福利项','decimal',NULL,12,0,75),('6723bcee-6c1b-43d0-9483-c8c1c6a144e4','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional9','自定义字段09','string',NULL,15,1,60),('673f10bd-79d5-45a8-9984-4aa0ff0d6804','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn11','公司代缴社保','decimal',NULL,12,0,72),('6763a274-e43c-4257-a104-1161bb65bd7b','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('6766d552-c1cc-4589-bec2-9be6a5fd95e9','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDiploma','学历','string',NULL,10,1,29),('6770f92d-4367-47f5-9c27-e680f27e3dce','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveAnnualHours','年假(小时)','decimal','2',12,0,31),('67949407-c342-49e7-bb02-26360cddff63','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTravelHours','出差(小时)','decimal','2',12,0,43),('68fac9a9-f0e2-4da9-ac8a-77ae90c8014b','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('691880a6-bf48-43db-b0d1-b67a151db983','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empBranch.departmentName','分公司','string',NULL,16,1,6),('693491fa-bc0b-4df0-9187-8af88add72e8','68ee8cfa-4788-4290-b265-f6f4c14e4889','englishName','英文名','string',NULL,20,0,2),('696b0e3b-bdcf-4094-8105-b02df7dbb2e5','5d46f106-6099-478c-8911-9c684222cf4c','employee.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('69f3cf82-a924-42e6-868f-7a3e12b995ce','f0f570ec-4057-4ba8-ad13-a1f542828cbe','EspComment','备注','string',NULL,20,0,76),('69fb3607-b35a-4492-a460-294d54a893c0','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empSpeciality','专业','string','',20,1,31),('6a56a70c-4bc6-4299-96cc-1b7a9a508e15','5d46f106-6099-478c-8911-9c684222cf4c','employee.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('6ac3fddb-ba07-40a6-9634-80b7c631094f','5d46f106-6099-478c-8911-9c684222cf4c','employee.empName','姓名','string',NULL,10,0,1),('6acc88dd-b708-405d-bf47-673d0b987195','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional4','自定义字段04','string',NULL,15,1,55),('6b4132b2-55a4-4736-8272-8ebe254f6053','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDistinctNo','工号','string',NULL,10,0,0),('6bb6d076-0b24-4424-bd83-1b420ccbc090','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional14','自定义字段14','string',NULL,15,1,65),('6c49aa8c-d371-4e34-8d26-c499e51985b7','801f19ca-c8a7-4550-be9c-8ab0950631a3','empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('6c5cf3f6-8f76-494e-8394-f6663f3bcfaa','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('6c8f6a54-7cc8-40ba-866e-80a43b0772f0','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('6dee72b0-df7a-47ab-b35a-05743d72b45b','801f19ca-c8a7-4550-be9c-8ab0950631a3','empCityNo','籍贯','string','',10,1,32),('6e466dd5-f345-4362-b4a1-9aa489bea48a','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empComments','备注','string',NULL,20,0,51),('6e8dbb76-0a53-4d18-8830-e374d7653200','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOtHolidayHours','节假日加班(小时)','decimal','2',12,0,56),('6e98144b-8ed8-4942-b04a-93682d7ed6c2','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empSchool','毕业院校','string','',20,0,30),('6eae7049-7d3e-4bce-bcd1-8bc455f07777','5d46f106-6099-478c-8911-9c684222cf4c','employee.empBlood','血型','string',NULL,6,1,27),('6eb4dbb9-57eb-4eeb-ad15-93ba159b4ca7','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional10','自定义字段10','string',NULL,15,1,61),('6ed00967-8588-41d9-8e94-c1067b95fdad','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empShiftType','考勤方式','empShiftType',NULL,12,0,48),('6ef0c8d3-ef3f-4390-901c-35b41b3b24db','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDistinctNo','工号','string','',10,0,0),('6ef1cfd5-085f-4c3b-96cb-bbe64f8fb5e2','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empProfileLoc','档案所在地','string',NULL,26,0,35),('6ef835ec-3478-4ec8-bf55-53c020b814a9','801f19ca-c8a7-4550-be9c-8ab0950631a3','empLocationNo.locationDesc','工作地区描述','string','',20,1,17),('6f21be6a-6a8c-45d4-8edf-70d96b539e2b','5d46f106-6099-478c-8911-9c684222cf4c','employee.empIdentificationNo','证件号码','string',NULL,20,0,26),('6f52675a-d4c2-4c97-9181-5525bf11d3ef','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empGender','性别','empGender',NULL,6,1,4),('6f5b349b-9bc6-4c53-b95b-338073cf6abf','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional11','自定义字段11','string',NULL,15,1,62),('7031a24f-25bd-4cd8-81f6-1311f6a4e212','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional4','自定义字段04','string',NULL,15,1,55),('7067c602-43de-4a84-945c-3bf544af1b27','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo3.departmentName','原三级部门','string',NULL,20,1,74),('70aae49b-54e5-42da-8d57-a514cef4eadb','653d4849-2f36-4261-bce9-a99eb6c15073','eqPermission.empDistinctNo','离职审批人工号','string',NULL,10,1,70),('70ef9324-a2b0-4165-b6a8-81f12fba8872','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional12','自定义字段12','string',NULL,15,1,63),('7163da50-8cb4-4175-b220-30681b45ce66','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo3.DepartmentName','现三级部门','string',NULL,16,1,14),('71852d09-a6ca-4838-9d69-8ce9fba04a4a','801f19ca-c8a7-4550-be9c-8ab0950631a3','quit.eqPermission.empName','离职审批人','string','',10,0,70),('7201920f-dd98-4273-92e4-599beb1a08ea','801f19ca-c8a7-4550-be9c-8ab0950631a3','empProfileLoc','档案所在地','string','',26,0,35),('723936ad-b7f9-43bc-ac75-6141593ed874','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empHomeAddr','家庭地址','string','',30,0,44),('7259f95e-abe4-4b03-a368-42f99e2a8dd2','5d46f106-6099-478c-8911-9c684222cf4c','employee.empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,36),('72b8f8f2-48f7-4eb9-aae3-e46dab721fa3','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empName','姓名','string',NULL,10,0,1),('72ea1da4-5332-49ab-bd0b-ea2142c6b666','801f19ca-c8a7-4550-be9c-8ab0950631a3','empBranch.departmentName','分公司','string','',16,1,6),('737417fc-11b1-49ab-a623-081c3865e74b','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('738239c7-00df-4944-921a-d31f06e933e6','68ee8cfa-4788-4290-b265-f6f4c14e4889','empWorkPhone','工作电话','string',NULL,16,0,39),('73ff27af-31ae-49b0-9796-be70157d4eb8','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional8','自定义字段08','string',NULL,15,1,59),('7404839b-8f00-47d9-bd78-3632f9652f00','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDiploma','学历','string','',10,0,29),('7463df78-7a41-4a8c-b96b-6a8dc15491f1','032ee263-9f5b-46b8-bede-6491a8fa7730','shiftName','考勤班次','string',NULL,10,0,26),('751634b9-ac30-4bc9-92eb-3fb660ee5cb0','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('758e84a6-60b4-48db-b71d-b0dd37613540','801f19ca-c8a7-4550-be9c-8ab0950631a3','empShiftNo','考勤卡号','string','',12,0,49),('75e53854-a831-4168-956d-12fff393de06','5d46f106-6099-478c-8911-9c684222cf4c','ectStatus','合同状态','ectStatus',NULL,12,0,72),('7775ed9d-5971-41e0-9cef-1eabb8eb4e0d','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empMobile','手机','string',NULL,16,0,40),('77abdc66-e8e2-4061-ae43-d4c19739b03c','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empCurrAddr','当前住址','string',NULL,30,0,42),('77e20d0b-f5a7-484c-869d-064cb152926c','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo.departmentDesc','原部门描述','string',NULL,20,1,72),('786c82f4-ec15-40a8-923a-c19fc32082f1','801f19ca-c8a7-4550-be9c-8ab0950631a3','empUrgentConMethod','紧急联系方式','string','',20,0,47),('788eda69-a027-4fba-b9d2-771e260e7b77','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','eeStartDate','起始日期','date','yyyy-MM-dd',12,0,68),('78a3158c-e8a7-438c-8800-bf6f8b1b5e44','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empWorkPhone','工作电话','string','',16,0,39),('78efa491-f15d-440c-ac5a-400ada0cdd11','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('793b3624-155f-49ab-9317-b00aa1fd7605','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('797082ad-1147-48e5-a256-58e65db263e0','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empBlood','血型','string',NULL,6,1,27),('79d780bc-4d47-4078-bdcd-2b9fae1b52f5','5d46f106-6099-478c-8911-9c684222cf4c','employee.empPbNo.pbName','职位','string',NULL,10,1,18),('7a99b12a-afc4-4154-a4c7-29016bf9a53f','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftReason','变动原因','string',NULL,20,0,78),('7a9fee2b-f923-4ba2-bd85-dc0ce87a4cee','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiDataSeqStr','项目编号','string',NULL,10,0,2),('7b7ed767-c592-4ea9-81e9-f620cf4046ce','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional5','自定义字段05','string',NULL,15,1,56),('7b82488b-0a80-40cf-92f9-425b68cfb4b8','68ee8cfa-4788-4290-b265-f6f4c14e4889','empHomeAddr','家庭地址','string',NULL,30,0,44),('7b825afc-9138-47e1-a492-6a249613ac81','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empGender','性别','empGender',NULL,6,1,4),('7be81a56-19e6-44be-9bc6-36d0a7535376','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional13','自定义字段13','string',NULL,15,1,64),('7bfb1adb-6da3-4496-b044-c9e563bc091c','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empIdentificationType','证件种类','empIdentificationType','',10,0,25),('7c1194f4-b65b-4e98-ac68-b5f37f6eedad','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional7','自定义字段07','string',NULL,15,1,58),('7c9b165e-654b-4319-a7f7-785f5db4783c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empProfileLoc','档案所在地','string',NULL,26,0,35),('7cc39071-96c0-454f-804f-987aa48e65f7','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional6','自定义字段06','string',NULL,15,1,57),('7d0050a8-6052-43c1-8441-2fa64dfc91a3','53979184-f70f-4360-b548-635dde0fc7ec','empPbNo.pbDesc','职位描述','string',NULL,10,0,19),('7d3cadaf-a843-48ce-8f03-4aa62724df75','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewBranch.departmentName','现分公司','string',NULL,16,1,6),('7d4aaec5-b24f-4abf-ab88-bea531811f70','5d46f106-6099-478c-8911-9c684222cf4c','contractType.ectDescription','合同种类描述','string',NULL,20,1,71),('7d658224-e91f-4690-90e3-23966e80bcc0','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empCityNo','籍贯','string',NULL,10,1,32),('7dd9ecc0-32b6-40cc-8658-400f1fc159a2','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional9','自定义字段09','string',NULL,15,1,60),('7deb8f15-144e-4035-b132-36b5bed9efe1','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional13','自定义字段13','string',NULL,15,1,64),('7e005000-9823-4356-b841-7a79c8034cfe','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empNation','民族','string',NULL,6,1,33),('7f850bd6-893b-46fa-bc12-e4f4e25abf53','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empHomePhone','家庭电话','string','',16,0,41),('7fd2d201-95eb-4b1d-8368-b9cf2408202d','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empCurrAddr','当前住址','string',NULL,30,0,42),('7fe71b14-6738-447f-89cf-e11fe8b1d61f','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo.departmentName','部门','string',NULL,16,1,8),('8001dc94-796d-4736-b080-6c20ea675620','801f19ca-c8a7-4550-be9c-8ab0950631a3','empHomeAddr','家庭地址','string','',30,0,44),('8002d3a8-14ac-4a48-b1f8-21a188983114','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional4','自定义字段04','string',NULL,15,1,55),('8060816d-a625-4889-a476-fbdbaa065f86','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('80656b0e-2e4b-45da-928f-f85b0a81afb0','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empWorkPhone','工作电话','string',NULL,16,0,39),('80fc67fa-2f49-486a-9eaa-700fc20af810','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empStatus','状态','empStatus',NULL,10,1,50),('811c5298-1181-4cb5-8761-2c07aaa4c2c6','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('81312f3e-e399-4f5f-b9dc-f3f8778ea02e','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo2.DepartmentName','现二级部门','string',NULL,12,1,12),('819de58c-d079-4eae-b663-4feb949bc0de','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('81bf5be9-e955-4ae4-acd8-63ec76adefbc','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empName','姓名','string',NULL,10,0,1),('8289aa85-4ae9-4fca-b0b5-181f3e2d859b','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo1.DepartmentName','一级部门','string','',12,1,10),('8314fb80-ff8a-4b36-97c2-811835a104d6','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional7','自定义字段07','string',NULL,15,1,58),('838b4dac-7be2-49be-b0d0-480a7c2ad49d','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empIdentificationNo','证件号码','string',NULL,20,0,26),('839fbf2c-3db0-446b-bbf7-bc5c1f0bf751','68ee8cfa-4788-4290-b265-f6f4c14e4889','empMobile','手机','string',NULL,16,0,40),('83f80480-3f21-4eec-ba27-16d0407929a2','68ee8cfa-4788-4290-b265-f6f4c14e4889','empComments','备注','string',NULL,20,0,51),('84079380-e5af-426e-86ce-1bdfaf1ed6ff','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('8429b061-ac1f-41bd-b61e-649c2620f72e','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional15','自定义字段15','string','',15,1,66),('848dde83-4b7d-41a8-8ca6-685d8784f6d2','68ee8cfa-4788-4290-b265-f6f4c14e4889','empBlood','血型','string',NULL,6,1,27),('849e9f9d-7793-49e0-926f-4dc9f4d3c582','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empResidenceLoc','户口所在地','string','',26,0,34),('85514c1b-5b56-4b1d-9ee2-708dd9399ae4','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empCurrZip','当前住址邮编','string',NULL,10,0,43),('85935d5e-3394-4bf7-814c-540c9f47b429','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('85e4c9cb-7816-46f1-af8b-4ac5c0abf9c7','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empLocationNo.locationDesc','工作地区描述','string','',20,1,17),('861c91a5-ecd3-42d2-bc5a-affbcd40308d','cca1a5ac-6140-421c-9eea-848c615b8306','esaiEsdd.esddName','项目名称','string',NULL,15,0,2),('86317746-a0f1-41ed-858d-caddb66d7d9c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional16','自定义字段16','string',NULL,15,1,67),('8651132a-0ae4-4b4e-9db1-16561ebb7d3c','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empMsn','MSN/QQ','string',NULL,30,0,38),('86891510-94b6-4b77-9fdf-08c6249b57e3','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional2','自定义字段02','string',NULL,15,1,53),('87baabf4-0a03-4f81-9e93-0aecd81dfddc','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empMarital','婚否','empMarital',NULL,6,1,5),('884bbb7d-6eb3-4d94-95b5-b99acc144b75','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional2','自定义字段02','string',NULL,15,1,53),('884e46cd-085e-440f-b540-65a55d4ad9b3','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional11','自定义字段11','string',NULL,15,1,62),('88577c5a-a705-4d6f-b3f3-906b640bfea2','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.ectEndDate','合同结束日期','date','yyyy-MM-dd',12,1,73),('88d2ba83-8339-4f37-ba83-01617e64878e','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empPolitics','政治面貌','string','',10,1,28),('89195781-45a8-4365-a084-0b448c35b17b','68ee8cfa-4788-4290-b265-f6f4c14e4889','empCityNo','籍贯','string',NULL,10,1,32),('89f60239-a260-4755-a215-f2113ea01920','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empComments','备注','string',NULL,20,0,51),('8a3728c1-7fba-4f68-931b-5965aa746d37','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo2.DepartmentDesc','现二级部门描述','string',NULL,20,1,13),('8a49d1f7-3862-4f04-875b-53789998564f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empBlood','血型','string','',6,1,27),('8a73334f-6e89-4d2c-b6db-c3b96ef9adcb','efa905bc-c55a-4678-b21b-2ea94067f178','erType','奖惩种类','string',NULL,12,0,69),('8aeac1d6-18f3-4afa-a884-f3ee86207cde','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empWorkPhone','工作电话','string',NULL,16,0,39),('8b461277-8176-40d0-850c-fa0e81ac3266','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveMaternityHours','产假(小时)','decimal','2',12,0,41),('8b515ccd-ea5d-402d-80a7-1134d8a51675','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveHours','请假(小时)','decimal','2',12,0,29),('8b5d62f1-53c9-4171-8bb2-2dbef09984a7','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOtNormalHours','日常加班(小时)','decimal','2',12,0,54),('8b6793e5-aa67-4d70-bc59-3757d715be95','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('8bd1f46c-6bfb-4cec-b4dd-812dbca27e12','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empShiftType','考勤方式','empShiftType',NULL,12,1,48),('8be1487e-f791-486f-8cbd-849077d37d76','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('8cf24533-b9f3-44f0-acf6-013c9c861ed5','f0f570ec-4057-4ba8-ad13-a1f542828cbe','EspCostCenter','成本中心','string',NULL,20,1,73),('8dd8fd9c-813f-45f3-9984-f5e915a63464','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empWorkPhone','工作电话','string',NULL,16,0,39),('8e6982b6-8d97-4695-8463-7ac19f522f4b','5d46f106-6099-478c-8911-9c684222cf4c','contractType.ectName','合同种类','string',NULL,20,1,70),('8e7a11d1-8a39-4da1-b335-674cd41086fe','801f19ca-c8a7-4550-be9c-8ab0950631a3','empShiftType','考勤方式','empShiftType','',12,1,48),('8e9a3588-a825-4f51-a3ed-5e50abca388d','5d46f106-6099-478c-8911-9c684222cf4c','employee.empGender','性别','empGender',NULL,6,1,4),('8eaa3bdd-fef4-43db-bf22-e810fd92f523','032ee263-9f5b-46b8-bede-6491a8fa7730','earlyMinutes','早退(分钟)','decimal',NULL,10,0,33),('8f143e11-9a49-457d-91c5-8b224d32c3b0','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empSchool','毕业院校','string',NULL,20,0,30),('8f280fdb-2eb2-4a8a-890e-415c8c3ecafa','801f19ca-c8a7-4550-be9c-8ab0950631a3','empBenefitType.benefitTypeName','社保种类','string','',12,1,36),('8f4cb6ac-07c3-4194-8660-b10ba665cdc2','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDiploma','学历','string',NULL,10,1,29),('8f9a3ab3-5c40-4a3d-bab9-329da03b2256','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empMarital','婚否','empMarital',NULL,6,1,5),('8f9adecb-279b-42f3-a78a-5d94ca676372','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional1','自定义字段01','string',NULL,15,1,52),('8fe0fbd9-eaf4-42f5-8477-1c597bc04a03','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empCurrZip','当前住址邮编','string',NULL,10,0,43),('900e35ae-7dd2-4981-a3d6-98c839eb4a70','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empStatus','状态','integer',NULL,10,0,50),('903ab48b-7935-47d8-a4bd-8e8f49247817','cca1a5ac-6140-421c-9eea-848c615b8306','esaiDataIsCalc','字段属性','esaiDataIsCalc',NULL,12,1,3),('9090c357-1e01-489a-98f2-82f6722c72b3','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional6','自定义字段06','string',NULL,15,1,57),('9167dd9d-194a-4aa6-9d5f-fd06d4ded7b1','801f19ca-c8a7-4550-be9c-8ab0950631a3','empCurrAddr','当前住址','string','',30,0,42),('917993fe-7ef5-48b1-94ec-c1cc53d8d8f8','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empComments','备注','string','',20,0,51),('91a73cc4-0929-4fe6-8274-d98b59766024','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional8','自定义字段08','string',NULL,15,1,59),('920a8c56-4b13-4d59-bc26-1475112e2ae4','68ee8cfa-4788-4290-b265-f6f4c14e4889','empEmail','邮箱','string',NULL,30,0,37),('9210faff-820c-4bfe-9de8-01176ed7e61c','032ee263-9f5b-46b8-bede-6491a8fa7730','comments','异常描述','string',NULL,20,0,39),('9283c2d0-c41d-425d-8d8a-cec9c8990f89','5d46f106-6099-478c-8911-9c684222cf4c','employee.empEmail','邮箱','string',NULL,30,0,37),('92aec4d7-1214-47d0-a103-81e1f3086f74','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('92baef1d-40cf-408e-87ad-82581208326a','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional3','自定义字段03','string',NULL,15,1,54),('932ca1cf-dc30-4da6-bf16-ec3089d44021','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional10','自定义字段10','string',NULL,15,1,61),('93bb59c3-a34c-45fe-a6b1-8781ebb22b82','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empShiftNo','考勤卡号','string',NULL,12,0,49),('94393a05-f8fc-4bac-8898-e4b8ce16fbf6','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo.departmentDesc','部门描述','string','',20,1,9),('9497f072-4288-4764-ba86-bf90e2c62353','5d46f106-6099-478c-8911-9c684222cf4c','employee.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('95713fee-4eb1-4624-9c37-1e35e6d1d901','032ee263-9f5b-46b8-bede-6491a8fa7730','onDutyTime','上班时间','date','HH:mm',10,0,27),('965f4927-c3b1-47a1-896b-b1af12139c8d','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional12','自定义字段12','string',NULL,15,1,63),('96710f76-df8c-49be-9076-7752641a14da','5d46f106-6099-478c-8911-9c684222cf4c','employee.empCurrZip','当前住址邮编','string',NULL,10,0,43),('969ed78d-e8b1-47f3-b72f-d76d15e8623b','653d4849-2f36-4261-bce9-a99eb6c15073','eqPermission.empName','离职审批人','string',NULL,10,1,71),('96cbd82c-66d9-4c21-aa6a-3a7f7329cba7','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional9','自定义字段09','string',NULL,15,1,60),('96ddde75-4d09-4da5-94ed-2f99cb237dc9','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('975926b0-c9f2-4dc2-b03c-e8ead856befb','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo3.Departmentdesc','三级部门描述','string','',20,1,15),('978f1c52-3dc8-4295-9ca9-0dca2f098bba','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfPensionAmount','社保基数','decimal',NULL,12,0,68),('979674ae-e65e-48be-a6ca-a3a786f155e5','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional6','自定义字段06','string',NULL,15,1,57),('980ba3f4-0d3d-4c1b-b079-8f272f433c4e','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('981ddf5e-5024-4afa-9731-0c6e45c0b08d','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('9862c541-be9a-4d51-ab62-901d3047f5dd','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional9','自定义字段09','string',NULL,15,1,60),('990c160e-c0ce-48d7-9a07-52563b8cbe20','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('9aad815b-b938-4e61-89ea-112417dba31f','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empCityNo','籍贯','string',NULL,10,1,32),('9ab7ecb6-f2d6-4477-8872-3064283e0831','68ee8cfa-4788-4290-b265-f6f4c14e4889','empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('9b091a55-d462-441b-8289-5b5cf8a09b34','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewEmpType.emptypeName','现用工形式','string',NULL,12,1,20),('9b5a4679-61ce-416b-8843-090fcd30bbc4','5d46f106-6099-478c-8911-9c684222cf4c','ectStartDate','合同起始日期','date','yyyy-MM-dd',12,0,68),('9b7a9856-83c9-4d3e-a071-84cb9ed57283','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveCasualHours','事假(小时)','decimal','2',12,0,33),('9bd8f863-e944-494c-b734-2bb308b6f48f','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('9c45b911-09c1-4823-b5e2-ee11c1955968','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('9c684133-459a-4778-bcd2-8772302e61c3','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espLocation.locationName','工作地区','string',NULL,12,1,16),('9c73c0e7-413c-4f4f-acb0-c3a5bec1f24c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDistinctNo','工号','string',NULL,10,0,0),('9c884ac3-a2c7-4fbb-89c3-3f6a49ce8d50','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional3','自定义字段03','string',NULL,15,1,54),('9d142c9c-8229-4817-aade-faca06680b4e','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOtWeekendHours','周末加班(小时)','decimal','2',12,0,55),('9da167e3-386a-4c93-984d-c2140003308d','5d46f106-6099-478c-8911-9c684222cf4c','employee.empType.emptypeName','用工形式','string',NULL,12,1,20),('9de87469-852f-4f66-b4e0-8e5944c50a79','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empDistinctNo','工号','string',NULL,10,0,0),('9e21def8-8e6f-44ca-963f-25cceb06f825','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empMobile','手机','string','',16,0,40),('9e526398-25fa-4170-aaec-cc63fbe338de','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDistinctNo','工号','string','',10,0,0),('9ea5cbc3-9c8e-4d7e-877e-b809190b6bdc','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empSchool','毕业院校','string',NULL,20,0,30),('9ef9d287-86df-4048-8962-9cade3442092','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpStatus','缴费种类','ebpStatus',NULL,10,1,24),('9f31b528-2326-4187-8261-d37cb30fca5d','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional13','自定义字段13','string',NULL,15,1,64),('9f6f08d0-db01-4959-b697-a7ac11747874','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional15','自定义字段15','string',NULL,15,1,66),('a0073411-6766-4e7f-8056-794aa68e0fce','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional11','自定义字段11','string',NULL,15,1,62),('a03da18b-57f9-4295-88a3-fb16bafa3eec','68ee8cfa-4788-4290-b265-f6f4c14e4889','empStatus','状态','empStatus',NULL,10,1,50),('a0489f0e-0534-435d-bb80-77a7b611c6a0','801f19ca-c8a7-4550-be9c-8ab0950631a3','empName','姓名','string','',10,0,1),('a23d0118-5130-4283-89ff-09f0b33a5cd8','dab34b21-15cd-42b0-beb9-8c66d1f49a22','joinYear','公司司龄','decimal','',10,0,68),('a24c4173-848a-4c4d-a30d-08effa8906e4','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empNation','民族','string',NULL,6,1,33),('a34be83f-c122-46d7-813f-306920cf7786','efa905bc-c55a-4678-b21b-2ea94067f178','erJobTitle.jobtitleName','执行岗位','string',NULL,20,1,74),('a4073287-7301-4f14-bc96-05297f38e887','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiDataCalc','计算公式','string',NULL,30,0,6),('a4458ade-910e-4ee0-9f97-089db2120db4','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empDistinctNo','工号','string',NULL,10,0,0),('a48be020-6e70-48c8-bc7c-cf80c8218883','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empUrgentContact','紧急联系人','string',NULL,10,0,46),('a4971dfd-e7ae-4af1-bd70-f55f089c8e0b','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('a4dc5c19-b59c-4f28-9195-e1f74418583f','68ee8cfa-4788-4290-b265-f6f4c14e4889','empGender','性别','empGender',NULL,6,1,4),('a50d766a-b518-41de-a844-0560988e47cd','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empShiftNo','考勤卡号','string',NULL,12,0,49),('a530cdb3-6e6a-457f-b375-6f96ecc9d417','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn10','个人缴社保','decimal',NULL,12,0,71),('a5e15eb7-b4fb-42e3-a351-f57102c3f169','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional5','自定义字段05','string',NULL,15,1,56),('a650181a-7611-4c0d-9fad-0ef09690f64a','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmAbsentDays','旷工(天)','decimal','2',12,0,28),('a6b3c332-345b-4690-83f4-90dd871aca2f','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empMarital','婚否','empMarital',NULL,6,1,5),('a6e7ca7b-4edf-44ad-a2c2-2c4371a730e1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeManager.empName','考评职位描述','string',NULL,10,1,78),('a71cc0cf-8217-4a46-a5f6-b823bf1c4fa4','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional7','自定义字段07','string','',15,1,58),('a73c54bf-1ad6-4e45-a850-cbac30c52133','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional14','自定义字段14','string',NULL,15,1,65),('a7ab7106-cf96-402e-a157-f093deb2c0dc','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,12),('a7e8b06f-f009-4526-9e70-259bf8f04401','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo1.departmentDesc','原一级部门描述','string',NULL,20,1,73),('a8647e13-781c-4c25-827c-fe401215cefd','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLateTimes','迟到(次)','decimal','2',12,0,25),('a89521b8-3c91-4fa5-b008-377f2a504b4a','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo3.Departmentdesc','现三级部门描述','string',NULL,20,1,15),('a9236cd9-17cd-463b-abc3-c644380f2ec1','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional12','自定义字段12','string',NULL,15,1,63),('a924677c-29fe-4727-8ebe-a1bb77596141','801f19ca-c8a7-4550-be9c-8ab0950631a3','empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('a926535d-9a0f-4e20-b949-135795af0b2e','5d46f106-6099-478c-8911-9c684222cf4c','employee.empSpeciality','专业','string',NULL,20,1,31),('a92812e8-be23-4b5d-b626-7ec456df4b2c','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('a99b1935-73b4-48af-93ed-bd25fa1cb1cf','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empProfileLoc','档案所在地','string','',26,0,35),('aa1dbde6-2a46-423b-9c91-4de4fa92de39','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empEmail','邮箱','string','',30,0,37),('aa23fef7-8b6a-4943-bd83-50e03a06e35a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional16','自定义字段16','string','',15,1,67),('aa578ff5-cb92-47ad-9b1b-68e503ce8973','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empComments','备注','string',NULL,20,0,51),('aa67d3ca-995e-441c-b245-157fa74ce34a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empStatus','状态','empStatus','',10,1,50),('aa8bce71-4fae-4c54-91d9-b07bde97ef28','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empHomePhone','家庭电话','string',NULL,16,0,41),('ab0b8a6a-ec3e-4e7a-96ad-5068f764d054','801f19ca-c8a7-4550-be9c-8ab0950631a3','empMsn','MSN/QQ','string','',30,0,38),('ab45d048-d40c-47f0-8554-988b01011c09','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional11','自定义字段11','string',NULL,15,1,62),('aba41e2a-1ef0-4f26-8dc0-ec7d48b52ddc','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empCurrAddr','当前住址','string','',30,0,42),('abc92f75-c3fc-4712-8e49-03807bec8cb4','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empPolitics','政治面貌','string',NULL,10,1,28),('ac3acb85-f3ce-41eb-b526-aa1fb7022138','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empMsn','MSN/QQ','string',NULL,30,0,38),('aca61359-43f2-431e-ba8c-6bc811cf0c07','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional2','自定义字段02','string',NULL,15,1,53),('ace22b89-85f6-4061-a605-6ec205e3e725','53979184-f70f-4360-b548-635dde0fc7ec','empBenefitType.benefitTypeName','社保种类','string',NULL,12,1,67),('acf4af89-a206-481d-9332-159f61fb3247','68ee8cfa-4788-4290-b265-f6f4c14e4889','empHomePhone','家庭电话','string',NULL,16,0,41),('ad66ed30-ad26-4b2a-9b2f-8be02f663dd9','5d46f106-6099-478c-8911-9c684222cf4c','employee.empHomePhone','家庭电话','string',NULL,16,0,41),('ae0b5f44-f297-4ab5-893b-91015054ace5','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional6','自定义字段06','string',NULL,15,1,57),('af00bd7e-24aa-439d-b6de-bcb346757a9a','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo.departmentDesc','现部门描述','string',NULL,20,1,9),('af1b2804-72de-4c80-8198-34b77e384c61','68ee8cfa-4788-4290-b265-f6f4c14e4889','empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('af5390a1-76bf-457b-af39-9b13a34ac47c','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldBranch.departmentName','原分公司','string',NULL,20,1,70),('b004419c-8c77-4e0e-a845-89978b20ddb5','653d4849-2f36-4261-bce9-a99eb6c15073','eqDate','离职日期','date','yyyy-MM-dd',12,0,68),('b0602d22-a005-49dc-9ba8-9ef75f3ee478','801f19ca-c8a7-4550-be9c-8ab0950631a3','empHomePhone','家庭电话','string','',16,0,41),('b07d6024-9edc-41a3-9257-a8204843fcbb','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empStatus','状态','integer',NULL,10,1,50),('b0cb27ec-b4da-4251-b82e-b3d461e9f4d3','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empLocationNo.locationName','工作地区','string','',12,1,16),('b147a282-e6c4-4243-a748-4df95ecb8370','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empSpeciality','专业','string',NULL,20,1,31),('b1878f89-47dd-4b4f-a3ec-8f5c53afb2c8','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('b2256504-8d3b-4fd5-9e79-1c22dfd5154e','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional15','自定义字段15','string',NULL,15,1,66),('b2365077-ff82-47ac-9787-2b2212950806','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEmpId.empName','姓名','string',NULL,10,0,2),('b4055f3c-088c-401b-bf92-7a83e0d95b09','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiDataRounding','尾数舍入','esaiDataRounding',NULL,10,0,7),('b42dc96d-75b4-4dcc-a4c2-2532d6e3de85','68ee8cfa-4788-4290-b265-f6f4c14e4889','empHomeZip','家庭地址邮编','string',NULL,10,0,45),('b42f864d-866e-4f38-bbcd-8ba6e184ad42','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empUrgentConMethod','紧急联系方式','string',NULL,20,0,47),('b43680ea-1fa1-42c6-aae0-c891e5139f22','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('b4decc7f-81cd-48e7-937c-15b191c1af1e','032ee263-9f5b-46b8-bede-6491a8fa7730','leaveHours','请假(小时)','decimal',NULL,10,0,34),('b4e0ca0d-6180-45ba-91b6-3909f849b702','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional5','自定义字段05','string',NULL,15,1,56),('b53ca36f-9d64-44fc-8cfd-a5d5a9ec94c4','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('b562c994-9960-49f1-a31a-f9b395e286c0','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeManager.empDistinctNo','考评职位','string',NULL,10,1,77),('b5e1c7fa-c570-4c76-a521-bafc734f515c','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOvertimeHours','加班(小时)','decimal','2',12,0,53),('b6a6a1ad-3a77-403f-bda0-b34360ff871a','5d46f106-6099-478c-8911-9c684222cf4c','employee.empMarital','婚否','empMarital',NULL,6,1,5),('b6a8e7b1-4777-4b34-8d85-d077f4c51b72','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empType.emptypeName','用工形式','string',NULL,12,1,20),('b6aec0fe-80be-4808-a764-951709ed6242','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo.departmentName','原部门','string',NULL,16,1,71),('b7ec7fff-ad59-481d-a507-33f9a83e189b','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo.departmentDesc','部门描述','string','',20,1,9),('b7f1d25d-3570-48c8-ae37-9b0208566ce5','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.englishName','英文名','string',NULL,20,0,2),('b7f49a7a-a778-4ee6-9531-bc8c2bca08fc','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn13','公司代缴公积金','decimal',NULL,12,0,74),('b83ee7b7-0c33-489b-80eb-4ff4adb95ee4','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('b87791f4-ab9b-4aa1-a5f4-b161575ce26a','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.ectComments','合同备注','string','',20,1,75),('b8957c7e-af44-4757-817b-c60c030ed018','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional8','自定义字段08','string',NULL,15,1,59),('b8ead996-e9bd-442d-b3ea-f118da7dea2b','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empPbNo.pbName','职位','string',NULL,10,1,18),('b96f06d5-bddf-4b0d-802c-414cb1538a7a','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional7','自定义字段07','string',NULL,15,1,58),('b99575cb-fba6-4073-b7c8-3c6912d83c49','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional5','自定义字段05','string',NULL,15,1,56),('ba00a4e9-0cf9-41ac-9f7b-778055455ecb','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empStatus','状态','empStatus','',10,1,50),('bafc537a-003a-4896-82cc-cabe3e3522cf','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional10','自定义字段10','string',NULL,15,1,61),('bbfbf5e9-c3ef-4698-9d5b-b2bd7c483de9','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional6','自定义字段06','string',NULL,15,1,57),('bc07cfcb-d9c6-49ae-825e-f0e5d726d764','68ee8cfa-4788-4290-b265-f6f4c14e4889','empResidenceLoc','户口所在地','string',NULL,26,0,34),('bca3c812-fc94-4655-bfa7-7693b97b9ddf','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.ectStatus','合同状态','ectStatus','',10,1,74),('bd091c77-ada9-4735-8449-180c37076929','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('bd2b474c-b7bf-4292-a386-42feb49ea2c7','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('bd78bcd6-f5b6-43ab-94d4-9fdf2478c546','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftTransferType','变动种类','eftTransferType',NULL,15,0,69),('bda9d330-bdae-4f34-8264-019268686933','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empHomePhone','家庭电话','string',NULL,16,0,41),('be4e4a29-4058-4172-a556-a578d5b2a8fb','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo.departmentName','部门','string',NULL,16,1,8),('be70916f-acd5-4228-8361-f94893119b12','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empHomePhone','家庭电话','string',NULL,16,0,41),('be838b39-496f-498f-bdde-f79a4fc07b76','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empBenefitType.benefitTypeName','社保种类','string','',12,1,36),('be926824-829e-4083-82a7-545d938ef7e6','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional2','自定义字段02','string','',15,1,53),('bfd320c2-01d5-432b-9a3f-fe2f48231fac','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo2.DepartmentName','二级部门','string','',12,1,12),('c0ca5071-fdf3-42f9-a3a2-4642e081836c','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional3','自定义字段03','string',NULL,15,1,54),('c0cb4682-2aec-4b0c-bad1-149890ead9fd','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empBlood','血型','string',NULL,6,1,27),('c10e64bd-469b-4523-886c-c0f20456a2dc','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,10),('c13530e8-63d9-4a68-9427-4f9eb977b207','efa905bc-c55a-4678-b21b-2ea94067f178','erForm','奖惩形式','string',NULL,12,0,76),('c16731d5-c67a-4ed4-a9c8-bbb01ec2d554','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo.departmentName','部门','string','',16,1,8),('c182a3f2-479c-40d0-bb57-91206c9988c8','5d46f106-6099-478c-8911-9c684222cf4c','employee.empMsn','MSN/QQ','string',NULL,30,0,38),('c20bbc32-d5a5-4321-88d7-adc84bd496bd','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('c21651d0-4951-434c-941b-123edc3624ca','5d46f106-6099-478c-8911-9c684222cf4c','ectEndDate','合同结束日期','date','yyyy-MM-dd',12,0,69),('c218fac2-84df-4018-a15a-ed3a53448d9e','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveSick01Hours','病假住院(小时)','decimal','2',12,0,37),('c22f28a7-bf4f-40c2-afa1-03d332bf0588','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','eeType','考评种类','string',NULL,12,0,70),('c28dc65f-56ff-495f-ab38-b326de83e734','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empShiftNo','考勤卡号','string',NULL,12,0,49),('c2aa9461-4aaa-47de-abc5-94852c865470','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empUrgentContact','紧急联系人','string',NULL,10,0,46),('c2f25fcd-28f4-4b4b-9e81-d157c2ddd48c','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiDataIsCalc','字段属性','esaiDataIsCalc',NULL,12,0,5),('c36a5928-109a-44d6-87c6-2904eb044195','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional9','自定义字段09','string',NULL,15,1,60),('c3defad5-dd1c-49cc-aca5-f281cb661209','5d46f106-6099-478c-8911-9c684222cf4c','employee.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('c4602c23-40a1-4f99-8a61-b1ed950dd731','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo1.DepartmentName','现一级部门','string',NULL,12,1,10),('c5867a79-69f7-41d2-b0e2-b8ea94e9e548','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional3','自定义字段03','string',NULL,15,1,54),('c5e22b6c-4602-40db-8e6a-ac22ce09aa0f','032ee263-9f5b-46b8-bede-6491a8fa7730','offDutyTime','下班时间','date','HH:mm',10,0,28),('c7736adc-faad-4884-80ab-43b99518899a','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,8),('c7a8ebe7-e39a-40b1-87de-c97c649b73c6','68ee8cfa-4788-4290-b265-f6f4c14e4889','empMarital','婚否','empMarital',NULL,6,1,5),('c7fca848-1219-4598-a362-80f442fcaae8','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmEarlyLeave','早退(次)','decimal','2',12,0,26),('c806ddc6-22b3-4cb7-b37d-127231053e6a','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional6','自定义字段06','string',NULL,15,1,57),('c858dc97-a566-423d-bd0d-693d1e952afa','5d46f106-6099-478c-8911-9c684222cf4c','employee.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('c9107a25-0144-4698-bca3-56b8604cad5f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional12','自定义字段12','string','',15,1,63),('c952aef0-fabc-44a8-b9b9-daf51212c9ce','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empPbNo.pbName','职位','string',NULL,10,1,18),('c9c23c68-2369-40fe-992c-66c63ba61344','5d46f106-6099-478c-8911-9c684222cf4c','ectNo','合同编号','string',NULL,15,0,73),('ca709e8d-9bbc-4bcb-a342-19d46574fb5e','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional8','自定义字段08','string',NULL,15,1,59),('cb2bbc9b-c392-462e-8467-b85604e588f9','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo.departmentName','部门名称','string',NULL,20,1,5),('cb3a1cfb-d0f4-445a-bd2e-238178f27a8d','68ee8cfa-4788-4290-b265-f6f4c14e4889','empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('cb47ac01-3de9-49bd-8c5e-d41e726abcc4','801f19ca-c8a7-4550-be9c-8ab0950631a3','empGender','性别','empGender','',6,1,4),('cc929da2-0f5d-4a1f-ac29-e57883c0b8a0','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional11','自定义字段11','string','',15,1,62),('cc9411b7-a802-4d98-a6d9-419dc768e06d','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empCityNo','籍贯','string',NULL,10,1,32),('ccafd7e5-0a17-41d5-adea-01e95467be2c','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional14','自定义字段14','string',NULL,15,1,65),('ccd34d2d-55ca-42b1-9cae-0d6c29ca17a1','801f19ca-c8a7-4550-be9c-8ab0950631a3','empNation','民族','string','',6,1,33),('cce6a3e1-1956-4e31-86fe-38870437e60c','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empBranch.departmentName','分公司','string','',16,1,6),('cd05a843-943e-4720-805b-9236e0c8f759','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional15','自定义字段15','string',NULL,15,1,66),('cd794a47-c1b2-4caf-91e7-84a9b929ce66','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empDeptNo1.DepartmentName','一级部门','string','',12,1,10),('cf72285c-d0cc-4831-9bed-6d200e6b2547','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empJoinDate','入职日期','date','yyyy-MM-dd',12,0,23),('cfa09229-4798-4328-a94b-c886c15cc9d3','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empShiftType','考勤方式','empShiftType',NULL,12,1,48),('d05edaad-2aa8-4f1a-9c90-854b92307042','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional4','自定义字段04','string',NULL,15,1,55),('d07c7dff-8ec8-4b1e-9449-5ddd7c09fbab','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empName','姓名','string',NULL,10,1,1),('d0cde5c5-88b9-4944-8f51-8cb6ee957a58','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional4','自定义字段04','string','',15,1,55),('d14bfc2e-dcc5-48de-bca1-ebfb32fd8b22','53979184-f70f-4360-b548-635dde0fc7ec','config.showColumn15','个人缴社保总额','decimal',NULL,12,0,76),('d2568053-deae-46c2-9ba4-ffd595c4c1f1','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('d3ae8b8f-f0f6-47bb-8b83-6efb051f6c3d','68ee8cfa-4788-4290-b265-f6f4c14e4889','empIdentificationType','证件种类','empIdentificationType',NULL,10,0,25),('d4281b93-edf4-4497-972c-66c4290e7f29','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empResidenceLoc','户口所在地','string',NULL,26,0,34),('d4362714-b060-42c6-8437-eaecd02c2cc7','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('d621c88b-7561-44ee-8628-1429ca3974c2','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfInsuranceAmount','综合保险基数','decimal',NULL,12,0,70),('d70ac137-348b-4fbe-8f9e-d28fe9ed067e','68ee8cfa-4788-4290-b265-f6f4c14e4889','empCurrAddr','当前住址','string',NULL,30,0,42),('d713df7e-d8d7-4175-b052-6453f8801549','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empAdditional12','自定义字段12','string',NULL,15,1,63),('d71486ca-497a-440c-a830-2c2cecfecb8c','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEsavId.esavEsac.esacName','帐套名称','string',NULL,15,1,19),('d7c103a7-863d-4d13-bb48-463b3287f9b5','801f19ca-c8a7-4550-be9c-8ab0950631a3','empBranch.departmentDesc','分公司描述','string','',20,1,7),('d82c92ac-d563-4c5d-a28e-1f3d185115f8','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftNewDeptNo1.DepartmentDesc','现一级部门描述','string',NULL,20,1,11),('d8c802ec-cae1-47ac-bd55-24400c7a89ee','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('d9322f29-3e75-49c5-b7c6-09205866441f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('da360691-9bee-479f-9afe-978e2bf48dda','801f19ca-c8a7-4550-be9c-8ab0950631a3','empPbNo.pbName','职位','string','',10,1,18),('da68a20e-552b-40e1-95ea-923c3e2a2c3a','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOnDutyDays','出勤(天)','decimal','2',12,0,22),('da976cbb-adea-40fa-a009-b9f965eae8ec','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empHomeAddr','家庭地址','string',NULL,30,0,44),('db18b49f-b0aa-4820-9abb-fd375d6f4d07','801f19ca-c8a7-4550-be9c-8ab0950631a3','empSpeciality','专业','string','',20,1,31),('db597506-896f-4ece-b12b-e251b995fef8','5d46f106-6099-478c-8911-9c684222cf4c','employee.empSchool','毕业院校','string',NULL,20,0,30),('db6c577e-a75c-45e0-9a9d-9089315256bb','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empProfileLoc','档案所在地','string',NULL,26,0,35),('dc4fdac0-2773-4209-9b53-ff3586a9740e','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveWeddingHours','婚假(小时)','decimal','2',12,0,39),('dc711751-cdc1-4af8-9f40-ba01a9b0da8b','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpBelongYearmonth','所属年月','string',NULL,10,1,23),('dcbdc388-0cb7-467d-9012-846f898e8f78','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDistinctNo','工号','string',NULL,10,1,0),('dcd0bdb0-a439-45d2-ae66-50dba8b749f1','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveTiaoxiuHours','调休(小时)','decimal','2',12,0,47),('dda5688e-a9f8-471d-984c-f9f6bc96d031','5d46f106-6099-478c-8911-9c684222cf4c','employee.empShiftType','考勤方式','empShiftType',NULL,12,0,48),('de678597-584c-45ef-919c-f0d21d394385','032ee263-9f5b-46b8-bede-6491a8fa7730','absentHours','缺勤(小时)','decimal',NULL,10,0,37),('e04c49c0-f4df-4572-bd19-82f42cc9b11f','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldDeptNo1.departmentName','原一级部门','string',NULL,20,1,72),('e063f503-55ec-4f9c-a71c-ba52c1216a73','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empAdditional1','自定义字段01','string',NULL,15,1,52),('e07d869e-9926-4ea7-90ef-009d6cfc216e','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empBirthDate','生日','date','yyyy-MM-dd',12,0,3),('e0f2623f-c827-4eeb-b624-e15f2a15ac77','68ee8cfa-4788-4290-b265-f6f4c14e4889','empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('e1a50868-194f-4977-900a-b07a2785ab0f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empDeptNo1.DepartmentDesc','一级部门描述','string','',20,1,11),('e251c0dc-d67a-489e-9466-d9caa0a67486','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('e25b05cb-3bf1-4c5f-8538-5712f91d4ccf','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDistinctNo','工号','string',NULL,10,0,0),('e28c1303-49d4-4e20-a4a5-421c86f58eaf','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empNation','民族','string','',6,1,33),('e2c41ac8-289f-4300-940b-d86abb8bdabc','801f19ca-c8a7-4550-be9c-8ab0950631a3','empComments','备注','string','',20,0,51),('e2fc0f5f-3a87-4ac9-8e0c-ad183813dcf5','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empConfirmDate','转正日期','date','yyyy-MM-dd',12,0,24),('e3346699-3051-4f3b-b000-513c57f5b3f0','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('e35c7f11-7269-4834-bbd3-adb087460036','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmOnDutyHours','出勤(小时)','decimal','2',12,0,21),('e3d1620e-fa74-4148-9002-cac362e5f01e','efa905bc-c55a-4678-b21b-2ea94067f178','erContent','奖惩备注','string','15',20,0,77),('e469a90e-fa61-4166-b4f3-72d575ad0f49','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('e4d7e7f8-dd2f-4258-ade1-38370127eacf','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDiploma','学历','string',NULL,10,1,29),('e5aabd7b-d5cd-47e9-aa34-1f123d0d6078','653d4849-2f36-4261-bce9-a99eb6c15073','employee.englishName','英文名','string',NULL,20,0,2),('e5e39892-6a53-4818-830d-eff3f12c5112','09e0e6bb-d846-474a-aeeb-8655d2541686','esaiEsdd.esddName','项目名称','string',NULL,15,0,4),('e6537ba1-48e9-4e31-bd69-8f58345655ce','653d4849-2f36-4261-bce9-a99eb6c15073','erComments','离职备注','string',NULL,20,0,73),('e7561987-a5a1-4965-90ea-a8aab87b5018','801f19ca-c8a7-4550-be9c-8ab0950631a3','empEmail','邮箱','string','',30,0,37),('e790c7b2-84ca-44a3-8440-d1bf30386dea','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empAdditional8','自定义字段08','string',NULL,15,1,59),('e7f72fb0-9bbb-4e68-8dbb-553757c97390','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('e8a5a02e-87c6-4276-b4ea-360dd237716c','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empSchool','毕业院校','string',NULL,20,0,30),('e8dc3be0-cc2f-4a8c-9a96-d4244c259677','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('e9295f7c-b45e-4a1c-afb9-9dec3d2a6676','68ee8cfa-4788-4290-b265-f6f4c14e4889','empPolitics','政治面貌','string',NULL,10,1,28),('e932027b-fbb9-49f3-a477-5a5763351cb4','801f19ca-c8a7-4550-be9c-8ab0950631a3','empType.emptypeName','用工形式','string','',12,1,20),('e95c2469-3ca8-486a-9fee-3e969d6c86af','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('e990efde-bfeb-4fea-8869-c49bc0d5a6bd','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empProfileLoc','档案所在地','string',NULL,26,0,35),('e9975c17-a03d-4066-a707-697d1131b0b8','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,11),('ea0570fd-1f2f-43f4-bc60-62a88f62a8eb','5d46f106-6099-478c-8911-9c684222cf4c','employee.empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('ea475ce5-5739-4377-9a5f-c6b6d88efdf6','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empSpeciality','专业','string',NULL,20,1,31),('eac6a619-7e8b-4563-94e0-7aeb83dd8fa4','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empEmail','邮箱','string',NULL,30,0,37),('eaeb77b0-d0bd-4b8a-b37c-c39580dbf5e1','f0f570ec-4057-4ba8-ad13-a1f542828cbe','espEmpno.empDistinctNo','工号','string',NULL,10,0,0),('eb99a1a8-471a-4f36-8302-57993b4685ea','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empNation','民族','string',NULL,6,1,33),('ebe62cef-3420-49ce-9d2c-563ae3907306','68ee8cfa-4788-4290-b265-f6f4c14e4889','empMsn','MSN/QQ','string',NULL,30,0,38),('ed7f032d-0290-4b2d-9bb6-62bf60d03a2d','53979184-f70f-4360-b548-635dde0fc7ec','benefit.ebfPensionNo','养老保险号','string',NULL,20,0,79),('ed903c81-7162-4e28-988a-a9253f29a4a1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empHomeZip','家庭地址邮编','string',NULL,10,0,45),('eda22534-4023-4f04-b070-ebb80a4db7cb','801f19ca-c8a7-4550-be9c-8ab0950631a3','quit.erComments','离职备注','string','',20,0,72),('eda68c78-cb69-4eae-8e27-d0c918e10baa','5d46f106-6099-478c-8911-9c684222cf4c','employee.empProfileLoc','档案所在地','string',NULL,26,0,35),('ef236ac8-eb59-4668-82a1-8515f93ada27','53979184-f70f-4360-b548-635dde0fc7ec','empIdentificationNo','证件号码','string',NULL,20,0,26),('f009d746-4cc0-4a20-a6d2-31b29313da0b','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('f01c7c27-60b2-4e91-9dbc-33e652d7a0c3','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empGender','性别','empGender',NULL,6,1,4),('f02a5ed1-8623-4917-9ba3-e6732bb53393','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empMsn','MSN/QQ','string','',30,0,38),('f05969d6-b020-479f-ad1c-085ba9ceef49','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,14),('f073c756-fc06-432f-b47b-8c2fada65a34','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empEmail','邮箱','string',NULL,30,0,37),('f080babb-eaa5-44fa-8eef-e5b636e3cea9','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional13','自定义字段13','string',NULL,15,1,64),('f2e559d7-68b7-470e-94b0-1c6ca300ba80','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('f2ea14f1-553a-48db-8ff2-041e8a52bf7d','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empComments','备注','string',NULL,20,0,51),('f2f27d98-1b0c-4444-a300-e8ea564a8e1d','53979184-f70f-4360-b548-635dde0fc7ec','empLocationNo.locationName','工作地区','string',NULL,12,1,16),('f38afe04-1331-4bcd-82a1-9b971932434b','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional10','自定义字段10','string',NULL,15,1,61),('f56970a4-f288-45af-9142-e793c1f4056a','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('f5dd5240-2e34-485b-909f-13a38352278e','5d46f106-6099-478c-8911-9c684222cf4c','employee.empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('f60cce69-32d7-4b9a-91b8-8228b769089b','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empCurrAddr','当前住址','string',NULL,30,0,42),('f62aa006-55b4-4914-91d1-226a228fee6a','5d46f106-6099-478c-8911-9c684222cf4c','employee.empAdditional15','自定义字段15','string',NULL,15,1,66),('f666ce48-8f4a-4367-be3c-aa8fb98e55c0','653d4849-2f36-4261-bce9-a99eb6c15073','eqType','离职种类','eqType',NULL,12,0,69),('f79fe146-b802-4167-849c-c7fed0247cc2','5d46f106-6099-478c-8911-9c684222cf4c','employee.empUrgentContact','紧急联系人','string',NULL,10,0,46),('f7bd8e15-cd05-4298-858c-ee36bb5a06d4','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empBranch.departmentName','分公司','string',NULL,16,1,6),('f8039a44-fd8a-49b3-bf4d-b19a09050762','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empEmail','邮箱','string',NULL,30,0,37),('f84e1d77-a1ec-4dc3-950f-4bc6031bc892','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','attmLeaveOuterHours','因公外出(小时)','decimal','2',12,0,45),('f8668d0c-1fa7-4365-986e-a24057d3ec7f','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional1','自定义字段01','string','',15,1,52),('f8a3394e-c5fb-42aa-af5b-e86d10dffe92','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional8','自定义字段08','string',NULL,15,1,59),('f96e2ccb-947d-4bc5-a828-e7d5ffd890c7','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empIdentificationNo','证件号码','string','',20,0,26),('f9e15593-ed45-4023-b3e1-fb4b1ea076dc','032ee263-9f5b-46b8-bede-6491a8fa7730','empDistinctNo','工号','string',NULL,20,0,0),('fa17741b-03f8-4e19-b839-4ea1838dc317','efa905bc-c55a-4678-b21b-2ea94067f178','employee.empHomeAddr','家庭地址','string',NULL,30,0,44),('fa1ea0f9-a875-4596-8d82-9e038a710e00','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('fac2ba4e-a371-45a0-87e4-c45554c4a489','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empAdditional7','自定义字段07','string',NULL,15,1,58),('fb0c2f6a-7611-4177-a5d8-c8e7bae4e7b7','801f19ca-c8a7-4550-be9c-8ab0950631a3','empHomeZip','家庭地址邮编','string','',10,0,45),('fba9b9f1-d6dd-4c24-b20d-6b051bc6bd8a','801f19ca-c8a7-4550-be9c-8ab0950631a3','empAdditional5','自定义字段05','string','',15,1,56),('fbacd67e-c45f-4429-ace2-4403f98be992','dab34b21-15cd-42b0-beb9-8c66d1f49a22','contract.ectStartDate','合同起始日期','date','yyyy-MM-dd',12,1,72),('fc01696a-faf6-40b2-b4bc-872e9e792100','68ee8cfa-4788-4290-b265-f6f4c14e4889','empIdentificationNo','证件号码','string',NULL,20,0,26),('fc226222-da25-4fb1-9ca1-c62cc987d0b8','653d4849-2f36-4261-bce9-a99eb6c15073','eqReason','离职原因','string',NULL,20,0,72),('fc31bfc2-cb90-45be-98d3-5340128f7af1','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','employeeByEeEmpNo.empHomePhone','家庭电话','string',NULL,16,0,41),('fc4756a2-c196-44dc-bcb9-02451fc1e459','5d46f106-6099-478c-8911-9c684222cf4c','employee.empPolitics','政治面貌','string',NULL,10,1,28),('fcca7328-4fc4-458f-8149-c923acb58317','653d4849-2f36-4261-bce9-a99eb6c15073','employee.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('fcf600bc-43cb-4508-b884-6b875b79fc0b','53979184-f70f-4360-b548-635dde0fc7ec','empDeptNo.departmentName','部门','string',NULL,16,1,8),('fd3b7f7c-9785-499a-9a25-52f867c59b52','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('fd878218-2c57-453c-a4e6-be606bae7388','801f19ca-c8a7-4550-be9c-8ab0950631a3','empCurrZip','当前住址邮编','string','',10,0,43),('fdb09fe4-c4a4-4959-8336-13fe05c26dc1','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empPbNo.pbDesc','职位描述','string',NULL,10,1,16),('fddeab1e-0a2a-4bca-9220-d29e3be94c4d','dab34b21-15cd-42b0-beb9-8c66d1f49a22','empHomeZip','家庭地址邮编','string','',10,0,45),('fe2301c3-27eb-469e-b654-e097e72f1cbc','b809eebf-b8a0-4a00-919f-57e330ad4da1','employee.empWorkDate','参加工作日期','date','yyyy-MM-dd',12,0,22),('fe2f9efc-e32b-45fb-83c0-8260f17f5db5','68ee8cfa-4788-4290-b265-f6f4c14e4889','empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('fe789c6d-5454-4720-9a96-496e6b62ea8d','801f19ca-c8a7-4550-be9c-8ab0950631a3','englishName','英文名','string','',20,0,2),('fee604ed-a546-459e-980a-766334684968','b809eebf-b8a0-4a00-919f-57e330ad4da1','eftOldBranch.departmentDesc','原分公司描述','string',NULL,20,1,71),('ff042d42-b873-4c36-abe0-114836e95a27','68ee8cfa-4788-4290-b265-f6f4c14e4889','empAdditional2','自定义字段02','string',NULL,15,1,53),('ff731861-d28d-4e19-9bf3-1c2069208566','5d46f106-6099-478c-8911-9c684222cf4c','employee.empDistinctNo','工号','string',NULL,10,0,0),('ff808081232096ef0123236fe1fa01b2','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EnglishName','英文名','string',NULL,20,0,2),('ff808081232096ef0123236fe20e01b3','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpBirthDate','生日','date','yyyy-MM-dd',12,0,3),('ff808081232096ef0123236fe20e01b4','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpGender','性别','empGender',NULL,6,1,4),('ff808081232096ef0123236fe20e01b5','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpMarital','婚否','empMarital',NULL,6,1,5),('ff808081232096ef0123236fe20e01b6','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empBranch.departmentName','分公司','string',NULL,16,1,6),('ff808081232096ef0123236fe20e01b7','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empBranch.departmentDesc','分公司描述','string',NULL,20,1,7),('ff808081232096ef0123236fe20e01b8','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo.departmentName','部门','string',NULL,16,1,8),('ff808081232096ef0123236fe20e01b9','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,9),('ff808081232096ef0123236fe20e01ba','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo1.DepartmentName','一级部门','string',NULL,12,1,10),('ff808081232096ef0123236fe20e01bb','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo1.DepartmentDesc','一级部门描述','string',NULL,20,1,11),('ff808081232096ef01232373933e01c6','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo2.DepartmentName','二级部门','string',NULL,12,1,12),('ff808081232096ef01232373933e01c7','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo2.DepartmentDesc','二级部门描述','string',NULL,20,1,13),('ff808081232096ef01232373933e01c8','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo3.DepartmentName','三级部门','string',NULL,16,1,14),('ff808081232096ef01232373933e01c9','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empDeptNo3.Departmentdesc','三级部门描述','string',NULL,20,1,15),('ff808081232096ef01232373933e01ca','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empLocationNo.locationName','工作地区','string',NULL,12,1,16),('ff808081232096ef01232373933e01cb','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empLocationNo.locationDesc','工作地区描述','string',NULL,20,1,17),('ff808081232096ef01232373933e01cc','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empPbNo.pbName','职位','string',NULL,10,1,18),('ff808081232096ef01232373933e01cd','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empPbNo.pbDesc','职位描述','string',NULL,10,1,19),('ff808081232096ef01232373933e01ce','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empType.emptypeName','用工形式','string',NULL,12,1,20),('ff808081232096ef01232373933e01cf','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.empType.emptypeDesc','用工形式描述','string',NULL,20,1,21),('ff808081232096ef01232375ec5701da','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpShiftType','考勤方式','empShiftType',NULL,12,1,22),('ff808081232096ef01232375ec5701db','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpShiftNo','考勤卡号','string',NULL,12,0,23),('ff808081232096ef01232375ec6101dc','032ee263-9f5b-46b8-bede-6491a8fa7730','empObj.EmpStatus','状态','empStatus',NULL,10,1,24),('ffc882f9-cf36-4f45-b1d1-30c33ef82462','444bd513-c17a-4dc3-ac7a-e73444b00dfb','ebpEmpno.empDeptNo.departmentDesc','部门描述','string',NULL,20,1,6);
/*!40000 ALTER TABLE `outmatchbasic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outmatchmodel`
--

DROP TABLE IF EXISTS `outmatchmodel`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `outmatchmodel` (
  `omm_id` varchar(36) NOT NULL,
  `omm_io_id` varchar(36) NOT NULL,
  `omm_name` varchar(64) NOT NULL,
  `omm_desc` varchar(64) default NULL,
  `omm_output_type` varchar(8) NOT NULL,
  `omm_no_title` int(10) unsigned NOT NULL default '0',
  `omm_statistic_place` int(10) unsigned NOT NULL default '0',
  `omm_statistic_display_type` int(10) unsigned NOT NULL default '0',
  `omm_row_height` int(10) unsigned default NULL,
  `omm_default` int(10) unsigned NOT NULL default '0',
  `omm_create_by` varchar(36) NOT NULL,
  `omm_create_time` datetime NOT NULL,
  `omm_last_change_by` varchar(36) NOT NULL,
  `omm_last_change_time` datetime NOT NULL,
  PRIMARY KEY  (`omm_id`),
  KEY `FK_omm_out_id` USING BTREE (`omm_io_id`),
  CONSTRAINT `FK_omm_io_id` FOREIGN KEY (`omm_io_id`) REFERENCES `iodef` (`io_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `outmatchmodel`
--

LOCK TABLES `outmatchmodel` WRITE;
/*!40000 ALTER TABLE `outmatchmodel` DISABLE KEYS */;
INSERT INTO `outmatchmodel` VALUES ('0524a661-f0f1-4f67-8bb3-04ddd8d983ef','444bd513-c17a-4dc3-ac7a-e73444b00dfb','社保缴费历史','社保缴费历史','excel',0,0,0,25,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-09-04 17:35:49','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 20:06:14'),('402880e4232bd2d301232c51f3bd0001','f0f570ec-4057-4ba8-ad13-a1f542828cbe','每月薪资发放-部门','每月薪资发放-部门','excel',0,3,0,25,0,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-18 15:04:11','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 19:06:24'),('402880e423785e400123789c86660007','b809eebf-b8a0-4a00-919f-57e330ad4da1','变动记录','变动记录','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-09-02 10:36:47','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:55:22'),('402880e423785e40012378c1cea60058','5d46f106-6099-478c-8911-9c684222cf4c','人事合同','人事合同','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-09-02 11:17:30','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:25:54'),('402880e423792e7f01237935899a0001','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','考评记录','考评记录','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-09-02 13:23:51','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:50:57'),('402880e423792e7f01237977b07a005a','efa905bc-c55a-4678-b21b-2ea94067f178','奖惩记录','奖惩记录','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-09-02 14:36:10','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:58:44'),('402880e42379dc1e01237a4d35bb0003','653d4849-2f36-4261-bce9-a99eb6c15073','离职记录','离职记录','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-09-02 18:29:23','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:29:59'),('402880f0230992e7012309b3892c003f','68ee8cfa-4788-4290-b265-f6f4c14e4889','在职员工基础资料','在职员工基础资料','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 21:44:04','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:22:27'),('402880f0230992e7012309ba32dc0084','68ee8cfa-4788-4290-b265-f6f4c14e4889','在职员工基础资料-部门','在职员工基础资料-部门','excel',0,3,1,25,0,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 21:51:20','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:23:53'),('402880f0230992e7012309ca912b00c9','801f19ca-c8a7-4550-be9c-8ab0950631a3','离职员工基础资料','离职员工基础资料','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 22:09:13','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:49:17'),('402880f0230992e7012309cdc7ae0113','801f19ca-c8a7-4550-be9c-8ab0950631a3','离职员工基础资料-原因','离职员工基础资料-原因','excel',0,3,0,25,0,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 22:12:44','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:47:56'),('402880f0230992e7012309d6c689015d','dab34b21-15cd-42b0-beb9-8c66d1f49a22','在职员工人事合同','在职员工人事合同','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 22:22:33','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:57:14'),('402880f0230992e7012309d979f101aa','dab34b21-15cd-42b0-beb9-8c66d1f49a22','在职员工人事合同-部门','在职员工人事合同-部门','excel',0,3,0,25,0,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-11 22:25:30','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:57:35'),('402880f0239cabc30123a70f5238000f','5d46f106-6099-478c-8911-9c684222cf4c','人事合同-部门','人事合同-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-09-11 11:04:42','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:26:23'),('402880f0246a87a301246bcb85350003','653d4849-2f36-4261-bce9-a99eb6c15073','离职记录-部门','离职记录-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-19 15:55:50','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:31:24'),('402880f0246a87a301246bd423df004e','96d972eb-a0cd-4ccd-a56e-a9bdbea4c68f','考评记录-部门','考评记录-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-19 16:05:15','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:52:50'),('402880f0246a87a301246be0df8400a0','b809eebf-b8a0-4a00-919f-57e330ad4da1','变动记录-部门','变动记录-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-19 16:19:07','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:54:39'),('402880f0246a87a301246be3c70b00f1','efa905bc-c55a-4678-b21b-2ea94067f178','奖惩记录-部门','奖惩记录-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-19 16:22:20','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:59:01'),('402880f024945fb301249478ae110001','68ee8cfa-4788-4290-b265-f6f4c14e4889','员工通讯录','员工通讯录','excel',0,0,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 13:29:46','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 18:21:43'),('402880f02495539a01249557fa5f0003','09e0e6bb-d846-474a-aeeb-8655d2541686','所有薪资帐套','所有薪资帐套','excel',0,3,0,25,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 17:33:41','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 17:52:25'),('402880f02495539a01249566a793000c','cca1a5ac-6140-421c-9eea-848c615b8306','薪资帐套','薪资帐套','excel',0,0,0,25,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 17:49:43','855e2ab3-1a63-417a-a296-a0add3085500','2009-10-27 17:49:53'),('402880f024a13a830124a15435790063','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','每月考勤汇总-小时','每月考勤汇总-小时','excel',0,0,0,25,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-30 01:25:00','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 12:41:05'),('402880f024a13a830124a1554023008c','ab8a4176-30f2-4e5d-afcb-fe91744c7ffd','每月考勤汇总-天','每月考勤汇总-天','excel',0,0,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-10-30 01:26:09','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 12:39:21'),('402881e524ae08300124ae0fc0490002','032ee263-9f5b-46b8-bede-6491a8fa7730','每日考勤-部门','每日考勤-部门','excel',0,3,0,25,0,'855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 12:45:18','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 20:20:24'),('f26f823e-30b0-460a-b357-ea4527364ab4','eee58bd1-176f-4853-9d50-c4581b1aff2e','员工排班信息','员工排班信息','excel',0,0,0,25,1,'855e2ab3-1a63-417a-a296-a0add3085500','2009-07-23 10:32:30','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 19:08:24'),('ff808081232096ef012320b833ba0002','53979184-f70f-4360-b548-635dde0fc7ec','社保配置','社保配置','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-16 09:00:25','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 20:05:09'),('ff808081232096ef012321e796490087','53979184-f70f-4360-b548-635dde0fc7ec','社保配置-部门','社保配置-部门','excel',0,3,0,25,0,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-16 14:31:48','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 20:05:18'),('ff808081232096ef0123221a6b420127','f0f570ec-4057-4ba8-ad13-a1f542828cbe','每月薪资发放','每月薪资发放','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-16 15:27:19','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 19:06:35'),('ff808081232096ef0123237a954e01e0','032ee263-9f5b-46b8-bede-6491a8fa7730','每日考勤','每日考勤','excel',0,0,0,25,1,'16fa4ba1-bf83-455a-b28b-28f2fdd34d93','2009-08-16 21:51:59','855e2ab3-1a63-417a-a296-a0add3085500','2009-11-01 20:20:12');
/*!40000 ALTER TABLE `outmatchmodel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtimerequest`
--

DROP TABLE IF EXISTS `overtimerequest`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `overtimerequest` (
  `or_id` varchar(36) NOT NULL default '',
  `or_no` int(10) unsigned NOT NULL default '0',
  `or_emp_no` varchar(36) NOT NULL default '',
  `or_emp_dept` varchar(36) NOT NULL default '',
  `or_emp_location` varchar(36) NOT NULL default '',
  `or_ot_no` varchar(36) NOT NULL default '',
  `or_reason` varchar(255) NOT NULL default '',
  `or_start_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `or_end_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `or_total_hours` decimal(3,1) NOT NULL default '0.0',
  `or_tiaoxiu_hours` decimal(3,1) default NULL,
  `or_tiaoxiu_expire` date default NULL,
  `or_next_approver` varchar(36) default NULL,
  `or_status` int(10) unsigned NOT NULL default '0',
  `or_need_gm_approve` int(10) unsigned default NULL,
  `or_create_by` varchar(36) NOT NULL default '',
  `or_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `or_last_change_by` varchar(36) NOT NULL default '',
  `or_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `or_security_no` varchar(36) default NULL,
  PRIMARY KEY  (`or_id`),
  KEY `FK_or_emp_no` (`or_emp_no`),
  KEY `FK_or_emp_dept` (`or_emp_dept`),
  KEY `FK_or_emp_location` (`or_emp_location`),
  KEY `FK_or_ot_no` (`or_ot_no`),
  KEY `FK_or_create_by` (`or_create_by`),
  KEY `FK_or_last_change_by` (`or_last_change_by`),
  CONSTRAINT `FK_or_emp_dept` FOREIGN KEY (`or_emp_dept`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_or_emp_location` FOREIGN KEY (`or_emp_location`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_or_emp_no` FOREIGN KEY (`or_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_or_ot_no` FOREIGN KEY (`or_ot_no`) REFERENCES `overtimetype` (`ot_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `overtimerequest`
--

LOCK TABLES `overtimerequest` WRITE;
/*!40000 ALTER TABLE `overtimerequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `overtimerequest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overtimetype`
--

DROP TABLE IF EXISTS `overtimetype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `overtimetype` (
  `ot_no` varchar(36) NOT NULL default '',
  `ot_name` varchar(64) NOT NULL default '',
  `ot_desc` varchar(128) default NULL,
  `ot_over_limit` int(10) unsigned default NULL,
  `ot_hour_rate` decimal(3,1) NOT NULL default '1.0',
  `ot_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`ot_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `overtimetype`
--

LOCK TABLES `overtimetype` WRITE;
/*!40000 ALTER TABLE `overtimetype` DISABLE KEYS */;
INSERT INTO `overtimetype` VALUES ('4d97f14f-d472-4005-828d-6d6bbd444407','普通加班','general overtime',NULL,'1.0',0),('6c78e8bc-5b18-4595-bd3e-facc23d8763e','周末加班','weekend overtime',NULL,'1.0',0),('a1b47b0f-2bb9-459a-bf55-ba72b5b6739c','节日加班','festival overtime',NULL,'1.0',0);
/*!40000 ALTER TABLE `overtimetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `position` (
  `position_id` varchar(36) NOT NULL default '',
  `position_pb_id` varchar(36) NOT NULL default '',
  `position_parent_id` varchar(36) default NULL,
  `position_take_num` int(10) unsigned default NULL,
  `position_emp_no` varchar(36) default NULL,
  PRIMARY KEY  (`position_id`),
  KEY `FK_position_pb_id` (`position_pb_id`),
  KEY `FK_position_emp_no` (`position_emp_no`),
  CONSTRAINT `FK_position_emp_no` FOREIGN KEY (`position_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_position_pb_id` FOREIGN KEY (`position_pb_id`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positionbase`
--

DROP TABLE IF EXISTS `positionbase`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `positionbase` (
  `pb_id` varchar(36) NOT NULL default '',
  `pb_name` varchar(64) NOT NULL default '',
  `pb_desc` varchar(128) default NULL,
  `pb_sup_id` varchar(36) default NULL,
  `pb_job_title` varchar(36) NOT NULL,
  `pb_dept_id` varchar(36) NOT NULL default '',
  `pb_in_charge` int(10) NOT NULL default '0',
  `pb_max_employee` int(10) unsigned default NULL,
  `pb_max_exceed` int(10) unsigned NOT NULL default '1',
  `pb_desc_attach` varchar(128) default NULL,
  `pb_work_address` varchar(64) default NULL,
  `pb_work_time` varchar(32) default NULL,
  `pb_work_tool` varchar(128) default NULL,
  `pb_status` int(10) unsigned NOT NULL default '1',
  `pb_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`pb_id`),
  KEY `FK_pb_job_title` (`pb_job_title`),
  KEY `FK_pb_dept_id` (`pb_dept_id`),
  CONSTRAINT `FK_pb_dept_id` FOREIGN KEY (`pb_dept_id`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_pb_job_title` FOREIGN KEY (`pb_job_title`) REFERENCES `jobtitle` (`jobtitle_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `positionbase`
--

LOCK TABLES `positionbase` WRITE;
/*!40000 ALTER TABLE `positionbase` DISABLE KEYS */;
/*!40000 ALTER TABLE `positionbase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `positionbasehist`
--

DROP TABLE IF EXISTS `positionbasehist`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `positionbasehist` (
  `pbhi_id` varchar(36) NOT NULL default '',
  `pbhi_pb_id` varchar(36) NOT NULL default '',
  `pbhi_dept_id` varchar(36) NOT NULL default '',
  `pbhi_in_charge` int(10) unsigned NOT NULL default '0',
  `pbhi_max_employee` int(10) unsigned default NULL,
  `pbhi_pb_status` int(10) unsigned NOT NULL default '0',
  `pbhi_valid_from` date NOT NULL default '0000-00-00',
  `pbhi_valid_to` date default NULL,
  PRIMARY KEY  (`pbhi_id`),
  KEY `FK_pbhi_pb_id` (`pbhi_pb_id`),
  KEY `FK_pbhi_dept_id` (`pbhi_dept_id`),
  CONSTRAINT `FK_pbhi_dept_id` FOREIGN KEY (`pbhi_dept_id`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_pbhi_pb_id` FOREIGN KEY (`pbhi_pb_id`) REFERENCES `positionbase` (`pb_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `positionbasehist`
--

LOCK TABLES `positionbasehist` WRITE;
/*!40000 ALTER TABLE `positionbasehist` DISABLE KEYS */;
/*!40000 ALTER TABLE `positionbasehist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruitapplier`
--

DROP TABLE IF EXISTS `recruitapplier`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `recruitapplier` (
  `reca_id` varchar(36) NOT NULL default '',
  `reca_plan_id` varchar(36) NOT NULL default '',
  `reca_channel_no` varchar(36) NOT NULL default '',
  `reca_refer_emp_no` varchar(36) default NULL,
  `reca_name` varchar(32) NOT NULL default '',
  `reca_phone` varchar(16) NOT NULL default '',
  `reca_email` varchar(64) default NULL,
  `reca_diploma` varchar(16) default NULL,
  `reca_emp_type` varchar(36) default NULL,
  `reca_interview_time` datetime default NULL,
  `reca_interviewer` varchar(32) default NULL,
  `reca_attachment_name` varchar(255) default NULL,
  `reca_status` int(10) unsigned NOT NULL default '0',
  `reca_comment` varchar(255) default NULL,
  `reca_create_by` varchar(36) NOT NULL default '',
  `reca_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `reca_last_change_by` varchar(36) NOT NULL default '',
  `reca_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`reca_id`),
  KEY `FK_reca_plan_id` (`reca_plan_id`),
  KEY `FK_reca_channel_no` (`reca_channel_no`),
  KEY `FK_reca_refer_emp_no` (`reca_refer_emp_no`),
  KEY `FK_reca_emp_type` (`reca_emp_type`),
  KEY `FK_reca_create_by` (`reca_create_by`),
  KEY `FK_reca_last_change_by` (`reca_last_change_by`),
  CONSTRAINT `FK_reca_channel_no` FOREIGN KEY (`reca_channel_no`) REFERENCES `recruitchannel` (`recch_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_reca_emp_type` FOREIGN KEY (`reca_emp_type`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_reca_plan_id` FOREIGN KEY (`reca_plan_id`) REFERENCES `recruitplan` (`recp_id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_reca_refer_emp_no` FOREIGN KEY (`reca_refer_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `recruitapplier`
--

LOCK TABLES `recruitapplier` WRITE;
/*!40000 ALTER TABLE `recruitapplier` DISABLE KEYS */;
/*!40000 ALTER TABLE `recruitapplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruitchannel`
--

DROP TABLE IF EXISTS `recruitchannel`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `recruitchannel` (
  `recch_no` varchar(36) NOT NULL default '',
  `recch_name` varchar(64) NOT NULL default '',
  `recch_resp_person` varchar(32) NOT NULL default '',
  `recch_phone` varchar(32) default NULL,
  `recch_email` varchar(64) default NULL,
  `recch_city_no` varchar(16) default NULL,
  `recch_addr` varchar(255) default NULL,
  `recch_addr_zip` varchar(6) default NULL,
  `recch_comments` varchar(255) default NULL,
  PRIMARY KEY  (`recch_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `recruitchannel`
--

LOCK TABLES `recruitchannel` WRITE;
/*!40000 ALTER TABLE `recruitchannel` DISABLE KEYS */;
/*!40000 ALTER TABLE `recruitchannel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruitplan`
--

DROP TABLE IF EXISTS `recruitplan`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `recruitplan` (
  `recp_id` varchar(36) NOT NULL default '',
  `recp_no` int(10) unsigned NOT NULL default '0',
  `recp_job_title` varchar(36) NOT NULL default '',
  `recp_job_desc` varchar(500) default NULL,
  `recp_type` varchar(36) NOT NULL default '',
  `recp_department_no` varchar(36) NOT NULL default '',
  `recp_work_location` varchar(36) NOT NULL default '',
  `recp_start_date` date NOT NULL default '0000-00-00',
  `recp_end_date` date default NULL,
  `recp_number_expect` int(10) unsigned NOT NULL default '0',
  `recp_degree` varchar(64) default NULL,
  `recp_language_skill` varchar(64) default NULL,
  `recp_job_skill` varchar(255) default NULL,
  `recp_comments` varchar(255) default NULL,
  `recp_next_approver` varchar(36) default NULL,
  `recp_status` int(10) unsigned NOT NULL default '0',
  `recp_need_gm_approve` int(10) unsigned default NULL,
  `recp_create_by` varchar(36) NOT NULL default '',
  `recp_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `recp_last_change_by` varchar(36) NOT NULL default '',
  `recp_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`recp_id`),
  KEY `FK_recp_type` (`recp_type`),
  KEY `FK_recp_department_no` (`recp_department_no`),
  KEY `FK_recp_job_title` (`recp_job_title`),
  KEY `FK_recp_work_location` (`recp_work_location`),
  KEY `FK_recp_create_by` (`recp_create_by`),
  KEY `FK_recp_last_change_by` (`recp_last_change_by`),
  CONSTRAINT `FK_recp_department_no` FOREIGN KEY (`recp_department_no`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_recp_job_title` FOREIGN KEY (`recp_job_title`) REFERENCES `jobtitle` (`jobtitle_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_recp_type` FOREIGN KEY (`recp_type`) REFERENCES `emptype` (`emptype_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_recp_work_location` FOREIGN KEY (`recp_work_location`) REFERENCES `location` (`location_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `recruitplan`
--

LOCK TABLES `recruitplan` WRITE;
/*!40000 ALTER TABLE `recruitplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `recruitplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportdef`
--

DROP TABLE IF EXISTS `reportdef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportdef` (
  `report_id` varchar(36) NOT NULL default '',
  `report_name` varchar(64) NOT NULL default '',
  `report_desc` varchar(255) default NULL,
  `report_module` int(10) unsigned NOT NULL default '0',
  `report_main_table` varchar(64) default NULL,
  `report_file` varchar(128) NOT NULL default '',
  `report_authority` varchar(64) NOT NULL default '',
  `report_display_mode` int(10) unsigned NOT NULL default '0',
  `report_chart_mode` varchar(32) default NULL,
  `report_chart_dimension` int(10) unsigned default NULL,
  `report_chart_type` varchar(32) default NULL,
  `report_chart_title` varchar(64) default NULL,
  `report_type` int(10) unsigned default NULL,
  `report_url` int(10) unsigned default NULL,
  `report_background` varchar(16) default NULL,
  `report_xseries` varchar(64) default NULL,
  `report_yseries` varchar(64) default NULL,
  `report_fontcolor` varchar(16) default NULL,
  `report_optionalyseries` varchar(64) default NULL,
  `report_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `report_create_by` varchar(36) default NULL,
  `report_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `report_last_change_by` varchar(36) default NULL,
  PRIMARY KEY  (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportdef`
--

LOCK TABLES `reportdef` WRITE;
/*!40000 ALTER TABLE `reportdef` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportdef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportgroups`
--

DROP TABLE IF EXISTS `reportgroups`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportgroups` (
  `reportgroups_id` varchar(36) NOT NULL default '',
  `reportgroups_rd_id` varchar(36) NOT NULL default '',
  `reportgroups_rsd_id` varchar(36) NOT NULL default '',
  `reportgroups_function` varchar(8) default NULL,
  `reportgroups_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`reportgroups_id`),
  KEY `FK_reportgroups_rd_id` (`reportgroups_rd_id`),
  KEY `FK_reportgroups_rsd_id` (`reportgroups_rsd_id`),
  CONSTRAINT `FK_reportgroups_rd_id` FOREIGN KEY (`reportgroups_rd_id`) REFERENCES `reportdef` (`report_id`),
  CONSTRAINT `FK_reportgroups_rsd_id` FOREIGN KEY (`reportgroups_rsd_id`) REFERENCES `reportsetsdef` (`reportsetsdef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportgroups`
--

LOCK TABLES `reportgroups` WRITE;
/*!40000 ALTER TABLE `reportgroups` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportgroups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportjoindef`
--

DROP TABLE IF EXISTS `reportjoindef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportjoindef` (
  `reportjoindef_id` varchar(36) NOT NULL default '',
  `reportjoindef_main_table` varchar(64) NOT NULL default '',
  `reportjoindef_assistant_table` varchar(64) NOT NULL default '',
  `reportjoindef_main_join` varchar(64) NOT NULL default '',
  `reportjoindef_assistant_join` varchar(64) NOT NULL default '',
  PRIMARY KEY  (`reportjoindef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportjoindef`
--

LOCK TABLES `reportjoindef` WRITE;
/*!40000 ALTER TABLE `reportjoindef` DISABLE KEYS */;
INSERT INTO `reportjoindef` VALUES ('076b09ed7252489ea7501b,(e2abfbe','leaverequest','leavetype','lr_lt_no','lt_no'),('07a93fcfcb524131a2464e5dde4017f5','employee','groups','emp_group','group_no'),('07fdc5f5dfe34f028e5038eecbe94555','leavebalance','employee','lb_emp_no','emp_no'),('105fbd272ef645c2b218a6c4acc94813','empeval','businessunit','ee_bu_no','business_no'),('113f8e661f8a4e9eb46839cf8605b568','recruitapplier','recruitplan','reca_plan_id','recp_id'),('173f8f68b77c4edbb4052a5878afb37b','employee','employee','emp_sup_no','emp_no'),('17c60c72ab5c4dc3a545c4d7145ff79a','recruitplan','department','recp_department_no','department_no'),('1aea217c015c40da846c3a90265f2b95','recruitplan','emptype','recp_type','emptype_no'),('1d6099633dd54df5be0169a7290cb68e','empquit','employee','eq_emp_no','emp_no'),('1f92e87421c146fe9aec9e7dd5bb3d99','employee','location','emp_location_no','location_no'),('249546bab93b4bef95992dd00cd3fead','employee','jobtitle','emp_job_title','jobtitle_no'),('35d02fcfd68c45fdb76d0fd4c0476c5f','empeval','employee','ee_emp_no','emp_no'),('3de40372901a42a1a7b38f93d5a5c612','recruitplan','location','recp_work_location','location_no'),('41865c237f0a4c56b3f107e4d385c528','tremployeeplan','jobtitle','trp_trainee_job_title','jobtitle_no'),('44f28c1c1d364e2f8aa8966de178202e','employee','businessunit','emp_bu_no','business_no'),('4bc796710848497090bc94daa934f5c0','attenddaily','attendshift','attd_shift','atts_id'),('5cb525fd4ef94e048a437df73cd80de7','overtimerequest','overtimetype','or_ot_no','ot_no'),('65fdbbf06fbe4ba291df34b2e05e11cc','attenddaily','leavetype','attd_overtime_type','lt_no'),('661ed63ed3b0478b82c4d48549fc6a76','attendmonthly','employee','attm_emp_id','emp_no'),('663f0044813c449bb87aeccaffbfdfce','leaverequest','employee','lr_emp_no','emp_no'),('67e7e56b33e142c395922d91acee7ecd','empeval','employee','ee_manager','emp_no'),('7ad5cca921284ff6bf2ca012b37335e0','trcourseplan','trcourse','trcp_course_no','trc_no'),('851df9e32dd640a4913ac8b9002da1e3','overtimerequest','department','or_emp_dept','department_no'),('8e8b90d0418340b58cd79480fea167ee','attenddaily','overtimetype','attd_leave_type','ot_no'),('8f024b985ea64b128b06359b1b1)5d','empeval','department','ee_dept_no','department_no'),('97b1a55b220c4b97893558aa0bd26a10','trcourse','trtype','trc_type','trt_no'),('9b76780b860841e492035d8c74dc46c8','attenddaily','employee','attd_emp_id','emp_no'),('9bcbb1f10fb84b3098e6f4ce3150587d','overtimerequest','location','or_emp_location','location_no'),('9f74421a257142829f494adb164c9e66','leaverequest','location','lr_emp_location','location_no'),('9fa4a3cd14e84dc985c3e309ac0ba7e6','empcontract','ecttype','ect_type_no','ecttype_no'),('a0f63cb3d2d245c79ca585c55d01e974','recruitplan','jobtitle','recp_job_title','jobtitle_no'),('a458d5bf50274cd5bf3ec63f35469e74','empcontract','employee','ect_emp_no','emp_no'),('abf461e64b2a4c288d076f85a10a136f','tremployeeplan','employee','trp_trainee_no','emp_no'),('ae4d374a23a5405995a3bf5c37c64a21','recruitapplier','employee','reca_refer_emp_no','emp_no'),('b2dc4b2f39344ae3963368699240dd3f','overtimerequest','employee','or_emp_no','emp_no'),('bc4767a0-30a6-4f99-84bd-065774a012e5','employee','benefittype','emp_benefittype','benefittype_id'),('c14777619b6f4cd49d706eb63fdd3383','tremployeeplan','trcourseplan','trp_trcp_id','trcp_id'),('c70063337c07486db545dda7f3820f10','recruitapplier','recruitchannel','reca_channel_no','recch_no'),('c7456543a89b4d0b9ae8b52289ef3985','tremployeeplan','department','trp_trainee_dept','department_no'),('c7c090f32bfb4d30a3ea78cdbf2550f0','leaverequest','department','lr_emp_dept','department_no'),('ca7c8e2efa184f4180aa893804cd1063','trcourseplan','employee','trcp_coordinator','emp_no'),('cc0c775c8e084e539eb9e8b625176496','empsalaryconfig','employee','esc_empno','emp_no'),('ce7986468b79412d9a190bd95e09c82a','userinfo','employee','ui_emp_no','emp_no'),('e1d72d0e92274ae0ad6d8e1ab22e991f','empeval','jobtitle','ee_job_title','jobtitle_no'),('eef84a1155ad44db8759be67809c9aee','employee','emptype','emp_type','emptype_no'),('ffae0f7aab40468c97ad73c7eb81cf04','employee','department','emp_dept_no','department_no');
/*!40000 ALTER TABLE `reportjoindef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportmoduledef`
--

DROP TABLE IF EXISTS `reportmoduledef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportmoduledef` (
  `reportmoduledef_id` varchar(36) NOT NULL default '',
  `reportmoduledef_module` int(10) unsigned NOT NULL default '0',
  `reportmoduledef_flag` int(10) unsigned NOT NULL default '0',
  `reportmoduledef_table` varchar(64) NOT NULL default '',
  `reportmoduledef_table_desc` varchar(64) NOT NULL default '',
  PRIMARY KEY  (`reportmoduledef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportmoduledef`
--

LOCK TABLES `reportmoduledef` WRITE;
/*!40000 ALTER TABLE `reportmoduledef` DISABLE KEYS */;
INSERT INTO `reportmoduledef` VALUES ('06b650f5ebc94d66910e734a73d77f08',6,1,'recruitapplier','应聘人才库'),('09599c6c3ad84c2a92dab8565f347350',2,0,'businessunit','业务单元'),('0ed08baabf3640c38774e00b7fe22d43',6,0,'location','工作地点'),('126abe284eb1422db72ff0a011e16cac',2,0,'emptype','用工形式'),('1a7aba4de05a41e98ba742f75134954a',6,1,'recruitplan','招聘计划'),('1b43425e78b34fd3864824a793c14412',4,0,'leavetype','请假种类'),('1cfa1a48138f4dad817a33cf03999c96',4,1,'leavebalance','员工年假余额'),('1f8c54862cff473db317e78217c511ce',1,0,'ecttype','合同种类'),('2406f5f225114068a3827c0fd9494c07',4,1,'overtimerequest','加班申请'),('2ff554f4420d4a2583cd124e23df4660',6,0,'department','部门'),('381c789ef67d409abf69e7b1bf354a87',4,0,'attendshift','考勤班次'),('390d8e546dad4376aa117aa908fb8456',1,1,'empquit','离职记录'),('3f2eef16442f4bc7b0eaae3da9151b1a',6,0,'jobtitle','工作岗位'),('4b4732d72f004cffaa4cf9ae7e28a4f1',4,1,'leaverequest','请假申请'),('4b6786e4b6fc43ad8897cb5f67aea038',4,1,'attenddaily','每日考勤'),('4d1d762d0151474c9a4590355f23d09d',1,0,'groups','工作小组'),('503cbbfa4ac44c79aafe08c4a4c9e0ed',9,0,'employee','员工'),('5319a4cb8c524e2b8ff7509ff6dfefbd',1,1,'employee','员工'),('580f804dc8e94598956689a3a5f893b3',4,0,'overtimetype','加班种类'),('58a86b48b9904a72824120ca8faf973b',3,1,'trcourse','培训课程'),('5c24897e222f4e6e9937cc23e4f8ab1d',3,0,'jobtitle','工作岗位'),('70b87af6f87a46a48d3ea9622d38f03a',1,0,'location','工作地点'),('72ea7c766334440f9c0fe0aba203b312',9,1,'userinfo','用户表'),('7567ac479acb4950afcc709a5b9fc4f7',4,0,'department','部门'),('780d8e5e678c42158f38113a886714ee',1,1,'empcontract','人事合同'),('7b6dbeccbca84d7aaa87c2d7e2317dcf',3,1,'tremployeeplan','员工培训计划'),('832b0eac2d464a22ab03e66ba19a6987',1,0,'department','部门'),('8e2717b8b94040f0a1061faba12e875c',2,0,'location','工作地点'),('91e3916bf5f3406fb1e23bf50bd9dd35',3,1,'trcourseplan','培训课程计划'),('93b73a03e0c942c2ad9bf6bc7baefd70',2,0,'jobgrade','薪资级别'),('9771b060e03147d8b56a36b3edc363f8',4,0,'employee','员工'),('a5201d67-4f4f-4bef-b6c4-417471d6a18b',1,0,'benefittype','社保种类'),('abd0f200719d4c63836c5161dc1be227',4,1,'attendmonthly','每月考勤'),('b13ef3bd71c3404e9379bfc45ec3f5e3',1,1,'empeval','考评记录'),('ba9319f70d4f4877811cbd833f789e32',6,0,'recruitchannel','招聘渠道'),('bdea4cfc1ce747eb9f4804fdf365367a',2,0,'groups','工作小组'),('c1da61d809f743eaa84d5f476d6aadfb',1,0,'emptype','用工形式'),('c418edf5f66a4cd4abc2e67b2e2ccfc8',3,0,'department','部门'),('c7871dff46f04802b85f59ac5e01478d',2,0,'jobtitle','工作岗位'),('ca2d96eaaa66401ab7b29cd37cc42e24',1,0,'jobtitle','工作岗位'),('cff540224b5e4389a4624fbce781e5b7',3,0,'trtype','培训种类'),('d978215a83184727b47a4c4a59c2694e',6,0,'employee','员工'),('dba28e213cd7474f9a4b0194e3ad2217',2,1,'empsalaryconfig','薪资配置表'),('dbe2c7b30cd745179d281315fb837e2b',2,0,'empsalarydatadef','薪资项目表'),('e319ba0d8d0d4f8da7e9602fa63e9934',6,0,'emptype','用工形式'),('e4cef90644954e45a0ae1d5688fbee23',2,0,'department','部门'),('e7f9a903f1bd465f8bcd8eb26e00f0da',4,0,'location','工作地点'),('f7e1c6187b5e4f7cac29e279d09684de',2,0,'employee','员工'),('fd17e9cd746f4243b95399267a965308',1,0,'businessunit','业务单元'),('fdf61810de1e4825825e8c1d5c0e7f93',3,0,'employee','员工');
/*!40000 ALTER TABLE `reportmoduledef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportparams`
--

DROP TABLE IF EXISTS `reportparams`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportparams` (
  `reportparams_id` varchar(36) NOT NULL default '',
  `reportparams_rd_id` varchar(36) NOT NULL default '',
  `reportparams_rsd_id` varchar(36) NOT NULL default '',
  `reportparams_condition` varchar(8) NOT NULL default '',
  `reportparams_range_low` varchar(255) default NULL,
  `reportparams_range_high` varchar(36) default NULL,
  `reportparams_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`reportparams_id`),
  KEY `FK_reportparams_rd_id` (`reportparams_rd_id`),
  KEY `FK_reportparams_rsd_id` (`reportparams_rsd_id`),
  CONSTRAINT `FK_reportparams_rd_id` FOREIGN KEY (`reportparams_rd_id`) REFERENCES `reportdef` (`report_id`),
  CONSTRAINT `FK_reportparams_rsd_id` FOREIGN KEY (`reportparams_rsd_id`) REFERENCES `reportsetsdef` (`reportsetsdef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportparams`
--

LOCK TABLES `reportparams` WRITE;
/*!40000 ALTER TABLE `reportparams` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportparams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportsets`
--

DROP TABLE IF EXISTS `reportsets`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportsets` (
  `reportsets_id` varchar(36) NOT NULL default '',
  `reportsets_rd_id` varchar(36) NOT NULL default '',
  `reportsets_rsd_id` varchar(36) NOT NULL default '',
  `reportsets_desc` varchar(64) NOT NULL default '',
  `reportsets_function` varchar(32) default NULL,
  `reportsets_group_function` varchar(16) default NULL,
  `reportsets_display_length` int(10) unsigned default NULL,
  `reportsets_sort_id` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`reportsets_id`),
  KEY `FK_reportsets_rd_id` (`reportsets_rd_id`),
  KEY `FK_reportsets_rsd_id` (`reportsets_rsd_id`),
  CONSTRAINT `FK_reportsets_rd_id` FOREIGN KEY (`reportsets_rd_id`) REFERENCES `reportdef` (`report_id`),
  CONSTRAINT `FK_reportsets_rsd_id` FOREIGN KEY (`reportsets_rsd_id`) REFERENCES `reportsetsdef` (`reportsetsdef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportsets`
--

LOCK TABLES `reportsets` WRITE;
/*!40000 ALTER TABLE `reportsets` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportsets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportsetsdef`
--

DROP TABLE IF EXISTS `reportsetsdef`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `reportsetsdef` (
  `reportsetsdef_id` varchar(36) NOT NULL default '',
  `reportsetsdef_table` varchar(64) NOT NULL default '',
  `reportsetsdef_field` varchar(64) NOT NULL default '',
  `reportsetsdef_desc` varchar(64) NOT NULL default '',
  `reportsetsdef_type` varchar(8) NOT NULL default '',
  `reportsetsdef_params_type` varchar(16) default NULL,
  `reportsetsdef_params_length` int(10) unsigned default NULL,
  PRIMARY KEY  (`reportsetsdef_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `reportsetsdef`
--

LOCK TABLES `reportsetsdef` WRITE;
/*!40000 ALTER TABLE `reportsetsdef` DISABLE KEYS */;
INSERT INTO `reportsetsdef` VALUES ('002e36c6-8c6a-47ec-afc0-bb40da6956c1','trcourseplan','trcp_attendee_no','培训报名人数限制','number',NULL,NULL),('002e36c6-8c6a-47ec-afc0-bb40da6956cx','trtype','trt_name','培训类型名称','text','trtype',NULL),('01805fb66c9f4aa4b0a410cf1df20560','empsalarydatadef','esdd_data_type','项目种类','number','esddDataType',NULL),('028fc171-0b07-4d99-a104-705f607dae7e','userinfo','ui_login_ip','最新登陆IP','text',NULL,NULL),('071c89dc-e420-4528-8854-17217e4aba76','emptype','emptype_name','用工形式名称','text','emptype',NULL),('08dc4e31-4186-4a40-b57b-1079b4011a77','userinfo','ui_login_time','最新登陆时间','date',NULL,NULL),('0a877108-b8a9-416a-82f2-ca398f159f49','empadditional','eadl_field04','用户自字义字段4','text',NULL,NULL),('0cb72eb824e64f97bd37abc8042854db','benefittype','benefittype_name','社保种类名称','text','benefittype',NULL),('0cf2f2ee2b314f98a8f71c7d6ce1f05f','attenddaily','attd_field02','用户自定义字段2','text',NULL,NULL),('0d2f4c3e-4452-4e0c-a2ba-2b12335a651a','empadditional','eadl_field03','用户自字义字段3','text',NULL,NULL),('0fe90293-0b7a-448a-a368-6f5bec15ac2c','empsalaryconfig','esc_last_incr_rate1','税前收入上次加薪%','number',NULL,NULL),('0ff7bb0c1602447892cf616306809e36','attendmonthly','attm_leave_days','本月请假天数','number',NULL,NULL),('1189d78c-dfed-4450-9543-059d763bfc67','employee','emp_name','员工姓名','text',NULL,NULL),('11ef3b38c9ff4397847fb75102bf9428','attendmonthly','attm_field03','用户自定义字段3','text',NULL,NULL),('122a563efccc4154ac2dc10510ecd146','employee','emp_mobile','手机','text',NULL,NULL),('147f2351b0994d8eb27ae0dbf55b8b81','attendmonthly','attm_leave_maternity_days','产假天数','number',NULL,NULL),('155a9b01-1ba3-4142-8716-711705546dac','empadditional','eadl_field12','用户自字义字段12','text',NULL,NULL),('1693575f1b80471db53ae90a376359e6','empeval','ee_type','考评类型','text','eeType',NULL),('18fab50b-2a80-4b01-ae87-2033231c92e8','employee','emp_distinct_no','员工公司内部编号','text',NULL,NULL),('199014cf70704005bb5a2ee18ef8be77','empeval','ee_start_date','起始日期','date',NULL,NULL),('19eff0f2-bc63-441b-811f-2d8d04f31558','employee','emp_city_no','籍贯','text','empCityNo',NULL),('1bd09aa1ec6e4e4387c28611a37f1395','attendmonthly','attm_leave_casual_days','事假天数','number',NULL,NULL),('1c304d8b-4517-4f46-82ee-afe4000a4853','employee','emp_diploma','学历','text','diploma',NULL),('20917c6d3ce14136b6e9635ba5c04b3c','attendmonthly','attm_status','月考勤状态','number','attmStatus',NULL),('20ec4768-4618-4b68-b1b5-69faaf7b7644','jobgrade','jobgrade_no','薪资级别编号','text','jobgrade',NULL),('220fecb31d2f45c6a042a900f0ecf459','attenddaily','attd_overtime_hours','加班(小时)','number',NULL,NULL),('22facd1f61944139bbb43b2ef1b8b8ed','empquit','eq_date','离职日期','date',NULL,NULL),('2376425ed89a417a8848b22b582cc988','leavebalance','lb_days_of_year','今年年假额度','number',NULL,NULL),('23d76a49-7b3f-4b2f-9391-c81368a7000a','jobtitle','jobtitle_name','岗位名称','text','jobtitle',NULL),('249ea93f-177c-497a-9b30-7c22b0e771a6','recruitchannel','recch_resp_person','渠道负责人','text',NULL,NULL),('2ba97bde-d7b6-4e04-b0db-afff6f6ecad3','employee','emp_perf_date','最近考评日期','date',NULL,NULL),('2bf5d30d116e4d509ea91e1488c6b86f','attendmonthly','attm_ot_normal_hours','普通加班小时数','number',NULL,NULL),('2bfb78bb43174ecda466d212aa1a9d65','attendmonthly','attm_card_id','员工考勤卡号','text',NULL,NULL),('2c1243f13d934ffbb198ea0b70660c7b','employee','emp_urgent_con_method','紧急联系方式','text',NULL,NULL),('30cd73b6192b46c1b19ca44649675d10','attendmonthly','attm_on_duty','本月实际出勤天数','number',NULL,NULL),('3150a051-39ab-4b6e-9860-ab906a24454c','recruitplan','recp_work_location','工作地点','text',NULL,NULL),('31ab8bf16e924f4c96e8a3835d9753e9','empcontract','ect_no','合同编号','text',NULL,NULL),('3267efe7837c44209b606563965dd280','employee','emp_work_phone','公司联系电话','text',NULL,NULL),('331ede5b-1edd-4fd3-81ee-5fe9b7f3b37f','recruitapplier','reca_diploma','应聘者学历','text','diploma',NULL),('34542da6-6e32-409c-b37e-44eaa82f8002','tremployeeplan','trp_status','状态','number','trpStatus',NULL),('392d7829-1cbc-4fe2-a53a-88cfebabf8d1','trcourseplan','trcp_budget_hour','培训预算时间','number',NULL,NULL),('397af80a-374a-4fbd-8e88-902e34eb7eeb','trcourseplan','trcp_end_date','培训结束日期','date',NULL,NULL),('39d8dfc37b1643d7a9d4d947601e5ee9','employee','emp_urgent_contact','紧急联系人','text',NULL,NULL),('39eceb41842746ca892b21300995bfd7','attendmonthly','attm_ot_weekend_hours','周末加班小时数','number',NULL,NULL),('39f985b9-715e-4d80-9be8-85468df484d4','employee','emp_confirm_date','转正日期','date',NULL,NULL),('3a807460-c72b-4790-a193-edbc28218e84','department','department_name','部门名称','text','department',NULL),('3aaa344d6275426eb3eee0a558007b58','attendmonthly','attm_ot_holiday_hours','节假日加班小时数','number',NULL,NULL),('3d28ea254da247b48bef5af1c1eef929','empeval','ee_rate','考评结果','text',NULL,NULL),('411395a0-39b6-4879-be95-57f36b10e4ae','empsalaryconfig','esc_last_incr_rate','基本工资上次加薪%','number',NULL,NULL),('423357c7109b4816b7bd3fde7dd3f863','empsalarydatadef','esdd_name','项目名称','text',NULL,NULL),('45439d49-cb8a-4632-9351-b71fafa0df8b','userinfo','ui_status','用户状态','number','uiStatus',NULL),('4621dfd6213f4abea9c45757405ae72e','attenddaily','attd_field08','用户自定义字段8','text',NULL,NULL),('48d61c1e-c840-4d62-be64-26a42c148921','recruitplan','recp_status','计划状态','number','recpStatus',NULL),('4951ed3a3f1d47678a43d79fec1e6885','attenddaily','attd_on_duty_time','员工上班时间','datetime',NULL,NULL),('4ae167169bad4fcfb33d5568e1e1ee37','employee','emp_identification_type','证件种类','text','empIDType',NULL),('4b46ae42-32be-4de3-b9d2-d0be8bfdfa0f','groups','group_name','工作小组名称','text','groups',NULL),('50347ee94b644e56a010697d59e862fc','recruitchannel','recch_addr_zip','渠道地址邮政编码','text',NULL,NULL),('5086a28dd8964fe19bdbbcbd66a25298','attendmonthly','attm_field05','用户自定义字段5','text',NULL,NULL),('50bab968-2b48-42ea-b2a6-a32f3836c5a1','trcourseplan','trcp_institution','培训机构','text',NULL,NULL),('52b29acd56da4e88b138a8e49f654ee7','employee','emp_home_phone','家庭联系电话','text',NULL,NULL),('5317a55feeb0432e99b9c2ae1f68883b','empquit','eq_type','离职种类','number','eqType',NULL),('54616d67-b4b2-4216-9cca-d4dddf7ce41d','recruitapplier','reca_status','状态','number','recaStatus',NULL),('54937d99976b4b828bf35bf496021b3c','ecttype','ecttype_name','合同种类名称','text','ecttype',NULL),('54de4655-eff3-4d88-ae2f-8c3b402ef1ed','jobgrade','jobgrade_level','薪资级别等级','number',NULL,NULL),('56042a584ceb42888e7319a01f014a7c','attenddaily','attd_is_absent','旷工(小时)','number',NULL,NULL),('58e58ef8cce44169aeddcc1de08d14ea','attenddaily','attd_field07','用户自定义字段7','text',NULL,NULL),('5a09bc3d202e4a75a9b6ba29b499413f','attenddaily','attd_field03','用户自定义字段3','text',NULL,NULL),('5c33b849-7abf-40d9-9812-b5c8ad498a62','trcourseplan','trcp_teacher','培训老师','text',NULL,NULL),('5dca3838-ee4d-4e6d-bc8e-e18bafe1207d','overtimerequest','or_end_date','结束日期','datetime',NULL,NULL),('5dd8de79-95e4-40d2-93ea-0fbafaeffb76','employee','emp_join_date','入职日期','date',NULL,NULL),('5fe40427-77f7-42e9-a173-ff42458fc3eb','employee','emp_school','毕业院校','text',NULL,NULL),('6153e77f-59d4-4539-9fb3-d05f7617d83f','leaverequest','lr_total_days','本次请假总日期','number',NULL,NULL),('6364f3f1ba9046ea88c6da3bc3ab0f89','recruitchannel','recch_email','负责人电子邮件','text',NULL,NULL),('65cea506b25d4ba1854613e447dd362e','employee','emp_email','员工email','text',NULL,NULL),('69a2356f-97e4-48a6-88d4-2011be3b84ed','empsalaryconfig','esc_last_eff_date','上次加薪生效日','date',NULL,NULL),('6a508eac-588a-4124-b1df-fc05fc6ed18c','empadditional','eadl_field16','用户自字义字段16','text',NULL,NULL),('6d1418b2-2a43-4c92-a40d-d072b4178a37','tremployeeplan','trp_comments','培训反馈','text',NULL,NULL),('6ec8909a-a9e2-4af1-8e98-5d6db2c07d5a','empadditional','eadl_field10','用户自字义字段10','text',NULL,NULL),('6ecb411518a54c71937ba1fc9f39cc5b','attenddaily','attd_duty_date','考勤日期','date',NULL,NULL),('6edb19b903334f21a90c12ed8884cd33','attendmonthly','attm_leave_travel_days','出差天数','number',NULL,NULL),('6f3747e1-1809-4f85-8a9a-e0cde127f284','empadditional','eadl_field05','用户自字义字段5','text',NULL,NULL),('7094354e-98a8-42ad-b05c-2f632dccd4b8','employee','emp_terminate_status','员工','number','empTermStatus',NULL),('7094354e-98a8-42ad-b05c-2f632dccd4b9','employee','emp_status','员工状态','number','empStatus',NULL),('710c12819fda4ec38e03edd0caab8c35','attenddaily','attd_card_id','员工考勤卡号','text',NULL,NULL),('71eb57b972d34aab90337db54c34e397','leaverequest','lr_no','请假申请编号','number',NULL,NULL),('74fdeb17-2793-49c7-839b-99eee8a41d25','empadditional','eadl_field08','用户自字义字段8','text',NULL,NULL),('763014db-fec3-448f-b3b5-d192d9e089dc','trcourseplan','trcp_comments','培训备注','text',NULL,NULL),('7667c9bc-0563-4c57-a25c-6ac0f47365f2','trcourseplan','trcp_enroll_end_date','报名结束日期','date',NULL,NULL),('78415208-da07-48d2-9990-af894ebd4bdc','trcourseplan','trcp_start_date','培训起始日期','date',NULL,NULL),('78b7b4f8bd634394ba03a2f5027932b5','attendmonthly','attm_field08','用户自定义字段8','text',NULL,NULL),('79e1c227-549d-443b-a380-73851c085592','leaverequest','lr_start_date','请假起始日期','text',NULL,NULL),('7a018214f1fe4623a48192db8dda5f0c','jobgrade','jobgrade_name','薪资级别描述','text',NULL,NULL),('7c8d4532-d90b-4dbf-9d68-38a02f1e220b','location','location_name','办公地点名称','text','location',NULL),('7d33cfa181f34e0a97148c1b31c176c5','attendshift','atts_name','考勤班次名称','text','attendshift',NULL),('7fde51dc8fe540d5819663bc0d2a4031','attendmonthly','attm_late_times','本月迟到次数','number',NULL,NULL),('82002b64-b311-4ddd-a4b0-c8c0e8bcc8e2','businessunit','businessunit_name','业务单元名称','text','businessunit',NULL),('83a074e0ce0d4ed7b9e75e962749cfd0','empeval','ee_end_date','终止日期','date',NULL,NULL),('862bbbde-49be-401c-a9ec-3071c7bc9632','userinfo','ui_username','用户名','text',NULL,NULL),('8655e33272a94233b22c2cc5a962ade4','attendmonthly','attm_duty_days','本月应出勤天数','number',NULL,NULL),('86922385-d327-4994-9200-a043e1582e06','overtimerequest','or_start_date','起始日期','datetime',NULL,NULL),('870b3c48-54af-41a3-a04b-99787dd3715a','employee','emp_perf_rate','最近考评结果','text','empPerfRate',NULL),('886aadfc07cb443cb25fc676ad981f40','recruitchannel','recch_phone','负责人电话','text',NULL,NULL),('8adb22f0-7058-4d6e-ad05-ca7f4cef523a','trcourseplan','trcp_budget_year','培训预算年度','text','year',NULL),('8bd77561513a41e3a2396cd901bf52fd','leavebalance','lb_year','年假计算年份','date',NULL,NULL),('8c1ad413d7b845b9ab5f029662b0ac4b','attenddaily','attd_off_duty_time','员工下班时间','datetime',NULL,NULL),('8f0200c52bb74ed48747219577fd6882','attenddaily','attd_duty_hours','工时(小时)','number',NULL,NULL),('91492c1d8a944d438ec5d3efe2361108','attenddaily','attd_field05','用户自定义字段5','text',NULL,NULL),('94f153255aa94d39a873e6350ad48171','attenddaily','attd_field06','用户自定义字段6','text',NULL,NULL),('9527d61b-c2c4-47e7-9ccd-7e0de53c9438','employee','emp_blood','血型','text','empBlood',NULL),('954ba15d-cd7b-4079-a557-ab2aad72bcad','leavetype','lt_name','休假种类名称','text','leavetype',NULL),('95c98210-4273-4aef-8c92-2857660a8916','overtimetype','ot_name','加班种类名称','text','overtimetype',NULL),('9bb42102-e803-4ce1-8e42-baaac63f5c65','overtimerequest','or_status','状态','number','orStatus',NULL),('9d4c30fb9f014ed7a6142b4cebb82421','leavebalance','lb_bal_forward','去年年假余额','number',NULL,NULL),('9de5c00fca594e67a2c0bab6d81decdb','recruitplan','recp_no','招聘计划编号','number',NULL,NULL),('9f0ef6c5-14db-43d3-9803-4a3491c3d3f0','leaverequest','lr_status','状态','number','lrStatus',NULL),('9fa03eba7df849c49869455cdc4dfa09','attendmonthly','attm_leave_wedding_days','婚假天数','number',NULL,NULL),('a21c41ed-f850-40de-80a0-d91266f70b63','empadditional','eadl_field02','用户自字义字段2','text',NULL,NULL),('a56a2afd5f8045f4bc59480b72d444cc','attendmonthly','attm_year','年','text','year',NULL),('a6746d52bdec4565821d92a81eac5634','attendmonthly','attm_early_leave','本月早退次数','number',NULL,NULL),('a7c26767-5cf5-4ce5-b936-30e918b10708','trcourseplan','trcp_budget_fee','培训预算成本','number',NULL,NULL),('abac13efffd349809981f8095f16b7de','attendmonthly','attm_leave_sick_days','病假天数','number',NULL,NULL),('ac20de63-6863-48db-8faf-931920a6f5ff','trcourseplan','trcp_status','培训计划状态','text','trcpStatus',NULL),('ad224664143549438d07628be2f3f2b2','attendmonthly','attm_field07','用户自定义字段7','text',NULL,NULL),('ad50ca1a665b413e8ca2d7c4eaf5fba7','jobtitle','jobtitle_max_employee','岗位限定人数','number',NULL,NULL),('aff837873e1640b08e9b3a2d8dfbd470','attendmonthly','attm_field02','用户自定义字段2','text',NULL,NULL),('b057b366-0715-446c-b2eb-7df99d3f6bf3','recruitapplier','reca_email','应聘者电子邮件','text',NULL,NULL),('b1c93f9b31f34c7a851d3bd912596de3','attenddaily','attd_field04','用户自定义字段4','text',NULL,NULL),('b1da46b8-4c7c-4048-b0bf-3f2853ef2fcf','employee','emp_birth_date','生日','date',NULL,NULL),('b28fbdfc-58eb-4c5d-9f99-00ea2463494e','employee','emp_marital','婚否','number','empMarital',NULL),('b59e2dcb-517f-4961-95bb-c6f13b731a24','empadditional','eadl_field11','用户自字义字段11','text',NULL,NULL),('b5f3906970c64e7e8d81df67f4e23f0d','attenddaily','attd_late_hours','迟到(小时)','number',NULL,NULL),('b5ff52ca5a834c47a6bce41314b7968b','attenddaily','attd_leave_hours','请假(小时)','number',NULL,NULL),('b6926054dbbd40c3a7e4662fdb4bfd53','attenddaily','attd_early_leave','早退(小时)','number',NULL,NULL),('b73a936644bd4b33a07bd16aba16517d','empcontract','ect_status','合同状态','text','ectStatus',NULL),('b8ae6638c7694d42999ef76ae86ce0d1','attendmonthly','attm_off_duty','本月缺勤(旷工)天数','number',NULL,NULL),('b8f8b0d28e1e4f2bb23d07099dad3ebb','employee','emp_curr_zip','当前住址邮编','text',NULL,NULL),('b97536e9-235c-4f5b-bcd7-86a024d9cae8','employee','emp_work_date','参加工作日期','date',NULL,NULL),('ba9e2f5865884df6a34bb8d8b7a39ad0','attendmonthly','attm_field06','用户自定义字段6','text',NULL,NULL),('bdd658a5-1568-4ce9-b150-6a1c4de25a51','employee','emp_terminate_date','员工离职时间','date',NULL,NULL),('c05e5f75-0811-449d-992e-e2a4dd55b0eb','employee','emp_nation','民族','text','empNation',NULL),('c28dabb9-23ba-4480-9174-f21b5eb8a152','trcourseplan','trcp_location','培训地点','text',NULL,NULL),('c422e153-d2ba-43dc-8b3b-6d69a36cfdec','recruitplan','recp_degree','学历要求','text','diploma',NULL),('c6797ffffc3e467faf991c9c36025b4c','attendmonthly','attm_leave_other_days','其它请假天数','number',NULL,NULL),('c6aab067-3293-4220-8b79-850900eff8e4','empadditional','eadl_field07','用户自字义字段7','text',NULL,NULL),('c79c9087c0224b15a2615efd453eda79','empcontract','ect_end_date','合同结束日期','date',NULL,NULL),('c7c114dbe6ac43d790427a3633fc3e33','empcontract','ect_start_date','合同起始日期','date',NULL,NULL),('ca80a31a-2969-4158-9551-12f6d3324520','overtimerequest','or_total_hours','本次加班总小时数','number',NULL,NULL),('cb134a1c-213a-4b86-9949-d1d8dcf07d8c','trcourseplan','trcp_dept_limit','培训参加部门限制','text',NULL,NULL),('cd8d3fa3-4419-44a3-a54d-1cdcafabc2bb','employee','emp_gender','性别','number','empGender',NULL),('cfe7a194-c5f7-4c51-aa59-e55ef170cff6','userinfo','ui_last_login_time','前次登陆时间','date',NULL,NULL),('d47b7175-8e42-453d-8156-e4ebca416be8','overtimerequest','or_reason','加班理由','text',NULL,NULL),('d5ce7b91ce4446c09b9bd52acd9ea2a8','overtimerequest','or_no','加班申请编号','number',NULL,NULL),('d72b1eea-095d-4d26-a79a-300d62cb709e','recruitapplier','reca_phone','应聘者电话','text',NULL,NULL),('d8994670-f1d9-454e-bcbf-bcd03ff15455','recruitplan','recp_number_expect','计划招聘人数','number',NULL,NULL),('dc8a240c-66d7-4656-aca6-8c29a847f158','empadditional','eadl_field14','用户自字义字段14','text',NULL,NULL),('dd61ab37716a41bea51e4b4e3270c7a6','attendmonthly','attm_month','月','text','month',NULL),('df370c81-d611-4210-943a-4376738ddbc2','employee','emp_sup_no','所属经理员工号','text',NULL,NULL),('df68e966-2e5f-4e94-91ad-7da079414c3d','leaverequest','lr_reason','请假理由','text',NULL,NULL),('e126141b-8910-4957-a21e-5785943fe76c','empadditional','eadl_field13','用户自字义字段13','text',NULL,NULL),('e17d4d23-5835-43e8-a59b-06a60a9a4e8c','recruitplan','recp_language_skill','语言技能要求','text',NULL,NULL),('e1c646bb3fbb456784aad4dedbd4343c','attendmonthly','attm_leave_annual_days','年假天数','number',NULL,NULL),('e2ba5aa9-3fb8-4fd0-8da8-b81a50a66b3e','empadditional','eadl_field09','用户自字义字段9','text',NULL,NULL),('e3217108-e5f8-4608-ba58-fc82ee4bec37','empadditional','eadl_field15','用户自字义字段15','text',NULL,NULL),('e4c7617b-7011-4be3-a825-f3b1fda2e652','employee','emp_politics','政治面貌','text','empPolitics',NULL),('e549d9dcaad749fb809ec59a96231bb8','attendmonthly','attm_overtime_hours','本月加班小时数','number',NULL,NULL),('e59f3c8d-e6d1-4d57-a68d-947799ddfb4b','empadditional','eadl_field01','用户自字义字段1','text',NULL,NULL),('e5a0a503-0616-44c8-bc57-2e12fc67c841','trcourse','trc_status','培训课程状态','text','trcStatus',NULL),('ea41677c663d4177bca21d85e997a5af','leavebalance','lb_bal_end_date','去年余额最晚使用','date',NULL,NULL),('eac3522e-09e2-44be-a72e-ceb82ed2b112','recruitplan','recp_end_date','职位关闭日期','date',NULL,NULL),('eae33ec569ec406593a7d00491ae0425','attendmonthly','attm_field01','用户自定义字段1','text',NULL,NULL),('eb49d0dfc49d4723b884e8cf9fe6a3c0','attendmonthly','attm_field04','用户自定义字段4','text',NULL,NULL),('f1765df8-9711-4b6b-b1fb-0173b27d7120','trcourse','trc_name','培训课程名称','text',NULL,NULL),('f1fe1ab7-2eb2-4413-a4a8-37911210c5b6','recruitchannel','recch_name','渠道名称','text','recruitchannel',NULL),('f40884a7-1497-437f-8e10-83f4358094a3','leaverequest','lr_end_date','请假结束日期','text',NULL,NULL),('f43d69ea1df049f787c5e43acdb09d6a','employee','emp_identification_no','证件号码','text',NULL,NULL),('f452b200-c459-4098-8271-bb62bda709f6','empadditional','eadl_field06','用户自字义字段6','text',NULL,NULL),('f45f57b1-5265-46a6-9156-084d5cd1ac98','trcourseplan','trcp_enroll_start_date','报名起始日期','date',NULL,NULL),('f790ed15-d417-4cfe-b4b3-7e6fa17a0cf4','recruitapplier','reca_name','应聘者姓名','text',NULL,NULL),('f7c7c60ed3e745c3a70404e787b5ed9a','employee','emp_home_zip','家庭地址邮编','text',NULL,NULL),('f878164d196f4267b3938ea2af0d63e5','empquit','eq_reason','离职原因','text',NULL,NULL),('f8f476518c8c49079f7ce8d8154ce353','attenddaily','attd_status','日考勤状态','number','attdStatus',NULL),('f91bfac8-56ad-4388-91de-8ca886342f0b','recruitplan','recp_start_date','职位发布日期','date',NULL,NULL),('fb72239a153d4042accf6614ead9963a','attenddaily','attd_field01','用户自定义字段1','text',NULL,NULL),('fda91a09-b9cb-49c8-9d25-1f3b12a64438','employee','emp_speciality','专业','text','empSpeciality',NULL),('ffa2280cd4e842bf96dad51f78ce1b83','attendmonthly','attm_on_duty_hours','本月总工时数(小时)','number',NULL,NULL),('ffe30533afdb419d90b740607d0a5046','empcontract','ect_expire','合同期限','text','ectExpire',NULL);
/*!40000 ALTER TABLE `reportsetsdef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `role` (
  `role_id` varchar(36) NOT NULL default '',
  `role_no` int(10) unsigned NOT NULL default '0',
  `role_name` varchar(64) NOT NULL default '',
  `role_desc` varchar(128) default NULL,
  `role_sort_id` int(10) unsigned NOT NULL default '0',
  `role_authority` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('402880e618c6f9830118c723604c0002',13,'HR报表管理员','HR报表管理员（ 报表管理）',13,'61,62,63'),('402880e618c6f9830118c723604c0003',14,'HR新闻管理员','HR新闻管理员（新闻管理）',14,'71'),('402880e61950a28e011951ab913e0008',12,'HR考勤专员','HR考勤管理员（管理考勤）',9,'31,61,91'),('402880fd178062eb0117806b9a270002',1,'普通员工','普通员工（基础权限）',1,'23,33,72,13,3'),('402880fd178062eb0117806c303d0003',2,'部门调薪经理','调薪经理（能够提交并审批下属调薪计划）',5,'14'),('402880fd178062eb0117806cf8ec0004',3,'HR薪资专员','HR薪资管理员（管理薪资）',8,'11,16,17,61,91'),('402880fd178062eb0117806d99720005',4,'HR培训专员','HR培训管理员（培训管理）',10,'21,61,91'),('402880fd178062eb0117806e10860006',5,'HR经理','人力资源经理（基础权限）',4,'2,4,12,14,16,17,22,24,32,34,42,44,52,54,61,62,63,64,65,71,81,82,83,84,85,86,87,88,89,91,92'),('402880fd178062eb0117806eca7f0007',6,'HR系统管理员','HR系统管理员（系统管理）',12,'81,82,83,84,85,86,87,88,89,91,92'),('402880fd178062eb0117806f35dc0008',7,'部门经理','部门经理（基础权限）',2,'4,14,24,34,44,54,72'),('402880fd178062eb0117806f8bdb0009',8,'总经理','总经理（基础权限）',3,'5,15,25,35,45,55,73'),('402880fd178062eb0117806ff0b0000a',9,'部门招聘经理','招聘经理（能够提交本部门招聘计划）',6,'53'),('402880fd178062eb011780705b41000b',10,'HR招聘专员','HR招聘管理员（招聘管理）',11,'51,61,91'),('402880fd178062eb01178070cdc2000c',11,'HR员工管理员','HR员工管理员（管理首页新闻、员工）',7,'1,61,91');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statusconf`
--

DROP TABLE IF EXISTS `statusconf`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `statusconf` (
  `statusconf_tablename` varchar(32) NOT NULL default '',
  `statusconf_no` int(10) unsigned NOT NULL default '0',
  `statusconf_desc` varchar(128) default NULL,
  PRIMARY KEY  (`statusconf_tablename`,`statusconf_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `statusconf`
--

LOCK TABLES `statusconf` WRITE;
/*!40000 ALTER TABLE `statusconf` DISABLE KEYS */;
INSERT INTO `statusconf` VALUES ('emailsend',0,'已创建'),('emailsend',1,'已发送'),('emailsend',2,'发送出错'),('employee',0,'离职'),('employee',1,'在职'),('empprofile',1,'草稿（暂不使用！）'),('empprofile',11,'已提交（暂不使用！）'),('empprofile',21,'部门已审（暂不使用！）'),('empprofile',22,'部门已拒绝（暂不使用！）'),('empprofile',31,'HR已审（暂不使用！）'),('empprofile',32,'HR已拒绝（暂不使用！）'),('empprofile',41,'已作废（暂不使用！）'),('empquit',0,'辞职'),('empquit',1,'辞退'),('empquit',2,'退休'),('empquit',3,'合同到期'),('empquit',4,'停薪留职'),('empquit',5,'其他'),('empsalaryadj',11,'已提交'),('empsalaryadj',41,'已调整'),('leaverequest',2,'已提交'),('leaverequest',6,'经理已审'),('leaverequest',10,'组长已审'),('leaverequest',11,'主管已审'),('leaverequest',12,'部门已审'),('leaverequest',13,'地区经理已审'),('leaverequest',14,'总经理已审'),('leaverequest',15,'HR备案'),('leaverequest',16,'已销假'),('leaverequest',21,'已拒绝'),('leaverequest',22,'已作废'),('overtimerequest',2,'已提交'),('overtimerequest',6,'经理已审'),('overtimerequest',10,'组长已审'),('overtimerequest',11,'主管已审'),('overtimerequest',12,'部门已审'),('overtimerequest',13,'地区经理已审'),('overtimerequest',14,'总经理已审'),('overtimerequest',15,'HR备案'),('overtimerequest',16,'已确认'),('overtimerequest',21,'已拒绝'),('overtimerequest',22,'已作废'),('recruitapplier',1,'初选通过'),('recruitapplier',2,'笔试'),('recruitapplier',3,'面试'),('recruitapplier',4,'电话面试'),('recruitapplier',9,'不合格'),('recruitapplier',11,'待定候选人'),('recruitapplier',12,'已发录取通知'),('recruitapplier',13,'已录取'),('recruitapplier',19,'已拒绝'),('recruitapplier',21,'黑名单'),('recruitplan',1,'草稿'),('recruitplan',2,'已提交'),('recruitplan',11,'部门已审'),('recruitplan',12,'HR已审'),('recruitplan',21,'已拒绝'),('recruitplan',22,'已作废'),('recruitplan',23,'已中止'),('recruitplan',31,'已关闭'),('tremployeeplan',1,'草稿（废弃！！！）'),('tremployeeplan',2,'已提交'),('tremployeeplan',11,'部门已审'),('tremployeeplan',12,'HR已审'),('tremployeeplan',21,'已拒绝'),('tremployeeplan',22,'已作废'),('tremployeeplan',31,'已反馈');
/*!40000 ALTER TABLE `statusconf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sysconfig`
--

DROP TABLE IF EXISTS `sysconfig`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sysconfig` (
  `sysconfig_id` varchar(36) NOT NULL default '',
  `sysconfig_key` varchar(32) NOT NULL default '',
  `sysconfig_value` varchar(64) NOT NULL default '',
  PRIMARY KEY  (`sysconfig_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sysconfig`
--

LOCK TABLES `sysconfig` WRITE;
/*!40000 ALTER TABLE `sysconfig` DISABLE KEYS */;
INSERT INTO `sysconfig` VALUES ('1757eaef-ae71-4a67-86f9-161612a3d2ff','sys.examin.month.sum','1-1'),('1ab79b1f-5393-4ea3-99de-c8eca4add3ae','sys.database.version','V1.5.0,2010-04-08 14:23:00'),('24','sys.salary.joinyear.calc','15'),('25','sys.examin.shiftimport.override','0'),('2f5f7bce-0665-462a-9bc2-da450b7fc974','sys.birthday.remind','45'),('30b24386-57f6-4a45-beb1-38d7a79a01ad','sys.shift.end.minute','120'),('394579dd-2f38-446d-9aa2-cdd30be643c3','sys.examin.lr.confirm','0'),('3c60faf5-cd4e-49ad-94bc-7a4172f552b4','sys.salary.tax.min','2000'),('3ce92b99-7bfe-4b39-a80e-8eff18578ce6','sys.split.pages','20'),('44885f63-8b85-4254-8cf7-5a49fa9e0dcb','sys.shift.card.calchour','55,25'),('48135cc2-e5f4-4959-a025-cf8f501a73c6','sys.shift.start.minute','120'),('4938ab4e-2e6d-400e-8a3f-916c5cd0581a','sys.salary.config.update','1'),('5248b875-e61f-4ff2-9b37-2eed1fd2265c','sys.trial.expire.remind','45'),('56e84785-d48e-43f0-a256-9bc16f435163','sys.salary.approve.level','1'),('5a1bec5d-8905-487a-b831-2078412faa9e','sys.examin.approve.level','1'),('5af110e9-34c7-40e3-8239-6e9dec14f7db','sys.shift.cardrepeat.minute','1'),('5c965eac-b834-4cdd-8018-8c5f625a152a','sys.examin.ot.confirm','0'),('647be3f6-cf50-4084-a9b5-b83fa0a335f3','sys.examin.leave.mode','day,8'),('68ef0f55-267e-4f40-963e-c06a0a1012ad','sys.salary.tax.rate','0.05,0.10,0.15,0.20,0.25,0.30,0.35,0.40,0.45'),('6b71ab06-b792-486c-94cf-acce9142050b','sys.examin.absent.minute','120'),('801c3b98-2fea-439c-a22b-320a81c9001d','sys.profile.import.update','0'),('83c97ade-b2c1-4276-8902-dc3875d9f8ba','sys.backup.copies','30'),('8856870a-8e7a-443e-8755-3abaed178981','sys.contract.expire.remind','45'),('9a731667-80d8-4e54-bfd4-2b3395527bb5','sys.sub.window.pages','15'),('9ad14b7e-bcd7-4453-a7aa-15c8263d675a','sys.examin.leave.type','0'),('ab006eed-3804-41be-9909-3310bbc3891c','sys.salary.tax.range','0,500,2000,5000,20000,40000,60000,80000,100000'),('c0911edd-4305-4303-ad11-590d74e00804','sys.shift.card.calcday','75,25'),('cfafc404-ab38-40d1-9a3e-4e98d4aef3ec','sys.shift.late.minute','5,15;15,30;30,90'),('d98fdbce-aac1-4bfb-adc8-528af8d883f9','sys.examin.create.level','1'),('e1779cc1-fe4b-40b9-9100-4d6623705b6e','sys.examin.update.level','1'),('e70d9033-3dc4-47f4-ae9d-80ffdc0e55d9','sys.file.path','file/'),('ee789fa2-8924-4404-8d9b-6ec925872f2a','sys.shift.earlyleave.minute','5,15;15,30;30,90'),('f54b7192-5eb5-4ca8-9a79-f3eecf4acf12','sys.examin.shift.enable','0'),('f9ee066a-6a3f-4f7b-9e0a-8a90fd55477b','sys.backup.frequency','3'),('fd7ba85b-62f0-413b-998d-1668a0f9414f','sys.profile.sub.type','1');
/*!40000 ALTER TABLE `sysconfig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syslog`
--

DROP TABLE IF EXISTS `syslog`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `syslog` (
  `sl_id` varchar(36) NOT NULL default '',
  `sl_type` int(10) unsigned NOT NULL default '0',
  `sl_table_name` varchar(32) NOT NULL default '',
  `sl_record_id` varchar(36) NOT NULL default '',
  `sl_empno` varchar(36) default NULL,
  `sl_change_empno` varchar(36) default NULL,
  `sl_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `sl_action` varchar(64) NOT NULL default '',
  `sl_comments` varchar(255) default NULL,
  PRIMARY KEY  (`sl_id`),
  KEY `FK_sl_empno` (`sl_empno`),
  KEY `FK_sl_change_empno` (`sl_change_empno`),
  CONSTRAINT `FK_sl_empno` FOREIGN KEY (`sl_empno`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `syslog`
--

LOCK TABLES `syslog` WRITE;
/*!40000 ALTER TABLE `syslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `syslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trcourse`
--

DROP TABLE IF EXISTS `trcourse`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `trcourse` (
  `trc_no` varchar(36) NOT NULL default '',
  `trc_name` varchar(64) NOT NULL default '',
  `trc_type` varchar(36) NOT NULL default '',
  `trc_info` mediumtext NOT NULL,
  `trc_status` int(10) unsigned NOT NULL default '1',
  `trc_file_name` varchar(128) default NULL,
  `trc_create_by` varchar(36) NOT NULL default '',
  `trc_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `trc_last_change_by` varchar(36) NOT NULL default '',
  `trc_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`trc_no`),
  KEY `FK_trc_type` (`trc_type`),
  KEY `FK_trc_create_by` (`trc_create_by`),
  KEY `FK_trc_last_change_by` (`trc_last_change_by`),
  CONSTRAINT `FK_trc_type` FOREIGN KEY (`trc_type`) REFERENCES `trtype` (`trt_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `trcourse`
--

LOCK TABLES `trcourse` WRITE;
/*!40000 ALTER TABLE `trcourse` DISABLE KEYS */;
/*!40000 ALTER TABLE `trcourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trcourseplan`
--

DROP TABLE IF EXISTS `trcourseplan`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `trcourseplan` (
  `trcp_id` varchar(36) NOT NULL default '',
  `trcp_course_no` varchar(36) NOT NULL default '',
  `trcp_budget_year` varchar(4) default NULL,
  `trcp_budget_fee` decimal(9,2) default NULL,
  `trcp_budget_hour` decimal(6,1) NOT NULL default '0.0',
  `trcp_coordinator` varchar(36) default NULL,
  `trcp_attendee_no` int(10) unsigned default NULL,
  `trcp_dept_limit` varchar(255) default NULL,
  `trcp_teacher` varchar(64) default NULL,
  `trcp_location` varchar(128) default NULL,
  `trcp_institution` varchar(128) default NULL,
  `trcp_start_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `trcp_end_date` datetime NOT NULL default '0000-00-00 00:00:00',
  `trcp_enroll_start_date` datetime default NULL,
  `trcp_enroll_end_date` datetime default NULL,
  `trcp_status` int(10) unsigned NOT NULL default '1',
  `trcp_comments` varchar(255) default NULL,
  `trcp_file_name` varchar(128) default NULL,
  `trcp_create_by` varchar(36) NOT NULL default '',
  `trcp_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `trcp_last_change_by` varchar(36) NOT NULL default '',
  `trcp_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`trcp_id`),
  KEY `FK_trcp_course_no` (`trcp_course_no`),
  KEY `FK_trcp_coordinator` (`trcp_coordinator`),
  KEY `FK_trcp_create_by` (`trcp_create_by`),
  KEY `FK_trcp_last_change_by` (`trcp_last_change_by`),
  CONSTRAINT `FK_trcp_coordinator` FOREIGN KEY (`trcp_coordinator`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trcp_course_no` FOREIGN KEY (`trcp_course_no`) REFERENCES `trcourse` (`trc_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `trcourseplan`
--

LOCK TABLES `trcourseplan` WRITE;
/*!40000 ALTER TABLE `trcourseplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `trcourseplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tremployeeplan`
--

DROP TABLE IF EXISTS `tremployeeplan`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `tremployeeplan` (
  `trp_id` varchar(36) NOT NULL default '',
  `trp_trcp_id` varchar(36) NOT NULL default '0',
  `trp_trainee_no` varchar(36) NOT NULL default '',
  `trp_trainee_dept` varchar(36) NOT NULL default '',
  `trp_trainee_job_title` varchar(36) NOT NULL default '',
  `trp_next_approver` varchar(36) default NULL,
  `trp_status` int(10) unsigned NOT NULL default '1',
  `trp_need_gm_approve` int(10) unsigned default NULL,
  `trp_comments` mediumtext,
  `trp_file_name` varchar(128) default NULL,
  `trp_create_by` varchar(36) NOT NULL default '',
  `trp_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `trp_last_change_by` varchar(36) NOT NULL default '',
  `trp_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`trp_id`),
  KEY `FK_trp_trcp_id` (`trp_trcp_id`),
  KEY `FK_trp_trainee_no` (`trp_trainee_no`),
  KEY `FK_trp_trainee_dept` (`trp_trainee_dept`),
  KEY `FK_trp_trainee_job_title` (`trp_trainee_job_title`),
  KEY `FK_trp_create_by` (`trp_create_by`),
  KEY `FK_trp_last_change_by` (`trp_last_change_by`),
  CONSTRAINT `FK_trp_trainee_dept` FOREIGN KEY (`trp_trainee_dept`) REFERENCES `department` (`department_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trp_trainee_job_title` FOREIGN KEY (`trp_trainee_job_title`) REFERENCES `jobtitle` (`jobtitle_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trp_trainee_no` FOREIGN KEY (`trp_trainee_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE,
  CONSTRAINT `FK_trp_trcp_id` FOREIGN KEY (`trp_trcp_id`) REFERENCES `trcourseplan` (`trcp_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `tremployeeplan`
--

LOCK TABLES `tremployeeplan` WRITE;
/*!40000 ALTER TABLE `tremployeeplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `tremployeeplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trtype`
--

DROP TABLE IF EXISTS `trtype`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `trtype` (
  `trt_no` varchar(36) NOT NULL default '',
  `trt_name` varchar(64) NOT NULL default '',
  `trt_desc` varchar(128) default NULL,
  `trt_sort_id` int(10) unsigned default NULL,
  PRIMARY KEY  (`trt_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `trtype`
--

LOCK TABLES `trtype` WRITE;
/*!40000 ALTER TABLE `trtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `trtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `userinfo` (
  `ui_emp_no` varchar(36) NOT NULL default '',
  `ui_username` varchar(64) NOT NULL default '',
  `ui_password` varchar(64) NOT NULL default '',
  `ui_role` varchar(128) NOT NULL default '',
  `ui_auth` varchar(255) default NULL,
  `ui_status` int(11) NOT NULL default '1',
  `ui_last_login_time` datetime default NULL,
  `ui_login_ip` varchar(64) default NULL,
  `ui_login_time` datetime default NULL,
  `ui_mac_restrict` varchar(64) default NULL,
  `ui_ip_restrict` varchar(64) default NULL,
  `ui_time_restrict` varchar(64) default NULL,
  `ui_level_restrict` int(10) unsigned NOT NULL default '0',
  `ui_is_gm_hr` varchar(32) default NULL,
  `ui_reference_id` varchar(36) default NULL,
  `ui_create_by` varchar(36) NOT NULL default '',
  `ui_create_time` datetime NOT NULL default '0000-00-00 00:00:00',
  `ui_last_change_by` varchar(36) NOT NULL default '',
  `ui_last_change_time` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ui_emp_no`),
  KEY `FK_ui_create_by` (`ui_create_by`),
  KEY `FK_ui_last_change_by` (`ui_last_change_by`),
  CONSTRAINT `FK_ui_emp_no` FOREIGN KEY (`ui_emp_no`) REFERENCES `employee` (`emp_no`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wfapprover`
--


DROP TABLE IF EXISTS `wfapprover`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `wfapprover` (
  `workflow_id` varchar(36) NOT NULL default '',
  `workflow_approver_type` varchar(32) NOT NULL default '',
  `workflow_approver_level` int(10) unsigned default NULL,
  `workflow_approver_id` varchar(36) NOT NULL default '',
  `workflow_limit` decimal(9,2) NOT NULL default '0.00',
  `workflow_approver_ind` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`workflow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `wfapprover`
--

LOCK TABLES `wfapprover` WRITE;
/*!40000 ALTER TABLE `wfapprover` DISABLE KEYS */;
/*!40000 ALTER TABLE `wfapprover` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-03-30 11:05:22
