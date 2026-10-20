package com.leowvazd.carservice.adapters.inbound.rest;

import com.leowvazd.carservice.application.usecases.CreateCarUseCase;
import com.leowvazd.carservice.application.usecases.DeleteCarUseCase;
import com.leowvazd.carservice.application.usecases.FindCarUseCase;
import com.leowvazd.carservice.application.usecases.UpdateCarUseCase;
import com.leowvazd.carservice.domain.exception.CarNotFoundException;
import com.leowvazd.carservice.domain.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    private static final String BASE_URL = "/api/v1/cars";
    private static final Car CAR = new Car(1L, "Prata", "Toyota", "Corolla", 2022);
    private static final String VALID_BODY = """
            {"color":"Prata","brand":"Toyota","model":"Corolla","year":2022}
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CreateCarUseCase createCarUseCase;

    @MockitoBean
    private FindCarUseCase findCarUseCase;

    @MockitoBean
    private UpdateCarUseCase updateCarUseCase;

    @MockitoBean
    private DeleteCarUseCase deleteCarUseCase;

    @Test
    void createShouldReturn201WithLocation() throws Exception {
        when(createCarUseCase.create(new Car(null, "Prata", "Toyota", "Corolla", 2022))).thenReturn(CAR);

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/cars/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.color").value("Prata"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.year").value(2022));
    }

    @Test
    void createShouldReturn400WhenBodyIsInvalid() throws Exception {
        String invalidBody = """
                {"color":"","brand":"Toyota","model":"Corolla","year":1500}
                """;

        mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid request body"))
                .andExpect(jsonPath("$.errors.color").exists())
                .andExpect(jsonPath("$.errors.year").exists());
        verify(createCarUseCase, never()).create(any());
    }

    @Test
    void findAllShouldReturnList() throws Exception {
        when(findCarUseCase.findAll()).thenReturn(List.of(CAR));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].model").value("Corolla"));
    }

    @Test
    void findByIdShouldReturnCar() throws Exception {
        when(findCarUseCase.findById(1L)).thenReturn(CAR);

        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"));
    }

    @Test
    void findByIdShouldReturn404WhenNotFound() throws Exception {
        when(findCarUseCase.findById(9L)).thenThrow(new CarNotFoundException(9L));

        mockMvc.perform(get(BASE_URL + "/9"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Car not found with id 9"));
    }

    @Test
    void updateShouldReturnUpdatedCar() throws Exception {
        Car updated = new Car(1L, "Preto", "Toyota", "Corolla", 2022);
        when(updateCarUseCase.update(eq(1L), any(Car.class))).thenReturn(updated);

        mockMvc.perform(put(BASE_URL + "/1").contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY.replace("Prata", "Preto")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Preto"));
    }

    @Test
    void deleteShouldReturn204() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
        verify(deleteCarUseCase).delete(1L);
    }

    @Test
    void deleteShouldReturn404WhenNotFound() throws Exception {
        doThrow(new CarNotFoundException(9L)).when(deleteCarUseCase).delete(9L);

        mockMvc.perform(delete(BASE_URL + "/9"))
                .andExpect(status().isNotFound());
    }
}
