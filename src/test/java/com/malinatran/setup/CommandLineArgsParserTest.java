package com.malinatran.setup;

import org.junit.Before;
import org.junit.Test;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    private String[] args;
    private Map<String, String> expected;
    private String port;

    @Before
    public void setUp() {
        expected = new Hashtable<String, String>();
    }

    @Test
    public void configureReturnsHashtable() {
        port = "9000";
        args = new String[]{ServerSettings.PORT_FLAG, port, ServerSettings.DIRECTORY_FLAG, "/somewhere/over/the/rainbow/"};
        expected.put(ServerSettings.PORT_FLAG, port);
        expected.put(ServerSettings.DIRECTORY_FLAG, "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.configure());
    }

    @Test
    public void constructorWithTwoValidArgsReturnsHashtable() {
        port = "5050";
        args = new String[]{ServerSettings.PORT_FLAG, port};
        expected.put(ServerSettings.PORT_FLAG, port);

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.configure());
    }
}
