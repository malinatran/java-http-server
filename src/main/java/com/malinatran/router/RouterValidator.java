package com.malinatran.router;

import com.malinatran.constants.Method;
import com.malinatran.request.Request;

public class RouterValidator {

    private static final String CREDENTIALS = "Basic YWRtaW46aHVudGVyMg==";
    private static final String AUTHORIZATION = "Authorization";

    public boolean isValidRouteAndCredentials(Request request) {
        String method = request.getMethod();
        String path = request.getPath();
        String credentials = request.getHeaderValue(AUTHORIZATION);

        return (method.equals(Method.GET) &&
                path.equals("/logs") &&
                isValidCredentials(credentials));
    }

    public boolean isValidCredentials(String credentials) {
        return ((credentials != null) && credentials.equals(CREDENTIALS));
    }
}
