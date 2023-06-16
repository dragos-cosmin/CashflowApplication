package com.example.cashflow.service;

import com.example.cashflow.model.Client;
import com.example.cashflow.model.Supplier;
import com.example.cashflow.repository.ClientsRepository;
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
public class ClientsServiceImpl implements ClientsService {

    public Logger logger = LoggerFactory.getLogger(SuppliersService.class);

    @Autowired
    private ClientsRepository clientsRepository;


    @Override
    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    @Override
    public void save(Client client) {
        clientsRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientsRepository.findById(id);
    }

    @Override
    public void updateBalance() {
        List<Client> clients = findAll();
        clients.forEach(client -> {
            client.setBalance(BigDecimal.valueOf(client.getInvoices().stream()
                                                           .mapToDouble(invoice -> invoice.getBalance().doubleValue())
                                                           .sum()));
            clientsRepository.save(client);
        });

    }

    @Override
    public void deleteById(Long id) {
        Client deleteClient = clientsRepository.findById(id).get();
        if (deleteClient.getInvoices().isEmpty()) {
            clientsRepository.deleteById(id);
        }
        else {
            logger.warn("Cannot delete " + deleteClient.getName() + "! Client has invoices associated with it!");
        }

    }
}
