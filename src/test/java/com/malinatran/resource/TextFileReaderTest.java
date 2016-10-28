package com.malinatran.resource;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TextFileReaderTest {

    TextFileReader fileReader = new TextFileReader();
    Map<String, Integer> rangeBytes = new HashMap<String, Integer>();
    String DEFAULT_PATH = System.getProperty("user.home") + "/Development/cob_spec/public/";

    @Test
    public void readPartialTextFileReturnsBeginningOfText() throws IOException {
        rangeBytes.put("Start", 0);
        rangeBytes.put("End", 10);
        String result = fileReader.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readPartialTextFileReturnsEndOfText() throws IOException {
        rangeBytes.put("Start", 5);
        rangeBytes.put("End", 14);
        String result = fileReader.readPartialTextFile(DEFAULT_PATH, "text-file.txt", rangeBytes);

        assertEquals(" contents\n", result);
    }

    @Test
    public void getCharacterCountReturnsIntegerRepresentingTotalNumberOfCharacters() throws IOException {
        int count = fileReader.getCharacterCount(DEFAULT_PATH, "text-file.txt");

        assertEquals(14, count);
    }

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        String content = fileReader.readTextFile(DEFAULT_PATH, "text-file.txt");

        assertEquals("file1 contents", content);
    }

    @Test
    public void setPartialContentRangeTakesStartAndEndRanges() {
        rangeBytes.put("Start", 1);
        rangeBytes.put("End", 5);

        int[] result = fileReader.setPartialContentRange(rangeBytes, 10);

        assertEquals(1, result[0]);
        assertEquals(5, result[1]);
    }

    @Test
    public void setPartialContentRangeTakesStartRange() {
        rangeBytes.put("Start", 1);

        int[] result = fileReader.setPartialContentRange(rangeBytes, 10);

        assertEquals(1, result[0]);
        assertEquals(10, result[1]);
    }

    @Test
    public void setPartialContentRangeTakesEndRange() {
        rangeBytes.put("End", 5);

        int[] result = fileReader.setPartialContentRange(rangeBytes, 10);

        assertEquals(6, result[0]);
        assertEquals(10, result[1]);
    }
}