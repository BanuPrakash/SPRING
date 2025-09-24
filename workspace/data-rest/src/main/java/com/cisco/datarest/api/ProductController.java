package com.cisco.datarest.api;

import com.cisco.datarest.entity.Product;
import com.cisco.datarest.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@BasePathAwareController
public class ProductController {
    @Autowired
    private ProductRepo productRepo;

    @RequestMapping(path = "products", method = RequestMethod.GET)
    public  @ResponseBody List<Product> getProducts() {
        // we can pull productRepo.findAll() and add extra links using WebMvcLinkBuilder
        return Arrays.asList(new Product(35,"A", 523.22, 110),
                new Product(62, "B", 2351.11, 100));
    }
}
