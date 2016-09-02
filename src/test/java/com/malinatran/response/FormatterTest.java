package com.malinatran.response;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class FormatterTest {

    @Test
    public void testFormatHeaderLines() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        Formatter formatter = new Formatter();
        String formattedLines = formatter.formatHeaderLines(headers);
        String expected = "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n";
        assertEquals(expected, formattedLines);
    }

    @Test
    public void testAddNewLine() {
        String name = "Malina";
        Formatter formatter = new Formatter();
        String formattedLine = formatter.addNewLine(name);
        String expected = "Malina\r\n";
        assertEquals(expected, formattedLine);
    }
}