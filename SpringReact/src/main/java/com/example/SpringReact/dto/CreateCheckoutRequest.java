package com.example.SpringReact.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateCheckoutRequest {
    private Long userId;
    private List<Long> bookIds;
}
