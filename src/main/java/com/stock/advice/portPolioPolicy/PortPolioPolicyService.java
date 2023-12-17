package com.stock.advice.portPolioPolicy;


import com.stock.advice.advice.dto.request.MakePortPolioPolicyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortPolioPolicyService {
    private final PortPolioPolicyRepository portPolioPolicyRepository;
    @Transactional
    public void makeNewPolicy(MakePortPolioPolicyDto makePortPolioPolicyDto){
        Optional<PortPolioPolicy> portPolioPolicy = portPolioPolicyRepository.getPortPolioPolicyByType(makePortPolioPolicyDto.getType());
        if(portPolioPolicy.isPresent()) throw new IllegalArgumentException("이미 존재하는 이름의 유형입니다.");
        PortPolioPolicy newPortPolioPolicy = PortPolioPolicy.builder()
                .type(makePortPolioPolicyDto.getType())
                .rate(makePortPolioPolicyDto.getRate())
                .build();
        portPolioPolicyRepository.save(newPortPolioPolicy);
    }
    @Transactional
    public PortPolioPolicy getPortPolioPolicy(String type){
        Optional<PortPolioPolicy> portPolioPolicy = portPolioPolicyRepository.getPortPolioPolicyByType(type);
        if(portPolioPolicy.isPresent()) return portPolioPolicy.get();
        throw new IllegalArgumentException("존재하지 않는 포트폴리오 유형입니다.");
    }
}
