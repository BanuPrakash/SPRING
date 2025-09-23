package com.cisco.vehiclerental.client;

import com.cisco.vehiclerental.entity.Booking;
import com.cisco.vehiclerental.entity.Customer;
import com.cisco.vehiclerental.entity.Vehicle;
import com.cisco.vehiclerental.service.RentalService;
import com.cisco.vehiclerental.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingClient implements CommandLineRunner {
    private  final RentalService service;
    private final DateUtil dateUtil;

    @Override
    public void run(String... args) throws Exception {
//        bookVehicle();
        returnVehicle();
    }

    private void returnVehicle() {
        System.out.println(service.returnBookedVehicle(1, dateUtil.fromString("21-08-2025")));
    }

    private void bookVehicle() {
//        Booking booking = Booking.builder().
//                customer(Customer.builder().email("anne@cisco.com").build())
//                        .vehicle(Vehicle.builder().registrationNumber("KA-05-AB-1234").build())
//                        .dateFrom(dateUtil.fromString("19-08-2025")).
//                build();
        Booking booking = Booking.builder().
                customer(Customer.builder().email("roger@cisco.com").build())
                .vehicle(Vehicle.builder().registrationNumber("DH-10-AA-0434").build())
                .dateFrom(dateUtil.fromString("20-08-2025")).
                build();
        System.out.println(service.doBooking(booking));
    }
}
