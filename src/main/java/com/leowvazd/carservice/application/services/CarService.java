package com.leowvazd.carservice.application.services;

import com.leowvazd.carservice.application.ports.out.CarRepositoryPort;
import com.leowvazd.carservice.application.usecases.CreateCarUseCase;
import com.leowvazd.carservice.application.usecases.DeleteCarUseCase;
import com.leowvazd.carservice.application.usecases.FindCarUseCase;
import com.leowvazd.carservice.application.usecases.UpdateCarUseCase;
import com.leowvazd.carservice.domain.exception.CarNotFoundException;
import com.leowvazd.carservice.domain.model.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements CreateCarUseCase, FindCarUseCase, UpdateCarUseCase, DeleteCarUseCase {

    private final CarRepositoryPort carRepositoryPort;

    public CarService(CarRepositoryPort carRepositoryPort) {
        this.carRepositoryPort = carRepositoryPort;
    }

    @Override
    public Car create(Car car) {
        return carRepositoryPort.save(car.withId(null));
    }

    @Override
    public Car findById(Long id) {
        return carRepositoryPort.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @Override
    public List<Car> findAll() {
        return carRepositoryPort.findAll();
    }

    @Override
    public Car update(Long id, Car car) {
        ensureExists(id);
        return carRepositoryPort.save(car.withId(id));
    }

    @Override
    public void delete(Long id) {
        ensureExists(id);
        carRepositoryPort.deleteById(id);
    }

    private void ensureExists(Long id) {
        if (!carRepositoryPort.existsById(id)) {
            throw new CarNotFoundException(id);
        }
    }
}
