package com.example.cashflow.controller;

import com.example.cashflow.model.Client;
import com.example.cashflow.model.Supplier;
import com.example.cashflow.service.ClientsService;
import com.example.cashflow.service.PurchasesService;
import com.example.cashflow.service.SalesService;
import com.example.cashflow.service.SuppliersService;
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
@RequestMapping(value = "/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientsService;

    @Autowired
    private SalesService salesService;


    @GetMapping("")
    public String getClients(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {
        clientsService.updateBalance();
        List<Client> clients = clientsService.findAll();

        model.addAttribute("clients", clients);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "clients";
    }

    @GetMapping("/add")
    public String clientForm(Model model) {
        model.addAttribute("client", new Client());

        return "client";
    }

    @PostMapping("/add")
    public String clientSubmit(@ModelAttribute Client client,
                                 Model model) {
        model.addAttribute("client", client);
        clientsService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/edit/{id}")
    public String clientEdit(@PathVariable Long id,
                               Model model) {
        Optional<Client> optClient = clientsService.findById(id);

        optClient.ifPresent(client -> model.addAttribute("client", client));

        return "clientEdit";
    }

    @PostMapping("/edit/{id}")
    public String clientEditSubmit(@ModelAttribute Client editedClient,

                                     Model model) {
        model.addAttribute("client", editedClient);
        Client client = clientsService.findById(editedClient.getId()).get();
        client.setCui(editedClient.getCui());
        client.setName(editedClient.getName());
        client.setPaymentDelay(editedClient.getPaymentDelay());
        client.setBalance(editedClient.getBalance());
        clientsService.save(client);


        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String clientDelete(@PathVariable Long id,
                                 Model model) {
        clientsService.deleteById(id);

        return "redirect:/clients";
    }

    @GetMapping("/details/{id}")
    public String clientDetails(@PathVariable Long id,
                               Model model) {
        Optional<Client> optClient = clientsService.findById(id);
        double[] totalValue = {0.0};
        double[] totalPayments={0.0};

        optClient.ifPresent(client -> {
            model.addAttribute("client", client);
            totalValue[0] =client.getTotalInvoicesValue();
            totalPayments[0]=client.getTotalPayments();
            model.addAttribute("totalValue",totalValue[0]);
            model.addAttribute("totalPayments",totalPayments[0]);
        });

        return "clientDetails";
    }


}
