package com.jeff.fischman.spex.messages;

public class MutableTrade extends Trade {
    public MutableTrade() {
        super(0L, "", 0L, 0L);
    }

    public void set(long timestamp, String symbol, long quantity, long price) {
        _timestamp = timestamp;
        _symbol = symbol;
        _quantity = quantity;
        _price = price;
    }
}
