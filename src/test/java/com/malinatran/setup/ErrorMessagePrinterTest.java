package com.malinatran.setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class ErrorMessagePrinterTest {

    private String expected;
    private ErrorMessagePrinter handler;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        handler = new ErrorMessagePrinter();
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

        handler.print(ErrorMessagePrinter.DIRECTORY, "/directory", ErrorMessagePrinter.INVALID);

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringsAndIntegerAsArgs() {
        expected = "Busy port: 9090. Exiting now.\n";

        handler.print(ErrorMessagePrinter.PORT, 9090, ErrorMessagePrinter.BUSY);

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringArrayAsArgs() {
        expected = "Invalid args: [-p, 5050, -d]. Exiting now.\n" + ErrorMessagePrinter.FORMATTING_RULES + "\n";

        handler.print(new String[]{"-p", "5050", "-d"});

        assertEquals(expected, out.toString());
    }
}