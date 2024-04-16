package com.hixtrip.sample.client.order.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建订单示例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {
    private String id;
    private String code;
    private String msg;

    public OrderVO success(String id) {
        return new OrderVO(id, "200", "success");
    }
    public OrderVO fail(String msg) {
        return new OrderVO("", "500", msg);
    }
}
