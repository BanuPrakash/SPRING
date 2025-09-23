package com.cisco.vehiclerental.repo;

import com.cisco.vehiclerental.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Spring Data JPA will generate @Repository class
public interface VehicleRepo extends JpaRepository<Vehicle, String> {
    // Projections
    // select * from vehicles where fuel_type = ?
    List<Vehicle> findByFuelType(String type);

    // select * from vehicles where fuel_type = ? AND hire_rate >= ? AND hire_rate <= ?
    List<Vehicle> findByFuelTypeAndDailyHireRateBetween(String type, double low, double high);
    // select * from vehicles where reg_no like %KA%
    List<Vehicle> findByRegistrationNumberLike(String code);

    // custom methods
    @Modifying // executeUpdate() instead of executeQuery()
    // JP-QL
    @Query("update Vehicle set dailyHireRate = :c where registrationNumber = :r")
    void updateVehicleHireRate(@Param("r") String regNo, @Param("c") double newCost);
}
