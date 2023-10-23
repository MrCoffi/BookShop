package com.example.MyBookShopApp.data.Entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "book")
@ApiModel(description = "entity representing a book")
@NoArgsConstructor

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("id generate by db automaticaly ")
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;
    @ApiModelProperty("this's title by book")
    private String title;
    @ApiModelProperty("mnemonically identity sequence of characters")
    private String slug;
    @ApiModelProperty("image author")
    private String image;
    private String description;
    @Column(name = "is_bestseller")
    private int isBestseller;
    private Integer price;
    @ApiModelProperty("this's sale in procent")
    private int discount;
    @Column(name = "pub_date")
    private Date pubDate;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "book2genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book2tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;
    @OneToMany(mappedBy = "book")
    private List<BookFile> bookFileList = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();
    @OneToMany(mappedBy = "book")
    private List<BookReview> bookReviews = new ArrayList<>();


    @JsonProperty()
    public Integer discountPrice() {
        Integer discountedPrice = price - Math.toIntExact(Math.round(price * (discount % 100)));
        return discountedPrice;
    }

    @JsonGetter("authors")
    public String authorFullName() {
        return author.toString();
    }


    public String getStarRatingsCount(Integer i) {
        return String.valueOf(ratings.stream()
                .filter(rating -> rating.getValue() == i)
                .count());
    }

    public double getMeanRatingsCount() {
        return Math.floor((double) ratings.stream().mapToInt(Rating::getValue).sum() / ratings.size());
    }
}