package com.stock.advice.account.service;

import com.stock.advice.account.domain.Account;
import com.stock.advice.account.domain.AccountHistory;
import com.stock.advice.account.domain.AccountHistoryType;
import com.stock.advice.account.dto.GetAccountHistoriesDto;
import com.stock.advice.account.repository.AccountHistoryRepository;
import com.stock.advice.account.repository.AccountRepository;
import com.stock.advice.member.domain.Member;
import com.stock.advice.member.repository.MemberRepository;
import com.stock.advice.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberService memberService;
    private final AccountHistoryRepository accountHistoryRepository;
    private final String ACCOUNT_NOT_EXIST = "계좌가 존재하지 않습니다.";

    @Transactional
    public long makeAccount(String memberId){
        Member findMember = memberService.findMember(memberId);
        if(findMember.getAccount()!=null) throw new IllegalArgumentException("이미 계좌가 존재합니다.");
        Account account = Account.builder()
                .balance(0)
                .member(findMember)
                .build();
        findMember.setAccount(account);
        accountRepository.save(account);
        return account.getId();
    }
    public int getAccountBalance(String memberId){
        Member findMember = memberService.findMember(memberId);
        if(findMember.getAccount()==null) throw new IllegalArgumentException(ACCOUNT_NOT_EXIST);
        return findMember.getAccount().getBalance();
    }
    public List<GetAccountHistoriesDto> getAccountHistories(String memberId){
        Member findMember = memberService.findMember(memberId);
        if(findMember.getAccount()==null) throw new IllegalArgumentException(ACCOUNT_NOT_EXIST);
        Optional<Account> account = accountRepository.getAccountByIdFetchAccountHistories(findMember.getAccount().getId());
        return account.get().getAccountHistories().stream()
                .map(accountHistory->{
                    return GetAccountHistoriesDto.builder()
                            .localDateTime(accountHistory.getLocalDateTime())
                            .accountHistoryType(accountHistory.getAccountHistoryType())
                            .amount(accountHistory.getAmount())
                            .temporaryBalance(accountHistory.getTemporaryBalance())
                            .build();
                })
                .collect(Collectors.toList());
    }
    public int withDraw(String memberId,int amount){
        Member findMember = memberService.findMember(memberId);
        if(findMember.getAccount()==null) throw new IllegalArgumentException(ACCOUNT_NOT_EXIST);
        Account account = findMember.getAccount();
        account.withDraw(amount);
        makeAccountHistory(account, amount, AccountHistoryType.WITHDRAW);
        return account.getBalance();
    }
    public int deposit(String memberId,int amount){
        Member findMember = memberService.findMember(memberId);
        if(findMember.getAccount()==null) throw new IllegalArgumentException(ACCOUNT_NOT_EXIST);
        Account account = findMember.getAccount();
        account.deposit(amount);
        makeAccountHistory(account, amount, AccountHistoryType.DEPOSIT);
        return account.getBalance();
    }
    private void makeAccountHistory(Account account, int amount, AccountHistoryType deposit) {
        AccountHistory accountHistory = AccountHistory.builder()
                .account(account)
                .amount(amount)
                .temporaryBalance(account.getBalance())
                .localDateTime(LocalDateTime.now())
                .accountHistoryType(deposit)
                .build();
        accountHistoryRepository.save(accountHistory);
    }

}
