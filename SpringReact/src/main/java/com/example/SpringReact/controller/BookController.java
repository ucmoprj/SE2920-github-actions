package com.example.SpringReact.controller;

import com.example.SpringReact.domain.Book;
import com.example.SpringReact.dto.CreateBookRequest;
import com.example.SpringReact.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @CrossOrigin
    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book) {
        final Long loggedInUserId = 1L;

        System.out.println("title:  " + book.getTitle());
        System.out.println("author: " + book.getAuthor());

        Book saved = bookService.create(
                book.getTitle(),
                book.getAuthor(),
                loggedInUserId
        );
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @CrossOrigin
    @GetMapping("/books")
    public List<Book> list() {
        return bookService.list();
    }


    @CrossOrigin
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id) {
        Book found = bookService.get(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }


    @CrossOrigin
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        Book updated = bookService.update(id, book);
        return ResponseEntity.ok(updated);
    }


    @CrossOrigin
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
