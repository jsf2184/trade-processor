package com.jeff.fischman.spex.process.components;

public interface Accumulator {
    long onValue(long v);
    long getValue();
}
