package com.stock.advice.account.dto;

import com.stock.advice.account.domain.AccountHistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetAccountHistoriesDto {
    private LocalDateTime localDateTime;
    private AccountHistoryType accountHistoryType;
    private int amount;
    private int temporaryBalance;

}
