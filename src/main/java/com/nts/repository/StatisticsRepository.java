package com.nts.repository;

import com.nts.entity.Transaction;
import com.nts.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.List;

/**
 * Created by subramanya on 16/03/17.
 */
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

HashSet<Transaction> findTransactionsSinceLastSixtySeconds(long transactionStartTime);

}
