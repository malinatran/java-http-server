package com.malinatran;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private Map<String, String> headers;
    private String methodType;
    private String uri;
    private String version;
    private String body;

    public Request() {
        headers = new HashMap<String, String>();
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
        methodType = parts[0];
        uri = parts[1];
        version = parts[2];
    }

    public String getMethodType() {
        return methodType;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() { return version; }

    public String getBody() { return body; }
}
