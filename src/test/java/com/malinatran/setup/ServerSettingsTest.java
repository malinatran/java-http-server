package com.malinatran.setup;

import com.malinatran.mocks.MockServerSettings;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ServerSettingsTest {

    String TARGET_DIRECTORY = "/Development/cob_spec/public/";
    String USER_HOME = System.getProperty("user.home");
    String DEFAULT_DIRECTORY = USER_HOME + TARGET_DIRECTORY;

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        Map<String, String> map = new HashMap<String, String>();
        int port = 5050;
        String directory = TARGET_DIRECTORY;
        map.put("-p", String.valueOf(port));
        map.put("-d", directory);

        ServerSettings settings = new ServerSettings(map);

        assertEquals(port, settings.getPort());
        assertEquals(DEFAULT_DIRECTORY, settings.getDirectory());
    }

    @Test
    public void getPortReturnsDefaultPortIfThereIsNoCustomPort() {
        Map<String, String> map = new HashMap<String, String>();
        ServerSettings settings = new ServerSettings(map);

        int result = settings.getPort();

        assertEquals(5000, result);
    }

    @Test
    public void getPortReturnsCustomPort() {
        Map<String, String> map = new HashMap<String, String>();
        int port = 5050;
        map.put("-p", String.valueOf(port));
        ServerSettings settings = new ServerSettings(map);

        int result = settings.getPort();

        assertEquals(port, result);
    }

    @Test
    public void getPortPrintsErrorMessageAndTerminates() {
        Map<String, String> map = new HashMap<String, String>();
        String port = "invalidPort";
        map.put("-p", port);
        MockServerSettings settings = new MockServerSettings(map);

        settings.getPort();
        assertTrue(settings.didExit());
    }

    @Test
    public void getDirectoryReturnsDefaultDirectoryIfThereIsNoCustomDirectory() {
        Map<String, String> map = new HashMap<String, String>();
        ServerSettings settings = new ServerSettings(map);

        String result = settings.getDirectory();

        assertEquals(DEFAULT_DIRECTORY, result);
    }

    @Test
    public void getDirectoryReturnsCustomDirectory() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        String directory = "/Documents/directory/";
        map.put("-d", directory);
        File file = new File(USER_HOME + directory);
        file.mkdir();
        ServerSettings settings = new ServerSettings(map);

        String result = settings.getDirectory();

        assertEquals(USER_HOME + directory, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void getDirectoryPrintsErrorMessageAndTerminates() {
        Map<String, String> map = new HashMap<String, String>();
        String directory = "/Documents/nonexistent-directory";
        map.put("-d", directory);
        MockServerSettings settings = new MockServerSettings(map);

        settings.getDirectory();
        assertTrue(settings.didExit());
    }
}