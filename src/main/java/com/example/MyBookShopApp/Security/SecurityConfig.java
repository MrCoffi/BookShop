package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.Security.JWT.JWTRequestFilter;
import com.example.MyBookShopApp.data.Service.GitHubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final GitHubUserService gitHubUserService;
    private final BookStoreUserDetailService bookStoreUserDetailService;
    private final JWTRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(GitHubUserService gitHubUserService, BookStoreUserDetailService bookStoreUserDetailService, JWTRequestFilter jwtRequestFilter) {
        this.gitHubUserService = gitHubUserService;
        this.bookStoreUserDetailService = bookStoreUserDetailService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(bookStoreUserDetailService)
                .passwordEncoder(getPasswordEncoder());

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/my", "/profile").authenticated()//.hasRole("USER")
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/signin").failureUrl("/signin")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/signin").deleteCookies("token")
                .and() .oauth2Login()
                .userInfoEndpoint()
                .userAuthoritiesMapper(this.userAuthoritiesMapper())
                .userService(this.oauth2UserService());

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return (userRequest) -> {
            OAuth2User oauth2User = delegate.loadUser(userRequest);
            if (userRequest.getClientRegistration().getRegistrationId().equals("github")) {
                gitHubUserService.processOAuth2User(oauth2User);
            }
            return oauth2User;
        };
    }
    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> authorities;
    }

}