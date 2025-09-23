package com.cisco.shopapp.service;

import com.cisco.shopapp.entity.Customer;
import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.repo.CustomerRepo;
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
    public final CustomerRepo customerRepo;

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
