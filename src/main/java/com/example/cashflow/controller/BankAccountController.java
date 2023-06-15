package com.example.cashflow.controller;

import com.example.cashflow.model.BankAccount;
import com.example.cashflow.model.Supplier;
import com.example.cashflow.service.BankAccountService;
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
@RequestMapping(value = "/bankAccount")
public class BankAccountController {
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("")
    public String getExtras(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {

        List<BankAccount> extras = bankAccountService.findAll();

        model.addAttribute("extras", extras);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "bankAccountExtras";
    }

    @GetMapping("/add")
    public String extrasForm(Model model) {
        model.addAttribute("bankExtra", new BankAccount());

        return "bankAccountExtra";
    }

    @PostMapping("/add")
    public String extrasSubmit(@ModelAttribute BankAccount bankExtra,
                                 Model model) {
        model.addAttribute("bankExtra", bankExtra);
        bankAccountService.save(bankExtra);
        return "redirect:/bankAccount";
    }

    @GetMapping("/edit/{id}")
    public String extrasEdit(@PathVariable Long id,
                               Model model) {
        Optional<BankAccount> optExtra = bankAccountService.findById(id);

        optExtra.ifPresent(extra -> model.addAttribute("bankExtra", extra));

        return "bankAccountExtraEdit";
    }

    @PostMapping("/edit/{id}")
    public String extrasEditSubmit(@ModelAttribute BankAccount editedBankExtra,

                                     Model model) {
        model.addAttribute("bankExtra", editedBankExtra);
        BankAccount extra = bankAccountService.findById(editedBankExtra.getId()).get();
        extra.setDate(editedBankExtra.getDate());
        extra.setBalance(editedBankExtra.getBalance());
        bankAccountService.save(extra);


        return "redirect:/bankAccount";
    }

    @GetMapping("/delete/{id}")
    public String extrasDelete(@PathVariable Long id,
                                 Model model) {
        bankAccountService.deleteById(id);

        return "redirect:/bankAccount";
    }

}
