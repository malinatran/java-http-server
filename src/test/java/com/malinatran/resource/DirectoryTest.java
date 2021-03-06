package com.malinatran.resource;

import com.malinatran.setup.DirectoryArg;

import org.junit.Before;
import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class DirectoryTest {

    private String PATH = DirectoryArg.HOME_DIRECTORY + DirectoryArg.DEFAULT_DIRECTORY;
    private Map<String, Integer> map;
    private boolean result;

    @Before
    public void setUp() {
        map = new Hashtable<String, Integer>();
    }

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = Directory.getLinks("", PATH);

        assertTrue(link.contains("<a style=\"display: block\" href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        result = Directory.existsInDirectory(PATH + "file1");

        assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        result = Directory.existsInDirectory(PATH + "image.pdf");

        assertFalse(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfNotDirectory() {
        result = Directory.existsInDirectory(PATH);

        assertFalse(result);
    }
}
