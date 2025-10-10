package com.example.SpringReact.controller;

import com.example.SpringReact.domain.LibUser;
import com.example.SpringReact.service.LibUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LibUserController {

    private final LibUserService libUserService;


    @PostMapping("/user")
    public ResponseEntity<?> create(@RequestBody LibUser user) {
        System.out.println("username: " + user.getUsername());
        System.out.println("role: " + user.getRole());

        LibUser saved = libUserService.createUser(user.getUsername(), user.getRole());
        return ResponseEntity
                .created(URI.create("/users/" + saved.getId()))
                .body(saved);
    }


    @GetMapping("/users")
    public List<LibUser> list() {
        return libUserService.getAllUsers();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        LibUser found = libUserService.getUserById(id);
        if (found == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(found);
    }
}