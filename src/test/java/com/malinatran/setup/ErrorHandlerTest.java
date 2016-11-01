package com.malinatran.setup;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class ErrorHandlerTest {

    ErrorHandler handler = new ErrorHandler();

    @Test
    public void printTakesInStringsAsArgs() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        handler.print(ErrorHandler.DIRECTORY, "/directory", ErrorHandler.INVALID);
        String expected = "Invalid directory: /directory. Exiting now.\n";

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringsAndIntegerAsArgs() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        handler.print(ErrorHandler.PORT, 9090, ErrorHandler.BUSY);
        String expected = "Busy port: 9090. Exiting now.\n";

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringArrayAsArgs() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        handler.print(new String[]{"-p", "5050", "-d"});
        String expected = "Invalid args: [-p, 5050, -d]. Exiting now.\n" + ErrorHandler.FORMATTING_RULES + "\n";

        assertEquals(expected, out.toString());
    }
}