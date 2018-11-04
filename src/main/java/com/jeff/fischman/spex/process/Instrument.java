package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.MaxPriceCalculator;
import com.jeff.fischman.spex.process.calculator.TimeGapCalculator;
import com.jeff.fischman.spex.process.calculator.TotalVolumeCalculator;
import com.jeff.fischman.spex.process.calculator.WeightedAvgCalculator;

public class Instrument {
    private final String _symbol;
    private final MaxPriceCalculator _maxPriceCalculator;
    private final TimeGapCalculator _timeGapCalculator;
    private final TotalVolumeCalculator _totalVolumeCalculator;
    private final WeightedAvgCalculator _weightedAvgCalculator;

    public Instrument(String symbol) {
        this(symbol,
             new MaxPriceCalculator(),
             new TimeGapCalculator(),
             new TotalVolumeCalculator(),
             new WeightedAvgCalculator());
    }

    public Instrument(String symbol,
                      MaxPriceCalculator maxPriceCalculator,
                      TimeGapCalculator timeGapCalculator,
                      TotalVolumeCalculator totalVolumeCalculator,
                      WeightedAvgCalculator weightedAvgCalculator)
    {
        _symbol = symbol;
        _maxPriceCalculator = maxPriceCalculator;
        _timeGapCalculator = timeGapCalculator;
        _totalVolumeCalculator = totalVolumeCalculator;
        _weightedAvgCalculator = weightedAvgCalculator;
    }

    public String getSymbol() {
        return _symbol;
    }

    public void onTrade(Trade trade) {
        long price = trade.getPrice();
        long quantity = trade.getQuantity();
        long timestamp = trade.getTimestamp();
        _maxPriceCalculator.onValue(price);
        _totalVolumeCalculator.onValue(quantity);
        _timeGapCalculator.onValue(timestamp);
        _weightedAvgCalculator.onTrade(price, quantity);
    }

    public InstrumentSummary getInstrumentSummary() {
        InstrumentSummary res = new InstrumentSummary(_symbol,
                                                      _timeGapCalculator.getValue(),
                                                      _totalVolumeCalculator.getValue(),
                                                      _weightedAvgCalculator.getValue(),
                                                      _maxPriceCalculator.getValue());
        return res;
    }


}
