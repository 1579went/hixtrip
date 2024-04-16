# 你的建表语句,包含索引
CREATE TABLE `order`  (
                          `id` bigint NOT NULL COMMENT '订单id',
                          `user_id` bigint NOT NULL COMMENT '购买人ID',
                          `seller_id` bigint NOT NULL DEFAULT NULL COMMENT '卖家ID',
                          `sku_id` bigint NOT NULL COMMENT 'skuId',
                          `amount` int NOT NULL COMMENT '购买数量',
                          `money` decimal(10, 2) NOT NULL COMMENT '购买金额',
                          `pay_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
                          `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态(0->待支付，1->支付成功，2->支付失败,3->重复支付)',
                          `order_status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态(0->待付款,1->已付款,2->待发货,3->已发货,4->待收货,5->已完成,6->已取消,7->退款中,8->已退款)',
                          `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志(0->未删除,1->删除)',
                          `coupon_id` bigint NULL DEFAULT NULL COMMENT '优惠券编码',
                          `coupon_discount` decimal(10, 2) NULL DEFAULT NULL COMMENT '优惠券折扣金额',
                          `logistics_fee` decimal(10, 2) NULL DEFAULT NULL COMMENT '运费金额',
                          `address_id` bigint NULL DEFAULT NULL COMMENT '收获地址ID',
                          `logistics_id` bigint NULL DEFAULT NULL COMMENT '物流ID',
                          `payment_type` tinyint NULL DEFAULT NULL COMMENT '支付方式：1 -> 借记卡,2 -> 信用卡,3 -> 微信,4 -> 支付宝',
                          `create_by` bigint NOT NULL COMMENT '创建人',
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_by` bigint NOT NULL COMMENT '修改人',
                          `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `order_index_create_time`(`create_time` ASC) USING BTREE COMMENT '创建时间索引',
                          INDEX `order_index_seller_id`(`seller_id` ASC) USING BTREE COMMENT '卖家ID索引',
                          INDEX `order_index_user_id`(`user_id` ASC) USING BTREE COMMENT '用户ID索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

