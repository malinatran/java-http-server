package com.malinatran.router;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryReaderTest {

    DirectoryReader directoryReader = new DirectoryReader();
    String DEFAULT_PATH = System.getProperty("user.home") + "/Development/cob_spec/public/";
    Map<String, Integer> rangeBytes = new HashMap<String, Integer>();

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = directoryReader.getLinks(DEFAULT_PATH);

        assertTrue(link.contains("<a style=\"display: block\" href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void getBytesOfTextFileReturnsCharacterCount() throws IOException {
        int count = directoryReader.getCharCountOfTextFile(DEFAULT_PATH, "text-file.txt");

        assertEquals(14, count);
    }

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        String content = directoryReader.readTextFile(DEFAULT_PATH, "text-file.txt");

        assertEquals("file1 contents", content);
    }

    @Test
    public void calculatePartialContentSizeTakesStartAndEndRanges() {
        rangeBytes.put("Start", 1);
        rangeBytes.put("End", 5);

        int[] result = directoryReader.calculatePartialContentSize(rangeBytes, 10);

        assertEquals(1, result[0]);
        assertEquals(5, result[1]);
    }

    @Test
    public void calculatePartialContentSizeTakesStartRange() {
        rangeBytes.put("Start", 1);

        int[] result = directoryReader.calculatePartialContentSize(rangeBytes, 10);

        assertEquals(1, result[0]);
        assertEquals(10, result[1]);
    }

    @Test
    public void calculatePartialContentSizeTakesEndRange() {
        rangeBytes.put("End", 5);

        int[] result = directoryReader.calculatePartialContentSize(rangeBytes, 10);

        assertEquals(6, result[0]);
        assertEquals(10, result[1]);
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        Boolean result = directoryReader.existsInDirectory(DEFAULT_PATH, "file1");

        assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        Boolean result = directoryReader.existsInDirectory(DEFAULT_PATH, "image.pdf");

        assertFalse(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfNotDirectory() {
        Boolean result = directoryReader.existsInDirectory(DEFAULT_PATH + "text-file.txt", "file1");

        assertFalse(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithNoExtension() {
        Boolean result = directoryReader.isTextFile("testing");

        assertTrue(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithTxtExtension() {
        Boolean result = directoryReader.isTextFile("lala.txt");

        assertTrue(result);
    }

    @Test
    public void isTextFileReturnsFalseIfNotTextFile() {
        Boolean result = directoryReader.isTextFile("lala.pdf");

        assertFalse(result);
    }

    @Test
    public void isImageFileReturnsTrueIfFileWithValidExtension() {
        Boolean result = directoryReader.isImageFile("la.gif");

        assertTrue(result);
    }

    @Test
    public void isImageFileReturnsFalseIfFileWithInvalidExtension() {
        Boolean result = directoryReader.isImageFile("testing.txt");

        assertFalse(result);
    }
}