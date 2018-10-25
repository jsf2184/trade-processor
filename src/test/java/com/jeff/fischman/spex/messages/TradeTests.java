package com.jeff.fischman.spex.messages;

import org.junit.Assert;
import org.junit.Test;

public class TradeTests {

    @Test
    public void testGetters() {
        Trade t = new Trade(123L, "aaa", 456L, 789L);
        Assert.assertEquals(123L, t.getTimestamp());
        Assert.assertEquals("aaa", t.getSymbol());
        Assert.assertEquals(456L, t.getQuantity());
        Assert.assertEquals(789L, t.getPrice());
    }
}
