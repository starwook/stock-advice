package com.stock.advice.advice.service;


import com.stock.advice.account.domain.Account;
import com.stock.advice.advice.domain.AdviceStock;
import com.stock.advice.portPolioPolicy.PortPolioPolicy;
import com.stock.advice.portPolioPolicy.PortPolioPolicyService;
import com.stock.advice.stock.domain.Stock;
import com.stock.advice.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoInvestService {
    private final PortPolioPolicyService portPolioPolicyService;
    private final StockService stockService;

    public List<AdviceStock> autoInvest(Account account, String portPolioType){
        PortPolioPolicy portPolioPolicy = portPolioPolicyService.getPortPolioPolicy(portPolioType);
        int remainRate = portPolioPolicy.getRate();
        List<Stock> stocks = stockService.getAllStocks();


        return makeAdviceStock(stocks);
    }
    private List<AdviceStock> makeAdviceStock(List<Stock> stocks) {
        List<AdviceStock> adviceStocks = new ArrayList<>();
        for(Stock stock : stocks){
            AdviceStock adviceStock = AdviceStock.builder()
                    .stock(stock)
                    .build();
            adviceStocks.add(adviceStock);
        }
        return adviceStocks;
    }
}
