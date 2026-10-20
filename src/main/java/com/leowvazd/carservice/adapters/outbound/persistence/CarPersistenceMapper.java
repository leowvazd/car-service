package com.leowvazd.carservice.adapters.outbound.persistence;

import com.leowvazd.carservice.domain.model.Car;

final class CarPersistenceMapper {

    private CarPersistenceMapper() {
    }

    static CarEntity toEntity(Car car) {
        return new CarEntity(car.id(), car.color(), car.brand(), car.model(), car.year());
    }

    static Car toDomain(CarEntity entity) {
        return new Car(entity.getId(), entity.getColor(), entity.getBrand(), entity.getModel(), entity.getYear());
    }
}
