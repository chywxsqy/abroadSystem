

SET NAMES utf8 ;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(8) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE COMMENT '昵称',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `addtime` datetime(0) NOT NULL COMMENT '注册时间',
  `comment` tinyint(1) NOT NULL DEFAULT 0 COMMENT '为0可以发言，1被禁言',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-普通用户， 1-超管，2-版主',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0-未激活; 1-已激活;',
  `sex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '性别：0保密；1男；2女',
  `qq` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  COMMENT 'qq号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL UNIQUE COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '电话',
  `activation_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '激活码',

  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'avatar.jpg' COMMENT '头像',
--   `posts_num` int(8) NOT NULL DEFAULT 0 COMMENT '发微博的数量',
--   `follows_num` int(8) NOT NULL DEFAULT 0 COMMENT '关注数量',
--   `fans_num` int(8) NOT NULL DEFAULT 0 COMMENT '粉丝数量',

  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '学校',
  `gpa` double(4, 2) NOT NULL DEFAULT 0.00 COMMENT 'gpa',
  `sat` int(8) NOT NULL DEFAULT 0 COMMENT 'sat',
  `ielts` double(6, 2) NOT NULL DEFAULT 0.00 COMMENT '雅思',
  `toefl` int(8) NOT NULL DEFAULT 0 COMMENT '托福',
--   `recommend` json NOT NULL COMMENT '意向院校，里面是一个json数组，长度为150，下标为学校的qs排名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 微博-用户相关




DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `college_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '学校中文名，不重复',
  `college_e_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '学校英文名，不重复',
  `country` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '国家',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  COMMENT '地址',
  `qs_rank` int(8) NOT NULL COMMENT 'qs排名',
  `rate` double(4, 2) NOT NULL COMMENT '录取率',
  `local_rank_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '本国排名标准',
  `local_rank` int(8) NOT NULL COMMENT '本国排名',
--   `hot_major` json NOT NULL COMMENT '热门专业',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '图标',
  `introduce` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '学校介绍',
--   `sum` int(8) NOT NULL COMMENT '总学生数',
--   `undergraduate` int(8) NOT NULL DEFAULT 0 COMMENT '本科学生数',
--   `graduate` int(8) NOT NULL DEFAULT 0 COMMENT '研究生学生数',
--   `student_staff_ratio` double(4, 2) NOT NULL COMMENT '师生比例',
--   `undergraduate_international_proportion` double(4, 2) NOT NULL COMMENT '国际生比例(本科)',
--   `graduate_international_proportion` double(4, 2) NOT NULL COMMENT '国际生比例(研究生)',
  `ea` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'EA申请截止日期',
  `rd` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'RD申请截止日期',
  `transfer` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '转学申请截止日期',
--   `undergraduate_gpa` double(4, 2) NOT NULL DEFAULT 6.00 COMMENT '本科平均GPA分数',
--   `sat` int(8) NOT NULL DEFAULT 9999 COMMENT '本科SAT分数',
--   `undergraduate_language` double(6, 2) NOT NULL DEFAULT 6.00 COMMENT '本科外语录取最低分',
--   `graduate_gpa` double(4, 2) NOT NULL COMMENT '研究生平均GPA分数',
--   `graduate_language` double(6, 2) NOT NULL COMMENT '研究生外语最低分',
--   `undergraduate_document` json NOT NULL COMMENT '本科生申请材料',
--   `graduate_document` json NOT NULL COMMENT '研究生申请材料',
--   `profession` json NOT NULL COMMENT '专业设置',
  `tuition_fee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  COMMENT '学费',
  `living_fee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  COMMENT '生活费',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `college_name`(`college_name`) USING BTREE,
  UNIQUE INDEX `college_e_name`(`college_e_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 54 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- 学校link