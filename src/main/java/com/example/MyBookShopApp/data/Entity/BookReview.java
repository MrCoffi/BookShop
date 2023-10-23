package com.example.MyBookShopApp.data.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "book_review")
@NoArgsConstructor
@Getter
@Setter
public class BookReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String text;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonIgnore
    @OneToMany(mappedBy = "bookReview")
    private List<BookReviewLike> bookReviewLikes;

/*
 * Подсчет рейтинга для отзыва на странице /books/{slug} в контроллере BooksController.
 *  В Thymeleaf на странице передается значение 1 или -1 , из этого следует расчет.
 */
    public long countingValue(int value) {
        if (value == 1) {
            return bookReviewLikes.stream().filter(s-> s.getValue()>0).count();
        } else {
            return bookReviewLikes.stream().filter(s-> s.getValue()<0).count();
        }
    }
}
