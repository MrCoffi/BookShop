package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.data.Entity.Genre;
import com.example.MyBookShopApp.data.Repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> findAllTopLevelGenres() {
        return genreRepository.findAllTopLevelGenres();
    }
    public Genre searchGenreBySlug(String slug) {
        return genreRepository.findGenreBySlug(slug);
    }
}
