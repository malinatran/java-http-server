package com.malinatran.setup;

import com.malinatran.mocks.MockArg;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class DirectoryArgTest {

    private DirectoryArg directoryArg;
    private boolean result;
    private Map<String, String> settings;

    @Before
    public void setUp() {
        directoryArg = new DirectoryArg();
        settings = new Hashtable<String, String>();
    }

    @Test
    public void isFlagReturnsTrue() throws Exception {
        result = directoryArg.isFlag(DirectoryArg.FLAG);

        assertTrue(result);
    }

    @Test
    public void isFlagReturnsFalse() throws Exception {
        result = directoryArg.isFlag("-x");

        assertFalse(result);
    }

    @Test
    public void setStringReturnsDefaultDirectory() throws Exception {
        String result = directoryArg.setString(settings);

        assertEquals(DirectoryArg.DEFAULT_PATH, result);
    }

    @Test
    public void setStringReturnsCustomPort() throws Exception {
        String directory = File.separator + "test" + File.separator;
        settings.put(DirectoryArg.FLAG, directory);
        File file = new File(DirectoryArg.HOME_DIRECTORY + directory);
        file.mkdir();

        String actual = directoryArg.setString(settings);

        assertEquals(DirectoryArg.HOME_DIRECTORY + directory, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void setStringPrintsErrorMessageAndTerminates() {
        MockArg directoryArg = new MockArg();

        directoryArg.setString();

        assertTrue(directoryArg.didExit());
    }
}