package com.example.MyBookShopApp.data;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    private String title;
    private String slug;
    private String image;
    private String description;
    @Column(name = "is_bestseller")
    private boolean isBestseller;
    private Integer price;
    private int discount;
    @Column(name = "pub_date")
    private Date pubDate;

    public Book() {

    }
}

