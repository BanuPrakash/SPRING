package com.cisco.vehiclerental.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // AUTO_INCREMENT

    @ManyToOne
    @JoinColumn(name = "customer_fk")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_fk")
    private Vehicle vehicle;

    @Temporal(TemporalType.DATE)
    @Column(name="date_from")
    private Date dateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name="date_to")
    private Date dateTo;

    private double amount;
}
