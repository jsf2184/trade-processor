package com.jeff.fischman.spex.process.components;

import org.junit.Assert;
import org.junit.Test;

public class MaxValueAccumulatorTests {
    @Test
    public void testNewAccumulatorHasValueZero() {
        Accumulator sut = new MaxValueAccumulator();
        Assert.assertEquals(0L, sut.getValue());
    }

    @Test
    public void testScenario() {
        Accumulator sut = new MaxValueAccumulator();
        Assert.assertEquals(3L, sut.onValue(3L));
        Assert.assertEquals(3L, sut.onValue(2L));
        Assert.assertEquals(3L, sut.onValue(1L));
        Assert.assertEquals(4L, sut.onValue(4L));
        Assert.assertEquals(4L, sut.getValue());
    }

}
