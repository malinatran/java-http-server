package com.malinatran.setup;

import com.malinatran.response.Formatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class CommandLinePrinterTest {

    private int PORT_9090 = 9090;
    private String expected;
    private CommandLinePrinter printer;
    private ByteArrayOutputStream out;
    private PrintStream testOut;

    @Before
    public void setUp() {
        printer = new CommandLinePrinter();
        out = new ByteArrayOutputStream();
        testOut = System.out;
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(testOut);
    }

    @Test
    public void printTakesInPortAndDirectory() {
        expected = Formatter.addLF("Port: 9090" + Formatter.LF + "Directory: /directory");

        printer.print(PORT_9090, "/directory");

        assertEquals(expected, out.toString());
    }

    @Test
    public void printTakesInStringsAsArgs() {
        expected = Formatter.addLF("Invalid directory: /directory. Exiting now.");

        printer.print(DirectoryArg.DIRECTORY, "/directory", DirectoryArg.INVALID);

        assertEquals(expected, out.toString());
    }
}