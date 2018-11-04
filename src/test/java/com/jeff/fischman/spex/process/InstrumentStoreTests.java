package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.OutputField;
import com.jeff.fischman.spex.process.output.Printer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

public class InstrumentStoreTests {
    private Printer _printer;

    @Test
    public void testFindOrAdd() {
        InstrumentStore sut = createSut();
        Instrument instrument1 = sut.findOrAddInstrument("aaa");
        Assert.assertEquals("aaa", instrument1.getSymbol());
        // now do it a second time
        Instrument instrument2 = sut.findOrAddInstrument("aaa");
        Assert.assertSame(instrument1, instrument2);
    }

    @Test
    public void testReportPassesInstrumentsToReportInProperOrder() {
        InstrumentStore sut = createSut();
        Instrument instrumentC = sut.findOrAddInstrument("c");
        Instrument instrumentA = sut.findOrAddInstrument("a");
        Instrument instrumentB = sut.findOrAddInstrument("b");

        // report() should iterate through the instruments in sorted order: a,b,c
        sut.report();

        // Uses mockito's inorder capability to verify the printer's print() method was called in right order.
        InOrder inOrder = Mockito.inOrder(_printer);
        inOrder.verify(_printer).print(instrumentA.getInstrumentSummary());
        inOrder.verify(_printer).print(instrumentB.getInstrumentSummary());
        inOrder.verify(_printer).print(instrumentC.getInstrumentSummary());
    }

    private  InstrumentStore createSut() {
        _printer = mock(Printer.class);
        OutputCalculatorFactory outputCalculatorFactory = new OutputCalculatorFactory(Arrays.asList(OutputField.values()));
        InstrumentStore res = new InstrumentStore(_printer, outputCalculatorFactory);
        return res;
    }
}
