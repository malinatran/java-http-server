package com.malinatran.setup;

import com.malinatran.mocks.MockServerSettings;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerSettingsTest {

    private String CURRENT_DIRECTORY = ServerSettings.ROOT;
    private String TARGET_DIRECTORY = ServerSettings.DEFAULT_DIRECTORY;
    private String DEFAULT_DIRECTORY = CURRENT_DIRECTORY + TARGET_DIRECTORY;
    private int port = 5050;
    private Map<String, String> map;

    @Before
    public void setUp() {
        map = new Hashtable<String, String>();
    }

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        map.put("-p", String.valueOf(port));
        map.put("-d", TARGET_DIRECTORY);

        ServerSettings settings = new ServerSettings(map);

        assertEquals(port, settings.getPort());
        assertEquals(DEFAULT_DIRECTORY, settings.getDirectory());
    }

    @Test
    public void getPortReturnsDefaultPortIfThereIsNoCustomPort() {
        ServerSettings settings = new ServerSettings(map);

        int result = settings.getPort();

        assertEquals(5000, result);
    }

    @Test
    public void getPortReturnsCustomPort() {
        map.put("-p", String.valueOf(port));
        ServerSettings settings = new ServerSettings(map);

        int result = settings.getPort();

        assertEquals(port, result);
    }

    @Test
    public void getPortPrintsErrorMessageAndTerminates() {
        String port = "invalidPort";
        map.put("-p", port);
        MockServerSettings settings = new MockServerSettings(map);

        settings.getPort();
        assertTrue(settings.didExit());
    }

    @Test
    public void getDirectoryReturnsDefaultDirectoryIfThereIsNoCustomDirectory() {
        ServerSettings settings = new ServerSettings(map);

        String result = settings.getDirectory();

        assertEquals(DEFAULT_DIRECTORY, result);
    }

    @Test
    public void getDirectoryReturnsCustomDirectory() throws IOException {
        String directory = "/directory/";
        map.put("-d", directory);
        File file = new File(CURRENT_DIRECTORY + directory);
        file.mkdir();
        ServerSettings settings = new ServerSettings(map);

        String result = settings.getDirectory();

        assertEquals(CURRENT_DIRECTORY + directory, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getDirectoryPrintsErrorMessageAndTerminates() {
        String directory = "/Documents/nonexistent-directory";
        map.put("-d", directory);
        MockServerSettings settings = new MockServerSettings(map);

        settings.getDirectory();

        assertTrue(settings.didExit());
    }
}