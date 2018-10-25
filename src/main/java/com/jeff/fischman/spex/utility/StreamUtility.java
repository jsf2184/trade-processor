package com.jeff.fischman.spex.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamUtility {
    public Stream<String> getFileStream(String fileName) {
        try {
            return  Files.lines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.printf("Unable to create stream from file: %s\n", fileName);
            return null;
        }
    }

    public Stream<String> getCannedSampleInputStream() {
        Stream<String> res = CannedData.WriteupSampleInput.stream();
        return res;
    }

}
