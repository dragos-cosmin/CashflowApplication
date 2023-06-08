package com.example.cashflow.service;

import com.example.cashflow.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionsService {

    List<Transaction> findAll();

    void updateBalance(BigDecimal initial);

    void save(Transaction transaction);
}
