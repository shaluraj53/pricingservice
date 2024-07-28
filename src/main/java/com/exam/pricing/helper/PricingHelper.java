package com.exam.pricing.helper;

import org.springframework.stereotype.Component;

import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.OrderPriceInput;
import com.exam.pricing.dto.PriceInfo;
import com.exam.pricing.dto.User;

@Component
public class PricingHelper {

    public Order convertPriceInputToOrder(OrderPriceInput priceInput) {
        Order order = null;
        if (priceInput != null) {
            order = new Order();
            order.setOrderId(priceInput.getOrderId());
            order.setItems(priceInput.getItems());
            order.setOrderDate(priceInput.getDate());

            User user = new User();
            user.setId(priceInput.getUserId());
            user.setRole(priceInput.getUserRole());
            user.setCreatedOn(priceInput.getUserCreatedOn());

            order.setUserInfo(user);
            order.setPriceInfo(new PriceInfo());
        }

        return order;
    }

}
