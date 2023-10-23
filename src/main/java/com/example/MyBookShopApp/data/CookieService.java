package com.example.MyBookShopApp.data;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

@Service
public class CookieService {
    public Boolean handleChangeBookStatus(String slug, String cartContents, HttpServletResponse response,
                                          Model model, String status, String keptContents) {
        if (!response.isCommitted()) {
            String cookieName = status.equals("CART") ? "cartContents" : "keptContents";
            String contents = status.equals("CART") ? cartContents : keptContents;
            String attributeName = status.equals("CART") ? "isCartEmpty" : "isPostponed";

            if (contents == null || contents.isEmpty() || !contents.contains(slug)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                if (contents != null) {
                    stringJoiner.add(contents);
                }
                stringJoiner.add(slug);
                Cookie cookie = new Cookie(cookieName, stringJoiner.toString());
                cookie.setPath("/books");
                response.addCookie(cookie);
                model.addAttribute(attributeName, false);
                return true;
            }
        }
        return false;
    }

    public Boolean handleRemoveBookFromCartRequest(String slug, String cartContents, HttpServletResponse response,
                                                   Model model, String keptContents, String status) {
        if (!response.isCommitted()) {
            String cookieName = status.equals("UNLINK") ? "cartContents" : "keptContents";
            String contents = status.equals("UNLINK") ? cartContents : keptContents;
            String attributeName = status.equals("UNLINK") ? "isCartEmpty" : "isPostponed";
            if (contents.contains(slug)) {
                ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(contents.split("/")));
                cookieBooks.remove(slug);
                Cookie cookie = new Cookie(cookieName, String.join("/", cookieBooks));
                cookie.setPath("/books");
                response.addCookie(cookie);
                model.addAttribute(attributeName, false);
            } else {
                model.addAttribute(attributeName, true);
            }
       return true;
        }
        return false;
    }
}
