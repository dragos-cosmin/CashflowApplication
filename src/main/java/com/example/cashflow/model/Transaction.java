package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bank_financial_records")
public class Transaction implements Comparable<Transaction>{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private LocalDate a_date;

    @Column(name = "fin_type")
    private FinancialType financialType;

    @Column(name = "amount")
    private BigDecimal d_amount;

    @Column(name = "obs")
    private String b_observation;

    @Column(name = "balance")

    private BigDecimal g_balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return a_date;
    }

    public void setDate(LocalDate date) {
        this.a_date = date;
    }

    public FinancialType getFinancialType() {
        return financialType;
    }

    public void setFinancialType(FinancialType financialType) {
        this.financialType = financialType;
    }

    public BigDecimal getAmount() {
        return d_amount;
    }

    public void setAmount(BigDecimal amount) {
        this.d_amount = amount;
    }

    public String getObservation() {
        return b_observation;
    }

    public void setObservation(String observation) {
        this.b_observation = observation;
    }

    public BigDecimal getBalance() {
        return g_balance;
    }

    public void setBalance(BigDecimal balance) {
        this.g_balance = balance;
    }

    public void updateBalance(BigDecimal initialBalance){
        if(this.financialType==FinancialType.ENCASHMENT){
            this.g_balance =initialBalance.add(this.d_amount);
        } else this.g_balance =initialBalance.subtract(this.d_amount);
    }



    @Override
    public int compareTo(Transaction t) {
        if (this.a_date.isBefore(t.getDate())){
            return -1;
        } else if (this.a_date.isAfter(t.getDate())){
            return 1;
        } else return 0;
    }
}
