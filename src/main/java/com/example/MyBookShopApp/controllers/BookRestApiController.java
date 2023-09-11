package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRestApiController {
    private final BookService bookService;

    @Autowired
    public BookRestApiController(BookService bookService) {
        this.bookService = bookService;
    }
}
