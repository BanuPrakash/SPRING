package com.cisco.shopapp.api;

import com.cisco.shopapp.entity.Order;
import com.cisco.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    // GET http://localhost:8080/api/orders
    @GetMapping()
    public List<Order> getOrders() {
        return  service.getOrders();
    }

    // POST  http://localhost:8080/api/orders
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody Order order) {
        return service.placeOrder(order);
    }
}
