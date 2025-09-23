package com.cisco.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name="order_id")
    private int orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    private Date orderDate = new Date();

    @ManyToOne
    @JoinColumn(name = "customer_fk") // FK references to email of Customer
    private  Customer customer; // order is by a customer

    @OneToMany
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>(); // order has many items
}
