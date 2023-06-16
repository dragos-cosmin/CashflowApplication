package com.example.cashflow.repository;

import com.example.cashflow.model.ClientInvoice;
import com.example.cashflow.model.SupplierInvoice;
import org.springframework.data.repository.ListCrudRepository;

/**
 * @author dragos.cosmin
 **/
public interface SalesRepository extends ListCrudRepository<ClientInvoice, Long> {
}
