package com.jeff.fischman.spex.messages;

import javax.lang.model.type.ErrorType;
import java.util.Objects;

public class Trade {
    protected   long _timestamp;
    protected  String _symbol;
    protected  long _quantity;
    protected  long _price;

    public Trade(long timestamp, String symbol, long quantity, long price) {
        _timestamp = timestamp;
        _symbol = symbol;
        _quantity = quantity;
        _price = price;
    }

    public Trade(Trade src) {
        this(src._timestamp, src._symbol, src._quantity, src._price);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")

    public long getTimestamp() {
        return _timestamp;
    }

    public String getSymbol() {
        return _symbol;
    }

    public long getQuantity() {
        return _quantity;
    }

    public long getPrice() {
        return _price;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "_timestamp=" + _timestamp +
                ", _symbol='" + _symbol + '\'' +
                ", _quantity=" + _quantity +
                ", _price=" + _price +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Trade trade = (Trade) o;
//        return _timestamp == trade._timestamp &&
//                _quantity == trade._quantity &&
//                _price == trade._price &&
//                Objects.equals(_symbol, trade._symbol);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trade)) return false;
        Trade trade = (Trade) o;
        return _timestamp == trade._timestamp &&
                _quantity == trade._quantity &&
                _price == trade._price &&
                Objects.equals(_symbol, trade._symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_timestamp, _symbol, _quantity, _price);
    }
}
