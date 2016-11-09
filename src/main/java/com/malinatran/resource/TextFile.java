package com.malinatran.resource;

import com.malinatran.response.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TextFile {

    public static int getCharacterCount(String filePath) throws IOException {
        String content = readFull(filePath);

        return content.length();
    }

    public static String read(String filePath, Map<String, Integer> range) throws IOException {
        return (range.isEmpty() ? readFull(filePath) : readPartial(range, filePath));
    }

    private static String readFull(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        return content;
    }

    private static String readPartial(Map<String, Integer> range, String filePath) throws IOException {
        int count = getCharacterCount(filePath);
        int[] contentRange = ContentRangeHelper.getRange(range, count);
        String content = readFull(filePath);

        int start = contentRange[0];
        int end = Math.min(contentRange[1] + 1, count);

        return content.substring(start, end) + Formatter.addEOFCharacter(end, count);
    }
}