package com.stock.advice.member.service;

import com.stock.advice.auth.JwtTokenProvider;
import com.stock.advice.auth.dto.TokenInfo;
import com.stock.advice.member.domain.LoginHistory;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.dto.request.CreateMemberDto;
import com.stock.advice.member.dto.respond.LoginHistoryDto;
import com.stock.advice.member.repository.LoginHistoryRepository;
import com.stock.advice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final LoginHistoryRepository loginHistoryRepository;
    private final String USER_NOT_FOUND_EXCEPTION_MESSAGE ="존재하지 않는 유저 ID";
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
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        Optional<Member> member = memberRepository.findMemberByMemberId(memberId);
        LoginHistory loginHistory = LoginHistory.builder()
                .localDateTime(LocalDateTime.now())
                .member(member.get())
                .build();
        loginHistoryRepository.save(loginHistory);
        return tokenInfo;
    }

    @Transactional
    public List<LoginHistoryDto> getLoginHistories(String memberId){
        Optional<Member> member = memberRepository.findMemberByMemberId(memberId);
        if(member.isPresent()) {
            return member.get().getLoginHistories()
                    .stream()
                    .map(loginHistory -> {
                        return LoginHistoryDto.builder()
                                        .localDateTime(loginHistory.getLocalDateTime())
                                                .build();
                    }).collect(Collectors.toList());
        }
        return null;
    }
    public Member findMember(String memberId){
        Optional<Member> member = memberRepository.findMemberByMemberId(memberId);
        if(member.isPresent()) return member.get();
        else throw new UsernameNotFoundException(USER_NOT_FOUND_EXCEPTION_MESSAGE);
    }


}
