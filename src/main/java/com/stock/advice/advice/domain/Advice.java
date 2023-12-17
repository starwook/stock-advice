package com.stock.advice.advice.domain;

import com.stock.advice.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Advice {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;
    private int totalPrice;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<AdviceStock> adviceStocks = new ArrayList<>();

}
