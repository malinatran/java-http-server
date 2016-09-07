package com.malinatran.router;

import com.malinatran.request.Request;

import java.util.ArrayList;

public class Logger {

    private ArrayList<String> loggedRequestLines;
    private String requestLines;

    public Logger() {
        loggedRequestLines = new ArrayList<String>();
        requestLines = "";
    }

    public void addRequestLine(Request request) {
        String initialLine = request.getMethod() + " " + request.getPath() + " " + request.getProtocolAndVersion();
        loggedRequestLines.add(initialLine + "\r\n");
    }

    public String getLoggedRequests() {
        for (String line : loggedRequestLines) {
            requestLines += line;
        }

        return requestLines;
    }
}
