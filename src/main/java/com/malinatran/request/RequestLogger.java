package com.malinatran.request;

import java.util.ArrayList;

public class RequestLogger {

    private ArrayList<String> loggedRequestLines;
    private char[] data;

    public RequestLogger() {
        loggedRequestLines = new ArrayList<String>();
        data = new char[0];
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

    public void addData(String eTag, char[] data) {
        if (!eTag.isEmpty()) {
            this.data = data;
        }
    }

    public String getData() {
       return String.valueOf(getDataInCharArray());
    }

    public boolean hasData() {
        return (getDataInCharArray().length > 0);
    }

    private char[] getDataInCharArray() {
        return data;
    }
}
