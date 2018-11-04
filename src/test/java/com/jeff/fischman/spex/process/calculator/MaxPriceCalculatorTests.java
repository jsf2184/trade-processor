package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MaxPriceCalculatorTests {
    @Test
    public void testMaxPriceCalculatorDelegatesForOnValue() {
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        MaxPriceCalculator sut = new MaxPriceCalculator(maxValueAccumulator);
        sut.onValue(1234L);
        verify(maxValueAccumulator, times(1)).onValue(1234L);
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
