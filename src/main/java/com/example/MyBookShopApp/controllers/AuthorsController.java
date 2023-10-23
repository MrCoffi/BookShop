package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.Author;
import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Service.AuthorService;
import com.example.MyBookShopApp.data.Service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Api()
@ApiModel("data model of author entity")
@RequiredArgsConstructor
public class AuthorsController {

    private final AuthorService authorService;

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @ApiOperation("method to get map authors")
    @GetMapping("/api/authors")
    @ResponseBody
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors/{authorName}")
    public String authorPage(@PathVariable(value = "authorName", required = false) String authorName, Model model) {
        model.addAttribute("authorName", authorName);
        model.addAttribute("author", authorService.getAuthorByName(authorName));
        model.addAttribute("authorList", authorService.getBookListByAuthor(authorName, 0, 20).getContent());
        return "/authors/slug";
    }

    @GetMapping("/books/author/{authorName}")
    public String getAllBookByAuthorPage(@PathVariable(value = "authorName", required = false) String authorName, Model model) {
        model.addAttribute("authorList", authorService.getBookListByAuthor(authorName, 0, 20).getContent());
        return "books/author";
    }

    @GetMapping("/books/author/page/{authorName}")
    @ResponseBody
    public BooksPageDto getNextPageByBookAuthor(@PathVariable(value = "authorName", required = false) String authorName,
                                                @RequestParam("offset") Integer offset,
                                                @RequestParam("limit") Integer limit) {
        return new BooksPageDto(authorService.getBookListByAuthor(authorName, offset, limit).getContent());
    }
}
