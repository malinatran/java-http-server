package com.malinatran.resource;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Directory {

    private Image image = new Image();
    private TextFile textFile = new TextFile();

    public String getLinks(String directoryPath) {
        File directory = new File(directoryPath);
        String message = "";

        if (directory.isDirectory()) {
            String[] files = directory.list();
            message = (files != null ? getAnchorTagLinks(files) : "");
        }
        return message;
    }

    public boolean existsInDirectory(String directoryPath) {
        File directory = new File(getDirectoryPath(directoryPath));
        String[] files;

        if (directory.isDirectory()) {
            files = directory.list();
            return hasFileName(files, getFileName(directoryPath));
        } else {
            return false;
        }
    }

    public String getContent(String filePath) throws IOException {
        return textFile.readTextFile(filePath);
    }

    public String getContent(String filePath, Map<String, Integer> ranges) throws IOException {
        return textFile.readPartialTextFile(filePath, ranges);
    }

    public byte[] getBytes(String filePath) throws IOException {
        return image.extractBytes(filePath);
    }

    private String getAnchorTagLinks(String[] files) {
        String links = "";

        for (String file : files) {
            links += "<a style=\"display: block\" href=\"/" + file + "\">" + file + "</a>\n";
        }

        return links;
    }

    private boolean hasFileName(String[] files, String fileName) {
        for (String file : files) {
            if (file.equals(fileName)) {
                return true;
            }
        }

        return false;
    }

    private String getFileName(String path) {
        int delimiter = path.lastIndexOf("/") + 1;
        int length = path.length();

        return path.substring(delimiter, length);
    }

    private String getDirectoryPath(String path) {
        String fileName = getFileName(path);

        return path.replace(fileName, "");
    }
}
