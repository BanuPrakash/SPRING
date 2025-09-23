package com.cisco.vehiclerental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @Column(name="REG_NO", length = 25)
    @Pattern(regexp="^[A-Z]{2}-[0-9]{2}-[A-Z]{0,2}-[0-9]{4}$", message = "Registration Number ${validatedValue} is not valid!!")
    private String registrationNumber;

    @Column(name="HIRE_RATE")
    @Min(value = 1000, message = "Daily Hire Rate entered ${validatedValue} should be more than {value}")
    private double dailyHireRate;

    @Column(name="FUEL_TYPE")
    @NotBlank(message = "Fuel Type is required!!!")
    private String fuelType;
}
