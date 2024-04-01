package com.pvxdv.loggerspringaop.service;



import com.pvxdv.loggerspringaop.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long orderId);

    void updateOrderById(Long orderId, OrderDto orderDto);

    void deleteOrderById(Long orderId);

}
