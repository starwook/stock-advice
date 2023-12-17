package com.stock.advice.stock.domain;


import com.stock.advice.advice.domain.AdviceStock;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String code;
    private int price;


    @OneToMany(mappedBy = "stock",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private List<AdviceStock> adviceStocks = new ArrayList<>();
    public void setPrice(int price){
        this.price = price;
    }

}
