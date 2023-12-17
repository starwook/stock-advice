package com.stock.advice.account.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistory {
    @Id @GeneratedValue
    private Long id;
    private int amount;
    private int temporaryBalance;
    private AccountHistoryType accountHistoryType;
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
