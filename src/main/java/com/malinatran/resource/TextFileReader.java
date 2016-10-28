package com.malinatran.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TextFileReader {

    private final String START = "Start";
    private final String END = "End";

    public String readPartialTextFile(String directoryPath, String fileName, Map<String, Integer> range) throws IOException {
        int count = getCharacterCount(directoryPath, fileName);
        int[] contentRange = setPartialContentRange(range, count);
        String content = readTextFile(directoryPath, fileName);

        int start = contentRange[0];
        int end = Math.min(contentRange[1] + 1, count);

        return content.substring(start, end) + addNewLine(end, count);
    }

    private String addNewLine(int end, int count) {
        return (end == count ?  "\n" : "");
    }

    public int getCharacterCount(String directoryPath, String fileName) throws IOException {
        String content = readTextFile(directoryPath, fileName);

        return content.length();
    }

    public String readTextFile(String directoryPath, String fileName) throws IOException {
        String content = "";
        String line;

        FileReader fileReader = new FileReader(directoryPath + fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        return content;
    }

    public int[] setPartialContentRange(Map<String, Integer> range, int totalCount) {
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

    private Boolean hasStartAndEndRange(Map<String, Integer> range) {
        return hasRange(range, START) && hasRange(range, END);
    }

    private Boolean hasStartRangeOnly(Map<String, Integer> range) {
        return hasRange(range, START) && !hasRange(range, END);
    }

    private Boolean hasEndRangeOnly(Map<String, Integer> range) {
        return !hasRange(range, START) && hasRange(range, END);
    }

    private Boolean hasRange(Map<String, Integer> range, String key) {
        return range.containsKey(key);
    }
}
