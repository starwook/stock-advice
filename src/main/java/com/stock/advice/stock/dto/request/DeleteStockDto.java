package com.stock.advice.stock.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteStockDto {
    private String code;
}
