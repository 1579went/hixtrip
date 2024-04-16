package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hixtrip.sample.domain.pay.model.PayStatusEnum.PAY_SUCCESS;

/**
 * @author went
 * @date 2024/4/13
 **/
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;
    // 创建订单
    @Override
    public Long createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.domainToDO(order);
        int i = orderMapper.insert(orderDO);
        if(i < 1){
            return 0L;
        }
        return orderDO.getId();
    }
    // 获取订单
    @Override
    public Order getOrder(String orderId) {
        OrderDO orderDO = orderMapper.selectById(orderId);
        if(orderDO != null){
            return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
        }
        return null;
    }
    // 更新订单支付状态
    @Override
    public Boolean updatePayStatus(CommandPay commandPay) {
        // 校验是否支付成功
        if (commandPay.getPayStatus().equals(PAY_SUCCESS.toString())){
            return false;
        }
        OrderDO orderDO = new OrderDO();
        orderDO.setId(Long.valueOf(commandPay.getOrderId()));
        orderDO.setPayStatus((commandPay.getPayStatus()));
        return orderMapper.updateById(orderDO) > 0;
    }
}
