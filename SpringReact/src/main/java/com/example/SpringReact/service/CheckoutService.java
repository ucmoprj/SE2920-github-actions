package com.example.SpringReact.service;

import com.example.SpringReact.domain.Book;
import com.example.SpringReact.domain.Checkout;
import com.example.SpringReact.domain.CheckoutItem;
import com.example.SpringReact.domain.LibUser;
import com.example.SpringReact.repository.BookRepository;
import com.example.SpringReact.repository.CheckoutItemRepository;
import com.example.SpringReact.repository.CheckoutRepository;
import com.example.SpringReact.repository.LibUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CheckoutRepository checkoutRepo;
    private final CheckoutItemRepository itemRepo;
    private final BookRepository bookRepo;
    private final LibUserRepository userRepo;


    @Transactional
    public Checkout createCheckout(Long userId, List<Long> bookIds) {
        LibUser user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (bookIds == null || bookIds.isEmpty()) {
            throw new IllegalArgumentException("At least one bookId must be provided.");
        }


        for (Long bookId : bookIds) {
            Book b = bookRepo.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
            if (!b.isAvailable()) {
                throw new IllegalStateException("Book is not available. bookId=" + b.getId());
            }
        }

        Checkout co = new Checkout();
        co.setLibUser(user);
        checkoutRepo.save(co);

        // 항목 생성 + 대여 처리
        for (Long bookId : bookIds) {
            Book b = bookRepo.findById(bookId).orElseThrow();
            b.setAvailable(false); // 대여 중

            CheckoutItem item = new CheckoutItem();
            item.setCheckout(co);
            item.setBook(b);
            itemRepo.save(item);

            co.getItems().add(item);
        }

        return co;
    }


    @Transactional
    public CheckoutItem returnItem(Long checkoutItemId) {
        CheckoutItem item = itemRepo.findById(checkoutItemId)
                .orElseThrow(() -> new IllegalArgumentException("CheckoutItem not found: " + checkoutItemId));

        if (item.getReturnedAt() != null) {
            throw new IllegalStateException("Already returned. itemId=" + checkoutItemId);
        }

        item.setReturnedAt(LocalDateTime.now());


        Book b = item.getBook();
        b.setAvailable(true);


        List<CheckoutItem> remaining =
                itemRepo.findByCheckoutIdAndReturnedAtIsNull(item.getCheckout().getId());
        if (remaining.isEmpty()) {
            Checkout co = item.getCheckout();
            co.setClosedAt(LocalDateTime.now());
        }

        return item;
    }


    @Transactional
    public Checkout returnAll(Long checkoutId) {
        Checkout co = checkoutRepo.findById(checkoutId)
                .orElseThrow(() -> new IllegalArgumentException("Checkout not found: " + checkoutId));

        if (co.getClosedAt() != null) {
            return co;
        }

        List<CheckoutItem> items = itemRepo.findByCheckoutId(checkoutId);
        for (CheckoutItem item : items) {
            if (item.getReturnedAt() == null) {
                item.setReturnedAt(LocalDateTime.now());
                Book b = item.getBook();
                b.setAvailable(true);
            }
        }

        co.setClosedAt(LocalDateTime.now());
        return co;
    }

    public Checkout get(Long id) {
        return checkoutRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Checkout not found: " + id));
    }

    public static record ItemRequest(Long bookId) {}
}