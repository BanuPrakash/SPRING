package com.cisco.shopapp.api;

import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.exceptions.EntityNotFoundException;
import com.cisco.shopapp.service.OrderService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "Product API Service")
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
    public Product addProduct(@RequestBody @Valid Product product) {
        return  service.addProduct(product);
    }

    // GET http://localhost:8080/api/products/3
    @Operation(
            description = "Service that return a Product",
            summary = "This service returns a Product by the ID",
            responses = {
                    @ApiResponse(description = "Successful Operation", responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "404", description = "Product  Not found", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))
            })
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
    @Hidden
    @PutMapping("/{pid}")
    public Product modifyPrice(@PathVariable("pid") int id, @RequestBody Product product) {
        //
        return null;
    }
}
