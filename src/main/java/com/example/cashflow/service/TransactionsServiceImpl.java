package com.example.cashflow.service;

import com.example.cashflow.model.Transaction;
import com.example.cashflow.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService{

    @Autowired
    private TransactionsRepository transactionsRepository;


    @Override
    public List<Transaction> findAll() {
        List<Transaction> sortedTransactions = transactionsRepository.findAll().stream().sorted().toList();
//
        return sortedTransactions;
    }

    @Override
    public void updateBalance(BigDecimal initial) {
        List<Transaction> sortedTransactions = transactionsRepository.findAll().stream().sorted().toList();
        for (Transaction transaction :
                sortedTransactions) {
            int currentIndex=sortedTransactions.indexOf(transaction);
            if (currentIndex == 0) {
                transaction.updateBalance(initial);
            } else {
                transaction.updateBalance(sortedTransactions.get(currentIndex-1).getBalance());
            }
        }
    }

//    public void updateBalances(BigDecimal initialBalance, List<Transaction> transactions){
//        for (Transaction transaction :
//                transactions) {
//            int currentIndex=transactions.indexOf(transaction);
//            if (currentIndex == 0) {
//                transaction.updateBalance(initialBalance);
//            } else {
//                transaction.updateBalance(transactions.get(currentIndex-1).getBalance());
//            }
//        }
//
//    }
}
