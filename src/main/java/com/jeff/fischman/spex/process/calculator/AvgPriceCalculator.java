package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;

public class AvgPriceCalculator implements  OutputCalculator {
    long _priceSum;
    long _numTrades;

    public AvgPriceCalculator() {
        _priceSum = 0;
        _numTrades = 0;
    }

    @Override
    public void onTrade(Trade trade) {
        long price = trade.getPrice();
        _priceSum += price;
        _numTrades++;
    }

    @Override
    public long getValue() {
        if (_priceSum == 0 || _numTrades == 0) {
            return 0;
        }
        long res = _priceSum / _numTrades;
        return res;
    }
}
