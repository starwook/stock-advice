package com.stock.advice.account.repository;

import com.stock.advice.account.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory,Long> {
}
