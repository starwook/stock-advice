package com.stock.advice.advice.service;


import com.stock.advice.account.domain.Account;
import com.stock.advice.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoInvestService {
    private final PortPolioPolicyService portPolioPolicyService;
    public void autoInvest(Account account, String portPolioType){

    }
}
