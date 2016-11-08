package com.malinatran.response;

import java.util.Map;
import java.util.HashMap;

public class Response {

    private String protocol;
    private String status;
    private byte[] bodyContent;
    private Map<String, String> headers;

    public Response(String protocol) {
        this.protocol = protocol;
        this.headers = new HashMap<String, String>();
    }

    public boolean hasHeader(String key) {
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
