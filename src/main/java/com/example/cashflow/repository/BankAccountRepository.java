package com.example.cashflow.repository;

import com.example.cashflow.model.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDate;

/**
 * @author dragos.cosmin
 **/
public interface BankAccountRepository extends ListCrudRepository<BankAccount,Long> {
    @Query(value = "SELECT * FROM bank_account_extras WHERE date = ?",nativeQuery = true)
    BankAccount findByDate(LocalDate date);
}
