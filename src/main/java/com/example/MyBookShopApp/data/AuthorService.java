package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    public Map<String, List<Author>> getAuthorsMap() {
        return authorRepository.findAll().stream().collect(Collectors.groupingBy((Author a) -> {
            return a.getName().substring(0, 1);
        }));
    }

    public char[] getAlphabet() {
        return alphabet.toCharArray();
    }
}
