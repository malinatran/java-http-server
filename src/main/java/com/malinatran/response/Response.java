package com.malinatran.response;

import com.malinatran.constants.Status;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private String method;
    private String path;
    private String body;
    private String protocol;
    private String status;

    private Map<String, String> headers;

    public Response(String method, String protocol, String path) {
        this(method, protocol, path, null);
    }

    public Response(String method, String protocol, String path, String body) {
        this.method = method;
        this.protocol = protocol;
        this.path = path;
        this.body = body;
        this.headers = new HashMap<String, String>();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
       return status;
    }

    public void setHeader(String key, String val) {
        headers.put(key, val);
    }

    public Boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    public void redirectTo(String url) {
        setStatus(Status.FOUND);
        setHeader("Location", url);
    }

    public String getStatusLine() {
        String statusLine = protocol + " " + status;

        return statusLine;
    }

    public String toString() {
        String response = Formatter.addNewLine(getStatusLine());
        response += getHeaderLines();

        return response;
    }

    private String getHeaderLines() {
        return Formatter.formatHeaderLines(headers);
    }
}
