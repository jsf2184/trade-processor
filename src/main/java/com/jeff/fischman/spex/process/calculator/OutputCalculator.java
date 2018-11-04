package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;

public interface OutputCalculator {
    void onTrade(Trade trade);
    long getValue();
}
