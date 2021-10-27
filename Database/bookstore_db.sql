-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	8.0.25

CREATE SCHEMA IF NOT EXISTS `bookstore`;
use `bookstore`;
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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(45) DEFAULT NULL,
  `district` varchar(45) DEFAULT NULL,
  `ward` varchar(45) DEFAULT NULL,
  `street` varchar(45) DEFAULT NULL,
  `customer_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpkaif5e5vy013h0spva59jpfd` (`customer_id`),
  CONSTRAINT `FKpkaif5e5vy013h0spva59jpfd` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'HCM','1','da kao','nguyen trai',2),(2,'HCM','3','13','le van sy',1),(3,'HCM','5','6','nguyen van cu',3),(4,'HCM','Tan Binh','12','Truong Chinh',2);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `photo` varchar(45) DEFAULT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `parent_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_parent_id_idx` (`parent_id`),
  FULLTEXT KEY `fulltextsearch_by_category_name` (`name`),
  CONSTRAINT `fk_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (2,'All','ALPR',NULL,NULL,1,2),(3,'Sách Thiếu Nhi','STTN',NULL,NULL,1,10),(8,'Văn Học','SVH',NULL,NULL,1,10),(9,'Sách Tâm Lý','STL',NULL,NULL,1,10),(10,'Sách Tiếng Việt','STV',NULL,NULL,1,NULL),(11,'Sách Ngoại Văn','SNV',NULL,NULL,1,NULL),(12,'Kinh Tế','SKT',NULL,NULL,1,10),(14,'Tiểu Thuyêt','STTH',NULL,NULL,1,8),(15,'Truyện Ngắn','TRN',NULL,NULL,1,8),(16,'Ngôn Tình','NNT',NULL,NULL,1,8),(17,'Truyện Tranh','TRTR',NULL,NULL,1,8),(18,'Truyện Thiếu Nhi','TRTN',NULL,NULL,1,3),(19,'Tô Màu - Luyện Chữ','TMLC',NULL,NULL,1,3),(20,'Từ Điển Thiếu Nhi','TDTN',NULL,NULL,1,3),(21,'Quản Trị - Lãnh Đạo','QTLD',NULL,NULL,1,12),(22,'Marketing','MKT',NULL,NULL,1,12),(23,'Nhân Sự - Việc Làm','NSVL',NULL,NULL,1,12),(24,'Rèn Luyện Nhân Cách','RLNC',NULL,NULL,1,9),(25,'Sách Cho Tuổi Mới Lớn','STML',NULL,NULL,1,9);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  `photo_url` varchar(45) DEFAULT NULL,
  `customer_rank` varchar(45) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `last_login` varchar(45) DEFAULT NULL,
  `email_verified` tinyint DEFAULT '0',
  `verification_code` varchar(256) DEFAULT NULL,
  `auth_provider` varchar(45) DEFAULT NULL,
  `enabled` tinyint DEFAULT '1',
  `date_of_birth` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'longnguyen25089@gmail.com','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG','Long','Nguyen','0123456789','customer-photosslash1slashimages.jpg',NULL,NULL,NULL,NULL,NULL,NULL,1,NULL),(2,'antran@gmail.com','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG','An','Tran','0987654321',NULL,NULL,NULL,NULL,0,NULL,NULL,1,NULL),(3,'vanhoang@gmail.com','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG','Van','Hoang','0546231879',NULL,NULL,NULL,NULL,0,NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL,
  `order_date` datetime DEFAULT NULL,
  `total_payable` double NOT NULL,
  `status` varchar(45) NOT NULL,
  `customer_invoice_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm1m61xamqjc8ypwnawig1dew8` (`customer_invoice_id`),
  CONSTRAINT `FKm1m61xamqjc8ypwnawig1dew8` FOREIGN KEY (`customer_invoice_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,'1635258518507',NULL,114000,'DELIVERING',1),(2,'1635258551166',NULL,440000,'NEW',1);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_details`
--

DROP TABLE IF EXISTS `invoice_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quantity` int NOT NULL,
  `item_total` double NOT NULL,
  `invoice_detail_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfhhcrner4quarjce3dq6rygpl` (`invoice_detail_id`),
  KEY `FKchhydd0d280ruig3hmars76wa` (`product_id`),
  CONSTRAINT `FKchhydd0d280ruig3hmars76wa` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKfhhcrner4quarjce3dq6rygpl` FOREIGN KEY (`invoice_detail_id`) REFERENCES `invoice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_details`
