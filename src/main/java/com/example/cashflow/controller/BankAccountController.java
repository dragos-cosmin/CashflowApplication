package com.example.cashflow.controller;

import com.example.cashflow.model.BankAccount;
import com.example.cashflow.model.Supplier;
import com.example.cashflow.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dragos.cosmin
 **/
@Controller
@RequestMapping(value = "/bankAccount")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("")
    public String getSuppliers(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {

        List<BankAccount> extras = bankAccountService.findAll();

        model.addAttribute("extras", extras);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "bankAccountExtras";
    }

    @GetMapping("/add")
    public String supplierForm(Model model) {
        model.addAttribute("bankExtra", new BankAccount());

        return "bankAccountExtra";
    }

    @PostMapping("/add")
    public String supplierSubmit(@ModelAttribute BankAccount bankExtra,
                                 Model model) {
        model.addAttribute("bankExtra", bankExtra);
        bankAccountService.save(bankExtra);
        return "redirect:/bankAccount";
    }
}
