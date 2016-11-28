package com.malinatran.resource;

import com.malinatran.utility.FileType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileTypeReaderTest {

    private String result;
    private FileType type;

    @Before
    public void setUp() {
    }

    @Test
    public void getFileTypeReturnsTextIfDoesNotHaveFileExtension() {
        type = FileTypeReader.getFileType("words.txt");

        assertEquals(FileType.TEXT, type);
    }

    @Test
    public void getFileTypeReturnsTextIfTxtFileExtension() {
        type = FileTypeReader.getFileType("words");

        assertEquals(FileType.TEXT, type);
    }

    @Test
    public void getFileTypeReturnsImage() {
        type = FileTypeReader.getFileType("meme.gif");

        assertEquals(FileType.IMAGE, type);
    }

    @Test
    public void getFileTypeReturnsUnsupported() {
        type = FileTypeReader.getFileType("unsupported.pdf");

        assertEquals(FileType.UNSUPPORTED, type);
    }

    @Test
    public void getFileExtensionReturnsImageFileExtension() {
        result = FileTypeReader.getFileExtension("test.png");

        assertEquals("png", result);
    }

    @Test
    public void getFileExtensionReturnsFileNameIfNoExtension() {
        result = FileTypeReader.getFileExtension("lala");

        assertEquals("lala", result);
    }
}