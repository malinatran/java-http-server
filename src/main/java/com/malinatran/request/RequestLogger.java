package com.malinatran.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class RequestLogger {

    private ArrayList<String> loggedRequestLines;
    private String eTag;
    private char[] body;

    public RequestLogger() {
        loggedRequestLines = new ArrayList<String>();
        eTag = "";
        body = new char[0];
    }

    public void addRequestLine(Request request) {
        String initialLine = request.getMethod() + " " + request.getPath() + " " + request.getProtocolAndVersion();
        loggedRequestLines.add(initialLine + "\r\n");
    }

    public String getLoggedRequests() {
        String requestLines = "";

        for (String line : loggedRequestLines) {
            requestLines += line;
        }

        return requestLines;
    }

    public void setETagAndBody(String eTag, char[] body) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (eTag != null) {
            this.eTag = eTag;
            this.body = body;
        }
    }

    public String getETag() {
        return eTag;
    }

    public boolean hasBody() {
        return (getBody().length > 0);
    }

    public char[] getBody() {
        return body;
    }

    public void setBody(char[] body) {
        this.body = body;
    }
}
