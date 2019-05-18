/*
Navicat MySQL Data Transfer

Source Server         : 47.106.183.43
Source Server Version : 50725
Source Host           : 47.106.183.43:3306
Source Database       : zhugeliang

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-05-18 00:47:57
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
INSERT INTO `arrange_subject` VALUES ('1558011692496', '-1', '计算机', '8:20-9:55', null, null);
INSERT INTO `arrange_subject` VALUES ('1558015889020', '-1', '大地方', '多舒服啊阿斯蒂芬', null, null);

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
INSERT INTO `classinfo` VALUES ('1557981126523', '18物理', '大二', '1557663144328', '1557971470636', '地方');
INSERT INTO `classinfo` VALUES ('1557981146585', '爱的精灵', '啊打发', '1557663144328', '1557971470636', '阿发');
INSERT INTO `classinfo` VALUES ('1557981167193', '17计本', '大二', '-1', '-1', '大数据');

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
INSERT INTO `classroom` VALUES ('1557999456228', '17计本二班', '32205', '2', '无');
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
INSERT INTO `department` VALUES ('1557966595790', '大数据工程学院', null, '2班');
INSERT INTO `department` VALUES ('1557969002118', '法国公司的', null, '反攻倒算');

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
INSERT INTO `major` VALUES ('1557662829688', '开卷考', '阿飞', '145315');
INSERT INTO `major` VALUES ('1557663144328', '阿道夫', '出生', '啊哈');
INSERT INTO `major` VALUES ('1557760046653', '收到', '-1', '地方');

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

-- ----------------------------
-- Table structure for `parents`
-- ----------------------------
DROP TABLE IF EXISTS `parents`;
CREATE TABLE `parents` (
  `id` varchar(255) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `home_addr` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of parents
-- ----------------------------
INSERT INTO `parents` VALUES ('1557672446342', '65213', '凯里市', '1853');
INSERT INTO `parents` VALUES ('1557673419486', '阿飞', '士大夫', '倒萨');
INSERT INTO `parents` VALUES ('1557981282074', '啊说法', '发生', '阿斯弗');

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
INSERT INTO `school` VALUES ('1557982238239', '凯里学院', '95001', '贵州省黔东南州凯里市');
INSERT INTO `school` VALUES ('1557982250476', '贵州大学', '95002', '贵州省贵阳市');
INSERT INTO `school` VALUES ('1557982259992', null, '啊手动阀', '阿凡达 ');

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
INSERT INTO `stu_pare` VALUES ('1557729784915', '-1', '-1', '卡拉');
INSERT INTO `stu_pare` VALUES ('1557980425493', '1557658708847', '1557672446342', '地方');
INSERT INTO `stu_pare` VALUES ('1557980439818', '1557832612148', '1557672446342', '的思考啦啦啦');

-- ----------------------------
-- Table structure for `stu_register`
-- ----------------------------
DROP TABLE IF EXISTS `stu_register`;
CREATE TABLE `stu_register` (
  `id` varchar(255) NOT NULL,
  `reg_time` date DEFAULT NULL,
  `addr` int(11) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `studey_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_register
-- ----------------------------

-- ----------------------------
-- Table structure for `stu_study`
-- ----------------------------
DROP TABLE IF EXISTS `stu_study`;
CREATE TABLE `stu_study` (
  `id` varchar(255) NOT NULL,
  `studey_id` varchar(255) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stu_study
-- ----------------------------
INSERT INTO `stu_study` VALUES ('1558056329967', '-1', '0', 'saa');
INSERT INTO `stu_study` VALUES ('1558056803004', '-1', '3', '打发');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` varchar(255) NOT NULL,
  `no` varchar(255) DEFAULT NULL,
  `clas` varchar(255) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1557631216472', '95001', '萨弗迪', '0');
INSERT INTO `student` VALUES ('1557631359694', '95002', '士大夫', '1');
INSERT INTO `student` VALUES ('1557646605205', '123456', '123456', '0');
INSERT INTO `student` VALUES ('1557658708847', '李华', '17班', '1');
INSERT INTO `student` VALUES ('1557672590866', '154', '23156', '0');
INSERT INTO `student` VALUES ('1557832612148', '95006', '18物理', '0');

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
INSERT INTO `subject` VALUES ('1558005717302', '计算机', '嘿嘿');
INSERT INTO `subject` VALUES ('1558005785991', '英语', '不会');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` varchar(255) NOT NULL,
  `no` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1557673047426', '10086', 'heihei', '110');
INSERT INTO `teacher` VALUES ('1557673133618', '10087', '好莱坞', '18228547532');
INSERT INTO `teacher` VALUES ('1557971470636', '加快立法', '地方萨芬', '地方');
INSERT INTO `teacher` VALUES ('1557980315786', '20145411', '3251454', '182245682');

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
INSERT INTO `user` VALUES ('1557747536071', 'skk', '0', 'das', 'df', '2', '0', '1555138505019', null);
INSERT INTO `user` VALUES ('1557756391317', '李颖鹏', '0', '123456', '20001105', '2', '0', '1555138505019', null);
INSERT INTO `user` VALUES ('1557757131854', '罗兴峰', '0', '123456', '2000/08/05', '3', '0', '1555138505019', null);
INSERT INTO `user` VALUES ('1557759452707', 'lyp', '1', '123456', '20140202', '2', '0', '1555148650901', null);
INSERT INTO `user` VALUES ('admin', 'admin', '0', 'admin', '20001010', '1', '0', '1555138505019', null);

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
