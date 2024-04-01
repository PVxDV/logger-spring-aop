package com.pvxdv.loggerspringaop.mapper.impl;

import com.pvxdv.loggerspringaop.model.Order;
import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderDtoMapper implements Mapper<Order, OrderDto> {
    @Override
    public OrderDto map(Order order) {
        return new OrderDto(
                order.getId(),
                order.getDescription(),
                order.getOrderStatus(),
                order.getUser().getId());
    }
}
