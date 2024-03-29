package com.vominh.example.spring.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello everybody !!!");
        return "home";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
