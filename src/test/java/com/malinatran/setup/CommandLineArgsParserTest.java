package com.malinatran.setup;

import com.malinatran.setup.CommandLineArgsParser;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    @Test
    public void constructorWithFourArgsReturnsHashMap() {
        String[] args = {"-p", "9000", "-d", "/somewhere/over/the/rainbow/"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Port", "9000");
        expected.put("Directory", "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }

    @Test
    public void constructorWithoutReturnsHashMap() {
        String[] args = {"5000", "skies/are/blue"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Port", "5000");
        expected.put("Directory", "/skies/are/blue/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }

    @Test
    public void constructorWithVariableNumberOfArgsReturnsHashMap() {
        String[] args = {"-p", "/directory", "-d"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("Directory", "/directory/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
}