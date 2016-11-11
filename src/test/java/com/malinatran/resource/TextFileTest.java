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
    private String PATH = ServerSettings.HOME_DIRECTORY + ServerSettings.DEFAULT_DIRECTORY;
    private int start = 6;
    private int end = 10;
    private String result;

    @Before
    public void setUp() {
        range = new Hashtable<String, Integer>();
    }

    @Test
    public void getCharacterCountReturnsCharacterInFile() throws IOException {
        String filePath = PATH + "text-file.txt";

        int count = TextFile.getCharacterCount(filePath);

        assertEquals(14, count);
    }

    @Test
    public void readReturnsEntireFileContents() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 contents", result);
    }

    @Test
    public void readReturnsBeginningOfTextIfRangesAreSpecified() throws IOException {
        range.put(ContentRangeHelper.START, 0);
        range.put(ContentRangeHelper.END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readReturnsSnippetOfTextIfRangesAreSpecified() throws IOException {
        range.put(ContentRangeHelper.START, start);
        range.put(ContentRangeHelper.END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("conte", result);
    }

    @Test
    public void readReturnsEndOfTextIfOnlyRangeEndIsSpecified() throws IOException {
        range.put(ContentRangeHelper.END, end);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals(" contents\n", result);
    }

    @Test
    public void readReturnsTextFromStartToEndIfOnlyRangeStartIsSpecified() throws IOException {
        range.put(ContentRangeHelper.START, start);

        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("contents\n", result);
    }

    @Test
    public void readReturnsFullTextIfNoRangeIsSpecified() throws IOException {
        result = TextFile.read(PATH + "text-file.txt", range);

        assertEquals("file1 contents", result);
    }
}
