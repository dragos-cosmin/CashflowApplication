package com.example.cashflow.service;

import com.example.cashflow.model.Client;
import com.example.cashflow.model.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/
public interface ClientsService {

    List<Client> findAll();

    void save(Client client);

    Optional<Client> findById(Long id);

    void updateBalance();

    void deleteById(Long id);
}
