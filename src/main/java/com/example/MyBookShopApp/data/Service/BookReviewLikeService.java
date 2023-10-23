package com.example.MyBookShopApp.data.Service;


import com.example.MyBookShopApp.data.Entity.BookReviewLike;
import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.BookReviewLikeRepository;

import com.example.MyBookShopApp.data.Repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookReviewLikeService {
    private final BookReviewLikeRepository bookReviewLikeRepository;
    private final BookReviewRepository bookReviewRepository;

    public boolean feedback(Long reviewid, int value, Users user) {
        try {

            if (user != null) {
                BookReviewLike bookReview = new BookReviewLike();
                if (bookReviewLikeRepository.findByBookReview_IdAndUsers(reviewid, user) == null) {
                    bookReview.setUsers(user);
                    bookReview.setTime(LocalDateTime.now());
                    bookReview.setBookReview(bookReviewRepository.findBookReviewById(reviewid));

                } else {
                    bookReview = bookReviewLikeRepository.findByBookReview_IdAndUsers(reviewid, user);
                }
                bookReview.setValue(value);
                bookReviewLikeRepository.save(bookReview);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
