package com.pvxdv.loggerspringaop.mapper.impl;

import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.exception.ResourceNotFoundException;
import com.pvxdv.loggerspringaop.mapper.Mapper;
import com.pvxdv.loggerspringaop.model.Order;
import com.pvxdv.loggerspringaop.model.User;
import com.pvxdv.loggerspringaop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderDtoToOrderMapper implements Mapper<OrderDto, Order> {
    private final UserRepository userRepository;

    @Override
    public Order map(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.id());
        order.setDescription(orderDto.description());
        order.setOrderStatus(orderDto.orderStatus());
        order.setUser(getUser(orderDto.userId()));
        return order;
    }

    private User getUser(Long id) {
        return Optional.ofNullable(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new ResourceNotFoundException("User with id=%d not found".formatted(id)));
    }
}
