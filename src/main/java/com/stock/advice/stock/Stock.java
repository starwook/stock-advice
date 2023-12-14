package com.stock.advice.stock;


import javax.persistence.*;
import java.util.List;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.ALL)
    private List<StockMember> stockMembers;

}
