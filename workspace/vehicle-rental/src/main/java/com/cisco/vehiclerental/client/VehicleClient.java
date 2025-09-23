package com.cisco.vehiclerental.client;

import com.cisco.vehiclerental.entity.Vehicle;
import com.cisco.vehiclerental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class VehicleClient implements CommandLineRunner {
    private final RentalService service;

    @Override
    public void run(String... args) throws Exception {
        addVehicles();
    }

    private void addVehicles() {
        if(service.getVehicleCount() == 0) {
//            KA-05-AB-1234
            service.addVehicle(Vehicle.builder()
                            .registrationNumber("KA-05-AB-1234")
                            .dailyHireRate(4500.00)
                            .fuelType("PETROL").
                    build());

            service.addVehicle(new Vehicle("UP-15-EB-4321",
                    6500.00, "ELECTRIC"));

            service.addVehicle(Vehicle.builder()
                    .registrationNumber("DH-10-AA-0434")
                    .dailyHireRate(5300.00)
                    .fuelType("PETROL").
                    build());
        }
    }
}
