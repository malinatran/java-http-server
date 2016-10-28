package com.malinatran.response;

import com.malinatran.constants.Status;
import com.malinatran.router.Logger;
import java.util.*;

public class Response {

    private static final String LOCATION = "Location";

    private String protocol;
    private String status;
    private byte[] bodyContent;
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

    public void setText(String text) {
        setStatus(Status.OK);
        setHeader("Content-Type", "text/plain");
        setBodyContent(text);
    }

    public void setPartialText(String text, Map<String, Integer> range) {
        setStatus(Status.PARTIAL_CONTENT);
        setHeader("Content-Type", "text/plain");
        setHeader("Content-Range", range.get("Start") + "-" + range.get("End"));
        setBodyContent(text);
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
        this.bodyContent = bodyContent.getBytes();
    }

    public void setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;
    }

    public String getStatus() {
        return status;
    }

    public byte[] getResponseHeadersAndBody() {
        byte[] header = getHeaders().getBytes();
        byte[] body = getBodyContent();
        byte[] response = new byte[header.length + body.length];

        System.arraycopy(header, 0, response, 0, header.length);
        System.arraycopy(body, 0, response, header.length, body.length);

        return response;
    }

    public byte[] getBodyContent() {
        return (bodyContent == null ? new byte[0] : bodyContent);
    }

    public String getStatusLine() {
        return Formatter.addNewLine(protocol + " " + status);
    }

    private String getHeaders() {
        return getStatusLine() + getHeaderLines();
    }

    private String getHeaderLines() {
        return Formatter.formatHeaderLines(headers);
    }
}