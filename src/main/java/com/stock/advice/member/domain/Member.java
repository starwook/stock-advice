package com.stock.advice.member.domain;


import com.stock.advice.account.domain.Account;
import com.stock.advice.stock.StockMember;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String memberId;
    private String password;
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<LoginHistory> loginHistories = new ArrayList<>();
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<StockMember> stockMembers = new ArrayList<>();
    @OneToOne(mappedBy = "member",cascade = CascadeType.ALL)
    private Account account;

    public void setAccount(Account account){
        this.account = account;
        account.setMember(this);
    }

}
