package com.malinatran.router;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;

public class RedirectRouterCallback implements RouterCallback {

    private final String HTTP = "http://";

    public void run(Request request, Response response) {
        String host = request.getHeaderValue(Header.HOST);
        String url = HTTP + host + "/";
        response.setStatus(Status.FOUND);
        response.setHeader(Header.LOCATION, url);
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}
