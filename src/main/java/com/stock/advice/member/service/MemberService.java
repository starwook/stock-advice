package com.stock.advice.member.service;

import com.stock.advice.auth.JwtTokenProvider;
import com.stock.advice.auth.dto.TokenInfo;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.dto.request.CreateMemberDto;
import com.stock.advice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void createMember(CreateMemberDto createMemberDto){
        Optional<Member> member = memberRepository.findMemberByMemberId(createMemberDto.getMemberId());
        if (member.isPresent()) throw new IllegalArgumentException("이미 존재하는 유저Id");
        Member newMember = Member.builder()
                .memberId(createMemberDto.getMemberId())
                .password(passwordEncoder.encode(createMemberDto.getPassword()))
                .build();
        memberRepository.save(newMember);
    }
    @Transactional
    public TokenInfo login(String memberId, String password){
        //Id/Pw 기반으로 Authentication 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId,password);
        //authenrication은 기본적으로 false (비밀번호등) 검증이 완료된다면 true로 바뀜
        // 사용자 비밀번호 체크
        // authenticate가 실행될 때 CustomUserDetailsService의 loadUserByUsername 메서드 실행
        // 암호화된 비밀번호를 가진 userDetails와 암호화되지 않은 authenticationToken의 password간 비교
        try{
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
            return tokenInfo;
        }
        catch (Exception e){ System.out.println(e.getMessage());}

        //jwt 토큰 생성
        return null;
    }
}
