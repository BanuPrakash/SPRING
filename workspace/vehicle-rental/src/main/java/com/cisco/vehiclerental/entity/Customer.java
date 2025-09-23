package com.cisco.vehiclerental.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="customers")
public class Customer {

    @Id
    private String email;

    @Column(name="FNAME")
    private String firstName;

    @Column(name="LNAME")
    private String lastName;
}
