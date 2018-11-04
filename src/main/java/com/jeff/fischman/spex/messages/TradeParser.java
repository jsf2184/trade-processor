package com.jeff.fischman.spex.messages;

public class TradeParser {

    MutableTrade _mutableTrade;

    public TradeParser() {
        _mutableTrade = new MutableTrade();
    }

    public Trade parseTrade(String line) throws Exception
    {
        line = removeSpaces(line);
        String[] parts = line.split(",");
        if (parts.length != 4) {
            throw new Exception("Trade.parseTrade() received line with an invalid number of fields");
        }
        String symbol = parts[1];
        if (symbol.length() == 0) {
            throw new Exception("Trade.parseTrade() symbol field cannot be empty");
        }
        long timestamp = parseLong(parts[0], true);
        long quantity = parseLong(parts[2], false);
        long price = parseLong(parts[3], false);
        _mutableTrade.set(timestamp, symbol, quantity, price);
        return _mutableTrade;
    }

    public static String removeSpaces(String s) throws Exception{
        if (s == null) {
            throw new Exception("Trade.removeSpaces(): Error, null input");
        }
        String res = s.replaceAll("\\s", "");
        return res;
    }

    public static long parseLong(String s, boolean zeroOk) throws Exception {

        if (s == null) {
            throw new Exception("Trade.parseLong(): input string is null");
        }
        long qty;
        try {
            qty = Long.parseLong(s);
        } catch (Exception ignore) {
            throw new Exception("Trade.parseLong(): invalid integer string");
        }
        if (qty < 0) {
            throw new Exception("Trade.parseLong(): value cannot be negative");
        }
        if (qty == 0 && !zeroOk) {
            throw new Exception("Trade.parseLong(): value cannot be zero");
        }
        return qty;
    }

}
