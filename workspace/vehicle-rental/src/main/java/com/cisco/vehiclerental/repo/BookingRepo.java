package com.cisco.vehiclerental.repo;

import com.cisco.vehiclerental.dto.RentalVehicleDTO;
import com.cisco.vehiclerental.entity.Booking;
import com.cisco.vehiclerental.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepo extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT * FROM vehicles v WHERE v.reg_no NOT IN " +
            "(SELECT b.vehicle_fk FROM bookings b " +
            " WHERE :dt BETWEEN b.date_from AND b.date_to OR b.date_to is null)", nativeQuery = true)
    List<Vehicle> getAvailableVehicles(@Param("dt") Date date);


    @Query("select " +
            " new com.cisco.vehiclerental.dto.RentalVehicleDTO(v.registrationNumber, " +
            "v.fuelType, v.dailyHireRate, b.dateFrom, b.dateTo, c.firstName, c.lastName) " +
            " from Booking  b inner  join  b.vehicle v inner  join b.customer c")
    List<RentalVehicleDTO> getVehicleRentalInfo();

    // IF SQL was used instead of JP-QL
    // @Query(value=" select
    //        v1_0.reg_no,
    //        v1_0.fuel_type,
    //        v1_0.hire_rate,
    //        b1_0.date_from,
    //        b1_0.date_to,
    //        c1_0.fname,
    //        c1_0.lname
    //    from
    //        bookings b1_0
    //    join
    //        vehicles v1_0
    //            on v1_0.reg_no=b1_0.vehicle_fk
    //    join
    //        customers c1_0
    //            on c1_0.email=b1_0.customer_fk",
//    nativeQuery=true)
    // List<Object[]> getVehicleRentalInfo();
}
