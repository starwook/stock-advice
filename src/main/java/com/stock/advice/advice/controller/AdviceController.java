package com.stock.advice.advice.controller;

import com.stock.advice.advice.dto.respond.GetAdviceDto;
import com.stock.advice.advice.dto.respond.GetAdviceReturnDto;
import com.stock.advice.advice.service.AdviceService;
import com.stock.advice.error.UserNotLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdviceController {
    private final AdviceService adviceService;

    @PostMapping("/advices")
    public void requestAdvice(@AuthenticationPrincipal User user, String portPolioType){
        if(user ==null) throw new UserNotLoginException();
        adviceService.requestAdvice(user.getUsername(),portPolioType);
    }

    @GetMapping("/advices")
    public List<GetAdviceDto> getAdviceDtos(@AuthenticationPrincipal User user){
        if(user==null) throw new UserNotLoginException();
        return adviceService.getAdvices(user.getUsername());
    }
    @GetMapping("/advices/return")
    public GetAdviceReturnDto getAdvicesReturn(@AuthenticationPrincipal User user){
        if(user==null) throw new UserNotLoginException();
        return adviceService.getAdvicesReturn(user.getUsername());
    }

}
