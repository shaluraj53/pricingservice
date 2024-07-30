package com.exam.pricing.utils;

import java.math.BigDecimal;

public class MathUtil {

    private MathUtil() {
    }

    public static BigDecimal toBigDecimal(final Number n) {
        return (n instanceof BigDecimal ? (BigDecimal) n : new BigDecimal(n.toString()));
    }

    public static BigDecimal computeValueFromPercentage(final Number percentage, final Number value) {
        final BigDecimal percentageRate = toBigDecimal(percentage);
        final BigDecimal originalValue = toBigDecimal(value);
        return originalValue.multiply(percentageRate).divide(new BigDecimal(100));
    }

}
