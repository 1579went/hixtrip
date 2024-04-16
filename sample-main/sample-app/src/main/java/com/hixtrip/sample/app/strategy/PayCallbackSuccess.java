package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hixtrip.sample.domain.pay.model.PayStatusEnum.PAY_SUCCESS;


/**
 *  支付成功
 * @author went
 * @date 2024/4/15
 **/
@Component
public class PayCallbackSuccess implements PayCallbackStrategy{
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String payCallback(CommandPay commandPay) {
        return orderDomainService.orderPaySuccess(commandPay);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        PayCallbackStrategyFactory.register(String.valueOf(PAY_SUCCESS.getCode()), this);
    }
}
