package com.example.SpringReact.domain;


import lombok.*;
import javax.persistence.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity @Table(name="lib_user")
public class LibUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // "admin", "alice"

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // ADMIN or USER

    public enum Role { ADMIN, USER }
}