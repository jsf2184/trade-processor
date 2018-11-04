package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;
import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TotalVolumeCalculatorTests {
    @Test
    public void testMaxPriceCalculatorDelegatesForOnValue() {
        AdditionAccumulator additionAccumulator = mock(AdditionAccumulator.class);
        TotalVolumeCalculator sut = new TotalVolumeCalculator(additionAccumulator);
        sut.onValue(1234L);
        verify(additionAccumulator, times(1)).onValue(1234L);
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
