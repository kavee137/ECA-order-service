package com.ijse.eca.orders.repo;

import com.ijse.eca.orders.domain.Order;
import java.util.List;

public interface OrderRepository {
    Order save(Order order);

    List<Order> saveAll(List<Order> orders);

    List<Order> findByUserId(Long userId);
}
