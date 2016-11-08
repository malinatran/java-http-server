package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DirectoryTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private Map<String, Integer> map;
    private boolean result;

    @Before
    public void setUp() {
        map = new HashMap<String, Integer>();
    }

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = Directory.getLinks(DEFAULT_DIRECTORY);

        assertTrue(link.contains("<a style=\"display: block\" href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        result = Directory.existsInDirectory(DEFAULT_DIRECTORY + "file1");

        assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        result = Directory.existsInDirectory(DEFAULT_DIRECTORY + "image.pdf");

        assertFalse(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfNotDirectory() {
        result = Directory.existsInDirectory(DEFAULT_DIRECTORY);

        assertFalse(result);
    }
}
