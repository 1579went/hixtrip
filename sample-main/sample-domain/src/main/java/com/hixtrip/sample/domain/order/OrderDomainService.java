package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

import static com.hixtrip.sample.domain.pay.model.PayStatusEnum.*;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {
    /**
     * 订单仓储
     */
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;

    /**
     * 需要实现
     * 创建待付款订单
     */
    public Long createOrder(Order order) {
        // 校验参数
        if (ObjectUtils.isEmpty(order)){
            throw new RuntimeException("订单内容不能为空");
        }
        // 计算金额
        order.setMoney(commodityDomainService.getSkuPrice(order.getSkuId()).multiply(new BigDecimal(order.getAmount())));
        // 校验库存
        Integer inventory = inventoryDomainService.getInventory(order.getSkuId());
        if (inventory < order.getAmount()){
            throw new RuntimeException("库存不足");
        }
        //修改库存
        Boolean changeInventory = inventoryDomainService.changeInventory(order.getSkuId(), order.getAmount().longValue(), order.getAmount().longValue(), order.getAmount().longValue());
        if (!changeInventory){
            throw new RuntimeException("修改库存失败");
        }
        //创建订单
        order.setCreateBy(order.getUserId());
        order.setUpdateBy(order.getUserId());
        Long orderId = orderRepository.createOrder(order);
        if(orderId == 0L){
            inventoryDomainService.changeInventory(order.getSkuId(), -order.getAmount().longValue(), -order.getAmount().longValue(), -order.getAmount().longValue());
            throw new RuntimeException("创建订单失败");
        }
        return orderId;
    }

    /**
     * 需要实现
     * 待付款订单支付成功
     */
    public String orderPaySuccess(CommandPay commandPay) {
        //查询订单
        Order order = orderRepository.getOrder(commandPay.getOrderId());
        if(ObjectUtils.isEmpty(order)){
            throw new RuntimeException("订单不存在");
        }
        if(!String.valueOf(WAIT_PAY.getCode()).equals(order.getPayStatus())
            && !String.valueOf(PAY_FAIL.getCode()).equals(order.getPayStatus())){
            throw new RuntimeException("订单状态异常！");
        }
        //修改订单状态
        if(!orderRepository.updatePayStatus(commandPay)){
            throw new RuntimeException("修改订单状态失败！");
        }
        Boolean changeInventory = inventoryDomainService.changeInventory(order.getSkuId(), 0L, -order.getAmount().longValue(), order.getAmount().longValue());
        if(!changeInventory){
            throw new RuntimeException("扣除预占库存失败！");
        }
        return PAY_SUCCESS.getMessage();
    }

    /**
     * 需要实现
     * 待付款订单支付失败
     */
    public String orderPayFail(CommandPay commandPay) {
        //查询订单
        Order order = orderRepository.getOrder(commandPay.getOrderId());
        if(ObjectUtils.isEmpty(order)){
            throw new RuntimeException("订单不存在！");
        }
        if(!String.valueOf(WAIT_PAY.getCode()).equals(order.getPayStatus())
            && !String.valueOf(PAY_FAIL.getCode()).equals(order.getPayStatus())){
            throw new RuntimeException("订单状态异常！");
        }
        if(!orderRepository.updatePayStatus(commandPay)){
            throw new RuntimeException("修改订单状态失败！");
        }
        //扣除预占库存
        return PAY_FAIL.getMessage();
    }
}
