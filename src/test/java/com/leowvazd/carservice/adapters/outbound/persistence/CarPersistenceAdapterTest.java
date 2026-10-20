package com.leowvazd.carservice.adapters.outbound.persistence;

import com.leowvazd.carservice.domain.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPersistenceAdapterTest {

    private static final Car CAR = new Car(1L, "Prata", "Toyota", "Corolla", 2022);
    private static final CarEntity ENTITY = new CarEntity(1L, "Prata", "Toyota", "Corolla", 2022);

    @Mock
    private CarJpaRepository carJpaRepository;

    @InjectMocks
    private CarPersistenceAdapter adapter;

    @Test
    void saveShouldMapToEntityAndBack() {
        when(carJpaRepository.save(any(CarEntity.class))).thenReturn(ENTITY);

        Car result = adapter.save(new Car(null, "Prata", "Toyota", "Corolla", 2022));

        ArgumentCaptor<CarEntity> captor = ArgumentCaptor.forClass(CarEntity.class);
        verify(carJpaRepository).save(captor.capture());
        CarEntity saved = captor.getValue();
        assertThat(saved.getId()).isNull();
        assertThat(saved.getColor()).isEqualTo("Prata");
        assertThat(saved.getBrand()).isEqualTo("Toyota");
        assertThat(saved.getModel()).isEqualTo("Corolla");
        assertThat(saved.getYear()).isEqualTo(2022);
        assertThat(result).isEqualTo(CAR);
    }

    @Test
    void findByIdShouldReturnMappedCar() {
        when(carJpaRepository.findById(1L)).thenReturn(Optional.of(ENTITY));

        assertThat(adapter.findById(1L)).contains(CAR);
    }

    @Test
    void findByIdShouldReturnEmptyWhenMissing() {
        when(carJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThat(adapter.findById(1L)).isEmpty();
    }

    @Test
    void findAllShouldReturnMappedCars() {
        when(carJpaRepository.findAll()).thenReturn(List.of(ENTITY));

        assertThat(adapter.findAll()).containsExactly(CAR);
    }

    @Test
    void existsByIdShouldDelegate() {
        when(carJpaRepository.existsById(1L)).thenReturn(true);

        assertThat(adapter.existsById(1L)).isTrue();
    }

    @Test
    void deleteByIdShouldDelegate() {
        adapter.deleteById(1L);

        verify(carJpaRepository).deleteById(1L);
    }
}
