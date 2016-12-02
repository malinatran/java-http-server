package com.malinatran.utility;

import com.malinatran.request.Request;

public abstract class Logger {

    public String getLoggedRequests() {
        return "";
    }

    public Request logRequest(Request request) {
        return request;
    }
}