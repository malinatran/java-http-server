package com.malinatran.request;

import com.malinatran.constant.FileType;
import com.malinatran.constant.Header;
import com.malinatran.utility.RangeParser;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private final String[] FILE_EXTENSIONS = { ".gif", ".jpeg", ".jpg", ".png" };
    private Map<String, String> headers;
    private String method;
    private String path;
    private String protocolAndVersion;
    private char[] body;
    private String directoryPath;
    private Map<String, Integer> ranges;
    private RangeParser rangeParser;

    public Request() {
        headers = new HashMap<String, String>();
        rangeParser = new RangeParser();
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void setHeader(String header) {
        String[] keyAndValue = header.split(": ");
        String key = keyAndValue[0];
        String value = keyAndValue[1];
        headers.put(key, value);
    }

    public void setBody(char[] body) {
        this.body = body;
    }

    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }

    public boolean hasHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public void setRequestLine(String requestLine) {
        String[] parts = requestLine.split(" ");
        method = parts[0];
        path = parts[1];
        protocolAndVersion = parts[2];
    }

    public Map<String, Integer> getRangeValues() {
        String header = getHeaderValue(Header.RANGE);
        ranges = new HashMap<String, Integer>();

        if (getHeaderValue(Header.RANGE) != null) {
            String[] rangeValues = rangeParser.getValues(header);
            setRanges(rangeValues);
        }

        return ranges;
    }

    private void setRanges(String[] rangeValues) {
        String rangeStart = rangeValues[0];
        String rangeEnd = rangeValues[1];

        if (rangeStart.length() > 0) {
            ranges.put("Start", Integer.parseInt(rangeStart));
        }

        if (rangeEnd.length() > 0) {
            ranges.put("End", Integer.parseInt(rangeEnd));
        }
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getCleanPath() {
        return getPath().replace("/", "").trim();
    }

    public String getRoute() {
        return method + " " + path;
    }

    public String getProtocolAndVersion() {
        return protocolAndVersion;
    }

    public char[] getBody() {
        return body; }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public String getFilePath() {
        return getDirectoryPath() + getCleanPath();

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

    public boolean isTextFile(String fileName) {
        return isFileWithoutExtension(fileName) || isFileWithTxtExtension(fileName);
    }

    private boolean isImageFile(String fileName) {
        for (String extension : FILE_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }

    private boolean isFileWithoutExtension(String fileName) {
        return (fileName.indexOf(".") == -1);
    }

    private boolean isFileWithTxtExtension(String fileName) {
        return (fileName.endsWith(".txt"));
    }

}
