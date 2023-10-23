package com.example.MyBookShopApp.data.Service;


import com.example.MyBookShopApp.data.Entity.Rating;
import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.BookRepository;
import com.example.MyBookShopApp.data.ConvectorDate;
import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Repository.GenreRepository;
import com.example.MyBookShopApp.data.Repository.RatingRepository;
import com.example.MyBookShopApp.data.Repository.UsersRepository;
import com.example.MyBookShopApp.data.errs.BookstoreApiWrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor

public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final UsersRepository usersRepository;

    public List<Book> getBookByAuthor(String authorName) {
        return bookRepository.findBookByAuthorNameContaining(authorName);
    }

    public List<Book> getBookByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.trim().length() <= 1) {
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters ");
        } else {
            List<Book> data = bookRepository.findBookByTitleContaining(title);
            if (data.size() > 0) {
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters.");
            }
        }
    }

    public List<Book> getBookPriceBetween(Integer min, Integer max) {
        return bookRepository.findBookByPriceBetween(min, max);
    }

    public List<Book> getBookByPrice() {
        return bookRepository.getBookWidthMaxDiscount();
    }

    public List<Book> getBestseller() {
        return bookRepository.getBestseller();
    }

    public Page<Book> getPageOfRecommendedBook(Integer offset, Integer limit) {
        return bookRepository.findAll(PageRequest.of(offset, limit));
    }

    public Page<Book> getFilterBook(String from, String to, Integer offset, Integer limit) {
        ConvectorDate convectorDate = new ConvectorDate();
        return bookRepository.findBookByPubDateBetween(convectorDate.convertStrDateFormat(from),
                convectorDate.convertStrDateFormat(to), PageRequest.of(offset, limit));
    }

    public Page<Book> getNewBook(Integer offset, Integer limit) {
        Date fromDate = new Date();
        fromDate.setMonth(fromDate.getMonth() - 3);
        return bookRepository.findBookByPubDateGreaterThanEqualOrderByPubDateDesc
                (fromDate, PageRequest.of(offset, limit));
    }

    public Page<Book> getBestsellerPage(Integer offset, Integer limit) {
        return bookRepository.getBestsellerPage(PageRequest.of(offset, limit));
    }


    public Page<Book> getPageOfSearchResultBook(String searchWord, Integer offset, Integer limit) {
        return bookRepository.findBookByTitleContaining(searchWord, PageRequest.of(offset, limit));
    }

    public Page<Book> findBookBySlug(String slug, Integer offset, Integer limit) {
        return bookRepository.findBookByGenres(genreRepository.findGenreBySlug(slug), PageRequest.of(offset, limit));
    }

    public Book findBookBySlug(String slug) {
        return bookRepository.findBookBySlug(slug);
    }

    public void saveImage(Book book) {
        bookRepository.save(book);
    }

    public boolean saveBookRating(String slug, Integer value, Users user) {
        try {
            if (user != null) {
                Rating rating = new Rating();
                if (ratingRepository.findRatingByUsers_IdAndBookSlug(user.getId(),slug) == null) {
                    rating.setBook(bookRepository.findBookBySlug(slug));
                    rating.setUsers(user);
                } else {
                    rating = ratingRepository.findRatingByUsers_IdAndBookSlug(user.getId(),slug);
                }
                rating.setValue(value);
                ratingRepository.save(rating);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Book> findBooksBySlugIn(String[] slug) {
        return bookRepository.findBooksBySlugIn(slug);
    }
}