
-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `productid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`productid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Truncate table before insert `student`
--

TRUNCATE TABLE `product`;
INSERT INTO `product`(`productid`, `name`, `description`, `quantity`) VALUES (null,"Stone","Greate Stone",12);