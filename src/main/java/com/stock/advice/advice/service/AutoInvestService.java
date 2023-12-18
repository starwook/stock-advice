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
        return makeSelectedAdviceStocksOrderByDesc(account, portPolioPolicy, stocks);
    }
    private List<AdviceStock> makeSelectedAdviceStocksOrderByDesc(Account account, PortPolioPolicy portPolioPolicy, List<Stock> stocks) {
        Collections.sort(stocks,(o1, o2) -> o2.getPrice()-o1.getPrice());
        List<AdviceStock> adviceStocks = new ArrayList<>();
        int originalBalance = account.getBalance();
        int limitBalance =  portPolioPolicy.getRate()*originalBalance/100;
        for(int i = 0; i< stocks.size(); i++){
            Stock stock = stocks.get(i);
            int amount =0;
            while(originalBalance-stock.getPrice()>=limitBalance){
                amount++;
                originalBalance-=stock.getPrice();
            }
            if(amount==0) continue;
            AdviceStock adviceStock = AdviceStock.builder()
                    .stock(stock)
                    .amount(amount)
                    .price(stock.getPrice())
                    .build();
            int buyPrice = stock.getPrice()*amount;
            accountRenew(account, originalBalance, buyPrice);
            adviceStocks.add(adviceStock);
        }
        return adviceStocks;
    }

    private  int accountRenew(Account account, int originalBalance, int buyPrice) {
        account.setInvestmentAmount(account.getInvestmentAmount()+ buyPrice);
        account.setBalance(account.getBalance() - buyPrice);
        return originalBalance;
    }


}
