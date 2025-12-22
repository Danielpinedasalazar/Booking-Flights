-- MySQL dump 10.13  Distrib 9.4.0, for macos15.4 (arm64)
--
-- Host: localhost    Database: Airline
-- ------------------------------------------------------
-- Server version	9.4.0

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
-- Table structure for table `airports`
--

DROP TABLE IF EXISTS `airports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` enum('BOGOTA','DALLAS','LEEDS','LONDON','MEDELLIN','MIAMI') NOT NULL,
  `country` enum('COL','USA','UK') NOT NULL,
  `iata_code` varchar(3) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrck10qn096aw10ds8rjqf35ah` (`iata_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airports`
--

LOCK TABLES `airports` WRITE;
/*!40000 ALTER TABLE `airports` DISABLE KEYS */;
INSERT INTO `airports` VALUES (1,'MEDELLIN','COL','MED','Medellin Airport'),(2,'MIAMI','USA','MIA','Miami Airport'),(3,'BOGOTA','COL','BOG','El Dorado International Airport'),(5,'DALLAS','USA','DFW','Dallas Forth Worth'),(6,'LONDON','UK','LHR','London Heathrow'),(7,'LEEDS','UK','LBA','Leeds Bradford');
/*!40000 ALTER TABLE `airports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_date` datetime(6) DEFAULT NULL,
  `booking_reference` varchar(255) NOT NULL,
  `booking_status` enum('CANCELLED','CHECKED_IN','CHECKED_OUT','CONFIRMED','PENDING') DEFAULT NULL,
  `flight_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKe92mgyq35mdeo8gc1un2o6uk0` (`booking_reference`),
  KEY `FKidcytqkgq0ve4x1elcnbmdy8a` (`flight_id`),
  KEY `FKeyog2oic85xg7hsu2je2lx3s6` (`user_id`),
  CONSTRAINT `FKeyog2oic85xg7hsu2je2lx3s6` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKidcytqkgq0ve4x1elcnbmdy8a` FOREIGN KEY (`flight_id`) REFERENCES `flights` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (5,'2025-12-21 14:57:17.192909','71534498','CONFIRMED',7,10);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_notification`
--

DROP TABLE IF EXISTS `email_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `body` longtext,
  `is_html` bit(1) NOT NULL,
  `recipient_email` varchar(255) NOT NULL,
  `send_at` datetime(6) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `booking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2p6pts9ws80x00j0ow04yi1ls` (`booking_id`),
  CONSTRAINT `FK2p6pts9ws80x00j0ow04yi1ls` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_notification`
--

LOCK TABLES `email_notification` WRITE;
/*!40000 ALTER TABLE `email_notification` DISABLE KEYS */;
INSERT INTO `email_notification` VALUES (12,'<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Welcome to Daniel Airline!</title>\n    <style>\n        body {\n            font-family: \'Inter\', sans-serif;\n            background-color: #f4f7f6;\n            margin: 0;\n            padding: 20px;\n            color: #333;\n        }\n        .container {\n            max-width: 600px;\n            margin: 0 auto;\n            background-color: #ffffff;\n            border-radius: 12px;\n            overflow: hidden;\n            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n            border: 1px solid #e0e0e0;\n        }\n        .header {\n            background-color: #007bff;\n            color: #ffffff;\n            padding: 25px;\n            text-align: center;\n            border-top-left-radius: 12px;\n            border-top-right-radius: 12px;\n        }\n        .header h1 {\n            margin: 0;\n            font-size: 28px;\n            font-weight: 700;\n        }\n        .content {\n            padding: 30px;\n            line-height: 1.6;\n        }\n        .section-title {\n            font-size: 22px;\n            color: #007bff;\n            margin-bottom: 20px;\n            border-bottom: 2px solid #007bff;\n            padding-bottom: 10px;\n            font-weight: 600;\n        }\n        .button {\n            display: inline-block;\n            background-color: #28a745;\n            color: #ffffff;\n            padding: 12px 25px;\n            border-radius: 8px;\n            text-decoration: none;\n            font-weight: 600;\n            margin-top: 25px;\n            transition: background-color 0.3s ease;\n        }\n        .button:hover {\n            background-color: #218838;\n        }\n        .footer {\n            background-color: #f8f9fa;\n            padding: 20px;\n            text-align: center;\n            font-size: 14px;\n            color: #777;\n            border-bottom-left-radius: 12px;\n            border-bottom-right-radius: 12px;\n            border-top: 1px solid #e0e0e0;\n        }\n        .greeting {\n            font-size: 18px;\n            margin-bottom: 20px;\n        }\n    </style>\n</head>\n<body>\n<div class=\"container\">\n    <div class=\"header\">\n        <h1>Welcome Aboard!</h1>\n    </div>\n    <div class=\"content\">\n        <p class=\"greeting\">Hello <strong>Daniel Pineda</strong>,</p>\n\n        <p>Thank you for registering with Daniel Airline! We are thrilled to have you join our community.</p>\n\n        <p>You can now explore our wide range of flights, manage your bookings, and enjoy exclusive offers.</p>\n\n\n        <div style=\"text-align: center;\">\n            <a class=\"button\" href=\"http://localhost:3000/login\">Log In to Your Account</a>\n        </div>\n\n        <p style=\"margin-top: 30px;\">If you have any questions or need assistance, please do not hesitate to contact our\n            customer support team.</p>\n        <p>We look forward to helping you with your next journey!</p>\n    </div>\n    <div class=\"footer\">\n        Best regards,<br>\n        Daniel Airline Team<br>\n        <a href=\"[frontendLoginUrl]\" style=\"color: #007bff; text-decoration: none;\">www.danielairlines.com</a><br>\n        &copy; <span>2025</span> Daniel Airline. All rights\n        reserved.\n    </div>\n</div>\n</body>\n</html>',_binary '','danielpinedasalazar19@gmail.com','2025-12-17 17:07:19.320710','Welcome to Daniel Airlines',NULL),(13,'<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Welcome to Daniel Airline!</title>\n    <style>\n        body {\n            font-family: \'Inter\', sans-serif;\n            background-color: #f4f7f6;\n            margin: 0;\n            padding: 20px;\n            color: #333;\n        }\n        .container {\n            max-width: 600px;\n            margin: 0 auto;\n            background-color: #ffffff;\n            border-radius: 12px;\n            overflow: hidden;\n            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n            border: 1px solid #e0e0e0;\n        }\n        .header {\n            background-color: #007bff;\n            color: #ffffff;\n            padding: 25px;\n            text-align: center;\n            border-top-left-radius: 12px;\n            border-top-right-radius: 12px;\n        }\n        .header h1 {\n            margin: 0;\n            font-size: 28px;\n            font-weight: 700;\n        }\n        .content {\n            padding: 30px;\n            line-height: 1.6;\n        }\n        .section-title {\n            font-size: 22px;\n            color: #007bff;\n            margin-bottom: 20px;\n            border-bottom: 2px solid #007bff;\n            padding-bottom: 10px;\n            font-weight: 600;\n        }\n        .button {\n            display: inline-block;\n            background-color: #28a745;\n            color: #ffffff;\n            padding: 12px 25px;\n            border-radius: 8px;\n            text-decoration: none;\n            font-weight: 600;\n            margin-top: 25px;\n            transition: background-color 0.3s ease;\n        }\n        .button:hover {\n            background-color: #218838;\n        }\n        .footer {\n            background-color: #f8f9fa;\n            padding: 20px;\n            text-align: center;\n            font-size: 14px;\n            color: #777;\n            border-bottom-left-radius: 12px;\n            border-bottom-right-radius: 12px;\n            border-top: 1px solid #e0e0e0;\n        }\n        .greeting {\n            font-size: 18px;\n            margin-bottom: 20px;\n        }\n    </style>\n</head>\n<body>\n<div class=\"container\">\n    <div class=\"header\">\n        <h1>Welcome Aboard!</h1>\n    </div>\n    <div class=\"content\">\n        <p class=\"greeting\">Hello <strong>Daniel Pineda</strong>,</p>\n\n        <p>Thank you for registering with Daniel Airline! We are thrilled to have you join our community.</p>\n\n        <p>You can now explore our wide range of flights, manage your bookings, and enjoy exclusive offers.</p>\n\n\n        <div style=\"text-align: center;\">\n            <a class=\"button\" href=\"http://localhost:3000/login\">Log In to Your Account</a>\n        </div>\n\n        <p style=\"margin-top: 30px;\">If you have any questions or need assistance, please do not hesitate to contact our\n            customer support team.</p>\n        <p>We look forward to helping you with your next journey!</p>\n    </div>\n    <div class=\"footer\">\n        Best regards,<br>\n        Daniel Airline Team<br>\n        <a href=\"[frontendLoginUrl]\" style=\"color: #007bff; text-decoration: none;\">www.danielairlines.com</a><br>\n        &copy; <span>2025</span> Daniel Airline. All rights\n        reserved.\n    </div>\n</div>\n</body>\n</html>',_binary '','danielpinedasalazar19@gmail.com','2025-12-17 17:19:06.181402','Welcome to Daniel Airlines',NULL),(14,'<!DOCTYPE html>\n<html>\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>Welcome to Daniel Airline!</title>\n    <style>\n        body {\n            font-family: \'Inter\', sans-serif;\n            background-color: #f4f7f6;\n            margin: 0;\n            padding: 20px;\n            color: #333;\n        }\n        .container {\n            max-width: 600px;\n            margin: 0 auto;\n            background-color: #ffffff;\n            border-radius: 12px;\n            overflow: hidden;\n            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n            border: 1px solid #e0e0e0;\n        }\n        .header {\n            background-color: #007bff;\n            color: #ffffff;\n            padding: 25px;\n            text-align: center;\n            border-top-left-radius: 12px;\n            border-top-right-radius: 12px;\n        }\n        .header h1 {\n            margin: 0;\n            font-size: 28px;\n            font-weight: 700;\n        }\n        .content {\n            padding: 30px;\n            line-height: 1.6;\n        }\n        .section-title {\n            font-size: 22px;\n            color: #007bff;\n            margin-bottom: 20px;\n            border-bottom: 2px solid #007bff;\n            padding-bottom: 10px;\n            font-weight: 600;\n        }\n        .button {\n            display: inline-block;\n            background-color: #28a745;\n            color: #ffffff;\n            padding: 12px 25px;\n            border-radius: 8px;\n            text-decoration: none;\n            font-weight: 600;\n            margin-top: 25px;\n            transition: background-color 0.3s ease;\n        }\n        .button:hover {\n            background-color: #218838;\n        }\n        .footer {\n            background-color: #f8f9fa;\n            padding: 20px;\n            text-align: center;\n            font-size: 14px;\n            color: #777;\n            border-bottom-left-radius: 12px;\n            border-bottom-right-radius: 12px;\n            border-top: 1px solid #e0e0e0;\n        }\n        .greeting {\n            font-size: 18px;\n            margin-bottom: 20px;\n        }\n    </style>\n</head>\n<body>\n<div class=\"container\">\n    <div class=\"header\">\n        <h1>Welcome Aboard!</h1>\n    </div>\n    <div class=\"content\">\n        <p class=\"greeting\">Hello <strong>Daniel Pineda</strong>,</p>\n\n        <p>Thank you for registering with Daniel Airline! We are thrilled to have you join our community.</p>\n\n        <p>You can now explore our wide range of flights, manage your bookings, and enjoy exclusive offers.</p>\n\n\n        <div style=\"text-align: center;\">\n            <a class=\"button\" href=\"http://localhost:3000/login\">Log In to Your Account</a>\n        </div>\n\n        <p style=\"margin-top: 30px;\">If you have any questions or need assistance, please do not hesitate to contact our\n            customer support team.</p>\n        <p>We look forward to helping you with your next journey!</p>\n    </div>\n    <div class=\"footer\">\n        Best regards,<br>\n        Daniel Airline Team<br>\n        <a href=\"[frontendLoginUrl]\" style=\"color: #007bff; text-decoration: none;\">www.danielairlines.com</a><br>\n        &copy; <span>2025</span> Daniel Airline. All rights\n        reserved.\n    </div>\n</div>\n</body>\n</html>',_binary '','daniel.pineda73@eia.edu.co','2025-12-21 14:49:19.493686','Welcome to Daniel Airlines',NULL),(15,'<!DOCTYPE html>\n<html>\n<head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n  <title>Your Flight Booking Confirmation</title>\n  <style>\n    body {\n      font-family: \'Inter\', sans-serif;\n      background-color: #f4f7f6;\n      margin: 0;\n      padding: 20px;\n      color: #333;\n    }\n    .container {\n      max-width: 600px;\n      margin: 0 auto;\n      background-color: #ffffff;\n      border-radius: 12px;\n      overflow: hidden;\n      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);\n      border: 1px solid #e0e0e0;\n    }\n    .header {\n      background-color: #007bff;\n      color: #ffffff;\n      padding: 25px;\n      text-align: center;\n      border-top-left-radius: 12px;\n      border-top-right-radius: 12px;\n      position: relative;\n    }\n    .header h1 {\n      margin: 0;\n      font-size: 28px;\n      font-weight: 700;\n    }\n    .header p {\n      margin: 5px 0 0;\n      font-size: 16px;\n      opacity: 0.9;\n    }\n    .content {\n      padding: 30px;\n    }\n    .section-title {\n      font-size: 22px;\n      color: #007bff;\n      margin-bottom: 20px;\n      border-bottom: 2px solid #007bff;\n      padding-bottom: 10px;\n      font-weight: 600;\n    }\n    .detail-row {\n      display: flex;\n      justify-content: space-between;\n      padding: 10px 0;\n      border-bottom: 1px dashed #e0e0e0;\n      font-size: 16px;\n    }\n    .detail-row:last-child {\n      border-bottom: none;\n    }\n    .detail-label {\n      font-weight: 500;\n      color: #555;\n      flex: 1;\n    }\n    .detail-value {\n      font-weight: 600;\n      color: #000;\n      text-align: right;\n      flex: 1;\n    }\n    .passenger-list {\n      margin-top: 20px;\n      border: 1px solid #e0e0e0;\n      border-radius: 8px;\n      overflow: hidden;\n    }\n    .passenger-header {\n      background-color: #f0f0f0;\n      padding: 15px;\n      font-weight: 600;\n      font-size: 18px;\n      border-bottom: 1px solid #e0e0e0;\n    }\n    .passenger-item {\n      padding: 15px;\n      border-bottom: 1px solid #f0f0f0;\n    }\n    .passenger-item:last-child {\n      border-bottom: none;\n    }\n    .passenger-name {\n      font-weight: 600;\n      color: #007bff;\n      margin-bottom: 5px;\n    }\n    .footer {\n      background-color: #f8f9fa;\n      padding: 20px;\n      text-align: center;\n      font-size: 14px;\n      color: #777;\n      border-bottom-left-radius: 12px;\n      border-bottom-right-radius: 12px;\n      border-top: 1px solid #e0e0e0;\n    }\n    .button {\n      display: inline-block;\n      background-color: #28a745;\n      color: #ffffff;\n      padding: 12px 25px;\n      border-radius: 8px;\n      text-decoration: none;\n      font-weight: 600;\n      margin-top: 25px;\n      transition: background-color 0.3s ease;\n    }\n    .button:hover {\n      background-color: #218838;\n    }\n    .note {\n      font-size: 13px;\n      color: #888;\n      margin-top: 20px;\n      text-align: center;\n    }\n    .airport-code {\n      font-weight: bold;\n      color: #007bff;\n    }\n    .flight-info {\n      font-size: 18px;\n      font-weight: 600;\n      margin-bottom: 15px;\n      text-align: center;\n    }\n    .flight-route {\n      display: flex;\n      justify-content: center;\n      align-items: center;\n      margin-bottom: 20px;\n    }\n    .flight-route .arrow {\n      font-size: 24px;\n      color: #007bff;\n      margin: 0 10px;\n    }\n  </style>\n</head>\n<body>\n<div class=\"container\">\n  <div class=\"header\">\n    <h1>Booking Confirmed!</h1>\n    <p>Your journey is all set.</p>\n  </div>\n  <div class=\"content\">\n    <p style=\"font-size: 18px; text-align: center; margin-bottom: 30px;\">\n      Hello <strong>Daniel Pineda Salazar</strong>,\n      <br>\n      Your booking with reference <strong>71534498</strong> is confirmed.\n    </p>\n\n    <div class=\"section-title\">Flight Details</div>\n\n    <div class=\"flight-info\">\n      Flight: <span>DA2004</span>\n    </div>\n\n    <div class=\"flight-route\">\n      <span class=\"airport-code\">MED</span>\n      <span class=\"arrow\">&#x27A1;</span>\n      <span class=\"airport-code\">MIA</span>\n    </div>\n\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Departure Airport:</span>\n      <span class=\"detail-value\">Medellin Airport</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Departure City:</span>\n      <span class=\"detail-value\">MEDELLIN</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Departure Time:</span>\n      <span class=\"detail-value\">08 Jan 2026 10:00</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Arrival Airport:</span>\n      <span class=\"detail-value\">Miami Airport</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Arrival City:</span>\n      <span class=\"detail-value\">MIAMI</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Arrival Time:</span>\n      <span class=\"detail-value\">08 Jan 2026 13:30</span>\n    </div>\n    <div class=\"detail-row\">\n      <span class=\"detail-label\">Base Price:</span>\n      <span class=\"detail-value\">$120,000.00</span>\n    </div>\n\n    <div class=\"section-title\" style=\"margin-top: 30px;\">Passenger Details</div>\n    <div class=\"passenger-list\">\n      <div class=\"passenger-header\">Passengers</div>\n      <div class=\"passenger-item\">\n        <div class=\"passenger-name\">Daniel Pineda</div>\n        <div>\n          <span class=\"detail-label\">Passport:</span>\n          <span class=\"detail-value\">BD65121</span>\n        </div>\n        <div>\n          <span class=\"detail-label\">Seat:</span>\n          <span class=\"detail-value\">1A</span>\n        </div>\n        \n      </div>\n    </div>\n\n    <p class=\"note\">\n      Please arrive at the airport at least 2 hours before departure for domestic flights and 3 hours for\n      international flights.\n    </p>\n\n    <div style=\"text-align: center;\">\n      <a class=\"button\" href=\"http://localhost:3000/my-bookings\">View My Booking</a>\n\n    </div>\n  </div>\n  <div class=\"footer\">\n    Thank you for choosing our service!\n    <br>\n    &copy; <span>2025</span> Your Airline Name. All rights\n    reserved.\n  </div>\n</div>\n</body>\n</html>',_binary '','danielpinedasalazar19@gmail.com','2025-12-21 14:57:19.647214','Your Flight Booking',5);
/*!40000 ALTER TABLE `email_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flights`
--

DROP TABLE IF EXISTS `flights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flights` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `arrival_time` datetime(6) DEFAULT NULL,
  `base_price` decimal(38,2) DEFAULT NULL,
  `departure_time` datetime(6) DEFAULT NULL,
  `flight_number` varchar(255) NOT NULL,
  `status` enum('ARRIVED','CANCELLED','DELAYED','DEPARTED','SCHEDULED') DEFAULT NULL,
  `arrival_airport_id` bigint DEFAULT NULL,
  `assigned_pilot_id` bigint DEFAULT NULL,
  `departure_airport_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6bx3i9v6ikjiy0ru5ybor8t7` (`flight_number`),
  KEY `FKr90ujcvdphv3co3ry7aiel6l4` (`arrival_airport_id`),
  KEY `FK6iupidffxxbc4s41teqjxe3qc` (`assigned_pilot_id`),
  KEY `FK27lt4nklvbrwsw7x32dw0d05q` (`departure_airport_id`),
  CONSTRAINT `FK27lt4nklvbrwsw7x32dw0d05q` FOREIGN KEY (`departure_airport_id`) REFERENCES `airports` (`id`),
  CONSTRAINT `FK6iupidffxxbc4s41teqjxe3qc` FOREIGN KEY (`assigned_pilot_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKr90ujcvdphv3co3ry7aiel6l4` FOREIGN KEY (`arrival_airport_id`) REFERENCES `airports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flights`
--

LOCK TABLES `flights` WRITE;
/*!40000 ALTER TABLE `flights` DISABLE KEYS */;
INSERT INTO `flights` VALUES (5,'2025-07-27 18:00:00.000000',120000.00,'2025-07-27 10:00:00.000000','DA2005','SCHEDULED',2,11,1),(6,'2025-01-08 13:30:00.000000',120000.00,'2025-01-08 10:00:00.000000','DA20056','SCHEDULED',2,11,1),(7,'2026-01-08 13:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2004','SCHEDULED',2,11,1),(8,'2026-01-08 13:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2005326','SCHEDULED',6,11,1),(9,'2026-01-08 13:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA200536','SCHEDULED',7,11,3),(10,'2026-01-10 13:30:00.000000',120000.00,'2026-01-09 10:00:00.000000','DA2010536','SCHEDULED',3,11,7),(11,'2026-01-10 19:30:00.000000',120000.00,'2026-01-10 10:00:00.000000','DA210536','SCHEDULED',6,11,5),(12,'2026-01-11 19:30:00.000000',120000.00,'2026-01-11 10:00:00.000000','DA2101536','SCHEDULED',5,11,6),(13,'2026-01-12 19:30:00.000000',120000.00,'2026-01-12 10:00:00.000000','DA210136','SCHEDULED',2,11,7),(14,'2026-01-08 19:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2101136','SCHEDULED',2,11,7),(15,'2026-01-08 19:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA21011316','SCHEDULED',6,11,1),(16,'2026-01-08 19:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2101131','SCHEDULED',5,11,1),(17,'2026-01-08 19:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA201131','SCHEDULED',1,11,5),(18,'2026-01-08 19:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA20131','SCHEDULED',1,11,6),(19,'2026-01-08 10:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA201311','SCHEDULED',1,11,3),(20,'2026-01-08 10:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2011','SCHEDULED',3,11,1),(21,'2026-01-08 14:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA20211','SCHEDULED',3,11,5),(22,'2026-01-08 14:30:00.000000',120000.00,'2026-01-08 10:00:00.000000','DA2021','SCHEDULED',5,11,3),(23,'2026-01-08 14:30:00.000000',2200000.00,'2026-01-08 10:00:00.000000','DA202121','SCHEDULED',5,11,6);
/*!40000 ALTER TABLE `flights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passengers`
--

DROP TABLE IF EXISTS `passengers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passengers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `passport_number` varchar(255) DEFAULT NULL,
  `seat_number` varchar(255) DEFAULT NULL,
  `special_request` varchar(255) DEFAULT NULL,
  `type` enum('ADULT','CHILD','INFANT') DEFAULT NULL,
  `booking_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgc7vcfrut3vamougerwse2m2u` (`booking_id`),
  CONSTRAINT `FKgc7vcfrut3vamougerwse2m2u` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passengers`
--

LOCK TABLES `passengers` WRITE;
/*!40000 ALTER TABLE `passengers` DISABLE KEYS */;
INSERT INTO `passengers` VALUES (5,'Daniel','Pineda','BD65121','1A',NULL,'ADULT',5);
/*!40000 ALTER TABLE `passengers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(3,'CUSTOMER'),(2,'PILOT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email_verified` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `provider` enum('FACEBOOK','GOOGLE','LOCAL') NOT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (10,_binary '','2025-12-17 17:19:04.072298','danielpinedasalazar19@gmail.com',_binary '\0','Daniel Pineda Salazar','$2a$10$i9Z/tgEz/fcF8xBr2uLBOeu7nw92LVGHJ2EMaPaIns1tCblE1fQkS','3005103421','LOCAL',NULL,'2025-12-19 14:50:14.841191'),(11,_binary '','2025-12-21 14:49:12.664447','daniel.pineda73@eia.edu.co',_binary '\0','Daniel Pineda','$2a$10$uE1UgGHPntBh5hpqUrpMI.EKYMToEiu3eGKKBNbC8GHJFxbhCIFA2','3005103421','LOCAL',NULL,'2025-12-21 14:49:12.664478');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  KEY `FK2o0jvgh89lemvvo17cbqvdxaa` (`user_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (10,1),(11,1),(11,2),(11,3),(10,2),(10,3);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-21 23:53:53
