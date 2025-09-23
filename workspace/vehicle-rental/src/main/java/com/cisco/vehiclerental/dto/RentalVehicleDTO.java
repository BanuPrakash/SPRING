package com.cisco.vehiclerental.dto;

import java.util.Date;

// will generate constructor, getters, hashCode, equals
// Note there are no setters
// immutable object
public record RentalVehicleDTO(String registrationNumber, String fuelType,
                               double dailyHireRate, Date dateFrom, Date dateTo, String firstName, String lastName) {
}
