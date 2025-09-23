package com.cisco.shopapp.api;

import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.exceptions.EntityNotFoundException;
import com.cisco.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService service;

    // GET http://localhost:8080/api/products
    // GET http://localhost:8080/api/products?low=10000&high=99999
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name = "low", defaultValue = "0.0") double l,
                                     @RequestParam(name="high", defaultValue = "0.0") double h) {
        if(l == 0.0 && h  == 0.0) {
            return service.getProducts();
        } else {
            return service.getByRange(l, h);
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Product addProduct(@RequestBody Product product) {
        return  service.addProduct(product);
    }

    // GET http://localhost:8080/api/products/3
    @GetMapping("/{pid}")
    public Product getById(@PathVariable("pid") int id) throws EntityNotFoundException  {
        return service.getProductById(id);
    }

    // PATCH http://localhost:8080/api/products/3?price=3999.20
    @PatchMapping("/{pid}")
    public Product updateProductPrice(@PathVariable("pid") int id, @RequestParam("price") double price) throws  EntityNotFoundException {
        return service.modifyProductPrice(id, price);
    }

    // PUT http://localhost:8080/api/products/3
    /*
        {
            "price": 9914.99,
            "quantity": 98
        }
     */
    @PutMapping("/{pid}")
    public Product modifyPrice(@PathVariable("pid") int id, @RequestBody Product product) {
        //
        return null;
    }
}
