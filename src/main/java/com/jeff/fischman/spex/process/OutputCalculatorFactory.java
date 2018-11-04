package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.OutputField;
import com.jeff.fischman.spex.process.calculator.OutputCalculator;

import java.util.ArrayList;
import java.util.List;

public class OutputCalculatorFactory {
    List<OutputField> _outputFields;

    public OutputCalculatorFactory(List<OutputField> outputFields) {
        _outputFields = outputFields;
    }

    public List<OutputCalculator> create() {
        List<OutputCalculator> res = new ArrayList<>();
        _outputFields.forEach(f -> res.add(f.createCalculator()));
        return res;
    }
}
