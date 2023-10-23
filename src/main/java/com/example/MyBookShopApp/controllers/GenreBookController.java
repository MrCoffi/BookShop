package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.Genre;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Service.BookService;
import com.example.MyBookShopApp.data.Service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreBookController {
    private final BookService bookService;
    private final GenreService genreService;

    @ModelAttribute()
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @ModelAttribute("allGenres")
    public List<Genre> getTopGenre() {
        return genreService.findAllTopLevelGenres();
    }

    @GetMapping("/genres")
    public String getCategory(Model model) {
        model.addAttribute("searchWorldDto", new SearchWorldDto());
        return "genres/index";
    }

    @GetMapping("/genres/{oneSlug}")
    public String getGenreSlug(@PathVariable(value = "oneSlug") String slug, Model model) {
        model.addAttribute("oneSlug", genreService.searchGenreBySlug(slug));
        model.addAttribute("genre", bookService.findBookBySlug(slug, 0, 20));
        return "genres/slug";
    }

    @GetMapping("/genres/slug/{oneSlug}")
    @ResponseBody
    public BooksPageDto getNextPage(@PathVariable(value = "oneSlug", required = false) String slug, @RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.findBookBySlug(slug, offset, limit).getContent());
    }
}