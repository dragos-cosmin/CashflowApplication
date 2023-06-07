package com.example.cashflow.controller;

import com.example.cashflow.model.Transaction;
import com.example.cashflow.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class TransactionsController {
    private static final BigDecimal INITIAL_BALANCE=BigDecimal.valueOf(100.50).setScale(2, RoundingMode.HALF_UP);

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("transactions")
    public String getRegistrations(Model model){
        List<Transaction> transactions=transactionsService.findAll();
        transactionsService.updateBalance(INITIAL_BALANCE);


//        Transaction first= transactions.get(1);
//        System.out.println(first.getDate());
//        System.out.println(first.getFinancialType());
//        System.out.println(first.getAmount());
//        model.addAttribute("date",first.getDate());
//        model.addAttribute("observation",first.getObservation());
//        model.addAttribute("type",first.getFinancialType().ordinal());
//        model.addAttribute("amount",first.getAmount());
        model.addAttribute("initial",INITIAL_BALANCE);
        model.addAttribute("transactions",transactions);

        return "transactions";
    }

    @GetMapping("welcome")
    public String welcome(Model model){
        model.addAttribute("message","Welcome User");
        return "index";
    }
}
