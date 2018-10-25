package com.jeff.fischman.spex.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class StringUtility {
    private static final DecimalFormat _priceFormat = new DecimalFormat("0.##");

    public static String toMulitLineString(Iterable<String> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }

}
