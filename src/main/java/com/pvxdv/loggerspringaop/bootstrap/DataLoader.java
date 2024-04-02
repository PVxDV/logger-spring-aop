package com.pvxdv.loggerspringaop.bootstrap;


import com.pvxdv.loggerspringaop.enums.OrderStatus;
import com.pvxdv.loggerspringaop.model.Order;
import com.pvxdv.loggerspringaop.model.User;
import com.pvxdv.loggerspringaop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findAll().isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        User vanya = User.builder()
                .name("Ivan")
                .email("Ivan@mail.ru")
                .build();

        User pavel = User.builder()
                .name("Pavel")
                .email("Pavel@mail.ru")
                .build();

        Order vanyaOrderOne = Order.builder()
                .description("Order of some fruits")
                .orderStatus(OrderStatus.AWAITING_PAYMENT)
                .user(vanya)
                .build();

        Order vanyaOrderTwo = Order.builder()
                .description("Order of some vegetables")
                .orderStatus(OrderStatus.AWAITING_SHIPMENT)
                .user(vanya)
                .build();

        Order pavelOrderOne = Order.builder()
                .description("Order of some fruits")
                .orderStatus(OrderStatus.COMPLETED)
                .user(pavel)
                .build();

        Order pavelOrderTwo = Order.builder()
                .description("Order of some vegetables")
                .orderStatus(OrderStatus.AWAITING_PICKUP)
                .user(pavel)
                .build();

        List<Order> vanyaOrders = new ArrayList<>();
        vanyaOrders.add(vanyaOrderOne);
        vanyaOrders.add(vanyaOrderTwo);
        vanya.setOrders(vanyaOrders);


        List<Order> pavelOrders = new ArrayList<>();
        pavelOrders.add(pavelOrderOne);
        pavelOrders.add(pavelOrderTwo);
        pavel.setOrders(pavelOrders);

        userRepository.save(vanya);
        userRepository.save(pavel);
    }
}
