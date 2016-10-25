package com.malinatran.router;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Image {

    private static final String ROOT_DIRECTORY = System.getProperty("user.home") + "/Development/cob_spec/public/";

    public byte[] extractBytes(String fileName) {
        byte[] byteArray = new byte[0];

        try {
            byteArray = Files.readAllBytes(Paths.get(ROOT_DIRECTORY + fileName));
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
