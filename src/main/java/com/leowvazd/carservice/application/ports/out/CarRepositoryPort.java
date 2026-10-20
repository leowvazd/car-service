package com.leowvazd.carservice.application.ports.out;

import com.leowvazd.carservice.domain.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída: contrato que a camada de aplicação exige da persistência.
 */
public interface CarRepositoryPort {

    Car save(Car car);

    Optional<Car> findById(Long id);

    List<Car> findAll();

    boolean existsById(Long id);

    void deleteById(Long id);
}
