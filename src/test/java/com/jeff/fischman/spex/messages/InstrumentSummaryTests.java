package com.jeff.fischman.spex.messages;

import com.jeff.fischman.spex.process.Instrument;
import org.junit.Assert;
import org.junit.Test;

public class InstrumentSummaryTests {
    @Test
    public void testToCsvStringAndGetters() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut = new InstrumentSummary("aaa", 5787L, 40L, 1161L, 1222L);
        Assert.assertEquals("aaa", sut.getSymbol());
        Assert.assertEquals(5787L, sut.getMaxTimeGap());
        Assert.assertEquals(40L, sut.getTotalVolume());
        Assert.assertEquals(1161L, sut.getWeightedAvgPrice());
        Assert.assertEquals(1222L, sut.getMaxPrice());
        Assert.assertEquals("aaa,5787,40,1161,1222\n", sut.toCsvString());
    }

    @Test
    public void testEquals() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut1 = new InstrumentSummary("aaa", 5787L, 40L, 1161L, 1222L);
        InstrumentSummary sut2 = new InstrumentSummary("aaa", 5787L, 40L, 1161L, 1222L);
        Assert.assertEquals(sut1, sut2);
    }

    @Test
    public void testNotEquals() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut1 = new InstrumentSummary("aaa", 5787L, 40L, 1161L, 1222L);

        // Change each attribute to make sure not equals if any are different.

        // different sym
        InstrumentSummary sut2 = new InstrumentSummary("aax", 5787L, 40L, 1161L, 1222L);
        Assert.assertNotEquals(sut1, sut2);

        // different timegap
        InstrumentSummary sut3 = new InstrumentSummary("aaa", 5786L, 40L, 1161L, 1222L);
        Assert.assertNotEquals(sut1, sut3);

        // different qty
        InstrumentSummary sut4 = new InstrumentSummary("aaa", 5787L, 41L, 1161L, 1222L);
        Assert.assertNotEquals(sut1, sut4);

        // different weighted avg
        InstrumentSummary sut5 = new InstrumentSummary("aaa", 5787L, 40L, 1162L, 1222L);
        Assert.assertNotEquals(sut1, sut5);

        // different max price
        InstrumentSummary sut6 = new InstrumentSummary("aaa", 5787L, 40L, 1161L, 1223L);
        Assert.assertNotEquals(sut1, sut6);

    }


}
