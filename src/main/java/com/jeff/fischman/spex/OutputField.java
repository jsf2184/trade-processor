package com.jeff.fischman.spex;

import com.jeff.fischman.spex.process.calculator.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Supplier;

public enum OutputField {
    timegap('g', TimeGapCalculator::new),
    volume('v', TotalVolumeCalculator::new),
    wavg('w', WeightedAvgCalculator::new),
    maxprice('m', MaxPriceCalculator::new);

    char _abbrev;
    private Supplier<OutputCalculator> _supplier;

    OutputField(char abbrev, Supplier<OutputCalculator> supplier)
    {
        _abbrev = abbrev;
        _supplier = supplier;
    }

    public OutputCalculator createCalculator() {
        OutputCalculator res = _supplier.get();
        return res;
    }

    public static OutputField abbrevToEnum(char c) {
        return _map.get(c);
    }

    public static String getAllAbbrevs() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(OutputField.values()).forEach(f -> sb.append(f._abbrev));
        return sb.toString();
    }

    public static String getAbbrevHelp() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(OutputField.values()).forEach(f -> sb.append(String.format("    %c - %s\n", f._abbrev, f)));
        return sb.toString();
    }

    static HashMap<Character, OutputField> _map = new HashMap<>();
    static {
        Arrays.stream(OutputField.values()).forEach(f -> _map.put(f._abbrev, f));
    }
}
