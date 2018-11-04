package com.jeff.fischman.spex.args;

import com.jeff.fischman.spex.OutputField;
import com.jeff.fischman.spex.utility.StreamUtility;

import java.util.*;
import java.util.stream.Stream;

public class ArgParser {

    static  final String DefaultInputFile = "input.csv";
    private static  final String DefaultOutputFile = "output.csv";
    private Stream<String> _inputStream;
    private String[] _args;
    private StreamUtility _streamUtility;
    private String _outputFileName;
    private static final String DefaultOutputFields = OutputField.getAllAbbrevs();
    private List<OutputField> _outputFields;

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
        String inputFile = null;
        int numArgs = _args.length;
        boolean canned = false;
        String outputFieldAbbrevs = DefaultOutputFields;

        for (int i=0; i< numArgs; i++) {
            String arg = _args[i];
            switch (arg) {
                case "-h":
                    printUsage();
                    return false;
                case "-canned":
                    canned = true;
                    break;
                case "-output":
                    if (i+1 >= numArgs) {
                        System.err.println("Missing -output argument");
                        printUsage();
                        return false;
                    }
                    outputFieldAbbrevs = _args[++i];
                    break;

                default:
                    if (inputFile != null) {
                        System.err.println("Extra command line arguments.");
                        printUsage();
                        return false;
                    }
                    inputFile = arg;
                    break;
            }
        }

        if (canned && inputFile != null) {
            System.err.println("Cannot specify both an inputFile and the -canned option.");
            printUsage();
            return false;
        }

        try {
            _outputFields = parseOutputFields(outputFieldAbbrevs);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            printUsage();
            return false;
        }

        if (canned) {
            System.err.println("Will be using canned input data");
            _inputStream = _streamUtility.getCannedSampleInputStream();
        } else {
            if (inputFile == null) {
                inputFile = DefaultInputFile;
            }
            System.out.printf("Will be using input from file: '%s'\n", inputFile);
            _inputStream = _streamUtility.getFileStream(inputFile);
        }
        if (_inputStream == null) {
            printUsage();
            return false;
        }
        return true;
    }

    List<OutputField> parseOutputFields(String outputFieldAbbrevs) throws Exception {
        List<OutputField> res = new ArrayList<>();
        for (int i=0; i<outputFieldAbbrevs.length(); i++) {
            char o = outputFieldAbbrevs.charAt(i);
            OutputField outputField = OutputField.abbrevToEnum(o);
            if (outputField == null) {
                throw new Exception(String.format("char '%c' is an invalid output field abbreviation", o));
            }
            res.add(outputField);
        }
        Collection<OutputField> set = new HashSet<>(res);
        if (set.size() != res.size()) {
            throw new Exception(String.format("duplicate output format fields in '%s' are prohibited", outputFieldAbbrevs));
        }

        return res;
    }
    public Stream<String> getInputStream() {
        return _inputStream;
    }

    public String getOutputFileName() {
        // for now, we don't give the user to control the outputFileName, maybe later.
        return _outputFileName;
    }

    public List<OutputField> getOutputFields() {
        return _outputFields;
    }

    private void printUsage() {
        System.err.println("\nThe program accepts either a fileName or the '-canned' flag (NOT BOTH)");
        System.err.println("Usage: |-canned| |-output outputAbbrevChars| |fileName|");
        System.err.println("  -h:                           for help");
        System.err.println("  -canned:                      to run the program off of canned data rather than file input");
        System.err.println("  -output outputAbbrevChars:    to customize output, See legal chars below");
        System.err.println("  fileName:                     to explicitly name an input file\n");
        System.err.println("Here are the legal characters that can appear in 'outputAbbrevChars'");
        System.err.println(OutputField.getAbbrevHelp());
    }

}
