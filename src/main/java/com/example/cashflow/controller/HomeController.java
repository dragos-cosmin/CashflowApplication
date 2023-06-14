package com.example.cashflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dragos.cosmin
 **/
@Controller
public class HomeController {
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome User");
        return "index";
    }
}
