package com.malinatran.response;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class ResponseFormatterTest {

    @Test
    public void formatHeaderLinesIteratesThroughHashMapAndReturnsStringWithNewLine() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PUT");

        String formattedLines = ResponseFormatter.formatHeaderLines(headers);
        String expected = "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n\r\n";

        assertEquals(expected, formattedLines);
    }

    @Test
    public void addNewLineReturnsStringWithNewLine() {
        String name = "Malina";

        String formattedLine = ResponseFormatter.addNewLine(name);
        String expected = "Malina\r\n";

        assertEquals(expected, formattedLine);
    }
}