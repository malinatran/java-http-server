package com.malinatran.response;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class FormatterTest {

    @Test
    public void formatHeaderLinesIteratesThroughHashMapAndReturnsStringWithNewLine() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PUT");

        String formattedLines = Formatter.formatHeaderLines(headers);
        String expected = "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n\r\n";

        assertEquals(expected, formattedLines);
    }

    @Test
    public void addNewLineReturnsStringWithNewLine() {
        String name = "Malina";

        String formattedLine = Formatter.addNewLine(name);
        String expected = "Malina\r\n";

        assertEquals(expected, formattedLine);
    }
}