package com.malinatran.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image {

    public static byte[] extractBytes(String fileName) {
        byte[] imageBytes = new byte[0];

        try {
            imageBytes = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageBytes;
    }

    public static String getImageType(String fileName) {
        int startIndex = fileName.indexOf(".") + 1;
        int endIndex = fileName.length();
        return fileName.substring(startIndex, endIndex);
    }
}
