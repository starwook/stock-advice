package com.stock.advice.advice.repository;

import com.stock.advice.advice.domain.PortPolioPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PortPolioPolicyRepository extends JpaRepository<PortPolioPolicy,Long> {
    Optional<PortPolioPolicy> getPortPolioPolicyByType(@Param("type") String type);
}
