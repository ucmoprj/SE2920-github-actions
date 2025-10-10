package com.example.SpringReact.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class Checkout {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private LibUser libUser;

    private LocalDateTime createdAt = LocalDateTime.now();


    private LocalDateTime closedAt;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckoutItem> items = new ArrayList<>();

    public boolean isClosed() {
        return closedAt != null; }
}
