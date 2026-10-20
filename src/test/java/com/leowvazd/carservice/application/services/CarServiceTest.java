package com.leowvazd.carservice.application.services;

import com.leowvazd.carservice.application.ports.out.CarRepositoryPort;
import com.leowvazd.carservice.domain.exception.CarNotFoundException;
import com.leowvazd.carservice.domain.model.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    private static final Car CAR = new Car(1L, "Prata", "Toyota", "Corolla", 2022);

    @Mock
    private CarRepositoryPort carRepositoryPort;

    @InjectMocks
    private CarService carService;

    @Test
    void createShouldIgnoreIncomingIdAndSave() {
        Car input = new Car(99L, "Prata", "Toyota", "Corolla", 2022);
        when(carRepositoryPort.save(input.withId(null))).thenReturn(CAR);

        assertThat(carService.create(input)).isEqualTo(CAR);
        verify(carRepositoryPort).save(new Car(null, "Prata", "Toyota", "Corolla", 2022));
    }

    @Test
    void findByIdShouldReturnCar() {
        when(carRepositoryPort.findById(1L)).thenReturn(Optional.of(CAR));

        assertThat(carService.findById(1L)).isEqualTo(CAR);
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(carRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carService.findById(1L))
                .isInstanceOf(CarNotFoundException.class)
                .hasMessage("Car not found with id 1");
    }

    @Test
    void findAllShouldReturnAllCars() {
        when(carRepositoryPort.findAll()).thenReturn(List.of(CAR));

        assertThat(carService.findAll()).containsExactly(CAR);
    }

    @Test
    void updateShouldSaveWithPathId() {
        Car input = new Car(null, "Preto", "Toyota", "Corolla", 2023);
        Car expected = input.withId(1L);
        when(carRepositoryPort.existsById(1L)).thenReturn(true);
        when(carRepositoryPort.save(expected)).thenReturn(expected);

        assertThat(carService.update(1L, input)).isEqualTo(expected);
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(carRepositoryPort.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> carService.update(1L, CAR))
                .isInstanceOf(CarNotFoundException.class);
        verify(carRepositoryPort, never()).save(any());
    }

    @Test
    void deleteShouldRemoveCar() {
        when(carRepositoryPort.existsById(1L)).thenReturn(true);

        carService.delete(1L);

        verify(carRepositoryPort).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(carRepositoryPort.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> carService.delete(1L))
                .isInstanceOf(CarNotFoundException.class);
        verify(carRepositoryPort, never()).deleteById(any());
    }
}
