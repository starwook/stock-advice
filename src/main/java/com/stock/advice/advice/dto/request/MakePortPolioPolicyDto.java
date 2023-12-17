package com.stock.advice.advice.dto.request;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MakePortPolioPolicyDto {
    private String type;
    private int rate;
}
