package com.leowvazd.carservice.domain.model;

/**
 * Entidade de domínio. Não depende de frameworks.
 */
public record Car(Long id, String color, String brand, String model, Integer year) {

    public Car withId(Long newId) {
        return new Car(newId, color, brand, model, year);
    }
}
