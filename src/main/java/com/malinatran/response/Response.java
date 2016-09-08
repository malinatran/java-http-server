package com.malinatran.response;

import com.malinatran.constants.Status;
import com.malinatran.router.Logger;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private static final String LOCATION = "Location";

    private String protocol;
    private String status;
    private String bodyContent;
    private Map<String, String> headers;


    public Response(String protocol) {
        this(protocol, null);
    }

    public Response(String protocol, String body) {
        this.protocol = protocol;
        this.headers = new HashMap<String, String>();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getBodyContent() {
        return (bodyContent == null ? "" : "\r\n" + bodyContent + "\r\n");
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
        setHeader(LOCATION, url);
    }

    public void setLogsToBody(Logger logger) {
        setStatus(Status.OK);
        setBodyContent(logger.getLoggedRequests());
    }

    public String getStatusLine() {
        return protocol + " " + status;
    }

    public String toString() {
        String response = Formatter.addNewLine(getStatusLine());
        response += getHeaderLines();
        response += getBodyContent();

        return response;
    }

    private String getHeaderLines() {
        return Formatter.formatHeaderLines(headers);
    }
}
