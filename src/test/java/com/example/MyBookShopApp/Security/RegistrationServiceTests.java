package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.data.Entity.Users;
import com.example.MyBookShopApp.data.Repository.UsersRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class RegistrationServiceTests {
    private RegistrationForm registrationForm;
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;
    @MockBean
    private final UsersRepository usersRepositoryMock;
    private ContactConfirmationPayload contactConfirmationPayload;
    private ContactConfirmationResponce responce;
    @MockBean
    private AuthenticationManager authenticationManager;

    private BookStoreUserDetailService service = Mockito.mock(BookStoreUserDetailService.class);

    @Autowired
    RegistrationServiceTests(RegistrationService registrationService, PasswordEncoder passwordEncoder, UsersRepository usersRepository) {
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepositoryMock = usersRepository;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setName("Tester");
        registrationForm.setEmail("1@mail.ru");
        registrationForm.setPhone("123");
        registrationForm.setPassword("1234567");
        contactConfirmationPayload = new ContactConfirmationPayload();
        responce = new ContactConfirmationResponce();
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerNewUser() {
        Users users = registrationService.registerNewUser(registrationForm);
        assertNotNull(users);
        assertTrue(passwordEncoder.matches(registrationForm.getPassword(), users.getHash()));
        assertTrue(CoreMatchers.is(users.getName()).matches(registrationForm.getName()));
        assertTrue(CoreMatchers.is(users.getEmail()).matches(registrationForm.getEmail()));
        verify(usersRepositoryMock, times(1))
                .save(Mockito.any(Users.class));
    }

    @Test
    void registerNewUserFail() {
        Mockito.doReturn(new Users())
                .when(usersRepositoryMock)
                .findByEmail(registrationForm.getEmail());
        Users users = registrationService.registerNewUser(registrationForm);
        assertNull(users);
    }

    @Test
    void login() {

    }

    @Test
    void jwtLogin() {
        Mockito.doReturn(new Users())
                .when(usersRepositoryMock)
                .findByEmail(registrationForm.getEmail());
         contactConfirmationPayload.setContact(registrationForm.getEmail());
        contactConfirmationPayload.setCode(registrationForm.getPassword());
        responce = registrationService.jwtLogin(contactConfirmationPayload);
       assertNotNull(responce, "jwt token is not null");
    }
}