package com.example.SpringReact.service;

import com.example.SpringReact.domain.Book;
import com.example.SpringReact.domain.LibUser;
import com.example.SpringReact.repository.BookRepository;
import com.example.SpringReact.repository.LibUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

//function, algorithm, transaction

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final LibUserRepository libUserRepository;

    @Transactional
    public Book create(String title, String author, Long createdByUserId) {
        LibUser creator = libUserRepository.findById(createdByUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + createdByUserId));


        if (creator.getRole() != LibUser.Role.ADMIN) {
            throw new IllegalStateException("Only ADMIN can create a book");
        }

        Book b = new Book();
        b.setTitle(title);
        b.setAuthor(author);
        b.setAvailable(true);
        b.setCreatedBy(creator);
        return bookRepository.save(b);
    }

    public List<Book> list() { return bookRepository.findAll(); }

    public Book get(Long id) { return bookRepository.findById(id).orElseThrow(); }

    @Transactional
    public Book update(Long id, Book req) {
        Book b = get(id);
        b.setTitle(req.getTitle());
        b.setAuthor(req.getAuthor());
        b.setAvailable(req.isAvailable());
        return b;
    }

    @Transactional
    public void delete(Long id) { libUserRepository.deleteById(id); }
}