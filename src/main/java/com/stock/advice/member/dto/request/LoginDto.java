package com.stock.advice.member.dto.request;

import lombok.Data;

@Data
public class LoginDto {
    private String memberId;
    private String password;
}
