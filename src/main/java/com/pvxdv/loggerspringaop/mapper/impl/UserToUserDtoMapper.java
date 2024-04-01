package com.pvxdv.loggerspringaop.mapper.impl;

import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.dto.UserDto;
import com.pvxdv.loggerspringaop.mapper.Mapper;
import com.pvxdv.loggerspringaop.model.User;
import com.pvxdv.loggerspringaop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserToUserDtoMapper implements Mapper<User, UserDto> {
    private final OrderToOrderDtoMapper orderToOrderDtoMapper;
    private final OrderRepository orderRepository;

    @Override
    public UserDto map(User user) {
        List<OrderDto> orders = orderRepository.findAllByUserId(user.getId())
                .stream()
                .map(orderToOrderDtoMapper::map)
                .toList();

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                orders);
    }
}
