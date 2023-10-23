package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByName(String name);
}
