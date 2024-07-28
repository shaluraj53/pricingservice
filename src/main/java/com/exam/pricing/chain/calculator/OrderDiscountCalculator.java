package com.exam.pricing.chain.calculator;

import org.springframework.stereotype.Component;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.dto.DiscountInfo;
import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.PriceInfo;
import com.exam.pricing.utils.PricingConstants;

@Component
public class OrderDiscountCalculator implements Calculator {

    private double amountOff = 5.0; // Amountoff for every $100 in netprice

    @Override
    public void compute(ChainContext chainContext) {

        Order order = chainContext.getOrder();
        PriceInfo priceInfo = order.getPriceInfo();

        if (priceInfo != null) {
            double netPrice = priceInfo.getNetPrice();
            int hundreds = (int) (netPrice / 100);
            double discount = hundreds * amountOff;

            netPrice = netPrice - discount;
            priceInfo.setNetPrice(netPrice);

            DiscountInfo discountInfo = new DiscountInfo();
            discountInfo.setAmount(discount);
            discountInfo.setType(PricingConstants.ORDER_DISCOUNT);
            priceInfo.setDiscountInfo(discountInfo);

        }

    }

}
