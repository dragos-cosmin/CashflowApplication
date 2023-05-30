package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bank_financial_records")
public class Transaction implements Comparable<Transaction>{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fin_rec_date")
    private LocalDate date;

    @Column(name = "fin_type")
    private FinancialType financialType;
    private BigDecimal amount;
    private String observation;

    private BigDecimal balance;

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

    public FinancialType getFinancialType() {
        return financialType;
    }

    public void setFinancialType(FinancialType financialType) {
        this.financialType = financialType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void updateBalance(BigDecimal initialBalance){
        if(this.financialType==FinancialType.ENCASHMENT){
            this.balance=initialBalance.add(this.amount);
        } else this.balance=initialBalance.subtract(this.amount);
    }



    @Override
    public int compareTo(Transaction t) {
        if (this.date.isBefore(t.getDate())){
            return -1;
        } else if (this.date.isAfter(t.getDate())){
            return 1;
        } else return 0;
    }
}
