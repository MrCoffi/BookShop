package com.example.MyBookShopApp.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;
    private Date regTime;
    private Long balance;
    private String name;
    private String email;
    private String phone;
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<BookReview> bookReviews = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<Rating> ratings = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "users")
    private List<BookReviewLike> bookReviewLikes;
    @Column(name = "git_id")
    private Integer gitId;

    public int getRatingBook(String slug) {
        for (Rating rating : ratings) {
            if (rating.getBook().getSlug().equals(slug)) {
                return rating.getValue();
            }
        }
        return 0;
    }
}
