package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookStoreUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users users = usersRepository.findByEmail(s);
        if (users != null) {
            return new BookStoreUserDetails(users);
        } else {
            throw new UsernameNotFoundException("user not found doh!.." + s);
        }
    }
    public UserDetails loadUserByPhone(String s)throws UsernameNotFoundException{

        Users users = usersRepository.findUsersByPhone(s);
        if (users != null) {
            return new BookStoreUserDetails(users);
        } else {
            throw new UsernameNotFoundException("user not found doh!.." + s);
        }
    }
}
