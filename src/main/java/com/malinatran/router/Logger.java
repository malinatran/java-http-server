package com.malinatran.router;

import com.malinatran.request.Request;

import java.util.ArrayList;

public class Logger {

    private ArrayList<String> loggedRequestLines;

    public Logger() {
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
