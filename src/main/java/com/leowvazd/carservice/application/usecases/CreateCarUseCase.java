package com.leowvazd.carservice.application.usecases;

import com.leowvazd.carservice.domain.model.Car;

public interface CreateCarUseCase {

    Car create(Car car);
}
