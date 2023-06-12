package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dragos.cosmin
 **/
@Entity
@Table(name = "suppliers")
public class Supplier{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cui;
    private String name;

    private int paymentDelay;

    @OneToMany(mappedBy = "supplier",cascade = CascadeType.ALL)
    private List<SupplierInvoice> invoices=new ArrayList<>();
    private BigDecimal balance;

    public Supplier() {
    }

    public Supplier(String cui, String name) {
        this(cui,name,0);
    }

    public Supplier(String cui, String name, int paymentDelay) {
        this.cui = cui;
        this.name = name;
        this.paymentDelay = paymentDelay;
        this.balance=BigDecimal.ZERO;
    }

    public Supplier(String cui, String name, int paymentDelay, List<SupplierInvoice> invoices) {
        this.cui = cui;
        this.name = name;
        this.paymentDelay = paymentDelay;
        this.invoices = invoices;
        updateBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SupplierInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<SupplierInvoice> invoices) {
        this.invoices = invoices;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getPaymentDelay() {
        return paymentDelay;
    }

    public void setPaymentDelay(int paymentDelay) {
        this.paymentDelay = paymentDelay;
    }

    public void updateBalance(){
        BigDecimal newBalance=BigDecimal.ZERO;
        for (SupplierInvoice invoice :
                this.invoices) {
            newBalance=newBalance.add(invoice.getBalance()).setScale(2, RoundingMode.HALF_UP);
        }
        this.balance=newBalance;
    }

    public void addSupplierInvoice(SupplierInvoice invoice){
        this.invoices.add(invoice);
        updateBalance();
    }
}
