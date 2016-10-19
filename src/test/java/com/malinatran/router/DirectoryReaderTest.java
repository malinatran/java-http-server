package com.malinatran.router;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryReaderTest {

    DirectoryReader directoryReader = new DirectoryReader();

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        String link = directoryReader.getLinks();

        Assert.assertTrue(link.contains("<a href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        String content = directoryReader.readTextFile("text-file.txt");

        assertEquals("file1 contents", content);
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        Boolean result = directoryReader.existsInDirectory("file1");

        Assert.assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        Boolean result = directoryReader.existsInDirectory("image.pdf");

        Assert.assertFalse(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithNoExtension() {
        Boolean result = directoryReader.isTextFile("testing");

        Assert.assertTrue(result);
    }

    @Test
    public void isTextFileReturnsTrueIfFileWithTxtExtension() {
        Boolean result = directoryReader.isTextFile("lala.txt");

        Assert.assertTrue(result);
    }

    @Test
    public void isTextFileReturnsFalseIfNotTextFile() {
        Boolean result = directoryReader.isTextFile("lala.pdf");

        Assert.assertFalse(result);
    }

    @Test
    public void isImageFileReturnsTrueIfFileWithValidExtension() {
        Boolean result = directoryReader.isImageFile("la.gif");

        Assert.assertTrue(result);
    }

    @Test
    public void isImageFileReturnsFalseIfFileWithInvalidExtension() {
        Boolean result = directoryReader.isImageFile("testing.txt");

        Assert.assertFalse(result);
    }
}