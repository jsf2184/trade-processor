package com.jeff.fischman.spex.process;

import com.jeff.fischman.spex.messages.Trade;
import com.jeff.fischman.spex.messages.TradeParser;

public class LineProcessor {
    private final TradeParser _tradeParser;
    private long _lno;
    private long _priorTimestamp;

    public LineProcessor(TradeParser tradeParser) {
        _tradeParser = tradeParser;
        _lno = 0L;
        _priorTimestamp = -1;
    }

    public void processLine(String line, InstrumentStore instrumentStore) {
        Trade trade;
        _lno++;
        try {
            trade = _tradeParser.parseTrade(line);
        } catch (Exception e) {
            String message = e.getMessage();
            System.err.printf("Processor.processLine: parse error on line: %d, %s \n", _lno, message);
            return;
        }

        long timestamp = trade.getTimestamp();
        if (timestamp < _priorTimestamp) {
            System.err.printf("Processor.processLine: timestamp error on line: %d, timestamp(%d) < priorTimestamp(%d)\n",
                              _lno, timestamp, _priorTimestamp);
            return;
        }
        _priorTimestamp = timestamp;
        Instrument instrument = instrumentStore.findOrAddInstrument(trade.getSymbol());
        instrument.onTrade(trade);
    }

}
