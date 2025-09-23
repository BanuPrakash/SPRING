package com.cisco.vehiclerental.api;

import com.cisco.vehiclerental.entity.Vehicle;
import com.cisco.vehiclerental.service.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalService service;

    @Test
    public void testGetVehicles() throws Exception{
        // dummy vehicles, not the actual one coming from Database
        List<Vehicle> vehicles = List.of(Vehicle.builder().
                registrationNumber("KA-01-AA-1111").
                fuelType("PETROL").
                dailyHireRate(1000).build(),
                Vehicle.builder().
                        registrationNumber("UP-02-BB-3111").
                        fuelType("DIESEL").
                        dailyHireRate(2000).build()
                );

        // mock
        when(service.getVehicles()).thenReturn(vehicles);

        // GET http://localhost:8080/api/products
        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber", is("KA-01-AA-1111")))
                .andExpect(jsonPath("$[1].registrationNumber", is("UP-02-BB-3111")));

        verify(service, times(1)).getVehicles();
    }

    @Test
    public void testAddVehicle() throws Exception {
        Vehicle vehicle =   Vehicle.builder().
                registrationNumber("UP-02-BB-3111").
                fuelType("DIESEL").
                dailyHireRate(2000).build();
        // convert vehicle to JSON so that it's sent as payload to server
        ObjectMapper mapper = new ObjectMapper();
        String JSON = mapper.writeValueAsString(vehicle);
        // this JSON is sent to SERVER, Server converts JSON to Vehicle

        // mock service method to take a vehicle and return a vehicle
        when(service.addVehicle(Mockito.any(Vehicle.class))).
                thenReturn(Mockito.any(Vehicle.class));

        mockMvc.perform(post("/api/vehicles")
                .content(JSON)
                .contentType("application/json")).andExpect(status().isCreated());

        verify(service, times(1)).addVehicle(Mockito.any(Vehicle.class));
    }


    @Test
    public void testAddInvalidVehicle() throws Exception{
        Vehicle vehicle =   Vehicle.builder().
                registrationNumber("JK").
                fuelType("DIESEL").
                dailyHireRate(500).build();
        // convert vehicle to JSON so that it's sent as payload to server
        ObjectMapper mapper = new ObjectMapper();
        String JSON = mapper.writeValueAsString(vehicle);
        // this JSON is sent to SERVER, Server converts JSON to Vehicle

        // no need to mock service method; expected MethodArgumentNotValidException to be thrown


        mockMvc.perform(post("/api/vehicles")
                .content(JSON)
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("Daily Hire Rate entered 500.0 should be more than 1000")))
                .andExpect(jsonPath("$.errors", hasItem("Registration Number JK is not valid!!")));

        verifyNoInteractions(service);

    }
}
