package com.malinatran.request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class RequestLogger {

    private ArrayList<String> loggedRequestLines;
    private String eTag;
    private char[] patchedContent;

    public RequestLogger() {
        loggedRequestLines = new ArrayList<String>();
        eTag = "";
        patchedContent = new char[0];
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

    public void addData(String eTag, char[] data) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (eTag != null) {
            this.eTag = eTag;
            this.patchedContent = data;
        }
    }

    public String getPatchedContent() {
        return String.valueOf(getPatchedContentAsCharArray());
    }

    public String getETag() {
        return eTag;
    }

    public boolean hasPatchedContent() {
        return (getPatchedContentAsCharArray().length > 0);
    }

    private char[] getPatchedContentAsCharArray() {
        return patchedContent;
    }
}
