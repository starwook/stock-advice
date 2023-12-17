package com.stock.advice.stock.service;


import com.stock.advice.advice.domain.Advice;
import com.stock.advice.advice.domain.AdviceStock;
import com.stock.advice.stock.domain.Stock;
import com.stock.advice.stock.dto.request.ChangePriceDto;
import com.stock.advice.stock.dto.request.CreateStockDto;
import com.stock.advice.stock.dto.request.DeleteStockDto;
import com.stock.advice.stock.dto.respond.GetStockDto;
import com.stock.advice.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;

    public void makeStock(CreateStockDto createStockDto){
        Optional<Stock> stock = stockRepository.checkIfExistStockByNameOrCode(createStockDto.getName(), createStockDto.getCode());
        if(stock.isPresent()) throw new IllegalArgumentException("이미 존재하는 증권입니다.");
        Stock newStock = Stock.builder()
                .name(createStockDto.getName())
                .code(createStockDto.getCode())
                .price(createStockDto.getPrice())
                .build();
        stockRepository.save(newStock);
    }
    public void deleteStock(DeleteStockDto deleteStockDto){
        Optional<Stock> stock = stockRepository.findByCode(deleteStockDto.getCode());
        if(!stock.isPresent()) throw new IllegalArgumentException("존재하지 않는 코드의 증권입니다.");
        changePrice(stock,0);
        stockRepository.delete(stock.get());
    }
    public void changeStockPrice(ChangePriceDto changePriceDto){
        if(changePriceDto.getNewPrice()<0) throw new IllegalArgumentException("가능하지 않은 새 가격입니다.");
        Optional<Stock> stock = stockRepository.findByCode(changePriceDto.getCode());
        if(!stock.isPresent()) throw new IllegalArgumentException("존재하지 않는 코드의 증권입니다.");
        int newPrice = changePriceDto.getNewPrice();
        changePrice( stock, newPrice);
    }

    private static void changePrice( Optional<Stock> stock, int newPrice) {
        for(AdviceStock adviceStock : stock.get().getAdviceStocks()){
            int originalStockPrice = adviceStock.getPrice();
            int gap = originalStockPrice- newPrice;
            adviceStock.setPrice(newPrice);
            Advice advice = adviceStock.getAdvice(); //Join하여 N+1문제 해결 필요
            advice.setTotalPrice(advice.getTotalPrice()+(gap*adviceStock.getAmount()));
        }
    }

    public List<GetStockDto> getStockDtos(){
        return stockRepository.findAll().stream()
                .map(stock->{
                    return GetStockDto.builder()
                            .price(stock.getPrice())
                            .code(stock.getCode())
                            .name(stock.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }
    public List<Stock> getAllStocks(){
        return stockRepository.findAll();
    }
}
