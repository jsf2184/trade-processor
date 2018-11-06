package com.jeff.fischman.spex.process;

// The class we are testing here: Instrument uses instances of the following component classes
//   MaxPriceCalculator
//   TimeGapCalculator
//   TotalVolumeCalculator
//   WeightedAvgCalculator
//
// Here, we are going to do both integration tests that use real versions of the component classes as well as
// tests that focus on Instrument's ability to delegate properly to its component classes.
// Those 'delegation' tests use mocked implementations of the component classes
//

import com.jeff.fischman.spex.OutputField;
import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.process.calculator.*;
import org.junit.Assert;
import org.junit.Test;
import sun.management.Agent;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class InstrumentTests {
    @Test
    public void testIntegrationScenario() {
        List<Trade> trades = Arrays.asList(
                new Trade(52924702, "aaa", 13, 1136),
                new Trade(52930489, "aaa", 18, 1222),
                new Trade(52931654, "aaa", 9, 1077)
        );

        List<OutputField> outputFields = Arrays.asList(OutputField.values());
        OutputCalculatorFactory outputCalculatorFactory = new OutputCalculatorFactory(outputFields);
        List<OutputCalculator> calculators = outputCalculatorFactory.create();

        Instrument sut = new InstrumentImpl("aaa", calculators);
        trades.forEach(sut::onTrade);
        InstrumentSummary instrumentSummary = sut.getInstrumentSummary();
        validateInstrumentSummary(instrumentSummary);
    }

    @Test
    public void testOnTradeDelegation() {
        Trade t = new Trade(52924702, "aaa", 13, 1136);

        MaxPriceCalculator maxPriceCalculator = mock(MaxPriceCalculator.class);
        TimeGapCalculator timeGapCalculator = mock(TimeGapCalculator.class);
        TotalVolumeCalculator totalVolumeCalculator = mock(TotalVolumeCalculator.class);
        WeightedAvgCalculator weightedAvgCalculator = mock(WeightedAvgCalculator.class);

        List<OutputCalculator> calculators = Arrays.asList(
                timeGapCalculator,
                totalVolumeCalculator,
                weightedAvgCalculator,
                maxPriceCalculator);

        Instrument sut = new InstrumentImpl("aaa", calculators);

        sut.onTrade(t);
        verify(maxPriceCalculator, times(1)).onTrade(t);
        verify(timeGapCalculator, times(1)).onTrade(t);
        verify(totalVolumeCalculator, times(1)).onTrade(t);
        verify(weightedAvgCalculator, times(1)).onTrade(t);
    }

    @Test
    public void testGetInstrumentSummaryDelegation() {
        MaxPriceCalculator maxPriceCalculator = mock(MaxPriceCalculator.class);
        when(maxPriceCalculator.getValue()).thenReturn(1222L);

        TimeGapCalculator timeGapCalculator = mock(TimeGapCalculator.class);
        when(timeGapCalculator.getValue()).thenReturn(5787L);

        TotalVolumeCalculator totalVolumeCalculator = mock(TotalVolumeCalculator.class);
        when(totalVolumeCalculator.getValue()).thenReturn(40L);

        WeightedAvgCalculator weightedAvgCalculator = mock(WeightedAvgCalculator.class);
        when(weightedAvgCalculator.getValue()).thenReturn(1161L);

        AvgPriceCalculator avgPriceCalculator = mock(AvgPriceCalculator.class);
        when(avgPriceCalculator.getValue()).thenReturn(1145L);


        List<OutputCalculator> calculators = Arrays.asList(
                timeGapCalculator,
                totalVolumeCalculator,
                weightedAvgCalculator,
                maxPriceCalculator,
                avgPriceCalculator);


        Instrument sut = new InstrumentImpl("aaa", calculators);

        InstrumentSummary instrumentSummary = sut.getInstrumentSummary();
        validateInstrumentSummary(instrumentSummary);
    }

    private static void validateInstrumentSummary(InstrumentSummary instrumentSummary) {
        Assert.assertEquals("aaa", instrumentSummary.getSymbol());
        Assert.assertEquals("aaa,5787,40,1161,1222,1145\n", instrumentSummary.toCsvString());
    }
}
