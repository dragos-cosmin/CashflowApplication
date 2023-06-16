package com.example.cashflow.controller;

import com.example.cashflow.model.ClientInvoice;
import com.example.cashflow.model.FinancialType;
import com.example.cashflow.model.SupplierInvoice;
import com.example.cashflow.model.Transaction;
import com.example.cashflow.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author dragos.cosmin
 **/

@Controller
@RequestMapping(value = "/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("")
    public String getSales(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {

        List<ClientInvoice> sales = salesService.findAll();
        clientsService.updateBalance();

        model.addAttribute("sales", sales);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "sales";
    }

    @GetMapping("/add")
    public String saleForm(Model model) {
        model.addAttribute("clientInvoice", new ClientInvoice());
        model.addAttribute("clients", clientsService.findAll());


        return "sale";
    }

    @PostMapping("/add")
    public String saleSubmit(@ModelAttribute ClientInvoice clientInvoice,
                                 Model model) {
        Transaction transaction = new Transaction();

        model.addAttribute("clientInvoice", clientInvoice);
        clientInvoice.setClient(clientsService.findById(clientInvoice.getClient().getId()).get());
        clientInvoice.getClient().addClientInvoice(clientInvoice);
        transaction.setClientInvoice(clientInvoice);
        setTransaction(transaction);

        transactionsService.save(transaction);

        salesService.save(clientInvoice);
        return "redirect:/sales";
    }

    private void setTransaction(Transaction transaction) {
        transaction.setDate(transaction.getClientInvoice().getDueDate());
        transaction.setFinancialType(FinancialType.ENCASHMENT);
        if (transaction.getClientInvoice().getBalance().doubleValue() == 0.0) {
            transaction.setAmount(transaction.getClientInvoice().getValue());
        }
        else {
            transaction.setAmount(transaction.getClientInvoice().getBalance());
        }
        transaction.setObservation("encasment owed by " + transaction.getClientInvoice().getClient().getName());
    }

    @GetMapping("/edit/{id}")
    public String saleEdit(@PathVariable Long id,
                               Model model) {
        Optional<ClientInvoice> optClientInvoice = salesService.findById(id);
        model.addAttribute("clients", clientsService.findAll());
        optClientInvoice.ifPresent(clientInvoice -> model.addAttribute("clientInvoice", clientInvoice));

        return "saleEdit";
    }

    @PostMapping("/edit/{id}")
    public String saleEditSubmit(@ModelAttribute ClientInvoice editedClientInvoice,

                                     Model model) {
        model.addAttribute("clientInvoice", editedClientInvoice);
        ClientInvoice clientInvoice = salesService.findById(editedClientInvoice.getId()).get();
        clientInvoice.setClient(clientsService.findById(editedClientInvoice.getClient().getId()).get());
        clientInvoice.setDate(editedClientInvoice.getDate());
        clientInvoice.setSerial(editedClientInvoice.getSerial());
        clientInvoice.setValue(editedClientInvoice.getValue());
        clientInvoice.setPayments(editedClientInvoice.getPayments());
        clientInvoice.getClient().addClientInvoice(clientInvoice);
        salesService.save(clientInvoice);


        return "redirect:/sales";
    }
}
