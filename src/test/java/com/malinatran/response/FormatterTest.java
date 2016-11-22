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

        assertEquals(Formatter.LF, eofCharacter);
    }

    @Test
    public void addEOFCharacterDoesNotAddIfNotEndOfLine() {
        int end = 2;

        String eofCharacter = Formatter.addEOFCharacter(end, count);

        assertEquals("", eofCharacter);
    }

    @Test
    public void addLFReturnsStringWithNewLF() {
        String name = "Jane Villanueva";

        String actual = Formatter.addLF(name);
        String expected = "Jane Villanueva\n";

        assertEquals(expected, actual);
    }

    @Test
    public void addCRLFReturnsStringWithNewCRLF() {
        String name = "Malina";

        String actual = Formatter.addCRLF(name);
        String expected = "Malina\r\n";

        assertEquals(expected, actual);
    }

    @Test
    public void formatHeaderLinesIteratesThroughHashtableAndReturnsStringWithNewLine() {
        Map<String, String> headers = new Hashtable<String, String>();
        headers.put("Allow", "GET,HEAD,POST,OPTIONS,PUT");

        String formattedLines = Formatter.formatHeaderLines(headers);
        String expected = "Allow: GET,HEAD,POST,OPTIONS,PUT\r\n\r\n";

        assertEquals(expected, formattedLines);
    }
}
