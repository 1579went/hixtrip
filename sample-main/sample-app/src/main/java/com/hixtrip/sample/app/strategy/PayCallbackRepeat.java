package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import static com.hixtrip.sample.domain.pay.model.PayStatusEnum.PAY_REPEAT;


/**
 * 重复支付
 * @author went
 * @date 2024/4/15
 **/
@Component
public class PayCallbackRepeat implements PayCallbackStrategy{
    @Override
    public String payCallback(CommandPay commandPay) {
        return PAY_REPEAT.getMessage();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        PayCallbackStrategyFactory.register(String.valueOf(PAY_REPEAT.getCode()), this);
    }
}
