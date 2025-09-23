package com.cisco.vehiclerental.api;


import com.cisco.vehiclerental.entity.Vehicle;
import com.cisco.vehiclerental.exceptions.VehicleNotFoundException;
import com.cisco.vehiclerental.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vehicles")
@RequiredArgsConstructor
public class VehicleController  {
    private final RentalService service;

    // GET http://localhost:8080/api/vehicles
    // Query Parameter [ ? ]
    // GET http://localhost:8080/api/vehicles?type=PETROL
    @GetMapping()
    public List<Vehicle> getVehicles(@RequestParam(name = "type", required = false) String type) {
       if(type == null) {
           return service.getVehicles();
       } else {
           return service.getVehiclesByType(type);
       }
    }

    // use PATH Parameter to access RESOURCE by ID --> Unique [ / ]
    // GET http://localhost:8080/api/vehicles/KA-05-AB-1234
    @GetMapping("/{reg}")
    public Vehicle getVehicleByRegNo(@PathVariable("reg") String regNo) throws VehicleNotFoundException  {
        return service.getByRegNo(regNo);
    }

    // POST http://localhost:8080/api/vehicles
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Vehicle addVehicle(@RequestBody @Valid Vehicle vehicle) {
        return service.addVehicle(vehicle);
    }

    //PATCH http://localhost:8080/api/vehicles/KA-05-AB-1234?cost=7612.55
    @PatchMapping("/{regNo}")
    public  Vehicle update(@PathVariable("regNo") String regNo, @RequestParam("cost") double cost) throws VehicleNotFoundException{
        return  service.updateHireRate(regNo, cost);
    }
}