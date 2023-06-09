package com.example.cashflow.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * @author dragos.cosmin
 **/

@Entity
public class ClientInvoice implements Comparable<ClientInvoice> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serial;

    @Column(name = "date")
    private LocalDate a_date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client = new Client();
    private BigDecimal value = BigDecimal.ZERO;
    private BigDecimal payments = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDate dueDate;

    @OneToMany(mappedBy = "clientInvoice", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public ClientInvoice() {
    }

    public ClientInvoice(String serial, LocalDate date, Client client, BigDecimal value) {
        this.serial = serial;
        this.a_date = date;
        this.dueDate = date.plusDays(client.getPaymentDelay());
        this.client = client;
        this.value = value.setScale(2, RoundingMode.HALF_UP);
        this.payments = BigDecimal.ZERO;
        this.balance = value.subtract(this.payments).setScale(2, RoundingMode.HALF_UP);
    }

    public ClientInvoice(String serial, LocalDate date, Client client, BigDecimal value, BigDecimal payments) {
        this.serial = serial;
        this.a_date = date;
        this.dueDate = date.plusDays(client.getPaymentDelay());
        this.client = client;
        this.value = value;
        this.payments = payments;
        this.balance = value.subtract(payments).setScale(2, RoundingMode.HALF_UP);
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.dueDate = this.a_date.plusDays(client.getPaymentDelay());
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
        this.balance = this.value.subtract(this.payments).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPayments() {
        return payments;
    }

    public void setPayments(BigDecimal payments) {
        this.payments = payments;
        this.balance = this.value.subtract(this.payments).setScale(2, RoundingMode.HALF_UP);
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
        this.dueDate = a_date.plusDays(this.client.getPaymentDelay());
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addPayment(BigDecimal newPayment) {
        this.payments = this.payments.add(newPayment).setScale(2, RoundingMode.HALF_UP);
        this.balance = this.value.subtract(this.payments).setScale(2, RoundingMode.HALF_UP);
    }


    @Override
    public int compareTo(ClientInvoice s) {
        if (this.a_date.isBefore(s.getDate())) {
            return -1;
        }
        else if (this.a_date.isAfter(s.getDate())) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientInvoice that = (ClientInvoice) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
