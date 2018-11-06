package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvgPriceCalculatorTests {
    @Test
    public void testNewlyConstructedGetValueReturnsZero() {
        OutputCalculator sut = new AvgPriceCalculator();
        Assert.assertEquals(0, sut.getValue());
    }

    @Test
    public void testScenario() {
        OutputCalculator sut = new AvgPriceCalculator();
        // Provide the numbers 1 to 9 whose sum is 45
        for (long i=1; i<10; i++) {
            Trade t = mock(Trade.class);
            when(t.getPrice()).thenReturn(i);
            sut.onTrade(t);
        }
        Assert.assertEquals(5L, sut.getValue());

    }
}
