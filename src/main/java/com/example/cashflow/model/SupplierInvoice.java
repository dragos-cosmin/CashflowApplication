package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @author dragos.cosmin
 **/

@Entity
public class SupplierInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serial;

    private LocalDate date;

    @ManyToOne
    private Supplier supplier;
    private BigDecimal value;
    private BigDecimal payments;
    private BigDecimal balance;

    private LocalDate dueDate;

    public SupplierInvoice(String serial, LocalDate date, Supplier supplier, BigDecimal value) {
        this.serial = serial;
        this.date = date;
        this.dueDate=date.plusDays(supplier.getPaymentDelay());
        this.supplier = supplier;
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.payments=BigDecimal.ZERO;
        this.balance=value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }

    public SupplierInvoice(String serial, LocalDate date, Supplier supplier, BigDecimal value, BigDecimal payments) {
        this.serial = serial;
        this.date = date;
        this.dueDate=date.plusDays(supplier.getPaymentDelay());
        this.supplier = supplier;
        this.value = value;
        this.payments = payments;
        this.balance=value.subtract(payments).setScale(2,RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getPayments() {
        return payments;
    }

    public void setPayments(BigDecimal payments) {
        this.payments = payments;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dueDate=date.plusDays(this.supplier.getPaymentDelay());
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void addPayment(BigDecimal newPayment){
        this.payments=this.payments.add(newPayment).setScale(2,RoundingMode.HALF_UP);
        this.balance=this.value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }


}
