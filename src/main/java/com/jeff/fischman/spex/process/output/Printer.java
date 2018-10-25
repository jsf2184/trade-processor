package com.jeff.fischman.spex.process.output;

import com.jeff.fischman.spex.messages.InstrumentSummary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public interface Printer {
    void print(InstrumentSummary instrumentSummary);
    void close();

    class FilePrinter implements  Printer {
        private final PrintWriter _printWriter;
        public FilePrinter(String fileName) throws IOException {
            FileWriter fileWriter = new FileWriter(fileName, false);
            _printWriter = new PrintWriter(fileWriter);
        }

        @Override
        public void print(InstrumentSummary instrumentSummary) {
            _printWriter.print(instrumentSummary.toCsvString());
        }

        @Override
        public void close() {
            _printWriter.close();
        }
    }

    class CapturePrinter implements  Printer {
        StringBuilder _sb;

        public CapturePrinter() {
            _sb = new StringBuilder();
        }

        @Override
        public void print(InstrumentSummary instrumentSummary) {
            _sb.append(instrumentSummary.toCsvString());
        }

        public String getOutput() {
            return _sb.toString();
        }

        @Override
        public void close() {

        }
    }
}
