package com.example.cashflow.repository;

import com.example.cashflow.model.Supplier;
import org.springframework.data.repository.ListCrudRepository;

/**
 * @author dragos.cosmin
 **/
public interface SuppliersRepository extends ListCrudRepository<Supplier, Long> {
}
