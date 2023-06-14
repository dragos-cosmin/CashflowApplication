package com.example.cashflow.service;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.repository.SuppliersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
@Service
public class SuppliersServiceImpl implements SuppliersService {

    public Logger logger = LoggerFactory.getLogger(SuppliersService.class);

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

    @Override
    public Optional<Supplier> findById(Long id) {
        return suppliersRepository.findById(id);
    }

    @Override
    public void updateBalance() {
        List<Supplier> suppliers = findAll();
        suppliers.forEach(supplier -> {
            supplier.setBalance(BigDecimal.valueOf(supplier.getInvoices().stream()
                                                           .mapToDouble(invoice -> invoice.getBalance().doubleValue())
                                                           .sum()));
            suppliersRepository.save(supplier);
        });

    }

    @Override
    public void deleteById(Long id) {
        Supplier deleteSupplier = suppliersRepository.findById(id).get();
        if (deleteSupplier.getInvoices().isEmpty()) {
            suppliersRepository.deleteById(id);
        }
        else {
            logger.warn("Cannot delete " + deleteSupplier.getName() + "! Supplier has invoices associated with it!");
        }

    }
}
