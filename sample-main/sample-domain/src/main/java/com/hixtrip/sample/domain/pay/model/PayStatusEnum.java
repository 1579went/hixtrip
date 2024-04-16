package com.hixtrip.sample.domain.pay.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author went
 * @date 2024/4/13
 **/
@AllArgsConstructor
@Getter
public enum PayStatusEnum {
    /**
     * 待支付
     */
    WAIT_PAY(0, "待支付"),

    /**
     * 支付成功
     */
    PAY_SUCCESS(1, "支付成功"),


    /**
     * 支付失败
     */
    PAY_FAIL(2, "支付失败"),

    /**
     * 支付失败
     */
    PAY_REPEAT(3, "重复支付");
    /**
     * 状态码
     */
    private final int code;

    /**
     *
     * 信息
     */
    private final String message;
}
