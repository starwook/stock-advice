package com.stock.advice.account.repository;

import com.stock.advice.account.domain.Account;
import com.stock.advice.account.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query("select a from Account a left join fetch a.accountHistories where a.id = :accountId")
    Optional<Account> getAccountByIdFetchAccountHistories(@Param("accountId") Long accountId);
}
