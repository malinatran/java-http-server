package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DirectoryReaderTest {

    private DirectoryReader directoryReader;
    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private Boolean result;

    @Before
    public void setUp() {
       directoryReader = new DirectoryReader();
    }

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = directoryReader.getLinks(DEFAULT_DIRECTORY);

        assertTrue(link.contains("<a style=\"display: block\" href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        result = directoryReader.existsInDirectory(DEFAULT_DIRECTORY, "file1");

        assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        result = directoryReader.existsInDirectory(DEFAULT_DIRECTORY, "image.pdf");

        assertFalse(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfNotDirectory() {
        result = directoryReader.existsInDirectory(DEFAULT_DIRECTORY + "text-file.txt", "file1");

        assertFalse(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithNoExtension() {
        result = directoryReader.isTextFile("testing");

        assertTrue(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithTxtExtension() {
        result = directoryReader.isTextFile("lala.txt");

        assertTrue(result);
    }

    @Test
    public void isTextFileReturnsFalseIfNotTextFile() {
        result = directoryReader.isTextFile("lala.pdf");

        assertFalse(result);
    }

    @Test
    public void isImageFileReturnsTrueIfFileWithValidExtension() {
        result = directoryReader.isImageFile("la.gif");

        assertTrue(result);
    }

    @Test
    public void isImageFileReturnsFalseIfFileWithInvalidExtension() {
        result = directoryReader.isImageFile("testing.txt");

        assertFalse(result);
    }
}