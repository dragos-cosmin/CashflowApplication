package com.example.cashflow.controller;

import com.example.cashflow.model.FinancialType;
import com.example.cashflow.model.Supplier;
import com.example.cashflow.model.SupplierInvoice;
import com.example.cashflow.model.Transaction;
import com.example.cashflow.service.PurchasesService;
import com.example.cashflow.service.SuppliersService;
import com.example.cashflow.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dragos.cosmin
 **/

@Controller
@RequestMapping(value = "/purchases")
public class PurchasesController {

    @Autowired
    private PurchasesService purchasesService;

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private TransactionsService transactionsService;

    @GetMapping("")
    public String getPurchases(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {

        List<SupplierInvoice> purchases = purchasesService.findAll();
        suppliersService.updateBalance();

        model.addAttribute("purchases", purchases);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "purchases";
    }

    @GetMapping("/add")
    public String purchaseForm(Model model){
        model.addAttribute("supplierInvoice",new SupplierInvoice());
        model.addAttribute("suppliers",suppliersService.findAll());


        return "purchase";
    }

    @PostMapping("/add")
    public String purchaseSubmit(@ModelAttribute SupplierInvoice supplierInvoice,
                                 Model model){
        Transaction transaction=new Transaction();
        model.addAttribute("supplierInvoice",supplierInvoice);
        supplierInvoice.setSupplier(suppliersService.findById(supplierInvoice.getSupplier().getId()).get());
        supplierInvoice.getSupplier().addSupplierInvoice(supplierInvoice);
        transaction.setDate(supplierInvoice.getDueDate());
        transaction.setFinancialType(FinancialType.PAYMENT);
        if (supplierInvoice.getBalance().doubleValue()==0.0){
            transaction.setAmount(supplierInvoice.getValue());
        }else {
            transaction.setAmount(supplierInvoice.getBalance());
        }
        transaction.setObservation("plata "+supplierInvoice.getSupplier().getName());
        transactionsService.save(transaction);
        purchasesService.save(supplierInvoice);
        return "redirect:/purchases";
    }
}
