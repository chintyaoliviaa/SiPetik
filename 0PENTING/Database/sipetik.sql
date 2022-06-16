-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: sipetik
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbbatik`
--

DROP TABLE IF EXISTS `tbbatik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbbatik` (
  `kode_batik` varchar(15) NOT NULL,
  `kode_pemasok` varchar(15) NOT NULL,
  `nama_batik` varchar(50) NOT NULL,
  `harga_beli` int(10) NOT NULL,
  `harga_jual` int(10) NOT NULL,
  PRIMARY KEY (`kode_batik`),
  KEY `kode_buku` (`kode_batik`,`kode_pemasok`),
  KEY `kode_pemasok` (`kode_pemasok`),
  CONSTRAINT `tbbatik_ibfk_1` FOREIGN KEY (`kode_pemasok`) REFERENCES `tbpemasok` (`kode_pemasok`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbbatik`
--

LOCK TABLES `tbbatik` WRITE;
/*!40000 ALTER TABLE `tbbatik` DISABLE KEYS */;
INSERT INTO `tbbatik` VALUES ('BK000001','P-01','Mega Mendung',250000,200000),('BK000002','P-04','Sogan',150000,125000),('BK000003','P-05','Gentongan',80000,50000),('BK000004','P-03','Tujuh Rupa',65000,50000),('BK000005','P-06','Kraton',150000,120000),('BK000006','P-01','Parang',120000,90000),('BK000007','p-03','Trusme',135000,120000),('BK000008','p-07','Tritis',185000,160000);
/*!40000 ALTER TABLE `tbbatik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbdetail`
--

DROP TABLE IF EXISTS `tbdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbdetail` (
  `kode_transaksi` varchar(50) NOT NULL,
  `kode_batik` varchar(15) NOT NULL,
  `nama_batik` varchar(50) NOT NULL,
  `harga` int(20) NOT NULL,
  `jumlah_beli` int(20) NOT NULL,
  `subtotal` int(50) NOT NULL,
  KEY `kode_transaksi` (`kode_transaksi`),
  CONSTRAINT `tbdetail_ibfk_1` FOREIGN KEY (`kode_transaksi`) REFERENCES `tbtransaksi` (`kode_transaksi`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbdetail`
--

LOCK TABLES `tbdetail` WRITE;
/*!40000 ALTER TABLE `tbdetail` DISABLE KEYS */;
INSERT INTO `tbdetail` VALUES ('20220603TR000001','BK000001','Mega Mendung',200000,3,600000),('20220603TR000002','BK000002','Sogan',125000,3,375000),('20220603TR000003','BK000002','Sogan',125000,3,375000),('20220603TR000004','BK000003','Gentongan',50000,2,100000),('20220603TR000004','BK000005','Kraton',120000,3,360000),('20220603TR000005','BK000002','Sogan',125000,3,375000),('20220603TR000006','BK000002','Sogan',125000,3,375000),('20220603TR000006','BK000003','Gentongan',50000,4,200000),('20220603TR000007','BK000005','Kraton',120000,4,480000),('20220613TR000008','BK000006','Parang',90000,2,180000),('20220613TR000008','BK000005','Kraton',120000,2,180000),('20220613TR000008','BK000004','Tujuh Rupa',50000,1,50000),('20220613TR000008','BK000002','Sogan',125000,3,375000),('20220613TR000009','BK000003','Gentongan',50000,3,150000),('20220613TR000009','BK000002','Sogan',125000,5,625000),('20220613TR000009','BK000005','Kraton',120000,5,600000),('20220614TR000010','BK000003','Gentongan',50000,3,150000),('20220614TR000010','BK000005','Kraton',120000,2,240000),('20220614TR000010','BK000002','Sogan',125000,3,375000),('20220614TR000010','BK000006','Parang',90000,3,375000),('20220616TR000011','BK000002','Sogan',125000,3,375000),('20220616TR000011','BK000003','Gentongan',50000,5,250000),('20220616TR000011','BK000006','Parang',90000,3,270000);
/*!40000 ALTER TABLE `tbdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbpegawai`
--

DROP TABLE IF EXISTS `tbpegawai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbpegawai` (
  `kode_pegawai` varchar(50) NOT NULL,
  `nama_pegawai` varchar(100) NOT NULL,
  `jk` varchar(6) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `hp` varchar(15) NOT NULL,
  `foto` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `hak_akses` enum('admin','operator','kasir','') NOT NULL,
  PRIMARY KEY (`kode_pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbpegawai`
--

LOCK TABLES `tbpegawai` WRITE;
/*!40000 ALTER TABLE `tbpegawai` DISABLE KEYS */;
INSERT INTO `tbpegawai` VALUES ('PG000001','Chintya Olivia','Wanita','2012-05-01','Cimahi','083136143090','src\\foto\\Oliv.jpg','oliv','123','admin'),('PG000002','Jisoo BP','Wanita','2022-05-03','Bandung','083136143090','src/foto/Jisoo.jpg','jisoo','123','operator'),('PG000003','Jungkook BTS','Pria','2022-05-01','Bogor','083136143090','src/foto/Jungkook.jpg','jungkook','123','kasir'),('PG000004','Shafa','Wanita','2022-06-01','Margaasih','082117119895','src\\foto\\Shafa.png','shafa','123','admin'),('PG000005','Arivah','Wanita','2022-06-01','Lebak','082167894554','src\\foto\\Arivah.png','arivah','123','admin'),('PG000006','Eka','Wanita','2022-06-01','Purwakarta','083136143090','src\\foto\\Eka.jpg','eka','123','kasir'),('PG000007','Anthon','Pria','2022-06-01','Jakarta','098637494736','src\\foto\\Jin.jpg','anthon','123','operator');
/*!40000 ALTER TABLE `tbpegawai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbpemasok`
--

DROP TABLE IF EXISTS `tbpemasok`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbpemasok` (
  `kode_pemasok` varchar(15) NOT NULL,
  `nama_pemasok` varchar(20) NOT NULL,
  `alamat` text NOT NULL,
  `pj` varchar(100) NOT NULL,
  `hp` varchar(15) NOT NULL,
  PRIMARY KEY (`kode_pemasok`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbpemasok`
--

LOCK TABLES `tbpemasok` WRITE;
/*!40000 ALTER TABLE `tbpemasok` DISABLE KEYS */;
INSERT INTO `tbpemasok` VALUES ('P-01','Trusme','Purwakarta','Eka Tiara','083136143090'),('P-02','Sinar Jaya','Lebak','Siti Nur','082117119895'),('P-03','Danar Hadi','Solo','Shafa','083136143090'),('P-04','Iwan Tirta','Pekalongan','Faisal','082117119895'),('P-05','Alleira','Jawa','Aldi','083136143090'),('P-06','Rodeo','Surabaya','Wisnu','082126156565'),('P-07','Sinar Jaya','Bandung','Anthon','089767563453'),('P-08','Cahaya Abadi','Cimahi','Asep','083979237929');
/*!40000 ALTER TABLE `tbpemasok` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbstok`
--

DROP TABLE IF EXISTS `tbstok`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbstok` (
  `kode_batik` varchar(15) NOT NULL,
  `stok` int(10) NOT NULL,
  KEY `kode_buku` (`kode_batik`),
  CONSTRAINT `tbstok_ibfk_1` FOREIGN KEY (`kode_batik`) REFERENCES `tbbatik` (`kode_batik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbstok`
--

LOCK TABLES `tbstok` WRITE;
/*!40000 ALTER TABLE `tbstok` DISABLE KEYS */;
INSERT INTO `tbstok` VALUES ('BK000001',15),('BK000002',10),('BK000003',18),('BK000004',35),('BK000005',80),('BK000006',50),('BK000007',35),('BK000008',65);
/*!40000 ALTER TABLE `tbstok` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbtemporary`
--

DROP TABLE IF EXISTS `tbtemporary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbtemporary` (
  `kode_batik` varchar(15) NOT NULL,
  `nama_batik` varchar(20) NOT NULL,
  `harga` int(20) NOT NULL,
  `jml_beli` int(20) NOT NULL,
  `subtotal` int(20) NOT NULL,
  KEY `kode_buku` (`kode_batik`),
  CONSTRAINT `tbtemporary_ibfk_1` FOREIGN KEY (`kode_batik`) REFERENCES `tbbatik` (`kode_batik`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbtemporary`
--

LOCK TABLES `tbtemporary` WRITE;
/*!40000 ALTER TABLE `tbtemporary` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbtemporary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbtransaksi`
--

DROP TABLE IF EXISTS `tbtransaksi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbtransaksi` (
  `kode_transaksi` varchar(50) NOT NULL,
  `tgl_transaksi` varchar(50) NOT NULL,
  `kode_kasir` varchar(15) NOT NULL,
  `nama_kasir` varchar(20) NOT NULL,
  `total` int(20) NOT NULL,
  `bayar` int(20) NOT NULL,
  `kembali` int(20) NOT NULL,
  PRIMARY KEY (`kode_transaksi`),
  KEY `kode_kasir` (`kode_kasir`),
  CONSTRAINT `tbtransaksi_ibfk_1` FOREIGN KEY (`kode_kasir`) REFERENCES `tbpegawai` (`kode_pegawai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbtransaksi`
--

LOCK TABLES `tbtransaksi` WRITE;
/*!40000 ALTER TABLE `tbtransaksi` DISABLE KEYS */;
INSERT INTO `tbtransaksi` VALUES ('20220603TR000001','03 Jun 2022 01:20','PG000001','Chintya Olivia',600000,700000,100000),('20220603TR000002','03 Jun 2022 15:22','PG000001','Chintya Olivia',375000,400000,25000),('20220603TR000003','03 Jun 2022 15:35','PG000001','Chintya Olivia',375000,400000,25000),('20220603TR000004','03 Jun 2022 15:35','PG000001','Chintya Olivia',460000,500000,40000),('20220603TR000005','03 Jun 2022 18:28','PG000001','Chintya Olivia',375000,400000,25000),('20220603TR000006','03 Jun 2022 18:28','PG000001','Chintya Olivia',575000,600000,25000),('20220603TR000007','03 Jun 2022 18:31','PG000001','Chintya Olivia',480000,500000,20000),('20220613TR000008','13 Jun 2022 18:19','PG000001','Chintya Olivia',785000,800000,15000),('20220613TR000009','13 Jun 2022 21:07','PG000001','Chintya Olivia',1375000,1400000,25000),('20220614TR000010','14 Jun 2022 10:48','PG000004','Shafa',1140000,1150000,10000),('20220616TR000011','16 Jun 2022 09:05','PG000006','Eka',895000,900000,5000);
/*!40000 ALTER TABLE `tbtransaksi` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-16 12:01:14
