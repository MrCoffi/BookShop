package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.*;
import com.example.MyBookShopApp.data.Service.BookService;
import com.example.MyBookShopApp.data.Service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainPageController {
    private final BookService bookService;
    private final TagService tagService;

    @ModelAttribute(name = "postponedCarts")
    public List<Book> bookCart() {

        return new ArrayList<>();
    }

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @ModelAttribute("searchResult")
    public List<Book> searchResult() {
        return new ArrayList<>();
    }

    /*
     *  Добавление модели для отображения первых 20 книг по жанру Рекомендации.
     */
    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBook(0, 20).getContent();
    }
    @ModelAttribute("allTag")
    public List<TagEntity> getAllTag() {
        return tagService.getAllTag();
    }

    @ModelAttribute("bestsellerBooks")
    public List<Book> bestsellerBooks() {
        return bookService.getBestsellerPage(0, 20).getContent();
    }

    @ModelAttribute("recentedBook")
    public List<Book> newBook() {
        return bookService.getNewBook(0, 20).getContent();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
    /*
     *   Возвращает новый объект с листом книг по жанру Рекомендации по offset и limit.
     */
    @GetMapping("books/recommended")
    @ResponseBody
    public BooksPageDto getBookPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBook(offset, limit).getContent());
    }
}
