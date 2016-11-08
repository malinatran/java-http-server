package com.malinatran.request;

import com.malinatran.utility.Header;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.malinatran.utility.Method.PATCH;

public class RequestLogger {

    private ArrayList<String> loggedRequestLines;
    private String eTag;
    private char[] body;

    public RequestLogger() {
        loggedRequestLines = new ArrayList<String>();
        eTag = "";
        body = new char[0];
    }

    public void logRequest(Request request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String method = request.getMethod();
        String path = request.getPath();
        addRequestLine(request);

        if (MethodReader.isMethod(method, PATCH)) {
            handlePatch(request);
       } else if (MethodReader.isPutOrPostToForm(method, path)) {
            handlePutOrPost(request);
        } else if (MethodReader.isDeleteToForm(method, path)) {
            handleDelete();
        }
    }

    public String getLoggedRequests() {
        String requestLines = "";

        for (String line : loggedRequestLines) {
            requestLines += line;
        }

        return requestLines;
    }

    public String getETag() {
        return eTag;
    }

    public char[] getBody() {
        return body;
    }

    public boolean hasBody() {
        return body.length > 0;
    }

    private void addRequestLine(Request request) {
        String initialLine = request.getMethod() + " " + request.getPath() + " " + request.getProtocolAndVersion();
        loggedRequestLines.add(initialLine + "\r\n");
    }

    private void handlePatch(Request request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String eTag = request.getHeaderValue(Header.IF_MATCH);
        char[] body = request.getBody();

        setETagAndBody(eTag, body);
    }

    private void setETagAndBody(String eTag, char[] body) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (eTag != null) {
            this.eTag = eTag;
            this.body = body;
        }
    }

    private void handlePutOrPost(Request request) {
        char[] data = request.getBody();
        setBody(data);
    }

    private void handleDelete() {
        setBody(new char[0]);
    }

    private void setBody(char[] body) {
        this.body = body;
    }
}