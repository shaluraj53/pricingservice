package com.exam.pricing.chain.processor.item;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.chain.calculator.ItemDiscountCalculator;
import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.ChainProcessor;
import com.exam.pricing.dto.Item;
import com.exam.pricing.enums.Role;
import com.exam.pricing.utils.PricingConstants;

@Component
public class ItemDiscountProcessor implements ChainProcessor {

    @Autowired
    ItemDiscountCalculator itemDiscountCalculator;

    @Override
    public void process(ChainContext chainContext) {
        Item item = chainContext.getCurrentItem();

        if (StringUtils.equalsIgnoreCase(PricingConstants.GROCERY, item.getCategory())) {
            return; // No discounts for grocery items
        }

        if (StringUtils.equalsIgnoreCase(Role.EMPLOYEE.name(), chainContext.getUserRole())) {
            // 30 % discount
            calculateDiscount(chainContext, 30, PricingConstants.EMPLOYEE_DISCOUNT);
        } else if (StringUtils.equalsIgnoreCase(Role.AFFILIATE.name(), chainContext.getUserRole())) {
            // 10 % discount
            calculateDiscount(chainContext, 10, PricingConstants.AFFILIATE_DISCOUNT);
        } else if (StringUtils.equalsIgnoreCase(Role.CUSTOMER.name(), chainContext.getUserRole()) &&
                checkIfOldCustomer(chainContext.getUserCreatedOn())) {
            // 5 % discount
            calculateDiscount(chainContext, 5, PricingConstants.CUSTOMER_DISCOUNT);
        } else {
            // No discount for this role
            return;
        }
    }

    private void calculateDiscount(ChainContext chainContext, double discountPercentage, String discountType) {
        chainContext.setDiscountPercentage(discountPercentage);
        chainContext.setDiscountType(discountType);
        itemDiscountCalculator.compute(chainContext);
    }

    private boolean checkIfOldCustomer(LocalDate userCreatedOn) {
        boolean moreThanTwoYearOldCustomer = false;
        LocalDate today = LocalDate.now();
        moreThanTwoYearOldCustomer = userCreatedOn.isBefore(today.minusYears(2));
        return moreThanTwoYearOldCustomer;
    }

}
