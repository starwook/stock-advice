package com.stock.advice.advice.service;

import com.stock.advice.advice.domain.PortPolioPolicy;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdviceService {
    private final MemberService memberService;
    private final AutoInvestService autoInvestService;
    @Transactional
    public void requestAdvice(String userName,String portPolioType){
        Member member = memberService.findMember(userName);
        autoInvestService.autoInvest(member.getAccount(),portPolioType);
    }



}
