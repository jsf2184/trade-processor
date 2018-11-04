package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.process.calculator.components.MaxValueAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;


// The class we are testing: MaxGapAccumulator uses a simple component class: MaxValueAccumulator.
// Here, we are going to do both integration tests that use a real MaxValueAccumulator as well as
// tests that focus on MaxGapAccumulator's ability to delegate properly to MaxCalueAccumulator.
// Those 'delegation' tests use mocked implementations of MaxValueAccumulator
//
public class TimeGapCalculatorTests {

    // *************************************************************************************
    // Start with "integration" tests that tests a sequence of calls to MaxGapAccumulator
    // using a true implementation of its component class: MaxValueAccumulator
    // *************************************************************************************

    @Test
    public void testNewAccumulatorHasValueZero() {
        OutputCalculator sut = new TimeGapCalculator();
        Assert.assertEquals(0L, sut.getValue());
    }

    @Test
    public void testIntegrationScenario() {
        TimeGapCalculator sut = new TimeGapCalculator();
        Assert.assertEquals(0L, sut.getValue());

        sut.onValue(7L);
        Assert.assertEquals(0L, sut.getValue());

        sut.onValue(12L);  // Gap of 5
        Assert.assertEquals(5L, sut.getValue());

        sut.onValue(14L);  // Gap of 2 is less than 5
        Assert.assertEquals(5L, sut.getValue());

        sut.onValue(19L);  // Gap of 5, max is still 5
        Assert.assertEquals(5L, sut.getValue());

        sut.onValue(25L);  // Gap of 6 is our new max
        Assert.assertEquals(6L, sut.getValue());

    }


    // *************************************************************************************
    // The following tests use Mockito to focus on MaxGapAccumulator properly delegating to
    // its component class: MaxValueAccumulator
    // *************************************************************************************
    @Test
    public void testFirstInvocationPassesZeroToMaxValueAccumulatorAndReturnsZero() {
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        when(maxValueAccumulator.onValue(3476L)).thenReturn(0L);
        TimeGapCalculator sut = new TimeGapCalculator(maxValueAccumulator);
        sut.onValue(3476L);
        Assert.assertEquals(0L, sut.getValue());
        verify(maxValueAccumulator, times(1)).onValue(0L);
    }

    @Test
    public void testSubsequentInvocationPassesDifferenceToMaxValueAccumulator() {

        // Prime it with a 1st invocation
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        TimeGapCalculator sut = new TimeGapCalculator(maxValueAccumulator);
        sut.onValue(3476L);

        // Now, in this subsequent invocation, see that we pass the time difference to the maxValueAccumulator
        when(maxValueAccumulator.onValue(5L)).thenReturn(5L);
        when(maxValueAccumulator.getValue()).thenReturn(5L);
        sut.onValue(3481L);
        Assert.assertEquals(5, sut.getValue()); // should return 3581 - 3476  = 5
        verify(maxValueAccumulator, times(1)).onValue(5L);
    }

    @Test
    public void testSmallerTimestampCausesRuntimeException() {
        MaxValueAccumulator maxValueAccumulator = mock(MaxValueAccumulator.class);
        TimeGapCalculator sut = new TimeGapCalculator(maxValueAccumulator);
        sut.onValue(3476L);

        boolean caught = false;
        try {
            sut.onValue(3475L);
        } catch (RuntimeException ignore) {
            caught = true;
        }
        Assert.assertTrue(caught);
    }


}
