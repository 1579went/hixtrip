package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @TableName order
 */
@TableName(value ="`order`", autoResultMap = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class OrderDO implements Serializable {
    /**
     * 订单id
     */
    @TableId
    private Long id;

    /**
     * 购买人ID
     */
    private Long userId;

    /**
     * 卖家ID
     */
    private Long sellerId;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态(0->待支付，1->支付成功，2->支付失败,3->重复支付)
     */
    private String payStatus;

    /**
     * 订单状态(0->待付款,1->已付款,2->待发货,3->已发货,4->待收货,5->已完成,6->已取消,7->退款中,8->已退款)
     */
    private Integer orderStatus;

    /**
     * 删除标志(0->未删除,1->删除)
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 优惠券编码
     */
    private Long couponId;

    /**
     * 优惠券折扣金额
     */
    private BigDecimal couponDiscount;

    /**
     * 运费金额
     */
    private BigDecimal logisticsFee;

    /**
     * 收获地址ID
     */
    private Long addressId;

    /**
     * 物流ID
     */
    private Long logisticsId;

    /**
     * 支付方式：1 -> 借记卡,2 -> 信用卡,3 -> 微信,4 -> 支付宝
     */
    private Integer paymentType;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}