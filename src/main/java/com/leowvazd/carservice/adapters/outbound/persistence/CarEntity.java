package com.leowvazd.carservice.adapters.outbound.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    // "year" é palavra reservada no H2
    @Column(name = "car_year", nullable = false)
    private Integer year;

    protected CarEntity() {
    }

    public CarEntity(Long id, String color, String brand, String model, Integer year) {
        this.id = id;
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getYear() {
        return year;
    }
}
