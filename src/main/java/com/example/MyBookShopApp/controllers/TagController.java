package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.Entity.BooksPageDto;
import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import com.example.MyBookShopApp.data.Service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;


    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    /*
     *Добавление основной страницы, добавление модели для Thymeleaf ,отображение  "Все теги"
     */
    @GetMapping("/tags")
    public String getAllTag(Model model) {
        model.addAttribute("tagName", "Все теги");
        return "tags/index";
    }
    /*
     * Добавление страницы для одног тега, создание 2х моделей:
     * OneTag - запрос к бд возвращает тег, чтобы отобразить его name на странице.
     * tagBookList - запрос к бд , для получения тегов по параметрам (offset,limit)
     */
    @GetMapping("/tags/{oneTag}")
    public String getOneTag(@PathVariable(value = "oneTag", required = false) String slug, Model model) {
        model.addAttribute("oneTag", tagService.findByTagSlug(slug));
        model.addAttribute("tagBookList", tagService.findByTagSlug(slug, 0, 20));
        return "tags/index";
    }
    /*
     * Получение нового объекта с листом для отображения следующих 20 книг на странице.
     * Получает список новых тегов по slug.
     */
    @GetMapping("/tags/page/{tagBookList}")
    @ResponseBody
    public BooksPageDto getNextPageOfTag(@PathVariable(value = "tagBookList", required = false) String slug,
                                         @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(tagService.findByTagSlug(slug, offset, limit));
    }
}