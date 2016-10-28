package com.malinatran.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image {

    public byte[] extractBytes(String fullPath, String fileName) {
        byte[] byteArray = new byte[0];

        try {
            byteArray = Files.readAllBytes(Paths.get(fullPath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;
    }

    public String getImageType(String fileName) {
        int startIndex = fileName.indexOf(".") + 1;
        int endIndex = fileName.length();
        return fileName.substring(startIndex, endIndex);
    }
}
