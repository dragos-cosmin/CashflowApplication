package com.example.cashflow.controller;

import com.example.cashflow.model.Transaction;
import com.example.cashflow.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionsController {
    private static final BigDecimal INITIAL_BALANCE=BigDecimal.valueOf(100.50).setScale(2, RoundingMode.HALF_UP);

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("transactions")
    public String getTransactions(@RequestParam(defaultValue = "false") boolean edit, Model model){
        List<Transaction> transactions=transactionsService.findAll();
        transactionsService.updateBalance(INITIAL_BALANCE);

        model.addAttribute("edit",edit);
        model.addAttribute("initial",INITIAL_BALANCE);
        model.addAttribute("transactions",transactions);

        return "transactions";
    }

    @GetMapping("transactions/add")
    public String transactionForm(Model model){
        model.addAttribute("transaction",new Transaction());

        return "transaction";
    }

    @PostMapping("transactions/add")
    public String transactionSubmit(@ModelAttribute Transaction transaction,
                                    Model model){
        model.addAttribute("transaction",transaction);
        transactionsService.save(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("transactions/edit/{id}")
    public String transactionEdit (@PathVariable Long id,
                                   Model model){
        Optional<Transaction> optTransaction=transactionsService.findById(id);

        optTransaction.ifPresent(transaction -> model.addAttribute("transaction", transaction));
//        Transaction transaction=transactionsService.findById(id).get();
//        model.addAttribute("transaction",transaction);



        return "transactionEdit";
    }

    @PostMapping("transactions/edit/{id}")
    public String transactionEditSubmit(@ModelAttribute Transaction editedTransaction,

                                        Model model){
        model.addAttribute("transaction",editedTransaction);
        Transaction transaction=transactionsService.findById(editedTransaction.getId()).get();
        transaction.setDate(editedTransaction.getDate());
        transaction.setObservation(editedTransaction.getObservation());
        transaction.setAmount(editedTransaction.getAmount());
        transaction.setFinancialType(editedTransaction.getFinancialType());
        transactionsService.save(transaction);


        return "redirect:/transactions";
    }



    @GetMapping("/")
    public String welcome(Model model){
        model.addAttribute("message","Welcome User");
        return "index";
    }
}
