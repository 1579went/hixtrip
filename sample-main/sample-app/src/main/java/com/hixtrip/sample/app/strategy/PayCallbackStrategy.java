package com.hixtrip.sample.app.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.InitializingBean;

/**
 * 支付回调策略
 * @author went
 * @date 2024/4/15
 **/
public interface PayCallbackStrategy extends InitializingBean {
    String payCallback(CommandPay commandPay);
}
