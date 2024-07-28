package com.exam.pricing.facade;

import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.OrderPriceInput;

public interface PricingFacade {

    public Order computeOrderPrice(final OrderPriceInput orderPriceInput);

}
