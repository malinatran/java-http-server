package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TextFileTest {

    private Map<String, Integer> range;
    private String PATH = ServerSettings.ROOT + ServerSettings.DEFAULT_DIRECTORY;
    private String START = "Start";
    private String END = "End";
    private int start = 6;
    private int end = 10;
    private String result;

    @Before
    public void setUp() {
        range = new Hashtable<String, Integer>();
    }

    @Test
    public void readReturnsEntireFileContents() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 contents", result);
    }

    @Test
    public void readReturnsBeginningOfTextIfRangesAreSpecified() throws IOException {
        range.put(START, 0);
        range.put(END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readReturnsSnippetOfTextIfRangesAreSpecified() throws IOException {
        range.put(START, start);
        range.put(END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("conte", result);
    }

    @Test
    public void readReturnsEndOfTextIfOnlyRangeEndIsSpecified() throws IOException {
        range.put(END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals(" contents\n", result);
    }

    @Test
    public void readReturnsTextFromStartToEndIfOnlyRangeStartIsSpecified() throws IOException {
        range.put(START, start);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("contents\n", result);
    }

    @Test
    public void readReturnsFullTextIfNoRangeIsSpecified() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 contents", result);
    }
}