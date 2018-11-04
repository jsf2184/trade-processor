package com.jeff.fischman.spex.process.calculator.components;

import com.jeff.fischman.spex.process.calculator.components.Accumulator;

public class AdditionAccumulator implements Accumulator {
    private long _value;

    public AdditionAccumulator() {
        _value = 0;
    }

    public long onValue(long v) {
        _value += v;
        return _value;
    }

    public long getValue() {
        return _value;
    }
}
