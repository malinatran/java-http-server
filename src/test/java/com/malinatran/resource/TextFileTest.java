package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;
import static org.junit.Assert.assertEquals;

public class TextFileTest {

    private TextFile textFile;
    private Map<String, Integer> rangeBytes;
    private String DEFAULT_PATH = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private String result;

    @Before
    public void setUp() {
        textFile = new TextFile();
        rangeBytes = new HashMap<String, Integer>();
    }

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        result = textFile.readTextFile(DEFAULT_PATH + "text-file.txt");

        assertEquals("file1 contents", result);
    }

    @Test
    public void readPartialTextFileReturnsBeginningOfTextIfRangesAreSpecified() throws IOException {
        rangeBytes.put(START, 0);
        rangeBytes.put(END, 10);

        result = textFile.readPartialTextFile(DEFAULT_PATH + "text-file.txt", rangeBytes);

        assertEquals("file1 conte", result);
    }

    @Test
    public void readPartialTextFileReturnsSnippetOfTextIfRangesAreSpecified() throws IOException {
        rangeBytes.put(START, 6);
        rangeBytes.put(END, 10);

        result = textFile.readPartialTextFile(DEFAULT_PATH + "text-file.txt", rangeBytes);

        assertEquals("conte", result);
    }

    @Test
    public void readPartialTextFileReturnsEndOfTextIfOnlyRangeEndIsSpecified() throws IOException {
        rangeBytes.put(END, 10);

        result = textFile.readPartialTextFile(DEFAULT_PATH + "text-file.txt", rangeBytes);

        assertEquals(" contents\n", result);
    }

    @Test
    public void readPartialTextFileReturnsTextFromStartToEndIfOnlyRangeStartIsSpecified() throws IOException {
        rangeBytes.put(START, 6);

        result = textFile.readPartialTextFile(DEFAULT_PATH + "text-file.txt", rangeBytes);

        assertEquals("contents\n", result);
    }
}