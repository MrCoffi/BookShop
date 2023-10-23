package com.example.MyBookShopApp.data.Repository;


import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.Rating;
import com.example.MyBookShopApp.data.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findRatingByUsers_IdAndBookSlug(Long users,String slug);

    Rating findRatingByBook(Book book);
}
