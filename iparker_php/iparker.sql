-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2023 at 07:07 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iparker`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(20) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `email` varchar(240) NOT NULL,
  `Password` varchar(30) DEFAULT NULL,
  `Balance` varchar(22) NOT NULL,
  `flag` int(200) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `Name`, `mobile`, `email`, `Password`, `Balance`, `flag`) VALUES
(1, 'admin', '', 'h', 'h', '0', 1),
(2, 'h', '8421050145', 'g@gmail.com', '123456', '', 1),
(3, 'o', '9730325138', 'itsap004@gmail.com', '123456', '', 1),
(4, 'oo', '9730325138', 'k', 'k', '', 0),
(5, 'd', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(6, 'd', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(7, 'd', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(8, 'd', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(9, 'd', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(10, 'oo', '9730325138', 'itsap004@gmail.com', '123456', '', 0),
(11, 'oo', '9730325138', 'itsap004@gmail.com', '123456', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `admin_reg`
--

CREATE TABLE `admin_reg` (
  `id` int(11) NOT NULL,
  `name` varchar(240) NOT NULL,
  `mobile` bigint(20) NOT NULL,
  `email` varchar(240) NOT NULL,
  `password` varchar(240) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin_reg`
--

INSERT INTO `admin_reg` (`id`, `name`, `mobile`, `email`, `password`) VALUES
(1, 'h', 4, '4', '4'),
(2, 'h', 1234, 'f', 'f'),
(3, 'h', 6, 'h', 'h');

-- --------------------------------------------------------

--
-- Table structure for table `parkingrequest`
--

CREATE TABLE `parkingrequest` (
  `id` int(11) NOT NULL,
  `username` varchar(240) NOT NULL,
  `vehicle_no` varchar(240) NOT NULL,
  `email` varchar(240) NOT NULL,
  `parkingname` varchar(240) NOT NULL,
  `date` date NOT NULL,
  `timeFrom` time NOT NULL,
  `timeTo` time NOT NULL,
  `flag` int(11) NOT NULL DEFAULT 0,
  `qrst` int(11) NOT NULL DEFAULT 0,
  `qrname` int(11) NOT NULL DEFAULT 0,
  `qrinfo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parkingrequest`
--

INSERT INTO `parkingrequest` (`id`, `username`, `vehicle_no`, `email`, `parkingname`, `date`, `timeFrom`, `timeTo`, `flag`, `qrst`, `qrname`, `qrinfo`) VALUES
(28, 'h', '123456', 'h', 'Place Name: swargate', '2022-11-09', '22:41:00', '22:41:00', 1, 0, 0, ''),
(29, 'h', '1234', 'h', 'Place Name: swargate', '2022-11-29', '13:03:00', '13:03:00', 0, 0, 0, ''),
(30, 'h', '1234', 'h', 'Place Name: swargate', '2022-11-29', '14:01:00', '14:01:00', 0, 0, 0, ''),
(31, 'h', '1234', 'h', 'Place Name: swargate', '2022-11-28', '14:13:00', '14:13:00', 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `parking_area`
--

CREATE TABLE `parking_area` (
  `loc_id` int(11) NOT NULL,
  `lat` varchar(200) NOT NULL,
  `lon` varchar(200) NOT NULL,
  `pname` varchar(240) NOT NULL,
  `avail_slots` varchar(240) NOT NULL,
  `rate` varchar(240) NOT NULL,
  `ptype` varchar(240) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `parking_area`
--

INSERT INTO `parking_area` (`loc_id`, `lat`, `lon`, `pname`, `avail_slots`, `rate`, `ptype`) VALUES
(4, '18.5239547', '73.8490738', 'jm road', '25', '2', 'apartment'),
(5, '18.5236267', '73.8494414', 'swargate', '12', '24', 'apartment '),
(6, '18.5244625', '73.8504958', 'swargate', '25', '5', 'apartments'),
(7, '18.56', '73', '123', '25', '25', 'pune');

-- --------------------------------------------------------

--
-- Table structure for table `user_qr`
--

CREATE TABLE `user_qr` (
  `id` int(11) NOT NULL,
  `username` varchar(240) DEFAULT NULL,
  `vehicle_no` varchar(240) DEFAULT NULL,
  `email` varchar(240) DEFAULT NULL,
  `mobile` varchar(20) NOT NULL,
  `area` varchar(249) DEFAULT NULL,
  `Rdate` varchar(240) DEFAULT NULL,
  `qr_info` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_qr`
--

INSERT INTO `user_qr` (`id`, `username`, `vehicle_no`, `email`, `mobile`, `area`, `Rdate`, `qr_info`) VALUES
(1, 'Santosh', 'MH11F1215', 'santosh.insrc@gmail.com', '9876543222', 'Katraj', '06/06/2019', ''),
(2, 'Harshal', 'MH20A2213', 'harshalshingne@gmail.com', '9875432242', 'Kothrud', '06/06/2019', '');

-- --------------------------------------------------------

--
-- Table structure for table `user_reg`
--

CREATE TABLE `user_reg` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `mobile` bigint(20) NOT NULL,
  `area` text NOT NULL,
  `cardno` varchar(240) NOT NULL,
  `cvv` varchar(240) NOT NULL,
  `amount` varchar(240) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_reg`
--

INSERT INTO `user_reg` (`id`, `name`, `email`, `password`, `mobile`, `area`, `cardno`, `cvv`, `amount`) VALUES
(5, 'h', 'h', 'h', 8, 'swargate', '1111111111111', '123', '895'),
(6, 'rahul', 'rahul@gmail.com', '123', 8421050145, 'shivajinagar', '', '', ''),
(7, 'a', 'itsap004@gmail.com', '123456', 9730325138, 'pune', '123456789', '123', '100');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `admin_reg`
--
ALTER TABLE `admin_reg`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parkingrequest`
--
ALTER TABLE `parkingrequest`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `parking_area`
--
ALTER TABLE `parking_area`
  ADD PRIMARY KEY (`loc_id`);

--
-- Indexes for table `user_qr`
--
ALTER TABLE `user_qr`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_reg`
--
ALTER TABLE `user_reg`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `admin_reg`
--
ALTER TABLE `admin_reg`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `parkingrequest`
--
ALTER TABLE `parkingrequest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `parking_area`
--
ALTER TABLE `parking_area`
  MODIFY `loc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user_qr`
--
ALTER TABLE `user_qr`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user_reg`
--
ALTER TABLE `user_reg`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
