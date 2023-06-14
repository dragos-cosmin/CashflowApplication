package com.example.cashflow.controller;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.model.SupplierInvoice;
import com.example.cashflow.model.Transaction;
import com.example.cashflow.service.PurchasesService;
import com.example.cashflow.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String supplierForm(Model model){
        model.addAttribute("supplier",new Supplier());

        return "supplier";
    }

    @PostMapping("/add")
    public String supplierSubmit(@ModelAttribute Supplier supplier,
                                    Model model){
        model.addAttribute("supplier",supplier);
        suppliersService.save(supplier);
        return "redirect:/suppliers";
    }


}
