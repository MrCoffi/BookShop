package com.example.MyBookShopApp.data.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "book_rating")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int value;
    @ManyToOne
    @JoinColumn(name = "slug")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
