package com.exam.pricing.chain.processor.order;

import java.util.List;

import org.springframework.stereotype.Component;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.ChainProcessor;
import com.exam.pricing.dto.Item;
import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.PriceInfo;

@Component
public class OrderPriceComputationProcessor implements ChainProcessor {

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();
            List<Item> items = order.getItems();

            PriceInfo orderPriceInfo = order.getPriceInfo();
            if (orderPriceInfo == null) {
                orderPriceInfo = new PriceInfo();
            }

            if (!items.isEmpty()) {
                double totalPrice = items.stream().map(item -> item.getPriceInfo().getTotalPrice()).reduce(0.0,
                        (x, y) -> x + y);
                double netPrice = items.stream().map(item -> item.getPriceInfo().getNetPrice()).reduce(0.0,
                        (x, y) -> x + y);
                orderPriceInfo.setTotalPrice(totalPrice);
                orderPriceInfo.setNetPrice(netPrice);
                order.setPriceInfo(orderPriceInfo);
            }
        }
    }

}