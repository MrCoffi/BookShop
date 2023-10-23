package com.example.MyBookShopApp.Security;

import com.example.MyBookShopApp.data.Entity.SearchWorldDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;

    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @GetMapping("/signin")
    public String handleSignIn() {
        return "/signin";
    }

    @GetMapping("/signup")
    public String handleSignUp(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "/signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ContactConfirmationResponce handleRequestContactConfimation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponce response = new ContactConfirmationResponce();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ContactConfirmationResponce handleApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponce response = new ContactConfirmationResponce();
        response.setResult("true");
        return response;
    }


    @PostMapping("/reg")
    public String handleUserRegistration(RegistrationForm registrationForm, Model model) {
        registrationService.registerNewUser(registrationForm);
        model.addAttribute("regOk", true);
        return "/signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponce handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse response) {
        ContactConfirmationResponce loginResponse;
        if (payload.getContact().contains("+7")) {
            loginResponse = registrationService.login(payload);
        } else {
          loginResponse = registrationService.jwtLogin(payload);
        }
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        response.addCookie(cookie);
        return loginResponse;

    }


    @GetMapping("/my")
    public String handleMy(Model model) {
        model.addAttribute("curUser", registrationService.getCurrentUser());
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        model.addAttribute("curUser", registrationService.getCurrentUser());
        return "/profile";
    }
}
