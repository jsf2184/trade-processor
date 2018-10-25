package com.jeff.fischman.spex.process.components;

public class MaxGapAccumulator  implements  Accumulator{
    private MaxValueAccumulator _maxValueAccumulator;
    private long _prior;

    public MaxGapAccumulator() {
        this(new MaxValueAccumulator());
    }

    MaxGapAccumulator(MaxValueAccumulator maxValueAccumulator) {
        _maxValueAccumulator = maxValueAccumulator;
        _prior = -1;
    }

    public long onValue(long v) {
        if (v < 0 || v <_prior) {
            throw new RuntimeException("MaxGapAccumulator.onValue(): unexpected input");
        }
        if (_prior < 0) {
            _prior = v;
        }
        long delta = v - _prior;
        _prior = v;
        long res = _maxValueAccumulator.onValue(delta);
        return res;
    }

    public long getValue() {
        return _maxValueAccumulator.getValue();
    }


}
