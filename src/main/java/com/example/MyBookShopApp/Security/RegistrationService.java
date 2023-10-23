package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.Security.JWT.JWTUtil;
import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BookStoreUserDetailService bookStoreUserDetailService;
    private final JWTUtil jwtUtil;

    public Users registerNewUser(RegistrationForm registrationForm) {

        if (usersRepository.findByEmail(registrationForm.getEmail()) == null) {
            Users users = new Users();
            users.setName(registrationForm.getName());
            users.setEmail(registrationForm.getEmail());
            users.setHash(passwordEncoder.encode(registrationForm.getPassword()));
            users.setRegTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            users.setBalance(0L);
            users.setPhone(registrationForm.getPhone());
            usersRepository.save(users);
            return users;
        }
        return null;
    }

    public ContactConfirmationResponce login(ContactConfirmationPayload payload) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        BookStoreUserDetails userDetails =
                (BookStoreUserDetails) bookStoreUserDetailService.loadUserByPhone(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponce response = new ContactConfirmationResponce();
        response.setResult(jwtToken);
        return response;
    }

    public ContactConfirmationResponce jwtLogin(ContactConfirmationPayload payload) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(),
                payload.getCode()));
        BookStoreUserDetails userDetails =
                (BookStoreUserDetails) bookStoreUserDetailService.loadUserByUsername(payload.getContact());
        String jwtToken = jwtUtil.generateToken(userDetails);
        ContactConfirmationResponce response = new ContactConfirmationResponce();
        response.setResult(jwtToken);
        return response;
    }

    public Users getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                if (principal instanceof DefaultOAuth2User) {
                    if (!usersRepository.existsByGitId(((Integer) ((DefaultOAuth2User) principal).getAttributes().get("id")))) {
                        Users user = new Users();
                        user.setRegTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
                        user.setName((String) ((DefaultOAuth2User) principal).getAttributes().get("login"));
                        user.setGitId((Integer) ((DefaultOAuth2User) principal).getAttributes().get("id"));
                        user.setBalance(0l);
                        usersRepository.save(user);
                        return user;
                    } else {
                        return usersRepository.findUsersByGitId((((Integer) ((DefaultOAuth2User) principal).getAttributes().get("id"))));
                    }
                } else if (principal instanceof BookStoreUserDetails) {
                    return ((BookStoreUserDetails) principal).getOneUser();
                }
            }
            throw new UnsupportedOperationException(" Пользователь не авторизован.\"");
        } catch (UnsupportedOperationException e) {
            System.err.println("Ошибка при получении текущего пользователя:\" " + e.getMessage());
        }
        return null;
    }
}
