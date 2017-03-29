package com.nts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Created by subramanya on 15/03/17.
 */
@Entity
@NamedQuery(name = "Transaction.findTransactionsSinceLastSixtySeconds", query = "SELECT t FROM Transaction t WHERE t.time >= ?1")
@Table(name = "transactions")
public class Transaction {
    @Column(name = "amount", nullable = false)
    private double amount = 0.0;
    @Column(name = "time", nullable = false)
    private long time = 0L;

    public Transaction(double amount, long time) {
        this.amount = amount;
        this.time = time;
    }

    public Transaction() {
        // for jpa
    }

    public double getAmount() {
        return amount;
    }

    public long getTime() {
        return time;
    }
}
