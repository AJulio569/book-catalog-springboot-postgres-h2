package com.book.controller;

import com.book.dto.response.BookResponse;
import com.book.dto.response.PaginatedBookResponse;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books/rest")
public class RestController {
    private final BookService service;

    @GetMapping("/v1/title/{title}")
    public ResponseEntity<PaginatedBookResponse> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.getBookByTitle(title));
    }

    @PostMapping("/v1/title/{title}")
    public ResponseEntity<BookResponse> saveBookFromGutendex(@PathVariable String title) {
        BookResponse savedBook = service.saveBookFromGutendex(title);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/v1")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(service.getAllBooks());
    }
}
