package com.example.SpringReact.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
public class CheckoutItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Checkout checkout;

    @ManyToOne(optional = false)
    private Book book;


    @Column(nullable = false)
    private int quantity;

    private LocalDateTime returnedAt;
    public boolean isReturned() {
        return returnedAt != null; }
}
