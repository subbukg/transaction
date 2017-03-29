package com.nts.app;

import com.nts.entity.Transaction;
import com.nts.repository.StatisticsRepository;
import com.nts.repository.TransationRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}


}
