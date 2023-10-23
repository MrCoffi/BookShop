package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Entity.Genre;
import com.example.MyBookShopApp.data.Entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByAuthorNameContaining(String authorName);

    List<Book> findBookByTitleContaining(String title);

    List<Book> findBookByPriceBetween(Integer min, Integer max);

    @Query(value = "SELECT b FROM Book b ORDER BY b.isBestseller DESC", countQuery = "SELECT COUNT(b) FROM Book b")
    Page<Book> getBestsellerPage(Pageable pageable);

    @Query(value = "SELECT isBestseller FROM Book ORDER BY isBestseller DESC")
    List<Book> getBestseller();

    Page<Book> findBookByPubDateGreaterThanEqualOrderByPubDateDesc(Date pubDate, Pageable pageable);

    @Query(value = "SELECT * FROM Book WHERE price=(SELECT MAX (price) FROM Book )", nativeQuery = true)
    List<Book> getBookWidthMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    Page<Book> findBookByPubDateBetween(Date from, Date to, Pageable pageable);

    Page<Book> findBookByGenres(Genre genre, Pageable nextPage);

    Page<Book> findBookByAuthorName(String name, Pageable pageable);

    List<Book> findBookByTags(TagEntity tag, Pageable pageable);

    Book findBookBySlug(String slug);

    List<Book> findBooksBySlugIn(String[] slugs);
}
