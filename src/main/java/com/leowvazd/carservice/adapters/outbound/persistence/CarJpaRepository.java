package com.leowvazd.carservice.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<CarEntity, Long> {
}
