package com.malinatran.utility;

import com.malinatran.request.MethodTypeReader;
import com.malinatran.request.Request;
import com.malinatran.response.Formatter;
import com.malinatran.routing.Header;

import java.util.List;
import java.util.Vector;

import static com.malinatran.request.Method.PATCH;

public class RequestLogger {

    // TODO: Add more tests
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

    // TODO: Handles different types of request (should wrap to handle polymorphically)
    public void logRequest(Request request) {
        String method = request.getMethod();
        String path = request.getPath();
        String protocolAndVersion = request.getProtocolAndVersion();
        addRequestLine(method, path, protocolAndVersion);

        if (method.equals(PATCH)) {
            handlePatch(request);
        } else if (MethodTypeReader.isPutOrPostToForm(method, path)) {
            handlePutOrPost(request);
        } else if (MethodTypeReader.isDeleteToForm(method, path)) {
            handleDelete();
        }
    }

    private List<String> addRequestLine(String method, String path, String protocolAndVersion) {
        String initialLine = String.format("%s %s %s", method, path, protocolAndVersion);
        loggedRequestLines.add(Formatter.addCRLF(initialLine));

        return loggedRequestLines;
    }

    private RequestLogger handlePatch(Request request) {
        String eTag = request.getHeaderValue(Header.IF_MATCH);
        char[] body = request.getBody();
        setETagAndBody(eTag, body);

        return this;
    }

    private RequestLogger handleDelete() {
        setBody(new char[0]);

        return this;
    }

    private RequestLogger handlePutOrPost(Request request) {
        char[] data = request.getBody();
        setBody(data);

        return this;
    }

    private RequestLogger setETagAndBody(String eTag, char[] body) {
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