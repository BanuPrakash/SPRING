package com.cisco.shopapp.client;

import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.exceptions.EntityNotFoundException;
import com.cisco.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@Order(1)
@RequiredArgsConstructor
public class ProductClient implements CommandLineRunner {
    private final OrderService service; // wired using Constructor

    //  this method will be called once after spring container is created and initialized
    @Override
    public void run(String... args) throws Exception {
//        addProducts();
//        modifyPrice();
//        printProducts();
    }

    private void modifyPrice() throws EntityNotFoundException {
        Product p = service.modifyProductPrice(3, 4500.00);
        System.out.println(p);
    }
    private void addProducts() {
        if(service.getProductsCount() == 0) {
            service.addProduct(Product.builder().name("iPhone 16").price(89000.00).build());
            service.addProduct(Product.builder().name("Sony Bravia").price(215000.00).build());
            service.addProduct(Product.builder().name("Wacom").price(5000.00).build());
        }
    }

    private void printProducts() {
        List<Product> products = service.getProducts();
        for(Product product : products) {
            System.out.println(product); // toString()
        }
    }
}
