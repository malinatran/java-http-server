package com.malinatran.resource;

import com.malinatran.resource.Image;
import org.junit.Test;
import static org.junit.Assert.*;

public class ImageTest {

    Image image = new Image();

    @Test
    public void getImageTypeReturnsImageFileExtension() {
        String result = image.getImageType("test.png");

        assertEquals("png", result);
    }

    @Test
    public void getImageTypeReturnsFileNameIfNoExtension() {
        String result = image.getImageType("lala");

        assertEquals("lala", result);
    }
}