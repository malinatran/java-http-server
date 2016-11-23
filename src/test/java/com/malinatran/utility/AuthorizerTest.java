package com.malinatran.utility;

import com.malinatran.mocks.MockAuthorizer;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizerTest {

    private String method;
    private String path;
    private String credentials;

    @Test
    public void hasValidRouteAndCredentialsReturnsTrueWithCorrectRouteAndCredentials() {
        method = "GET";
        path = "/logs";

        boolean result = MockAuthorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertTrue(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectMethod() {
        method = "POST";
        path = "/logs";
        credentials = "creds";

        boolean result = MockAuthorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectPath() {
        method = "GET";
        path = "/log";
        credentials = "creds";

        boolean result = MockAuthorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }

    @Test
    public void hasValidRouteAndCredentialsReturnsFalseWithIncorrectCredentials() {
        method = "GET";
        path = "/logs";
        credentials = "Hello world";

        boolean result = Authorizer.hasValidRouteAndCredentials(method, path, credentials);

        assertFalse(result);
    }
}