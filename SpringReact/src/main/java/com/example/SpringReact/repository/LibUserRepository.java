package com.example.SpringReact.repository;

import com.example.SpringReact.domain.LibUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibUserRepository extends JpaRepository<LibUser, Long> {
    Optional<LibUser> findByUsername(String username);
}
