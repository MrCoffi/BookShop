package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.data.Entity.BlackListJWTtoken;
import com.example.MyBookShopApp.data.Repository.JwtBlackListTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JWTBlackListTokenService {
    private final JwtBlackListTokenRepository tokenRepository;

    public boolean saveToken(String token) {
        if (tokenRepository.findByToken(token) != null) {
            return true;
        } else {
            BlackListJWTtoken blackListJWTtoken = new BlackListJWTtoken();
            blackListJWTtoken.setToken(token);
            tokenRepository.save(blackListJWTtoken);
            return false;
        }
    }
}
