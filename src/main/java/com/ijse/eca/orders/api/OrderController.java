package com.ijse.eca.orders.api;

import com.ijse.eca.orders.api.dto.CreateOrderRequest;
import com.ijse.eca.orders.api.dto.OrderResponse;
import com.ijse.eca.orders.domain.Order;
import com.ijse.eca.orders.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse place(@Valid @RequestBody CreateOrderRequest request) {
        return toResponse(orderService.place(request));
    }

    @PostMapping("/orders/different-quantity")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderResponse> placeBatch(@Valid @RequestBody BatchOrderRequest request) {
        return orderService.placeBatch(request.getUserId(), request.getItems()).stream()
                .map(OrderController::toResponse).toList();
    }

    @GetMapping("/orders")
    public List<OrderResponse> list(@RequestParam("userId") Long userId) {
        return orderService.listByUserId(userId).stream().map(OrderController::toResponse).toList();
    }

    private static OrderResponse toResponse(Order o) {
        return new OrderResponse(o.getId(), o.getUserId(), o.getProductId(), o.getQuantity(), o.getCreatedAt());
    }

    // Inner class for batch order request
    public static class BatchOrderRequest {
        private Long userId;
        private List<OrderItem> items;

        public BatchOrderRequest() {}

        public BatchOrderRequest(Long userId, List<OrderItem> items) {
            this.userId = userId;
            this.items = items;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public void setItems(List<OrderItem> items) {
            this.items = items;
        }
    }

    public static class OrderItem {
        private String productId;
        private Integer quantity;

        public OrderItem() {}

        public OrderItem(String productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
