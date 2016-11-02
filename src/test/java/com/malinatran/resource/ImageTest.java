package com.malinatran.resource;

import com.malinatran.setup.ServerSettings;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ImageTest {

    private String DEFAULT_DIRECTORY = ServerSettings.HOME + ServerSettings.DEFAULT_PATH;
    private Image image;
    private String result;

    @Before
    public void setUp() {
        image = new Image();
    }

    @Test
    public void extractBytesReturnsByteArray() {
        byte[] result = image.extractBytes(DEFAULT_DIRECTORY + "/image.gif");

        assertEquals(7169, result.length);
    }

    @Test
    public void getImageTypeReturnsImageFileExtension() {
        result = image.getImageType("test.png");

        assertEquals("png", result);
    }

    @Test
    public void getImageTypeReturnsFileNameIfNoExtension() {
        result = image.getImageType("lala");

        assertEquals("lala", result);
    }
}