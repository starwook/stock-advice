package com.stock.advice.advice.repository;

import com.stock.advice.advice.domain.Advice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviceRepository extends JpaRepository<Advice,Long> {
}
