package com.stock.advice.account.domain;


import com.stock.advice.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name ="member_id")
    private Member member;


    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<AccountHistory> accountHistories = new ArrayList<>();

    private int balance;
    private int investmentAmount;
    public void deposit(int amount){
        this.balance+=amount;
    }
    public void withDraw(int amount){
        if(this.balance-amount<0) throw new IllegalArgumentException("잔액 부족");
        this.balance -=amount;
    }
}
