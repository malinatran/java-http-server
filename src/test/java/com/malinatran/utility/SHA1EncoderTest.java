package com.malinatran.utility;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SHA1EncoderTest {

    @Test
    public void convertToSHA1ReturnsEncodedString() {
        String actual = SHA1Encoder.encode("default content");
        String expected = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";

        assertEquals(expected, actual);
    }
}
