package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.process.components.LineProcessor;

import java.util.stream.Stream;

public class Processor {
    private final Stream<String> _stream;
    private LineProcessor _lineProcessor;
    private InstrumentStore _instrumentStore;

    public Processor(Stream<String> stream,
                     LineProcessor lineProcessor,
                     InstrumentStore instrumentStore)
    {
        _stream = stream;
        _lineProcessor = lineProcessor;
        _instrumentStore = instrumentStore;
    }

    public void run() {
        _stream.forEach(line -> _lineProcessor.processLine(line, _instrumentStore));
        _instrumentStore.report();
    }

}
