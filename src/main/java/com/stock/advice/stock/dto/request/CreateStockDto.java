package com.stock.advice.stock.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateStockDto {
    private String code;
    private String name;
    private int price;
}
