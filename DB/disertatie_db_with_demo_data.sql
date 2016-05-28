-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 20, 2016 at 03:30 PM
-- Server version: 5.6.25-log
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `disertatie_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `client_id` int(11) NOT NULL,
  `client_nume` varchar(45) NOT NULL,
  `client_judet` varchar(30) NOT NULL,
  `client_localitate` varchar(30) NOT NULL,
  `client_adresa` varchar(100) NOT NULL,
  `client_telefon` varchar(10) NOT NULL,
  `client_email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`client_id`, `client_nume`, `client_judet`, `client_localitate`, `client_adresa`, `client_telefon`, `client_email`) VALUES
(1, 'Popescu Vasile', 'Galati', 'Galati', 'aaa', '111', NULL),
(2, 'Ionescu Claudiu', 'Bucuresti', 'Bucuresti', 'bbb', '222', NULL),
(3, 'Popa', 'Florin', 'Iasi', 'ccc', '333', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `colet`
--

CREATE TABLE `colet` (
  `colet_id` int(11) NOT NULL,
  `colet_awb` bigint(20) DEFAULT NULL,
  `colet_status` varchar(30) NOT NULL,
  `colet_comanda_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `colet`
--

INSERT INTO `colet` (`colet_id`, `colet_awb`, `colet_status`, `colet_comanda_id`) VALUES
(1, NULL, 'nepreluat', 1),
(2, NULL, 'nepreluat', 2),
(3, NULL, 'nepreluat', 2),
(4, NULL, 'nepreluat', 2),
(5, NULL, 'nepreluat', 3),
(6, NULL, 'nepreluat', 4),
(7, NULL, 'nepreluat', 5);

-- --------------------------------------------------------

--
-- Table structure for table `comanda`
--

CREATE TABLE `comanda` (
  `comanda_id` int(11) NOT NULL,
  `comanda_nr_colete` int(11) NOT NULL,
  `comanda_greutate` int(11) NOT NULL,
  `comanda_plata` varchar(30) NOT NULL,
  `comanda_data_comanda` date NOT NULL,
  `comanda_data_preluare` datetime DEFAULT NULL,
  `comanda_data_expediere` datetime DEFAULT NULL,
  `comanda_data_livrare` datetime DEFAULT NULL,
  `comanda_observatii` varchar(100) DEFAULT NULL,
  `comanda_asignare` int(11) DEFAULT NULL,
  `comanda_exp_id` int(11) NOT NULL,
  `comanda_dest_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comanda`
--

INSERT INTO `comanda` (`comanda_id`, `comanda_nr_colete`, `comanda_greutate`, `comanda_plata`, `comanda_data_comanda`, `comanda_data_preluare`, `comanda_data_expediere`, `comanda_data_livrare`, `comanda_observatii`, `comanda_asignare`, `comanda_exp_id`, `comanda_dest_id`) VALUES
(1, 1, 2, 'ramburs', '2016-05-20', NULL, NULL, NULL, NULL, NULL, 1, 2),
(2, 3, 7, 'online', '2016-05-20', NULL, NULL, NULL, NULL, NULL, 2, 1),
(3, 1, 5, 'online', '2016-05-20', NULL, NULL, NULL, NULL, NULL, 1, 2),
(4, 1, 1, 'ramburs', '2016-05-20', NULL, NULL, NULL, NULL, NULL, 1, 3),
(5, 2, 4, 'ramburs', '2016-05-20', NULL, NULL, NULL, NULL, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `hub`
--

CREATE TABLE `hub` (
  `hub_id` int(11) NOT NULL,
  `hub_judet` varchar(30) NOT NULL,
  `hub_localitate` varchar(30) NOT NULL,
  `hub_adresa` varchar(100) NOT NULL,
  `hub_telefon` varchar(10) DEFAULT NULL,
  `hub_email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hub`
--

INSERT INTO `hub` (`hub_id`, `hub_judet`, `hub_localitate`, `hub_adresa`, `hub_telefon`, `hub_email`) VALUES
(1, 'Galati', 'Galati', 'aaaaa', '11111', NULL),
(2, 'Braila', 'Braila', 'bbbbb', '22222', NULL),
(3, 'Bucuresti', 'Bucuresti', 'ccccc', '33333', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `operare`
--

CREATE TABLE `operare` (
  `operare_id` int(11) NOT NULL,
  `operare_user_id` int(11) NOT NULL,
  `operare_colet_id` int(11) NOT NULL,
  `operare_data` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `proxi`
--

CREATE TABLE `proxi` (
  `proxi_id` int(11) NOT NULL,
  `proxi_hub_id_plecare` int(11) NOT NULL,
  `proxi_hub_id_sosire` int(11) NOT NULL,
  `proxi_next` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `proxi`
--

INSERT INTO `proxi` (`proxi_id`, `proxi_hub_id_plecare`, `proxi_hub_id_sosire`, `proxi_next`) VALUES
(1, 1, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_nume` varchar(30) NOT NULL,
  `user_prenume` varchar(30) NOT NULL,
  `user_telefon` varchar(10) NOT NULL,
  `user_statut` varchar(20) NOT NULL,
  `user_userlog_id` int(11) NOT NULL,
  `user_hub_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_nume`, `user_prenume`, `user_telefon`, `user_statut`, `user_userlog_id`, `user_hub_id`) VALUES
(1, 'Chirvase', 'Liviu', '1111', 'curier', 1, 1),
(2, 'Popa', 'Gigel', '2222', 'curier', 2, 2),
(3, 'Ionescu', 'Alin', '3333', 'curier', 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `userlog`
--

CREATE TABLE `userlog` (
  `userlog_id` int(11) NOT NULL,
  `userlog_username` varchar(30) NOT NULL,
  `userlog_password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userlog`
--

INSERT INTO `userlog` (`userlog_id`, `userlog_username`, `userlog_password`) VALUES
(1, 'lchirvase', 'qqq'),
(2, 'gpopa', 'aaa'),
(3, 'aionescu', 'zzz');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`client_id`);

--
-- Indexes for table `colet`
--
ALTER TABLE `colet`
  ADD PRIMARY KEY (`colet_id`),
  ADD UNIQUE KEY `colet_awb_UNIQUE` (`colet_awb`),
  ADD KEY `fk_colet_comanda_idx` (`colet_comanda_id`);

--
-- Indexes for table `comanda`
--
ALTER TABLE `comanda`
  ADD PRIMARY KEY (`comanda_id`),
  ADD KEY `fk_comanda_exp_idx` (`comanda_exp_id`),
  ADD KEY `fk_comanda_dest_idx` (`comanda_dest_id`);

--
-- Indexes for table `hub`
--
ALTER TABLE `hub`
  ADD PRIMARY KEY (`hub_id`);

--
-- Indexes for table `operare`
--
ALTER TABLE `operare`
  ADD PRIMARY KEY (`operare_id`),
  ADD UNIQUE KEY `UNIQUE` (`operare_user_id`,`operare_colet_id`),
  ADD KEY `fk_user_has_colet_colet_idx` (`operare_colet_id`),
  ADD KEY `fk_user_has_colet_user_idx` (`operare_user_id`);

--
-- Indexes for table `proxi`
--
ALTER TABLE `proxi`
  ADD PRIMARY KEY (`proxi_id`),
  ADD UNIQUE KEY `UNIQUE` (`proxi_hub_id_plecare`,`proxi_hub_id_sosire`),
  ADD KEY `fk_hub_has_hub_hub2_idx` (`proxi_hub_id_sosire`),
  ADD KEY `fk_hub_has_hub_hub1_idx` (`proxi_hub_id_plecare`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_cnp_UNIQUE` (`user_telefon`),
  ADD KEY `fk_user_userlog_idx` (`user_userlog_id`),
  ADD KEY `fk_user_hub1_idx` (`user_hub_id`);

--
-- Indexes for table `userlog`
--
ALTER TABLE `userlog`
  ADD PRIMARY KEY (`userlog_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `client_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `colet`
--
ALTER TABLE `colet`
  MODIFY `colet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `comanda`
--
ALTER TABLE `comanda`
  MODIFY `comanda_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `hub`
--
ALTER TABLE `hub`
  MODIFY `hub_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `userlog`
--
ALTER TABLE `userlog`
  MODIFY `userlog_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `colet`
--
ALTER TABLE `colet`
  ADD CONSTRAINT `fk_colet_comanda` FOREIGN KEY (`colet_comanda_id`) REFERENCES `comanda` (`comanda_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `comanda`
--
ALTER TABLE `comanda`
  ADD CONSTRAINT `fk_comanda_dest` FOREIGN KEY (`comanda_dest_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_comanda_exp` FOREIGN KEY (`comanda_exp_id`) REFERENCES `client` (`client_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `operare`
--
ALTER TABLE `operare`
  ADD CONSTRAINT `fk_user_has_colet_colet` FOREIGN KEY (`operare_colet_id`) REFERENCES `colet` (`colet_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_has_colet_user` FOREIGN KEY (`operare_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `proxi`
--
ALTER TABLE `proxi`
  ADD CONSTRAINT `fk_hub_has_hub_hub1` FOREIGN KEY (`proxi_hub_id_plecare`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_hub_has_hub_hub2` FOREIGN KEY (`proxi_hub_id_sosire`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_hub1` FOREIGN KEY (`user_hub_id`) REFERENCES `hub` (`hub_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_user_userlog` FOREIGN KEY (`user_userlog_id`) REFERENCES `userlog` (`userlog_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
