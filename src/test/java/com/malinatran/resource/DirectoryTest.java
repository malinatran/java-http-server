package com.malinatran.resource;

import com.malinatran.constant.FileType;
import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DirectoryTest {

    private Directory directory;
    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private Map<String, Integer> map;
    private boolean result;
    private FileType type;

    @Before
    public void setUp() {
        directory = new Directory();
        map = new HashMap<String, Integer>();
    }

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = directory.getLinks(DEFAULT_DIRECTORY);

        assertTrue(link.contains("<a style=\"display: block\" href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        result = directory.existsInDirectory(DEFAULT_DIRECTORY + "file1");

        assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        result = directory.existsInDirectory(DEFAULT_DIRECTORY + "image.pdf");

        assertFalse(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfNotDirectory() {
        result = directory.existsInDirectory(DEFAULT_DIRECTORY);

        assertFalse(result);
    }

    @Test
    public void getTextReturnsTextFileContent() throws IOException {
        String result = directory.getContent(DEFAULT_DIRECTORY + "text-file.txt");

        assertEquals("file1 contents", result);
    }

    @Test
    public void getBytesReturnsBytesFromImageFile() throws IOException {
        byte[] result = directory.getBytes(DEFAULT_DIRECTORY + "image.gif");

        assertEquals(7169, result.length);
    }
}
