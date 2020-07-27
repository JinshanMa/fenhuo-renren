/*
MySQL Backup
Database: renren_fast
Backup Time: 2020-07-17 16:33:30
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_fault`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_faultdefend`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_operationlog`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_partyalist`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_projectinfo`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_projectservicelist`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_pushlog`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_user_sys_role`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_users`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_usertoken`;
DROP TABLE IF EXISTS `renren_fast`.`fenhuo_zabbixhost`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_blob_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_calendars`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_cron_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_fired_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_job_details`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_locks`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_scheduler_state`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_simple_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`qrtz_triggers`;
DROP TABLE IF EXISTS `renren_fast`.`schedule_job`;
DROP TABLE IF EXISTS `renren_fast`.`schedule_job_log`;
DROP TABLE IF EXISTS `renren_fast`.`sys_captcha`;
DROP TABLE IF EXISTS `renren_fast`.`sys_config`;
DROP TABLE IF EXISTS `renren_fast`.`sys_log`;
DROP TABLE IF EXISTS `renren_fast`.`sys_menu`;
DROP TABLE IF EXISTS `renren_fast`.`sys_oss`;
DROP TABLE IF EXISTS `renren_fast`.`sys_role`;
DROP TABLE IF EXISTS `renren_fast`.`sys_role_menu`;
DROP TABLE IF EXISTS `renren_fast`.`sys_user`;
DROP TABLE IF EXISTS `renren_fast`.`sys_user_role`;
DROP TABLE IF EXISTS `renren_fast`.`sys_user_token`;
DROP TABLE IF EXISTS `renren_fast`.`tb_user`;
CREATE TABLE `fenhuo_fault` (
  `faultid` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '故障ID',
  `projectid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目ID',
  `projectname` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目名称',
  `ip` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目IP',
  `equipment` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '设备名',
  `faulttype` int(11) DEFAULT NULL COMMENT '故障类型ID',
  `faulttypename` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '故障类型名称',
  `faultdesc` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '故障问题描述',
  `starttime` datetime NOT NULL COMMENT '故障开始时间',
  `networkstatus` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '当前网络状态',
  `declartype` varchar(255) DEFAULT NULL,
  `faultstatus` int(11) DEFAULT NULL COMMENT '故障状态(1 创建成功，2 已取消，3 已解决)',
  `faultstatustxt` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `declarer` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '申报人ID',
  `declarername` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目申报人真实名字',
  `plan` varchar(300) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护计划',
  `handlerid` varchar(32) DEFAULT NULL COMMENT '项目负责人',
  `handlername` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目负责人名称',
  `remark` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `isdelete` int(1) unsigned zerofill DEFAULT NULL COMMENT '0为未来删除，1为删除',
  PRIMARY KEY (`faultid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='故障申报表';
CREATE TABLE `fenhuo_faultdefend` (
  `defendid` int(11) NOT NULL AUTO_INCREMENT COMMENT '维护单ID',
  `faultid` varchar(32) DEFAULT NULL COMMENT '故障ID',
  `defenderid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护人员ID',
  `defendername` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护人员姓名',
  `defenderposition` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护人员位置',
  `locationtime` timestamp NULL DEFAULT NULL COMMENT '定位时间',
  `plan` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护计划',
  `defendresult` int(11) DEFAULT NULL COMMENT '维护结果(1 成功，2 失败)',
  `resultdesc` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '维护结果描述',
  `createrid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `creatername` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人姓名',
  `createtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `defendstarttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '维护开始时间',
  `projectid` bigint(20) DEFAULT NULL COMMENT '项目id',
  `defendendtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '维护结束时间',
  `projectname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '项目名称',
  `isdelete` int(4) DEFAULT NULL COMMENT '是否删除标志',
  `faultdesc` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`defendid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='故障维护单';
CREATE TABLE `fenhuo_operationlog` (
  `logid` bigint(11) NOT NULL AUTO_INCREMENT,
  `opdatetime` datetime NOT NULL,
  `opersonid` bigint(4) DEFAULT NULL COMMENT '操作人员id',
  `opersoname` varchar(255) DEFAULT NULL COMMENT '操作人真实姓名',
  `opname` varchar(255) DEFAULT NULL COMMENT '操作名称',
  `projectid` bigint(4) DEFAULT NULL COMMENT '操作的项目id',
  `projectname` varchar(255) DEFAULT NULL COMMENT '操作项目名称',
  `isdelete` int(1) DEFAULT '0' COMMENT '是否删除，1删除，0未删除',
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
CREATE TABLE `fenhuo_partyalist` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `projectid` varchar(32) DEFAULT NULL COMMENT '项目id',
  `partyaid` varchar(32) DEFAULT NULL COMMENT '甲方负责人id',
  `partyaname` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '甲方姓名',
  `partyamobile` varchar(20) DEFAULT NULL COMMENT '甲方联系方式',
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='项目甲方负责人表';
CREATE TABLE `fenhuo_projectinfo` (
  `projectid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `projectname` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `orgname` varchar(100) DEFAULT NULL COMMENT '单位名称',
  `province` varchar(100) DEFAULT NULL COMMENT '单位省',
  `city` varchar(100) DEFAULT NULL COMMENT '单位市',
  `county` varchar(100) DEFAULT NULL COMMENT '区',
  `address` varchar(300) DEFAULT NULL COMMENT '门牌地址',
  `projectcreatetime` datetime DEFAULT NULL COMMENT '项目创建时间',
  `effectivetime` varchar(20) DEFAULT NULL,
  `serviceid` int(11) DEFAULT NULL COMMENT '服务内容id',
  `serviceditemetail` varchar(300) DEFAULT NULL COMMENT '服务内容描述',
  `taskid` int(11) DEFAULT NULL COMMENT '巡检项目id',
  `taskname` varchar(100) DEFAULT NULL COMMENT '巡检名称',
  `servicestarttime` datetime DEFAULT NULL COMMENT '服务开始时间',
  `serviceendtime` datetime DEFAULT NULL COMMENT '服务结束时间',
  `headid` varchar(32) DEFAULT NULL COMMENT '项目负责人id',
  `headname` varchar(20) DEFAULT NULL COMMENT '项目负责人',
  `headmobile` varchar(20) DEFAULT NULL COMMENT '项目负责人联系方式',
  `partyaid` varchar(32) DEFAULT NULL,
  `partyaname` varchar(20) DEFAULT NULL,
  `servicemid` varchar(32) DEFAULT NULL COMMENT '维护人员id',
  `servicemname` varchar(20) DEFAULT NULL COMMENT '维护人员姓名',
  `creater` varchar(32) DEFAULT NULL,
  `auditstatus` tinyint(4) DEFAULT NULL COMMENT '审批状态',
  `auditname` varchar(32) DEFAULT NULL COMMENT '项目审核状态',
  `isdelete` tinyint(1) DEFAULT '0' COMMENT '删除标志',
  `fileurl` varchar(1500) DEFAULT NULL,
  `isactive` int(1) DEFAULT '0' COMMENT '项目是否激活：0未激活、1激活',
  `projectmemo` text COMMENT '备注',
  `log` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`projectid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='项目信息表';
CREATE TABLE `fenhuo_projectservicelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` varchar(32) NOT NULL COMMENT '项目id',
  `servicemid` varchar(32) DEFAULT NULL COMMENT '维护人员id',
  `servicername` varchar(128) DEFAULT NULL COMMENT '维护人员姓名',
  `servicermobile` varchar(20) DEFAULT NULL COMMENT '维护人员电话',
  `servicerpushid` varchar(32) DEFAULT NULL COMMENT '维护人员推送id',
  `serviceraddress` varchar(200) DEFAULT NULL COMMENT '维护人员地址',
  `roleid` varchar(32) DEFAULT NULL COMMENT '角色id',
  `rolename` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `servicermemo` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='项目维护人员表';
CREATE TABLE `fenhuo_pushlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectid` bigint(20) DEFAULT NULL,
  `pushid` varchar(255) DEFAULT NULL,
  `pushtitle` varchar(255) DEFAULT NULL,
  `pushtxt` varchar(255) DEFAULT NULL,
  `pushtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
CREATE TABLE `fenhuo_user_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '烽火用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '系统角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
CREATE TABLE `fenhuo_users` (
  `userid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `loginname` varchar(20) DEFAULT NULL COMMENT '登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码md5',
  `salt` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '盐',
  `orgid` varchar(32) DEFAULT NULL COMMENT '组织id',
  `orgname` varchar(100) DEFAULT NULL COMMENT '组织名称',
  `pushid` varchar(64) DEFAULT NULL COMMENT '推送id',
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `roleid` varchar(32) DEFAULT NULL COMMENT '角色id',
  `rolename` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `isdelete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `serviceid` int(11) DEFAULT NULL COMMENT '服务ID',
  `companyname` varchar(100) DEFAULT NULL COMMENT '单位名称',
  `servicecontext` varchar(256) DEFAULT NULL COMMENT '期望服务内容',
  `contacts` varchar(100) DEFAULT NULL COMMENT '联系人',
  `contactstel` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `expectsupport` varchar(256) DEFAULT NULL COMMENT '希望技术支持',
  `university` varchar(100) DEFAULT NULL COMMENT '毕业院校',
  `experience` varchar(128) DEFAULT NULL COMMENT '工作经验',
  `skill` varchar(256) DEFAULT NULL COMMENT '个人技能',
  `intention` varchar(100) DEFAULT NULL COMMENT '意向',
  `certificate` varchar(256) DEFAULT NULL COMMENT '个人证书',
  `remark` varchar(256) DEFAULT NULL COMMENT '补充说明',
  `sex` varchar(8) DEFAULT NULL COMMENT '性别',
  `provice` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `area` varchar(20) DEFAULT NULL COMMENT '区',
  `address` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';
CREATE TABLE `fenhuo_usertoken` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT 'fenhuo_token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '跟新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `fenhuo_zabbixhost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectid` bigint(20) NOT NULL COMMENT '项目id',
  `projectname` varchar(200) DEFAULT NULL COMMENT '项目名称',
  `hostids` varchar(255) DEFAULT NULL COMMENT '监控主机id',
  `zabbixhostnames` varchar(255) DEFAULT NULL COMMENT '监控主机名称',
  `os` varchar(255) DEFAULT NULL,
  `hostip` varchar(200) DEFAULT NULL,
  `zbip` varchar(100) DEFAULT NULL,
  `zbusername` varchar(100) DEFAULT NULL,
  `zbuserpwd` varchar(100) DEFAULT NULL,
  `hoststatus` varchar(64) DEFAULT NULL COMMENT '主机状态',
  `isdeleted` int(2) DEFAULT '0' COMMENT '是否删除标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='项目主机表';
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务';
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
) ENGINE=InnoDB AUTO_INCREMENT=490 DEFAULT CHARSET=utf8mb4 COMMENT='定时任务日志';
CREATE TABLE `sys_captcha` (
  `uuid` char(36) NOT NULL COMMENT 'uuid',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统验证码';
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='系统配置信息表';
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
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COMMENT='系统日志';
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
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COMMENT='菜单管理';
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件上传';
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COMMENT='角色';
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=532 DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单对应关系';
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色对应关系';
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户Token';
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_fault` WRITE;
DELETE FROM `renren_fast`.`fenhuo_fault`;
INSERT INTO `renren_fast`.`fenhuo_fault` (`faultid`,`projectid`,`projectname`,`ip`,`equipment`,`faulttype`,`faulttypename`,`faultdesc`,`starttime`,`networkstatus`,`declartype`,`faultstatus`,`faultstatustxt`,`declarer`,`declarername`,`plan`,`handlerid`,`handlername`,`remark`,`createtime`,`isdelete`) VALUES (17, '35', '第一个项目', '192.168.1.1', '测试设备', 400, '数据库', '暂时没有设备描述', '2020-07-14 00:00:00', '通畅', '', 500, '申报成功', '58', '甲方猴子', '没有1', '57', '小猴子', '无', '2020-07-15 23:12:06', 0),(18, '36', '第二个项目', '192.168.1.1', '测试设备', 401, '设备故障', '暂时没有设备描述', '2020-07-16 00:00:00', '通畅', '', 500, '申报成功', '61', '第二甲方负责猴子', '没有1', '60', '第二小猴子', '无', '2020-07-15 23:18:43', 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_faultdefend` WRITE;
DELETE FROM `renren_fast`.`fenhuo_faultdefend`;
INSERT INTO `renren_fast`.`fenhuo_faultdefend` (`defendid`,`faultid`,`defenderid`,`defendername`,`defenderposition`,`locationtime`,`plan`,`defendresult`,`resultdesc`,`createrid`,`creatername`,`createtime`,`defendstarttime`,`projectid`,`defendendtime`,`projectname`,`isdelete`,`faultdesc`) VALUES (7, '17', '59', '维护工程师猴子', NULL, '2020-07-15 23:12:06', '没有1', NULL, NULL, '58', '甲方猴子', '2020-07-15 23:12:06', '2020-07-15 23:12:06', 35, '2020-07-15 23:12:06', '第一个项目', 0, '暂时没有设备描述'),(8, '18', '62', '第三个维护工程师猴子', NULL, '2020-07-15 23:18:43', '没有1', NULL, NULL, '61', '第二甲方负责猴子', '2020-07-15 23:18:43', '2020-07-15 23:18:43', 36, '2020-07-15 23:18:43', '第二个项目', 0, '暂时没有设备描述');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_operationlog` WRITE;
DELETE FROM `renren_fast`.`fenhuo_operationlog`;
INSERT INTO `renren_fast`.`fenhuo_operationlog` (`logid`,`opdatetime`,`opersonid`,`opersoname`,`opname`,`projectid`,`projectname`,`isdelete`) VALUES (44, '2020-07-15 22:56:26', 57, '小猴子', '新建项目', 35, '第一个项目', 0),(45, '2020-07-15 23:07:59', 2, 'fenhuo', '激活项目', 35, '第一个项目', 0),(46, '2020-07-15 23:08:25', 2, 'fenhuo', '关闭项目', 35, '第一个项目', 0),(47, '2020-07-15 23:09:02', 2, 'fenhuo', '激活项目', 35, '第一个项目', 0),(48, '2020-07-15 23:17:17', 60, '第二小猴子', '新建项目', 36, '第二个项目', 0),(49, '2020-07-15 23:18:13', 2, 'fenhuo', '激活项目', 36, '第二个项目', 0),(50, '2020-07-17 10:31:57', 2, 'fenhuo', '关闭项目', 36, '第二个项目', 0),(51, '2020-07-17 10:46:43', 2, 'fenhuo', '关闭项目', 35, '第一个项目', 0),(52, '2020-07-17 11:07:50', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(53, '2020-07-17 11:22:37', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(54, '2020-07-17 11:27:08', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(55, '2020-07-17 11:30:37', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(56, '2020-07-17 11:35:06', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(57, '2020-07-17 11:35:53', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(58, '2020-07-17 11:38:11', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(59, '2020-07-17 11:44:36', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(60, '2020-07-17 11:48:34', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(61, '2020-07-17 11:57:09', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(62, '2020-07-17 12:00:44', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(63, '2020-07-17 12:02:32', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(64, '2020-07-17 12:03:48', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(65, '2020-07-17 12:06:42', 57, '小猴子', '更新项目', 35, '第一个项目', 0),(66, '2020-07-17 12:58:27', 60, '第二小猴子', '更新项目', 36, '第二个项目', 0),(67, '2020-07-17 13:00:22', 60, '第二小猴子', '更新项目', 36, '第二个项目', 0),(68, '2020-07-17 15:13:34', 60, '第二小猴子', '更新项目', 36, '第二个项目', 0),(69, '2020-07-17 15:14:20', 60, '第二小猴子', '更新项目', 36, '第二个项目', 0),(70, '2020-07-17 15:22:22', 60, '第二小猴子', '新建项目', 37, '第三个项目', 0),(71, '2020-07-17 15:23:09', 60, '第二小猴子', '更新项目', 37, '第三个项目', 0),(72, '2020-07-17 15:23:57', 60, '第二小猴子', '更新项目', 37, '第三个项目', 0),(73, '2020-07-17 15:37:35', 2, 'fenhuo', '激活项目', 37, '第三个项目', 0),(74, '2020-07-17 16:25:05', 60, '第二小猴子', '更新项目', 36, '第二个项目', 0),(75, '2020-07-17 16:25:20', 2, 'fenhuo', '关闭项目', 37, '第三个项目', 0),(76, '2020-07-17 16:25:39', 60, '第二小猴子', '更新项目', 37, '第三个项目', 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_partyalist` WRITE;
DELETE FROM `renren_fast`.`fenhuo_partyalist`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_projectinfo` WRITE;
DELETE FROM `renren_fast`.`fenhuo_projectinfo`;
INSERT INTO `renren_fast`.`fenhuo_projectinfo` (`projectid`,`projectname`,`orgname`,`province`,`city`,`county`,`address`,`projectcreatetime`,`effectivetime`,`serviceid`,`serviceditemetail`,`taskid`,`taskname`,`servicestarttime`,`serviceendtime`,`headid`,`headname`,`headmobile`,`partyaid`,`partyaname`,`servicemid`,`servicemname`,`creater`,`auditstatus`,`auditname`,`isdelete`,`fileurl`,`isactive`,`projectmemo`,`log`) VALUES (36, '第二个项目', '第二个单位', '140000|山西省', '140300|阳泉市', '140311|郊区', '哈啊哈', '2020-07-17 16:25:05', NULL, 201, '故障网络维修', 300, '每天一次', '2020-07-16 00:00:00', '2020-07-23 00:00:00', '60', '第二小猴子', NULL, '61', '第二甲方负责猴子', '62', '第三个维护工程师猴子', NULL, 100, '未审批', 0, '', 0, NULL, NULL),(37, '第三个项目', '第三个单位', '350000|福建省', '350400|三明市', '350423|清流县', 'aaaa', '2020-07-17 16:25:39', NULL, 200, '基础网络维护', 300, '每天一次', '2020-07-22 00:00:00', '2020-07-31 00:00:00', '60', '第二小猴子', NULL, '61', '第二甲方负责猴子', '62', '第三个维护工程师猴子', NULL, 100, '未审批', 0, '', 0, NULL, NULL);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_projectservicelist` WRITE;
DELETE FROM `renren_fast`.`fenhuo_projectservicelist`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_pushlog` WRITE;
DELETE FROM `renren_fast`.`fenhuo_pushlog`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_user_sys_role` WRITE;
DELETE FROM `renren_fast`.`fenhuo_user_sys_role`;
INSERT INTO `renren_fast`.`fenhuo_user_sys_role` (`id`,`user_id`,`role_id`) VALUES (12, 57, 7),(13, 58, 8),(14, 59, 9),(15, 60, 7),(16, 61, 8),(17, 62, 9);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_users` WRITE;
DELETE FROM `renren_fast`.`fenhuo_users`;
INSERT INTO `renren_fast`.`fenhuo_users` (`userid`,`mobile`,`loginname`,`password`,`salt`,`orgid`,`orgname`,`pushid`,`realname`,`roleid`,`rolename`,`create_time`,`last_login`,`isdelete`,`serviceid`,`companyname`,`servicecontext`,`contacts`,`contactstel`,`expectsupport`,`university`,`experience`,`skill`,`intention`,`certificate`,`remark`,`sex`,`provice`,`city`,`area`,`address`,`status`) VALUES (57, '11111111111', '11111111111', '4097f1dad780410ab71f87cae14109240b44c03247d89a9ebe4ec4fe1c85c2ef', 'CC5dihhJ3xLCW0rXsNjl', NULL, NULL, NULL, '小猴子', '2', '', '2020-07-15 22:48:53', '2020-07-15 22:48:53', 0, NULL, '', '', '没有', '没有', '', '', '', '', '', '', '', 'man', '110000|北京市', '110100|市辖区', '110101|东城区', '', '1'),(58, '22222222222', '22222222222', '219b0db8bc272768857fdc9f97eb1bfa74ad92152faa07cb90666165d9f6a9f7', 'IdZsZp9pPCunyHQYFO5F', NULL, NULL, NULL, '甲方猴子', '1', '', '2020-07-15 22:49:38', '2020-07-15 22:49:38', 0, NULL, '', '', '没有', '没有', '', '', '', '', '', '', '', 'man', '120000|天津市', '120100|市辖区', '120101|和平区', '', '1'),(59, '33333333333', '33333333333', '0e54e10b2409e09a3b4c14ab12c90b9598655404f151101ab0b9915e37aa7924', 'voAtUPsiOf1sXiTHbvNP', NULL, NULL, NULL, '维护工程师猴子', '3', '', '2020-07-15 22:50:12', '2020-07-15 22:50:12', 0, NULL, '', '', '没有', '没有', '', '', '', '', '', '', '', 'man', '120000|天津市', '120100|市辖区', '120105|河北区', '', '1'),(60, '44444444444', '44444444444', '5fdc0888a2d7df926045a23db4e9a85dc89967dbcff5aaae7cf00ebc71212006', 'b6SOJ2Pk9OLNWMjFsAXe', NULL, NULL, NULL, '第二小猴子', '2', '', '2020-07-15 23:14:01', '2020-07-15 23:14:00', 0, NULL, '', '', '没有', '没有', '', '', '', '', '', '', '', 'man', '110000|北京市', '110100|市辖区', '110101|东城区', '', '1'),(61, '55555555555', '55555555555', 'da5137ebc904446f8b4f613fcb763223aaad01c25cde655f85afcb9e2f30ce74', 'VG9S7yt6WXmxry6Ja1Wk', NULL, NULL, NULL, '第二甲方负责猴子', '1', '', '2020-07-15 23:14:48', '2020-07-15 23:14:47', 0, NULL, '', '', '灭有', '灭有', '', '', '', '', '', '', '', 'man', '130000|河北省', '130300|秦皇岛市', '130304|北戴河区', '', '1'),(62, '66666666666', '66666666666', '731982161ea5c12f843411f46f3d16c44a6c808eb2b5ecff8e8fe73aba3ea2f4', 'tIsVA7NH5a3unmlumBJc', NULL, NULL, NULL, '第三个维护工程师猴子', '3', '', '2020-07-15 23:15:34', '2020-07-15 23:15:33', 0, NULL, '', '', '没有', '没有', '', '', '', '', '', '', '', 'man', '150000|内蒙古自治区', '150500|通辽市', '150522|科尔沁左翼后旗', '', '1');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_usertoken` WRITE;
DELETE FROM `renren_fast`.`fenhuo_usertoken`;
INSERT INTO `renren_fast`.`fenhuo_usertoken` (`user_id`,`token`,`expire_time`,`update_time`) VALUES (57, '72357e2a38b9e97b5199ca54bb23b2dc', '2020-07-17 17:12:49', '2020-07-17 15:12:49'),(58, 'c9471ec1428ffbb413803a47d265eb6e', '2020-07-16 01:11:47', '2020-07-15 23:11:47'),(59, 'afae29b3851e35e8de7b1fafec8eb902', '2020-07-16 01:12:23', '2020-07-15 23:12:23'),(60, '400b8ff603411988cc9a15fb4deb6ac6', '2020-07-17 17:13:15', '2020-07-17 15:13:15'),(61, '7ea4bd707598681b697802b384149455', '2020-07-16 13:20:40', '2020-07-16 11:20:40');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`fenhuo_zabbixhost` WRITE;
DELETE FROM `renren_fast`.`fenhuo_zabbixhost`;
INSERT INTO `renren_fast`.`fenhuo_zabbixhost` (`id`,`projectid`,`projectname`,`hostids`,`zabbixhostnames`,`os`,`hostip`,`zbip`,`zbusername`,`zbuserpwd`,`hoststatus`,`isdeleted`) VALUES (23, 35, '第一个项目', '10264', '测试服务器A', NULL, NULL, NULL, 'Admin', 'Fire@2019', NULL, 0),(24, 36, '第二个项目', '10084', '主服务器', NULL, NULL, NULL, 'Admin', 'Fire@2019', NULL, 0),(25, 37, '第三个项目', '10084', '主服务器', NULL, NULL, NULL, 'Admin', 'Fire@2019', NULL, 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_blob_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_blob_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_calendars` WRITE;
DELETE FROM `renren_fast`.`qrtz_calendars`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_cron_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_cron_triggers`;
INSERT INTO `renren_fast`.`qrtz_cron_triggers` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', '0 0/30 * * * ?', 'Asia/Shanghai');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_fired_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_fired_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_job_details` WRITE;
DELETE FROM `renren_fast`.`qrtz_job_details`;
INSERT INTO `renren_fast`.`qrtz_job_details` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', NULL, 'io.renren.modules.job.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016CFF249FB07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_locks` WRITE;
DELETE FROM `renren_fast`.`qrtz_locks`;
INSERT INTO `renren_fast`.`qrtz_locks` (`SCHED_NAME`,`LOCK_NAME`) VALUES ('RenrenScheduler', 'STATE_ACCESS'),('RenrenScheduler', 'TRIGGER_ACCESS');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_paused_trigger_grps` WRITE;
DELETE FROM `renren_fast`.`qrtz_paused_trigger_grps`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_scheduler_state` WRITE;
DELETE FROM `renren_fast`.`qrtz_scheduler_state`;
INSERT INTO `renren_fast`.`qrtz_scheduler_state` (`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) VALUES ('RenrenScheduler', 'DESKTOP-5NM40V81594961984898', 1594974807755, 15000);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_simple_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_simple_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_simprop_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_simprop_triggers`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`qrtz_triggers` WRITE;
DELETE FROM `renren_fast`.`qrtz_triggers`;
INSERT INTO `renren_fast`.`qrtz_triggers` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) VALUES ('RenrenScheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', NULL, 1594976400000, 1594974600000, 5, 'WAITING', 'CRON', 1593007568000, 0, NULL, 2, 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002E696F2E72656E72656E2E6D6F64756C65732E6A6F622E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200074C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000016CFF249FB07874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000174000672656E72656E74000CE58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`schedule_job` WRITE;
DELETE FROM `renren_fast`.`schedule_job`;
INSERT INTO `renren_fast`.`schedule_job` (`job_id`,`bean_name`,`params`,`cron_expression`,`status`,`remark`,`create_time`) VALUES (1, 'testTask', 'renren', '0 0/30 * * * ?', 0, '参数测试', '2019-09-05 09:58:06');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`schedule_job_log` WRITE;
DELETE FROM `renren_fast`.`schedule_job_log`;
INSERT INTO `renren_fast`.`schedule_job_log` (`log_id`,`job_id`,`bean_name`,`params`,`status`,`error`,`times`,`create_time`) VALUES (414, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-24 22:30:00'),(415, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-24 23:30:00'),(416, 1, 'testTask', 'renren', 0, NULL, 13, '2020-06-25 00:00:00'),(417, 1, 'testTask', 'renren', 0, NULL, 8, '2020-06-25 00:30:00'),(418, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 09:30:00'),(419, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 10:00:00'),(420, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 10:30:00'),(421, 1, 'testTask', 'renren', 0, NULL, 3, '2020-06-25 11:00:00'),(422, 1, 'testTask', 'renren', 0, NULL, 3, '2020-06-25 11:30:00'),(423, 1, 'testTask', 'renren', 0, NULL, 3, '2020-06-25 12:00:00'),(424, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 13:00:00'),(425, 1, 'testTask', 'renren', 0, NULL, 4, '2020-06-25 13:30:00'),(426, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 14:00:00'),(427, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 14:30:00'),(428, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 15:00:00'),(429, 1, 'testTask', 'renren', 0, NULL, 0, '2020-06-25 15:30:00'),(430, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 16:00:00'),(431, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 16:30:00'),(432, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 17:00:00'),(433, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-25 21:30:00'),(434, 1, 'testTask', 'renren', 0, NULL, 3, '2020-06-25 22:30:00'),(435, 1, 'testTask', 'renren', 0, NULL, 1, '2020-06-25 23:00:00'),(436, 1, 'testTask', 'renren', 0, NULL, 2, '2020-06-26 13:30:00'),(437, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-14 16:00:00'),(438, 1, 'testTask', 'renren', 0, NULL, 4, '2020-07-14 16:30:00'),(439, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-14 17:00:00'),(440, 1, 'testTask', 'renren', 0, NULL, 3, '2020-07-14 17:30:00'),(441, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-14 18:00:00'),(442, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-14 18:30:00'),(443, 1, 'testTask', 'renren', 0, NULL, 20, '2020-07-14 21:30:00'),(444, 1, 'testTask', 'renren', 0, NULL, 24, '2020-07-14 22:00:00'),(445, 1, 'testTask', 'renren', 0, NULL, 0, '2020-07-15 11:00:00'),(446, 1, 'testTask', 'renren', 0, NULL, 14, '2020-07-15 11:30:00'),(447, 1, 'testTask', 'renren', 0, NULL, 5, '2020-07-15 12:00:00'),(448, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 12:30:00'),(449, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-15 13:30:00'),(450, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 14:00:00'),(451, 1, 'testTask', 'renren', 0, NULL, 0, '2020-07-15 14:30:00'),(452, 1, 'testTask', 'renren', 0, NULL, 5, '2020-07-15 15:00:00'),(453, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 15:30:00'),(454, 1, 'testTask', 'renren', 0, NULL, 8, '2020-07-15 16:00:00'),(455, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 16:30:00'),(456, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-15 17:00:00'),(457, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 17:30:00'),(458, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 21:30:00'),(459, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 22:30:00'),(460, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-15 23:00:00'),(461, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-15 23:30:00'),(462, 1, 'testTask', 'renren', 0, NULL, 56, '2020-07-16 00:00:00'),(463, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 09:30:00'),(464, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 10:00:00'),(465, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 10:30:00'),(466, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 11:00:00'),(467, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-16 11:30:00'),(468, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 12:00:00'),(469, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 12:30:00'),(470, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 13:00:00'),(471, 1, 'testTask', 'renren', 0, NULL, 0, '2020-07-16 13:30:00'),(472, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 14:00:00'),(473, 1, 'testTask', 'renren', 0, NULL, 0, '2020-07-16 16:30:00'),(474, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-16 17:00:00'),(475, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 17:30:00'),(476, 1, 'testTask', 'renren', 0, NULL, 3, '2020-07-16 21:00:00'),(477, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 23:00:00'),(478, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-16 23:30:00'),(479, 1, 'testTask', 'renren', 0, NULL, 14, '2020-07-17 00:00:00'),(480, 1, 'testTask', 'renren', 0, NULL, 13, '2020-07-17 00:30:00'),(481, 1, 'testTask', 'renren', 0, NULL, 7, '2020-07-17 10:00:00'),(482, 1, 'testTask', 'renren', 0, NULL, 2, '2020-07-17 10:30:00'),(483, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-17 11:00:00'),(484, 1, 'testTask', 'renren', 0, NULL, 6, '2020-07-17 11:30:00'),(485, 1, 'testTask', 'renren', 0, NULL, 5, '2020-07-17 13:30:00'),(486, 1, 'testTask', 'renren', 0, NULL, 0, '2020-07-17 14:00:00'),(487, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-17 15:30:00'),(488, 1, 'testTask', 'renren', 0, NULL, 1, '2020-07-17 16:00:00'),(489, 1, 'testTask', 'renren', 0, NULL, 8, '2020-07-17 16:30:00');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_captcha` WRITE;
DELETE FROM `renren_fast`.`sys_captcha`;
INSERT INTO `renren_fast`.`sys_captcha` (`uuid`,`code`,`expire_time`) VALUES ('0552cf87-8682-4a5e-8568-6ea4a4bbd27a', '2an2n', '2020-04-28 18:36:08'),('073479f0-ce7d-478d-87b6-658504d9958f', 'm72ap', '2020-04-28 18:46:34'),('074b6ee0-f851-4c9d-8116-a5e96a1d1e3f', 'm5xa7', '2020-05-12 19:57:55'),('0ba3113b-f6e8-433c-8ada-2e43808e69cc', '26epp', '2020-04-26 17:58:11'),('1ee8a50b-933b-4436-86d5-2271473309dc', '2mn72', '2020-04-28 18:34:53'),('216573a5-9416-40e4-8ff0-41e04561abc9', 'f3d23', '2020-05-28 00:05:26'),('23bee3ca-f11d-4c83-809c-860ceab1e0fa', 'xfwa3', '2020-04-28 18:40:26'),('2cc1e14e-cb7b-437d-8dca-aeddc49fa511', '36mx2', '2020-04-29 17:15:25'),('51437c3d-c5b1-49e1-88f6-0d47b8075fbb', '2c44p', '2020-05-28 09:42:17'),('531ea249-9c72-46b9-84c7-5c3ad7419c23', 'neyyx', '2020-04-28 18:35:38'),('5ce240be-fe22-452a-8f1a-d892dc864218', 'fwa8b', '2020-04-22 16:24:04'),('6900724a-28bb-40f4-85e0-269591eaa5ec', '3g4np', '2020-04-27 17:27:48'),('6f374906-9c69-4216-8530-24080644f6b8', '87ec7', '2020-04-28 18:40:51'),('81ada778-86ff-4f48-8f51-5f54f8ce7737', 'fem7n', '2020-04-28 18:40:13'),('85bee2bf-da65-45fa-81e1-d03587c67e22', '3nm25', '2020-04-22 16:23:13'),('863e0e8a-fd0c-41c2-8b74-319ba839e655', 'e8nna', '2020-04-26 17:58:13'),('8a1bda17-f03a-4cb3-8fba-4b87c8cb9838', 'n6cwg', '2020-04-28 18:36:22'),('9c42d4f0-35dc-44a4-89e1-622e2146a68e', '5gp44', '2020-05-28 00:02:46'),('9f9ce596-8424-4493-8528-93056fcb5ebf', 'c8nc6', '2020-05-12 17:30:56'),('9fb8122b-2b9e-4ea2-89d5-564c72708f39', 'cxpcf', '2020-04-29 23:50:08'),('b94e760a-8766-4f01-8e3a-45ce07929342', '2mam5', '2020-04-28 18:40:45'),('c565c534-9d78-45e0-8037-45f6dd796de0', 'gnc63', '2020-04-28 18:46:34'),('cbe8b3c5-fdaf-4010-82e5-fa71597048b6', 'p8bg8', '2020-04-28 18:39:09'),('ce82e9a8-3403-46c5-84c5-52a2e828a97e', 'dp2en', '2020-05-12 22:27:19'),('dbde90a1-ae8f-4f9e-875b-022bb4beca56', 'g4638', '2020-05-12 20:02:08'),('dee68dec-3c36-4ff5-8755-aee6fac7377a', 'yxen5', '2020-04-27 17:21:05'),('edad796f-f32c-4dfa-8ba8-48d98444cddc', '23xm8', '2020-04-28 18:35:02'),('ee9f53ae-5a3a-4084-8607-8dacba3a9330', 'a2e5x', '2020-04-30 16:34:27'),('f90c58e8-9508-4118-8c54-30f707f90927', 'nc2ad', '2020-04-28 18:46:34'),('fabaa030-7848-4645-81d5-d49126e82dba', '8np6f', '2020-04-28 18:36:32'),('fef9601d-cebe-4760-8604-f214e2542340', '6g4d2', '2020-05-12 22:25:45');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_config` WRITE;
DELETE FROM `renren_fast`.`sys_config`;
INSERT INTO `renren_fast`.`sys_config` (`id`,`param_key`,`param_value`,`status`,`remark`) VALUES (1, 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', 0, '云存储配置信息'),(2, '100', '未审批', 1, '参数名100-199为烽火项目审批预留字段'),(3, '101', '审批中', 1, '参数名100-199为烽火项目审批预留字段'),(4, '102', '审批完成', 1, '参数名100-199为烽火项目审批预留字段'),(5, '200', '基础网络维护', 1, '参数名200-299为烽火项目服务类型预留字段'),(6, '201', '故障网络维修', 1, '参数名200-299为烽火项目服务类型预留字段'),(7, '2', '项目负责人', 1, '参数名0-99为烽火项目所有角色预留字段'),(8, '1', '甲方负责人', 1, '参数名0-99为烽火项目所有角色预留字段'),(9, '3', '维护工程师', 1, '参数名0-99为烽火项目所有角色预留字段'),(10, '4', '系统管理员', 1, '参数名0-99为烽火项目所有角色预留字段'),(11, '5', '注册工程师', 1, '参数名0-99为烽火项目所有角色预留字段'),(12, '6', '注册用户', 1, '参数名0-99为烽火项目所有角色预留字段'),(13, '300', '每天一次', 1, '参数名300-399为烽火项目中项目监测周期数'),(14, '301', '每周一次', 1, '参数名300-399为烽火项目中项目监测周期数'),(15, '302', '每月一次', 1, '参数名300-399为烽火项目中项目监测周期数'),(16, '103', '未审批通过', 1, '参数名100-199为烽火项目审批预留字段'),(17, '400', '数据库', 1, '参数名400-499为故障类型预留字段'),(18, '401', '设备故障', 1, '参数名400-499为故障类型预留字段'),(19, '402', '人为原因', 1, '参数名400-499为故障类型预留字段'),(21, '403', '其他', 1, '参数名400-499为故障类型预留字段'),(22, '500', '申报成功', 1, '参数名500-500为故障申报状态预留字段'),(23, '501', '已取消', 1, '参数名500-500为故障申报状态预留字段'),(24, '502', '已解决', 1, '参数名500-500为故障申报状态预留字段');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_log` WRITE;
DELETE FROM `renren_fast`.`sys_log`;
INSERT INTO `renren_fast`.`sys_log` (`id`,`username`,`operation`,`method`,`params`,`time`,`ip`,`create_date`) VALUES (1, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"用户管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"geren\",\"orderNum\":0}]', 17, '0:0:0:0:0:0:0:1', '2020-04-29 10:59:10'),(2, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"项目负责人\",\"url\":\"/userMg/proManager\",\"perms\":\"userMg:proManager\",\"type\":1,\"icon\":\"config\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2020-04-29 11:01:59'),(3, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"项目负责人\",\"url\":\"/use/promg\",\"perms\":\"user:promg\",\"type\":1,\"icon\":\"config\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2020-04-29 11:28:24'),(4, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":32,\"parentId\":31,\"name\":\"项目负责人\",\"url\":\"/user/promg\",\"perms\":\"user:promg\",\"type\":1,\"icon\":\"config\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2020-04-29 13:09:13'),(5, 'admin', '保存用户', 'io.renren.modules.sys.controller.SysUserController.save()', '[{\"userId\":2,\"username\":\"fenhuo\",\"password\":\"f46ee88c504b2196714d4e20b6ade939f1001f9524aca996e620cafb6cb26f98\",\"salt\":\"mIMV4e2a40kfCo7XETzO\",\"email\":\"616391592@qq.com\",\"mobile\":\"11111111111\",\"status\":1,\"roleIdList\":[],\"createUserId\":1,\"createTime\":\"Apr 29, 2020 2:45:11 PM\"}]', 457, '0:0:0:0:0:0:0:1', '2020-04-29 14:45:12'),(6, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,-666666],\"createTime\":\"Apr 29, 2020 2:45:43 PM\"}]', 84, '0:0:0:0:0:0:0:1', '2020-04-29 14:45:44'),(7, 'admin', '修改用户', 'io.renren.modules.sys.controller.SysUserController.update()', '[{\"userId\":2,\"username\":\"fenhuo\",\"password\":\"f46ee88c504b2196714d4e20b6ade939f1001f9524aca996e620cafb6cb26f98\",\"salt\":\"mIMV4e2a40kfCo7XETzO\",\"email\":\"616391592@qq.com\",\"mobile\":\"11111111111\",\"status\":1,\"roleIdList\":[1],\"createUserId\":1}]', 37, '0:0:0:0:0:0:0:1', '2020-04-29 14:46:46'),(8, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":33,\"parentId\":0,\"name\":\"项目管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"mudedi\",\"orderNum\":1}]', 13, '0:0:0:0:0:0:0:1', '2020-04-29 15:57:44'),(9, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":34,\"parentId\":33,\"name\":\"项目管理\",\"url\":\"/proj/mng\",\"perms\":\"proj:mng\",\"type\":1,\"icon\":\"mudedi\",\"orderNum\":0}]', 15, '0:0:0:0:0:0:0:1', '2020-04-29 15:59:43'),(10, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,33,34,-666666]}]', 85, '0:0:0:0:0:0:0:1', '2020-04-29 16:33:05'),(11, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":35,\"parentId\":32,\"name\":\"烽火_用户查看\",\"url\":\"\",\"perms\":\"feihuo:user:list,feihuo:user:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 27, '0:0:0:0:0:0:0:1', '2020-04-29 16:36:41'),(12, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":36,\"parentId\":32,\"name\":\"烽火_用户添加\",\"url\":\"\",\"perms\":\"fenhuo:user:add\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 13, '0:0:0:0:0:0:0:1', '2020-04-29 16:38:45'),(13, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":37,\"parentId\":32,\"name\":\"烽火_用户删除\",\"url\":\"\",\"perms\":\"fenhuo:user:del\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 10, '0:0:0:0:0:0:0:1', '2020-04-29 16:47:51'),(14, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":38,\"parentId\":32,\"name\":\"烽火_用户批量导入\",\"url\":\"\",\"perms\":\"fenhuo:user:batch:add\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 27, '0:0:0:0:0:0:0:1', '2020-04-29 16:49:43'),(15, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[3,19,20,21,22,31,32,35,36,37,38,33,34,-666666,1]}]', 104, '0:0:0:0:0:0:0:1', '2020-04-29 16:59:12'),(16, 'fenhuo', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":5,\"roleName\":\"ces_角色\",\"remark\":\"\",\"createUserId\":2,\"menuIdList\":[3,19,20,21,22,-666666,1],\"createTime\":\"Apr 29, 2020 5:01:05 PM\"}]', 55, '0:0:0:0:0:0:0:1', '2020-04-29 17:01:05'),(17, 'fenhuo', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[[5]]', 34, '0:0:0:0:0:0:0:1', '2020-04-29 17:01:13'),(18, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":39,\"parentId\":32,\"name\":\"烽火_添加角色\",\"url\":\"\",\"perms\":\"feihuo:user:role:add\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2020-04-29 17:09:44'),(19, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[3,19,20,21,22,31,32,35,36,37,38,39,33,34,-666666,1]}]', 109, '0:0:0:0:0:0:0:1', '2020-04-29 17:10:18'),(20, 'fenhuo', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":6,\"roleName\":\"烽火_项目管理人员\",\"remark\":\"\",\"createUserId\":2,\"menuIdList\":[33,34,-666666],\"createTime\":\"Apr 29, 2020 11:03:28 PM\"}]', 412, '0:0:0:0:0:0:0:1', '2020-04-29 23:03:28'),(21, 'fenhuo', '删除角色', 'io.renren.modules.sys.controller.SysRoleController.delete()', '[[6]]', 58, '0:0:0:0:0:0:0:1', '2020-04-29 23:05:30'),(22, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":40,\"parentId\":31,\"name\":\"甲方负责人\",\"url\":\"/user/Amng\",\"perms\":\"user:Amng\",\"type\":1,\"icon\":\"log\",\"orderNum\":2}]', 18, '0:0:0:0:0:0:0:1', '2020-04-29 23:08:10'),(23, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":41,\"parentId\":31,\"name\":\"维护工程师\",\"url\":\"/user/maintain\",\"perms\":\"user:maintain\",\"type\":1,\"icon\":\"daohang\",\"orderNum\":3}]', 14, '0:0:0:0:0:0:0:1', '2020-04-29 23:09:59'),(24, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":42,\"parentId\":31,\"name\":\"注册用户\",\"url\":\"/user/regist\",\"perms\":\"user:regist\",\"type\":1,\"icon\":\"editor\",\"orderNum\":4}]', 11, '0:0:0:0:0:0:0:1', '2020-04-29 23:10:54'),(25, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":43,\"parentId\":31,\"name\":\"注册工程师\",\"url\":\"/user/registEngineer\",\"perms\":\"user:registEngineer\",\"type\":1,\"icon\":\"shezhi\",\"orderNum\":5}]', 12, '0:0:0:0:0:0:0:1', '2020-04-29 23:12:34'),(26, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,35,36,37,38,39,40,41,42,43,33,34,-666666]}]', 56, '0:0:0:0:0:0:0:1', '2020-04-29 23:18:47'),(27, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[33,34,-666666],\"createTime\":\"Apr 29, 2020 11:20:40 PM\"}]', 20, '0:0:0:0:0:0:0:1', '2020-04-29 23:20:40'),(28, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":44,\"parentId\":40,\"name\":\"烽火_甲方_列表\",\"url\":\"\",\"perms\":\"fenhuo:fenhuousers:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 14, '0:0:0:0:0:0:0:1', '2020-05-06 10:04:03'),(29, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":45,\"parentId\":40,\"name\":\"烽火_甲方_信息\",\"url\":\"\",\"perms\":\"fenhuo:fenhuousers:info\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 13, '0:0:0:0:0:0:0:1', '2020-05-06 10:04:39'),(30, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":46,\"parentId\":40,\"name\":\"烽火_甲方_保存\",\"url\":\"\",\"perms\":\"fenhuo:fenhuousers:save\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 11, '0:0:0:0:0:0:0:1', '2020-05-06 10:05:49'),(31, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":47,\"parentId\":40,\"name\":\"烽火_甲方_更新\",\"url\":\"\",\"perms\":\"fenhuo:fenhuousers:update\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 10, '0:0:0:0:0:0:0:1', '2020-05-06 10:06:21'),(32, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":48,\"parentId\":40,\"name\":\"烽火_甲方_删除\",\"url\":\"\",\"perms\":\"fenhuo:fenhuousers:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 9, '0:0:0:0:0:0:0:1', '2020-05-06 10:06:54'),(33, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":49,\"parentId\":34,\"name\":\"烽火项目_保存\",\"url\":\"\",\"perms\":\"fenhuo:proj:save\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 27, '0:0:0:0:0:0:0:1', '2020-05-12 23:20:20'),(34, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":50,\"parentId\":34,\"name\":\"烽火项目_删除\",\"url\":\"\",\"perms\":\"fenhuo:proj:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 12, '0:0:0:0:0:0:0:1', '2020-05-12 23:21:03'),(35, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":51,\"parentId\":34,\"name\":\"烽火项目_激活\",\"url\":\"\",\"perms\":\"fenhuo:proj:active\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 13, '0:0:0:0:0:0:0:1', '2020-05-12 23:21:29'),(36, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":52,\"parentId\":34,\"name\":\"烽火项目_审批\",\"url\":\"\",\"perms\":\"fenhuo:proj:auth\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 8, '0:0:0:0:0:0:0:1', '2020-05-12 23:21:55'),(37, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":53,\"parentId\":34,\"name\":\"烽火项目_关闭\",\"url\":\"\",\"perms\":\"fenhuo:proj:close\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 10, '0:0:0:0:0:0:0:1', '2020-05-12 23:22:15'),(38, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[33,34,49,50,51,52,53,-666666]}]', 297, '0:0:0:0:0:0:0:1', '2020-05-12 23:22:35'),(39, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":54,\"parentId\":34,\"name\":\"烽火项目_查看\",\"url\":\"\",\"perms\":\"fenhuo:fenhuoprojectinfo:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 9, '0:0:0:0:0:0:0:1', '2020-05-12 23:25:28'),(40, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[33,34,49,50,51,52,53,54,-666666]}]', 44, '0:0:0:0:0:0:0:1', '2020-05-12 23:27:14'),(41, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":2,\"paramKey\":\"100\",\"paramValue\":\"未审批\",\"remark\":\"参数名100-199为烽火项目审批预留字段\"}]', 395, '0:0:0:0:0:0:0:1', '2020-05-25 23:58:51'),(42, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":3,\"paramKey\":\"101\",\"paramValue\":\"项目审批中\",\"remark\":\"\"}]', 6, '0:0:0:0:0:0:0:1', '2020-05-25 23:59:17'),(43, 'admin', '修改配置', 'io.renren.modules.sys.controller.SysConfigController.update()', '[{\"id\":3,\"paramKey\":\"101\",\"paramValue\":\"项目审批中\",\"remark\":\"参数名100-199为烽火项目审批预留字段\"}]', 82, '0:0:0:0:0:0:0:1', '2020-05-25 23:59:26'),(44, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":4,\"paramKey\":\"102\",\"paramValue\":\"审批完成\",\"remark\":\"参数名100-199为烽火项目审批预留字段\"}]', 8, '0:0:0:0:0:0:0:1', '2020-05-25 23:59:46'),(45, 'admin', '修改配置', 'io.renren.modules.sys.controller.SysConfigController.update()', '[{\"id\":3,\"paramKey\":\"101\",\"paramValue\":\"审批中\",\"remark\":\"参数名100-199为烽火项目审批预留字段\"}]', 8, '0:0:0:0:0:0:0:1', '2020-05-25 23:59:58'),(46, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":5,\"paramKey\":\"200\",\"paramValue\":\"基础网络维护\",\"remark\":\"参数名200-299为烽火项目服务类型预留字段\"}]', 6, '0:0:0:0:0:0:0:1', '2020-05-26 00:04:36'),(47, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":6,\"paramKey\":\"201\",\"paramValue\":\"故障网络维修\",\"remark\":\"参数名200-299为烽火项目服务类型预留字段\"}]', 7, '0:0:0:0:0:0:0:1', '2020-05-26 00:04:51'),(48, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":7,\"paramKey\":\"2\",\"paramValue\":\"项目负责人\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 6, '0:0:0:0:0:0:0:1', '2020-05-26 00:08:58'),(49, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":8,\"paramKey\":\"1\",\"paramValue\":\"甲方负责人\",\"remark\":\"\"}]', 4, '0:0:0:0:0:0:0:1', '2020-05-26 00:09:55'),(50, 'admin', '修改配置', 'io.renren.modules.sys.controller.SysConfigController.update()', '[{\"id\":8,\"paramKey\":\"1\",\"paramValue\":\"甲方负责人\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 8, '0:0:0:0:0:0:0:1', '2020-05-26 00:10:02'),(51, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":9,\"paramKey\":\"3\",\"paramValue\":\"维护工程师\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 7, '0:0:0:0:0:0:0:1', '2020-05-26 00:10:27'),(52, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":10,\"paramKey\":\"4\",\"paramValue\":\"系统管理员\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 7, '0:0:0:0:0:0:0:1', '2020-05-26 00:11:05'),(53, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":11,\"paramKey\":\"5\",\"paramValue\":\"注册工程师\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 4, '0:0:0:0:0:0:0:1', '2020-05-26 00:11:22'),(54, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":12,\"paramKey\":\"6\",\"paramValue\":\"注册用户\",\"remark\":\"参数名0-99为烽火项目所有角色预留字段\"}]', 6, '0:0:0:0:0:0:0:1', '2020-05-26 00:11:35'),(55, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":13,\"paramKey\":\"300\",\"paramValue\":\"每天一次\",\"remark\":\"参数名300-399为烽火项目中项目监测周期数\"}]', 388, '0:0:0:0:0:0:0:1', '2020-05-26 16:05:20'),(56, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":14,\"paramKey\":\"301\",\"paramValue\":\"每周一次\",\"remark\":\"参数名300-399为烽火项目中项目监测周期数\"}]', 9, '0:0:0:0:0:0:0:1', '2020-05-26 16:05:42'),(57, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":15,\"paramKey\":\"302\",\"paramValue\":\"每月一次\",\"remark\":\"参数名300-399为烽火项目中项目监测周期数\"}]', 9, '0:0:0:0:0:0:0:1', '2020-05-26 16:05:54'),(58, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":55,\"parentId\":34,\"name\":\"烽火项目_修改\",\"url\":\"\",\"perms\":\"fenhuo:fenhuoprojectinfo:update\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 35, '0:0:0:0:0:0:0:1', '2020-05-27 23:53:12'),(59, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":56,\"parentId\":0,\"name\":\"日志\",\"url\":\"/rec/oplog\",\"perms\":\"rec:oplog:list\",\"type\":1,\"icon\":\"menu\",\"orderNum\":2}]', 18, '0:0:0:0:0:0:0:1', '2020-05-28 15:35:04'),(60, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":57,\"parentId\":56,\"name\":\"烽火日志删除\",\"url\":\"\",\"perms\":\"fenhuo:fenhuooperationlog:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 13, '0:0:0:0:0:0:0:1', '2020-05-28 16:23:07'),(61, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":58,\"parentId\":56,\"name\":\"烽火日志查找\",\"url\":\"\",\"perms\":\"fenhuo:fenhuooperationlog:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 12, '0:0:0:0:0:0:0:1', '2020-05-28 16:23:49'),(62, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":51,\"parentId\":34,\"name\":\"烽火项目_激活\",\"url\":\"\",\"perms\":\"fenhuo:proj:active,fenhuo:fenhuoprojectinfo:active\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 31, '0:0:0:0:0:0:0:1', '2020-05-28 22:53:18'),(63, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":16,\"paramKey\":\"103\",\"paramValue\":\"未审批通过\",\"remark\":\"\"}]', 290, '0:0:0:0:0:0:0:1', '2020-05-28 23:12:32'),(64, 'admin', '修改配置', 'io.renren.modules.sys.controller.SysConfigController.update()', '[{\"id\":16,\"paramKey\":\"103\",\"paramValue\":\"未审批通过\",\"remark\":\"参数名100-199为烽火项目审批预留字段\"}]', 139, '0:0:0:0:0:0:0:1', '2020-05-28 23:12:47'),(65, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":59,\"parentId\":34,\"name\":\"烽火项目_未审核通过\",\"url\":\"\",\"perms\":\"fenhuo:fenhuoprojectinfo:faildaudit\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 21, '0:0:0:0:0:0:0:1', '2020-05-28 23:39:26'),(66, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":60,\"parentId\":0,\"name\":\"维护记录\",\"url\":\"/rec/fenhuofault\",\"perms\":\"fenhuo:rec:fenhuofault\",\"type\":1,\"icon\":\"zhedie\",\"orderNum\":7}]', 22, '0:0:0:0:0:0:0:1', '2020-05-29 23:50:11'),(67, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":60,\"parentId\":0,\"name\":\"故障申报\",\"url\":\"/rec/fenhuofault\",\"perms\":\"fenhuo:rec:fenhuofault\",\"type\":1,\"icon\":\"zhedie\",\"orderNum\":7}]', 137, '0:0:0:0:0:0:0:1', '2020-05-29 23:54:54'),(68, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":60,\"parentId\":0,\"name\":\"故障申报\",\"url\":\"/rec/fenhuofault\",\"perms\":\"fenhuo:rec:fenhuofault\",\"type\":1,\"icon\":\"sql\",\"orderNum\":7}]', 19, '0:0:0:0:0:0:0:1', '2020-05-29 23:55:15'),(69, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":61,\"parentId\":60,\"name\":\"烽火故障申报_添加\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofault:save\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2020-05-29 23:56:37'),(70, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":62,\"parentId\":60,\"name\":\"烽火故障申报_删除\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofault:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 16, '0:0:0:0:0:0:0:1', '2020-05-29 23:57:26'),(71, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[32,35,36,37,38,39,40,44,45,46,47,48,41,33,34,49,50,51,52,53,54,55,59,-666666,31]}]', 371, '0:0:0:0:0:0:0:1', '2020-05-30 12:35:02'),(72, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,35,36,37,38,39,40,44,45,46,47,48,41,42,43,33,34,49,50,51,52,53,54,55,59,-666666]}]', 170, '0:0:0:0:0:0:0:1', '2020-05-30 12:40:35'),(73, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,35,36,37,38,39,40,44,45,46,47,48,41,42,43,33,34,49,50,51,52,53,54,55,59,56,57,58,60,61,62,-666666]}]', 177, '0:0:0:0:0:0:0:1', '2020-05-30 12:44:04'),(74, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[32,35,36,37,38,39,40,44,45,46,47,48,41,33,34,49,50,51,52,53,54,55,59,56,57,58,60,61,62,-666666,31]}]', 88, '0:0:0:0:0:0:0:1', '2020-05-30 12:44:28'),(75, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,-666666,33,34],\"createTime\":\"May 30, 2020 2:11:01 PM\"}]', 317, '0:0:0:0:0:0:0:1', '2020-05-30 14:11:02'),(76, 'admin', '保存角色', 'io.renren.modules.sys.controller.SysRoleController.save()', '[{\"roleId\":9,\"roleName\":\"烽火项目_维护工程师\",\"remark\":\"烽火项目_维护工程师角色\",\"createUserId\":1,\"menuIdList\":[54,-666666,33,34],\"createTime\":\"May 30, 2020 2:49:31 PM\"}]', 336, '0:0:0:0:0:0:0:1', '2020-05-30 14:49:32'),(77, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火项目_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,-666666,33,34]}]', 65, '0:0:0:0:0:0:0:1', '2020-05-30 14:50:03'),(78, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,-666666,33,34]}]', 51, '0:0:0:0:0:0:0:1', '2020-05-30 14:50:17'),(79, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,-666666,33,34]}]', 292, '0:0:0:0:0:0:0:1', '2020-05-30 16:52:09'),(80, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":17,\"paramKey\":\"400\",\"paramValue\":\"数据库\",\"remark\":\"参数名400-499为故障类型预留字段\"}]', 24, '0:0:0:0:0:0:0:1', '2020-05-30 16:58:27'),(81, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":18,\"paramKey\":\"401\",\"paramValue\":\"设备故障\",\"remark\":\"参数名400-499为故障类型预留字段\"}]', 108, '0:0:0:0:0:0:0:1', '2020-05-30 16:58:48'),(82, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":19,\"paramKey\":\"402\",\"paramValue\":\"人为原因\",\"remark\":\"参数名400-499为故障类型预留字段\"}]', 8, '0:0:0:0:0:0:0:1', '2020-05-30 16:59:04'),(83, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":21,\"paramKey\":\"403\",\"paramValue\":\"其他\",\"remark\":\"参数名400-499为故障类型预留字段\"}]', 5, '0:0:0:0:0:0:0:1', '2020-05-30 16:59:27'),(84, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":63,\"parentId\":60,\"name\":\"烽火故障申报_查看\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofault:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 28, '0:0:0:0:0:0:0:1', '2020-06-06 14:51:42'),(85, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[33,34,49,50,51,52,53,54,55,59,56,57,58,60,61,62,63,-666666]}]', 279, '0:0:0:0:0:0:0:1', '2020-06-06 15:13:43'),(86, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,-666666,33,34]}]', 42, '0:0:0:0:0:0:0:1', '2020-06-06 15:13:57'),(87, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,-666666,33,34]}]', 34, '0:0:0:0:0:0:0:1', '2020-06-06 15:14:07'),(88, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,-666666,33,34]}]', 302, '0:0:0:0:0:0:0:1', '2020-06-06 15:34:26'),(89, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,-666666,33,34]}]', 55, '0:0:0:0:0:0:0:1', '2020-06-06 15:34:33'),(90, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":22,\"paramKey\":\"500\",\"paramValue\":\"申报成功\",\"remark\":\"参数名500-500为故障申报状态预留字段\"}]', 193, '0:0:0:0:0:0:0:1', '2020-06-07 10:23:37'),(91, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":23,\"paramKey\":\"501\",\"paramValue\":\"已取消\",\"remark\":\"参数名500-500为故障申报状态预留字段\"}]', 7, '0:0:0:0:0:0:0:1', '2020-06-07 10:23:56'),(92, 'admin', '保存配置', 'io.renren.modules.sys.controller.SysConfigController.save()', '[{\"id\":24,\"paramKey\":\"502\",\"paramValue\":\"已解决\",\"remark\":\"参数名500-500为故障申报状态预留字段\"}]', 10, '0:0:0:0:0:0:0:1', '2020-06-07 10:24:12'),(93, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":64,\"parentId\":0,\"name\":\"维护记录\",\"url\":\"/rec/fenhuofaultdefend\",\"perms\":\"fenhuo:fenhuofaultdefend:list\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":0}]', 15, '0:0:0:0:0:0:0:1', '2020-06-07 11:07:45'),(94, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":65,\"parentId\":60,\"name\":\"烽火维护记录_修改\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofaultdefend:update\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 11, '0:0:0:0:0:0:0:1', '2020-06-07 11:09:43'),(95, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[65]', 25, '0:0:0:0:0:0:0:1', '2020-06-07 11:10:18'),(96, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":66,\"parentId\":64,\"name\":\"烽火维护记录_修改\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofaultdefend:update\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 9, '0:0:0:0:0:0:0:1', '2020-06-07 11:10:52'),(97, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":67,\"parentId\":64,\"name\":\"烽火维护记录_删除\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofaultdefend:delete\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 15, '0:0:0:0:0:0:0:1', '2020-06-07 11:14:21'),(98, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":68,\"parentId\":0,\"name\":\"平台统计\",\"url\":\"entry/viewall\",\"perms\":\"entry:viewall\",\"type\":1,\"icon\":\"oss\",\"orderNum\":0}]', 8, '0:0:0:0:0:0:0:1', '2020-06-15 23:14:08'),(99, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":1,\"parentId\":0,\"name\":\"系统管理\",\"type\":0,\"icon\":\"system\",\"orderNum\":1}]', 13, '0:0:0:0:0:0:0:1', '2020-06-15 23:15:25'),(100, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":31,\"parentId\":0,\"name\":\"用户管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"geren\",\"orderNum\":2}]', 6, '0:0:0:0:0:0:0:1', '2020-06-15 23:15:35'),(101, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":33,\"parentId\":0,\"name\":\"项目管理\",\"url\":\"\",\"perms\":\"\",\"type\":0,\"icon\":\"mudedi\",\"orderNum\":3}]', 5, '0:0:0:0:0:0:0:1', '2020-06-15 23:15:43'),(102, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":56,\"parentId\":0,\"name\":\"日志\",\"url\":\"/rec/oplog\",\"perms\":\"rec:oplog:list\",\"type\":1,\"icon\":\"menu\",\"orderNum\":4}]', 7, '0:0:0:0:0:0:0:1', '2020-06-15 23:15:56'),(103, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":60,\"parentId\":0,\"name\":\"故障申报\",\"url\":\"/rec/fenhuofault\",\"perms\":\"fenhuo:rec:fenhuofault\",\"type\":1,\"icon\":\"sql\",\"orderNum\":5}]', 7, '0:0:0:0:0:0:0:1', '2020-06-15 23:16:07'),(104, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":64,\"parentId\":0,\"name\":\"维护记录\",\"url\":\"/rec/fenhuofaultdefend\",\"perms\":\"fenhuo:fenhuofaultdefend:list\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":6}]', 7, '0:0:0:0:0:0:0:1', '2020-06-15 23:16:14'),(105, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":68,\"parentId\":0,\"name\":\"平台统计\",\"url\":\"entry/viewall\",\"perms\":\"entry:viewall\",\"type\":1,\"icon\":\"oss\",\"orderNum\":0}]', 5, '0:0:0:0:0:0:0:1', '2020-06-15 23:16:20'),(106, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":1,\"roleName\":\"烽火账号\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[31,32,35,36,37,38,39,40,44,45,46,47,48,41,42,43,33,34,49,50,51,52,53,54,55,59,56,57,58,60,61,62,63,64,66,67,68,-666666]}]', 398, '0:0:0:0:0:0:0:1', '2020-06-16 12:04:48'),(107, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,-666666,33,34]}]', 420, '0:0:0:0:0:0:0:1', '2020-07-15 15:15:13'),(108, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,60,61,62,63,64,66,67,-666666,33,34]}]', 53, '0:0:0:0:0:0:0:1', '2020-07-15 15:29:59'),(109, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":69,\"parentId\":34,\"name\":\"zabbix_登录\",\"url\":\"\",\"perms\":\"fenhuo:fenhuozabbixhost:authAndgethosts\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 20, '0:0:0:0:0:0:0:1', '2020-07-15 16:53:35'),(110, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[33,34,49,50,51,52,53,54,55,59,69,56,57,58,60,61,62,63,-666666]}]', 131, '0:0:0:0:0:0:0:1', '2020-07-15 16:53:51'),(111, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,56,57,58,60,61,62,63,-666666,33,34]}]', 387, '0:0:0:0:0:0:0:1', '2020-07-15 17:21:18'),(112, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,56,57,58,60,61,62,63,64,66,67,-666666,33,34]}]', 373, '0:0:0:0:0:0:0:1', '2020-07-15 22:15:39'),(113, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[35,44,41,49,50,54,55,69,56,57,58,60,61,62,63,64,66,67,-666666,31,32,40,33,34]}]', 170, '127.0.0.1', '2020-07-15 22:25:49'),(114, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,56,57,58,60,61,62,63,64,66,67,-666666,33,34]}]', 518, '127.0.0.1', '2020-07-15 22:52:37'),(115, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":55,\"parentId\":34,\"name\":\"烽火项目_修改\",\"url\":\"\",\"perms\":\"fenhuo:fenhuoprojectinfo:update,fenhuo:fenhuousers:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 38, '127.0.0.1', '2020-07-15 22:54:48'),(116, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":70,\"parentId\":60,\"name\":\"烽火维护记录_列表\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofaultdefend:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 19, '127.0.0.1', '2020-07-15 23:02:11'),(117, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[70]', 28, '127.0.0.1', '2020-07-15 23:04:15'),(118, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":71,\"parentId\":64,\"name\":\"烽火维护记录_列表\",\"url\":\"\",\"perms\":\"fenhuo:fenhuofaultdefend:list\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 14, '127.0.0.1', '2020-07-15 23:05:01'),(119, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,56,57,58,63,71,-666666,33,34,60,64]}]', 88, '127.0.0.1', '2020-07-15 23:06:18'),(120, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,63,66,71,-666666,33,34,60,64]}]', 83, '127.0.0.1', '2020-07-15 23:11:12'),(121, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":72,\"parentId\":34,\"name\":\"烽火项目_相关文件下载\",\"url\":\"\",\"perms\":\"fenhuo:proj:relatedfile:download\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 28, '0:0:0:0:0:0:0:1', '2020-07-17 09:52:18'),(122, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,72,56,57,58,63,71,-666666,33,34,60,64]}]', 415, '0:0:0:0:0:0:0:1', '2020-07-17 09:52:40'),(123, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,72,60,61,62,63,64,66,67,71,-666666,33,34]}]', 149, '0:0:0:0:0:0:0:1', '2020-07-17 09:52:50'),(124, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,72,63,66,71,-666666,33,34,60,64]}]', 100, '0:0:0:0:0:0:0:1', '2020-07-17 09:52:57'),(125, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":73,\"parentId\":34,\"name\":\"烽火项目_相关文件列表\",\"url\":\"\",\"perms\":\"fenhuo:fenhuoprojectinfo:projectfilelist\",\"type\":2,\"icon\":\"\",\"orderNum\":0}]', 39, '127.0.0.1', '2020-07-17 09:58:24'),(126, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":7,\"roleName\":\"烽火_项目负责人\",\"remark\":\"\",\"createUserId\":1,\"menuIdList\":[49,50,54,55,69,72,73,56,57,58,63,71,-666666,33,34,60,64]}]', 461, '127.0.0.1', '2020-07-17 09:58:40'),(127, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":8,\"roleName\":\"烽火_甲方负责人\",\"remark\":\"烽火项目角色-甲方负责人\",\"createUserId\":1,\"menuIdList\":[54,72,73,60,61,62,63,64,66,67,71,-666666,33,34]}]', 148, '127.0.0.1', '2020-07-17 09:58:50'),(128, 'admin', '修改角色', 'io.renren.modules.sys.controller.SysRoleController.update()', '[{\"roleId\":9,\"roleName\":\"烽火_维护工程师\",\"remark\":\"烽火项目角色-维护工程师\",\"createUserId\":1,\"menuIdList\":[54,72,73,63,66,71,-666666,33,34,60,64]}]', 123, '127.0.0.1', '2020-07-17 09:58:59');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_menu` WRITE;
DELETE FROM `renren_fast`.`sys_menu`;
INSERT INTO `renren_fast`.`sys_menu` (`menu_id`,`parent_id`,`name`,`url`,`perms`,`type`,`icon`,`order_num`) VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 1),(2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1),(3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2),(4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3),(5, 1, 'SQL监控', 'http://localhost:8080/renren-fast/druid/sql.html', NULL, 1, 'sql', 4),(6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5),(7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0),(8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0),(9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0),(10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0),(11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0),(12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0),(13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0),(14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0),(15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0),(16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0),(17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0),(18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0),(19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0),(20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0),(21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0),(22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0),(23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0),(24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0),(25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0),(26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0),(27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6),(29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7),(30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6),(31, 0, '用户管理', '', '', 0, 'geren', 2),(32, 31, '项目负责人', '/user/promg', 'user:promg', 1, 'config', 0),(33, 0, '项目管理', '', '', 0, 'mudedi', 3),(34, 33, '项目管理', '/proj/mng', 'proj:mng', 1, 'mudedi', 0),(35, 32, '烽火_用户查看', '', 'fenhuo:user:list,fenhuo:user:info', 2, '', 0),(36, 32, '烽火_用户添加', '', 'fenhuo:user:add', 2, '', 0),(37, 32, '烽火_用户删除', '', 'fenhuo:user:del', 2, '', 0),(38, 32, '烽火_用户批量导入', '', 'fenhuo:user:batch:add', 2, '', 0),(39, 32, '烽火_添加角色', '', 'feihuo:user:role:add', 2, '', 0),(40, 31, '甲方负责人', '/user/Amng', 'user:Amng', 1, 'log', 2),(41, 31, '维护工程师', '/user/maintain', 'user:maintain', 1, 'daohang', 3),(42, 31, '注册用户', '/user/regist', 'user:regist', 1, 'editor', 4),(43, 31, '注册工程师', '/user/registEngineer', 'user:registEngineer', 1, 'shezhi', 5),(44, 40, '烽火_甲方_列表', '', 'fenhuo:fenhuousers:list', 2, '', 0),(45, 40, '烽火_甲方_信息', '', 'fenhuo:fenhuousers:info', 2, '', 0),(46, 40, '烽火_甲方_保存', '', 'fenhuo:fenhuousers:save,fenhuo:fenhuozabbixhost:authAndgethosts', 2, '', 0),(47, 40, '烽火_甲方_更新', '', 'fenhuo:fenhuousers:update', 2, '', 0),(48, 40, '烽火_甲方_删除', '', 'fenhuo:fenhuousers:delete', 2, '', 0),(49, 34, '烽火项目_保存', '', 'fenhuo:proj:save,fenhuo:fenhuoprojectinfo:save,fenhuo:proj:upload', 2, '', 0),(50, 34, '烽火项目_删除', '', 'fenhuo:proj:delete,fenhuo:fenhuoprojectinfo:delete', 2, '', 0),(51, 34, '烽火项目_激活', '', 'fenhuo:proj:active,fenhuo:fenhuoprojectinfo:active', 2, '', 0),(52, 34, '烽火项目_审批', '', 'fenhuo:proj:auth', 2, '', 0),(53, 34, '烽火项目_关闭', '', 'fenhuo:proj:close', 2, '', 0),(54, 34, '烽火项目_查看', '', 'fenhuo:fenhuoprojectinfo:list,fenhuo:fenhuoprojectinfo:info', 2, '', 0),(55, 34, '烽火项目_修改', '', 'fenhuo:fenhuoprojectinfo:update,fenhuo:fenhuousers:list', 2, '', 0),(56, 0, '日志', '/rec/oplog', 'rec:oplog:list', 1, 'menu', 4),(57, 56, '烽火日志删除', '', 'fenhuo:fenhuooperationlog:delete', 2, '', 0),(58, 56, '烽火日志查找', '', 'fenhuo:fenhuooperationlog:list', 2, '', 0),(59, 34, '烽火项目_未审核通过', '', 'fenhuo:fenhuoprojectinfo:faildaudit', 2, '', 0),(60, 0, '故障申报', '/rec/fenhuofault', 'fenhuo:rec:fenhuofault', 1, 'sql', 5),(61, 60, '烽火故障申报_添加', '', 'fenhuo:fenhuofault:save', 2, '', 0),(62, 60, '烽火故障申报_删除', '', 'fenhuo:fenhuofault:delete', 2, '', 0),(63, 60, '烽火故障申报_查看', '', 'fenhuo:fenhuofault:list,fenhuo:fenhuozabbixhost:list', 2, '', 0),(64, 0, '维护记录', '/rec/fenhuofaultdefend', 'fenhuo:fenhuofaultdefend:list', 1, 'zonghe', 6),(66, 64, '烽火维护记录_修改', '', 'fenhuo:fenhuofaultdefend:update', 2, '', 0),(67, 64, '烽火维护记录_删除', '', 'fenhuo:fenhuofaultdefend:delete', 2, '', 0),(68, 0, '平台统计', 'entry/viewall', 'entry:viewall,fenhuo:platform:list', 1, 'oss', 0),(69, 34, 'zabbix_登录', '', 'fenhuo:fenhuozabbixhost:authAndgethosts', 2, '', 0),(71, 64, '烽火维护记录_列表', '', 'fenhuo:fenhuofaultdefend:list', 2, '', 0),(72, 34, '烽火项目_相关文件下载', '', 'fenhuo:proj:relatedfile:download', 2, '', 0),(73, 34, '烽火项目_相关文件列表', '', 'fenhuo:fenhuoprojectinfo:projectfilelist', 2, '', 0);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_oss` WRITE;
DELETE FROM `renren_fast`.`sys_oss`;
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_role` WRITE;
DELETE FROM `renren_fast`.`sys_role`;
INSERT INTO `renren_fast`.`sys_role` (`role_id`,`role_name`,`remark`,`create_user_id`,`create_time`) VALUES (1, '烽火账号', '', 1, '2020-04-29 14:45:44'),(7, '烽火_项目负责人', '', 1, '2020-04-29 23:20:40'),(8, '烽火_甲方负责人', '烽火项目角色-甲方负责人', 1, '2020-05-30 14:11:01'),(9, '烽火_维护工程师', '烽火项目角色-维护工程师', 1, '2020-05-30 14:49:32');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_role_menu` WRITE;
DELETE FROM `renren_fast`.`sys_role_menu`;
INSERT INTO `renren_fast`.`sys_role_menu` (`id`,`role_id`,`menu_id`) VALUES (276, 1, 31),(277, 1, 32),(278, 1, 35),(279, 1, 36),(280, 1, 37),(281, 1, 38),(282, 1, 39),(283, 1, 40),(284, 1, 44),(285, 1, 45),(286, 1, 46),(287, 1, 47),(288, 1, 48),(289, 1, 41),(290, 1, 42),(291, 1, 43),(292, 1, 33),(293, 1, 34),(294, 1, 49),(295, 1, 50),(296, 1, 51),(297, 1, 52),(298, 1, 53),(299, 1, 54),(300, 1, 55),(301, 1, 59),(302, 1, 56),(303, 1, 57),(304, 1, 58),(305, 1, 60),(306, 1, 61),(307, 1, 62),(308, 1, 63),(309, 1, 64),(310, 1, 66),(311, 1, 67),(312, 1, 68),(313, 1, -666666),(490, 7, 49),(491, 7, 50),(492, 7, 54),(493, 7, 55),(494, 7, 69),(495, 7, 72),(496, 7, 73),(497, 7, 56),(498, 7, 57),(499, 7, 58),(500, 7, 63),(501, 7, 71),(502, 7, -666666),(503, 7, 33),(504, 7, 34),(505, 7, 60),(506, 7, 64),(507, 8, 54),(508, 8, 72),(509, 8, 73),(510, 8, 60),(511, 8, 61),(512, 8, 62),(513, 8, 63),(514, 8, 64),(515, 8, 66),(516, 8, 67),(517, 8, 71),(518, 8, -666666),(519, 8, 33),(520, 8, 34),(521, 9, 54),(522, 9, 72),(523, 9, 73),(524, 9, 63),(525, 9, 66),(526, 9, 71),(527, 9, -666666),(528, 9, 33),(529, 9, 34),(530, 9, 60),(531, 9, 64);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_user` WRITE;
DELETE FROM `renren_fast`.`sys_user`;
INSERT INTO `renren_fast`.`sys_user` (`user_id`,`username`,`password`,`salt`,`email`,`mobile`,`status`,`create_user_id`,`create_time`) VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11'),(2, 'fenhuo', 'f46ee88c504b2196714d4e20b6ade939f1001f9524aca996e620cafb6cb26f98', 'mIMV4e2a40kfCo7XETzO', '616391592@qq.com', '11111111111', 1, 1, '2020-04-29 14:45:12');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_user_role` WRITE;
DELETE FROM `renren_fast`.`sys_user_role`;
INSERT INTO `renren_fast`.`sys_user_role` (`id`,`user_id`,`role_id`) VALUES (1, 2, 1);
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`sys_user_token` WRITE;
DELETE FROM `renren_fast`.`sys_user_token`;
INSERT INTO `renren_fast`.`sys_user_token` (`user_id`,`token`,`expire_time`,`update_time`) VALUES (1, '99a578b6fdcaa82cfe118479fba06198', '2020-07-17 21:57:37', '2020-07-17 09:57:37'),(2, 'ed953fd0884c91111ce8539edbe50f68', '2020-07-17 22:31:42', '2020-07-17 10:31:42');
UNLOCK TABLES;
COMMIT;
BEGIN;
LOCK TABLES `renren_fast`.`tb_user` WRITE;
DELETE FROM `renren_fast`.`tb_user`;
INSERT INTO `renren_fast`.`tb_user` (`user_id`,`username`,`mobile`,`password`,`create_time`) VALUES (1, 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
UNLOCK TABLES;
COMMIT;
