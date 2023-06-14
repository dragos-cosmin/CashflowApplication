package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
public interface SuppliersService {

    List<Supplier> findAll();

    void save(Supplier supplier);

    Optional<Supplier> findById(Long id);

    void updateBalance();

    void deleteById(Long id);
}
