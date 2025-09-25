package com.cisco.shopapp.api;

import com.cisco.shopapp.entity.Product;
import com.cisco.shopapp.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
// use below to disable Security
//@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private OrderService service; // mock service

    @Test
    public void testGetProducts() throws Exception {
        List<Product> products = Arrays.asList(
                Product.builder().id(12).name("A").price(1334.11).quantity(100).build(),
                Product.builder().id(52).name("B").price(9999.99).quantity(100).build()
        );

        // mocking
        when(service.getProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("A")));

        verify(service, times(1)).getProducts();
    }

    @Test
    public  void addProductTest() throws  Exception {
        Product product = Product.builder().name("Tata").price(152).quantity(100).build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        when(service.addProduct(Mockito.any(Product.class))).thenReturn(Mockito.any(Product.class));

        mockMvc.perform(post("/api/products")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(service, times(1)).addProduct(Mockito.any(Product.class));
    }

    @Test
    public  void addInvalidProductTest() throws  Exception {
        Product product = Product.builder().name("").price(-152).quantity(0).build();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);

        // No need to mock Service, because RestController should throw MethodArgumentNotValidException
//        when(service.addProduct(Mockito.any(Product.class))).thenReturn(Mockito.any(Product.class));

        mockMvc.perform(post("/api/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors", hasItem("Name is required!!!")));

            verifyNoInteractions(service);
    }
}
