package com.exam.pricing.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderPriceInput {

    private String orderId;

    private String userId;

    private String userRole;

    private LocalDate userCreatedOn;

    private LocalDate date;

    private List<Item> items;

}
