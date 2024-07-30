package com.exam.pricing.chain.processor.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.order.OrderPriceComputationProcessor;
import com.exam.pricing.dto.Item;
import com.exam.pricing.dto.Order;

@SpringBootTest
class ItemPriceComputationProcessorTest {

    private ChainContext chainContext;

    private Order order;

    @Autowired
    private ItemPriceComputationProcessor itemPriceComputationProcessor;

    @InjectMocks
    private OrderPriceComputationProcessor orderPriceComputationProcessor;

    @BeforeEach
    public void setUp() {
        Item item1 = new Item();
        item1.setQuantity(2);
        item1.setUnitPrice(20);
        item1.setCategory("Health");

        Item item2 = new Item();
        item2.setQuantity(1);
        item2.setUnitPrice(10.0);
        item2.setCategory("Beauty");

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        order = new Order();
        order.setItems(items);
        chainContext = new ChainContext(order);
    }

    @Test
    void employeeTestProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2021, 10, 9));
        chainContext.setUserRole("Employee");

        itemPriceComputationProcessor.process(chainContext);
        orderPriceComputationProcessor.process(chainContext);
        assertEquals(35.0, order.getPriceInfo().getNetPrice());
    }

    @Test
    void affiliateTestProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2021, 10, 9));
        chainContext.setUserRole("Affiliate");

        itemPriceComputationProcessor.process(chainContext);
        orderPriceComputationProcessor.process(chainContext);
        assertEquals(45.0, order.getPriceInfo().getNetPrice());
    }

    @Test
    void newCustomerTestProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2024, 07, 30));
        chainContext.setUserRole("Customer");

        itemPriceComputationProcessor.process(chainContext);
        orderPriceComputationProcessor.process(chainContext);
        assertEquals(50.0, order.getPriceInfo().getNetPrice());
    }

    @Test
    void oldCustomerTestProcess() {
        chainContext.setUserCreatedOn(LocalDate.of(2022, 07, 27));
        chainContext.setUserRole("Customer");

        itemPriceComputationProcessor.process(chainContext);
        orderPriceComputationProcessor.process(chainContext);
        assertEquals(47.5, order.getPriceInfo().getNetPrice());
    }

    @Test
    void oldCustomerWithGroceryTestProcess() {
        Item item = new Item();
        item.setQuantity(1);
        item.setUnitPrice(10);
        item.setCategory("Grocery");

        List<Item> items = chainContext.getOrder().getItems();
        items.add(item);
        chainContext.getOrder().setItems(items);

        chainContext.setUserCreatedOn(LocalDate.of(2022, 07, 27));
        chainContext.setUserRole("Customer");

        itemPriceComputationProcessor.process(chainContext);
        orderPriceComputationProcessor.process(chainContext);
        assertEquals(57.5, order.getPriceInfo().getNetPrice());
    }

}
