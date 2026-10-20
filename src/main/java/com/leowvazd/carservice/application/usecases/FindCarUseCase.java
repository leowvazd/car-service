package com.leowvazd.carservice.application.usecases;

import com.leowvazd.carservice.domain.model.Car;

import java.util.List;

public interface FindCarUseCase {

    Car findById(Long id);

    List<Car> findAll();
}
