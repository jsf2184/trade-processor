package com.jeff.fischman.spex.args;

import com.jeff.fischman.spex.utility.StreamUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class ArgParserTests {

    private static Stream<String> DummyStream = Stream.of("abc");

    @Test
    public void testWithTooManyArgs() {
        verifyBadParse(new String[] {"-canned", "inputFile.csv"});
    }

    @Test
    public void testWithHelpFlag() {
        verifyBadParse(new String[] {"-h"});
    }

    @Test
    public void testWithNoArgs() {
        StreamUtility streamUtility = mock(StreamUtility.class);
        when(streamUtility.getFileStream(ArgParser.DefaultInputFile)).thenReturn(DummyStream);
        ArgParser sut = new ArgParser(new String[0], streamUtility);
        Assert.assertTrue(sut.parse());
        verify(streamUtility, times(1)).getFileStream(ArgParser.DefaultInputFile);
        Assert.assertSame(DummyStream, sut.getInputStream());
    }

    @Test
    public void testWithGoodAlternativeInputFile() {
        StreamUtility streamUtility = mock(StreamUtility.class);
        when(streamUtility.getFileStream("sampleInput.csv")).thenReturn(DummyStream);
        ArgParser sut = new ArgParser(new String[] {"sampleInput.csv"}, streamUtility);
        Assert.assertTrue(sut.parse());
        verify(streamUtility, times(1)).getFileStream("sampleInput.csv");
        Assert.assertSame(DummyStream, sut.getInputStream());
    }

    @Test
    public void testWithNonExistentInputFile() {
        StreamUtility streamUtility = mock(StreamUtility.class);
        when(streamUtility.getFileStream("nonExistent.csv")).thenReturn(null);
        ArgParser sut = new ArgParser(new String[] {"nonExistent.csv"}, streamUtility);
        Assert.assertFalse(sut.parse());
        verify(streamUtility, times(1)).getFileStream("nonExistent.csv");
        Assert.assertNull(sut.getInputStream());
    }

    @Test
    public void testWithCannedInput() {
        StreamUtility streamUtility = mock(StreamUtility.class);
        when(streamUtility.getCannedSampleInputStream()).thenReturn(DummyStream);
        ArgParser sut = new ArgParser(new String[] {"-canned"}, streamUtility);
        Assert.assertTrue(sut.parse());
        verify(streamUtility, times(1)).getCannedSampleInputStream();
        Assert.assertSame(DummyStream, sut.getInputStream());
    }


    private void verifyBadParse(String[] args) {
        ArgParser sut = new ArgParser(args);
        Assert.assertFalse(sut.parse());
    }
}
