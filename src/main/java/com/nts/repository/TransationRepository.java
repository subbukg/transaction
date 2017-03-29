package com.nts.repository;

import com.nts.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by subramanya on 16/03/17.
 */
public interface TransationRepository extends JpaRepository<Transaction, Long> {

}



