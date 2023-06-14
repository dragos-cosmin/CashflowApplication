package com.example.cashflow.repository;

import com.example.cashflow.model.SupplierInvoice;
import org.springframework.data.repository.ListCrudRepository;

/**
 * @author dragos.cosmin
 **/
public interface PurchasesRepository extends ListCrudRepository<SupplierInvoice, Long> {
}
