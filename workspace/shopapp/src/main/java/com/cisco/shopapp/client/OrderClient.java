package com.cisco.shopapp.client;

import com.cisco.shopapp.entity.Customer;
import com.cisco.shopapp.entity.LineItem;
import com.cisco.shopapp.entity.Order;
import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderClient implements CommandLineRunner {
    private final OrderService service; // wired using Constructor
    @Override
    public void run(String... args) throws Exception {
        newOrder();
    }

    private void newOrder() {
        Customer customer = Customer.builder().email("ria@cisco.com").build();

        Order order = new Order();
        order.setCustomer(customer);

        LineItem item1 = LineItem.builder()
                    .product(Product.builder().id(3).build())
                    .quantity(1).build();
        LineItem item2 = LineItem.builder()
                    .product(Product.builder().id(1).build())
                    .quantity(2).build();

        order.getItems().add(item1);
        order.getItems().add(item2);

        service.placeOrder(order);
    }
}
