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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoInvestService {
    private final PortPolioPolicyService portPolioPolicyService;
    private final StockService stockService;

    public List<AdviceStock> autoInvest(Account account, String portPolioType){
        PortPolioPolicy portPolioPolicy = portPolioPolicyService.getPortPolioPolicy(portPolioType);
        List<Stock> stocks = stockService.getAllStocks();
        List<Stock> selectedStocks = makeSelectedStocksOrderByDesc(account, portPolioPolicy, stocks);
        return makeAdviceStock(selectedStocks);
    }
    private List<Stock> makeSelectedStocksOrderByDesc(Account account, PortPolioPolicy portPolioPolicy, List<Stock> stocks) {
        Collections.sort(stocks,(o1, o2) -> o2.getPrice()-o1.getPrice());
        List<Stock> selectedStocks = new ArrayList<>();
        int originalBalance = account.getBalance();
        int limitBalance = originalBalance*100/ portPolioPolicy.getRate();
        for(int i = 0; i< stocks.size(); i++){
            Stock stock = stocks.get(i);
            if(originalBalance-stock.getPrice()>=limitBalance){
                selectedStocks.add(stock);
                account.setInvestmentAmount(account.getInvestmentAmount()+stock.getPrice());
                originalBalance-=stock.getPrice();
            }
        }
        return selectedStocks;
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
