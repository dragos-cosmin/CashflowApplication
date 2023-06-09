package com.example.cashflow.service;

import com.example.cashflow.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionsService {

    List<Transaction> findAll();

    void updateBalance(BigDecimal initial);

    void save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    void deleteById(Long id);
}
