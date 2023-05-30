package com.example.cashflow.service;

import com.example.cashflow.model.Transaction;

import java.util.List;

public interface TransactionsService {

    List<Transaction> findAll();
}
