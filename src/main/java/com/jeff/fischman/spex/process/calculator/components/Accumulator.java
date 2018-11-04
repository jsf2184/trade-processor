package com.jeff.fischman.spex.process.calculator.components;

public interface Accumulator {
    long onValue(long v);
    long getValue();
}
