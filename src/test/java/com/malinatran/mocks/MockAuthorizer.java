package com.malinatran.mocks;

import com.malinatran.request.Method;
import com.malinatran.utility.Authorizer;

public class MockAuthorizer extends Authorizer {

    public static boolean hasValidRouteAndCredentials(String method, String path, String credentials) {
        return (method.equals(Method.GET) &&
                path.equals("/logs") &&
                hasValidCredentials(credentials));
    }

    public static boolean hasValidCredentials(String credentials) {
        return true;
    }
}