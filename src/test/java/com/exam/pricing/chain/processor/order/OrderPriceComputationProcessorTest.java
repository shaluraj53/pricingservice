package com.exam.pricing.chain.processor.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.manager.ChainManager;
import com.exam.pricing.dto.Item;
import com.exam.pricing.dto.Order;

@SpringBootTest
public class OrderPriceComputationProcessorTest {

    private ChainContext chainContext;

    private Order order;

    @Autowired
    ChainManager chainManager;

    @BeforeEach
    public void setUp() {
        Item item1 = new Item();
        item1.setQuantity(4);
        item1.setUnitPrice(20);
        item1.setCategory("Health");

        Item item2 = new Item();
        item2.setQuantity(2);
        item2.setUnitPrice(30.0);
        item2.setCategory("Beauty");

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        order = new Order();
        order.setItems(items);
        chainContext = new ChainContext(order);
    }

    @Test
    void orderAmountOffProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2024, 07, 30));
        chainContext.setUserRole("Customer");

        chainManager.processChain(chainContext);
        assertEquals(135.0, order.getPriceInfo().getNetPrice());
    }

    @Test
    void orderWithGroceryAmountOffProcess() {
        Item item = new Item();
        item.setQuantity(1);
        item.setUnitPrice(10);
        item.setCategory("Grocery");

        List<Item> items = chainContext.getOrder().getItems();
        items.add(item);
        chainContext.getOrder().setItems(items);

        chainContext.setUserCreatedOn(LocalDate.of(2024, 07, 30));
        chainContext.setUserRole("Customer");

        chainManager.processChain(chainContext);
        assertEquals(145.0, order.getPriceInfo().getNetPrice());
    }

    @Test
    void orderWithPercentOffAndAmountOffProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2021, 10, 9));
        chainContext.setUserRole("Affiliate");

        chainManager.processChain(chainContext);
        assertEquals(121.0, order.getPriceInfo().getNetPrice());
    }
}
