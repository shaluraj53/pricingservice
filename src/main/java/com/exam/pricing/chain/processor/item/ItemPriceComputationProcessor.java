package com.exam.pricing.chain.processor.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.ChainProcessor;
import com.exam.pricing.dto.Item;
import com.exam.pricing.dto.Order;
import com.exam.pricing.dto.PriceInfo;

@Component
public class ItemPriceComputationProcessor implements ChainProcessor {

    @Autowired
    ItemDiscountProcessor itemDiscountProcessor;

    @Override
    public void process(ChainContext chainContext) {
        if (chainContext != null && chainContext.getOrder() != null) {
            Order order = chainContext.getOrder();
            List<Item> items = order.getItems();

            for (Item item : items) {
                PriceInfo priceInfo = item.getPriceInfo();
                if (priceInfo == null) {
                    priceInfo = new PriceInfo();
                    item.setPriceInfo(priceInfo);
                }
                double totalPrice = item.getQuantity() * item.getUnitPrice();
                priceInfo.setTotalPrice(totalPrice);
                priceInfo.setNetPrice(totalPrice);

                chainContext.setCurrentItem(item);
                itemDiscountProcessor.process(chainContext);

            }
        }
    }

}
