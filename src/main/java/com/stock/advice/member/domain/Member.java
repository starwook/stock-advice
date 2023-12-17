package com.stock.advice.member.domain;


import com.stock.advice.account.Account;
import com.stock.advice.stock.StockMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String memberId;
    private String password;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<LoginHistory> loginHistories;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<StockMember> stockMembers;
    @OneToOne
    private Account account;

}
