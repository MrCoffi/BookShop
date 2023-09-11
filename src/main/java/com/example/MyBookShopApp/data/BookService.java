package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public List<Book> getBookByAuthor(String authorName) {
        return bookRepository.findBookByAuthorNameContaining(authorName);
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findBookByTitleContaining(title);
    }

    public List<Book> getBookPriceBetween(Integer min, Integer max) {
        return bookRepository.findBookByPriceBetween(min, max);
    }

    public List<Book> getBookByPrice(Integer price) {
        return bookRepository.findBookByPriceIs(price);
    }

    public List<Book> getBestseller() {
        return bookRepository.getBestseller();
    }
}
