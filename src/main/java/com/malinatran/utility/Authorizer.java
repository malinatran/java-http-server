package com.malinatran.utility;

public class Authorizer {

    private static final String CREDENTIALS = System.getenv("JAVA_SERVER_TOKEN");

    public static boolean hasValidRouteAndCredentials(String method, String path, String credentials) {
        return (method.equals(Method.GET) &&
                path.equals("/logs") &&
                hasValidCredentials(credentials));
    }

    private static boolean hasValidCredentials(String credentials) {
        return ((credentials != null) && credentials.equals(CREDENTIALS));
    }
}