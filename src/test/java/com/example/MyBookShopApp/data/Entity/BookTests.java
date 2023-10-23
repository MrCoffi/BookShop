package com.example.MyBookShopApp.data.Entity;

import com.example.MyBookShopApp.data.Repository.BookRepository;
import com.example.MyBookShopApp.data.Repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")

class BookTests {
    private final BookRepository bookRepository;
    private final RatingRepository repository;
    private Book book;

    @Autowired
    BookTests(BookRepository bookRepository, RatingRepository repository) {
        this.bookRepository = bookRepository;
        this.repository = repository;
    }


    @Test
    @Transactional
    void getStarRatingsCountTest() {
        book = bookRepository.findBookBySlug("book-bou-653");
        assertNotNull(book.getMeanRatingsCount());
    }

    @Test
    void getMeanRatingsCountTest() {

    }
}