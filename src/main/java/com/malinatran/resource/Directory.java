package com.malinatran.resource;

import com.malinatran.response.Formatter;

import java.io.File;

public class Directory {

    public static boolean existsInDirectory(String filePath) {
        File file = new File(getDirectory(filePath));
        String[] files;

        if (file.isDirectory()) {
            files = file.list();
            return hasFileName(files, getFileName(filePath));
        } else {
            return false;
        }
    }

    public static String getLinks(String directory, String filePath) {
        File file = new File(filePath);
        String message = "";

        if (file.isDirectory()) {
            String[] files = file.list();
            message = (files != null ? getAnchorTagLinks(directory, files) : "");
        }

        return message;
    }

    private static String getAnchorTagLinks(String directory, String[] files) {
        String links = "";

        for (String file : files) {
            links += getAnchorTagLink(directory, file);
        }

        return links;
    }

    private static String getAnchorTagLink(String directory, String file) {
        String html = ""; 
        
        if (directory != "") {
            html += "<a style=\"display: block\" href=\"/" + directory + "/" + file + "\">" + file + "</a>";
        } else {
            html += "<a style=\"display: block\" href=\"/" + file + "\">" + file + "</a>";
        }

        return Formatter.addLF(html);
    }

    private static String getDirectory(String filePath) {
        String fileName = getFileName(filePath);

        return filePath.replace(fileName, "");
    }

    private static String getFileName(String filePath) {
        int delimiter = filePath.lastIndexOf("/") + 1;
        int length = filePath.length();

        return filePath.substring(delimiter, length);
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
