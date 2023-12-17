package com.stock.advice.advice.dto.respond;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class GetAdviceDto {
    private int totalPrice;
    private LocalDateTime localDateTime;
}
