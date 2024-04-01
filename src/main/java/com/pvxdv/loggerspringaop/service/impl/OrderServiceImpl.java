package com.pvxdv.loggerspringaop.service.impl;


import com.pvxdv.loggerspringaop.dto.OrderDto;
import com.pvxdv.loggerspringaop.exception.ResourceNotFoundException;
import com.pvxdv.loggerspringaop.mapper.impl.OrderDtoToOrderMapper;
import com.pvxdv.loggerspringaop.mapper.impl.OrderToOrderDtoMapper;
import com.pvxdv.loggerspringaop.model.Order;
import com.pvxdv.loggerspringaop.repository.OrderRepository;
import com.pvxdv.loggerspringaop.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderToOrderDtoMapper orderToOrderDtoMapper;
    private final OrderDtoToOrderMapper orderDtoToOrderMapper;
    private final String ORDER_NOT_FOUND = "Order with id=%d not found";
    private final String SAVE_ORDER = "Saving order=%s";

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order orderToSave = orderDtoToOrderMapper.map(orderDto);
        log.debug(SAVE_ORDER.formatted(orderToSave));

        return orderToOrderDtoMapper.map(orderRepository.save(orderToSave));
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderDto> orders = orderRepository.findAll()
                .stream()
                .map(orderToOrderDtoMapper::map)
                .toList();
        log.debug("Find orders: %s".formatted(orders));
        return orders;
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        OrderDto order = orderToOrderDtoMapper.map(orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(ORDER_NOT_FOUND.formatted(orderId))));
        log.debug("Find order: %s".formatted(order));
        return order;
    }

    @Override
    public void updateOrderById(Long orderId, OrderDto orderDto) {
        if (orderExist(orderId)) {
            Order orderToUpdate = orderRepository.findById(orderId).get();

            if (orderDto.description() != null) {
                orderToUpdate.setDescription(orderDto.description());
            }

            if (orderDto.orderStatus() != null) {
                orderToUpdate.setOrderStatus(orderDto.orderStatus());
            }

            log.debug(SAVE_ORDER.formatted(orderToUpdate));
            orderRepository.save(orderToUpdate);
        } else {
            throw new ResourceNotFoundException(ORDER_NOT_FOUND.formatted(orderId));
        }
    }

    @Override
    public void deleteOrderById(Long orderId) {
        if (orderExist(orderId)) {
            orderRepository.deleteById(orderId);
            log.debug("Order with id=%d delete successfully".formatted(orderId));
        } else {
            throw new ResourceNotFoundException(ORDER_NOT_FOUND.formatted(orderId));
        }
    }

    private Boolean orderExist(Long orderId) {
        if (orderId != null) {
            return orderRepository.findById(orderId).isPresent();
        } else {
            return false;
        }
    }
}