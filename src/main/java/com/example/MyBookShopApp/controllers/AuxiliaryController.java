package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuxiliaryController {
    @GetMapping("/faq")
    public String faqPage() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "contacts";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
    @GetMapping("/document")
    public String documentPage(){
        return "/documents/index";
    }
}
