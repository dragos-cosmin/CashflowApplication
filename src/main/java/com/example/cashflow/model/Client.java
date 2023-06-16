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
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cui;
    private String name;

    private int paymentDelay;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientInvoice> invoices = new ArrayList<>();
    private BigDecimal balance = BigDecimal.ZERO;

    public Client() {
    }

    public Client(String cui, String name) {
        this(cui, name, 0);
    }

    public Client(String cui, String name, int paymentDelay) {
        this.cui = cui;
        this.name = name;
        this.paymentDelay = paymentDelay;
    }

    public Client(String cui, String name, int paymentDelay, List<ClientInvoice> invoices) {
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

    public List<ClientInvoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<ClientInvoice> invoices) {
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

    public void updateBalance() {

        for (ClientInvoice invoice :
                this.invoices) {
            this.balance = this.balance.add(invoice.getBalance()).setScale(2, RoundingMode.HALF_UP);
        }

    }

    public void addClientInvoice(ClientInvoice invoice) {
        this.invoices.add(invoice);
        updateBalance();
    }

    public double getTotalInvoicesValue(){
        return invoices.stream().mapToDouble(invoice -> invoice.getValue().doubleValue()).sum();
    }

    public double getTotalPayments(){
        return invoices.stream().mapToDouble(invoice->invoice.getPayments().doubleValue()).sum();
    }
}
