package com.malinatran.setup;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerConfigurationTest {

    private String CURRENT_DIRECTORY = DirectoryArg.HOME_DIRECTORY;
    private String DEFAULT_DIRECTORY = DirectoryArg.DEFAULT_DIRECTORY;
    private String DEFAULT_PATH = DirectoryArg.DEFAULT_PATH;
    private int PORT_5000 = 5000;
    private int PORT_5050 = 5050;
    private String[] args;
    private Map<String, String> expected;
    private ServerConfiguration config;

    @Before
    public void setUp() {
        args = new String[] { PortArg.FLAG, String.valueOf(PORT_5000), DirectoryArg.FLAG, DEFAULT_DIRECTORY };
        expected = new Hashtable<String, String>();
        config = new ServerConfiguration(args);
    }

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        assertEquals(PORT_5000, config.getPort());
        assertEquals(DEFAULT_PATH, config.getDirectory());
    }

    @Test
    public void constructorSetsSettingsMap() {
        expected.put(PortArg.FLAG, String.valueOf(PORT_5000));
        expected.put(DirectoryArg.FLAG, DEFAULT_DIRECTORY);

        config = new ServerConfiguration(args);

        assertEquals(expected, config.getSettings());
    }

    @Test
    public void constructorSetsSettingsMapWithValidPortAndDirectory() throws IOException {
        String fileName = File.separator + "directory" + File.separator;
        File file = new File(CURRENT_DIRECTORY + fileName);
        file.mkdir();
        args = new String[] { "-p", String.valueOf(PORT_5050), "-d", fileName };
        expected.put("-p", String.valueOf(PORT_5050));
        expected.put("-d", fileName);

        config = new ServerConfiguration(args);

        assertEquals(expected, config.getSettings());
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void constructorDoesNotSetSettingsMapWithoutPrecedingFlag() {
        args = new String[] { "-x", "test", "-o", "test"};

        config = new ServerConfiguration(args);

        assertEquals(expected, config.getSettings());
    }

    @Test
    public void getPortReturnsDefaultPortIfThereIsNoCustomPort() {
        args = new String[] {};
        config = new ServerConfiguration(args);

        int actual = config.getPort();

        assertEquals(PORT_5000, actual);
    }

    @Test
    public void getPortReturnsCustomPort() {
        args = new String[] { PortArg.FLAG, String.valueOf(PORT_5050) };
        config = new ServerConfiguration(args);

        int actual = config.getPort();

        assertEquals(PORT_5050, actual);
    }

    @Test
    public void getDirectoryReturnsDefaultDirectoryIfThereIsNoCustomDirectory() {
        args = new String[] {};
        config = new ServerConfiguration(args);

        String actual = config.getDirectory();

        assertEquals(DEFAULT_PATH, actual);
    }

    @Test
    public void getDirectoryReturnsCustomDirectoryWithFileSeparators() throws IOException {
        String fileName = "testDirectory";
        String directory = CURRENT_DIRECTORY + File.separator + fileName + File.separator;
        File file = new File(directory);
        file.mkdir();
        args = new String[] { DirectoryArg.FLAG, fileName };
        config = new ServerConfiguration(args);

        String actual = config.getDirectory();

        assertEquals(directory, actual);
        Files.deleteIfExists(file.toPath());
    }
}