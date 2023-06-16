package com.example.cashflow.service;

import com.example.cashflow.model.ClientInvoice;
import com.example.cashflow.model.SupplierInvoice;

import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
public interface SalesService {
    List<ClientInvoice> findAll();

    void save(ClientInvoice clientInvoice);

    Optional<ClientInvoice> findById(Long id);
}
