package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Service.BookService;
import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.errs.EmptySearchExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) SearchWorldDto searchWorldDto,
                                  Model model) throws EmptySearchExeption {
        if (searchWorldDto != null) {
            model.addAttribute("searchWorldDto", searchWorldDto);
            model.addAttribute("searchResults",
                    bookService.getPageOfSearchResultBook(searchWorldDto.getExample(), 0, 5).getContent());

            return "/search/index";
        } else {
            throw new EmptySearchExeption("Search 'null ' - is unstable ");
        }
    }

    @GetMapping("search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@PathVariable(value = "searchWord", required = false) SearchWorldDto searchWorldDto,
                                          @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfSearchResultBook(searchWorldDto.getExample(), offset, limit).getContent());
    }

}
