

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



-- 论坛相关


DROP TABLE IF EXISTS `discuss_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `discuss_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(8)  NOT NULL COMMENT '以后再加外键;',
  `college_id` int(8) NOT NULL DEFAULT 0 COMMENT '以后再加外键,0表示跟学校无关的帖子;',
--   `major_id` int(8) UNSIGNED NOT NULL,COMMENT '以后再加外键;',
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '0-普通; 1-置顶;',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0-正常; 1-精华; 2-拉黑;',
  `create_time` timestamp NULL DEFAULT NULL,
  `comment_count` int(11) NOT NULL DEFAULT 0,
  `score` double NOT NULL DEFAULT 0.0,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `entity_type` int(11) DEFAULT NULL,
  `entity_id` int(11) DEFAULT NULL,
  `target_id` int(11) DEFAULT NULL,
  `content` text,
  `status` int(11) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`) /*!80000 INVISIBLE */,
  KEY `index_entity_id` (`entity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert  into `user`(`id`,`username`,`password`,`addtime`,`comment`,`type`,`status`,`sex`,`qq`,`email`,`phone`,`activation_code`,`avatar`,`college`,`gpa`,`sat`,`ielts`,`toefl`) values (18,'crea','202cb962ac59075b964b07152d234b70','2021-06-08 09:24:10',0,1,1,1,'45557891','Lanjun1998@163.com','1839999123123','ff65951e9fb9422bafe009a185509645','http://creasbucket.oss-cn-shanghai.aliyuncs.com/project4_header/th.jpg',NULL,3.99,1500,6.50,110);

insert  into `discuss_post`(`id`,`user_id`,`college_id`,`title`,`content`,`type`,`status`,`create_time`,`comment_count`,`score`) values (1,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-08 18:09:35',0,0),(2,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 07:54:12',0,0),(3,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:17',0,0),(4,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(5,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(6,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(7,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(8,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(9,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(10,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(11,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(12,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(13,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(14,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(15,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(16,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(17,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(18,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(19,18,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(20,19,0,'测试帖子hello','第一条帖子',1,0,'2021-06-09 08:30:18',0,0),(21,19,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0),(23,18,0,'测试发帖','这是一条测试发帖啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊阿瓦我',0,0,'2021-06-09 09:25:07',0,0),(25,19,0,'测试帖子hello','第一条帖子',0,0,'2021-06-09 08:30:18',0,0);

