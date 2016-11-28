package com.malinatran.resource;

import com.malinatran.response.Formatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileContentReader {

    public static int getCharacterCount(String filePath) throws IOException {
        String content = new String(read(filePath));

        return content.length() - 1;
    }

    public static byte[] read(String fileName) {
        byte[] fileContent = new byte[0];

        try {
            fileContent = Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    public static String read(String filePath, Map<String, Integer> range) throws IOException {
        return (range.isEmpty() ? new String(read(filePath)) : read(range, filePath));
    }

    private static String read(Map<String, Integer> range, String filePath) throws IOException {
        int count = getCharacterCount(filePath);
        int[] contentRange = ContentRangeHelper.getRange(range, count);
        String content = new String(read(filePath));

        int start = contentRange[0];
        int end = Math.min(contentRange[1] + 1, count);

        return content.substring(start, end) + Formatter.addEOFCharacter(end, count);
    }
}