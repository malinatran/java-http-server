package com.malinatran.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TextFile {

    public static final String START = "Start";
    public static final String END = "End";

    public String readTextFile(String filePath) throws IOException {
        String content = "";
        String line;

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        return content;
    }

    public String readPartialTextFile(String filePath, Map<String, Integer> ranges) throws IOException {
        int count = getCharacterCount(filePath);
        int[] contentRange = setContentRange(ranges, count);
        String content = readTextFile(filePath);

        int start = contentRange[0];
        int end = Math.min(contentRange[1] + 1, count);

        return content.substring(start, end) + addEOFCharacter(end, count);
    }

    private int getCharacterCount(String filePath) throws IOException {
        String content = readTextFile(filePath);

        return content.length();
    }

    private String addEOFCharacter(int end, int count) {
        return (end == count ?  "\n" : "");
    }

    private int[] setContentRange(Map<String, Integer> range, int totalCount) {
        int[] contentRange = new int[2];

        if (hasStartAndEndRange(range)) {
            contentRange[0] = getValue(range, START);
            contentRange[1] = getValue(range, END);
        } else if (hasStartRangeOnly(range)) {
            contentRange[0] = getValue(range, START);
            contentRange[1] = totalCount;
        } else if (hasEndRangeOnly(range)) {
            contentRange[0] = totalCount - (getValue(range, END) - 1);
            contentRange[1] = totalCount;
        }

        return contentRange;
    }

    private int getValue(Map<String, Integer> range, String key) {
        return range.get(key);
    }

    private boolean hasStartAndEndRange(Map<String, Integer> range) {
        return hasRange(range, START) && hasRange(range, END);
    }

    private boolean hasStartRangeOnly(Map<String, Integer> range) {
        return hasRange(range, START) && !hasRange(range, END);
    }

    private boolean hasEndRangeOnly(Map<String, Integer> range) {
        return !hasRange(range, START) && hasRange(range, END);
    }

    private boolean hasRange(Map<String, Integer> range, String key) {
        return range.containsKey(key);
    }
}
