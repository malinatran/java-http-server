package com.malinatran.response;

import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class FormatterTest {

    private int count = 5;

    @Test
    public void addEOFCharacterAddsNewLineIfEndOfLine() {
        int end = 5;

        String eofCharacter = Formatter.addEOFCharacter(end, count);

        assertEquals("\n", eofCharacter);
    }

    @Test
    public void addEOFCharacterDoesNotAddIfNotEndOfLine() {
        int end = 2;

        String eofCharacter = Formatter.addEOFCharacter(end, count);

        assertEquals("", eofCharacter);
    }

    @Test
    public void formatHeaderLinesIteratesThroughHashtableAndReturnsStringWithNewLine() {
        Map<String, String> headers = new Hashtable<String, String>();
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
