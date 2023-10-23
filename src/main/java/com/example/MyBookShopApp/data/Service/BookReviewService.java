package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.data.Entity.BookReview;
import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.BookRepository;
import com.example.MyBookShopApp.data.Repository.BookReviewRepository;
import com.example.MyBookShopApp.data.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookReviewService {
    private final BookRepository bookRepository;
    private final BookReviewRepository bookReviewRepository;

    public boolean createMessage(String slug, String messages,Users users) {
        if (!messages.trim().isEmpty() && users!=null) {
            BookReview bookReview = new BookReview();
            bookReview.setText(messages);
            bookReview.setUsers(users);
            bookReview.setTime(LocalDateTime.now());
            bookReview.setBook(bookRepository.findBookBySlug(slug));
            bookReviewRepository.save(bookReview);
            return true;
        }
        return false;
    }

}
