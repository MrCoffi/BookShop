package com.example.MyBookShopApp.data.Repository;


import com.example.MyBookShopApp.data.Entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@TestPropertySource("/application-test.properties")
class UsersRepositoryTests {
    @MockBean
    private final UsersRepository usersRepository;

    @Autowired
    UsersRepositoryTests(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    @Test
    public void testAddNewUser() {
        Users user = new Users();
        user.setEmail("test@mail.ru");
        user.setName("tester");
        user.setHash("1");
        user.setRegTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        user.setBalance(0l);
        assertNotNull(usersRepository.save(user));

    }
}