package com.leowvazd.carservice.adapters.inbound.rest;

import com.leowvazd.carservice.adapters.inbound.rest.dto.CarRequest;
import com.leowvazd.carservice.adapters.inbound.rest.dto.CarResponse;
import com.leowvazd.carservice.application.usecases.CreateCarUseCase;
import com.leowvazd.carservice.application.usecases.DeleteCarUseCase;
import com.leowvazd.carservice.application.usecases.FindCarUseCase;
import com.leowvazd.carservice.application.usecases.UpdateCarUseCase;
import com.leowvazd.carservice.domain.model.Car;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@Tag(name = "Cars", description = "Operações CRUD de carros")
public class CarController {

    private final CreateCarUseCase createCarUseCase;
    private final FindCarUseCase findCarUseCase;
    private final UpdateCarUseCase updateCarUseCase;
    private final DeleteCarUseCase deleteCarUseCase;

    public CarController(CreateCarUseCase createCarUseCase,
                         FindCarUseCase findCarUseCase,
                         UpdateCarUseCase updateCarUseCase,
                         DeleteCarUseCase deleteCarUseCase) {
        this.createCarUseCase = createCarUseCase;
        this.findCarUseCase = findCarUseCase;
        this.updateCarUseCase = updateCarUseCase;
        this.deleteCarUseCase = deleteCarUseCase;
    }

    @PostMapping
    @Operation(summary = "Cria um carro")
    @ApiResponse(responseCode = "201", description = "Carro criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<CarResponse> create(@Valid @RequestBody CarRequest request) {
        Car created = createCarUseCase.create(CarRestMapper.toDomain(request));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(CarRestMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Lista todos os carros")
    @ApiResponse(responseCode = "200", description = "Lista de carros")
    public List<CarResponse> findAll() {
        return findCarUseCase.findAll().stream()
                .map(CarRestMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um carro pelo id")
    @ApiResponse(responseCode = "200", description = "Carro encontrado")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public CarResponse findById(@PathVariable Long id) {
        return CarRestMapper.toResponse(findCarUseCase.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um carro")
    @ApiResponse(responseCode = "200", description = "Carro atualizado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public CarResponse update(@PathVariable Long id, @Valid @RequestBody CarRequest request) {
        return CarRestMapper.toResponse(updateCarUseCase.update(id, CarRestMapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um carro")
    @ApiResponse(responseCode = "204", description = "Carro removido")
    @ApiResponse(responseCode = "404", description = "Carro não encontrado")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteCarUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
