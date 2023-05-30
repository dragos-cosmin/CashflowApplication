package com.example.cashflow.repository;

import com.example.cashflow.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface TransactionsRepository extends ListCrudRepository<Transaction,Long> {
}
