package com.exam.pricing.chain.processor.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.chain.calculator.OrderDiscountCalculator;
import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.ChainProcessor;
import com.exam.pricing.dto.Order;

@Component
public class OrderDiscountProcessor implements ChainProcessor {

    @Autowired
    OrderDiscountCalculator orderDiscountCalculator;

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();

            double netPrice = order.getPriceInfo() != null ? order.getPriceInfo().getNetPrice() : 0;
            if (netPrice < 100) {
                return; // No discount for orders less than $100
            }
            orderDiscountCalculator.compute(chainContext);
        }
    }

}
