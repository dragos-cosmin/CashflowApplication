package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "bank_financial_records")
public class Transaction implements Comparable<Transaction> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(fetch = FetchType.LAZY)
    private SupplierInvoice supplierInvoice;

    private boolean dayEnd=false;

    private BigDecimal bankAccountBalance;

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
        this.d_amount = amount.setScale(2, RoundingMode.HALF_UP);
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

    public SupplierInvoice getSupplierInvoice() {
        return supplierInvoice;
    }

    public void setSupplierInvoice(SupplierInvoice supplierInvoice) {
        this.supplierInvoice = supplierInvoice;
    }

    public boolean isDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(boolean dayEnd) {
        this.dayEnd = dayEnd;
    }

    public BigDecimal getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(BigDecimal bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    public void updateBalance(BigDecimal initialBalance) {
        if (this.financialType == FinancialType.ENCASHMENT) {
            this.g_balance = initialBalance.add(this.d_amount).setScale(2, RoundingMode.HALF_UP);
        }
        else {
            this.g_balance = initialBalance.subtract(this.d_amount).setScale(2, RoundingMode.HALF_UP);
        }
    }


    @Override
    public int compareTo(Transaction t) {
        if (this.a_date.isBefore(t.getDate())) {
            return -1;
        }
        else if (this.a_date.isAfter(t.getDate())) {
            return 1;
        }
        else if (this.financialType == FinancialType.ENCASHMENT) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
