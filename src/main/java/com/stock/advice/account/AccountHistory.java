package com.stock.advice.account;


import javax.persistence.*;

@Entity
public class AccountHistory {
    @Id @GeneratedValue
    private Long id;
    private int amount;
    private HistoryType historyType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
