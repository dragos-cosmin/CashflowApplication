package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.repository.SuppliersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dragos.cosmin
 **/
@Service
public class SuppliersServiceImpl implements SuppliersService{

    @Autowired
    private SuppliersRepository suppliersRepository;


    @Override
    public List<Supplier> findAll() {
        return suppliersRepository.findAll();
    }

    @Override
    public void save(Supplier supplier) {
        suppliersRepository.save(supplier);
    }
}
