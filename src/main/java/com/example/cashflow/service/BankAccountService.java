package com.example.cashflow.service;

import com.example.cashflow.model.BankAccount;
import com.example.cashflow.model.Supplier;

import java.time.LocalDate;
import java.util.List;

/**
 * @author dragos.cosmin
 **/
public interface BankAccountService {
    List<BankAccount> findAll();

    void save(BankAccount bankExtra);

    BankAccount findByDate(LocalDate date);
}
