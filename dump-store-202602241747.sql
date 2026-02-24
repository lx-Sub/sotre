-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: store
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `att_attendance`
--

DROP TABLE IF EXISTS `att_attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `mor_start_time` time DEFAULT NULL COMMENT '上午上班时间',
  `mor_end_time` time DEFAULT NULL COMMENT '上午下班时间',
  `aft_start_time` time DEFAULT NULL COMMENT '下午上班时间',
  `aft_end_time` time DEFAULT NULL COMMENT '下午下班时间',
  `attendance_date` date NOT NULL COMMENT '考勤日期',
  `status` tinyint(1) DEFAULT NULL COMMENT '0正常，1迟到，2早退，3旷工，4休假',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工考勤表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_attendance`
--

LOCK TABLES `att_attendance` WRITE;
/*!40000 ALTER TABLE `att_attendance` DISABLE KEYS */;
INSERT INTO `att_attendance` VALUES (284,1,'06:00:00','12:00:00','13:00:55','18:00:16','2024-03-08',0,NULL,'2024-03-31 15:30:39','2024-03-31 15:30:39',0),(285,1,'09:00:00','10:30:00','13:01:55','16:00:16','2024-03-11',3,NULL,'2024-03-31 15:30:39','2024-03-31 15:30:39',0),(286,2,'06:45:00','11:40:00','13:10:55','16:30:16','2024-03-08',1,NULL,'2024-03-31 15:30:39','2024-03-31 15:30:39',0),(287,2,'07:40:00','12:30:00','13:09:55','18:49:16','2024-03-11',1,NULL,'2024-03-31 15:30:39','2024-03-31 15:30:39',0),(288,38,'06:00:00','12:00:00','13:00:55','18:00:16','2024-03-08',0,NULL,'2024-03-31 16:46:05','2024-03-31 16:46:05',0),(289,38,'09:00:00','10:30:00','13:01:55','16:00:16','2024-03-11',3,NULL,'2024-03-31 16:46:05','2024-03-31 16:46:05',0),(290,39,'06:45:00','11:40:00','13:10:55','16:30:16','2024-03-08',3,NULL,'2024-03-31 16:46:05','2024-03-31 16:46:05',0),(291,39,'07:40:00','12:30:00','13:09:55','18:49:16','2024-03-11',1,NULL,'2024-03-31 16:46:05','2024-03-31 16:46:05',0);
/*!40000 ALTER TABLE `att_attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_equipment`
--

DROP TABLE IF EXISTS `user_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_equipment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '装备ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `name` varchar(100) NOT NULL COMMENT '装备名称',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `purchase_date` date DEFAULT NULL COMMENT '购买日期',
  `usage_condition` varchar(20) DEFAULT NULL COMMENT '使用情况：全新/轻微/明显/磨损',
  `description` text COMMENT '描述',
  `images` json DEFAULT NULL COMMENT '图片URL数组',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户装备库表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_equipment`
--

LOCK TABLES `user_equipment` WRITE;
/*!40000 ALTER TABLE `user_equipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `role` tinyint DEFAULT '2' COMMENT '角色：1-超级管理员 2-普通管理员',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'admin','0192023a7bbd73250516f069df18b500','超级管理员',NULL,NULL,NULL,1,1,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL,NULL);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sal_salary`
--

DROP TABLE IF EXISTS `sal_salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sal_salary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `base_salary` decimal(10,3) DEFAULT NULL COMMENT '基础工资',
  `overtime_salary` decimal(10,3) DEFAULT NULL COMMENT '加班费',
  `subsidy` decimal(10,3) DEFAULT NULL COMMENT '生活补贴',
  `bonus` decimal(10,3) DEFAULT NULL COMMENT '奖金',
  `total_salary` decimal(10,3) DEFAULT NULL COMMENT '总工资',
  `late_deduct` decimal(10,3) DEFAULT NULL COMMENT '早退扣款',
  `leave_deduct` decimal(10,3) DEFAULT NULL COMMENT '休假扣款',
  `leave_early_deduct` decimal(10,3) DEFAULT NULL COMMENT '早退扣款',
  `absenteeism_deduct` decimal(10,3) DEFAULT NULL COMMENT '旷工扣款',
  `month` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '月份',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工工资表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sal_salary`
--

LOCK TABLES `sal_salary` WRITE;
/*!40000 ALTER TABLE `sal_salary` DISABLE KEYS */;
INSERT INTO `sal_salary` VALUES (1,1,7000.000,NULL,400.000,200.000,5595.000,0.000,640.000,0.000,100.000,'202204','jack的工资明细','2024-03-31 13:30:12','2024-03-31 13:30:12',0),(2,1,6800.000,NULL,0.000,100.000,4795.000,50.000,160.000,50.000,100.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(3,1,6500.000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'202105',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(4,1,7000.000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'202112',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(5,2,6000.000,NULL,600.000,200.000,5270.000,0.000,240.000,50.000,0.000,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(6,2,7000.000,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'202104',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(7,3,6000.000,NULL,0.000,0.000,5175.000,NULL,NULL,NULL,NULL,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(8,1,6000.000,NULL,0.000,0.000,4735.000,NULL,NULL,NULL,NULL,'202202',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(9,4,6000.000,NULL,500.000,300.000,5710.000,NULL,NULL,NULL,NULL,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(10,5,7000.000,NULL,0.000,0.000,5368.000,NULL,NULL,NULL,NULL,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(11,6,8000.000,NULL,0.000,0.000,6340.000,0.000,0.000,50.000,0.000,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(12,7,6000.000,NULL,1000.000,0.000,5710.000,0.000,0.000,0.000,0.000,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(13,8,6000.000,NULL,0.000,0.000,2100.000,0.000,0.000,0.000,0.000,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(14,9,6000.000,NULL,0.000,0.000,5275.000,0.000,0.000,0.000,0.000,'202204',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(15,1,6666.000,NULL,0.000,0.000,5401.000,0.000,0.000,0.000,0.000,'202203',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(16,10,8000.000,NULL,0.000,0.000,5030.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(17,2,6050.000,NULL,0.000,0.000,4660.000,50.000,0.000,0.000,100.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(18,3,6000.000,NULL,0.000,0.000,4632.000,50.000,0.000,100.000,300.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(19,4,7000.000,NULL,0.000,0.000,5510.000,100.000,0.000,0.000,300.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(20,5,7500.000,NULL,0.000,0.000,5868.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(21,6,6500.000,NULL,0.000,0.000,4890.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(22,7,7878.000,NULL,0.000,0.000,6588.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(23,8,7999.000,NULL,0.000,0.000,4099.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(24,9,7000.000,NULL,0.000,0.000,6275.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(25,11,7000.000,NULL,200.000,0.000,5345.000,0.000,0.000,0.000,0.000,'202205',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0),(26,1,4000.000,NULL,500.000,1000.000,3026.000,0.000,0.000,0.000,0.000,'202403',NULL,'2024-03-31 13:30:12','2024-03-31 13:30:12',0);
/*!40000 ALTER TABLE `sal_salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_appeal`
--

DROP TABLE IF EXISTS `credit_appeal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_appeal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申诉ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `credit_record_id` bigint DEFAULT NULL COMMENT '关联信用记录ID',
  `reason` varchar(500) NOT NULL COMMENT '申诉原因',
  `evidence` text COMMENT '申诉材料（JSON数组）',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待处理 1-已通过 2-已驳回',
  `feedback` varchar(500) DEFAULT NULL COMMENT '处理反馈',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信用申诉表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_appeal`
--

LOCK TABLES `credit_appeal` WRITE;
/*!40000 ALTER TABLE `credit_appeal` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_appeal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_verification`
--

DROP TABLE IF EXISTS `user_verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_verification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '认证ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `id_number` varchar(18) NOT NULL COMMENT '身份证号',
  `id_card_front` varchar(255) NOT NULL COMMENT '身份证正面照片URL',
  `id_card_back` varchar(255) NOT NULL COMMENT '身份证反面照片URL',
  `handheld_id_card` varchar(255) NOT NULL COMMENT '手持身份证照片URL',
  `equipment_credentials` text COMMENT '装备权属凭证（JSON数组）',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待审核 1-已通过 2-已驳回',
  `submit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `verifier_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `expiry_date` datetime DEFAULT NULL COMMENT '资质有效期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户实名认证表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_verification`
--

LOCK TABLES `user_verification` WRITE;
/*!40000 ALTER TABLE `user_verification` DISABLE KEYS */;
INSERT INTO `user_verification` VALUES (1,2,'张三','320101199001011234','/uploads/idcard/zhangsan_front.jpg','/uploads/idcard/zhangsan_back.jpg','/uploads/idcard/zhangsan_hand.jpg',NULL,1,'2026-02-24 16:38:35',NULL,NULL,NULL,NULL),(2,3,'李四','320101199001011235','/uploads/idcard/lisi_front.jpg','/uploads/idcard/lisi_back.jpg','/uploads/idcard/lisi_hand.jpg',NULL,0,'2026-02-24 16:38:35',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_verification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_like`
--

DROP TABLE IF EXISTS `user_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_type` tinyint NOT NULL COMMENT '目标类型：1-帖子 2-评论',
  `target_id` bigint NOT NULL COMMENT '目标ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`,`target_type`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_like`
--

LOCK TABLES `user_like` WRITE;
/*!40000 ALTER TABLE `user_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `user_id` bigint NOT NULL COMMENT '卖家ID',
  `user_type` tinyint NOT NULL COMMENT '卖家类型：1-商家 2-个人',
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `condition` varchar(20) DEFAULT NULL COMMENT '成色：全新/99新/95新等',
  `images` json NOT NULL COMMENT '图片URL数组',
  `trade_type` tinyint NOT NULL DEFAULT '1' COMMENT '交易类型：1-仅出售 2-可交换',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待审核 1-上架 2-下架 3-违规下架',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `favorite_count` int NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `sale_count` int NOT NULL DEFAULT '0' COMMENT '销量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_price` (`price`),
  FULLTEXT KEY `ft_name_desc` (`name`,`description`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `merchant_apply`
--

DROP TABLE IF EXISTS `merchant_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `merchant_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `business_license` varchar(50) NOT NULL COMMENT '营业执照号',
  `business_license_url` varchar(255) NOT NULL COMMENT '营业执照URL',
  `brand` varchar(100) DEFAULT NULL COMMENT '品牌名称',
  `brand_authorization_url` varchar(255) DEFAULT NULL COMMENT '品牌授权书URL',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) NOT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待审核 1-已通过 2-已驳回',
  `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `merchant_apply`
--

LOCK TABLES `merchant_apply` WRITE;
/*!40000 ALTER TABLE `merchant_apply` DISABLE KEYS */;
INSERT INTO `merchant_apply` VALUES (1,5,'耐克体育用品有限公司','91440101MA5XXXXXX1','/uploads/license/nike.jpg','Nike',NULL,'张三','13800000004',NULL,1,'2026-02-24 16:38:35',NULL,NULL,NULL),(2,6,'阿迪达斯体育有限公司','91440101MA5XXXXXX2','/uploads/license/adidas.jpg','Adidas',NULL,'李四','13800000005',NULL,0,'2026-02-24 16:38:35',NULL,NULL,NULL);
/*!40000 ALTER TABLE `merchant_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `per_role_menu`
--

DROP TABLE IF EXISTS `per_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='角色菜单关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_role_menu`
--

LOCK TABLES `per_role_menu` WRITE;
/*!40000 ALTER TABLE `per_role_menu` DISABLE KEYS */;
INSERT INTO `per_role_menu` VALUES (1,1,2,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(2,1,5,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(3,1,6,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(5,1,3,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(6,1,4,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(7,1,1,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(18,1,13,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(24,1,15,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(25,1,17,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(26,1,18,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(27,1,14,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(28,1,19,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(29,1,20,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(30,1,16,1,'2024-03-31 13:29:53','2024-03-31 13:29:53',0),(41,11,17,1,'2024-03-31 15:09:24',NULL,0),(42,11,15,1,'2024-03-31 15:09:24',NULL,0),(43,11,16,1,'2024-03-31 15:09:24',NULL,0),(44,11,18,1,'2024-03-31 15:09:24',NULL,0),(45,12,1,1,'2024-03-31 15:09:42',NULL,0),(46,12,14,1,'2024-03-31 15:09:42',NULL,0),(47,12,19,1,'2024-03-31 15:09:42',NULL,0),(48,12,20,1,'2024-03-31 15:09:42',NULL,0),(49,12,5,1,'2024-03-31 15:09:42',NULL,0),(51,13,5,1,'2024-03-31 15:31:38',NULL,0),(52,13,1,1,'2024-03-31 15:31:38',NULL,0),(53,13,2,1,'2024-03-31 15:31:38',NULL,0),(54,13,13,1,'2024-03-31 15:31:38',NULL,0),(55,13,14,1,'2024-03-31 15:31:38',NULL,0),(56,13,19,1,'2024-03-31 15:31:38',NULL,0),(57,13,20,1,'2024-03-31 15:31:38',NULL,0),(58,13,17,1,'2024-03-31 15:31:38','2024-03-31 15:51:38',0),(59,13,15,1,'2024-03-31 15:31:38','2024-03-31 15:51:38',0),(60,13,16,1,'2024-03-31 15:31:38','2024-03-31 15:51:38',0),(61,13,18,1,'2024-03-31 15:31:38','2024-03-31 15:51:38',0);
/*!40000 ALTER TABLE `per_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `att_overtime`
--

DROP TABLE IF EXISTS `att_overtime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_overtime` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salary_multiple` decimal(5,2) DEFAULT NULL COMMENT '工资倍数，如按照小时计算，就是员工平均小时工资乘以倍数',
  `bonus` decimal(10,3) DEFAULT NULL COMMENT '加班奖金',
  `type_num` int DEFAULT NULL COMMENT '加班类型',
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `count_type` tinyint(1) DEFAULT NULL COMMENT '0小时，1天，默认0，计数加班工资的类型',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_time_off` tinyint(1) DEFAULT '0' COMMENT '0不补休，1补休，默认0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='加班表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_overtime`
--

LOCK TABLES `att_overtime` WRITE;
/*!40000 ALTER TABLE `att_overtime` DISABLE KEYS */;
INSERT INTO `att_overtime` VALUES (1,4.00,200.000,1,2,1,NULL,0,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(2,0.00,0.000,3,15,0,NULL,1,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(3,0.10,NULL,1,5,0,NULL,0,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(4,0.30,NULL,2,5,1,NULL,0,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(5,0.00,0.000,2,2,1,NULL,1,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(6,0.00,0.000,3,2,0,NULL,1,'2024-03-31 13:29:31','2024-03-31 13:29:31',0),(7,2.00,150.000,0,2,0,NULL,0,'2024-03-31 13:29:31','2024-03-31 13:29:31',0);
/*!40000 ALTER TABLE `att_overtime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_record`
--

DROP TABLE IF EXISTS `credit_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `change_score` int NOT NULL COMMENT '变更分数',
  `before_score` int NOT NULL COMMENT '变更前分数',
  `after_score` int NOT NULL COMMENT '变更后分数',
  `reason` varchar(500) DEFAULT NULL COMMENT '变更原因',
  `type` varchar(50) DEFAULT NULL COMMENT '变更类型：violation-违规 appeal-申诉 manual-手动调整',
  `business_id` bigint DEFAULT NULL COMMENT '关联业务ID（订单ID、申诉ID等）',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信用记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_record`
--

LOCK TABLES `credit_record` WRITE;
/*!40000 ALTER TABLE `credit_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `credit_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_order`
--

DROP TABLE IF EXISTS `exchange_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exchange_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '交换订单ID',
  `exchange_no` varchar(32) NOT NULL COMMENT '交换单号',
  `initiator_id` bigint NOT NULL COMMENT '发起方ID',
  `receiver_id` bigint NOT NULL COMMENT '接收方ID',
  `initiator_product_id` bigint NOT NULL COMMENT '发起方商品ID',
  `receiver_product_id` bigint NOT NULL COMMENT '接收方商品ID',
  `initiator_product_name` varchar(200) NOT NULL COMMENT '发起方商品名称',
  `receiver_product_name` varchar(200) NOT NULL COMMENT '接收方商品名称',
  `price_diff` decimal(10,2) DEFAULT '0.00' COMMENT '补差价金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待对方确认 1-待付款 2-待双方发货 3-待确认收货 4-已完成 5-已取消 6-仲裁中',
  `initiator_logistics_no` varchar(50) DEFAULT NULL COMMENT '发起方物流单号',
  `receiver_logistics_no` varchar(50) DEFAULT NULL COMMENT '接收方物流单号',
  `initiator_ship_time` datetime DEFAULT NULL COMMENT '发起方发货时间',
  `receiver_ship_time` datetime DEFAULT NULL COMMENT '接收方发货时间',
  `initiator_receive_time` datetime DEFAULT NULL COMMENT '发起方收货时间',
  `receiver_receive_time` datetime DEFAULT NULL COMMENT '接收方收货时间',
  `pay_time` datetime DEFAULT NULL COMMENT '差价付款时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `exchange_no` (`exchange_no`),
  KEY `idx_exchange_no` (`exchange_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交换订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_order`
--

LOCK TABLES `exchange_order` WRITE;
/*!40000 ALTER TABLE `exchange_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `code` varchar(100) NOT NULL COMMENT '权限代码',
  `type` varchar(20) DEFAULT NULL COMMENT '类型：menu-菜单 button-按钮 api-接口',
  `parent_code` varchar(100) DEFAULT NULL COMMENT '父权限代码',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_code` (`code`),
  KEY `idx_parent` (`parent_code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'用户管理','user:view','menu',NULL,NULL,NULL,NULL,1,1,'2026-02-24 15:28:49'),(2,'用户列表','user:list','menu',NULL,NULL,NULL,NULL,2,1,'2026-02-24 15:28:49'),(3,'用户详情','user:detail','button',NULL,NULL,NULL,NULL,3,1,'2026-02-24 15:28:49'),(4,'用户冻结','user:freeze','button',NULL,NULL,NULL,NULL,4,1,'2026-02-24 15:28:49'),(5,'用户解冻','user:unfreeze','button',NULL,NULL,NULL,NULL,5,1,'2026-02-24 15:28:49'),(6,'用户注销','user:delete','button',NULL,NULL,NULL,NULL,6,1,'2026-02-24 15:28:49'),(7,'商家审核','merchant:audit','menu',NULL,NULL,NULL,NULL,7,1,'2026-02-24 15:28:49'),(8,'商家列表','merchant:list','menu',NULL,NULL,NULL,NULL,8,1,'2026-02-24 15:28:49'),(9,'商家审核通过','merchant:approve','button',NULL,NULL,NULL,NULL,9,1,'2026-02-24 15:28:49'),(10,'商家审核驳回','merchant:reject','button',NULL,NULL,NULL,NULL,10,1,'2026-02-24 15:28:49'),(11,'信用管理','credit:view','menu',NULL,NULL,NULL,NULL,11,1,'2026-02-24 15:28:49'),(12,'信用列表','credit:list','menu',NULL,NULL,NULL,NULL,12,1,'2026-02-24 15:28:49'),(13,'信用修改','credit:update','button',NULL,NULL,NULL,NULL,13,1,'2026-02-24 15:28:49'),(14,'申诉处理','credit:appeal','button',NULL,NULL,NULL,NULL,14,1,'2026-02-24 15:28:49'),(15,'寄售审核','consignment:audit','menu',NULL,NULL,NULL,NULL,15,1,'2026-02-24 15:28:49'),(16,'寄售列表','consignment:list','menu',NULL,NULL,NULL,NULL,16,1,'2026-02-24 15:28:49'),(17,'寄售通过','consignment:approve','button',NULL,NULL,NULL,NULL,17,1,'2026-02-24 15:28:49'),(18,'寄售驳回','consignment:reject','button',NULL,NULL,NULL,NULL,18,1,'2026-02-24 15:28:49'),(19,'订单管理','order:view','menu',NULL,NULL,NULL,NULL,19,1,'2026-02-24 15:28:49'),(20,'订单列表','order:list','menu',NULL,NULL,NULL,NULL,20,1,'2026-02-24 15:28:49'),(21,'订单详情','order:detail','button',NULL,NULL,NULL,NULL,21,1,'2026-02-24 15:28:49'),(22,'售后仲裁','order:arbitration','button',NULL,NULL,NULL,NULL,22,1,'2026-02-24 15:28:49'),(23,'内容管理','content:view','menu',NULL,NULL,NULL,NULL,23,1,'2026-02-24 15:28:49'),(24,'帖子管理','post:list','menu',NULL,NULL,NULL,NULL,24,1,'2026-02-24 15:28:49'),(25,'帖子审核','post:audit','button',NULL,NULL,NULL,NULL,25,1,'2026-02-24 15:28:49'),(26,'帖子删除','post:delete','button',NULL,NULL,NULL,NULL,26,1,'2026-02-24 15:28:49'),(27,'系统管理','system:view','menu',NULL,NULL,NULL,NULL,27,1,'2026-02-24 15:28:49'),(28,'管理员管理','admin:list','menu',NULL,NULL,NULL,NULL,28,1,'2026-02-24 15:28:49'),(29,'权限分配','permission:assign','button',NULL,NULL,NULL,NULL,29,1,'2026-02-24 15:28:49'),(30,'操作日志','log:view','menu',NULL,NULL,NULL,NULL,30,1,'2026-02-24 15:28:49'),(31,'数据统计','statistics:view','menu',NULL,NULL,NULL,NULL,31,1,'2026-02-24 15:28:49');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `soc_insurance`
--

DROP TABLE IF EXISTS `soc_insurance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soc_insurance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL COMMENT '城市id',
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `house_base` decimal(10,3) DEFAULT NULL COMMENT '公积金基数',
  `per_house_rate` decimal(6,3) DEFAULT NULL COMMENT '公积金个人缴纳比例',
  `per_house_pay` decimal(10,3) DEFAULT NULL COMMENT '公积金个人缴纳费用',
  `com_house_rate` decimal(6,3) DEFAULT NULL COMMENT '公积金企业缴纳比例',
  `com_house_pay` decimal(10,3) DEFAULT NULL COMMENT '公积金企业缴纳费用',
  `social_base` decimal(10,3) DEFAULT NULL COMMENT '社保基数',
  `com_social_pay` decimal(10,3) DEFAULT NULL COMMENT '社保企业缴纳费用',
  `per_social_pay` decimal(10,3) DEFAULT NULL COMMENT '社保个人缴纳费用',
  `com_injury_rate` decimal(6,3) DEFAULT NULL COMMENT '工伤保险企业缴纳比例',
  `social_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '社保备注',
  `house_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公积金备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未支付，1已支付，2支付失败',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工五险一金表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `soc_insurance`
--

LOCK TABLES `soc_insurance` WRITE;
/*!40000 ALTER TABLE `soc_insurance` DISABLE KEYS */;
INSERT INTO `soc_insurance` VALUES (1,4,1,10000.000,0.080,800.000,0.050,500.000,9000.000,2313.000,1674.000,0.005,'社保个人缴纳1674元','公积金个人缴纳800元',0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(2,3,2,5000.000,0.080,400.000,0.080,400.000,8000.000,2224.000,840.000,0.015,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(3,4,3,3000.000,0.120,360.000,0.120,360.000,3000.000,813.000,558.000,0.019,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(4,3,4,5000.000,0.050,250.000,0.050,250.000,8000.000,2256.000,840.000,0.019,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(5,5,5,3400.000,0.080,272.000,0.080,272.000,4000.000,1916.000,1360.000,0.003,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(6,5,6,5000.000,0.050,250.000,0.050,250.000,4000.000,1916.000,1360.000,0.003,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(7,3,7,4500.000,0.100,450.000,0.100,450.000,8000.000,2120.000,840.000,0.002,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(8,5,8,10000.000,0.050,500.000,0.050,500.000,10000.000,4910.000,3400.000,0.015,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(9,2,9,4000.000,0.050,200.000,0.050,200.000,5000.000,1375.000,525.000,0.010,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(10,5,10,5000.000,0.050,250.000,0.050,250.000,8000.000,3888.000,2720.000,0.010,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0),(11,6,11,10000.000,0.100,1000.000,0.100,1000.000,9000.000,1485.000,855.000,0.015,NULL,NULL,0,'2024-03-31 13:30:31','2024-03-31 13:30:31',0);
/*!40000 ALTER TABLE `soc_insurance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(100) DEFAULT NULL COMMENT '操作人姓名',
  `operation` varchar(50) NOT NULL COMMENT '操作类型',
  `target_id` bigint DEFAULT NULL COMMENT '操作目标ID',
  `target_type` varchar(50) DEFAULT NULL COMMENT '操作目标类型',
  `details` text COMMENT '操作详情',
  `result` varchar(20) DEFAULT NULL COMMENT '操作结果',
  `reason` varchar(500) DEFAULT NULL COMMENT '操作原因',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`),
  KEY `idx_target` (`target_id`,`target_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_log`
--

LOCK TABLES `operation_log` WRITE;
/*!40000 ALTER TABLE `operation_log` DISABLE KEYS */;
INSERT INTO `operation_log` VALUES (1,NULL,NULL,'unfreeze',2,'user',NULL,NULL,'1',NULL,NULL,'2026-02-24 16:46:49'),(2,NULL,NULL,'batch_update_status',NULL,'user','批量更新用户状态为：0，用户IDs：[2, 3]',NULL,'批量冻结测试',NULL,NULL,'2026-02-24 16:48:21'),(3,NULL,NULL,'batch_update_status',NULL,'user','批量更新用户状态为：1，用户IDs：[2, 3]',NULL,'批量冻结测试',NULL,NULL,'2026-02-24 16:48:26');
/*!40000 ALTER TABLE `operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `province` varchar(50) NOT NULL COMMENT '省',
  `city` varchar(50) NOT NULL COMMENT '市',
  `district` varchar(50) NOT NULL COMMENT '区',
  `detail_address` varchar(200) NOT NULL COMMENT '详细地址',
  `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否默认：0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '作者ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型：1-普通帖子 2-评测',
  `equipment_id` bigint DEFAULT NULL COMMENT '关联装备ID（评测时）',
  `images` json DEFAULT NULL COMMENT '图片数组',
  `category` varchar(50) DEFAULT NULL COMMENT '板块分类',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待审核 1-已发布 2-已屏蔽 3-已删除',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览数',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `favorite_count` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `is_essence` tinyint NOT NULL DEFAULT '0' COMMENT '是否加精：0-否 1-是',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶：0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_is_essence` (`is_essence`),
  FULLTEXT KEY `ft_content` (`title`,`content`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,2,'新手跑步装备推荐','最近开始跑步，想请教大家有什么推荐的跑鞋？',1,NULL,NULL,'跑步',1,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(2,3,'篮球鞋评测','分享几款最近入手的篮球鞋体验',1,NULL,NULL,'篮球',0,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(3,2,'骑行装备清单','分享一下我的骑行装备清单',1,NULL,NULL,'骑行',2,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(4,2,'新手跑步装备推荐','最近开始跑步，想请教大家有什么推荐的跑鞋？',1,NULL,NULL,'跑步',1,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:42:17','2026-02-24 16:42:17'),(5,3,'篮球鞋评测','分享几款最近入手的篮球鞋体验',1,NULL,NULL,'篮球',0,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:42:17','2026-02-24 16:42:17'),(6,2,'骑行装备清单','分享一下我的骑行装备清单',1,NULL,NULL,'骑行',2,NULL,NULL,0,0,0,0,0,0,'2026-02-24 16:42:17','2026-02-24 16:42:17');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `soc_city`
--

DROP TABLE IF EXISTS `soc_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `soc_city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参保城市',
  `average_salary` decimal(10,3) DEFAULT NULL COMMENT '职工上年度平均月工资',
  `lower_salary` decimal(10,3) DEFAULT NULL COMMENT '职工上年度最低月工资',
  `soc_upper_limit` decimal(10,3) DEFAULT NULL COMMENT '职工社保缴纳基数上限',
  `soc_lower_limit` decimal(10,3) DEFAULT NULL COMMENT '职工社保缴纳基数下限',
  `hou_upper_limit` decimal(10,3) DEFAULT NULL COMMENT '公积金缴纳基数上限',
  `hou_lower_limit` decimal(10,3) DEFAULT NULL COMMENT '公积金缴纳基数下限',
  `per_pension_rate` decimal(6,3) DEFAULT NULL COMMENT '个人养老保险缴费比例',
  `com_pension_rate` decimal(6,3) DEFAULT NULL COMMENT '企业养老保险缴费比例',
  `per_medical_rate` decimal(6,3) DEFAULT NULL COMMENT '个人医疗保险缴费比例',
  `com_medical_rate` decimal(6,3) DEFAULT NULL COMMENT '企业医疗保险缴费比例',
  `per_unemployment_rate` decimal(6,3) DEFAULT NULL COMMENT '个人失业保险缴费比例',
  `com_unemployment_rate` decimal(6,3) DEFAULT NULL COMMENT '企业失业保险缴费比例',
  `com_maternity_rate` decimal(6,3) DEFAULT NULL COMMENT '企业生育保险缴费比例',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='参保城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `soc_city`
--

LOCK TABLES `soc_city` WRITE;
/*!40000 ALTER TABLE `soc_city` DISABLE KEYS */;
INSERT INTO `soc_city` VALUES (1,'成都',10000.000,3000.000,30000.000,6000.000,30000.000,3000.000,0.090,0.160,0.020,0.090,0.007,0.006,0.010,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0),(2,'重庆',8000.000,3000.000,24000.000,4800.000,24000.000,3000.000,0.100,0.100,0.020,0.085,0.005,0.009,0.028,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0),(3,'北京',12000.000,4000.000,36000.000,7200.000,36000.000,4000.000,0.080,0.120,0.020,0.090,0.011,0.005,0.008,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0),(4,'上海',15000.000,10000.000,45000.000,9000.000,45000.000,10000.000,0.076,0.022,0.010,0.020,0.100,0.120,0.090,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0),(5,'武汉',5000.000,3400.000,15000.000,3000.000,15000.000,3400.000,0.100,0.130,0.100,0.130,0.140,0.021,0.025,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0),(6,'深圳',13000.000,10000.000,39000.000,7800.000,39000.000,10000.000,0.050,0.070,0.030,0.060,0.015,0.010,0.010,NULL,'2024-03-31 13:30:24','2024-03-31 13:30:24',0);
/*!40000 ALTER TABLE `soc_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favorite`
--

DROP TABLE IF EXISTS `user_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favorite`
--

LOCK TABLES `user_favorite` WRITE;
/*!40000 ALTER TABLE `user_favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_permission`
--

DROP TABLE IF EXISTS `admin_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` bigint NOT NULL COMMENT '管理员ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_permission` (`admin_id`,`permission_id`),
  KEY `idx_admin_id` (`admin_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员权限关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_permission`
--

LOCK TABLES `admin_permission` WRITE;
/*!40000 ALTER TABLE `admin_permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `shop_name` varchar(100) NOT NULL COMMENT '店铺名称',
  `shop_logo` varchar(500) DEFAULT NULL COMMENT '店铺Logo',
  `shop_banner` varchar(500) DEFAULT NULL COMMENT '店铺横幅',
  `shop_desc` text COMMENT '店铺简介',
  `shop_address` varchar(200) DEFAULT NULL COMMENT '店铺地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `business_license` varchar(500) DEFAULT NULL COMMENT '营业执照',
  `brand_authorization` json DEFAULT NULL COMMENT '品牌授权书数组',
  `audit_status` tinyint NOT NULL DEFAULT '0' COMMENT '审核状态：0-待审核 1-已通过 2-已驳回',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '店铺状态：0-关闭 1-营业中',
  `score` decimal(3,2) DEFAULT '5.00' COMMENT '店铺评分',
  `sale_count` int NOT NULL DEFAULT '0' COMMENT '总销量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (1,2,'耐克官方旗舰店',NULL,NULL,'耐克官方授权店铺，正品保障',NULL,NULL,NULL,NULL,1,NULL,NULL,1,5.00,0,'2026-02-24 15:28:49','2026-02-24 15:28:49'),(2,3,'阿迪达斯官方旗舰店',NULL,NULL,'阿迪达斯官方授权店铺',NULL,NULL,NULL,NULL,1,NULL,NULL,1,5.00,0,'2026-02-24 15:28:49','2026-02-24 15:28:49');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `private_message`
--

DROP TABLE IF EXISTS `private_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `private_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '私信ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `content` text NOT NULL COMMENT '内容',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读：0-否 1-是',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_sender_receiver` (`sender_id`,`receiver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='私信表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `private_message`
--

LOCK TABLES `private_message` WRITE;
/*!40000 ALTER TABLE `private_message` DISABLE KEYS */;
INSERT INTO `private_message` VALUES (1,2,3,'你好，请问鞋子还在吗？',0,NULL,'2026-02-24 16:38:35'),(2,3,2,'在的，可以看看',1,NULL,'2026-02-24 16:38:35'),(3,2,3,'你好，请问鞋子还在吗？',0,NULL,'2026-02-24 16:42:17'),(4,3,2,'在的，可以看看',1,NULL,'2026-02-24 16:42:17');
/*!40000 ALTER TABLE `private_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `att_staff_leave`
--

DROP TABLE IF EXISTS `att_staff_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_staff_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `days` int DEFAULT NULL COMMENT '请假的天数',
  `type_num` int DEFAULT NULL COMMENT '请假类型id',
  `start_date` date DEFAULT NULL COMMENT '请假的开始日期',
  `status` tinyint(1) DEFAULT '0' COMMENT '0未审核，1审核通过，2驳回，3撤销',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工请假表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_staff_leave`
--

LOCK TABLES `att_staff_leave` WRITE;
/*!40000 ALTER TABLE `att_staff_leave` DISABLE KEYS */;
INSERT INTO `att_staff_leave` VALUES (18,1,4,4,'2023-05-06',1,'回家喽','2024-03-31 13:29:37','2024-03-31 16:20:35',0),(19,1,4,4,'2023-05-14',1,'回家喽','2024-03-31 13:29:37','2024-03-31 16:20:38',0),(20,1,4,4,'2023-05-19',1,NULL,'2024-03-31 13:29:37','2024-03-31 16:20:39',0),(21,1,5,0,'2024-01-10',3,'努力复习中！','2024-03-31 13:29:37','2024-03-31 16:20:42',1),(22,1,2,2,'2024-02-16',1,NULL,'2024-03-31 13:29:37','2024-03-31 16:20:45',0),(23,1,5,0,'2024-02-15',1,'有事情需要处理','2024-03-31 13:29:37','2024-03-31 16:20:46',0),(24,1,2,0,'2024-02-21',1,'有事','2024-03-31 13:29:37','2024-03-31 16:20:51',0);
/*!40000 ALTER TABLE `att_staff_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `reporter_id` bigint NOT NULL COMMENT '举报人ID',
  `reported_user_id` bigint NOT NULL COMMENT '被举报人ID',
  `target_type` tinyint NOT NULL COMMENT '举报类型：1-帖子 2-评论 3-商品',
  `target_id` bigint NOT NULL COMMENT '目标ID',
  `reason` varchar(200) NOT NULL COMMENT '举报原因',
  `description` text COMMENT '详细描述',
  `evidence_images` json DEFAULT NULL COMMENT '证据图片',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '处理状态：0-待处理 1-已处理 2-驳回',
  `handle_result` varchar(500) DEFAULT NULL COMMENT '处理结果',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='举报表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
INSERT INTO `report` VALUES (1,2,3,1,2,'广告贴',NULL,NULL,0,NULL,NULL,NULL,'2026-02-24 16:38:35'),(2,3,4,2,3,'人身攻击',NULL,NULL,1,NULL,NULL,NULL,'2026-02-24 16:38:35'),(3,2,3,1,2,'广告贴',NULL,NULL,0,NULL,NULL,NULL,'2026-02-24 16:42:17'),(4,3,4,2,3,'人身攻击',NULL,NULL,1,NULL,NULL,NULL,'2026-02-24 16:42:17');
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(200) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型：1-系统公告 2-活动通知 3-平台规则',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶：0-否 1-是',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-草稿 1-已发布 2-已下架',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (1,'平台上线公告','运动装备交易社区正式上线啦！',1,0,1,'2026-02-24 15:28:49','2026-02-24 15:28:49','2026-02-24 15:28:49'),(2,'信用体系规则','信用分规则说明...',3,0,1,'2026-02-24 15:28:49','2026-02-24 15:28:49','2026-02-24 15:28:49'),(3,'平台系统升级公告','本周六凌晨2-6点系统升级',1,0,1,'2026-02-24 16:38:35','2026-02-24 16:38:35','2026-02-24 16:38:35'),(4,'618活动预告','618大促即将开始',2,0,0,NULL,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(5,'平台系统升级公告','本周六凌晨2-6点系统升级',1,0,1,'2026-02-24 16:42:17','2026-02-24 16:42:17','2026-02-24 16:42:17'),(6,'618活动预告','618大促即将开始',2,0,0,NULL,'2026-02-24 16:42:17','2026-02-24 16:42:17');
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `mor_start_time` time DEFAULT NULL COMMENT '上午上班时间',
  `mor_end_time` time DEFAULT NULL COMMENT '上午下班时间',
  `aft_start_time` time DEFAULT NULL COMMENT '下午上班时间',
  `aft_end_time` time DEFAULT NULL COMMENT '下午下班时间',
  `total_work_time` decimal(3,1) DEFAULT NULL COMMENT '员工工作总时长',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门备注',
  `parent_id` int NOT NULL DEFAULT '0' COMMENT '父级部门id，0根部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0未删除，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (1,NULL,'运维部门',NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(2,NULL,'运维1部','06:00:00','11:30:00','13:00:00','17:00:00',9.5,'核心部门',1,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(3,NULL,'运维2部','06:00:00','10:30:00','13:30:00','17:30:00',8.5,NULL,1,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(4,NULL,'销售部门',NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(5,NULL,'销售1部','06:00:00','10:00:00','13:00:00','16:30:00',7.5,NULL,4,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(6,NULL,'销售2部','06:00:00','10:30:00','13:30:00','17:00:00',8.0,NULL,4,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(7,NULL,'财务部门',NULL,NULL,NULL,NULL,NULL,'财务',0,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(8,NULL,'财务1部','06:00:00','10:00:00','14:00:00','18:00:00',8.0,NULL,7,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(9,NULL,'财务2部','06:00:00','10:00:00','13:00:00','16:30:00',7.5,NULL,7,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(10,NULL,'财务3部','06:00:00','11:30:00','13:00:00','16:30:00',9.0,NULL,7,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(11,NULL,'开发部门',NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(12,NULL,'开发1部','06:00:00','12:00:00','13:30:00','17:30:00',10.0,NULL,11,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(13,NULL,'开发2部','06:00:00','11:00:00','14:00:00','17:30:00',8.5,NULL,11,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(14,NULL,'财务4部','06:00:00','10:30:00','14:30:00','18:00:00',8.0,NULL,7,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(15,NULL,'运维3部','06:00:00','12:00:00','13:30:00','18:00:00',10.5,NULL,1,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(16,NULL,'人事部门',NULL,NULL,NULL,NULL,NULL,'人力资源管理',0,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(17,NULL,'人事1部','07:00:00','11:00:00','13:30:00','17:30:00',8.0,NULL,16,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(18,NULL,'人事2部','06:10:00','10:40:00','13:40:00','17:10:00',8.0,NULL,16,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(19,NULL,'人事3部','06:10:00','10:20:00','13:30:00','17:00:00',7.7,NULL,16,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(20,NULL,'运维4部','06:00:00','11:00:00','13:00:00','17:30:00',9.5,NULL,1,'2024-03-31 13:30:35','2024-03-31 13:30:35',0),(21,NULL,'办公室',NULL,NULL,NULL,NULL,NULL,NULL,0,'2024-03-31 15:32:49','2024-03-31 15:32:49',0),(22,NULL,'总经理办公室','09:30:00','10:00:00','15:00:00','16:30:00',2.0,NULL,21,'2024-03-31 15:33:31','2024-03-31 15:33:31',0),(23,NULL,'管理员','09:30:00','10:00:00','15:00:00','16:30:00',2.0,NULL,21,'2024-03-31 15:38:26','2024-03-31 15:38:26',0);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sal_salary_deduct`
--

DROP TABLE IF EXISTS `sal_salary_deduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sal_salary_deduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `type_num` int DEFAULT NULL COMMENT '扣款类型，0迟到，1早退，2旷工，3休假',
  `deduct` int NOT NULL DEFAULT '0' COMMENT '每次扣款金额',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='工资扣除表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sal_salary_deduct`
--

LOCK TABLES `sal_salary_deduct` WRITE;
/*!40000 ALTER TABLE `sal_salary_deduct` DISABLE KEYS */;
INSERT INTO `sal_salary_deduct` VALUES (1,2,2,200,'旷工绝不姑息','2024-03-31 13:30:19','2024-03-31 13:30:19',0),(2,2,1,0,NULL,'2024-03-31 13:30:19','2024-03-31 13:30:19',0),(3,2,3,0,NULL,'2024-03-31 13:30:19','2024-03-31 13:30:19',0),(4,2,0,100,NULL,'2024-03-31 13:30:19','2024-03-31 13:30:19',0);
/*!40000 ALTER TABLE `sal_salary_deduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `att_leave`
--

DROP TABLE IF EXISTS `att_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `att_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `days` int DEFAULT NULL COMMENT '休假天数',
  `type_num` tinyint(1) DEFAULT NULL COMMENT '休假类型',
  `status` tinyint(1) DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='请假表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `att_leave`
--

LOCK TABLES `att_leave` WRITE;
/*!40000 ALTER TABLE `att_leave` DISABLE KEYS */;
INSERT INTO `att_leave` VALUES (1,2,40,1,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(2,2,4,2,0,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(3,3,NULL,1,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(4,3,NULL,4,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(5,3,NULL,5,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(6,2,3,4,0,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(7,2,10,0,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(8,2,4,3,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(9,5,10,2,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(10,5,10,4,0,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(11,5,10,0,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(12,5,30,1,0,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(13,5,10,3,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(14,12,10,2,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(15,12,10,4,0,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(16,12,10,0,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(17,2,2,5,1,NULL,'2024-03-31 13:29:24','2024-03-31 13:29:24',0),(18,17,5,0,1,NULL,'2024-03-31 16:25:15','2024-03-31 16:25:15',0),(19,17,5,1,1,NULL,'2024-03-31 16:25:22','2024-03-31 16:25:22',0),(20,17,5,2,1,NULL,'2024-03-31 16:25:26','2024-03-31 16:25:26',0),(21,17,5,3,1,NULL,'2024-03-31 16:25:30','2024-03-31 16:25:30',0),(22,17,5,4,1,NULL,'2024-03-31 16:25:35','2024-03-31 16:25:35',0),(23,17,5,5,1,NULL,'2024-03-31 16:25:38','2024-03-31 16:25:38',0),(24,22,5,0,1,NULL,'2024-03-31 16:26:05','2024-03-31 16:26:05',0),(25,22,5,1,1,NULL,'2024-03-31 16:26:09','2024-03-31 16:26:09',0),(26,22,5,2,1,NULL,'2024-03-31 16:26:13','2024-03-31 16:26:13',0),(27,22,5,3,1,NULL,'2024-03-31 16:26:16','2024-03-31 16:26:16',0),(28,22,5,4,1,NULL,'2024-03-31 16:26:20','2024-03-31 16:26:20',0),(29,22,5,5,1,NULL,'2024-03-31 16:26:23','2024-03-31 16:26:23',0),(30,23,5,0,1,NULL,'2024-03-31 16:27:05','2024-03-31 16:27:05',0),(31,23,5,1,1,NULL,'2024-03-31 16:27:08','2024-03-31 16:27:08',0),(32,23,5,2,1,NULL,'2024-03-31 16:27:12','2024-03-31 16:27:12',0),(33,23,5,3,1,NULL,'2024-03-31 16:27:35','2024-03-31 16:27:35',0),(34,23,5,4,1,NULL,'2024-03-31 16:27:39','2024-03-31 16:27:39',0),(35,23,5,5,1,NULL,'2024-03-31 16:27:42','2024-03-31 16:27:42',0);
/*!40000 ALTER TABLE `att_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `type` tinyint NOT NULL COMMENT '类型：1-满减券 2-折扣券',
  `condition_amount` decimal(10,2) DEFAULT NULL COMMENT '满减条件',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '减免金额',
  `discount_rate` decimal(3,2) DEFAULT NULL COMMENT '折扣率',
  `total_count` int NOT NULL COMMENT '发放总量',
  `remaining_count` int NOT NULL COMMENT '剩余数量',
  `per_limit` int NOT NULL DEFAULT '1' COMMENT '每人限领',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-下架 1-上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_coupon`
--

DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '领取记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-未使用 1-已使用 2-已过期',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT NULL COMMENT '使用订单ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_coupon` (`user_id`,`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_coupon`
--

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_docs`
--

DROP TABLE IF EXISTS `sys_docs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_docs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件类型',
  `old_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件的原名称',
  `md5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件md5信息',
  `size` bigint DEFAULT NULL COMMENT '文件大小KB',
  `staff_id` int DEFAULT NULL COMMENT '文件上传者id',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除，默认为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_docs`
--

LOCK TABLES `sys_docs` WRITE;
/*!40000 ALTER TABLE `sys_docs` DISABLE KEYS */;
INSERT INTO `sys_docs` VALUES (52,'7cac1b294047f2b76ab2.jpg','jpg','07.jpg','507704f05fbca53793bce9970b40e6c8',175,1,NULL,'2024-03-31 13:30:42','2024-03-31 13:30:42',0);
/*!40000 ALTER TABLE `sys_docs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `per_menu`
--

DROP TABLE IF EXISTS `per_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单路径',
  `parent_id` int NOT NULL DEFAULT '0' COMMENT '父菜单id，0代表根菜单，默认0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_menu`
--

LOCK TABLES `per_menu` WRITE;
/*!40000 ALTER TABLE `per_menu` DISABLE KEYS */;
INSERT INTO `per_menu` VALUES (1,'staff','员工管理','team','/staff',5,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(2,'docs','文件管理','paper','/docs',5,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(3,'role','角色管理','user3','/role',6,'','2024-03-31 13:29:43','2024-03-31 13:29:43',0),(4,'menu','菜单管理','list','/menu',6,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(5,'system','系统管理','setting','/system',0,'','2024-03-31 13:29:43','2024-03-31 13:29:43',0),(6,'permission','权限管理','shield','/permission',0,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(13,'department','部门管理','building','/department',5,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(14,'attendance','考勤管理','refresh','/attendance',0,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(15,'insurance','五险一金','mark1','/insurance',17,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(16,'salary','薪资管理','shield','/salary',17,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(17,'money','财务管理','paper','/money',0,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(18,'city','参保城市','building','/city',17,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(19,'leave','请假审批','yes','/leave',14,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0),(20,'performance','考勤表现','user2','/performance',14,NULL,'2024-03-31 13:29:43','2024-03-31 13:29:43',0);
/*!40000 ALTER TABLE `per_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `per_role`
--

DROP TABLE IF EXISTS `per_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `code` varchar(20) CHARACTER SET utf16 COLLATE utf16_general_ci DEFAULT NULL COMMENT '角色编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_role`
--

LOCK TABLES `per_role` WRITE;
/*!40000 ALTER TABLE `per_role` DISABLE KEYS */;
INSERT INTO `per_role` VALUES (1,'superadmin','管理员','','2024-03-31 13:29:48','2024-03-31 13:29:48',0),(11,'money','财务',NULL,'2024-03-31 15:08:53',NULL,0),(12,'hr','人力',NULL,'2024-03-31 15:09:12',NULL,0),(13,'admin','总经理',NULL,'2024-03-31 15:31:33','2024-03-31 15:47:08',0),(14,'111','111',NULL,'2024-03-31 23:51:27','2024-03-31 23:51:27',0);
/*!40000 ALTER TABLE `per_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint DEFAULT NULL COMMENT '发送者ID（null表示系统）',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `type` tinyint NOT NULL COMMENT '消息类型：1-系统通知 2-订单消息 3-互动消息 4-私信',
  `title` varchar(200) DEFAULT NULL COMMENT '消息标题',
  `content` text NOT NULL COMMENT '消息内容',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读：0-否 1-是',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `role` tinyint DEFAULT '1' COMMENT '角色：1-普通用户 2-商家 3-管理员',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-冻结 1-正常 -1-已注销',
  `merchant_status` tinyint DEFAULT '0' COMMENT '商家状态：0-待审核 1-正常 2-驳回',
  `credit_score` int DEFAULT '600' COMMENT '信用分',
  `verified` tinyint(1) DEFAULT '0' COMMENT '是否实名认证',
  `consignment_enabled` tinyint(1) DEFAULT '0' COMMENT '寄售功能是否开启',
  `consignment_expiry` datetime DEFAULT NULL COMMENT '寄售资质有效期',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  KEY `idx_username` (`username`),
  KEY `idx_phone` (`phone`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`),
  KEY `idx_credit_score` (`credit_score`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','0192023a7bbd73250516f069df18b500',NULL,NULL,'13800000000',NULL,3,1,0,800,0,0,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL),(2,'nike_official','0192023a7bbd73250516f069df18b500',NULL,NULL,'13800000001',NULL,2,1,1,780,0,0,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL),(3,'adidas_official','0192023a7bbd73250516f069df18b500',NULL,NULL,'13800000002',NULL,2,1,1,780,0,0,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL),(4,'张三','0192023a7bbd73250516f069df18b500',NULL,NULL,'13800000003',NULL,1,1,0,620,0,0,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL),(5,'李四','0192023a7bbd73250516f069df18b500',NULL,NULL,'13800000004',NULL,1,1,0,585,0,0,NULL,NULL,NULL,'2026-02-24 15:28:49',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(200) DEFAULT NULL COMMENT '分类描述',
  `sort` int DEFAULT '0' COMMENT '排序值（越小越靠前）',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-启用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `idx_status` (`status`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社区板块分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'跑步','跑步爱好者交流区',1,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(2,'篮球','篮球装备讨论区',2,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(3,'足球','足球装备交流区',3,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(4,'健身','健身器材讨论区',4,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(5,'骑行','骑行装备交流区',5,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(6,'游泳','游泳装备讨论区',6,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(7,'户外','户外运动装备区',7,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(8,'瑜伽','瑜伽健身交流区',8,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(9,'羽毛球','羽毛球装备讨论区',9,1,'2026-02-24 16:41:49','2026-02-24 16:41:49'),(10,'乒乓球','乒乓球装备交流区',10,1,'2026-02-24 16:41:49','2026-02-24 16:41:49');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `per_staff_role`
--

DROP TABLE IF EXISTS `per_staff_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `per_staff_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `per_staff_role`
--

LOCK TABLES `per_staff_role` WRITE;
/*!40000 ALTER TABLE `per_staff_role` DISABLE KEYS */;
INSERT INTO `per_staff_role` VALUES (1,1,1,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(2,29,2,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(3,29,3,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(4,3,3,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(5,3,9,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(6,3,5,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(7,31,9,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(8,1,9,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(9,6,9,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(10,6,2,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(11,9,7,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(12,2,9,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(13,3,6,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(14,3,8,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(15,2,2,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(16,1,4,0,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(17,2,4,1,'2024-03-31 13:29:59','2024-03-31 13:29:59',0),(18,38,1,0,'2024-03-31 15:34:38','2024-03-31 15:34:42',0),(19,38,13,0,'2024-03-31 15:34:42','2024-03-31 15:38:48',0),(20,38,12,1,'2024-03-31 15:38:48',NULL,0),(21,39,11,1,'2024-03-31 15:39:39',NULL,0),(22,2,1,0,'2024-03-31 15:48:21','2024-03-31 15:54:05',0),(23,2,13,1,'2024-03-31 15:54:05',NULL,0);
/*!40000 ALTER TABLE `per_staff_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` bigint NOT NULL COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `parent_id` bigint DEFAULT NULL COMMENT '父评论ID',
  `reply_to_user_id` bigint DEFAULT NULL COMMENT '回复目标用户ID',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-正常 1-屏蔽',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1,3,NULL,NULL,'推荐亚瑟士的跑鞋，很不错',0,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(2,1,4,NULL,NULL,'我觉得耐克的飞马系列也不错',0,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(3,2,2,NULL,NULL,'这篇文章写得很详细',1,0,'2026-02-24 16:38:35','2026-02-24 16:38:35'),(4,1,3,NULL,NULL,'推荐亚瑟士的跑鞋，很不错',0,0,'2026-02-24 16:42:17','2026-02-24 16:42:17'),(5,1,4,NULL,NULL,'我觉得耐克的飞马系列也不错',0,0,'2026-02-24 16:42:17','2026-02-24 16:42:17'),(6,2,2,NULL,NULL,'这篇文章写得很详细',1,0,'2026-02-24 16:42:17','2026-02-24 16:42:17');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_spec`
--

DROP TABLE IF EXISTS `product_spec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_spec` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规格ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `spec_name` varchar(50) NOT NULL COMMENT '规格名称（如：颜色、尺寸）',
  `spec_value` varchar(50) NOT NULL COMMENT '规格值',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `price` decimal(10,2) DEFAULT NULL COMMENT '规格价格（可为空，为空则使用商品价格）',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_spec`
--

LOCK TABLES `product_spec` WRITE;
/*!40000 ALTER TABLE `product_spec` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_spec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_staff`
--

DROP TABLE IF EXISTS `sys_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_staff` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工姓名',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别，0男，1女，默认0',
  `pwd` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工密码',
  `avatar` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工头像',
  `birthday` date DEFAULT NULL COMMENT '员工生日',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工电话',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工备注',
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '员工状态，0异常，1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_staff`
--

LOCK TABLES `sys_staff` WRITE;
/*!40000 ALTER TABLE `sys_staff` DISABLE KEYS */;
INSERT INTO `sys_staff` VALUES (1,'superadmin','管理员',0,'E10A83EE057F20F8DC3949BA59ABBE56','7cac1b294047f2b76ab2.jpg','2000-12-05','13344444444','北京市朝阳区','系统管理员，拥有最高权限',23,1,'2024-03-31 13:29:02','2024-03-31 16:32:38',0),(2,'admin','张三',1,'E10A83EE057F20F8DC3949BA59ABBE56','','1998-04-17','13344444444','北京市海淀区',NULL,22,1,'2024-03-31 13:29:02','2024-03-31 15:42:17',0),(38,'user','陈小明',0,'E10A83EE057F20F8DC3949BA59ABBE56',NULL,'1996-02-23','15977777777','北京市海淀区',NULL,17,1,'2024-03-31 15:32:36','2024-03-31 15:45:50',0),(39,'user2','王小红',1,'E10A83EE057F20F8DC3949BA59ABBE56',NULL,'2000-06-06','19977777777','北京市朝阳区',NULL,8,1,'2024-03-31 15:39:22','2024-03-31 15:45:52',0);
/*!40000 ALTER TABLE `sys_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `buyer_id` bigint NOT NULL COMMENT '买家ID',
  `seller_id` bigint NOT NULL COMMENT '卖家ID',
  `seller_type` tinyint NOT NULL COMMENT '卖家类型：1-商家 2-个人',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) NOT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `spec_id` bigint DEFAULT NULL COMMENT '规格ID',
  `spec_desc` varchar(100) DEFAULT NULL COMMENT '规格描述',
  `price` decimal(10,2) NOT NULL COMMENT '成交单价',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '购买数量',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `order_type` tinyint NOT NULL DEFAULT '1' COMMENT '订单类型：1-销售订单 2-交换订单',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待付款 1-待发货 2-待收货 3-已完成 4-退款中 5-已退款 6-已取消',
  `logistics_no` varchar(50) DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(50) DEFAULT NULL COMMENT '物流公司',
  `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `receiver_address` varchar(200) NOT NULL COMMENT '收货地址',
  `buyer_remark` varchar(500) DEFAULT NULL COMMENT '买家备注',
  `seller_remark` varchar(500) DEFAULT NULL COMMENT '卖家备注',
  `close_reason` varchar(200) DEFAULT NULL COMMENT '关闭原因',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `ship_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_seller_id` (`seller_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `after_sale`
--

DROP TABLE IF EXISTS `after_sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `after_sale` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '售后ID',
  `after_sale_no` varchar(32) NOT NULL COMMENT '售后单号',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `exchange_order_id` bigint DEFAULT NULL COMMENT '关联交换订单ID',
  `user_id` bigint NOT NULL COMMENT '申请人ID',
  `type` tinyint NOT NULL COMMENT '售后类型：1-退款 2-退货退款 3-换货',
  `reason` varchar(500) NOT NULL COMMENT '申请原因',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待处理 1-卖家同意 2-卖家拒绝 3-平台仲裁中 4-已完成 5-已关闭',
  `evidence_images` json DEFAULT NULL COMMENT '凭证图片',
  `seller_response` varchar(500) DEFAULT NULL COMMENT '卖家回复',
  `arbitration_result` varchar(500) DEFAULT NULL COMMENT '仲裁结果',
  `arbitration_time` datetime DEFAULT NULL COMMENT '仲裁时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `after_sale_no` (`after_sale_no`),
  KEY `idx_after_sale_no` (`after_sale_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='售后表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `after_sale`
--

LOCK TABLES `after_sale` WRITE;
/*!40000 ALTER TABLE `after_sale` DISABLE KEYS */;
INSERT INTO `after_sale` VALUES (1,'AF202402240001',3,NULL,4,1,'商品与描述不符',1200.00,0,NULL,NULL,NULL,NULL,NULL,'2026-02-24 16:38:35','2026-02-24 16:38:35');
/*!40000 ALTER TABLE `after_sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_wish`
--

DROP TABLE IF EXISTS `user_wish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_wish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '心愿ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_name` varchar(100) NOT NULL COMMENT '期望装备名称',
  `brand` varchar(50) DEFAULT NULL COMMENT '期望品牌',
  `category` varchar(50) DEFAULT NULL COMMENT '期望分类',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户心愿单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_wish`
--

LOCK TABLES `user_wish` WRITE;
/*!40000 ALTER TABLE `user_wish` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_wish` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-24 17:47:04
