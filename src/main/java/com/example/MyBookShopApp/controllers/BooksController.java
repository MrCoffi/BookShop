package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.JsonObject.RateBookJson;
import com.example.MyBookShopApp.JsonObject.ReviewJson;
import com.example.MyBookShopApp.Security.RegistrationService;
import com.example.MyBookShopApp.data.Entity.*;
import com.example.MyBookShopApp.data.ResourceStorage;
import com.example.MyBookShopApp.data.Service.BookReviewLikeService;
import com.example.MyBookShopApp.data.Service.BookReviewService;
import com.example.MyBookShopApp.data.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("books/")
@RequiredArgsConstructor
public class BooksController {
    private final BookService bookService;
    private final ResourceStorage storage;
    private final BookReviewService bookReviewService;
    private final BookReviewLikeService bookReviewLikeService;
    private final RegistrationService registrationService;
    @ModelAttribute("searchWorldDto")
    public SearchWorldDto searchWorldDto() {
        return new SearchWorldDto();
    }

    @GetMapping("/{slug}")
    public String bookPage(@PathVariable(value = "slug") String slug, Model model) {
        model.addAttribute("book", bookService.findBookBySlug(slug));

        return "books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImgage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.findBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.saveImage(bookToUpdate);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);
        MediaType mediaType = storage.getBookFileMide(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime: " + mediaType);
        byte[] data = storage.getBookFileByArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/rateBook/{slug}")
    @ResponseBody
    public Map<String, Object> rate(@PathVariable String slug, @RequestBody() RateBookJson rateBookJson) {

         Map<String, Object> result = new HashMap<>();
        result.put("result", bookService.saveBookRating(slug, rateBookJson.getValue(),registrationService.getCurrentUser()));
        return result;
    }

    @PostMapping("/{slug}/messages")
    public String uploadMessage(@PathVariable String slug, @RequestParam("message") String message) {
        bookReviewService.createMessage(slug, message,registrationService.getCurrentUser());
        return "redirect:/books/" + slug;
    }

    @PostMapping("/rateBookReview/{slug}")
    @ResponseBody
    public boolean feedback(@PathVariable("slug") String slug, @RequestBody ReviewJson review) {
        return  bookReviewLikeService.feedback(review.getReviewid(), review.getValue(),registrationService.getCurrentUser());
    }


}
