package com.malinatran;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ServerConfigurationTest {

    @Test
    public void constructorSetsPortAndDirectoryWithNewValues() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Port", "5050");
        map.put("Directory", "/hi/there/");

        ServerConfiguration config = new ServerConfiguration(map);

        assertEquals(5050, config.getPort());
//        assertEquals("/Users/mteatran/hi/there/", config.getFullPath());
    }

    @Test
    public void constructorUsesDefaultPortIfPortNotProvided() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Directory", "/no/port/");

        ServerConfiguration config = new ServerConfiguration(map);

        assertEquals(5000, config.getPort());
    }
}