package com.leowvazd.carservice.adapters.inbound.rest;

import com.leowvazd.carservice.adapters.inbound.rest.dto.CarRequest;
import com.leowvazd.carservice.adapters.inbound.rest.dto.CarResponse;
import com.leowvazd.carservice.domain.model.Car;

final class CarRestMapper {

    private CarRestMapper() {
    }

    static Car toDomain(CarRequest request) {
        return new Car(null, request.color(), request.brand(), request.model(), request.year());
    }

    static CarResponse toResponse(Car car) {
        return new CarResponse(car.id(), car.color(), car.brand(), car.model(), car.year());
    }
}
