package com.malinatran.router;

import java.io.*;

public class DirectoryReader {

    private static final String[] IMAGE_EXTENSIONS = { ".gif", ".jpeg", ".png" };

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

    public String readTextFile(String directoryPath, String fileName) throws IOException {
        String content = "";
        String line;

        FileReader fileReader = new FileReader(directoryPath + fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }

        return content;
    }

    public Boolean isTextFile(String fileName) {
        return (fileName.indexOf(".") == -1) || (fileName.endsWith(".txt"));
    }

    public Boolean isImageFile(String fileName) {
        for (String extension : IMAGE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
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