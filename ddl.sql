-- Table structure for table `suppliers`
CREATE TABLE `suppliers` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `suppliercode` varchar(100) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `suppliercode_UNIQUE` (`suppliercode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `customers`
CREATE TABLE `customers` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `customercode` varchar(100) NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `customercode_UNIQUE` (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `users`
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `category` enum('admin','normal') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `products`
CREATE TABLE `products` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `productcode` varchar(100) NOT NULL,
  `productname` varchar(50) NOT NULL,
  `costprice` double NOT NULL,
  `sellingprice` double NOT NULL,
  `brand` varchar(50) NOT NULL,
  PRIMARY KEY (`pid`),
  UNIQUE KEY `productcode_UNIQUE` (`productcode`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `purchaseinfo`
CREATE TABLE `purchaseinfo` (
  `purchaseid` int(11) NOT NULL AUTO_INCREMENT,
  `suppliercode` varchar(200) NOT NULL,
  `productcode` varchar(200) NOT NULL,
  `date` date NOT NULL,
  `quantity` int(11) NOT NULL,
  `totalcost` double NOT NULL,
  PRIMARY KEY (`purchaseid`),
  KEY `fk_purchaseinfo_supplier` (`suppliercode`),
  KEY `fk_purchaseinfo_product` (`productcode`),
  CONSTRAINT `fk_purchaseinfo_product` FOREIGN KEY (`productcode`) REFERENCES `products` (`productcode`) ON DELETE CASCADE,
  CONSTRAINT `fk_purchaseinfo_supplier` FOREIGN KEY (`suppliercode`) REFERENCES `suppliers` (`suppliercode`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Table structure for table `salesreport`
CREATE TABLE `salesreport` (
  `salesid` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `customercode` varchar(100) NOT NULL,
  `quantity` int(11) NOT NULL,
  `revenue` double NOT NULL,
  `soldby` int(11) NOT NULL,
  PRIMARY KEY (`salesid`),
  KEY `fk_salesreport_customer` (`customercode`),
  KEY `fk_salesreport_user` (`soldby`),
  CONSTRAINT `fk_salesreport_customer` FOREIGN KEY (`customercode`) REFERENCES `customers` (`customercode`) ON DELETE CASCADE,
  CONSTRAINT `fk_salesreport_user` FOREIGN KEY (`soldby`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Intermediate table for many-to-many relationship between products and salesreport
CREATE TABLE `product_salesreport` (
  `product_salesreport_id` int(11) NOT NULL AUTO_INCREMENT,
  `productcode` varchar(100) NOT NULL,
  `salesid` int(11) NOT NULL,
  PRIMARY KEY (`product_salesreport_id`),
  KEY `fk_product_salesreport_product` (`productcode`),
  KEY `fk_product_salesreport_salesreport` (`salesid`),
  CONSTRAINT `fk_product_salesreport_product` FOREIGN KEY (`productcode`) REFERENCES `products` (`productcode`) ON DELETE CASCADE,
  CONSTRAINT `fk_product_salesreport_salesreport` FOREIGN KEY (`salesid`) REFERENCES `salesreport` (`salesid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO suppliers (suppliercode, fullname, location, phone) VALUES
('SUPP001', 'Supplier One', 'New York', '1234567890'),
('SUPP002', 'Supplier Two', 'Los Angeles', '0987654321');
