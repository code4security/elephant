/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.17 : Database - game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`game` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `game`;

/*Table structure for table `channel_and_version` */

DROP TABLE IF EXISTS `channel_and_version`;

CREATE TABLE `channel_and_version` (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `is_available` tinyint(1) NOT NULL COMMENT '是否可用',
  `channel_info` varchar(255) NOT NULL COMMENT '渠道全称',
  `version_num` varchar(255) NOT NULL COMMENT '版本号',
  `version_download` varchar(255) NOT NULL COMMENT '版本更新路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='渠道和版本信息表';

/*Table structure for table `channel_pay_parameter` */

DROP TABLE IF EXISTS `channel_pay_parameter`;

CREATE TABLE `channel_pay_parameter` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `sub_channel_id` int(11) DEFAULT NULL COMMENT '子渠道',
  `os` varchar(20) NOT NULL COMMENT '操作系统',
  `app_key` varchar(64) DEFAULT NULL,
  `app_secret` varchar(255) DEFAULT NULL,
  `app_id` varchar(64) DEFAULT NULL,
  `public_key` varchar(1024) DEFAULT NULL,
  `private_key` varchar(2048) DEFAULT NULL,
  `pay_public_key` varchar(1024) DEFAULT NULL,
  `pay_private_key` varchar(2048) DEFAULT NULL,
  `server_id` varchar(10) DEFAULT NULL,
  `merchant_id` varchar(10) DEFAULT NULL,
  `cp_id` varchar(20) DEFAULT NULL,
  `pay_key` varchar(64) DEFAULT NULL,
  `notify_url` varchar(255) DEFAULT NULL COMMENT '回调下载地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='设置渠道支付回调参数信息表';

/*Table structure for table `game` */

DROP TABLE IF EXISTS `game`;

CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `name_cn` varchar(32) NOT NULL COMMENT '中文名',
  `name_en` varchar(64) DEFAULT NULL COMMENT '英文名',
  `type` int(11) DEFAULT NULL COMMENT '类型（单机、网游）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='游戏名单信息表';

/*Table structure for table `game_jump_site` */

DROP TABLE IF EXISTS `game_jump_site`;

CREATE TABLE `game_jump_site` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `button_id` int(11) NOT NULL COMMENT '按钮id',
  `is_show` int(11) NOT NULL COMMENT '1-显示 0-不显示',
  `jump_url` varchar(255) NOT NULL COMMENT '跳转网址',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `number` int(11) DEFAULT NULL COMMENT '点击次数',
  `player_num` int(11) DEFAULT NULL COMMENT '点击人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏内周边按钮跳转页面信息表';

/*Table structure for table `game_notify` */

DROP TABLE IF EXISTS `game_notify`;

CREATE TABLE `game_notify` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `content` varchar(255) DEFAULT NULL COMMENT '公告内容',
  `status` int(11) DEFAULT NULL COMMENT '展示状态（0,1,2）',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏内展示公告信息表';

/*Table structure for table `harmony_word` */

DROP TABLE IF EXISTS `harmony_word`;

CREATE TABLE `harmony_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(250) NOT NULL COMMENT '违禁词名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='违禁词表';

/*Table structure for table `ip_location` */

DROP TABLE IF EXISTS `ip_location`;

CREATE TABLE `ip_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `start_ip_num` bigint(20) NOT NULL COMMENT '开始ip数',
  `end_ip_num` bigint(20) NOT NULL COMMENT '结束ip数',
  `locid` int(11) NOT NULL COMMENT '标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ip记录表';

/*Table structure for table `lipinma` */

DROP TABLE IF EXISTS `lipinma`;

CREATE TABLE `lipinma` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `lp_list_id` varchar(10) DEFAULT NULL COMMENT '礼品码信息',
  `lp_id` int(11) NOT NULL COMMENT '批次号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼品码生成表';

/*Table structure for table `lipinma_list` */

DROP TABLE IF EXISTS `lipinma_list`;

CREATE TABLE `lipinma_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `type` int(11) NOT NULL COMMENT '分类（0：激活码:1：兑换码:2:通用码）',
  `lp_name` varchar(50) NOT NULL COMMENT '礼品码名称',
  `desc_txt` varchar(500) DEFAULT NULL COMMENT '礼品码说明',
  `lp_reward_id` varchar(100) DEFAULT NULL COMMENT '奖励礼品id',
  `lp_num` int(11) DEFAULT NULL COMMENT '礼品码数量',
  `begin_time` varchar(255) DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(255) DEFAULT NULL COMMENT '结束时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='礼品码信息表';

/*Table structure for table `mail` */

DROP TABLE IF EXISTS `mail`;

CREATE TABLE `mail` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `send_role_id` bigint(20) NOT NULL COMMENT '发送者id',
  `recv_role_id` bigint(20) NOT NULL COMMENT '接收者id',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `type` smallint(6) NOT NULL COMMENT '邮件类型',
  `title` varchar(100) DEFAULT NULL COMMENT '邮件标题',
  `context` varchar(1024) DEFAULT NULL COMMENT '邮件内容',
  `goods_id` int(11) DEFAULT NULL COMMENT '物品id',
  `goods_num` int(11) DEFAULT NULL COMMENT '物品数量',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '邮件状态（未领，已领）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件表';

/*Table structure for table `module_switch` */

DROP TABLE IF EXISTS `module_switch`;

CREATE TABLE `module_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(10) NOT NULL COMMENT '渠道id',
  `module_name` varchar(32) DEFAULT NULL COMMENT '组件名字',
  `status` tinyint(1) DEFAULT NULL COMMENT '激活码开关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏模块开关';

/*Table structure for table `pay_goods` */

DROP TABLE IF EXISTS `pay_goods`;

CREATE TABLE `pay_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name_cn` varchar(255) DEFAULT NULL COMMENT '商品中文名',
  `goods_name` varchar(255) NOT NULL COMMENT '商品英文名',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `currency` int(11) DEFAULT NULL COMMENT '虚拟货币',
  `rmb` double DEFAULT NULL COMMENT '人民币',
  `goods_des` varchar(255) DEFAULT NULL COMMENT '商品说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏商店中展示的商品表';

/*Table structure for table `player` */

DROP TABLE IF EXISTS `player`;

CREATE TABLE `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `device_model` varchar(255) NOT NULL COMMENT '设备',
  `device_uniquely_id` varchar(250) NOT NULL COMMENT '设备id',
  `first_login_time` datetime NOT NULL COMMENT '首次登陆',
  `first_login_ip` varchar(250) NOT NULL COMMENT '首次登陆ip',
  `last_login_time` datetime NOT NULL COMMENT '最后登陆时间',
  `last_login_ip` varchar(250) NOT NULL COMMENT '最后登陆ip',
  `ip_region` int(11) NOT NULL COMMENT 'ip所在区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家信息表';

/*Table structure for table `player_ban_list` */

DROP TABLE IF EXISTS `player_ban_list`;

CREATE TABLE `player_ban_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `ban_minute` int(11) DEFAULT NULL COMMENT '封停时间',
  `is_ban` tinyint(1) DEFAULT NULL COMMENT '是否处于封停状态',
  `ban_time` datetime DEFAULT NULL COMMENT '封停开始时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家封账号表\n';

/*Table structure for table `player_channel` */

DROP TABLE IF EXISTS `player_channel`;

CREATE TABLE `player_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `sub_channel` int(11) DEFAULT NULL COMMENT '子渠道',
  `channel_user_id` varchar(250) NOT NULL COMMENT '渠道用户id',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道玩家信息表';

/*Table structure for table `player_game_oss` */

DROP TABLE IF EXISTS `player_game_oss`;

CREATE TABLE `player_game_oss` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) DEFAULT NULL COMMENT '渠道id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `bucket` varchar(64) NOT NULL COMMENT 'oss bucket',
  `obj_type` int(11) DEFAULT NULL COMMENT '存档分类',
  `obj_key` varchar(64) DEFAULT NULL COMMENT 'oss key',
  `gold` bigint(20) DEFAULT NULL COMMENT '游戏金币数量',
  `save_time` varchar(64) DEFAULT NULL COMMENT '保存时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `end_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `meta_md5` varchar(64) DEFAULT NULL COMMENT '上传验证用',
  `fname` varchar(64) DEFAULT NULL COMMENT '存档名称',
  `backup_oss_time` datetime DEFAULT NULL COMMENT '上一次存档时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家存档信息表';

/*Table structure for table `player_lipinma_log` */

DROP TABLE IF EXISTS `player_lipinma_log`;

CREATE TABLE `player_lipinma_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `lp_list_id` varchar(10) NOT NULL COMMENT '礼品码id',
  `lp_id` int(11) NOT NULL COMMENT '礼品码批次id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `activate_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家领取的礼品码记录信息表';

/*Table structure for table `player_login_log` */

DROP TABLE IF EXISTS `player_login_log`;

CREATE TABLE `player_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  `is_login` tinyint(1) NOT NULL COMMENT '是否登陆',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `device_model` varchar(250) DEFAULT NULL COMMENT '设备名称',
  `Device_uniquely_id` varchar(250) DEFAULT NULL COMMENT '设备id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家登陆日志信息表';

/*Table structure for table `player_pay_log` */

DROP TABLE IF EXISTS `player_pay_log`;

CREATE TABLE `player_pay_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `rmb` float NOT NULL COMMENT '充值金额',
  `currency_get` int(11) DEFAULT NULL COMMENT '发送虚拟货币',
  `currency_get_extra` int(11) DEFAULT NULL COMMENT '附加虚拟货币',
  `order_id` varchar(128) NOT NULL COMMENT '订单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='玩家购买物品信息表';

/*Table structure for table `player_role` */

DROP TABLE IF EXISTS `player_role`;

CREATE TABLE `player_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  `role_name` varchar(64) NOT NULL COMMENT '角色名字',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `last_login_time` datetime NOT NULL COMMENT '最后登陆时间',
  `last_login_server` int(11) DEFAULT NULL COMMENT '最后登陆服务器',
  `friendid` int(11) DEFAULT NULL COMMENT '好友id',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `minute` bigint(20) DEFAULT NULL COMMENT '在线时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reg_friend` tinyint(4) DEFAULT NULL COMMENT '添加好友数量',
  `is_deleted` tinyint(4) DEFAULT NULL COMMENT '删除好友数量',
  `adtime` datetime DEFAULT NULL COMMENT '去广告',
  `viptime` datetime DEFAULT NULL COMMENT '黄金会员',
  `currency` int(11) DEFAULT NULL COMMENT '虚拟货币数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏角色信息表';

/*Table structure for table `server` */

DROP TABLE IF EXISTS `server`;

CREATE TABLE `server` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `game_id` varchar(11) NOT NULL COMMENT '游戏id',
  `language` varchar(250) DEFAULT NULL COMMENT '语言',
  `name` varchar(250) NOT NULL COMMENT '名字',
  `is_available` tinyint(1) NOT NULL COMMENT '是否可用',
  `recommend` smallint(6) NOT NULL COMMENT '是否推荐',
  `ip` varchar(128) NOT NULL COMMENT '服务器ip地址（外网）',
  `ip_internal` varchar(128) DEFAULT NULL COMMENT '服务器ip地址（内网）',
  `port_number` int(11) DEFAULT NULL COMMENT '端口号',
  `start_url` varchar(250) NOT NULL,
  `version_num` varchar(250) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务器信息表';

/*Table structure for table `server_history` */

DROP TABLE IF EXISTS `server_history`;

CREATE TABLE `server_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `game_id` varchar(255) NOT NULL COMMENT '游戏id',
  `channel_id` varchar(255) NOT NULL COMMENT '渠道id',
  `player_id` bigint(20) NOT NULL COMMENT '玩家id',
  `server_id` int(11) NOT NULL COMMENT '服务器id',
  `last_login_time` datetime NOT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登陆服务器历史记录表';

/*Table structure for table `virtual_currency` */

DROP TABLE IF EXISTS `virtual_currency`;

CREATE TABLE `virtual_currency` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit` varchar(20) NOT NULL COMMENT '货币编号',
  `explain` varchar(20) DEFAULT NULL COMMENT '货币说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏内虚拟货币单位信息表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
