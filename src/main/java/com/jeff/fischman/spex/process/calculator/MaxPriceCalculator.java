package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;

public class MaxPriceCalculator implements OutputCalculator {
    MaxValueAccumulator _maxValueAccumulator;

    public MaxPriceCalculator() {
        this(new MaxValueAccumulator());
    }

    public MaxPriceCalculator(MaxValueAccumulator maxValueAccumulator) {
        _maxValueAccumulator = maxValueAccumulator;
    }

    @Override
    public void onTrade(Trade trade) {
        onValue(trade.getPrice());
    }

    public void onValue(Long price) {
        _maxValueAccumulator.onValue(price);
    }

    @Override
    public long getValue() {
        return _maxValueAccumulator.getValue();
    }
}
