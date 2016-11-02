package com.malinatran.resource;

import com.malinatran.constants.FileType;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Directory {

    private static final String[] FILE_EXTENSIONS = { ".gif", ".jpeg", ".png" };
    private static Image image = new Image();
    private static TextFile textFile = new TextFile();

    public String getLinks(String directoryPath) {
        File directory = new File(directoryPath);
        String message = "";

        if (directory.isDirectory()) {
            String[] files = directory.list();
            message = (files != null ? getAnchorTagLinks(files) : "");
        }
        return message;
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

    public FileType getFileType(String fileName, Map<String, Integer> ranges) {
        if (isTextFile(fileName) && ranges.isEmpty()) {
            return FileType.TEXT;
        } else if (isTextFile(fileName) && !ranges.isEmpty()) {
            return FileType.PARTIAL_TEXT;
        } else if (isImageFile(fileName)) {
            return FileType.IMAGE;
        }
        return FileType.UNSUPPORTED;
    }

    public String getFileContent(String filePath) throws IOException {
        return textFile.readTextFile(filePath);
    }

    public String getFileContent(String filePath, Map<String, Integer> ranges) throws IOException {
        return textFile.readPartialTextFile(filePath, ranges);
    }

    public byte[] getBytes(String filePath) throws IOException {
        return image.extractBytes(filePath);
    }

    private Boolean isTextFile(String fileName) {
        return isFileWithoutExtension(fileName) || isFileWithTxtExtension(fileName);
    }

    private Boolean isImageFile(String fileName) {
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

    private Boolean isFileWithTxtExtension(String fileName) {
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