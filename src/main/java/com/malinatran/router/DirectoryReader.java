package com.malinatran.router;

import java.io.*;
import java.util.Map;

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

    public int getCharCountOfTextFile(String directoryPath, String fileName) throws IOException {
        String content = readTextFile(directoryPath, fileName);
        return content.length();
    }

    // TODO: read only set of bytes determined by rangeBytes
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

    public int[] calculatePartialContentSize(Map<String, Integer> rangeBytes, int totalCount) {
        int[] contentSize = new int[2];

        if (hasStartAndEndRange(rangeBytes)) {
            contentSize[0] = rangeBytes.get("Start");
            contentSize[1] = rangeBytes.get("End");
        } else if (hasStartRangeOnly(rangeBytes)) {
            contentSize[0] = rangeBytes.get("Start");
            contentSize[1] = totalCount;
        } else if (hasEndRangeOnly(rangeBytes)) {
            contentSize[0] = totalCount - (rangeBytes.get("End") - 1);
            contentSize[1] = totalCount;
        }

        return contentSize;
    }

    private Boolean hasStartAndEndRange(Map<String, Integer> rangeBytes) {
        return hasStartRange(rangeBytes) && hasEndRange(rangeBytes);
    }

    private Boolean hasStartRangeOnly(Map<String, Integer> rangeBytes) {
       return hasStartRange(rangeBytes) && !hasEndRange(rangeBytes);
    }

    private Boolean hasEndRangeOnly(Map<String, Integer> rangeBytes) {
        return !hasStartRange(rangeBytes) && hasEndRange(rangeBytes);
    }

    private Boolean hasStartRange(Map<String, Integer> rangeBytes) {
        return rangeBytes.containsKey("Start");
    }

    private Boolean hasEndRange(Map<String, Integer> rangeBytes) {
        return rangeBytes.containsKey("End");
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