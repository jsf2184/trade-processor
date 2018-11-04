package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.*;

import java.util.ArrayList;
import java.util.List;

public class InstrumentImpl implements Instrument {
    private final String _symbol;
    private List<OutputCalculator> _outputCalculators;

    public InstrumentImpl(String symbol, List<OutputCalculator> outputCalculators)
    {
        _symbol = symbol;
        _outputCalculators = outputCalculators;
    }

    @Override
    public String getSymbol() {
        return _symbol;
    }

    @Override
    public void onTrade(Trade trade) {
        _outputCalculators.forEach(c -> c.onTrade(trade));
    }

    @Override
    public InstrumentSummary getInstrumentSummary() {
        List<Long> values = new ArrayList<>();
        _outputCalculators.forEach(c -> values.add(c.getValue()));
        InstrumentSummary res = new InstrumentSummary(_symbol, values);
        return res;
    }
}
