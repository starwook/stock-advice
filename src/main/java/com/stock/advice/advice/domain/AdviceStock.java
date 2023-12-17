package com.stock.advice.advice.domain;


import com.stock.advice.stock.domain.Stock;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AdviceStock {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Advice advice;
    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;
    private int amount;
    private int price;
}
