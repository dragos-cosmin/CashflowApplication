package com.example.cashflow.controller;

import com.example.cashflow.model.FinancialType;
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
import java.util.Optional;

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
    public String purchaseForm(Model model) {
        model.addAttribute("supplierInvoice", new SupplierInvoice());
        model.addAttribute("suppliers", suppliersService.findAll());


        return "purchase";
    }

    @PostMapping("/add")
    public String purchaseSubmit(@ModelAttribute SupplierInvoice supplierInvoice,
                                 Model model) {
        Transaction transaction = new Transaction();

        model.addAttribute("supplierInvoice", supplierInvoice);
        supplierInvoice.setSupplier(suppliersService.findById(supplierInvoice.getSupplier().getId()).get());
        supplierInvoice.getSupplier().addSupplierInvoice(supplierInvoice);
        transaction.setSupplierInvoice(supplierInvoice);
        setTransaction(transaction);

        transactionsService.save(transaction);

        purchasesService.save(supplierInvoice);
        return "redirect:/purchases";
    }

    private void setTransaction(Transaction transaction) {
        transaction.setDate(transaction.getSupplierInvoice().getDueDate());
        transaction.setFinancialType(FinancialType.PAYMENT);
        if (transaction.getSupplierInvoice().getBalance().doubleValue() == 0.0) {
            transaction.setAmount(transaction.getSupplierInvoice().getValue());
        }
        else {
            transaction.setAmount(transaction.getSupplierInvoice().getBalance());
        }
        transaction.setObservation("payment due to " + transaction.getSupplierInvoice().getSupplier().getName());
    }

    @GetMapping("/edit/{id}")
    public String purchaseEdit(@PathVariable Long id,
                               Model model) {
        Optional<SupplierInvoice> optSupplierInvoice = purchasesService.findById(id);
        model.addAttribute("suppliers", suppliersService.findAll());
        optSupplierInvoice.ifPresent(supplierInvoice -> model.addAttribute("supplierInvoice", supplierInvoice));

        return "purchaseEdit";
    }

    @PostMapping("/edit/{id}")
    public String purchaseEditSubmit(@ModelAttribute SupplierInvoice editedSupplierInvoice,

                                     Model model) {
        model.addAttribute("supplierInvoice", editedSupplierInvoice);
        SupplierInvoice supplierInvoice = purchasesService.findById(editedSupplierInvoice.getId()).get();
        supplierInvoice.setSupplier(suppliersService.findById(editedSupplierInvoice.getSupplier().getId()).get());
        supplierInvoice.setDate(editedSupplierInvoice.getDate());
        supplierInvoice.setSerial(editedSupplierInvoice.getSerial());
        supplierInvoice.setValue(editedSupplierInvoice.getValue());
        supplierInvoice.setPayments(editedSupplierInvoice.getPayments());
        supplierInvoice.getSupplier().addSupplierInvoice(supplierInvoice);
        purchasesService.save(supplierInvoice);


        return "redirect:/purchases";
    }
}
