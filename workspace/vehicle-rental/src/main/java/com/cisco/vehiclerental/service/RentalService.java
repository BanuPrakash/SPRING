package com.cisco.vehiclerental.service;

import com.cisco.vehiclerental.dto.RentalVehicleDTO;
import com.cisco.vehiclerental.entity.Booking;
import com.cisco.vehiclerental.entity.Customer;
import com.cisco.vehiclerental.entity.Vehicle;
import com.cisco.vehiclerental.exceptions.VehicleNotFoundException;
import com.cisco.vehiclerental.repo.BookingRepo;
import com.cisco.vehiclerental.repo.CustomerRepo;
import com.cisco.vehiclerental.repo.VehicleRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RentalService {
    private final VehicleRepo vehicleRepo; // constructor wiring instead of @Autowired [setter]
    private final CustomerRepo customerRepo;
    private final BookingRepo bookingRepo;

    // this won't have return date and amount
    public Booking doBooking(Booking booking) {
        return bookingRepo.save(booking);
    }

    // pass booking id and return date
    // atomic operation, unit of work

    /**
     *
     * @param id rental ID
     * @param returnDate when vehicle is returned
     * @return success message
     */
    @Transactional
    public String returnBookedVehicle(int id, Date returnDate) {
        Booking booking = bookingRepo.findById(id).get(); // get complete booking info form DB
        Vehicle vehicle = vehicleRepo.findById(booking.getVehicle().getRegistrationNumber()).get();
        double cost = vehicle.getDailyHireRate();

        long diffInMillies = Math.abs(returnDate.getTime() - booking.getDateFrom().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double amount = cost * diff;

        booking.setDateTo(returnDate); // DIRTY
        booking.setAmount(amount); // DIRTY
        // no explicit UPDATE called
        // booking became DIRTY, ORM does DIRTY CHECKING and issues UPDATE SQL
        return "Vehicle returned!!!";
    }

    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    public long getVehicleCount() {
        return vehicleRepo.count();
    }
    public long getCustomerCount() {
        return customerRepo.count();
    }

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepo.save(vehicle);
    }

    public Customer addCustomer(Customer customer) {
        return  customerRepo.save(customer);
    }

    public List<Vehicle> getVehicles() {
        // select * from vehicles
        return  vehicleRepo.findAll();
    }

    public List<Vehicle> getVehiclesByType(String type) {
        // select * from vehicles
        return  vehicleRepo.findByFuelType(type);
    }

    public List<Customer> getCustomers() {
        // select * from customers
        return  customerRepo.findAll();
    }

    public Vehicle getByRegNo(String reg) throws VehicleNotFoundException {
        Optional<Vehicle> opt = vehicleRepo.findById(reg);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw  new VehicleNotFoundException("Vehicle with Registration Number " + reg + " doesn't exist!!!");
    }

    @Transactional
    public  Vehicle updateHireRate(String regNo, double cost) throws VehicleNotFoundException {
        vehicleRepo.updateVehicleHireRate(regNo, cost);
        return  getByRegNo(regNo);
    }

    public List<RentalVehicleDTO> getVehicleRentalInfo() {
        return bookingRepo.getVehicleRentalInfo();
    }
}
