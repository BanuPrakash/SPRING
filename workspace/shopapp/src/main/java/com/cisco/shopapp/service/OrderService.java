package com.cisco.shopapp.service;

import com.cisco.shopapp.entity.Customer;
import com.cisco.shopapp.entity.LineItem;
import com.cisco.shopapp.entity.Order;
import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.repo.CustomerRepo;
import com.cisco.shopapp.repo.OrderRepo;
import com.cisco.shopapp.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    // Constructor DI instead of setter using @Autowired
    private final ProductRepo productRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    /*
        {
            "customer": {"email":"ria@cisco.com"},
            "items": [
                {"product": {id:3}, "quantity" : 2},
                {"product": {id:2}, "quantity" : 1}
            ]
        }
     */
    // place order, atomic operation
    @Transactional
    public String placeOrder(Order order) {
        double total = 0.0;
        for (LineItem item: order.getItems()) {
            Product product = getProductById(item.getProduct().getId());
            item.setAmount(product.getPrice() * item.getQuantity()); // tax, discount
            total += item.getAmount();
            product.setQuantity(product.getQuantity() - item.getQuantity()); // DIRTY CHECKING ,UPDATE SQL
            if(product.getQuantity() < 0) {
                throw  new IllegalArgumentException("Product " + product.getName() + " not in Stock!!!");
            }
        }
        order.setTotal(total);
        orderRepo.save(order); // cascade takes care of saving line items also
        return "Order Placed!!!";
    }


    // method for custom mutation
    @Transactional
    public Product modifyProductPrice(int id, double price) {
        productRepo.modifyProductPrice(id, price);
//        throw  new IllegalArgumentException("Some thing went wrong:-(");
        return getProductById(id);
    }


    public Product addProduct(Product product) {
        return productRepo.save(product);
    }

    public Customer addCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public List<Product> getProducts() {
        return  productRepo.findAll();
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    public Product getProductById(int id) {
        Optional<Product> optProduct = productRepo.findById(id);
        if(optProduct.isPresent()) {
            return optProduct.get();
        } else {
            return  null; // change to exception later
        }
    }

    public long getProductsCount() {
        return productRepo.count();
    }

    public long getCustomersCount() {
        return customerRepo.count();
    }
}
