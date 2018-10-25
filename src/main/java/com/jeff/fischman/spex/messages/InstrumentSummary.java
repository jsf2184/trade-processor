package com.jeff.fischman.spex.messages;

import java.util.Objects;

public class InstrumentSummary {
    private String _symbol;
    private long _maxTimeGap;
    private long _totalVolume;
    private long _weightedAvgPrice;
    private long _maxPrice;

    public InstrumentSummary(String symbol,
                             long maxTimeGap,
                             long totalVolume,
                             long weightedAvgPrice,
                             long maxPrice) {
        _symbol = symbol;
        _maxTimeGap = maxTimeGap;
        _totalVolume = totalVolume;
        _weightedAvgPrice = weightedAvgPrice;
        _maxPrice = maxPrice;
    }

    public String toCsvString() {
        String res = String.format("%s,%d,%d,%d,%d\n",
                                   _symbol,
                                   _maxTimeGap,
                                   _totalVolume,
                                   _weightedAvgPrice,
                                   _maxPrice);
        return res;
    }

    public String getSymbol() {
        return _symbol;
    }

    public long getMaxTimeGap() {
        return _maxTimeGap;
    }

    public long getTotalVolume() {
        return _totalVolume;
    }

    public long getWeightedAvgPrice() {
        return _weightedAvgPrice;
    }

    public long getMaxPrice() {
        return _maxPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentSummary that = (InstrumentSummary) o;
        return _maxTimeGap == that._maxTimeGap &&
                _totalVolume == that._totalVolume &&
                _weightedAvgPrice == that._weightedAvgPrice &&
                _maxPrice == that._maxPrice &&
                Objects.equals(_symbol, that._symbol);
    }
}
