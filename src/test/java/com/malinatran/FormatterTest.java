package com.malinatran;

import org.junit.Test;
import static org.junit.Assert.*;

public class FormatterTest {

    @Test
    public void testBuildResponse() {
        String[] lines = {"Hi", "my", "name", "is", "Malina"};
        Formatter formatter = new Formatter();
        String formattedLines = formatter.buildResponse(lines);
        String expected = "Hi\r\n" + "my\r\n" + "name\r\n" + "is\r\n" + "Malina\r\n";
        assertEquals(expected, formattedLines);
    }

    @Test
    public void testBuildInitialLine() {
        String protocol = "HTTP/1.1";
        String status200 = "200 OK";
        Formatter formatter = new Formatter();
        String formattedLine = formatter.buildInitialLine(protocol, status200);
        String expected = "HTTP/1.1 200 OK\r\n";
        assertEquals(expected, formattedLine);
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