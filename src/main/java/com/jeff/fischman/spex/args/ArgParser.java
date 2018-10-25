package com.jeff.fischman.spex.args;

import com.jeff.fischman.spex.utility.StreamUtility;

import java.util.stream.Stream;

public class ArgParser {

    static  final String DefaultInputFile = "input.csv";
    private static  final String DefaultOutputFile = "output.csv";
    private Stream<String> _inputStream;
    private String[] _args;
    private StreamUtility _streamUtility;
    private String _outputFileName;

    public ArgParser(String[] args) {
        this(args, new StreamUtility());
    }

    public ArgParser(String[] args, StreamUtility streamUtility) {
        _args = args;
        _streamUtility = streamUtility;
        _inputStream = null;
        _outputFileName = DefaultOutputFile;
    }

    public boolean parse() {
        int numArgs = _args.length;
        if (numArgs > 1) {
            System.err.println("Too many input Args");
            printUsage();
            return false;
        }

        String inputFile = DefaultInputFile;
        if (numArgs == 1) {
            String arg = _args[0];
            switch (arg) {
                case "-h":
                    printUsage();
                    return false;
                case "-canned":
                    System.out.println("Will be using canned input data");
                    _inputStream = _streamUtility.getCannedSampleInputStream();
                    return true;
                default:
                    inputFile = arg;
                    break;
            }
        }

        // Get a stream from whichever inputFile we are using.
        System.out.printf("Will be using input from file: '%s'\n", inputFile);
        _inputStream = _streamUtility.getFileStream(inputFile);
        return _inputStream != null;
    }

    public Stream<String> getInputStream() {
        return _inputStream;
    }

    public String getOutputFileName() {
        // for now, we don't give the user to control the outputFileName, maybe later.
        return _outputFileName;
    }

    private void printUsage() {
        System.err.println("The program accepts either a fileName or the '-canned' flag");
        System.err.println("Usage: |-canned|fileName|");
        System.err.println("  -h:         for help");
        System.err.println("  -canned:    to run the program off of canned data rather than file input");
        System.err.println("  fileName:   to explicitly name an input file");
    }

}
