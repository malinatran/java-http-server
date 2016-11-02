package com.malinatran.setup;

import com.malinatran.setup.CommandLineArgsParser;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandLineArgsParserTest {

    @Test
    public void constructorWithFlagsReturnsHashMap() {
        String[] args = {"-p", "9000", "-d", "/somewhere/over/the/rainbow/"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("-p", "9000");
        expected.put("-d", "/somewhere/over/the/rainbow/");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }

    @Test
    public void constructorWithoutFlagsReturnsEmptyHashMap() {
        String[] args = {"5000", "skies/are/blue"};
        Map<String, String> expected = new HashMap<String, String>();

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }

    @Test
    public void constructorWithNoEntryForDirectoryReturnsOnlyPort() {
        String[] args = {"-p", "9050", "-d"};
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("-p", "9050");

        CommandLineArgsParser result = new CommandLineArgsParser(args);

        assertEquals(expected, result.getConfiguration());
    }
}