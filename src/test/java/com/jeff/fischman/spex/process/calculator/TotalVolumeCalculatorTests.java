package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

@SuppressWarnings("SameParameterValue")
public class TotalVolumeCalculatorTests {
    @Test
    public void testMaxPriceCalculatorDelegatesForOnValue() {
        AdditionAccumulator additionAccumulator = mock(AdditionAccumulator.class);
        OutputCalculator sut = new TotalVolumeCalculator(additionAccumulator);
        sut.onTrade(mockTrade(1234L));
        verify(additionAccumulator, times(1)).onValue(1234L);
    }

    static Trade mockTrade(long qty) {
        Trade trade = mock(Trade.class);
        when(trade.getQuantity()).thenReturn(qty);
        return trade;
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testMaxPriceCalculatorDelegateForGetValue() {
        AdditionAccumulator additionAccumulator = mock(AdditionAccumulator.class);
        when(additionAccumulator.getValue()).thenReturn(1234L);
        OutputCalculator sut = new TotalVolumeCalculator(additionAccumulator);
        Assert.assertEquals(1234L, sut.getValue());
        verify(additionAccumulator, times(1)).getValue();
    }

}
