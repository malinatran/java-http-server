package com.malinatran.setup;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class DirectoryArg extends Arg {

    public static final String HOME_DIRECTORY = System.getProperty("user.dir");
    public static final String DEFAULT_DIRECTORY = File.separator + "public" + File.separator;
    public static final String DEFAULT_PATH = HOME_DIRECTORY + DEFAULT_DIRECTORY;
    public static final String DIRECTORY = "Directory";
    public static final String FLAG = "-d";

    public boolean isFlag(String arg) {
        return arg.equals(FLAG);
    }

    public String setString(Map<String, String> configuration) {
        String valid = DEFAULT_PATH;
        String directory = configuration.get(FLAG);

        if (directory != null) {
            valid = getDirectoryOrErrorMessage(valid, HOME_DIRECTORY + directory);
        }

        return valid;
    }

    private String getDirectoryOrErrorMessage(String valid, String directory) {
        if (exists(directory)) {
            return directory;
        } else {
            printError(directory);
        }

        return valid;
    }

    private boolean exists(String directory) {
        Path path = Paths.get(directory);

        return Files.exists(path);
    }

    void printError(String directory) {
        CommandLinePrinter.print(DIRECTORY, directory, INVALID);
        System.exit(-1);
    }
}