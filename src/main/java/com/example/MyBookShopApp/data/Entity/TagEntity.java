package com.example.MyBookShopApp.data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@Data
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String title;
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<Book> books;
    public TagEntity() {

    }
}
