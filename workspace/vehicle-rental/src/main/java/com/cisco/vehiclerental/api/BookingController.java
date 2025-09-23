package com.cisco.vehiclerental.api;

import com.cisco.vehiclerental.dto.RentalVehicleDTO;
import com.cisco.vehiclerental.entity.Booking;
import com.cisco.vehiclerental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final RentalService service;

    // POST http://localhost:8080/api/bookings
    // Accept: text/plain
    // Content-type: application/json
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
//    public String bookVehicle(@RequestBody @Valid BookingDTO booking) {
    public String bookVehicle(@RequestBody Booking booking) {
        // form BookingDTO we need to create Booking object to be passed to service - repo ->DB
        service.doBooking(booking);
        return  "Vehicle Rental booked!!!";
    }

    @PatchMapping ("/{bookingId}")
    public String returnVehicle(@PathVariable("bookingId") int id,
                                @RequestParam(name="returnDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date returnDate) {
        return service.returnBookedVehicle(id, returnDate);
    }

    @GetMapping()
    public List<Booking> getBookings() {
        return  service.getBookings();
    }


    // GET http://localhost:8080/api/bookings/report
    @GetMapping("/report")
    public List<RentalVehicleDTO> getVehicleRentalInfo() {
        return service.getVehicleRentalInfo();
    }
}
