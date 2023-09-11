package com.example.MyBookShopApp.data;

import liquibase.pro.packaged.B;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> customFindAllBooks();

    List<Book> findBookByAuthorNameContaining(String authorName);

    List<Book> findBookByTitleContaining(String title);

    List<Book> findBookByPriceBetween(Integer min, Integer max);

    List<Book> findBookByPriceIs(Integer price);

    @Query(value = "from Book where isBestseller=true")
    List<Book> getBestseller();
@Query(value = "SELECT * FROM Book WHERE discount=(SELECT MAX (discount) FROM Book )", nativeQuery = true )
    List<Book> getBookWidthMaxDiscount();
}
