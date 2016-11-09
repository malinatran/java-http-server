package com.malinatran.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image {

    public static byte[] read(String fileName) {
        byte[] image = new byte[0];

        try {
            image = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static String getImageType(String fileName) {
        int startIndex = fileName.indexOf(".") + 1;
        int endIndex = fileName.length();

        return fileName.substring(startIndex, endIndex);
    }
}