package com.example.MyBookShopApp.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id")
    private Long parentId;
    private String slug;
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private List<Book> books;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Genre parentGenre;

    @JsonIgnore
    @OneToMany(mappedBy = "parentGenre", cascade = CascadeType.ALL)
    private List<Genre> subgenres = new ArrayList<>();

    public Genre() {
    }
}
