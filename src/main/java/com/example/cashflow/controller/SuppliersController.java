package com.example.cashflow.controller;

import com.example.cashflow.model.Supplier;
import com.example.cashflow.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author dragos.cosmin
 **/
@Controller
@RequestMapping(value = "/suppliers")
public class SuppliersController {

    @Autowired
    private SuppliersService suppliersService;

@GetMapping("")
    public String getSuppliers(@RequestParam(defaultValue = "false") boolean edit,
                               @RequestParam(defaultValue = "false") boolean delete,
                               Model model){
    List<Supplier> suppliers=suppliersService.findAll();
    model.addAttribute("suppliers",suppliers);
    model.addAttribute("edit",edit);
    model.addAttribute("delete",delete);

    return "suppliers";
}

}
