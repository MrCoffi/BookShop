package com.example.MyBookShopApp.data.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "book_file")
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;
    @Column(name = "type_id")
    private Long typeId;
    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public void BookFile() {

    }

    public String getBookFileExtensionString() {
        return BookFileType.getExtensionString(typeId);
    }
}
