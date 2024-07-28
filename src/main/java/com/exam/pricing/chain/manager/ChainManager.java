package com.exam.pricing.chain.manager;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exam.pricing.chain.context.ChainContext;
import com.exam.pricing.chain.processor.ChainProcessor;
import com.exam.pricing.chain.processor.factory.ChainProcessorFactory;

@Component
public class ChainManager {

    List<String> itemPriceProcessors = Arrays.asList("itemPriceComputationProcessor");
    List<String> orderPriceProcessors = Arrays.asList("orderPriceComputationProcessor", "orderDiscountProcessor");

    @Autowired
    ChainProcessorFactory chainProcessorFactory;

    public void processChain(final ChainContext chainContext) {

        for (final String processorName : itemPriceProcessors) {
            final ChainProcessor processor = this.chainProcessorFactory.getChainProcessor(processorName);
            processor.process(chainContext);
        }

        for (final String processorName : orderPriceProcessors) {
            final ChainProcessor processor = this.chainProcessorFactory.getChainProcessor(processorName);
            processor.process(chainContext);
        }

    }

}
