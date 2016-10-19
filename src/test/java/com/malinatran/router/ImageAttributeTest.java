package com.malinatran.router;

import org.junit.Test;
import static org.junit.Assert.*;

public class ImageAttributeTest {

    Image imageAttribute = new Image();

    @Test
    public void getImageTypeReturnsImageFileExtension() {
        String result = imageAttribute.getImageType("test.png");

        assertEquals("png", result);
    }

    @Test
    public void getImageTypeReturnsFileNameIfNoExtension() {
        String result = imageAttribute.getImageType("lala");

        assertEquals("lala", result);
    }
}