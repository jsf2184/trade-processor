package com.jeff.fischman.spex.process.components;

public class WeightedAvgAccumulator {
    private AdditionAccumulator _runningCostTotal;
    private AdditionAccumulator _runningQtyTotal;

    public WeightedAvgAccumulator() {
        this(new AdditionAccumulator(), new AdditionAccumulator());
    }

    public WeightedAvgAccumulator(AdditionAccumulator runningCostTotal,
                                  AdditionAccumulator runningQtyTotal)
    {
        _runningCostTotal = runningCostTotal;
        _runningQtyTotal = runningQtyTotal;
    }

    public void onTrade(long price, long qty) {
        if (price <= 0 || qty <= 0) {
            throw new RuntimeException("WeightedAvgAccumulator.onTrade(): price and qty must be > 0");
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
