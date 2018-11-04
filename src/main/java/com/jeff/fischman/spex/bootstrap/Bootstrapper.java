package com.jeff.fischman.spex.bootstrap;

import com.jeff.fischman.spex.messages.TradeParser;
import com.jeff.fischman.spex.process.InstrumentStore;
import com.jeff.fischman.spex.process.Processor;
import com.jeff.fischman.spex.process.LineProcessor;
import com.jeff.fischman.spex.process.output.Printer;

import java.util.stream.Stream;

public class Bootstrapper {
    private final Stream<String> _stream;
    private final Printer _printer;

    public Bootstrapper(Stream<String> stream, Printer printer) {
        _stream = stream;
        _printer = printer;
    }

    public Processor create() {
        InstrumentStore instrumentStore = new InstrumentStore(_printer);
        TradeParser tradeParser = new TradeParser();
        LineProcessor lineProcessor = new LineProcessor(tradeParser);
        Processor res = new Processor(_stream, lineProcessor, instrumentStore);
        return res;
    }

    public static class Test extends Bootstrapper {
        public Test(Stream<String> stream, Printer printer)  {
            super(stream, printer);
        }
    }
}
