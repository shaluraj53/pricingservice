package com.exam.pricing.chain.context;

import java.time.LocalDate;

import com.exam.pricing.dto.Item;
import com.exam.pricing.dto.Order;

import lombok.Data;

@Data
public class ChainContext {

    String userRole;

    String discountType;

    double discountPercentage;

    Order order;

    Item currentItem;

    LocalDate userCreatedOn;

    public ChainContext(Order order) {
        this.order = order;
    }

}
