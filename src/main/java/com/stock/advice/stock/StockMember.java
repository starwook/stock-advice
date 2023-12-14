package com.stock.advice.stock;


import com.stock.advice.member.domain.Member;

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
}
