package com.ramij.inventory.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    private SizeName sizeName;
    private double height;
    private double width;

    // Constructors, getters, and setters
}
