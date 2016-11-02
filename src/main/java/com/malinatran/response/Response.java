package com.malinatran.response;

import com.malinatran.constants.Status;
import com.malinatran.constants.Header;
import com.malinatran.request.RequestLogger;
import com.malinatran.resource.TextFile;

import java.util.Map;
import java.util.HashMap;

import static com.malinatran.resource.TextFile.END;
import static com.malinatran.resource.TextFile.START;

public class Response {

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
        setHeader(Header.LOCATION, url);
    }

    public void setLogsToBody(RequestLogger requestLogger) {
        setStatus(Status.OK);
        setBodyContent(requestLogger.getLoggedRequests());
    }

    public void setText(String text) {
        setStatus(Status.OK);
        setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        setBodyContent(text);
    }

    public void setText(String text, Map<String, Integer> range) {
        setStatus(Status.PARTIAL_CONTENT);
        setHeader(Header.CONTENT_TYPE, Header.TEXT_PLAIN);
        setHeader(Header.CONTENT_RANGE, range.get(START) + "-" + range.get(END));
        setBodyContent(text);
    }

    public void setImage(String fileType, byte[] image) {
        setStatus(Status.OK);
        setHeader(Header.CONTENT_TYPE, Header.IMAGE + fileType);
        setHeader(Header.CONTENT_LENGTH, String.valueOf(image.length));
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
        return ResponseFormatter.addNewLine(protocol + " " + status);
    }

    private String getHeaders() {
        return getStatusLine() + getHeaderLines();
    }

    private String getHeaderLines() {
        return ResponseFormatter.formatHeaderLines(headers);
    }
}