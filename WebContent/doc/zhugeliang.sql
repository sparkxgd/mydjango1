/*
Navicat MySQL Data Transfer

Source Server         : 47.106.183.43
Source Server Version : 50725
Source Host           : 47.106.183.43:3306
Source Database       : zhugeliang

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-19 19:39:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `arrange_subject`
-- ----------------------------
DROP TABLE IF EXISTS `arrange_subject`;
CREATE TABLE `arrange_subject` (
  `id` varchar(255) NOT NULL,
  `teacher` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `class_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `classid` varchar(255) DEFAULT NULL,
  `classroom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arrange_subject
-- ----------------------------
INSERT INTO `arrange_subject` VALUES ('1558011692496', '1558233381471', '计算机', '8:20-9:55', '1558232124945', '32206');
INSERT INTO `arrange_subject` VALUES ('1558015889020', '-1', '大地方', '20010415', '1558232124945', '1557999456228');
INSERT INTO `arrange_subject` VALUES ('1558149045219', '1558233381471', '物理', '8：20-9：55', '1558232124945', '1557999814317');

-- ----------------------------
-- Table structure for `baidugroup`
-- ----------------------------
DROP TABLE IF EXISTS `baidugroup`;
CREATE TABLE `baidugroup` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of baidugroup
-- ----------------------------
INSERT INTO `baidugroup` VALUES ('1558107329739', '总管');
INSERT INTO `baidugroup` VALUES ('test', '测试组');

-- ----------------------------
-- Table structure for `classinfo`
-- ----------------------------
DROP TABLE IF EXISTS `classinfo`;
CREATE TABLE `classinfo` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `major_id` varchar(255) DEFAULT NULL,
  `headmaster` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classinfo
-- ----------------------------
INSERT INTO `classinfo` VALUES ('1558232124945', '17计本班', '大二', '1558231707603', '1558233381471', '无');
INSERT INTO `classinfo` VALUES ('1558232536105', '17英语2', '大二', '1558232037901', '1558233381471', '无');
INSERT INTO `classinfo` VALUES ('1558234624747', 'kuid', 'as', '1558232015706', '1558233381471', '地方');

-- ----------------------------
-- Table structure for `classroom`
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classroom
-- ----------------------------
INSERT INTO `classroom` VALUES ('1557999382305', '17计本2', '32206', '1', '上课');
INSERT INTO `classroom` VALUES ('1557999456228', '17计本二班', '32205', '2', '有');
INSERT INTO `classroom` VALUES ('1557999814317', '17数媒', '32504', '0', '正常');

-- ----------------------------
-- Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` varchar(255) NOT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of config
-- ----------------------------

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1558231396464', '大数据工程学院', '1558230649261', '3栋教学楼');
INSERT INTO `department` VALUES ('1558231422539', '外国语学院', '1558230649261', '4号教学楼');
INSERT INTO `department` VALUES ('1558256539611', '美术与设计学院', '1558230649261', '艺术楼');

-- ----------------------------
-- Table structure for `major`
-- ----------------------------
DROP TABLE IF EXISTS `major`;
CREATE TABLE `major` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of major
-- ----------------------------
INSERT INTO `major` VALUES ('1558231707603', '计算机科学与技术', '1558231396464', '42404');
INSERT INTO `major` VALUES ('1558232015706', '物联网', '1558231396464', '无');
INSERT INTO `major` VALUES ('1558232037901', '英语', '1558231422539', '无');

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` varchar(255) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `release_time` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `reading` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1558168285971', '你好', '你真棒', '-1', '2019-05-18 16:31:25.971', null, '0', '阿斯弗');
INSERT INTO `news` VALUES ('1558168476559', '最棒', '你最棒帮', 'admin', '2019-05-19 19:39:02.774', null, '0', '嘿嘿');
INSERT INTO `news` VALUES ('1558262853802', 'adfaf', 'sdf', '1558248031373', '2019-05-19 19:28:29.42', null, '0', 'xiaohao');
INSERT INTO `news` VALUES ('1558264565107', '阿德', '大', '-1', '2019-05-19 19:16:05.107', null, '0', '大');

-- ----------------------------
-- Table structure for `parents`
-- ----------------------------
DROP TABLE IF EXISTS `parents`;
CREATE TABLE `parents` (
  `id` varchar(255) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `home_addr` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parents
-- ----------------------------
INSERT INTO `parents` VALUES ('1557672446342', '65213', '凯里市', '1853', null);
INSERT INTO `parents` VALUES ('1557673419486', '阿飞', '士大夫', '倒萨', null);
INSERT INTO `parents` VALUES ('1557981282074', '啊说法', '发生', '阿斯弗', null);
INSERT INTO `parents` VALUES ('1558248031373', '602938', '贵州', '有', null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1555138505019', 'admin', '{\"c101\":1,\"c100\":1,\"c103\":1,\"c102\":1,\"c104\":1}');
INSERT INTO `role` VALUES ('1555148650901', '超级管理员', '{\"c101\":1,\"c100\":1,\"c103\":0,\"c102\":0,\"c104\":1}');

-- ----------------------------
-- Table structure for `school`
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `school` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES ('1558230471319', '贵州大学', '95001', '贵州省贵阳市');
INSERT INTO `school` VALUES ('1558230649261', '凯里学院', '95002', '贵州省凯里市');
INSERT INTO `school` VALUES ('1558231182645', '贵州师范大学', '95003', '贵州省贵阳市花溪区');
INSERT INTO `school` VALUES ('1558231220717', '贵州财经大学', '95004', '贵州省贵阳市');
INSERT INTO `school` VALUES ('1558256625691', '贵州医科大学', '95005', '贵阳花溪');

-- ----------------------------
-- Table structure for `stu_pare`
-- ----------------------------
DROP TABLE IF EXISTS `stu_pare`;
CREATE TABLE `stu_pare` (
  `id` varchar(255) NOT NULL,
  `pare_id` varchar(255) DEFAULT NULL,
  `stu_id` varchar(255) DEFAULT NULL,
  `relation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_pare
-- ----------------------------
INSERT INTO `stu_pare` VALUES ('1557729784915', '1557673419486', '1557631359694', '母女');
INSERT INTO `stu_pare` VALUES ('1557980425493', '1557673419486', '1557658708847', '地方');
INSERT INTO `stu_pare` VALUES ('1557980439818', '1557673419486', '1557672590866', '的思考啦啦啦');

-- ----------------------------
-- Table structure for `stu_register`
-- ----------------------------
DROP TABLE IF EXISTS `stu_register`;
CREATE TABLE `stu_register` (
  `id` varchar(255) NOT NULL,
  `reg_time` date DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `stu_study_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_register
-- ----------------------------
INSERT INTO `stu_register` VALUES ('1558164766053', '2000-12-14', '5', '0', '1', null);
INSERT INTO `stu_register` VALUES ('1558165083930', '1996-02-04', '凯里学院', '0', '有', null);

-- ----------------------------
-- Table structure for `stu_registernew`
-- ----------------------------
DROP TABLE IF EXISTS `stu_registernew`;
CREATE TABLE `stu_registernew` (
  `id` varchar(255) NOT NULL,
  `reg_time` date DEFAULT NULL,
  `addr` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `tcsuid` varchar(255) DEFAULT NULL,
  `stuid` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `week` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_registernew
-- ----------------------------
INSERT INTO `stu_registernew` VALUES ('1558164766053', '2000-12-14', '5', '0', '1', null, null, null, null);
INSERT INTO `stu_registernew` VALUES ('1558165083930', '1996-02-04', '凯里学院', '0', '有', null, null, null, null);

-- ----------------------------
-- Table structure for `stu_study`
-- ----------------------------
DROP TABLE IF EXISTS `stu_study`;
CREATE TABLE `stu_study` (
  `id` varchar(255) NOT NULL,
  `studey_id` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `stu_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_study
-- ----------------------------
INSERT INTO `stu_study` VALUES ('1558056329967', '-1', '0', 'saa', null);
INSERT INTO `stu_study` VALUES ('1558056803004', '-1', '3', '打发', null);
INSERT INTO `stu_study` VALUES ('1558237214975', null, '1', 'afa', null);
INSERT INTO `stu_study` VALUES ('1558237344289', null, '0', 'sd', null);
INSERT INTO `stu_study` VALUES ('1558237664449', null, '0', 'wu', null);
INSERT INTO `stu_study` VALUES ('1558249172314', '1558237740077', '0', 'wu', null);
INSERT INTO `stu_study` VALUES ('1558258424176', '1558237740077', '3', '考试', null);

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(255) NOT NULL,
  `no` varchar(255) DEFAULT NULL,
  `clas` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1557631216472', '95001', '萨弗迪', '0', null);
INSERT INTO `student` VALUES ('1557631359694', '95002', '士大夫', '1', null);
INSERT INTO `student` VALUES ('1557646605205', '123456', '1557981146585', '0', null);
INSERT INTO `student` VALUES ('1557658708847', '李华', '17班', '1', null);
INSERT INTO `student` VALUES ('1557672590866', '154', '23156', '0', null);
INSERT INTO `student` VALUES ('1557832612148', '95006', '18物理', '0', null);
INSERT INTO `student` VALUES ('1558229916625', null, null, null, null);
INSERT INTO `student` VALUES ('1558235731743', null, null, null, null);
INSERT INTO `student` VALUES ('1558258243286', null, null, null, '1558258242115');

-- ----------------------------
-- Table structure for `study`
-- ----------------------------
DROP TABLE IF EXISTS `study`;
CREATE TABLE `study` (
  `id` varchar(255) NOT NULL,
  `start_time` date DEFAULT NULL,
  `end_time` date DEFAULT NULL,
  `teacher` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of study
-- ----------------------------
INSERT INTO `study` VALUES ('1558237740077', '2000-11-11', '2000-11-11', '1558233381471', '-1', '0', 'kan');

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1558005717302', '计算机', '无');
INSERT INTO `subject` VALUES ('1558005785991', '英语', '不会');
INSERT INTO `subject` VALUES ('1558256993541', 'python', 'ai');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` varchar(255) NOT NULL,
  `no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `userid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1558233381471', 'sdkldjf', '撒地方', '18228547532', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `birth` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1558229916625', '李颖鹏', '1', '123456', '2019-05-19', '1', '0', '1555148650901', '1558229915257.jpg');
INSERT INTO `user` VALUES ('1558233381471', '梁建波', '0', '123456', '2019-05-19', '2', '0', '1555148650901', '1558233377281.jpg');
INSERT INTO `user` VALUES ('1558235731743', 'wts', '1', '123456', '1998-05-19', '1', '0', '1555148650901', '1558235721329.jpg');
INSERT INTO `user` VALUES ('1558248031373', 'wondows', '1', '123456', '2019-05-16', '3', '0', '1555148650901', '1558248021047.jpg');
INSERT INTO `user` VALUES ('1558258242115', '肖光鼎', '1', null, '2019-05-19', '1', '0', '1555148650901', '1558257928962.jpg');
INSERT INTO `user` VALUES ('admin', 'admin', '0', '123456', '20001010', '1', '1', '1555138505019', null);

-- ----------------------------
-- Table structure for `userface`
-- ----------------------------
DROP TABLE IF EXISTS `userface`;
CREATE TABLE `userface` (
  `id` varchar(255) NOT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `user_info` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userface
-- ----------------------------
INSERT INTO `userface` VALUES ('xgd', 'test', null, null);
