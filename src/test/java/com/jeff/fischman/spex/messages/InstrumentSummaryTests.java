package com.jeff.fischman.spex.messages;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class InstrumentSummaryTests {
    @Test
    public void testToCsvStringAndGetters() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut = new InstrumentSummary("aaa", Arrays.asList(5787L, 40L, 1161L, 1222L));
        Assert.assertEquals("aaa", sut.getSymbol());
        Assert.assertEquals("aaa,5787,40,1161,1222\n", sut.toCsvString());
    }

    @Test
    public void testEquals() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut1 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 40L, 1161L, 1222L));
        InstrumentSummary sut2 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 40L, 1161L, 1222L));
        Assert.assertEquals(sut1, sut2);
    }

    @Test
    public void testNotEquals() {
//        aaa,5787.40,1161,1222
        InstrumentSummary sut1 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 40L, 1161L, 1222L));

        // Change each attribute to make sure not equals if any are different.

        // different sym
        InstrumentSummary sut2 = new InstrumentSummary("aax",  Arrays.asList(5787L, 40L, 1161L, 1222L));
        Assert.assertNotEquals(sut1, sut2);

        // different timegap
        InstrumentSummary sut3 = new InstrumentSummary("aaa",  Arrays.asList(5786L, 40L, 1161L, 1222L));
        Assert.assertNotEquals(sut1, sut3);

        // different qty
        InstrumentSummary sut4 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 41L, 1161L, 1222L));
        Assert.assertNotEquals(sut1, sut4);

        // different weighted avg
        InstrumentSummary sut5 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 40L, 1162L, 1222L));
        Assert.assertNotEquals(sut1, sut5);

        // different max price
        InstrumentSummary sut6 = new InstrumentSummary("aaa",  Arrays.asList(5787L, 40L, 1161L, 1223L));
        Assert.assertNotEquals(sut1, sut6);

    }


}
