package com.malinatran.routing;

import com.malinatran.utility.Authorizer;
import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class AuthorizedAction implements Action {

    public static final String MESSAGE = "Basic realm=MALINA_REALM";

    public void run(Request request, Response response) {
        String method = request.getMethod();
        String path = request.getPath();
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);

        if (!Authorizer.hasValidRouteAndCredentials(method, path, credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader(Header.WWW_AUTHENTICATE, MESSAGE);
        }
    }
}