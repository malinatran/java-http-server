package com.malinatran.router;

import com.malinatran.constant.Header;
import com.malinatran.constant.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;

public class LogsRouterCallback implements RouterCallback {

    private RouterValidator validator = new RouterValidator();

    public void run(Request request, Response response) {
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);

        if (credentials == null || !validator.isValidCredentials(credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader(Header.WWW_AUTHENTICATE, Header.MESSAGE);
        }
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}
