package com.leowvazd.carservice.application.usecases;

import com.leowvazd.carservice.domain.model.Car;

public interface UpdateCarUseCase {

    Car update(Long id, Car car);
}
