package com.malinatran.router;

import java.io.File;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DirectoryReaderTest {

    @Test
    public void getLinksWithExistingDirectoryReturnsLinks() {
        DirectoryReader directoryReader = new DirectoryReader();

        String link = directoryReader.getLinks("public");

        Assert.assertTrue(link.contains("<a href=\"/image.png\">image.png</a>"));
    }

    @Test
    public void getLinksWithEmptyDirectoryReturnsEmptyString() {
        DirectoryReader directoryReader = new DirectoryReader();
        new File("test3");

        String listing = directoryReader.getLinks("test3");

        assertEquals("", listing);
    }
}