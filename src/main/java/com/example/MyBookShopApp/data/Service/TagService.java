package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.TagEntity;
import com.example.MyBookShopApp.data.Repository.BookRepository;
import com.example.MyBookShopApp.data.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagService {
    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    @Autowired
    public TagService(TagRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public List<TagEntity> getAllTag() {
        return tagRepository.findAll();
    }

    public TagEntity findByTagSlug(String slug) {
        return tagRepository.findBySlug(slug);
    }

    public List<Book> findByTagSlug(String slug, Integer offset, Integer limit) {
        return bookRepository
                .findBookByTags(tagRepository.findBySlug(slug), PageRequest.of(offset, limit));
    }
}
