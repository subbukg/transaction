package com.nts.app.controller;

import com.nts.entity.Transaction;
import com.nts.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.nts.repository.TransationRepository;
import com.nts.repository.StatisticsRepository;

import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by subramanya on 14/03/17.
 */
@RestController
public class TransactionController {

    private final TransationRepository transationRepository;

    private final StatisticsRepository statisticsRepository;

    @Autowired
    TransactionController(TransationRepository transationRepository, StatisticsRepository statisticsRepository) {
        this.transationRepository = transationRepository;
        this.statisticsRepository = statisticsRepository;
    }


    /**
     * POST method
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public synchronized void addTransaction(@RequestBody Transaction currentTransaction) {
        transationRepository.save(new Transaction(currentTransaction.getAmount(), currentTransaction.getTime()));
    }

    /**
     *  Get Statistics:
     *      This method must run in constant time and memorary O(1)
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/statistics")
    public synchronized  Statistics  getStatistics() {

        long transactionStartTime = getTransactionStartTime();
        HashSet<Transaction> transactions = statisticsRepository.findTransactionsSinceLastSixtySeconds(transactionStartTime);
        Statistics statistics = computeStatistics(transactions);
        return statistics;
    }

    private long getTransactionStartTime() {
        long currentTime = System.currentTimeMillis();
        long sixtySecondsInMillis = TimeUnit.SECONDS.toMillis(60);
        return currentTime - sixtySecondsInMillis;
    }

    private Statistics computeStatistics(HashSet<Transaction> resultTransactions) {
        double sum = 0.0;
        double avg = 0.0;
        double max = 0.0;
        double min = 0.0;
        long count = 0;

        DoubleSummaryStatistics summaryStatistics = resultTransactions.stream().mapToDouble(d -> d.getAmount() ).summaryStatistics();
        avg=   summaryStatistics.getAverage();
        sum = summaryStatistics.getSum();
        max = summaryStatistics.getMax();
        min = summaryStatistics.getMin();
        count = summaryStatistics.getCount();

        return new Statistics(sum, avg, min, max,count );
    }



}
