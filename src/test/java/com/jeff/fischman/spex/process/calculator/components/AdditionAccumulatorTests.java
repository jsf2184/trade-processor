package com.jeff.fischman.spex.process.calculator.components;

import com.jeff.fischman.spex.process.calculator.components.Accumulator;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;
import org.junit.Assert;
import org.junit.Test;

public class AdditionAccumulatorTests {

    @Test
    public void testNewAccumulatorHasValueZero() {
        Accumulator sut = new AdditionAccumulator();
        Assert.assertEquals(0L, sut.getValue());
    }

    @Test
    public void testScenario() {
        Accumulator sut = new AdditionAccumulator();
        Assert.assertEquals(3L, sut.onValue(3L));
        Assert.assertEquals(5L, sut.onValue(2L));
        Assert.assertEquals(6L, sut.onValue(1L));
        Assert.assertEquals(6L, sut.getValue());
    }
}
