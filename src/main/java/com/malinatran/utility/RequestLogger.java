package com.malinatran.utility;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Vector;

import static com.malinatran.utility.Method.PATCH;

public class RequestLogger {

    private Vector<String> loggedRequestLines;
    private String eTag;
    private char[] body;

    public RequestLogger() {
        loggedRequestLines = new Vector<String>();
        eTag = "";
        body = new char[0];
    }

    public char[] getBody() {
        return body;
    }

    public String getETag() {
        return eTag;
    }

    public String getLoggedRequests() {
        String requestLines = "";

        for (String line : loggedRequestLines) {
            requestLines += line;
        }

        return requestLines;
    }

    public boolean hasBody() {
        return body.length > 0;
    }

    public void logRequest(Request request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String method = request.getMethod();
        String path = request.getPath();
        addRequestLine(request);

        if (MethodTypeReader.isMethod(method, PATCH)) {
            handlePatch(request);
       } else if (MethodTypeReader.isPutOrPostToForm(method, path)) {
            handlePutOrPost(request);
        } else if (MethodTypeReader.isDeleteToForm(method, path)) {
            handleDelete();
        }
    }

    private List<String> addRequestLine(Request request) {
        String initialLine = request.getMethod() + " " + request.getPath() + " " + request.getProtocolAndVersion();
        loggedRequestLines.add(initialLine + "\r\n");

        return loggedRequestLines;
    }

    private RequestLogger handleDelete() {
        setBody(new char[0]);

        return this;
    }

    private RequestLogger handlePatch(Request request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String eTag = request.getHeaderValue(Header.IF_MATCH);
        char[] body = request.getBody();
        setETagAndBody(eTag, body);

        return this;
    }

    private RequestLogger handlePutOrPost(Request request) {
        char[] data = request.getBody();
        setBody(data);

        return this;
    }

    private RequestLogger setETagAndBody(String eTag, char[] body) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (eTag != null) {
            this.eTag = eTag;
            this.body = body;
        }

        return this;
    }

    private RequestLogger setBody(char[] body) {
        this.body = body;

        return this;
    }
}