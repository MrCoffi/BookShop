package com.example.MyBookShopApp.data;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
class CookieServiceTests {

    @Test
    void testHandleChangeBookStatus() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        Model model = Mockito.mock(Model.class);
        CookieService cookieService = new CookieService();
        String slug = "book-slug";
        String cartContents = "book1/book2";
        String keptContents = "book3/book4";
        String status = "CART";
        Boolean result = cookieService.handleChangeBookStatus(slug, cartContents, response, model, status, keptContents);
        assertTrue(result);
        assertNotNull(response.getCookie("cartContents"));
        Mockito.verify(model).addAttribute("isCartEmpty", false);
    }
    @Test
    void testHandleRemoveBookFromCartRequest() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        Model model = Mockito.mock(Model.class);
        CookieService cookieService = new CookieService();
        String slugToRemove = "book1";
        String cartContents = "book1/book2/book3";
        String keptContents = "book4/book5";
        String status = "UNLINK";
        Boolean result = cookieService.handleRemoveBookFromCartRequest(slugToRemove, cartContents, response, model, keptContents, status);
        assertTrue(result);
        assertNotNull(response.getCookie("cartContents"));
        Mockito.verify(model).addAttribute("isCartEmpty", false);
    }
}