package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TextFileTest {

    private Map<String, Integer> range;
    private String PATH = ServerSettings.ROOT + ServerSettings.DEFAULT_DIRECTORY;
    private String START = "Start";
    private String END = "End";
    private String result;

    @Before
    public void setUp() {
        range = new HashMap<String, Integer>();
    }

    @Test
    public void readFullTextFileReturnsEntireFileContents() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 contents", result);
    }

    @Test
    public void readPartialTextFileReturnsBeginningOfTextIfRangesAreSpecified() throws IOException {
        range.put(START, 0);
        range.put(END, 10);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readPartialTextFileReturnsSnippetOfTextIfRangesAreSpecified() throws IOException {
        range.put(START, 6);
        range.put(END, 10);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("conte", result);
    }

    @Test
    public void readPartialTextFileReturnsEndOfTextIfOnlyRangeEndIsSpecified() throws IOException {
        range.put(END, 10);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals(" contents\n", result);
    }

    @Test
    public void readPartialTextFileReturnsTextFromStartToEndIfOnlyRangeStartIsSpecified() throws IOException {
        range.put(START, 6);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("contents\n", result);
    }

    @Test
    public void readPartialTextReturnsFullTextIfNoRangeIsSpecified() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);


        assertEquals("file1 contents", result);
    }
}