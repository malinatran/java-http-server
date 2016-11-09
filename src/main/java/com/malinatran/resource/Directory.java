package com.malinatran.resource;

import java.io.File;

public class Directory {

    public static boolean existsInDirectory(String directoryPath) {
        File directory = new File(getDirectoryPath(directoryPath));
        String[] files;

        if (directory.isDirectory()) {
            files = directory.list();
            return hasFileName(files, getFileName(directoryPath));
        } else {
            return false;
        }
    }

    public static String getLinks(String directoryPath) {
        File directory = new File(directoryPath);
        String message = "";

        if (directory.isDirectory()) {
            String[] files = directory.list();
            message = (files != null ? getAnchorTagLinks(files) : "");
        }
        return message;
    }

    private static String getAnchorTagLinks(String[] files) {
        String links = "";

        for (String file : files) {
            links += "<a style=\"display: block\" href=\"/" + file + "\">" + file + "</a>\n";
        }

        return links;
    }

    private static String getDirectoryPath(String path) {
        String fileName = getFileName(path);

        return path.replace(fileName, "");
    }

    private static String getFileName(String path) {
        int delimiter = path.lastIndexOf("/") + 1;
        int length = path.length();

        return path.substring(delimiter, length);
    }

    private static boolean hasFileName(String[] files, String fileName) {
        for (String file : files) {
            if (file.equals(fileName)) {
                return true;
            }
        }

        return false;
    }
}