package com.bitbank.repository.v1;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.v1.BankAccountTransactionsEntity;

@Repository
public interface BankAccountTransactionsRepository extends CrudRepository<BankAccountTransactionsEntity, Long> {

}
