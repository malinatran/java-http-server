package com.malinatran.router;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DirectoryReaderTest {

    @Test
    public void getListingWithExistingDirectoryReturnsListing() {
        DirectoryReader directoryReader = new DirectoryReader();
        File test = new File("test1/folder");
        Boolean isCreated = test.mkdirs();
        String listing = directoryReader.getListing("test1");
        assertEquals("folder", listing);
    }

    @Test
    public void getListingWithNoDirectoryReturnsEmptyString() {
        DirectoryReader directoryReader = new DirectoryReader();
        String listing = directoryReader.getListing("test2");
        assertEquals("", listing);
    }
}