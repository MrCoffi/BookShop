package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class RecentController {
    private final BookService bookService;

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @ModelAttribute("recentedBook")
    public List<Book> recededBook() {
        return bookService.getNewBook(0, 20).getContent();
    }


    @GetMapping("/recent")
    public String newBookPage() {
        return "books/recent";
    }

    @GetMapping("/recent/page")
    @ResponseBody
    public BooksPageDto getNextRecentPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getNewBook(offset, limit).getContent());
    }

    @GetMapping("/recent/filter")
    @ResponseBody
    public BooksPageDto getNextRecentPageFilter(@RequestParam("from") String from, @RequestParam("to") String to,
                                                @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        try {
            return new BooksPageDto(bookService.getFilterBook(from, to, offset, limit).getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
