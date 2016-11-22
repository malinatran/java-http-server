package com.malinatran.response;

import java.util.Map;
import java.util.Hashtable;

public class Response {

    private String protocol;
    private String status;
    private byte[] bodyContent;
    private Map<String, String> headers;

    public Response(String protocol) {
        this.protocol = protocol;
        this.headers = new Hashtable<String, String>();
    }

    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    public byte[] getBodyContent() {
        return (bodyContent == null ? new byte[0] : bodyContent);
    }

    private String getHeaderLines() {
        return Formatter.formatHeaderLines(headers);
    }

    private String getHeaders() {
        return getStatusLine() + getHeaderLines();
    }

    public byte[] getHeadersAndBody() {
        byte[] header = getHeaders().getBytes();
        byte[] body = getBodyContent();
        byte[] response = new byte[header.length + body.length];

        System.arraycopy(header, 0, response, 0, header.length);
        System.arraycopy(body, 0, response, header.length, body.length);

        return response;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusLine() {
        String statusLine = String.format("%s %s", protocol, status);

        return Formatter.addCRLF(statusLine);
    }

    public Response setBodyContent(byte[] bodyContent) {
        this.bodyContent = bodyContent;

        return this;
    }

    public Response setBodyContent(String bodyContent) {
        this.bodyContent = bodyContent.getBytes();

        return this;
    }

    public Response setHeader(String key, String val) {
        headers.put(key, val);

        return this;
    }

    public Response setStatus(String status) {
        this.status = status;

        return this;
    }
}