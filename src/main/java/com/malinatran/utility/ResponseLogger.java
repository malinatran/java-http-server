package com.malinatran.utility;

import com.malinatran.response.Response;

public class ResponseLogger {
    public Response response;

    public void logResponse(Response response) {
        this.response = response;
    }

    public Response getLoggedResponse() {
        return response;
    }
}