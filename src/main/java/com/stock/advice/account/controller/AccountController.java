package com.stock.advice.account.controller;


import com.stock.advice.account.domain.AccountHistoryType;
import com.stock.advice.account.dto.GetAccountHistoriesDto;
import com.stock.advice.account.service.AccountService;
import com.stock.advice.error.UserNotLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PostMapping(value ="/accounts")
    public long makeAccount(@AuthenticationPrincipal User user){
        if(user==null) throw new UserNotLoginException();
        return accountService.makeAccount(user.getUsername());
    }
    @GetMapping(value ="/accounts")
    public int getAccountBalance(@AuthenticationPrincipal User user){
        if(user==null) throw new UserNotLoginException();
        return accountService.getAccountBalance(user.getUsername());
    }
    @GetMapping(value = "/accounts/histories")
    public List<GetAccountHistoriesDto> getAccountHistories(@AuthenticationPrincipal User user){
        if(user==null) throw new UserNotLoginException();
        return accountService.getAccountHistories(user.getUsername());
    }
    @PostMapping(value ="/accounts/withdraw")
    public int withDraw(@AuthenticationPrincipal User user, int amount){
        if(user==null) throw new UserNotLoginException();
        return accountService.withDraw(user.getUsername(),amount);
    }
    @PostMapping(value ="/accounts/deposit")
    public int deposit(@AuthenticationPrincipal User user, int amount) {
        if(user==null) throw new UserNotLoginException();
        return accountService.deposit(user.getUsername(),amount);
    }
}
