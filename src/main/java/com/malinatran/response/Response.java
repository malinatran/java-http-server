package com.malinatran.response;

import com.malinatran.constants.Status;
import com.malinatran.router.Logger;
import java.util.*;

public class Response {

    private static final String LOCATION = "Location";

    private String protocol;
    private String status;
    private String bodyContent;
    private Map<String, String> headers;

    public Response(String protocol) {
        this.protocol = protocol;
        this.headers = new HashMap<String, String>();
    }

    public void redirectTo(String url) {
        setStatus(Status.FOUND);
        setHeader(LOCATION, url);
    }

    public void setLogsToBody(Logger logger) {
        setStatus(Status.OK);
        setBodyContent(logger.getLoggedRequests());
    }

    public void setImage(String fileType, byte[] image) {
        setStatus(Status.OK);
        setHeader("Content-Type", "image/" + fileType);
        setHeader("Content-Length", String.valueOf(image.length));
        setBodyContent(image);
    }

    public Boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHeader(String key, String val) {
        headers.put(key, val);
    }

    public void setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = new String(bodyContent);
    }

    public String getStatus() {
        return status;
    }

    public byte[] getBodyContent() {
        return bodyContent == null ? new byte[0] : Formatter.addNewLines(bodyContent).getBytes();
    }

    public byte[] getHeaders() {
        String header = getStatusLine() + getHeaderLines();
        return header.getBytes();
    }

    public String getStatusLine() {
        return Formatter.addNewLine(protocol + " " + status);
    }

    private String getHeaderLines() {
        return Formatter.formatHeaderLines(headers);
    }
}