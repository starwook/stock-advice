package com.stock.advice.stock.controller;


import com.stock.advice.stock.dto.request.ChangePriceDto;
import com.stock.advice.stock.dto.request.CreateStockDto;
import com.stock.advice.stock.dto.request.DeleteStockDto;
import com.stock.advice.stock.dto.respond.GetStockDto;
import com.stock.advice.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @PostMapping(value = "/stocks")
    public void makeStock(@RequestBody CreateStockDto createStockDto) {
        stockService.makeStock(createStockDto);
    }

    @GetMapping(value = "/stocks")
    public List<GetStockDto> getAllStock() {
        return stockService.getStockDtos();
    }
    @PostMapping(value ="/stocks/change")
    public void changeStockPrice(ChangePriceDto changePriceDto){
        stockService.changeStockPrice(changePriceDto);
    }
    @PostMapping(value ="/stocks/delete")
    public void deleteStock(DeleteStockDto deleteStockDto){
        stockService.deleteStock(deleteStockDto);
    }
}