package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author dragos.cosmin
 **/

@Entity
public class SupplierInvoice implements Comparable<SupplierInvoice>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serial;

    @Column(name = "date")
    private LocalDate a_date;

    @ManyToOne
    private Supplier supplier=new Supplier();
    private BigDecimal value=BigDecimal.ZERO;
    private BigDecimal payments=BigDecimal.ZERO;
    private BigDecimal balance=BigDecimal.ZERO;

    private LocalDate dueDate;

    public SupplierInvoice() {
    }

    public SupplierInvoice(String serial, LocalDate date, Supplier supplier, BigDecimal value) {
        this.serial = serial;
        this.a_date = date;
        this.dueDate=date.plusDays(supplier.getPaymentDelay());
        this.supplier = supplier;
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.payments=BigDecimal.ZERO;
        this.balance=value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }

    public SupplierInvoice(String serial, LocalDate date, Supplier supplier, BigDecimal value, BigDecimal payments) {
        this.serial = serial;
        this.a_date = date;
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
        this.dueDate=this.a_date.plusDays(supplier.getPaymentDelay());
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
        this.balance=this.value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getPayments() {
        return payments;
    }

    public void setPayments(BigDecimal payments) {
        this.payments = payments;
        this.balance=this.value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDate() {
        return a_date;
    }

    public void setDate(LocalDate date) {
        this.a_date = date;
        this.dueDate= a_date.plusDays(this.supplier.getPaymentDelay());
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void addPayment(BigDecimal newPayment){
        this.payments=this.payments.add(newPayment).setScale(2,RoundingMode.HALF_UP);
        this.balance=this.value.subtract(this.payments).setScale(2,RoundingMode.HALF_UP);
    }


    @Override
    public int compareTo(SupplierInvoice s) {
        if (this.a_date.isBefore(s.getDate())){
            return -1;
        } else if (this.a_date.isAfter(s.getDate())){
            return 1;
        } else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierInvoice that = (SupplierInvoice) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
