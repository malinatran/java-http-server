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
}
