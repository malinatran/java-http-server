package com.malinatran.setup;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    @Test
    public void constructorWithFlagsAndArgsReturnsHashMap() {
        String[] args = {"-p", "9000", "-d", "/somewhere/over/the/rainbow/"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("-p", "9000");
        expected.put("-d", "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
    @Test
    public void constructorWithTwoValidArgsReturnsHashMap() {
        String[] args = {"-p", "5050"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("-p", "5050");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
}