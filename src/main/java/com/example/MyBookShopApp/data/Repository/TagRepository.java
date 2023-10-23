package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    TagEntity findBySlug(String slug);
}
