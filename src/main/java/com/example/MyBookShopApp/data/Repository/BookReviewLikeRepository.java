package com.example.MyBookShopApp.data.Repository;


import com.example.MyBookShopApp.data.Entity.BookReviewLike;
import com.example.MyBookShopApp.data.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewLikeRepository extends JpaRepository<BookReviewLike, Long> {


    BookReviewLike findByBookReview_IdAndUsers(Long id, Users users);

}
