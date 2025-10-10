package com.example.SpringReact.controller;


import com.example.SpringReact.domain.Checkout;
import com.example.SpringReact.domain.CheckoutItem;
import com.example.SpringReact.dto.CreateCheckoutRequest;
import com.example.SpringReact.dto.ReturnBookRequest;
import com.example.SpringReact.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/loan")
    public Checkout create(@RequestBody CreateCheckoutRequest req) {
        return checkoutService.createCheckout(req.getUserId(), req.getBookIds());
    }


}
