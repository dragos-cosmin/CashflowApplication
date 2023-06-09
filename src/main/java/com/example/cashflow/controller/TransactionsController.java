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
@RequestMapping(value = "/transactions")
public class TransactionsController {
    private static final BigDecimal INITIAL_BALANCE=BigDecimal.valueOf(100.50).setScale(2, RoundingMode.HALF_UP);

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("")
    public String getTransactions(@RequestParam(defaultValue = "false") boolean edit,
                                  @RequestParam(defaultValue = "false") boolean delete,
                                  Model model){
        List<Transaction> transactions=transactionsService.findAll();
        transactionsService.updateBalance(INITIAL_BALANCE);

        model.addAttribute("edit",edit);
        model.addAttribute("delete",delete);

        model.addAttribute("initial",INITIAL_BALANCE);
        model.addAttribute("transactions",transactions);

        return "transactions";
    }

    @GetMapping("/add")
    public String transactionForm(Model model){
        model.addAttribute("transaction",new Transaction());

        return "transaction";
    }

    @PostMapping("/add")
    public String transactionSubmit(@ModelAttribute Transaction transaction,
                                    Model model){
        model.addAttribute("transaction",transaction);
        transactionsService.save(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/edit/{id}")
    public String transactionEdit (@PathVariable Long id,
                                   Model model){
        Optional<Transaction> optTransaction=transactionsService.findById(id);

        optTransaction.ifPresent(transaction -> model.addAttribute("transaction", transaction));
//        Transaction transaction=transactionsService.findById(id).get();
//        model.addAttribute("transaction",transaction);



        return "transactionEdit";
    }

    @PostMapping("/edit/{id}")
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

    @GetMapping("/delete/{id}")
    public String transactionDelete (@PathVariable Long id,
                                   Model model){
        transactionsService.deleteById(id);

        return "redirect:/transactions";
    }




}
