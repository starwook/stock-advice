package com.stock.advice.member.controller;

import com.stock.advice.auth.dto.TokenInfo;
import com.stock.advice.member.dto.request.CreateMemberDto;
import com.stock.advice.member.dto.request.LoginDto;
import com.stock.advice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/members")
    public void createMember(@RequestBody CreateMemberDto createMemberDto){
        memberService.createMember(createMemberDto);
    }
    @PostMapping(value ="/login")
    public TokenInfo login(@RequestBody LoginDto loginDto){
        return memberService.login(loginDto.getMemberId(),loginDto.getPassword());
    }
}
