-- phpMyAdmin SQL Dump
-- version 4.4.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Dec 26, 2016 at 11:42 PM
-- Server version: 5.5.42
-- PHP Version: 5.6.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `dms`
--

-- --------------------------------------------------------

--
-- Table structure for table `activity`
--

CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `input_document_id` bigint(20) DEFAULT NULL,
  `output_document_id` bigint(20) DEFAULT NULL,
  `parent_process_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`id`, `name`) VALUES
(1, 'Company1');

-- --------------------------------------------------------

--
-- Table structure for table `descriptor`
--

CREATE TABLE `descriptor` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `descriptor`
--

INSERT INTO `descriptor` (`id`, `name`) VALUES
(1, 'descriptor1'),
(2, 'descriptor2');

-- --------------------------------------------------------

--
-- Table structure for table `document`
--

CREATE TABLE `document` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document`
--

INSERT INTO `document` (`id`, `name`) VALUES
(1, 'Document1'),
(2, 'Document2');

-- --------------------------------------------------------

--
-- Table structure for table `document_descriptor`
--

CREATE TABLE `document_descriptor` (
  `value` varchar(255) DEFAULT NULL,
  `document_id` bigint(20) NOT NULL,
  `descriptor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `document_descriptor`
--

INSERT INTO `document_descriptor` (`value`, `document_id`, `descriptor_id`) VALUES
('value for descriptor1', 1, 1),
('value for descriptor 2', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `process`
--

CREATE TABLE `process` (
  `id` bigint(20) NOT NULL,
  `is_primitive` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKk97sd3ap803dk2ed3xdv2k236` (`input_document_id`),
  ADD KEY `FKcn0plasvn0q5u4en77ukpsqk9` (`output_document_id`),
  ADD KEY `FK5hphtyq779e2uje8aj4kkbcxv` (`parent_process_id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `descriptor`
--
ALTER TABLE `descriptor`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `document`
--
ALTER TABLE `document`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `document_descriptor`
--
ALTER TABLE `document_descriptor`
  ADD PRIMARY KEY (`document_id`,`descriptor_id`),
  ADD KEY `FKrfwx5w142jqiqahvl4o6068kn` (`descriptor_id`);

--
-- Indexes for table `process`
--
ALTER TABLE `process`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtp6mcnqbhtack2e576vuncyqb` (`company_id`),
  ADD KEY `FKj2u8onhd3seyndnuf3xx3d6hh` (`parent_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `descriptor`
--
ALTER TABLE `descriptor`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `document`
--
ALTER TABLE `document`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `process`
--
ALTER TABLE `process`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `FK5hphtyq779e2uje8aj4kkbcxv` FOREIGN KEY (`parent_process_id`) REFERENCES `process` (`id`),
  ADD CONSTRAINT `FKcn0plasvn0q5u4en77ukpsqk9` FOREIGN KEY (`output_document_id`) REFERENCES `document` (`id`),
  ADD CONSTRAINT `FKk97sd3ap803dk2ed3xdv2k236` FOREIGN KEY (`input_document_id`) REFERENCES `document` (`id`);

--
-- Constraints for table `document_descriptor`
--
ALTER TABLE `document_descriptor`
  ADD CONSTRAINT `FKrfwx5w142jqiqahvl4o6068kn` FOREIGN KEY (`descriptor_id`) REFERENCES `descriptor` (`id`),
  ADD CONSTRAINT `FKqj8ykwtbqg9dg63lo0g9s0te` FOREIGN KEY (`document_id`) REFERENCES `document` (`id`);

--
-- Constraints for table `process`
--
ALTER TABLE `process`
  ADD CONSTRAINT `FKj2u8onhd3seyndnuf3xx3d6hh` FOREIGN KEY (`parent_id`) REFERENCES `process` (`id`),
  ADD CONSTRAINT `FKtp6mcnqbhtack2e576vuncyqb` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);
