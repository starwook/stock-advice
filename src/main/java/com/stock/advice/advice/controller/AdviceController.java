package com.stock.advice.advice.controller;

import com.stock.advice.advice.service.AdviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AdviceController {
    private final AdviceService adviceService;

    @PostMapping("/advices")
    public void requestAdvice(@AuthenticationPrincipal User user, String portPolioType){
        adviceService.requestAdvice(user.getUsername(),portPolioType);
    }

}
