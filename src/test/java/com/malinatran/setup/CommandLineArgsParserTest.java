package com.malinatran.setup;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    Map<String, String> expected;

    @Before
    public void setUp() {
        expected = new HashMap<String, String>();
    }

    @Test
    public void constructorWithFlagsAndArgsReturnsHashMap() {
        String[] args = {"-p", "9000", "-d", "/somewhere/over/the/rainbow/"};
        expected.put("-p", "9000");
        expected.put("-d", "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
    @Test
    public void constructorWithTwoValidArgsReturnsHashMap() {
        String[] args = {"-p", "5050"};
        expected.put("-p", "5050");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
}