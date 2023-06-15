package com.example.cashflow.controller;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.service.PurchasesService;
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
@RequestMapping(value = "/suppliers")
public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;

    @Autowired
    private PurchasesService purchasesService;


    @GetMapping("")
    public String getSuppliers(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model) {
        suppliersService.updateBalance();
        List<Supplier> suppliers = suppliersService.findAll();

        model.addAttribute("suppliers", suppliers);
        model.addAttribute("edit", edit);
        model.addAttribute("delete", delete);

        return "suppliers";
    }

    @GetMapping("/add")
    public String supplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());

        return "supplier";
    }

    @PostMapping("/add")
    public String supplierSubmit(@ModelAttribute Supplier supplier,
                                 Model model) {
        model.addAttribute("supplier", supplier);
        suppliersService.save(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String supplierEdit(@PathVariable Long id,
                               Model model) {
        Optional<Supplier> optSupplier = suppliersService.findById(id);

        optSupplier.ifPresent(supplier -> model.addAttribute("supplier", supplier));

        return "supplierEdit";
    }

    @PostMapping("/edit/{id}")
    public String supplierEditSubmit(@ModelAttribute Supplier editedSupplier,

                                     Model model) {
        model.addAttribute("supplier", editedSupplier);
        Supplier supplier = suppliersService.findById(editedSupplier.getId()).get();
        supplier.setCui(editedSupplier.getCui());
        supplier.setName(editedSupplier.getName());
        supplier.setPaymentDelay(editedSupplier.getPaymentDelay());
        supplier.setBalance(editedSupplier.getBalance());
        suppliersService.save(supplier);


        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String supplierDelete(@PathVariable Long id,
                                 Model model) {
        suppliersService.deleteById(id);

        return "redirect:/suppliers";
    }

    @GetMapping("/details/{id}")
    public String supplierDetails(@PathVariable Long id,
                               Model model) {
        Optional<Supplier> optSupplier = suppliersService.findById(id);
        double[] totalValue = {0.0};
        double[] totalPayments={0.0};

        optSupplier.ifPresent(supplier -> {
            model.addAttribute("supplier", supplier);
            totalValue[0] =supplier.getTotalInvoicesValue();
            totalPayments[0]=supplier.getTotalPayments();
            model.addAttribute("totalValue",totalValue[0]);
            model.addAttribute("totalPayments",totalPayments[0]);
        });

        return "supplierDetails";
    }


}
