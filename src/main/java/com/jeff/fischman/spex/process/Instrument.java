package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;

public interface Instrument {
    String getSymbol();

    void onTrade(Trade trade);

    InstrumentSummary getInstrumentSummary();
}
