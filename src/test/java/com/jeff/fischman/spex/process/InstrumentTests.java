package com.jeff.fischman.spex.process;

// The class we are testing here: Instrument uses instances of the following component classes
//   AdditionAccumulator
//   MaxGapAccumulator
//   MaxValueAccumulator
//   WeightedAvgAccumulator
//
// Here, we are going to do both integration tests that use real versions of the component classes as well as
// tests that focus on Instrument's ability to delegate properly to its component classes.
// Those 'delegation' tests use mocked implementations of the component classes
//

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.components.AdditionAccumulator;
import com.jeff.fischman.spex.process.components.MaxGapAccumulator;
import com.jeff.fischman.spex.process.components.MaxValueAccumulator;
import com.jeff.fischman.spex.process.components.WeightedAvgAccumulator;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class InstrumentTests {
    @Test
    public void testIntegrationScenario() {
        List<Trade> trades = Arrays.asList(
                new Trade(52924702, "aaa", 13, 1136),
                new Trade(52930489, "aaa", 18, 1222),
                new Trade(52931654, "aaa", 9, 1077)
        );

        Instrument sut = new Instrument("aaa");
        trades.forEach(sut::onTrade);
        InstrumentSummary instrumentSummary = sut.getInstrumentSummary();
        validateInstrumentSummary(instrumentSummary);
    }

    @Test
    public void testOnTradeDelegation() {
        Trade t = new Trade(52924702, "aaa", 13, 1136);
        MaxValueAccumulator maxPriceAccumulator = mock(MaxValueAccumulator.class);
        MaxGapAccumulator timeGapAccumulator = mock(MaxGapAccumulator.class);
        AdditionAccumulator totalVolumeAccumulator = mock(AdditionAccumulator.class);
        WeightedAvgAccumulator weightedAvgAccumulator = mock(WeightedAvgAccumulator.class);

        Instrument sut = new Instrument("aaa",
                                        maxPriceAccumulator,
                                        timeGapAccumulator,
                                        totalVolumeAccumulator,
                                        weightedAvgAccumulator);

        sut.onTrade(t);
        verify(maxPriceAccumulator, times(1)).onValue(t.getPrice());
        verify(timeGapAccumulator, times(1)).onValue(t.getTimestamp());
        verify(totalVolumeAccumulator, times(1)).onValue(t.getQuantity());
        verify(weightedAvgAccumulator, times(1)).onTrade(t.getPrice(), t.getQuantity());
    }

    @Test
    public void testGetInstrumentSummaryDelegation() {
        MaxValueAccumulator maxPriceAccumulator = mock(MaxValueAccumulator.class);
        when(maxPriceAccumulator.getValue()).thenReturn(1222L);

        MaxGapAccumulator timeGapAccumulator = mock(MaxGapAccumulator.class);
        when(timeGapAccumulator.getValue()).thenReturn(5787L);

        AdditionAccumulator totalVolumeAccumulator = mock(AdditionAccumulator.class);
        when(totalVolumeAccumulator.getValue()).thenReturn(40L);

        WeightedAvgAccumulator weightedAvgAccumulator = mock(WeightedAvgAccumulator.class);
        when(weightedAvgAccumulator.getValue()).thenReturn(1161L);

        Instrument sut = new Instrument("aaa",
                                        maxPriceAccumulator,
                                        timeGapAccumulator,
                                        totalVolumeAccumulator,
                                        weightedAvgAccumulator);

        InstrumentSummary instrumentSummary = sut.getInstrumentSummary();
        validateInstrumentSummary(instrumentSummary);
    }

    private static void validateInstrumentSummary(InstrumentSummary instrumentSummary) {
        Assert.assertEquals("aaa", instrumentSummary.getSymbol());
        Assert.assertEquals(5787L, instrumentSummary.getMaxTimeGap());
        Assert.assertEquals(40, instrumentSummary.getTotalVolume());
        Assert.assertEquals(1161L, instrumentSummary.getWeightedAvgPrice());
        Assert.assertEquals(1222L, instrumentSummary.getMaxPrice());
    }
}
