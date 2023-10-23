package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.data.Repository.AuthorRepository;
import com.example.MyBookShopApp.data.Repository.BookRepository;
import com.example.MyBookShopApp.data.Entity.Author;
import com.example.MyBookShopApp.data.Entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Map<String, List<Author>> getAuthorsMap() {
        return authorRepository.findAll().stream().collect(Collectors.groupingBy((Author a) -> a.getName().substring(0, 1)));
    }

    public Author getAuthorByName(String name) {

        return authorRepository.findAuthorByName(name);
    }

    public Page<Book> getBookListByAuthor(String name, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByAuthorName(name, nextPage);
    }
}
