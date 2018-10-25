package com.jeff.fischman.spex;

import static org.junit.Assert.assertTrue;

import com.jeff.fischman.spex.bootstrap.Bootstrapper;
import com.jeff.fischman.spex.process.Processor;
import com.jeff.fischman.spex.process.output.Printer;
import com.jeff.fischman.spex.utility.CannedData;
import com.jeff.fischman.spex.utility.StringUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
public class EndToEndTest
{
    @Test
    public void testWithSampleData()
    {
        // This test runs the sample input described in the problem write-up,
        // collects the output in a special Printer that captures all the output given to it,
        // and the compares that collected output to the output listed in the writeup.

        Stream<String> sampleStream = CannedData.WriteupSampleInput.stream();
        Printer.CapturePrinter capturePrinter = new Printer.CapturePrinter();
        Bootstrapper bootstrapper = new Bootstrapper.Test(sampleStream, capturePrinter);
        Processor processor = bootstrapper.create();
        processor.run();

        List<String> expectedOutputList = Arrays.asList(
                "aaa,5787,40,1161,1222",
                "aab,6103,69,810,907",
                "aac,3081,41,559,638"
        );

        String expectedOutput = StringUtility.toMulitLineString(expectedOutputList);
        String capturedOutput = capturePrinter.getOutput();
        Assert.assertEquals(expectedOutput, capturedOutput);

        // print it for human observers
        System.out.print(capturedOutput);

    }
}
