package com.malinatran.setup;

import org.junit.Test;

import java.io.File;

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
    public void addFileSeparatorsAddsSeparatorsIfNone() {
        String fileName = "test-directory";
        String directory = System.getProperty("user.home") + "/" + fileName + "/";
        File file = new File(directory);
        file.mkdir();
        String expected = "/test-directory/";

        String result = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, result);
        file.delete();
    }

    @Test
    public void addFileSeparatorsAddsSeparatorToBeginningIfDoesNotHaveOne() {
        String fileName = "test-directory/";
        String directory = System.getProperty("user.home") + "/" + fileName;
        File file = new File(directory);
        file.mkdir();
        String expected = "/test-directory/";

        String result = validator.addFileSeparators("-d", fileName);

        assertEquals(expected, result);
        file.delete();
    }

    @Test
    public void addFileSeparatorsDoesNotAddSeparatorsIfInteger() {
        String expected = "9090";

        String result = validator.addFileSeparators("-p", expected);

        assertEquals(expected, result);
    }

    @Test
    public void isValidDirectoryReturnsTrueIfExistingDirectory() {
        String directory = System.getProperty("user.home") + "/test-directory";
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