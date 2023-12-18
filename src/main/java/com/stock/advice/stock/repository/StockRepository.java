package com.stock.advice.stock.repository;

import com.stock.advice.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Long> {
    @Query("select s from Stock s where s.name = :name or s.code = :code")
    Optional<Stock> checkIfExistStockByNameOrCode(@Param("name")String name,@Param("code") String code);
    @Query("select s from Stock s where s.code = :code")
    Optional<Stock> findStockByCode(@Param("code") String code);
}
