package com.malinatran.setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class ErrorHandlerTest {

    private String expected;
    private ErrorHandler handler;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        handler = new ErrorHandler();
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void printTakesInStringsAsArgs() {
        expected = "Invalid directory: /directory. Exiting now.\n";

        handler.print(ErrorHandler.DIRECTORY, "/directory", ErrorHandler.INVALID);

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringsAndIntegerAsArgs() {
        expected = "Busy port: 9090. Exiting now.\n";

        handler.print(ErrorHandler.PORT, 9090, ErrorHandler.BUSY);

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringArrayAsArgs() {
        expected = "Invalid args: [-p, 5050, -d]. Exiting now.\n" + ErrorHandler.FORMATTING_RULES + "\n";

        handler.print(new String[]{"-p", "5050", "-d"});

        assertEquals(expected, out.toString());
    }
}