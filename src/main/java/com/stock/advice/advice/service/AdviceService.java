package com.stock.advice.advice.service;

import com.stock.advice.advice.domain.Advice;
import com.stock.advice.advice.domain.AdviceStock;
import com.stock.advice.advice.repository.AdviceRepository;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .build();
        for(AdviceStock adviceStock: stocks) {
            advice.setTotalPrice(advice.getTotalPrice() + adviceStock.getAmount() * adviceStock.getPrice());
            advice.getAdviceStocks().add(adviceStock);
        }
        adviceRepository.save(advice);
    }


}
