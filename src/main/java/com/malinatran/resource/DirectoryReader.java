package com.malinatran.resource;

import java.io.File;

public class DirectoryReader {

    private static final String[] FILE_EXTENSIONS = { ".gif", ".jpeg", ".png" };

    public String getLinks(String directoryPath) {
        File directory = new File(directoryPath);
        String[] files = directory.list();

        return (files != null ? getAnchorTagLinks(files) : "");
    }

    public Boolean existsInDirectory(String directoryPath, String fileName) {
        File directory = new File(directoryPath);
        String[] files;

        if (directory.isDirectory()) {
            files = directory.list();
            return hasFileName(files, fileName);
        } else {
           return false;
        }
    }

    public Boolean isTextFile(String fileName) {
        return isFileWithoutExtension(fileName) || isFileWIthTxtExtension(fileName);
    }

    public Boolean isImageFile(String fileName) {
        for (String extension : FILE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    private Boolean isFileWithoutExtension(String fileName) {
        return (fileName.indexOf(".") == -1);
    }

    private Boolean isFileWIthTxtExtension(String fileName) {
        return (fileName.endsWith(".txt"));
    }

    private String getAnchorTagLinks(String[] files) {
        String links = "";

        for (String file : files) {
            links += "<a style=\"display: block\" href=\"/" + file + "\">" + file + "</a>\n";
        }

        return links;
    }

    private Boolean hasFileName(String[] files, String fileName) {
        for (String file : files) {
            if (file.equals(fileName)) {
                return true;
            }
        }

        return false;
    }
}