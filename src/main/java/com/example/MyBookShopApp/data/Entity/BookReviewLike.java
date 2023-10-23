package com.example.MyBookShopApp.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review_like")
@NoArgsConstructor
@Getter
@Setter
public class BookReviewLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private Users users;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "review_id")
    private BookReview bookReview;

    private int value;
    private LocalDateTime time;
}
