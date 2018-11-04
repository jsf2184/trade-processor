package com.jeff.fischman.spex.process;

import org.junit.Test;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ProcessorTests {
    @Test
    public void testProcessorProcessesEachLineAndThenPrintsReport() {
        Stream<String> stream = Stream.of("a", "b", "c");
        LineProcessor lineProcessor = mock(LineProcessor.class);
        InstrumentStore instrumentStore = mock(InstrumentStore.class);
        Processor sut = new Processor(stream, lineProcessor, instrumentStore);
        sut.run();
        verify(lineProcessor, times(1)).processLine("a", instrumentStore);
        verify(lineProcessor, times(1)).processLine("b", instrumentStore);
        verify(lineProcessor, times(1)).processLine("c", instrumentStore);
        verify(instrumentStore, times(1)).report();
        verifyNoMoreInteractions(lineProcessor);
        verifyNoMoreInteractions(instrumentStore);
    }

}
