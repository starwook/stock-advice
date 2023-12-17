package com.stock.advice.advice.service;

import com.stock.advice.account.domain.Account;
import com.stock.advice.advice.domain.Advice;
import com.stock.advice.advice.domain.AdviceStock;
import com.stock.advice.advice.dto.respond.GetAdviceDto;
import com.stock.advice.advice.dto.respond.GetAdviceReturnDto;
import com.stock.advice.advice.repository.AdviceRepository;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdviceService {
    private final MemberService memberService;
    private final AdviceRepository adviceRepository;
    private final AutoInvestService autoInvestService;
    @Transactional
    public void requestAdvice(String userName,String portPolioType){
        Member member = memberService.findMember(userName);
        List<AdviceStock> stocks = autoInvestService.autoInvest(member.getAccount(),portPolioType);
        if(stocks.isEmpty()) return;
        Advice advice = Advice.builder()
                .member(member)
                .totalPrice(0)
                .localDateTime(LocalDateTime.now())
                .build();
        for(AdviceStock adviceStock: stocks) {
            advice.setTotalPrice(advice.getTotalPrice() + adviceStock.getAmount() * adviceStock.getPrice());
            advice.getAdviceStocks().add(adviceStock);
        }
        adviceRepository.save(advice);
    }
    @Transactional
    public List<GetAdviceDto> getAdvices(String memberId){
        Member member = memberService.findMember(memberId);
        return member.getAdvices().stream()
                .map(advice-> GetAdviceDto.builder()
                        .totalPrice(advice.getTotalPrice())
                        .localDateTime(advice.getLocalDateTime())
                        .build())
                .collect(Collectors.toList());
    }
    @Transactional
    public GetAdviceReturnDto getAdvicesReturn(String memberId){
        Member member = memberService.findMember(memberId);
        int investmentAmount = member.getAccount().getInvestmentAmount();
        int evaluationAmount =0;
        for(Advice advice: member.getAdvices()){ //N+1 문제
            evaluationAmount += advice.getTotalPrice();
        }
        return GetAdviceReturnDto.builder()
                .investAmount(investmentAmount)
                .evaluationAmount(evaluationAmount)
                .revenue(evaluationAmount-investmentAmount)
                .revenueRate((float)((evaluationAmount-investmentAmount)/investmentAmount))
                .build();
    }
}
