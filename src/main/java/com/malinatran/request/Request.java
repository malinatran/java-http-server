package com.malinatran.request;

import com.malinatran.resource.ContentRangeHelper;
import com.malinatran.utility.Header;
import com.malinatran.utility.RangeParser;

import java.util.Hashtable;
import java.util.Map;

public class Request {

    private Map<String, String> headers;
    private String method;
    private String path;
    private String protocolAndVersion;
    private char[] body;
    private String directory;
    private Map<String, Integer> ranges;

    public Request() {
        headers = new Hashtable<String, String>();
    }

    public String getAbsolutePath() {
        return getDirectory() + getPath().replace("/", "").trim();
    }

    public char[] getBody() {
        return body;
    }

    public String getDirectory() {
        return directory;
    }

    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocolAndVersion() {
        return protocolAndVersion;
    }

    public Map<String, Integer> getRangeValues() {
        String header = getHeaderValue(Header.RANGE);
        ranges = new Hashtable<String, Integer>();

        if (getHeaderValue(Header.RANGE) != null) {
            String[] rangeValues = RangeParser.getValues(header);
            setRanges(rangeValues);
        }

        return ranges;
    }

    public String getRoute() {
        return method + " " + path;
    }

    public boolean hasHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public Request setBody(char[] body) {
        this.body = body;

        return this;
    }

    public Request setDirectory(String directory) {
        this.directory = directory;

        return this;
    }

    public Request setHeader(String header) {
        String[] keyAndValue = header.split(": ");
        String key = keyAndValue[0];
        String value = keyAndValue[1];
        headers.put(key, value);

        return this;
    }

    private Request setRanges(String[] rangeValues) {
        String rangeStart = rangeValues[0];
        String rangeEnd = rangeValues[1];

        if (rangeStart.length() > 0) {
            ranges.put(ContentRangeHelper.START, Integer.parseInt(rangeStart));
        }

        if (rangeEnd.length() > 0) {
            ranges.put(ContentRangeHelper.END, Integer.parseInt(rangeEnd));
        }

        return this;
    }

    public Request setRequestLine(String requestLine) {
        String[] parts = requestLine.split(" ");
        method = parts[0];
        path = parts[1];
        protocolAndVersion = parts[2];

        return this;
    }
}
