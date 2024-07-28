package com.exam.pricing.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.manager.ChainManager;
import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.OrderPriceInput;
import com.exam.pricing.helper.PricingHelper;

@Component
public class PricingServiceImpl implements PricingService {

    @Autowired
    PricingHelper pricingHelper;

    @Autowired
    ChainManager chainManager;

    @Override
    public Order computeOrderPrice(OrderPriceInput orderPriceInput) {

        Order order = pricingHelper.convertPriceInputToOrder(orderPriceInput);
        if (order != null) {
            populateChainContext(orderPriceInput, order);
            ChainContext chainContext = populateChainContext(orderPriceInput, order);
            chainManager.processChain(chainContext);
            return chainContext.getOrder();
        }
        return null;
    }

    private ChainContext populateChainContext(OrderPriceInput orderPriceInput, Order order) {
        ChainContext chainContext = new ChainContext(order);
        BeanUtils.copyProperties(orderPriceInput, chainContext);
        return chainContext;
    }

}
