package com.malinatran.resource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class TextFile {

    public static final String START = "Start";
    public static final String END = "End";
    private Map<String, Integer> ranges;
    private int count;

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
        this.ranges = ranges;
        this.count = getCharacterCount(filePath);

        if (ranges.isEmpty()) {
            return readTextFile(filePath);
        } else {
            int[] contentRange = setContentRange();
            String content = readTextFile(filePath);

            int start = contentRange[0];
            int end = Math.min(contentRange[1] + 1, count);

            return content.substring(start, end) + addEOFCharacter(end);
        }
    }

    private int getCharacterCount(String filePath) throws IOException {
        String content = readTextFile(filePath);

        return content.length();
    }

    private String addEOFCharacter(int end) {
        return (end == count ?  "\n" : "");
    }

    private int[] setContentRange() {
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
