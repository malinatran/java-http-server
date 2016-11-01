package com.malinatran.setup;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.*;

public class InputValidatorTest {

    InputValidator validator = new InputValidator();

    @Test
    public void isFlagReturnsTrueIfArgIsFlag() {
        Boolean firstResult = validator.isFlag("-p");
        Boolean secondResult = validator.isFlag("-d");

        assertTrue(firstResult);
        assertTrue(secondResult);
    }

    @Test
    public void isFlagReturnsFalseIfArgIsNotFlag() {
        Boolean result = validator.isFlag("-o");

        assertFalse(result);
    }

    @Test
    public void addFileSeparatorsAddsSeparatorsIfNone() throws IOException {
        String fileName = "Documents/dingbat";
        String directory = System.getProperty("user.home") + "/" + fileName + "/";
        File file = new File(directory);
        file.mkdir();
        String expected = "/Documents/dingbat/";

        String result = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addFileSeparatorsAddsSeparatorToBeginningIfDoesNotHaveOne() throws IOException {
        String fileName = "Documents/test-directory/";
        String directory = System.getProperty("user.home") + "/" + fileName;
        File file = new File(directory);
        file.mkdir();
        String expected = "/Documents/test-directory/";

        String result = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, result);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void addFileSeparatorsDoesNotAddSeparatorsIfInteger() {
        String expected = "9090";

        String result = validator.addFileSeparators("-p", expected);

        assertEquals(expected, result);
    }

    @Test
    public void isValidDirectoryReturnsTrueIfExistingDirectory() {
        String directory = System.getProperty("user.home") + "/some-directory";
        File file = new File(directory);
        file.mkdir();
        Boolean result = validator.isValidDirectory(directory);

        assertTrue(result);
        file.delete();
    }

    @Test
    public void isValidDirectoryReturnsFalseIfNonexistentDirectory() {
        String directory = System.getProperty("user.home") + "/not-a-test-directory";
        Boolean result = validator.isValidDirectory(directory);

        assertFalse(result);
    }

    @Test
    public void isIntegerReturnsTrueIfInteger() {
        String integer = "10";
        Boolean result = validator.isInteger(integer);

        assertTrue(result);
    }

    @Test
    public void isIntegerReturnsFalseIfNotInteger() {
        String notInteger = "words";
        Boolean result = validator.isInteger(notInteger);

        assertFalse(result);
    }
}