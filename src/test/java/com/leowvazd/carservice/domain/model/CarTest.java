package com.leowvazd.carservice.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {

    @Test
    void withIdShouldReturnCopyWithNewId() {
        Car car = new Car(null, "Prata", "Toyota", "Corolla", 2022);

        Car result = car.withId(10L);

        assertThat(result).isEqualTo(new Car(10L, "Prata", "Toyota", "Corolla", 2022));
        assertThat(car.id()).isNull();
    }
}
