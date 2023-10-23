package com.example.MyBookShopApp.data.Repository;

import com.example.MyBookShopApp.data.Entity.BlackListJWTtoken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtBlackListTokenRepository extends JpaRepository<BlackListJWTtoken, Long> {

    BlackListJWTtoken findByToken(String token);
}
