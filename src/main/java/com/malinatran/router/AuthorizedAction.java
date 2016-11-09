package com.malinatran.router;

import com.malinatran.utility.Authorizer;
import com.malinatran.utility.Header;
import com.malinatran.utility.Status;
import com.malinatran.request.Request;
import com.malinatran.response.Response;

public class AuthorizedAction implements Action {

    public static final String MESSAGE = "Basic realm=MALINA_REALM";

    public void run(Request request, Response response) {
        String credentials = request.getHeaderValue(Header.AUTHORIZATION);

        if (isInvalid(credentials)) {
            response.setStatus(Status.UNAUTHORIZED);
            response.setHeader(Header.WWW_AUTHENTICATE, MESSAGE);
        }
    }

    private boolean isInvalid(String credentials) {
        return (credentials == null || !Authorizer.hasValidCredentials(credentials));
    }
}