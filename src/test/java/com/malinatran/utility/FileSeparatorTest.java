package com.malinatran.utility;

import com.malinatran.setup.DirectoryArg;

import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class FileSeparatorTest {

    private String PATH = DirectoryArg.HOME_DIRECTORY;
    private String actual;

    @Test
    public void formatAddsSeparatorsIfNone() throws IOException {
        String fileName = "Documents" + File.separator + "dingbat";
        String expected = File.separator + fileName + File.separator;
        String directory = PATH + expected;
        File file = new File(directory);
        file.mkdir();

        actual = FileSeparator.format(fileName);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void formatDoesNotAddSeparatorsIfTheyExist() throws IOException {
        String expected = File.separator + "Documents" + File.separator;
        String directory = PATH + expected;
        File file = new File(directory);
        file.mkdir();

        actual = FileSeparator.format(expected);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void formatAddsSeparatorToBeginningIfDoesNotHaveOne() throws IOException {
        String fileName = "Documents" + File.separator + "test-directory" + File.separator;
        String expected = File.separator + fileName;
        String directory = PATH + expected;
        File file = new File(directory);
        file.mkdir();

        actual = FileSeparator.format(fileName);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }

    @Test
    public void formatAddsSeparatorToEndIfDoesNotHaveOne() throws IOException {
        String fileName = File.separator + "Documents";
        String expected = File.separator + "Documents" + File.separator;
        String directory = PATH + expected;
        File file = new File(directory);
        file.mkdir();

        actual = FileSeparator.format(fileName);

        assertEquals(expected, actual);
        Files.deleteIfExists(file.toPath());
    }
}