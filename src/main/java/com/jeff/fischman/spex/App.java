package com.jeff.fischman.spex;

import com.jeff.fischman.spex.args.ArgParser;
import com.jeff.fischman.spex.bootstrap.Bootstrapper;
import com.jeff.fischman.spex.process.Processor;
import com.jeff.fischman.spex.process.output.Printer;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ArgParser argParser = new ArgParser(args);
        if (!argParser.parse()) {
            return;
        }

        Stream<String> inputStream = argParser.getInputStream();
        try {
            Printer printer = new Printer.FilePrinter(argParser.getOutputFileName());
            Bootstrapper bootstrapper = new Bootstrapper(inputStream, printer);
            Processor processor = bootstrapper.create();
            processor.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
//        inputStream.forEach(s -> {
//            Trade trade = Trade.parseTrade(s);
//            System.out.println(trade);
//        });
    }
}
