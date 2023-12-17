package com.stock.advice.portPolioPolicy;

import com.stock.advice.advice.dto.request.MakePortPolioPolicyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PortPolioPolicyController {
    private final PortPolioPolicyService portPolioPolicyService;
    @PostMapping("/portPolioPolicy")
    public void makePortPolioPolicy(@RequestBody MakePortPolioPolicyDto makePortPolioPolicyDto){
        portPolioPolicyService.makeNewPolicy(makePortPolioPolicyDto);
    }
}
