package com.leowvazd.carservice.adapters.outbound.persistence;

import com.leowvazd.carservice.application.ports.out.CarRepositoryPort;
import com.leowvazd.carservice.domain.model.Car;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CarPersistenceAdapter implements CarRepositoryPort {

    private final CarJpaRepository carJpaRepository;

    public CarPersistenceAdapter(CarJpaRepository carJpaRepository) {
        this.carJpaRepository = carJpaRepository;
    }

    @Override
    public Car save(Car car) {
        return CarPersistenceMapper.toDomain(carJpaRepository.save(CarPersistenceMapper.toEntity(car)));
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carJpaRepository.findById(id).map(CarPersistenceMapper::toDomain);
    }

    @Override
    public List<Car> findAll() {
        return carJpaRepository.findAll().stream()
                .map(CarPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return carJpaRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        carJpaRepository.deleteById(id);
    }
}
