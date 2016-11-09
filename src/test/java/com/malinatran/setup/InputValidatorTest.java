package com.malinatran.setup;

import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.Assert.*;

public class InputValidatorTest {

    private String PATH = ServerSettings.ROOT;
    private InputValidator validator;
    private boolean result;
    private String actual;

    @Before
    public void setUp() {
        validator = new InputValidator();
    }

    @Test
    public void isFlagReturnsTrueIfArgIsFlag() {
        boolean firstResult = validator.isFlag("-p");
        boolean secondResult = validator.isFlag("-d");

        assertTrue(firstResult);
        assertTrue(secondResult);
    }

    @Test
    public void isFlagReturnsFalseIfArgIsNotFlag() {
        result = validator.isFlag("-o");

        assertFalse(result);
    }

    @Test
    public void addFileSeparatorsAddsSeparatorsIfNone() throws IOException {
        String fileName = "Documents/dingbat";
        String directory = PATH + "/" + fileName + "/";
        File file = new File(directory);
        file.mkdir();
        String expected = "/Documents/dingbat/";

        actual = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addFileSeparatorsAddsSeparatorToBeginningIfDoesNotHaveOne() throws IOException {
        String fileName = "Documents/test-directory/";
        String directory = PATH + "/" + fileName;
        File file = new File(directory);
        file.mkdir();
        String expected = "/Documents/test-directory/";

        actual = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addFileSeparatorsDoesNotAddSeparatorsIfInteger() {
        String expected = "9090";

        actual = validator.addFileSeparators("-p", expected);

        assertEquals(expected, actual);
    }

    @Test
    public void isValidDirectoryReturnsTrueIfExistingDirectory() {
        String directory = PATH + "/some-directory";
        File file = new File(directory);
        file.mkdir();
        result = validator.isValidDirectory(directory);

        assertTrue(result);
        file.delete();
    }

    @Test
    public void isValidDirectoryReturnsFalseIfNonexistentDirectory() {
        String directory = PATH + "/not-a-test-directory";
        result = validator.isValidDirectory(directory);

        assertFalse(result);
    }
}