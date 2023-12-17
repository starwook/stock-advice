package com.stock.advice.member.dto.respond;


import lombok.Builder;
import lombok.Getter;
import net.bytebuddy.utility.nullability.NeverNull;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginHistoryDto {
    private LocalDateTime localDateTime;
}
