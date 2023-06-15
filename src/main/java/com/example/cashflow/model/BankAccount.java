package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author dragos.cosmin
 **/
@Entity
@Table(name = "bank_account_extras")
public class BankAccount implements Comparable<BankAccount> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private BigDecimal balance = BigDecimal.ZERO;

    public BankAccount() {
    }

    public BankAccount(LocalDate date, BigDecimal balance) {
        this.date = date;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public int compareTo(BankAccount b) {
        if (this.date.isBefore(b.getDate())) {
            return -1;
        }
        else if (this.date.isAfter(b.getDate())) {
            return 1;
        }
        else {
            return 0;
        }


    }
}
