package com.malinatran.setup;

import com.malinatran.setup.ServerSettings;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ServerSettingsTest {

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("-p", "5050");
        map.put("-d", "/hi/there/");

        ServerSettings config = new ServerSettings(map);

        assertEquals(5050, config.getPort());
    }

    @Test
    public void constructorUsesDefaultPortIfPortNotProvided() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("-d", "/no/port/");

        ServerSettings config = new ServerSettings(map);

        assertEquals(5000, config.getPort());
    }
}