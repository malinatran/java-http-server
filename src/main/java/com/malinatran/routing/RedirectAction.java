package com.malinatran.routing;

import com.malinatran.utility.RequestLogger;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class RedirectAction implements Action {

    private final String HTTP = "http://";

    public void run(Request request, Response response) {
        String host = request.getHeaderValue(Header.HOST);
        String url = HTTP + host + "/";
        response.setStatus(Status.FOUND);
        response.setHeader(Header.LOCATION, url);
    }

    public void run(Response response, RequestLogger logger) {}

    public void run(Request request, Response response, RequestLogger logger) {}
}