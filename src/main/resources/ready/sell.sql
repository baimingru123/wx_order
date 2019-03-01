/*
Navicat MySQL Data Transfer

Source Server         : 虚拟机(5.7.17版本)
Source Server Version : 50717
Source Host           : 192.168.1.132:3306
Source Database       : sell

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-11 17:42:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `product_quantity` int(11) NOT NULL COMMENT '商品数量',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '商品图片',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '123456', '123457', '皮皮虾', '30.00', '1', 'http://xxx.jpg', '2019-01-11 17:14:14', '2019-01-11 17:17:53');
INSERT INTO `order_detail` VALUES ('2', '123456', '123456', '皮蛋粥', '3.20', '1', 'http://xxx.jpg', '2019-01-11 17:17:30', '2019-01-11 17:18:12');
INSERT INTO `order_detail` VALUES ('3', '123456', '123458', '皮皮豆腐', '1.20', '1', 'http://xxx.jpg', '2019-01-11 17:17:30', '2019-01-11 17:18:12');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态,默认0 新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态，默认0未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('123456', '小白', '18754190709', '大金新苑', 'bmr1008', '34.40', '0', '0', '2019-01-11 16:59:09', '2019-01-11 17:09:45');
INSERT INTO `order_master` VALUES ('123457', '小白', '18754190709', '大金新苑', 'bmr1008', '30.00', '0', '0', '2019-01-11 17:01:08', '2019-01-11 17:01:08');
INSERT INTO `order_master` VALUES ('123458', '杜姐', '15966699226', '金科城', 'duyan1008', '63.60', '0', '0', '2019-01-11 17:02:09', '2019-01-11 17:02:09');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名称',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='类目表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1', '热销榜', '2', '2019-01-11 09:50:14', '2019-01-11 09:50:14');
INSERT INTO `product_category` VALUES ('2', '女生最爱', '10', '2019-01-11 10:50:58', '2019-01-11 11:19:18');
INSERT INTO `product_category` VALUES ('3', '供不应求榜', '1', '2019-01-11 11:10:18', '2019-01-11 13:50:59');
INSERT INTO `product_category` VALUES ('4', '男生最爱', '3', '2019-01-11 11:39:00', '2019-01-11 11:39:00');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '商品图片',
  `product_status` int(11) NOT NULL DEFAULT '0' COMMENT '商品状态  0上级 1下架',
  `category_type` int(11) NOT NULL COMMENT '所属类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('123456', '皮蛋粥', '3.20', '100', '很好喝的粥', 'http://xxx.jpg', '0', '2', '2019-01-11 14:04:16', '2019-01-11 16:13:13');
INSERT INTO `product_info` VALUES ('123457', '皮皮虾', '30.00', '100', '很好吃的虾', 'http://xxx.jpg', '0', '2', '2019-01-11 14:38:44', '2019-01-11 17:09:25');
INSERT INTO `product_info` VALUES ('123458', '皮皮豆腐', '1.20', '100', '很好吃的豆腐', 'http://xxx.jpg', '0', '3', '2019-01-11 14:39:23', '2019-01-11 16:13:15');
