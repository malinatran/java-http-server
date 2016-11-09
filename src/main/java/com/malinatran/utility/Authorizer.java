package com.malinatran.utility;

import com.malinatran.request.Request;

public class Authorizer {

    private static final String CREDENTIALS = System.getenv("COB_SPEC_CREDENTIALS");
    private static final String AUTHORIZATION = "Authorization";

    public static boolean hasValidCredentials(String credentials) {
        return ((credentials != null) && credentials.equals(CREDENTIALS));
    }

    public static boolean hasValidRouteAndCredentials(Request request) {
        String method = request.getMethod();
        String path = request.getPath();
        String credentials = request.getHeaderValue(AUTHORIZATION);

        return (method.equals(Method.GET) &&
                path.equals("/logs") &&
                hasValidCredentials(credentials));
    }
}