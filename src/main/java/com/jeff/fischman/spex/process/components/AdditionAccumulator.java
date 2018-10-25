package com.jeff.fischman.spex.process.components;

public class AdditionAccumulator implements  Accumulator{
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
