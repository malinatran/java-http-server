package com.malinatran.router;

import com.malinatran.constants.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class LogsRouterCallback implements RouterCallback {

    private static final String AUTHORIZATION = "Authorization";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String MESSAGE = "Basic realm=MALINA_REALM";

    public void run(Request request, Response response) {
        String credentials = request.getHeaderValue(AUTHORIZATION);

        RouterValidator validator = new RouterValidator();

        if (validator.isValidCredentials(credentials)) {
            response.setStatus(Status.OK);
        } else if (credentials == null || !validator.isValidCredentials(credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader(WWW_AUTHENTICATE, MESSAGE);
        }
    }
}
