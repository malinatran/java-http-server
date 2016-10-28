package com.malinatran.resource;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TextFileTest {

    String START = "Start";
    String END = "End";

    TextFile textFile = new TextFile();
    Map<String, Integer> rangeBytes = new HashMap<String, Integer>();
    String DEFAULT_PATH = System.getProperty("user.home") + "/Development/cob_spec/public/";

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        String content = textFile.readTextFile(DEFAULT_PATH, "text-file.txt");

        assertEquals("file1 contents", content);
    }

    @Test
    public void readPartialTextFileReturnsBeginningOfTextIfRangesAreSpecified() throws IOException {
        rangeBytes.put(START, 0);
        rangeBytes.put(END, 10);

        String result = textFile.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readPartialTextFileReturnsSnippetOfTextIfRangesAreSpecified() throws IOException {
        rangeBytes.put(START, 6);
        rangeBytes.put(END, 10);

        String result = textFile.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals("conte", result);
    }

    @Test
    public void readPartialTextFileReturnsEndOfTextIfOnlyRangeEndIsSpecified() throws IOException {
        rangeBytes.put(END, 10);

        String result = textFile.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals(" contents\n", result);
    }

    @Test
    public void readPartialTextFileReturnsTextFromStartToEndIfOnlyRangeStartIsSpecified() throws IOException {
        rangeBytes.put(START, 6);

        String result = textFile.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals("contents\n", result);
    }
}