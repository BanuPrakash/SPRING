package com.cisco.vehiclerental.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
{
   "email": "roger@adobe.com",
   "registrationNumber": "DH-10-AA-0434",
   "dateFrom": "2025-08-20"
}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDTO {
    @NotBlank(message = "Email is required!!!")
    @Email(message = "entered ${validatedValue} is not a proper email")
    private String email;

    @Pattern(regexp="^[A-Z]{2}-[0-9]{2}-[A-Z]{0,2}-[0-9]{4}$", message = "Registration Number ${validatedValue} is not valid!!")
    private String registrationNumber;

    @FutureOrPresent(message = "Entered Date ${validatedValue} should be present or Future Date")
    private Date dateFrom;
}
