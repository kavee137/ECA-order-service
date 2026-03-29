package com.ijse.eca.orders.service;

import com.ijse.eca.orders.api.dto.CreateOrderRequest;
import com.ijse.eca.orders.api.OrderController;
import com.ijse.eca.orders.domain.Order;
import com.ijse.eca.orders.repo.OrderRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order place(CreateOrderRequest request) {
        return orderRepository.save(new Order(request.userId(), request.productId(), request.quantity(), Instant.now()));
    }

    public List<Order> placeBatch(Long userId, List<OrderController.OrderItem> items) {
        List<Order> orders = items.stream()
                .map(item -> new Order(userId, item.getProductId(), item.getQuantity(), Instant.now()))
                .toList();
        return orderRepository.saveAll(orders);
    }

    public List<Order> listByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
