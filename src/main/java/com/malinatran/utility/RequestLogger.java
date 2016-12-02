package com.malinatran.utility;
import com.malinatran.request.Request;
import com.malinatran.response.Formatter;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class RequestLogger extends Logger {

    public static final String ETAG = "eTag";
    public static final String BODY = "body";
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

    public Request logRequest(Request request) {
        addRequestLine(request);
        addETagAndBody(request);

        return request;
    }

    private List<String> addRequestLine(Request request) {
        String method = request.getMethod();
        String path = request.getPath();
        String protocolAndVersion = request.getProtocolAndVersion();
        String initialLine = String.format("%s %s %s", method, path, protocolAndVersion);
        loggedRequestLines.add(Formatter.addCRLF(initialLine));

        return loggedRequestLines;
    }

    private Request addETagAndBody(Request request) {
        RequestBuilder builder = new RequestBuilder();
        Map<String, String> array = builder.getRequestBody(request);

        if (containsETagAndBody(array)) {
            setETagAndBody(array);
        } else if (containsBody(array)) {
            setBody(array);
        }

       return request;
    }

    private boolean containsETagAndBody(Map<String, String> array) {
        return array.containsKey(ETAG) && containsBody(array);
    }

    private boolean containsBody(Map<String, String> array) {
        return array.containsKey(BODY);
    }

   private RequestLogger setETagAndBody(Map<String, String> array) {
       String eTag = array.get(ETAG);
       char[] body = array.get(BODY).toCharArray();

       if (eTag != null) {
           this.eTag = eTag;
           this.body = body;
       }

        return this;
    }

    private RequestLogger setBody(Map<String, String> array) {
        char[] body = array.get(BODY).toCharArray();
        this.body = body;

        return this;
    }
}
