package com.hixtrip.sample.app.strategy;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支付回调策略工厂
 * @author went
 * @date 2024/4/15
 **/
public class PayCallbackStrategyFactory {
    // 策略集合
    private static Map<String, PayCallbackStrategy> payCallbackStrategyMap = new ConcurrentHashMap<>();

    /**
     * 获取策略
     */
    public static PayCallbackStrategy getPayCallbackStrategy(String payStatus) {
        PayCallbackStrategy payCallbackStrategy = payCallbackStrategyMap.get(payStatus);
        if (payCallbackStrategy == null) {
            throw new RuntimeException("没有找到对应的策略");
        }
        return payCallbackStrategy;
    }
    /**
     * 注册
     */
    public static void register(String name, PayCallbackStrategy payCallbackStrategy){
        if(StringUtils.isEmpty(name)||null == payCallbackStrategy){
            return;
        }
        payCallbackStrategyMap.put(name,payCallbackStrategy);

    }

}
