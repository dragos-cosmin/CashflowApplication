package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;

import java.util.List;

/**
 * @author dragos.cosmin
 **/
public interface SuppliersService {

    List<Supplier> findAll();

    void save(Supplier supplier);
}
