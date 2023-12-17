package com.stock.advice.stock.dto.request;

import lombok.Getter;

@Getter
public class ChangePriceDto {
    private int newPrice;
    private String code;
}
