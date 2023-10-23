package com.example.MyBookShopApp.data.Entity;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BooksPageDto {
    private Integer count;
    private List<Book> books;

    public BooksPageDto(List<Book> books) {
        this.count = books.size();
        this.books = books;
    }
}
