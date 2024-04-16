package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.strategy.PayCallbackStrategyFactory;
import com.hixtrip.sample.app.strategy.PayCallbackStrategy;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PayDomainService payDomainService;

    @Override
    public OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 参数校验
        if (commandOderCreateDTO == null){
            return null;
        }
        // 调用领域服务
        Long orderId = orderDomainService.createOrder(OrderConvertor.INSTANCE.dtoToDomain(commandOderCreateDTO));
        if (orderId == null){
            return new OrderVO().fail("创建订单失败");
        }
        return new OrderVO().success(orderId.toString());
    }

    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        // 参数校验
        if (commandPayDTO == null){
            return null;
        }
        // 调用领域服务
        CommandPay commandPay = CommandPayConvertor.INSTANCE.dtoToCommandPay(commandPayDTO);
        payDomainService.payRecord(commandPay);
        // 根据支付状态，调用策略
        PayCallbackStrategy payCallbackStrategy = PayCallbackStrategyFactory.getPayCallbackStrategy(commandPay.getPayStatus());
        return payCallbackStrategy.payCallback(commandPay);
    }
}
