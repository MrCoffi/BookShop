package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.CookieService;
import com.example.MyBookShopApp.JsonObject.CookiesStatusJson;
import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor

public class CartController {
    private final BookService bookService;
    private final CookieService cookieService;


    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false)
                                    String cartContents, Model model) {
        if (cartContents == null || cartContents.isEmpty()) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            model.addAttribute("bookCart", bookService.findBooksBySlugIn(cartContents.split("/")));
            AtomicReference<Long> sum = new AtomicReference<>(0l);
            bookService.findBooksBySlugIn(cartContents.split("/")).forEach(s -> sum.updateAndGet(v -> v + s.getPrice()));
            model.addAttribute("bookSum", sum);
            AtomicReference<Long> sale = new AtomicReference<>(0l);
            bookService.findBooksBySlugIn(cartContents.split("/")).forEach(s -> sale.updateAndGet(v -> v + s.discountPrice()));
            model.addAttribute("bookSale", sale);
        }
        return "cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    @ResponseBody
    public Boolean handleChangeBookStatus(@PathVariable("slug") String slug,
                                                      @CookieValue(name = "cartContents", required = false) String cartContents,
                                                      @CookieValue(name = "keptContents", required = false) String keptContent,
                                                      HttpServletResponse response, Model model, @RequestBody CookiesStatusJson status) {
       return cookieService.handleChangeBookStatus(slug, cartContents, response, model, status.getStatus(), keptContent);
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    @ResponseBody
    public Boolean handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
                                                  @CookieValue(name = "cartContents", required = false) String cartContents,
                                                  HttpServletResponse response, Model model, @RequestBody CookiesStatusJson status,
                                                  @CookieValue(name = "keptContents", required = false) String keptContent) {
        return cookieService.handleRemoveBookFromCartRequest(slug, cartContents, response, model, keptContent, status.getStatus());
    }
}