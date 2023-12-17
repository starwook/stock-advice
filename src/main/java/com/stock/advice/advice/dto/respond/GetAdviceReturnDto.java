package com.stock.advice.advice.dto.respond;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAdviceReturnDto {
    private int investAmount;
    private int evaluationAmount;
    private int revenue;
    private float revenueRate;
}
