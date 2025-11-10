package com.example.SpringReact.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.SpringReact.domain.Book;
import com.example.SpringReact.domain.LibUser;
import com.example.SpringReact.repository.BookRepository;
import com.example.SpringReact.repository.LibUserRepository;


@SpringBootTest
@Transactional
@Rollback(false)
public class BookServiceTest {


    @Autowired
    private BookService bookService;             

    @Autowired
    private BookRepository bookRepository;       

    @Autowired
    private LibUserRepository libUserRepository;
    

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        libUserRepository.deleteAll();
    }

    //create(String title, String author, Long createdByUserId)

    @Test
    void testCreate() {
        LibUser admin = new LibUser();
        admin.setUsername("alice");
        admin.setRole(LibUser.Role.ADMIN);    
        LibUser savedAdmin = libUserRepository.save(admin);
        
        Book createdBook = bookService.create("1984", "George Orwell", savedAdmin.getId());
        
        Book fetchedBook = bookRepository.findById(createdBook.getId()).orElseThrow();

        assertEquals(fetchedBook, createdBook);

    }
}
