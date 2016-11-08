package com.malinatran.router;

import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.request.RequestLogger;
import com.malinatran.response.Response;

import java.io.IOException;

public class AuthorizedRouterCallback implements RouterCallback {

    public static final String MESSAGE = "Basic realm=MALINA_REALM";
    private RouterValidator validator = new RouterValidator();

    public void run(Request request, Response response) {
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);

        if (isInvalid(credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader(Header.WWW_AUTHENTICATE, MESSAGE);
        }
    }

    private boolean isInvalid(String credentials) {
        return (credentials == null || !validator.isValidCredentials(credentials));
    }

    public void run(Response response, RequestLogger logger) throws IOException {}
    public void run(Request request, Response response, RequestLogger logger) throws IOException {}
}
