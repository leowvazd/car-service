package com.leowvazd.carservice;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class CarServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void mainShouldStartSpringApplication() {
        String[] args = {};
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            CarServiceApplication.main(args);
            springApplication.verify(() -> SpringApplication.run(CarServiceApplication.class, args));
        }
    }

    @Test
    void databaseShouldStartWithSeedCar() throws Exception {
        mockMvc.perform(get("/api/v1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"));
    }

    @Test
    void fullCrudFlowShouldWorkAgainstH2() throws Exception {
        mockMvc.perform(post("/api/v1/cars").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"color\":\"Azul\",\"brand\":\"Fiat\",\"model\":\"Uno\",\"year\":2010}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber());

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.model == 'Uno')]").exists());

        mockMvc.perform(delete("/api/v1/cars/12345"))
                .andExpect(status().isNotFound());
    }

    @Test
    void swaggerShouldExposeOpenApiDocs() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Car Service API"))
                .andExpect(jsonPath("$.paths['/api/v1/cars/{id}']").exists());
    }
}
