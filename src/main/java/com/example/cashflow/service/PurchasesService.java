package com.example.cashflow.service;

import com.example.cashflow.model.SupplierInvoice;

import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
public interface PurchasesService {
    List<SupplierInvoice> findAll();

    void save(SupplierInvoice supplierInvoice);

    Optional<SupplierInvoice> findById(Long id);
}
