package com.malinatran.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Encoder {

    private static final String SHA_1 = "SHA-1";
    private static final String UTF_8 = "UTF-8";

    public static String encode(String text) {
        MessageDigest digest;
        byte[] input = null;

        try {
            digest = MessageDigest.getInstance(SHA_1);
            digest.reset();
            input = digest.digest(text.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return convertFromBytesToHex(input);
    }

    private static String convertFromBytesToHex(byte[] data) {
        StringBuffer encodedData = new StringBuffer();

        for (byte datum : data) {
            int halfByte = setInitialValue(datum);
            int counter = 0;

            do {
                if (isWithinRange(halfByte)) {
                    appendZero(encodedData, halfByte);
                } else
                    appendLetter(encodedData, halfByte);
                    halfByte = changeValue(datum);

            } while (counter++ < 1);
        }

        return encodedData.toString();
    }

    private static int setInitialValue(byte datum) {
        return (datum >>> 4) & 0x0F;
    }

    private static int changeValue(byte datum) {
        return datum & 0x0F;
    }

    private static boolean isWithinRange(int halfByte) {
        return (0 <= halfByte) && (halfByte <= 9);
    }

    private static StringBuffer appendZero(StringBuffer encodedData, int halfByte) {
       return encodedData.append((char) ('0' + halfByte));
    }

    private static StringBuffer appendLetter(StringBuffer encodedData, int halfByte) {
        return encodedData.append((char) ('a' + (halfByte - 10)));
    }
}
