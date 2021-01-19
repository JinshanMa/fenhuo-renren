/*
MySQL Backup
Database: renren_security
Backup Time: 2019-04-05 13:36:38
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `renren_security`.`qrtz_blob_triggers`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_calendars`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_cron_triggers`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_fired_triggers`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_job_details`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_locks`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_scheduler_state`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_simple_triggers`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `renren_security`.`qrtz_triggers`;
DROP TABLE IF EXISTS `renren_security`.`schedule_job`;
DROP TABLE IF EXISTS `renren_security`.`schedule_job_log`;
DROP TABLE IF EXISTS `renren_security`.`sys_config`;
DROP TABLE IF EXISTS `renren_security`.`sys_dept`;
DROP TABLE IF EXISTS `renren_security`.`sys_dict`;
DROP TABLE IF EXISTS `renren_security`.`sys_log`;
DROP TABLE IF EXISTS `renren_security`.`sys_menu`;
DROP TABLE IF EXISTS `renren_security`.`sys_oss`;
DROP TABLE IF EXISTS `renren_security`.`sys_role`;
DROP TABLE IF EXISTS `renren_security`.`sys_role_dept`;
DROP TABLE IF EXISTS `renren_security`.`sys_role_menu`;
DROP TABLE IF EXISTS `renren_security`.`sys_user`;
DROP TABLE IF EXISTS `renren_security`.`sys_user_role`;
DROP TABLE IF EXISTS `renren_security`.`tb_blayer`;
DROP TABLE IF EXISTS `renren_security`.`tb_building`;
DROP TABLE IF EXISTS `renren_security`.`tb_role_building`;
DROP TABLE IF EXISTS `renren_security`.`tb_room`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='定时任务';
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='部门管理';
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='数据字典表';
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='系统日志';
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='菜单管理';
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件上传';
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='角色';
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='系统用户';
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';
CREATE TABLE `tb_blayer` (
  `layer_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '楼层ID',
  `layer_name` varchar(255) NOT NULL COMMENT '楼层名称',
  `building_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '所在楼栋id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`layer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='楼层管理';
CREATE TABLE `tb_building` (
  `building_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '楼栋id',
  `address` varchar(255) DEFAULT NULL COMMENT '楼栋地址',
  `coding` varchar(255) DEFAULT NULL COMMENT '编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `floor_count` int(11) NOT NULL COMMENT '楼栋层数',
  `name` varchar(255) NOT NULL COMMENT '楼栋名称',
  `opt_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '是否已经删除',
  PRIMARY KEY (`building_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='楼栋管理';
CREATE TABLE `tb_role_building` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色楼栋关联表ID',
  `role_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '角色ID',
  `building_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '楼栋ID',
  `del_flag` int(1) NOT NULL DEFAULT '0' COMMENT '是否被删除字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
CREATE TABLE `tb_room` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `area_code` varchar(16) DEFAULT NULL COMMENT '区域码',
  `floor_no` varchar(255) NOT NULL COMMENT '所在层数',
  `name` varchar(255) NOT NULL COMMENT '房间名称',
  `handler_id` varchar(255) DEFAULT NULL,
  `pad_id` varchar(255) DEFAULT NULL COMMENT 'PADID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `building_id` bigint(20) DEFAULT NULL,
  `hotel_user_id` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `del_flag` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `area_code` (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_blob_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_blob_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_calendars` WRITE;
DELETE FROM `renren_security`.`qrtz_calendars`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_cron_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_cron_triggers`;
INSERT INTO `renren_security`.`qrtz_cron_triggers` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_fired_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_fired_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_job_details` WRITE;
DELETE FROM `renren_security`.`qrtz_job_details`;
INSERT INTO `renren_security`.`qrtz_job_details` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016993EAEA407874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_locks` WRITE;
DELETE FROM `renren_security`.`qrtz_locks`;
INSERT INTO `renren_security`.`qrtz_locks` (`SCHED_NAME`,`LOCK_NAME`) VALUES ('RenrenScheduler', 'STATE_ACCESS'),('RenrenScheduler', 'TRIGGER_ACCESS');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_paused_trigger_grps` WRITE;
DELETE FROM `renren_security`.`qrtz_paused_trigger_grps`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_scheduler_state` WRITE;
DELETE FROM `renren_security`.`qrtz_scheduler_state`;
INSERT INTO `renren_security`.`qrtz_scheduler_state` (`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) VALUES ('RenrenScheduler', 'DESKTOP-2BQSS8N1554369212376', 1554397970931, 15000);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_simple_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_simple_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_simprop_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_simprop_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`qrtz_triggers` WRITE;
DELETE FROM `renren_security`.`qrtz_triggers`;
INSERT INTO `renren_security`.`qrtz_triggers` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1554399000000, 1554397200000, 5, 'WAITING', 'CRON', 1552966189000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016993EAEA407874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`schedule_job` WRITE;
DELETE FROM `renren_security`.`schedule_job`;
INSERT INTO `renren_security`.`schedule_job` (`job_id`,`bean_name`,`params`,`cron_expression`,`status`,`remark`,`create_time`) VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 0, '参数测试', '2019-03-19 11:07:20');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`schedule_job_log` WRITE;
DELETE FROM `renren_security`.`schedule_job_log`;
INSERT INTO `renren_security`.`schedule_job_log` (`log_id`,`job_id`,`bean_name`,`params`,`status`,`error`,`times`,`create_time`) VALUES (1, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 12:00:00'),(2, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 12:30:00'),(3, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 13:00:00'),(4, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-19 13:30:00'),(5, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 14:00:00'),(6, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 14:30:00'),(7, 1, 'testTask', 'renren', 0, NULL, 2, '2019-03-19 15:00:00'),(8, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-19 15:30:00'),(9, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 16:00:00'),(10, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-19 16:30:00'),(11, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-19 17:00:00'),(12, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-19 17:30:00'),(13, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 10:30:00'),(14, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 11:00:00'),(15, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 11:30:00'),(16, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 12:00:00'),(17, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 12:30:00'),(18, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 13:00:00'),(19, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 13:30:00'),(20, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 14:00:00'),(21, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 14:30:00'),(22, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 15:00:00'),(23, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-28 15:30:00'),(24, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-28 16:00:00'),(25, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-28 16:30:00'),(26, 1, 'testTask', 'renren', 0, NULL, 1, '2019-03-28 17:00:00'),(27, 1, 'testTask', 'renren', 0, NULL, 0, '2019-03-28 17:30:00'),(28, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 09:30:00'),(29, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 10:00:00'),(30, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 10:30:00'),(31, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 11:00:00'),(32, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 11:30:00'),(33, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 12:00:00'),(34, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 12:30:00'),(35, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 13:00:00'),(36, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 13:30:00'),(37, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 14:00:00'),(38, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 14:30:00'),(39, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 15:00:00'),(40, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 15:30:00'),(41, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 16:00:00'),(42, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-01 16:30:00'),(43, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-01 17:00:00'),(44, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 09:30:00'),(45, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 10:00:00'),(46, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 10:30:00'),(47, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 11:00:00'),(48, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 11:30:00'),(49, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 12:00:00'),(50, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 12:30:00'),(51, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 13:00:00'),(52, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 13:30:00'),(53, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 14:00:00'),(54, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 14:30:00'),(55, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 15:00:00'),(56, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 15:30:00'),(57, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 16:00:00'),(58, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 16:30:00'),(59, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 17:00:00'),(60, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 17:30:00'),(61, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 18:00:00'),(62, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 18:30:00'),(63, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 19:00:00'),(64, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 19:30:00'),(65, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 20:00:00'),(66, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 20:30:00'),(67, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 21:00:00'),(68, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 21:30:00'),(69, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-02 22:00:00'),(70, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 22:30:00'),(71, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 23:00:00'),(72, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-02 23:30:00'),(73, 1, 'testTask', 'renren', 0, NULL, 15, '2019-04-03 00:00:00'),(74, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 00:30:00'),(75, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 01:00:00'),(76, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 01:30:00'),(77, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 02:00:00'),(78, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 02:30:00'),(79, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 03:00:00'),(80, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 03:30:00'),(81, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 04:00:00'),(82, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 04:30:00'),(83, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 05:00:00'),(84, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 05:30:00'),(85, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 06:00:00'),(86, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 06:30:00'),(87, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 07:00:00'),(88, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 07:30:00'),(89, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 08:00:00'),(90, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 08:30:00'),(91, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 09:00:00'),(92, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 09:30:00'),(93, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 10:00:00'),(94, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 10:30:00'),(95, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 11:00:00'),(96, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 11:30:00'),(97, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 12:00:00'),(98, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 12:30:00'),(99, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 13:00:00'),(100, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 13:30:00'),(101, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 14:00:00'),(102, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 14:30:00'),(103, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 15:00:00'),(104, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 16:00:00'),(105, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 16:30:00'),(106, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 17:00:00'),(107, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 17:30:00'),(108, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 18:00:00'),(109, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 18:30:00'),(110, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 19:00:00'),(111, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 19:30:00'),(112, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 20:00:00'),(113, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 20:30:00'),(114, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 21:00:00'),(115, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-03 21:30:00'),(116, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 22:00:00'),(117, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 22:30:00'),(118, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-03 23:00:00'),(119, 1, 'testTask', 'renren', 0, NULL, 2, '2019-04-03 23:30:00'),(120, 1, 'testTask', 'renren', 0, NULL, 7, '2019-04-04 00:00:00'),(121, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 00:30:00'),(122, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 01:00:00'),(123, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 01:30:00'),(124, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 02:00:00'),(125, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 02:30:00'),(126, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 03:00:00'),(127, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 03:30:00'),(128, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 04:00:00'),(129, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 04:30:00'),(130, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 05:00:00'),(131, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 05:30:00'),(132, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 06:00:00'),(133, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 06:30:00'),(134, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 07:00:00'),(135, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 07:30:00'),(136, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 08:00:00'),(137, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 08:30:00'),(138, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 09:00:00'),(139, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 09:30:00'),(140, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 10:00:00'),(141, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 10:30:00'),(142, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 11:00:00'),(143, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 11:30:00'),(144, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 12:00:00'),(145, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 12:30:00'),(146, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 13:00:00'),(147, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 13:30:00'),(148, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 14:00:00'),(149, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 14:30:00'),(150, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 16:00:00'),(151, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 16:30:00'),(152, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 17:00:00'),(153, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 17:30:00'),(154, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 18:00:00'),(155, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 18:30:00'),(156, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 19:00:00'),(157, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 19:30:00'),(158, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 20:00:00'),(159, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 20:30:00'),(160, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 21:00:00'),(161, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 21:30:00'),(162, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 22:00:00'),(163, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 22:30:00'),(164, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-04 23:00:00'),(165, 1, 'testTask', 'renren', 0, NULL, 0, '2019-04-04 23:30:00'),(166, 1, 'testTask', 'renren', 0, NULL, 7, '2019-04-05 00:00:00'),(167, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-05 00:30:00'),(168, 1, 'testTask', 'renren', 0, NULL, 1, '2019-04-05 01:00:00');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_config` WRITE;
DELETE FROM `renren_security`.`sys_config`;
INSERT INTO `renren_security`.`sys_config` (`id`,`param_key`,`param_value`,`status`,`remark`) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_dept` WRITE;
DELETE FROM `renren_security`.`sys_dept`;
INSERT INTO `renren_security`.`sys_dept` (`dept_id`,`parent_id`,`name`,`order_num`,`del_flag`) VALUES (1, 0, '人人开源集团', 0, 0),(2, 1, '长沙分公司', 1, 0),(3, 1, '上海分公司', 2, 0),(4, 3, '技术部', 0, 0),(5, 3, '销售部', 1, 0),(6, 1, '厦门分公司', 0, 0),(7, 2, '技术部', 0, 0),(8, 2, '销售部', 0, 0),(9, 0, NULL, 0, -1);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_dict` WRITE;
DELETE FROM `renren_security`.`sys_dict`;
INSERT INTO `renren_security`.`sys_dict` (`id`,`name`,`type`,`code`,`value`,`order_num`,`remark`,`del_flag`) VALUES (1, '性别', 'sex', '0', '女', 0, NULL, 0),(2, '性别', 'sex', '1', '男', 1, NULL, 0),(3, '性别', 'sex', '2', '未知', 3, NULL, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_log` WRITE;
DELETE FROM `renren_security`.`sys_log`;
INSERT INTO `renren_security`.`sys_log` (`id`,`username`,`operation`,`method`,`params`,`time`,`ip`,`create_date`) VALUES (1, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[],\"deptIdList\":[3],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 137, '0:0:0:0:0:0:0:1', '2019-03-19 11:35:04'),(2, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":2,\"username\":\"shanghai\",\"password\":\"1acff71c8d2a4efe13a2940747a4823be34ad7e204db148da5e917158dd13491\",\"salt\":\"zFNyYXJYEXz9q3r4a2J9\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[1],\"createTime\":\"Mar 19, 2019 11:35:38 AM\",\"deptId\":3,\"deptName\":\"上海分公司\"}', 147, '0:0:0:0:0:0:0:1', '2019-03-19 11:35:39'),(3, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"设备管理\",\"type\":0,\"icon\":\"fa-microchip\",\"orderNum\":0}', 54, '0:0:0:0:0:0:0:1', '2019-04-01 09:24:00'),(4, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"设备管理\",\"type\":0,\"icon\":\"fa fa-microchip\",\"orderNum\":0}', 67, '0:0:0:0:0:0:0:1', '2019-04-01 09:30:00'),(5, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[41],\"deptIdList\":[3],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 154, '0:0:0:0:0:0:0:1', '2019-04-01 09:32:19'),(6, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"信息管理\",\"type\":0,\"icon\":\"fa fa-microchip\",\"orderNum\":0}', 60, '0:0:0:0:0:0:0:1', '2019-04-01 09:36:52'),(7, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":41,\"parentId\":0,\"parentName\":\"一级菜单\",\"name\":\"信息管理\",\"type\":0,\"icon\":\"fa fa-file-text\",\"orderNum\":0}', 68, '0:0:0:0:0:0:0:1', '2019-04-01 09:38:02'),(8, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"楼栋管理\",\"url\":\"modules/job/building.html\",\"type\":1,\"icon\":\"fa fa-building\",\"orderNum\":0}', 63, '0:0:0:0:0:0:0:1', '2019-04-01 11:27:47'),(9, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"楼栋管理\",\"url\":\"modules/msg/building.html\",\"type\":1,\"icon\":\"fa fa-building\",\"orderNum\":0}', 160, '0:0:0:0:0:0:0:1', '2019-04-01 11:29:12'),(10, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":43,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"增加楼栋\",\"perms\":\"msg:building:save\",\"type\":2,\"orderNum\":0}', 159, '0:0:0:0:0:0:0:1', '2019-04-01 11:42:57'),(11, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":44,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"修改楼栋\",\"perms\":\"msg:building:update\",\"type\":2,\"orderNum\":0}', 45, '0:0:0:0:0:0:0:1', '2019-04-01 11:43:40'),(12, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":45,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"删除楼栋\",\"perms\":\"msg:building:delete\",\"type\":2,\"orderNum\":0}', 111, '0:0:0:0:0:0:0:1', '2019-04-01 11:44:28'),(13, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":46,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"查看楼栋\",\"perms\":\"msg:building:list\",\"type\":2,\"orderNum\":0}', 108, '127.0.0.1', '2019-04-01 14:53:49'),(14, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":47,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"楼栋查看\",\"perms\":\"msg:building:info\",\"type\":2,\"orderNum\":0}', 79, '127.0.0.1', '2019-04-01 17:31:58'),(15, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":46,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"楼栋列表\",\"perms\":\"msg:building:list\",\"type\":2,\"orderNum\":0}', 70, '127.0.0.1', '2019-04-01 17:32:21'),(16, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":48,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"房间管理\",\"url\":\"modules/msg/building.html\",\"type\":1,\"icon\":\"fa fa-th\",\"orderNum\":0}', 52, '127.0.0.1', '2019-04-01 17:44:22'),(17, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":48,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"房间管理\",\"url\":\"modules/msg/room.html\",\"type\":1,\"icon\":\"fa fa-th\",\"orderNum\":0}', 75, '127.0.0.1', '2019-04-01 17:44:59'),(18, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":2,\"roleName\":\"厦门管理人员\",\"deptId\":6,\"deptName\":\"厦门分公司\",\"menuIdList\":[],\"deptIdList\":[6],\"createTime\":\"Apr 2, 2019 9:54:04 AM\"}', 116, '127.0.0.1', '2019-04-02 09:54:04'),(19, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":49,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"房间列表\",\"perms\":\"msg:room:list\",\"type\":2,\"orderNum\":0}', 59, '0:0:0:0:0:0:0:1', '2019-04-02 14:59:53'),(20, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":50,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"新增房间\",\"perms\":\"msg:room:save\",\"type\":2,\"orderNum\":0}', 122, '0:0:0:0:0:0:0:1', '2019-04-02 15:01:22'),(21, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":51,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"修改房间\",\"perms\":\"msg:room:update\",\"type\":2,\"orderNum\":0}', 52, '0:0:0:0:0:0:0:1', '2019-04-02 15:01:52'),(22, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":52,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"删除房间\",\"perms\":\"msg:room:delete\",\"type\":2,\"orderNum\":0}', 47, '0:0:0:0:0:0:0:1', '2019-04-02 15:02:29'),(23, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[1,3,19,20,21,22,41],\"deptIdList\":[3,4,5],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 187, '0:0:0:0:0:0:0:1', '2019-04-02 15:53:31'),(24, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[1,3,19,20,21,22,41],\"deptIdList\":[3,4,5],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 97, '0:0:0:0:0:0:0:1', '2019-04-02 15:55:46'),(25, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[1,3,19,20,21,22,4,23,24,25,26,41],\"deptIdList\":[3,4,5],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 385, '0:0:0:0:0:0:0:1', '2019-04-02 15:59:38'),(26, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[1,3,19,20,21,22,4,23,24,25,26,31,32,33,34,35,41],\"deptIdList\":[3,4,5],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 88, '0:0:0:0:0:0:0:1', '2019-04-02 16:01:18'),(27, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '{\"roleId\":1,\"roleName\":\"上海管理人员\",\"remark\":\"无\",\"deptId\":3,\"deptName\":\"上海分公司\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,31,32,33,34,35,41],\"deptIdList\":[3,4,5],\"createTime\":\"Mar 19, 2019 11:35:04 AM\"}', 342, '0:0:0:0:0:0:0:1', '2019-04-02 16:03:28'),(28, 'shanghai', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":3,\"roleName\":\"技术部人员\",\"remark\":\"技术人员是否可以重新分配其他权限\",\"deptId\":4,\"deptName\":\"技术部\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52],\"deptIdList\":[4],\"createTime\":\"Apr 2, 2019 4:06:30 PM\"}', 140, '0:0:0:0:0:0:0:1', '2019-04-02 16:06:31'),(29, 'shanghai', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":3,\"username\":\"shanghai_jishu\",\"password\":\"77730a95d771a22993f14b69234e8e16eea97928120131214c3788e65c20a900\",\"salt\":\"P4CszMWgibh2AaUqwUiq\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[3],\"createTime\":\"Apr 2, 2019 4:07:20 PM\",\"deptId\":4,\"deptName\":\"技术部\"}', 155, '0:0:0:0:0:0:0:1', '2019-04-02 16:07:20'),(30, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":4,\"roleName\":\"长沙部门管理人员\",\"remark\":\"长沙分公司管理人员\",\"deptId\":2,\"deptName\":\"长沙分公司\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,29,30,31,32,33,34,35,36,37,38,39,40],\"deptIdList\":[2,7,8],\"createTime\":\"Apr 2, 2019 4:16:50 PM\"}', 110, '0:0:0:0:0:0:0:1', '2019-04-02 16:16:51'),(31, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":4,\"username\":\"changsha\",\"password\":\"5118d9c4c85dd9a4e69a8d31c26d00f42d60beb507811a475c56f33bdd4cd8ed\",\"salt\":\"p1mTKmNFABkfv9xmkau4\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[4],\"createTime\":\"Apr 2, 2019 4:18:00 PM\",\"deptId\":2,\"deptName\":\"长沙分公司\"}', 82, '0:0:0:0:0:0:0:1', '2019-04-02 16:18:00'),(32, 'changsha', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":5,\"roleName\":\"长沙分公司技术部管理人员\",\"remark\":\"无\",\"deptId\":7,\"deptName\":\"技术部\",\"menuIdList\":[1,2,15,16,17,18,3,19,20,21,22,4,23,24,25,26,5,6,7,8,9,10,11,12,13,14,27,29,30,31,32,33,34,35,36,37,38,39,40],\"deptIdList\":[7],\"createTime\":\"Apr 2, 2019 4:19:31 PM\"}', 126, '0:0:0:0:0:0:0:1', '2019-04-02 16:19:32'),(33, 'changsha', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":5,\"username\":\"changsha_jishu\",\"password\":\"58da034f412538e42bbd1d549a3877b32dd2c64df34fbdaa53b36faea76374e5\",\"salt\":\"S5V8FWKC1AEcsv5EVwhE\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[5],\"createTime\":\"Apr 2, 2019 4:21:25 PM\",\"deptId\":7,\"deptName\":\"技术部\"}', 61, '0:0:0:0:0:0:0:1', '2019-04-02 16:21:26'),(34, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":53,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"楼层管理\",\"url\":\"modules/msg/blayer.html\",\"type\":1,\"icon\":\"fa fa-server\",\"orderNum\":0}', 113, '0:0:0:0:0:0:0:1', '2019-04-03 09:37:56'),(35, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":54,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"楼层列表\",\"perms\":\"msg:blayer:list\",\"type\":2,\"orderNum\":0}', 56, '0:0:0:0:0:0:0:1', '2019-04-03 09:39:14'),(36, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":55,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"新增楼层\",\"perms\":\"msg:blayer:save\",\"type\":2,\"orderNum\":0}', 63, '0:0:0:0:0:0:0:1', '2019-04-03 09:39:46'),(37, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":56,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"修改楼层\",\"perms\":\"msg:blayer:update\",\"type\":2,\"orderNum\":0}', 59, '0:0:0:0:0:0:0:1', '2019-04-03 09:40:19'),(38, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '56', 56, '0:0:0:0:0:0:0:1', '2019-04-03 09:40:50'),(39, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":57,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"修改楼层\",\"perms\":\"msg:blayer:update\",\"type\":2,\"orderNum\":0}', 60, '0:0:0:0:0:0:0:1', '2019-04-03 09:41:30'),(40, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":58,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"删除楼层\",\"perms\":\"msg:blayer:delete\",\"type\":2,\"orderNum\":0}', 65, '0:0:0:0:0:0:0:1', '2019-04-03 09:41:59'),(41, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":42,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"楼栋管理\",\"url\":\"modules/msg/building.html\",\"type\":1,\"icon\":\"fa fa-building\",\"orderNum\":1}', 61, '0:0:0:0:0:0:0:1', '2019-04-03 09:50:20'),(42, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":48,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"房间管理\",\"url\":\"modules/msg/room.html\",\"type\":1,\"icon\":\"fa fa-th\",\"orderNum\":2}', 60, '0:0:0:0:0:0:0:1', '2019-04-03 09:50:33'),(43, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":48,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"房间管理\",\"url\":\"modules/msg/room.html\",\"type\":1,\"icon\":\"fa fa-th\",\"orderNum\":3}', 63, '0:0:0:0:0:0:0:1', '2019-04-03 09:50:43'),(44, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":53,\"parentId\":41,\"parentName\":\"信息管理\",\"name\":\"楼层管理\",\"url\":\"modules/msg/blayer.html\",\"type\":1,\"icon\":\"fa fa-server\",\"orderNum\":2}', 242, '0:0:0:0:0:0:0:1', '2019-04-03 09:50:56'),(45, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":59,\"parentId\":42,\"parentName\":\"楼栋管理\",\"name\":\"层级查看\",\"perms\":\"sys:reverse:list\",\"type\":2,\"orderNum\":0}', 107, '127.0.0.1', '2019-04-03 15:48:13'),(46, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":60,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"层级查看\",\"perms\":\"sys:reverse:list\",\"type\":2,\"orderNum\":0}', 57, '127.0.0.1', '2019-04-03 15:48:34'),(47, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":61,\"parentId\":48,\"parentName\":\"房间管理\",\"name\":\"层级查看\",\"perms\":\"sys:reverse:list\",\"type\":2,\"orderNum\":0}', 44, '127.0.0.1', '2019-04-03 15:48:48'),(48, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":62,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"楼层列表\",\"perms\":\"sys:building:list\",\"type\":2,\"orderNum\":0}', 70, '127.0.0.1', '2019-04-03 17:45:13'),(49, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '{\"menuId\":62,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"楼栋列表\",\"perms\":\"sys:building:list\",\"type\":2,\"orderNum\":0}', 58, '127.0.0.1', '2019-04-04 09:26:19'),(50, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '{\"menuId\":63,\"parentId\":53,\"parentName\":\"楼层管理\",\"name\":\"楼层列表\",\"perms\":\"msg:blayer:info\",\"type\":2,\"orderNum\":0}', 69, '127.0.0.1', '2019-04-04 10:17:14'),(51, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":6,\"roleName\":\"福州大学管理人员\",\"remark\":\"测试角色，测试楼栋和楼层的数据隔离\",\"deptId\":6,\"deptName\":\"厦门分公司\",\"menuIdList\":[41,42,43,44,45,46,47,59,53,54,55,57,58,60,62,63],\"deptIdList\":[6],\"createTime\":\"Apr 4, 2019 11:28:54 AM\"}', 3901, '127.0.0.1', '2019-04-04 11:28:55'),(52, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":6,\"username\":\"fuda_001\",\"password\":\"98652affc97db4bb9764b42c26a6cd8748cebf5b2c2ef0bcfacba0528105994e\",\"salt\":\"FvIpgFuxZQLXODfxVwc2\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[6],\"createTime\":\"Apr 4, 2019 11:29:32 AM\",\"deptId\":6,\"deptName\":\"厦门分公司\"}', 112, '127.0.0.1', '2019-04-04 11:29:33'),(53, 'admin', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[6]', 123, '127.0.0.1', '2019-04-04 12:07:44'),(54, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":22,\"roleName\":\"福大管理人员\",\"remark\":\"测试楼栋数据隔离\",\"deptId\":6,\"deptName\":\"厦门分公司\",\"menuIdList\":[41,42,43,44,45,46,47,59,53,54,55,57,58,60,62,63],\"deptIdList\":[],\"msgReverseList\":[{\"layerType\":\"BUILDING\",\"currentId\":1,\"parentId\":-1},{\"layerType\":\"BUILDING\",\"currentId\":2,\"parentId\":-1}],\"createTime\":\"Apr 4, 2019 3:52:04 PM\"}', 30083, '0:0:0:0:0:0:0:1', '2019-04-04 15:52:35'),(55, 'admin', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[22]', 118, '0:0:0:0:0:0:0:1', '2019-04-04 15:54:15'),(56, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":23,\"roleName\":\"福大管理人员\",\"remark\":\"测试数据隔离\",\"deptId\":6,\"deptName\":\"厦门分公司\",\"menuIdList\":[41,42,43,44,45,46,47,59,53,54,55,57,58,60,62,63],\"deptIdList\":[],\"msgReverseList\":[{\"layerType\":\"BUILDING\",\"currentId\":1,\"parentId\":-1},{\"layerType\":\"BUILDING\",\"currentId\":2,\"parentId\":-1},{\"layerType\":\"LAYER\",\"currentId\":5,\"parentId\":2}],\"createTime\":\"Apr 4, 2019 3:55:12 PM\"}', 40985, '0:0:0:0:0:0:0:1', '2019-04-04 15:55:53'),(57, 'admin', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[23]', 122, '0:0:0:0:0:0:0:1', '2019-04-04 16:00:47'),(58, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '{\"roleId\":24,\"roleName\":\"福大管理人员\",\"remark\":\"测试数据过滤\",\"deptId\":6,\"deptName\":\"厦门分公司\",\"menuIdList\":[41,42,43,44,45,46,47,59,53,54,55,57,58,60,62,63],\"deptIdList\":[],\"msgReverseList\":[{\"layerType\":\"BUILDING\",\"currentId\":2,\"parentId\":-1},{\"layerType\":\"LAYER\",\"currentId\":5,\"parentId\":2},{\"layerType\":\"BUILDING\",\"currentId\":3,\"parentId\":-1}],\"createTime\":\"Apr 4, 2019 4:01:37 PM\"}', 26130, '0:0:0:0:0:0:0:1', '2019-04-04 16:02:04'),(59, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '{\"userId\":8,\"username\":\"fuda_001\",\"password\":\"7ef8090d475a92bc12c5eabebc1de38594b5c8b7d56f553c6439ed13e3fae9e8\",\"salt\":\"hEFqYXEUQzk2v08uZ7dg\",\"email\":\"534132996@qq.com\",\"mobile\":\"18659122602\",\"status\":1,\"roleIdList\":[24],\"createTime\":\"Apr 4, 2019 4:05:27 PM\",\"deptId\":6,\"deptName\":\"厦门分公司\"}', 96, '0:0:0:0:0:0:0:1', '2019-04-04 16:05:28');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_menu` WRITE;
DELETE FROM `renren_security`.`sys_menu`;
INSERT INTO `renren_security`.`sys_menu` (`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) VALUES (1, 0, '系统管理', NULL, NULL, 0, 'fa fa-cog', 0),(2, 1, '管理员管理', 'modules/sys/user.html', NULL, 1, 'fa fa-user', 1),(3, 1, '角色管理', 'modules/sys/role.html', NULL, 1, 'fa fa-user-secret', 2),(4, 1, '菜单管理', 'modules/sys/menu.html', NULL, 1, 'fa fa-th-list', 3),(5, 1, 'SQL监控', 'druid/sql.html', NULL, 1, 'fa fa-bug', 4),(6, 1, '定时任务', 'modules/job/schedule.html', NULL, 1, 'fa fa-tasks', 5),(7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0),(8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0),(9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0),(10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0),(11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0),(12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0),(13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0),(14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0),(15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0),(16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0),(17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0),(18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0),(19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0),(20, 3, '新增', NULL, 'sys:role:save,sys:menu:perms', 2, NULL, 0),(21, 3, '修改', NULL, 'sys:role:update,sys:menu:perms', 2, NULL, 0),(22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0),(23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0),(24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0),(25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0),(26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0),(27, 1, '参数管理', 'modules/sys/config.html', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'fa fa-sun-o', 6),(29, 1, '系统日志', 'modules/sys/log.html', 'sys:log:list', 1, 'fa fa-file-text-o', 7),(30, 1, '文件上传', 'modules/oss/oss.html', 'sys:oss:all', 1, 'fa fa-file-image-o', 6),(31, 1, '部门管理', 'modules/sys/dept.html', NULL, 1, 'fa fa-file-code-o', 1),(32, 31, '查看', NULL, 'sys:dept:list,sys:dept:info', 2, NULL, 0),(33, 31, '新增', NULL, 'sys:dept:save,sys:dept:select', 2, NULL, 0),(34, 31, '修改', NULL, 'sys:dept:update,sys:dept:select', 2, NULL, 0),(35, 31, '删除', NULL, 'sys:dept:delete', 2, NULL, 0),(36, 1, '字典管理', 'modules/sys/dict.html', NULL, 1, 'fa fa-bookmark-o', 6),(37, 36, '查看', NULL, 'sys:dict:list,sys:dict:info', 2, NULL, 6),(38, 36, '新增', NULL, 'sys:dict:save', 2, NULL, 6),(39, 36, '修改', NULL, 'sys:dict:update', 2, NULL, 6),(40, 36, '删除', NULL, 'sys:dict:delete', 2, NULL, 6),(41, 0, '信息管理', NULL, NULL, 0, 'fa fa-file-text', 0),(42, 41, '楼栋管理', 'modules/msg/building.html', NULL, 1, 'fa fa-building', 1),(43, 42, '增加楼栋', NULL, 'msg:building:save', 2, NULL, 0),(44, 42, '修改楼栋', NULL, 'msg:building:update', 2, NULL, 0),(45, 42, '删除楼栋', NULL, 'msg:building:delete', 2, NULL, 0),(46, 42, '楼栋列表', NULL, 'msg:building:list', 2, NULL, 0),(47, 42, '楼栋查看', NULL, 'msg:building:info', 2, NULL, 0),(48, 41, '房间管理', 'modules/msg/room.html', NULL, 1, 'fa fa-th', 3),(49, 48, '房间列表', NULL, 'msg:room:list', 2, NULL, 0),(50, 48, '新增房间', NULL, 'msg:room:save', 2, NULL, 0),(51, 48, '修改房间', NULL, 'msg:room:update', 2, NULL, 0),(52, 48, '删除房间', NULL, 'msg:room:delete', 2, NULL, 0),(53, 41, '楼层管理', 'modules/msg/blayer.html', NULL, 1, 'fa fa-server', 2),(54, 53, '楼层列表', NULL, 'msg:blayer:list', 2, NULL, 0),(55, 53, '新增楼层', NULL, 'msg:blayer:save', 2, NULL, 0),(57, 53, '修改楼层', NULL, 'msg:blayer:update', 2, NULL, 0),(58, 53, '删除楼层', NULL, 'msg:blayer:delete', 2, NULL, 0),(59, 42, '层级查看', NULL, 'sys:reverse:list', 2, NULL, 0),(60, 53, '层级查看', NULL, 'sys:reverse:list', 2, NULL, 0),(61, 48, '层级查看', NULL, 'sys:reverse:list', 2, NULL, 0),(62, 53, '楼栋列表', NULL, 'sys:building:list', 2, NULL, 0),(63, 53, '楼层列表', NULL, 'msg:blayer:info', 2, NULL, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_oss` WRITE;
DELETE FROM `renren_security`.`sys_oss`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_role` WRITE;
DELETE FROM `renren_security`.`sys_role`;
INSERT INTO `renren_security`.`sys_role` (`role_id`,`role_name`,`remark`,`dept_id`,`create_time`) VALUES (1, '上海管理人员', '无', 3, '2019-03-19 11:35:04'),(2, '厦门管理人员', NULL, 6, '2019-04-02 09:54:04'),(3, '技术部人员', '技术人员是否可以重新分配其他权限', 4, '2019-04-02 16:06:31'),(4, '长沙部门管理人员', '长沙分公司管理人员', 2, '2019-04-02 16:16:50'),(5, '长沙分公司技术部管理人员', '无', 7, '2019-04-02 16:19:32'),(24, '福大管理人员', '测试数据过滤', 6, '2019-04-04 16:01:38');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_role_dept` WRITE;
DELETE FROM `renren_security`.`sys_role_dept`;
INSERT INTO `renren_security`.`sys_role_dept` (`id`,`role_id`,`dept_id`) VALUES (3, 2, 6),(16, 1, 3),(17, 1, 4),(18, 1, 5),(19, 3, 4),(20, 4, 2),(21, 4, 7),(22, 4, 8),(23, 5, 7);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_role_menu` WRITE;
DELETE FROM `renren_security`.`sys_role_menu`;
INSERT INTO `renren_security`.`sys_role_menu` (`id`,`role_id`,`menu_id`) VALUES (45, 1, 1),(46, 1, 2),(47, 1, 15),(48, 1, 16),(49, 1, 17),(50, 1, 18),(51, 1, 3),(52, 1, 19),(53, 1, 20),(54, 1, 21),(55, 1, 22),(56, 1, 4),(57, 1, 23),(58, 1, 24),(59, 1, 25),(60, 1, 26),(61, 1, 31),(62, 1, 32),(63, 1, 33),(64, 1, 34),(65, 1, 35),(66, 1, 41),(67, 3, 1),(68, 3, 2),(69, 3, 15),(70, 3, 16),(71, 3, 17),(72, 3, 18),(73, 3, 3),(74, 3, 19),(75, 3, 20),(76, 3, 21),(77, 3, 22),(78, 3, 4),(79, 3, 23),(80, 3, 24),(81, 3, 25),(82, 3, 26),(83, 3, 5),(84, 3, 6),(85, 3, 7),(86, 3, 8),(87, 3, 9),(88, 3, 10),(89, 3, 11),(90, 3, 12),(91, 3, 13),(92, 3, 14),(93, 3, 27),(94, 3, 29),(95, 3, 30),(96, 3, 31),(97, 3, 32),(98, 3, 33),(99, 3, 34),(100, 3, 35),(101, 3, 36),(102, 3, 37),(103, 3, 38),(104, 3, 39),(105, 3, 40),(106, 3, 41),(107, 3, 42),(108, 3, 43),(109, 3, 44),(110, 3, 45),(111, 3, 46),(112, 3, 47),(113, 3, 48),(114, 3, 49),(115, 3, 50),(116, 3, 51),(117, 3, 52),(118, 4, 1),(119, 4, 2),(120, 4, 15),(121, 4, 16),(122, 4, 17),(123, 4, 18),(124, 4, 3),(125, 4, 19),(126, 4, 20),(127, 4, 21),(128, 4, 22),(129, 4, 4),(130, 4, 23),(131, 4, 24),(132, 4, 25),(133, 4, 26),(134, 4, 5),(135, 4, 6),(136, 4, 7),(137, 4, 8),(138, 4, 9),(139, 4, 10),(140, 4, 11),(141, 4, 12),(142, 4, 13),(143, 4, 14),(144, 4, 27),(145, 4, 29),(146, 4, 30),(147, 4, 31),(148, 4, 32),(149, 4, 33),(150, 4, 34),(151, 4, 35),(152, 4, 36),(153, 4, 37),(154, 4, 38),(155, 4, 39),(156, 4, 40),(157, 5, 1),(158, 5, 2),(159, 5, 15),(160, 5, 16),(161, 5, 17),(162, 5, 18),(163, 5, 3),(164, 5, 19),(165, 5, 20),(166, 5, 21),(167, 5, 22),(168, 5, 4),(169, 5, 23),(170, 5, 24),(171, 5, 25),(172, 5, 26),(173, 5, 5),(174, 5, 6),(175, 5, 7),(176, 5, 8),(177, 5, 9),(178, 5, 10),(179, 5, 11),(180, 5, 12),(181, 5, 13),(182, 5, 14),(183, 5, 27),(184, 5, 29),(185, 5, 30),(186, 5, 31),(187, 5, 32),(188, 5, 33),(189, 5, 34),(190, 5, 35),(191, 5, 36),(192, 5, 37),(193, 5, 38),(194, 5, 39),(195, 5, 40),(372, 24, 41),(373, 24, 42),(374, 24, 43),(375, 24, 44),(376, 24, 45),(377, 24, 46),(378, 24, 47),(379, 24, 59),(380, 24, 53),(381, 24, 54),(382, 24, 55),(383, 24, 57),(384, 24, 58),(385, 24, 60),(386, 24, 62),(387, 24, 63);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_user` WRITE;
DELETE FROM `renren_security`.`sys_user`;
INSERT INTO `renren_security`.`sys_user` (`user_id`,`username`,`password`,`salt`,`email`,`mobile`,`status`,`dept_id`,`create_time`) VALUES (1, 'admin', 'e1153123d7d180ceeb820d577ff119876678732a68eef4e6ffc0b1f06a01f91b', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11'),(2, 'shanghai', '1acff71c8d2a4efe13a2940747a4823be34ad7e204db148da5e917158dd13491', 'zFNyYXJYEXz9q3r4a2J9', '534132996@qq.com', '18659122602', 1, 3, '2019-03-19 11:35:39'),(3, 'shanghai_jishu', '77730a95d771a22993f14b69234e8e16eea97928120131214c3788e65c20a900', 'P4CszMWgibh2AaUqwUiq', '534132996@qq.com', '18659122602', 1, 4, '2019-04-02 16:07:20'),(4, 'changsha', '5118d9c4c85dd9a4e69a8d31c26d00f42d60beb507811a475c56f33bdd4cd8ed', 'p1mTKmNFABkfv9xmkau4', '534132996@qq.com', '18659122602', 1, 2, '2019-04-02 16:18:00'),(5, 'changsha_jishu', '58da034f412538e42bbd1d549a3877b32dd2c64df34fbdaa53b36faea76374e5', 'S5V8FWKC1AEcsv5EVwhE', '534132996@qq.com', '18659122602', 1, 7, '2019-04-02 16:21:26'),(8, 'fuda_001', '7ef8090d475a92bc12c5eabebc1de38594b5c8b7d56f553c6439ed13e3fae9e8', 'hEFqYXEUQzk2v08uZ7dg', '534132996@qq.com', '18659122602', 1, 6, '2019-04-04 16:05:28');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`sys_user_role` WRITE;
DELETE FROM `renren_security`.`sys_user_role`;
INSERT INTO `renren_security`.`sys_user_role` (`id`,`user_id`,`role_id`) VALUES (1, 2, 1),(2, 3, 3),(3, 4, 4),(4, 5, 5),(6, 8, 24);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`tb_blayer` WRITE;
DELETE FROM `renren_security`.`tb_blayer`;
INSERT INTO `renren_security`.`tb_blayer` (`layer_id`,`layer_name`,`building_id`,`create_time`,`update_time`,`del_flag`) VALUES (5, '第八层', 2, '2019-04-03 21:13:22', '2019-04-03 21:13:22', 0),(7, '第一层', 3, '2019-04-04 10:54:33', '2019-04-04 10:54:33', 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`tb_building` WRITE;
DELETE FROM `renren_security`.`tb_building`;
INSERT INTO `renren_security`.`tb_building` (`building_id`,`address`,`coding`,`create_time`,`floor_count`,`name`,`opt_id`,`remark`,`del_flag`) VALUES (1, '福建省福州市闽侯县上街镇建平路48号建平村3号楼107单元', '0001', NULL, 10, '福州第一中学教学楼1#', NULL, '无', 0),(2, '福建省福州市闽侯县溪源宫路', '0001', NULL, 6, '福大中楼#3', NULL, '无', 0),(3, '福建省福州市仓山区上下店路15号', '0001', '2019-04-01 17:31:05', 12, '明德楼', NULL, '无', 0),(4, '闽江四季城', '0002', '2019-04-02 10:00:24', 20, '夏季楼栋2#', NULL, '无', 0),(5, '乌龙江中大道与学府南路交叉口西北角', '00002', '2019-04-04 09:40:39', 0, '福建师范大学旗山校区-美术学院', NULL, '无', 0),(6, '福建师大旗山校区', '0002', '2019-04-04 10:57:11', 6, '福建师大旗山校区-青春剧场', NULL, '无', -1);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`tb_role_building` WRITE;
DELETE FROM `renren_security`.`tb_role_building`;
INSERT INTO `renren_security`.`tb_role_building` (`id`,`role_id`,`building_id`,`del_flag`) VALUES (1, 22, 1, 0),(2, 22, 2, 0),(3, 23, 1, 0),(4, 23, 2, 0),(5, 24, 2, 0),(6, 24, 3, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_security`.`tb_room` WRITE;
DELETE FROM `renren_security`.`tb_room`;
UNLOCK TABLES;
COMMIT;
