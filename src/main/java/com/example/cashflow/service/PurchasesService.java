package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.model.SupplierInvoice;

import java.util.List;

/**
 * @author dragos.cosmin
 **/
public interface PurchasesService {
    List<SupplierInvoice> findAll();

    void save(SupplierInvoice supplierInvoice);
}
