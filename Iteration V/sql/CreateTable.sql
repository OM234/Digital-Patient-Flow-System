-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: digihealth
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `contact_info`
--

DROP TABLE IF EXISTS `contact_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact_info` (
  `patientID` varchar(100) NOT NULL,
  `streetNumber` varchar(100) DEFAULT NULL,
  `streetName` varchar(100) DEFAULT NULL,
  `postalCode` varchar(6) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`patientID`),
  CONSTRAINT `contact_info_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_info`
--

LOCK TABLES `contact_info` WRITE;
/*!40000 ALTER TABLE `contact_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_note`
--

DROP TABLE IF EXISTS `medical_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_note` (
  `patientID` varchar(100) NOT NULL,
  `writerID` varchar(100) DEFAULT NULL,
  `pulse` int DEFAULT NULL,
  `noteID` int NOT NULL,
  `o2Sat` int DEFAULT NULL,
  `sbp` int DEFAULT NULL,
  `dbp` int DEFAULT NULL,
  `temp` double DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `note` varchar(10000) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`patientID`,`noteID`),
  KEY `writerID` (`writerID`),
  KEY `patientID` (`patientID`),
  CONSTRAINT `medical_note_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `medical_note_ibfk_2` FOREIGN KEY (`writerID`) REFERENCES `user` (`userID`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_note`
--

LOCK TABLES `medical_note` WRITE;
/*!40000 ALTER TABLE `medical_note` DISABLE KEYS */;
/*!40000 ALTER TABLE `medical_note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medication`
--

DROP TABLE IF EXISTS `medication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medication` (
  `patientID` varchar(100) NOT NULL,
  `prescriberID` varchar(100) NOT NULL,
  `medName` varchar(100) NOT NULL,
  `route` varchar(100) NOT NULL,
  `dose` double NOT NULL,
  `frequency` varchar(100) NOT NULL,
  `units` varchar(100) NOT NULL,
  `prescribed` date NOT NULL,
  `expires` date NOT NULL,
  PRIMARY KEY (`patientID`,`prescriberID`,`medName`,`route`,`frequency`,`units`,`prescribed`,`expires`,`dose`),
  KEY `prescriberID` (`prescriberID`),
  KEY `patient_medication__index` (`patientID`),
  CONSTRAINT `medication_ibfk_1` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `medication_ibfk_2` FOREIGN KEY (`prescriberID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medication`
--

LOCK TABLES `medication` WRITE;
/*!40000 ALTER TABLE `medication` DISABLE KEYS */;
/*!40000 ALTER TABLE `medication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medication_names`
--

DROP TABLE IF EXISTS `medication_names`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medication_names` (
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medication_names`
--

LOCK TABLES `medication_names` WRITE;
/*!40000 ALTER TABLE `medication_names` DISABLE KEYS */;
INSERT INTO `medication_names` VALUES ('Accolate'),('Accupril'),('Accuretic'),('Aciphex'),('Aclovate'),('Actonel'),('Actos'),('Adalat cc'),('Adderall XR'),('Alaway OTC'),('Albuterol Inhalation Solution'),('Aldactazide'),('Aldactone'),('Altace'),('Amaryl'),('Ambien'),('Amoxapine tablet'),('Anaprox DS'),('AndroGel'),('Antivert'),('Aricept'),('Arimidex'),('Artificial Tears 1.4% drops (OTC)'),('Aspirin EC Tablet (OTC)'),('Astelin'),('Astepro'),('Atacand'),('Atacand HCT'),('Ativan'),('Avalide'),('Avapro'),('Azilect'),('Azulfidine'),('Azulfidine EN-Tabs'),('Bactrim DS'),('Bentyl'),('Benzonatate capsule'),('Benztropine tablet'),('Brimonidine Tartrate 0.2% Solution'),('Bromocriptine tablet'),('Buspar'),('Calan'),('Calan-SR'),('Capoten'),('Capsaicin Cream, 0.035% (OTC)'),('Carafate'),('Cardizem CD'),('Cardura'),('Casodex'),('Catapres'),('Catapres TTS -3'),('Celexa'),('CellCept'),('Chlordiazepoxide capsule'),('Chlorpromazine HCL'),('Chlorthalidone tablet'),('Claritin OTC'),('Cleocin'),('Colestid'),('Cordarone'),('Coreg tablets'),('Cozaar'),('Crestor'),('Cytomel'),('Dacogen'),('Depakene'),('Depakote'),('Depo-Testosterone'),('Desyrel'),('Detrol'),('Dextroamphetamine sulfate ER capsule'),('Dextroamphetamine-Amphetamine tablet'),('Dibenzyline capsule'),('Diflucan'),('Dilacor XR'),('Dilantin'),('Diovan'),('Diovan HCT'),('Diprolene Cream'),('Ditropan'),('Donnatal Elixir'),('Donnatal Tablet'),('Doxepin capsule'),('DuoNeb'),('Dutoprol Tablet'),('Dyazide'),('Dynacin'),('Dyrenium capsule'),('Effexor'),('Elavil'),('Elocon Cream'),('Elocon Ointment'),('Enulose'),('Epinephrine Auto Injector .3mg'),('Eplerenone tablet'),('Estrace'),('Evista'),('Evoxac'),('Exelon'),('Exforge'),('Exforge HCT'),('Femara'),('Ferrous Sulfate EC tablet'),('Flecainide Acetate tablet'),('Flomax'),('Flonase (50 mcg)'),('Fluvoxamine Maleate tablet'),('Focalin'),('Folic Acid'),('Folic Acid 2.2 tablet'),('Fortical Nasal Spray'),('Fosamax'),('Glucophage'),('Glucophage XR'),('Glucotrol'),('Glucotrol XL'),('Glucovance'),('Glynase PresTab'),('Haloperidol tablet'),('Hexalen capsules'),('Hydralazine tablet'),('Hydrea'),('hydrochlorothiazide tablet'),('Hydrocortisone tablet'),('Hydroxyzine HCL tablet'),('Hytrin'),('Hyzaar'),('Imatinib tablet'),('Imdur'),('Imitrex (Tablets)'),('Imuran'),('Indapamide tablet'),('Inderal'),('Inderal LA Capsules'),('Intuniv Extended Release Tablets'),('Ismo'),('Isoniazid/INH'),('Isoptin-SR'),('Jantoven'),('K-Dur 20MEQ'),('Keppra'),('Keppra XR tablets'),('Klonopin'),('Klor-Con 10mEQ'),('Klor-Con M20'),('Lamictal'),('Lanoxin'),('Lasix'),('Levoxyl'),('Lexapro'),('Lexapro 20mg'),('Lexapro 5mg'),('Lidocaine 2% viscous solution'),('Lidoderm'),('Lioresal'),('Lipitor'),('Lithium Carbonate'),('Lodine'),('Lodine tablet'),('Lofibra'),('Lomotil'),('Lonox'),('Loperamide capsule'),('Lopid'),('Lopressor'),('Lotensin'),('Lotensin HCT'),('Lotrel'),('Lotrisone Cream'),('Lovaza'),('Loxapine capsule'),('Lunesta'),('Magnesium Oxide tablet (OTC)'),('Mavik'),('Maxzide'),('Mestinon'),('Metadate CD'),('Methotrexate'),('Mevacor'),('Micardis'),('Micronase'),('Microzide'),('Midodrine HCl tablet'),('Minocin'),('Mirapex tablets'),('Mobic'),('Monoket'),('Motrin'),('Mysoline'),('Nabumetone tablet'),('Namenda'),('Naprosyn'),('Neurontin'),('Nexium'),('Niacin'),('Niacin SA capsule (OTC)'),('Niaspan ER'),('Nifedipine capsule'),('Nilandron tablet'),('Nitroglycerin SA capsule'),('Norvasc'),('Nystatin powder'),('Nystatin tablet'),('Ogen'),('Ortho Micronor'),('Ortho-Cyclen'),('Ortho-Tri-Cyclen'),('Ovcon 35'),('Oxandrin'),('Pamelor'),('Panretin gel'),('Parnate 10mg tablets'),('Paxil'),('Pepcid'),('Perphenazine tablet'),('PhosLo gelcaps'),('Plaquenil'),('Plavix'),('Plendil ER'),('Pletal'),('PNV Prenatal Multivitamin Plus Iron'),('Potassium Iodide'),('Prandin'),('Pravachol'),('prazosin capsule'),('Prednisone tablet'),('Prevacid'),('Prevalite Powder'),('Prilosec'),('Prinivil'),('Prinzide'),('ProAir RespiClick Powder Inhaler'),('Prochlorperazine tablet'),('Progesterone capsule'),('Prograf'),('Propylthiouracil tablet'),('Proscar'),('Protonix'),('Provera'),('Prozac'),('Pulmicort Respules'),('Qudexy XR capsule'),('Ranexa'),('Reglan'),('Remeron'),('Requip tablets'),('Restoril'),('Revatio'),('Risperdal'),('Ritalin'),('Ritalin LA'),('Robaxin'),('Rocaltrol'),('Rythmol'),('Salagen Tablet'),('Septra DS'),('Seroquel'),('Sinemet'),('Sinemet CR'),('Singulair'),('Sonata'),('SSKI oral solution'),('Synthroid'),('Tamoxifen citrate tablet'),('Tapazole'),('Tegretol'),('Tenoretic'),('Tenormin'),('Tessalon Capsule'),('Topamax tablets'),('Trandate'),('Trental'),('Triamcinolone cream, 0.1%'),('Triamcinolone ointment, 0.1%'),('Tricor'),('Trifluoperazine HCl tablet'),('Trilipix'),('Trospium Chloride tablet'),('Trusopt'),('Ultracet'),('Ultram'),('Ultravate'),('Urecholine'),('Uroxatral'),('Valium'),('Valtrex tablet'),('Vaseretic'),('Vasotec'),('Viagra'),('Vistaril'),('Vitamin B Complex capsule (OTC)'),('Vitamin B-6 tablet (OTC)'),('Vitamin D3 capsule (OTC)'),('Vitamin D3 tablet (OTC)'),('Vogelxo gel packet'),('Vogelxo gel pump'),('Vogelxo gel tube'),('Voltaren'),('Voltaren XR'),('Wellbutrin'),('Wellbutrin SR'),('Wellbutrin XL'),('Xalatan'),('Xanax'),('Xopenex'),('Xyzal tablets'),('Zanaflex'),('Zaroxolyn'),('Zestoretic'),('Zestril'),('Ziac'),('Zidovudine tablet'),('Zocor'),('Zofran'),('Zofran ODT'),('Zoloft'),('Zovirax'),('Zyloprim'),('Zyprexa'),('Zyrtec OTC');
/*!40000 ALTER TABLE `medication_names` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `patientID` varchar(30) NOT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `lastName` varchar(30) DEFAULT NULL,
  `height` int DEFAULT NULL,
  `weight` double DEFAULT NULL,
  `BMI` double DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `Gender` char(1) DEFAULT NULL,
  PRIMARY KEY (`patientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patonunit`
--

DROP TABLE IF EXISTS `patonunit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patonunit` (
  `unitID` varchar(30) NOT NULL,
  `patientID` varchar(30) NOT NULL,
  PRIMARY KEY (`unitID`,`patientID`),
  KEY `patOnUnit___fk2` (`patientID`),
  CONSTRAINT `patOnUnit___fk` FOREIGN KEY (`unitID`) REFERENCES `unit` (`unitID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `patOnUnit___fk2` FOREIGN KEY (`patientID`) REFERENCES `patient` (`patientID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patonunit`
--

LOCK TABLES `patonunit` WRITE;
/*!40000 ALTER TABLE `patonunit` DISABLE KEYS */;
/*!40000 ALTER TABLE `patonunit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `unit` (
  `unitID` varchar(30) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`unitID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userID` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-03 21:15:51
