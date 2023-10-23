package com.example.MyBookShopApp.data.Service;

import com.example.MyBookShopApp.Security.BookStoreUserDetailService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;



@Service
public class GitHubUserService {
    private final BookStoreUserDetailService userDetailService;

    public GitHubUserService(BookStoreUserDetailService userDetailService) {

        this.userDetailService = userDetailService;
    }

    public void processOAuth2User(OAuth2User oAuth2User) {
        long id = Long.parseLong((oAuth2User.getName()));
        String login = oAuth2User.getAttribute("login");

    }
}
