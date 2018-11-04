package com.jeff.fischman.spex.bootstrap;

import com.jeff.fischman.spex.OutputField;
import com.jeff.fischman.spex.messages.TradeParser;
import com.jeff.fischman.spex.process.InstrumentStore;
import com.jeff.fischman.spex.process.OutputCalculatorFactory;
import com.jeff.fischman.spex.process.Processor;
import com.jeff.fischman.spex.process.LineProcessor;
import com.jeff.fischman.spex.process.output.Printer;

import java.util.List;
import java.util.stream.Stream;

public class Bootstrapper {
    private final Stream<String> _stream;
    private List<OutputField> _outputFields;
    private final Printer _printer;

    public Bootstrapper(Stream<String> stream,
                        List<OutputField> outputFields,
                        Printer printer)
    {
        _stream = stream;
        _outputFields = outputFields;
        _printer = printer;
    }

    public Processor create() {
        OutputCalculatorFactory outputCalculatorFactory = new OutputCalculatorFactory(_outputFields);
        InstrumentStore instrumentStore = new InstrumentStore(_printer, outputCalculatorFactory);
        TradeParser tradeParser = new TradeParser();
        LineProcessor lineProcessor = new LineProcessor(tradeParser);
        Processor res = new Processor(_stream, lineProcessor, instrumentStore);
        return res;
    }

//    public static class Test extends Bootstrapper {
//        public Test(Stream<String> stream,
//                    List<OutputField> outputFields, Printer printer)  {
//            super(stream,   printer);
//        }
//    }
}
