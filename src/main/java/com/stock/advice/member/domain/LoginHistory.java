package com.stock.advice.member.domain;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LoginHistory {
    @Id @GeneratedValue
    private Long id;
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
