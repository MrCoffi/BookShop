package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.data.Entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class BookStoreUserDetails implements UserDetails {
    private final Users oneUser;

    public BookStoreUserDetails(Users users) {
        this.oneUser = users;
    }

    public Users getOneUser() {
        return oneUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return oneUser.getHash();
    }

    @Override
    public String getUsername() {
        return oneUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
