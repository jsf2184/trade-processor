package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;

public class WeightedAvgCalculator implements OutputCalculator {
    private AdditionAccumulator _runningCostTotal;
    private AdditionAccumulator _runningQtyTotal;

    public WeightedAvgCalculator() {
        this(new AdditionAccumulator(), new AdditionAccumulator());
    }

    public WeightedAvgCalculator(AdditionAccumulator runningCostTotal,
                                 AdditionAccumulator runningQtyTotal)
    {
        _runningCostTotal = runningCostTotal;
        _runningQtyTotal = runningQtyTotal;
    }

    @Override
    public void onTrade(Trade trade) {
        onTrade(trade.getPrice(), trade.getQuantity());
    }

    public void onTrade(long price, long qty) {
        if (price <= 0 || qty <= 0) {
            throw new RuntimeException("WeightedAvgCalculator.onTrade(): price and qty must be > 0");
        }
        long tradeValue = price * qty;
        _runningCostTotal.onValue(tradeValue);
        _runningQtyTotal.onValue(qty);
    }

    public long getValue() {
        long qtyTotalValue = _runningQtyTotal.getValue();
        if (qtyTotalValue == 0) {
            return 0;
        }
        long res = _runningCostTotal.getValue() / qtyTotalValue;
        return res;
    }
}
