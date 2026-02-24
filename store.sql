CREATE TABLE `admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `role` tinyint DEFAULT '2' COMMENT '角色：1-超级管理员 2-普通管理员',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_by` bigint DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_username` (`username`),
  KEY `idx_role` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';

CREATE TABLE `admin_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_id` bigint NOT NULL COMMENT '管理员ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_admin_permission` (`admin_id`,`permission_id`),
  KEY `idx_admin_id` (`admin_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员权限关联表';

CREATE TABLE `after_sale` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '售后ID',
  `after_sale_no` varchar(32) NOT NULL COMMENT '售后单号',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `exchange_order_id` bigint DEFAULT NULL COMMENT '关联交换订单ID',
  `user_id` bigint NOT NULL COMMENT '申请人ID',
  `type` tinyint NOT NULL COMMENT '售后类型：1-退款 2-退货退款 3-换货',
  `reason` varchar(500) NOT NULL COMMENT '申请原因',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待处理 1-卖家同意 2-卖家拒绝 3-平台仲裁中 4-已完成 5-已关闭',
  `evidence_images` json DEFAULT NULL COMMENT '凭证图片',
  `seller_response` varchar(500) DEFAULT NULL COMMENT '卖家回复',
  `arbitration_result` varchar(500) DEFAULT NULL COMMENT '仲裁结果',
  `arbitration_time` datetime DEFAULT NULL COMMENT '仲裁时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `after_sale_no` (`after_sale_no`),
  KEY `idx_after_sale_no` (`after_sale_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='售后表';

CREATE TABLE `announcement` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(200) NOT NULL COMMENT '公告标题',
  `content` text NOT NULL COMMENT '公告内容',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型：1-系统公告 2-活动通知 3-平台规则',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶：0-否 1-是',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-草稿 1-已发布 2-已下架',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';

CREATE TABLE `att_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `mor_start_time` time DEFAULT NULL COMMENT '上午上班时间',
  `mor_end_time` time DEFAULT NULL COMMENT '上午下班时间',
  `aft_start_time` time DEFAULT NULL COMMENT '下午上班时间',
  `aft_end_time` time DEFAULT NULL COMMENT '下午下班时间',
  `attendance_date` date NOT NULL COMMENT '考勤日期',
  `status` tinyint(1) DEFAULT NULL COMMENT '0正常，1迟到，2早退，3旷工，4休假',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=292 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工考勤表';

CREATE TABLE `att_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `days` int DEFAULT NULL COMMENT '休假天数',
  `type_num` tinyint(1) DEFAULT NULL COMMENT '休假类型',
  `status` tinyint(1) DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='请假表';

CREATE TABLE `att_overtime` (
  `id` int NOT NULL AUTO_INCREMENT,
  `salary_multiple` decimal(5,2) DEFAULT NULL COMMENT '工资倍数，如按照小时计算，就是员工平均小时工资乘以倍数',
  `bonus` decimal(10,3) DEFAULT NULL COMMENT '加班奖金',
  `type_num` int DEFAULT NULL COMMENT '加班类型',
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `count_type` tinyint(1) DEFAULT NULL COMMENT '0小时，1天，默认0，计数加班工资的类型',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_time_off` tinyint(1) DEFAULT '0' COMMENT '0不补休，1补休，默认0',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='加班表';

CREATE TABLE `att_staff_leave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `days` int DEFAULT NULL COMMENT '请假的天数',
  `type_num` int DEFAULT NULL COMMENT '请假类型id',
  `start_date` date DEFAULT NULL COMMENT '请假的开始日期',
  `status` tinyint(1) DEFAULT '0' COMMENT '0未审核，1审核通过，2驳回，3撤销',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工请假表';

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) NOT NULL COMMENT '分类名称',
  `description` varchar(200) DEFAULT NULL COMMENT '分类描述',
  `sort` int DEFAULT '0' COMMENT '排序值（越小越靠前）',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-启用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `idx_status` (`status`),
  KEY `idx_sort` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='社区板块分类表';

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` bigint NOT NULL COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `parent_id` bigint DEFAULT NULL COMMENT '父评论ID',
  `reply_to_user_id` bigint DEFAULT NULL COMMENT '回复目标用户ID',
  `content` varchar(1000) NOT NULL COMMENT '评论内容',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-正常 1-屏蔽',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

CREATE TABLE `coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `type` tinyint NOT NULL COMMENT '类型：1-满减券 2-折扣券',
  `condition_amount` decimal(10,2) DEFAULT NULL COMMENT '满减条件',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '减免金额',
  `discount_rate` decimal(3,2) DEFAULT NULL COMMENT '折扣率',
  `total_count` int NOT NULL COMMENT '发放总量',
  `remaining_count` int NOT NULL COMMENT '剩余数量',
  `per_limit` int NOT NULL DEFAULT '1' COMMENT '每人限领',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-下架 1-上架',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券表';

CREATE TABLE `credit_appeal` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申诉ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `credit_record_id` bigint DEFAULT NULL COMMENT '关联信用记录ID',
  `reason` varchar(500) NOT NULL COMMENT '申诉原因',
  `evidence` text COMMENT '申诉材料（JSON数组）',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待处理 1-已通过 2-已驳回',
  `feedback` varchar(500) DEFAULT NULL COMMENT '处理反馈',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信用申诉表';

CREATE TABLE `credit_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `change_score` int NOT NULL COMMENT '变更分数',
  `before_score` int NOT NULL COMMENT '变更前分数',
  `after_score` int NOT NULL COMMENT '变更后分数',
  `reason` varchar(500) DEFAULT NULL COMMENT '变更原因',
  `type` varchar(50) DEFAULT NULL COMMENT '变更类型：violation-违规 appeal-申诉 manual-手动调整',
  `business_id` bigint DEFAULT NULL COMMENT '关联业务ID（订单ID、申诉ID等）',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_type` (`type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信用记录表';

CREATE TABLE `exchange_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '交换订单ID',
  `exchange_no` varchar(32) NOT NULL COMMENT '交换单号',
  `initiator_id` bigint NOT NULL COMMENT '发起方ID',
  `receiver_id` bigint NOT NULL COMMENT '接收方ID',
  `initiator_product_id` bigint NOT NULL COMMENT '发起方商品ID',
  `receiver_product_id` bigint NOT NULL COMMENT '接收方商品ID',
  `initiator_product_name` varchar(200) NOT NULL COMMENT '发起方商品名称',
  `receiver_product_name` varchar(200) NOT NULL COMMENT '接收方商品名称',
  `price_diff` decimal(10,2) DEFAULT '0.00' COMMENT '补差价金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待对方确认 1-待付款 2-待双方发货 3-待确认收货 4-已完成 5-已取消 6-仲裁中',
  `initiator_logistics_no` varchar(50) DEFAULT NULL COMMENT '发起方物流单号',
  `receiver_logistics_no` varchar(50) DEFAULT NULL COMMENT '接收方物流单号',
  `initiator_ship_time` datetime DEFAULT NULL COMMENT '发起方发货时间',
  `receiver_ship_time` datetime DEFAULT NULL COMMENT '接收方发货时间',
  `initiator_receive_time` datetime DEFAULT NULL COMMENT '发起方收货时间',
  `receiver_receive_time` datetime DEFAULT NULL COMMENT '接收方收货时间',
  `pay_time` datetime DEFAULT NULL COMMENT '差价付款时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `exchange_no` (`exchange_no`),
  KEY `idx_exchange_no` (`exchange_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='交换订单表';

CREATE TABLE `merchant_apply` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `company_name` varchar(100) NOT NULL COMMENT '公司名称',
  `business_license` varchar(50) NOT NULL COMMENT '营业执照号',
  `business_license_url` varchar(255) NOT NULL COMMENT '营业执照URL',
  `brand` varchar(100) DEFAULT NULL COMMENT '品牌名称',
  `brand_authorization_url` varchar(255) DEFAULT NULL COMMENT '品牌授权书URL',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) NOT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待审核 1-已通过 2-已驳回',
  `apply_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家申请表';

CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint DEFAULT NULL COMMENT '发送者ID（null表示系统）',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `type` tinyint NOT NULL COMMENT '消息类型：1-系统通知 2-订单消息 3-互动消息 4-私信',
  `title` varchar(200) DEFAULT NULL COMMENT '消息标题',
  `content` text NOT NULL COMMENT '消息内容',
  `related_id` bigint DEFAULT NULL COMMENT '关联ID',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读：0-否 1-是',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_is_read` (`is_read`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';

CREATE TABLE `operation_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operator_name` varchar(100) DEFAULT NULL COMMENT '操作人姓名',
  `operation` varchar(50) NOT NULL COMMENT '操作类型',
  `target_id` bigint DEFAULT NULL COMMENT '操作目标ID',
  `target_type` varchar(50) DEFAULT NULL COMMENT '操作目标类型',
  `details` text COMMENT '操作详情',
  `result` varchar(20) DEFAULT NULL COMMENT '操作结果',
  `reason` varchar(500) DEFAULT NULL COMMENT '操作原因',
  `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
  `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`),
  KEY `idx_target` (`target_id`,`target_type`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志表';

CREATE TABLE `order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(32) NOT NULL COMMENT '订单号',
  `buyer_id` bigint NOT NULL COMMENT '买家ID',
  `seller_id` bigint NOT NULL COMMENT '卖家ID',
  `seller_type` tinyint NOT NULL COMMENT '卖家类型：1-商家 2-个人',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) NOT NULL COMMENT '商品名称',
  `product_image` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `spec_id` bigint DEFAULT NULL COMMENT '规格ID',
  `spec_desc` varchar(100) DEFAULT NULL COMMENT '规格描述',
  `price` decimal(10,2) NOT NULL COMMENT '成交单价',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '购买数量',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `order_type` tinyint NOT NULL DEFAULT '1' COMMENT '订单类型：1-销售订单 2-交换订单',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待付款 1-待发货 2-待收货 3-已完成 4-退款中 5-已退款 6-已取消',
  `logistics_no` varchar(50) DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(50) DEFAULT NULL COMMENT '物流公司',
  `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `receiver_address` varchar(200) NOT NULL COMMENT '收货地址',
  `buyer_remark` varchar(500) DEFAULT NULL COMMENT '买家备注',
  `seller_remark` varchar(500) DEFAULT NULL COMMENT '卖家备注',
  `close_reason` varchar(200) DEFAULT NULL COMMENT '关闭原因',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `ship_time` datetime DEFAULT NULL COMMENT '发货时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收货时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_buyer_id` (`buyer_id`),
  KEY `idx_seller_id` (`seller_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

CREATE TABLE `per_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单路径',
  `parent_id` int NOT NULL DEFAULT '0' COMMENT '父菜单id，0代表根菜单，默认0',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='菜单表';

CREATE TABLE `per_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `code` varchar(20) CHARACTER SET utf16 COLLATE utf16_general_ci DEFAULT NULL COMMENT '角色编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工角色表';

CREATE TABLE `per_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='角色菜单关系表';

CREATE TABLE `per_staff_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `role_id` int DEFAULT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0禁用，1正常，默认1',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工角色关系表';

CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `code` varchar(100) NOT NULL COMMENT '权限代码',
  `type` varchar(20) DEFAULT NULL COMMENT '类型：menu-菜单 button-按钮 api-接口',
  `parent_code` varchar(100) DEFAULT NULL COMMENT '父权限代码',
  `path` varchar(200) DEFAULT NULL COMMENT '路径',
  `component` varchar(200) DEFAULT NULL COMMENT '组件',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用 1-正常',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `idx_code` (`code`),
  KEY `idx_parent` (`parent_code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';

CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `user_id` bigint NOT NULL COMMENT '作者ID',
  `title` varchar(200) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型：1-普通帖子 2-评测',
  `equipment_id` bigint DEFAULT NULL COMMENT '关联装备ID（评测时）',
  `images` json DEFAULT NULL COMMENT '图片数组',
  `category` varchar(50) DEFAULT NULL COMMENT '板块分类',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待审核 1-已发布 2-已屏蔽 3-已删除',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览数',
  `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
  `comment_count` int NOT NULL DEFAULT '0' COMMENT '评论数',
  `favorite_count` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `is_essence` tinyint NOT NULL DEFAULT '0' COMMENT '是否加精：0-否 1-是',
  `is_top` tinyint NOT NULL DEFAULT '0' COMMENT '是否置顶：0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_is_essence` (`is_essence`),
  FULLTEXT KEY `ft_content` (`title`,`content`) /*!50100 WITH PARSER `ngram` */
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子表';

CREATE TABLE `private_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '私信ID',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `content` text NOT NULL COMMENT '内容',
  `is_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读：0-否 1-是',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `idx_sender_receiver` (`sender_id`,`receiver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='私信表';

CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `user_id` bigint NOT NULL COMMENT '卖家ID',
  `user_type` tinyint NOT NULL COMMENT '卖家类型：1-商家 2-个人',
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `category` varchar(50) NOT NULL COMMENT '分类',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `condition` varchar(20) DEFAULT NULL COMMENT '成色：全新/99新/95新等',
  `images` json NOT NULL COMMENT '图片URL数组',
  `trade_type` tinyint NOT NULL DEFAULT '1' COMMENT '交易类型：1-仅出售 2-可交换',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-待审核 1-上架 2-下架 3-违规下架',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `favorite_count` int NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `sale_count` int NOT NULL DEFAULT '0' COMMENT '销量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shelf_time` datetime DEFAULT NULL COMMENT '上架时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_price` (`price`),
  FULLTEXT KEY `ft_name_desc` (`name`,`description`) /*!50100 WITH PARSER `ngram` */
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';

CREATE TABLE `product_spec` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '规格ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `spec_name` varchar(50) NOT NULL COMMENT '规格名称（如：颜色、尺寸）',
  `spec_value` varchar(50) NOT NULL COMMENT '规格值',
  `stock` int NOT NULL DEFAULT '0' COMMENT '库存',
  `price` decimal(10,2) DEFAULT NULL COMMENT '规格价格（可为空，为空则使用商品价格）',
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品规格表';

CREATE TABLE `report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `reporter_id` bigint NOT NULL COMMENT '举报人ID',
  `reported_user_id` bigint NOT NULL COMMENT '被举报人ID',
  `target_type` tinyint NOT NULL COMMENT '举报类型：1-帖子 2-评论 3-商品',
  `target_id` bigint NOT NULL COMMENT '目标ID',
  `reason` varchar(200) NOT NULL COMMENT '举报原因',
  `description` text COMMENT '详细描述',
  `evidence_images` json DEFAULT NULL COMMENT '证据图片',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '处理状态：0-待处理 1-已处理 2-驳回',
  `handle_result` varchar(500) DEFAULT NULL COMMENT '处理结果',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='举报表';

CREATE TABLE `sal_salary` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `base_salary` decimal(10,3) DEFAULT NULL COMMENT '基础工资',
  `overtime_salary` decimal(10,3) DEFAULT NULL COMMENT '加班费',
  `subsidy` decimal(10,3) DEFAULT NULL COMMENT '生活补贴',
  `bonus` decimal(10,3) DEFAULT NULL COMMENT '奖金',
  `total_salary` decimal(10,3) DEFAULT NULL COMMENT '总工资',
  `late_deduct` decimal(10,3) DEFAULT NULL COMMENT '早退扣款',
  `leave_deduct` decimal(10,3) DEFAULT NULL COMMENT '休假扣款',
  `leave_early_deduct` decimal(10,3) DEFAULT NULL COMMENT '早退扣款',
  `absenteeism_deduct` decimal(10,3) DEFAULT NULL COMMENT '旷工扣款',
  `month` char(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '月份',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工工资表';

CREATE TABLE `sal_salary_deduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `type_num` int DEFAULT NULL COMMENT '扣款类型，0迟到，1早退，2旷工，3休假',
  `deduct` int NOT NULL DEFAULT '0' COMMENT '每次扣款金额',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='工资扣除表';

CREATE TABLE `shop` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '店铺ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `shop_name` varchar(100) NOT NULL COMMENT '店铺名称',
  `shop_logo` varchar(500) DEFAULT NULL COMMENT '店铺Logo',
  `shop_banner` varchar(500) DEFAULT NULL COMMENT '店铺横幅',
  `shop_desc` text COMMENT '店铺简介',
  `shop_address` varchar(200) DEFAULT NULL COMMENT '店铺地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `business_license` varchar(500) DEFAULT NULL COMMENT '营业执照',
  `brand_authorization` json DEFAULT NULL COMMENT '品牌授权书数组',
  `audit_status` tinyint NOT NULL DEFAULT '0' COMMENT '审核状态：0-待审核 1-已通过 2-已驳回',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '店铺状态：0-关闭 1-营业中',
  `score` decimal(3,2) DEFAULT '5.00' COMMENT '店铺评分',
  `sale_count` int NOT NULL DEFAULT '0' COMMENT '总销量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺表';

CREATE TABLE `soc_city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参保城市',
  `average_salary` decimal(10,3) DEFAULT NULL COMMENT '职工上年度平均月工资',
  `lower_salary` decimal(10,3) DEFAULT NULL COMMENT '职工上年度最低月工资',
  `soc_upper_limit` decimal(10,3) DEFAULT NULL COMMENT '职工社保缴纳基数上限',
  `soc_lower_limit` decimal(10,3) DEFAULT NULL COMMENT '职工社保缴纳基数下限',
  `hou_upper_limit` decimal(10,3) DEFAULT NULL COMMENT '公积金缴纳基数上限',
  `hou_lower_limit` decimal(10,3) DEFAULT NULL COMMENT '公积金缴纳基数下限',
  `per_pension_rate` decimal(6,3) DEFAULT NULL COMMENT '个人养老保险缴费比例',
  `com_pension_rate` decimal(6,3) DEFAULT NULL COMMENT '企业养老保险缴费比例',
  `per_medical_rate` decimal(6,3) DEFAULT NULL COMMENT '个人医疗保险缴费比例',
  `com_medical_rate` decimal(6,3) DEFAULT NULL COMMENT '企业医疗保险缴费比例',
  `per_unemployment_rate` decimal(6,3) DEFAULT NULL COMMENT '个人失业保险缴费比例',
  `com_unemployment_rate` decimal(6,3) DEFAULT NULL COMMENT '企业失业保险缴费比例',
  `com_maternity_rate` decimal(6,3) DEFAULT NULL COMMENT '企业生育保险缴费比例',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='参保城市表';

CREATE TABLE `soc_insurance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL COMMENT '城市id',
  `staff_id` int DEFAULT NULL COMMENT '员工id',
  `house_base` decimal(10,3) DEFAULT NULL COMMENT '公积金基数',
  `per_house_rate` decimal(6,3) DEFAULT NULL COMMENT '公积金个人缴纳比例',
  `per_house_pay` decimal(10,3) DEFAULT NULL COMMENT '公积金个人缴纳费用',
  `com_house_rate` decimal(6,3) DEFAULT NULL COMMENT '公积金企业缴纳比例',
  `com_house_pay` decimal(10,3) DEFAULT NULL COMMENT '公积金企业缴纳费用',
  `social_base` decimal(10,3) DEFAULT NULL COMMENT '社保基数',
  `com_social_pay` decimal(10,3) DEFAULT NULL COMMENT '社保企业缴纳费用',
  `per_social_pay` decimal(10,3) DEFAULT NULL COMMENT '社保个人缴纳费用',
  `com_injury_rate` decimal(6,3) DEFAULT NULL COMMENT '工伤保险企业缴纳比例',
  `social_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '社保备注',
  `house_remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公积金备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未支付，1已支付，2支付失败',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工五险一金表';

CREATE TABLE `sys_dept` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `mor_start_time` time DEFAULT NULL COMMENT '上午上班时间',
  `mor_end_time` time DEFAULT NULL COMMENT '上午下班时间',
  `aft_start_time` time DEFAULT NULL COMMENT '下午上班时间',
  `aft_end_time` time DEFAULT NULL COMMENT '下午下班时间',
  `total_work_time` decimal(3,1) DEFAULT NULL COMMENT '员工工作总时长',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门备注',
  `parent_id` int NOT NULL DEFAULT '0' COMMENT '父级部门id，0根部门',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0未删除，1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='部门表';

CREATE TABLE `sys_docs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件类型',
  `old_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件的原名称',
  `md5` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件md5信息',
  `size` bigint DEFAULT NULL COMMENT '文件大小KB',
  `staff_id` int DEFAULT NULL COMMENT '文件上传者id',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未删除，1已删除，默认为0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='文件表';

CREATE TABLE `sys_staff` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '员工id',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '员工编码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '员工姓名',
  `gender` tinyint(1) DEFAULT '0' COMMENT '性别，0男，1女，默认0',
  `pwd` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工密码',
  `avatar` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工头像',
  `birthday` date DEFAULT NULL COMMENT '员工生日',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工电话',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '员工备注',
  `dept_id` int DEFAULT NULL COMMENT '部门id',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '员工状态，0异常，1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0未删除，1已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='员工表';

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `role` tinyint DEFAULT '1' COMMENT '角色：1-普通用户 2-商家 3-管理员',
  `status` tinyint DEFAULT '1' COMMENT '状态：0-冻结 1-正常 -1-已注销',
  `merchant_status` tinyint DEFAULT '0' COMMENT '商家状态：0-待审核 1-正常 2-驳回',
  `credit_score` int DEFAULT '600' COMMENT '信用分',
  `verified` tinyint(1) DEFAULT '0' COMMENT '是否实名认证',
  `consignment_enabled` tinyint(1) DEFAULT '0' COMMENT '寄售功能是否开启',
  `consignment_expiry` datetime DEFAULT NULL COMMENT '寄售资质有效期',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone` (`phone`),
  KEY `idx_username` (`username`),
  KEY `idx_phone` (`phone`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`),
  KEY `idx_credit_score` (`credit_score`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

CREATE TABLE `user_address` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
  `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
  `province` varchar(50) NOT NULL COMMENT '省',
  `city` varchar(50) NOT NULL COMMENT '市',
  `district` varchar(50) NOT NULL COMMENT '区',
  `detail_address` varchar(200) NOT NULL COMMENT '详细地址',
  `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否默认：0-否 1-是',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户地址表';

CREATE TABLE `user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '领取记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0-未使用 1-已使用 2-已过期',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  `order_id` bigint DEFAULT NULL COMMENT '使用订单ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_coupon` (`user_id`,`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券表';

CREATE TABLE `user_equipment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '装备ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `name` varchar(100) NOT NULL COMMENT '装备名称',
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌',
  `model` varchar(100) DEFAULT NULL COMMENT '型号',
  `category` varchar(50) DEFAULT NULL COMMENT '分类',
  `purchase_date` date DEFAULT NULL COMMENT '购买日期',
  `usage_condition` varchar(20) DEFAULT NULL COMMENT '使用情况：全新/轻微/明显/磨损',
  `description` text COMMENT '描述',
  `images` json DEFAULT NULL COMMENT '图片URL数组',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户装备库表';

CREATE TABLE `user_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_product` (`user_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收藏表';

CREATE TABLE `user_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `target_type` tinyint NOT NULL COMMENT '目标类型：1-帖子 2-评论',
  `target_id` bigint NOT NULL COMMENT '目标ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '点赞时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_target` (`user_id`,`target_type`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='点赞表';

CREATE TABLE `user_verification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '认证ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
  `id_number` varchar(18) NOT NULL COMMENT '身份证号',
  `id_card_front` varchar(255) NOT NULL COMMENT '身份证正面照片URL',
  `id_card_back` varchar(255) NOT NULL COMMENT '身份证反面照片URL',
  `handheld_id_card` varchar(255) NOT NULL COMMENT '手持身份证照片URL',
  `equipment_credentials` text COMMENT '装备权属凭证（JSON数组）',
  `status` tinyint DEFAULT '0' COMMENT '状态：0-待审核 1-已通过 2-已驳回',
  `submit_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `verifier_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `expiry_date` datetime DEFAULT NULL COMMENT '资质有效期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户实名认证表';

CREATE TABLE `user_wish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '心愿ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `product_name` varchar(100) NOT NULL COMMENT '期望装备名称',
  `brand` varchar(50) DEFAULT NULL COMMENT '期望品牌',
  `category` varchar(50) DEFAULT NULL COMMENT '期望分类',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户心愿单表';

CREATE DATABASE `store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

create or replace
algorithm = UNDEFINED view `store`.`v_pending_after_sales` as
select
    `a`.`id` as `id`,
    `a`.`after_sale_no` as `after_sale_no`,
    `a`.`order_id` as `order_id`,
    `a`.`exchange_order_id` as `exchange_order_id`,
    `a`.`user_id` as `user_id`,
    `a`.`type` as `type`,
    `a`.`reason` as `reason`,
    `a`.`amount` as `amount`,
    `a`.`status` as `status`,
    `a`.`evidence_images` as `evidence_images`,
    `a`.`seller_response` as `seller_response`,
    `a`.`arbitration_result` as `arbitration_result`,
    `a`.`arbitration_time` as `arbitration_time`,
    `a`.`complete_time` as `complete_time`,
    `a`.`create_time` as `create_time`,
    `a`.`update_time` as `update_time`,
    `u`.`username` as `user_name`,
    `o`.`order_no` as `order_no`
from
    ((`store`.`after_sale` `a`
left join `store`.`user` `u` on
    ((`a`.`user_id` = `u`.`id`)))
left join `store`.`order` `o` on
    ((`a`.`order_id` = `o`.`id`)))
where
    (`a`.`status` in (0, 3))
order by
    (case
        when (`a`.`status` = 0) then 0
        else 1
    end),
    `a`.`create_time`;

create or replace
algorithm = UNDEFINED view `store`.`v_pending_reports` as
select
    `r`.`id` as `id`,
    `r`.`reporter_id` as `reporter_id`,
    `r`.`reported_user_id` as `reported_user_id`,
    `r`.`target_type` as `target_type`,
    `r`.`target_id` as `target_id`,
    `r`.`reason` as `reason`,
    `r`.`description` as `description`,
    `r`.`evidence_images` as `evidence_images`,
    `r`.`status` as `status`,
    `r`.`handle_result` as `handle_result`,
    `r`.`handle_time` as `handle_time`,
    `r`.`handler_id` as `handler_id`,
    `r`.`create_time` as `create_time`,
    `u1`.`username` as `reporter_name`,
    `u2`.`username` as `reported_user_name`
from
    ((`store`.`report` `r`
left join `store`.`user` `u1` on
    ((`r`.`reporter_id` = `u1`.`id`)))
left join `store`.`user` `u2` on
    ((`r`.`reported_user_id` = `u2`.`id`)))
where
    (`r`.`status` = 0)
order by
    `r`.`create_time`;

create or replace
algorithm = UNDEFINED view `store`.`v_pending_review` as
select
    'post' as `content_type`,
    `store`.`post`.`id` as `id`,
    `store`.`post`.`user_id` as `user_id`,
    `store`.`post`.`title` as `title`,
    `store`.`post`.`status` as `status`,
    `store`.`post`.`create_time` as `create_time`
from
    `store`.`post`
where
    (`store`.`post`.`status` = 0)
union all
select
    'comment' as `content_type`,
    `store`.`comment`.`id` as `id`,
    `store`.`comment`.`user_id` as `user_id`,
    `store`.`comment`.`content` as `title`,
    `store`.`comment`.`status` as `status`,
    `store`.`comment`.`create_time` as `create_time`
from
    `store`.`comment`
where
    (`store`.`comment`.`status` = 0)
union all
select
    'product' as `content_type`,
    `store`.`product`.`id` as `id`,
    `store`.`product`.`user_id` as `user_id`,
    `store`.`product`.`name` as `title`,
    `store`.`product`.`status` as `status`,
    `store`.`product`.`create_time` as `create_time`
from
    `store`.`product`
where
    (`store`.`product`.`status` = 0)
union all
select
    'merchant' as `content_type`,
    `store`.`merchant_apply`.`id` as `id`,
    `store`.`merchant_apply`.`user_id` as `user_id`,
    `store`.`merchant_apply`.`company_name` as `title`,
    `store`.`merchant_apply`.`status` as `status`,
    `store`.`merchant_apply`.`apply_time` as `create_time`
from
    `store`.`merchant_apply`
where
    (`store`.`merchant_apply`.`status` = 0)
union all
select
    'verification' as `content_type`,
    `store`.`user_verification`.`id` as `id`,
    `store`.`user_verification`.`user_id` as `user_id`,
    `store`.`user_verification`.`real_name` as `title`,
    `store`.`user_verification`.`status` as `status`,
    `store`.`user_verification`.`submit_time` as `create_time`
from
    `store`.`user_verification`
where
    (`store`.`user_verification`.`status` = 0)
order by
    `create_time` desc;