package com.malinatran.request;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private Map<String, String> headers;
    private String method;
    private String path;
    private String protocolAndVersion;
    private String body;
    private String directoryPath;

    public Request() {
        headers = new HashMap<String, String>();
    }

    public void setDirectoryPath(String directoryPath) {
       this.directoryPath = directoryPath;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setHeader(String header) {
        String[] keyAndValue = header.split(": ");
        String key = keyAndValue[0];
        String value = keyAndValue[1];
        headers.put(key, value);
    }

    public void setBody(String body) {
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

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocolAndVersion() {
        return protocolAndVersion;
    }

    public String getBody() {
        return body; }
}
