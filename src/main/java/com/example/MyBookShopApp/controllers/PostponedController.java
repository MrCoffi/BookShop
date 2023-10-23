package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class PostponedController {
    private final BookRepository bookRepository;

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @ModelAttribute(name = "postponedCarts")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public String handleCartRequest(@CookieValue(value = "keptContents", required = false)
                                    String keptContents, Model model) {
        if (keptContents == null || keptContents.isEmpty()) {
            model.addAttribute("isPostponed", true);
        } else {
            model.addAttribute("isPostponed", false);
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(keptContents.split("/"));
            model.addAttribute("postponedCarts", booksFromCookieSlugs);
        }
        return "postponed";
    }

}

