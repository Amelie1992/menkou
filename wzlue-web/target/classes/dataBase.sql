/*项目数据库记录*/


-- 客户表 @author Weisiqi
DROP TABLE IF EXISTS wechat_user;
CREATE TABLE `wechat_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `openid` VARCHAR(255) DEFAULT NULL,
  `unionId` VARCHAR(255) DEFAULT NULL,
  `nick_name` VARCHAR(255) DEFAULT NULL COMMENT '用户昵称',
  `avatar_url` VARCHAR(255) DEFAULT NULL COMMENT '用户头像',
  `gender` VARCHAR(255) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `street` VARCHAR(255) DEFAULT NULL COMMENT '街道',
  `city` VARCHAR(255) DEFAULT NULL COMMENT '用户所在城市',
  `province` VARCHAR(255) DEFAULT NULL COMMENT '用户所在省份',
  `country` VARCHAR(255) DEFAULT NULL COMMENT '用户所在国家',
  `language` VARCHAR(255) DEFAULT NULL COMMENT '用户的语言，简体中文为zh_CN',
  `device_id` VARCHAR(255) DEFAULT NULL COMMENT '设备id',
  `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建日期',
  `update_by` BIGINT(20) DEFAULT NULL COMMENT '修改人',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='微信用户表'
-- end

