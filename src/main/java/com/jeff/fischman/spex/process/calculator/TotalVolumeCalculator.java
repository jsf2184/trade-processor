package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;

public class TotalVolumeCalculator implements OutputCalculator {
    AdditionAccumulator _additionAccumulator;

    public TotalVolumeCalculator() {
        this(new AdditionAccumulator());
    }

    public TotalVolumeCalculator(AdditionAccumulator additionAccumulator) {
        _additionAccumulator = additionAccumulator;
    }

    @Override
    public void onTrade(Trade trade) {
        onValue(trade.getQuantity());
    }

    public void onValue(long qty) {
        _additionAccumulator.onValue(qty);
    }

    @Override
    public long getValue() {
        return _additionAccumulator.getValue();
    }
}
