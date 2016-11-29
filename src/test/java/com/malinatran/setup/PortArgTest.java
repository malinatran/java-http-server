package com.malinatran.setup;

import com.malinatran.mocks.MockArg;

import org.junit.Before;
import org.junit.Test;

import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class PortArgTest {

    private int PORT_5000 = 5000;
    private int PORT_5050 = 5050;
    private PortArg portArg;
    private boolean result;
    private Map<String, String> settings;

    @Before
    public void setUp() {
        portArg = new PortArg();
        settings = new Hashtable<String, String>();
    }

    @Test
    public void isFlagReturnsTrue() throws Exception {
        result = portArg.isFlag(PortArg.FLAG);

        assertTrue(result);
    }

    @Test
    public void isFlagReturnsFalse() throws Exception {
        result = portArg.isFlag("-o");

        assertFalse(result);
    }

    @Test
    public void isIntegerReturnsTrue() throws Exception {
        result = portArg.isInteger("1000");

        assertTrue(result);
    }

    @Test
    public void isIntegerReturnsFalse() throws Exception {
        result = portArg.isInteger("lalala");

        assertFalse(result);
    }

    @Test
    public void setIntegerReturnsDefaultPortIfPortIsNull() throws Exception {
        int result = portArg.setInteger(settings);

        assertEquals(PORT_5000, result);
    }

    @Test
    public void setIntegerReturnsCustomPort() throws Exception {
        settings.put(PortArg.FLAG, String.valueOf(PORT_5050));

        int result = portArg.setInteger(settings);

        assertEquals(PORT_5050, result);
    }

    @Test
    public void setStringPrintsErrorMessageAndTerminates() {
        MockArg portArg = new MockArg();

        portArg.setInteger();

        assertTrue(portArg.didExit());
    }
}