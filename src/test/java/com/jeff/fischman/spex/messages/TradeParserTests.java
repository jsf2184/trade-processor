package com.jeff.fischman.spex.messages;

import org.junit.Assert;
import org.junit.Test;

public class TradeParserTests {

    @Test
    public void testRemoveSpacesThrowsExceptionOnNull() {
        boolean caught = false;
        try {
            TradeParser.removeSpaces(null);
        } catch (Exception e) {
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    @Test
    public void testRemoveSpacesOnEmptyString() throws Exception {
        String res = TradeParser.removeSpaces("");
        Assert.assertEquals("", res);
    }

    @Test
    public void testRemoveSpacesWithSpaces() throws Exception {
        String res = TradeParser.removeSpaces("   a b c");
        Assert.assertEquals("abc", res);
    }

    @Test
    public void testRemoveSpacesWithNoSpaces() throws Exception {
        String res = TradeParser.removeSpaces("abc");
        Assert.assertEquals("abc", res);
    }

    @Test
    public void parseLongWithNullString() {
        validateParseLongException(null, true);
    }

    @Test
    public void parseLongWithBadString() {
        validateParseLongException("123X", true);
    }

    @Test
    public void parseLongWithNegativeString() {
        validateParseLongException("-1", true);
    }

    @Test
    public void parseLongWithZeroStringWhenZeroBad() {
        validateParseLongException("0", false);
    }

    @Test
    public void parseLongWithZeroStringWhenZeroGood() throws Exception {
        Assert.assertEquals(0L, TradeParser.parseLong("0", true));
    }

    @Test
    public void parseLongWithPosNumber() throws Exception {
        Assert.assertEquals(123L, TradeParser.parseLong("123", true));
        Assert.assertEquals(123L, TradeParser.parseLong("123", false));
    }

    @Test
    public void testParseTradeWithNullInput() {
        validateParseTradeException(null);
    }

    @Test
    public void testParseTradeWithTooFewFields() {
        validateParseTradeException("123,a,456");
    }

    @Test
    public void testParseTradeWithTooManyFields() {
        validateParseTradeException("12,a,34,56,78");
    }

    @Test
    public void testParseTradeWithBadTimeStamp() {
        validateParseTradeException("12x,a,34,56");
    }

    @Test
    public void testParseTradeWithEmptySymbol() {
        validateParseTradeException("12,,34,56");
    }

    @Test
    public void testParseTradeWithBadQty() {
        validateParseTradeException("12,a,-34,56");
    }

    @Test
    public void testParseTradeWithBadPrice() {
        validateParseTradeException("12,a,34,0");
    }

    @Test
    public void testGoodParseTrade() throws Exception {
        TradeParser sut = new TradeParser();
        Trade t = sut.parseTrade("123,aaa,456,789");
        Assert.assertEquals(123L, t.getTimestamp());
        Assert.assertEquals("aaa", t.getSymbol());
        Assert.assertEquals(456L, t.getQuantity());
        Assert.assertEquals(789L, t.getPrice());
    }

    @Test
    public void testGoodParseTradeWithExtraSpaces() throws Exception {
        TradeParser sut = new TradeParser();
        Trade t = sut.parseTrade("123  ,aaa, 456,       789");
        Assert.assertEquals(123L, t.getTimestamp());
        Assert.assertEquals("aaa", t.getSymbol());
        Assert.assertEquals(456L, t.getQuantity());
        Assert.assertEquals(789L, t.getPrice());
    }


    private void validateParseTradeException(String s) {
        TradeParser tradeParser = new TradeParser();
        boolean caught = false;
        try {
            tradeParser.parseTrade(s);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            caught = true;
        }
        Assert.assertTrue(caught);
    }

    private void validateParseLongException(String s, boolean zeroOk) {
        boolean caught = false;
        try {
            TradeParser.parseLong(s, zeroOk);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            caught = true;
        }
        Assert.assertTrue(caught);
    }

}
