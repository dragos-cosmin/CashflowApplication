package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.model.SupplierInvoice;
import com.example.cashflow.repository.PurchasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dragos.cosmin
 **/
@Service
public class PurchasesServiceImpl implements PurchasesService{

    @Autowired
    private PurchasesRepository purchasesRepository;


    @Override
    public List<SupplierInvoice> findAll() {
        return purchasesRepository.findAll().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public void save(SupplierInvoice supplierInvoice) {
        purchasesRepository.save(supplierInvoice);
    }
}
