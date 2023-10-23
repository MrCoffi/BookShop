package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    BookReview findBookReviewById(Long id);
}
