package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    Long createOrder(Order order);

    Order getOrder(String orderId);
    Boolean updatePayStatus(CommandPay commandPay);
}
