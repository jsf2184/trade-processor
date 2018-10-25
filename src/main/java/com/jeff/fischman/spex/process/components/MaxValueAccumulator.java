package com.jeff.fischman.spex.process.components;

public class MaxValueAccumulator implements Accumulator {
    private long _max;

    public MaxValueAccumulator() {
        _max = 0;
    }

    public long onValue(long v) {
        if (v > _max) {
            _max = v;
        }
        return _max;
    }

    public long getValue() {
        return _max;
    }
}
