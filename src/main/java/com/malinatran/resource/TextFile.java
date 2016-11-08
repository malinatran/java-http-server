package com.malinatran.resource;

import com.malinatran.response.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TextFile {

    public static final String START = "Start";
    public static final String END = "End";
    private Map<String, Integer> ranges;

    public String readTextFile(String filePath, Map<String, Integer> ranges) throws IOException {
        this.ranges = ranges;

        return (ranges.isEmpty() ? readFull(filePath) : readPartial(filePath));
    }

    private String readFull(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String content = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        return content;
    }

    private String readPartial(String filePath) throws IOException {
        int count = getCharacterCount(filePath);
        int[] contentRange = setContentRange(count);
        String content = readFull(filePath);

        int start = contentRange[0];
        int end = Math.min(contentRange[1] + 1, count);

        return content.substring(start, end) + Formatter.addEOFCharacter(end, count);
    }

    private int getCharacterCount(String filePath) throws IOException {
        String content = readFull(filePath);

        return content.length();
    }

    private int[] setContentRange(int count) {
        int[] contentRange = new int[2];

        if (hasStartAndEndRange()) {
            contentRange[0] = getValue(START);
            contentRange[1] = getValue(END);
        } else if (hasStartRangeOnly()) {
            contentRange[0] = getValue(START);
            contentRange[1] = count;
        } else if (hasEndRangeOnly()) {
            contentRange[0] = count - (getValue(END) - 1);
            contentRange[1] = count;
        }

        return contentRange;
    }

    private int getValue(String key) {
        return ranges.get(key);
    }

    private boolean hasStartAndEndRange() {
        return hasRange(START) && hasRange(END);
    }

    private boolean hasStartRangeOnly() {
        return hasRange(START) && !hasRange(END);
    }

    private boolean hasEndRangeOnly() {
        return !hasRange(START) && hasRange(END);
    }

    private boolean hasRange(String key) {
        return ranges.containsKey(key);
    }
}
