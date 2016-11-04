package com.malinatran.utility;

import com.malinatran.utility.SHA1Encoder;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.*;

public class SHA1EncoderTest {

    @Test
    public void convertToSHA1ReturnsEncodedString() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String actual = SHA1Encoder.convert("default content");
        String expected = "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec";

        assertEquals(expected, actual);
    }
}