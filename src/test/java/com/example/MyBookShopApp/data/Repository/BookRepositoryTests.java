package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class BookRepositoryTests {
    private final BookRepository bookRepository;

    @Autowired
    BookRepositoryTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    void findBookByAuthorNameContaining() {
        String token = "Kassulke Inc";
        List<Book> bookListByAuthorFirsName = bookRepository.findBookByAuthorNameContaining(token);
        assertNotNull(bookListByAuthorFirsName);
        assertFalse(bookListByAuthorFirsName.isEmpty());
        for (Book book : bookListByAuthorFirsName) {
            assertThat(book.getAuthor().getName().contains(token));
        }
    }

    @Test
    void findBookByTitleContaining() {
        String token = "Enforcer, The";
        List<Book> bookListByTitleContaining = bookRepository.findBookByTitleContaining(token);
        assertNotNull(bookListByTitleContaining);
        assertFalse(bookListByTitleContaining.isEmpty());
        for (Book book : bookListByTitleContaining) {
            assertThat(book.getTitle().contains(token));
        }
    }

    @Test
    void BestsellerBookTest() {
        Page<Book> bestsellerBooks = bookRepository.getBestsellerPage(PageRequest.of(0, 20));
        assertNotNull(bestsellerBooks);
        assertFalse(bestsellerBooks.isEmpty());
        assertThat(bestsellerBooks.getTotalElements()).isGreaterThan(1);
    }
}