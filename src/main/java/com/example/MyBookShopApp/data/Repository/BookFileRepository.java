package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Long> {
     BookFile findBookFileByHash(String hash);
}
