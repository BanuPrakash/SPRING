package com.cisco.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

@Entity
@Table(name="line_items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    @Column(name="item_id")
    private int itemId;

    @ManyToOne
    @JoinColumn(name="product_fk")
    private Product product; // line item of a given Product

    private int quantity;

    private double amount;
}
