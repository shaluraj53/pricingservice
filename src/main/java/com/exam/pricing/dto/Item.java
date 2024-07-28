package com.exam.pricing.dto;

import lombok.Data;

@Data
public class Item {

    private String id;

    private String name;

    private String category;

    private int quantity;

    private double unitPrice;

    private PriceInfo priceInfo;

}
