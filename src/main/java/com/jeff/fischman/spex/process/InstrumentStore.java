package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.process.calculator.OutputCalculator;
import com.jeff.fischman.spex.process.output.Printer;

import java.util.List;
import java.util.TreeMap;

public class InstrumentStore {
    private TreeMap<String, Instrument> _map;
    private Printer _printer;
    OutputCalculatorFactory _outputCalculatorFactory;

    public InstrumentStore(Printer printer,  OutputCalculatorFactory outputCalculatorFactory) {
        _printer = printer;
        _outputCalculatorFactory = outputCalculatorFactory;
        _map = new TreeMap<>();
    }

    public Instrument findOrAddInstrument(String symbol) {
        Instrument res = _map.computeIfAbsent(symbol, this::createInstrument);
        return res;
    }

    public void report() {
        _map.values().forEach(i -> _printer.print(i.getInstrumentSummary()));
        _printer.close();
    }

    public Instrument createInstrument(String symbol) {
        List<OutputCalculator> outputCalculators = _outputCalculatorFactory.create();
        Instrument res = new InstrumentImpl(symbol, outputCalculators);
        return res;
    }



}
