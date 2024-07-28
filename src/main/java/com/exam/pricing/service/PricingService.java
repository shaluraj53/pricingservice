package com.exam.pricing.service;

import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.OrderPriceInput;

public interface PricingService {

    public Order computeOrderPrice(final OrderPriceInput orderPriceInput);

}
