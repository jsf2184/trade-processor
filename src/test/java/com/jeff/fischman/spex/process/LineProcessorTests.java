package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.InstrumentSummary;
import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.messages.TradeParser;
import org.junit.Assert;
import org.junit.Test;
import sun.util.resources.cldr.ar.CalendarData_ar_SY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class LineProcessorTests {
    private String priorTradeStr = "122,aaa,40,99";
    private String goodTradeStr1 = "123,aaa,50,100";
    private String goodTradeStr2 = "124,aaa,11,101";
    private String goodTradeStr3 = "124,aaa,9,102";

    private Trade goodTrade1 = new Trade(123L, "aaa", 50L, 100L);
    private Trade goodTrade2 = new Trade(124L, "aaa", 11L, 101L);
    private Trade goodTrade3 = new Trade(124L, "aaa", 9L,  102L);
    private String badTradeStr = "bad";
    private InstrumentStore _instrumentStore;
    private MockInstrument _instrument;

    @Test
    public void testsSkipsInstrumentStoreIfLineDoesntParse() {
        LineProcessor sut = createSut();
        sut.processLine(badTradeStr, _instrumentStore);
        verifyNoMoreInteractions(_instrumentStore);
    }

    @Test
    public void testGoodLineHandlingFindsInstrumentAndPassesItTheTrade() {
        LineProcessor sut = createSut();
        sut.processLine(goodTradeStr1, _instrumentStore);
        verify(_instrumentStore, times(1)).findOrAddInstrument("aaa");
        _instrument.validateExpectedTrades(Arrays.asList(goodTrade1));
        verifyNoMoreInteractions(_instrumentStore);
    }

    @Test
    public void testProcesses3TradesIfTimestampsOk() {
        LineProcessor sut = createSut();
        sut.processLine(goodTradeStr1, _instrumentStore);
        verify(_instrumentStore, times(1)).findOrAddInstrument("aaa");
        _instrument.validateExpectedTrades(Arrays.asList(goodTrade1));

        // The 2nd trade has a larger timestamp than the 1st trade
        sut.processLine(goodTradeStr2, _instrumentStore);
        verify(_instrumentStore, times(2)).findOrAddInstrument("aaa");
        _instrument.validateExpectedTrades(Arrays.asList(goodTrade1, goodTrade2));

        // The 3rd trade has the same timestamp as the 2nd trade
        sut.processLine(goodTradeStr3, _instrumentStore);
        verify(_instrumentStore, times(3)).findOrAddInstrument("aaa");
        _instrument.validateExpectedTrades(Arrays.asList(goodTrade1, goodTrade2, goodTrade3));

        verifyNoMoreInteractions(_instrumentStore);
    }

    @Test
    public void testRejects2ndTradeIfTimestampsDecrease() {
        LineProcessor sut = createSut();
        sut.processLine(goodTradeStr1, _instrumentStore);
        verify(_instrumentStore, times(1)).findOrAddInstrument("aaa");
        _instrument.validateExpectedTrades(Arrays.asList(goodTrade1));

        // now feed it an out of order tradeStr.
        sut.processLine(priorTradeStr, _instrumentStore);

        // dont use instrumentStore 2ndTime since out of order trade received
        verify(_instrumentStore, times(1)).findOrAddInstrument("aaa");
        verifyNoMoreInteractions(_instrumentStore);
    }


    LineProcessor createSut() {
        TradeParser tradeParser = new TradeParser();
        _instrumentStore = mock(InstrumentStore.class);
        _instrument = new MockInstrument();
        when(_instrumentStore.findOrAddInstrument("aaa")).thenReturn(_instrument);
        LineProcessor sut = new LineProcessor(tradeParser);
        return sut;
    }

    // For testing purposes had to create this special MockInstrument due to mockito problems
    // that came up once our Trade class became mutable.
    //
    public static class MockInstrument implements Instrument {
        List<Trade> _submittedTrades = new ArrayList<>();
        @Override
        public String getSymbol() {
            return null;
        }

        @Override
        public void onTrade(Trade trade) {
            _submittedTrades.add(new Trade(trade));
        }

        @Override
        public InstrumentSummary getInstrumentSummary() {
            return null;
        }

        public void validateExpectedTrades(List<Trade> expected) {
            Assert.assertEquals(expected, _submittedTrades);
        }
    }

}
