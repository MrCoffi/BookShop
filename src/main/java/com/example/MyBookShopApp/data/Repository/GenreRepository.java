package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findGenreBySlug(String slug);

    @Query("SELECT g FROM Genre g WHERE g.parentId IS NULL")
    List<Genre> findAllTopLevelGenres();
}
