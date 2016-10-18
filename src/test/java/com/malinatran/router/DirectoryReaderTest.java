package com.malinatran.router;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryReaderTest {

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        DirectoryReader directoryReader = new DirectoryReader();

        String link = directoryReader.getLinks();

        Assert.assertTrue(link.contains("<a href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void readTextFileReturnsEntireFileContents() throws IOException {
        DirectoryReader directoryReader = new DirectoryReader();

        String content = directoryReader.readTextFile("text-file.txt");

        assertEquals("file1 contents", content);
    }

    @Test
    public void existsInDirectoryReturnsTrueIfItExistsInDirectory() {
        DirectoryReader directoryReader = new DirectoryReader();

        Boolean result = directoryReader.existsInDirectory("file1");

        Assert.assertTrue(result);
    }

    @Test
    public void existsInDirectoryReturnsFalseIfItDoesNotExistInDirectory() {
        DirectoryReader directoryReader = new DirectoryReader();

        Boolean result = directoryReader.existsInDirectory("image.pdf");

        Assert.assertFalse(result);
    }

    @Test
    public void isFileFormatSupportedReturnsTrueIfTextFile() {
        DirectoryReader directoryReader = new DirectoryReader();

        Boolean result = directoryReader.isFileFormatSupported("lala.txt");

        Assert.assertTrue(result);
    }

    @Test
    public void isFileFormatSupportedReturnsFalseIfNotTextFile() {
        DirectoryReader directoryReader = new DirectoryReader();

        Boolean result = directoryReader.isFileFormatSupported("lala.pdf");

        Assert.assertFalse(result);
    }
}