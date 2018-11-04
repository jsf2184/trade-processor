package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

@SuppressWarnings("SameParameterValue")
public class MaxPriceCalculatorTests {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testMaxPriceCalculatorDelegatesForOnValue() {
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        OutputCalculator sut = new MaxPriceCalculator(maxValueAccumulator);
        Trade trade = mockTrade((1234L));
        sut.onTrade(trade);
        verify(maxValueAccumulator, times(1)).onValue(1234L);
        verify(trade, times(1)).getPrice();
    }

    static Trade mockTrade(long price) {
        Trade trade = mock(Trade.class);
        when(trade.getPrice()).thenReturn(price);
        return trade;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testMaxPriceCalculatorDelegateForGetValue() {
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        when(maxValueAccumulator.getValue()).thenReturn(1234L);
        OutputCalculator sut = new MaxPriceCalculator(maxValueAccumulator);
        Assert.assertEquals(1234L, sut.getValue());
        verify(maxValueAccumulator, times(1)).getValue();
    }

}
