package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Service.BookService;
import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class PopularBookController {
    private final BookService bookService;

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }
    /*
     *   Добавление объекта для отображения первых 20 книг по жанру Бестселлер.
     */
    @ModelAttribute("bestsellerBooks")
    public List<Book> bestsellerBooks() {
        return bookService.getBestsellerPage(0, 20).getContent();
    }
    @GetMapping("/popular")
    public String getPopularPage() {
        return "books/popular";
    }
    /*
     *   Возвращает новый объект с следущими книгами по жанру Популярные по критерию:  offset и limit.
     */
    @GetMapping("/popular/page")
    @ResponseBody
    public BooksPageDto getNextPopularPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getBestsellerPage(offset, limit).getContent());
    }
}