--

LOCK TABLES `invoice_details` WRITE;
/*!40000 ALTER TABLE `invoice_details` DISABLE KEYS */;
INSERT INTO `invoice_details` VALUES (1,3,114000,1,41),(2,4,80000,2,9),(3,2,360000,2,13);
/*!40000 ALTER TABLE `invoice_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins` (
  `series` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
INSERT INTO `persistent_logins` VALUES ('752EcJQGdRxP9u4tifrstA==','admin','0GfkNusiDQ38KK3vGoMtVQ==','2021-10-26 14:31:42'),('7L9hGNuVj/pMyZ9HyijLlw==','admin','LdRCVGkeG7Gnugwavf5mkg==','2021-10-26 14:57:19');
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `code` varchar(45) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `category_id` int NOT NULL,
  `photo` varchar(256) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `sale_price` float DEFAULT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `status` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_id_idx` (`category_id`),
  FULLTEXT KEY `fulltextsearch_product_name` (`name`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (5,'Rồng Đỏ','BS001',NULL,14,'product-photosslash1slashrong do.jpg',50,109000,98100,1,NULL),(6,'Sự Im Lặng Của Bầy Cừu','BS002',NULL,14,'product-photosslash2slashsu im lang cua bay cuu.jpg',50,115000,103500,1,NULL),(7,'Hannibal','BS003',NULL,14,'product-photosslash3slashHannibal.jpg',50,120000,108000,1,NULL),(8,'Hannibal Trỗi Dậy','BS004',NULL,14,'product-photosslash6slashhannibal troi day.jpg',50,138000,124200,1,NULL),(9,'Thám Tử Lừng Danh Conan - Tập 98','BS005',NULL,17,'product-photosslash7slashconan tap 98.jpg',50,20000,19000,1,NULL),(10,'Nhà Giả Kim','BS006',NULL,14,'product-photosslash8slashnha gia kim.jpg',50,69000,69000,1,NULL),(11,'12 Năm Nô Lệ','BS007',NULL,14,'product-photosslash9slash12 nam no le.jpg',50,82000,73800,1,NULL),(12,'28','BS008',NULL,14,'product-photosslash10slash28.jpg',50,154000,138600,1,NULL),(13,'13.67','BS009',NULL,14,'product-photosslash11slash13.67.jpg',50,180000,162000,1,NULL),(14,'15 Năm Chờ Đợi Chim Di Trú','BS010',NULL,14,'product-photosslash12slash15 nam cho doi chim di tru.jpg',50,119000,107100,1,NULL),(15,'Anh Meaulnes','BS011',NULL,14,'product-photosslash13slashanh meaulnes.jpg',50,99000,89100,1,NULL),(16,'Bà Già Trúng Quá','BS012',NULL,14,'product-photosslash14slashba gia trung qua.jpg',50,136000,122400,1,NULL),(17,'Người Trong Đau Khổ Vẫn Cười','BS013',NULL,15,'product-photosslash15slashnguoi trong dau kho van cuoi.jpg',50,69000,NULL,1,NULL),(18,'Duyên Nợ Thái Bình Dương','BS014',NULL,15,'product-photosslash16slashduyen no thai binh duong.jpg',50,99000,NULL,1,NULL),(19,'Hai - Mươi - Bảy','BS015',NULL,15,'product-photosslash17slashhai muoi bay.jpg',50,99000,NULL,1,NULL),(20,'Mình Lại Yêu Nhau... Thêm Một Lần Nữa','BS016',NULL,15,'product-photosslash18slashminh lai yeu nhau them mot lan nua.jpg',50,88000,NULL,1,NULL),(21,'Có Người Sực Tỉnh Cơn Mơ','BS017',NULL,15,'product-photosslash19slashco nguoi suc tinh con mo.jpg',50,79000,NULL,1,NULL),(22,'Lạc Trong Miền Nhớ','BS018',NULL,15,'product-photosslash20slashlac trong mien nho.jpg',50,62000,NULL,1,NULL),(23,'Môi - Một Người Đàn Bà','BS019',NULL,15,'product-photosslash21slashmoi mot nguoi dan ba.jpg',50,60000,NULL,1,NULL),(24,'Mùa Hè Năm Ấy','BS020',NULL,15,'product-photosslash22slashmua he nam ay.jpg',50,90000,NULL,1,NULL),(25,'Chỉ Còn Đây Nỗi Nhớ','BS021',NULL,15,'product-photosslash23slashchi con day noi nho.jpg',50,65000,NULL,1,NULL),(26,'Nhà Có Hai Người (Tái Bản 2020)','BS022',NULL,15,'product-photosslash24slashnha co hai nguoi.jpg',50,139000,NULL,1,NULL),(27,'Ai Hiểu Được Lòng Em','BS023',NULL,16,'product-photosslash25slashai hieu duoc long em.jpg',50,140000,126000,1,NULL),(28,'Ai Ngọt Ngào Hơn','BS024',NULL,16,'product-photosslash26slashai ngot ngao hon.jpg',50,64000,51200,1,NULL),(29,'Ai Quyến Rũ Ai','BS025',NULL,16,'product-photosslash27slashai quyen ru ai.jpg',50,79000,71100,1,NULL),(30,'Anh Ấy Là Của Tôi','BS026',NULL,16,'product-photosslash28slashanh ay la cua toi.jpg',50,469000,152100,1,NULL),(31,'Bất Khả Kháng Lực','BS027',NULL,16,'product-photosslash29slashbat kha khang luc.jpg',50,93000,83700,1,NULL),(32,'Spy X Family - Tập 2','BS028',NULL,17,'product-photosslash30slashspy x famoly tap 2.jpg',50,25000,23750,1,NULL),(33,'Chang Hoang Dã - Gấu','BS029',NULL,17,'product-photosslash31slashchamg hoang da.jpg',50,160000,152000,1,NULL),(34,'Barakamon - Tập 14','BS030',NULL,17,'product-photosslash32slashbarakamon tap 14.jpg',50,40000,36000,1,NULL),(35,'\"Cậu\" Ma Nhà Xí Hanako - Tập 7','BS031',NULL,17,'product-photosslash33slashcau ma nha xi hanako tap 7.jpg',50,28000,26600,1,NULL),(36,'100 Gương Mặt Của Mẹ','BS032',NULL,18,'product-photosslash34slash100 guong mat cua me.jpg',50,30000,28500,1,NULL),(37,'100 Truyện Cổ Tích Việt Nam','BS033',NULL,18,'product-photosslash35slash100 truyen co tich viet nam.jpg',50,160000,128000,1,NULL),(38,'100 Truyện Hay Rèn Đức Tính Tốt','BS034',NULL,18,'product-photosslash36slash100 truyen hay ren duc tinh tot.jpg',50,65000,58500,1,NULL),(39,'101 Truyện Cổ Tích Chọn Lọc','BS035',NULL,18,'product-photosslash37slash101 truyen co tich chá»n loc.jpg',50,65000,58500,1,NULL),(40,'101 Truyện Kể Hay Nhất','BS036',NULL,18,'product-photosslash38slash101 truyen ke hay nhat.jpg',50,150000,135000,1,NULL),(41,'Ai Vẽ Xa Giỏi Nhất','BS037',NULL,19,'product-photosslash39slashai va xa gioi nhat.jpg',50,38000,36100,1,NULL),(42,'Bé Chơi Với Bút Màu - Rô - Bốt','BS038',NULL,19,'product-photosslash40slashbé choi voi but mua ro bot.jpg',50,15000,13500,1,NULL),(43,'Bé Học Đếm (Tái Bản)','BS039',NULL,19,'product-photosslash41slashbe hoc dem.jpg',50,34200,30780,1,NULL),(44,'Bé Học Vẽ','BS040',NULL,19,'product-photosslash42slashbe hoc ve.jpg',50,39000,NULL,1,NULL),(45,'Bé Tập Tạo Hình Nhà Trẻ','BS041',NULL,19,'product-photosslash43slashbe tap tao hinh nha tre.jpg',50,9000,NULL,1,NULL),(46,'100 Từ Trung - Việt Đầu Tiên','BS042',NULL,20,'product-photosslash44slash100 tu trung viet dau tien.jpg',50,49000,44100,1,NULL),(47,'365 Kì Quan Thế Giới','BS043',NULL,20,'product-photosslash45slash365 ki quan the gioi.jpg',50,220000,198000,1,NULL),(48,'Động Vật Kỳ Thú - 100 Từ Đầu Đời Của Bé','BS044',NULL,20,'product-photosslash46slashdong vat ky thu 100 tu dau doi cua be.jpg',50,109000,98100,1,NULL),(49,'Tớ Chọn Người Mẹ Này (2+)','BS045',NULL,20,'product-photosslash47slashto chon nguoi me nay.jpg',50,55000,49500,1,NULL),(50,'Từ Điển Bằng Hình - Thế Giới Động Vật','BS046',NULL,20,'product-photosslash48slashtu dien bang hinh.jpg',50,48000,NULL,1,NULL),(51,'10 Nguyên Tắc Vàng Của Nhà Lãnh Đạo','BS047',NULL,21,'product-photosslash49slash10 nguyen tac vang cua nha lanh dao.jpg',50,80000,72000,1,NULL),(52,'Bản Lĩnh Người Làm Giám Đốc','BS048',NULL,21,'product-photosslash50slashban linh nguoi lam giam doc.jpg',50,45000,40500,1,NULL),(53,'Bí Quyết Dùng Người','BS049',NULL,21,'product-photosslash51slashbi quyet dung nguoi.jpg',50,75000,67500,1,NULL),(54,'Các Công Thức Và Hệ Số Kinh Doanh','BS050',NULL,21,'product-photosslash52slashcac cong thuc va he so kinh doanh.jpg',50,129000,NULL,1,NULL),(55,'Cẩm Nang Quản Lý Và CEO','BS051',NULL,21,'product-photosslash53slashcam nang quan ly va ceo.jpg',50,138000,124200,1,NULL),(56,'100 Ý Tưởng Bán Hàng Tuyệt Hay','BS052',NULL,22,'product-photosslash54slash10 y tuong ban hang tuyet hay.jpg',50,77000,69300,1,NULL),(57,'60 Giây Vàng Trong Bán Hàng','BS053',NULL,22,'product-photosslash55slash60 giay vang trong ban hang.jpg',50,168000,151200,1,NULL),(58,'Bán Được Hàng Hay Là Chết','BS054',NULL,22,'product-photosslash56slashban duoc hang hay la chet.jpg',50,158000,142200,1,NULL),(59,'Cẩm Nang Truyền Thông Xã Hội B2B','BS055',NULL,22,'product-photosslash57slashcam nang truyen thong xa hoi.jpg',50,120000,108000,1,NULL),(60,'Chiến Lược Bán Hàng','BS056',NULL,22,'product-photosslash58slashchien luoc ban hang.jpg',50,100000,NULL,1,NULL),(61,'Bí Quyết Tuyển Dụng & Đãi Ngộ Người Tài','BS057',NULL,23,'product-photosslash59slashbi quyet tuyen dung va dai ngo ngÆ°á»i tai.jpg',50,54000,48600,1,NULL),(62,'Inbound PR','BS058',NULL,23,'product-photosslash60slashinbound pr.jpg',50,108000,97200,1,NULL),(63,'Nhân Sự Cốt Cán','BS059',NULL,23,'product-photosslash61slashnhan su cot can.jpg',50,159000,143100,1,NULL),(64,'Tuyệt Chiêu Tuyển Dụng','BS060',NULL,23,'product-photosslash62slashtuyet chieu tuyen dung.jpg',50,89000,80100,1,NULL),(65,'Tinh Anh Công Sở 4.0','BS061',NULL,23,'product-photosslash63slashtinh anh cong so.jpg',50,112000,100800,1,NULL),(66,'1001 Lời Hay - Ý Đẹp','BS062',NULL,24,'product-photosslash64slash1001 loi hay y dep.jpg',50,30000,27000,1,NULL),(67,'15 Gương Phụ Nữ','BS063',NULL,24,'product-photosslash65slash15 Guong phu nu.jpg',50,99000,89100,1,NULL),(68,'Bách Khoa Toàn Thư - Động Vật','BS064',NULL,24,'product-photosslash66slashbach khoa toan thu - dong vat.jpg',50,60000,54000,1,NULL),(69,'Bàn Về Hạnh Phúc (Tái Bản)','BS065',NULL,24,'product-photosslash67slashban ve hanh phuc.jpg',50,79000,71100,1,NULL),(70,'Bí Kíp Giúp Bạn Cực Kì Hạnh Phúc','BS066',NULL,24,'product-photosslash68slashbi kip giup ban cuc ky hanh phuc.jpg',50,48000,45600,1,NULL),(71,'18 Trải Nghiệm Tuổi Teen (2018)','BS067',NULL,25,'product-photosslash69slash18 trai nghiem tuoi teen.jpg',50,49000,44100,1,NULL),(72,'39 Câu Hỏi Cho Người Trẻ','BS068',NULL,25,'product-photosslash70slash39 cau hoi cho nguoi tre.jpg',50,90000,NULL,1,NULL),(73,'Ảo Thuật Vui 07','BS069',NULL,25,'product-photosslash71slashao thuat vui.jpg',50,35000,31500,1,NULL),(74,'Âm Thanh Của Sắc Màu','BS070',NULL,25,'product-photosslash72slasham thanh cua sac mau.jpg',50,89000,84550,1,NULL),(75,'Bí Mật Của Con Gái','BS071',NULL,25,'product-photosslash73slashbi mat cua con gai.jpg',50,30000,27000,1,NULL),(76,'Heads Up Money','BS072',NULL,11,'product-photosslash83slashheads up money.jpg',50,330000,NULL,1,NULL),(77,'Dk Heads Up Sociology','BS073',NULL,11,'product-photosslash84slashdk heads up sociology.jpg',50,330000,NULL,1,NULL),(78,'Factfulness','BS074',NULL,11,'product-photosslash85slashfactfulness.jpg',50,264000,NULL,1,NULL),(79,'Atomic Habits','BS075',NULL,11,'product-photosslash86slashatomic habits.jpg',50,423000,NULL,1,NULL),(80,'Secret Of Millionaire Mind','BS076',NULL,11,'product-photosslash87slashsecret Ã² millionaire mind.jpg',50,188000,185000,1,NULL),(81,'The Godfather (Paperback)','BS077',NULL,11,'product-photosslash88slashthe godfather.jpg',50,256400,NULL,1,NULL),(82,'The Scarlet Letter','BS078',NULL,11,'product-photosslash89slashthe scarlet letter.jpg',50,610000,579500,1,NULL),(83,'The Awakening & Other Stories','BS079',NULL,11,'product-photosslash90slashthe awakening & other stories.jpg',50,610000,579500,1,NULL),(84,'Treasury Of Best-Loved Fairy Tales','BS080',NULL,11,'product-photosslash91slashtreasury of best-loved fairy tales.jpg',50,1000000,950000,1,NULL),(85,'Charles Dickens','BS081',NULL,11,'product-photosslash92slashcharles dickens.jpg',50,1000000,950000,1,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  FULLTEXT KEY `fulltextsearch_by_role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN',NULL),(2,'PRODUCT',NULL),(3,'SALE',NULL),(4,'SHIPPER',NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,2),(2,3),(3,3),(4,4);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(64) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  `enabled` tinyint NOT NULL DEFAULT '1',
  `address` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `identity_number` varchar(12) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `fullltextsearch_by_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ADMIN','admin','$2a$10$mT3QTf6rV3EcE/5z0uwoE.vRNpQhunfkYTZDQQQ0C9iYesOd.btFO',1,'','','2021-10-26','026415631','0865415647'),(2,'GreenA','greena','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG',1,NULL,NULL,NULL,NULL,NULL),(3,'GreenB','greenb','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG',1,NULL,NULL,NULL,NULL,NULL),(4,'GreenC','greenc','$2a$10$K7eWEi998zLbvR4IztZBYOPbhev2D8X1k8Axbk.dJ6Tgzb7CzS5hG',1,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-26 22:03:28
