package com.jeff.fischman.spex.messages;

import java.util.List;
import java.util.Objects;

public class InstrumentSummary {
    private String _symbol;
    List<Long> _values;

    public InstrumentSummary(String symbol,
                             List<Long> values)
    {
        _symbol = symbol;
        _values = values;
    }

    public String toCsvString() {
        StringBuilder sb = new StringBuilder(_symbol);
        _values.forEach(v -> {
            sb.append(",");
            sb.append(v);
        });
        sb.append('\n');
        return sb.toString();
    }

    public String getSymbol() {
        return _symbol;
    }

//    public long getMaxTimeGap() {
//        return _maxTimeGap;
//    }
//
//    public long getTotalVolume() {
//        return _totalVolume;
//    }
//
//    public long getWeightedAvgPrice() {
//        return _weightedAvgPrice;
//    }
//
//    public long getMaxPrice() {
//        return _maxPrice;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        InstrumentSummary that = (InstrumentSummary) o;
//        return _maxTimeGap == that._maxTimeGap &&
//                _totalVolume == that._totalVolume &&
//                _weightedAvgPrice == that._weightedAvgPrice &&
//                _maxPrice == that._maxPrice &&
//                Objects.equals(_symbol, that._symbol);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentSummary summary = (InstrumentSummary) o;
        return Objects.equals(_symbol, summary._symbol) &&
                Objects.equals(_values, summary._values);
    }

}
