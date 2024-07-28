package com.exam.pricing.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.OrderPriceInput;
import com.exam.pricing.service.PricingService;

@Component
public class PricingFacadeImpl implements PricingFacade {

    @Autowired
    PricingService pricingService;

    @Override
    public Order computeOrderPrice(OrderPriceInput orderPriceInput) {
        return pricingService.computeOrderPrice(orderPriceInput);
    }

}
