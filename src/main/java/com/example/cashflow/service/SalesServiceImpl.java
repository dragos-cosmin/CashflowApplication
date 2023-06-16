package com.example.cashflow.service;

import com.example.cashflow.model.ClientInvoice;
import com.example.cashflow.model.SupplierInvoice;
import com.example.cashflow.repository.PurchasesRepository;
import com.example.cashflow.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author dragos.cosmin
 **/
@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;


    @Override
    public List<ClientInvoice> findAll() {
        return salesRepository.findAll().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public void save(ClientInvoice clientInvoice) {
        salesRepository.save(clientInvoice);
    }

    @Override
    public Optional<ClientInvoice> findById(Long id) {
        return salesRepository.findById(id);
    }
}
