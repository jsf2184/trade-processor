package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.process.output.Printer;

import java.util.TreeMap;

public class InstrumentStore {
    private TreeMap<String, Instrument> _map;
    private Printer _printer;

    public InstrumentStore(Printer printer) {
        _printer = printer;
        _map = new TreeMap<>();
    }

    public Instrument findOrAddInstrument(String symbol) {
        Instrument res = _map.computeIfAbsent(symbol, Instrument::new);
        return res;
    }

    public void report() {
        _map.values().forEach(i -> _printer.print(i.getInstrumentSummary()));
        _printer.close();
    }

}