-- 客户收货地址表 @author Weisiqi
DROP TABLE IF EXISTS wechat_address;
CREATE TABLE `wechat_address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `wx_user_id` BIGINT(20) DEFAULT NULL,
  `user_uame` VARCHAR(255) DEFAULT NULL COMMENT '收货人姓名',
  `province` VARCHAR(255) DEFAULT NULL COMMENT '国标收货地址第一级地址',
  `city` VARCHAR(255) DEFAULT NULL COMMENT '国标收货地址第二级地址',
  `county` VARCHAR(255) DEFAULT NULL COMMENT '国标收货地址第三级地址',
  `street` VARCHAR(255) DEFAULT NULL COMMENT '街道',
  `detail_info` VARCHAR(255) DEFAULT NULL COMMENT '详细收货地址信息',
  `tel_number` VARCHAR(255) DEFAULT NULL COMMENT '收货人手机号码',
  `create_by` BIGINT(20) DEFAULT NULL COMMENT '创建人',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建日期',
  `update_by` BIGINT(20) DEFAULT NULL COMMENT '修改人',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改日期',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='微信用户收货表'
-- end

-- 商品表 @author Zeyee
DROP TABLE IF EXISTS t_goods;
CREATE TABLE `t_goods` (
   goods_name VARCHAR(100) NOT NULL COMMENT'名称',
   original_price DECIMAL(10,2) NOT NULL COMMENT'商品原价',
   description text COMMENT'详情描述',
   pic_url VARCHAR(200) NOT NULL COMMENT'图片',
   goods_standard VARCHAR(100) NULL COMMENT'规格信息',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动商品表'
-- end

-- 拼团活动表 @author Zeyee
DROP TABLE IF EXISTS wechat_group_purchase;
CREATE TABLE `wechat_group_purchase` (
   name VARCHAR(100) NOT NULL COMMENT'名称',
   purchase_code VARCHAR(100) NOT NULL COMMENT '活动编码',
   original_price DECIMAL(10,2) NOT NULL COMMENT'活动原价',
   group_price DECIMAL(10,2) NOT NULL COMMENT'团购价',
   desprition  VARCHAR(200) COMMENT'描述',
   image_url VARCHAR(200) COMMENT'图片',
   details TEXT COMMENT'详情',
   good_id BIGINT(200) NOT NULL COMMENT'商品id',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   event_status INT(2) DEFAULT 1 NOT NULL COMMENT '订单的状态 1.已创建 2.已付款 3.结束 4.已退款 5.退款成功 6.退款失败',
   event_start_time TIMESTAMP COMMENT'活动开始时间',
   event_end_time TIMESTAMP COMMENT'活动结束时间',
   category_id BIGINT(20) COMMENT'分类id',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='拼团活动表'
-- end

-- 活动商品记录表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_goods_log;
CREATE TABLE `wechat_event_goods_log` (
   name VARCHAR(100) NOT NULL COMMENT'名称',
   original_price DECIMAL(10,2) NOT NULL COMMENT'商品原价',
   description VARCHAR(200) COMMENT'描述',
   bg_image VARCHAR(200) NOT NULL COMMENT'图片',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   purchase_id BIGINT(20) NOT NULL COMMENT'活动id' ,
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动商品记录表'
-- end

-- 拼团订单表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_order;
CREATE TABLE `wechat_event_order` (
   ord_code VARCHAR(100) NOT NULL COMMENT'订单编码',
   ord_status INT(2) NOT NULL DEFAULT 1 COMMENT'订单的状态 1.已创建 2已付款 3.已结束 4.已退款 5.退款成功 6.退款失败',
   customer_id BIGINT(20) NOT NULL COMMENT'客户id',
   purchase_id BIGINT(20) NOT NULL COMMENT'活动id' ,
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   consignee VARCHAR(50) NOT NULL COMMENT'收货人',
   telphone VARCHAR(11) NOT NULL COMMENT'联系电话',
   receipt_address VARCHAR(500) NOT NULL COMMENT'收货地址',
   event_start_time TIMESTAMP COMMENT'活动开始时间',
   event_end_time TIMESTAMP COMMENT'活动结束时间',
   original_price DECIMAL(10,2) NOT NULL COMMENT'活动原价',
   group_price DECIMAL(10,2) NOT NULL COMMENT'团购价',
   payment_amount DECIMAL(10,2) COMMENT'实付金额',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='拼团订单表'
-- end

-- 活动分类表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_category;
CREATE TABLE `wechat_event_category` (
   name VARCHAR(100) NOT NULL COMMENT'分类名称',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动分类表'
-- end

-- 商品规格表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_goods_standard;
CREATE TABLE `wechat_event_goods_standard` (
   NAME VARCHAR(100) NOT NULL COMMENT'名称',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   parent_id BIGINT(20) NOT NULL DEFAULT 0 COMMENT'上级键值, 目前只是两级层次',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='商品规格表'
-- end

-- 订单商品表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_order_goods_log;
CREATE TABLE `wechat_event_order_goods_log` (
   NAME VARCHAR(100) NOT NULL COMMENT'名称',
   original_price DECIMAL(10,2) NOT NULL COMMENT'商品原价',
   description VARCHAR(200) COMMENT'描述',
   bg_image VARCHAR(200) NOT NULL COMMENT'图片',
   order_id BIGINT(20) NOT NULL COMMENT'订单id',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单商品记录表'
-- end

-- 订单收获地址表 @author Zeyee
DROP TABLE IF EXISTS wechat_event_order_address_log;
CREATE TABLE `wechat_event_order_address_log` (
   customer_name VARCHAR(100) NOT NULL COMMENT'客户姓名',
   telphone VARCHAR(11) NOT NULL COMMENT'联系电话',
   province BIGINT(20) NOT NULL COMMENT '省份',
   city BIGINT(20) NOT NULL COMMENT'市区',
   county BIGINT(20) NOT NULL COMMENT'县镇',
   street BIGINT(20) NULL COMMENT '街道',
   detail_address VARCHAR(200) NULL COMMENT'详细地址',
   order_id BIGINT(20) NOT NULL COMMENT'订单id',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单收货地址记录表'
-- end

-- 订单物流表
DROP TABLE IF EXISTS wechat_event_order_logistics;
CREATE TABLE `wechat_event_order_logistics` (
   logistics_name VARCHAR(100) NOT NULL COMMENT'物流服务名称',
   logistics_code VARCHAR(100) NOT NULL COMMENT'物流服务代码',
   logistics_no VARCHAR(100) NOT NULL COMMENT'物流运单号',
   order_id BIGINT(20) NOT NULL COMMENT'订单id',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='订单物流表'
-- end

-- ----------------------------
-- Table structure for sys_logistics
-- ----------------------------
DROP TABLE IF EXISTS `sys_logistics`;
CREATE TABLE `sys_logistics` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `service_id` varchar(10) DEFAULT NULL COMMENT '服务id',
  `code` varchar(10) DEFAULT NULL COMMENT 'code',
  `sort` int(10) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='物流表';

-- ----------------------------
-- Records of sys_logistics
-- ----------------------------
INSERT INTO `sys_logistics` VALUES ('1', '安捷快递', null, 'AJ', '0');
INSERT INTO `sys_logistics` VALUES ('2', '安能物流', null, 'ANE', '0');
INSERT INTO `sys_logistics` VALUES ('3', '安信达快递', null, 'AXD', '0');
INSERT INTO `sys_logistics` VALUES ('4', '北青小红帽', null, 'BQXHM', '0');
INSERT INTO `sys_logistics` VALUES ('5', '百福东方', null, 'BFDF', '0');
INSERT INTO `sys_logistics` VALUES ('6', '百世快运', null, 'BTWL', '0');
INSERT INTO `sys_logistics` VALUES ('7', 'CCES快递', null, 'CCES', '0');
INSERT INTO `sys_logistics` VALUES ('8', '城市100', null, 'CITY100', '0');
INSERT INTO `sys_logistics` VALUES ('9', 'COE东方快递', null, 'COE,', '0');
INSERT INTO `sys_logistics` VALUES ('10', '长沙创一', null, 'CSCY', '0');
INSERT INTO `sys_logistics` VALUES ('11', '成都善途速运', null, 'CDSTKY', '0');
INSERT INTO `sys_logistics` VALUES ('12', '德邦', null, 'DBL', '0');
INSERT INTO `sys_logistics` VALUES ('13', 'D速物流', null, 'DSWL', '0');
INSERT INTO `sys_logistics` VALUES ('14', '大田物流', null, 'DTWL', '0');
INSERT INTO `sys_logistics` VALUES ('15', 'EMS', null, 'EMS', '0');
INSERT INTO `sys_logistics` VALUES ('16', '快捷速递', null, 'FAST', '0');
INSERT INTO `sys_logistics` VALUES ('17', 'FEDEX联邦(国内件)', null, 'FEDEX', '0');
INSERT INTO `sys_logistics` VALUES ('18', 'FEDEX联邦(国际件）', null, 'FEDEX_GJ', '0');
INSERT INTO `sys_logistics` VALUES ('19', '飞康达', null, 'FKD', '0');
INSERT INTO `sys_logistics` VALUES ('20', '广东邮政', null, 'GDEMS', '0');
INSERT INTO `sys_logistics` VALUES ('21', '共速达', null, 'GSD', '0');
INSERT INTO `sys_logistics` VALUES ('22', '国通快递', null, 'GTO', '0');
INSERT INTO `sys_logistics` VALUES ('23', '高铁速递', null, 'GTSD', '0');
INSERT INTO `sys_logistics` VALUES ('24', '汇丰物流', null, 'HFWL', '0');
INSERT INTO `sys_logistics` VALUES ('25', '天天快递', null, 'HHTT', '0');
INSERT INTO `sys_logistics` VALUES ('26', '恒路物流', null, 'HLWL', '0');
INSERT INTO `sys_logistics` VALUES ('27', '天地华宇', null, 'HOAU', '0');
INSERT INTO `sys_logistics` VALUES ('28', '华强物流', null, 'hq568', '0');
INSERT INTO `sys_logistics` VALUES ('29', '百世快递', null, 'HTKY', '0');
INSERT INTO `sys_logistics` VALUES ('30', '华夏龙物流', null, 'HXLWL', '0');
INSERT INTO `sys_logistics` VALUES ('31', '好来运快递', null, 'HYLSD', '0');
INSERT INTO `sys_logistics` VALUES ('32', '京广速递', null, 'JGSD', '0');
INSERT INTO `sys_logistics` VALUES ('33', '九曳供应链', null, 'JIUYE', '0');
INSERT INTO `sys_logistics` VALUES ('34', '佳吉快运', null, 'JJKY', '0');
INSERT INTO `sys_logistics` VALUES ('35', '嘉里物流', null, 'JLDT', '0');
INSERT INTO `sys_logistics` VALUES ('36', '捷特快递', null, 'JTKD', '0');
INSERT INTO `sys_logistics` VALUES ('37', '急先达', null, 'JXD', '0');
INSERT INTO `sys_logistics` VALUES ('38', '晋越快递', null, 'JYKD', '0');
INSERT INTO `sys_logistics` VALUES ('39', '加运美', null, 'JYM', '0');
INSERT INTO `sys_logistics` VALUES ('40', '佳怡物流', null, 'JYWL', '0');
INSERT INTO `sys_logistics` VALUES ('41', '跨越物流', null, 'KYWL', '0');
INSERT INTO `sys_logistics` VALUES ('42', '龙邦快递', null, 'LB', '0');
INSERT INTO `sys_logistics` VALUES ('43', '联昊通速递', null, 'LHT', '0');
INSERT INTO `sys_logistics` VALUES ('44', '民航快递', null, 'MHKD', '0');
INSERT INTO `sys_logistics` VALUES ('45', '明亮物流', null, 'MLWL', '0');
INSERT INTO `sys_logistics` VALUES ('46', '能达速递', null, 'NEDA', '0');
INSERT INTO `sys_logistics` VALUES ('47', '平安达腾飞快递', null, 'PADTF', '0');
INSERT INTO `sys_logistics` VALUES ('48', '全晨快递', null, 'QCKD', '0');
INSERT INTO `sys_logistics` VALUES ('49', '全峰快递', null, 'QFKD', '0');
INSERT INTO `sys_logistics` VALUES ('50', '全日通快递', null, 'QRT', '0');
INSERT INTO `sys_logistics` VALUES ('51', '如风达', null, 'RFD', '0');
INSERT INTO `sys_logistics` VALUES ('52', '赛澳递', null, 'SAD', '0');
INSERT INTO `sys_logistics` VALUES ('53', '圣安物流', null, 'SAWL', '0');
INSERT INTO `sys_logistics` VALUES ('54', '盛邦物流', null, 'SBWL', '0');
INSERT INTO `sys_logistics` VALUES ('55', '上大物流', null, 'SDWL', '0');
INSERT INTO `sys_logistics` VALUES ('56', '顺丰快递', null, 'SF', '0');
INSERT INTO `sys_logistics` VALUES ('57', '盛丰物流', null, 'SFWL', '0');
INSERT INTO `sys_logistics` VALUES ('58', '盛辉物流', null, 'SHWL', '0');
INSERT INTO `sys_logistics` VALUES ('59', '速通物流', null, 'ST', '0');
INSERT INTO `sys_logistics` VALUES ('60', '申通快递', null, 'STO', '0');
INSERT INTO `sys_logistics` VALUES ('61', '速腾快递', null, 'STWL', '0');
INSERT INTO `sys_logistics` VALUES ('62', '速尔快递', null, 'SURE', '0');
INSERT INTO `sys_logistics` VALUES ('63', '唐山申通', null, 'TSSTO', '0');
INSERT INTO `sys_logistics` VALUES ('64', '全一快递', null, 'UAPEX', '0');
INSERT INTO `sys_logistics` VALUES ('65', '优速快递', null, 'UC', '0');
INSERT INTO `sys_logistics` VALUES ('66', '万家物流', null, 'WJWL', '0');
INSERT INTO `sys_logistics` VALUES ('67', '万象物流', null, 'WXWL', '0');
INSERT INTO `sys_logistics` VALUES ('68', '新邦物流', null, 'XBWL', '0');
INSERT INTO `sys_logistics` VALUES ('69', '信丰快递', null, 'XFEX', '0');
INSERT INTO `sys_logistics` VALUES ('70', '希优特', null, 'XYT', '0');
INSERT INTO `sys_logistics` VALUES ('71', '新杰物流', null, 'XJ', '0');
INSERT INTO `sys_logistics` VALUES ('72', '源安达快递', null, 'YADEX', '0');
INSERT INTO `sys_logistics` VALUES ('73', '远成物流', null, 'YCWL', '0');
INSERT INTO `sys_logistics` VALUES ('74', '韵达快递', null, 'YD', '0');
INSERT INTO `sys_logistics` VALUES ('75', '义达国际物流', null, 'YDH', '0');
INSERT INTO `sys_logistics` VALUES ('76', '越丰物流', null, 'YFEX', '0');
INSERT INTO `sys_logistics` VALUES ('77', '原飞航物流', null, 'YFHEX', '0');
INSERT INTO `sys_logistics` VALUES ('78', '亚风快递', null, 'YFSD', '0');
INSERT INTO `sys_logistics` VALUES ('79', '运通快递', null, 'YTKD', '0');
INSERT INTO `sys_logistics` VALUES ('80', '圆通速递', null, 'YTO', '0');
INSERT INTO `sys_logistics` VALUES ('81', '亿翔快递', null, 'YXKD', '0');
INSERT INTO `sys_logistics` VALUES ('82', '邮政平邮/小包', null, 'YZPY', '0');
INSERT INTO `sys_logistics` VALUES ('83', '增益快递', null, 'ZENY', '0');
INSERT INTO `sys_logistics` VALUES ('84', '汇强快递', null, 'ZHQKD', '0');
INSERT INTO `sys_logistics` VALUES ('85', '宅急送', null, 'ZJS', '0');
INSERT INTO `sys_logistics` VALUES ('86', '众通快递', null, 'ZTE', '0');
INSERT INTO `sys_logistics` VALUES ('87', '中铁快运', null, 'ZTKY', '0');
INSERT INTO `sys_logistics` VALUES ('88', '中通速递', null, 'ZTO', '0');
INSERT INTO `sys_logistics` VALUES ('89', '中铁物流', null, 'ZTWL', '0');
INSERT INTO `sys_logistics` VALUES ('90', '中邮物流', null, 'ZYWL', '0');
INSERT INTO `sys_logistics` VALUES ('91', '亚马逊物流', null, 'AMAZON', '0');
INSERT INTO `sys_logistics` VALUES ('92', '速必达物流', null, 'SUBIDA', '0');
INSERT INTO `sys_logistics` VALUES ('93', '瑞丰速递', null, 'RFEX', '0');
INSERT INTO `sys_logistics` VALUES ('94', '快客快递', null, 'QUICK', '0');
INSERT INTO `sys_logistics` VALUES ('95', '城际快递', null, 'CJKD', '0');
INSERT INTO `sys_logistics` VALUES ('96', 'CNPEX中邮快递', null, 'CNPEX', '0');
INSERT INTO `sys_logistics` VALUES ('97', '鸿桥供应链', null, 'HOTSCM', '0');
INSERT INTO `sys_logistics` VALUES ('98', '海派通物流公司', null, 'HPTEX', '0');
INSERT INTO `sys_logistics` VALUES ('99', '澳邮专线', null, 'AYCA', '0');
INSERT INTO `sys_logistics` VALUES ('100', '泛捷快递', null, 'PANEX', '0');
INSERT INTO `sys_logistics` VALUES ('101', 'PCA Express', null, 'PCA', '0');
INSERT INTO `sys_logistics` VALUES ('102', 'UEQ Express', null, 'UEQ', '0');


-- 客户活动订单表
DROP TABLE IF EXISTS t_user_purchase_order;
CREATE TABLE `t_user_purchase_order` (
   user_id BIGINT(20) NOT NULL COMMENT'用户id',
   order_id BIGINT(20) NOT NULL COMMENT'订单id',
   purchase_id BIGINT(20) NULL COMMENT'活动id',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='客户活动订单表'
-- end

-- 图片表
DROP TABLE IF EXISTS t_images;
CREATE TABLE `t_images` (
   image_name VARCHAR(100) NOT NULL COMMENT'图片名',
   pic_url VARCHAR(255) NOT NULL COMMENT '图片路径',
   img_type VARCHAR(50) NOT NULL COMMENT '图片类型: 1. WZLUE_GOODS_IMG 商品图片',
   foreign_id BIGINT(20) NOT NULL COMMENT'外键id',
   created_by BIGINT(20) NOT NULL COMMENT'创建人',
   created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   updated_by BIGINT(20) NOT NULL COMMENT'修改人',
   updated_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'修改时间',
   id BIGINT(20) AUTO_INCREMENT NOT NULL COMMENT'主键id,自增',
   PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='图片表'
-- end