package com.example.SpringReact.repository;

import com.example.SpringReact.domain.CheckoutItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CheckoutItemRepository extends JpaRepository<CheckoutItem, Long> {
    List<CheckoutItem> findByCheckoutId(Long checkoutId);
    List<CheckoutItem> findByCheckoutIdAndReturnedAtIsNull(Long checkoutId);
}