package com.example.SpringReact.service;

import com.example.SpringReact.domain.LibUser;
import com.example.SpringReact.repository.LibUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibUserService {

    private final LibUserRepository libUserRepository;


    @Transactional
    public LibUser createUser(String username, LibUser.Role role) {
        if (libUserRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists: " + username);
        }

        LibUser user = new LibUser();
        user.setUsername(username);
        user.setRole(role == null ? LibUser.Role.USER : role);
        return libUserRepository.save(user);
    }


    public List<LibUser> getAllUsers() {
        return libUserRepository.findAll();
    }

    public LibUser getUserById(Long id) {
        return libUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }


    public LibUser getUserByUsername(String username) {
        return libUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }


    @Transactional
    public void deleteUser(Long id) {
        libUserRepository.deleteById(id);
    }
}