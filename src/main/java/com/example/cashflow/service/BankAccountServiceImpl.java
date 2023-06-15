package com.example.cashflow.service;

import com.example.cashflow.model.BankAccount;
import com.example.cashflow.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
@Service
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Override
    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    @Override
    public void save(BankAccount bankExtra) {
        bankAccountRepository.save(bankExtra);
    }

    @Override
    public BankAccount findByDate(LocalDate date) {
        if(bankAccountRepository.findByDate(date)!=null){
            return bankAccountRepository.findByDate(date);
        } else return new BankAccount();
//        return bankAccountRepository.findByDate(date);

    }

    @Override
    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);
    }
}
