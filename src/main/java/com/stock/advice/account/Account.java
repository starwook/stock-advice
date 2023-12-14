package com.stock.advice.account;


import com.stock.advice.member.domain.Member;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<AccountHistory> accountHistories;
}
