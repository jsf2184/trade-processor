package com.jeff.fischman.spex.process.calculator;

import com.jeff.fischman.spex.process.calculator.WeightedAvgAccumulator;
import com.jeff.fischman.spex.process.calculator.components.AdditionAccumulator;
import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

// The class we are testing here: WeightedValueAccumulator uses 2 instances of the
// simple component class: AdditionAccumulator.
//
// Here, we are going to do both integration tests that use real AdditionAccumulators as well as
// tests that focus on WeightedValueAccumulator's ability to delegate properly to AdditionAccumulators.
// Those 'delegation' tests use mocked implementations of AdditionAccumulator
//
@SuppressWarnings("ResultOfMethodCallIgnored")
public class WeightedAvgAccumulatorTests {
    // *************************************************************************************
    // Start with "integration" tests that tests a sequence of calls to WeightedValueAccumulator
    // using true instantiations of its component class: AdditionAccumulator
    // *************************************************************************************

    @Test
    public void testNewWeightedAvgAccumulatorReturnsZero() {
        WeightedAvgAccumulator sut = new WeightedAvgAccumulator();
        Assert.assertEquals(0, sut.getValue());
    }

    @Test
    public void testScenarioWithOccasionalTruncation() {
        WeightedAvgAccumulator sut = new WeightedAvgAccumulator();

        sut.onTrade(50, 1);
        Assert.assertEquals(50, sut.getValue());

        sut.onTrade(51, 2);
        Assert.assertEquals(50, sut.getValue()); // true res is 152/3 = 50.666 truncates to 50

        sut.onTrade(74, 2);
        Assert.assertEquals(60, sut.getValue()); // 300/5 = 60

        sut.onTrade(40, 5);
        Assert.assertEquals(50, sut.getValue()); // 500/10 = 50

        sut.onTrade(49, 1);
        Assert.assertEquals(49, sut.getValue()); // true res is 549/11 = 49.9 truncates to 49

        sut.onTrade(51, 1);
        Assert.assertEquals(50, sut.getValue()); // 600/12 = 50
    }

    // *************************************************************************************
    // And now a simple 'delegation' test
    // *************************************************************************************

    @Test
    public void testDelegationDuringOnTrade() {
        AdditionAccumulator runningCostTotal = mock(AdditionAccumulator.class);
        AdditionAccumulator runningQtyTotal = mock(AdditionAccumulator.class);
        WeightedAvgAccumulator sut = new WeightedAvgAccumulator(runningCostTotal, runningQtyTotal);
        sut.onTrade(60, 5);
        verify(runningCostTotal, times(1)).onValue(300); // passes the product to runningCostTotal
        verify(runningQtyTotal, times(1)).onValue(5);    // passes the qty to runningCostTotal
    }

    @Test
    public void testDelegationDuringGetValue() {
        AdditionAccumulator runningCostTotal = mock(AdditionAccumulator.class);
        AdditionAccumulator runningQtyTotal = mock(AdditionAccumulator.class);
        WeightedAvgAccumulator sut = new WeightedAvgAccumulator(runningCostTotal, runningQtyTotal);
        when(runningCostTotal.getValue()).thenReturn(300L);
        when(runningQtyTotal.getValue()).thenReturn(5L);
        Assert.assertEquals(60L, sut.getValue());
        verify(runningCostTotal, times(1)).getValue();
        verify(runningQtyTotal, times(1)).getValue();
    }

    @Test
    public void testDelegationAndTruncationInGetValue() {
        AdditionAccumulator runningCostTotal = mock(AdditionAccumulator.class);
        AdditionAccumulator runningQtyTotal = mock(AdditionAccumulator.class);
        WeightedAvgAccumulator sut = new WeightedAvgAccumulator(runningCostTotal, runningQtyTotal);
        when(runningCostTotal.getValue()).thenReturn(299L);
        when(runningQtyTotal.getValue()).thenReturn(5L);
        Assert.assertEquals(59L, sut.getValue());
        verify(runningCostTotal, times(1)).getValue();
        verify(runningQtyTotal, times(1)).getValue();
    }


}
