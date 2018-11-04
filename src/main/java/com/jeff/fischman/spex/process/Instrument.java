package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;
import com.jeff.fischman.spex.process.calculator.MaxGapAccumulator;
import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;
import com.jeff.fischman.spex.process.calculator.WeightedAvgAccumulator;

public class Instrument {
    private final String _symbol;
    private final MaxValueAccumulator _maxPriceAccumulator;
    private final MaxGapAccumulator _timeGapAccumulator;
    private final AdditionAccumulator _totalVolumeAccumulator;
    private final WeightedAvgAccumulator _weightedAvgAccumulator;

    public Instrument(String symbol) {
        this(symbol,
             new MaxValueAccumulator(),
             new MaxGapAccumulator(),
             new AdditionAccumulator(),
             new WeightedAvgAccumulator());
    }

    public Instrument(String symbol,
                      MaxValueAccumulator maxPriceAccumulator,
                      MaxGapAccumulator timeGapAccumulator,
                      AdditionAccumulator totalVolumeAccumulator,
                      WeightedAvgAccumulator weightedAvgAccumulator)
    {
        _symbol = symbol;
        _maxPriceAccumulator = maxPriceAccumulator;
        _timeGapAccumulator = timeGapAccumulator;
        _totalVolumeAccumulator = totalVolumeAccumulator;
        _weightedAvgAccumulator = weightedAvgAccumulator;
    }

    public String getSymbol() {
        return _symbol;
    }

    public void onTrade(Trade trade) {
        long price = trade.getPrice();
        long quantity = trade.getQuantity();
        long timestamp = trade.getTimestamp();
        _maxPriceAccumulator.onValue(price);
        _totalVolumeAccumulator.onValue(quantity);
        _timeGapAccumulator.onValue(timestamp);
        _weightedAvgAccumulator.onTrade(price, quantity);
    }

    public InstrumentSummary getInstrumentSummary() {
        InstrumentSummary res = new InstrumentSummary(_symbol,
                                                      _timeGapAccumulator.getValue(),
                                                      _totalVolumeAccumulator.getValue(),
                                                      _weightedAvgAccumulator.getValue(),
                                                      _maxPriceAccumulator.getValue());
        return res;
    }


}
