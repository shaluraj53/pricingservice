package com.exam.pricing.chain.processor;

import com.exam.pricing.chain.context.ChainContext;

public interface ChainProcessor {

    void process(ChainContext chainContext);

}