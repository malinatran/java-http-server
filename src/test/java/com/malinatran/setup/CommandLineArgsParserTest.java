package com.malinatran.setup;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    private Map<String, String> expected;
    private String port;
    private String[] args;

    @Before
    public void setUp() {
        expected = new HashMap<String, String>();
    }

    @Test
    public void configureReturnsHashMap() {
        port = "9000";
        args = new String[]{ServerSettings.PORT_FLAG, port, ServerSettings.DIRECTORY_FLAG, "/somewhere/over/the/rainbow/"};
        expected.put(ServerSettings.PORT_FLAG, port);
        expected.put(ServerSettings.DIRECTORY_FLAG, "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.configure());
    }
    @Test
    public void constructorWithTwoValidArgsReturnsHashMap() {
        port = "5050";
        args = new String[]{ServerSettings.PORT_FLAG, port};
        expected.put(ServerSettings.PORT_FLAG, port);

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.configure());
    }
}