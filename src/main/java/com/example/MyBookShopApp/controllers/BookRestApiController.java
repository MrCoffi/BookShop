package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.data.Entity.Book;
import com.example.MyBookShopApp.data.Service.BookService;
import com.example.MyBookShopApp.data.errs.BookstoreApiWrongParameterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BookRestApiController {
    private final BookService bookService;

    @Autowired
    public BookRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books/by-author")
    @ApiOperation("operation to get book list of bookshop by Author full name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author") String authorName) {
        return ResponseEntity.ok(bookService.getBookByAuthor(authorName));
    }

    @GetMapping("books/by-title")
    @ApiOperation("get book by title")
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBookByTitle(title);

        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("books/by-price-range")
    @ApiOperation("get book range by min and max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        return ResponseEntity.ok(bookService.getBookPriceBetween(min, max));
    }

    @GetMapping("books/with-max-price")
    @ApiOperation("get max price book in book shop")
    public ResponseEntity<List<Book>> maxPriceBooks() {
        return ResponseEntity.ok(bookService.getBookByPrice());
    }

    @GetMapping("books/bestseller")
    @ApiOperation("get bestseller book in book shop")
    public ResponseEntity<List<Book>> bestsellerBook() {
        return ResponseEntity.ok(bookService.getBestseller());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,
                "Missing request parameters", exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST,
                "Bad parameter value", exception), HttpStatus.BAD_REQUEST);
    }
}
