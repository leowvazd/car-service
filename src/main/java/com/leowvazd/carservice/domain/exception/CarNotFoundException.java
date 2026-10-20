package com.leowvazd.carservice.domain.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long id) {
        super("Car not found with id " + id);
    }
}
