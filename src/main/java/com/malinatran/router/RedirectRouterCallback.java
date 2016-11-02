package com.malinatran.router;

import com.malinatran.constants.Header;
import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class RedirectRouterCallback implements RouterCallback {

    public void run(Request request, Response response) {
        String host = request.getHeaderValue("Host");
        String url = "http://" + host + "/";
        response.setStatus(Status.FOUND);
        response.setHeader(Header.LOCATION, url);
    }
}
