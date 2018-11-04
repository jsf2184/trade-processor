package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;

public class TimeGapCalculator implements OutputCalculator {
    private MaxValueAccumulator _maxValueAccumulator;
    private long _prior;

    public TimeGapCalculator() {
        this(new MaxValueAccumulator());
    }

    public TimeGapCalculator(MaxValueAccumulator maxValueAccumulator) {
        _maxValueAccumulator = maxValueAccumulator;
        _prior = -1;
    }

    public void onValue(Long timestamp) {
        if (timestamp < 0 || timestamp <_prior) {
            throw new RuntimeException("TimeGapCalculator.onTrade(): unexpected input");
        }
        if (_prior < 0) {
            _prior = timestamp;
        }
        long delta = timestamp - _prior;
        _prior = timestamp;
        _maxValueAccumulator.onValue(delta);
    }

    public long getValue() {
        return _maxValueAccumulator.getValue();
    }


}
