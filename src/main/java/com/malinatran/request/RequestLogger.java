package com.malinatran.request;

import java.util.ArrayList;

public class RequestLogger {

    private ArrayList<String> loggedRequestLines;

    public RequestLogger() {
        loggedRequestLines = new ArrayList<String>();
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
}
