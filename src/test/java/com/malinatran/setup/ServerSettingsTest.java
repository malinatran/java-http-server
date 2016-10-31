package com.malinatran.setup;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
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

        ServerSettings config = new ServerSettings(map);

        assertEquals(port, config.getPort());
        assertEquals(DEFAULT_DIRECTORY, config.getDirectory());
    }

    @Test
    public void getPortReturnsDefaultPortIfThereIsNoCustomPort() {
        Map<String, String> map = new HashMap<String, String>();
        ServerSettings config = new ServerSettings(map);

        int result = config.getPort();

        assertEquals(5000, result);
    }

    @Test
    public void getPortReturnsCustomPort() {
        Map<String, String> map = new HashMap<String, String>();
        int port = 5050;
        map.put("-p", String.valueOf(port));
        ServerSettings config = new ServerSettings(map);

        int result = config.getPort();

        assertEquals(port, result);
    }

    @Test
    public void getDirectoryReturnsDefaultDirectoryIfThereIsNoCustomDirectory() {
        Map<String, String> map = new HashMap<String, String>();
        ServerSettings config = new ServerSettings(map);

        String result = config.getDirectory();

        assertEquals(DEFAULT_DIRECTORY, result);
    }

    @Test
    public void getDirectoryReturnsCustomDirectory() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        String directory = "/Documents/test-directory/";
        map.put("-d", directory);
        File file = new File(USER_HOME + directory);
        file.mkdir();
        ServerSettings config = new ServerSettings(map);

        String result = config.getDirectory();

        assertEquals(USER_HOME + directory, result);
        file.delete();
    }
}