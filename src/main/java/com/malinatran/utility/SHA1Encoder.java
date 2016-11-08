package com.malinatran.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Encoder {

    private static final String SHA_1 = "SHA-1";
    private static final String UTF_8 = "UTF-8";

    public static String convert(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest;
        byte[] input = null;

        try {
            digest = MessageDigest.getInstance(SHA_1);
            digest.reset();
            input = digest.digest(text.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertFromBytesToHex(input);
    }

    private static String convertFromBytesToHex(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfByte = (data[i] >>> 4) & 0x0F;
            int counter = 0;
            do {
                if ((0 <= halfByte) && (halfByte <= 9))
                    stringBuffer.append((char) ('0' + halfByte));
                else
                    stringBuffer.append((char) ('a' + (halfByte - 10)));
                    halfByte = data[i] & 0x0F;

            } while (counter++ < 1);
        }

        return stringBuffer.toString();
    }
}

