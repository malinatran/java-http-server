package com.malinatran;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ServerSettingsTest {

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Port", "5050");
        map.put("Directory", "/hi/there/");

        ServerSettings config = new ServerSettings(map);

        assertEquals(5050, config.getPort());
    }

    @Test
    public void constructorUsesDefaultPortIfPortNotProvided() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Directory", "/no/port/");

        ServerSettings config = new ServerSettings(map);

        assertEquals(5000, config.getPort());
    }
}