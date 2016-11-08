package com.malinatran.setup;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

public class OptionalArgsTest {

    private ServerSettings settings;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void configureServerReturnsServerSettingsObject() {
        int port = 9090;
        String[] args = { "-p", "9090"};

        settings = OptionalArgs.configureServer(args);

        assertEquals(port, settings.getPort());
    }

    @Test
    public void printArgsPrintsPortAndDirectory() {
        String[] args = { "-p", "9090"};
        OptionalArgs.configureServer(args);
        String expected = "Port: 9090\nDirectory: " + System.getProperty("user.dir") + "/public/\n";

        OptionalArgs.printArgs();

        assertEquals(expected, out.toString());
    }
}