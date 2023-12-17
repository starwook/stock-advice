package com.stock.advice.stock.dto.respond;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetStockDto {
    private String code;
    private String name;
    private int price;
}
