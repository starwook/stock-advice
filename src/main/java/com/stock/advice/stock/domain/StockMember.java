package com.stock.advice.stock.domain;


import com.stock.advice.member.domain.Member;
import com.stock.advice.stock.domain.Stock;

import javax.persistence.*;

@Entity
public class StockMember {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
    private int totalPrice;
    private int amount;
}
